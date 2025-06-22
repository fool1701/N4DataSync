/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.io.*;
import java.util.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;
import javax.baja.io.*;

/**
 * BWsAnnotation is a Niagara specific BSimple designed to 
 * store the WireSheet annotations for a component's 
 * placement on the WireSheet.
 *
 * <pre>
 * BNF:
 *   annotation  := boundsBlock
 *   boundsBlock := p "," q "," wixelWidth [ "," wixelHeight ]
 *   pinnedBlock := name { "," name }*
 * </pre>
 *
 * @author    Brian Frank
 * @creation  2 Aug 00
 * @version   $Revision: 7$ $Date: 7/30/08 10:53:54 AM EDT$
 * @since     Niagara 3.0
 */
@NiagaraType
@NoSlotomatic
public final class BWsAnnotation
  extends BSimple
{   

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make with p, q.
   */
  public static BWsAnnotation make(int p, int q)
  {
    return new BWsAnnotation(p, q, DEFAULT.wixelWidth, DEFAULT.wixelHeight);
  }

  /**
   * Make with p, q, and wixelWidth.
   */
  public static BWsAnnotation make(int p, int q, int wixelWidth)
  {
    return new BWsAnnotation(p, q, wixelWidth, DEFAULT.wixelHeight);
  }

  /**
   * Make with p, q, wixelWidth, wixelHeight.
   */
  public static BWsAnnotation make(int p, int q, int wixelWidth, int wixelHeight)
  {
    return new BWsAnnotation(p, q, wixelWidth, wixelHeight);
  }

  /**
   * Merge two annotations together.
   */
  public static BWsAnnotation merge(BWsAnnotation a1, BWsAnnotation a2)
  {                          
    if (a1 == null && a2 == null) return DEFAULT;       
    if (a1 == null) return a2;
    if (a2 == null) return a1;

    int w = Math.max(a1.wixelWidth, a2.wixelWidth);
    int h = Math.max(a1.wixelHeight, a2.wixelHeight);
        
    return new BWsAnnotation(a2.p, a2.q, w, h);
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BWsAnnotation(int p, int q, int wixelWidth, int wixelHeight)
  {
    this.p = p;
    this.q = q;                                                      
    this.wixelWidth = wixelWidth;
    this.wixelHeight = wixelHeight;
    
    StringBuilder s = new StringBuilder();
    s.append(p).append(',').append(q).append(',').append(wixelWidth);
    if (wixelHeight != 0) s.append(',').append(wixelHeight);
      
    this.string = s.toString();
  }
  
  /**
   * Decoder
   */
  private BWsAnnotation(String s)
    throws Exception
  {
    // tokenize blocks
    StringTokenizer blocks = new StringTokenizer(s, "|");
    
    // first block is p, q, pixelWidth
    StringTokenizer st = new StringTokenizer(blocks.nextToken(), ",");
    this.p = Integer.parseInt(st.nextToken());
    this.q = Integer.parseInt(st.nextToken());
    this.wixelWidth = Integer.parseInt(st.nextToken());
    if (st.hasMoreTokens())
      this.wixelHeight = Integer.parseInt(st.nextToken());
    else
      this.wixelHeight = 0;
    
    // ignore rest (could be old pinned slots)
    
    this.string = s;
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  
  
  /**
   * Translate this annotation by pTrans and qTrans.
   */
  public BWsAnnotation translate(int pTrans, int qTrans)
  {
    return new BWsAnnotation(p+pTrans, q+qTrans, wixelWidth, wixelHeight);
  }        

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Hashcode based on annotation coordinates.
   * Added override for this method in Niagara 3.4.
   */
  public int hashCode()
  {
    return p ^ q ^ wixelWidth ^ wixelHeight;
  }
  
  public boolean equals(Object obj)
  {
    if (obj instanceof BWsAnnotation)
    {
      BWsAnnotation x = (BWsAnnotation)obj;
      if (p != x.p) return false;
      if (q != x.q) return false;
      if (wixelWidth != x.wixelWidth) return false;
      if (wixelHeight != x.wixelHeight) return false;
      return true;
    }
    return false;
  }
  
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(string);
  }
  
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }

  @Override
  public String encodeToString()
    throws IOException
  {
    return string;
  }

  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      return new BWsAnnotation(s);
    }
    catch(Exception e)
    {
      throw new BajaIOException(s, e);
    }
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public static final BWsAnnotation DEFAULT = new BWsAnnotation(0, 0, 8, 0);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWsAnnotation.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////      

  /** This is the required property name for an annotation*/
  public static final String PROPERTY_NAME = "wsAnnotation";
  
  /** Logical x wixel grid coordinate */    
  public final int p;
  
  /** Logical y wixel grid coordinate */    
  public final int q;
  
  /** Logical wixel width */    
  public final int wixelWidth;

  /** Logical wixel height */    
  public final int wixelHeight;
  
  private String string;
  
}
