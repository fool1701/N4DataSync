/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import javax.baja.hx.HxOp;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

import com.tridium.hx.px.BHxPxButton;

/**
 * BHxWbCommandButton. Subclass for HxButtons who register as agents on BWbCommandButton
 *
 * @author    JJ Frankovich
 * @creation  26 April 2017
 * @version   $Revision$ $Date$
 * @since     Niagara 4.3
 */
@NiagaraType
public abstract class BHxWbCommandButton
  extends BHxPxButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxWbCommandButton(2979906276)1.0$ @*/
/* Generated Fri Nov 19 14:22:33 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxWbCommandButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxWbCommandButton() {}

////////////////////////////////////////////////////////////////
// HxPxButton
////////////////////////////////////////////////////////////////
  public void write(HxOp op)
    throws Exception
  {
    super.write(op);
    BWidget widget = (BWidget) op.get();
    if(!widget.getEnabled())
    {
      widget.setEnabled(true); //Allow Mouse Over to work since BWbCommands are all disabled by default
    }
  }

  public boolean isMouseEnabled(HxOp op)
  {
    return true;
  }
  
  protected boolean getEnabled(HxOp op)
  {
    return true;
  }
  
}
