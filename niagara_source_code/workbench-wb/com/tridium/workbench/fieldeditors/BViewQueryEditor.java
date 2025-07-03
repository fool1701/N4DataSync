/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.baja.naming.ViewQuery;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.IllegalNameException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.options.BMruButton;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BConstrainedPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.table.TableSelection;
import javax.baja.util.Lexicon;
import javax.baja.workbench.celleditor.BWbCellEditor;

import com.tridium.util.ObjectUtil;
import com.tridium.workbench.celltable.BCellTable;

/**
 * Dialog editor for view query parameters, used in the ORD field editor. 
 * Borrows heavily from Facets dialog editor. 
 * <p>
 * This editor does not store the view query ID in the history, only the 
 * parameter string. This allows similar
 * sets of view parameters to be applied to different view types.
 * 
 * @author Logan Byam
 * @creation 13 Jun 2013
 * @since Niagara 3.8
 */
@NiagaraType
@NiagaraAction(
  name = "tableSelectionModified"
)
public class BViewQueryEditor extends BEdgePane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BViewQueryEditor(1012093694)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "tableSelectionModified"

  /**
   * Slot for the {@code tableSelectionModified} action.
   * @see #tableSelectionModified()
   */
  public static final Action tableSelectionModified = newAction(0, null);

  /**
   * Invoke the {@code tableSelectionModified} action.
   * @see #tableSelectionModified
   */
  public void tableSelectionModified() { invoke(tableSelectionModified, null, null); }

  //endregion Action "tableSelectionModified"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BViewQueryEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Convenience for <code>BViewQueryEditor.open(owner, query, false)</code>.
   */
  public static String open(BWidget owner, ViewQuery query)
    throws Exception
  {
    return open(owner, query, false);
  }

  /**
   * Open BViewQueryEditor in dialog using the default title. Return new 
   * ViewQuery, or null if dialog was cancelled.
   */
  public static String open(BWidget owner, ViewQuery query, boolean readonly)
    throws Exception
  {
    String title = wbLex.getText("viewQueryFE.dialogTitle");
    return open(owner, title, query, readonly);
  }
  
  /**
   * Open BViewQueryEditor in dialog. Return new query, or
   * null if dialog was cancelled.
   */
  public static String open(BWidget owner, String title, ViewQuery query, 
      boolean readonly)
    throws Exception
  {
    BViewQueryEditor editor = new BViewQueryEditor(query, readonly);    

    int r = BDialog.open(owner, title, editor, BDialog.OK_CANCEL);
    if (r != BDialog.OK) return null;  

    return editor.save();
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /* fw use only */
  public BViewQueryEditor()
  {
    throw new IllegalStateException();
  }

  public BViewQueryEditor(ViewQuery query, boolean readonly)
  {
    // set up the cell table
    table = new BCellTable(
      new String[] { lexKey, lexValue },
      new double[] { 120, 75 });
    tableSel = table.getSelection();
    table.setCellsEnabled(!readonly);
    this.readonly = readonly;

    loadViewQuery(query);
    
    BConstrainedPane tablePane = new BConstrainedPane(table);
    tablePane.setMinHeight(150);
    tablePane.setMinWidth(300);

    // MRU
    history = new BMruButton("viewQueryFE");
    history.setMruController(new MruController());
    
    // set up the buttons
    BGridPane buttons = new BGridPane(4);
    buttons.setHalign(BHalign.right);
    buttons.add(null, btnAdd = newButton(new Add(this)));
    buttons.add(null, btnRemove = newButton(new Remove(this)));
    buttons.add(null, history);
    
    btnRemove.setEnabled(false);
    btnAdd.setEnabled(!readonly);  

    BEdgePane buttonPane = new BEdgePane();
    buttonPane.setCenter(buttons);

    // glue it all together
    BEdgePane edge = new BEdgePane();
    edge.setCenter(tablePane);
    edge.setBottom(new BBorderPane(buttonPane, 3, 0, 6, 0)); 
    setCenter(edge);

    // done
    linkTo(
      "linkTable", table, 
      BCellTable.selectionModified, 
      tableSelectionModified);
  } 

  /**
   * newButton
   */
  private static BButton newButton(Command cmd)
  {
    BButton b = new BButton(cmd, true, true);
    b.setHalign(BHalign.left);
    return b;
  }
  
////////////////////////////////////////////////////////////////
// public
////////////////////////////////////////////////////////////////

  /**
   * Assemble the table rows into a view query string.
   * @return a valid ViewQuery string
   * @throws Exception if the table contains any characters that are illegal
   * for a ViewQuery
   * @see javax.baja.naming.ViewQuery#ViewQuery(String)
   */
  public String save() throws Exception
  {
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < rows.size(); i++)
    {
      Row row = rows.get(i);
      BString key = (BString) row.keyCe.saveValue();
      BString value = (BString) row.valCe.saveValue();
      
      if (key.getString().length() == 0)
      {
        throw new IllegalNameException("baja", "IllegalNameException.name", new Object[] { key });
      }
      
      if (i > 0) 
      {
        sb.append(";");
      }
      
      sb.append(key).append("=").append(value);
    }
    
    String params = sb.toString();
    
    //ensure successful parse - exception thrown here will be shown to user
    ViewQuery q = new ViewQuery(viewQueryId + "?" + params);
    
    //add param string only to history
    history.getMruOptions().save(params);
    
    //return complete view query string
    return q.getBody();
  }

////////////////////////////////////////////////////////////////
// actions
////////////////////////////////////////////////////////////////

  /**
   * tableSelectionModified
   */
  public void doTableSelectionModified()
  {
    // Only attempt to enable remove button if our editor is not in 
    // read only state. See issue: 19615
    if( !readonly)
    {
      int n = tableSel.getRow();
      btnRemove.setEnabled(!(n == -1));
    }
  }
  
////////////////////////////////////////////////////////////////
//private methods
////////////////////////////////////////////////////////////////

  /**
   * Remove all rows from the table.
   */
  private void clearTable() 
  {
    while(rows.size()>0)
    {
      rows.remove(0);
      table.removeRow(0);
    }

    tableSel.deselectAll();
    table.relayout();
  }
  
  /**
   * Parse a view query parameter string (e.g. 
   * <code>key1=a;key2=b;key3=c</code>). Returns a String => String ordered map.
   */
  private SortedMap<String, String> parseParamString(String paramString)
  {
    SortedMap<String, String> map = new TreeMap<>();
    for (String param : paramString.split(";"))
    {
      String[] kv = param.split("=");
      map.put(kv[0], kv.length == 2 ? kv[1] : "");
    }
    return map;
  }
  
  /**
   * Parse out the view parameters from the query and load them in as table
   * rows.
   */
  private void loadViewQuery(ViewQuery query) 
  {
    if(query != null)
    {
      viewQueryId = query.getViewId();
      String body = query.getBody();
      int index = body.indexOf("?");
      
      if (index >= 0) {
        String paramString = body.substring(index + 1);
        Map<String, String> paramMap = parseParamString(paramString);
        
        for (Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator(); it.hasNext();)
        {
          Map.Entry<String, String> entry = it.next();
          String k = entry.getKey(), v = entry.getValue();
          table.addRow(new Row(k, BString.make(v), false).toArray());
        }
      }
    }
  }
  
  /**
   * Check to see if any existing rows in the table contain this key already.
   */
  private boolean containsKey(String key) 
  {
    try
    {
      for (int i = 0; i < rows.size(); i++)
      {
        Row row = rows.get(i);
        String k = row.keyCe.saveValue().toString();
        if (k.equals(key)) return true;
      }
      return false;
    }
    catch (Exception e)
    {
      throw new BajaRuntimeException(e);
    }
  }


////////////////////////////////////////////////////////////////
// Button Commands
////////////////////////////////////////////////////////////////

  /**
   * Add
   */
  private class Add 
    extends Command
    implements ObjectUtil.NameContainer
  {
    Add(BWidget owner) { super(owner, wbLex, "viewQueryFE.add"); }

    /** NameContainer */
    public boolean contains(String name)
    {                   
      return containsKey(name);
    }

    /** Command */
    public CommandArtifact doInvoke() 
    {
      int count = table.getRowCount();

      table.addRow(
        new Row(
          ObjectUtil.generateUniqueSlotName("key", this),
          BString.DEFAULT,
          false).toArray());

      tableSel.deselectAll();
      tableSel.select(count - 1);
      table.relayout();

      return null;
    }
  }

  /**
   * Remove
   */
  private class Remove extends Command
  {
    Remove(BWidget owner) 
    {
      super(owner, wbLex, "viewQueryFE.remove"); 
    }

    public CommandArtifact doInvoke()
    {
      if(readonly)return null;
      int n = tableSel.getRow();
      if (n == -1) throw new IllegalStateException(); 

      rows.remove(n);
      table.removeRow(n);
      tableSel.deselectAll();
      
      int len = table.getRowCount();
      if (len > 0) tableSel.select((n == len) ? n-1 : n);
      table.relayout();

      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Row
////////////////////////////////////////////////////////////////

  /**
   * Row
   */
  private class Row
  {
    Row(String key, BObject value, boolean readOnly)
    {
      BString k = BString.make(key);
      
      this.keyCe = BWbCellEditor.makeFor(k);
      this.valCe = BWbCellEditor.makeFor(value);

      keyCe.setReadonly(readOnly);
      valCe.setReadonly(readOnly);

      keyCe.loadValue(k);
      valCe.loadValue(value);

      rows.add(this);
    }

    BWbCellEditor[] toArray()
    {
      return new BWbCellEditor[] { keyCe, valCe };
    }

    BWbCellEditor keyCe;
    BWbCellEditor valCe;
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
       clearTable();
       // Add view query from history
       loadViewQuery(new ViewQuery(viewQueryId + "?" + value));
     }
     catch (Exception e) { e.printStackTrace(); }
   }
 }  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static Lexicon wbLex = Lexicon.make("workbench");
  private static String lexKey = wbLex.get("viewQueryFE.key", "Key");
  private static String lexValue = wbLex.get("viewQueryFE.value", "Value");

  private BCellTable table;
  private TableSelection tableSel;
  private BButton btnRemove;
  private BButton btnAdd;
  private BMruButton history;
  
  private String viewQueryId = "ViewQueryEditor";

  private Array<Row> rows = new Array<>(Row.class);
  private boolean readonly = false;
}
