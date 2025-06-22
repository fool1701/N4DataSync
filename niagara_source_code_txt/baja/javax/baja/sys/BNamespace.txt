/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A namespace that holds only simple values.
 * <p>
 * Please note, this class is now deprecated and should no longer be used.
 * </p>
 *
 * @author Matthew Giannini
 * @deprecated this class's unique loading and subscription behavior is no longer supported.
 * @since Niagara 4.0
 */
@NiagaraType
@Deprecated
public final class BNamespace extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BNamespace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNamespace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * We don't want namespaces to show up in the nav tree, so always return {@code false}.
   *
   * @return false
   */
  @Override
  public boolean isNavChild()
  {
    return false;
  }

  /**
   * Only allows {@link BSimple} children to be added.
   */
  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    if (!value.isSimple())
    {
      // TODO: add lexicon entry
      //
      throw new LocalizableRuntimeException("baja", "namespace.notSimple");
    }
    super.checkAdd(name, value, flags, facets, context);
  }
}
