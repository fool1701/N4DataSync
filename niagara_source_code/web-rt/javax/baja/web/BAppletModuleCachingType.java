/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This class enumerates the different options for caching modules when using the
 * applet.
 *
 * <p>
 * The options are:
 * <ul>
 *   <li>host: the client will have a module cache for each host visited</li>
 *   <li>user: the client will generally use a single module cache per user, but
 *       will fall back to the host cache if there's a conflict (e.g. trying to download
 *       a module already in the cache, but that has a different timestamp)</li>
 * </ul>
 *
 * @author Melanie Coggan on 2015-09-11
 * @since Niagara 4.1
 * @deprecated since Niagara 4.13 - will be removed in Niagara 5.0
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("host"),
    @Range("user")
  }
)
@Deprecated
public final class BAppletModuleCachingType
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BAppletModuleCachingType(3966206205)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for host. */
  public static final int HOST = 0;
  /** Ordinal value for user. */
  public static final int USER = 1;

  /** BAppletModuleCachingType constant for host. */
  public static final BAppletModuleCachingType host = new BAppletModuleCachingType(HOST);
  /** BAppletModuleCachingType constant for user. */
  public static final BAppletModuleCachingType user = new BAppletModuleCachingType(USER);

  /** Factory method with ordinal. */
  public static BAppletModuleCachingType make(int ordinal)
  {
    return (BAppletModuleCachingType)host.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BAppletModuleCachingType make(String tag)
  {
    return (BAppletModuleCachingType)host.getRange().get(tag);
  }

  /** Private constructor. */
  private BAppletModuleCachingType(int ordinal)
  {
    super(ordinal);
  }

  public static final BAppletModuleCachingType DEFAULT = host;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAppletModuleCachingType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
