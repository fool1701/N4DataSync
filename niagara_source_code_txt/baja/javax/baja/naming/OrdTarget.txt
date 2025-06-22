/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.space.*;
import javax.baja.sys.*;
import javax.baja.user.*;
import javax.baja.file.*;
import javax.baja.security.*;

/**
 * OrdTarget is the result of resolving a BOrd.
 *
 * @author    Brian Frank
 * @creation  10 Feb 03
 * @version   $Revision: 38$ $Date: 9/8/09 1:59:07 PM EDT$
 * @since     Baja 1.0
 */
public class OrdTarget
  implements Context
{  

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor used during BOrdScheme.resolve().
   */  
  public OrdTarget(OrdTarget base, BObject object)
  {                   
    this.base    = base;
    this.user    = base.user;
    this.lang    = base.lang;
    this.facets  = base.facets;
    this.ord     = base.ord;
    this.queries = base.queries;
    
    if (object != null)
    {
      this.object = object;
    }
    else
    {
      this.object           = base.object;
      this.permissions      = base.permissions;
      this.container        = base.container;
      this.isTargetReadonly = base.isTargetReadonly;
      this.slot             = base.slot;
      this.propertyPath     = base.propertyPath;
    }                              
  }

  /**
   * Make an OrdTarget with the specified base and object that has the specified additional
   * facets.
   *
   * @param base The base OrdTarget.
   * @param object The target object of the OrdTarget.
   * @param facets The facets that will be merged with the base facets.
   * @return Returns a new OrdTarget.
   */
  public static OrdTarget makeWithFacets(OrdTarget base, BObject object, BFacets facets)
  {
    return OrdTarget.makeWithFacetsAndLanguage(base, object, facets, null);
  }

  /**
   * Make an OrdTarget with the specified base and object that has the specified additional
   * facets and language.
   *
   * @param base The base OrdTarget.
   * @param object The target object of the OrdTarget.
   * @param facets The facets that will be merged with the base facets.
   * @param language The language that will be used if provided
   * @return Returns a new OrdTarget.
   * @since Niagara 4.2U2
   */
  public static OrdTarget makeWithFacetsAndLanguage(OrdTarget base, BObject object, BFacets facets, String language)
  {
    OrdTarget t = new OrdTarget(base, object);
    if (facets != null)
      t.facets = BFacets.make(t.facets, facets);

    if (language != null)
      t.lang = language;
    return t;
  }

  /**
   * Make an OrdTarget with the specified base and
   * additional ViewQuery.
   *
   * @param base The base OrdTarget.
   * @param viewQuery The additional ViewQuery.
   * @return Returns a new OrdTarget.
   * @since Niagara 4.11
   */
  public static OrdTarget makeWithViewQuery(OrdTarget base, ViewQuery viewQuery)
  {
    if(viewQuery == null)
    {
      throw new IllegalArgumentException("ViewQuery must be specified");
    }
    OrdTarget t = new OrdTarget(base);
    if (t.ord.isNull())
    {
      t.ord = BOrd.make(viewQuery);
    }
    else
    {
      t.ord = BOrd.make(t.ord, BOrd.make(viewQuery)).normalize();
    }

    t.queries = t.ord.parse();
    return t;
  }

  /**
   * Construct an OrdTarget which wraps another OrdTarget.
   */  
  public OrdTarget(OrdTarget base)
  {               
    this(base, null);
  }

  /**
   * Make an OrdTarget with the specified base and additional facets.
   *
   * @param base The base OrdTarget.
   * @param facets The facets that will be merged with the base facets.
   * @return Returns a new OrdTarget.
   */
  public static OrdTarget makeWithFacets(OrdTarget base, BFacets facets)
  {
    return makeWithFacetsAndLanguage(base, null, facets, null);
  }
  
  /**
   * Package scoped constructor for base.
   */  
  OrdTarget(Context cx, BOrd ord, OrdQuery[] queries, BObject object)
  {
    BasicContext temp = new BasicContext(cx);
    this.user    = temp.getUser();
    this.lang    = temp.getLanguage();
    this.facets  = temp.getFacets();
    this.ord     = ord;
    this.queries = queries;
    this.object  = object;          
  }

  /**
   * Package scoped constructor to SlotPath.
   */  
  public OrdTarget(
    OrdTarget  base,
    BComponent component,
    BObject    object,
    Slot       slot,
    Property[] propertyPath)
  {
    this(base, (BIPropertyContainer)component, object, slot, propertyPath);
  }
  
  /**
   * Package scoped constructor to SlotPath.
   *
   * @since Niagara 3.5
   */  
  OrdTarget(
    OrdTarget  base,
    BIPropertyContainer container,
    BObject    object,
    Slot       slot,
    Property[] propertyPath)
  {
    this(base, object);
    this.container    = container;
    this.object       = object;
    this.slot         = slot;
    this.propertyPath = propertyPath;
  }
  
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Convenience for {@code unmounted(target, null)}
   * 
   * @see #unmounted(BObject, Context)
   * @since Niagara 3.5
   */
  public static OrdTarget unmounted(BObject target)
  {
    return unmounted(target, null);
  }
  
  /**
   * Creates an unmounted OrdTarget. An unmounted target has no
   * base, OrdQuerys, or BOrd associated with it; it just wraps
   * a BObject.
   * 
   * @since Niagara 3.5
   */
  public static OrdTarget unmounted(BObject target, Context cx)
  {
    return new OrdTarget(cx, BOrd.NULL, new OrdQuery[0], target);
  }

////////////////////////////////////////////////////////////////
// Context
////////////////////////////////////////////////////////////////

  /**
   * Get user which originates from context passed to BOrd.resolve().
   */
  @Override
  public BUser getUser()
  { 
    return user; 
  }

  /**
   * Get language which originates from context passed to BOrd.resolve().
   */
  @Override
  public String getLanguage()
  { 
    return lang; 
  }

  /**
   * Get the facets which originates from context passed to BOrd.resolve().
   */
  @Override
  public BFacets getFacets()
  {    
    // if this is an ord target for a slot within a container
    // then fetch the slot facets and merge those with facets
    // from the original context (only do this once though since
    // it could be fairly expensive)
    if ((propertyPath != null || slot != null) && !mergedSlotFacets && container != null)
    {                      
      BFacets slotFacets = BFacets.NULL;
      if (propertyPath != null)
      {
        BObject c = (BObject)container;
        boolean isPropContainer = true;
        for(int i=0; i<propertyPath.length; ++i)
        {                       
          Property prop = propertyPath[i];
          slotFacets = BFacets.make((isPropContainer)?((BIPropertyContainer)c).getSlotFacets(prop):c.asComplex().getSlotFacets(prop), slotFacets);
          c = (isPropContainer)?((BIPropertyContainer)c).get(prop):c.asComplex().get(prop);
          isPropContainer = (c instanceof BIPropertyContainer);
        }
      }                                                  
      else
      {
        slotFacets = BFacets.make(container.getSlotFacets(slot), slotFacets);
      }
      facets =  BFacets.make(slotFacets, facets);
      mergedSlotFacets = true;
    }
    return facets;
  }
  
  /**
   * Convenience for {@code getFacets().get(name)}.
   */
  @Override
  public BObject getFacet(String name)
  {
    return getFacets().get(name);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Get the base Context or null if this maps to the root query.
   */
  @Override
  public Context getBase()
  {
    return base;
  }

  /**
   * If the {@code getBase()} is an OrdTarget return it, otherwise null.
   */
  public OrdTarget getBaseOrdTarget()
  {
    return base;
  }

  /**
   * Get the ord used to resolve the target.
   */
  public BOrd getOrd()
  {
    return ord;
  }

  /**
   * Get the space for this target.
   *
   * @return Returns the space that target object belongs to
   *   or null if the object is not associated with a space.
   */
  public BSpace getSpace()
  {
    OrdTarget target = this;
    
    while (target != null)
    {
      BSpace space = BOrd.toSpace(object);
      if (space == null)
      {
        BComponent component = getComponent();
        if (component != null)
          space = component.getSpace();
      }
      
      if (space != null) return space;
      target = target.getBaseOrdTarget();
    }
    
    return null;
  }

////////////////////////////////////////////////////////////////
// Target Access
////////////////////////////////////////////////////////////////

  /**
   * Get the value of the resolved ord.
   */
  public BObject get()
  {
    return object;
  }
  
  /**
   * If the resolved value is a BComponent then return 
   * {@code get()} cast to a BComponent.  If the value
   * is a slot in a BComponent, then return the parent
   * BComponent.  Otherwise return null.
   */
  public BComponent getComponent()
  {
    if (container instanceof BComponent) return (BComponent)container;
    if (object.isComponent()) return object.asComponent();
    if (object.isComplex()) 
    {
      BComponent c = object.asComplex().getParentComponent();
      if (c != null) return c;
    }
    if (base != null) return base.getComponent();
    return null;
  }                        

  /**
   * If the ord resolves to a property within a component,
   * then return the parent of get().  If the ord resolved
   * to a BComponent itself or something else then return null.
   * {@code
   *  getParent().get(getPropertyInParent()) == get()
   * }
   */
  public BComplex getParent()
  {
    if ((container instanceof BComplex) && propertyPath != null)
    {                                      
      BComplex p = (BComplex)container;
      for (int i=0; i<propertyPath.length-1; ++i)
        p = (BComplex)p.get(propertyPath[i]);

      return p;
    }   
    
    if (base != null) return base.getParent();
    return null;
  }
    
  /**
   * If the ord resolves to a slot within a component then
   * return the slot of {@code getComponent()}.  If not
   * applicable then return null.
   */
  public Slot getSlotInComponent()
  {        
    if (slot != null) return slot;
    
    // In keeping consistent with the getComponent() code above, we only want to
    // return the base's slot if the base's getComponent() is used.
    if ((base != null) &&
        (container == null) &&
        (!object.isComponent()) &&
        ((!object.isComplex()) || (object.asComplex().getParentComponent() == null)))
      return base.getSlotInComponent();
    
    return null;
  }

  /**
   * If the ord resolves to a property value within a component 
   * then return the path of properties which identify the value
   * within the component.  If not applicable then return null.
   */
  public Property[] getPropertyPathInComponent()
  {                    
    if (propertyPath != null) return propertyPath;
    if (base != null) return base.getPropertyPathInComponent();    
    return null;
  }                  
  
  /**
   * If the ord resolves to a property value within a component,
   * then return {@code path[path.length-1]} which is the
   * value's property in {@code getParent()}.  Otherwise
   * return null.
   */
  public Property getPropertyInParent()
  {
    if (propertyPath != null) return propertyPath[propertyPath.length-1];
    if (base != null) return base.getPropertyInParent();    
    return null;
  }       
  
////////////////////////////////////////////////////////////////
// Security
////////////////////////////////////////////////////////////////
    
  /**
   * Get the IProtected object to use for security
   * checks.  If the target itself is an IProtected then
   * return it.  If this is a child slot of a BComponent
   * then return the BComponent.  Otherwise return 
   * {@code getBase().getSecurityTarget()}  If all
   * else fails, then return null.
   */
  public BIProtected getSecurityTarget()
  {
    if (object instanceof BIProtected) return (BIProtected)object;
    if (container instanceof BIProtected) return (BIProtected)container;
    if (base != null) return base.getSecurityTarget();
    return null;
  }

  /**
   * Get the permissions the BUser has enabled on the target 
   * object.  This method is the cached result of calling
   * {@code getSecurityTarget().getPermissions(this)}.
   */
  public BPermissions getPermissionsForTarget()
  {        
    if (permissions == null)
    {                       
      BIProtected target = getSecurityTarget();                            
      if (target != null)
        permissions = target.getPermissions(this);
      else
        permissions = BPermissions.all;
    }
    return permissions;
  }                      
  
  /**
   * Convenience for {@code getSecurityTarget().canRead(this)}.
   * If {@code getSecurityTarget()} is null, then return true.
   */
  public boolean canRead()
  {                                     
    BIProtected st = getSecurityTarget();
    if (st != null) return st.canRead(this);
    return true;
  }

  /**
   * Convenience for {@code getSecurityTarget().canWrite(this)}.
   * If {@code getSecurityTarget()} is null, then return true.
   */
  public boolean canWrite()
  {                        
    BIProtected st = getSecurityTarget();
    if (st != null) return st.canWrite(this);
    return true;
  }

  /**
   * Convenience for {@code getSecurityTarget().canInvoke(this)}.
   * If {@code getSecurityTarget()} is null, then return true.
   */
  public boolean canInvoke()
  {                        
    BIProtected st = getSecurityTarget();
    if (st != null) return st.canInvoke(this);
    return true;
  }
  
////////////////////////////////////////////////////////////////
// Query Access
////////////////////////////////////////////////////////////////  

  /**
   * Get the length of the query list that was used to
   * resolve this target.
   */
  public int depth()
  {
    return (queries == null) ? 0 : queries.length;
  }

  /**
   * Get the query at the specified index.
   */
  public OrdQuery queryAt(int index)
  {
    return queries[index];
  }

  /**
   * Get the parsed queries of the target ord.
   */
  public OrdQuery[] getOrdQueries()
  {
    return queries.clone();
  }
  
  /**
   * Search from the last query down to the first query looking
   * for an instance of FilePath.  If not found return null.
   */
  public FilePath getFilePath()
  {
    for(int i=queries.length-1; i>=0; --i)
      if (queries[i] instanceof FilePath)
        return (FilePath)queries[i];
    return null;
  }
  
  /**
   * Return {@code getFilePath().getFragment()}.  If
   * {@code getFilePath()} returns null, then return null.
   */
  public String getFilePathFragment()
  {
    FilePath path = getFilePath();
    if (path != null) return path.getFragment();
    return null;
  }
  
  /**
   * If there was a view query in the ord then return it,
   * otherwise return null.
   */
  public ViewQuery getViewQuery()
  {
    // view query is always the last query
    int n = queries.length-1;
    if (n >= 0 && queries[n] instanceof ViewQuery)
      return (ViewQuery)queries[n];
    return null;
  }                     
  
  /**
   * If there is a ViewQuery, then return 
   * {@code getViewQuery().getParameter(name, def)},
   * otherwise return def.
   */                                      
  public String getViewParameter(String name, String def)
  {                       
    ViewQuery q = getViewQuery();
    if (q == null) return def;
    return q.getParameter(name, def);
  }
  
  /**
   * If there is a view query, get the ord without it.
   */
  public BOrd getOrdWithoutViewQuery()
  {
    if (getViewQuery() == null)
      return ord;
    else
      return BOrd.make(queries, 0, queries.length-1);
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////  
    
  /**
   * To string.
   */
  public String toString()
  {
    return ord + " -> " + object;
  }             
  
  /**
   * Dump all the gory details to std out.
   */
  public void dump()
  {
    System.out.println(getClass().getName());
    System.out.println("  ord:         " + ord);
    System.out.println("  facets:      " + facets);
    System.out.println("  object:      " + object);
    System.out.println("  permissions: " + permissions);
    System.out.println("  container:   " + container);
    System.out.println("  readonly:    " + isTargetReadonly);
    System.out.println("  slot:        " + slot);  
    System.out.println("  propPath:    " + (propertyPath == null ? "null" : ""+propertyPath.length));  
    System.out.println("  component(): " + getComponent());
    System.out.println("  slot():      " + getSlotInComponent());  
    System.out.println("  propPath():  " + (getPropertyPathInComponent() == null ? "null" : ""+getPropertyPathInComponent().length));  
    if (base != null) base.dump();
  }          

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  // context
  BUser user;
  String lang;
  BFacets facets;

  OrdTarget base;
  BOrd ord;
  OrdQuery[] queries;
  BObject object;
  BPermissions permissions;
  
  // slot path component specific fields
  BIPropertyContainer container;  
  boolean isTargetReadonly;
  Slot slot;
  Property[] propertyPath;
  boolean mergedSlotFacets;
  
  
}

