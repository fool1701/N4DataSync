/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */

import com.tridium.gradle.plugins.module.util.ModulePart.RuntimeProfile.*

plugins {
  // The Niagara Module plugin configures the "moduleManifest" extension and the
  // "jar" and "moduleTestJar" tasks.
  id("com.tridium.niagara-module")

  // The signing plugin configures the correct signing of modules. It requires
  // that the plugin also be applied to the root project.
  id("com.tridium.niagara-signing")

  // The bajadoc plugin configures the generation of Bajadoc for a module.
  id("com.tridium.bajadoc")

  // Configures JaCoCo for the "niagaraTest" task of this module.
  id("com.tridium.niagara-jacoco")

  // The Annotation processors plugin adds default dependencies on "Tridium:nre"
  // for the "annotationProcessor" and "moduleTestAnnotationProcessor"
  // configurations by creating a single "niagaraAnnotationProcessor"
  // configuration they extend from. This value can be overridden by explicitly
  // declaring a dependency for the "niagaraAnnotationProcessor" configuration.
  id("com.tridium.niagara-annotation-processors")

  // The niagara_home repositories convention plugin configures !bin/ext and
  // !modules as flat-file Maven repositories so that projects in this build can
  // depend on already-installed Niagara modules.
  id("com.tridium.convention.niagara-home-repositories")
}

description = "Example Niagara Module"

moduleManifest {
  moduleName.set("componentLinks")
  // The runtime profile indicates the minimum Java runtime support required for this module jar
  runtimeProfile.set(rt)
}

dependencies {
  // NRE dependencies
  nre("Tridium:nre")

  // Niagara module dependencies
  api("Tridium:baja")
  api("Tridium:kitControl-rt")

  // Test Niagara module dependencies
  moduleTestImplementation("Tridium:test-wb")
  moduleTestImplementation("Tridium:bajaui-wb")
  moduleTestImplementation("Tridium:control-rt")
}


// Include additional files in module jar with the following configuration.
//tasks.named<Jar>("jar") {
//  from("src") {
//    include("com/tridium/history/hx/*.js")
//    include("com/tridium/history/ui/icons/*.png")
//  }
//}

// Include files in the test jar with the following configuration.
//tasks.named<Jar>("moduleTestJar") {
//  from("srcTest") {
//    include("test/bogs/*.bog")
//  }
//}
