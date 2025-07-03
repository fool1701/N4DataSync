/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import java.util.Optional;
import javax.baja.data.BIDataValue;
import javax.baja.gx.BImage;
import javax.baja.naming.SlotPath;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.BInteger;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.tag.Id;
import javax.baja.tag.Tag;
import javax.baja.tag.TagInfo;
import javax.baja.ui.table.TableCellRenderer;
import javax.baja.ui.util.UiLexicon;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.sys.tag.ComponentTags;
import com.tridium.workbench.fieldeditors.BStringFE;
import com.tridium.workbench.fieldeditors.BStringMgrColFE;

/**
 * MgrColumn is used to display a column of information 
 * in a BAbstractManager table or to support get/set for batch 
 * edits.                              
 *        
 * <pre>{@code
 * Using MgrColumn with MgrEdit
 *   target -> load() -> MgrEditRow.cell -> toFieldEditor() -> FieldEditor
 *   FieldEditor -> fromFieldEditor() -> MgrEditRow.cell -> save() -> target
 * }</pre>
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 51$ $Date: 7/1/11 1:58:07 PM EDT$
 * @since     Baja 1.0
 */
public abstract class MgrColumn 
{           

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Convience for <code>MgrColumn(displayName, 0)</code>.
   */
  protected MgrColumn(String displayName)
  {                         
    this(displayName, 0);
  }
  
  /**
   * Construct an MgrColumn.
   */
  protected MgrColumn(String displayName, int flags)
  {                 
    this.displayName = displayName;
    this.flags       = flags;
  }
  
  /**
   * Construct an MgrColumn.
   */
  protected MgrColumn(String displayName, int flags, BImage columnIcon)
  {                 
    this.displayName = displayName;
    this.flags       = flags;
    this.columnIcon = columnIcon;
  }
  
  void init(BAbstractManager manager)
  {
    if (this.manager != null && this.manager!=manager)
      throw new IllegalStateException("MgrColumns cannot shared between manager instances");
    this.manager = manager;
  }   
                              
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the manager associated with this column. 
   */
  public BAbstractManager getManager()
  {                                              
    return manager;
  }  
  
  /**
   * Get the display name for the current VM's locale.
   */
  public String getDisplayName()
  {
    return displayName;
  }
  
  /**
   * Given a row, extract the column value.  This method
   * is used to get a value to display in a table.  In the
   * MgrTable row is the BComponent being managed.  If used
   * with the LearnTable then row is a discovery object.
   */
  public abstract Object get(Object row);     
  
  /**
   * Given a row, extract an Object to use for a sort
   * key.  The default is to return <code>get()</code>.
   */          
  public Object toSortKey(Object row)
  {
    return get(row);
  }                
  
  /**
   * Given a row and it's value return the string to display to the 
   * user in a table cell.  The row is BComponent or learn object,
   * the value is the result of <code>get(row)</code>, and context
   * is the view's current context.
   */
  public String toDisplayString(Object row, Object value, Context cx)
  {
    if (value instanceof BObject)
      return ((BObject)value).toString(manager.getCurrentContext());
    else
      return String.valueOf(value);
  }
  
  /**
   * Return the renderer to use for painting the cells
   * of this column in a BTable.  Return null to use the
   * default renderering.
   */
  public TableCellRenderer getCellRenderer()
  {
    return null;
  }
  
  /**
   * Return the flags bit mask.
   */
  public int getFlags()
  {
    return flags;
  }         

  /**
   * Set the flags bit mask.
   * 
   * @since Niagara 3.6
   */
  public void setFlags(int flags)
  {
    this.flags = flags;
  }         
  
  
  /**
   * Return if the EDITABLE flag is set.
   */
  public boolean isEditable()
  {
    return (flags & EDITABLE) != 0;          
  }                                          

  /**
   * Return if the UNSEEN flag is set.
   */
  public boolean isUnseen()
  {
    return (flags & UNSEEN) != 0;          
  }                                          

  /**
   * Return if the READONLY flag is set.
   */
  public boolean isReadonly()
  {
    return (flags & READONLY) != 0;          
  }           
  
  /**
   * Convenience method to modify the columns flags, removing
   * EDITABLE and setting UNSEEN.
   * 
   * @since Niagara 3.6
   */
  public void setHidden()
  {
    flags = (flags | MgrColumn.UNSEEN) & ~MgrColumn.EDITABLE;
  }

  /**
   * Convenience method to modify the columns flags, setting
   * READONLY.
   * 
   * @since Niagara 3.6
   */
  public void setReadOnly()
  {
    flags = (flags | MgrColumn.READONLY);
  }
  
  
  /**
   * Given a row return true if this column cell is valid for the row.  Will cause the cell to be "gray' out.
   * 
   * @since Niagara 4.0
   */
  public boolean isCellValid(MgrEditRow row)
  {
    return true;
  }
  
  
  /**
   * Set column icon.
   *   Image must support disabled and highlighted images
   */
  public void setIcon(BImage columnIcon)
  {
    this.columnIcon = columnIcon;
  }                               
    
  /**
   * Get column icon.
   */
  public BImage getIcon()
  {
    return columnIcon;
  }                               
    
  /**
   * Get debug string.
   */
  public String toString()
  {
    return displayName + "[" + getClass().getName() + "]";
  }                               
    
////////////////////////////////////////////////////////////////
// EditMgr
////////////////////////////////////////////////////////////////
  
  /**
   * This method is called to load the value when working 
   * with an MgrEditRow.  Load is used to initialize the 
   * cell of a MgrEdit.
   */
  public BValue load(MgrEditRow row)
    throws Exception
  {                       
    throw new UnsupportedOperationException(getClass().getName());
  }

  /**
   * This method is called to save the value when working with 
   * an MgrEditRow.  Save is used to save the cell of a MgrEditRow
   * to a target component.
   */
  public void save(MgrEditRow row, BValue value, Context cx)
    throws Exception
  {
    throw new UnsupportedOperationException(getClass().getName());
  }               
        
  /**
   * During the user input phase of MgrEdit, each editable
   * column provides a widget for changing the cell of one
   * or more rows.  This method is called to return that widget 
   * as a loaded editor.  If this column doesn't support
   * concurrent editing of all the given rows then return null.   
   * If an editor has been allocated by a previous call to
   * this method, then it is passed as currentEditor.  The best
   * way to override this method is to check if the current
   * editor is non-null and suitable, if not allocate a new 
   * one.  Then load the editor to reflect the current rows.
   */
  public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    throws Exception
  {      
    return null;
  }  

  /**
   * This method is called to store the value in the editor
   * back to the cells of this column for each of the specified
   * rows.  The widget passed with whatever widget was returned
   * by the toEditor method.
   */
  public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor editor)
    throws Exception
  {
    throw new UnsupportedOperationException(getClass().getName());
  }  
  
  /**
   * This is a utility to map the rows to a common field editor.
   * If the rows don't have a homogeneous type, then return null.
   */
  public static BWbFieldEditor toFieldEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor, Property[] propPath)
  {                                 
    // first make sure every row as the same type
    BValue val = rows[0].getCell(colIndex);
    for(int i=1; i<rows.length; ++i)             
      if (rows[i].getCell(colIndex).getClass() != val.getClass())
        return null;
        
    // now we need to decide what facets to use
    // for the editor, if one row then use the real
    // instance facets; otherwise use the default 
    // facets or the property
    BFacets facets;
    if (rows.length == 1 && propPath.length == 1)
      facets = rows[0].getTarget().getSlotFacets(propPath[0]);
    else
      facets = propPath[propPath.length-1].getFacets();  
    
    // wrap facets with view's current context
    Context cx = rows[0].getManager().getCurrentContext();
    if (!facets.isNull()) cx = new BasicContext(cx, facets);

    // create the editor
    BWbFieldEditor editor = BWbFieldEditor.makeFor(val, cx);
    if (editor instanceof BStringFE) editor = new BStringMgrColFE(colIndex);

    // if the old one if of  correct type, then let's reuse it
    if (currentEditor != null && currentEditor.getClass() == editor.getClass())
      editor = (BWbFieldEditor)currentEditor;
      
    editor.loadValue(val, cx);
    return editor;
  }

  /**
   * This is a utility to map the rows to a common field editor.
   * If the rows don't have a homogeneous type, then return null.
   */
  public static BWbFieldEditor toFieldEditor(javax.baja.sys.Type mixinType, MgrEditRow[] rows, int colIndex, BWbEditor currentEditor, Property[] propPath)
  {                                 
    // first make sure every row as the same type
    BValue val = rows[0].getCell(colIndex);
    for(int i=1; i<rows.length; ++i)             
      if (rows[i].getCell(colIndex).getClass() != val.getClass())
        return null;
        
    // now we need to decide what facets to use
    // for the editor, if one row then use the real
    // instance facets; otherwise use the default 
    // facets or the property
    BFacets facets;
    if (rows.length == 1 && propPath.length == 1)
      facets = ((BComponent)rows[0].getTarget().getMixIn(mixinType)).getSlotFacets(propPath[0]);
    else
      facets = propPath[propPath.length-1].getFacets();  
    
    // wrap facets with view's current context
    Context cx = rows[0].getManager().getCurrentContext();
    if (!facets.isNull()) cx = new BasicContext(cx, facets);

    // create the editor
    BWbFieldEditor editor = BWbFieldEditor.makeFor(val, cx);
    if (editor instanceof BStringFE) editor = new BStringMgrColFE(colIndex);

    // if the old one if of  correct type, then let's reuse it
    if (currentEditor != null && currentEditor.getClass() == editor.getClass())
      editor = (BWbFieldEditor)currentEditor;
      
    editor.loadValue(val, cx);
    return editor;
  }

  /**
   * This is a utility to save back the results from a 
   * field editor to a list of rows.
   */
  public static void fromFieldEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget, Property[] propPath)
    throws Exception
  {
    BWbFieldEditor editor = (BWbFieldEditor)widget;
    BValue val = (BValue)editor.saveValue();
    for(int i=0; i<rows.length; ++i)
      rows[i].setCell(colIndex, val);
  }
  
////////////////////////////////////////////////////////////////
// Name
////////////////////////////////////////////////////////////////
  
  /**
   * The MgrColumn.Name implementation is used to display 
   * and manage the name of a row component.
   */
  public static class Name extends MgrColumn
  {                   
    public Name() 
    { 
      super(UiLexicon.bajaui().getText("name"), EDITABLE); 
    }

    public Name(int flags)
    {
      super(UiLexicon.bajaui().getText("name"), flags);
    }
    
    public Object get(Object row)
    {
      return ((BComponent)row).getDisplayName(null);
    }   

    public String toDisplayString(Object row, Object value, Context cx)
    {                                 
      try
      {
        return SlotPath.unescape(value.toString());
      }
      catch(Exception e)
      {                       
        // this is a hack - for some reason in view we pass in 
        // the unescaped name and in the dialog we pass in the escaped
        // name - the unescape call doesn't actually hurt anything when
        // passing in an already unescaped string unless there is a
        // $ in the name in which case an exception is thrown, so we will
        // just return the string
        return String.valueOf(value);
      }
    }

    public BValue load(MgrEditRow row)
    {
      String name = row.getTarget().getName();
      if (name != null) return BString.make(name);
      return BString.DEFAULT;
    }

    public void save(MgrEditRow row, BValue value, Context cx)
    {                
      // handled specially in MgrEditRow
    }
        
    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {
      if (rows.length != 1) return null;

      Property propInParent = rows[0].getTarget().getPropertyInParent();
      if (null !=  propInParent && propInParent.isFrozen())
      {
        return null;
      }
      
      BString name = (BString)rows[0].getCell(colIndex);
      BString displayName = BString.make(SlotPath.unescape(name.toString()));
      
      BWbFieldEditor editor = (BWbFieldEditor)currentEditor;
      if (editor == null) editor = BWbFieldEditor.makeFor(displayName);
      
      BFacets facets = BFacets.make(BFacets.FIELD_WIDTH, BInteger.make(30));
      editor.loadValue(displayName, facets);
      
      return editor;
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget)
      throws Exception
    {                             
      BWbFieldEditor editor = (BWbFieldEditor)widget;
      BString displayName = (BString)editor.saveValue();
      
      BString name = BString.make(SlotPath.escape(displayName.toString()));
      
      for(int i=0; i<rows.length; ++i)  
      {
        rows[i].setCell(colIndex, name);
        rows[i].userDefinedName = name.toString();
      }
    }    
  }                                                        
  

  
  /**
   * The MgrColumn.Type implementation is used to display 
   * and manage the name of a row component.
   */
  public static class Type extends MgrColumn
  {                   
    public Type() 
    { 
      super(UiLexicon.bajaui().getText("type"), EDITABLE); 
    }

    public Type(int flags) 
    { 
      super(UiLexicon.bajaui().getText("type"), flags); 
    }
    
    public Object get(Object row)
    {
      return getManager().getModel().toType((BComponent)row);
    }   

    public String toDisplayString(Object row, Object value, Context cx)
    {
      if (value instanceof BFacets)
      {
        BFacets dummy = (BFacets)value;
        return dummy.getPickle().toString(); 
      }
      else
      { 
        return String.valueOf(value);
      }
    }
    
    public BValue load(MgrEditRow row)
    {            
      MgrTypeInfo type;
            
      BComponent target = row.getTarget();
      if (target != null)
      {
        type = getManager().getModel().toType(target);
      }
      else
      {
        type = row.getAvailableTypes()[0];
      }
      BFacets dummy = BFacets.makePickle(BFacets.make("x", BBoolean.TRUE), type);
      return dummy;
    }   

    public void save(MgrEditRow row, BValue value, Context cx)
    {                
      // handled specially in MgrEditRow
    }        

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {            
      // get intersection of types
      MgrTypeInfo[] types = MgrEdit.getTypeIntersection(rows);
      if (types.length == 0) return null;
      
      // attempt to use type as configured in first row
      int curIndex = 0;      
      if (rows.length > 0)
      {
        MgrTypeInfo curType = rows[0].getType();
        for(int i=0; i<types.length; ++i)     
          if (curType.equals(types[i])) { curIndex = i; break; }
      }
      
      // map to BDynamicEnum so we can use it's field editor
      String[] tags = new String[types.length];
      for(int i=0; i<tags.length; ++i) tags[i] = SlotPath.escape(types[i].getDisplayName());
      BDynamicEnum choices = BDynamicEnum.make(curIndex, BEnumRange.make(tags));
      
      // create editor if needed
      BWbFieldEditor editor = (BWbFieldEditor)currentEditor;
      if (editor == null) editor = BWbFieldEditor.makeFor(choices);      
      editor.loadValue(choices);      
      return editor;
    }
    
    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget)
      throws Exception
    {                             
      BWbFieldEditor editor = (BWbFieldEditor)widget;
      BDynamicEnum choices = (BDynamicEnum)editor.saveValue();
      String name = choices.getDisplayTag(null);
      
      MgrTypeInfo[] types = MgrEdit.getTypeIntersection(rows);
      for(int i=0; i<types.length; ++i)
      {
        if (types[i].getDisplayName().equals(name))
        {              
          for(int j=0; j<rows.length; ++j)
            rows[j].setType(types[i]);
          return;
        }
      }          
      
      throw new IllegalStateException();
    }    
  }                                                        
  
////////////////////////////////////////////////////////////////
// Path
////////////////////////////////////////////////////////////////
  
  /**
   * The MgrColumn.Path implementation is used to display 
   * the slot path of a row component.
   */
  public static class Path extends MgrColumn
  {                           
    public Path(int flags) 
    { 
      super(UiLexicon.bajaui().getText("path"), flags); 
    }
    
    public Object get(Object row)
    {
      return ((BComponent)row).getSlotPath().toDisplayString();
    }   
  }                                                        

////////////////////////////////////////////////////////////////
// ToString
////////////////////////////////////////////////////////////////
  
  /**
   * The MgrColumn.ToString implementation is used to display 
   * the result to toString of a row component.
   */
  public static class ToString extends MgrColumn
  {                   
    public ToString(String displayName, int flags) 
    { 
      super(displayName, flags); 
    }
    
    public Object get(Object row)
    {
      return ((BComponent)row).toString(null);
    }   
  }                                                        

////////////////////////////////////////////////////////////////
// Prop
///////////////////////////////////////////////////////////////
  
  /**
   * The MgrColumn.Prop implementation is used to display 
   * and manage a property of a row component.
   */
  public static class Prop extends MgrColumn
  {                   
    public Prop(String name, Property prop, int flags) 
    { 
      super(name, flags); 
      this.prop = prop;     
      this.props = new Property[] { prop };
    }
    
    public Prop(Property prop, int flags) 
    { 
      this(prop.getDefaultDisplayName(null), prop, flags); 
    }                                                        
    
    public Prop(Property prop) 
    { 
      this(prop, 0); 
    }

    public Object get(Object row)
    {                                                   
      BComplex complex = (BComplex)row;
      return complex.get(prop);
    }                        
    
    public String toDisplayString(Object row, Object value, Context cx)
    {                                         
      BComplex complex = (BComplex)row;
      BFacets facets = complex.getSlotFacets(prop);
      
      if (!facets.isNull()) cx = new BasicContext(cx, facets);
      
      return ((BObject)value).toString(cx);
    }
    
    public BValue load(MgrEditRow row)
    {                       
      return row.getTarget().get(prop).newCopy();
    }

    public void save(MgrEditRow row, BValue value, Context cx)
    {                          
      BComponent target = row.getTarget();
      BValue old = target.get(prop);
      if (!old.equivalent(value)) target.set(prop, value.newCopy(), cx);
    }

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {               
      return toFieldEditor(rows, colIndex, currentEditor, props);
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor editor)
      throws Exception
    {
      fromFieldEditor(rows, colIndex, editor, props);
    }

    protected Property prop;
    protected Property[] props; 
  }                                                        

////////////////////////////////////////////////////////////////
// PropPath
///////////////////////////////////////////////////////////////
  
  /**
   * The MgrColumn.PropPath implementation is used to display 
   * and manage a property of a row component.
   */
  public static class PropPath extends MgrColumn
  {                   
    public PropPath(String name, Property props[], int flags) 
    { 
      super(name, flags); 
      this.props = props; 
    }
      
    public PropPath(Property props[], int flags) 
    { 
      this(props[props.length-1].getDefaultDisplayName(null), props, flags);
    }
    
    public PropPath(Property props[]) 
    {        
      this(props, 0);
    }

    protected BComplex getRowBase(Object row)
    {
      return (BComplex)row;
    }
    
    public Object get(Object row)
    {                      
      BComplex val = getRowBase(row);                  
      for(int i=0; i<props.length-1; ++i) 
        val = (BComplex)val.get(props[i]);                 
      Property prop = props[props.length-1];
      return val.get(prop);
    }   

    public String toDisplayString(Object row, Object value, Context cx)
    {                       
      BComplex val = getRowBase(row);                  
      for(int i=0; i<props.length-1; ++i) 
        val = (BComplex)val.get(props[i]);                 
      Property prop = props[props.length-1];
      BFacets facets = val.getSlotFacets(prop);
                      
      if (!facets.isNull()) cx = new BasicContext(cx, facets);
      
      return ((BObject)value).toString(cx);
    }

    protected BComplex getTargetBase(MgrEditRow row)
    {
      return row.getTarget();
    }

    public BValue load(MgrEditRow row)
    {                       
      BValue val = getTargetBase(row);                  

      for(int i=0; i<props.length; ++i) 
        val = ((BComplex)val).get(props[i]);
      return val.newCopy();
    }

    public void save(MgrEditRow row, BValue value, Context cx)
    {     
      BComplex target = getTargetBase(row);

      int len = props.length;
      for(int i=0; i<len-1; ++i)
        target = (BComplex)target.get(props[i]);
      
      Property prop = props[len-1];
      BValue old = target.get(prop);
      if (!old.equivalent(value)) target.set(prop, value.newCopy(), cx);
    }

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {
      return toFieldEditor(rows, colIndex, currentEditor, props);
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor editor)
      throws Exception
    {
      fromFieldEditor(rows, colIndex, editor, props);
    }
    
    protected Property[] props;
  }                                                    

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
          return "-";
//        BFacets facets = val.getSlotFacets(val.getProperty(propString));
//                        
//        if (!facets.isNull()) cx = new BasicContext(cx, facets);
//        
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
  
////////////////////////////////////////////////////////////////
// MixIn
////////////////////////////////////////////////////////////////

  /**
   * The MgrColumn.MixinProp implementation is used to display 
   * and manage a property of a row component's mixin.
   * 
   * This MgrColumn type is probably created by a BIMixinColumns agent.
   * See <code>MgrModel.appendMixInColumns()</code>.
   */
  public static class MixinProp extends PropPath
  {                   
    public MixinProp(javax.baja.sys.Type mixinType, String name, Property prop, int flags) 
    {
      this(mixinType, name, new Property[] { prop }, flags);
    }

    public MixinProp(javax.baja.sys.Type mixinType, String name, Property props[], int flags) 
    { 
      super(name, props, flags); 
      this.mixinType = mixinType;
    }
      
    public MixinProp(javax.baja.sys.Type mixinType, Property prop, int flags) 
    {
      this(mixinType, new Property[] { prop }, flags);
    }

    public MixinProp(javax.baja.sys.Type mixinType, Property props[], int flags) 
    { 
      this(mixinType, props[props.length-1].getDefaultDisplayName(null), props, flags);
    }
    
    public MixinProp(javax.baja.sys.Type mixinType, Property prop) 
    {
      this(mixinType, new Property[] { prop });
    }

    public MixinProp(javax.baja.sys.Type mixinType, Property props[]) 
    {        
      this(mixinType, props, 0);
    }

    protected BComplex getRowBase(Object row)
    {
      BComponent parent = (BComponent)row;
      return (BComplex)parent.getMixIn(mixinType);
    }

    protected BComplex getTargetBase(MgrEditRow row)
    {
      BComponent parent = row.getTarget();
      BComplex result = (BComplex)parent.getMixIn(mixinType);
      if (result == null)
      {
        result = (BComplex)mixinType.getInstance();
        parent.add(mixinType.toString().replace(':', '_'), result);
      }
      return result;
    }
    
    javax.baja.sys.Type mixinType;   
  }                                                    

  /**
   * The MgrColumn.MixIn implementation is used to display and edit a 
   * MixIn dynamic property.  See <code>MgrModel.appendMixInColumns()</code>.
   */
  public static class MixIn extends MgrColumn
  {                   
    public MixIn(javax.baja.sys.Type type)
    {                   
      super(((BIMixIn)type.getInstance()).getDisplayNameInParent(null), EDITABLE);
      this.type = type;
    }                  
    
    public Object get(Object row)
    {                        
      BComponent parent = (BComponent)row;
      return parent.getMixIn(type);
    }   

    public String toDisplayString(Object row, Object value, Context cx)
    {        
      return value == null ? "-" : value.toString();
    }
    
    public BValue load(MgrEditRow row)
    {            
      BValue value = row.getTarget().getMixIn(type);
      if (value == null) value = (BValue)type.getInstance();
      return value;
    }   

    public void save(MgrEditRow row, BValue value, Context cx)
    {                     
      BComponent parent = row.getTarget();
      String propName = type.toString().replace(':', '_');
      Property prop = parent.getProperty(propName);
      if (prop == null)
        parent.add(propName, value, cx);
      else
        parent.set(prop, value, cx);
    }        

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {                    
      // create editor if one not created already
      BWbFieldEditor editor = (BWbFieldEditor)currentEditor;
      if (editor == null) editor = BWbFieldEditor.makeFor(type.getInstance());

      // use first non-null mix in value 
      BValue val = null;
      for(int i=0; i<rows.length; ++i)             
      {            
        BComponent parent = rows[i].getTarget();
        val = parent.getMixIn(type);
        if (val != null) break;
      }
      
      // if we didn't find an instance, then create a fresh one
      if (val == null) val = (BValue)type.getInstance();
      
      // load the editor
      editor.loadValue(val.newCopy());
      return editor;
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget)
      throws Exception
    {                             
      BWbFieldEditor editor = (BWbFieldEditor)widget;
      BValue val = (BValue)editor.saveValue();
      for(int i=0; i<rows.length; ++i)
        rows[i].setCell(colIndex, val.newCopy());
    }                 
      
    javax.baja.sys.Type type;   
  }

///////////////////////////////////////////////////////////
// TagColumn
///////////////////////////////////////////////////////////

  /**
   * The MgrColumn.Tag implementation is used to display and edit a 
   * BITaggable's tag.
   */
  public static class TagColumn extends MgrColumn
  {                   
    public TagColumn(Tag tag, int flags)
    {
      super(tag.getId().getName(), flags, BImage.make("module://icons/x16/tag.png"));
      compTag = tag;
    }                  
    
    public Tag getTag()
    {
      return compTag;
    }
    
    public void setTag(Tag compTag)
    {
      this.compTag = compTag;
    }
    
    public Object get(Object row)
    {                        
      BComponent parent = (BComponent)row;
      Optional<BIDataValue> tarValue = parent.tags().get(compTag.getId());
      if(tarValue.isPresent())
      {
        return tarValue.get();
      }
      return compTag.getValue();
    }

    public String toDisplayString(Object row, Object value, Context cx)
    {        
      return value == null ? "-" : value.toString();
    }
    
    public boolean isCellValid(MgrEditRow row)
    {
      try
      {
        Id id = compTag.getId();
        // Only consider direct tags (implied tags are never valid for editing)
        if (new ComponentTags(row.getTarget()).get(id).isPresent())
        {
          // Exclude direct tags that are marked readonly or hidden
          Property tagProp = row.getTarget().getProperty(SlotPath.escape(id.getQName()));
          if (tagProp == null ||
              !Flags.isReadonly(row.getTarget(), tagProp) &&
              !Flags.isHidden(row.getTarget(), tagProp))
          {
            return true;
          }
        }
      }
      catch(Exception ignore) { }

      return false;
    }
    
    public BValue load(MgrEditRow row)
    {
      BValue value = BString.make("-");
      try
      {
        // TODO: add support for BISpaceNode tag values?
        //
        value = row.getTarget().tags().get(compTag.getId())
          .map(dv -> dv.as(BValue.class))
          .orElse(value);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      return value;
    }   

    public void save(MgrEditRow row, BValue value, Context cx)
    {
      // TODO: add support for BISpaceNode tag values?
      //

      // Remove old tag first since we allow lists of values
      // TODO: we may want to firm up the semantics of BDataSet values for this column type.
      // Perhaps we should just assume single-value? in which case we can do remove all for that tag id
      //
      BComponent parent = row.getTarget();
      String tagPropName = SlotPath.escape(compTag.getId().getQName());
      Property tagProp = parent.getProperty(tagPropName);
      // Only replace the tag if it existed as a direct tag in the first place
      // and it wasn't marked with the readonly or hidden flag
      if (tagProp != null &&
          !Flags.isReadonly(parent, tagProp) && !Flags.isHidden(parent, tagProp))
      {
        // Stash away the slot flags and facets since we will reapply them after
        // the tag replacement
        int originalFlags = parent.getFlags(tagProp);
        BFacets originalFacets = parent.getSlotFacets(tagProp);

        // Replace the tag
        parent.tags().remove(compTag);
        parent.tags().set(new Tag(compTag.getId(), value.as(BIDataValue.class)));

        // Reapply the slot flags and facets (since the user could have manually
        // changed them for the direct tag's slot)
        tagProp = parent.getProperty(tagPropName);
        if (tagProp != null)
        {
          parent.setFlags(tagProp, originalFlags);
          parent.setFacets(tagProp, originalFacets);
        }
      }
    }

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {                    
      // first make sure every row as the same type
      BValue val = rows[0].getCell(colIndex);
      if(val == null)
        return null;
      for(int i=1; i<rows.length; ++i)             
      {
        if (rows[i].getCell(colIndex).getClass() != val.getClass())
          return null;
      }
      // create editor if one not created already
      BWbFieldEditor editor;
      BValue editValue = val;
      editor = BWbFieldEditor.makeFor(editValue);

      // load the editor
      editor.loadValue(editValue.newCopy());
      return editor;
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget)
      throws Exception
    {                             
      BWbFieldEditor editor = (BWbFieldEditor)widget;
      BValue val = (BValue)editor.saveValue();
      for(int i=0; i<rows.length; ++i)
        rows[i].setCell(colIndex, val.newCopy());
    }                 
    
    protected javax.baja.tag.Tag compTag;   
  }

///////////////////////////////////////////////////////////
// TagInfoColumn
///////////////////////////////////////////////////////////

  /**
   * The MgrColumn.Tag implementation is used to display and edit a
   * BITaggable's tag.
   */
  public static class TagInfoColumn extends MgrColumn
  {
    public TagInfoColumn(TagInfo tag, int flags)
    {
      super(tag.getTagId().getName(), flags, BImage.make("module://icons/x16/tag.png"));
    }

    public Object get(Object row)
    {
      BComponent parent = (BComponent)row;
      Optional<BIDataValue> tarValue = parent.tags().get(compTag.getId());
      if(tarValue.isPresent())
      {
        return tarValue.get();
      }
      return compTag.getValue();
    }

    public String toDisplayString(Object row, Object value, Context cx)
    {
      return value == null ? "-" : value.toString();
    }

    public boolean isCellValid(MgrEditRow row)
    {
      try
      {
        return row.getTarget().tags().get(compTag.getId()).isPresent();
      }
      catch(Exception e)
      {
        return false;
      }

    }

    public BValue load(MgrEditRow row)
    {
      BValue value = BString.make("-");
      try
      {
        // TODO: add support for BISpaceNode tag values?
        //
        value = row.getTarget().tags().get(compTag.getId())
          .map(dv -> dv.as(BValue.class))
          .orElse(value);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
      return value;
    }

    public void save(MgrEditRow row, BValue value, Context cx)
    {
      // TODO: add support for BISpaceNode tag values?
      //

      // Remove old tag first since we allow lists of values
      // TODO: we may want to firm up the semantics of BDataSet values for this column type.
      // Perhaps we should just assume single-value? in which case we can do remove all for that tag id
      //
      BComponent parent = row.getTarget();
      parent.tags().remove(compTag);
      parent.tags().set(new Tag(compTag.getId(), value.as(BIDataValue.class)));
    }

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {
      // first make sure every row as the same type
      BValue val = rows[0].getCell(colIndex);
      if(val == null)
        return null;
      for(int i=1; i<rows.length; ++i)
      {
        if (rows[i].getCell(colIndex).getClass() != val.getClass())
          return null;
      }
      // create editor if one not created already
      BWbFieldEditor editor;
      BValue editValue = val;
      editor = BWbFieldEditor.makeFor(editValue);

      // load the editor
      editor.loadValue(editValue.newCopy());
      return editor;
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget)
      throws Exception
    {
      BWbFieldEditor editor = (BWbFieldEditor)widget;
      BValue val = (BValue)editor.saveValue();
      for(int i=0; i<rows.length; ++i)
        rows[i].setCell(colIndex, val.newCopy());
    }

    protected javax.baja.tag.Tag compTag;
  }
      
////////////////////////////////////////////////////////////////
// Flags
////////////////////////////////////////////////////////////////

  /** Editable indicates a column which is included for edits
      via the MgrEdit APIs. */
  public static final int EDITABLE = 0x0001;

  /** The unseen flag is used on columns which are not shown
      by default until the user turns them on via table options */
  public static final int UNSEEN = 0x0002;

  /** The Readonly flag is used on editable columns which
      are displayed via the MgrEdit dialog, but not user modifiable */
  public static final int READONLY = 0x0004;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  BAbstractManager manager;
  String displayName;
  int flags;
  BImage columnIcon = BImage.make("module://icons/x16/object.png");

} 

