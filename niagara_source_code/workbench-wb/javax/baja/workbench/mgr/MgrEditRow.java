/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;

/**
 * MgrEditRow is a single row in a MgrEditTable.  It stores
 * both the target BComponent plus a copy of each cell.
 *
 * @author    Brian Frank
 * @creation  17 Dec 03
 * @version   $Revision: 20$ $Date: 7/27/10 8:02:52 AM EDT$
 * @since     Baja 1.0
 */
public class MgrEditRow
{                   

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor to edit a specific component.  If there
   * if a MgrColumn.Type column then the specified types
   * will be available for user selection.
   */
  public MgrEditRow(BComponent target, Object discovery, MgrTypeInfo[] types)
  {
    this.target = target;                                
    this.discovery = discovery;
    this.types = types;
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * If this row is in an edit, then return the 
   * associated BAbstractManager, otherwise null.
   */
  public final BAbstractManager getManager()
  {  
    if (edit != null) return edit.manager;
    return null;
  }

  /**
   * Convenience for <code>getManager().getModel()</code>.
   */
  public final MgrModel getModel()
  { 
    return getManager().model;
  }

  /**
   * Convenience for <code>getManager().getLearn()</code>.
   */
  public final MgrLearn getLearn()
  { 
    return getManager().getLearn();
  }

  /**
   * If this row is in a edit, then return the 
   * associated MgrEdit, otherwise null.
   */
  public MgrEdit getEdit()
  {             
    return edit;
  }
                    
  /**
   * Get the target component.  If there is a list of 
   * available of types, then this target represents an
   * instance of the currently selected type.
   */
  public BComponent getTarget()
  {
    return target;
  }                
  
  /**
   * Get the target's parent component.  If the component isn't
   * added yet then return MgrEdit.getAddContainer(), otherwise
   * return the target's current parent.
   */
  public BComponent getTargetParent()
  {        
    BComponent parent = (BComponent)target.getParent();
    if (parent != null) return parent;
    return edit.getAddContainer();   
  }  
  
  /**
   * Get the discovery object associated with this row 
   * if one is available, otherwise return null.
   */
  public Object getDiscovery()
  {
    return discovery;
  }

  /**
   * Set the tag def object associated with this row 
   */
  public void setTagDef(Object tagDef)
  {
    this.tagDef = tagDef;
  }

  /**
   * Get the tag def object associated with this row 
   * if one is available, otherwise return null.
   */
  public Object getTagDef()
  {
    return tagDef;
  }

  /**
   * If this row supports an editable type column then 
   * return the list of types for the user to choose 
   * from.  If no types were passed to the constructor
   * then return null.
   */
  public MgrTypeInfo[] getAvailableTypes()
  {                                     
    return types;
  }
  
////////////////////////////////////////////////////////////////
// Name
////////////////////////////////////////////////////////////////

  /**
   * Get the index of the MgrColumn.Name column or 
   * return -1 if name is not an available column.
   */
  public int getNameColumnIndex()
  {                       
    return nameColumn;
  }     
  
  /**
   * Get the name cell or null if name is not an available column.
   * If the name hasn't been set yet then return null.  This
   * name should be escaped.
   */
  public String getName()
  {
    if (nameColumn < 0) return null;
    BValue val = getCell(nameColumn);
    if (val == null) return null;
    String name = val.toString();
    if (name.length() == 0) return null;
    return name;
  }            

  /**
   * Set the name cell or ignore if name is not an available column.
   * This name should be escaped.
   */
  public void setName(String name)
  {
    if (nameColumn < 0) return;
    setCell(nameColumn, BString.make(name));
  }            

  /**
   * If no name has been set yet, then set the name cell to the 
   * specified string using <code>MgrEdit.getUniqueName()</code>.  
   * If the name is already set or there is no name column 
   * then ignore this call.  This name should be escaped.
   */
  public void setDefaultName(String name)
  {
    if (nameColumn < 0) return;
    if (getName() == null) setName(getEdit().getUniqueName(this, name));
  }


  
  /**
   * Get the index of the MgrColumn.Type column or 
   * return -1 if type is not an available column.
   */
  public int getTypeColumnIndex()
  {                       
    return typeColumn;
  }     
  
  /**
   * Get the type cell or null if type is not an available column.
   */
  public MgrTypeInfo getType()
  {
    if (typeColumn < 0) return null;
    BFacets dummy = (BFacets)getCell(typeColumn);
    return (MgrTypeInfo)dummy.getPickle();
  }            

  /**
   * Set the type cell or ignore if type is not an available column.
   */
  public void setType(MgrTypeInfo type)
    throws Exception
  {
    if (typeColumn < 0) return;
    BFacets dummy = BFacets.makePickle(BFacets.make("x", BBoolean.TRUE), type);
    setCell(typeColumn, dummy);
    
    if (target.isMounted())
      throw new IllegalStateException("Cannot change type of mounted component");

    target = getManager().getModel().newInstance(type); 

    // reload all the cells to be safe
    loadCells();                 

    // give subclass a chance to update row
    if (discovery != null)                                              
      getLearn().toRow(discovery, this);    
    
    // if the user explicitly has entered a name into the name editor
    // and it has been saved back to this row into the userDefinedName
    // field, then we never change it - otherwise we generate a new name 
    // based on the new type or what the learn code set default name to
    if (userDefinedName != null)
      setName(userDefinedName);
    else if (getName() == null)
      setName(edit.getUniqueName(this));
  }         

////////////////////////////////////////////////////////////////
// Cells
////////////////////////////////////////////////////////////////

  /**
   * Return <code>getEdit().getColumns()</code>.
   */                
  public final MgrColumn[] getColumns()
  {
    return edit.getColumns();
  }                  
  
  /**
   * Get the editable column index.
   */
  public final int getColumnIndex(MgrColumn col)
  {
    MgrColumn[] cols = getColumns();
    for(int i=0; i<cols.length; ++i)
      if (col == cols[i]) return i;
    throw new IllegalArgumentException("MgrColumn not found in getManager().getEditableColumns()");
  }
  
  /**
   * Get the current value of the cell at the specified column.
   */
  public BValue getCell(int col)
  {
    return cells[col];
  }

  /**
   * Get the current value of the cell at the specified column.
   */
  public BValue getCell(MgrColumn col)
  {
    return cells[getColumnIndex(col)];
  }

  /**
   * Set the current value of the cell at the specified column.
   */
  public void setCell(int col, BValue val)
  {
    cells[col] = val;    
  }

  /**
   * Set the current value of the cell at the specified column.
   */
  public void setCell(MgrColumn col, BValue val)
  {                      
    setCell(getColumnIndex(col), val);
  }
  
  /**
   * Make a copy of the cells to use as a working set.  This
   * method routes to the MgrColumn.load() method of each
   * column.
   */
  public void loadCells() 
    throws Exception
  {
    MgrColumn[] cols = getColumns();
    
    nameColumn = -1;    
    typeColumn = -1;
    cells = new BValue[cols.length];
    
    for(int i=0; i<cells.length; ++i)
    {
      MgrColumn col = cols[i];
      if (col instanceof MgrColumn.Name) nameColumn = i;
      if (col instanceof MgrColumn.Type) typeColumn = i;
      try{cells[i] = col.load(this);}
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }  
  }
  
  /**
   * Save the cells back to the target component.  This
   * method routes to the MgrColumn.save() method of each
   * column.
   */
  public void saveCells(Context cx)
    throws Exception
  {                      
    MgrColumn[] cols = getColumns();
    for(int i=0; i<cols.length; ++i)
      cols[i].save(this, cells[i], cx);
  }
  
////////////////////////////////////////////////////////////////
// Commit
////////////////////////////////////////////////////////////////
  
  /**
   * Commit the edit to the database.  This should apply 
   * all changes to the components up to (but not including) 
   * the point where it is actually added to the database, 
   * that occurs via MgrModel.addInstances().
   */
  public void commit(Context cx)
    throws Exception
  {                                 
    // save cells
    saveCells(target.isMounted() ? cx : null);

    // perform rename last
    BComponent parent = getTargetParent();
    String name = getName();
    if (target.isMounted())
    {
      if (name != null && !name.equals(target.getName()))
        parent.rename(target.getPropertyInParent(), name, cx);
    }
  }                 
  
////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////
  
  /**
   * Utility method to dump cell debug to standard out.
   */
  public void dumpCells()
  {                  
    System.out.println("MgrEditRow " + target.getType());
    MgrColumn[] cols = getColumns();
    for(int i=0; i<cells.length; ++i)
      System.out.println("  " + cols[i].getDisplayName() + " = " + cells[i].toString());
  }
      
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  BComponent target;  
  Object discovery;
  Object tagDef;
  MgrTypeInfo[] types;
  MgrEdit edit;       
  int nameColumn = -1;
  int typeColumn = -1;
  BValue[] cells;     
  String userDefinedName; // set when MgrColumn.Name saves field
  
}                    


