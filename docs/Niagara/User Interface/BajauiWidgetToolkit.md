# Bajaui Widget Toolkit

## Overview
 The bajaui module contains a widget toolkit for building rich user interfaces. This toolkit is built using the Niagara
 component model. Widgets are BComponents derived from BWidget. Widgets define basic UI functions:
Layout: defines the layout model - how widget trees are positioned on the graphics device
Painting: defines how widgets paint themselves using graphical composition and clipping
Input: defines how user widgets process user input in the form of mouse, keyboard, and focus events
Data Binding: defines are how widgets are bound to data sources
 Widgets are organized in a tree structure using the standard component slot model. Typically the root of the tree is a
 widget modeling a top level window such as BFrame or BDialog.

## Layout
 All widgets occupy a rectangular geometry called the bounds. Bounds includes a position and a size. Position is a x,y
 point relative to the widget parent's coordinate system. Size is the width and height of the widget. The widget itself
 defines its own logical coordinate system with its origin in the top left corner, which is then used to position its children
 widgets. Every widget may define a preferred size using
computePreferredSize(). Layout is the process of assigning
 bounds to all the widgets in a widget tree. Every widget is given a chance to set the bounds of all its children in the
 doLayout() callback. When a layout refresh is needed, you may call relayout(). The relayout call is asynchronous - it
 merely enqueues the widget (and all its ancestors) for layout at some point in the near future.

### Panes
 Widget's which are designed to be containers for child widgets derive from the BPane class. A summary of commonly
 used panes:
BCanvasPane: used for absolute positioning (discussed below);
BBorderPane: is used to wrap one widget and provide margin, border, and padding similar to the CSS box model.
BEdgePane: supports five potential children top, bottom, left, right, and center. The top and bottom widgets fill the
 pane horizontally and use their preferred height. The left and right widgets use their preferred width, and occupy
 the vertical space between the top and bottom. The center widget gets all the remainder space.
BGridPane: lays out it children as a series of columns and rows. Extra space in the rows and columns is configurable
 a number of ways.
BSplitPane: supports two children with a movable splitter between them.
BTabbedPane: supports any number of children - only one is currently selected using a set of tabs.
BScrollPane: supports a single child that may have a preferred size larger than the available bounds using a set of
 scroll bars.

### Absolute Layout
 Every widget also has a frozen property called layout of type BLayout. The BLayout class is used to store absolute
 layout. Widgets which wish to use absolute layout should be placed in a BCanvasPane. BLayout is a simple with the
 following string format "x,y,width,height". Each value may be a logical coordinate within the parent's coordinate space or
 it may be a percent of the parent's size. Additionally width and height may use the keyword "pref" to indicate use of
 preferred width or height.
Examples include "10,5,100,20" "0,0,30%,100%", and "10%,10%,pref,pref".
 Lastly the keyword "fill" may be used as a shortcut for "0,0,100%,100%" which means fill the parent pane. Fill is the default
 for the layout property which makes it easy to define layers and shapes.

## Painting
 All widgets are given a chance to paint themselves using the paint(Graphics) callback. The graphics is always
Niagara Developer Guide
8/26/2015
91

============================================================
PAGE 94
============================================================

 translated so that the origin 0,0 is positioned at the top, left corner of the widget. The graphic's clip is set to the widget's
 size. Widget's with children, should route to paintChild() or paintChildren(). Painting follows the gx compositing
 rules. Alpha and transparent pixels blend with the pixels already drawn. Widgets are drawn in property order. So the first
 widget is drawn first (at the bottom), and the last widget drawn last (on the top). Note that hit testing occurs in reverse
 order (last is checked first). Effective z-order is reverse of property order (consistent with SVG).

## Input
 User events are grouped into keyboard input, mouse input, and focus events. The following events are defined for each
 group:
BKeyEvent
keyPressed
keyReleased
keyTyped
BMouseEvent
mouseEntered
mouseExited
mousePressed
mouseReleased
mouseMoved
mouseDragged
mouseDragStarted
mouseHover
mousePulse
mouseWheel
BFocusEvent
focusGained
focusLost

## Design Patterns
 Some complicated widgets have mini-frameworks all to their own. These include BTable, BTree, BTreeTable, and
 BTextEditor. All of these widgets use a consistent design pattern based on a set of support APIs:
Model: defines the underlying logical model of the widget visualization
Controller: processes all user input events, handles popup menus, and manages commands
Renderer: responsible for painting the widget
Selection: manages the current selection of the widget

## Commands
 The bajaui module provides a standard API for managing user commands using the Command and ToggleCommand
 classes. Commands are associated with one or more widgets which invoke the command. Typically this association
 happens by using a special constructor of the widget such as BButton(Command cmd) or using a setCommand()
 method. Commands are commonly used with BButton and BActionMenuItem. ToggleCommands are commonly used
 with BCheckBox, BRadioButton, BCheckBoxMenuItem, and BRadioButtonMenuItem.
 Commands provide several functions. First they provide a centralized location to enable and disable the command. It is
 common for a command to be available via a menu item, a toolbar button, and a popup menu. By enabling and disabling
 the command all the widgets are automatically enabled and disabled.
 Commands also provide a standard mechanism used for localization via the lexicon APIs. If one of the module or lexicon
Niagara Developer Guide
8/26/2015
92

============================================================
PAGE 95
============================================================

 constructors is used the command automatically loads its visualization from a lexicon using a naming pattern:
 keyBase+".label", keyBase+".icon", keyBase+".accelerator", and keyBase+".description". The icon value should be an ord to

a 16x16 png file. Widgets created with the Command will automatically set their properties accordingly.
 The Command API also defines the basic framework for undo and redo. Whenever a command is invoked via the
 invoke() method, the Command can return an instance of CommandArtifact to add to the undo stack. Commands
 which don't support undo can just return null.

## Data Binding
 All widgets may be bound to zero or more data sources using the BBinding class. Bindings are added to the widget as
 dynamic child slots. You may use the BWidget.getBindings() to access the current bindings on a given widget.
 Bindings always reference a data source via an ord. The BBinding API defines the basic hooks given to bindings to
 animate their parent widget.
 The most common type of binding is the BValueBinding class. Value binding provides typical functionality associated
 with building real-time graphics. It supports mouse over status and right click actions. Additionally it provides a
 mechanism to animate any property of its parent widget using BConverters to convert the target object into property
 values. Converters are added as dynamic properties using the name of the widget property to animate. For example to
 animate the "text" property of a BLabel you might add a ObjectToString converter to the binding using the
 property name "text".

## Performance Tuning
The gx and bajaui toolkits are built using the AWT and Java2D. A key characteristic of performance is based on how
 widgets are double buffered and drawn to the screen. The following system properties may be used to tune widget
 renderering:
niagara.ui.volatileBackBuffer: Defines whether the back buffer used for widget rendering uses
 createVolatile() or createImage(). Volatile back buffers can take advantage of Video RAM and hardware
 acceleration, non-volatile back buffers are located in system memory. Note: this feature is currently disabled.
sun.java2d.noddraw: Used to disable Win32 DirectDraw. Often disabling DirectDraw can correct problems with
 flickering.
Niagara Developer Guide
8/26/2015
93
