# **Niagara Development with Gradle**

**Garrett L. Ward @ Tridium**

## **Overview**

This document outlines the process and tools for Niagara development, with a focus on using Gradle.

## **Developer Environment Setup**

To set up your Niagara development environment, you will need:

* Niagara 4 Developer  
* Java 8 JDK  
* Node.js LTS (Long Term Support)  
* An IDE (Integrated Development Environment) or Text Editor

## **Java 8**

* For simply building, 'tools.jar \+ javac' is sufficient.  
* A full JDK is necessary for most IDE integrations.  
* Available from various vendors:  
  * AdoptOpenJDK / Adoptium ("Eclipse Temurin by Adoptium")  
  * Azul Zulu Community™  
  * Amazon Corretto

## **Gradle**

* Official website: [https://gradle.org](https://gradle.org)  
* Utilizes Niagara Gradle plugins.  
* Uses a Gradle wrapper.  
* A significant update occurred in Niagara 4.13, moving from Gradle 4.0.2 to Gradle 7.x.

## **Niagara Module**

* A standard JAR (Java Archive) file.  
* Contains a Niagara module manifest file: module.xml.  
* Requires a JAR signature.  
* **Module Parts**:  
  * 'module' serves as a logical grouping.  
  * 'runtime profile' roughly indicates the purpose of the module.  
  * 'module part' is the actual JAR file.

## **Example: Basic Module Build**

This section will cover:

* Creating the module with the New Module Wizard.  
* Adding a sample BComponent.  
* Adding a sample BTestNg test.  
* Running Slot-o-matic on it.  
* Building the module.  
* Testing the module.

### **New Module Wizard (Niagara Workbench)**

The New Module Wizard is accessible via Tools \-\> New Module in Niagara Workbench.

**Step** 1 of 3: Module **Details**

* **Create Module under Directory**: Specify the root directory for your Niagara projects (e.g., /C:/work/Niagara).  
* **Module Name**: e.g., myModule  
* **Preferred Symbol**: e.g., mm  
* **Version**: e.g., 1.0  
* **Description**: e.g., My awesome module  
* **Vendor**: e.g., glward  
* **Runtime Profiles**:  
  * RUNTIME: Module JARs with core runtime Java classes only (no UI).  
  * UX: Module JARs with lightweight HTML5+JavaScript+CSS UI only.  
  * WB: Module JARs with Workbench or Workbench Applet UI classes.  
  * SE: Module JARs with Java classes using the full Java 8 Standard Edition (SE) platform API.  
* **Options**:  
  * Create Lexicon  
  * Create Palette

**Step 2 of 3: Select Dependencies**

* Add necessary module dependencies. Examples:  
  * nre (Tridium, 4.13)  
  * baja (Tridium, 4.13)  
  * history-rt (Tridium, 4.13)

**Step 3 of 3: Add Packages**

* Define packages and their associated runtime profiles. Examples:  
  * com.glward.myModule (rt)  
  * com.glward.myModule.ui (wb)

### **Build Environment Files \- Root Directory (C:/work/Niagara/myModule)**

* .gradle/ (Directory)  
* .idea/ (Directory, if using IntelliJ IDEA)  
* build/ (Directory)  
* gradle/ (Directory)  
* myModule/ (Directory, containing the actual module code)  
* build.gradle.kts (File)  
* gradle.properties (File)  
* gradlew (File, Gradle wrapper script for Unix-like systems)  
* gradlew.bat (File, Gradle wrapper script for Windows)  
* settings.gradle.kts (File)

#### **settings.gradle.kts**

/\*  
\* Copyright 2022 glward, All Rights Reserved.  
 \*/

import com.tridium.gradle.plugins.settings.MultiProjectExtension

pluginManagement {  
    // lots of magic  
    val gradlePluginHome: String \= "magic" // Placeholder, actual path  
    val gradlePluginRepoUrl \= "file:///${gradlePluginHome.replace('\\\\', '/')}"  
    val gradlePluginVersion: String \= "7.3.5-SNAPSHOT"  
    val settingsPluginVersion: String \= "7.3.0"

    repositories {  
        maven(url \= gradlePluginRepoUrl)  
        gradlePluginPortal()  
    }

    plugins {  
        id("com.tridium.settings.multi-project") version settingsPluginVersion  
        id("com.tridium.settings.local-settings-convention") version settingsPluginVersion  
        id("com.tridium.niagara") version gradlePluginVersion  
        id("com.tridium.vendor") version gradlePluginVersion  
        id("com.tridium.niagara-module") version gradlePluginVersion  
        id("com.tridium.niagara-signing") version gradlePluginVersion  
        id("com.tridium.convention.niagara-home-repositories") version gradlePluginVersion  
    }  
}

plugins {  
    // Discover all subprojects in this build  
    id("com.tridium.settings.multi-project")  
}

configure\<MultiProjectExtension\> {  
    findProjects()  
}

#### **build.gradle.kts (Root Project)**

/\* Copyright 2022... \*/

plugins {  
    // Base Niagara plugin  
    id("com.tridium.niagara")  
    // The vendor plugin provides the vendor {} extension ...  
    id("com.tridium.vendor")  
    // The signing plugin configures signing  
    id("com.tridium.niagara-signing")  
    // The niagara\_home repositories convention plugin configures \!bin/ext and \!modules...  
    id("com.tridium.convention.niagara-home-repositories")  
}

vendor {  
    // defaultVendor sets the "vendor" attribute on module and dist files;  
    // it's what's shown in Niagara when viewing a module or dist.  
    defaultVendor("glward")

    // defaultModuleVersion sets the "vendorVersion attribute on a module.  
    defaultModuleVersion("1.0")  
}

subprojects {  
    repositories {  
        mavenCentral()  
    }  
}

#### **gradle.properties**

gradlePluginHome=C:\\\\work\\\\developer-4.13.0.0  
niagara\_home=C:\\\\niagara\\\\r4dev\\\\niagara\\\\niagara\_home  
niagara\_user\_home=C:\\\\niagara\\\\r4dev\\\\niagara\\\\niagara\_user\_home  
nodeHome=C:\\\\Program Files\\\\nodejs

\#AUTOMATICALLY GENERATED BY GRADLE DO NOT MODIFY  
org.gradle.java.installations.auto-detect=false  
\#AUTOMATICALLY GENERATED BY GRADLE DO NOT MODIFY  
org.gradle.java.installations.auto-download=false  
\#AUTOMATICALLY GENERATED BY GRADLE DO NOT MODIFY  
org.gradle.java.installations.paths=C:\\\\niagara\\\\r4dev\\\\niagara\\\\niagara\_home\\\\jre

### **Build Environment Files \- Module Directory (myModule/)**

* myModule-rt/ (Directory for runtime part)  
* myModule-wb/ (Directory for Workbench part)  
* niagara-module.xml (File)

#### **niagara-module.xml**

\<?xml version="1.0" encoding="UTF-8"?\>  
\<niagara-module  
    moduleName="myModule"  
    preferredSymbol="mm"  
    runtimeProfiles="rt,wb"  
/\>

### **Build Environment Files \- Module Part Directory (e.g., myModule/myModule-rt/)**

* build/ (Directory)  
* src/ (Directory for main source code)  
* srcTest/ (Directory for test source code)  
* module-include.xml (File)  
* module-permissions.xml (File)  
* module.lexicon (File)  
* module.palette (File)  
* moduleTest-include.xml (File)  
* myModule-rt.gradle.kts (File, specific build script for this module part)

#### **myModule-rt.gradle.kts (Module Part Build Script)**

/\* Copyright 2022 \*/

import com.tridium.gradle.plugins.module.util.ModulePart.RuntimeProfile.\*

plugins {  
    id("com.tridium.niagara-module")  
    id("com.tridium.niagara-signing")  
    id("com.tridium.bajadoc")  
    id("com.tridium.niagara-jacoco")  
    id("com.tridium.niagara-annotation-processors")  
    id("com.tridium.convention.niagara-home-repositories")  
}

description \= "My awesome module"

moduleManifest {  
    moduleName.set("myModule")  
    runtimeProfile.set(rt) // Example: rt for runtime  
}

// Dependencies for this specific module part  
dependencies {  
    // NRE dependencies  
    nre("Tridium:nre")

    // Niagara module dependencies  
    api("Tridium:history-rt")  
    api("Tridium:baja")

    // Test Niagara module dependencies  
    moduleTestImplementation("Tridium:test-wb")  
}

### **Example BComponent**

Located in myModule/myModule-rt/src/com/glward/myModule/BMyFoo.java

package com.glward.myModule;

import javax.baja.nre.annotations.NiagaraProperty;  
import javax.baja.nre.annotations.NiagaraType;  
import javax.baja.sys.\*;

@NiagaraType  
@NiagaraProperty(  
    name \= "foo",  
    type \= "baja:String",  
    defaultValue \= "BString.make(\\"bar\\")"  
)  
// @NiagaraAction // Example annotation  
// @NiagaraTopic // Example annotation  
public class BMyFoo extends BComponent {  
    // {{ AUTO GENERATED CODE BEGINS HERE }}  
    // {{ AUTO GENERATED CODE ENDS HERE }}  
}

### **Example BTestNg Test**

Located in myModule/myModule-rt/srcTest/test/com/glward/myModule/BMyFooTest.java

package test.com.glward.myModule;

import com.glward.myModule.BMyFoo;  
import org.testng.Assert;  
import org.testng.annotations.Test;  
import javax.baja.nre.annotations.NiagaraType;  
import javax.baja.test.BTestNg;  
import javax.baja.sys.Sys;  
import javax.baja.sys.Type;

@NiagaraType  
@Test(groups \= "ci")  
public class BMyFooTest extends BTestNg {  
    // {{ AUTO GENERATED CODE BEGINS HERE }}  
    // {{ AUTO GENERATED CODE ENDS HERE }}

    // test 'foo'  
    public void testFoo() {  
        // given  
        BMyFoo myFoo \= new BMyFoo();  
        // when  
        myFoo.setFoo("foo");  
        // then  
        Assert.assertEquals(myFoo.getFoo(), "foo");  
    }  
}

### **Slot-o-matic**

Slot-o-matic generates boilerplate code based on Niagara annotations.

* **Annotations**: @NiagaraType, @NiagaraProperty, etc.  
* **Generated Slot Code** (Example for @NiagaraProperty):  
  //region Property "foo"  
  public static final Property foo \=  
      newProperty(0, BString.make("bar"), null); // Flags (0) might vary  
  public String getFoo() { return getString(foo); }  
  public void setFoo (String v) { setString(foo, v, null); }  
  //endregion

* **Generated Slot Code** (Example for @NiagaraType):  
  //region Type  
  @Override  
  public Type getType() { return TYPE; }  
  public static final Type TYPE \= Sys.loadType(BMyFoo.class);  
  //endregion

  The generated code is typically enclosed in comments like:  
  //region /\*+  
  //@formatter:off  
  // BEGIN BAJA AUTO GENERATED CODE  
  // \+\*/  
  ...  
  /\*@ $test.com.glward.myModule.BMyFooTest (2979906276)1.0$ @\*/ // Unique ID  
  /\* Generated Mon Mar 21 09:23:42 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 \*/  
  ...  
  //@formatter:on  
  //endregion /\*+  
  // END BAJA AUTO GENERATED CODE  
  // \+\*/

### **Running Slot-o-matic**

From the root project directory (C:\\work\\Niagara\\myModule):

* Run on a specific module part:  
  gradlew :myModule-rt:slotomatic

* Run on a specific class:  
  gradlew :myModule-rt:slotomatic \--include com.glward.myModule.BMyFoo

* Run on a package:  
  gradlew :myModule-rt:slotomatic \--include com.glward.myModule.\*

* Run using file path pattern:  
  gradlew :myModule-rt:slotomatic \--include com/glward/myModule/\*\*.\*

  Output: BUILD SUCCESSFUL

### **Building**

From the root project directory:

gradlew moduleTestJar

Output: BUILD SUCCESSFUL

### **Testing**

From the root project directory:

gradlew :myModule-rt:niagaraTest

Initially, this might fail due to signature validation if the signing certificate is not trusted.  
Error message: Module myModule-rtTest failed signature validation: Could not validate cert chain

### **Module Signing**

1. Export Certificate from Keystore (if you have one):  
   This command exports a certificate from the default Tridium signing profile.  
   gradlew exportCertificate \--profile-path %USERPROFILE%\\.tridium\\security\\niagara.signing.xml \--alias Niagara4Modules \--pem-file Niagara4Modules.pem

   Output: BUILD SUCCESSFUL  
2. **Import Certificate into Niagara Workbench User Trust Store**:  
   * Open Niagara Workbench.  
   * Go to Tools \-\> Certificate Management.  
   * Select the User Trust Store tab.  
   * Click Import.  
   * Browse to and select the exported Niagara4Modules.pem file.  
   * Enter an alias (e.g., Niagara4Modules or niagara4modules).  
   * Review certificate details and click OK.  
     The certificate (e.g., glward@eris (Niagara4Modules)) will now appear in the User Trust Store.  
3. Re-run Tests:  
   After importing the certificate, clean and re-run the tests:  
   gradlew :myModule-rt:clean :myModule-rt:niagaraTest

   Output:  
   INFO \[nre\] Launching Niagara Runtime Environment  
   myModuleTest\_MyFooTest  
   Total tests run: 1, Failures: 0, Skips: 0  
   \===  
   Results of all run tests  
   Total tests run: 1, Failures: 0, Skips: 0  
   BUILD SUCCESSFUL

## **Updates (Gradle Changes in 4.13)**

### **Gradle Breaking Changes**

* **Configuration name changes**:  
  * compile \-\> api  
  * niagaraModuleTestCompile \-\> moduleTestImplementation  
* niagaraModule block \-\> moduleManifest block  
* certAlias property replaced with niagaraSigning extension.  
* environment.gradle \-\> gradle.properties  
* vendor.gradle \-\> vendor extension in build.gradle.kts

### **Updating Existing Builds**

* **Recommended approach: Cheat using the New Module Wizard**:  
  1. Create a shell project with the new Gradle scripts using the New Module Wizard.  
  2. Copy src, srcTest, and other supporting files for each module part from your old project to the new structure.  
  3. Fix dependencies in the .gradle.kts files.  
  4. Fix module resources (e.g., module-include.xml).  
  5. Fix signing configuration.  
* **Dependency Updates Example** (module-rt.gradle (old) vs module-rt.gradle.kts (new)):  
  Old (module-rt.gradle):  
  dependencies {  
      compile 'Tridium:history-wb:4.10'  
      compile 'Acme:fnord:1.0'  
      uberjar 'com.example:example:3.1415'  
      niagaraModuleTestCompile 'Tridium:test-se:4.10'  
  }

  New (module-rt.gradle.kts):  
  dependencies {  
      api("Tridium:history-wb") // Version can often be omitted if managed by platform  
      api("Acme:fnord")  
      uberjar("com.example:example:3.1415")  
      moduleTestImplementation("Tridium:test-se")  
  }

* **JAR Task Resource Inclusion Example**:  
  Old (module-rt.gradle):  
  jar {  
      from('src/rc') {  
          include 'foo.png'  
          include '\*\*/\*.px'  
          exclude 'test.jpg'  
      }  
  }

  New (module-rt.gradle.kts):  
  tasks.named\<Jar\>("jar") {  
      from("src/rc") {  
          include("foo.png")  
          include("\*\*/\*.px")  
          exclude("test.jpg")  
      }  
  }

* **Signing Configuration Example**:  
  Old (module-rt.gradle):  
  niagaraModule {  
      certAlias \= \["myCert"\]  
  }

  New (module-rt.gradle.kts in module part, or build.gradle.kts in root for global):  
  niagaraSigning {  
      aliases.set(listOf("myCert"))  
  }

* Build Signing Configuration (Root build.gradle.kts):  
  To specify a custom signing profile XML file:  
  signingServices {  
      signingProfileFactory {  
          allowDefaultProfile.set(false) // Disallow default to ensure custom is used  
      }  
  }

  niagaraSigning {  
      aliases.set(listOf("myCert")) // Or your specific alias  
      signingProfileFile.set(project.layout.projectDirectory.file("security/niagara.signing.xml"))  
  }

* **Resources for Updating**:  
  * Gradle upgrade guide  
  * docDeveloper Update Guide (Niagara documentation)  
  * Developer support  
  * Niagara Community

### **Gradle Signing Profile Tools (New in 4.13)**

No more manual keytool commands are needed for managing signing profiles.

1. Create Profile:  
   Creates a niagara.signing.xml properties file.  
   gradlew :createProfile \--profile-path security\\niagara.signing.xml

   The generated XML file contains default properties for code signing, such as storetype, validity, dname (Distinguished Name), keyalg, keysize.  
   Example niagara.signing.xml entry for dname (can be customized):  
   Default:  
   \<entry key="niagara.signing.dname"\>C=US, ST=Virginia, L=Richmond, O=Tridium, OU=For Development ...,CN=${user.name}@${host.name}${alias}\</entry\>

   Customized Example:  
   \<entry key="niagara.signing.dname"\>C=US, ST=Any State, L=Any City, O=Acme\\, Inc, OU=Engineering,CN=${alias}\</entry\>

   (Note: \\ escapes comma in Acme, Inc)  
2. Generate Certificate (and Private Key):  
   Generates a new private key and a Certificate Signing Request (CSR) if needed for a CA, or a self-signed certificate.  
   gradlew :generateCertificate \--profile-path security\\niagara.signing.xml \--alias NiagaraSigning \--csr-file NiagaraSigning.csr

   (The \--csr-file option generates a CSR; without it, it might generate a self-signed cert directly into the keystore defined by the profile).  
   Output: Generating new private key for NiagaraSigning  
3. Print Certificate Info:  
   Displays information about the certificate associated with an alias in the keystore.  
   gradlew printCertificateInfo \--profile-path security\\niagara.signing.xml \--alias NiagaraSigning

   Output includes subject, issuer, serial number, validity, fingerprints, etc.  
4. Import Certificate:  
   Imports a certificate (e.g., signed by a CA, or a root/intermediate CA cert) into the keystore.  
   gradlew :importCertificate \--profile-path security\\niagara.signing.xml \--alias NiagaraSigning \--pem-file NiagaraSigning.pem

   After importing a CA-signed certificate and its chain, printCertificateInfo will show the certificate chain.  
5. Update Build Signing Configuration (Root build.gradle.kts):  
   Ensure the niagaraSigning block in your root build.gradle.kts points to the correct alias and profile file.  
   niagaraSigning {  
       aliases.set(listOf("NiagaraSigning")) // Use the alias you generated/imported  
       signingProfileFile.set(project.layout.projectDirectory.file("security/niagara.signing.xml"))  
   }

## **All The Small Things (Other Improvements)**

* **Gradle parallel builds**: Improved build performance.  
* **JaCoCo support for test modules**: Code coverage reporting for tests.  
* **Brand dependency for modules**: Ability to specify brand dependencies.  
* **New Module/New Driver in Gradle**: Wizards integrated with Gradle.  
* **Slot-o-matic migration improvements**:  
  * AX-style comment block slot code is deprecated for removal.

## **Beta Access (for 4.13 Build Tools)**

* The new Gradle build tools were available in Niagara 4.13 as Early Access/Beta.  
* This applied to Gradle/Plugins only, not initially the New Module Wizard updates.  
* Designed to work against older Niagara versions like 4.10U2, 4.11U1, 4.12 for easier migration.  
* Contact: tridiumbetatest@tridium.com  
* Niagara Community Group for discussions and support.

## **Q & A**

(This section was for live questions during the presentation.)