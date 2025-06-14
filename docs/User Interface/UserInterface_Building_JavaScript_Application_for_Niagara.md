# Building JavaScript Applications for Niagara

============================================================
PAGE 79
============================================================

Building JavaScript Applications for Niagara
Contents
Introduction
Frameworks and Libraries
Setting Up Your Environment
Node.js
npm
Grunt
Build and Development Tools
Open Source Niagara Development Utilities
grunt-init-niagara
grunt-niagara
niagara-station
niagara-test-server
Building and Compacting your JavaScript
Developing in real time with moduledev mode
First Steps
Creating a new module
package.json
Gruntfile.js
Watching
A note on Continuous Integration
Building your JavaScript into a Niagara module
Compacting using the Gradle RequireJS Plugin
Implementing BIJavaScript
What JavaScript file defines my editor?
What Types can I edit with my editor?
Switching your Station into Web Development mode
Moving Forward
Introduction
In Niagara 4, the user interface is moving in a new direction with a heavy focus on HTML5 applications and open web
technologies. Tridium provides a number of frameworks and utilities, intended to give developers the power to create
their own web apps, from tiny field editors to powerful, full-featured full-screen views.
Developing in HTML5 and JavaScript is a different process from the familiar Java-based process for developing in the
Niagara Framework. However, using the tools and techniques described in this document, you can create a JavaScript
codebase that is robust, error-checked, and well-tested.
Frameworks and Libraries
Before beginning HTML5 development in the Niagara Framework, you’ll want to take a few moments to familiarize
yourself with a few of the different libraries and frameworks available to you. These frameworks include
BajaScript,
bajaux, and other open web technologies.
Niagara Developer Guide
8/26/2015
77



============================================================
PAGE 80
============================================================

Setting Up Your Environment
In order to most effectively develop Niagara web applications, you’ll need to install a few external utilities for building,
code analysis, and testing.
The toolchain described in this document is the one used by Tridium to develop all of its HTML5 web applications for
Niagara 4. The tools described are free and open-source. The toolchain is separate from the Niagara Framework itself, so
if you find that it does not suit your purposes, you are free to use a different set of development tools, or create your own.
To use the standard Tridium toolchain, you’ll need to install the following utilities:
Node.js
Node.js allows you to run JavaScript applications from the command line, without using a web browser. Tridium’s open-
source tools for developing Niagara web applications are all based upon Node.js.
npm
npm is a utility used to download and install Node.js modules. It is automatically installed alongside Node.js.
Grunt
Grunt is a task runner for JavaScript. You will use it to find errors, run tests, and analyze your code. Install it like this:
npm install -g grunt-cli
After that, you’ll be able to run Grunt tasks for a web module by cd-ing into its directory and typing grunt.
Git
Git is used to clone entire repositories of source code to your machine. This is mostly used internally by npm, but you
might also need to clone some repositories manually.
Build and Development Tools
Open Source Niagara Development Utilities
In order to properly build and test JavaScript modules, there is some setup involved. npm libraries need to be downloaded
and installed, configuration files need to be written, and code needs to be moved into the proper directories.
We have created a number of utilities to make this process as easy as possible. These are open-source Node.js modules,
created and released by Tridium. They will facilitate a fully automated, test-driven approach to creating functional,
reliable, well-tested Niagara web modules.
grunt-init-niagara
grunt-init is a tool that streamlines the creation of a new JavaScript project. The process is similar to Wizards you
might use in Workbench: it asks you a number of setup questions and generates a brand-new web module for you to
Niagara Developer Guide
8/26/2015
78



============================================================
PAGE 81
============================================================

begin development.
grunt-init-niagara is Tridium’s own grunt-init template which is Niagara-specific; it generates you a brand-
new Niagara web module that can be built and run using standard Niagara tools. The module will be pre-configured with
source code and test directories, configuration files, and everything necessary to start developing.
All of the modules described in the rest of this section will be automatically included in your new module with no further
configuration necessary. Simply type npm install to download everything, then begin development.
While it’s entirely possible to create a new web module without using grunt-init-niagara, we highly recommend
that you use it for your first web module to learn about the recommended project structure and configuration.
grunt-niagara
This is less of a development tool and more of a collection of smart defaults. Including grunt-niagara in your project
will include JSHint, Karma, Plato, and other development utilities, with configuration files pre-configured with settings
Tridium has found to be optimal when developing web modules. The settings can be added to or changed in your own
configuration files.
When creating a new module using grunt-init-niagara, you’ll automatically have grunt-niagara installed and
configured.
niagara-station
This is a library that allows you to start and stop Niagara 4 stations using Node.js. It is most commonly used during the
test process to start a station with BajaScript installed, so that the web module’s unit tests can use a live BajaScript
session to verify real-world behavior.
It also has some basic port configuration abilities. For example, if you are running tests on a machine that has port 80
already in use,
niagara-station can reconfigure the station to run HTTP on port 8080 instead.
When using grunt-init-niagara, your web module will automatically receive a test station and a default niagara-
station configuration.
niagara-test-server
This module simply includes some utilities for logging into a running test station using JavaScript and triggering the
browser to run tests. Again, it’s automatically included by grunt-init-niagara.
JSHint
Since JavaScript is an interpreted, not compiled, language, there is no compilation step to catch syntax errors before the
code is run in the browser. This makes a static analysis tool like JSHint a necessity. It will find syntax errors (and some
semantic errors as well) in your JavaScript code before it is run.
JSHint runs as a Grunt task as part of the standard development process.
Jasmine
Jasmine is Tridium’s unit testing framework of choice. All of our JavaScript libraries, like BajaScript and bajaux, are fully
unit tested using Jasmine.
The Jasmine framework will be automatically configured when using grunt-niagara. It is possible to use other testing
frameworks, but you will need to configure these manually.
Niagara Developer Guide
8/26/2015
79



============================================================
PAGE 82
============================================================

Karma
Karma actually runs your tests and generates the results. By default, it will start up an instance of PhantomJS - a headless
browser - and run your full test suite. You can also connect any other browser to it, such as Chrome, Firefox, IE, or an
iPhone, and run your tests in all of those browsers as well.
Building and Compacting your JavaScript
When building your web module, the build process can use the r.js optimizer to compact all of your JavaScript code into a
single file. Since network calls are the biggest factor affecting the loading time of your application, this is a crucial step in
ensuring your app loads as quickly as possible.
This plugin also generates documentation for your app using JSDoc.
For more information, see the
Deploying Help section.
Developing in real time with moduledev mode
When developing a web module, it’s a severe slowdown to have to rebuild your module and restart your station every time
you want to test out a new change in your browser. It’s much faster to simply change a file and refresh the browser to
pick up the latest changes.
To facilitate this, Niagara 4 includes a
moduledev mode that allows files from certain modules to be read directly from
your hard drive instead of from a module JAR file. In order to enable this mode, you’ll need to perform the following
steps.
First, you’ll need to ensure this feature is enabled in your Tridium license. You’ll need the developer feature included in
your license with the moduleDev property set to true. If your license does not include this feature, please contact your
Tridium sales representative.
Next, add an entry to system.properties: niagara.module.dev=true. This is for the browser only; for
Workbench, there is another setting: niagara.module.dev.wb=true.
Not every file type can be resolved in this way: by default, only JS, CSS, HTML, image files, and some other web-related
file types are supported. If you wish to resolve some different file types, you can configure this list using this system
property:
niagara.module.dev.supportedExtensions=js,css,vm,htm(...)
Next, create a file niagara_home/etc/moduledev.properties. Each entry in this file maps a module name to the
directory on your hard drive in which that module’s source code lives. For instance,
myModule=d:/niagara/dev/myModule.
Now, whenever a request is made for a file, with a supported extension, from your web module, it will be resolved from
your hard drive instead of from the JAR. This way, hitting refresh in the browser will always load your up-to-date
changes. Note that this mode should
never be enabled in production - only in development.
First Steps
Now that you’ve set up your environment and gotten a brief introduction to the various JavaScript libraries and
frameworks available to you, it’s time to start developing! This section will contain a full tutorial, from creating your first
module using
grunt-init-niagara, to writing your first test, to viewing the result in the browser.
Creating a new module
Niagara Developer Guide
8/26/2015
80



============================================================
PAGE 83
============================================================

Begin by installing grunt-init-niagara to your machine, following the instructions from the Grunt website. Once
installed, navigate in a console to your dev directory containing the source for your Niagara modules. Begin the process
like so:
mkdir myWebModule
cd myWebModule/
grunt-init grunt-init-niagara
You’ll be asked a series of questions. Leaving the answer to any question blank will accept the default (shown in
parentheses). For this tutorial, we’ll ask it to create a bajaux Widget for us to study and modify.
Please answer the following:
[?] Niagara module name (myWebModule)
[?] Shortened preferred symbol for your Niagara module (myWbMdl)
[?] Description of your Niagara module My First Web Module
[?] Author name (tridium)
[?] Would you like to create a bajaux Widget? (y/N) y
After completing all the questions (you can select default values for all additional questions not shown above), it will
generate a
myWebModule-ux directory, containing the generated sources for your web module. It should build and
install using the normal build process: gradlew :myWebModule-ux:jar.
(To facilitate rapid development, ensure that you have enabled moduledev mode and added an entry for myWebModule
to moduledev.properties at this time.)
Just to make sure everything is functional, let’s install and run tests for our module.
cd myWebModule-ux/
npm install
grunt ci
Don’t worry, I’ll describe what’s happening in just a moment. These commands should download and install all the
necessary dependencies to run tests for your module, then it should actually run those tests. Hopefully, you’ll see

SUCCESS at the end of this process. Now let’s take a look at a few of the individual steps in this process, and the relevant
configuration files.
package.json
This file is used by Node.js and npm. Most of it won’t be relevant to you unless you decide to publish your module to the
npm repository. The important part is the devDependencies section, which describes which additional modules your
module depends on. It will be pre-populated with those modules that are necessary to run unit tests for your Niagara web
module.
Typing
npm install will download these dependencies from npm and install them for you.
Gruntfile.js
This file is used by Grunt. It describes all the automated tasks that are available for your module, and sets up
configuration settings for each.
You will see at the bottom of the file that it loads tasks for the grunt-niagara module. This is a utility module that
includes tasks that we at Tridium have found to be very useful when developing web applications. It sets up a default
configuration for each one that we find to work well. You are free to override these configurations as you wish, but just
leaving this file alone and working with the provided config is a great way to get started. (Later on, if you decide that

grunt-niagara isn’t right for you, you can leave it out completely and build your own toolchain as you see fit.)
To see the tasks that are available to you right out of the box, type grunt usage.
Another feature that grunt-niagara provides for you is the ability to pass in config flags on the command line when
running tests. To see a list of all available flags, type grunt flags. A few flags you may find particularly useful are:
Niagara Developer Guide
8/26/2015
81



============================================================
PAGE 84
============================================================

--testOnly: if your test suite grows large, you may find it taking longer and longer to run all of your specs. You
can use testOnly to limit which specs are run.
--station-http-port: if your test station runs a different HTTP port, you can specify which port your tests
should use to attempt to log into the station. Also works for HTTPS, FOX, and FOXS.
--override-station-ports: used in conjunction with the previous flags, actually will inject the port number
into the config.bog before starting the station to force it to run on a particular port.
--station-log-level: configure how much detail from the station log is output to the console when running
tests.
Many of these flags’ default values are set using the NIAGARA_HOME and NIAGARA_USER_HOME environment variables.
Watching - The Important Stuff
Let’s start developing straight away by typing grunt watch. Your test station will be copied into your stations/
directory and started up (note that the default credentials for the test station are user: admin, password: asdf1234), and an
instance of Karma will be started as well. By default, a single instance of PhantomJS - a command-line, headless web
browser - will start and get ready to run tests. (If you wish to run tests in other browsers, like Firefox or IE, it will print
out instructions on how to connect those as well.)
What happens now is that every time you save a change to a file, the grunt watch task will immediately spring into
action, running JSHint to detect syntax errors and other bugs, and then running your suite of tests in Karma to verify
behavior. This allows for a very rapid TDD, red-green-refactor workflow.
Let’s demonstrate the process. grunt-init-niagara should have generated you a file called
MyWebModuleWidget.js. Out of the box, the code should pass all linting and tests, so we’ll have to introduce some
new errors to see how they are handled. Take a look at the doLoad function and you should see a line that looks like this:
that.$buildButtons(value);
Change it so that it looks like this:
that.$buildButtons(valve)
Now save the file. Grunt should detect your change and immediately go to work. The first step in the watch process is to
run JSHint on your code. Our change introduced two errors in our code, and JSHint should have caught them both:
>> File "src\rc\MyWebModuleWidget.js" changed.
Running "jshint:src" (jshint) task
src\rc\MyWebModuleWidget.js
118 |    that.$buildButtons(valve)
^ Missing semicolon.
118 |    that.$buildButtons(valve)
^ 'valve' is not defined.
As you can see, whenever you introduce an error that JSHint can detect, you will immediately be alerted. Note that JSHint
is a static code analysis tool; your code has not actually run at this point. JSHint looks for syntax errors, misspellings,
code style problems, and similar categories of errors. It will not find logical errors in the execution of your code. That
happens in the next step.
Back out the change we just made so that it looks like it did before:
that.$buildButtons(value);
Now, we’ll demonstrate the unit testing capabilities of Karma. We’re going to follow the traditional TDD method of red-
green-refactor:
1. Write a new unit test and run it. It will fail, because you haven’t yet written the code to make it pass. Unit tests
   should be small and focused. This is called the “red” phase because test failures usually are shown in red.
2. Write the bare minimum amount of code to implement the behavior that the test is verifying, then run it again. It
   should pass. This is called the “green” phase because test successes usually are shown in green. You should never
   Niagara Developer Guide
   8/26/2015
   82



============================================================
PAGE 85
============================================================

write production code without first having already written a failing test for it.
3. Once you have good test coverage by following steps 1 and 2 many times, you can refactor your code for greater
   readability and efficiency, without worrying about breaking old behavior. Just make sure that your refactoring
   doesn’t introduce new behavior without accounting for it in the unit tests.
   Take a look at the example page at http://localhost/module/myWebModule/rc/myWebModule.htm that grunt-init-
   niagara generated for us (remember that the default credentials for the test station are user: admin, password:
   asdf1234). You’ll see that it creates a clickable button for every slot on a Component, and whenever we click one of the
   buttons, it updates to show the name of the slot we selected. For this next example, we want to change the widget’s
   behavior slightly. Instead of just displaying the slot name, we’ll add an exclamation mark just to properly convey our
   excitement.
   The first step is to change the test. (If we were adding new behavior to the widget we’d add another test, but since we’re
   changing existing behavior, we can just change the test.) Take a look at

srcTest/rc/MyWebModuleWidgetSpec.js. This suite of unit tests is written using the Jasmine framework, which
is Tridium’s test framework of choice and the one automatically configured by grunt-niagara.
Look at the spec named arms a handler to display selected slot name. Since we’re changing the way the
slot name is displayed, this is also the spec that we will change. Towards the bottom of the spec, you will see the
verification test that looks something like this:
runs(function () {
expect(slotDom.text()).toBe('curlyJoe');
});
Change it so that it’s expecting an exclamation point:
runs(function () {
expect(slotDom.text()).toBe('curlyJoe!');
});
Save the file. Since grunt watch is running, the change will be immediately detected and your test suite will be run.
You should see the spec fail:
nmodule/myWebModule/rc/MyWebModuleWidget #doInitialize() arms a handler to
display selected slot name FAILED
Expected 'curlyJoe' to be 'curlyJoe!'.
Executed 8 of 8 (1 FAILED) (0.562 secs / 0.567 secs)
This is the “red” phase of development: we have a failing unit test. By first having a failing test, we get visual proof that the
behavior we’re writing has correct test coverage behind it. To get back to green, we go into

src/rc/MyWebModuleWidget.js and update the $updateSlotText function to add the exclamation point we
need:
that.jq().find('.MyWebModuleWidget-selected-slot').text(slotName + '!');
Save the file and the test will automatically be run. This time, everything should pass.
Using these frameworks and techniques, you have the ability to implement a fully test-driven development process from
beginning to end. It may seem like a large upfront time investment (isn’t it twice as much code for the same
functionality?) but a comprehensive suite of automated tests for your code is invaluable: short-term, it helps to ensure
that the code you write actually does what you think it does; and long-term, it allows you to make changes,
improvements, and refactorings to your code without worrying about breaking existing functionality.
A note on Continuous Integration
Unit tests, as configured by grunt-niagara, will export their results in a JUnit-compatible XML format. This means
that if your organization uses a Continuous Integration solution, like Bamboo or Jenkins, it can consume these XML files.
Your JavaScript code can then be included in your CI process.
When invoking the Grunt process, use
grunt ci to run CI-related tasks and generate these XML files. If needed, you
can use the --junit-reports-dir flag to specify where the XML results go.
Niagara Developer Guide
8/26/2015
83



============================================================
PAGE 86
============================================================

Building your JavaScript into a Niagara module
Implementing your JavaScript-based widgets is only one part of the development process. They must also be built into
your Niagara module so that they can be deployed to Niagara stations and supervisors.
Compacting using the Gradle RequireJS Plugin
Build your module:
gradlew :myWebModule-ux:jar
As part of the build process, you’ll notice that one of the build steps is to optimize your JavaScript using the Niagara
RequireJS Gradle plugin. Let’s take a closer look at how this works.
Examine myWebModule.gradle and take a look at the niagaraRjs section:
builds = [
'myWebModule': [
rootDir: 'src/rc',
include: [
'nmodule/myWebModule/rc/myWebModule',
'nmodule/myWebModule/rc/MyWebModuleWidget'
]
]
]
This tells the Gradle build process to optimize your JavaScript using r.js. The JavaScript modules specified in the
include section will have all of their dependencies resolved and included in a single file:
myWebModule.built.min.js. This allows you to download every JavaScript file in your Niagara module using a
single network call. This is crucial on embedded devices where negotiating network connections can be slow.
Implementing BIJavaScript
Often, your HTML5 web app will have an instance of some BajaScript value: a Facets, for instance, or a DynamicEnum,
that you wish to present to the user in an editor. You could manually go and fetch the code for FacetsEditor or
DynamicEnumEditor, then instantiate, initialize, and load it into your page. It would be much easier, though, to simply
ask the framework for an editor that’s appropriate to edit that value. Then, you’ll be certain that you’ll be provided the
correct, most up-to-date version of that editor.
Also, consider the Property Sheet. If you have a custom Baja value and a custom field editor to edit that value, you’ll need
to create an association between the two so the Property Sheet knows how to show the correct editor for your custom
value.
The way to do this is to create a simple Java class in your module. Its only job is to provide answers to these questions:
1. What JavaScript file represents the implementation of my editor?
2. What Types can I edit with this editor?
   What JavaScript file defines my editor?
   To let the Niagara framework know about the JavaScript file containing your editor, create a Java class that extends
   BSingleton and implements javax.baja.web.js.BIJavaScript. This simply creates a mapping between a
   Niagara Type and a JavaScript file.
   public final class BMyWebModuleWidget
   Niagara Developer Guide
   8/26/2015
   84



============================================================
PAGE 87
============================================================

    extends BSingleton
    implements BIJavaScript, BIFormFactorMini
{
//private constructor, TYPE and INSTANCE as per BSingleton
public JsInfo getJsInfo(Context cx) { return jsInfo; }
private static final JsInfo jsInfo =
JsInfo.make(
BOrd.make("module://myWebModule/rc/MyWebModuleWidget.js"),
BMyWebModuleJsBuild.TYPE
);
}
As you can see, there is very little to a BIJavaScript class. It has only one method to implement, getJsInfo(),
which lets the Niagara Framework know where the JavaScript implementation of your widget is located.
(Also notice the implemented interface BIFormFactorMini - see the bajaux documentation for details on what this
does.)
You can also see as part of the JsInfo.make() method, you can pass in an instance of BJsBuild. This performs a
very similar function to BIJavaScript, but instead of providing the location of a single JavaScript module, it provides
the location of an optimized JavaScript built file: e.g., myWebModule.built.min.js produced by the Gradle
RequireJS plugin as described above.
The implementation of a BJsBuild class is also very simple:
public class BMyWebModuleJsBuild extends BJsBuild
{
public static final BMyWebModuleJsBuild INSTANCE = new BMyWebModuleJsBuild(
"myWebModule", //webdev ID
new BOrd[] {
BOrd.make("module://myWebModule/rc/myWebModule.built.min.js")
}
);
//TYPE and private constructor
}
While optimizing your JavaScript and creating BJsBuild classes are strictly optional, they are highly recommended.
Don’t forget that your BIJavaScript and BJsBuild classes are standard Niagara Types and should be included in
module-include.xml.
What Types can I edit with my editor?
Now that you’ve implemented a JavaScript editor and registered its existence with the framework, you can also declare it
to be compatible with certain Types. For instance, MyWebModuleWidget might be able to load values of type
myWebModule:MyWebModuleComponent. To declare this relationship, simply register your BIJavaScript as an
agent on a Type. You can do this the usual way, in module-include.xml:
<type name="MyWebModuleWidget"
class="com.tridium.mywebmodule.BMyWebModuleWidget">
<agent><on type="myWebModule:MyWebModuleComponent" /></agent>
</type>
Or the new annotation-based method available in Niagara 4:
@NiagaraType(agent=@AgentOn(types={"myWebModule:MyWebModuleComponent"}))
public final class BMyWebModuleWidget
Niagara Developer Guide
8/26/2015
85



============================================================
PAGE 88
============================================================

Switching your Station into Web Development mode
One last thing to consider. At this point you have two files containing your editor’s code: the one you’ve just created, with
the human-readable JavaScript code (MyWebModuleWidget.js), and the built and minified version for conserving
network traffic (myWebModule.built.min.js).
During normal usage, your station will only serve up the minified file. This is absolutely the correct behavior: on an
embedded device, conservation of network traffic is key. But take a look at the minified file: it’s completely
incomprehensible. When developing and debugging, you really want to be able to see the original, human-readable code.
By enabling
webdev mode for your module, the station will kick into debug mode: it will switch over to serving up the
original file, so that you can open up the console in Chrome or Internet Explorer and trace through your actual code.
To enable webdev mode, simply go into the spy page for the station and visit the webDevSetup page. (You’ll need to
actually visit the web interface first - so the station serves up some files - for it to appear.) Enable webdev mode for the
ID of your BJsBuild, and from then on, the unminified files from your module will be served to the browser. This is
invaluable when debugging your code live in the browser. (To learn more about browser debugging, search the web for
“{your browser name} console tutorial.”)
Moving Forward
Let’s do a quick recap of what you’ve accomplished so far.
1. You’ve created a widget with JavaScript that can be used in the new Niagara 4 HTML5 web views
   (MyWebModuleWidget.js)
2. You’ve registered its existence with the framework (BMyWebModuleWidget implements BIJavaScript)
3. You’ve optimized it down within a single built JavaScript file to minimize network traffic (niagaraRjs
   configuration creates myWebModule.built.min.js)
4. You’ve registered the existence of the built file with the framework (BMyWebModuleJsBuild extends BJsBuild)
5. You’ve let the framework know to show you a MyWebModuleWidget when it tries to load an editor for a
   compatible Type (BMyWebModuleWidget is an Agent on BMyWebModuleComponent).
   At this point, you have everything you need to go full-speed into developing HTML5 widgets and editors. Continue using
   the linting and testing tools provided by grunt-init-niagara to iterate over your widget until it is well tested, error-
   checked, and fully functional. (If you are developing a field editor intended for use in the HTML5 Property Sheet,
   definitely check out the webEditors tutorial for notes on this use case.)
   Feel free to stop by the Niagara Central Forums to discuss any questions or problems.
   Happy coding!
   Niagara Developer Guide
   8/26/2015
   86