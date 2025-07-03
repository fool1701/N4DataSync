/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIDirectory is a container of BIFiles
 *
 * @author    Brian Frank on 24 Jan 03
 * @version   $Revision: 3$ $Date: 3/28/05 9:22:56 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIDirectory
  extends BINavNode
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BIDirectory(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIDirectory.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get the list of containing files.
   */
  BIFile[] listFiles();
}
