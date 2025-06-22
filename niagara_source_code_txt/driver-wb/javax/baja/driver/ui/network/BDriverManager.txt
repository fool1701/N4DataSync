/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.network;

import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.HyperlinkInfo;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.BMgrTable;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrTypeInfo;

/**
 * BDriverManager is the default view of BDriverContainer.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 11$ $Date: 10/15/10 5:01:28 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "driver:DriverContainer"
  )
)
public class BDriverManager
  extends BAbstractManager
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.ui.network.BDriverManager(275292606)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDriverManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  protected MgrModel makeModel() { return new Model(this); }
  protected MgrController makeController() { return new Controller(this); }
  
////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////

 class Model extends MgrModel
  {                                  
    Model(BDriverManager mgr) { super(mgr); }
    
    protected String makeTableTitle()
    {
      return TYPE.getDisplayName(null);
    }  
    
    protected MgrColumn[] makeColumns()
    {
      return new MgrColumn[] 
      { 
        new MgrColumn.Name(),
        new MgrColumn.Type(),
        new MgrColumn.Prop(BDeviceNetwork.status),
        new MgrColumn.Prop(BDeviceNetwork.enabled, MgrColumn.EDITABLE),
        new MgrColumn.Prop(BDeviceNetwork.faultCause),
      };
    }
  
    public Type[] getIncludeTypes()
    {                                   
      return new Type[] { BDeviceNetwork.TYPE };
    }                                                              
    
    public MgrTypeInfo[] getNewTypes()
    {
      return MgrTypeInfo.makeArray(BDeviceNetwork.TYPE);
    }

    /**
     * Get the base type supported by the new operation.
     */
    public Type  getBaseNewType()
    {
      return BDeviceNetwork.TYPE;
    }


  }
  
  class Controller extends MgrController
  {
    Controller(BDriverManager mgr) { super(mgr); }
    
    public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
    {                                      
      BComponent comp = table.getComponentAt(row);
      BWbShell shell = getWbShell();
      if (comp != null && shell != null)
        shell.hyperlink(new HyperlinkInfo(comp.getNavOrd(), event));
    }

  }
  
}
