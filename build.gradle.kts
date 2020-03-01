buildscript {
  repositories {
    defaultRepositories()
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
    classpath(Deps.sqldelight.gradlePlugin)
  }
}

allprojects {
  repositories {
    defaultRepositories()
  }
}

tasks.register("clean").configure {
  delete("build")
}
