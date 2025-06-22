/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.gx;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

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
 * BTransforms encapsulates a set of Transform operations 
 * which create a new logical coordinate space.
 *
 * <pre> 
 *  transform  := ( translate | scale | rotate | skewX | skeyY )*
 * </pre>
 *
 * @author    Brian Frank on 2 Apr 04
 * @version   $Revision: 7$ $Date: 9/30/08 5:09:01 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BTransform
  extends BSimple
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////    

  /**
   * Make with a list of Transform operations.
   */
  public static BTransform make(Transform... transforms)
  {               
    return new BTransform(transforms);
  }

  /**
   * Make with a single Transform operation.
   */
  public static BTransform make(Transform transform)
  {                                        
    return new BTransform(transform);
  }

  /**
   * Make with a translate transformation.
   */
  public static BTransform makeTranslate(double x, double y)
  {
    return make(new Translate(x, y));
  }

  /**
   * Make with a scale transformation.
   */
  public static BTransform makeScale(double x, double y)
  {
    return make(new Scale(x, y));
  }

  /**
   * Make with a rotate transformation with an angle in degrees.
   */
  public static BTransform makeRotate(double angle)
  {
    return make(new Rotate(angle));
  }
 
  /**
   * Make with a skew x transformation.
   */
  public static BTransform makeSkewX(double angle)
  {
    return make(new SkewX(angle));
  }

  /**
   * Make with a skew y transformation.
   */
  public static BTransform makeSkewY(double angle)
  {
    return make(new SkewY(angle));
  }

  /**
   * Make from a string encoding.  See class header for format.
   */
  public static BTransform make(String s)
  {               
    Parser parser = new Parser(s);
    BTransform x = parser.parseTransform();     
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
  private BTransform(Transform[] transforms)
  {                 
    this.transforms = transforms.clone();
    calcMatrix();
  }

  /**
   * Private constructor.
   */
  private BTransform(Transform transform)
  {
    this.transforms = new Transform[] { transform };
    calcMatrix();
  }              
  
  /**
   * Calculate the transform matrix.
   */
  void calcMatrix()
  {                
    if (transforms.length == 1)
    {                                   
      matrix = transforms[0].toMatrix();
    }
    else
    {
      matrix = new Matrix();
      for(int i=0; i<transforms.length; ++i)
        matrix.multiply(transforms[i].toMatrix(), matrix);
    }
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Get the number of transform steps.
   */
  public int size()
  {
    return transforms.length;
  }

  /**
   * Get the transform step at the specified index.
   */
  public Transform getTransform(int index)
  {
    return transforms[index];
  }

  /**
   * Return the list of transform operations.
   */
  public Transform[] getTransforms()
  {
    return transforms.clone();
  }                    
  
  /**
   * Get the inverse of this tranform.
   */
  public BTransform getInverse()
  {     
    if (inverse == null)
    {   
      Transform[] inverses = new Transform[transforms.length];
      for(int i=0; i<inverses.length; ++i)
        inverses[i] = transforms[transforms.length-i-1].getInverse();
      inverse = make(inverses);
    }
    return inverse;
  }         
  
  /**
   * Transform the specified point and store the result in
   * the specified result point (or if null create new point).
   * Return result point.
   */
  public Point transform(IPoint point, Point result)
  {                        
    if (result == null) result = new Point();                 
    return matrix.transform(point, result);
  }
            
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////  
  
  /**
   * BTransform hash code.
   * 
   * @since Niagara 3.4
   */
  public int hashCode()
  {
    try
    {
      return encodeToString().hashCode();
    }
    catch(Throwable e)
    {
      return System.identityHashCode(this);
    }
  }
  
  /**
   * Return if the specified object is an equivalent BTransform.
   */
  public boolean equals(Object obj)
  {
    if (obj instanceof BTransform)
    {                      
      BTransform x = (BTransform)obj;
      if (transforms.length != x.transforms.length) return false;
      for(int i=0; i<transforms.length; ++i)
        if (!transforms[i].equals(x.transforms[i])) return false;
      return true;
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
    if (string == null)
    {                 
      if (transforms.length == 1)
      {
        string = transforms[0].toString();
      }
      else
      {    
        StringBuilder s = new StringBuilder();
        for(int i=0; i<transforms.length; ++i)
        {
          if (i > 0) s.append(' ');
          s.append(transforms[i]);
        }
        string = s.toString();
      }
    }
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
// Transform
////////////////////////////////////////////////////////////////

  public static final int TRANSLATE = 1;
  public static final int SCALE     = 2;
  public static final int ROTATE    = 3;
  public static final int SKEW_X    = 4;
  public static final int SKEW_Y    = 5;
  
  /**
   * Transform is the base for BTransform transformation operations.
   */
  public static abstract class Transform
  { 
    /**
     * Get a constant for the transform operation.
     */                  
    public abstract int getTransformCase();
    
    /**
     * Get the inverse of the operation.
     */                   
    abstract Transform getInverse();
    
    /**
     * Get the transform as matrix.
     */
    abstract Matrix toMatrix();
  }

////////////////////////////////////////////////////////////////
// Translate
////////////////////////////////////////////////////////////////
  
  /**
   * Translate shifts the coordinate origin.
   */
  public static class Translate extends Transform
  { 
    /**
     * Constructor.
     */                           
    public Translate(double x, double y)
    {
      this.x = x;
      this.y = y;
    }       

    /**
     * Return TRANSLATE
     */                  
    public int getTransformCase() { return TRANSLATE; }
    
    /**
     * Get the distance to translate x origin.
     */
    public double getX() { return x; }
    
    /**
     * Get the distance to translate y origin.
     */
    public double getY() { return y; }
    
    /**
     * Get inverse translate.
     */
    Transform getInverse() { return new Translate(-x, -y); }

    /**
     * Get the transform as matrix.
     */
    Matrix toMatrix()
    {                
      Matrix matrix = new Matrix();
      matrix.e = x;
      matrix.f = y;
      return matrix;
    }
    
    /**
     * Object equality.
     */
    @Override
    public boolean equals(Object obj)
    {                  
      if (obj instanceof Translate)
      {                           
        Translate t = (Translate)obj;
        return x == t.x && y == t.y;
      }
      return false;
    }

    /**
     * Hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    /**
     * String representation.
     */
    @Override
    public String toString()
    { 
      StringBuilder s = new StringBuilder();
      s.append("translate(").append(BDouble.encode(x));
      if (y != 0) s.append(',').append(BDouble.encode(y));
      s.append(')');
      return s.toString();
    }
    
    double x, y;
  }

////////////////////////////////////////////////////////////////
// Scale
////////////////////////////////////////////////////////////////
  
  /**
   * Scale performs a linear scaling of the x and y dimensions.
   */
  public static class Scale extends Transform
  { 
    /**
     * Constructor.
     */                           
    public Scale(double x, double y)
    {
      this.x = x;
      this.y = y;
    }       

    /**
     * Return SCALE
     */                  
    public int getTransformCase() { return SCALE; }
    
    /**
     * Get the x scale factor.
     */
    public double getX() { return x; }
    
    /**
     * Get the y scale factor.
     */
    public double getY() { return y; }

    /**
     * Get inverse scale.
     */
    Transform getInverse() { return new Scale(1.0/x, 1.0/y); }

    /**
     * Get the transform as matix.
     */
    Matrix toMatrix()
    {                
      Matrix matrix = new Matrix();
      matrix.a = x;
      matrix.d = y;
      return matrix;
    }
    
    /**
     * Object equality.
     */
    @Override
    public boolean equals(Object obj)
    {                  
      if (obj instanceof Scale)
      {                           
        Scale t = (Scale)obj;
        return x == t.x && y == t.y;
      }
      return false;
    }

    /**
     * Hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(x, y);
    }

    /**
     * String representation.
     */
    @Override
    public String toString()
    { 
      StringBuilder s = new StringBuilder();
      s.append("scale(").append(BDouble.encode(x));
      if (y != 0) s.append(',').append(BDouble.encode(y));
      s.append(')');
      return s.toString();
    }
    
    double x, y;
  }

////////////////////////////////////////////////////////////////
// Rotate
////////////////////////////////////////////////////////////////
  
  /**
   * Rotate rotates the coordinate system by an angle in degrees.
   */
  public static class Rotate extends Transform
  { 
    /**
     * Constructor with angle in degrees.
     */                           
    public Rotate(double angle)
    {
      this.angle = angle;
    }       

    /**
     * Return ROTATE
     */                  
    public int getTransformCase() { return ROTATE; }
    
    /**
     * Get the angle in degrees.
     */
    public double getAngle() { return angle; }

    /**
     * Get inverse rotate.
     */
    Transform getInverse() { return new Rotate(-angle); }
    
    /**
     * Get the transform as matix.
     */
    Matrix toMatrix()
    {                                        
      double radians = angle/180.0*Math.PI;
      Matrix matrix = new Matrix();
      matrix.a = Math.cos(radians);
      matrix.b = Math.sin(radians);
      matrix.c = -Math.sin(radians);
      matrix.d = Math.cos(radians);
      return matrix;
    }
        
    /**
     * Object equality.
     */
    @Override
    public boolean equals(Object obj)
    {                  
      if (obj instanceof Rotate)
      {                           
        Rotate t = (Rotate)obj;
        return angle == t.angle;
      }
      return false;
    }

    /**
     * Hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(angle);
    }

    /**
     * String representation.
     */
    @Override
    public String toString()
    {
      return "rotate(" + BDouble.encode(angle) + ')';
    }
    
    double angle;
  }

////////////////////////////////////////////////////////////////
// SkewX
////////////////////////////////////////////////////////////////
  
  /**
   * SkewX skews the x dimension by an angle in degrees.
   */
  public static class SkewX extends Transform
  { 
    /**
     * Constructor.
     */                           
    public SkewX(double angle)
    {
      this.angle = angle;
    }       

    /**
     * Return SKEW_X
     */                  
    public int getTransformCase() { return SKEW_X; }
    
    /**
     * Get the angle in degrees.
     */
    public double getAngle() { return angle; }

    /**
     * Get inverse skew X.
     */
    Transform getInverse() { return new SkewX(-angle); }

    /**
     * Get the transform as matix.
     */
    Matrix toMatrix()
    {                
      Matrix matrix = new Matrix();
      matrix.c = Math.tan(angle/180.0*Math.PI);
      return matrix;
    }

    /**
     * Object equality.
     */
    @Override
    public boolean equals(Object obj)
    {                  
      if (obj instanceof SkewX)
      {                           
        SkewX t = (SkewX)obj;
        return angle == t.angle;
      }
      return false;
    }

    /**
     * Hash code.
     */
    @Override
    public int hashCode() {
      return Objects.hash(angle);
    }

    /**
     * String representation.
     */
    @Override
    public String toString()
    {
      return "skewX(" + BDouble.encode(angle) + ')';
    }
    
    double angle;
  }

////////////////////////////////////////////////////////////////
// SkewY
////////////////////////////////////////////////////////////////
  
  /**
   * SkewY skews the y dimension by an angle in degrees.
   */
  public static class SkewY extends Transform
  { 
    /**
     * Constructor.
     */                           
    public SkewY(double angle)
    {
      this.angle = angle;
    }       

    /**
     * Return SKEW_Y
     */                  
    public int getTransformCase() { return SKEW_Y; }
    
    /**
     * Get the angle in degrees.
     */
    public double getAngle() { return angle; }

    /**
     * Get inverse skew Y.
     */
    Transform getInverse() { return new SkewY(-angle); }

    /**
     * Get the transform as matix.
     */
    Matrix toMatrix()
    {                
      Matrix matrix = new Matrix();
      matrix.b = Math.tan(angle/180.0*Math.PI);
      return matrix;
    }

    /**
     * Object equality.
     */
    @Override
    public boolean equals(Object obj)
    {
      if (obj instanceof SkewY)
      {                           
        SkewY t = (SkewY)obj;
        return angle == t.angle;
      }
      return false;
    }

    /**
     * Hash code.
     */
    @Override
    public int hashCode() {
      return Double.hashCode(angle);
    }

    /**
     * String representation.
     */
    @Override
    public String toString()
    {
      return "skewY(" + BDouble.encode(angle) + ')';
    }
    
    double angle;
  }          

////////////////////////////////////////////////////////////////
// Matrix
////////////////////////////////////////////////////////////////
  
  /**
   * Manage a 3x3 matrix which looks like:
   * <pre>
   *   [ a c e ]
   *   [ b d f ]
   *   [ 0 0 1 ]
   * </pre>
   */
  static class Matrix
  {                      
    /**
     * Construct as identity matrix.
     */
    Matrix()
    {                
      a = d = 1;
    }                               
    
    /**     
     * Transform a point:               
     *
     * <pre>
     *   [ x']   [ a  c  e ] [ x ]   [ ax + cy + e ]
     *   [ y'] = [ b  d  f ] [ y ] = [ bx + dy + f ]
     *   [ 1 ]   [ 0  0  1 ] [ 1 ]   [     1       ]
     * </pre> 
     */
    public Point transform(IPoint arg, Point result)
    {
      double x = arg.x();
      double y = arg.y();
      result.x = a*x + c*y + e;
      result.y = b*x + d*y + f;
      return result;
    }
    
    /**
     * Matrix multilication is:
     *                      
     * <pre>
     *   this:       arg:        result:
     *   [ a c e ]   [ m o q ]   [ (am+cn+e0) (ao+cp+e0) (aq+cr+e1) ]
     *   [ b d f ] x [ n p r ] = [ (bm+dn+f0) (bo+dp+f0) (bq+dr+f1) ]
     *   [ 0 0 1 ]   [ 0 0 1 ]   [ (0m+0n+10) (00+0p+10) (0q+0r+11) ]
     * </pre>
     */
    public Matrix multiply(Matrix arg, Matrix result)
    {
      double a=this.a, b=this.b, c=this.c, d=this.d, e=this.e, f=this.f;
      double m=arg.a, n=arg.b, o=arg.c, p=arg.d, q=arg.e, r=arg.f;
      result.a = (a*m+c*n); result.c = (a*o+c*p); result.e = (a*q+c*r+e);
      result.b = (b*m+d*n); result.d = (b*o+d*p); result.f = (b*q+d*r+f);
      return result;
    }            
    
    double a, b, c, d, e, f;
  }
                                                                     
////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  /**
   * The default transform is the identity transform.
   */
  public static final BTransform DEFAULT = make();

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
  /*@ $javax.baja.gx.BTransform(2979906276)1.0$ @*/
  /* Generated Fri Nov 19 12:18:06 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTransform.class);

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
 
  Transform[] transforms;   
  String string;
  BTransform inverse; 
  Matrix matrix;
  Object awtSupport;
   
}
