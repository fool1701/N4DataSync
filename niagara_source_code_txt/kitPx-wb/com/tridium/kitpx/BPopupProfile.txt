/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitpx;

import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.nav.BRootScheme;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BMenuBar;
import javax.baja.ui.BToolBar;
import javax.baja.workbench.BWbLocatorBar;
import javax.baja.workbench.BWbProfile;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.BWbStatusBar;

import com.tridium.workbench.shell.BNiagaraWbDialog;

/**
 * The pop up profile
 *
 * @author    Gareth Johnson
 * @creation  11 May 2007
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
public final class BPopupProfile
  extends BWbProfile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BPopupProfile(2979906276)1.0$ @*/
/* Generated Fri Nov 19 14:22:33 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPopupProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BPopupProfile() {}

  public BPopupProfile(BWbShell shell)
  {
    super(shell);

    title = ((BNiagaraWbDialog)shell).getTitle();
    ord   = ((BNiagaraWbDialog)shell).getOrd();
  }

  public boolean hasSideBar() { return false; }
  public boolean hasTools() { return false; }
  public BMenuBar makeMenuBar() { return null; }
  public BWbStatusBar makeStatusBar() { return null; }
  public BToolBar makeToolBar() { return null; }
  public BWbLocatorBar makeLocatorBar() { return null; }

  public String getFrameTitle() { return title; }
  
  public BOrd getStartOrd() { return ord; }                   
  public BOrd getOpenOrd(BISession session, BOrd def) { return def; }
  public BOrd getHomeOrd() { return ord; }
  public BOrd getNavRootOrd() { return BRootScheme.ORD; }

  private String title = "Pop up";
  private BOrd ord = BOrd.NULL;
}
