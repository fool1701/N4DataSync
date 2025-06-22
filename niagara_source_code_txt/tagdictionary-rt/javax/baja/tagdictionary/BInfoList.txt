/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.IllegalNameException;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BInfoList is a base class for {@link BTagDictionary} list containers.
 *
 * @author Andy Saunders
 * @creation 4/18/15
 * @since Niagara 4.0
 */
@NiagaraType
public class BInfoList
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BInfoList(2979906276)1.0$ @*/
/* Generated Tue Jan 25 17:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BInfoList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns the first {@code BTagDictionary} in the ancestor tree for this
   * component.
   *
   * @return the first {@code BTagDictionary} in this component's ancestor tree
   */
  public BTagDictionary getTagDictionary()
  {
    // once found, dictionary is cached for future calls to this method
    if (dictionary != null)
      return dictionary;

    // search up the ancestor tree for the first parent of type BTagDictionary
    BComplex parent = getParent();
    while (parent != null && dictionary == null)
    {
      if (parent.getType().is(BTagDictionary.TYPE))
        dictionary = (BTagDictionary)parent;

      parent = parent.getParent();
    }

    return dictionary;
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Disallows adding child objects when under a frozen {@link BTagDictionary}.
   *
   * @param name name of the child object being added
   * @param value child object being added
   * @param flags {@link Flags} to be added to the child object
   * @param facets {@link BFacets} to be added to the child object
   * @param context execution context
   */
  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    if (checkContext(context))
      return;

    if (isDictionaryFrozen())
      throw new LocalizableRuntimeException("tagdictionary", "frozenDictionary.checkAdd");
  }

  /**
   * Disallows removing slots when under a frozen {@link BTagDictionary}.
   *
   * @param property slot to be removed
   * @param context execution context
   */
  @Override
  public void checkRemove(Property property, Context context)
  {
    if (checkContext(context))
      return;

    if (isDictionaryFrozen())
      throw new LocalizableRuntimeException("tagdictionary", "frozenDictionary.checkRemove");
  }

  /**
   * Disallows renaming slots of a frozen {@link BTagDictionary}.
   *
   * @param property slot to be renamed
   * @param newName new name
   * @param context execution context
   */
  @Override
  public void checkRename(Property property, String newName, Context context)
  {
    if (checkContext(context))
      return;

    if (isDictionaryFrozen())
      throw new IllegalNameException("tagdictionary", "frozenDictionary.checkRename", new String[] {newName});
  }

  /**
   * Checks the context against various conditions.
   *
   * @param context execution context
   * @return {@code true} if the context is {@code null}, if the component is running
   * (see {@link #isRunning()}) and the context is decoding
   * (see {@link Context#decoding}) or commit (see {@link Context#commit}),
   * or if the context is the tag dictionary import context (see
   * {@link BTagDictionary#importContext}); {@code false} otherwise
   */
  boolean checkContext(Context context)
  {
    if (context == null)
      return true;

    //noinspection UnnecessaryParentheses
    if ((!isRunning() && (context.equals(Context.decoding) || context.equals(Context.commit)))
        || context.equals(BTagDictionary.importContext))
      return true;

    return false;
  }

  /**
   * Test if the dictionary is frozen.
   *
   * @return {@code true} if the dictionary is not {@code null} and not frozen
   */
  boolean isDictionaryFrozen()
  {
    BTagDictionary dictionary = getTagDictionary();
    return dictionary != null && dictionary.getFrozen();
  }

///////////////////////////////////////////////////////////
// Icon
///////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    return icon;
  }
  private static final BIcon icon = BIcon.std("folder.png");

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  BTagDictionary dictionary = null;
}
