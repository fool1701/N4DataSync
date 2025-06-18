/*
 * Copyright 2025 mea. All Rights Reserved.
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

description = "Workbench data synchronization tool"

moduleManifest {
  moduleName.set("datasync")
  runtimeProfile.set(wb)
}

// Configure signing to use our custom certificate
niagaraSigning {
  signingProfileFile.set(rootProject.layout.projectDirectory.file("security/niagara.signing.xml"))
  aliases.set(listOf("myN4DataSyncCert"))
}

// See documentation at module://docDeveloper/doc/build.html#dependencies for the supported
// dependency types
dependencies {
  // NRE dependencies
  nre("Tridium:nre")

  // Niagara module dependencies (4.11+ compatible)
  api("Tridium:baja") {
    version { require("[4.11,)") }
  }
  api("Tridium:bajaui-ux") {
    version { require("[4.11,)") }
  }
  api("Tridium:bajaui-wb") {
    version { require("[4.11,)") }
  }
  api("Tridium:bajaux-rt") {
    version { require("[4.11,)") }
  }
  api("Tridium:bajaux-ux") {
    version { require("[4.11,)") }
  }
  api("Tridium:workbench-wb") {
    version { require("[4.11,)") }
  }
  api("Tridium:gx-rt") {
    version { require("[4.11,)") }
  }
  api("Tridium:gx-ux") {
    version { require("[4.11,)") }
  }
  api("Tridium:gx-wb") {
    version { require("[4.11,)") }
  }

  // Optional dependencies for data sync functionality
  api("Tridium:bacnet-rt")
  api("Tridium:bacnet-ux")
  api("Tridium:bacnet-wb")
  api("Tridium:control-rt")
  api("Tridium:control-ux")
  api("Tridium:control-wb")

  // Test Niagara module dependencies
  moduleTestImplementation("Tridium:test-wb")
}

