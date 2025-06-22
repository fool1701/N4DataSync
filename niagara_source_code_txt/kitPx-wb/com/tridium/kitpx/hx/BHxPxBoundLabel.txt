/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import javax.baja.gx.BBrush;
import javax.baja.gx.BFont;
import javax.baja.hx.HxOp;
import javax.baja.hx.HxUtil;
import javax.baja.hx.PropertiesCollection;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.hx.px.BHxPxLabel;
import com.tridium.hx.px.ux.UxLabelUtil;
import com.tridium.kitpx.BBoundLabel;
import com.tridium.kitpx.enums.BMouseOverEffect;
import com.tridium.kitpx.hx.ux.UxBoundLabelUtil;
import com.tridium.ui.theme.Theme;

@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:BoundLabel",
    requiredPermissions = "r"
  )
)
@NiagaraSingleton
public class BHxPxBoundLabel
  extends BHxPxLabel
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxBoundLabel(2915584518)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxBoundLabel INSTANCE = new BHxPxBoundLabel();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxBoundLabel.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxBoundLabel() {}

  @Override
  public void write(HxOp op)
    throws Exception
  {
    if (!UxLabelUtil.hasLegacyLabel(op))
    {
      UxBoundLabelUtil.write(op);
      return;
    }

    super.write(op);
  }

  @Override
  public void update(int width, int height, boolean forcedUpdate, HxOp op)
    throws Exception
  {
    if (!UxLabelUtil.hasLegacyLabel(op))
    {
      UxBoundLabelUtil.update(width, height, op);
      return;
    }

    super.update(width, height, forcedUpdate, op);

    PropertiesCollection properties = new PropertiesCollection.Properties();
    PropertiesCollection borderStyle = new PropertiesCollection.Styles();
    PropertiesCollection spanStyle = new PropertiesCollection.Styles();

    BBoundLabel label = (BBoundLabel) op.get();

    properties.add("className", "hxpx-boundLabel");

    HxUtil.makeBorder(label.getBorder(), borderStyle);

    borderStyle.write(op.scope("border"), op);

    BFont font = label.getFont();
    BBrush foreground = label.getForeground();

    if (!label.getEnabled())
    {
      properties.add("className", "pxDisabled");
      foreground = Theme.label().getTextDisabled(label);
    }
    else
    {
      BMouseOverEffect effect = label.getMouseOver();
      switch (effect.getOrdinal())
      {
        case BMouseOverEffect.NONE:
          break;
        case BMouseOverEffect.HIGHLIGHT:
          properties.add("className", "pxHighlight");
          break;
        case BMouseOverEffect.OUTLINE:
          properties.add("className", "pxOutline");
          break;
      }
    }

    HxUtil.makeFont(foreground, font, spanStyle, properties, label.getWordWrapEnabled(), op);
    properties.write(op);
    spanStyle.write(op.scope("text"), op);
  }
}
