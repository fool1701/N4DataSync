/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.naming.ViewQuery;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.options.BMruButton;
import javax.baja.ui.pane.BFlowPane;

@NiagaraType
public class BViewQueryFE extends BDialogFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BViewQueryFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BViewQueryFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  ////////////////////////////////////////////////////////////////
  //Cosntructor
  ////////////////////////////////////////////////////////////////

  public BViewQueryFE() { this(false); }
  /**
   * Constructor.
   * @param buttonOnly true if only the edit button should be shown (the 
   * label and history button will be omitted).
   */
  public BViewQueryFE(boolean buttonOnly)
  {
    BFlowPane pane = new BFlowPane();

    if (!buttonOnly) {
      history = new BMruButton("viewQueryFE");
      history.setMruController(new MruController());
      history.setButtonStyle(BButtonStyle.toolBar);
      pane.add(null, label);
    }

    BWidget editButton = getEditButton();
    pane.add(null, editButton);

    if (!buttonOnly) {
      pane.add(null, history);
    }

    setContent(pane);
  }

  protected void doSetReadonly(boolean readonly)
  {
    history.setEnabled(!readonly);
    getEditButton().setEnabled(!readonly);
  }

  /**
   * The loaded value should be a BString constructed directly from the
   * toString() output of a ViewQuery. If an empty string is received, this
   * field editor will set itself to readonly mode.
   */
  protected void doLoadValue(BObject queryString, Context cx)
  {
    String str = queryString.toString();
    
    String viewQueryId = getViewId(str);
    String paramString = getParamString(str);
    
    if (paramString.length() > MAX_LENGTH)
    {
      label.setText(paramString.substring(0, MAX_LENGTH) + "...");
    }
    else
    {
      label.setText(paramString);
    }

    if (viewQueryId.length() > 0) {
      query = new ViewQuery(str);
    } else if (paramString.length() > 0) {
      query = new ViewQuery("?" + paramString);
    }
  }

  /**
   * Returns a string in valid ViewQuery format. The current parameter string
   * (minus the view ID) will be stored in history for reuse.
   * @see javax.baja.naming.ViewQuery#ViewQuery(String)
   */
  protected BObject doSaveValue(BObject value, Context cx)
      throws Exception
  {
    String str = query.getBody();
    String paramString = getParamString(str);

    try 
    {
      if (paramString.length() > 0)
        history.getMruOptions().save(paramString);
    }
    catch (Exception e) { e.printStackTrace(); }

    return BString.make(str);
  }
  
  /**
   * Parse out the view ID from a view query string. The view ID is the contents
   * of the string up to the first instance of a <code>?</code>. If the string
   * contains no question mark the entire string is the view ID.
   */
  public static String getViewId(String queryString)
  {
    int index = queryString.indexOf("?");
    if (index >= 0) {
      return queryString.substring(0, index);
    } else {
      return queryString;
    }
  }
  
  /**
   * Parse out the parameters from a view query string. The parameter string is
   * the contents of the string after the first instance of a <code>?</code>.
   * If the string contains no question mark, an empty string is returned.
   */
  public static String getParamString(String queryString)
  {
    int index = queryString.indexOf("?");
    if (index >= 0 && index + 1 < queryString.length()) {
      return queryString.substring(index + 1);
    } else {
      return "";
    }
  }

  ////////////////////////////////////////////////////////////////
  //Events
  ////////////////////////////////////////////////////////////////

  public void doEditPressed()
  {
    try
    { 
      String temp = BViewQueryEditor.open(this, query, isReadonly());
      if (temp != null)
      {
        loadValue(BString.make(temp), getCurrentContext());
        setModified();
      }
    }
    catch (Exception e) { BDialog.error(this, "Error", "Error", e); }
  }

  ////////////////////////////////////////////////////////////////
  //MruController
  ////////////////////////////////////////////////////////////////

  class MruController extends BMruButton.MruController
  {
    public void select(String value)
    {
      try
      {
        loadValue(BString.make(viewQueryId + "?" + value), getCurrentContext());
        setModified();
      }
      catch (Exception e) { e.printStackTrace(); }
    }
  }

  private String viewQueryId = "ViewQueryFE";
  private ViewQuery query;
  private BLabel label = new BLabel("");
  private BMruButton history;
  
  private static final int MAX_LENGTH = 50;
}
