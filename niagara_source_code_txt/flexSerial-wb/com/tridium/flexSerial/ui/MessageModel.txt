/* 
 * Copyright 2004 Tridium, Inc.  All rights reserved.
 * 
 */

package com.tridium.flexSerial.ui;

import javax.baja.sys.*;
import javax.baja.workbench.mgr.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;
/**
 * @author Andy Saunders
 * @creation 14 Sept 2005
 * @version $Revision: 6$ $Date: 5/26/2004 7:22:16 AM$
 */
public class MessageModel 
  extends MgrModel
{
  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public MessageModel(BMessageManager manager)
  {
    super(manager);
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  public MgrTypeInfo[] getNewTypes()
  {
    return new MgrTypeInfo[] 
    { 
      //MgrTypeInfo.make(BFlexMessageElement.TYPE),
      MgrTypeInfo.make(BFlexByteElement.TYPE),
      MgrTypeInfo.make(BFlexWordElement.TYPE),
      MgrTypeInfo.make(BFlexIntegerElement.TYPE),
      MgrTypeInfo.make(BFlexFloatElement.TYPE),
      MgrTypeInfo.make(BFlexStringElement.TYPE),
      MgrTypeInfo.make(BFlexMarkerElement.TYPE),
    };
  } 

  public int getSubscribeDepth()
  {           
    return 1;
  }  
  
  public Type[] getIncludeTypes()
  {
    return new Type[] 
    { 
      BFlexByteElement.TYPE   ,
      BFlexWordElement.TYPE   ,
      BFlexIntegerElement.TYPE,
      BFlexFloatElement.TYPE  ,
      BFlexStringElement.TYPE ,
      BFlexMarkerElement.TYPE ,
    };
  }


  public boolean accept(BComponent comp)
  {                                 
    return comp instanceof BFlexMessageElement;
  }
  
  /**
   * Get the title to use for the database table.
   */
  protected String makeTableTitle()
  {             
    return "Message Item Setup";
  }

  /*
  protected BMgrTable makeTable()
  {             
    return new BAlarmTable(this);
  }
  */


  /**
   * Creates a new proxy point with an initialized Slimline
   * proxy extension
   */ 
  /*
  public BComponent newInstance(MgrTypeInfo type, int property)
    throws Exception
  {                  
    pt = (BFlexMessageElement)type.newInstance();
    ((BSlimlineProxyExt)ext).setMemoryAddress(property);
    BFacets deviceFacets = (BFacets)(pt.getFacets().newCopy());
    ((BProxyExt)ext).setDeviceFacets(deviceFacets);

    pt.setProxyExt(ext);
    return pt;
  } 
*/

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected MgrColumn[] makeColumns()
  {
    return new MgrColumn[] 
    { 
      new MgrColumn.Name(),
      new MgrColumn.Prop(BFlexMessageElement.offset  , MgrColumn.READONLY),
      new MgrColumn.Prop(BFlexMessageElement.size    , MgrColumn.READONLY),
      new MgrColumn.Prop(BFlexMessageElement.dataType, MgrColumn.READONLY),
      new MgrColumn.Prop(BFlexMessageElement.value   , MgrColumn.EDITABLE),
      new MgrColumn.Prop(BFlexMessageElement.encode  , MgrColumn.EDITABLE),
      new MgrColumn.Prop(BFlexMessageElement.facets  , MgrColumn.EDITABLE),
      new MgrColumn.Prop(BFlexMessageElement.source, MgrColumn.EDITABLE),
    };
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  //public static final int DEFAULT_SORT_COLUMN = 6;

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//MessageModel
