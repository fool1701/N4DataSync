/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.List;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDropDown;
import javax.baja.ui.BListDropDown;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * A generic field editor for a dropdown list of strings. The subclass need only supply the list of strings.
 *
 * @author Nick Dodd
 * @since Niagara 4.13
 */
@NiagaraType
public abstract class BDropDownFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BDropDownFE(2979906276)1.0$ @*/
/* Generated Wed Nov 09 11:44:52 GMT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDropDownFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BDropDownFE()
  {
    setContent(dropDown);

    linkTo("lk?", dropDown, BDropDown.valueModified, setModified);
    linkTo("lk?", dropDown, BDropDown.actionPerformed, actionPerformed);
  }

  @Override
  protected void doLoadValue(BObject selected, Context context)
    throws Exception
  {
    populateDropDown(selected, context);

    if (selected instanceof BString)
    {
      dropDown.getList().setSelectedItem(selected.as(BString.class).getString());
    }
  }

  @Override
  protected BObject doSaveValue(BObject value, Context cx)
  {
    if (dropDown.getSelectedIndex() >= 0)
    {
      return BString.make(dropDown.getSelectedItem().toString());
    }
    return BString.DEFAULT;
  }

  @Override
  protected void doSetReadonly(boolean readonly)
  {
    dropDown.setDropDownEnabled(!readonly);
  }

  /**
   * Supply the list of strings with which to populate the dropdown.
   * @param selected the currently selected save value, for optional use.
   * @param cx the context.
   * @return the list of strings with which to populate the dropdown.
   */
  protected abstract List<String> getItemList(BObject selected, Context cx);

  private BListDropDown populateDropDown(BObject selected, Context cx)
  {
    dropDown.getList().removeAllItems();
    List<String> itemList = getItemList(selected, cx);
    itemList.forEach(key -> dropDown.getList().addItem(key));
    return dropDown;
  }

  protected final BListDropDown dropDown = new BListDropDown();
}
