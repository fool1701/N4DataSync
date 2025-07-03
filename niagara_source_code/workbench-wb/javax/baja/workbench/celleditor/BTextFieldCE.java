/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.celleditor;

import javax.baja.gx.BBrush;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.text.TextRenderer;

import com.tridium.workbench.cellmini.BMiniTextField;

/**
 * BTextFieldCE 
 *
 * @author    Mike Jarmy
 * @creation  13 Aug 02
 * @version   $Revision: 1$ $Date: 8/15/07 3:39:00 PM EDT$
 * @since     Baja 1.0
 */
 
@NiagaraType
@NiagaraAction(
  name = "textModified"
)
public abstract class BTextFieldCE
  extends BWbCellEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.celleditor.BTextFieldCE(4176348111)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "textModified"

  /**
   * Slot for the {@code textModified} action.
   * @see #textModified()
   */
  public static final Action textModified = newAction(0, null);

  /**
   * Invoke the {@code textModified} action.
   * @see #textModified
   */
  public void textModified() { invoke(textModified, null, null); }

  //endregion Action "textModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTextFieldCE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTextFieldCE()
  {
    textField.setRenderer(new TextRenderer() {
      public BBrush getBackground() {            
        return BTextFieldCE.this.getBackground();
      }});

    add("txtFld", textField);
    linkTo("linkMod", textField, BMiniTextField.textModified, textModified);
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  /**
   * paint
   */
  public void paint(Graphics g)
  {
    paintBackground(g);

    super.paint(g);    
  }
  
  /**
   * doLayout
   */
  public void doLayout(BWidget[] children)
  {
    textField.setBounds(2, 2, getWidth()-4, getHeight()-2);
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  /**
   * doSetReadonly
   */
  protected void doSetReadonly(boolean readonly)
  {
    textField.setEditable(!readonly);
  }

  /**
   * load the value into a copy
   */
  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {
    super.doLoadValue(value, cx);
    
    remove("linkMod");
    textField.setText(valueToString(value, cx));
    linkTo("linkMod", textField, BMiniTextField.textModified, textModified);
  }

  protected abstract String valueToString(BObject value, Context cx);

////////////////////////////////////////////////////////////////
// actions
////////////////////////////////////////////////////////////////

  public void doTextModified()
  {
    setModified();
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public BTextField getTextField() { return textField; }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BMiniTextField textField = new BMiniTextField();
}
