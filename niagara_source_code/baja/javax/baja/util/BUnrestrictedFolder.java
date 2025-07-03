/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BUnrestrictedFolder is a folder which by-passes normal 
 * BComponent.isParentLegal() checks.  It is useful for building
 * palettes or when a general container is needed.
 *
 * @author    Brian Frank
 * @creation  31 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 9:23:16 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BUnrestrictedFolder
  extends BFolder
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BUnrestrictedFolder(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUnrestrictedFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
