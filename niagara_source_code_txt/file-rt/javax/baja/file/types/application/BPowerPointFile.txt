/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.application;

import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BPowerPointFile stores a Microsoft PowerPoint file
 *
 * @author    Brian Frank       
 * @creation  27 Jan 03
 * @version   $Revision: 2$ $Date: 8/11/03 11:27:08 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "ppt")
)
public class BPowerPointFile
  extends BApplicationFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.application.BPowerPointFile(899655512)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPowerPointFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BPowerPointFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BPowerPointFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "application/powerpoint"}.
   */
  @Override
  public String getMimeType()
  {
    return "application/powerpoint";
  }
      
  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("files/powerPoint.png");
}
