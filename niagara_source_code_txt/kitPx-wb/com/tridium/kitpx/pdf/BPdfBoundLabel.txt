/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.pdf;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.pdf.PdfOp;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

import com.tridium.kitpx.BBoundLabel;
import com.tridium.pdf.BIPdfWidget;

/**
 * BPdfBoundLabel.
 *
 * @author   Andy Frank
 * @creation 17 May 05
 * @version  $Revision$ $Date$
 * @since    Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:BoundLabel"
  )
)
public class BPdfBoundLabel
  extends BBoundLabel
  implements BIPdfWidget
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.pdf.BPdfBoundLabel(4141145999)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPdfBoundLabel.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BIPdfWidget
////////////////////////////////////////////////////////////////
  

  /**
   * Create from this source BWidget.
   */
  public void fromWidget(BWidget widget, PdfOp op)
  {    
    SlotCursor<Property> c = widget.getProperties();
    while (c.next())
    {
      Property prop = c.property();
      if (prop.isFrozen() && get(prop.getName()) != null) 
        set(prop.getName(), widget.get(prop));
    }
  }
}
