/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.video;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BMpegVideoFile stores MPEG Videos.
 *
 * @author    Tom Duffy       
 * @creation  13 Jun 2013
 */
@NiagaraType(
  ext = {
    @FileExt(name = "mpg"),
    @FileExt(name = "mpeg"),
    @FileExt(name = "mpv"),
    @FileExt(name = "mpe"),
    @FileExt(name = "m1v"),
    @FileExt(name = "m2v")
  }
)
public class BMpegVideoFile
  extends BVideoFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.video.BMpegVideoFile(1988912377)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMpegVideoFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  ////////////////////////////////////////////////////////////////
  //Constructor
  ////////////////////////////////////////////////////////////////
  
  /**
  * Construct a file with the specified store.
  */
  public BMpegVideoFile(BIFileStore store)
  {
    super(store);
  }
  
  /**
   * Construct (must call setStore()).
   */
  public BMpegVideoFile()
  {  
  }
  
  ////////////////////////////////////////////////////////////////
  //Overrides
  ////////////////////////////////////////////////////////////////
  
  /**
  * Return {@code "video/mpeg"}.
  */
  @Override
  public String getMimeType()
  {
    return "video/mpeg";
  }
}
