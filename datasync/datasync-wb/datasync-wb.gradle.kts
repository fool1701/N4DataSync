/*
 * Copyright 2025 MEA. All Rights Reserved.
 */

import com.tridium.gradle.plugins.module.util.ModulePart.RuntimeProfile.*

plugins {
  // The Niagara Module plugin configures the "moduleManifest" extension and the
  // "jar" and "moduleTestJar" tasks.
  id("com.tridium.niagara-module")

  // The signing plugin configures the correct signing of modules. It requires
  // that the plugin also be applied to the root project.
  id("com.tridium.niagara-signing")

  // The bajadoc plugin is disabled as it requires Niagara-specific dependencies
  // id("com.tridium.bajadoc")

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

description = "Workbench data synchronization tool"

moduleManifest {
  moduleName.set("datasync")
  runtimeProfile.set(wb)
}

// Use custom N4-DataSync signing profile configured in root build.gradle.kts
// This uses the DataSyncModules certificate from datasync.signing.xml

// See documentation at module://docDeveloper/doc/build.html#dependencies for the supported
// dependency types
dependencies {
  // NRE dependencies
  nre("Tridium:nre")

  // Niagara module dependencies
  api("Tridium:baja")
  api("Tridium:bajaui-ux")
  api("Tridium:bajaui-wb")
  api("Tridium:bajaux-rt")
  api("Tridium:bajaux-ux")
  api("Tridium:workbench-wb")
  api("Tridium:gx-rt")
  api("Tridium:gx-ux")
  api("Tridium:gx-wb")

  // Optional dependencies for data sync functionality
  api("Tridium:bacnet-rt")
  api("Tridium:bacnet-ux")
  api("Tridium:bacnet-wb")
  api("Tridium:control-rt")
  api("Tridium:control-ux")
  api("Tridium:control-wb")

  // External 3rd party library dependencies
  // Use 'uberjar' configuration for 3rd party libraries as per Niagara documentation
  uberjar("com.google.code.gson:gson:2.10.1")

  // Test Niagara module dependencies
  moduleTestImplementation("Tridium:test-wb")
}

// Configure Niagara test reporting
tasks.named("niagaraTest") {
  // Use standard Niagara test result locations
  // Results will be in build/reports/tests/niagara/ and build/test-results/niagara/
  doFirst {
    println("Running Niagara tests for datasync-wb module...")
    println("Test results will be available in:")
    println("  HTML Report: build/reports/tests/niagara/index.html")
    println("  XML Results: build/test-results/niagara/")
    println("Certificate validation: Using custom N4DataSyncDev certificate")
    println("Certificate file: ${rootProject.projectDir}/N4DataSyncDev.pem")
    println("To import certificate: Import N4DataSyncDev.pem into Niagara Workbench User Trust Store")
  }

  doLast {
    println("Niagara tests completed.")
    println("View detailed results: ${project.layout.buildDirectory.get()}/reports/tests/niagara/index.html")
  }
}

// Task to automatically copy the built module to Niagara modules directory
tasks.register<Copy>("deployToNiagara") {
  dependsOn("jar")
  from(layout.buildDirectory.file("libs/datasync-wb.jar"))

  val niagaraHome = project.findProperty("niagara_home") as String?
    ?: System.getenv("NIAGARA_HOME")
    ?: throw GradleException("niagara_home property or NIAGARA_HOME environment variable must be set")

  into("$niagaraHome/modules")

  doLast {
    println("Module deployed to Niagara: $niagaraHome/modules/datasync-wb.jar")
  }
}

// Task to build and deploy in one step
tasks.register("buildAndDeploy") {
  dependsOn("deployToNiagara")
  group = "niagara"
  description = "Build the module and deploy it to Niagara modules directory"
}

