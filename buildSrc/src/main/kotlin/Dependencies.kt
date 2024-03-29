object Versions {
  const val androidGradlePlugin = "7.2.2"

  object androidx {
    const val activityCompose = "1.6.0-rc01"
    const val lifecycle = "2.6.0-alpha01"
    const val navigation = "2.5.1"
    const val workManager = "2.8.0-alpha03"
  }

  const val coil = "2.2.0"
  const val composeCompiler = "1.3.0"
  const val compose = "1.3.0-beta01"
  const val crashlyticsGradle = "2.4.1"

  object dagger {
    const val hilt = "2.43.2"
    const val hiltNavigation = "1.0.0"
    const val work = "1.0.0"
  }

  const val firebase = "30.3.2"
  const val flipper = "0.162.0"
  const val googlePlayPublisher = "3.7.0"
  const val googleServices = "4.3.10"
  const val gradleVersionsPlugin = "0.28.0"

  object kotlin {
    const val kotlin = "1.7.10"
    const val coroutines = "1.3.8"
    const val serialization = "1.4.0"
  }

  const val leakCanary = "2.9.1"
  const val material = "1.7.0-beta01"
  const val okhttp = "5.0.0-alpha.10"
  const val playCore = "1.10.3"

  object retrofit {
    const val kotlinSerialization = "0.8.0"
    const val retrofit = "2.9.0"
  }

  const val soloader = "0.10.4"
  const val sqldelight = "2.0.0-alpha03"
}

object Deps {
  const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

  object androidx {
    const val activityCompose = "androidx.activity:activity-compose:${Versions.androidx.activityCompose}"

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
    }

    object lifecycle {
      const val compiler = "androidx.lifecycle:lifecycle-compiler:${Versions.androidx.lifecycle}"
      const val process = "androidx.lifecycle:lifecycle-process:${Versions.androidx.lifecycle}"
      const val runtime = "androidx.lifecycle:lifecycle-runtime:${Versions.androidx.lifecycle}"
      const val viewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.androidx.lifecycle}"
    }

    object navigation {
      const val compose = "androidx.navigation:navigation-compose:${Versions.androidx.navigation}"
    }

    const val workManager = "androidx.work:work-runtime-ktx:${Versions.androidx.workManager}"
  }

  object coil {
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
  }

  const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlyticsGradle}"

  object dagger {
    const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.dagger.hilt}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger.hilt}"
    const val hiltWork = "androidx.hilt:hilt-work:${Versions.dagger.work}"
    const val hiltWorkCompiler = "androidx.hilt:hilt-compiler:${Versions.dagger.work}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:${Versions.dagger.hiltNavigation}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.dagger.hilt}"
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
