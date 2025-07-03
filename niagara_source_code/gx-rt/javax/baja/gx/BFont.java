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
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.FontPeer;
import com.tridium.gx.GxEnv;
import com.tridium.gx.parser.Parser;
import com.tridium.sys.schema.Fw;

/**
 * Font encapsulates a text font and its associated metrics.
 * String format is {@code "[bold] [italic] [underline] <size>pt <name>"}.
 * Examples include {@code "10pt Arial"} or {@code "bold italic 12pt Times New Roman"}.
 *
 * @author    Brian Frank       
 * @creation  30 Dec 02
 * @version   $Revision: 14$ $Date: 11/13/08 4:34:59 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BFont
  extends BSimple
{ 
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////    

  /**
   * Make a font for the specified name and size with a normal style.
   */
  public static BFont make(String name, double size)
  {
    return make(name, size, 0);
  }
  
  /**
   * Make a font for the specified name, size, and style..
   */
  public static BFont make(String name, double size, int style)
  {                         
    // arg checks                  
    if (name == null || (style & ~(BOLD|ITALIC|UNDERLINE)) != 0)
      throw new IllegalArgumentException();
    
    // encode to string key
    StringBuilder s = new StringBuilder();
    if ((style & BOLD) != 0) s.append("bold ");
    if ((style & ITALIC) != 0) s.append("italic ");
    if ((style & UNDERLINE) != 0) s.append("underline ");
    s.append(BDouble.encode(size)).append("pt ");
// NCCB-3549: don't actually resolve the concrete OS font name until peer creation    
//    String validName = GxEnv.get().getValidFontName(name);
//    if (validName != null)
//      name = validName;

    s.append(name);
    String string = s.toString();

    // reuse if already in the cache
    BFont font = cache.get(string);
    if (font == null)
    {                                 
      font = new BFont(name, size, style, string);
      cache.put(string, font);
    }              
    return font;
  }  

  /**
   * Make from a string encoding.  See class header for format.
   */
  public static BFont make(String s)
  {               
    Parser parser = new Parser(s);
    BFont x = parser.parseFont();     
    if (x == null || !parser.isEnd())
      throw new IllegalArgumentException(s);
    return x;
  }                               

  /**
   * Make from an existing font, defining a new style
   */  
  public static BFont make(BFont font, int style)
  {
    return BFont.make(font.getName(), font.getSize(), style);
  }

  /**
   * Make from an existing font, defining a new size
   */  
  public static BFont make(BFont font, double size)
  {
    return BFont.make(font.getName(), size, font.getStyle());
  }
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  
  
  /**
   * Private constructor.
   */
  private BFont(String name, double size, int style, String string)
  {
    this.name   = name;
    this.size   = size;
    this.style  = style;
    this.string = string;
    this.antiAliased = true;
    //this.antiAliased = !AntiAliasException.isException(name, size);
  }

////////////////////////////////////////////////////////////////
// Style
////////////////////////////////////////////////////////////////  

  /**
   * Return the font family name.
   */
  public String getName()
  {
    return name;
  }  
    
  /**
   * Return the font size.
   */
  public double getSize()
  {
    return size;
  }

  /**
   * Return the style bitmask.
   */
  public int getStyle()
  {
    return style;
  }
  
  /**
   * Return the bold style bit.
   */
  public boolean isBold()
  {
    return (style & BOLD) != 0;
  }

  /**
   * Return the italic style bit.
   */
  public boolean isItalic()
  {
    return (style & ITALIC) != 0;
  }

  /**
   * Return the underline style bit.
   */
  public boolean isUnderline()
  {
    return (style & UNDERLINE) != 0;
  }            

  public boolean isAntiAliased()
  {
    return antiAliased;
  }

  
////////////////////////////////////////////////////////////////
// Metrics
////////////////////////////////////////////////////////////////

  /**
   * The standard leading, or interline spacing, is the logical 
   * amount of space to be reserved between the descent of one 
   * line of text and the ascent of the next line. The height metric 
   * is calculated to include this extra space.
   */
  public double getLeading()
  {
    return peer().getLeading();
  }
  
  /**
   * The ascent is the distance from the font's baseline to the top 
   * of most alphanumeric characters. Some characters in the Font 
   * might extend above the font ascent line.
   */
  public double getAscent()
  {
    return peer().getAscent();
  }

  /**
   * The font descent is the distance from the font's baseline to 
   * the bottom of most alphanumeric characters with descenders. 
   * Some characters in the Font might extend below the font 
   * descent line.
   */
  public double getDescent()
  {
    return peer().getDescent();
  }
  
  /**
   * This is the distance between the baseline of adjacent lines of 
   * text. It is the sum of the leading + ascent + descent. Due to 
   * rounding this may not be the same as getAscent() + getDescent() + 
   * getLeading(). There is no guarantee that lines of text spaced 
   * at this distance are disjoint; such lines may overlap if some 
   * characters overshoot either the standard ascent or the standard 
   * descent metric.
   */
  public double getHeight()
  {
    return peer().getHeight();
  }
  
  /**
   * No character extends further above the font's baseline 
   * than this height.
   */
  public double getMaxAscent()
  {
    return peer().getMaxAscent();
  }
  
  /**
   * No character extends further below the font's baseline than 
   * this height.
   */
  public double getMaxDescent()
  {
    return peer().getMaxDescent();
  }
  
  /**
   * Returns the advance width of the specified character in 
   * this Font. The advance is the distance from the leftmost 
   * point to the rightmost point on the character's baseline. 
   * Note that the advance of a String is not necessarily the 
   * sum of the advances of its characters.
   */
  public double width(int c)
  {
    return peer().width(c);
  }

  /**
   * Returns the width of the specified character when it is intended to be
   * displayed using fractional font metrics. When measuring a character for
   * display, ensure you set the {@link Graphics} instance to use fractional
   * font metrics as well - otherwise it may not paint to the measured width.
   * <p>
   * If fractional font metrics are not enabled in the system, this will return
   * the same value as {@link #width(int)}.
   *
   * @param c the character to measure
   * @return the advance width of the character with fractional precision
   * @see Graphics#useFractionalFontMetrics(boolean)
   * @since Niagara 4.12
   */
  public double fractionalWidth(int c)
  {
    if (GxEnv.shouldUseFractionalFontMetrics())
    {
      return fractionalPeer().width(c);
    }

    return width(c);
  }

  /**
   * Returns the total advance width for showing the specified
   * String in this Font. The advance is the distance from the
   * leftmost point to the rightmost point on the string's baseline.
   */
  public double width(String s)
  {
    //sanity check
    if( null == s) return 0;
    return peer().width(s);
  }

  /**
   * Returns the width of the specified string when it is intended to be
   * displayed using fractional font metrics. When measuring a string for
   * display, ensure you set the {@link Graphics} instance to use fractional
   * font metrics as well - otherwise it may not paint to the measured width.
   * <p>
   * If fractional font metrics are not enabled in the system, this will return
   * the same value as {@link #width(String)}.
   *
   * @param s the string to measure
   * @return the advance width of the string with fractional precision
   * @see Graphics#useFractionalFontMetrics(boolean)
   * @since Niagara 4.12
   */
  public double fractionalWidth(String s)
  {
    if (GxEnv.shouldUseFractionalFontMetrics())
    {
      return fractionalPeer().width(s);
    }

    return width(s);
  }
  
  /**
   * Returns the total advance width for showing the specified 
   * String in this Font. The advance is the distance from the 
   * leftmost point to the rightmost point on the string's baseline.
   */
  public double width(char[] data, int off, int len)
  {
    return peer().width(data, off, len);
  }

  /**
   * Returns the width of the specified character array when it is intended to be
   * displayed using fractional font metrics. When measuring a character array for
   * display, ensure you set the {@link Graphics} instance to use fractional
   * font metrics as well - otherwise it may not paint to the measured width.
   * <p>
   * If fractional font metrics are not enabled in the system, this will return
   * the same value as {@link #width(char[], int, int)}.
   *
   * @param data the characters to measure
   * @param off the index of the first character to measure
   * @param len the number of characters to measure
   * @return the advance width of the characters with fractional precision
   * @see Graphics#useFractionalFontMetrics(boolean)
   * @since Niagara 4.12
   */
  public double fractionalWidth(char[] data, int off, int len)
  {
    if (GxEnv.shouldUseFractionalFontMetrics())
    {
      return fractionalPeer().width(data, off, len);
    }

    return width(data, off, len);
  }
    
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  

  /**
   * Return if this instance == NULL
   */
  public boolean isNull()
  {
    return this == NULL;
  }

  /**
   * Get a hashcode for the font.
   */
  public int hashCode()
  {                  
    return string.hashCode();
  }
  
  /**
   * Return if the specified object is an equivalent BFont.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BFont)
    {                      
      return string.equals(((BFont)obj).string);
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
   * Encode to string format.  See class header for format.
   */
  public String encodeToString()
    throws IOException
  {             
    return string;
  }
  
  /**
   * Encode from string format.  See class header for format.
   */
  public BObject decodeFromString(String s)
    throws IOException
  {                 
    return make(s);
  }
                                              
////////////////////////////////////////////////////////////////
// Style
////////////////////////////////////////////////////////////////

  public static final int BOLD      = 0x01;
  public static final int ITALIC    = 0x02;
  public static final int UNDERLINE = 0x04;
                       
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static Hashtable<String, BFont> cache = new Hashtable<>();

  /**
   * The default font is a plain 12 point sans-serif.
   */
  public static final BFont DEFAULT = make("sans-serif", 12);

  /**
   * The null font.
   */
  public static final BFont NULL = new BFont("null", 0, 0, "null");
  static { cache.put("null", NULL); }

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BFont(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFont.class);

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
      case Fw.GET_AWT:
        synchronized (makePeerLock)
        {
          return awtSupport;
        }
      case Fw.SET_AWT:
        synchronized (makePeerLock)
        {
          awtSupport = a;
        }
        return null;
      case Fw.GET_PEER: return peer();
    }
    return super.fw(x, a, b, c, d);      
  }     
  
  /**
   * Get the FontPeer from the default GxEnv.
   */
  FontPeer peer()
  {
    synchronized (makePeerLock)
    {
      if (peer == null)
      {
        peer = GxEnv.get().makeFontPeer(this);
      }
    }
    return peer;
  }

  private FontPeer fractionalPeer()
  {
    synchronized (makePeerLock)
    {
      if (fractionalPeer == null)
      {
        // makeFontPeer will try to reuse a cached AwtFontPeer (the non-fractional
        // one). temporarily wipe the cached one to make sure that makeFontPeer
        // creates us a fresh one.
        Object oldAwt = awtSupport;
        awtSupport = null;
        try
        {
          fractionalPeer = GxEnv.get().makeFontPeer(this, isAntiAliased(), true);
        }
        finally
        {
          awtSupport = oldAwt;
        }
      }
    }
    return fractionalPeer;
  }

//  private static class AntiAliasException
//  {
//    private AntiAliasException(String name, double min, double max)
//    {
//      this.name = name.toLowerCase();
//      this.min = min;
//      this.max = max;
//    }
//    
//    public static boolean isException(String name, double size)
//    {
//      AntiAliasException exception = (AntiAliasException) exceptions.get(name.toLowerCase());
//      if (exception == null)
//        return false;
//      
//      if (size >= exception.min && size < exception.max)
//      {
//        return true;
//      }
//      
//      return false;
//    }
//    
//    private String name;
//    private double min;
//    private double max;
//    
//    private static HashMap exceptions = new HashMap();
//    static
//    {
//      exceptions.put("arial", new AntiAliasException("arial", 9.0, 11.0));
//      exceptions.put("arial bold", new AntiAliasException("arial bold", 9.0, 11.0));
//      exceptions.put("tahoma", new AntiAliasException("tahoma", 9.0, 11.0));
//      exceptions.put("tahoma bold", new AntiAliasException("tahoma bold", 9.0, 11.0));
//      exceptions.put("verdana", new AntiAliasException("verdana", 9.0f, 11.0));
//      exceptions.put("verdana bold", new AntiAliasException("verdana bold", 9.0, 11.0));
//    }
//  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private String name;
  private double size;
  private int style;
  private boolean antiAliased = true;
  private String string;
  private FontPeer peer;
  private FontPeer fractionalPeer;
  private final Object makePeerLock = new Object();
  private Object awtSupport;
}
