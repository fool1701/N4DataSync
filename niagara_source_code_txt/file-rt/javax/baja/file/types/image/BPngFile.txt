/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.image;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BPngFile stores a PNG image
 *
 * @author    Brian Frank       
 * @creation  27 Jan 03
 * @version   $Revision: 1$ $Date: 1/27/03 10:55:51 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "png")
)
public class BPngFile
  extends BImageFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.image.BPngFile(721627617)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPngFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BPngFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BPngFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "image/png"}.
   */
  @Override
  public String getMimeType()
  {
    return "image/png";
  }
}
