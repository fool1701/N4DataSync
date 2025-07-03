/*
 * Copyright 2025 MEA. All Rights Reserved.
 */

import org.gradle.testing.jacoco.tasks.JacocoReport

plugins {
  // Base Niagara plugin
  id("com.tridium.niagara")

  // The vendor plugin provides the vendor {} extension to set the default group
  // for Maven publishing; the default vendor attribute for installable
  // manifests; and the default module and dist version for their respective
  // manifests
  id("com.tridium.vendor")

  // The signing plugin configures signing of all executables, modules, and
  // dists. It also registers a factory only on the root project to avoid
  // overhead from managing signing profiles on all subprojects
  id("com.tridium.niagara-signing")

  // The niagara_home repositories convention plugin configures !bin/ext and
  // !modules as flat-file Maven repositories to allow modules to compile against
  // Niagara
  id("com.tridium.convention.niagara-home-repositories")

  // Add jacoco plugin for code coverage
  jacoco
}


vendor {
  // defaultVendor sets the "vendor" attribute on module and dist files; it's
  // what's shown in Niagara when viewing a module or dist.
  defaultVendor("MEA")

  // defaultModuleVersion sets the "vendorVersion" attribute on all modules
  defaultModuleVersion("1.0")
}

// Use default Niagara signing profile from %USERPROFILE%\.tridium\security\
// This automatically uses the Niagara4Modules certificate that can be trusted
// Custom signing profiles require certificate import for all test environments

// Use default Niagara signing profile from %USERPROFILE%\.tridium\security\
// This automatically uses the Niagara4Modules certificate that can be trusted
// Custom signing profile approach requires certificate import for all test environments


////////////////////////////////////////////////////////////////
// Dependencies and configurations... configuration
////////////////////////////////////////////////////////////////

subprojects {
  repositories {
    mavenCentral()
  }
}

////////////////////////////////////////////////////////////////
// Development Quality Tasks
////////////////////////////////////////////////////////////////

tasks.register("checkTodos") {
  description = "Check for TODO/FIXME comments that need GitHub issue references"
  group = "verification"

  doLast {
    val todoPattern = Regex("(TODO|FIXME)(?!.*#\\d+)")
    var foundUnreferencedTodos = false

    fileTree("src").matching {
      include("**/*.java")
    }.forEach { file ->
      file.readLines().forEachIndexed { index, line ->
        if (todoPattern.containsMatchIn(line)) {
          println("${file.relativeTo(rootDir)}:${index + 1}: $line")
          foundUnreferencedTodos = true
        }
      }
    }

    if (foundUnreferencedTodos) {
      println("\n⚠️  Found TODO/FIXME comments without GitHub issue references")
      println("   Please reference issues like: // TODO: Fix validation (Issue #23)")
    } else {
      println("✅ All TODO/FIXME comments have issue references")
    }
  }
}

tasks.register("techDebtReport") {
  description = "Generate technical debt summary report"
  group = "reporting"

  doLast {
    println("📊 Technical Debt Report")
    println("========================")

    // Count TODOs/FIXMEs
    var todoCount = 0
    var fixmeCount = 0

    fileTree("src").matching {
      include("**/*.java")
    }.forEach { file ->
      file.readLines().forEach { line ->
        if (line.contains("TODO")) todoCount++
        if (line.contains("FIXME")) fixmeCount++
      }
    }

    println("📝 Code Comments:")
    println("   TODOs: $todoCount")
    println("   FIXMEs: $fixmeCount")
    println("\n📋 See docs/TECHNICAL_DEBT.md for detailed tracking")
  }
}

////////////////////////////////////////////////////////////////
// Code Quality and Security
////////////////////////////////////////////////////////////////

// Note: Code coverage and security scanning will be configured
// in individual module build files for better compatibility
