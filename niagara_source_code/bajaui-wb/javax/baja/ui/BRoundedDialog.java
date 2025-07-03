/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.event.BWindowEvent;

import com.tridium.ui.BRoundedPopup;
import com.tridium.ui.DialogPeer;
import com.tridium.ui.ShellPeer;
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
public class BRoundedDialog
    extends BRoundedWindow
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BRoundedDialog(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRoundedDialog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
//  Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Convenience method for open which has no arc width or height
   */
  public static int open(BWidget parent, String title, Object content, int buttons)
  {
    return open(parent, title, content, DEFAULT_ARC_WIDTH, DEFAULT_ARC_HEIGHT,buttons);
  }
  
  /**
   * This is the implementation method for all the static
   * factory methods for BRoundedDialog.  It opens a model dialog
   * and returns the resulting button which was selected.
   *
   * @param parent 
   *          - This is the widget which is contained by
   *            the BFrame or BDialog to use as an owner for the
   *            rounded dialog.
   * @param title 
   *          - String title of the Rounded dialog.
   * @param content 
   *          - if this parameter is a BWidget then it 
   *            is used for the dialog's content; if it is a String
   *            then the content is a BLabel displaying the String.
   * @param buttons 
   *          - a bit mask of YES, NO, OK, and CANCEL.   
   *          
   * @return the button which was pressed.  If the window
   *    is closed using the windowing system's default close
   *    mechanism then return the following in the specified
   *    order if available: CANCEL, OK, NO, YES.
   */
  public static int open(BWidget parent, String title, Object content, 
                         float arcWidth, float arcHeight, int buttons)
  {
    BRoundedPopup dialog = make(parent, title, content, arcWidth, arcHeight,buttons);
    dialog.setBoundsCenteredOnOwner();
    dialog.open();
    
    // unparent content
    if (content instanceof BWidget)
    {
      BWidget w = (BWidget)content;
      if (w.getPropertyInParent() != null)
      {
        BWidget wp = w.getParentWidget();
        wp.set(w.getPropertyInParent(), new BNullWidget());
      }
    }
    
    return dialog.getResult();
  }
  
  /**
   * Make a {@link BRoundedPopup}.
   */
  private static BRoundedPopup make(BWidget parent, String title, 
    Object content, float arcWidth, float arcHeight, int buttons)
  {
    BWidget c = null;
    
    if (content instanceof BWidget)
      c = (BWidget)content;
    else
      c = new BLabel(String.valueOf(content), BHalign.left);
    
    return new BRoundedPopup(parent, c, title, arcWidth, arcHeight,buttons);
  }
////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Constructor with owner, title, modal, content, arc width and height.
   */
  public BRoundedDialog(BWidget owner, String title, boolean modal, BWidget content, float arcWidth, float arcHeight)
  {                             
    super(UiEnv.get().makeRoundedDialogPeer(owner, title, modal, arcWidth, arcHeight));
    setContent(content);
    this.owner = owner;
  }
  
  /**
   * Constructor with owner, title, modal, and content.
   */
  public BRoundedDialog(BWidget owner, String title, boolean modal, BWidget content)
  {                             
    super(UiEnv.get().makeRoundedDialogPeer(owner, title, modal, DEFAULT_ARC_WIDTH, DEFAULT_ARC_HEIGHT));
    setContent(content);
    this.owner = owner;
  }

  /**
   * Constructor with owner frame, title, modal, arc width and height.
   */
  public BRoundedDialog(BWidget owner, String title, boolean modal, float arcWidth, float arcHeight)
  {
    super(UiEnv.get().makeRoundedDialogPeer(owner, title, modal, arcWidth, arcHeight));
    this.owner = owner;
  }

  
  /**
   * Constructor with owner frame, title, and modal.
   */
  public BRoundedDialog(BWidget owner, String title, boolean modal)
  {
    super(UiEnv.get().makeRoundedDialogPeer(owner, title, modal, DEFAULT_ARC_WIDTH, DEFAULT_ARC_HEIGHT));
    this.owner = owner;
  }

  /**
   * No argument constructor.
   */
  public BRoundedDialog()
  {
    super(UiEnv.get().makeRoundedDialogPeer(null, "", false, DEFAULT_ARC_WIDTH, DEFAULT_ARC_HEIGHT));
    this.owner = null;
  }
////////////////////////////////////////////////////////////////
//  Dialog
////////////////////////////////////////////////////////////////

  /**
   * Get the owner passed to the constructor which is usually a widget 
   * in the dialog's parent window.
   */
  public BWidget getOwner()
  {
    return owner;
  }

  /**
   * Get the dialog title.
   */
  public String getTitle()
  {
    return ((DialogPeer) getShellPeer()).getTitle();
  }

  /**
   * Set the dialog title.
   */
  public void setTitle(String title)
  {
    ((DialogPeer) getShellPeer()).setTitle(title);
  }

  /**
   * Is the dialog modal.
   */
  public boolean isModal()
  {
    return ((DialogPeer) getShellPeer()).isModal();
  }

  /**
   * Set the dialog modal.
   */
  public void setModal(boolean modal)
  {
    ((DialogPeer) getShellPeer()).setModal(modal);
  }

  /**
   * Is the dialog resizable?
   */
  public boolean isResizable()
  {
    return ((DialogPeer) getShellPeer()).isResizable();
  }

  /**
   * Set the resizability of the dialog.
   */
  public void setResizable(boolean resizable)
  {
    ((DialogPeer) getShellPeer()).setResizable(resizable);
  }

  /**
   * This method is called when escape is pressed in the dialog. The default implementation calls
   * <code>windowClosing(null)</code>.
   */
  public void handleEscape()
  {
    windowClosing(null);
  }

  public void windowClosing(BWindowEvent event)
  {
    close();
  }

////////////////////////////////////////////////////////////////
//  Layout
////////////////////////////////////////////////////////////////

  /**
   * Set the dialog bounds to be centered on its owner window.
   */
  public void setBoundsCenteredOnOwner()
  {
    try
    {
      ShellPeer peer = getOwner().getShell().getShellPeer();
      setBoundsCenteredOn(peer.getScreenBounds());
    }
    catch (RuntimeException e)
    {
      setBoundsCenteredOnScreen();
    }
  }  
  
////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  public static final int OK = 0x0001;
  public static final int CANCEL = 0x0002;
  public static final int YES = 0x0004;
  public static final int NO = 0x0008;
  public static final int YES_NO = YES | NO;
  public static final int OK_CANCEL = OK | CANCEL;  
  
////////////////////////////////////////////////////////////////
//Attribute
////////////////////////////////////////////////////////////////

  private BWidget owner;
  
}
