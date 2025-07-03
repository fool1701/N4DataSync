### module.lexicon

The `module.lexicon` file is an optional file that is placed directly under the module’s root directory. If included it is automatically inserted into the module jar file. The lexicon file defines the name/value pairs accessed via the Lexicon API.

### moduleTest-include.xml

Put any Niagara def, type, and lexicon elements used in your test classes in this file.

## Single Module Project

### Project Setup

A stand-alone module not part of a multi-project build needs to have only the `build.gradle` and `gradlew.bat` files in the top level folder to support building the module with Gradle. The `module-include.xml` is still required, and the `module.lexicon` and `module.palette` files can be included if needed. A `moduleTest-include.xml` is needed for any test classes. Here is the file structure for a stand-alone project.

```
<module name> - Top level source directory
|- build.gradle - Gradle script file
|- gradlew.bat - Gradle wrapper file
|- module.lexicon - Default lexicon file
|- module.palette - Defines Palette information for the module
|- module-include.xml - Declares Types, Defs, etc. for the module
|- moduleTest-include.xml - Declares Types, Defs, etc. for the test module
|- src\ - Folder containing module packages, source files, and resource files
|- srcTest\ - Folder containing test packages, source files, and resource files
```

### Project Build

Gradle commands will be run from a Windows command prompt. For a single module, navigate to the folder containing the module source and configurations. The Gradle tasks for separate phases of the build sequence are below.

-   `gradlew tasks` - List the Gradle tasks available for execution
-   `gradlew jar` - Compile module source code, assemble the module jar, and copy it to the installation location
-   `gradlew slotomatic` - Run slot-o-matic on the module source code creating boiler plate slot code
-   `gradlew javadocJar` - Generate javadoc files and assemble them into a jar file
-   `gradlew moduleTestJar` - Compile, jar, and install test module code
-   `gradlew clean` - Clean compiled artifacts from the module folder

## Multiple Module Project

### Project Setup

A multi-project set of module jar files will have a `build.gradle` and `gradlew.bat` in the top-level folder. There will also be a `settings.gradle` and `vendor.gradle` files containing Gradle elements that will be applied to all module builds. Each module jar will require a `<moduleName>.gradle` file containing Gradle configurations specific to that module. The `module-include.xml` is required for each module jar, and the `module.lexicon` and `module.palette` are included as needed. A `moduleTest-include.xml` is needed for any module jar containing test classes.

Niagara 4 supports multiple runtime profiles for a single Niagara module. To take advantage of this runtime configuration, there will be a separate module jar file for each profile. By convention, the gradle build file for a module jar file will be `<moduleName>-<profile>.gradle` and the runtime profile will be declared in that file as part of the Gradle build configuration. Profiles include `rt`, `ux`, `wb`, `se`, and `doc`. Here is the file structure for a project containing multiple modules.

```
<some folder name> - Top level directory
|- build.gradle - Main Gradle script file
|- gradlew.bat - Gradle wrapper file
|- vendor.gradle - Define the group (vendorName) and version here - they will be used in all modules
|- settings.gradle - Gradle script containing the names of all modules or folders containing modules
|- <module 1 name>\ - Folder containing Module 1
| |- <module 1 name>.gradle - Module 1 Gradle script file
| |- module.lexicon - Default lexicon file for module 1
| |- module.palette - Defines Palette information for module 1
| |- module-include.xml - Declares Types, Defs, etc. for module 1
| |- moduleTest-include.xml - Declares Types, Defs, etc. for test module 1
| |- src\ - Folder containing module 1 packages, source files, and resource files
| |- srcTest\ - Folder containing test 1 packages, source files, and resource files
|- <module 2 name>\ - Folder containing Module 2
| |- <module 2 name>.gradle - Module 2 Gradle script file
| |- module-include.xml - Declares Types, Defs, etc. for module 2
...
```

### Project Build

Gradle commands will be run from a Windows command prompt. For a multi-module project, navigate to the main project folder. The Gradle tasks for separate phases of the build sequence are below.

-   `gradlew tasks` - List the Gradle tasks available for execution
-   `gradlew jar` - Compile source code, assemble jars, and installation for all modules
-   `gradlew slotomatic` - Run slot-o-matic on the all source code.
-   `gradlew javadocJar` - Generate javadoc files and assemble them into jar files for all modules
-   `gradlew moduleTestJar` - Compile, jar, and install test code for all modules
-   `gradlew clean` - Clean compiled artifacts for all modules
-   `gradlew :<moduleName>:jar` - Compile, jar, and install a single
-   `gradlew :<moduleName>:slotomatic` - Run slot-o-matic on the source code.

This same module-specific syntax can be also used for the rest of the Gradle tasks.

## Example Gradle Scripts

### build.gradle (for the project)

```groovy
ext {
  niagaraHome = System.getenv("niagara_home")
  if (niagaraHome == null) {
    logger.error("niagara_home environment variable not set")
  }
}
//to enable idea/intellij or eclipse support, un-comment the lines below
// apply from: "${System.getenv("niagara_home")}/etc/gradle/idea.gradle"
// apply from: "${System.getenv("niagara_home")}/etc/gradle/eclipse.gradle"
gradle.beforeProject { p ->
  configure(p) {
    def vendorSettings = file("${rootDir}/vendor.gradle")
    if (vendorSettings.exists()) {
      apply from: vendorSettings
    }
    apply from: "${System.getenv("niagara_home")}/etc/gradle/niagara.gradle"
  }
}
tasks.addRule("""
Pattern: [jar[Test]|clean|<any gradle task>]/[path]: Run a Gradle task against
a set of modules rooted at path.
""") { String taskName ->
  def matcher = taskName =~ /(.*?)(Test)?\/(.*)/
  if (matcher) {
    def command = matcher.group(1)
    def includeTestModules = matcher.group(2) == "Test"
    def path = file("${projectDir}/${matcher.group(3)}").toPath()
    assert path.toFile().exists()
    def targetProjects = subprojects.findAll {
      it.projectDir.toPath().startsWith(path) 
    }
    // default is build command and build is an alias for Gradle"s jar task
    if (command.isEmpty() || command == "build") { command = "jar" }
    // Create task for subproject
    task(taskName, dependsOn: targetProjects.tasks[command])
    if (includeTestModules && command == "jar") {
      tasks[taskName].dependsOn targetProjects.moduleTestJar
    }
  }
}
```

### vendor.gradle

```groovy
// Vendor name applied to all modules
group = "Tridium"
// Major, minor, and build version
def moduleVersion = "5.0.1"
// Patch version can be declared
// For example, to patch envCtrlDriver module as 5.0.1.1
// moduleVersionPatch.'envCtrlDriver' = ".1"
def moduleVersionPatch = [:]
// Final version property applied to all modules
version = "${moduleVersion}${moduleVersionPatch.get(project.name, '')}"
```

### settings.gradle

```groovy
import groovy.io.FileVisitResult
import groovy.io.FileType

def discoveredProjects = [:] as Map
ext {
  // Configure your sub-project folders here
  // This will include ALL sub-folders as sub-projects.
  niagaraRoots = ["."]
  // To explicitly define sub-project folders, name them in the array like this

  // niagaraRoots = ["componentLinks", "envCtrlDriver"]
  // Configure any directories to exclude from search for nested sub-projects
  excludeDirs = [".hg", "build", "out", "src", "srcTest"]
}
// niagaraRoots configuration - do not modify
niagaraRoots.collect({ file(it) }).findAll({ it.exists() }).each { File
projectRoot ->
  projectRoot.traverse(
    type: FileType.DIRECTORIES,
    preRoot: true,
    preDir: { File projectDir ->
      def projectName = projectDir.name
      if (excludeDirs.contains(projectName)) {
        return FileVisitResult.SKIP_SUBTREE
      }
      File buildScript = new File(projectDir, "${projectName}.gradle")
      if (buildScript.exists()) {
        discoveredProjects[projectName] = projectDir
        if (projectDir != projectRoot) {
          include projectName
          return FileVisitResult.SKIP_SUBTREE
        }
      }
    }
  )
}
// Set up the project tree - no need to modify
rootProject.name = "niagara"
rootProject.children.each { project ->
  project.projectDir = discoveredProjects[project.name]
  project.buildFileName = "${project.name}.gradle"
  assert project.projectDir.isDirectory()
  assert project.buildFile.isFile()
}
```

## External Library Dependencies

### Project Setup

Modules may depend on 3rd party libraries that implement some desired functionality. These dependencies are configured much like Niagara module dependencies, but are contained in a configuration called `uberjar`. For example, if a module has a direct dependency on the Apache Velocity library and the baja module, the dependency declaration would look like:

```groovy
// Declare compile and test dependencies
dependencies {
  compile "Tridium:baja:4.0.0"
  uberjar "org.apache.velocity:velocity:1.7"
}
```

Libraries compiled with the `uberjar` configuration will cause the classes of the dependency to be included in the resulting module jar file. This makes it straightforward to distribute modules with external dependencies.

Note that the string used to identify a particular library follows a specific convention of `group:name:version`. So in the above example, the group is `org.apache.velocity`, the name is `velocity`, and the version is `1.7`. This information relates to the Maven information for that library, and it will be verified and downloaded from a central Maven repository. See the [Gradle documentation on dependency management](https://docs.gradle.org/current/userguide/dependency_management.html) for more information on external library dependency naming and the central Maven repository.
