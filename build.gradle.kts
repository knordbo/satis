buildscript {
  repositories {
    defaultRepositories()
  }
  dependencies {
    classpath("xml-apis:xml-apis:1.4.01") // https://github.com/cashapp/sqldelight/issues/1343
    classpath(Deps.androidGradlePlugin)
    classpath(Deps.kotlin.plugin)
    classpath(Deps.kotlin.serialization.serialization)
    classpath(Deps.googleServices)
    classpath(Deps.gradleVersionsPlugin)
    classpath(Deps.crashlyticsGradle)
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
