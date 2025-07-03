/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.gx.BTransform;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.list.BList;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BConstrainedPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BTransformFE.
 *
 * @author    Mike Jarmy
 * @creation  07 Oct 04
 * @version   $Revision: 6$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "gx:Transform"
  )
)
@NiagaraAction(
  name = "transformSelectionChanged"
)
@NiagaraAction(
  name = "typeChanged"
)
@NiagaraAction(
  name = "field1Modified"
)
@NiagaraAction(
  name = "field2Modified"
)
public class BTransformFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BTransformFE(3999021082)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "transformSelectionChanged"

  /**
   * Slot for the {@code transformSelectionChanged} action.
   * @see #transformSelectionChanged()
   */
  public static final Action transformSelectionChanged = newAction(0, null);

  /**
   * Invoke the {@code transformSelectionChanged} action.
   * @see #transformSelectionChanged
   */
  public void transformSelectionChanged() { invoke(transformSelectionChanged, null, null); }

  //endregion Action "transformSelectionChanged"

  //region Action "typeChanged"

  /**
   * Slot for the {@code typeChanged} action.
   * @see #typeChanged()
   */
  public static final Action typeChanged = newAction(0, null);

  /**
   * Invoke the {@code typeChanged} action.
   * @see #typeChanged
   */
  public void typeChanged() { invoke(typeChanged, null, null); }

  //endregion Action "typeChanged"

  //region Action "field1Modified"

  /**
   * Slot for the {@code field1Modified} action.
   * @see #field1Modified()
   */
  public static final Action field1Modified = newAction(0, null);

  /**
   * Invoke the {@code field1Modified} action.
   * @see #field1Modified
   */
  public void field1Modified() { invoke(field1Modified, null, null); }

  //endregion Action "field1Modified"

  //region Action "field2Modified"

  /**
   * Slot for the {@code field2Modified} action.
   * @see #field2Modified()
   */
  public static final Action field2Modified = newAction(0, null);

  /**
   * Invoke the {@code field2Modified} action.
   * @see #field2Modified
   */
  public void field2Modified() { invoke(field2Modified, null, null); }

  //endregion Action "field2Modified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTransformFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTransformFE()
  {
    ///////////////////////////////////////////
    // left side

    transforms = new BList();
    transforms.setMultipleSelection(false);

    BConstrainedPane transPane = new BConstrainedPane(transforms);
    transPane.setMinHeight(transforms.getRenderer().getItemHeight()*5);
    transPane.setMaxHeight(transforms.getRenderer().getItemHeight()*5);

    BGridPane buttons = new BGridPane(4);
    btnAdd    = newButton(new Add(this));
    btnRemove = newButton(new Remove(this));
    btnUp     = newButton(new Up(this));
    btnDown   = newButton(new Down(this));
    buttons.add(null, btnAdd);
    buttons.add(null, btnRemove);
    buttons.add(null, btnUp);
    buttons.add(null, btnDown);

    BEdgePane left = new BEdgePane();
    left.setCenter(transPane);
    left.setBottom(new BBorderPane(buttons, 5, 0, 0, 0));

    ///////////////////////////////////////////
    // right side

    types = new BListDropDown();
    types.getList().addItem(text("translate"));
    types.getList().addItem(text("scale"));
    types.getList().addItem(text("rotate"));
    types.getList().addItem(text("skewX"));
    types.getList().addItem(text("skewY"));

    label1 = new BLabel("???", BHalign.right);
    label2 = new BLabel("???", BHalign.right);
    field1 = new BTextField("", 10);
    field2 = new BTextField("", 10);

    double width = labelMaxWidth();
    BConstrainedPane cons1 = new BConstrainedPane(label1);
    BConstrainedPane cons2 = new BConstrainedPane(label2);
    cons1.setMinWidth(width);
    cons2.setMinWidth(width);

    BGridPane fields = new BGridPane(2);
    fields.setHalign(BHalign.left);
    fields.setValign(BValign.top);
    fields.add(null, cons1);
    fields.add(null, field1);
    fields.add(null, cons2);
    fields.add(null, field2);

    BEdgePane right = new BEdgePane();
    right.setTop(types);
    right.setCenter(new BBorderPane(fields, 5, 0, 0, 0));

    right.computePreferredSize();
    BConstrainedPane rightPane = new BConstrainedPane(right);
    rightPane.setMinWidth(right.getPreferredWidth());

    ///////////////////////////////////////////
    // done

    BEdgePane contents = new BEdgePane();
    contents.setLeft(left);
    contents.setRight(new BBorderPane(rightPane, 0, 0, 0, 5));
    setContent(contents);

    linkTo(null, types, BListDropDown.listActionPerformed, typeChanged);
    linkTo(null, transforms, BList.selectionModified, transformSelectionChanged);
    linkTo("lnkField1Modified", field1, BTextField.textModified, field1Modified);
    linkTo("lnkField2Modified", field2, BTextField.textModified, field2Modified);
  }

  /**
   * labelMaxWidth
   */
  double labelMaxWidth()
  {
    BLabel temp = new BLabel(text("x"));
    temp.computePreferredSize();
    double max = temp.getPreferredWidth();

    temp = new BLabel(text("y"));
    temp.computePreferredSize();
    max = Math.max(max, temp.getPreferredWidth());

    temp = new BLabel(text("angle"));
    temp.computePreferredSize();
    max = Math.max(max, temp.getPreferredWidth());

    return max;
  }

////////////////////////////////////////////////////////////////
// actions
////////////////////////////////////////////////////////////////

  /**
   * doTransformSelectionChanged
   */
  public void doTransformSelectionChanged()
  {
    int n = transforms.getSelectedIndex();

    if (!isReadonly())
    {
      btnRemove.setEnabled(!(n == -1));
      btnUp.setEnabled(    !((n == -1) || (n == 0)));
      btnDown.setEnabled(  !((n == -1) || (n == transforms.getItemCount()-1)));
    }

    if (n == -1)
    {
      types.setVisible(false);
      label1.setVisible(false);
      label2.setVisible(false);
      field1.setVisible(false);
      field2.setVisible(false);
    }
    else
    {
      types.setVisible(true);
      BTransform.Transform t = (BTransform.Transform) transforms.getItem(n);
      types.getList().setSelectedIndex(t.getTransformCase() - 1);
      editTransform(t);
    }

    relayout();
  }

  /**
   * editTransform
   */
  void editTransform(BTransform.Transform t)
  {
    remove("lnkField1Modified");
    remove("lnkField2Modified");

    switch(t.getTransformCase())
    {
      case BTransform.TRANSLATE:
        label1.setVisible(true);
        label2.setVisible(true);
        label1.setText(text("x"));
        label2.setText(text("y"));

        field1.setVisible(true);
        field2.setVisible(true);
        BTransform.Translate trans = (BTransform.Translate) t;
        field1.setText(Double.toString(trans.getX()));
        field2.setText(Double.toString(trans.getY()));
        break;

      case BTransform.SCALE:
        label1.setVisible(true);
        label2.setVisible(true);
        label1.setText(text("x"));
        label2.setText(text("y"));

        field1.setVisible(true);
        field2.setVisible(true);
        BTransform.Scale scale = (BTransform.Scale) t;
        field1.setText(Double.toString(scale.getX()));
        field2.setText(Double.toString(scale.getY()));
        break;

      case BTransform.ROTATE:
        label1.setVisible(true);
        label2.setVisible(false);
        label1.setText(text("angle"));

        field1.setVisible(true);
        field2.setVisible(false);
        BTransform.Rotate rotate = (BTransform.Rotate) t;
        field1.setText(Double.toString(rotate.getAngle()));
        break;

      case BTransform.SKEW_X:
        label1.setVisible(true);
        label2.setVisible(false);
        label1.setText(text("angle"));

        field1.setVisible(true);
        field2.setVisible(false);
        BTransform.SkewX skewX = (BTransform.SkewX) t;
        field1.setText(Double.toString(skewX.getAngle()));
        break;

      case BTransform.SKEW_Y:
        label1.setVisible(true);
        label2.setVisible(false);
        label1.setText(text("angle"));

        field1.setVisible(true);
        field2.setVisible(false);
        BTransform.SkewY skewY = (BTransform.SkewY) t;
        field1.setText(Double.toString(skewY.getAngle()));
        break;

      default:
        throw new IllegalStateException();
    }

    linkTo("lnkField1Modified", field1, BTextField.textModified, field1Modified);
    linkTo("lnkField2Modified", field2, BTextField.textModified, field2Modified);
  }

  /**
   * doTypeChanged
   */
  public void doTypeChanged()
  {
    BTransform.Transform t = makeTransform(types.getSelectedIndex() + 1);
    transforms.setItem(transforms.getSelectedIndex(), t);
    editTransform(t);

    setModified();
    repaint();
  }

  /**
   * makeTransform
   */
  private static BTransform.Transform makeTransform(int n)
  {
    switch(n)
    {
      case BTransform.TRANSLATE: return new BTransform.Translate(0, 0);
      case BTransform.SCALE:     return new BTransform.Scale(0, 0);
      case BTransform.ROTATE:    return new BTransform.Rotate(0);
      case BTransform.SKEW_X:    return new BTransform.SkewX(0);
      case BTransform.SKEW_Y:    return new BTransform.SkewY(0);
      default: throw new IllegalStateException();
    }
  }

  /**
   * doField1Modified
   */
  public void doField1Modified()
  {
    int n = transforms.getSelectedIndex();
    BTransform.Transform t = (BTransform.Transform) transforms.getItem(n);

    switch(t.getTransformCase())
    {
      case BTransform.TRANSLATE: 
        BTransform.Translate trans = (BTransform.Translate) t;
        t = new BTransform.Translate(atod(field1.getText()), trans.getY());
        break;

      case BTransform.SCALE:    
        BTransform.Scale scale = (BTransform.Scale) t;
        t = new BTransform.Scale(atod(field1.getText()), scale.getY());
        break;

      case BTransform.ROTATE:    
        t = new BTransform.Rotate(atod(field1.getText()));
        break;

      case BTransform.SKEW_X:    
        t = new BTransform.SkewX(atod(field1.getText()));
        break;

      case BTransform.SKEW_Y:    
        t = new BTransform.SkewY(atod(field1.getText()));
        break;

      default: throw new IllegalStateException();
    }

    transforms.setItem(n, t);
    setModified();
    repaint();
  }

  /**
   * doField2Modified
   */
  public void doField2Modified()
  {
    int n = transforms.getSelectedIndex();
    BTransform.Transform t = (BTransform.Transform) transforms.getItem(n);

    switch(t.getTransformCase())
    {
      case BTransform.TRANSLATE: 
        BTransform.Translate trans = (BTransform.Translate) t;
        t = new BTransform.Translate(trans.getX(), atod(field2.getText()));
        break;

      case BTransform.SCALE:    
        BTransform.Scale scale = (BTransform.Scale) t;
        t = new BTransform.Scale(scale.getX(), atod(field2.getText()));
        break;

      default: throw new IllegalStateException();
    }

    transforms.setItem(n, t);
    setModified();
    repaint();
  }

  /**
   * atod
   */
  double atod(String text)
  {
    try
    {
      return (Double.valueOf(text)).doubleValue();
    }
    catch (NumberFormatException e)
    {
      return 0;
    }    
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  /**
   * setReadonly
   */
  protected void doSetReadonly(boolean readonly)
  {
    btnAdd.setEnabled(false);
    btnRemove.setEnabled(false);
    btnUp.setEnabled(false);
    btnDown.setEnabled(false);
    types.setEnabled(false);
    field1.setEnabled(false);
    field2.setEnabled(false);
  }  

  /**
   * doLoadValue
   */
  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {                       
    BTransform t = (BTransform) value;

    BTransform.Transform[] forms = t.getTransforms();
    transforms.removeAllItems();
    for (int i = 0; i < forms.length; i++)
      transforms.addItem(forms[i]);

    transforms.getSelection().deselectAll();
    if (forms.length > 0) transforms.getSelection().select(0);
  }

  /**
   * doSaveValue
   */
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {         
    int len = transforms.getItemCount();
    BTransform.Transform[] forms = new BTransform.Transform[len];
    for (int i = 0; i < len; i++)
      forms[i] = (BTransform.Transform) transforms.getItem(i);

    return BTransform.make(forms);
  }

////////////////////////////////////////////////////////////////
// Button Commands
////////////////////////////////////////////////////////////////

  /**
   * Add
   */
  class Add
    extends Command
  {
    Add(BWidget owner) 
    { 
      super(owner, null, add, null, null);
    }

    public CommandArtifact doInvoke() 
    {
      transforms.addItem(new BTransform.Translate(0, 0));
      transforms.getSelection().deselectAll();
      transforms.getSelection().select(transforms.getItemCount() - 1);

      transforms.relayout();
      setModified();
      return null;
    }
  }

  /**
   * Remove
   */
  class Remove
    extends Command
  {
    Remove(BWidget owner) 
    {
      super(owner, null, remove, null, null);
    }

    public CommandArtifact doInvoke()
    {
      int n = transforms.getSelectedIndex();
      if (n == -1) throw new IllegalStateException(); 

      transforms.removeItem(n);

      transforms.getSelection().deselectAll();
      int len = transforms.getItemCount();
      if (len > 0) transforms.getSelection().select((n == len) ? n-1 : n);

      transforms.relayout();
      setModified();
      return null;
    }
  }

  /**
   * Up
   */
  class Up
    extends Command
  {
    Up(BWidget owner) 
    { 
      super(owner, null, up, null, null);
    }

    public CommandArtifact doInvoke() 
    {
      int n = transforms.getSelectedIndex();
      if ((n == -1) || (n == 0))
        throw new IllegalStateException();

      Object item = transforms.getItem(n);
      transforms.removeItem(n);
      transforms.insertItem(n-1, item);
      transforms.getSelection().deselectAll();
      transforms.getSelection().select(n-1);

      transforms.relayout();
      setModified();
      return null;
    }
  }

  /**
   * Down
   */
  class Down
    extends Command
  {
    Down(BWidget owner) 
    {
      super(owner, null, down, null, null);
    }

    public CommandArtifact doInvoke()
    {
      int n = transforms.getSelectedIndex();
      if ((n == -1) || (n == transforms.getItemCount()-1))
        throw new IllegalStateException();

      Object item = transforms.getItem(n);
      transforms.removeItem(n);
      transforms.insertItem(n+1, item);
      transforms.getSelection().deselectAll();
      transforms.getSelection().select(n+1);

      transforms.relayout();
      setModified();
      return null;
    }
  }

  /**
   * newButton
   */
  static BButton newButton(Command cmd)
  {
    BButton b = new BButton(cmd, true, true);
    b.setHalign(BHalign.left);
    return b;
  }

////////////////////////////////////////////////////////////////
// Static
////////////////////////////////////////////////////////////////

  static Lexicon lex = Lexicon.make("workbench");
  static String text(String s) { return lex.getText("transformFE." + s); }
  
  static final BImage add    = BImage.make("module://icons/x16/add.png");
  static final BImage remove = BImage.make("module://icons/x16/delete.png");
  static final BImage up     = BImage.make("module://icons/x16/arrowUp.png");
  static final BImage down   = BImage.make("module://icons/x16/arrowDown.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BList transforms;
  
  BButton btnAdd;
  BButton btnRemove;
  BButton btnUp;
  BButton btnDown;

  BListDropDown types;
  BLabel label1;
  BLabel label2;
  BTextField field1;
  BTextField field2;
}
