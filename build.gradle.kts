buildscript {
    repositories {
        google()
        jcenter()
        maven(Repositories.sonatype)
        maven(Repositories.kotlinEap)
        maven(Repositories.kotlinX)
        maven(Repositories.fabric)
        maven(Repositories.jitpack)
    }
    dependencies {
        classpath(Deps.androidGradlePlugin)
        classpath(Deps.kotlin.plugin)
        classpath(Deps.kotlin.serialization.serialization)
        classpath(Deps.googleServices)
        classpath(Deps.gradleVersionsPlugin)
        classpath(Deps.fabric)
        classpath(Deps.androidx.navigation.safeArgs)
        classpath(Deps.googlePlayPublisher)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(Repositories.sonatype)
        maven(Repositories.kotlinEap)
        maven(Repositories.kotlinX)
        maven(Repositories.jitpack)
    }
}

tasks.register("clean").configure {
    delete("build")
}
