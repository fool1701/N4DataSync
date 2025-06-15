/*
 * Copyright (c) 2020 typeextensiondemo. All Rights Reserved.
 */

import com.tridium.gradle.plugins.grunt.task.GruntBuildTask
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

  // JavaScript-specific extensions to bridge Gradle builds with with Grunt builds
  id("com.tridium.niagara-grunt")
}

description = ""

moduleManifest {
  moduleName.set("typeExtensionDemo")
  runtimeProfile.set(ux)
}

dependencies {
  // NRE dependencies
  nre("Tridium:nre")

    // Niagara module dependencies
  api("Tridium:baja")
  api("Tridium:bajaScript-ux")
  api("Tridium:box-rt")
  api("Tridium:js-ux")
  api("Tridium:nre")
  api("Tridium:web-rt")
  
  // Test Niagara module dependencies
  moduleTestImplementation("Tridium:test-wb")
}

tasks.named<Jar>("jar") {
  from("src") {
    include("jsdoc/")
    include("rc/")
  }
}

tasks.named<Jar>("moduleTestJar") {
  from("srcTest") {
    include("rc/")
  }
}

tasks.named<GruntBuildTask>("gruntBuild") {
  tasks("babel:dist", "copy:dist", "requirejs")
}

