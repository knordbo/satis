private object Versions {
  const val androidGradlePlugin = "4.1.0-alpha04"

  object androidx {
    const val appCompat = "1.2.0-alpha03"
    const val cardView = "1.0.0"
    const val constraintLayout = "2.0.0-beta4"
    const val coreKtx = "1.3.0-alpha02"
    const val coreTesting = "2.1.0"
    const val espresso = "3.3.0-alpha05"
    const val fragment = "1.3.0-alpha02"
    const val concurrent = "1.1.0-alpha01"
    const val lifecycle = "2.3.0-alpha01"
    const val navigation = "2.3.0-alpha04"
    const val recyclerView = "1.2.0-alpha01"
    const val test = "1.3.0-alpha05"
    const val testExt = "1.1.2-alpha05"
    const val workManager = "2.4.0-alpha01"
  }

  const val crashlytics = "2.10.1"

  object dagger {
    const val assistedInject = "0.5.2"
    const val dagger = "2.27"
  }

  const val fabric = "1.29.0"
  const val firebaseCore = "17.2.3"
  const val firebaseFirestore = "21.4.1"
  const val flipper = "0.35.0"
  const val glide = "4.11.0"
  const val googlePlayPublisher = "2.7.2"
  const val googleServices = "4.0.0"
  const val gradleVersionsPlugin = "0.27.0"
  const val junit = "4.13"

  object kotlin {
    const val kotlin = "1.4-M1"
    const val coroutines = "1.3.5-1.4-M1"
    const val serialization = "0.20.0-1.4-M1"
  }

  const val leakCanary = "2.2"
  const val material = "1.2.0-alpha05"
  const val mvrx = "2.0.0-alpha2"
  const val okhttp = "4.5.0-RC1"
  const val photoView = "2.3.0"
  const val playCore = "1.7.1"

  object retrofit {
    const val kotlinSerialization = "0.5.0"
    const val retrofit = "2.8.1"
  }

  const val soloader = "0.8.2"
  const val sqldelight = "1.2.2"
}

object Deps {
  const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

  object androidx {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.androidx.appCompat}"
    const val cardView = "androidx.cardview:cardview:${Versions.androidx.cardView}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidx.constraintLayout}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.androidx.coreKtx}"
    const val coreTesting = "androidx.arch.core:core-testing:${Versions.androidx.coreTesting}"

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
      const val extensions = "androidx.lifecycle:lifecycle-extensions:${Versions.androidx.lifecycle}"
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

  const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"

  object dagger {
    const val assistedInjectAnnotations = "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.dagger.assistedInject}"
    const val assistedInjectProcessor = "com.squareup.inject:assisted-inject-processor-dagger2:${Versions.dagger.assistedInject}"
    const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger.dagger}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger.dagger}"
  }

  const val fabric = "io.fabric.tools:gradle:${Versions.fabric}"

  object firebase {
    const val core = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
    const val firestore = "com.google.firebase:firebase-firestore:${Versions.firebaseFirestore}"
  }

  object flipper {
    const val flipper = "com.facebook.flipper:flipper:${Versions.flipper}"
    const val network = "com.facebook.flipper:flipper-network-plugin:${Versions.flipper}"
    const val noop = "com.facebook.flipper:flipper-noop:${Versions.flipper}"
  }

  object glide {
    const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val recyclerview = "com.github.bumptech.glide:recyclerview-integration:${Versions.glide}"
  }

  const val googlePlayPublisher = "com.github.triplet.gradle:play-publisher:${Versions.googlePlayPublisher}"
  const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
  const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}"
  const val junit = "junit:junit:${Versions.junit}"

  object kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin.kotlin}"
    const val test = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin.kotlin}"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin.kotlin}"

    object coroutines {
      const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin.coroutines}"
      const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.kotlin.coroutines}"
    }

    object serialization {
      const val runtime = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlin.serialization}"
      const val runtimeCommon = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.kotlin.serialization}"
      const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin.kotlin}"
    }
  }

  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
  const val material = "com.google.android.material:material:${Versions.material}"
  const val mvrx = "com.airbnb.android:mvrx:${Versions.mvrx}"

  const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

  const val photoView = "com.github.chrisbanes:PhotoView:${Versions.photoView}"
  const val playCore = "com.google.android.play:core:${Versions.playCore}"

  object retrofit {
    const val kotlinSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit.kotlinSerialization}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit.retrofit}"
  }

  const val soloader = "com.facebook.soloader:soloader:${Versions.soloader}"

  object sqldelight {
    const val androidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
    const val coroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:${Versions.sqldelight}"
    const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:${Versions.sqldelight}"
  }
}
