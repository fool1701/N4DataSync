/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

/**
 * Slot is the base interface for the Baja slot types:
 * Property, Action, or Topic.
 *
 * @author    Brian Frank
 * @creation  24 Mar 00
 * @version   $Revision: 31$ $Date: 3/28/05 9:23:14 AM EST$
 * @since     Baja 1.0
 */
public interface Slot
{  

////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////

  /**
   * Get the Type which originally declared this slot.
   */
  public Type getDeclaringType();

  /**
   * Get the programatic name of the slot.  This name is
   * the String key of the slot.
   */
  public String getName();
  
  /**
   * Get the default display name of this Slot.  Display name is
   * resolved against the declaring type's lexicon using
   * the language of context.  If context is null then language
   * defaults to {@code Sys.getLanguage()}.  The lexicon
   * key is the slot name.  If no entry is found in the lexicon
   * then display name defaults to:
   * <p>
   * For frozen slots:
   * {@code TextUtil.toFriendly(getName())}.
   * <p>
   * For dynamic slots:
   * {@code SlotPath.unescape(getName())}.
   */
   public String getDefaultDisplayName(Context context);
  
  /**
   * Frozen slots are the slots which are statically
   * defined by the implementation class.  They are
   * not allowed be modified.
   */
  public boolean isFrozen();

  /**
   * Convenience for {@code !isFrozen()}.
   */
  public boolean isDynamic();
  
  /**
   * Is this slot a Property.
   */
  public boolean isProperty();

  /**
   * Is this slot a Action.
   */
  public boolean isAction();

  /**
   * Is this slot a Topic.
   */
  public boolean isTopic();
  
  /**
   * Get the Slot cast to a Property.  
   * @throws ClassCastException if not an instance of Property.
   */
  public Property asProperty();

  /**
   * Get the Slot cast to a Action.
   * @throws ClassCastException if not an instance of Action.
   */
  public Action asAction();

  /**
   * Get the Slot cast to a Topic.
   * @throws ClassCastException if not an instance of Topic.
   */
  public Topic asTopic();
  
  /**
   * Get the facets which provide additional meta-data
   * about this slot.  If no facets have been specified,
   * then BFacets.NULL is returned.
   */
  public BFacets getFacets();
  
  /**
   * Returns if two slots are equal.  This is usually
   * the same as the == operator, except in the case where
   * a subclass overrides a Slot definition.
   */
  public boolean equals(Object object);
          
////////////////////////////////////////////////////////////////
// Flags
////////////////////////////////////////////////////////////////  

  /**
   * Get the slot's default flags bitmask.
   */
  public int getDefaultFlags();
  
////////////////////////////////////////////////////////////////
// Type Access Constants
////////////////////////////////////////////////////////////////

  public static final int BOOLEAN_TYPE  = 0;
  public static final int INT_TYPE      = 2;
  public static final int LONG_TYPE     = 3;
  public static final int FLOAT_TYPE    = 4;
  public static final int DOUBLE_TYPE   = 5;
  public static final int STRING_TYPE   = 6;
  public static final int BOBJECT_TYPE  = 7;
  
}
