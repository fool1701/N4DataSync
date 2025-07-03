/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.io.*;
import java.util.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;

import com.tridium.sys.schema.*;

/**
 * BIcon is used to represent the iconic image of an object.
 * Icons are identified via an ord, and their contents are
 * 16x16 raster images, preferably PNG files.
 *
 * @author    Brian Frank
 * @creation  12 Mar 03
 * @version   $Revision: 13$ $Date: 7/30/08 10:53:54 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BIcon
  extends BSimple
{ 

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code make(BOrdList.make(ordList))}
   */
  public static BIcon make(String ordList)
  {
    return make(BOrdList.make(ordList));
  }

  /**
   * Convenience for {@code make(BOrdList.make(ord))}
   */
  public static BIcon make(BOrd ord)
  {
    return make(BOrdList.make(ord));
  }
  
  /**
   * Make an icon for the specified ord list.
   */
  public static BIcon make(BOrdList ordList)
  {
    BIcon icon = cache.get(ordList.encodeToString());
    if (icon == null)
    {
      icon = new BIcon(ordList);
      cache.put(ordList.encodeToString(), icon);
    }
    return icon;
  }

  /**
   * Make an icon for a file found in the standard icons 
   * module.  The ord will be "module://icons/x16/" + fileName.
   */
  public static BIcon std(String fileName)
  {
    return make("module://icons/x16/" + fileName);
  }

  /**
   * Badge the base icon with the badge icon.
   */
  public static BIcon make(BIcon bottom, BIcon top)
  {                                                            
    BOrdList blist = bottom.ordList;
    BOrdList tlist = top.ordList;
    int bsize = blist.size();
    int tsize = tlist.size();
    
    BOrd[] ords = new BOrd[bsize + tsize];
    for(int i=0; i<bsize; ++i) ords[i] = blist.get(i);
    for(int i=0; i<tsize; ++i) ords[i+bsize] = tlist.get(i);
    
    return make(BOrdList.make(ords));
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BIcon(BOrdList ordList)
  {
    this.ordList = ordList;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Get the icon's ord list.
   */
  public BOrdList getOrdList()
  {
    return ordList;
  }         
  
  
////////////////////////////////////////////////////////////////
// Simple
////////////////////////////////////////////////////////////////

  /**
   * Return the ord as a String.
   */
  @Override
  public String toString(Context cx)
  {
    return ordList.toString(cx);
  }        

  /**
   * Hash is based on ord list hash.
   * Added override for this method in Niagara 3.4.
   */
  public int hashCode()
  {
    return ordList.hashCode();
  }
  
  /**
   * Equality is based on ord list equality.
   */
  public boolean equals(Object obj)
  {                    
    if (obj instanceof BIcon)
    {            
      return ordList.equals(((BIcon)obj).ordList);
    }
    return false;
  }
    
  /**
   * Encode using OrdList string format.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(ordList.encodeToString());
  }
  
  /**
   * Decode using OrdList string format.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return make(in.readUTF());
  }
  
  /**
   * Encode using OrdList string format.
   */               
  @Override
  public String encodeToString()
    throws IOException
  {
    return ordList.encodeToString();
  }

  /**
   * Decode using OrdList string format.
   */
  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    return make(s);
  }
  
////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {            
    switch(x)
    { 
      case Fw.GET_IMAGE: return image;
      case Fw.SET_IMAGE: this.image = a; return null;
    }                                                
    return super.fw(x, a, b, c, d);
  }  
  
////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public static final BIcon DEFAULT = new BIcon(BOrdList.NULL);

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIcon.class);  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static HashMap<String, BIcon> cache = new HashMap<>();

  private BOrdList ordList;
  Object image;
}
