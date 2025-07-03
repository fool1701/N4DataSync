/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.audio;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BM4aFile stores M4A audio.
 *
 * @author    Tom Duffy       
 * @creation  13 Jun 2013
 */
@NiagaraType(
  ext = @FileExt(name = "m4a")
)
public class BM4aFile
  extends BAudioFile 
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.audio.BM4aFile(257886942)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BM4aFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  ////////////////////////////////////////////////////////////////
  //Constructor
  ////////////////////////////////////////////////////////////////
  
  /**
  * Construct a file with the specified store.
  */
  public BM4aFile(BIFileStore store)
  {
    super(store);
  }
  
  /**
   * Construct (must call setStore()).
   */
  public BM4aFile()
  {  
  }
  
  ////////////////////////////////////////////////////////////////
  //Overrides
  ////////////////////////////////////////////////////////////////
  
  /**
  * Return {@code "audio/mp4"}.
  */
  @Override
  public String getMimeType()
  {
    return "audio/mp4";
  }
}
