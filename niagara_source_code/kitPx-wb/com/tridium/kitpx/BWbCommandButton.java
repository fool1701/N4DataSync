/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.Command;
import javax.baja.util.BFormat;
import javax.baja.workbench.BWbShell;

/**
 * BWbCommandButton provides the common functionlity for
 * buttons that call workbench built-in commands.
 *
 * @author    Andy Frank
 * @creation  29 Aug 07
 * @version   $Date$
 * @since     Niagara 3.3
 */
@NiagaraType
/*
 If this property is not empty, it is used to override
 the default text setting defined by WbCommand.saveCommand.
 */
@NiagaraProperty(
  name = "textOverride",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT"
)
public abstract class BWbCommandButton
  extends BButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BWbCommandButton(3998291524)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "textOverride"

  /**
   * Slot for the {@code textOverride} property.
   * If this property is not empty, it is used to override
   * the default text setting defined by WbCommand.saveCommand.
   * @see #getTextOverride
   * @see #setTextOverride
   */
  public static final Property textOverride = newProperty(0, BFormat.DEFAULT, null);

  /**
   * Get the {@code textOverride} property.
   * If this property is not empty, it is used to override
   * the default text setting defined by WbCommand.saveCommand.
   * @see #textOverride
   */
  public BFormat getTextOverride() { return (BFormat)get(textOverride); }

  /**
   * Set the {@code textOverride} property.
   * If this property is not empty, it is used to override
   * the default text setting defined by WbCommand.saveCommand.
   * @see #textOverride
   */
  public void setTextOverride(BFormat v) { set(textOverride, v, null); }

  //endregion Property "textOverride"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbCommandButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BWbCommandButton()
  {}
  
  public BWbCommandButton(String defaultText, BImage defaultImage)
  {
    this.defaultText = defaultText;
    this.defaultImage = defaultImage;

    setText(defaultText);
    setImage(defaultImage);
    setEnabled(false);

    setFlags(text,    Flags.TRANSIENT | Flags.READONLY);
    setFlags(enabled, Flags.TRANSIENT | Flags.READONLY);
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return the WbCommand to invoke for this button.
   */
  public abstract Command getWbCommand();

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  public void started() throws Exception
  {
    super.started();

    boolean useLabel = updateText();
    boolean useImage = false;

    BWidgetShell shell = getShell();
    if (shell instanceof BWbShell)
      setCommand(getWbCommand(), useLabel, useImage);
  }

  public void stopped() throws Exception
  {
    super.stopped();
    setCommand(null, true, true);
  }

  public void changed(Property prop,  Context cx)
  {
    if (prop == textOverride) updateText();
    if (prop == image)
    {
      BImage img = getImage();
      if( img.equals(BImage.NULL))
        setImage(BImage.make(NULL));
    }
  }

  /**
   * Update text property - return true if default was used.
   */
  boolean updateText()
  {
    BFormat f = getTextOverride();
    boolean v = f == BFormat.DEFAULT;
    setText(v ? defaultText : f.format(this));
    return v;
  }

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private static final BIcon NULL = BIcon.make("module://kitPx/icons/NULL.png");
  
  private String defaultText;
  private BImage defaultImage;

}
