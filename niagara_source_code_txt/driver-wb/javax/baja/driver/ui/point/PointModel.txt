/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.point;

import java.util.ArrayList;
import java.util.List;
import javax.baja.control.BBooleanPoint;
import javax.baja.control.BBooleanWritable;
import javax.baja.control.BControlPoint;
import javax.baja.control.BEnumPoint;
import javax.baja.control.BEnumWritable;
import javax.baja.control.BNumericPoint;
import javax.baja.control.BNumericWritable;
import javax.baja.control.BStringPoint;
import javax.baja.control.BStringWritable;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.driver.point.BIPointFolder;
import javax.baja.driver.point.BPointFolder;
import javax.baja.driver.point.BProxyExt;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIEnum;
import javax.baja.sys.BINumeric;
import javax.baja.sys.Property;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrTypeInfo;
import javax.baja.workbench.mgr.folder.FolderModel;

/**
 * PointModel is the MgrModel to be used for BPointManagers.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 24$ $Date: 6/30/09 11:10:23 AM EDT$
 * @since     Baja 1.0
 */
public class PointModel
  extends FolderModel
{
  
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public PointModel(BPointManager manager)
  {
    super(manager);
  }


////////////////////////////////////////////////////////////////
// FolderModel
////////////////////////////////////////////////////////////////

  public Type getFolderType()
  {
    return pointFolderType;
  }

  /**
   * Get the base type supported by the new operation.
   */
  public Type  getBaseNewType()
  {
    return BControlPoint.TYPE;
  }


  public int getSubscribeDepth()
  {                           
    return 2;
  }  
  
////////////////////////////////////////////////////////////////
// MgrModel
////////////////////////////////////////////////////////////////
  
  /**
   * The default columns are name, status, and health.
   */
  protected MgrColumn[] makeColumns()
  {
    return new MgrColumn[] 
    { 
      new MgrColumn.Path(MgrColumn.UNSEEN),
      new MgrColumn.Name(),
      new MgrColumn.Type(),
      new MgrColumn.ToString(lexOut, 0),
      new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.enabled}, MgrColumn.EDITABLE | MgrColumn.UNSEEN),
      new MgrColumn.PropPath(new Property[] {BControlPoint.facets}, MgrColumn.EDITABLE | MgrColumn.UNSEEN),
      new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt,  BProxyExt.tuningPolicyName}, MgrColumn.EDITABLE),
      new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.deviceFacets}, MgrColumn.EDITABLE | MgrColumn.UNSEEN),
      new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.conversion},   MgrColumn.EDITABLE | MgrColumn.UNSEEN),
      new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.faultCause},   MgrColumn.UNSEEN),
      new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.readValue},    MgrColumn.UNSEEN),
      new MgrColumn.PropPath(new Property[] {BControlPoint.proxyExt, BProxyExt.writeValue},   MgrColumn.UNSEEN),
    };
  }

  /**
   * Return control points and point folders
   */
  public Type[] getIncludeTypes()
  {                             
    return new Type[] { BControlPoint.TYPE, pointFolderType };    
  } 
  
  /**
   * Accept only control points which have a proxy ext of the correct type.
   */
  public boolean accept(BComponent c)
  {
    if (c instanceof BControlPoint)
    {                              
      return ((BControlPoint)c).getProxyExt().getType().is(proxyExtType);
    }                     
    else
    {
      return true;
    }
  }
  
  /**
   * Run PointManager specific code, then call super.
   */
  public void load(BComponent target)
  {              
    folder = (BIPointFolder)target;
    try
    {
      proxyExtType = folder.getProxyExtType();
      pointFolderType = folder.getPointFolderType();
    }
    catch(Exception e)
    {
      // this occurs when using offline
    }
    super.load(target);         
  }                   
    
////////////////////////////////////////////////////////////////
// Point Types
////////////////////////////////////////////////////////////////
  
  /**
   * The default implementation of this method creates a new
   * BControlPoint instance from the specified type.  Then the  
   * proxyExt is set to a new instance based on the type returned
   * from <code>BPointDeviceExt.getProxyExtType()</code>.
   */
  public BComponent newInstance(MgrTypeInfo type)
    throws Exception
  {                  
    BControlPoint pt = (BControlPoint)type.newInstance();
    BAbstractProxyExt ext = (BAbstractProxyExt)proxyExtType.getInstance();
    pt.setProxyExt(ext);                   
    return pt;
  }  

  /**
   * The default implementation returns all the standard 
   * read and write point types.  Use the utility methods 
   * <code>addXXXPointTypes()</code> to build the MgrTypeInfo array. 
   */
  public MgrTypeInfo[] getNewTypes()
  {           
    ArrayList<MgrTypeInfo> list = new ArrayList<>();
    addBooleanPointTypes(list, true);
    addNumericPointTypes(list, true);
    addEnumPointTypes(list, true);
    addStringPointTypes(list, true);
    return list.toArray(new MgrTypeInfo[list.size()]);
  } 
  
  /**
   * This method uses <code>addXXXPointTypes()</code> based 
   * on if the specified type implements IBoolean, INumeric, and 
   * IEnum.  It is assumed that any type can be mapped to
   * a StringPoint.
   */  
  public static void addPointTypes(TypeInfo type, List<MgrTypeInfo> list, boolean writable)
  {                            
    if (type.is(BIBoolean.TYPE))  addBooleanPointTypes(list, writable);
    if (type.is(BINumeric.TYPE))  addNumericPointTypes(list, writable);
    if (type.is(BIEnum.TYPE))     addEnumPointTypes(list, writable);
    addStringPointTypes(list, writable);
  }
  
  /**
   * Add the MgrTypeInfos to the list for the standard BooleanPoint types. 
   * If writable is false do not add the writable point types.
   */
  public static void addBooleanPointTypes(List<MgrTypeInfo> list, boolean writable)
  {                  
    if (writable) list.add(BOOLEAN_WRITABLE);
    list.add(BOOLEAN_POINT);
  } 

  /**
   * Add the MgrTypeInfos to the list for the standard NumericPoint types. 
   * If writable is false do not add the writable point types.
   */
  public static void addNumericPointTypes(List<MgrTypeInfo> list, boolean writable)
  {                  
    if (writable) list.add(NUMERIC_WRITABLE);
    list.add(NUMERIC_POINT);
  } 

  /**
   * Add the MgrTypeInfos to the list for the standard EnumPoint types. 
   * If writable is false do not add the writable point types.
   */
  public static void addEnumPointTypes(List<MgrTypeInfo> list, boolean writable)
  {                  
    if (writable) list.add(ENUM_WRITABLE);
    list.add(ENUM_POINT);
  } 

  /**
   * Add the MgrTypeInfos to the list for the standard StringPoint types. 
   * If writable is false do not add the writable point types.
   */
  public static void addStringPointTypes(List<MgrTypeInfo> list, boolean writable)
  {                  
    if (writable) list.add(STRING_WRITABLE);
    list.add(STRING_POINT);
  }       
        
////////////////////////////////////////////////////////////////
// Point Facets
////////////////////////////////////////////////////////////////
  
  /**
   * Attempt to update the facets the specified column.  
   * Typically this is done for both point.facets and
   * proxyExt.deviceFacets.
   */
  public static void mapPointFacets(MgrEditRow row, MgrColumn col, Object source)
  {                         
    BFacets orig = (BFacets)row.getCell(col);
    BFacets facets = mapPointFacets(orig, source);
    row.setCell(col, facets);                                        
  }
    
  /**
   * This is a utility method to map.
   */
  public static BFacets mapPointFacets(BFacets orig, Object source)
  {
    BFacets f = orig;
    if (source instanceof BIBoolean)  
    {              
      return ((BIBoolean)source).getBooleanFacets();
    }
    else if (source instanceof BINumeric)
    {
      return ((BINumeric)source).getNumericFacets();
    }
    else if (source instanceof BIEnum)
    {
      return ((BIEnum)source).getEnumFacets();
    }                                 
    return BFacets.make(f); // make sure we don't return null
  }


////////////////////////////////////////////////////////////////
// MgrTypeInfo
////////////////////////////////////////////////////////////////

  public static final MgrTypeInfo BOOLEAN_POINT = MgrTypeInfo.make(new BBooleanPoint());
  public static final MgrTypeInfo BOOLEAN_WRITABLE = MgrTypeInfo.make(new BBooleanWritable());

  public static final MgrTypeInfo NUMERIC_POINT = MgrTypeInfo.make(new BNumericPoint());
  public static final MgrTypeInfo NUMERIC_WRITABLE = MgrTypeInfo.make(new BNumericWritable());

  public static final MgrTypeInfo ENUM_POINT = MgrTypeInfo.make(new BEnumPoint());
  public static final MgrTypeInfo ENUM_WRITABLE = MgrTypeInfo.make(new BEnumWritable());

  public static final MgrTypeInfo STRING_POINT = MgrTypeInfo.make(new BStringPoint());
  public static final MgrTypeInfo STRING_WRITABLE = MgrTypeInfo.make(new BStringWritable());
                          
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Lexicon lex = Lexicon.make(BPointManager.class);
  String lexOut = lex.getText("out");
  
  BIPointFolder folder;
  Type proxyExtType = BProxyExt.TYPE;  
  Type pointFolderType = BPointFolder.TYPE;
} 


