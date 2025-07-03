/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BTextDropDown;
import javax.baja.user.BUser;
import javax.baja.user.BUserPrototypes;
import javax.baja.user.BUserService;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.workbench.util.WbUtil;

/**
 * UserPrototypeFE presents the list of user prototypes defined
 * in the user service of the target station.
 * 
 * @author    John Sublett
 * @creation  05 Sep 2007
 * @version   $Revision: 2$ $Date: 10/9/07 12:00:24 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BUserPrototypeFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BUserPrototypeFE(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserPrototypeFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BUserPrototypeFE()
  {
    setContent(field);
    linkTo("la", field, BTextDropDown.valueModified,   setModified);
    linkTo("lb", field, BTextDropDown.actionPerformed, actionPerformed);
  }
  
  protected void doSetReadonly(boolean readonly)
  { 
    field.getEditor().setEditable(!readonly);
    field.setDropDownEnabled(!readonly);
  }

  protected void doLoadValue(BObject value, Context cx)
  {                 
    build(value.toString());
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {  
    return BString.make(field.getText().trim());
  }
  
  private void build(String value)
  {
    field.setText(value);
    field.getList().removeAllItems();
    
    try
    {
      BComponent[] protos = loadPrototypes();
      field.getList().addItem(new Item());
    
      for (int i = 0; i < protos.length; i++)
      {
        field.getList().addItem(new Item(protos[i]));
        if (protos[i].getName().equals(value))
          field.getList().setSelectedIndex(i);
      }
    }
    catch(Exception ex)
    {
      ex.printStackTrace();
    }
  }

  /**
   * Load the prototypes from the user service of the current session.
   */
  private BComponent[] loadPrototypes()
    throws Exception
  {
    BUserService service = (BUserService)WbUtil.findService(this, BUserService.TYPE);
    if (service == null) return new BUser[0];
    BUserPrototypes prototypes = service.getUserPrototypes();
    prototypes.lease();
    return prototypes.getPrototypes();
  }

////////////////////////////////////////////////////////////////
// Item
////////////////////////////////////////////////////////////////

  static class Item
  {
    Item()
    {
      name = "";
      icon = BImage.NULL;
    }
  
    Item(BComponent prototype)
    {
      name = prototype.getName();
      icon = BImage.make(prototype.getIcon());
    }
    
    public String toString() { return name; }
    
    String name;
    BImage icon;
  }




////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  BTextDropDown field = new BTextDropDown("", 20, true);

}
