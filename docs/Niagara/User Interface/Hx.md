# Hx

## Overview
 The hx module defines the framework for building HTML-based user interfaces using HTML, CSS, JavaScript, and
 XmlHttp.
 Hx is designed to approximate the same paradigms that exist for developing user interfaces in the Workbench
 enviornment, such as Views, FieldEditors, and Profiles. It's main goal is try and transparently produce lightweight
 HTML-only interfaces automatically based on the workbench views. Limited support exists for standard views like the
 Property Sheet, but
Px is the main reuse target.
 If you are not familiar with how interfaces are designed in workbench you should read the Workbench documentation
 before continuing.

### HxView Details of HxView
### HxOp Details of HxOp
### HxProfile Details of HxProfile
### Events Detail of Events
### Dialogs Details of Dialogs
### Theming Details of Theming
Niagara Developer Guide
8/26/2015
102

============================================================
PAGE 105
============================================================

## Hx - HxView

### HxView Overview
 HxView provides the content viewers and editors for working with the active objects. As you have probably guessed,
 HxView is the Hx equivalent of WbView. HxView is designed to produce and interact with a snippet of HTML.
 BHxProfile takes one or more HxViews, adds some supporting markup plus some chrome, and produces a complete
 HTML page.
 From the diagram, a HxView:
Must have logic to render a HTML snippet from an object (write). This is synonymous to
 BWbView.doLoadValue().
May have logic to save changes back to the object (save). This is synonymous to BWbView.doSaveValue().
May have logic to periodically update the HTML snippet (update).
May have logic to respond to client background requests (process).
 The name in parenthesis at the end of each bullet is the corresponding method in HxView responsible for that behavior.
 Details on each method can be found below. The HxProfile is responsible for building the containing HTML around each
 HxView.

### Example
 The details of each method can be found at the end of this document, but lets take a simple example to walk through the
 API:

```java
  public class BFooView extends BHxView
  {
    public static final BFooView INSTANCE = new BFooView();
Niagara Developer Guide
8/26/2015
103

============================================================
PAGE 106
============================================================

    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BFooView.class);
    protected BFooView() {}
    public void write(HxOp op) throws Exception
    {
      BFoo foo = (BFoo)op.get();
      HtmlWriter out = op.getHtmlWriter();
      out.w("Current name: ").w(foo.getName()).w("<br/>");
      out.w("<input type='text' name='").w(op.scope("name")).w("'");
      out.w(" value='").w(foo.getName()).w("' /><br/>");
      out.w("<input type='submit' value='Submit' />");
    }
    public BObject save(HxOp op) throws Exception
    {
      BFoo foo = (BFoo)op.get();
      foo.setName(op.getFormValue("name"));
      return foo;
    }
  }

  // Register this view on BFoo in module-include.xml
  <type name="FooView" class="bar.BFooView">
    <agent><on type="bar:Foo"/></agent>
  </type>
```

 Assuming the current name in our BFoo object is "Peter Gibbons", the above code will produce the following HTML
 (ignoring the profile):

```html
  Current name: Peter Gibbons<br/>
  <input type='text' name='name' value='Peter Gibbons' /><br/>
  <input type='submit' value='Submit' />
```

 If you are familiar with Niagara AX and HTML, this should be pretty straightforward. Let's walk through it though.
 Here's the class heirarchy of the main hx components:
 The first thing to note is that BHxView extends BServletView, which extends BSingleton, which requires a
 public static final INSTANCE variable for all concrete implementations. If you have ever programmed Servlets
Niagara Developer Guide
8/26/2015
104

============================================================
PAGE 107
============================================================

 before, you'll know that a Servlet must be re-entrant, and HxViews follow the same model. The INSTANCE object is
 what will be used to handle requests. (The protected constructor is a convention used to enforce the singleton design
 pattern). Since HxView can't maintain state while its doing its thing, we need to stash stuff somewhere - thats where
 HxOp comes in. We won't get into the details of HxOp yet, just be aware that anything I need to know about my current
 request or response is very likely accessible via the op.

### Producing the HTML
 Let's move on to the lifecycle of an HxView. The first thing a view will do is render its HTML to the client. This occurs
 in the write() method. By default Hx documents use the XHTML 1.0 Strict DOCTYPE. So you are encouraged to use
 valid XHTML in your write method. Since HxViews are designed to be chromable, and compositable, you'll also only
 write the markup that directly pertains to this view in your write method. Here are the things you should take note of
 about write:
Think BWbView.doLoadValue()
Only write the HTML that directly pertains to this view.
You should always use op.scope() when writing a form element name. We'll get to why you should do that in
 'Writing Reusable HxViews' down below.
This is just a plain old HTML form, so all the normal form elements are applicable. And of course any other
 HTML.
Just like a plain HTML file, <input type='submit' /> is used to submit changes back to the server. The Hx
 framework will take care of building the form tag so this request gets routed to your save() method.

### Saving Changes
 Ok, my name is not "Peter Gibbons", so we need to be able to save something else back to the station. This is just as easy
 as writing my HTML, you simply implement a save method on your view. The request will automatically be routed, and
 all form data will be available from the HxOp.getFormValue() method.
 So now if I view our example view in my browser, enter "Michael Bolton" and hit "Submit", the page will refresh to
 display:

```html
  Current name: Michael Bolton<br/>
  <input type='text' name='name' value='Michael Bolton' /><br/>
  <input type='submit' value='Submit' />
```

 Technically, what happens, is the POST request gets routed to save, then Hx responds with a redirect back the same
 location. This forces the page contents to be requested on a GET request, avoiding double-posting problems.

### Writing Reusable HxViews
 Hx is designed to allow for reusing and nesting HxViews within other HxViews. To accomplish this, we need some type
 of scoping mechanism to avoid naming conflicts. Luckily, Hx handles this quite cleanly. Each HxOp that gets created has
 a name (explicit or auto-generated), which when combined with its parent tree, creates a unqiue path to each "instance"
 of a HxView. So if you take the this code:

```java
  public void write(HxOp op) throws Exception
  {
    HtmlWriter out = op.getHtmlWriter();
    out.w("<input type='text' name='").w(op.scope("foo")).w("'/>");
    ...
  }

  public BObject save(HxOp op) throws Exception
  {
    String foo = op.getFormValue("foo");
    ...
  }
```
Niagara Developer Guide
8/26/2015
105

============================================================
PAGE 108
============================================================

 The resulting HTML could look something like this:

```html
  <input type='text' name='uid1.editor.uid5.foo'/>
```

 HxOp.getFormValue() will automatically handle the "unscoping" for you. This allows any HxView to be nested
 anywhere without knowing its context. However, this only works if you follow a few rules:
Always give sub-views a sub-op using HxOp.make() - there should always be a 1:1 ratio between HxOps and
 HxViews. See "Managing Subviews" below.
Always use HxOp.scope() when writing the name attribute for a form control.
When using the auto name constructor of HxOp, names are created by appending the current counter value to a
 string ("uid0", "uid1", etc). So its very important that the order in which HxOps are created is always the same in
 write/save/update/process so the produced paths will always the same. Otherwise views will not be able to correctly
 resolve their control values.

### Managing Subviews
 Hx does not contain any notion of containment, so composite views are responsible for routing all
 write/save/update/process requests to its children:

```java
  public class BCompositeView extends BHxView
  {
    public void write(HxOp op) throws Exception
    {
      BSubView.INSTANCE.write(makeSubOp(op));
      ...
    }

    public BObject save(HxOp op) throws Exception
    {
      BFoo foo = BSubView.INSTANCE.write(makeSubOp(op));
      ...
    }

    public void update(HxOp op) throws Exception
    {
      BSubView.INSTANCE.update(makeSubOp(op));
    }

    public boolean process(HxOp op) throws Exception
    {
      if (BSubView.INSTANCE.process(makeSubOp(op)))
        return true;
      return false;
    }

    private HxOp makeSubOp(HxOp op)
    {
      BFoo foo;
      ...
      return op.make(new OrdTarget(op, foo));
    }
  }
```

 Don't forget to always create a sub-op to your child views so the Hx framework can strut its stuff.

### Writing HxView Agents for WbViews
Niagara Developer Guide
8/26/2015
106

============================================================
PAGE 109
============================================================

 Another feature of the hx framework is a transparent transition from the Workbench environment to the hx
 environment. For example, if you have created a WbView called WbFooView, all references to that view can be made to
 transparently map to your hx implementation. You just need to register your HxView directly on the WbView, and
 expect the input argument to be the same type as loaded into the WbView:

```xml
  // Register WbView agents directly on the View
  <type name="HxFooView" class="foo.BHxFooView">
    <agent><on type="foo:WbFooView"/></agent>
  </type>
```

```java
  public void write(HxOp op)
    throws Exception
  {
    // Assume object is what the corresponding WbView would
    // receive in its doLoadValue() method.
    BFoo foo = (BFoo)op.get();
    ...
  }
```

 Then this ord will work correctly in both enviornments:
  `station:|slot:/bar|view:foo:WbFooView`
 Also note that if your view is the default view on that object, the default ord will choose the correct view as well:
  `station:|slot:/bar`

### Writing HxView Agents for Px Widgets
 Similar to creating WbView agents, a BHxPxWidget is responsible for creating an hx representation of a BWidget used
 in a Px document. Note that BHxPxWidget works differently from a typical HxView in that op.get() will return the
 BWidget that this agent is supposed to model. The widget will already be mounted in a BComponentSpace and any
 bindings will already be active and leased. Also note that in module-include.xml this type should be registered as
 agent on the BWidget it is supposed to model.

```xml
  // Register PxWidget agents directly on the Widget
  <type name="HxPxLabel" class="com.tridium.hx.px.BHxPxLabel">
    <agent><on type="bajaui:Label"/></agent>
  </type>
```

```java
  public void write(HxOp op)
    throws Exception
  {
    // OrdTarget is the widget we want to model
    BLabel label = (BLabel)op.get();

    HtmlWriter out = op.getHtmlWriter();
    out.w(label.getText());
  }
```

### Uploading Files with Multi-part Forms
 Hx supports file uploading by using the multi-part encoding for form submission. This capability is only supported for
 standard form submissions. You may upload one or more files along side the rest of your form.
The files are stored in a
 temporary location on the station, and if not moved, will be automatically deleted at the end of the request.
Niagara Developer Guide
8/26/2015
107

============================================================
PAGE 110
============================================================

Must call op.setMultiPartForm() to change form encoding.
Uploaded files are accessible from op.getFile(), where the control name designates which file you want.
If the file is not explicity moved to another location, it will be deleted at the end of the request.
 Let's take an example:

```java
  public void write(HxOp op) throws Exception
  {
    op.setMultiPartForm();
    out.w("<input type='file' name='someFile' />");
  }

  public BObject save(HxOp op) throws Exception
  {
    BIFile file = op.getFile("someFile");
    FilePath toDir = new FilePath("^test");
    BFileSystem.INSTANCE.move(file.getFilePath(), toDir, op);
    return op.get();
  }
```

 This code will upload a file to a temporary file, accessible as "someFile", and move it to another location so that it will not
 be deleted at the end of the request.

### HxView Methods in Detail

#### write
 Write is used to output the HTML markup for the current view. HxViews should only write the markup that directly
 pertains to itself. Avoid writing any containing markup - this is handled by the parent view or the profile. Especially avoid
 writing outer tags like html, head, and body - these are handled by the profile.
 There is only one form tag in an hx page, and is written by the profile. HxViews should never write their own form
 blocks. So by design, the entire page content is encoded for save and Events. Naming collisions are handled
 automatically using the HxOp scoping rules (see 'Writing Reusable HxViews' above for more info on scoping).
 The write method is always called on an HTTP GET request. However, if its written correctly (which typically means
 escaping quotes properly), it may be reused by update or process if it makes sense.

#### save
 Save is used to save changes made from the view back to the target object. This is usually just a standard response to a
 form post, where the form values are accessed using
HxOp.getFormValue(). Views on BSimples should return a
 new instance based on their new value. Views on BComponents should modify the existing instance and return that
 instance.
 After a save is handled, a redirect is sent back to the browser to the current location. This is used to refresh the current
 page values, but more importantly to avoid double-posting problems. Content is always be requested on a GET request
 (and handled by write). You may choose to redirect back to a different URL using the HxOp.setRedirect()
 method.
 The save method is always called on a standard HTTP POST form submit request. Both standard url-encoded and
 multi-part forms are supported. See 'Uploading Files with Multi-part Forms' above for info on multi-part forms.

#### update
 Update is automatically called periodically on all views if at least one view was marked as dynamic (via HxOp). This is a
 background request made using JavaScript and XmlHttp. The content returned to the browser must be executable
 JavaScript. For example:
Niagara Developer Guide
8/26/2015
108

============================================================
PAGE 111
============================================================

```java
  public void write(HxOp op) throws Exception
  {
    op.setDynamic();
    HtmlWriter out = op.getHtmlWriter();
    out.w("<div id='time'>Current Time</div>");
  }

  public void update(HxOp op) throws Exception
  {
    HtmlWriter out = op.getHtmlWriter();
    out.w("var elem = document.getElementById('time');");
    out.w("elem.innerHTML = '").w(BAbsTime.now()).w("';");
  }
```

 Here, after the page is initially written, the browser will poll the station every five seconds running update on all the
 views. So this code will simply update the current time each time the station is polled.

#### process
 Process is used to handle non-update background requests. A process request is targeted and serviced by a single
 HxView. The default implementation for process handles routing events to the correct view. See Events.
 Note: If you override process, you must call super or event routing will fail.
Niagara Developer Guide
8/26/2015
109

============================================================
PAGE 112
============================================================

## Hx - HxOp

### HxOp
 HxOp maintains all the state for the current request, and provides the interface for creating and consuming a document.
 The original HxOp wraps the WebOp for the current request. Sub-views should be given a new HxOp from the current op
 via the HxOp.make() method. See 'Writing Reusable HxViews' in HxView.
 Note: There should always be a one-to-one mapping of HxOps to HxViews.

### WebOp API
### HxOp API
### Servlet API
Niagara Developer Guide
8/26/2015
110

============================================================
PAGE 113
============================================================

## Hx - HxProfile

### HxProfiles
 The BHxProfile is used to customize the HTML page around the current HxView. The profile is responsible for
 writing out the outer HTML tags ( html, head, and body), any custom markup, and the current view. It is important
 that your profile respect the order HxOps are created in these methods: writeDocument, updateDocument,
 processDocument, and saveDocument. If any HxView uses the auto name constructor of HxOp to create a unique
 path name, it must be called in the exact same order in order to resolve correctly.
HxProfile exposes customization hooks through convenience methods, so there is no need to handle the boilerplate
 code:

```java
  public class BMyProfile
    extends BHxProfile
  {
    public static final BMyProfile INSTANCE = new BMyProfile();

    public Type getType() { return TYPE; }
    public static final Type TYPE = Sys.loadType(BMyProfile.class);

    protected BMyProfile() {}

    public void doBody(BHxView view, HxOp op)
      throws Exception
    {
      BHxPathBar.INSTANCE.write(makePathBarOp(op));
      view.write(op);
      displayError(op);
    }

    public void updateDocument(BHxView view, HxOp op)
      throws Exception
    {
      BHxPathBar.INSTANCE.update(makePathBarOp(op));
      view.update(op);
    }

    public boolean processDocument(BHxView view, HxOp op)
      throws Exception
    {
      if (BHxPathBar.INSTANCE.process(makePathBarOp(op)))
        return true;
      return view.process(op);
    }

    public void saveDocument(BHxView view, HxOp op)
      throws Exception
    {
      BHxPathBar.INSTANCE.save(makePathBarOp(op));
      view.save(op);
    }

    protected HxOp makePathBarOp(HxOp op)
    {
      return op.make("pathbar", op);
    }
  }
```
Niagara Developer Guide
8/26/2015
111

============================================================
PAGE 114
============================================================

## Hx - Events

### Events
 Hx uses Events to provide background interaction between the server and the browser. Events always originate from
 the client browser, and must return executable javascript as the response (you are not required to return content). The
 html form is encoded and sent for every event fire, so op.getFormValue() can be used to query the browser page
 state.
Events are implemented on top of the HxView.process method, and therefore use the XmlHttp support
 implemented in the major browsers.
Command extends Event to add some convenience methods and a display name property. By convention Commands are
 triggered by the user (maybe by clicking on a button), while Events are triggered programmatically. Though in reality
 they are interchangeable.
 Note: javax.baja.hx.Command is not the same class as the javax.baja.ui.Command.
Niagara Developer Guide
8/26/2015
112

============================================================
PAGE 115
============================================================

## Hx - Dialogs

### Dialogs
 Support for modal dialog boxes is provided with Dialog and is typically used from an Command:

```java
  class EditCommand extends Command
  {
    public EditCommand(BHxView view)
    {
      super(view);
      dlg = new EditDialog(this);
    }

    public void handle(HxOp op) throws Exception
    {
      if (!dlg.isSubmit(op)) dlg.open(op);
      else
      {
        String name = op.getFormValue("name");
        String age  = op.getFormValue("age");

        BDude dude = (BDude)op.get();
        dude.setName(name);
        dude.setAge(Integer.parseInt(age));

        refresh(op);
      }
    }

    private EditDialog dlg;
  }

  class EditDialog extends Dialog
  {
    public EditDialog(Command handler) { super("Edit", handler); }
    protected void writeContent(HxOp op) throws Exception
    {
      BDude dude = (BDude)op.get();
      HtmlWriter out = op.getHtmlWriter();

      out.w("<table>");
      out.w("<tr>");
      out.w(" <td>Name</td>");
      out.w(" <td><input type='text' name='").w(op.scope("name"));
      out.w("' value='").w(dude.getName()).w("'/></td>");
      out.w("</tr>");
      out.w("<tr>");
      out.w(" <td>Age</td>");
      out.w(" <td><input type='text' name='").w(op.scope("age"));
      out.w("' value='").w(dude.getAge()).w("'/></td>");
      out.w("</tr>");
      out.w("</table>");
    }
  }
```
Niagara Developer Guide
8/26/2015
113

============================================================
PAGE 116
============================================================

## Hx - Theming

### Theming
 All styling in hx is handled with CSS. The core colors and fonts are defined in
 module://hx/javax/baja/hx/default.css. In order for your view to use the default theme, you should write
 your markup like this:

```html
  <div class="controlShadow-bg myCustomClass" style="...">
  ...
  </div>
```

 This order is important. The default class should always come first in the selector list, and before any style tag (though
 you should avoid using style directly in your view) - so that styles are overridden correctly.
 Note: HxProfiles that override the theme should always place their custom stylesheet last to make sure it overrides
 any stylesheets loaded during the write() phase.
Niagara Developer Guide
8/26/2015
114
