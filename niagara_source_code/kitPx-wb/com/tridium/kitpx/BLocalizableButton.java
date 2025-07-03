/*
 * Copyright 2008, Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import java.util.Optional;

import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BIHyperlinkShell;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.CommandEvent;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.MouseCursor;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.util.UiLexicon;
import javax.baja.user.BUser;
import javax.baja.util.BFormat;

import com.tridium.ui.UiEnv;

/**
 * BLocalizableButton allows localizable text to be used without
 * requiring a binding.  Animate the text property if you want the
 * label bound to a value.
 *
 * @author    John Huffman on 31 Mar 08
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
 The ord to hyperlink to.
 */
@NiagaraProperty(
  name = "hyperlink",
  type = "BOrd",
  defaultValue = "BOrd.DEFAULT"
)
/*
 The formatted text to display in the popup window on mouse hover.
 */
@NiagaraProperty(
  name = "hoverTextFormat",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT"
)
/*
 The formatted text to display in the status bar on mouse over.
 */
@NiagaraProperty(
  name = "statusTextFormat",
  type = "BFormat",
  defaultValue = "BFormat.DEFAULT"
)
/*
 Display hand cursor on mouse over.
 */
@NiagaraProperty(
  name = "showHandCursor",
  type = "boolean",
  defaultValue = "true"
)
/*
 Prompt user before action is performed.
 */
@NiagaraProperty(
  name = "confirmRequired",
  type = "boolean",
  defaultValue = "false"
)
public class BLocalizableButton
  extends BButton
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BLocalizableButton(1366133466)1.0$ @*/
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

  //region Property "hyperlink"

  /**
   * Slot for the {@code hyperlink} property.
   * The ord to hyperlink to.
   * @see #getHyperlink
   * @see #setHyperlink
   */
  public static final Property hyperlink = newProperty(0, BOrd.DEFAULT, null);

  /**
   * Get the {@code hyperlink} property.
   * The ord to hyperlink to.
   * @see #hyperlink
   */
  public BOrd getHyperlink() { return (BOrd)get(hyperlink); }

  /**
   * Set the {@code hyperlink} property.
   * The ord to hyperlink to.
   * @see #hyperlink
   */
  public void setHyperlink(BOrd v) { set(hyperlink, v, null); }

  //endregion Property "hyperlink"

  //region Property "hoverTextFormat"

  /**
   * Slot for the {@code hoverTextFormat} property.
   * The formatted text to display in the popup window on mouse hover.
   * @see #getHoverTextFormat
   * @see #setHoverTextFormat
   */
  public static final Property hoverTextFormat = newProperty(0, BFormat.DEFAULT, null);

  /**
   * Get the {@code hoverTextFormat} property.
   * The formatted text to display in the popup window on mouse hover.
   * @see #hoverTextFormat
   */
  public BFormat getHoverTextFormat() { return (BFormat)get(hoverTextFormat); }

  /**
   * Set the {@code hoverTextFormat} property.
   * The formatted text to display in the popup window on mouse hover.
   * @see #hoverTextFormat
   */
  public void setHoverTextFormat(BFormat v) { set(hoverTextFormat, v, null); }

  //endregion Property "hoverTextFormat"

  //region Property "statusTextFormat"

  /**
   * Slot for the {@code statusTextFormat} property.
   * The formatted text to display in the status bar on mouse over.
   * @see #getStatusTextFormat
   * @see #setStatusTextFormat
   */
  public static final Property statusTextFormat = newProperty(0, BFormat.DEFAULT, null);

  /**
   * Get the {@code statusTextFormat} property.
   * The formatted text to display in the status bar on mouse over.
   * @see #statusTextFormat
   */
  public BFormat getStatusTextFormat() { return (BFormat)get(statusTextFormat); }

  /**
   * Set the {@code statusTextFormat} property.
   * The formatted text to display in the status bar on mouse over.
   * @see #statusTextFormat
   */
  public void setStatusTextFormat(BFormat v) { set(statusTextFormat, v, null); }

  //endregion Property "statusTextFormat"

  //region Property "showHandCursor"

  /**
   * Slot for the {@code showHandCursor} property.
   * Display hand cursor on mouse over.
   * @see #getShowHandCursor
   * @see #setShowHandCursor
   */
  public static final Property showHandCursor = newProperty(0, true, null);

  /**
   * Get the {@code showHandCursor} property.
   * Display hand cursor on mouse over.
   * @see #showHandCursor
   */
  public boolean getShowHandCursor() { return getBoolean(showHandCursor); }

  /**
   * Set the {@code showHandCursor} property.
   * Display hand cursor on mouse over.
   * @see #showHandCursor
   */
  public void setShowHandCursor(boolean v) { setBoolean(showHandCursor, v, null); }

  //endregion Property "showHandCursor"

  //region Property "confirmRequired"

  /**
   * Slot for the {@code confirmRequired} property.
   * Prompt user before action is performed.
   * @see #getConfirmRequired
   * @see #setConfirmRequired
   */
  public static final Property confirmRequired = newProperty(0, false, null);

  /**
   * Get the {@code confirmRequired} property.
   * Prompt user before action is performed.
   * @see #confirmRequired
   */
  public boolean getConfirmRequired() { return getBoolean(confirmRequired); }

  /**
   * Set the {@code confirmRequired} property.
   * Prompt user before action is performed.
   * @see #confirmRequired
   */
  public void setConfirmRequired(boolean v) { setBoolean(confirmRequired, v, null); }

  //endregion Property "confirmRequired"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalizableButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLocalizableButton()
  {
    setFlags( text, Flags.READONLY );
  }

  /**
   * Hyperlink to the specified ord when clicked.
   */
  public void doInvokeAction(CommandEvent event)
  {
    try
    {
      // Hyperlink to the ORD.
      BWidgetShell shell = getShell();
      BOrd ord = getHyperlink();
      if ( ( ord != null ) && ( !ord.isNull() ) && ( shell instanceof BIHyperlinkShell ) )
      {
        if (getConfirmRequired())
        {
          String hyperlinkTo = UiLexicon.bajaui().getText("hyperlinkTo");
          if (!confirm(hyperlinkTo, hyperlinkTo + ": " + ord.toString()))
          {
            return;
          }
        }

        ( (BIHyperlinkShell) shell ).hyperlink( new HyperlinkInfo( ord ) );
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Paint the background of the button.
   */
  public void paintBackground(Graphics g)
  {
    super.paintBackground( g );
    // paint the background image
    BImage image = getBackgroundImage();
    if ( ( image != null ) && ( !image.isNull() ) )
    {
      if ( !isEnabled() ) image = image.getDisabledImage();
      double imageX = ( getWidth() - image.getWidth() ) / 2;
      double imageY = ( getHeight() - image.getHeight() ) / 2;
      paintIcon( g, image, imageX, imageY );
    }
  }

  /**
   * @return the formatted text.
   */
  public String getText()
  {
    return BLocalizableLabel.getDisplayText(this);
  }


  /**
   * Handle mouseEntered event.
   */
  public void mouseEntered(BMouseEvent event)
  {
    super.mouseEntered( event );

    BWidgetShell shell = getShell();
    if ( shell != null )
    {
      BOrd ord = getHyperlink();
      if ( ( getShowHandCursor() ) || ( ( ord != null ) && ( !ord.isNull() ) && ( shell instanceof BIHyperlinkShell ) ) )
      {
        setMouseCursor( MouseCursor.hand );
      }

      formatStatusText().ifPresent(shell::showStatus);
    }
  }

  /**
   * Handle mouseHover event.
   */
  public void mouseHover(BMouseEvent event)
  {
    if (getButtonStyle() == BButtonStyle.toolBar)
    {
      formatHoverText().ifPresent(hoverText -> {
        double x = event.getX();
        double y = event.getY();
        y = Math.max(getHeight() + 1, y + 16);
        UiEnv.get().openBubbleHelp(this, x, y, hoverText);
      });
    }
  }

  /**
   * Determine if user can invoke the action.
   */
  public boolean canInvokeAction(BComponent comp, String actionName)
  {
    try
    {
      BPermissions permissions = BPermissions.all;
      if (comp != null)
        permissions = comp.getPermissions( null );

      Action action = comp.getAction( actionName );
      int flags = comp.getFlags( action );
      if ( ( flags & Flags.OPERATOR ) != 0 )
      {
        return permissions.has(BPermissions.OPERATOR_INVOKE);
      }
      else
      {
        return permissions.has(BPermissions.ADMIN_INVOKE);
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Confirm request.
   */
  public boolean confirm( String title, String msg )
  {
    BWidgetShell shell = getShell();
    if ( shell != null )
    {
      int dlgResponse = BDialog.confirm( shell, title, msg );

      return dlgResponse == BDialog.YES;
    }

    return false;
  }

  private Optional<String> formatHoverText()
  {
    return formatText(getHoverTextFormat(), getDefaultContext());
  }

  private Optional<String> formatStatusText()
  {
    return formatText(getStatusTextFormat(), getDefaultContext());
  }

  /**
   * @param format text format
   * @param cx user/language context
   * @return the formatted text if the text format was present, empty if
   * format was empty
   */
  public Optional<String> formatText(BFormat format, Context cx)
  {
    if (format.equals(BFormat.DEFAULT)) { return Optional.empty(); }

    return Optional.of(format.format(this, cx));
  }

  private static Context getDefaultContext()
  {
    return BUser.getCurrentAuthenticatedUser();
  }
}
