import java.io.FileInputStream
import java.util.*

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("kotlin-android-extensions")
  id("kotlinx-serialization")
  id("com.github.ben-manes.versions")
  id("io.fabric")
  id("com.github.triplet.play")
  id("com.squareup.sqldelight")
}

apply {
  plugin("androidx.navigation.safeargs")
}

val keystoreProperties = Properties().apply {
  try {
    load(FileInputStream(file("../keystore.properties")))
  } catch (_: Exception) {
    // ignore
  }
}

android {
  compileSdkVersion(BuildVersions.targetSdk)

  ndkVersion = BuildVersions.ndk

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  defaultConfig {
    applicationId = "com.satis.app"
    minSdkVersion(BuildVersions.minSdk)
    targetSdkVersion(BuildVersions.targetSdk)
    versionCode = getVersionCodeOrDefault().toInt()
    versionName = getVersionCodeOrDefault()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    buildConfigField("String", "UNSPLASH_CLIENT_ID", "\"${keystoreProperties["unsplashClientId"]}\"")
  }
  signingConfigs {
    create("release") {
      keyAlias = keystoreProperties["keyAlias"] as? String
      keyPassword = keystoreProperties["keyPassword"] as? String
      storeFile = file(keystoreProperties["storeFile"] ?: "dummy")
      storePassword = keystoreProperties["storePassword"] as? String
    }
  }
  buildTypes {
    getByName("debug") {
      buildConfigField("Long", "BUILD_TIME", "0L")
    }
    getByName("release") {
      buildConfigField("Long", "BUILD_TIME", "${System.currentTimeMillis()}L")
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs["release"]
    }
  }

  packagingOptions {
    pickFirst("META-INF/atomicfu.kotlin_module")
    pickFirst("META-INF/kotlinx-coroutines-core.kotlin_module")
    pickFirst("META-INF/kotlinx-serialization-runtime.kotlin_module")
  }

  buildFeatures {
    viewBinding = true
  }
}

play {
  defaultToAppBundles = true
  serviceAccountCredentials = file("../service-account.json")
  track = "internal"
}

androidExtensions {
  isExperimental = true
}

dependencies {
  implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

  // AndroidX
  implementation(Deps.androidx.appCompat)
  implementation(Deps.androidx.cardView)
  implementation(Deps.androidx.constraintLayout)
  implementation(Deps.androidx.coreKtx)
  implementation(Deps.androidx.fragment.fragment)
  implementation(Deps.androidx.concurrent) {
    exclude(group = "com.google.guava", module = "listenablefuture")
  }
  implementation(Deps.androidx.lifecycle.extensions)
  implementation(Deps.androidx.lifecycle.runtime)
  implementation(Deps.androidx.navigation.fragmentKtx)
  implementation(Deps.androidx.navigation.uiKtx)
  implementation(Deps.androidx.recyclerView)
  kapt(Deps.androidx.lifecycle.compiler)

  // Work Manager
  implementation(Deps.androidx.workManager) {
    exclude(group = "com.google.guava", module = "listenablefuture")
  }

  // Crashlytics
  implementation(Deps.crashlytics)

  // Dagger
  implementation(Deps.dagger.dagger)
  implementation(Deps.dagger.assistedInjectAnnotations)
  kapt(Deps.dagger.compiler)
  kapt(Deps.dagger.assistedInjectProcessor)

  // Firebase
  implementation(Deps.firebase.core)
  implementation(Deps.firebase.firestore)

  // Flipper - debugger
  debugImplementation(Deps.flipper.flipper)
  debugImplementation(Deps.flipper.network)
  debugImplementation(Deps.soloader)
  releaseImplementation(Deps.flipper.noop)

  // Glide
  implementation(Deps.glide.glide)
  implementation(Deps.glide.recyclerview)
  kapt(Deps.glide.compiler)

  // Kotlin
  implementation(Deps.kotlin.serialization.runtime)
  implementation(Deps.kotlin.serialization.runtimeCommon)
  implementation(Deps.kotlin.stdlib)

  // Kotlin Coroutines
  implementation(Deps.kotlin.coroutines.android)
  implementation(Deps.kotlin.coroutines.common)

  // Leak canary
  debugImplementation(Deps.leakCanary)

  // Material Design
  implementation(Deps.material)

  // MvRx
  implementation(Deps.mvrx)

  // OkHttp
  implementation(Deps.okhttp)

  // Google Play Core
  implementation(Deps.playCore)

  // PhotoView
  implementation(Deps.photoView)

  // Retrofit
  implementation(Deps.retrofit.retrofit)
  implementation(Deps.retrofit.kotlinSerialization)

  // SQLDelight
  implementation(Deps.sqldelight.androidDriver)
  implementation(Deps.sqldelight.coroutinesExtensions)

  // Unit Testing
  testImplementation(Deps.junit)

  // Android Testing
  androidTestImplementation(Deps.androidx.test.runner)
  androidTestImplementation(Deps.androidx.test.rules)
  androidTestImplementation(Deps.androidx.test.junit)
  androidTestImplementation(Deps.androidx.coreTesting)
  androidTestImplementation(Deps.androidx.espresso.core)
  androidTestImplementation(Deps.androidx.espresso.contrib)
  androidTestImplementation(Deps.androidx.espresso.intents)
  debugImplementation(Deps.androidx.fragment.testing) {
    exclude(group = "androidx.test", module = "core")
  }
}

apply {
  plugin("com.google.gms.google-services")
}

fun getVersionCodeOrDefault(): String = System.getenv("GITHUB_RUN_NUMBER") ?: "1"