/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BAbstractButton;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.BTextField;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.workbench.fieldeditors.BStatusFE.Item;

/**
 * BStatusFE allows viewing and editing of a BStatus.
 * <p/>
 * When the BStatus value is not read-only, the bits set in an Integer facet named "filter" will be
 * used hide the corresponding BStatus value checkboxes.
 *
 * @author    Brian Frank
 * @creation  18 Jul 01
 * @version   $Revision: 8$ $Date: 3/28/05 1:40:36 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Status"
  )
)
public class BStatusFE
  extends BWbFieldEditor
  implements Comparator<Item>
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BStatusFE(3608598718)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStatusFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  @Override
  protected void doSetReadonly(boolean readonly)
  {
    rebuild();
  }  

  @Override
  protected void doLoadValue(BObject value, Context cx)
  {
    status = (BStatus) value;
    rebuild();
  }

  @Override
  protected BObject doSaveValue(BObject value, Context cx)
  {
    int bits = 0;
    for (Item item : items)
    {
      bits = item.save(bits);
    }
    return BStatus.make(bits);
  }

////////////////////////////////////////////////////////////////
// Build
////////////////////////////////////////////////////////////////

  private void rebuild()
  {
    // if readonly then just use a text field
    if (isReadonly())
    {
      String text = status.toString();

      if (getContent() instanceof BTextField)
      {
        // if already created just update text
        BTextField textField = (BTextField) getContent();
        textField.setText(text);
      }
      else
      {
        // other create text field
        int visibleColumns = Math.max(text.length(), MIN_VISIBLE_COLUMNS);
        BTextField textField = new BTextField(text, visibleColumns, NOT_EDITABLE);
        setContent(textField);
      }
      
      items = null;
      return;
    }
    
    // alloc a checkbox for each bit if not initialized yet
    if (items == null)
    {
      Context context = getCurrentContext();
      int statusFilter = context != null ? context.getFacets().geti("filter", ALL_STATUSES) : ALL_STATUSES;

      int[] ordinals = BStatus.ok.getOrdinals();
      items = new ArrayList<>(ordinals.length);
      for (int ordinal : ordinals)
      {
        if ((statusFilter & ordinal) != 0)
        {
          items.add(new Item(ordinal));
        }
      }

      items.sort(this);
      
      BGridPane pane = new BGridPane(items.size());
      for (int i = 0; i < items.size(); ++i)
      {
        Item item = items.get(i);
        pane.add("c" + i, item.checkBox);
        linkTo("lk" + i, item.checkBox, BAbstractButton.actionPerformed, setModified);
      }

      setContent(pane);
    }
    
    // load each bit  
    for (Item item : items)
    {
      item.load(status.getBits());
    }
  }

  @Override
  public int compare(Item x, Item y)
  {
    return Integer.compare(x.ordinal, y.ordinal);
  }
  
////////////////////////////////////////////////////////////////
// Item
////////////////////////////////////////////////////////////////

  static class Item
  {
    public Item(int ordinal)
    {
      this.ordinal = ordinal;
      checkBox = new BCheckBox(BStatus.ok.getDisplayTag(ordinal, null));
    }
    
    public void load(int bits)
    {
      checkBox.setSelected((bits & ordinal) != 0);
    }

    public int save(int bits)
    {
      return checkBox.isSelected() ? (bits | ordinal) : (bits & ~ordinal);
    }
    
    public final int ordinal;
    public final BCheckBox checkBox;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final int MIN_VISIBLE_COLUMNS = 20;
  private static final int ALL_STATUSES = 0xff;
  private static final boolean NOT_EDITABLE = false;

  private List<Item> items;
  private BStatus status = BStatus.ok;
}
