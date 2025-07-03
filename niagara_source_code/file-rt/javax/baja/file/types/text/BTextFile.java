/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.text;

import javax.baja.file.BDataFile;
import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BTextFile stores plain text.
 *
 * @author    Brian Frank       
 * @creation  27 Jan 03
 * @version   $Revision: 2$ $Date: 3/12/03 10:46:55 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = {
    @FileExt(name = "txt"),
    @FileExt(name = "text"),
    @FileExt(name = "perl"),
    @FileExt(name = "py"),
    @FileExt(name = "bat"),
    @FileExt(name = "sh"),
    @FileExt(name = "properties"),
    @FileExt(name = "lexicon"),
    @FileExt(name = "rc"),
    @FileExt(name = "mf"),
    @FileExt(name = "my")
  }
)
public class BTextFile
  extends BDataFile
  implements BITextFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.text.BTextFile(3183724417)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BTextFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BTextFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "text/plain"}.
   */
  @Override
  public String getMimeType()
  {
    return "text/plain";
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("file.png");
}
