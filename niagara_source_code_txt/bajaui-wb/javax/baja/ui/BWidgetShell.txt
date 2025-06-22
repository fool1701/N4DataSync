/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.ui.*;

/**
 * BWidgetShell models the root of a widget tree which
 * composes a logical shell.  Shells usually map one-to-one
 * with application windows.
 *
 * @author    Brian Frank
 * @creation  7 Jul 01
 * @version   $Revision: 32$ $Date: 1/11/10 4:17:47 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Content is the widget which occupies the center
 of the frame's available space.
 */
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
public class BWidgetShell
  extends BWidget
  implements UndoManager.Scope
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BWidgetShell(3210349976)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * Content is the widget which occupies the center
   * of the frame's available space.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * Content is the widget which occupies the center
   * of the frame's available space.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * Content is the widget which occupies the center
   * of the frame's available space.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWidgetShell.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a BWidgetShell.
   */
  public BWidgetShell()
  {
    if(!(UiEnv.get() instanceof NullUiEnv))
      init(UiEnv.get().makeShellManager(this));
  }

  /**
   * Package private
   */
  protected BWidgetShell(Object peer)
  {
    if(!(UiEnv.get() instanceof NullUiEnv))
      init(UiEnv.get().makeShellManager(this, (ShellPeer)peer));
  }

  void init(ShellManager shellManager)
  {
    this.shellManager = shellManager;
    BWidget[] kids = getChildWidgets();
    for(int i=0; i<kids.length; ++i)
      kids[i].initShell(shellManager);
  }

////////////////////////////////////////////////////////////////
// Shell Access
////////////////////////////////////////////////////////////////

  /**
   * Get the peer which is used to display this shell's
   * widget tree in the native windowing system.
   */
  ShellPeer getShellPeer()
  {
    return shellManager.getShellPeer();
  }

  /**
   * Get the widget with the current focus, or null.
   */
  public BWidget getCurrentFocus()
  {
    return shellManager.getCurrentFocus();
  }

  /**
   * Show a message in the shell's status bar if one
   * is available.  Pass null for message to clear any
   * messages currently displayed by the status bar.
   * This message should be localized for the current
   * user's locale.
   */
  public void showStatus(String message)
  {
  }

  /**
   * Get the default button of this shell or null if no
   * defalt button is installed.
   */
  public BButton getDefaultButton()
  {
    return defaultButton;
  }

  /**
   * Install the default button for this shell.  The button
   * must already be an descendent of this BWidgetShell.  Pass
   * null to uninstall any previous default button.
   */
  public void setDefaultButton(BButton button)
  {
    if (button != null && button.getShell() != this)
      throw new IllegalStateException("Button is not descendent");
    this.defaultButton = button;
  }

  /**
   * Handle when the enter key is pressed.  If there is
   * default button installed then invoke it.
   */
  public void handleEnter()
  {
    BButton defaultButton = getDefaultButton();
    if (defaultButton != null && defaultButton.isEnabled())
      defaultButton.doInvokeAction(null);
  }

  /**
   * Handle when the escape key is pressed.
   */
  public void handleEscape()
  {
  }

  /**
   * Return the widget shell's menu bar or null if a menu
   * bar has not been added to this widget shell.
   */
  public BWidget getMenuBar()
  {
    return menuBar;
  }

  /**
   * Set the widget shell's menu bar
   * @param menuBar
   */
  public void setMenuBar(BWidget menuBar)
  {
    this.menuBar = menuBar;
  }

////////////////////////////////////////////////////////////////
// Scope
////////////////////////////////////////////////////////////////

  /**
   * Get the UndoManager for this shell.
   */
  public UndoManager getInstalledUndoManager()
  {
    return undoManager;
  }

  /**
   * Set the UndoManager for this shell.
   */
  public void setInstalledUndoManager(UndoManager undoManager)
  {
    this.undoManager = undoManager;
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
  {
    BWidget c = getContent();
    c.computePreferredSize();
    setPreferredSize(c.getPreferredWidth(), c.getPreferredHeight());
  }

  public void doLayout(BWidget[] kids)
  {
    double w = getWidth(), h = getHeight();
    BWidget c = getContent();
    c.setBounds(0,0,w,h);
  }

  public void userActivity()
  {
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BButton defaultButton;
  UndoManager undoManager = new UndoManager(this);
  BWidget menuBar;

}
