/*
 * Copyright 2008, Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BHyperlinkLabel;
import javax.baja.ui.BWidget;
import javax.baja.user.BUser;
import javax.baja.util.BFormat;

/**
 * BLocalizableLabel allows localizable text to be used without
 * requiring a binding.  Animate the text property if you want the
 * label bound to a value.
 *
 * @author    John Huffman on 15 Apr 08
 * @version   $Revision: 1$
 * @since     Niagara 3.4
 */

@NiagaraType
/*
 The formatted string to use for the displayed label.
 */
@NiagaraProperty(
  name = "textFormat",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT"
)
/*
 Image to display for the background of the label.
 */
@NiagaraProperty(
  name = "backgroundImage",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
/*
 Border to use.
 */
@NiagaraProperty(
  name = "border",
  type = "BBorder",
  defaultValue = "BBorder.none"
)
public class BLocalizableLabel
  extends BHyperlinkLabel 
{ 



//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BLocalizableLabel(3932799490)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "textFormat"

  /**
   * Slot for the {@code textFormat} property.
   * The formatted string to use for the displayed label.
   * @see #getTextFormat
   * @see #setTextFormat
   */
  public static final Property textFormat = newProperty(0, BFormat.DEFAULT, null);

  /**
   * Get the {@code textFormat} property.
   * The formatted string to use for the displayed label.
   * @see #textFormat
   */
  public BFormat getTextFormat() { return (BFormat)get(textFormat); }

  /**
   * Set the {@code textFormat} property.
   * The formatted string to use for the displayed label.
   * @see #textFormat
   */
  public void setTextFormat(BFormat v) { set(textFormat, v, null); }

  //endregion Property "textFormat"

  //region Property "backgroundImage"

  /**
   * Slot for the {@code backgroundImage} property.
   * Image to display for the background of the label.
   * @see #getBackgroundImage
   * @see #setBackgroundImage
   */
  public static final Property backgroundImage = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code backgroundImage} property.
   * Image to display for the background of the label.
   * @see #backgroundImage
   */
  public BImage getBackgroundImage() { return (BImage)get(backgroundImage); }

  /**
   * Set the {@code backgroundImage} property.
   * Image to display for the background of the label.
   * @see #backgroundImage
   */
  public void setBackgroundImage(BImage v) { set(backgroundImage, v, null); }

  //endregion Property "backgroundImage"

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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalizableLabel.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * No argument constructor.
   */
  public BLocalizableLabel()
  {
    setFlags( text, Flags.READONLY );
  }             

  /**
   * Label constructor with specified text.
   */
  public BLocalizableLabel(String label)
  {
    setFlags( text, Flags.READONLY );
    this.setTextFormat( BFormat.make( label ) );
  }
  
  /**
   * Label constructor with specified text and ord.
   */
  public BLocalizableLabel(String label, BOrd ord)
  {
    setFlags( text, Flags.READONLY );
    this.setTextFormat( BFormat.make( label ) );
    super.setOrd( ord );
  }
  
  /**
   * Label constructor with specified text and ord text.
   */
  public BLocalizableLabel(String label, String ord)
  {
    setFlags( text, Flags.READONLY );
    this.setTextFormat( BFormat.make( label ) );
    super.setOrd( BOrd.make( ord ) );
  }

  /**
   * Paint the label.
   */
  public void paint(Graphics g)
  {
    // paint the background image
    BImage image = getBackgroundImage();
    if ( !image.isNull() )
    {
      if ( !isEnabled() ) image = image.getDisabledImage();
      double imageX = ( getWidth() - image.getWidth() ) / 2;
      double imageY = ( getHeight() - image.getHeight() ) / 2;
      paintIcon( g, image, imageX, imageY );
    }
    super.paint( g );

    BBorder border = getBorder();
    if ( ( border != null ) && ( !border.isNull() ) && (!border.equals(BBorder.none)) )
    {
      border.paint( g, 1, 1, getWidth() - 2, getHeight() - 2 );
    }
  }
  
  /**
   * @return the formatted text.
   */
  public String getText()
  {
    return getDisplayText(this);
  }

  /**
   * Get the display text for this widget, using the default user context.
   * @param widget the localizable button or label to display text for
   * @return the display string (will be empty if no text is configured, never
   * null)
   */
  static String getDisplayText(BWidget widget)
  {
    return getDisplayText(widget, BUser.getCurrentAuthenticatedUser());
  }

  /**
   * Get the display text for a LocalizableLabel or LocalizableButton. Will
   * use the "text" slot if present, and format the "textFormat" slot if not.
   * @param widget the localizable button or label to display text for
   * @param cx user/language context
   * @return the display string (will be empty if no text is configured, never
   * null)
   */
  public static String getDisplayText(BWidget widget, Context cx)
  {
    String text = widget.get("text").toString();
    if (!text.isEmpty())
    {
      return text;
    }

    BFormat textFormat = (BFormat) widget.get("textFormat");
    if (!textFormat.equals(BFormat.DEFAULT))
    {
      return textFormat.format(widget, cx);
    }

    return "";
  }
}
