/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.function.Consumer;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Localizable;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.event.BWindowEvent;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.util.UiLexicon;

import com.tridium.ui.BOptionDialog;
import com.tridium.ui.DialogPeer;
import com.tridium.ui.ShellPeer;
import com.tridium.ui.UiEnv;
import com.tridium.util.ThrowableUtil;

/**
 * BDialog a sub-window of Frame usually displayed to
 * gather some form of input from the user.
 *
 * @author    Brian Frank       
 * @creation  11 Jan 01
 * @version   $Revision: 58$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BDialog
  extends BWindow
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BDialog(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDialog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int OK     = 0x0001;
  public static final int CANCEL = 0x0002;
  public static final int YES    = 0x0004;
  public static final int NO     = 0x0008;
  public static final int YES_NO    = YES | NO;
  public static final int OK_CANCEL = OK | CANCEL;

  public static final BImage QUESTION_ICON = BImage.make("module://icons/x32/question.png");  
  public static final BImage INFO_ICON     = BImage.make("module://icons/x32/info.png");  
  public static final BImage WARNING_ICON  = BImage.make("module://icons/x32/warning.png");  
  public static final BImage ERROR_ICON    = BImage.make("module://icons/x32/error.png");  

  public static final String TITLE_CONFIRM = UiLexicon.bajaui().getText("dialog.confirm");
  public static final String TITLE_MESSAGE = UiLexicon.bajaui().getText("dialog.message");
  public static final String TITLE_INFO    = UiLexicon.bajaui().getText("dialog.info");
  public static final String TITLE_WARNING = UiLexicon.bajaui().getText("dialog.warning");
  public static final String TITLE_ERROR   = UiLexicon.bajaui().getText("dialog.error");

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Convenience method for:
   *   open(parent, title, message, YES_NO, QUESTION_ICON, null)
   */
  public static int confirm(BWidget parent, String title, Object message)
  {
    return open(parent, title, message, YES_NO, QUESTION_ICON, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, TITLE_CONFIRM, message, YES_NO, QUESTION_ICON, null)
   */
  public static int confirm(BWidget parent, Object message)
  {
    return open(parent, TITLE_CONFIRM, message, YES_NO, QUESTION_ICON, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, buttons, QUESTION_ICON, null)
   */
  public static int confirm(BWidget parent, String title, Object message, int buttons)
  {
    return open(parent, title, message, buttons, QUESTION_ICON, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, OK, null, null)
   */
  public static int message(BWidget parent, String title, Object message)
  {
    return open(parent, title, message, OK, null, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, TITLE_MESSAGE, message, OK, null, null)
   */
  public static int message(BWidget parent, Object message)
  {
    return open(parent, TITLE_MESSAGE, message, OK, null, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, OK, INFO_ICON, null)
   */
  public static int info(BWidget parent, String title, Object message)
  {
    return open(parent, title, message, OK, INFO_ICON, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, TITLE_INFO, message, OK, INFO_ICON, null)
   */
  public static int info(BWidget parent, Object message)
  {
    return open(parent, TITLE_INFO, message, OK, INFO_ICON, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, OK, WARNING_ICON, null)
   */
  public static int warning(BWidget parent, String title, Object message)
  {
    return open(parent, title, message, OK, WARNING_ICON, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, OK, WARNING_ICON, details)
   */
  public static int warning(BWidget parent, String title, Object message, String details)
  {
    return open(parent, title, message, OK, WARNING_ICON, details);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, OK, WARNING_ICON, details)
   */
  public static int warning(BWidget parent, String title, Object message, Throwable details)
  {
    return open(parent, title, message, OK, WARNING_ICON, details);
  }

  /**
   * Convenience method for:
   *   open(parent, TITLE_ERROR, message, OK, ERROR_ICON, null)
   */
  public static int error(BWidget parent, Object message)
  {
    return open(parent, TITLE_ERROR, message, OK, ERROR_ICON, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, OK, ERROR_ICON, null)
   */
  public static int error(BWidget parent, String title, Object message)
  {
    return open(parent, title, message, OK, ERROR_ICON, (String)null);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, OK, ERROR_ICON, details)
   */
  public static int error(BWidget parent, String title, Object message, String details)
  {
    return open(parent, title, message, OK, ERROR_ICON, details);
  }

  /**
   * Convenience method for:
   *   open(parent, title, message, OK, ERROR_ICON, details)
   */
  public static int error(BWidget parent, String title, Object message, Throwable details)
  {
    return open(parent, title, message, OK, ERROR_ICON, details);
  }
  
  /**
   * Prompt the user to input a String value.  Return
   * the String if the user hits ok, and null if cancel.
   */
  public static String prompt(BWidget parent, String title, String value, int fieldLength)
  {
    BTextField field = new BTextField(value, fieldLength);
    int result = open(parent, title, field, OK_CANCEL, QUESTION_ICON);
    if (result == CANCEL) return null;
    return field.getText();
  }
  
  /**
   * Convenience method for open which has no details or icon.
   */
  public static int open(BWidget parent, String title, Object content, int buttons)
  {
    return open(parent, title, content, buttons, null, (String)null);
  }

  /**
   * Convenience method for open which has no details.
   */
  public static int open(BWidget parent, String title, Object content, 
                         int buttons, BImage icon)
  {
    return open(parent, title, content, buttons, icon, (String)null);
  }

  /**
   * Convenience method for open which dumps the details
   * exception to a string if it is not null.
   */
  public static int open(BWidget parent, String title, Object content, 
                         int buttons, BImage icon, Throwable details)
  {
    String strDetails = null;
    if (details != null)
    {
      strDetails = ThrowableUtil.dumpToString(details);
      
      // if the details exception is localizable, then 
      // add a line to the content to display the message
      Localizable localizable = ThrowableUtil.toLocalizable(details);
      if (localizable != null)
      {
        String detailSummary = localizable.toString(null);

        if (content instanceof String)
          content = new BLabel((String)content, BHalign.left);
        
        if (content instanceof BLabel)
        {
          BGridPane pane = new BGridPane(1);
          pane.setHalign(BHalign.left);
          pane.add(null, (BWidget)content);
          if(detailSummary != null)
          {
            //NCCB-39574: Add the detailed summary if it is non-null and different from the current BLabel content
            String currentText = ((BLabel)content).getText() == null ? BLabel.text.getDefaultValue().toString() : ((BLabel)content).getText();
            if (!currentText.contains(detailSummary))
            {
              pane.add(null, new BLabel(detailSummary, BHalign.left));
            }
          }
          content = pane;
        }
      }
    }
    
    try
    {
      return open(parent, title, content, buttons, icon, strDetails);
    }
    catch (IllegalArgumentException e)
    {                                                
      System.out.println("ERROR: BDialog.open: " + e);
      if (details != null) details.printStackTrace();    
      throw e;
    }
  }                   
  
  public static int open(BWidget parent, String title, Object content,
                         int buttons, BImage icon, String details)
  {
    return open(parent, title, content, buttons, icon, details, null);
  }

  /**
   * This is the implementation method for all the static
   * factory methods for BDialog.  It opens a model dialog
   * and returns the resulting button which was selected.
   *
   * @param parent this is the widget which is contained by
   *    the BFrame or BDialog to use as an owner for the
   *    dialog.
   * @param title String title of the dialog
   * @param content if this parameter is a BWidget then it
   *    is used for the dialog's content, of if it is a String
   *    then the content is a BLabel displaying the String.
   * @param buttons a bit mask of YES, NO, OK, and CANCEL.
   * @param icon the icon to display on the dialog, or null
   *    if no icon is desired.
   * @param details if not null this String is a multi-line
   *    description that the user can access to view additional
   *    details about the message.  It is designed to be hidden
   *    to prevent confusion among the simple users, but available
   *    for more advanced users or debugging.
   * @param dialogConsumer Starting in Niagara 4.13, the dialog can be referenced just before its opened.
   *    If not null, this Consumer will be given the instance of the dialog before its opened.
   *
   * @return the button which was pressed.  If the window
   *    is closed using the windowing system's default close
   *    mechanism then return the following in the specified
   *    order if available: CANCEL, OK, NO, YES.
   */

  public static int open(BWidget parent, String title, Object content,
    int buttons, BImage icon, String details, Consumer<BDialog> dialogConsumer)
  {
    BOptionDialog dialog = make(parent, title, content, buttons, icon, details);
    dialog.setBoundsCenteredOnOwner();
    if (dialogConsumer != null)
    {
      dialogConsumer.accept(dialog);
    }
    dialog.open();



    //NCCB-5038
    if(dialog.getResult() == 0 && dialog.isShowing())
      dialog.close(CANCEL);
    
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
   * Make an BOptionDialog.
   */
  private static BOptionDialog make(BWidget parent, String title, 
    Object content, int buttons, BImage icon, String details)
  {
    BWidget c = null;
    
    if (content instanceof BWidget)
      c = (BWidget)content;
    else
      c = new BLabel(String.valueOf(content), BHalign.left);
    
    return new BOptionDialog(parent, title, c, buttons, icon, details);
  }

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with owner, title, modal, and content.
   */
  public BDialog(BWidget owner, String title, boolean modal, BWidget content)
  {                             
    super(UiEnv.get().makeDialogPeer(owner, title, modal));
    setContent(content);
    this.owner = owner;
  }

  /**
   * Constructor with owner frame, title, and modal.
   */
  public BDialog(BWidget owner, String title, boolean modal)
  {
    super(UiEnv.get().makeDialogPeer(owner, title, modal));
    this.owner = owner;
  }

  /**
   * No argument constructor.
   */
  public BDialog()
  {
    super(UiEnv.get().makeDialogPeer(null, "", false));
    this.owner = null;
  }

////////////////////////////////////////////////////////////////
// Dialog
////////////////////////////////////////////////////////////////

  /**
   * Get the owner passed to the constructor which is
   * usually a widget in the dialog's parent window.
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
    return ((DialogPeer)getShellPeer()).getTitle();
  }

  /**
   * Set the dialog title.
   */
  public void setTitle(String title)
  {
    ((DialogPeer)getShellPeer()).setTitle(title);
  }
  
  /**
   * Is the dialog modal.
   */
  public boolean isModal()
  {
    return ((DialogPeer)getShellPeer()).isModal();
  }

  /**
   * Set the dialog modal.
   */
  public void setModal(boolean modal)
  {
    ((DialogPeer)getShellPeer()).setModal(modal);
  }

  /**
   * Is the dialog resizable?
   */
  public boolean isResizable()
  {
    return ((DialogPeer)getShellPeer()).isResizable();
  }

  /**
   * Set the resizability of the dialog.
   */  
  public void setResizable(boolean resizable)
  {
    ((DialogPeer)getShellPeer()).setResizable(resizable);
  }
    
  /**
   * This method is called when escape is pressed
   * in the dialog.  The default implementation
   * calls <code>windowClosing(null)</code>.
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
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Set the dialog bounds to be centered on 
   * its owner window.
   */
  public void setBoundsCenteredOnOwner()
  {
    try
    {                         
      ShellPeer peer = getOwner().getShell().getShellPeer();
      setBoundsCenteredOn(peer.getScreenBounds());
    }
    catch(RuntimeException e)
    {                                   
      setBoundsCenteredOnScreen();
    }
  }
    
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////    

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/dialog.png");

////////////////////////////////////////////////////////////////
// Attribute
////////////////////////////////////////////////////////////////

  private BWidget owner;
}
