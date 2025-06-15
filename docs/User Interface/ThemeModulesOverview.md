# Theme Modules Overview

The scope and capabilities of theme modules has been greatly expanded for Niagara 4. This document will describe the
 parts of the UI that are available for theming, the general process of creating a theme module, then provide details and
 tips regarding the individual parts and pieces of a theme.

## Niagara 4 UI Overview
 Many more parts of the UI can have themes applied in Niagara 4.
 bajaui is the Java user interface technology used by all parts of the Workbench UI in Niagara AX. This includes
 things like the Workbench nav tree, Px widgets, wizard dialogs, and any other UI elements carried forward from
 AX Workbench. It is styled using files written in a Niagara-specific syntax called NSS.
 JavaFX is a user interface technology that is distributed as part of Java 8 itself. It has a number of capabilities that
 bajaui does not support, like rounded corners and drop shadows. It is styled using a specialized set of CSS rules. In
 Niagara 4, Workbench uses JavaFX to render certain elements like toolbars and menu buttons.
 Hx is a framework carried forward from Niagara AX. It runs in the station to generate web interfaces using HTML
 and JavaScript.
 bajaux is a new framework in Niagara 4, based on HTML and JavaScript. It is used to create browser-based Niagara
 applications like Property Sheet, Web Chart, and User Manager. It is styled using pure CSS.
 A theme in Niagara 4 consists of a number of different parts, arranged into specified folders.
 src/hx: contains the CSS used to style Hx views, such as the Hx Property Sheet and HxPx graphics.
 src/fx: contains the CSS used to style JavaFX elements, like toolbars and menu buttons.
 src/imageOverrides: contains theme-specific replacements for icons from individual Niagara modules.
 src/nss: contains the NSS files used to style UI elements created with bajaui. NSS syntax in 4.0 is exactly the
 same as it was in AX.
 src/sprite: contains a spritesheet image and CSS file used to minimize the number of network calls necessary to
 retrieve icons in web-based views.
 src/ux: contains the CSS used to style bajaux views, including the Shell Hx profile.

## Creating a theme module
 Although it is possible to create a brand-new theme module from scratch, it will be much easier to select an existing
 theme that comes closest to your desired look, then copy and make modifications to that theme. As part of this tutorial,
 we will use
 themeZebra-ux.jar as a basis, copy and modify it, and save it as themeOkapi-ux.jar (we love our
 striped ungulate mammals here at Tridium).
 The process of creating a theme module is very similar to creating any other Niagara module. The process outlined below
 will result in an exact copy of themeZebra, ready for modification.
Create the folder structure for your module: themeOkapi/themeOkapi-ux/.
Create a src directory in themeOkapi-ux and extract the contents of themeZebra-ux.jar into it.
Included in the src directory are the files cssTemplate.mustache, Gruntfile.js, and package.json.
 These should be moved into the root of themeOkapi-ux, not the src directory. (META-INF can be deleted.)
 Ensure that any remaining references to themeZebra in these files are updated to reference themeOkapi
 instead.
Create a standard themeOkapi-ux.gradle file, just like any other module. Ensure that all necessary files are
 included in the jar task.
Create a module-include.xml file with the following contents, as described in the Workbench Theming
 knowledge base article:

```xml
<defs>
  <def name="themeName" value="themeOkapi" />
</defs>
```
Niagara Developer Guide
8/26/2015
115

============================================================
PAGE 118
============================================================

 Now, you should be able to build and install your theme module, then select it both in your users’ web profiles and in
 Workbench options.

## NSS: Styling bajaui elements and icons
 The NSS syntax and method for overriding icons are unchanged since Niagara AX. This information is documented on
 Niagara Central:
 https://community.niagara-central.com/ord?portal:/blog/BlogEntry/235
 https://community.niagara-central.com/ord?portal:/dev/wiki/Workbench_Theming
 One difference from Niagara AX is that some widgets, such as menus and toolbar buttons, are now rendered using JavaFX
 instead of bajaui. This means that styling menu, menu-item, and other toolbar-related nodes in the NSS will likely have
 no effect.

## JavaFX: Styling menus and toolbars
 In Niagara 4, the Java runtime has been upgraded from 1.4 to 1.8. Java 8 includes a UI framework called JavaFX, which
 includes a number of UI widgets which have been integrated into parts of Workbench.
 JavaFX widgets are styled using a Java-specific dialect of CSS. A reference for JavaFX CSS is available here:
 http://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
 (Oracle’s documentation on JavaFX CSS seems to have a few omissions, like the .context-menu class. We’ve already
 found a number of these missing classes and included them in the Zebra and Lucid themes.)
 Workbench widgets that can be styled using JavaFX include the menu bar, toolbar, and scroll bars. These use the standard
 JavaFX class names: menu, menu-item, tool-bar, scroll-bar. To style these widgets, place a CSS file in your
 theme module at src/fx/theme.css.
 Some common IDs to use in your CSS will include the following. Additional selectors may be added in the future if more
 widgets are converted from bajaui to JavaFX.
 #menu-bar-profile: the topmost Workbench menu bar containing File, Edit, etc.
 #menu-bar-profile-background-container, #menu-bar-profile-foreground-container: wrap
 the upper toolbar in two separate containers for advanced border and shadow effects
 #tool-bar-profile: the topmost Workbench toolbar containing Back, Forwards, and Refresh buttons, etc.
 Foregrounds and backgrounds: primary areas of the UI are wrapped in two separate containers for advanced border
 and shadow effects.
 #menu-bar-profile-background-container, #menu-bar-profile-foreground-container:
 topmost Workbench menu bar
 #view-profile-foreground, #view-profile-background: the pane containing the primary
 Workbench view, such as Property Sheet
 #content-profile-foreground, #content-profile-background: the pane containing all
 Workbench content south of the location bar (this includes the main view, sidebars, and console)
 Example:
Niagara Developer Guide
8/26/2015
116

============================================================
PAGE 119
============================================================

 Niagara Framework > Niagara 4 Theme Module Creation > JavaFxIDs.png
 red: #menu-bar-profile-background-container
 orange: #menu-bar-profile-foreground-container
 yellow: #view-profile-foreground
 green: #view-profile-background
 blue: #content-profile-foreground
 purple: #content-profile-background
 Note that these background colors will show through any elements configured without a background color of their own.

## Hx: Styling classic web views
 Hx views can now be styled on a per-theme basis. The CSS file should go in your theme module at src/hx/theme.css.
 These styles will apply on top of the default Hx styles in default.css.

## bajaux: Styling the new generation of Niagara web views
 bajaux views use a common list of CSS classes, intended to be overridden by themes and to make it easy to apply global
 styles to bajaux widgets.
Niagara Developer Guide
8/26/2015
117

============================================================
PAGE 120
============================================================

 The base set of CSS classes lives in the web module at module://web/rc/theme/theme.css. Most bajaux widgets
 can be styled using these classes. You can view a visual demonstration of the different classes available by going to
 http://localhost/module/web/rc/theme/test.html?theme=Zebra (replacing localhost with the address of your station,
 if needed).
 In order to create a bajaux theme, you can simply redefine these CSS classes in src/ux/theme.css.
 src/ux/theme.css can also contain rules for any kind of bajaux widget, even those that might not use the base set of
 ux classes. There are several examples of this, which you can see in themeZebra’s src/ux/theme.css file, including
 dialogs, Property Sheet, charts, and other widgets. At the moment, adding additional rules to src/ux/theme.css is
 the only way to style these widgets in a theme. Future releases may include additional functions to apply styles in a more
 modular way.

## Developer notes on theming Hx and bajaux web views

### On using LESS
 If you are building a theme using themeZebra as a base, you will notice a folder named less containing a number of
 .less files. LESS is a CSS compiler that brings the power of variables, functions, mixins, math, and other tools to CSS.
 For example, one benefit of using LESS is that we’ve chosen to store a base color palette in palette.less, so that they
 can be easily shared between the Hx and bajaux themes. In fact, you could simply change palette.less with no other
 changes, and instantly apply a new color scheme to both.
 If you choose not to use LESS, you can simply edit src/hx/theme.css and src/ux/theme.css like any other CSS
 file. If you do want to give LESS a try, here are the steps you’ll need to take:
Install Node.js.
Install Grunt by typing npm install -g grunt-cli.
Install PhantomJS by typing npm install -g phantomjs.
In your themeOkapi-ux directory, type npm install.
Still in your themeOkapi-ux directory, type grunt watch:css.
 Now, whenever you save a change to a .less file, it will immediately be compiled into the corresponding
 CSS. Make your changes, hit reload in the browser, and immediately see those changes reflected in your Hx
 or bajaux views.
 To do a one-time compilation, just type grunt less.

### On sprites
 In previous releases, icons were displayed simply by retrieving the individual icon files from the station and displaying
 them as img tags in the browser. On an embedded device, or with HTTPS turned on, minimizing the number of network
 calls becomes critical. So in the new Niagara 4
 bajaux views, icons are now displayed using sprites.
 A sprite is a number of different images, all concatenated together into one large image that forms a kind of mosaic. That
 large image is set as the background of an icon, but offset using CSS so that the particular icon you want is scrolled into
 view. The end result is that you can retrieve the entire icon set for a module using just two network calls: the sprite
 image, and the sprite CSS.
 Due to this enhancement, if your theme module contains images, it
 must also contain a sprite. The sprite image should
 exist at src/sprite/sprite.png and the sprite CSS should be at src/sprite/sprite.css. You may generate
 your sprite using any tools you wish, but both themeZebra and themeLucid contain all the necessary configuration
 files to generate them for you. If you are using a stock theme as a base, you have everything you need.
 A quick overview of the process follows.
 The default Grunt task performs three different steps: sprite, imagemin, and concat. (Simply typing grunt
 will do these three things in order.)
 grunt sprite uses a utility called spritesmith to generate the sprite image and sprite CSS files. These both
 go in src/sprite.
 grunt imagemin will losslessly compress sprite.png to save on space.
Niagara Developer Guide
8/26/2015
118

============================================================
PAGE 121
============================================================

 grunt concat adds the @noSnoop tag to the top of the CSS file. (Tech details: this disables the
 SnoopHtmlWriter in the station, which would otherwise break the direct url references in sprite.css.)
 (themeZebra-ux does not actually contain any image overrides - it relies completely on the contents of the default
 icons-ux module. So themeZebra’s sprite will be empty. For an example of an actual generated sprite, try these steps
 with themeLucid.)

### The structure of a Niagara sprite CSS file, and how it’s loaded into a theme
 (This section is extremely techy. It’s not necessary if all you want to do is create a new theme. Feel free to skip.)
 Each icon in a Niagara module, when packed into a sprite, will have its own specified CSS class referenced in
 sprite.css. Each will look something like this:

```css
.icon-icons-x16-add:before {
  display: inline-block;
  vertical-align: text-top;
  content: '';
  background: url(/module/themeLucid/sprite/sprite.png) -180px -494px;
  width: 16px;
  height: 16px;
}
.icon-icons-x16-add > img { display: none; }
```

 This follows certain conventions relating to sprites in Niagara apps.
 First, the CSS class of the HTML icon element is derived from the ORD of the icon itself. It begins with .icon and
 matches the ORD starting with the module name and omitting the file extension.
 Second, it has an additional CSS rule indicating that any img tags inside of it are to be hidden. Why this rule? Well, for
 bajaux to correctly generate the HTML for the icon, it needs to know whether the icon is already represented in the
 spritesheet or not. If it’s already in sprite.png, it would make no sense to download the actual add.png on top of
 that. But if the icon is not in the sprite, it still needs to be displayed.
 So, a dummy element will be added to the DOM offscreen. It will have both the CSS class .icon-icons-x16-add, and
 an img tag inside of it. If it’s in sprite.css, that CSS rule will hide the img tag, and by checking the display CSS
 property of that img we’ll know if it’s in the sprite or not. Slightly clumsy, but it works.
 The RequireJS module bajaux/icon/iconUtils handles all of this logic and will generate the appropriate icon
 HTML for you.

## Known Limitations

### Incorporating premade widgets to stock themes
 Say you have an HTML widget you’ve already built, completely outside the context of Niagara or bajaux, and you wish to
 port it over as a bajaux widget. You might want to apply one set of CSS rules to your widget for the Zebra theme, and a
 different set for Lucid.
 At the moment, there is no way to do this. You will have to change the HTML structure of your widget so that it uses the
 standard set of ux classes, or else it will appear the same in all the default themes.
 You can still style the widget as you wish using your own custom theme.
Niagara Developer Guide
8/26/2015
119
