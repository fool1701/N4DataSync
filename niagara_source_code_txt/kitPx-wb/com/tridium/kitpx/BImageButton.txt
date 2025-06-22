/*  
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BImage;
import javax.baja.gx.BSize;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;

/**
 * BImageButton using images to display the state of the button.
 *
 * @author    Andy Frank
 * @creation  15 Nov 04
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 Image rendered when mouse is outside of button.
 */
@NiagaraProperty(
  name = "normal",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
/*
 Image rendered when mouse is over the button.
 */
@NiagaraProperty(
  name = "mouseOver",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
/*
 Image rendered when button is pressed.
 */
@NiagaraProperty(
  name = "pressed",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
/*
 Image rendered when button is disabled. If null, use
 getNormal().getDisabledImgage().
 */
@NiagaraProperty(
  name = "disabled",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
public class BImageButton
  extends BButton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BImageButton(897740835)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "normal"

  /**
   * Slot for the {@code normal} property.
   * Image rendered when mouse is outside of button.
   * @see #getNormal
   * @see #setNormal
   */
  public static final Property normal = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code normal} property.
   * Image rendered when mouse is outside of button.
   * @see #normal
   */
  public BImage getNormal() { return (BImage)get(normal); }

  /**
   * Set the {@code normal} property.
   * Image rendered when mouse is outside of button.
   * @see #normal
   */
  public void setNormal(BImage v) { set(normal, v, null); }

  //endregion Property "normal"

  //region Property "mouseOver"

  /**
   * Slot for the {@code mouseOver} property.
   * Image rendered when mouse is over the button.
   * @see #getMouseOver
   * @see #setMouseOver
   */
  public static final Property mouseOver = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code mouseOver} property.
   * Image rendered when mouse is over the button.
   * @see #mouseOver
   */
  public BImage getMouseOver() { return (BImage)get(mouseOver); }

  /**
   * Set the {@code mouseOver} property.
   * Image rendered when mouse is over the button.
   * @see #mouseOver
   */
  public void setMouseOver(BImage v) { set(mouseOver, v, null); }

  //endregion Property "mouseOver"

  //region Property "pressed"

  /**
   * Slot for the {@code pressed} property.
   * Image rendered when button is pressed.
   * @see #getPressed
   * @see #setPressed
   */
  public static final Property pressed = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code pressed} property.
   * Image rendered when button is pressed.
   * @see #pressed
   */
  public BImage getPressed() { return (BImage)get(pressed); }

  /**
   * Set the {@code pressed} property.
   * Image rendered when button is pressed.
   * @see #pressed
   */
  public void setPressed(BImage v) { set(pressed, v, null); }

  //endregion Property "pressed"

  //region Property "disabled"

  /**
   * Slot for the {@code disabled} property.
   * Image rendered when button is disabled. If null, use
   * getNormal().getDisabledImgage().
   * @see #getDisabled
   * @see #setDisabled
   */
  public static final Property disabled = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code disabled} property.
   * Image rendered when button is disabled. If null, use
   * getNormal().getDisabledImgage().
   * @see #disabled
   */
  public BImage getDisabled() { return (BImage)get(disabled); }

  /**
   * Set the {@code disabled} property.
   * Image rendered when button is disabled. If null, use
   * getNormal().getDisabledImgage().
   * @see #disabled
   */
  public void setDisabled(BImage v) { set(disabled, v, null); }

  //endregion Property "disabled"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BImageButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BImageButton()
  {
  }
  
////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////
  
  public void computePreferredSize()
  {
    super.computePreferredSize();
    BSize size = getMaxImageSize();
    double pw = Math.max(size.width, getPreferredWidth());
    double ph = Math.max(size.height, getPreferredHeight());
    if (pw > 0 && ph > 0) setPreferredSize(pw, ph);
  }
  
  public void paintBackground(Graphics g)
  {
    super.paintBackground(g);
        
    if (!isEnabled())
    {
      BImage img = getDisabled();
      if (img.isNull() && !getNormal().isNull()) 
        img = getNormal().getDisabledImage();
      paintImage(g, img);
    }
    else if (isPressed() && isMouseOver() && !getPressed().isNull()) 
      paintImage(g, getPressed());
    else if (isMouseOver() && !getMouseOver().isNull()) 
      paintImage(g, getMouseOver());
    else if (!getNormal().isNull()) 
      paintImage(g, getNormal());
  }  

  protected void paintIcon(Graphics g, BImage image, double x, double y)
  {
    // Only render text if no images have been set
    if (getNormal().isNull() && getMouseOver().isNull() && getPressed().isNull())
      super.paintIcon(g, image, x, y);
  }

  protected void paintText(Graphics g, String text, double tx, double ty)
  {
    //do not render text if normal image is set
    if( !getNormal().isNull())return;
    
    //if button is pressed and pressed image is non-null, return.
    if( !getPressed().isNull() && isPressed())return;
    
    //if mouse over is non-null and active, return
    if( !getMouseOver().isNull() && isMouseOver())return;
    
    super.paintText(g, text, tx, ty);
  }
  
  private void paintImage(Graphics g, BImage img)
  {
    double x = (getWidth() - img.getWidth()) / 2;
    double y = (getHeight() - img.getHeight()) / 2;
    g.drawImage(img, x, y);
  }
    
  public void animate()
  {
    // For BLabel lazy image loading and blink
    super.animate();
    
    if (!getNormal().isNull())
    {
      if(!nloaded && getNormal().isLoaded())
      {
        nloaded = true;
        relayout();
      }
      // if the image is animated and needs a repaint
      if (getNormal().animate())
        repaint();
    }
    
    if (!getMouseOver().isNull())
    {
      if(!oloaded && getMouseOver().isLoaded())
      {
        oloaded = true;
        relayout();
      }
      // if the image is animated and needs a repaint
      if (getMouseOver().animate())
        repaint();
    }    
    
    if (!getPressed().isNull())
    {
      if(!ploaded && getPressed().isLoaded())
      {
        ploaded = true;
        relayout();
      }
      // if the image is animated and needs a repaint
      if (getPressed().animate())
        repaint();
    }    
    
    if (!getDisabled().isNull())
    {
      if(!dloaded && getDisabled().isLoaded())
      {
        dloaded = true;
        relayout();
      }
      // if the image is animated and needs a repaint
      if (getDisabled().animate())
        repaint();
    }    
  }  
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  private BSize getMaxImageSize()
  {
    double iw = 0;
    double ih = 0;
    
    BImage img = getNormal();
    if (!img.isNull())
    {
      iw = img.getWidth();
      ih = img.getHeight();
    }
    
    img = getMouseOver();
    if (!img.isNull())
    {
      iw = Math.max(iw, img.getWidth());
      ih = Math.max(ih, img.getHeight());
    }
    
    img = getPressed();
    if (!img.isNull())
    {
      iw = Math.max(iw, img.getWidth());
      ih = Math.max(ih, img.getHeight());
    }

    img = getDisabled();
    if (!img.isNull())
    {
      iw = Math.max(iw, img.getWidth());
      ih = Math.max(ih, img.getHeight());
    }
    
    return BSize.make(iw, ih);
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private boolean nloaded = false;
  private boolean oloaded = false;
  private boolean ploaded = false;
  private boolean dloaded = false;
}
