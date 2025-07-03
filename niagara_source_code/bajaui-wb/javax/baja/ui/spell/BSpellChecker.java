/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.spell;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.*;
import javax.baja.sys.*;

/**
 * BSpellChecker.
 * 
 * @author    Andy Frank
 * @creation  5 Oct 2005
 * @version   $Revision: 1$ $Date: 11/29/05 11:03:02 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BSpellChecker
  extends BObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.spell.BSpellChecker(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpellChecker.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
  
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Return the first installed SpellChecker or null
   * if no implemenation can be found.
   */
  public static BSpellChecker get()
  {
    if (instance == null)
    {
      TypeInfo[] types = Sys.getRegistry().getConcreteTypes(TYPE.getTypeInfo());
      if (types.length > 0)
        instance = (BSpellChecker)types[0].getInstance();
    }
    return instance;
  }
  private static BSpellChecker instance = null;

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Check the given text for spelling mistakes.  If no
   * mistakes were found, return an empty array.
   */
  public abstract SpellingError[] check(String text);
}
