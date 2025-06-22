/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.baja.driver.point.BProxyConversion;
import javax.baja.gx.BImage;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.driver.ui.point.BProxyConversionFE;

/**
 * BNrioProxyConversionFE
 *
 * @author    Andy Saunders
 * @creation  19 Dec 08
 * @version   $Revision: 3$ $Date: 3/23/2005 12:53:26 PM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "listChanged"
)
public class BNrioProxyConversionFE
  extends BWbFieldEditor
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BNrioProxyConversionFE(4158059398)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "listChanged"

  /**
   * Slot for the {@code listChanged} action.
   * @see #listChanged()
   */
  public static final Action listChanged = newAction(0, null);

  /**
   * Invoke the {@code listChanged} action.
   * @see #listChanged
   */
  public void listChanged() { invoke(listChanged, null, null); }

  //endregion Action "listChanged"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioProxyConversionFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BNrioProxyConversionFE()
  {
  	initList();
    linkTo(list, BListDropDown.valueModified, listChanged); // do 1st
    linkTo(list, BListDropDown.valueModified, setModified);
    linkTo(list, BListDropDown.actionPerformed, actionPerformed);

    configPane = new BGridPane(1);
    configPane.setHalign(BHalign.left);

    BEdgePane pane = new BEdgePane();
    pane.setLeft(new BBorderPane(list, 0, 5, 0, 0));
    pane.setCenter(configPane);
    setContent(pane);
  }

  protected void initList()
  {
    list = new BListDropDown();
    for(int i=0; i<kinds.length; ++i)
    {
      list.getList().addItem(kinds[i].icon, kinds[i]);
    }
  }
////////////////////////////////////////////////////////////////
// WbEditor
////////////////////////////////////////////////////////////////

  protected void doSetReadonly(boolean readonly)
  {
    list.setDropDownEnabled(!readonly);
    if (config != null) config.setReadonly(readonly);
  }

  protected void doLoadValue(BObject value, Context cx)
  {
    this.conv = (BProxyConversion)value;
    list.setSelectedItem(toKind(conv));
    loadConfig();
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    if (config != null) conv = (BProxyConversion)config.saveValue();
    return conv;
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  public void doListChanged()
  {
    Kind k = (Kind)list.getSelectedItem();
    if (conv == null || !conv.getType().getTypeInfo().equals(k.type))
      this.conv = (BProxyConversion)k.type.getInstance();
    loadConfig();
  }

  public void loadConfig()
  {
    configPane.removeAll();
    config = null;

    BWbFieldEditor editor = BWbFieldEditor.makeFor(conv, null);
//    System.out.println("loadConfig editor = " + editor.getTypeDisplayName(null));
    if (editor.getType() == TYPE ||
    		editor.getType() == BProxyConversionFE.TYPE ) return;

    config = editor;
    config.loadValue(conv, null);
    config.setReadonly(isReadonly());
    configPane.add("config", config);
    linkTo(config, BWbFieldEditor.pluginModified, setModified);
    linkTo(config, BWbFieldEditor.actionPerformed, actionPerformed);
    relayout();
  }

//  public static  BComponent getComponent(BWidget widget, Context cx)
//    throws Exception
//  {
//	  // To find the component, I have to have a BComponent to link to.
//	  // Walk up the widget hierarchy looking for an BTransferWidget with
//	  // an BComponent as its Mark.
//	  BComplex x = widget.getParent();
//	  while ((x != null))
//	  {
//	  	System.out.println(x.getTypeDisplayName(null));
//	    if (x instanceof BTransferWidget)
//	    {
//	      TransferEnvelope envelope = ((BTransferWidget)x).getTransferData();
//	      if(envelope != null)
//	      {
//	        Mark mark = (Mark) envelope.getData(TransferFormat.mark);
//	        BObject val = mark.getValue(0);
//	        if (val instanceof BComponent)
//	        {
//	          BComponent c = (BComponent) val;
//	          c.lease(2);
//	          return c;
//	        }
//	      }
//	    }
//	    x = x.getParent();
//	  }
//	  return null;
//  }
//

////////////////////////////////////////////////////////////////
// Kind
////////////////////////////////////////////////////////////////

  static class Kind
  {
    Kind(TypeInfo t)
    {
      type = t;
      icon = BImage.make(t.getIcon(null));
      display = t.getDisplayName(null);
    }

    public String toString() { return display; }

    TypeInfo type;
    BImage icon;
    String display;
  }

  static Kind toKind(BProxyConversion c)
  {
    for(int i=0; i<kinds.length; ++i)
      if (kinds[i].type.equals(c.getType().getTypeInfo()))
        return kinds[i];
    throw new IllegalStateException(""+c.getType());
  }

  static Kind[] kinds = new Kind[0];
  static
  {
    HashMap<String,Kind> k = new HashMap<>();
    try
    {
      TypeInfo[] t = Sys.getRegistry().getConcreteTypes(BProxyConversion.TYPE.getTypeInfo());
      for(int i=0; i<t.length; ++i)
      {
        if (t[i].getModuleName().equals("driver") ||
            t[i].getModuleName().equals("nrio"))
        {
          k.put(t[i].getTypeName() + ":" + t[i].getModuleName(), new Kind(t[i]));
        }
      }

      String[] s = Sys.getRegistry().getDefs("driver:ProxyConversion[global]");
      for(int i=0; i<s.length; ++i)
      {
        k.put(s[i], new Kind(Sys.getRegistry().getType(s[i])));
      }

      Collection<Kind> c = k.values();
      kinds = new Kind[c.size()];
      int i = 0;
      for (Iterator<Kind> iterator = c.iterator(); iterator.hasNext();i++)
      {
        kinds[i] = iterator.next();
      }
      SortUtil.sort(kinds);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BListDropDown list;        // drop down of kinds
  BGridPane configPane;      // pane to mount config in
  BWbFieldEditor config;     // field editor for editing conv
  BProxyConversion conv;     // current working value
}
