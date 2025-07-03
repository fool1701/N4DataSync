/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.units;

import java.io.*;
import java.util.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.*;

/**
 * BDimension encapsulates a ratio of the seven base SI 
 * units plus an extra currency dimension based on the 
 * dollar.  A dimension is represented as the exponent
 * of each of the eight base components, where 0 indicates
 * an absense of a component.
 *
 * <pre>
 *   dim = [m] [kg] [s] [A] [K] [mol] [cd] [$]
 *   m    = "(" "m" exp ")"
 *   kg   = "(" "kg" exp ")"
 *   sec  = "(" "s" exp ")"
 *   K    = "(" "K" exp ")"
 *   A    = "(" "A" exp ")"
 *   mol  = "(" "Mol" exp ")"
 *   cd   = "(" "cd" exp ")"
 *   $    = "(" "$" exp ")"
 *   exp  = one num
 *   one  = ""
 *   num  = int
 * </pre>
 *
 * @author    Brian Frank
 * @creation  17 Dec 01
 * @version   $Revision: 8$ $Date: 4/23/08 11:54:57 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BDimension
  extends BSimple
{ 

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get a dimension based on the exponents of 
   * each of the seven base SI units.
   */
  public static BDimension make(int meter, int kilogram, int second, int ampere, 
                                int kelvin, int mole, int candela)
  {
    return make(meter, kilogram, second, ampere, kelvin, mole, candela, 0);
  }                                
 
  /**
   * Get a dimension based on the exponents of each of the 
   * seven base SI units plus the dollar for the currency 
   * dimension.
   */
  public static BDimension make(int meter, int kilogram, int second, int ampere, 
                                int kelvin, int mole, int candela, int dollar)
  {
    BDimension dim = new BDimension();
    
    dim.exp0 = ((meter    & 0xFF) << 24) |
               ((kilogram & 0xFF) << 16) |
               ((second   & 0xFF) << 8) |
               ((ampere   & 0xFF) << 0);
               
    dim.exp1 = ((kelvin   & 0xFF) << 24) |
               ((mole     & 0xFF) << 16) |
               ((candela  & 0xFF) << 8) |
               ((dollar   & 0xFF) << 0);
               
    synchronized(cache)
    {
      BDimension dup = cache.get(dim);
      if (dup != null) return dup;
      cache.put(dim, dim);
      return dim;
    }          
  }
  
  private BDimension() {}

  private static final HashMap<BDimension,BDimension> cache = new HashMap<>();
  
////////////////////////////////////////////////////////////////
// Dimension
////////////////////////////////////////////////////////////////

  /**
   * Get the exponent for the meter component of 
   * the dimension.
   */
  public final int getMeter()
  {
    return (byte)((exp0 >> 24) & 0xFF);
  }

  /**
   * Get the exponent for the kilogram component of 
   * the dimension.
   */
  public final int getKilogram()
  {
    return (byte)((exp0 >> 16) & 0xFF);
  }

  /**
   * Get the exponent for the second component of 
   * the dimension.
   */
  public final int getSecond()
  {
    return (byte)((exp0 >> 8) & 0xFF);
  }

  /**
   * Get the exponent for the ampere component of 
   * the dimension.
   */
  public final int getAmpere()
  {
    return (byte)((exp0 >> 0) & 0xFF);
  }

  /**
   * Get the exponent for the kelvin component of 
   * the dimension.
   */
  public final int getKelvin()
  {
    return (byte)((exp1 >> 24) & 0xFF);
  }

  /**
   * Get the exponent for the mole component of 
   * the dimension.
   */
  public final int getMole()
  {
    return (byte)((exp1 >> 16) & 0xFF);
  }

  /**
   * Get the exponent for the candela component of 
   * the dimension.
   */
  public final int getCandela()
  {
    return (byte)((exp1 >> 8) & 0xFF);
  }

  /**
   * Get the exponent dollar for the component of 
   * the dimension.
   */
  public final int getDollar()
  {
    return (byte)((exp1 >> 0) & 0xFF);
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * To string.  This method formats the dimension 
   * using unicode superscript for the exponents.
   */
  @Override
  public String toString(Context cx)
  {
    StringBuilder s = new StringBuilder();
    append(s, "m",   getMeter());
    append(s, "kg",  getKilogram());
    append(s, "s",   getSecond());
    append(s, "A",   getAmpere());
    append(s, "K",   getKelvin());
    append(s, "mol", getMole());
    append(s, "cd",  getCandela());
    append(s, "$",   getDollar());
    return s.toString();
  }
  
  private static void append(StringBuilder s, String sym, int n)
  {
    if (n == 0) return;
    if (s.length() > 0) s.append((char)0xb7);
    s.append(sym);
    if (n > 1)
    {
      if (n == 2) s.append((char)0xb2);
      else if (n == 3) s.append((char)0xb3);
      else s.append(n);
    }
    else if (n < 0)
    {
      s.append(n);
    }
  }
  
  /**
   * BDimension hashcode.
   */
  public final int hashCode()
  {
    return exp0 ^ (exp1 << 1);
  }
  
  /**
   * BDimension equality
   */
  public final boolean equals(Object obj)
  {
    if (obj instanceof BDimension) // issue 11846 
    {
      BDimension x = (BDimension)obj;
      return exp0 == x.exp0 && exp1 == x.exp1;
    }
    return false;
  }

  /**
   * BDimension is serialized using writeUTF(encodeToString()).
   */
  @Override
  public final void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(encodeToString());
  }
  
  /**
   * BDimension is unserialized using decodeFromString(readUTF()).
   */
  @Override
  public final BObject decode(DataInput in)
    throws IOException
  {
    return decodeFromString(in.readUTF());
  }
  
  /**
   * Encode the dimension in its string format.
   */               
  @Override
  public final String encodeToString()
  {
    if (string == null)
    {
      StringBuilder s = new StringBuilder();
      int m = getMeter(); 
      if (m != 0) 
      { 
        s.append('(').append('m'); 
        if (m != 1) s.append(m); 
        s.append(')'); 
      }
      
      int kg = getKilogram(); 
      if (kg != 0) 
      { 
        s.append('(').append('k').append('g'); 
        if (kg != 1) s.append(kg); 
        s.append(')'); 
      }
      
      int sec = getSecond(); 
      if (sec != 0) 
      { 
        s.append('(').append('s'); 
        if (sec != 1) s.append(sec); 
        s.append(')'); 
      }
      
      int A = getAmpere(); 
      if (A != 0) 
      { 
        s.append('(').append('A'); 
        if (A != 1) s.append(A); 
        s.append(')'); 
      }
      
      int K = getKelvin(); 
      if (K != 0) 
      { 
        s.append('(').append('K'); 
        if (K != 1) s.append(K); 
        s.append(')'); 
      }
      
      int mol = getMole(); 
      if (mol != 0) 
      { 
        s.append('(').append("Mol"); 
        if (mol != 1) s.append(mol); 
        s.append(')'); 
      }
      
      int cd = getCandela(); 
      if (cd != 0) 
      { 
        s.append('(').append("cd"); 
        if (cd != 1) s.append(cd); 
        s.append(')'); 
      }
      
      int dol = getDollar(); 
      if (dol != 0) 
      { 
        s.append('(').append('$'); 
        if (dol != 1) s.append(dol); 
        s.append(')'); 
      }
      
      string = s.toString();
    }
    return string;
  }

  /**
   * Decode the dimension in its string format.
   */
  @Override
  public final BObject decodeFromString(String s)
    throws IOException
  {
    try
    {
      int m=0, kg=0, sec=0, A=0, K=0, mol=0, cd=0, dol=0;
      int[] xRet = new int[1];
      
      int x = 0;
      int len = s.length();
      while(x < len)
      {
        if (s.charAt(x++) != '(') throw new Exception();
        int c = s.charAt(x++);
        switch(c)
        {
          case '$': dol = parseExp(s, x, xRet); break;
          case 'c': x++; cd = parseExp(s, x, xRet); break;
          case 'm': m = parseExp(s, x, xRet); break;
          case 'k': x++; kg = parseExp(s, x, xRet); break;
          case 's': sec = parseExp(s, x, xRet); break;
          case 'A': A = parseExp(s, x, xRet); break;
          case 'K': K = parseExp(s, x, xRet); break;
          case 'M': x+=2; mol = parseExp(s, x, xRet); break;
          default: throw new Exception();
        }          
        x = xRet[0];
        if (s.charAt(x++) != ')') throw new Exception();
      }
      
      return make(m, kg, sec, A, K, mol, cd, dol);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
      throw new IOException(s);      
    }
  }
  
  private int parseExp(String s, int x, int[] xRet)
  {
    boolean neg = false;
    
    int c = s.charAt(x);
    if (c == ')') { xRet[0] = x; return 1; }
    if (c == '-') { neg = true; x++; }
    
    int exp = 0;
    while((c = s.charAt(x)) != ')')
    {
      if (c < '0' || c > '9') throw new IllegalArgumentException("not number: " + c);
      exp = exp*10 + (c-'0');
      x++;
    }
    
    xRet[0] = x;
    
    if (neg) return -exp;
    else return exp;
  }

  @Override
  public boolean isNull()
  {
    return this.equals(NULL);
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////
  
  /**
   * The default dimension is the 1.
   */
  public static final BDimension DEFAULT = make(0, 0, 0, 0, 0, 0, 0);
  public static final BDimension NULL = DEFAULT;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDimension.class);
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  //
  // bits  31-24     23-16     15-8      7-0
  //       --------  --------  --------  --------
  // exp0  meter     kilogram  second    ampere
  // exp1  kelvin    mole      candela   dollar
  //
  
  private int exp0;
  private int exp1;
  private String string;
  
}
