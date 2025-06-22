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
 * BBackButton automatically integrates with the WbShell's back command.
 *
 * @author    Brian Frank
 * @creation  24 Aug 04
 * @version   $Revision$ $Date: 19-May-04 11:11:24 AM$
 * @since     Baja 1.0
 */
@NiagaraType
public class BBackButton
  extends BWbCommandButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BBackButton(2979906276)1.0$ @*/
/* Generated Fri Nov 19 14:22:33 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBackButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BBackButton()
  {
    super(label, BImage.make(icon));
  }

  public Command getWbCommand()
  {
    return ((BWbShell)getShell()).getBackCommand();
  }

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("arrowLeft.png");

  static final String label = UiLexicon.bajaui().getText("commands.back.label");
}
