/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidgetShell;
import javax.baja.ui.CommandEvent;
import javax.baja.util.BFormat;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbShell;

import com.tridium.platform.BSystemPlatformService;

/**
 * BRebootButton provides a mechanism to restart a station
 *
 * @author    John Huffman
 * @creation  25 Mar 08
 * @version   $Revision$
 * @since     Niagara 3.4
 */

@NiagaraType
/*
 The formatted string to use for the displayed label.
 */
@NiagaraProperty(
  name = "textFormat",
  type = "BFormat",
  defaultValue = "BFormat.make(\"%lexicon(kitPx:command.reboot.label)%\")",
  override = true
)
/*
 Image to display on the label.
 */
@NiagaraProperty(
  name = "image",
  type = "BImage",
  defaultValue = "BImage.make( BIcon.std(\"reboot.png\") )",
  override = true
)
public class BRebootButton
  extends BLocalizableButton
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BRebootButton(1777223217)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "textFormat"

  /**
   * Slot for the {@code textFormat} property.
   * The formatted string to use for the displayed label.
   * @see #getTextFormat
   * @see #setTextFormat
   */
  public static final Property textFormat = newProperty(0, BFormat.make("%lexicon(kitPx:command.reboot.label)%"), null);

  //endregion Property "textFormat"

  //region Property "image"

  /**
   * Slot for the {@code image} property.
   * Image to display on the label.
   * @see #getImage
   * @see #setImage
   */
  public static final Property image = newProperty(0, BImage.make( BIcon.std("reboot.png") ), null);

  //endregion Property "image"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRebootButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Enable/Disable the button based on user permission.
   */
  public void started()
  {
    try
    {
      // disable the button if the user doesn't have permission to invoke the action
      BSystemPlatformService service = getService();
      if ( service != null )
        setEnabled( canInvokeAction( service, "restartStation" ) );
      else
        setEnabled( false );
    }

    catch (Exception e)
    {
      e.printStackTrace();
      setEnabled( false );
    }
  }
  
  /**
   * Invoke the <code>restartStation</code> action.
   */
  public void doInvokeAction(CommandEvent event)
  {
    if ( getConfirmRequired() == true )
    {
      if ( confirm( lex.getText( "command.reboot.title" ), lex.getText( "command.reboot.message" ) ) == false )
      {
        return;
      }
    }

    BSystemPlatformService service = getService();
    if ( service != null )
    {
      service.restartStation();
      // inform the user that a reboot is in progress
      BDialog.info( getShell(), lex.getText( "command.reboot.saving.message" ) );
    }
  }
  
  private BSystemPlatformService getService()
  {
    BWidgetShell shell = getShell();
    if ( ( shell instanceof BWbShell) == false )
    {
      // don't get the service if we are in the PxEditor
      return( null );
    }
    
    BOrd ord = BOrd.make( ( (BWbShell) shell ).getActiveOrd(), "service:platform:SystemPlatformService" );
    return( ( BSystemPlatformService ) ord.get() );
  }

  static final Lexicon lex = Lexicon.make(BRebootButton.class);
}
