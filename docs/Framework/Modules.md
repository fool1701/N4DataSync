# **Modules**

## **Overview**

The first step in understanding the Niagara architecture is to grasp the concept of modules. Modules are the unit of deployment and versioning in the Niagara architecture. A module is a set of related **module files** having the same module name.

A **module file**:

* Is a JAR file compliant with PKZIP compression;  
* Contains a XML manifest in meta-inf/module.xml;  
* Is independently versioned and deployable;  
* States its dependencies on other module files and their versions;  
* Has contents for a single **runtime profile**;

Additionally, a module file has a **module part name** that is used by other module files to declare dependencies against it. The module part name is usually a concatenation of the module name and runtime profile (e.g. control-rt), but in a few cases is set explicitly in the module file's manifest.

## **Runtime Profile**

A runtime profile is used in the following ways:

* Describes the contents of a module file;  
* Describes the capabilities of a Niagara Runtime Environment in a way that the bootstrap can filter out module files that can't be or aren't configured to be used (e.g. headless station, full Java UI with doc, station with applet support, etc.);  
* Allows installer tools to correctly decide which module files should be installed to a particular Niagara system;

Runtime profiles describe what a module file's contents are used for, and if the contents include Java classes, which Java Runtime profiles can load them. The table below uses the module files that comprise the control module as an example:

| Runtime Profile | Example Module Name | Built with JRE Version | Notes |
| :---- | :---- | :---- | :---- |
| rt | control-rt | Java 8 Compact 3 | Data model and communication: Fox, Box and Web Servlets |
| ux | control-ux | Java 8 Compact 3 | BajaUX, HTML5, CSS, JavaScript code providing web-based user interaction |
| wb | control-wb | Java 8 SE | Java code supporting old Workbench-based user interaction views, field editors, etc. These JARs inject AWT dependencies at runtime if AWT is supported on the platform. They are a special case... (see original for full note) |
| se | control-se | Java 8 SE | Anything that has a direct dependence upon Java SE code \- database technologies, AWT, Swing, etc. |
| doc | control-doc | N/A | Documentation. Includes no class files or other runnable content. |

## **Versions**

Versions are specified as a series of whole numbers separated by periods, for example "1.0.3042". Two versions can be compared resulting in equality, less than, or greater than. This comparison is made by comparing the version numbers from left to right. If two versions are equal, except one contains more numbers then it is considered greater than the shorter version. For example:

* 2.0 \> 1.0  
* 2.0 \> 1.8  
* 2.0.45 \> 2.0.43  
* 1.0.24.2 \> 1.0.24

Every module declares a "vendor" name and "vendorVersion". The vendor name is a case insensitive identifier for the company who developed the module and the vendorVersion identifies the vendor's specific version of that module.

Tridium's vendorVersions are formatted as "major.minor.build\[.patch\]":

* Major and minor declare a feature release such as 3.0.  
* The third number specifies a build number. A build number starts at zero for each feature release and increments each time all the software modules are built.  
* Additional numbers may be specified for code changes made off a branch of a specific build. These are usually patch builds for minor changes and bug fixes.

So the vendorVersion "3.0.22" represents a module of build 22 in Niagara release 3.0. The vendorVersion "3.0.45.2" is the second patch of build 45 in release 3.0.

## **Manifest**

All module JAR files must include a manifest file in "meta-inf/module.xml".

The root \<module\> element has required attributes:

* **name:** The globally unique name of the module. (1-25 ASCII characters, unique prefix recommended).  
* **vendor:** The company name of the module's owner.  
* **vendorVersion:** Vendor specific version.  
* **description:** A short summary of the module's purpose.  
* **preferredSymbol:** Used during XML serialization.  
* **runtimeProfile:** Describes the contents of the module file. (Only one module file per module name, runtime profile, and version combination).

Optionally, the root module element may have a modulePartName attribute. If omitted, it's name-runtimeProfile (e.g., "control-rt").

If a module has multiple files, the manifest for the file with the lowest runtimeProfile (rt lowest, doc highest) must list others via a \<moduleParts\> element, with \<modulePart\> sub-elements (each with mandatory runtimeProfile and name attributes specifying the module part name).

All modules need a \<dirs\> element with \<dir name="system-home\_relative\_path"/\> for each content directory.

Module files can have a \<dependencies\> element with zero or more \<dependency\> elements. Each \<dependency\> needs:

* name: Module part name of the dependency.  
* vendorVersion: Lowest required version of the dependency (higher versions assumed backward compatible).  
* vendor (optional, but vendorVersion requires vendor).

doc runtimeProfile modules cannot have dependencies, nor can other modules depend on them. Dependencies are also limited by runtime profile (see table in original PDF for specifics, e.g., rt can depend on rt, but not ux).

Modules can declare zero or more \<def name="key" value="something"/\> elements. These are collapsed into a global def database by the registry.

Modules with concrete Niagara BObjects include a \<types\> element with zero or more \<type name="TypeName" class="javax.baja.package.BClassName"/\> elements, mapping Baja type names to Java class names.

Modules can declare zero or one \<lexicons\> element (with optional brand attribute for filtering) containing zero or more \<lexicon module="bajaui" resource="fr/bajaui\_fr.lexicon" language="fr"/\> elements. This associates a resource file with a module for localization.