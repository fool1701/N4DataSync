/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.ui.*;

/**
 * BFrame is a framed window for the native desktop.
 *
 * @author    Brian Frank       
 * @creation  8 Dec 00
 * @version   $Revision: 23$ $Date: 11/13/08 4:33:50 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BFrame
  extends BWindow
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BFrame(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFrame.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with title and content.
   */
  public BFrame(String title, BWidget content)
  {
    super(UiEnv.get().makeFramePeer(title));
    setContent(content);
  }

  /**
   * Constructor with title.
   */
  public BFrame(String title)
  {
    super(UiEnv.get().makeFramePeer(title));
  }

  /**
   * No argument constructor.
   */
  public BFrame()
  {
    super(UiEnv.get().makeFramePeer(""));
  }

////////////////////////////////////////////////////////////////
// Frame
////////////////////////////////////////////////////////////////

  /**
   * Get the frame title.
   */
  public String getTitle()
  {
    return ((FramePeer)getShellPeer()).getTitle();
  }

  /**
   * Set the frame title.
   */
  public void setTitle(String title)
  {
    ((FramePeer)getShellPeer()).setTitle(title);
  }

  /**
   * Gets the icon image for this frame.
   */
  public BImage getIconImage()
  {
    return ((FramePeer)getShellPeer()).getIconGxImage();
  }
  
  /**
   * Sets the image to displayed in the minimized icon 
   * for this frame. 
   */
  public void setIconImage(BImage iconImage)
  {
    ((FramePeer)getShellPeer()).setIconGxImage(iconImage);
  }

  public void setUndecorated(boolean undecorated)
  {  
    ((FramePeer)getShellPeer()).setUndecorated(undecorated);
  }  
    
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////    

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/frame.png");
  
}
