/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.ui;

import java.util.Comparator;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.status.BStatus;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.BTextField;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BPropagateFlagsFE allows viewing and editing of a BProgagateFlags.
 *
 * @author Andy Saunders on on 3 Aug 04
 * @since Baja 1.0
 */
@NiagaraType
public class BPropagateFlagsFE
  extends BWbFieldEditor 
  implements Comparator<Object>
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.ui.BPropagateFlagsFE(2979906276)1.0$ @*/
/* Generated Wed Jan 05 14:19:45 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPropagateFlagsFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    rebuild();
  }  

  protected void doLoadValue(BObject value, Context cx)
  {          
    this.status = (BStatus)value;
    rebuild();
  }
  
  protected BObject doSaveValue(BObject value, Context cx)
  {
    int bits = 0;
    for(int i=0; i<items.length; ++i)
      bits = items[i].save(bits);
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
      String str = status.toString();                   
      
      // if already created just update text
      if (getContent() instanceof BTextField)
      {
        BTextField text = (BTextField)getContent();
        text.setText(str);
      }                                     
      
      // other create text field
      else
      {                
        int len = Math.max(str.length(), 20);
        BTextField text = new BTextField(str, len, false);
        setContent(text);
      }      
      
      items = null;
      return;
    }                           
    
    // alloc a checkbox for each bit if not initialized yet
    if (items == null)
    {
      int[] ordinals = BStatus.ok.getOrdinals();
      items = new Item[ordinals.length];
      for(int i=0; i<items.length; ++i)
        items[i] = new Item(ordinals[i]);

      SortUtil.sort(items, items, this);
      
      BGridPane pane = new BGridPane(items.length);
      for(int i=0; i<items.length; ++i)
      {         
        Item item = items[i];
        pane.add("c"+i, item.checkBox);
        linkTo("lk"+i, item.checkBox, BCheckBox.actionPerformed, setModified);
      }
        
      setContent(pane);
    }               
    
    // load each bit  
    for(int i=0; i<items.length; ++i)
      items[i].load(status.getBits());
  }                
  
  public int compare(Object x, Object y)
  {                        
    Item a = (Item)x;
    Item b = (Item)y;
    if (a.ordinal < b.ordinal) return -1;
    if (a.ordinal == b.ordinal) return 0;
    return 1;
  }
  
////////////////////////////////////////////////////////////////
// Item
////////////////////////////////////////////////////////////////

  class Item
  {                    
    Item(int ordinal)
    {
      this.ordinal = ordinal;
      this.checkBox = new BCheckBox(BStatus.ok.getDisplayTag(ordinal, null));
      if( (ordinal & (BStatus.UNACKED_ALARM | BStatus.NULL | BStatus.STALE)) != 0 )
        this.checkBox.setEnabled(false);
    }                                                                        
    
    void load(int bits)
    {    
      checkBox.setSelected((bits & ordinal) != 0);
    }

    int save(int bits)
    {
      if (checkBox.isSelected())
        bits |= ordinal;
      else
        bits &= ~ordinal;
      return bits;
    }
    
    int ordinal;
    BCheckBox checkBox;

  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
    
  private Item[] items;   
  private BStatus status = BStatus.ok;
}
