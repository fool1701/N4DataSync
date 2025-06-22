/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.gx.parser.Parser;
import com.tridium.sys.schema.Fw;

/**
 * Brush encapsulates a type of "paint" used to fill a geometry.
 * 
 * <pre>
 *  brush     := color | inverse | linearGradient | radialGradient | image
 *  color     := any BColor format 
 *  inverse   := inverse( color )
 *  linearGradient := linearGradient ( [spread] [angle] stop* )
 *  radialGradient := radialGradient ( [spread] [center] [radius] [focal] stop* )
 *  image     := image ( source(ord) [tile] [halign] [valign] )
 *  spread    := spread( &lt;pad | reflect | repeat&gt; )
 *  angle     := angle( degrees )
 *  center    := c( offset , offset )
 *  radius    := r( offset )
 *  focal     := f( offset , offset )
 *  stop      := stop( offset color )
 *  offset    := double %              
 *  tile      := tile( &lt;rue | false | x | y&gt;  )
 *  valign    := valign( &lt;top | center | bottom&gt;  )
 *  halign    := halign( &lt;left | center | right&gt;  )
 *
 * Examples:
 *  #0ff
 *  linearGradient(stop(0% white) stop(100% black))
 *  linearGradient( angle(45) stop(0% #fff) stop(100% #0a3) )
 *  radialGradient( c(50%,50%) r(10%) f(20%,20%) spread(reflect) 
 *                  stop(0% red) stop(100% blue) )
 *  image(source(module://icons/x16/object.png))
 *  image( source(module://icons/x16/object.png) tile(true) )
 * </pre>
 *
 * @author    Brian Frank       
 * @creation  2 Apr 04
 * @version   $Revision: 12$ $Date: 9/30/08 5:09:00 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BBrush
  extends BSimple
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////    

  /**
   * Make a solid color brush.
   */
  public static BBrush makeSolid(BColor solid)
  {               
    // cache solid brushes on the color itself
    if (solid.brush == null)
      solid.brush = new BBrush(new Solid(solid));
    return solid.brush;
  }

  /**
   * Make a inverse (XOR) color brush.
   */
  public static BBrush makeInverse(BColor inverse)
  {               
    return new BBrush(new Inverse(inverse));
  }

  /**
   * Make a linear gradient brush with default 
   * pad spread and angle of 0 degrees.
   */
  public static BBrush makeLinearGradient(Stop[] stops)
  {                                                   
    return makeLinearGradient(stops, PAD, 0.0);
  }

  /**
   * Make a linear gradient brush.
   */
  public static BBrush makeLinearGradient(Stop[] stops, int spread, double angle)
  {
    return new BBrush(new LinearGradient(stops, spread, angle));
  }

  /**
   * Make a radial gradient brush with default pad spread, 
   * center 50% 50%, and radius of 50%.
   */
  public static BBrush makeRadialGradient(Stop[] stops)
  {                                                   
    return makeRadialGradient(stops, PAD, BPoint.make(50,50), 50);
  }

  /**
   * Make a radial gradient brush which defaults focal to center.
   */
  public static BBrush makeRadialGradient(Stop[] stops, int spread, IPoint center, double radius)
  {                                                   
    return makeRadialGradient(stops, spread, center, radius, center);
  }

  /**
   * Make a radial gradient brush.
   */
  public static BBrush makeRadialGradient(Stop[] stops, int spread, IPoint center, double radius, IPoint focal)
  {
    return new BBrush(new RadialGradient(stops, spread, center, radius, focal));
  }

  /**
   * Make a image brush with image centered.
   */
  public static BBrush makeImage(BImage image, int tile, int halign, int valign)
  {
    return new BBrush(new Image(image, tile, halign, valign));
  }

  /**
   * Make a image brush with the image untiled and centered.
   */
  public static BBrush makeImage(BImage image)
  {
    return makeImage(image, TILE_FALSE, CENTER, CENTER);
  }
 
  /**
   * Make from a string encoding.  See class header for format.
   */
  public static BBrush make(String s)
  {               
    Parser parser = new Parser(s);
    BBrush x = parser.parseBrush();     
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
  private BBrush(Paint paint)
  {
    this.paint = paint;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Return the paint configuration of this brush.
   */
  public Paint getPaint()
  {
    return paint;
  }  
        
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * Return if this is the null brush.
   */
  public boolean isNull()
  {
    return this == NULL;
  } 
  
  /**
   * BBrush hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    String paintStr = (paint != null)?paint.toString():null;
    return (paintStr != null)?paintStr.hashCode():0;
  }
  
  /**
   * Return if the specified object is an equivalent BBrush.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BBrush)
    {                      
      return paint.equals(((BBrush)obj).paint);
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
    return paint.toString();
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
// Paint
////////////////////////////////////////////////////////////////
  
  /**
   * BBrush.Paint is used to encapsulate a brush's
   * painting style: Solid, Xor, Gradient, or Texture.
   */
  public static abstract class Paint
  {  
    /**
     * Package private
     */
    Paint() 
    {
    }        
    
    /**
     * Get string encoding.
     */
    public abstract String toString();
  } 
  
////////////////////////////////////////////////////////////////
// Solid
////////////////////////////////////////////////////////////////
  
  /**
   * BBrush.Solid represents a solid color paint.
   */
  public static class Solid extends Paint
  {             
    /**
     * Package private
     */
    Solid(BColor color) 
    {
      this.color = color;
    }                    
    
    /**
     * Get the solid color used to paint.
     */
    public BColor getColor()
    {
      return color;
    }       
    
    /**
     * Object equality.
     */
    @Override
    public boolean equals(Object obj)
    {
      return (getClass() == obj.getClass()) &&
              color.equals(((Solid)obj).color);
    }

    /**
     * Hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(color);
    }

    /**
     * String representation.
     */
    @Override
    public String toString()
    {
      return color.encodeToString();
    }
    
    final BColor color;
  }

////////////////////////////////////////////////////////////////
// Inverse
////////////////////////////////////////////////////////////////
  
  /**
   * BBrush.Inverse represents a solid XOR paint. 
   */
  public static class Inverse extends Solid
  {             
    /**
     * Package private
     */
    Inverse(BColor color) 
    {                                      
      super(color);
    }                    
    
    /**
     * Get string encoding.
     */
    public String toString()
    {
      return "inverse(" + color.encodeToString() + ")";
    }
  }

////////////////////////////////////////////////////////////////
// Gradient
////////////////////////////////////////////////////////////////
  
  /** Gradient spread option */
  public static final int PAD     = 1;
  /** Gradient spread option */
  public static final int REFLECT = 2;
  /** Gradient spread option */
  public static final int REPEAT  = 3;
  
  /**
   * BBrush.Gradient is the base class for 
   * LinearGradient and RadialGradient.
   */
  public static abstract class Gradient extends Paint
  {             
    /**
     * Package private
     */
    Gradient(Stop[] stops, int spread) 
    {   
      // arg check                                               
      if (spread != PAD && spread != REFLECT && spread != REPEAT)
        throw new IllegalArgumentException("spread");
        
      this.stops = stops.clone();  // safe copy
      this.spread = spread;
    }                    
    
    /**
     * Get the the list of stops.
     */
    public Stop[] getStops()
    {
      return stops.clone(); // safe copy
    }                      
    
    /**
     * Get the spread constant (PAD, REFLECT, or REPEAT).
     */
    public int getSpread()
    {
      return spread;
    }
    
    /**
     * Object equality.
     */
    public final boolean equals(Object obj)
    {                                
      if (getClass() != obj.getClass()) return false;
      Gradient x = (Gradient)obj;       
      if (spread != x.spread) return false;
      if (stops.length != x.stops.length) return false;
      for(int i=0; i<stops.length; ++i)
        if (!stops[i].equals(x.stops[i])) return false;
      return doEquals(x);
    }        
    abstract boolean doEquals(Gradient x);

    /**
     * Get string encoding.
     */
    public final String toString()
    {             
      if (string == null)
      {
        StringBuffer s = new StringBuffer(64); 
        s.append(getStringStart());
        s.append('(');
        
        // spread (pad is default)
        if (spread == REFLECT) s.append(" reflect");
        else if (spread == REPEAT) s.append(" repeat");

        // subclass hook
        doString(s);
        
        // stops
        for(int i=0; i<stops.length; ++i)
          s.append(' ').append(stops[i]);
        
        s.append(" )");
        string = s.toString(); 
      }              
      return string;
    }
    abstract String getStringStart();
    abstract void doString(StringBuffer s);
        
    final Stop[] stops;
    final int spread;         
    String string;
  }

////////////////////////////////////////////////////////////////
// LinearGradient
////////////////////////////////////////////////////////////////
  
  /**
   * LinearGradient paints a color spectrum along a
   * line using a series of offset/color stops.
   */
  public static class LinearGradient extends Gradient
  {             
    /**
     * Package private
     */                                
    LinearGradient(Stop[] stops, int spread, double angle)
    {
      super(stops, spread);
      this.angle = angle;
    }                    
    
    /**
     * Get the angle in degrees of the gradient stop line.
     * An angle of 0 degrees specifies a horizontal gradient
     * from left (0%) to right (100%).
     */
    public double getAngle()
    {                                           
      return angle;
    }   
    
    boolean doEquals(Gradient g)
    {                          
      LinearGradient x = (LinearGradient)g;
      return angle == x.angle;
    }           
    
    String getStringStart() { return "linearGradient"; }
    void doString(StringBuffer s)
    {
      if (angle != 0.0) s.append(" angle(").append(BDouble.encode(angle)).append(')');
    }
    
    final double angle;
  }

////////////////////////////////////////////////////////////////
// RadialGradient
////////////////////////////////////////////////////////////////
  
  /**
   * RadialGradient paints a color spectrum a circular
   * radiating pattern using a series of offset/color stops.
   */
  public static class RadialGradient extends Gradient
  {             
    /**
     * Package private
     */                                
    RadialGradient(Stop[] stops, int spread, IPoint center, double radius, IPoint focal)
    {
      super(stops, spread);
      this.center = BPoint.make(center);
      this.radius = radius;
      this.focal  = BPoint.make(focal);
    }            
    
    /**
     * Get the center point which together with radius 
     * define the outer most circle of the 100% stop.
     */
    public BPoint getCenter()
    {             
      return center;
    }            
    
    /**
     * Get the radius from center as a percentage of fill geometry.
     */
    public double getRadius()
    {                                           
      return radius;
    }   

    /**
     * Get focal point which defines the 0% stop.
     */
    public BPoint getFocal()
    {                                    
      return focal;
    }
    
    boolean doEquals(Gradient g)
    {                          
      RadialGradient x = (RadialGradient)g;
      return center.equals(x.center) && 
             radius == x.radius &&
             focal.equals(x.focal);
    }           
    
    String getStringStart() { return "radialGradient"; }
    void doString(StringBuffer s)
    {
      if (center.x != 50.0 || center.y != 50.0) 
      {
        s.append(" c(")
         .append(BDouble.encode(center.x)).append("% ")
         .append(BDouble.encode(center.y)).append("%)");
      } 
                         
      if (radius != 50.0)
      {
        s.append(" r(").append(BDouble.encode(radius)).append("%)");
      }
      
      if (focal.x != center.x || focal.y != center.y) 
      {
        s.append(" f(")
         .append(BDouble.encode(focal.x)).append("% ")
         .append(BDouble.encode(focal.y)).append("%)");
      } 
    }
    
    final BPoint center;
    final double radius;
    final BPoint focal;
  }

////////////////////////////////////////////////////////////////
// Stop
////////////////////////////////////////////////////////////////
  
  /**
   * Create a gradient stop with the specified offset and color.
   * The offset is a percentage of the shape being filled.
   */
  public static Stop stop(double offset, BColor color)
  {                
    return new Stop(offset, color);
  }
  
  /**
   * BBrush.Stop is an offset/color stop for a gradient brush.    
   * Create stops using <code>BBrush.stop()</code> factory method.
   */
  public final static class Stop
  { 
    /**
     * Package private
     */
    Stop(double offset, BColor color)
    {                                             
      if (color == null) throw new NullPointerException();
      this.offset = offset;
      this.color  = color;
    }                     
    
    /**
     * Get the offset as a percentage of the geometry being filled.
     */
    public double getOffset()
    {             
      return offset;
    }

    /**
     * Get the color of the stop.
     */
    public BColor getColor()
    {             
      return color;
    }

    /**
     * Object equality.
     */
    @Override
    public boolean equals(Object obj)
    {
      if (obj instanceof Stop)
      {            
        Stop x = (Stop)obj;
        return offset == x.offset && color.equals(x.color);
      }
      return false;                                
    }

    /**
     * Hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(offset, color);
    }

    /**
     * String representation.
     */
    @Override
    public String toString()
    {
      return "stop(" + BDouble.encode(offset) + "% " + color + ")";
    }
    
    final double offset;
    final BColor color;
  }

////////////////////////////////////////////////////////////////
// Texture
////////////////////////////////////////////////////////////////

  /** Image is not tiled */
  public static final int TILE_FALSE = 0;           
  /** Image is tiled in both x and y directions */
  public static final int TILE_TRUE  = 1;           
  /** Image is tiled in x direction only */
  public static final int TILE_X     = 2;           
  /** Image is tiled in y direction only */
  public static final int TILE_Y     = 3;           
  
  /** Image horizontal/vertial align in center */
  public static final int CENTER  = 0;
  /** Image vertial align to top */
  public static final int TOP     = 1;
  /** Image vertial align to top */ 
  public static final int BOTTOM  = 2;
  /** Image horizontal align to left */
  public static final int LEFT    = 3;
  /** Image vertial align to top */
  public static final int RIGHT   = 4;
  
  /**
   * BBrush.Image paints a image with tiling support.
   */
  public static class Image extends Paint
  {             
    /**
     * Package private
     */
    Image(BImage image, int tile, int halign, int valign) 
    {         
      // arg check                                               
      if (image == null) 
        throw new NullPointerException("image");
      if (tile != TILE_TRUE && tile != TILE_FALSE && tile != TILE_X && tile != TILE_Y)
        throw new IllegalArgumentException("tile");
      if (halign != LEFT && halign != CENTER && halign != RIGHT)
        throw new IllegalArgumentException("halign");
      if (valign != TOP && valign != CENTER && valign != BOTTOM)
        throw new IllegalArgumentException("valign");
      
      this.image  = image;
      this.tile   = tile;
      this.halign = halign;
      this.valign = valign;
    }                    
    
    /**
     * Get the image to paint.
     */
    public BImage getImage()
    {
      return image;
    }       

    /**
     * Get the tile constant (TILE_FALSE, TILE_TRUE, TILE_X, TILE_Y).
     */
    public int getTile()
    {
      return tile;
    }

    /**
     * Get the horizontal alignment constant (LEFT, CENTER, RIGHT).
     */
    public int getHalign()
    {
      return halign;
    }

    /**
     * Get the vertical alignment constant (TOP, CENTER, BOTTOM).
     */
    public int getValign()
    {
      return valign;
    }
    
    /**
     * Object equality.
     */
    @Override
    public boolean equals(Object obj)
    {                 
      if (obj instanceof Image)
      {
        Image x = (Image)obj;
        return image.equals(x.image) &&
               tile == x.tile &&
               halign == x.halign &&
               valign == x.valign;
      }                      
      return false;
    }

    /**
     * Hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(image, tile, halign, valign);
    }

    /**
     * String representation.
     */
    @Override
    public String toString()
    {             
      if (string == null)
      {
        StringBuilder s = new StringBuilder();
        String source = image.toString();            
        s.append("image( source(").append(source).append(") ");
        switch(tile)
        {
          case TILE_FALSE: break;
          case TILE_TRUE:  s.append("tile(true) "); break;
          case TILE_X:     s.append("tile(x) "); break;
          case TILE_Y:     s.append("tile(y) "); break;
        }
        switch(halign)
        {
          case LEFT:   s.append("halign(left) "); break;
          case CENTER: break;
          case RIGHT:  s.append("halign(right) "); break;
        }
        switch(valign)
        {
          case TOP:    s.append("valign(top) "); break;
          case CENTER: break;
          case BOTTOM: s.append("valign(bottom) "); break;
        }
        s.append(")");
        string = s.toString();
      }
      return string;
    }
    
    final BImage image;
    final int tile;
    final int halign;
    final int valign;
    String string;
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The null brush.
   */
  public static final BBrush NULL = makeSolid(BColor.NULL);

  /**
   * The default brush is solid black.
   */
  public static final BBrush DEFAULT = makeSolid(BColor.black);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BBrush(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBrush.class);

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
      case Fw.GET_AWT:      return awtSupport;
      case Fw.SET_AWT:      awtSupport = a; return null;
      case Fw.SET_BASE_ORD: setBaseOrd((BOrd)a); return null;
    }
    return super.fw(x, a, b, c, d);      
  }     

  private void setBaseOrd(BOrd baseOrd)
  {
    if (paint instanceof BBrush.Image)
    {
      BImage img = ((BBrush.Image)paint).getImage();
      img.setBaseOrd(baseOrd);
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private Paint paint; 
  private Object awtSupport;
   
}
