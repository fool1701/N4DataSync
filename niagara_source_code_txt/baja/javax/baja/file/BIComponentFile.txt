/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.*;
import javax.baja.sys.*;

/**
 * BIComponentFile should be implemented by file type objects that
 * may be move/copied to a BComponent space but first require conversion
 * to a BComponent.
 *
 * @author    Robert Adams
 * @creation  12 Dec 06
 * @version   $Revision: 4$ $Date: 6/11/07 12:41:23 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType
public interface BIComponentFile
  extends BIFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BIComponentFile(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIComponentFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public Mark readComponents() throws Exception;
}
