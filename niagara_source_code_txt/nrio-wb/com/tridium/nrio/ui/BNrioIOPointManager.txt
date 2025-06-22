/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import javax.baja.control.BBooleanPoint;
import javax.baja.control.BControlPoint;
import javax.baja.gx.BImage;
import javax.baja.log.Log;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.CommandArtifact;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;
import javax.baja.workbench.mgr.MgrTypeInfo;

import com.tridium.nrio.components.BInputOutputModulePoints;
import com.tridium.nrio.components.BNrioIOPointEntry;
import com.tridium.nrio.enums.BNrioIoTypeEnum;
import com.tridium.nrio.points.BNrioProxyExt;


/**
 * BNrioIOPointManager uses the BAbstractLearn framework to
 * provide a way for the user to create proxy points within
 * a Nrio Input Output Module
 *
 * @author    Andy Saunders
 * @creation  12 Jan 2006
 * @version   $Revision$ $Date$
 * @since     Niagara 3
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "nrio:NrioIOPoints", "nrio:NrioIOPointFolder" },
    requiredPermissions = "r"
  )
)
public class BNrioIOPointManager 
extends BNrioPointManager
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BNrioIOPointManager(4009947180)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrioIOPointManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public Class<?> getPointEntryClass()
  {
    return BNrioIOPointEntry.class;
  }


////////////////////////////////////////////////////////////////
// Support 
////////////////////////////////////////////////////////////////
 
  protected MgrModel makeModel() { return new Model(this); }
  protected MgrLearn makeLearn() { return new Learn(this); }    
  protected MgrState makeState() { return new State(); }    
  protected MgrController makeController() { return new Controller(this); }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  public class Controller extends BNrioPointManager.Controller
  {
    Controller(BNrioIOPointManager manager){ super(manager); }

    public CommandArtifact doDiscover(Context context)
    throws Exception
    {            
      allDescendants.setSelected(true);
      super.doDiscover(context);
      updateDiscoveryRows(new BInputOutputModulePoints());
      return null;
    }

  }


////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////

  public class Model extends BNrioPointManager.Model
  {
    Model(BNrioIOPointManager mgr) { super(mgr); }

    protected MgrColumn[] makeColumns()
    {           
      return cols;
    }

  }    

////////////////////////////////////////////////////////////////
// Learn
////////////////////////////////////////////////////////////////
  class Learn extends BNrioPointManager.Learn
  {
    Learn(BNrioIOPointManager mgr) { super(mgr); } 

    protected MgrColumn[] makeColumns()
    {                     
      return new MgrColumn[] 
                           { 
          new MgrColumn.Name(),
          new MgrColumn.Prop(BNrioIOPointEntry.ioType, 0),
          new MgrColumn.Prop(BNrioIOPointEntry.instance, 0),
          new MgrColumn.Prop(BNrioIOPointEntry.usedByPoint, 0)
                           };
    }

    public MgrTypeInfo[] toTypes(Object discovery)
    throws Exception
    {
      BNrioIOPointEntry disc = (BNrioIOPointEntry)discovery;
      if(disc.getIoType().equals(BNrioIoTypeEnum.digitalInput))
        return diType;
      if(disc.getIoType().equals(BNrioIoTypeEnum.supervisedDigitalInput))
        return sdiType;
      return roType;
    }

    public BImage getIcon(Object dis)
    {
      return booleanIcon;
    }

    public void toRow(Object discovery, MgrEditRow row)
    throws Exception
    {
      BNrioIOPointEntry entry = (BNrioIOPointEntry) discovery;
      row.setDefaultName(entry.getName());        
      row.setCell(colInstance, BInteger.make(entry.getInstance()));
      row.setCell(colIsSdi, BBoolean.make(entry.getIoType().equals(BNrioIoTypeEnum.supervisedDigitalInput)));
    }

    public boolean isExisting(Object discovery, BComponent component)
    {
      try {
        BNrioIOPointEntry entry = (BNrioIOPointEntry) discovery;
        if(entry.matches(component))
        {
          entry.setUsedByPoint(component.getParent().getDisplayName(null)+ "." + component.getDisplayName(null));
          return true;
        }
        entry.setUsedByPoint("");
        return false;
      }
      catch(Throwable e){
        return false;
      }
    }    
  }

  static class NrioMgrTypeInfo extends MgrTypeInfo
  {                        
    NrioMgrTypeInfo(TypeInfo pntType, boolean isSdi)
    {
      this.pntType = pntType;
      this.isSdi   = isSdi;
    }

    public TypeInfo getPointTypeInfo() {return pntType; }
    public boolean isSdi() {return isSdi; }

    public BImage getIcon() 
    { 
      return null;
    }

    public String getDisplayName() 
    { 
      return pntType.getDisplayName(null);
    }


    public BComponent newInstance() 
    { 
      BControlPoint pnt = (BControlPoint) pntType.getInstance();
      BNrioProxyExt ext = new BNrioProxyExt();

      if(pnt instanceof BBooleanPoint)
      {
        ext.setIsSdi(isSdi);
        pnt.setProxyExt(ext);
        pnt.setFacets(BFacets.makeBoolean("Active", "Inactive"));

      }
      return pnt;
    }

    public boolean isMatchable(BComponent db) 
    { 
      return false;
    }

    private TypeInfo pntType;
    private boolean isSdi;
  }
////////////////////////////////////////////////////////////////
// State
////////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////
// Implementation
//////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////
// Constants
//////////////////////////////////////////////////////////////


//////////////////////////////////////////////////////////////
// Attributes
//////////////////////////////////////////////////////////////

  MgrColumn colName = new MgrColumn.Name();
  MgrColumn colType = new MgrColumn.Type(MgrColumn.EDITABLE);
  MgrColumn colInstance   = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BNrioProxyExt.instance }, MgrColumn.EDITABLE);
  MgrColumn colIsSdi      = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BNrioProxyExt.isSdi    }, MgrColumn.EDITABLE | MgrColumn.READONLY | MgrColumn.UNSEEN);
  MgrColumn colConversion = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BNrioProxyExt.conversion }, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colFacets = new MgrColumn.Prop(BControlPoint.facets, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colString = new MgrColumn.ToString("Value", 0);
  MgrColumn[] cols =
  {
      colName, colType, colInstance, colIsSdi, colConversion, colString, colFacets
  };

  static final Log log = Log.getLog("nrio");  

  private static final NrioMgrTypeInfo[] diType  = {new NrioMgrTypeInfo(Sys.getRegistry().getType("control:BooleanPoint"   ), false)}; 
  private static final NrioMgrTypeInfo[] sdiType = {new NrioMgrTypeInfo(Sys.getRegistry().getType("control:BooleanPoint"   ), true )}; 
  private static final NrioMgrTypeInfo[] roType  = {new NrioMgrTypeInfo(Sys.getRegistry().getType("control:BooleanWritable"), false)}; 

}
