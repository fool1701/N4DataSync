/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.celleditor;

import javax.baja.agent.AgentFilter;
import javax.baja.gx.BBrush;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.workbench.BWbEditor;

import com.tridium.ui.theme.Theme;
import com.tridium.workbench.celltable.CellEditorContainer;

/**
 * BWbCellEditor is an editor designed to view and edit a
 * BObject as a cell in a table-like property sheet.
 *
 * @author    Mike Jarmy
 * @creation  16 May 02
 * @version   $Revision: 2$ $Date: 12/11/07 11:10:07 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWbCellEditor
  extends BWbEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.celleditor.BWbCellEditor(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:48 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbCellEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>makeFor(obj, null)</code>.
   */
  public static BWbCellEditor makeFor(BObject obj)
  {                                               
    return makeFor(obj, null);
  }

  /**
   * Return the registered BWbCellEditor agent for the
   * specified obj, or throw NoSuchAgentException.  If
   * the context contains the the facet CELL_EDITOR, then
   *  use that type explicitly.
   */
  public static BWbCellEditor makeFor(BObject obj, Context cx)
  {
    if (cx != null && cx.getFacet(BFacets.CELL_EDITOR) != null)
    {
      String typeSpec = cx.getFacet(BFacets.CELL_EDITOR).toString();
      return (BWbCellEditor)Sys.getRegistry().getType(typeSpec).getInstance();
    }
    return (BWbCellEditor) obj.getAgents().filter(agentFilter).getDefault().getInstance();
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  /**
   * doLoadValue
   */
  protected void doLoadValue(BObject value, Context context)
    throws Exception
  {
    this.value = value;
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  /**
   * computePreferredSize
   */
  public void computePreferredSize()
  {
    setPreferredSize(75,10);
  }

////////////////////////////////////////////////////////////////
// public
////////////////////////////////////////////////////////////////

  /**
   * cellPopup
   */
  public final void cellPopup(BMouseEvent event)
  {
    if (getParent() instanceof CellEditorContainer)
    {
      ((CellEditorContainer) getParent()).getCellController().showCellPopup(this, event);
    }
  }

  /**
   * cellSelected
   */
  public final void cellSelected()
  {
    if (getParent() instanceof CellEditorContainer)
    {
      ((CellEditorContainer) getParent()).getCellController().cellSelected(this);
    }
  }

  /**
   * paintBackground
   */
  public final void paintBackground(Graphics g)
  {
    BBrush bg = getBackground();
    if (!bg.equals(BBrush.NULL))
    {
      g.setBrush(bg);
      g.fillRect(1, 1, getWidth()-1, getHeight()-1);
    }
  }

  /**
   * getBackground
   */
  public BBrush getBackground()
  {
    return (isReadonly()) ?  
      Theme.widget().getControlBackground() : 
      BBrush.NULL;
  }

////////////////////////////////////////////////////////////////
// access
////////////////////////////////////////////////////////////////

  public int getRowIndex() { return rowIndex; }
  public String getPropertyName() { return propName; }

  public void setRowIndex (int rowIndex) { this.rowIndex = rowIndex; }
  public void setPropertyName (String propName) { this.propName = propName; }

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////

  private static AgentFilter agentFilter = AgentFilter.is(TYPE);   

  private int rowIndex = -1;
  private String propName = "";

  protected BObject value;
}
