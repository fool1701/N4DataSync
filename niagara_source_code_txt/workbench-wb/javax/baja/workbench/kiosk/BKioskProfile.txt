/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.kiosk;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.menu.BIMenu;
import javax.baja.ui.menu.BIMenuBar;
import javax.baja.ui.toolbar.BIToolBar;
import javax.baja.util.BTypeConfig;
import javax.baja.workbench.BWbProfile;
import javax.baja.workbench.BWbShell;

/**
 * BKioskProfile is the base class for Workbench Profiles to 
 * use in kiosk mode when running a full screen workbench
 * out of station VM.
 *
 * @author    Brian Frank       
 * @creation  15 Nov 06
 * @version   $Revision: 5$ $Date: 6/11/07 12:41:49 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType
public abstract class BKioskProfile
  extends BWbProfile
  implements BTypeConfig.IConfigurable
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.kiosk.BKioskProfile(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BKioskProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * All BKioskProfile must provide two public constructors,
   * one which takes a BWbShell and one which takes no arguments.
   */
  public BKioskProfile(BWbShell shell)
  {                      
    super(shell);
  }

  /**
   * All BKioskProfile must provide two public constructors,
   * one which takes a BWbShell and one which takes no arguments.
   */
  public BKioskProfile()
  {                      
    super(null);
  }

////////////////////////////////////////////////////////////////
// BTypeConfig.IConfigurable
////////////////////////////////////////////////////////////////
  
  /**
   * Return the string keys of the configurable properties.
   */
  public String[] listConfig()
  {
    return new String[0];
  }

  /**
   * Get a configurable property by String key.
   */
  public BValue getConfig(String key)
  {
    return null;
  }             

  /**
   * Get the facets for a configurable property.
   */
  public BFacets getConfigFacets(String key)
  {
    return null;
  }             
  
  /**
   * Set a configurable property by String key.
   */
  public void setConfig(String key, BValue value)
  {
  }

////////////////////////////////////////////////////////////////
// Profile
////////////////////////////////////////////////////////////////
  
  public BIMenuBar makeMenuBar()
  {
    BIMenuBar menuBar = super.makeMenuBar();
    
    // no open
    BIMenu fileMenu = menuBar.getMenu("file");
    fileMenu.removeItem("open");
    
    // no tools
    menuBar.removeMenu("tools");
    
    return menuBar;
  }
  
  public BIToolBar makeToolBar()
  {
    BIToolBar toolBar = super.makeToolBar();
    toolBar.removeButton("open");
    return toolBar;
  }

  public boolean hasTool(TypeInfo typeInfo)
  {
    String t = typeInfo.toString();
    if (t.equals("wbutil:ManageCredentialsTool")) return false;
    return true;
  }
  
}
