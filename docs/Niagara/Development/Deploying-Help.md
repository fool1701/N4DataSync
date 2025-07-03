# Deploying Help

## Overview

Help documentation is deployed as a set of files zipped up in a module jar. With the introduction of module parts in Niagara 4, help content should be contained in a part with a runtime profile of `doc`. See Modules for a description of modules and runtime profiles. Help content can be any MIME typed file. The primary content types are:

-   **HTML**: Niagara 4 provides support for HTML 5. This is the main format used to distribute help content.
-   **Bajadoc**: These are an XML-based file type used to distribute Java API reference documentation. Niagara provides a special view for this file type, which allows users to view the documentation. Bajadoc files are generated from Javadoc comments in source code, using configuration specified in the build script.

There are three steps in help content creation:

1.  The module developer supplies help content files and help structure files. Most of help content will be in form of HTML files, optionally with some graphics to enhance the presentation. As a general rule, you, as developer, should not concern yourself with anything but the content itself, providing HTML files with defined title and body that contains only content-related information. Developers should also include guide help for all their views. This view documentation is in the form of standard HTML files, located in the “doc” directory using a naming convention of “module-TypeName.html”. A table of contents file should be provided, to specify the logical order of the help files.
2.  (Optional) The developer supplies a lexicon key to point to module containing help. Guide help (Guide on Target) will look for the HTML file defined above in the doc directory of its module if the `help.guide.base` is not defined in its lexicon. You can supply this key to point to another module. As an example, most core modules point to `docUser`: `help.guide.base=module://docUser/doc`.
3.  Build the module. The module part containing the help content is built using the same tools as other module parts. See the build documentation for more information on this. During this step, the help content is indexed for the full text search purposes.

## Build script

A `doc` module part’s build script should have the ‘docmodule.gradle’ script applied to it, as follows:

```groovy
apply from: "${System.getenv('niagara_home')}/etc/gradle/docmodule.gradle"
```

This will cause several things to happen when the module is built:

-   HTML files in the `/doc` folder and referenced in the table of contents will be enhanced with a link to the standard help style sheet. This style sheet is not user configurable.
-   A copyright notice will be applied to the HTML, if specified.
-   Navigation links, based on the table of contents, will be inserted at the top and bottom of the document. There are three navigation links generated:
    -   **Index** - always points to `index.html`.
    -   **Prev** - points to the previous file in TOC, or is disabled if this is the first file in the TOC.
    -   **Next** - points to the next file in TOC, or disabled if this is the last file in the TOC.

In order for the HTML enhancement processing to insert the style sheet and navigation links in the correct positions, the `<head>`, `<body>` and `</body>` elements in the document HTML should start on new lines. It is also required that HTML documentation files in the module are encoded with the UTF-8 character set.

The copyright text applied to the HTML can be specified in the project’s build script as an extra property named `copyright` on the project. For example, the build script could include the following extra property:

```groovy
ext.copyright = 'Copyright 2000-2015 Tridium Inc. All rights reserved.'
```

This will apply the copyright text to a single module part. For multi-project builds, the copyright text can also be specified in the `vendor.gradle` file, again as an extra property. This will ensure that the same copyright text is applied to all `doc` module parts under the root Niagara project.

## Generating Bajadocs

When optionally generating Bajadoc API documentation, there will be one or more projects containing the Java code to be documented - these projects will have runtime profiles such as ‘rt’ or ‘wb’. There will also be a project with a ‘doc’ runtime profile. This will be the project configured to contain user documentation relating to the other projects, and also to generate the Bajadocs from the source code in the other projects:

```
<some folder name> - Top level directory
| - build.gradle
| - vendor.gradle
| - settings.gradle
 |- <project name-rt>\ - Folder containing project name-rt
 |  |- <project name-rt>.gradle - rt Project Gradle script file
 |- <project name-wb>\ - Folder containing project name-wb
 |  |- <project 2 name-wb>.gradle - wb Project Gradle script file
 |- <project name-doc>\ - Folder containing project name-doc
 |  |- <project name-doc>.gradle - doc Project Gradle script file
```

The application of the `docmodule.gradle` script will add a dynamic method named `bajadoc()` to the `doc` project. This method is called to generate Java API documentation for a project, with the resulting documentation being included into the ‘doc’ module part the script is applied to.

Several configuration values can be passed to the method:

-   **source**: This is the project containing the code to be documented. This is always required. A project can be referenced by calling the `project()` method, passing the name of the project, prefixed with a colon. Example: `source project(":myDriver-rt")`
-   **includePackage**: This can be used to pass the name of a single Java package to be documented. There are two further configuration properties required here (see below for an example):
    -   `name` = The name of the Java package.
    -   `bajaOnly` = A boolean value, which, if true, will only generate API documentation for the properties, actions and topics of Niagara types. Methods, functions and regular Java classes will not be documented.
-   **includePattern**: This can be used to include code files via an ANT style include pattern. The pattern should target ‘.java’ files relative to the project’s `/src` directory. Example: `includePattern "com/mycompany/mydriver/messages/*.java"`
-   **excludePattern**: This can be used to exclude files via an ANT style exclude pattern. The pattern should target ‘.java’ files relative to the project’s `/src` directory. This might be used to exclude certain classes that need to be in a documented package, but are not considered part of the public API and therefore can be ignored. Example: `excludePattern "com/mycompany/mydriver/**/*Util.java"`

The `bajadoc` declaration must contain at least one usage of `includePackage` or `includePattern` in order to have a set of source files to generate the documentation from. Note that `includePackage` specifies a single package name, while `includePattern` and `excludePattern` specify file paths, which could potentially match more than one Java package.

The ‘doc’ part’s build script should invoke the `bajadoc()` method for each project to be documented, referencing the other project as the source. The following example is an extract from a build script for a project with a ‘doc’ runtime profile, which calls `bajadoc()` to generate class documentation for a project named “myDriver-rt”. It references the project containing the source code, and passes configuration such that two packages are documented, and classes with names ending “Util” are excluded.

**myDriver-doc.gradle:**

```groovy
niagaraModule {
  preferredSymbol = "mydriv"
  moduleName = "myDriver"
  runtimeProfile = "doc"
}

bajadoc {
  source project(":myDriver-rt")
  includePackage {
    name = "com.mycompany.mydriver.core"
    bajaOnly = true
  }
  includePackage {
    name = "com.mycompany.mydriver.messages"
    bajaOnly = false
  }
  excludePattern "com/mycompany/mydriver/**/*Util.java"
}
```

## Generating JavaScript Documentation

In addition to the Bajadoc feature for documenting Java source code, documentation may also be generated for JavaScript code by specifying a `jsdocBuilds` configuration with the Niagara `rjs` plugin. This will generate HTML-based documentation from comments in the source code using the JSDoc tool. See the [JSDoc site](https://jsdoc.app/) for details on the syntax of documentation comments.

To enable this functionality, the plugin needs to be applied in the project’s build script:

```groovy
apply plugin: "niagara-rjs"
```

The build script can then specify one or more projects to generate HTML documentation from. For each project, there are several options that can be configured:

-   **rootDir**: This string specifies a path to the root folder of the JavaScript code for the project. This will include all `.js` files it finds, but will exclude built and minified files.
-   **source**: As an alternative to the ‘rootDir’ option, a file tree containing the JavaScript files to be documented can be specified. This option allows the set of documented source files to be refined by the use of ANT style include and exclude patterns.
-   **destinationDir**: Specifies the directory to receive the JSDoc output. If it isn’t already, this directory should be added to the includes for the project’s jar task.
-   **options**: This optional configuration value allows a list of string values to be passed to JSDoc command line. Tutorials can be specified via this option; see the command line options for JSDoc [here](https://jsdoc.app/about-commandline.html).

The following is an example of the usage of these options to document the JavaScript for two projects:

```groovy
niagaraRjs {
  jsdocBuilds = [
    ModuleA: [
      source: project(":moduleA-ux").fileTree(".") {
        include "src/rc/**/*.js"
        include "README.md"
        exclude "src/rc/**/*.buil*.js"
      },
      destinationDir: "$buildDir/jsdoc/moduleA-ux"
    ],
    ModuleB: [
      rootDir: project(":moduleB-ux").projectDir.path + "/src/rc"
    ]
  ]
}
```

## Help Side Bar
The help side bar has three tabs: Table of Contents, API and Search.
- **Table of Contents**, a.k.a. TOC, is used for presenting help content as a structured tree, in some logical order.
- **API** is used for presenting Bajadoc API documentation, organized by module part.
- **Search** allows full text search of the help content based on some search criteria.

### Table Of Contents (TOC)
As a general rule, you should provide a TOC with your help content. This should be an XML file, named toc.xml, located in the `src/doc/` directory. This file is required for a module to appear in the help table of contents. The DTD for this file is as follows:

```xml
<!ELEMENT toc (tocitem*)>
  <!ATTLIST toc version CDATA #FIXED "1.0">
  <!ATTLIST toc xml:lang CDATA #IMPLIED>
<!-- an item -->
<!ELEMENT tocitem (#PCDATA | tocitem)*>
  <!ATTLIST tocitem xml:lang CDATA #IMPLIED>
  <!ATTLIST tocitem text CDATA #IMPLIED>
  <!ATTLIST tocitem image CDATA #IMPLIED>
  <!ATTLIST tocitem target CDATA #IMPLIED>
```

It should have `<toc>` as its root element, and a list of files that you want to include in the final TOC, in the logical order. Although the TOC structure can be many levels deep, the most likely case will be a flat list of files. Each file is included via the `<tocitem>` element, and has two important attributes: `text` and `target`. The `text` attribute is used to specify the label text of the node as it appears in the TOC tree, while the `target` attribute specifies the relative URL of the help content file associated with this TOC item. It is required that at least one of the `target` or `text` attributes is present.

The `target` attribute:
- Must be a sub-path relative to the project’s `src/doc/` directory.
- Must not contain backup characters: `..`

You may use `tocitem` elements with only the `text` attribute defined as a way of grouping TOC nodes. If you want to define a TOC node associated with some help content, you must provide the `target`. If you provide the `target` only, the text will be generated as the name of the target file, without path and extension.

The following is an example TOC file:
```xml
<toc version="1.0">
   <tocitem text="Index" target="index.html" />
   <tocitem text="User Guide" target="userGuide.html" />
   <tocitem text="Developer Guide" target="devGuide.html" />
</toc>
```

### API
This is a tree of module parts that have Bajadoc API documentation available. Packages and types within a module part can be viewed by expanding the items in the tree.

### Search
This is a search view, used to search for occurrences of text. Enter the search term in the ‘Find:’ box, and click the ‘Search’ button. Matching results will be displayed in a list below the search box.
