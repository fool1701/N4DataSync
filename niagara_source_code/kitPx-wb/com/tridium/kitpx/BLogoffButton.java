/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.Command;
import javax.baja.ui.util.UiLexicon;
import javax.baja.workbench.BWbShell;

/**
 * BLogoffButton automatically integrates with the WbShell's logoff command.
 *
 * @author    Brian Frank
 * @creation  24 Aug 04
 * @version   $Revision$ $Date: 19-May-04 11:11:24 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BLogoffButton
  extends BWbCommandButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BLogoffButton(2979906276)1.0$ @*/
/* Generated Fri Nov 19 14:22:33 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLogoffButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BLogoffButton()
  {
    super(label, BImage.make(icon));
  }

  public Command getWbCommand()
  {
    return ((BWbShell)getShell()).getLogoffCommand();
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("door.png");

  static final String label = UiLexicon.bajaui().getText("commands.logoff.label");
}
