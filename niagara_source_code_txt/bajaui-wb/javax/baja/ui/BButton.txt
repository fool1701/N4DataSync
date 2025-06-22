/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.agent.*;
import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.event.*;

import com.tridium.ui.theme.*;

/**
 * BButton is a control button which fires an 
 * action when pushed.
 *
 * @author    Brian Frank       
 * @creation  17 Nov 00
 * @version   $Revision: 34$ $Date: 5/9/05 3:40:26 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BButton
  extends BAbstractButton
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BButton(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BButton.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct a button with the specified label text.
   */
  public BButton(String text)
  {
    setText(text);    
  }

  /**
   * Construct a button with the specified label text and image.
   */
  public BButton(BImage image, String text)
  {
    setImage(image);
    setText(text);
  }

  /**
   * Constructor with Command.  The label and icon
   * of the command are set using specified flags, and
   * the button is automatically registered with the
   * command.
   */
  public BButton(Command cmd, boolean useLabel, boolean useIcon)
  {    
    setCommand(cmd, useLabel, useIcon);  
  }

  /**
   * Constructor with Command where useLabel and useIcon is true.
   */
  public BButton(Command cmd)
  {    
    setCommand(cmd, true, true);
  }

  /**
   * Construct a button with the specified image.
   */
  public BButton(BImage image)
  {
    setImage(image);
  }

  /**
   * No argument constructor.
   */
  public BButton()
  {
  }

////////////////////////////////////////////////////////////////
// Button
////////////////////////////////////////////////////////////////

  /**
   * Return if this button is the default button 
   * for the window.
   */
  public boolean isDefaultButton() 
  {
    BWidgetShell shell = getShell();
    if (shell != null)
      return shell.getDefaultButton() == this;
    return false;
  }

////////////////////////////////////////////////////////////////
// Keyboard Eventing
////////////////////////////////////////////////////////////////  

  public void keyPressed(BKeyEvent event)
  {
    super.keyPressed(event);
    if (event.getKeyCode() == BKeyEvent.VK_ENTER) 
    { 
      event.consume(); 
      doInvokeAction(new CommandEvent(event)); 
    }
  }

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("kitPx:ActionBinding");
    return agents;
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public void doInvokeAction(CommandEvent event)
  {
    super.doInvokeAction(event);
    if (command != null)
      command.invoke(event);
  }

  AbstractButtonTheme buttonTheme() { return Theme.button(); }

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/button.png");

}
