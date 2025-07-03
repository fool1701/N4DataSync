/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ui.FramePeer;
import com.tridium.ui.UiEnv;

/**
 * 
 *
 * @author 		J. Spangler
 * @creation 	Apr 27, 2011
 * @version		1
 * @since			Niagara 3.7
 *
 */
@NiagaraType
public class BRoundedFrame
    extends BRoundedWindow
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BRoundedFrame(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRoundedFrame.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructors
////////////////////////////////////////////////////////////////
  
  public BRoundedFrame(BWidget content,String title, float arcWidth, float arcHeight)
  {
    super(UiEnv.get().makeRoundedFramePeer(title, arcWidth, arcHeight));
    setContent(content);
  }
  
  public BRoundedFrame(BWidget content,float arcWidth, float arcHeight)
  {
    super(UiEnv.get().makeRoundedFramePeer("", arcWidth, arcHeight));
    setContent(content);
  }
  
  public BRoundedFrame(BWidget content)
  {
    super(UiEnv.get().makeRoundedFramePeer("", DEFAULT_ARC_WIDTH, DEFAULT_ARC_HEIGHT));
    setContent(content);
  }
  
  public BRoundedFrame(BWidget content,String title)
  {
    super(UiEnv.get().makeRoundedFramePeer(title, DEFAULT_ARC_WIDTH, DEFAULT_ARC_HEIGHT));
    setContent(content);
  }
  
  public BRoundedFrame()
  {
    super(UiEnv.get().makeRoundedFramePeer("", DEFAULT_ARC_WIDTH, DEFAULT_ARC_HEIGHT)); 
  }
  
////////////////////////////////////////////////////////////////
//  Frame
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the frame title.
   */
  public String getTitle()
  {
    return ((FramePeer) getShellPeer()).getTitle();
  }

  /**
   * Set the frame title.
   */
  public void setTitle(String title)
  {
    ((FramePeer) getShellPeer()).setTitle(title);
    //TODO: set our title widget label with title text
  }
}
