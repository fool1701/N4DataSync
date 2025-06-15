# Gx Graphics Toolkit

## Overview
 The gx module defines the graphics primitives used for rendering to a display device. For example there implements for
 "painting" to computer screens and another for "painting" a PDF file. Many of the simple types used in the rest of the
 stack are defined in gx including BColor, BFont, BPen, BBrush, BGeom, and BTransform.
 The gx APIs use a vector coordinate system based on x and y represented as doubles. The origin 0,0 is the top left hand
 corner with x incrementing to the right and y incrementing down. This coordinate system is called the logical coordinate
 space (sometimes called the user space). How the logical coordinate space maps to the device coordinate space is
 environment specific. Usually a logical coordinate maps directly into pixels, although transforms may alter this mapping.

## Color
BColor stores an RGBA color. It's string syntax supports a wide range of formats including most specified by CSS3:
SVG Keywords: the full list of X11/SVG keywords is available by name and also defined as constants on BColor.
 Examples: red, springgreen, navajowhite.
RGB Function: the rgb() function may be used with the red, green, and blue components specified as an integer
 between 0-255 or as a percent 0%-100%. Examples: rgb(0,255,0), rgb(0%,100%,0%).
RGBA Function: the rgba() function works just like rgb(), but adds a fourth alpha component expressed as a float
 between 0.0 and 1.0. Example: rgba(0,100,255,0.5).
Hash: the following hash formats are supported #rgb, #rrggbb, and #aarrggbb. The first two follow CSS rules, and
 the last defines the alpha component using the highest 8 bits. Examples: #0b7, #00bb77, #ff00bb77 (all are
 equivalent).

## Font
BFont is composed of three components:
Name: a font family name stored as a String.
Size: a point size stored as a double.
Style: a set of attributes including bold, italics, and underline.
 The format of fonts is "[italic || bold || underline] {size}pt {name}". Examples: "12pt Times New Roman", "bold 11pt sans-
serif", "italic underline 10pt Arial". The BFont class also provides access to a font's metrics such as baseline, height,
 ascent, descent, and for calculating character widths.

## Brush
 The BBrush class encapsulates how a shape is filled. The gx brush model is based on the SVG paint model. There are four
 types of brushes:
Solid Color: the string format is just a standard color string such as "red"
Inverse: uses an XOR painting mode
Gradients: linear and radial gradients
Image: based on a bitmap image file which may tiled or untiled

## Pen
 The BPen class models how a geometric shape is outlined. A pen is composed of:
Width: as double in logical coordinates
Cap: how a pen is terminated on open subpaths - capButt, capRound, capSquare.

Niagara Developer Guide
8/26/2015
87

============================================================
PAGE 90
============================================================

Join: how corners are drawn - joinMiter, joinRound, joinBevel
Dash: a pattern of doubles for on/off lengths

## Coordinates
 The following set of classes is designed to work with the gx coordinate system. Each concept is modeled by three classes:
 an interface, a mutable class, and an immutable BSimple version that manages the string encoding.

### Point
 The IPoint, Point, and BPoint classes store an x and y location using two doubles. The string format is "x, y". Example
 "40,20", "0.4,17.33".

### Size
 The ISize, Size, and BSize classes store a width and height using two doubles. The string format is "width,height".
 Examples include "100,20", "10.5, 0.5".

### Insets
 The IInsets, Insets, and BInsets classes store side distances using four doubles (top, right, bottom, and right). The string
 format for insets follows CSS margin style: "top,right,bottom,left". If only one value is provided it applies to all four sides.
 If two values are provided the first is top/bottom and the second right/left. If three values are provided the first is top,
 second is right/left, and third is bottom. Four values apply to top, right, bottom, left respectively. Examples "4" expands to
 "4,4,4,4"; "2,3" expands to "2,3,2,3"; "1,2,3" expands to "1,2,3,2".

## Geom
 The geometry classes are used to model rendering primitives. They all following the pattern used with Point, Size, and
 Insets with an interface, mutable class, and immutable BSimple. Geometries can be used to stroke outlines, fill shapes, or
 set clip bounds.

### Geom
 The IGeom, Geom, and BGeom classes are all abstract base classes for the geometry APIs.

### LineGeom
 The ILineGeom, LineGeom, and BLineGeom classes model a line between two points in the logical coordinate system.
 The string format of line is "x1,y1,x2,y2".

### RectGeom
 The IRectGeom, RectGeom, and BRectGeom classes model a rectangle in the logical coordinate system. The string
 format of rectangle is "x,y,width,height".

### EllipseGeom
 The IEllipseGeom, EllipseGeom, and BEllipseGeom classes model a ellipse bounded by a rectangle in the logical
 coordinate space. The string format is "x,y,width,height".

### PolygonGeom
 The IPolygonGeom, PolygonGeom, and BPolygonGeom classes model a closed area defined by a series of line segments.
 The string format of polygon is "x1,y1 x2,y2,...".

### PathGeom
 The IPathGeom, PathGeom, and BPathGeom classes define a general path to draw or fill. The model and string format of
Niagara Developer Guide
8/26/2015
88

============================================================
PAGE 91
============================================================

 a path is based on the SVG path element. The format is a list of operations. Each operation is denoted by a single letter. A
 capital letter implies absolute coordinates and a lowercase letter implies relative coordinates. Multiple operations of the
 same type may omit the letter after the first declaration.
Moveto: move to the specified point. "M x,y" or "m x,y".
Lineto: draw a line to the specified point: "L x,y" or "l x,y".
Horizontal Lineto: draw a horizontal line at current y coordinate: "H x" or "h x".
Vertical Lineto: draw a vertical line at the current x coordinate: "V y" or "v y".
Close: draw a straight line to close the current path: "Z" or "z".
Curveto: draws a Bezier curve from current point to x,y using x1,y1 as control point of beginning of curve and x2,y2
 as control point of end of curve: "C x1,y1 x2,y2 x,y" or "c x1,y1 x2,y2 x,y".
Smooth Curveto: draws a Bezier curve from current point to x,y. The first control point is assumed to be the
 reflection of the second control point on the previous command relative to the current point. "S x2,y2 x,y" or "s
 x2,y2 x,y".
Quadratic Curveto: draws a quadratic Bezier curve from current point to x,y using x1,y1 as control point: "Q x1,y1
 x,y" or "q x1,y1 x,y".
Smooth Quadratic Curveto: draws a quadratic Bezier curve from current point to x,y with control point a
 reflection of previous control point: "T x,y" or "t x,y".
Arc: draws an elliptical arc from the current point to x,y: "A rx,ry x-axis-rotation large-arc-flag sweep-flag x y" or "a
 rx,ry x-axis-rotation large-arc-flag sweep-flag x y".
 Refer to the W3 SVG spec (http://www.w3.org/TR/SVG/) for the formal specification and examples.

## Transform
 Transforms allow a new logical coordinate system to be derived from an existing coordinate system. The gx transform
 model is based on SVG and uses the exact string formatting. BTransform stores a list of transform operations:
Translate: moves the origin by a tx and ty distance.
Scale: scales the coordinates system by an sx and sy size.
Skew: skew may be defined along the x or y axis.
Rotate: rotate the coordinate system around the origin using a degree angle.

## Image
 The BImage class is used to manage bitmap images. Image's are typically loaded from a list of ords which identify a list of
 files (GIF, PNG, and JPEG supported). When more than file is specified, the image is composited using alpha
 transparency from bottom to top (useful for "badging" icons). Images may also be created in memory and "painted" using
 the Graphics API.
 The framework will often load images asynchronsouly in a background thread to maintain performance. Developers
 using BImages directly can poll to see if the image is loaded via the isLoaded() method. Use the sync() method to
 block until the image is fully loaded. Animated GIFs require that the developer call animate() at a frame rate of
 10frames/second. Typically developers should display images using BLabel which automatically handles async loading
 and animation.
 The framework caches images based on their size and how recently they are used. You may use the Image Manager spy
 page to review the current cache.

## Graphics
 Painting to a device is encapsulated by the Graphics class. The primitive paint operations are:
fill(IGeom): filling a geometry involves painting a geometry's interior area with the current brush. Remember a
 brush can be a solid color, a gradient, or texture.
stroke(IGeom): stroking a geometry is to draw its outline or line segments. Stroking uses the current pen to derive
 the "stroke geometry" based on the pen's width and style. Then the interior of the stroke is filled using the current
 brush.
Niagara Developer Guide
8/26/2015
89

============================================================
PAGE 92
============================================================

drawString(): this draws a set of characters to the device. The shape of the characters is derived from the current
 font. The interior of the font is filled with the current brush. Note: we don't currently support stroking fonts like
 SVG.
drawImage(): this draws an bitmap image to the device; the image may be scaled depending on the parameters
 and/or current transform.
 All paint operations perform compositing and clipping. Compositing means that colors are combined as painting occurs
 bottom to top. For example drawing a GIF or PNG file with transparent pixels allows the pixels drawn underneath to
 show through. Alpha transparency performs color blending with the pixels underneath. Clipping is the processing of
 constraining paint operations to a specified geometry. Any pixels from a paint operation which would be drawn outside
 the clip geometry are ignored.
Use the clip() method to clip to a specific region.
 Often it is necessary to save the current state of the graphics to perform a temporary paint operation, and then to restore
 the graphics. An example is to set a clip region, paint something, then restore the original clip. The push() and pop()
 are used to accomplish this functionality by maintaining a stack of graphics state. You should always call pop() in a try
 finally block to ensure that your code cleans up properly.
Niagara Developer Guide
8/26/2015
90
