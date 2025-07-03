/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Hashtable;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.Array;
import javax.baja.nre.util.IntHashMap;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.parser.Parser;
import com.tridium.sys.schema.Fw;

/**
 * BColor stores a color specification via an alpha, red, 
 * green, and blue component.  BColor supports the standard
 * list of CSS3 and SVG color formats:
 * <pre>
 *   #rgb
 *   #rrggbb
 *   #aarrggbb
 *   rgb(num, num, num)
 *   rgba(num, num, num, alpha)
 *   HTML4 keywords
 *   CSS3/SVG keywords
 * </pre>        
 *   
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 14$ $Date: 12/15/10 11:07:23 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BColor
  extends BSimple
{
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////    

  /**
   * Get a list of the predefined color constants.
   */
  public static BColor[] getConstants()
  {             
    return new Array<>(BColor.class, constants.values()).sort().trim();
  }
  
  /**
   * Get a constant by name or return null.
   */
  public static BColor getConstant(String name)
  {                                        
    return constants.get(TextUtil.toLowerCase(name));
  }

  /**
   * Make a BColor instance with the specified red, green,
   * blue, and alpha components as integers between 0-255.
   */
  public static BColor make(int r, int g, int b, int a)
  {
    return make( ((a & 0xFF) << 24) |
                 ((r & 0xFF) << 16) |
                 ((g & 0xFF) << 8)  |
                 ((b & 0xFF) << 0), 
                 true);
  }

  /**
   * Convenience for <code>make(r, g, b, 255)</code>.
   */
  public static BColor make(int r, int g, int b)
  {
    return make(r, g, b, 255);
  }

  /**
   * Convenience for <code>make(rgb, false)</code>.
   */
  public static BColor make(int rgb)
  {
    return make(rgb, false);
  }

  /**
   * Make a color from an existing color and an alpha 
   * component as an integer between 0-255.
   */
  public static BColor make(BColor color, int a)
  {
    return make( ((a & 0xFF) << 24) | (color.getRGB() & 0xFFFFFF), true);
  }  

  
  /**
   * Make a color with the specified RGB value where bits 24-31 are 
   * alpha, 16-23 are red, 8-15 are green, 0-7 are blue.  If hasAlpha
   * is false then alpha is set to 255.
   */
  public static BColor make(int rgb, boolean hasAlpha)
  {
    if (!hasAlpha) rgb |= 0xff000000;
      
    BColor c = (BColor)cache.get(rgb);
    if (c == null)
    {
      c = new BColor(rgb);
      cache.put(rgb, c);
    }
    return c;
  }

  /**
   * Make a color from its string encoding.
   */
  public static BColor make(String s)
  {                       
    Parser parser = new Parser(s);
    BColor x = parser.parseColor();
    if (x == null || !parser.isEnd())
      throw new IllegalArgumentException(s);
    return x;
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  
  
  /**
   * Private constructor.
   */
  private BColor(int rgb)
  {
    this.rgb = rgb;
  }

  /**
   * Private constructor.
   */
  private BColor(String string, int rgb)
  {
    this.string = string;
    this.rgb = rgb;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Get the red component as an integer from 0-255.
   */
  public int getRed() 
  {
    return(rgb >> 16) & 0xFF;
  }

  /**
   * Get the green component as an integer from 0-255.
   */
  public int getGreen() 
  {
    return(rgb >> 8) & 0xFF;
  }

  /**
   * Get the blue component as an integer from 0-255.
   */
  public int getBlue() 
  {
    return(rgb >> 0) & 0xFF;
  }

  /**
   * Get the alpha component as an integer from 0-255.
   */
  public int getAlpha() 
  {
    return(rgb >> 24) & 0xff;
  }

  /**
   * Get the RGB value where bits 24-31 are alpha, 16-23 are red, 
   * 8-15 are green, 0-7 are blue.
   */
  public int getRGB() 
  {
    return rgb;
  }            
  
  /**
 * Get this color as an HTML string '#rrggbb'.  The
 * alpha component of this color is ignored.
 */
public String toHtmlString()
{
  StringBuilder s = new StringBuilder(9);
  s.append('#');
  s.append( nibbles[(rgb >> 20) & 0x0F ]);
  s.append( nibbles[(rgb >> 16) & 0x0F ]);
  s.append( nibbles[(rgb >> 12) & 0x0F ]);
  s.append( nibbles[(rgb >>  8) & 0x0F ]);
  s.append( nibbles[(rgb >>  4) & 0x0F ]);
  s.append( nibbles[(rgb >>  0) & 0x0F ]);
  return s.toString();
}

  /**
   * Get this color as an HTML5 string that doesn't ignore alpha:
   * 'rgba( r, g, b, a)' where rgba are 0-255 and a is 0 to 1, or fallback to BColor.toHtmlString if alpha is 1
   * @see <a href="https://www.w3.org/TR/css3-color/#rgba-color">CSS3 for rgba</a>
   */
  public String toHtmlStringWithAlpha()
  {
    if(getAlpha() == 255)
      return toHtmlString();

    StringBuilder s = new StringBuilder(9);

    s.append("rgba(");
    s.append( getRed());
    s.append( "," );
    s.append( getGreen());
    s.append( "," );
    s.append( getBlue());
    s.append( "," );
    s.append( ((double)getAlpha())/255d ); //ensure this is a double
    s.append( ")" );
    return s.toString();
  }

  /**
   * Get this color as a solid brush.
   */
  public BBrush toBrush()
  {
    return BBrush.makeSolid(this);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * Return if this is the null color which should 
   * be used to fallback to a context senstive default.
   */
  public boolean isNull()
  {
    return this == NULL;
  }
  
  /**
   * BColor hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    return rgb;
  }
  
  /**
   * Return if the specified object is an equivalent Color.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BColor)
    {
      BColor c = (BColor)obj;
      return this.rgb == c.rgb;
    }
    return false;
  }
  
  /**
   * Serialized using writeUTF() of string encoding.
   */
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF( encodeToString() );
  }
  
  /**
   * Unserialized using readUTF() of string encoding.
   */
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString( in.readUTF() );
  }

  /**
   * If the string is a named constant then return 
   * its name.  If the string has an alpha value of 255
   * then return #rrggbb otherwise #aarrggbb
   */
  public String encodeToString()
  {                       
    if (string == null)
    {
      int rgb = this.rgb;
      StringBuilder s = new StringBuilder(9);
      s.append('#');                        
      if (getAlpha() != 255)
      {
        s.append( nibbles[(rgb >> 28) & 0x0F ]);
        s.append( nibbles[(rgb >> 24) & 0x0F ]);
      }
      s.append( nibbles[(rgb >> 20) & 0x0F ]);
      s.append( nibbles[(rgb >> 16) & 0x0F ]);
      s.append( nibbles[(rgb >> 12) & 0x0F ]);
      s.append( nibbles[(rgb >>  8) & 0x0F ]);
      s.append( nibbles[(rgb >>  4) & 0x0F ]);
      s.append( nibbles[(rgb >>  0) & 0x0F ]);
      string = s.toString();
    }
    return string;
  }
  
  private static final char[] nibbles = 
    { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
      'a', 'b', 'c', 'd', 'e', 'f' };

  /**
   * Read the primitive from String format.  See 
   * class header comments for supported formats.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }

////////////////////////////////////////////////////////////////
// CSS-3, SVG, X11 Constants
////////////////////////////////////////////////////////////////
  
  static IntHashMap cache = new IntHashMap();  // rbg -> color
  static Hashtable<String, BColor> constants = new Hashtable<>();    // constant string -> color
    
  static BColor constant(String name, int rgb) { return constant(name, rgb, false); }
  static BColor constant(String name, int rgb, boolean hasAlpha) 
  {                      
    if (!hasAlpha) rgb |= 0xff000000;
    BColor c = new BColor(name, rgb);
    cache.put(rgb, c);                  
    constants.put(TextUtil.toLowerCase(name), c); 
    return c;
  }

  public final static BColor transparent     = constant("transparent",     0x00ffffff, true);
  public final static BColor aliceBlue       = constant("aliceBlue",       0xf0f8ff);
  public final static BColor antiqueWhite    = constant("antiqueWhite",    0xfaebd7);
  public final static BColor aqua            = constant("aqua",            0x00ffff);
  public final static BColor aquamarine      = constant("aquamarine",      0x7fffd4);
  public final static BColor azure           = constant("azure",           0xf0ffff);
  public final static BColor beige           = constant("beige",           0xf5f5dc);
  public final static BColor bisque          = constant("bisque",          0xffe4c4);
  public final static BColor black           = constant("black",           0x000000);
  public final static BColor blanchedAlmond  = constant("blanchedAlmond",  0xffebcd);
  public final static BColor blue            = constant("blue",            0x0000ff);
  public final static BColor blueViolet      = constant("blueViolet",      0x8a2be2);
  public final static BColor brown           = constant("brown",           0xa52a2a);
  public final static BColor burlyWood       = constant("burlyWood",       0xdeb887);
  public final static BColor cadetBlue       = constant("cadetBlue",       0x5f9ea0);
  public final static BColor chartreuse      = constant("chartreuse",      0x7fff00);
  public final static BColor chocolate       = constant("chocolate",       0xd2691e);
  public final static BColor coral           = constant("coral",           0xff7f50);
  public final static BColor cornflowerBlue  = constant("cornflowerBlue",  0x6495ed);
  public final static BColor cornsilk        = constant("cornsilk",        0xfff8dc);
  public final static BColor crimson         = constant("crimson",         0xdc143c);
  public final static BColor cyan            = constant("cyan",            0x00ffff);
  public final static BColor darkBlue        = constant("darkBlue",        0x00008b);
  public final static BColor darkCyan        = constant("darkCyan",        0x008b8b);
  public final static BColor darkGoldenrod   = constant("darkGoldenrod",   0xb8860b);
  public final static BColor darkGray        = constant("darkGray",        0xa9a9a9);
  public final static BColor darkGreen       = constant("darkGreen",       0x006400);
  public final static BColor darkGrey        = constant("darkGrey",        0xa9a9a9);
  public final static BColor darkKhaki       = constant("darkKhaki",       0xbdb76b);
  public final static BColor darkMagenta     = constant("darkMagenta",     0x8b008b);
  public final static BColor darkOliveGreen  = constant("darkOliveGreen",  0x556b2f);
  public final static BColor darkOrange      = constant("darkOrange",      0xff8c00);
  public final static BColor darkOrchid      = constant("darkOrchid",      0x9932cc);
  public final static BColor darkRed         = constant("darkRed",         0x8b0000);
  public final static BColor darkSalmon      = constant("darkSalmon",      0xe9967a);
  public final static BColor darkSeaGreen    = constant("darkSeaGreen",    0x8fbc8f);
  public final static BColor darkSlateBlue   = constant("darkSlateBlue",   0x483d8b);
  public final static BColor darkSlateGray   = constant("darkSlateGray",   0x2f4f4f);
  public final static BColor darkSlateGrey   = constant("darkSlateGrey",   0x2f4f4f);
  public final static BColor darkTurquoise   = constant("darkTurquoise",   0x00ced1);
  public final static BColor darkViolet      = constant("darkViolet",      0x9400d3);
  public final static BColor deepPink        = constant("deepPink",        0xff1493);
  public final static BColor deepSkyBlue     = constant("deepSkyBlue",     0x00bfff);
  public final static BColor dimGray         = constant("dimGray",         0x696969);
  public final static BColor dimGrey         = constant("dimGrey",         0x696969);
  public final static BColor dodgerBlue      = constant("dodgerBlue",      0x1e90ff);
  public final static BColor firebrick       = constant("firebrick",       0xb22222);
  public final static BColor floralWhite     = constant("floralWhite",     0xfffaf0);
  public final static BColor forestGreen     = constant("forestGreen",     0x228b22);
  public final static BColor fuchsia         = constant("fuchsia",         0xff00ff);
  public final static BColor gainsboro       = constant("gainsboro",       0xdcdcdc);
  public final static BColor ghostWhite      = constant("ghostWhite",      0xf8f8ff);
  public final static BColor gold            = constant("gold",            0xffd700);
  public final static BColor goldenrod       = constant("goldenrod",       0xdaa520);
  public final static BColor gray            = constant("gray",            0x808080);
  public final static BColor green           = constant("green",           0x008000);
  public final static BColor greenYellow     = constant("greenYellow",     0xadff2f);
  public final static BColor grey            = constant("grey",            0x808080);
  public final static BColor honeydew        = constant("honeydew",        0xf0fff0);
  public final static BColor hotPink         = constant("hotPink",         0xff69b4);
  public final static BColor indianRed       = constant("indianRed",       0xcd5c5c);
  public final static BColor indigo          = constant("indigo",          0x4b0082);
  public final static BColor ivory           = constant("ivory",           0xfffff0);
  public final static BColor khaki           = constant("khaki",           0xf0e68c);
  public final static BColor lavender        = constant("lavender",        0xe6e6fa);
  public final static BColor lavenderBlush   = constant("lavenderBlush",   0xfff0f5);
  public final static BColor lawnGreen       = constant("lawnGreen",       0x7cfc00);
  public final static BColor lemonChiffon    = constant("lemonChiffon",    0xfffacd);
  public final static BColor lightBlue       = constant("lightBlue",       0xadd8e6);
  public final static BColor lightCoral      = constant("lightCoral",      0xf08080);
  public final static BColor lightCyan       = constant("lightCyan",       0xe0ffff);
  public final static BColor lightGoldenrodYellow = constant("lightGoldenrodYellow",  0xfafad2);
  public final static BColor lightGray       = constant("lightGray",       0xd3d3d3);
  public final static BColor lightGreen      = constant("lightGreen",      0x90ee90);
  public final static BColor lightGrey       = constant("lightGrey",       0xd3d3d3);
  public final static BColor lightPink       = constant("lightPink",       0xffb6c1);
  public final static BColor lightSalmon     = constant("lightSalmon",     0xffa07a);
  public final static BColor lightSeaGreen   = constant("lightSeaGreen",   0x20b2aa);
  public final static BColor lightSkyBlue    = constant("lightSkyBlue",    0x87cefa);
  public final static BColor lightSlateGray  = constant("lightSlateGray",  0x778899);
  public final static BColor lightSlateGrey  = constant("lightSlateGrey",  0x778899);
  public final static BColor lightSteelBlue  = constant("lightSteelBlue",  0xb0c4de);
  public final static BColor lightYellow     = constant("lightYellow",     0xffffe0);
  public final static BColor lime            = constant("lime",            0x00ff00);
  public final static BColor limeGreen       = constant("limeGreen",       0x32cd32);
  public final static BColor linen           = constant("linen",           0xfaf0e6);
  public final static BColor magenta         = constant("magenta",         0xff00ff);
  public final static BColor maroon          = constant("maroon",          0x800000);
  public final static BColor mediumAquamarine = constant("mediumAquamarine", 0x66cdaa);
  public final static BColor mediumBlue      = constant("mediumBlue",      0x0000cd);
  public final static BColor mediumOrchid    = constant("mediumOrchid",    0xba55d3);
  public final static BColor mediumPurple    = constant("mediumPurple",    0x9370db);
  public final static BColor mediumSeaGreen  = constant("mediumSeaGreen",  0x3cb371);
  public final static BColor mediumSlateBlue = constant("mediumSlateBlue", 0x7b68ee);
  public final static BColor mediumSpringGreen = constant("mediumSpringGreen", 0x00fa9a);
  public final static BColor mediumTurquoise = constant("mediumTurquoise", 0x48d1cc);
  public final static BColor mediumVioletRed = constant("mediumVioletRed", 0xc71585);
  public final static BColor midnightBlue    = constant("midnightBlue",    0x191970);
  public final static BColor mintCream       = constant("mintCream",       0xf5fffa);
  public final static BColor mistyRose       = constant("mistyRose",       0xffe4e1);
  public final static BColor moccasin        = constant("moccasin",        0xffe4b5);
  public final static BColor navajoWhite     = constant("navajoWhite",     0xffdead);
  public final static BColor navy            = constant("navy",            0x000080);
  public final static BColor oldLace         = constant("oldLace",         0xfdf5e6);
  public final static BColor olive           = constant("olive",           0x808000);
  public final static BColor oliveDrab       = constant("oliveDrab",       0x6b8e23);
  public final static BColor orange          = constant("orange",          0xffa500);
  public final static BColor orangeRed       = constant("orangeRed",       0xff4500);
  public final static BColor orchid          = constant("orchid",          0xda70d6);
  public final static BColor paleGoldenrod   = constant("paleGoldenrod",   0xeee8aa);
  public final static BColor paleGreen       = constant("paleGreen",       0x98fb98);
  public final static BColor paleTurquoise   = constant("paleTurquoise",   0xafeeee);
  public final static BColor paleVioletRed   = constant("paleVioletRed",   0xdb7093);
  public final static BColor papayaWhip      = constant("papayaWhip",      0xffefd5);
  public final static BColor peachPuff       = constant("peachPuff",       0xffdab9);
  public final static BColor peru            = constant("peru",            0xcd853f);
  public final static BColor pink            = constant("pink",            0xffc0cb);
  public final static BColor plum            = constant("plum",            0xdda0dd);
  public final static BColor powderBlue      = constant("powderBlue",      0xb0e0e6);
  public final static BColor purple          = constant("purple",          0x800080);
  public final static BColor red             = constant("red",             0xff0000);
  public final static BColor rosyBrown       = constant("rosyBrown",       0xbc8f8f);
  public final static BColor royalBlue       = constant("royalBlue",       0x4169e1);
  public final static BColor saddleBrown     = constant("saddleBrown",     0x8b4513);
  public final static BColor salmon          = constant("salmon",          0xfa8072);
  public final static BColor sandyBrown      = constant("sandyBrown",      0xf4a460);
  public final static BColor seaGreen        = constant("seaGreen",        0x2e8b57);
  public final static BColor seaShell        = constant("seaShell",        0xfff5ee);
  public final static BColor sienna          = constant("sienna",          0xa0522d);
  public final static BColor silver          = constant("silver",          0xc0c0c0);
  public final static BColor skyBlue         = constant("skyBlue",         0x87ceeb);
  public final static BColor slateBlue       = constant("slateBlue",       0x6a5acd);
  public final static BColor slateGray       = constant("slateGray",       0x708090);
  public final static BColor slateGrey       = constant("slateGrey",       0x708090);
  public final static BColor snow            = constant("snow",            0xfffafa);
  public final static BColor springGreen     = constant("springGreen",     0x00ff7f);
  public final static BColor steelBlue       = constant("steelBlue",       0x4682b4);
  public final static BColor tan             = constant("tan",             0xd2b48c);
  public final static BColor teal            = constant("teal",            0x008080);
  public final static BColor thistle         = constant("thistle",         0xd8bfd8);
  public final static BColor tomato          = constant("tomato",          0xff6347);
  public final static BColor turquoise       = constant("turquoise",       0x40e0d0);
  public final static BColor violet          = constant("violet",          0xee82ee);
  public final static BColor wheat           = constant("wheat",           0xf5deb3);
  public final static BColor white           = constant("white",           0xffffff);
  public final static BColor whiteSmoke      = constant("whiteSmoke",      0xf5f5f5);
  public final static BColor yellow          = constant("yellow",          0xffff00);
  public final static BColor yellowGreen     = constant("yellowGreen",     0x9acd32);

  /**
   * The default color is black.
   */
  public static final BColor DEFAULT = black;

  /**
   * The null color doesn't map to a real color.
   * It is a special singleton which should be used
   * to provide a context senstive "fallback".
   */
  public static final BColor NULL = constant("null", 0, true);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BColor(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BColor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {  
    switch(x)
    {      
      case Fw.GET_AWT: return awtSupport;
      case Fw.SET_AWT: awtSupport = a; return null;
    }
    return super.fw(x, a, b, c, d);      
  }     
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private String string;
  private int rgb;
  BBrush brush;
  Object awtSupport;
  
}
