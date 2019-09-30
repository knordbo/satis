private object Versions {
    const val androidGradlePlugin = "3.6.0-alpha12"

    object androidx {
        const val appCompat = "1.1.0"
        const val cardView = "1.0.0"
        const val constraintLayout = "2.0.0-beta2" // TODO when bumping version delete the attrs.xml workaround
        const val coreKtx = "1.2.0-alpha04"
        const val coreTesting = "2.1.0"
        const val espresso = "3.3.0-alpha02"
        const val fragment = "1.2.0-alpha04"
        const val concurrent = "1.0.0-rc01"
        const val lifecycle = "2.2.0-alpha05"
        const val navigation = "2.2.0-alpha03"
        const val recyclerView = "1.1.0-beta04"
        const val room = "2.2.0-rc01"
        const val test = "1.3.0-alpha02"
    }

    const val crashlytics = "2.10.1"

    object dagger {
        const val assistedInject = "0.5.0"
        const val dagger = "2.24"
    }

    const val fabric = "1.29.0"
    const val firebaseCore = "17.2.0"
    const val firebaseFirestore = "21.1.1"
    const val glide = "4.10.0"
    const val googlePlayPublisher = "2.5.0-SNAPSHOT"
    const val googleServices = "4.0.0"
    const val gradleVersionsPlugin = "0.20.0"
    const val junit = "4.13-beta-3"

    object kotlin {
        const val kotlin = "1.3.50"
        const val coroutines = "1.3.2"
        const val serialization = "0.13.0"
    }

    const val material = "1.1.0-alpha10"
    const val mvrx = "1.1.0"
    const val okhttp = "4.2.0"
    const val peekAndPop = "1.1.0"
    const val photoView = "2.3.0"
    const val playCore = "1.6.3"

    object retrofit {
        const val kotlinSerialization = "0.4.0"
        const val retrofit = "2.6.2"
    }

    const val tinydancer = "0.1.2"
    const val stetho = "1.5.1"
    const val workManager = "2.3.0-alpha01"
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

        const val fragment = "androidx.fragment:fragment:${Versions.androidx.fragment}"
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

        object room {
            const val runtime = "androidx.room:room-runtime:${Versions.androidx.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.androidx.room}"
            const val ktx = "androidx.room:room-ktx:${Versions.androidx.room}"
            const val testing = "androidx.room:room-testing:${Versions.androidx.room}"
        }

        object test {
            const val runner = "androidx.test:runner:${Versions.androidx.test}"
            const val rules = "androidx.test:rules:${Versions.androidx.test}"
        }
    }

    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"

    object dagger {
        const val assistedInjectAnnotations = "com.squareup.inject:assisted-inject-annotations-dagger2:${Versions.dagger.assistedInject}"
        const val assistedInjectProcessor = "com.squareup.inject:assisted-inject-processor-dagger2:${Versions.dagger.assistedInject}"
        const val android = "com.google.dagger:dagger-android:${Versions.dagger.dagger}"
        const val androidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${Versions.dagger.dagger}"
        const val dagger = "com.google.dagger:dagger:${Versions.dagger.dagger}"
    }

    const val fabric = "io.fabric.tools:gradle:${Versions.fabric}"

    object firebase {
        const val core = "com.google.firebase:firebase-core:${Versions.firebaseCore}"
        const val firestore = "com.google.firebase:firebase-firestore:${Versions.firebaseFirestore}"
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

    const val material = "com.google.android.material:material:${Versions.material}"
    const val mvrx = "com.airbnb.android:mvrx:${Versions.mvrx}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

    const val peekAndPop = "com.github.shalskar:PeekAndPop:${Versions.peekAndPop}"
    const val photoView = "com.github.chrisbanes:PhotoView:${Versions.photoView}"
    const val playCore = "com.google.android.play:core:${Versions.playCore}"

    object retrofit {
        const val kotlinSerialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit.kotlinSerialization}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit.retrofit}"
    }

    const val tinydancer = "com.github.brianPlummer:tinydancer:${Versions.tinydancer}"
    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"

    const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
}
