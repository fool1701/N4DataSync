/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BNavFileNode is a BINavNode loaded from a nav file using NavFileDecoder.
 *
 * @author    Brian Frank
 * @creation  1 Sept 04
 * @version   $Revision: 4$ $Date: 3/28/05 9:23:01 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BNavFileNode
  extends BNavContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BNavFileNode(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNavFileNode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor 
////////////////////////////////////////////////////////////////

  public BNavFileNode(String name, BOrd navOrd, BIcon icon)
  {
    super(name);
    
    if (icon == null || icon.isNull()) icon = defaultIcon;
    
    this.navOrd = navOrd;
    this.icon = icon;
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////
  
  /**
   * Get the normalized absolute ord for this node.
   */
  @Override
  public BOrd getNavOrd()
  {
    return navOrd;
  }
  
  /**
   * Get the nav ord relativized to the session which is
   * typically how it is used within the station VM.
   */
  public BOrd getOrdInSession()
  {                          
    if (ordInSession == null)
      ordInSession = navOrd.relativizeToSession();
    return ordInSession;
  }

  @Override
  protected void fireNavEvent(NavEvent event)
  {
    // don't fire any events - nav file are always
    // loaded in one batch inside a NavFileSpace
  }
  
////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  { 
    return icon; 
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  static final BIcon defaultIcon = BIcon.std("folder.png");

  BIcon icon;
  BOrd navOrd, ordInSession;

}
