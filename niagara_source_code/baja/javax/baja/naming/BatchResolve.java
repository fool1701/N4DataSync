/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.util.Array;
import javax.baja.space.BComponentSpace;
import javax.baja.space.BSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.virtual.BVirtualGateway;
import javax.baja.virtual.VirtualPath;

import com.tridium.sys.schema.Fw;

/**
 * BatchResolve is used to resolve a list of ords together.  Often
 * batch resolve has better performance then resolving the individual
 * ords especially over the network.
 *
 * @author    Brian Frank
 * @creation  29 Nov 04
 * @version   $Revision: 6$ $Date: 12/20/06 4:50:39 PM EST$
 * @since     Baja 1.0
 */
public class BatchResolve
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Construct with the specified list of ords.
   */
  public BatchResolve(BOrd[] ords)
  {
    this.ords = ords.clone();
    this.results = new Result[ords.length];
    for(int i=0; i<results.length; ++i) results[i] = new Result();
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the number of items in the batch.
   */
  public int size()
  {
    return ords.length;
  }                              
                              
  /**
   * Get the ord at the specified index.
   */
  public BOrd getOrd(int index)
  {
    return ords[index];
  }

  /**
   * Return true if the ord at the specified 
   * index has been successfully resolved.
   */
  public boolean isResolved(int index)
  {     
    return results[index].target != null;
  }

  /**
   * Get the resolve exception for the ord at the specified index or
   * null if no exception was raised during resolve (or if resolve
   * hasn't been attempted yet).
   */
  public Throwable getException(int index)
  {
    return results[index].exception;
  }

  /**
   * Get the OrdTarget result at the specified index.  If the
   * ord at the specified index is unresolved, then throw an
   * exception.
   */
  public OrdTarget getTarget(int index)
  {                                   
    Result result = results[index];
    OrdTarget t = result.target;
    if (t != null) return t;
    if (result.exception instanceof RuntimeException)
      throw (RuntimeException)result.exception;
    else
      throw new UnresolvedException(""+ords[index], result.exception);
  }                         
  
  /**
   * Convenience for {@code getTarget(index).get()}.
   */
  public BObject get(int index)
  {                   
    return getTarget(index).get();
  }                            

  /**
   * This method returns an array of all the resolved components.  
   * If any one targets was unresolved, then throw an exception.
   */
  public OrdTarget[] getTargets()
  {                
    OrdTarget[] targets = new OrdTarget[results.length];
    for(int i=0; i<targets.length; ++i)
      targets[i] = getTarget(i);
    return targets;
  }

  /**
   * Return an array of the targets as BObjects.  If any one of 
   * the targets was unresolved, then throw an exception.
   */
  public BObject[] getTargetObjects()
  {            
    OrdTarget[] targets = getTargets();     
    BObject[] objects = new BObject[targets.length];
    for(int i=0; i<objects.length; ++i)
      objects[i] = targets[i].get();
    return objects;
  }      

  /**
   * Return an array of the targets cast to a BComponent.  If
   * any one of the targets was unresolved, then throw an exception.
   */
  public BComponent[] getTargetComponents()
  {            
    OrdTarget[] targets = getTargets();     
    BComponent[] components = new BComponent[targets.length];
    for(int i=0; i<components.length; ++i)
      components[i] = (BComponent)targets[i].get();
    return components;
  }      
  
////////////////////////////////////////////////////////////////
// Resolve
////////////////////////////////////////////////////////////////
  
  /**
   * Convenience for {@code resolve(base, null)}.
   */
  public BatchResolve resolve(BObject base)
  {
    return resolve(base, null);
  }
  
  /**
   * Resolve the list of ords passed to the constructor.  Return this.
   */
  public BatchResolve resolve(BObject base, Context cx)
  {              
    // first attempt to resolve handles/slot paths; eventually
    // this could be made into a more generic API that gives
    // each BScheme the ability to do batch resolved; but
    // for now just handle the common case of BComponents
    try
    {
      resolveComponentSpace(base, cx);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    
    // now attempt to resolve anything still unresolved
    for(int i=0; i<ords.length; ++i)
    {
      Result result = results[i];         
      if (result.target != null || result.exception != null) continue;
      try
      {
        result.target = ords[i].resolve(base, cx);
        result.exception = null;
      }
      catch(Throwable e)
      {
        result.target = null;
        result.exception = e;
      }
    } 
    return this;
  }   

////////////////////////////////////////////////////////////////
// ComponentSpace Optimization
////////////////////////////////////////////////////////////////
  
  /**
   * This is an optimization to attempt to batch resolve ords 
   * in a BComponentSpace.  The end goal of this long stream of
   * code is to give the ComponentSpace the private framework
   * hook of Fw.ENSURE_LOADED.  We don't actually resolve anything,
   * but just make sure it is in memory so that normal resolve
   * is fast.
   */
  private void resolveComponentSpace(BObject base, Context cx)
  {               
    // first make a list of everything that 
    // resolves to a BValue in a BComponentSpace                   
    Array<ComponentSpaceItem> items = new Array<>(ComponentSpaceItem.class);
    for(int i=0; i<ords.length; ++i)
    {
      BOrd ord = ords[i]; 
      if (ord.toString().indexOf("slot:") >= 0 ||
          ord.toString().indexOf("virtual:") >= 0 || // 12/11/06 - Add virtual case
          ord.toString().indexOf("h:") >= 0)
      {   
        ComponentSpaceItem item = makeComponentSpaceItem(ord, base);
        if (item != null) items.add(item);
      }                                 
    }
    if (items.isEmpty()) return;
    
    // now break the items into groups for each space
    Array<ComponentSpaceGroup> groups = new Array<>(ComponentSpaceGroup.class);
    for(int i=0; i<items.size(); ++i)
    {                                  
      // for each item
      ComponentSpaceItem item = items.get(i);
      BComponentSpace space = item.space;  

      // find existing group
      ComponentSpaceGroup group = null;
      for(int j=0; j<groups.size(); ++j)
      {                  
        ComponentSpaceGroup g = groups.get(j);
        if (g.space == space) { group = g; break; }
      }                     
      
      // if we didn't find an existing group, create new one
      if (group == null)
      {                      
        group = new ComponentSpaceGroup(space);
        groups.add(group);                             
      }                                                     
      
      // add item to group
      group.items.add(item);
    }    
    
    // resolve each group
    for(int i=0; i<groups.size(); ++i)
    {
      ComponentSpaceGroup group = groups.get(i);
      resolveComponentSpaceGroup(group, base, cx);
    }
  }      
  
  /**
   * This method is called when something appears to be a
   * handle or slot path ord.  Here we try to turn the ord
   * into a ComponentSpaceItem or return null.
   */
  private ComponentSpaceItem makeComponentSpaceItem(BOrd ord, BObject base)
  {                                             
    ComponentSpaceItem item = new ComponentSpaceItem(ord);

    // normalize and parse into queries
    OrdQuery[] queries = ord.normalize().parse();
    int foundIndex = -1;
    for(int i=0; i<queries.length; ++i)
    {       
      OrdQuery q = queries[i];
      String scheme = q.getScheme();
       // 12/11/06 - Add virtual case as highest priority
      if (scheme.equals("virtual")) { foundIndex = i; item.slot = q; break; }
      if ((scheme.equals("h")) && (foundIndex == -1))
      { 
        foundIndex = i; item.h    = q; //break; 
      }
      if ((scheme.equals("slot")) && (foundIndex == -1))
      { 
        foundIndex = i; item.slot = q; //break; 
      }      
    }                
    
    // if we didn't find a handle or slot path scheme then bail
    if (foundIndex == -1) return null;
    
    // resolve the ord up to h: or slot: or virtual:
    BObject preObject = null;
    try
    {
      BOrd preOrd = BOrd.make(queries, 0, foundIndex);
      if (!preOrd.isNull()) preObject = preOrd.get(base); 
    }
    catch(Exception ignored)
    {                                   
    }                     
    
    // if preObject is a ComponentSpace, that is what we 
    // use; otherwise we extract the ComponentSpace from base
    BComponentSpace space = null;
    if (preObject instanceof BComponentSpace)
    {
      space = (BComponentSpace)preObject;
    }
    else if (preObject instanceof BVirtualGateway)
    {  // 12/11/06 - Check for virtual case, if virtual gateway, use its virtual space
      space = ((BVirtualGateway)preObject).getVirtualSpace();
    }
    else
    {
      BSpace baseSpace = BOrd.toSpace(base);   
      if (baseSpace instanceof BComponentSpace)
        space = (BComponentSpace)baseSpace;
    }
    if (space == null) return null;
    item.space = space;
    
    // we have a valid item to batch resolve
    return item;
  }                                     

  /**
   * Resolve all the items which are grouped together 
   * because they share the same component space.
   */
  private void resolveComponentSpaceGroup(ComponentSpaceGroup group, BObject base, Context cx)
  {  
    BComponentSpace space = group.space;
    ComponentSpaceItem[] items = group.items.trim();

    // first map every item to a slot path or if the query 
    // is a handle, put it into a list for processing next         
    Array<ComponentSpaceItem> handleItems = new Array<>(ComponentSpaceItem.class);
    for(int i=0; i<items.length; ++i)
    {                 
      ComponentSpaceItem item = items[i];              
      if (item.slot != null)         
        item.path = toSlotPath(item.slot, base);
      else
        handleItems.add(item);
    }          
    
    // if we have handles, then map to slot paths in batch
    if (!handleItems.isEmpty())
    {           
      // get the list of handles to resolve
      Object[] handles = new Object[handleItems.size()];
      for(int i=0; i<handleItems.size(); ++i)
      {
        ComponentSpaceItem item = handleItems.get(i);
        handles[i] = item.h.getBody();
      }                                    
      
      // resolve to paths in batch
      SlotPath[] handlePaths = space.handlesToSlotPaths(handles);
      
      // now map back to item's path field
      for(int i=0; i<handleItems.size(); ++i)
      {
        ComponentSpaceItem item = handleItems.get(i);
        item.path = handlePaths[i];
      }      
    }     
    
    // now get the complete list of SlotPaths
    Array<SlotPath> pathsAcc = new Array<>(SlotPath.class);
    for(int i=0; i<items.length; ++i)
    {
      ComponentSpaceItem item = items[i];
      if (item.path != null) pathsAcc.add(item.path);
    }     
    SlotPath[] paths = pathsAcc.trim();
    
    // use private fw() hook to ensure loaded in batch, this
    // is where FoxComponentSpace does it's optimization
    space.fw(Fw.ENSURE_LOADED, paths, null, null, null);
    
    // at this point we haven't actually resolved any of the 
    // ords yet, all we have done is make sure that everything
    // is in memory so that normal resolve should be speedy
  }
  
  /**
   * Given a slot: or virtual: OrdQuery and a base object, 
   * compute the absolute SlotPath.
   */
  SlotPath toSlotPath(OrdQuery q, BObject base)
  {             
    // map configured ord query to a SlotPath
    SlotPath path = null;
    boolean isVirtual = q.getScheme().equals("virtual");
    if (isVirtual)
      path = new VirtualPath(q.getBody());
    else
      path = new SlotPath(q.getBody());
    
    // if already absolute, return it
    if (path.isAbsolute()) return path;
    
    // otherwise we have to assume the base is a component
    if ((base instanceof BComponent) && (!isVirtual)) // only valid for non-virtual case
    {
      SlotPath basePath = ((BComponent)base).getSlotPath();
      return basePath.merge(path);
    }
    
    // no hope
    return null;
  }
  
////////////////////////////////////////////////////////////////
// ComponentSpaceGroup
////////////////////////////////////////////////////////////////

  /**
   * ComponentSpaceGroup is used to group all the 
   * ComponentSpaceItems with the same ComponentSpace.
   */
  static class ComponentSpaceGroup
  {                               
    ComponentSpaceGroup(BComponentSpace space)
    {
      this.space = space;
    }
    
    BComponentSpace space;
    Array<ComponentSpaceItem> items = new Array<>(ComponentSpaceItem.class);
  }               

////////////////////////////////////////////////////////////////
// ComponentSpaceItem
////////////////////////////////////////////////////////////////
    
  /**
   * ComponentSpaceItem is used to manage an ord that
   * resolved to a BValue in a BComponentSpace.
   */
  static class ComponentSpaceItem
  {            
    ComponentSpaceItem(BOrd ord) 
    {                      
      this.ord = ord;
    }
    
    BOrd ord;
    OrdQuery h, slot;      
    BComponentSpace space; 
    SlotPath path;
  }
  
////////////////////////////////////////////////////////////////
// Result
////////////////////////////////////////////////////////////////
  
  /**
   * Result is used to store the successful 
   * result or the failure exception.
   */
  static class Result
  {
    OrdTarget target;
    Throwable exception;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BOrd[] ords;
  Result[] results;   
  
}

