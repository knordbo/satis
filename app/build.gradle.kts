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
}

apply {
    plugin("androidx.navigation.safeargs")
}

val keystoreProperties = Properties().apply {
    try {
        load(FileInputStream(file("../keystore.properties")))
    } catch (_ : Exception) {
        // ignore
    }
}

android {
    compileSdkVersion(BuildVersions.targetSdk)

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
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "UNSPLASH_CLIENT_ID", "\"${keystoreProperties["unsplashClientId"]}\"")
    }
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] ?: "dummy")
            storePassword = keystoreProperties["storePassword"] as String
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

    viewBinding.isEnabled = true
}

play {
    defaultToAppBundles = true
    serviceAccountCredentials = file("../service-account.json")
    track = "internal"
    resolutionStrategy = "auto"
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
    implementation(Deps.androidx.fragment)
    implementation(Deps.androidx.concurrent) {
        exclude(group = "com.google.guava", module = "listenablefuture")
    }
    implementation(Deps.androidx.lifecycle.extensions)
    implementation(Deps.androidx.lifecycle.runtime)
    implementation(Deps.androidx.navigation.fragmentKtx)
    implementation(Deps.androidx.navigation.uiKtx)
    implementation(Deps.androidx.recyclerView)
    implementation(Deps.androidx.room.runtime)
    implementation(Deps.androidx.room.ktx)
    kapt(Deps.androidx.lifecycle.compiler)
    kapt(Deps.androidx.room.compiler)

    // Crashlytics
    implementation(Deps.crashlytics)

    // Dagger
    implementation(Deps.dagger.dagger)
    implementation(Deps.dagger.android)
    implementation(Deps.dagger.assistedInjectAnnotations)
    kapt(Deps.dagger.compiler)
    kapt(Deps.dagger.androidProcessor)
    kapt(Deps.dagger.assistedInjectProcessor)

    // Firebase
    implementation(Deps.firebase.core)
    implementation(Deps.firebase.firestore)

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

    // Material Design
    implementation(Deps.material)

    // MvRx
    implementation(Deps.mvrx)

    // OkHttp
    implementation(Deps.okhttp.okhttp)

    // Google Play Core
    implementation(Deps.playCore)

    // PeekAndPop
    implementation(Deps.peekAndPop)

    // PhotoView
    implementation(Deps.photoView)

    // Retrofit
    implementation(Deps.retrofit.retrofit)
    implementation(Deps.retrofit.kotlinSerialization)

    // Tiny Dancer
    implementation(Deps.tinydancer)

    // Stetho
    debugImplementation(Deps.stetho)

    // Work Manager
    implementation(Deps.workManager.runtime)

    // Unit Testing
    testImplementation(Deps.junit)

    // Android Testing
    androidTestImplementation(Deps.androidx.test.runner)
    androidTestImplementation(Deps.androidx.test.rules)
    androidTestImplementation(Deps.androidx.coreTesting)
    androidTestImplementation(Deps.androidx.espresso.core)
    androidTestImplementation(Deps.androidx.espresso.contrib)
    androidTestImplementation(Deps.androidx.espresso.intents)
}

apply {
    plugin("com.google.gms.google-services")
}
