/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */

plugins {
  application
}

group = "com.tridium.example"
apply(from = "version.gradle.kts")

val distZip = tasks.named<Zip>("distZip")

val distElements by configurations.creating {
  isCanBeConsumed = true
  isCanBeResolved = false
  extendsFrom(configurations["implementation"])
  
  attributes {
    attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
    attribute(Usage.USAGE_ATTRIBUTE, objects.named("dist"))
  }

  outgoing {
    artifact(distZip) {
      builtBy(distZip)
    }
  }
}

java {
  withSourcesJar()
}

application {
  mainClass.set("com.tridium.example.auth.client.AuthClientExample")
}

distributions {
  main {
    contents {
      from(".") {
        into("src")
        include("build.gradle.kts")
        include("readme.txt")
        include("src/**")
        include("gradlew")
        include("gradlew.bat")
        include("gradle/**")
      }
      from("dist") {
        into("src")
        include("settings.gradle.kts")
        include("version.gradle.kts")
      }
    }
  }
}
