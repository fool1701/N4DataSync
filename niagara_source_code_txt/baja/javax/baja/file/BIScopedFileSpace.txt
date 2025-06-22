/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.file;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * File space that encompasses only the files within a certain scope.
 *
 * @author Dan Heine
 * @creation 2013-12-03
 * @since Niagara 4.0
 */
@NiagaraType
public interface BIScopedFileSpace extends BIFileSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BIScopedFileSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIScopedFileSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// API
////////////////////////////////////////////////////////////////

  /**
   * Scope directory path.
   */
  FilePath getScope();

  /**
   * Is the given FilePath contained within this scoped file system?
   */
  boolean inScope(FilePath path);
}
