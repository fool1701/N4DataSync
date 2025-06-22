/*  
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.agent.AgentList;
import javax.baja.gx.BBrush;
import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BLabel;
import javax.baja.ui.event.BMouseEvent;

import com.tridium.kitpx.enums.BMouseOverEffect;
import com.tridium.ui.theme.Theme;

/**
 * BBoundLabel.
 *
 * @author    Andy Frank
 * @creation  08 Nov 04
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Border to use.
 */
@NiagaraProperty(
  name = "border",
  type = "BBorder",
  defaultValue = "BBorder.none"
)
/*
 The effect to use for mouse overs.
 */
@NiagaraProperty(
  name = "mouseOver",
  type = "BMouseOverEffect",
  defaultValue = "BMouseOverEffect.none"
)
/*
 Padding label bounds and widget bounds.
 */
@NiagaraProperty(
  name = "padding",
  type = "BInsets",
  defaultValue = "BInsets.DEFAULT"
)
public class BBoundLabel
  extends BLabel
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BBoundLabel(1204520251)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "border"

  /**
   * Slot for the {@code border} property.
   * Border to use.
   * @see #getBorder
   * @see #setBorder
   */
  public static final Property border = newProperty(0, BBorder.none, null);

  /**
   * Get the {@code border} property.
   * Border to use.
   * @see #border
   */
  public BBorder getBorder() { return (BBorder)get(border); }

  /**
   * Set the {@code border} property.
   * Border to use.
   * @see #border
   */
  public void setBorder(BBorder v) { set(border, v, null); }

  //endregion Property "border"

  //region Property "mouseOver"

  /**
   * Slot for the {@code mouseOver} property.
   * The effect to use for mouse overs.
   * @see #getMouseOver
   * @see #setMouseOver
   */
  public static final Property mouseOver = newProperty(0, BMouseOverEffect.none, null);

  /**
   * Get the {@code mouseOver} property.
   * The effect to use for mouse overs.
   * @see #mouseOver
   */
  public BMouseOverEffect getMouseOver() { return (BMouseOverEffect)get(mouseOver); }

  /**
   * Set the {@code mouseOver} property.
   * The effect to use for mouse overs.
   * @see #mouseOver
   */
  public void setMouseOver(BMouseOverEffect v) { set(mouseOver, v, null); }

  //endregion Property "mouseOver"

  //region Property "padding"

  /**
   * Slot for the {@code padding} property.
   * Padding label bounds and widget bounds.
   * @see #getPadding
   * @see #setPadding
   */
  public static final Property padding = newProperty(0, BInsets.DEFAULT, null);

  /**
   * Get the {@code padding} property.
   * Padding label bounds and widget bounds.
   * @see #padding
   */
  public BInsets getPadding() { return (BInsets)get(padding); }

  /**
   * Set the {@code padding} property.
   * Padding label bounds and widget bounds.
   * @see #padding
   */
  public void setPadding(BInsets v) { set(padding, v, null); }

  //endregion Property "padding"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBoundLabel.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBoundLabel()
  {
  }
  
////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////
  
  public boolean receiveInputEvents()
  {
    return true;
  }
    
  public void paint(Graphics g)
  {
    super.paint(g);
    
    BBorder border;
    if (isMouseOver && getMouseOver().equals(BMouseOverEffect.outline))
    {
      if(getBorder().isNull() || (getBorder().topWidth==0 && getBorder().leftWidth==0 && getBorder().rightWidth==0 && getBorder().bottomWidth==0))
        border = BBorder.make(1, BBorder.SOLID, Theme.widget().getSelectionBackground());
      else
        border = BBorder.make(getBorder(), Theme.widget().getSelectionBackground());
    } else 
      border = getBorder();
    
    border.paint(g, 1, 1, getWidth()-2, getHeight()-2);
  }

  protected void paintBackground(Graphics g, BBrush background)
  {
    if (isMouseOver && getMouseOver().equals(BMouseOverEffect.highlight))
    {
      g.push();
      g.setBrush(Theme.widget().getSelectionBackground());
      g.fillRect(0,0,getWidth(),getHeight());
      g.pop();
    } else
      super.paintBackground(g, background);
  }  
  
  protected void paintIcon(Graphics g, BImage image, double x, double y)
  {
    if (isMouseOver && getMouseOver().equals(BMouseOverEffect.highlight))
      image = makeImage(image);
    super.paintIcon(g, image, x, y);
  }  

  protected void paintText(Graphics g, String text, double tx, double ty)
  {
    g.push();
    if (isMouseOver && getMouseOver().equals(BMouseOverEffect.highlight))
      g.setBrush(Theme.widget().getSelectionForeground());
    super.paintText(g, text, tx, ty);
    g.pop();
  }
    
////////////////////////////////////////////////////////////////
// Mouse Events
////////////////////////////////////////////////////////////////

  public void mouseEntered(BMouseEvent event)
  { 
    isMouseOver = true;
    repaint();
  }
  
  public void mouseExited(BMouseEvent event)
  { 
    isMouseOver = false;
    repaint();
  }

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("kitPx:BoundLabelBinding");
    return agents;
  }

////////////////////////////////////////////////////////////////
// Composite
////////////////////////////////////////////////////////////////

  private BImage makeImage(BImage orig)
  {
    int w = (int)orig.getWidth();
    int h = (int)orig.getHeight();
    int[] pixels = orig.getPixels();
    
    for (int j=0; j<h; j++)
      for (int i=0; i<w; i++)
      {
        int rgb = pixels[j*w+i];
        int a = 0xff & rgb >> 24;
        int r = 0xff & rgb >> 16;
        int g = 0xff & rgb >> 8;
        int b = 0xff & rgb;
        
        r = r / 2;
        g = g / 2;
        b = b / 2 + 65;
        
        pixels[j*w+i] = (a << 24) | (r << 16) | (g << 8) | b;
      }
      
    if (img == null || img.getWidth() != w || img.getHeight() != h)
      img = BImage.make(w,h);
    img.setPixels(pixels);
    return img;
  }  
  private BImage img;
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private boolean isMouseOver = false;
}
