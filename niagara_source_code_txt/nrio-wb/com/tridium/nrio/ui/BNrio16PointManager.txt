/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.ui;

import java.util.ArrayList;

import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.control.BIWritablePoint;
import javax.baja.control.BNumericWritable;
import javax.baja.data.BIDataValue;
import javax.baja.driver.point.BDefaultProxyConversion;
import javax.baja.driver.point.BProxyConversion;
import javax.baja.gx.BImage;
import javax.baja.log.Log;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BInteger;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BTextField;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.pane.BGridPane;
import javax.baja.units.BUnit;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrController;
import javax.baja.workbench.mgr.MgrEdit;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrLearn;
import javax.baja.workbench.mgr.MgrModel;
import javax.baja.workbench.mgr.MgrState;
import javax.baja.workbench.mgr.MgrTypeInfo;

import com.tridium.nrio.components.BUIPointEntry;
import com.tridium.nrio.conv.BNrioThermistorType3Conversion;
import com.tridium.nrio.enums.BNrioIoTypeEnum;
import com.tridium.nrio.enums.BUniversalInputTypeEnum;
import com.tridium.nrio.ext.BLinearCalibrationExt;
import com.tridium.nrio.points.BNrio16ModulePoints;
import com.tridium.nrio.points.BNrio16ProxyExt;
import com.tridium.nrio.points.BNrio34ModulePoints;
import com.tridium.nrio.points.BNrio34PriSecPoints;
import com.tridium.nrio.points.BNrioProxyExt;
import com.tridium.nrio.points.BNrioResistiveInputProxyExt;
import com.tridium.nrio.points.BNrioVoltageInputProxyExt;
import com.tridium.nrio.points.BUiProxyExt;


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
    types = { "nrio:Nrio16Points", "nrio:Nrio16PointFolder" },
    requiredPermissions = "r"
  )
)
public class BNrio16PointManager extends BNrioPointManager
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.ui.BNrio16PointManager(4172393046)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNrio16PointManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
  public Class<?> getPointEntryClass()
  {
    return BUIPointEntry.class;
  }

////////////////////////////////////////////////////////////////
// Support 
////////////////////////////////////////////////////////////////
 
  protected MgrModel makeModel() { return new Io16Model(this); }
  protected MgrLearn makeLearn() { return new Io16Learn(this); }    
  protected MgrState makeState() { return new State(); }    
  protected MgrController makeController() { return new Io16Controller(this); }
    
////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////

  public class Io16Controller extends BNrioPointManager.Controller
  {
    Io16Controller(BNrio16PointManager manager)
    { 
      super(manager); 
    }

    
    public CommandArtifact doDiscover(Context context)
      throws Exception
    {
      allDescendants.setSelected(true);
      super.doDiscover(context);
      if(deviceExt instanceof BNrio34PriSecPoints)
        updateDiscoveryRows(new BNrio34ModulePoints());
      else
        updateDiscoveryRows(new BNrio16ModulePoints());
      return null;
    }

    /**
     * Prompt the user with the list of new types returned by
     * BAbstractManager.getNewTypes() and return a MgrEdit
     * with the default instances.
     */
    public MgrEdit promptForNew(Context cx)
      throws Exception
    {
      MgrModel model = getManager().getModel();

      MgrTypeInfo[] types = model.getNewTypes();
      if (types == null)
      {
        BDialog.error(getManager(), "Must override BAbstractManager.getNewTypes()");
        return null;
      }

      // build input fields
      BListDropDown typeField = new BListDropDown();
      for(int i=0; i<types.length; ++i)
        typeField.getList().addItem(types[i].getIcon(), types[i]);
      typeField.setSelectedIndex(0);
      BTextField countField = new BTextField("1", 6);

      // build input pane
      BGridPane grid = new BGridPane(2);
      grid.add(null, new BLabel(lex.getText("add.type")));
      grid.add(null, typeField);
      grid.add(null, new BLabel(lex.getText("add.count")));
      grid.add(null, countField);

      // prompt
      String title = newCommand.getLabel();
      int r = BDialog.open(getManager(), title, grid, BDialog.OK_CANCEL);
      if (r == BDialog.CANCEL) return null;

      // extract input
      NrioMgrTypeInfo type = (NrioMgrTypeInfo)typeField.getSelectedItem();
      int count = Integer.parseInt(countField.getText());

      // create edit list
      MgrEdit edit = makeEdit(title);
      BControlPoint newPoint = (BControlPoint)model.newInstance(type);
      if(type.pntType.is(BNumericWritable.TYPE) )
      	types = BNrio16PointManager.aoType;
      else if( type.pntType.is(BBooleanWritable.TYPE)    )
      	types = BNrio16PointManager.roType;
      else
      {
      	types = BNrio16PointManager.uiTypes;
      	int uiType = ((BUiProxyExt)newPoint.getProxyExt()).getUiType().getOrdinal();
      	switch(uiType)
      	{
      	case BUniversalInputTypeEnum.AI_0TO_10_VDC: types = BNrio16PointManager.uiViTypes; break;
      	case BUniversalInputTypeEnum.DI_HIGH_SPEED: types = BNrio16PointManager.uiCiTypes; break;
      	case BUniversalInputTypeEnum.DI_NORMAL    : types = BNrio16PointManager.uiDiTypes; break;
    	  case BUniversalInputTypeEnum.AI_RESISTIVE : 
    	  	if(type.getConverter() == null) types = BNrio16PointManager.uiRiTypes; 
    	  	else types = BNrio16PointManager.uiTiTypes;
    	  	break;
    	  default: types = BNrio16PointManager.uiTypes;
        }
      }
      
      for(int i=0; i<count; ++i)
        edit.addRow(new MgrEditRow(model.newInstance(type), null, types));
      return edit;
    }

    
  }

  static final Lexicon lex = Lexicon.make(MgrController.class);

////////////////////////////////////////////////////////////////
// Model
////////////////////////////////////////////////////////////////
  public class Io16Model extends BNrioPointManager.Model
  {
    Io16Model(BNrio16PointManager mgr) { super(mgr); }
    protected MgrColumn[] makeColumns()
    {           
      return cols;
    }

    /**
     * Overridden to add the appropriate proxy extension
     */
    public BComponent newInstance(MgrTypeInfo type)
      throws Exception
    {                  
      NrioMgrTypeInfo nrioType = (NrioMgrTypeInfo) type;
      
      BControlPoint pt = (BControlPoint) nrioType.newInstance();
      
      return pt;
    } 
    

    
    public MgrTypeInfo[] getNewTypes()
    {
//    	System.out.println("getNetTypes() called.");
      ArrayList<MgrTypeInfo> list = new ArrayList<>();
      list.add(aoType[0]);
      list.add(roType[0]);
      for(int i = 0; i < uiTypes.length; i++)
        list.add(uiTypes[i]);
      return list.toArray(new MgrTypeInfo[list.size()]);
    }

  }    

////////////////////////////////////////////////////////////////
// Learn
////////////////////////////////////////////////////////////////
  class Io16Learn extends BNrioPointManager.Learn
  {
    Io16Learn(BNrio16PointManager mgr) { super(mgr); } 
    
    protected MgrColumn[] makeColumns()
    {                     
      return new MgrColumn[] 
      { 
        new MgrColumn.Name(),
        new MgrColumn.Prop(BUIPointEntry.ioType, 0),
        new MgrColumn.Prop(BUIPointEntry.instance, 0),
        new MgrColumn.Prop(BUIPointEntry.usedByPoint, 0)
      };
    }
    
    public BImage getIcon(Object dis)
    {
    	BUIPointEntry entry = (BUIPointEntry) dis;
      
      if (entry.getIoType() == BNrioIoTypeEnum.universalInput)
        return mixIcon;
      else if (entry.getIoType() == BNrioIoTypeEnum.analogOutput)
        return floatIcon;
      else
        return booleanIcon;
    }

    
    public MgrTypeInfo[] toTypes(Object discovery)
    throws Exception
    {
//    	System.out.println("toTypes() called.");

      BUIPointEntry disc = (BUIPointEntry)discovery;
      if(disc.getIoType().equals(BNrioIoTypeEnum.universalInput))
        return uiTypes;
      if(disc.getIoType().equals(BNrioIoTypeEnum.analogOutput))
        return aoType;
      return roType;
    }

    public void toRow(Object discovery, MgrEditRow row)
      throws Exception
    {
      BUIPointEntry entry = (BUIPointEntry) discovery;
      row.setDefaultName(entry.getName());        
      row.setCell(colInstance, BInteger.make(entry.getInstance()));
      //row.setCell(colType, entry.getIoType());
//      System.out.println("entry.getIoType() = " + entry.getIoType());
    }

    /**
     * If the specified discovery object is already mapped into
     * the station database as an existing component then return
     * it, otherwise return null.  Subclasses should override
     * <code>isExisting(Object, BComponent)</code>. 
     */
    public boolean isExisting(Object discovery, BComponent component)
    {
      BUIPointEntry entry = (BUIPointEntry) discovery;
      try 
      {
        if(component == null || component.getName() == null)
        {
          entry.setUsedByPoint("");
          return false;
        }
        else if (entry.matches(component))
        {
          entry.setUsedByPoint(component.getParent().getDisplayName(null)+ "." + component.getDisplayName(null));
          return true;
        }
        entry.setUsedByPoint("");
        return false;
        
      }
      catch(Exception e)
      {
        e.printStackTrace();
        entry.setUsedByPoint("");
        return false;
      }
    }    
  }

  static class NrioMgrTypeInfo extends MgrTypeInfo
  {                        
	  NrioMgrTypeInfo(TypeInfo pntType, TypeInfo extType, BUniversalInputTypeEnum uiType, BProxyConversion converter, String displayKey)
    {
      this.extType = extType; 
      this.pntType = pntType;
      this.uiType  = uiType;
      this.converter = converter;
      this.displayName = gpioLex.getText(displayKey);
      
      if (this.converter == null)
        this.converter = BDefaultProxyConversion.DEFAULT;
    }
    
	  NrioMgrTypeInfo(BComponent c)
    {
      BControlPoint cp = (BControlPoint) c;
      BUiProxyExt cpe = (BUiProxyExt) cp.getProxyExt();

      pntType = cp.getType().getTypeInfo();
      extType = cpe.getType().getTypeInfo();
      uiType  = (BUniversalInputTypeEnum)cpe.getUiType();
      converter = cpe.getConversion();
      
      if (converter instanceof BNrioThermistorType3Conversion)
      {
        displayName = gpioLex.getText("type.UiThermistorInput");  
      }
      else
      {
        if (pntType.is(BIWritablePoint.TYPE))
          displayName = gpioLex.getText("type." + extType.getTypeName() + "Writable");
        else
          displayName = gpioLex.getText("type." + extType.getTypeName() + "Point");
      }
    }

    public String getDisplayName() 
    { 
      return displayName;
    }

    public TypeInfo getPointTypeInfo() 
    { 
      return pntType; 
    }

    public TypeInfo getExtTypeInfo() 
    { 
      return extType; 
    }
    
    public BProxyConversion getConverter()
    {
      return converter; 
    }
    
    public BImage getIcon() 
    { 
      return null;
    }

    public BComponent newInstance() 
    { 
      BControlPoint pnt = (BControlPoint) pntType.getInstance();
      BNrio16ProxyExt ext = (BNrio16ProxyExt) extType.getInstance();
      
      ext.setConversion(converter);
      pnt.setProxyExt(ext);     

      BFacets facets = pnt.getFacets();
      if (converter instanceof BNrioThermistorType3Conversion)
      {
        facets = BFacets.make(facets, BFacets.UNITS, BUnit.getUnit("celsius"));
      }
      else{
        BIDataValue facetsValue = (BIDataValue)ext.getDeviceFacets().get(BFacets.UNITS);
        if (facetsValue != null)
        {
          facets = BFacets.make(facets, BFacets.UNITS, facetsValue);
        }
      }
      pnt.setFacets(facets);
      if (ext instanceof BNrioVoltageInputProxyExt)
      {
        pnt.add("linearCalibration", new BLinearCalibrationExt(BUnit.getUnit("volt")), 0);
        
      }

      if (ext instanceof BNrioResistiveInputProxyExt)
      {
        if (converter instanceof BNrioThermistorType3Conversion)
        {
          pnt.add("linearCalibration", new BLinearCalibrationExt(BUnit.getUnit("celsius")), 0);
        }
        else
        {
          pnt.add("linearCalibration", new BLinearCalibrationExt(BUnit.getUnit("ohm")), 0);
        }
      }
      
      return pnt;
    }

    public boolean isMatchable(BComponent db) 
    { 
      return false;
    }
    
    private TypeInfo extType;
    private TypeInfo pntType;
    private String displayName;
    private BUniversalInputTypeEnum uiType;
    private BProxyConversion converter;
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
  MgrColumn colInstance   = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BUiProxyExt.instance }, MgrColumn.EDITABLE);
  MgrColumn colUiType     = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BUiProxyExt.uiType   }, MgrColumn.READONLY | MgrColumn.UNSEEN);
  MgrColumn colConversion = new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BNrioProxyExt.conversion }, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colFacets = new MgrColumn.Prop(BControlPoint.facets, MgrColumn.EDITABLE | MgrColumn.UNSEEN);
  MgrColumn colString = new MgrColumn.ToString("Value", 0);
  MgrColumn[] cols =
  {
    colName, colType, colInstance, colUiType, colConversion, colString, colFacets
  };
  
  static final Log log = Log.getLog("nrio");  

  private static final NrioMgrTypeInfo[] aoType  = 
  {
    new NrioMgrTypeInfo(Sys.getRegistry().getType("control:NumericWritable"),
                        Sys.getRegistry().getType("nrio:NrioVoltageOutputProxyExt"), 
                        null,
                        null,
                        "type.VoltageOutput"),
  };
  
  private static final NrioMgrTypeInfo[] roType  = 
  {
    new NrioMgrTypeInfo(Sys.getRegistry().getType("control:BooleanWritable"),
                        Sys.getRegistry().getType("nrio:NrioRelayOutputProxyExt"), 
                        null,
                        null,
                        "type.BooleanOutput"),
  };
  
  private static final NrioMgrTypeInfo uiDiType  = 
    new NrioMgrTypeInfo(Sys.getRegistry().getType("control:BooleanPoint"),
                        Sys.getRegistry().getType("nrio:NrioBooleanInputProxyExt"), 
                        BUniversalInputTypeEnum.di_Normal,
                        null,
                        "type.UiBooleanInput");
  
  private static final NrioMgrTypeInfo uiViType  = 
    new NrioMgrTypeInfo(Sys.getRegistry().getType("control:NumericPoint"),
                        Sys.getRegistry().getType("nrio:NrioVoltageInputProxyExt"), 
                        BUniversalInputTypeEnum.ai_0to10_vdc,
                        null,
                        "type.UiVoltageInput");
  
  private static final NrioMgrTypeInfo uiRiType  = 
    new NrioMgrTypeInfo(Sys.getRegistry().getType("control:NumericPoint"),
		                Sys.getRegistry().getType("nrio:NrioResistiveInputProxyExt"), 
		                BUniversalInputTypeEnum.ai_Resistive,
                        null,
		                "type.UiResistiveInput");
  
  private static final NrioMgrTypeInfo uiTiType  = 
    new NrioMgrTypeInfo(Sys.getRegistry().getType("control:NumericPoint"),
                        Sys.getRegistry().getType("nrio:NrioResistiveInputProxyExt"),
		                BUniversalInputTypeEnum.ai_Resistive,
                        BNrioThermistorType3Conversion.DEFAULT,
                        "type.UiThermistorInput");
  
  private static final NrioMgrTypeInfo uiCiType  = 
  	new NrioMgrTypeInfo(Sys.getRegistry().getType("control:NumericPoint"),
            			Sys.getRegistry().getType("nrio:NrioCounterInputProxyExt"), 
            			BUniversalInputTypeEnum.di_HighSpeed,
                        null,
            			"type.UiCounterInput" ); 

  private static final NrioMgrTypeInfo[] uiTypes  = 
  {
  	uiDiType,
  	uiViType,
  	uiRiType,
  	uiTiType,
  	uiCiType,
  };
  
  private static final NrioMgrTypeInfo[] uiDiTypes  = { uiDiType, };
  private static final NrioMgrTypeInfo[] uiViTypes  = { uiViType, };
  private static final NrioMgrTypeInfo[] uiRiTypes  = { uiRiType, };
  private static final NrioMgrTypeInfo[] uiTiTypes  = { uiTiType, };
  private static final NrioMgrTypeInfo[] uiCiTypes  = { uiCiType, };
  
  

}
