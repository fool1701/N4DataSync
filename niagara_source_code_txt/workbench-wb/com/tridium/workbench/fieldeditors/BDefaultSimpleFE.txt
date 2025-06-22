/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.io.IOException;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.util.UiLexicon;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BDefaultSimpleFE provides a default implementation of
 * BWbFieldEditor for BSimples which uses the BSimple's
 * encodeToString() and decodeFromString() methods to view
 * and edit the BSimple using a BTextField.
 *
 * @author    Brian Frank
 * @creation  3 May 01
 * @version   $Revision: 5$ $Date: 6/22/10 1:28:17 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Simple"
  )
)
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BTextField(\"\", 40)",
  override = true
)
public class BDefaultSimpleFE
  extends BWbFieldEditor
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BDefaultSimpleFE(2997231262)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BTextField("", 40), null);

  //endregion Property "content"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDefaultSimpleFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BDefaultSimpleFE()
  {
    BTextField field = (BTextField)getContent();
    linkTo("linkA", field, BTextField.textModified, setModified);
    linkTo("linkB", field, BTextField.actionPerformed, actionPerformed);
  }

  protected void doSetReadonly(boolean readonly)
  {
    ((BTextField)getContent()).setEditable(!readonly);
  }

  protected void doLoadValue(BObject value, Context context)
  {
    try
    {
      ((BTextField)getContent()).setText(value.asSimple().encodeToString());
    }
    catch(IOException e)
    {
      // this shouldn't ever happen
      throw new BajaRuntimeException(e);
    }
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    String str = ((BTextField)getContent()).getText();
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
