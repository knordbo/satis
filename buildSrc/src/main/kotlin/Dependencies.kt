object Versions {
  const val androidGradlePlugin = "7.1.0"

  object androidx {
    const val appCompat = "1.6.0-alpha01"
    const val cardView = "1.0.0"
    const val constraintLayout = "2.1.3"
    const val coreKtx = "1.9.0-alpha01"
    const val coreTesting = "2.1.0"
    const val espresso = "3.5.0-alpha05"
    const val fragment = "1.5.0-alpha04"
    const val concurrent = "1.1.0"
    const val lifecycle = "2.5.0-alpha05"
    const val navigation = "2.5.0-alpha03"
    const val recyclerView = "1.3.0-alpha01"
    const val test = "1.4.1-alpha05"
    const val testExt = "1.1.4-alpha05"
    const val workManager = "2.8.0-alpha01"
  }

  const val coil = "2.0.0-rc02"
  const val compose = "1.2.0-alpha06"
  const val crashlyticsGradle = "2.4.1"

  object dagger {
    const val dagger = "2.41"
  }

  const val firebase = "29.2.1"
  const val flipper = "0.140.0"
  const val googlePlayPublisher = "3.7.0"
  const val googleServices = "4.3.10"
  const val gradleVersionsPlugin = "0.28.0"
  const val junit = "4.13.2"

  object kotlin {
    const val kotlin = "1.6.10"
    const val coroutines = "1.3.8"
    const val serialization = "1.3.2"
  }

  const val leakCanary = "2.8.1"
  const val material = "1.6.0-beta01"
  const val mvrx = "2.0.0-beta3"
  const val okhttp = "4.10.0-RC1"
  const val playCore = "1.10.3"

  object retrofit {
    const val kotlinSerialization = "0.8.0"
    const val retrofit = "2.9.0"
  }

  const val soloader = "0.10.3"
  const val sqldelight = "2.0.0-SNAPSHOT"
}

object Deps {
  const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

  object androidx {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidx.appCompat}"
    const val cardView = "androidx.cardview:cardview:${Versions.androidx.cardView}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidx.constraintLayout}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.androidx.coreKtx}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.androidx.coreTesting}"

    object compose {
      const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
      const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
      const val material = "androidx.compose.material:material:${Versions.compose}"
      const val materialIconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
      const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
      const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
      const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
      const val test = "androidx.compose.ui:ui-test:${Versions.compose}"
      const val uiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
      const val uiUtil = "androidx.compose.ui:ui-util:${Versions.compose}"
      const val viewBinding = "androidx.compose.ui:ui-viewbinding:${Versions.compose}"
    }

    object espresso {
      const val core = "androidx.test.espresso:espresso-core:${Versions.androidx.espresso}"
      const val contrib = "androidx.test.espresso:espresso-contrib:${Versions.androidx.espresso}"
      const val intents = "androidx.test.espresso:espresso-intents:${Versions.androidx.espresso}"
    }

    object fragment {
      const val fragment = "androidx.fragment:fragment:${Versions.androidx.fragment}"
      const val testing = "androidx.fragment:fragment-testing:${Versions.androidx.fragment}"
    }

    const val concurrent = "androidx.concurrent:concurrent-futures:${Versions.androidx.concurrent}"

    object lifecycle {
      const val runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.androidx.lifecycle}"
      const val process = "androidx.lifecycle:lifecycle-process:${Versions.androidx.lifecycle}"
      const val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.androidx.lifecycle}"
    }

    object navigation {
      const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.androidx.navigation}"
      const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.androidx.navigation}"
      const val uiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.androidx.navigation}"
    }

    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.androidx.recyclerView}"

    object test {
      const val runner = "androidx.test:runner:${Versions.androidx.test}"
      const val rules = "androidx.test:rules:${Versions.androidx.test}"
      const val junit = "androidx.test.ext:junit:${Versions.androidx.testExt}"
    }

    const val workManager = "androidx.work:work-runtime-ktx:${Versions.androidx.workManager}"
  }

  object coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
  }

  const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsGradle}"

  object dagger {
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger.dagger}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger.dagger}"
  }

  object firebase {
    const val bom = "com.google.firebase:firebase-bom:${Versions.firebase}"
    const val analytics = "com.google.firebase:firebase-analytics-ktx"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val firestore = "com.google.firebase:firebase-firestore-ktx"
    const val messaging = "com.google.firebase:firebase-messaging-ktx"
  }

  object flipper {
    const val flipper = "com.facebook.flipper:flipper:${Versions.flipper}"
    const val network = "com.facebook.flipper:flipper-network-plugin:${Versions.flipper}"
    const val noop = "com.facebook.flipper:flipper-noop:${Versions.flipper}"
  }

  const val googlePlayPublisher = "com.github.triplet.gradle:play-publisher:${Versions.googlePlayPublisher}"
  const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
  const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"
  const val junit = "junit:junit:${Versions.junit}"

  object kotlin {
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin.kotlin}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin.kotlin}"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin.kotlin}"

    object coroutines {
      const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin.coroutines}"
      const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.kotlin.coroutines}"
    }

    object serialization {
      const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlin.serialization}"
      const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin.kotlin}"
    }
  }

  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
  const val material = "com.google.android.material:material:${Versions.material}"
  const val mvrx = "com.airbnb.android:mvrx:${Versions.mvrx}"

  const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

  const val playCore = "com.google.android.play:core:${Versions.playCore}"

  object retrofit {
    const val kotlinSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit.kotlinSerialization}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit.retrofit}"
  }

  const val soloader = "com.facebook.soloader:soloader:${Versions.soloader}"

  object sqldelight {
    const val androidDriver = "app.cash.sqldelight:android-driver:${Versions.sqldelight}"
    const val coroutinesExtensions = "app.cash.sqldelight:coroutines-extensions:${Versions.sqldelight}"
    const val gradlePlugin = "app.cash.sqldelight:gradle-plugin:${Versions.sqldelight}"
    const val primitiveAdapters = "app.cash.sqldelight:primitive-adapters:${Versions.sqldelight}"
  }
}
