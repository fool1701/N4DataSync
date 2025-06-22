/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.image;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIcoFile stores a GIF image
 *
 * @author    Andy Frank       
 * @creation  25 Oct 06
 * @version   $Revision: 1$ $Date: 10/25/06 3:14:53 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "ico")
)
public class BIcoFile
  extends BImageFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.image.BIcoFile(1437313251)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIcoFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BIcoFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BIcoFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "image/x-icon"}.
   */
  @Override
  public String getMimeType()
  {
    return "image/x-icon";
  }
}
