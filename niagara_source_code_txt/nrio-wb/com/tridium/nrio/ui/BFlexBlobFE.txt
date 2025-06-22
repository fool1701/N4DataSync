/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.ByteArrayUtil;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BBlob;
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
 * BFlexBlobFE provides a default implementation of
 * BWbFieldEditor for BBlob. Displays byte array as a hex string
 * in a BTextField.
 *
 * @author    Andy Saunders
 * @creation  20 Sept 2005
 * @version   $Revision: 1$ $Date: 3/28/2005 1:40:35 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BTextField(\"\", 40)",
  override = true
)
public class BFlexBlobFE
  extends BWbFieldEditor
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BFlexBlobFE(1379273797)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

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
  public static final Type TYPE = Sys.loadType(BFlexBlobFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BFlexBlobFE()
  {
    BTextField field = (BTextField)getContent();
    linkTo("linkA", field, BTextField.textModified, setModified);
    linkTo("linkB", field, BTextField.actionPerformed, actionPerformed);
  }

  protected void doSetReadonly(boolean readonly)
  {
    //((BTextField)getContent()).setEditable(!readonly);
    ((BTextField)getContent()).setEditable(false);
  }

  protected void doLoadValue(BObject value, Context context)
  {
    byte[] bytes = ((BBlob)value).copyBytes();
    try
    {
      if( ! context.getFacets().getb("reverse", false) )
      {
        byte[] temp = ((BBlob)value).copyBytes();
        for(int i = 0; i < temp.length; i++)
          bytes[temp.length-1-i] = temp[i];
      }

      if( ! context.getFacets().getb("showAscii", false))
        ((BTextField)getContent()).setText(ByteArrayUtil.toHexString(bytes));
      else
        ((BTextField)getContent()).setText(new String(bytes, StandardCharsets.US_ASCII ));
    }
    catch(Exception e)
    {
      // this shouldn't ever happen
      throw new BajaRuntimeException(e);
    }
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    String str = ((BTextField)getContent()).getText();
    byte[] bytes = str.getBytes();
    if( ! cx.getFacets().getb("reverse", false) )
    {
      byte[] temp = str.getBytes();
      for(int i = 0; i < temp.length; i++)
        bytes[temp.length-1-i] = temp[i];
    }

    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    int i = 0;

    try
    {
      while(i < bytes.length)
      {
        int iVal = TextUtil.hexCharToInt(str.charAt(i++)) & 0x0f;
        if(i < bytes.length)
          iVal = iVal << 4 | TextUtil.hexCharToInt(str.charAt(i++)) & 0x0f;
        bos.write(iVal);
      }
      return BBlob.make(bos.toByteArray());
    }
    catch(Exception e)
    {
      // msg -> Cannot parse "{0}" into a {1}.
      Object[] args = { str, value.getType() };
      String msg = UiLexicon.bajaui().getText("defaultSimplePlugin.error", args);
      throw new CannotSaveException(msg);
    }
  }

}
