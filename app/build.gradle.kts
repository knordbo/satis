import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.FileInputStream
import java.util.*

plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("kotlin-parcelize")
  id("kotlinx-serialization")
  id("com.github.ben-manes.versions")
  id("com.github.triplet.play")
  id("app.cash.sqldelight")
  id("com.google.firebase.crashlytics")
  id("dagger.hilt.android.plugin")
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
  compileSdk = BuildVersions.compileSdk

  ndkVersion = BuildVersions.ndk

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  kotlinOptions {
    jvmTarget = "11"
  }

  composeOptions {
    kotlinCompilerVersion = Versions.kotlin.kotlin
    kotlinCompilerExtensionVersion = Versions.compose
  }

  tasks.withType(KotlinCompile::class.java).configureEach {
    kotlinOptions {
      jvmTarget = "11"
      freeCompilerArgs =
        freeCompilerArgs + listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check")
    }
  }

  defaultConfig {
    applicationId = "com.satis.app"
    minSdk = BuildVersions.minSdk
    targetSdk = BuildVersions.targetSdk
    versionCode = versionCodeOrDefault
    versionName = versionCodeOrDefault.toString()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    buildConfigField("String",
      "UNSPLASH_CLIENT_ID",
      "\"${keystoreProperties["unsplashClientId"]}\"")
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
    pickFirst("META-INF/AL2.0")
    pickFirst("META-INF/LGPL2.1")
  }

  buildFeatures {
    viewBinding = true
    compose = true
  }
}

play {
  defaultToAppBundles.set(true)
  serviceAccountCredentials.set(file("../service-account.json"))
  track.set("internal")
}

dependencies {
  implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

  // AndroidX
  implementation(Deps.androidx.activity)
  implementation(Deps.androidx.appCompat)
  implementation(Deps.androidx.cardView)
  implementation(Deps.androidx.constraintLayout)
  implementation(Deps.androidx.coreKtx)
  implementation(Deps.androidx.fragment.fragment)
  implementation(Deps.androidx.concurrent) {
    exclude(group = "com.google.guava", module = "listenablefuture")
  }
  implementation(Deps.androidx.lifecycle.process)
  implementation(Deps.androidx.lifecycle.runtime)
  implementation(Deps.androidx.navigation.fragmentKtx)
  implementation(Deps.androidx.navigation.uiKtx)
  implementation(Deps.androidx.recyclerView)
  kapt(Deps.androidx.lifecycle.compiler)

  // Compose
  implementation(Deps.androidx.compose.layout)
  implementation(Deps.androidx.compose.material)
  implementation(Deps.androidx.compose.materialIconsExtended)
  implementation(Deps.androidx.compose.tooling)
  implementation(Deps.androidx.compose.uiUtil)
  implementation(Deps.androidx.compose.runtime)
  implementation(Deps.androidx.compose.runtimeLivedata)
  implementation(Deps.androidx.compose.viewBinding)
  androidTestImplementation(Deps.androidx.compose.uiTest)

  // Work Manager
  implementation(Deps.androidx.workManager) {
    exclude(group = "com.google.guava", module = "listenablefuture")
  }

  // Coil
  implementation(Deps.coil.coil)
  implementation(Deps.coil.coilCompose)

  // Dagger
  implementation(Deps.dagger.hilt)
  kapt(Deps.dagger.hiltCompiler)

  // Firebase
  implementation(platform(Deps.firebase.bom))
  implementation(Deps.firebase.analytics)
  implementation(Deps.firebase.crashlytics)
  implementation(Deps.firebase.firestore)
  implementation(Deps.firebase.messaging)

  // Flipper - debugger
  debugImplementation(Deps.flipper.flipper)
  debugImplementation(Deps.flipper.network)
  debugImplementation(Deps.soloader)
  releaseImplementation(Deps.flipper.noop)

  // Kotlin
  implementation(Deps.kotlin.serialization.json)
  implementation(Deps.kotlin.stdlib)
  implementation(Deps.kotlin.reflect)

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

  // Retrofit
  implementation(Deps.retrofit.retrofit)
  implementation(Deps.retrofit.kotlinSerialization)

  // SQLDelight
  implementation(Deps.sqldelight.androidDriver)
  implementation(Deps.sqldelight.coroutinesExtensions)
  implementation(Deps.sqldelight.primitiveAdapters)

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

kapt {
  correctErrorTypes = true
}

apply {
  plugin("com.google.gms.google-services")
}

val versionCodeOrDefault: Int
  get() {
    val githubRunNumber = System.getenv("GITHUB_RUN_NUMBER")
    return if (githubRunNumber != null) {
      githubRunNumber.toInt() + 1000
    } else {
      1
    }
  }
