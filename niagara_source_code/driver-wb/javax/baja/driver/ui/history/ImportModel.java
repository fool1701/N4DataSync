/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.ui.history;

import javax.baja.driver.history.BHistoryImport;
import javax.baja.driver.history.BIArchiveFolder;
import javax.baja.history.BCapacity;
import javax.baja.history.BFullPolicy;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.mgr.MgrColumn;
import javax.baja.workbench.mgr.MgrEditRow;
import javax.baja.workbench.mgr.MgrTypeInfo;

/**
 * ImportModel is the history model for managing import descriptors.
 *
 * @author    John Sublett
 * @creation  12 Jan 2004
 * @version   $Revision: 8$ $Date: 5/19/09 2:54:58 PM EDT$
 * @since     Baja 1.0
 */
public class ImportModel
  extends ArchiveModel
{
  public ImportModel(BHistoryImportManager manager)
  {
    super(manager);
  }
  
  public int getSubscribeDepth()
  {           
    return 2;
  }  
    
  /**
   * The import manager only displays instances of BHistoryImport.
   */
  public Type[] getIncludeTypes()
  {
    BArchiveManager mgr = (BArchiveManager)getManager();
    if (mgr.supportsArchiveFolders())
    {
      BObject val = mgr.getCurrentValue();
      if (val instanceof BIArchiveFolder)
      {
        BIArchiveFolder folder = (BIArchiveFolder)val;
        Type folderType = folder.getArchiveFolderType();
        return new Type[] { BHistoryImport.TYPE, folderType };
      }    
    }
    
    return new Type[] { BHistoryImport.TYPE };
  }
  
  /**
   * Get the list of types supported by the new operation.  The
   * first entry in the list should be the default type.
   */  
  public MgrTypeInfo[] getNewTypes()
  {
    return MgrTypeInfo.makeArray(BHistoryImport.TYPE);
  }

  public BComponent newInstance(MgrTypeInfo type)
    throws Exception
  {                 
    BHistoryImport result = (BHistoryImport)super.newInstance(type);
    BComponent overrides = result.getConfigOverrides();
    overrides.add("capacity", BCapacity.UNLIMITED, 0);
    overrides.add("fullPolicy", BFullPolicy.roll, 0);
    
    return result;
  }

  /**
   * Get the list of columns that are appropriate for
   * all archive descriptors.
   */
  protected MgrColumn[] makeColumns()
  {
    MgrColumn[] cols = super.makeColumns();
    MgrColumn[] result = new MgrColumn[cols.length + 2];
    
    System.arraycopy(cols, 0, result, 0, cols.length);
    
    result[result.length-2] =
      new ConfigColumn("capacity",
                       histLex.getText("HistoryConfig.capacity"),
                       MgrColumn.EDITABLE | MgrColumn.UNSEEN,
                       BCapacity.UNLIMITED);
    
    result[result.length-1] =
      new ConfigColumn("fullPolicy",
                       histLex.getText("HistoryConfig.fullPolicy"),
                       MgrColumn.EDITABLE | MgrColumn.UNSEEN,
                       BFullPolicy.roll);
    
    return result;
  }

////////////////////////////////////////////////////////////////
// Columns
////////////////////////////////////////////////////////////////

  public static class ConfigColumn
    extends MgrColumn
  {
    public ConfigColumn(String propName,
                        String displayName,
                        int flags,
                        BValue defaultValue)
    {
      super(displayName, flags);
      this.propName = propName;
      this.defaultValue = defaultValue;
    }
    
    /**
     * Given a row, extract the column value.
     */
    public Object get(Object row)
    {
      BHistoryImport desc = (BHistoryImport)row;
      return desc.getConfigOverrides().get(propName);
    }
    
    public BValue load(MgrEditRow row)
    {     
      BValue value = (BValue)get(row.getTarget());             
      if (value == null)
        value = defaultValue;

      return value.newCopy();
    }

    public void save(MgrEditRow row, BValue value, Context cx)
    {
      BHistoryImport desc = (BHistoryImport)row.getTarget();
      BComponent configOverrides = desc.getConfigOverrides();
      
      Property prop = configOverrides.getProperty(propName);
      if (prop == null)
        configOverrides.add(propName, value);
      else
        configOverrides.set(prop, value, cx);
    }               
    
    /**
     * During the user input phase of MgrEdit, each editable
     * column provides a widget for changing the cell of one
     * or more rows.  This method is called to return the
     * that editor widget.  If this column doesn't support
     * concurrent editing of all the given rows then return null.   
     */
    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
      throws Exception
    {
      BValue val = rows[0].getCell(colIndex);
      Property prop = BHistoryImport.configOverrides;
      BFacets facets = prop.getFacets();
      
      // create the editor, if the old one if of 
      // correct type, then let's reuse it
      BWbFieldEditor editor = BWbFieldEditor.makeFor(val, facets);
      if (currentEditor != null && currentEditor.getClass() == editor.getClass())
        editor = (BWbFieldEditor)currentEditor;
        
      editor.loadValue(val, facets);
      return editor;
    }  
    
    /**
     * This method is called to store the value in the editor
     * back to the cells of this column for each of the specified
     * rows.  The widget passed with whatever widget was returned
     * by the toEditor method.
     */
    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget)
      throws Exception
    {
      BWbFieldEditor editor = (BWbFieldEditor)widget;
      BValue val = (BValue)editor.saveValue();
      for(int i=0; i<rows.length; ++i)
        rows[i].setCell(colIndex, val);
    }
    
    String propName;
    BValue defaultValue;
  }
  
  static final Lexicon histLex = Lexicon.make("history");
}