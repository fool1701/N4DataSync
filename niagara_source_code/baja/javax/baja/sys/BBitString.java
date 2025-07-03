/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.HashMap;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.util.IntHashMap;
import javax.baja.util.Lexicon;

/**
 * BBitString is the super class for simples which are
 * composed of a set of boolean flags usually packed
 * as bits into an integer mask.  Like BEnum, BBitStrings
 * are organized as a set of 32-bit ordinal / String tag 
 * pairs.  Unlike BEnum, the value of a BBitString
 * is determined by each ordinal being set or clear.
 *
 * @author    Brian Frank
 * @creation  11 Apr 02
 * @version   $Revision: 10$ $Date: 3/31/04 9:01:40 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public abstract class BBitString
  extends BSimple
{    

////////////////////////////////////////////////////////////////
// BBitString
////////////////////////////////////////////////////////////////

  /**
   * Return if the bit specified by the given ordinal is set.
   */
  public abstract boolean getBit(int ordinal);

  /**
   * Return if the bit specified by the given tag is set.
   */
  public abstract boolean getBit(String tag);

  /**
   * Get an array enumerating the list of all known
   * ordinal values of this bitstring instance.
   */
  public abstract int[] getOrdinals();
  
  /**
   * Is the specified ordinal value included in this
   * bitstring's range of valid ordinals.
   */
  public abstract boolean isOrdinal(int ordinal);
  
  /**
   * Get the tag identifier for an ordinal value.
   */
  public abstract String getTag(int ordinal);

  /**
   * Get the user readable tag for an ordinal value.
   */
  public abstract String getDisplayTag(int ordinal, Context cx);
  
  /**
   * Get the BBitString instance which maps to the 
   * specified set of ordinal values.
   */
  public abstract BBitString getInstance(int[] ordinals);
  
  /**
   * Return true if the specified tag is contained by the range.
   */
  public abstract boolean isTag(String tag);
  
  /**
   * Get the ordinal associated with the specified tag.
   */
  public abstract int tagToOrdinal(String tag);
  
  /**
   * Return true if the set of bits is all false.
   */
  public abstract boolean isEmpty();

  /**
   * Return the tag used to indicate the empty bit string.
   * This tag is not valid for use by other methods which
   * accept a tag.
   */
  public abstract String getEmptyTag();        
    
////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////  

  /**
   * BBitString.Support is used to aid in creating a 
   * BBitString's int/value pair mapping.
   */
  public static class Support
  {
  
    public Support(BBitString instance)
    {
      this.instance = instance;
    }
  
    public int[] getOrdinals()
    {
      if (ordinals == null)
      {
        int[] temp = new int[byOrdinal.size()];
        IntHashMap.Iterator it = byOrdinal.iterator();
        for(int i=0; it.hasNext(); ++i) { it.next(); temp[i] = it.key(); }
        ordinals = temp;
      }
      int[] copy = new int[ordinals.length];
      System.arraycopy(ordinals, 0, copy, 0, copy.length);
      return copy;
    }
  
    public boolean isOrdinal(int ordinal)
    {
      return byOrdinal.get(ordinal) != null;
    }
  
    public String getTag(int ordinal)
    {
      String t = (String)byOrdinal.get(ordinal);
      if (t == null) throw new IllegalArgumentException(""+ordinal);
      return t;
    }
    
    /**
     * The implementation of this method is to look in the
     * module's lexicon for a key called "typeName.tag".
     */
    public String getDisplayTag(int ordinal, Context cx)
    {                            
      Type type = instance.getType();                       
      BModule module = type.getModule();
      Lexicon lex = Lexicon.make(module, cx);
      return lex.getText(type.getTypeName() + "." + getTag(ordinal));
    }
  
    public boolean isTag(String tag)
    {
      return byTag.get(tag) != null;
    }
    
    public int tagToOrdinal(String tag)
    {
      Integer o = byTag.get(tag);
      if (o == null) throw new IllegalArgumentException(tag);
      return o;
    }
    
    public void add(int ordinal, String tag)
    {
      byOrdinal.put(ordinal, tag);
      byTag.put(tag, ordinal);
    }
    
    private BBitString instance;
    private IntHashMap byOrdinal = new IntHashMap();
    private HashMap<String,Integer> byTag = new HashMap<>();
    private int[] ordinals;
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////  

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBitString.class);
  
  
}
