/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.image;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BWebpFile stores a WebP image.
 *
 * @author Patrick sager
 * @creation 8/7/2017
 * @since Niagara 4.4
 */
@NiagaraType(
  ext = @FileExt(name = "webp")
)
public class BWebpFile
  extends BPngFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.image.BWebpFile(250215940)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebpFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Construct a file with the specified store.
   */
  public BWebpFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BWebpFile()
  {
  }

  /**
   * Return {@code "image/webp"}.
   */
  @Override
  public String getMimeType()
  {
    return "image/webp";
  }
}
