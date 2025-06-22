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
 * BWmvFile stores Windows Media Videos.
 *
 * @author    Tom Duffy       
 * @creation  13 Jun 2013
 */
@NiagaraType(
  ext = @FileExt(name = "wmv")
)
public class BWmvFile
  extends BVideoFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.video.BWmvFile(900783091)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWmvFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  ////////////////////////////////////////////////////////////////
  //Constructor
  ////////////////////////////////////////////////////////////////
  
  /**
  * Construct a file with the specified store.
  */
  public BWmvFile(BIFileStore store)
  {
    super(store);
  }
  
  /**
   * Construct (must call setStore()).
   */
  public BWmvFile()
  {  
  }
  
  ////////////////////////////////////////////////////////////////
  //Overrides
  ////////////////////////////////////////////////////////////////
  
  /**
  * Return {@code "video/x-ms-wmv"}.
  */
  @Override
  public String getMimeType()
  {
    return "video/x-ms-wmv";
  }
}
