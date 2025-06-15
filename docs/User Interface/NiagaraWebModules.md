# Niagara Web Modules

In Niagara 4 standard Java Web Server technology can be used.

The Web Server currently being used is Jetty. Jetty is built around the standard Java Servlet Specification.

Currently, the Jetty supports version 3 of the Java Servlet Specification. Currently we’re not supporting the newer Servlet
 annotations; a Web XML descriptor must be used instead.

In Niagara AX, there are two other ways of creating Java Servlets. These are still supported in Niagara 4…

*   Servlet Views: create a view that is also a Servlet.
*   Web Servlet Components: create a component that’s also a Servlet.

Niagara 4

Please note that applications using BWebServlet and BServletView are still and will continue to be supported. From
 Niagara 4, we’ve additionally added support for adding standard Java Servlets that extend javax.servlet.http.HttpServlet.

## My First Niagara Web Module

Here’s how you can create a Niagara Module that extends a standard javax.servlet.http.HttpServlet class…

1.  In your Niagara Module, add a class that extends javax.servlet.http.HttpServlet.
2.  Override doGet or any other HTTP verb related methods to handle implementation.
3.  There’s no WebOp. If this Servlet is using Niagara Security, you can call the following HttpServletRequest
    methods…
    *   `getUserPrincipal()`: cast this to a BUser.
    *   `getLocale()`: returns a java.util.Locale object. Use the forLanguageTag and toLanguageTag methods to get
        the Locale to and from a String. Please note that javax.baja.util.Lexicon has a make method that takes a
        Locale object. Use a combination of these two methods to create an instance of javax.baja.sys.BasicContext.
        Please note, NCCB-7051 will address some of the issues concerning country and variant.
4.  In your module’s src directory, add a directory called WEB-INF.
5.  In this folder, create a file called web.xml.
6.  In your module’s gradle file, remember to add an entry to pick up the .xml files that will be created. For example…

```groovy
jar {
  from('src') {
    ...
    include 'WEB-INF/*.xml'
  }
}
```

Here’s a sample web.xml file that plugs a Servlet into our new Web architecture…

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>
  <servlet>
    <servlet-name>testServlet</servlet-name>
    <servlet-class>mypackage.TestServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>testServlet</servlet-name>
    <url-pattern>/test/*</url-pattern>
  </servlet-mapping>
</web-app>
```

The Servlet java class is plugged into the specified URL pattern. It’s a URL pattern and not just a servlet name like in Niagara AX. This gives developers far more flexibility.

The `*` in the pattern means that anything after /test/… will also be picked up by this Servlet. This is very useful if
 you’re making a RESTful API. The XML listed here is all part of the J2EE standard. There’s nothing specific to the
 Jetty implementation.

Now try building your module and starting up a Station. On Station start up, the Servlets will be automatically installed
 into the Web Server. No more configuration is required. Once the Station has started, the URL to access the Servlet
 would be in the following format…

`http://localhost/moduleName/test`

Or as we’re also using a `*` in the URI pattern…

`http://localhost/moduleName/test/whatever/foobar`

By default, the name of the module is used for the Context Path.

The Servlet Path is specified in the web.xml file. In this case it’s ‘test’. The path after the Servlet Path is known as
 the Path Info. Typically this is what you use in your Servlet. Please never call
 `HttpServletRequest#getRequestURI()` (as is typically done in AX). Servlets should be relative and
 reusable. Most of the time they really don’t need to know about their precise plug point within a Web Server!

### Changing the Context Path

To change the Context Path of the Servlet we need to add one more XML file alongside web.xml called `jetty-web.xml`. This file configures functionality specific to the Jetty Web Server that can’t be done via web.xml. By default,
 using the Niagara Module name is used for Context Path. This is a sound way to try and create a unique path mapping
 for a web application. This can be changed with the following `jetty-web.xml` file…

```xml
<?xml version="1.0"  encoding="ISO-8859-1"?>
<!DOCTYPE Configure PUBLIC "-//Jetty//Configure//EN"
"http://www.eclipse.org/jetty/configure.dtd">
<Configure id="webApp" class="org.eclipse.jetty.webapp.WebAppContext">
  <!-- Change the Context Path from the module name to something else -->
  <Set name="contextPath">/somethingelse</Set>
</Configure>
```

Note the contextPath has been added to the file.

Once built, the URI would change to `http://localhost/somethingelse/test/whatever`.

### Filters

As well as Servlets, developers can now use `javax.servlet.Filter`.

A class that implements the Filter interface can intercept HTTP Requests before they get to their Servlet.

*   They can also process the responses out of a Servlet.
*   They can have initialization parameters.
*   They can be plugged in via some web.xml.
*   They’re also great for monitoring and profiling Web Server performance.

### How can I use a proper WAR file?

This isn’t currently supported in Niagara 4.0.

---

## BServletView

### Overview

A Servlet View is Niagara view that typically generates HTML.

Alternatives to extending BWebServlet are…

*   `BServletView`: used by developers who want to create Web Views on Components.
*   Java Servlets: create standard Java Servlets.

Please note…

`BHxView` extends `BServletView` and may be a preferred alternative. Click here for more information on creating Hx
 Views.

In Niagara 4’s new Open Web architecture, you can create client side views in JavaScript. You may find this a better
 alternative than traditional Server Side Servlet programming for User Interfaces.

A developer creates a Servlet View in the following way…

1.  Extend `javax.baja.sys.BServletView`.
2.  Override `doGet` or any other HTTP verb related methods to handle implementation.
3.  Register the View as an Agent on the desired Niagara Type.

A few things to note about `BServletView`…

*   `BServletView` extends `BSingleton`. Therefore, there’s only ever one instance of a View.
*   `BHxView` extends `BServletView`.

### Example

Here’s an example of a Servlet View that will be rendered in a web browser when the user navigates to the UserService.
 Please note, in this case the view is declared as an Agent on `baja:UserService`.

```java
public final class BMyFirstServletView extends BServletView
{
  private BMyFirstServletView() {}
  public static final BMyFirstServletView INSTANCE = new BMyFirstServletView();
  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyFirstServletView.class);
  @Override
  public void doGet(WebOp op) throws Exception
  {
    op.getHtmlWriter()
      .w("<!DOCTYPE html>").nl()
      .w("<html>").nl()
      .w("<head></head>").nl()
      .w("<body>").nl()
      .w("<h1>Hello World!</h1>").nl()
      .w("</body></html>");
  }
}
```

---

## BWebServlet

### Overview

A Web Servlet is a Component that can be added to a Station. Alternatives to creating a Web Servlet are Servlet View and
 standard Java Servlets.

Once a Web Servlet has been added to a Station and is operational…

*   The Servlet can be accessed from the Web Server via its Servlet name.
*   The Servlet utilizes Niagara’s Security Model. This is a great way to limit access to the Servlet.
*   The Servlet Component can be removed from the Station. Once removed, the Servlet is no longer active.

To create a Web Servlet in Niagara AX, a developer has to…

1.  Extend `javax.baja.web.BWebServlet`.
2.  Define a Servlet name.
3.  Override `doGet` or any other HTTP verb related methods to handle implementation.
4.  A user adds the Servlet Component to a Station via a palette file.

### Example

This is a simple Web Servlet that can be accessed in a browser via `https://localhost/myFirstServlet`…

```java
@NiagaraType
@NiagaraProperty(
  name = "servletName",
  type = "baja:String",
  flags = Flags.READONLY,
  defaultValue = "myFirstServlet"
)
public final class BMyFirstWebServlet extends BWebServlet
{
/*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
/*@ $com.tridium.web.servlets.BMyFirstWebServlet(522312782)1.0$ @*/
/* Generated Wed May 13 12:49:26 BST 2015 by Slot-o-Matic (c) Tridium, Inc.
 2012 */
////////////////////////////////////////////////////////////////
// Property "servletName"
////////////////////////////////////////////////////////////////

  /**
   * Slot for the {@code servletName} property.
   * @see #getServletName
   * @see #setServletName
   */
  public static final Property servletName = newProperty(Flags.READONLY,
"myFirstServlet",null);

  /**
   * Get the {@code servletName} property.
   * @see #servletName
   */
  public String getServletName() { return getString(servletName); }

  /**
   * Set the {@code servletName} property.
   * @see #servletName
   */
  public void setServletName(String v) { setString(servletName,v,null); }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMyFirstWebServlet.class);
/*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  @Override
  public void doGet(WebOp op) throws Exception
  {
    op.getHtmlWriter()
      .w("<!DOCTYPE html>").nl()
      .w("<html>").nl()
      .w("<head></head>").nl()
      .w("<body>").nl()
      .w("<h1>Hello World!</h1>").nl()
      .w("</body></html>");
  }
}
```

---

## Apache Velocity

Apache Velocity is an open source web template engine that’s now integrated into the Niagara framework. It provides
 users with the ability to script an HTML page together using a very simple scripting language.

For more information on understanding what Velocity is, please visit the Apache website here.

The user guide for using Velocity can be found here.

Velocity support was added to Niagara AX in version 3.7.

### Users

The Velocity API is not just for developers but can also be used by advanced users. Advanced users can take advantage of
 Velocity through Station Components that one can configure from the `axvelocity` palette.

### Velocity Station Components

The AX Velocity Station Components require the ‘axvelocity’ license feature.

There are multiple ways to use Velocity. Advanced users (who aren’t necessarily fully trained Niagara developers) can
 configure a Station using Components from the `axvelocity` palette.

#### Getting Started

1.  Create a new Station
2.  Add a NumericWritable and BooleanWritable to the root of the Station.
3.  Open the `axvelocity` palette and drag and drop the `VelocityServlet` into the Station.
4.  Double click the Servlet, this opens the Velocity Document Manager View.
5.  In this view, the name of the Servlet can be set and new Velocity Document Components can be created.
6.  Create a new Velocity Document Component.
7.  Make sure the template file for the Document Component is `file:^test.vm`.
8.  This text file will be located in the root of the Station’s file system.
9.  Create this file using Workbench but leave it blank for now.
10. Double click on the newly created Document Component, this will load the Velocity Context Element
    Manager View.
11. Create two Velocity Context Ord Elements.
12. Set the ORD Property on one of the Elements to point to the NumericWritable you created earlier. The name
    of the Element should be `numPoint`.
13. Set the ORD Property of the other Element to point to the BooleanWritable you created earlier. The name of
    the Element should be `boolPoint`.
14. Now edit the vm file you created earlier.
15. This file will contain the HTML for our web view as well as some VTL (Velocity Template Language) script
    to dynamically generate part of the web page.
16. We’re now going to reference the two points (via the Velocity Context ORD Elements) we created earlier.
17. Use this HTML in the view…

```html
<!DOCTYPE html>
<html>
  <head>
    <title>My first Velocity test!</title>
  </head>
  <body>
    <p>Boolean Point: $boolPoint</p>
    <p>Numeric Point: $numPoint</p>
  </body>
</html>
```

18. Save the text file.
19. Open a web browser and log on to Niagara. Logging on as admin will do.
20. Change the URL to point to `http://localhost/velocity/test` (localhost can be changed to an IP address
    of your choice).
21. Note that `velocity` is the name of the servlet.
22. Note that `test` is the name of the Document Component you created earlier.
23. The page should now load.
24. Note how the HTML typed in the vm file has been used for this view.
25. Note how `$boolPoint` and `$numPoint` has been replaced with real point data.

#### Component Description

*   `VelocityServlet`: the Servlet used by the Web Service to generate the web based view. The Servlet is accessed
    through a Web Browser by `http://yourIpAddress/velocity`. Please note the name of the Servlet can be
    changed from ‘velocity’ to something else in the Velocity Document Manager View. The Velocity Document
    Manager is the default view for this Component.
*   `VelocityDoc`: a Document Component has a reference to a VM file and acts as a container for Velocity Context
    Elements. The VM file contains the HTML and VTL script used to generate a web page.
*   `VelocityContextOrdElement`: this Component has an ORD Property that’s used to point to another Component in
    the Station. The name of the Elememt is used in the VTL file so it can be referenced.
*   `VelocityDocWebProfile`: this is a Web Profile a user can select for VelocityDoc Components. If the VelocityDoc
    Component has its `useProfile` Property set to true, this Profile will be used to generate the outer HTML page.
    For more information, please see the section on profiling.

#### Security

Only a user with operator read permissions on a `VelocityDoc` Component can access that through the Velocity Servlet.

#### Navigation

Accessing a particular `VelocityDoc` through a browser can be done in a variety of ways. For example…

*   The velocity Servlet name can be changed by a user.
*   `http://localhost/velocity` - this will redirect to the first valid `VelocityDoc` that can be found for the user.
*   `http://localhost/velocity/test` - this will load the `VelocityDoc` named test.
*   `http://localhost/ord?station:%7Cslot:/VelocityServlet` - this will redirect to the first valid
    `VelocityDoc` that can be found for the user.
*   `http://localhost/ord?station:%7Cslot:/VelocityServlet%7Cview:test` - this will redirect to
    the `VelocityDoc` named test.
*   `station:|slot:/VelocityServlet|view:test` - an ORD that can be used as a hyperlink in a Px page.
    This will redirect to the `VelocityDoc` named test.

Specifying the ORD to the Servlet Component in a NavFile is a really great way to get to your `VelocityDoc` using Niagara.

#### View parameters

Parameters can be passed into a view via a URI (or ORD that redirects to a URI). For instance…

*   `http://localhost/velocity/test?param1=val1&param2=val2` - passes two parameters into a view.
*   `station:|slot:/VelocityServlet|view:test?param1=val1;param2=val2` - an ORD that can be
    used as a hyperlink in a Px page. This will redirect to the `VelocityDoc` named test with the specified parameters.

The parameters can be picked up from VTL code. For example…

```vtl
## Iterate through any query parameters used in the URL
#set( $parameters = $ax.op.getRequest().getParameterNames() )
#foreach ( $param in $parameters )
<p>
  Found parameter: $param - $ax.op.getRequest().getParameter($param)
</p>
#end
```

#### Profiling

Profiling is an advanced feature of working with `VelocityDoc` Station Components. A Profile generates the outer HTML
 content while the `VelocityDoc` generates the inner.

##### Getting Started

1.  Create a user who has their Default Web Profile set to `Velocity Doc Web Profile`.
2.  Create a VM file for the profile (i.e. `file:^profile.vm`). The VM file should have code similar to the
    following…
3.  Set the Template File ORD on the Profile to this VM file.

```vtl
<!DOCTYPE html>
<html>
  <head>
    <title>Velocity Profile</title>
    #set($generateHeader = true)

    ## Parse the inner view for the header...
    #parse("$ax.viewTemplate")

    #set($generateHeader = false)
  </head>
  <body>
    <h2>Path: $ax.target.getPathInfo()</h2>

    ## Parse the inner view for the header...
    #parse("$ax.viewTemplate")
  </body>
</html>
```

*   The Profile creates the outer HTML and then calls
    `#parse("$ax.viewTemplate")` to the inner HTML.
*   Note how the `$generateHeader` flag is used to determine whether the header
    of the body of the HTML document is being generated.

4.  Create a `VelocityDoc` Component that has its `useProfile` Property set to true.
5.  When the `VelocityDoc` Component view is accessed through a browser (with the Profile we’ve just set up),
    the `VelocityDoc` will rely on the Profile generating the outer HTML.
6.  If the `VelocityDoc`’s `useProfile` Property is set to false, the `VelocityDoc` is responsible for generating the
    whole of the HTML document (just as before).

Here’s an example of the VTL for the `VelocityDoc`’s VM file…

```vtl
#if ($generateHeader)
  ## Anything for the header of the HTML page goes here.
#else
  ## Anything for the body of the HTML page goes here.
  <h3>This is from the body of the view</h3>
#end
```

*Note how the `$generateHeader` (created by the Profile) is used to determine whether code is being generated for the
 HTML document’s header or body.

The ‘Velocity Doc Web Profile’ must always be used to access `VelocityDoc` views that have their ‘useProfile’ Property set to true!

### Standard Velocity Java API

Velocity can be used by Java developers to create Velocity based views. A developer who uses this API should have been
 on the Developer Course and hence should be a fully trained Niagara Jedi.

#### BVelocityView

`BVelocityView` extends `BServletView`. This class forms the basis for any Velocity based view. A view may or may not work
 in conjunction with a `BVelocityWebProfile`.

##### Getting Started

1.  Create a new class that extends `BVelocityView`.
2.  Implement the `getTemplateFileMethod`.
    *   This method will return an ORD to a file resource that contains the HTML and VTL (Velocity Template
        Language) used to generate the HTML.
    *   For debugging, it might be easier to point this to a file in your local Station’s file system.
    *   For distribution, it’s best to reference a file from a Niagara module (using the module ORD scheme).
3.  Override the `initVelocityContext` method.
    *   Overriding this method allows a developer to inject new things into a `VelocityContext`.
    *   Anything added to the `VelocityContext` can be referenced from VTL.

#### BIVelocityWebProfile

An interface used by any profile that wants to act as a Velocity Web Profile.

#### BVelocityWebProfile

A Web Profile that implements `BIVelocityWebProfile`.

A Velocity Web Profile has it’s own VM file used to render the overall page. In the Velocity Context, there’s a symbol
 called `$ax.viewTemplate` that be then be used by the profile VTL to render the underlying view.

#### VelocityContextUtil

This is a useful library of functions that can be called via VTL. In VTL, this library can be referenced via ‘`$ax.util`’ or
 ‘`$util`’. For more information on use please see Niagara VTL.

### Hx Velocity Views

To enhance Hx development, some Hx Velocity views have been added.

In Hx development, a developer would normally write out an Hx view’s initial HTML by overriding `BHxView`’s `write`
 method. In the following Velocity Hx classes, this method has already been overridden and will return output from
 Velocity.

#### BVelocityHxView

*   This class extends `BHxView`.
*   This class follows the same design as `BVelocityView`.
*   Note: the HxOp can be referenced from the VTL from
    `$ax.op`. For instance, `$ax.op.scope('idOrName')`.

#### BVelocityHxFieldEditor

An Hx Field Editor that uses Velocity.

#### BVelocityHxPxWidget

An Hx Px Widget that uses Velocity.

---

## Niagara VTL

Common to all Velocity based views are some really useful Niagara related methods and properties that are accessed
 through the Velocity Context.

### Use Cases

There’s a LOT we could add to the `VelocityContext`. However, to try and stem the tide, we’ve developed this feature
 around the following use cases…

*For anything ‘live’, please consider using BajaScript. It does all of the ‘live’ things (like invoking an Action for instance)
 that Velocity isn’t designed to do!*

*   To print out the value of a point. This includes any facets for units, precision etc.
*   To resolve an ORD to a point and print out its value.
*   To resolve an ORD with a base and print out its value.
*   To resolve an ORD to a table.
*   To then be able to iterate through the results.
*   To generate an HTML table from the results.
*   To print out fully localized display Strings of each value in the table (i.e. that use any necessary units and
    precision).
*   To create a hyperlink from an ORD or mounted Component.
*   To print out translated values from a Lexicon.
*   To print out translated values from a Lexicon (with parameters).
*   To iterate through a Component’s children.
*   To iterate through a Component’s children of a specific Type.
*   To test with a particular value is of a certain Type.
*   To detect whether a Property should be hidden.
    *   This includes checking for the hidden Slot flag as well as Types we never typically want the user to see (i.e.
        `BLink`, `BNameMap` (displayNames) and `BWsAnnotation`).
*   To detect whether a user has appropriate read, write and invoke security permissions on a particular Slot or
    Component.
*   To easily be able to include BajaScript into a Velocity View.
*   To easily be able to include jQuery into a Velocity View.

### API

The core Niagara Velocity related methods and properties are part of the `$ax` namespace. Some sub-namespaces (i.e.
 `$ax.util`) are consider so useful that we’ve also added them to the global namespace (i.e. `$util`).

#### Core ax namespace

*   `$ax.session`: the `HttpSession` being used to the current request.
*   `$ax.target`: the `OrdTarget` for the current request.
*   `$ax.cx`: the `Context` for the current request.
*   `$ax.op`: the `WebOp` for the current request.
*   `$ax.obj`: the resolved object for the current request (from `OrdTarget#get()`).
*   `$ax.profile`: the web profile being used.
*   `$ax.Flags`: `javax.baja.sys.Flags`.
*   `$ax.lang`: the language for the current request.
*   `$ax.user`: the user for the current request.

#### Util namespace

This can be accessed via `$ax.util` or `$util`.

All methods are fully listed (and commented) in `javax.baja.velocity.VelocityContextUtil`.

The methods are designed to be easy to use and in some cases can take a variety of argument Types (just like in
 JavaScript).

For convenience here’s an overview…

##### Value Access

*   `$util.get("station:|slot:/")`: resolves an ORD returns the object. If the result is a mounted Component,
    it will be leased.
    *   The argument can be a String or an `BOrd`.
    *   If this ORD resolves to a table, then an array will be returned that can be iterated through.
*   `$util.get("slot:BooleanWritable", "station:|slot:/")`: same as above except this can also take a
    base to help ORD resolution.
    *   Specifying a base is great for creating reusable VM files.
    *   The base can be an `BOrd`, String (that get resolves as an `BOrd`) or a `BObject` that will get used to resolve the
        ORD.
*   `$util.resolve("station:|slot:/")`: same as ‘get’ except this resolves to an `OrdTarget`.
*   `$util.resolve("slot:BooleanWritable", "station:|slot:/")`: same as ‘get’ except this resolves to
    an `OrdTarget`.
*   `$util.getChildren(complex)`: return an array of children of a `BComplex`.
    *   The argument can be `BComplex` or a `BOrd` (or String) that resolves to a `BComplex`.
*   `$util.getChildren(complex, "myModule:MyType")`: return an array of children from a Complex of a
    specific Type.
    *   The first argument can be `BComplex` or a `BOrd` (or String) that resolves to a `BComplex`.
    *   The second argument can be a `Type` or String to filter what children are returned.
*   `$util.is(complex, "myModule:MyType")`: returns a boolean indicating whether the specified value is of
    the specified Type.
    *   The second argument can be a `Type` or String.

##### Misc

*   `$util.ord("station:|slot:/", "bql:select * from baja:Component")`: creates a normalized
    ORD from a base and child. The argument can be `BOrd` or String.
*   `$util.lex("bajaui").get("dialog.ok")`: get a Lexicon value.
*   `$util.lex("bajaui").getText("fileSearch.scanningFiles", "first", "second")`: get
    a Lexicon value with parameters.
    *   Please note that `$util.lex("bajaui")` simply returns the Lexicon for the given module name.
*   `$util.requirejs()`: adds require js support. You need to add this at the top of the document if you want to use
    BajaScript or any of the new web technologies added to Niagara 4.

##### Display

*   `$util.getDisplay(complex)`: returns a display string for the given complex.
    *   The argument can also be an `BOrd` (or String that get resolves as an `BOrd`) that resolves to a `BComplex`.
*   `$util.getDisplay("slot:BooleanWritable", base)`: returns a display string for the given complex. If
    an ORD is specified as the argument then the base is used to help resolve it.
    *   The first argument can also be an `BOrd` (or String that get resolves as an `BOrd`) that resolves to a `BComplex`.
    *   The base argument can be a `BOrd` (or String that get resolves as a `BOrd`) or a `BObject` used to help resolve
        any ORDs.
*   `$util.getDisplayFromProperty(complex, prop)`: returns a display string for the given complex and
    Property.
    *   The first argument can also be an `BOrd` (or String that get resolves as an `BOrd`) that resolves to a `BComplex`.
    *   The second argument can be a `Property` or a Property name (String).
*   `$util.getDisplayFromProperty(complex, prop, base)`: returns a display string for the given complex
    and Property.
    *   The first argument can also be an `BOrd` (or String that get resolves as an `BOrd`) that resolves to a `BComplex`.
    *   The second argument can be a `Property` or a Property name (String).
    *   The base argument can be a `BOrd` (or String that get resolves as a `BOrd`) or a `BObject` used to help resolve
        any ORDs.

##### Slot Access

*   `$util.isHidden(complex, slot)`: returns a boolean indicating whether the specified slot should be hidden.
    *   The first argument can be a `BComplex` or a `BOrd` (or String) that resolves to a `BComplex`.
    *   The second argument can be a `Slot` or a Slot name (String).
    *   This method will also filter out Types that aren’t typically shown on a Property Sheet (i.e. `BWsAnnotation`,
        `BLink` and `BNameMap` (displayNames).
*   `$util.canRead(component)`: returns a boolean whether the currently logged on user has read permissions on
    the specified Component.
    *   The argument can be `BComponent` or `BOrd` (or String) that resolves to a `BComponent`.
*   `$util.canRead(component, slot)`: returns a boolean whether the currently logged on user has read
    permissions to access the specified Slot.
    *   The first argument can be `BComponent` or `BOrd` (or String) that resolves to a `BComponent`.
    *   The second argument can be a `Slot` or a Slot name (String).
*   `$util.canWrite(component, slot)`: returns a boolean whether the currently logged on user has write
    permissions to access the specified Slot.
    *   The first argument can be `BComponent` or `BOrd` (or String) that resolves to a `BComponent`.
    *   The second argument can be a `Slot` or a Slot name (String).
*   `$util.canInvoke(component, slot)`: returns a boolean whether the currently logged on user has invoke
    permissions to access the specified Slot.
    *   The first argument can be `BComponent` or `BOrd` (or String) that resolves to a `BComponent`.
    *   The second argument can be a `Slot` or a Slot name (String).

##### jQuery

*   `$util.jQuery()`: returns the HTML script tag to include jQuery.

---

## Velocity Px Views

This document follows on from the core velocity documentation.

In 3.8, a new feature was added that allows non-Java programmers to create dynamic Px Views (Px Views that are created
 on the fly). By using the Velocity, to create Px XML, customers have new flexibility in creating graphical views. This
 feature is intended for advanced Niagara users who want more dynamic graphics and navigation without having to use
 Java.

This feature does require the ‘axvelocity’ license feature.

### Getting Started

1.  In a Station, create a folder.
2.  Add some points to the folder.
3.  Go to create a new Px View on the folder.
4.  Click the Dynamic View option.
5.  Select the `axvelocity:VelocityPxView` option and click OK.
6.  Create a Px file on the file system.
7.  Using the Px Editor, create your basic template page.
8.  Exit the Px editor and rename the file to the extension `pxvm`.
9.  Please note that from this point onwards, the Px File can no longer be edited using the Px Editor.
10. Using the Property Sheet, navigate to the newly created Velocity Px View.
11. Set the Px Velocity Template File to point to the newly created `pxvm` file.
12. Load the view and note how it’s rendered the Px page.
13. Now edit the Px view and add some VTL to script the creation of the Px file. Here’s an example…

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- Niagara Presentation XML -->
<px version="1.0" media="workbench:WbPxMedia">
<import>
  <module name="baja"/>
  <module name="bajaui"/>
  <module name="gx"/>
  <module name="converters"/>
</import>
<content>
  <ScrollPane>
    <CanvasPane name="content" viewSize="500.0,400.0" background="#c0c0ff">
  #set( $kids = $util.getChildren($ax.obj, "baja:Component") )
      <GridPane layout="10.0,10.0,480.0,380.0" columnCount="1" rowGap="20">
    #foreach($k in $kids)
        <Label>
          <ValueBinding ord="$k.getSlotPathOrd()"
 hyperlink="$k.getSlotPathOrd()">
            <ObjectToString name="text" format="%displayName%: %.%"/>
         </ValueBinding>
        </Label>
    #end
      </GridPane>
    </CanvasPane>
  </ScrollPane>
</content>
</px>
```

This file will now create the navigation and labels for the points dynamically.

In conclusion, Velocity Px Views are very powerful but can be cumbersome without the initial use of the Px Editor to
 create the overall look and feel for the Px View.

### PxIncludes

In order to make the page more manageable, it’s best to use this in conjunction with PxIncludes. The PxInclude Px file
 can then still be edited. For example, in this example the ‘include.px’ file can still be edited…

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- Niagara Presentation XML -->
<px version="1.0" media="workbench:WbPxMedia">
<import>
  <module name="baja"/>
  <module name="bajaui"/>
  <module name="gx"/>
  <module name="converters"/>
</import>
<content>
<ScrollPane>
  <CanvasPane name="content" viewSize="500.0,400.0" background="#c0c0ff">
    #set( $kids = $util.getChildren($ax.obj, "baja:Component") )
    <GridPane layout="10.0,10.0,480.0,380.0" columnCount="1" rowGap="20">
      #foreach($k in $kids)
        <PxInclude ord="file:^px/include.px" variables="val=s:$k.getName()"/>
      #end
    </GridPane>
  </CanvasPane>
</ScrollPane>
</content>
</px>
```

### Querying the Media

The Px Media being used can be queried in Velocity. This is useful for dynamically creating Px Views for a Mobile
 experience that may require less Widgets…

```vtl
#if ($ax.pxView.isMobileMedia())
  <Label text="Mobile Media is set!"/>
#elseif ($ax.pxView.isHxMedia())
  <Label text="Hx Media is set!"/>
#elseif ($ax.pxView.isReportMedia())
  <Label text="Report Media is set!"/>
#elseif ($ax.pxView.isWorkbenchMedia())
  <Label text="Workbench Media is set!"/>
#end
```

### Why Velocity Px Views?

A Px file with Velocity code in allows graphics to be created on the fly without the aid of a Java programmer.
 Unfortunately, any Px file with VTL code can’t be edited by the Px Editor. That’s why PxIncludes are recommended to
 make this less cumbersome. The feature is intended for advanced users and covers the following use cases…

*   Dynamically created navigation
*   Reports
