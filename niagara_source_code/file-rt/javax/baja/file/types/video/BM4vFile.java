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
 * BM4vFile stores M4V Videos.
 *
 * @author    Tom Duffy       
 * @creation  13 Jun 2013
 */
@NiagaraType(
  ext = @FileExt(name = "m4v")
)
public class BM4vFile
  extends BVideoFile 
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.video.BM4vFile(3718201676)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BM4vFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  ////////////////////////////////////////////////////////////////
  //Constructor
  ////////////////////////////////////////////////////////////////
  
  /**
  * Construct a file with the specified store.
  */
  public BM4vFile(BIFileStore store)
  {
    super(store);
  }
  
  /**
   * Construct (must call setStore()).
   */
  public BM4vFile()
  {  
  }
  
  ////////////////////////////////////////////////////////////////
  //Overrides
  ////////////////////////////////////////////////////////////////
  
  /**
  * Return {@code "video/x-m4v"}.
  */
  @Override
  public String getMimeType()
  {
    return "video/x-m4v";
  }
}
