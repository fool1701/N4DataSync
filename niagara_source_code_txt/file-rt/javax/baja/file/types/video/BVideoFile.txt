/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.types.video;

import javax.baja.file.BDataFile;
import javax.baja.file.BIFileStore;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BVideoFile stores video media.
 *
 * @author    Brian Frank       
 * @creation  27 Jan 03
 * @version   $Revision: 2$ $Date: 3/12/03 10:47:00 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BVideoFile
  extends BDataFile
  implements BIVideoFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.types.video.BVideoFile(2979906276)1.0$ @*/
/* Generated Fri Jan 14 17:44:42 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVideoFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BVideoFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BVideoFile()
  {  
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return {@code "video/*"}.
   */
  @Override
  public String getMimeType()
  {
    return "video/*";
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("video.png");
}
