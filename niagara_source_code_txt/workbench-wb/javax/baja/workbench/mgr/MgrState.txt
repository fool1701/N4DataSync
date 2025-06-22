/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import java.util.BitSet;
import java.util.HashMap;
import javax.baja.naming.BOrd;
import javax.baja.ui.table.DynamicTableModel;
import javax.baja.ui.treetable.DynamicTreeTableModel;
import javax.baja.workbench.mgr.tag.BTagFilterEnum;

/**
 * MgrState is used to save state between hyperlinks.
 *
 * @author    Brian Frank
 * @creation  19 Jan 04
 * @version   $Revision: 10$ $Date: 6/8/10 5:23:54 PM EDT$
 * @since     Baja 1.0
 */
public class MgrState
{           
                                                                             
////////////////////////////////////////////////////////////////
// Cache
////////////////////////////////////////////////////////////////
  
  /**
   * Get the String key used to cache the state for the 
   * specified manager: <code>manager.getType().toString()</code>.
   *
   */
  public static String toKey(BAbstractManager manager)
  {                                                       
    return manager.getType().toString();
  }  
  
  /**
   * Get the cached MgrState for the specified Type of 
   * BAbstractManager or return null if not in cache.
   */
  public static MgrState getCached(String key)
  {                     
    return cache.get(key);
  }
  
  /**
   * Add the specified state to the cache.
   */
  public static void cache(String key, MgrState state)
  {                           
    cache.put(key, state);
  }                      

  
////////////////////////////////////////////////////////////////
// Restore
////////////////////////////////////////////////////////////////
  
  /**
   * This method is called against the cached MgrState instance 
   * when a manager is being loaded.  This method always routes 
   * to restoreForType().  If the last save was for the same ord 
   * as the current manager then this method also routes 
   * restoreForOrd().
   */
  public void restore(BAbstractManager manager)
  {
    restoreForType(manager);
    
    if(manager.getWbShell()!=null)
    {
      BOrd restoreOrd = manager.getWbShell().getActiveOrd();    
      if (lastSaveOrd != null && lastSaveOrd.equals(restoreOrd)) 
        restoreForOrd(manager);
    }
  }             
  
  /**
   * This is a hook for performing a restore based on 
   * managers of a specified type.  The default implementation
   * deals with the learn/tag/template toggle and columns turned on and off.
   */
  protected void restoreForType(BAbstractManager manager)
  {
    MgrController controller = manager.getController();
    MgrLearn learn = manager.getLearn();
    MgrTagDictionary tagDictionary = manager.getMgrTagDictionary();

    MgrTemplate mgrTemplate = manager.getMgrTemplate();

    restoreColumns(manager.getModel().getTable().dynamicModel, modelColumns);                                   
    
    if (learn != null)
    {
      controller.learnMode.setSelected(isLearnMode);
      restoreColumns(learn.getTable().dynamicModel, learnColumns);
    }
    if (tagDictionary != null)
    {
      tagDictionary.setNamespace(tagNamespace);
      controller.tagMode.setSelected(isTagMode);
      tagDictionary.setTagFilter(tagFilterEnum);
    }
    if (mgrTemplate != null)
    {
      controller.templateMode.setSelected(isTemplateMode);
      restoreColumns(mgrTemplate.getTable().dynamicModel, templateColumns);
    }

  }

  /**
   * This is a hook for performing a restore based on a 
   * manager for a specified ord.  It is only only called if
   * the last manager saved was for against the same ord
   * and the current manager.
   */
  protected void restoreForOrd(BAbstractManager manager)
  {
  }

////////////////////////////////////////////////////////////////
// Save
////////////////////////////////////////////////////////////////
  
  /**
   * Save the state of the specified manager which is being 
   * deactivated.  This method routes to saveForType() and 
   * saveForOrd().
   */
  public void save(BAbstractManager manager)
  {             
    lastSaveOrd = manager.getWbShell().getActiveOrd();
    saveForType(manager);
    saveForOrd(manager);
  }
  
  /**
   * Save state based on a type of manager.  The default implementation
   * deals with the learn/tag/template toggle and columns turned on and off.
   */
  protected void saveForType(BAbstractManager manager)
  {                  
    MgrController controller = manager.getController();
    MgrLearn learn = manager.getLearn();
    MgrModel model = manager.getModel();
    MgrTagDictionary tagDictionary = manager.getMgrTagDictionary();
    MgrTemplate mgrTemplate = manager.getMgrTemplate();
    
    modelColumns = saveColumns(model.getTable().dynamicModel);                                   
    
    if (learn != null)
    {              
      isTagMode = controller.tagMode.isSelected();
      isLearnMode = controller.learnMode.isSelected();
      learnColumns = saveColumns(learn.getTable().dynamicModel);
    }
    if(tagDictionary != null)
    {
      isTagMode = controller.tagMode.isSelected();
      tagNamespace = tagDictionary.getNamespace();
      tagFilterEnum = tagDictionary.getTagFilter();

    }
    if( mgrTemplate != null)
    {
      isTemplateMode = controller.templateMode.isSelected();
      if (isTemplateMode)
      {
        templateColumns = saveColumns(mgrTemplate.getTable().dynamicModel);
      }
    }



  }
  
  /**
   * Save state based for a specified ord.  This is a hook which let's 
   * subclasses easily save the last learn model.  See the restoreForOrd()
   * method.
   */
  protected void saveForOrd(BAbstractManager manager)
  {
  }                                                            
    
////////////////////////////////////////////////////////////////
// Utilities
////////////////////////////////////////////////////////////////

  BitSet saveColumns(DynamicTableModel model)
  {
    BitSet map = new BitSet();
    for(int i=0; i<model.getRootColumnCount(); ++i)
    {
      if (model.showColumn(i))
        map.set(i);
      else
        map.clear(i);
    }
    return map;
  }

  void restoreColumns(DynamicTableModel model, BitSet map)
  {             
    if (map == null) return;
    for(int i=0; i<model.getRootColumnCount(); ++i)
      model.setShowColumn(i, map.get(i));           
  }

  BitSet saveColumns(DynamicTreeTableModel model)
  {
    BitSet map = new BitSet();
    for(int i=0; i<model.getRootColumnCount(); ++i)
    {
      if (model.showColumn(i))
        map.set(i);
      else
        map.clear(i);
    }
    return map;
  }

  void restoreColumns(DynamicTreeTableModel model, BitSet map)
  {             
    if (map == null) return;
    for(int i=0; i<model.getRootColumnCount(); ++i)
      model.setShowColumn(i, map.get(i));           
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static HashMap<String, MgrState> cache = new HashMap<>();
  
  String tagNamespace;
  BOrd lastSaveOrd;        
  boolean isLearnMode;
  static boolean isTagMode;
  static boolean isTemplateMode;
  BitSet modelColumns;
  BitSet learnColumns;
  BitSet tagColumns;
  BitSet templateColumns;
  BTagFilterEnum tagFilterEnum;
}                    


