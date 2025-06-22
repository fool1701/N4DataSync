/* 
 * Copyright 2004 Tridium, Inc.  All rights reserved.
 * 
 */

package com.tridium.flexSerial.ui;

import javax.baja.sys.*;
import javax.baja.workbench.*;
import javax.baja.workbench.mgr.*;
import javax.baja.workbench.fieldeditor.*;

import com.tridium.flexSerial.*;
import com.tridium.flexSerial.messages.*;
/**
 * @author Andy Saunders
 * @creation 14 Sept 2005
 * @version $Revision: 6$ $Date: 5/26/2004 7:22:16 AM$
 */
public class FlexMessageModel 
  extends MgrModel
{
  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public FlexMessageModel(BFlexMessageManager manager)
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
      MgrTypeInfo.make(BFlexMessageBlockSelect.TYPE),
    };
  } 

  public int getSubscribeDepth()
  {           
    return 5;
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
      BFlexMessageBlockSelect.TYPE 
    };
  }

  public boolean accept(BComponent comp)
  {                                 
    return ( comp instanceof BFlexMessageElement ||
             comp instanceof BFlexMessageBlockSelect );
  }
  
  /*
  protected BMgrTable makeTable()
  {             
    return new BAlarmTable(this);
  }
  */


  /**
   * Creates a instance
   */ 
  public BComponent newInstance(MgrTypeInfo type)
    throws Exception
  {
    System.out.println("******   newInstance of type: " + type);
    BComponent newInstance = type.newInstance();
    if(newInstance instanceof BFlexMessageElement)
      return newInstance;
    if(newInstance instanceof BFlexMessageBlockSelect)
    {
      BFlexMessageManager manager = (BFlexMessageManager)getManager();
      System.out.println("parent object = " + manager.getCurrentValue());
      BFlexSerialNetwork network = BFlexSerialNetwork.getParentFlexNetwork((BComplex)manager.getCurrentValue());
      network.lease(2);
      String[] blockNames = network.getMessageBlocks().getMessageBlockNames();
      ((BFlexMessageBlockSelect)newInstance).setMessageBlockType(BDynamicEnum.make(0, BEnumRange.make(blockNames)));
      //BFlexMessageBlockSelect msgBlock = new BFlexMessageBlockSelect();
      return newInstance;
    }
    System.out.println("***** should not have gotten here!");
    return super.newInstance(type);
  } 

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected MgrColumn[] makeColumns()
  {
    return new MgrColumn[] 
    { 
      new MgrColumn.Name(),
      new PropString("offset"  , MgrColumn.READONLY),
      new PropString("size"    , MgrColumn.READONLY),
      new PropString("dataType", MgrColumn.READONLY),
      new PropString("messageBlock", "messageBlockType", MgrColumn.EDITABLE),
      new PropString("value"   , MgrColumn.EDITABLE),
      new PropString("encode"   , MgrColumn.EDITABLE),
      new PropString("facets"  , MgrColumn.EDITABLE),
      new PropString("expose", "exposeInParent"  , MgrColumn.EDITABLE),
      new PropString("source", MgrColumn.EDITABLE),
    };
  }


  public static class Prop
    extends MgrColumn.Prop
  {
    public Prop(Property prop, int flags)
    {
      super(prop, flags);
    }

    public Object get(Object row)
    {
      if (row instanceof BFlexMessageElement)
      {
        return super.get(row);
      }
      return "";
    }

    public BValue load(MgrEditRow row)
    {                       
      BValue val = row.getTarget();
      if(val instanceof BFlexMessageElement)
        return super.load(row);
      return null;
    }

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {
      // first make sure every row as the same type
      BValue val = rows[0].getCell(colIndex);
      if(val == null)
        return null;
      for(int i=1; i<rows.length; ++i)             
        if (rows[i].getCell(colIndex).getClass() != val.getClass())
          return null;

      if(rows[0].getTarget() instanceof BFlexMessageElement)
        return super.toEditor(rows, colIndex, currentEditor);
      return null;
    }

    public void save(MgrEditRow row, BValue value, Context cx)
    {                          
      if(row.getTarget() instanceof BFlexMessageElement)
        super.save(row, value, cx);
    }


  }


////////////////////////////////////////////////////////////////
// PropString
///////////////////////////////////////////////////////////////
  
  /**
   * The PropString implementation is used to display 
   * property using the property string name to specify
   * the property.
   */
  public static class PropString extends MgrColumn
  {

    public PropString(String propString, int flags) 
    {
      super(propString, flags);
      this.propString = propString;
      this.flags = flags;
    }
    
    public PropString(String displayName, String propString, int flags) 
    {
      super(displayName, flags);
      this.propString = propString;
      this.flags = flags;
    }
    
    public Object get(Object row)
    {                      
      BComplex val = (BComplex)row;                  
      return val.get(propString);
    }   

    public String toDisplayString(Object row, Object value, Context cx)
    {                       
      BComplex val = (BComplex)row;                  
      Property prop = val.getProperty(propString);
      if(prop == null)
        return "";
      BFacets facets = val.getSlotFacets(val.getProperty(propString));
                      
      if (!facets.isNull()) cx = new BasicContext(cx, facets);
      
      return ((BObject)value).toString(cx);
    }

    public BValue load(MgrEditRow row)
    {                       
      BValue val = row.getTarget();                  
      val = ((BComplex)val).get(propString);
      if(val == null)
        return null;
      return val.newCopy();
    }

    public void save(MgrEditRow row, BValue value, Context cx)
    {     
      BComponent target = row.getTarget();
      BValue old = target.get(propString);
      if(value == null || old == null)
        return;
      if (!old.equivalent(value)) target.set(propString, value.newCopy());
    }


    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {               
      return toFieldEditor(rows, colIndex, currentEditor, propString);
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor editor)
      throws Exception
    {
      fromFieldEditor(rows, colIndex, editor, propString);
    }
        /**
     * This is a utility to map the rows to a common field editor.
     * If the rows don't have a homogeneous type, then return null.
     */

    public static BWbFieldEditor toFieldEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor, String propString)
    {
      // first make sure every row as the same type
      BValue val = rows[0].getCell(colIndex);
      if(val == null)
        return null;
      for(int i=1; i<rows.length; ++i)             
        if (rows[i].getCell(colIndex).getClass() != val.getClass())
          return null;

      // now we need to decide what facets to use
      // for the editor, if one row then use the real
      // instance facets; otherwise use the default 
      // facets or the property
      BFacets facets = null;
      facets = rows[0].getTarget().getSlotFacets( rows[0].getTarget().getProperty(propString));  

      // wrap facets with view's current context
      Context cx = rows[0].getManager().getCurrentContext();
      if (!facets.isNull()) cx = new BasicContext(cx, facets);

      // create the editor, if the old one if of 
      // correct type, then let's reuse it
      BWbFieldEditor editor = BWbFieldEditor.makeFor(val, cx);
      if (currentEditor != null && currentEditor.getClass() == editor.getClass())
        editor = (BWbFieldEditor)currentEditor;

      editor.loadValue(val, cx);
      return editor;
    }

    /**
     * This is a utility to save back the results from a 
     * field editor to a list of rows.
     */
    public static void fromFieldEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget, String propString)
      throws Exception
    {
      BWbFieldEditor editor = (BWbFieldEditor)widget;
      BValue val = (BValue)editor.saveValue();
      for(int i=0; i<rows.length; ++i)
        rows[i].setCell(colIndex, val);
    }

    int flags;
    String propString; 
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


}//FlexMessageModel
