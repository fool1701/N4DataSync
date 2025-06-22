/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BColor;
import javax.baja.nre.annotations.Adapter;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BConverter;

import com.tridium.kitpx.enums.BStatusToBrushMode;

/**
 * Convert Status -> Brush using predefined 
 * Status foreground or background
 *
 * @author    Brian Frank
 * @creation  12 May 04
 * @version   $Revision: 4$ $Date: 1-Nov-04 11:59:26 AM$
 * @since     Baja 1.0
 */
@NiagaraType(
  adapter = @Adapter(from = "baja:IStatus", to = "gx:Brush")
)
/*
 Use predefined foreground or background colors.
 */
@NiagaraProperty(
  name = "mode",
  type = "BStatusToBrushMode",
  defaultValue = "BStatusToBrushMode.background"
)
public class BIStatusToBrush
  extends BConverter
{                           

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BIStatusToBrush(2872309213)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "mode"

  /**
   * Slot for the {@code mode} property.
   * Use predefined foreground or background colors.
   * @see #getMode
   * @see #setMode
   */
  public static final Property mode = newProperty(0, BStatusToBrushMode.background, null);

  /**
   * Get the {@code mode} property.
   * Use predefined foreground or background colors.
   * @see #mode
   */
  public BStatusToBrushMode getMode() { return (BStatusToBrushMode)get(mode); }

  /**
   * Set the {@code mode} property.
   * Use predefined foreground or background colors.
   * @see #mode
   */
  public void setMode(BStatusToBrushMode v) { set(mode, v, null); }

  //endregion Property "mode"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIStatusToBrush.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Converter
////////////////////////////////////////////////////////////////

  public BObject convert(BObject from, BObject to, Context cx)
  {                     
    BStatus status = ((BIStatus)from).getStatus();            
    
    BColor color;
    if (getMode() == BStatusToBrushMode.foreground)
      color = (BColor)status.getForegroundColor(null);
    else
      color = (BColor)status.getBackgroundColor(null);
    
    if (color != null) return color.toBrush();
    
    return to;
  }                            
  
} 
