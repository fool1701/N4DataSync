/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.io.IOException;

import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.list.BList;
import javax.baja.ui.list.ListController;
import javax.baja.ui.list.ListRenderer;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BConstrainedPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.transfer.Clipboard;
import javax.baja.ui.transfer.TransferEnvelope;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.Lexicon;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.ui.theme.Theme;
import com.tridium.ui.theme.custom.nss.StyleUtils;

/**
 * BOrdListFE is an editor for a BOrdList.  It supports
 * both single-line mode and mutli-line mode.
 * <p>
 * Single-line mode provides a very simple editor
 * that only displays the first ord in the list.
 * <p>
 * Multi-line provides a more sophisticated list-based editor.
 *
 * @author    Mike Jarmy
 * @creation  19 Jan 2005
 * @version   $Revision: 9$ $Date: 11/19/09 4:51:16 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:OrdList"
  )
)
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()",
  override = true
)
@NiagaraAction(
  name = "listSelChanged"
)
public class BOrdListFE
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BOrdListFE(1111729398)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  //endregion Property "content"

  //region Action "listSelChanged"

  /**
   * Slot for the {@code listSelChanged} action.
   * @see #listSelChanged()
   */
  public static final Action listSelChanged = newAction(0, null);

  /**
   * Invoke the {@code listSelChanged} action.
   * @see #listSelChanged
   */
  public void listSelChanged() { invoke(listSelChanged, null, null); }

  //endregion Action "listSelChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrdListFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BOrdListFE()
  {
    list.setMultipleSelection(false);
    list.setController(new Controller(this));

    BGridPane buttons = new BGridPane(2);
    buttons.setHalign(BHalign.left);
    buttons.setValign(BValign.top);
    buttons.add("add",    newButton(add    = new Add(this)));
    buttons.add("remove", newButton(remove = new Remove(this)));

    BEdgePane edge = new BEdgePane();
    edge.setCenter(list);
    edge.setRight(new BBorderPane(buttons, 0, 0, 0, 3));

    textField.computePreferredSize();
    double width = textField.getPreferredWidth();
    double height = list.getRenderer().getItemHeight()*5;

    listCons.setContent(edge);
    listCons.setMinHeight(height);
    listCons.setMaxHeight(height);
    listCons.setMinWidth(width);
  }

////////////////////////////////////////////////////////////////
// action handlers
////////////////////////////////////////////////////////////////

  /**
   * doListSelChanged
   */
  public void doListSelChanged()
  {
    if (!isReadonly())
    {
      int n = list.getSelectedIndex();
      remove.setEnabled(n != -1);
    }
  }

////////////////////////////////////////////////////////////////
// WbFieldEditor
////////////////////////////////////////////////////////////////

  /**
   * doSetReadonly
   */
  protected void doSetReadonly(boolean readonly)
  {
    //disable panes, editors
    list.getSelection().deselectAll();
    if (readonly)
    {
      list.setRenderer(new ReadonlyListRenderer());
    }
    textField.setEditable(!readonly);

    //disable commands
    add.setEnabled(!readonly);
    remove.setEnabled(!readonly);
  }

  /**
   * doLoadValue
   */
  protected void doLoadValue(BObject value, Context context)
  {
    if (get("linkA") != null) remove("linkA");
    if (get("linkB") != null) remove("linkB");
    if (get("linkC") != null) remove("linkC");

    multiLine = getBooleanFacetValue(context, BFacets.MULTI_LINE, false);

    // multi line
    if (multiLine)
    {
      BOrdList ords = (BOrdList) value;

      list.removeAllItems();
      for (int i = 0; i < ords.size(); i++)
        list.addItem(ords.get(i));

      setContent(listCons);

      linkTo("linkC", list, BList.selectionModified, listSelChanged);
    }
    // single line
    else
    {
      try
      {
        textField.setText(value.asSimple().encodeToString());
      }
      catch(IOException e)
      {
        // this shouldn't ever happen
        throw new BajaRuntimeException(e);
      }

      setContent(textField);

      linkTo("linkA", textField, BTextField.textModified, setModified);
      linkTo("linkB", textField, BTextField.actionPerformed, actionPerformed);
    }
  }

  /**
   * doSaveValue
   */
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    // multi line
    if (multiLine)
    {
      BOrd[] ords = new BOrd[list.getItemCount()];
      for (int i = 0; i < list.getItemCount(); i++)
        ords[i] = (BOrd) list.getItem(i);
      return BOrdList.make(ords);
    }
    // single line
    else
    {
      String str = textField.getText();
      try
      {
        return value.asSimple().decodeFromString(str);
      }
      catch(IOException e)
      {
        // msg -> Cannot parse "{0}" into a {1}.
        Object[] args = { str, value.getType() };
        String msg = UiLexicon.bajaui().getText("defaultSimplePlugin.error", args);
        throw new CannotSaveException(msg);
      }
    }
  }

////////////////////////////////////////////////////////////////
// List support classes
////////////////////////////////////////////////////////////////

  /**
   * Controller
   */
  static class Controller extends ListController
  {
    public Controller(BOrdListFE ordListFE)
    {
      this.ordListFE = ordListFE;
    }

    protected void itemDoubleClicked(BMouseEvent event, int index)
    {
      if (!ordListFE.isReadonly())
      {
        BOrd ord = ordListFE.editOrd((BOrd)ordListFE.list.getItem(index), null);
        if (ord != null)
        {
          ordListFE.list.setItem(index, ord);
          ordListFE.setModified();
          ordListFE.repaint();
        }
      }
    }

    @Override
    public void keyPressed(BKeyEvent event)
    {
      super.keyPressed(event);
      if (event.isControlDown() && event.getKeyCode() == BKeyEvent.VK_C)
      {
        Object[] selectedItems = ordListFE.list.getSelectedItems();
        if (selectedItems.length > 0)
        {
          Object payload = selectedItems[0];
          if (payload != null)
          {
            Clipboard.getDefault().setContents(TransferEnvelope.make(payload.toString()));
          }
        }
      }
    }

    BOrdListFE ordListFE;
  }

  static class ReadonlyListRenderer extends ListRenderer
  {
    public ReadonlyListRenderer()
    {
      super();
    }

    @Override
    public BBrush getBackground(Item item)
    {
      BTextField disabledWidget = new BTextField();
      disabledWidget.setEnabled(false);
      return Theme.textEditor().getControlBackground(disabledWidget);
    }
  }

////////////////////////////////////////////////////////////////
// Button Commands
////////////////////////////////////////////////////////////////

  /**
   * Add
   */
  class Add extends Command
  {
    Add(BWidget owner) 
    { 
      super(owner, wbLex, "ordListFE.add"); 
    }

    public CommandArtifact doInvoke()
    {
      BOrd ord = editOrd(
        BOrd.NULL, 
        getCurrentContext());

      if (ord != null)
      {
        list.addItem(ord);
        list.setSelectedIndex(list.getItemCount() - 1);
        setModified();
        repaint();
      }
      return null;
    }
  }

  /**
   * Remove
   */
  class Remove extends Command
  {
    Remove(BWidget owner) 
    { 
      super(owner, wbLex, "ordListFE.remove"); 
    }

    public CommandArtifact doInvoke() 
    {
      int n = list.getSelectedIndex();
      if (n == -1) throw new IllegalStateException();

      list.removeItem(n);
      list.setSelectedIndex(-1);
      int len = list.getItemCount();
      if (len > 0) list.setSelectedIndex((n == len) ? n-1 : n);

      setModified();
      repaint();

      return null;
    }
  }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

  /**
   * getBooleanFacetValue
   */
  private boolean getBooleanFacetValue(Context cx, String facetName, boolean def)
  {
    if (cx == null) return def;
    BBoolean value = (BBoolean)cx.getFacet(facetName);
    return (value == null) ? def : value.getBoolean();
  }

  /**
   * newButton
   */
  private static BButton newButton(Command cmd)
  {
    BButton b = new BButton(cmd, true, true);
    b.setButtonStyle(BButtonStyle.toolBar);
    b.setHalign(BHalign.left);
    return b;
  }

  /**
   * editOrd
   */
  private BOrd editOrd(BOrd ord, Context cx)
  {
    BOrdFE fe = new BOrdFE();
    fe.loadValue(ord, cx);

    int r = BDialog.open(BOrdListFE.this, baja("ord"), fe, BDialog.OK_CANCEL);
    if (r != BDialog.OK) return null;
    
    try { return (BOrd) fe.saveValue(); }
    catch (Exception e) { throw new BajaRuntimeException(e); }
  }

////////////////////////////////////////////////////////////////
// attribs
////////////////////////////////////////////////////////////////

  static Lexicon bajaLex = Lexicon.make("baja");
  static Lexicon wbLex   = Lexicon.make("workbench");
  static String baja(String s) { return bajaLex.getText(s); };

  boolean multiLine = false;

  BTextField textField = new BTextField("", 50);

  BConstrainedPane listCons = new BConstrainedPane();
  BList list = new BList();
  Add add;
  Remove remove;
}
