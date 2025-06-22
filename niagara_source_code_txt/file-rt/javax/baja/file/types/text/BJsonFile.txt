/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.file.types.text;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Added as a way to view JSON files in the browser.
 *
 * @author    Rowyn on 12 Nov 18
 * @since     Niagara 4.8
 */

@NiagaraType(
  ext = @FileExt(name = "json")
)
public class BJsonFile
  extends BTextFile implements BIJsonFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.text.BJsonFile(2383890743)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BJsonFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BJsonFile(BIFileStore store)
  {
    super(store);
  }

  public BJsonFile()
  {
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "application/json"}.
   */
  @Override
  public String getMimeType()
  {
    return "application/json";
  }
}
