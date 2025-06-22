/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BFolder is the standard BComponent used as a 
 * container for other BComponents.
 *
 * @author    Brian Frank
 * @creation  31 Aug 01
 * @version   $Revision: 12$ $Date: 3/28/05 9:23:16 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFolder
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BFolder(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon()
  { 
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
      return (BIcon)dynamic;
    return icon;
  }  
  private static final BIcon icon = BIcon.std("folder.png");  

}
