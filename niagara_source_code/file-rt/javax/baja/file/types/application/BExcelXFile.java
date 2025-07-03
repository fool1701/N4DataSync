/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.application;

import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType(
  ext = @FileExt(name = "xlsx")
)
public class BExcelXFile
	extends BExcelFile 
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.application.BExcelXFile(3131782020)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BExcelXFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//Overrides
////////////////////////////////////////////////////////////////

  /**
  * Return {@code "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}.
  */
  @Override
  public String getMimeType()
  {
    return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  }
}
