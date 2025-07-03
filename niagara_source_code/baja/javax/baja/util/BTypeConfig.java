/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.util.stream.Stream;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.*;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.*;

import com.tridium.sys.schema.Fw;
import com.tridium.util.CompUtil;
import com.tridium.util.ObjectUtil;

/**
 * BTypeConfig is used to store both a TypeSpec and additional
 * configuration.  It is useful for configuring plugin objects
 * indirectly without requiring the target to be a BValue or
 * to exist directly in a station database.  The design pattern
 * is that subclasses implement syncTo() and syncFrom() to
 * synchronize the configuration state between the TypeConfig
 * and the target instance.  TypeConfig should store the state
 * using dynamic properties.  The default implementation of
 * syncTo() and syncFrom() assume the use of an inner class
 * called IConfigurable.
 *
 * @author    Brian Frank
 * @creation  12 May 04
 * @version   $Revision: 5$ $Date: 1/3/11 2:36:24 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The type spec of the target type to allocate.
 */
@NiagaraProperty(
  name = "typeSpec",
  type = "BTypeSpec",
  defaultValue = "BTypeSpec.NULL"
)
public abstract class BTypeConfig
  extends BVector
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BTypeConfig(424633046)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "typeSpec"

  /**
   * Slot for the {@code typeSpec} property.
   * The type spec of the target type to allocate.
   * @see #getTypeSpec
   * @see #setTypeSpec
   */
  public static final Property typeSpec = newProperty(0, BTypeSpec.NULL, null);

  /**
   * Get the {@code typeSpec} property.
   * The type spec of the target type to allocate.
   * @see #typeSpec
   */
  public BTypeSpec getTypeSpec() { return (BTypeSpec)get(typeSpec); }

  /**
   * Set the {@code typeSpec} property.
   * The type spec of the target type to allocate.
   * @see #typeSpec
   */
  public void setTypeSpec(BTypeSpec v) { set(typeSpec, v, null); }

  //endregion Property "typeSpec"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTypeConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////      
  
  /**
   * Get the base class which target instances must implement.
   */
  public abstract TypeInfo getTargetType(); 
  
  /**
   * Create a new instance using the current typeSpec
   * and automatically call syncTo() to initialize its
   * configuration.
   */
  public BObject make()
  {                           
    BObject target = getTypeSpec().getInstance();
    syncTo(target);
    return target;
  }                                                
  
////////////////////////////////////////////////////////////////
// Sync
////////////////////////////////////////////////////////////////
  
  /**
   * Synchronize this TypeConfig to the target instance.
   * This should initialize the target instance with all of
   * its configurable properties based on the state of
   * this TypeConfig.  The default implementation assumes
   * the target instance is an IConfigurable. 
   */
  public void syncTo(BObject target)
  {             
    if (!(target instanceof IConfigurable))
      throw new UnsupportedOperationException("Must override syncTo() if not using IConfigurable");
    
    IConfigurable cfg = (IConfigurable)target;
    String[] keys = cfg.listConfig();
    for(int i=0; i<keys.length; ++i)
    {                 
      String key = keys[i];
      Property prop = getProperty(key);
      if (prop != null)
        cfg.setConfig(key, get(prop));
    }
  }
  
  /**
   * Synchronize the target instance to this TypeConfig.
   * This method should initialize this instance's properties
   * with the configuration state of the target.  The
   * default implementation assumes the target is an 
   * IConfigurable and maps its configuration as dynamic 
   * properties on this instance.
   */
  public void syncFrom(BObject target)
  {
    if (!(target instanceof IConfigurable))
      throw new UnsupportedOperationException("Must override syncFrom() if not using IConfigurable");

    removeAll();  
    IConfigurable cfg = (IConfigurable)target;
    String[] keys = cfg.listConfig();
    for(int i=0; i<keys.length; ++i)
    {
      String key = keys[i];
      BValue value = cfg.getConfig(key);
      BFacets facets = cfg.getConfigFacets(key);   
      add(SlotPath.escape(key), value, Flags.READONLY, facets, null);
    }

    updateModuleDependencies(/*forceUpdate*/false);
  }               

         
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.STARTED:
        fwStarted();
        break;
      case Fw.CHANGED:
        fwChanged((Property)a);
        break;
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    try
    { // If running in rt only JACE, then the type spec likely won't resolve (but that's ok)
      BObject target = getTypeSpec().getInstance();

      // Check to make sure any missing dynamic config properties exist on this BTypeConfig
      // instance on startup (with default values)
      if (target instanceof IConfigurable)
      {
        IConfigurable cfg = (IConfigurable)target;
        String[] keys = cfg.listConfig();
        for (String key: keys)
        {
          String slotName = SlotPath.escape(key);
          if (getSlot(slotName) == null)
          {
            BValue value = cfg.getConfig(key);
            BFacets slotFacets = cfg.getConfigFacets(key);
            add(slotName, value, Flags.READONLY, slotFacets, null);
          }
        }
      }
    }
    catch(Exception ignore) { }

    updateModuleDependencies(/*forceUpdate*/false);
  }

  private void fwChanged(Property prop)
  {
    if (prop.equals(typeSpec))
    {
      configChanged();
      updateModuleDependencies(/*forceUpdate*/false);
    }
  }

  /**
   * This callback is called when either the typeSpec
   * of configuration has been modified.
   */
  public void configChanged()
  {                           
  }

  public final void updateModuleDependencies(boolean forceUpdate)
  {
    BComponentSpace space = getComponentSpace();
    if (!forceUpdate && ((space == null) || (space.isProxyComponentSpace())))
      return; // Short-circuit if not mounted in a master component space

    // First remove the old module dependencies facet
    try
    {
      remove("moduleDependenciesFacets");
    }
    catch(NoSuchSlotException ignore) {}

    // Next add the new module dependencies as dynamic, readonly, hidden slots
    BObject target = null;
    try
    { // If running in rt only JACE, then the type spec likely won't resolve
      target = getTypeSpec().getInstance();
    }
    catch(Exception ignore) { }

    BFacets facets = BFacets.NULL;
    if (target instanceof IConfigurable)
    {
      facets = ObjectUtil.appendModuleDependencyFacets(facets,
        ((IConfigurable)target).getModuleDependencyNames(this));
    }
    else
    { // Fallback if the type spec couldn't be resolved to an IConfigurable,
      // just use the type spec's module name
      facets = ObjectUtil.appendModuleDependencyFacets(facets,
        Stream.of(getTypeSpec().getModuleName()));
    }

    if (!facets.isNull())
      CompUtil.setOrAdd(this, "moduleDependenciesFacets", facets, Flags.HIDDEN | Flags.READONLY, BFacets.NULL, null);
  }
  
////////////////////////////////////////////////////////////////
// IConfigurable
////////////////////////////////////////////////////////////////
  
  /**
   * IConfigurable may be implemented by TypeConfig target
   * instances to provide a standard mechanism to use for
   * syncTo() and syncFrom().
   */
  public static interface IConfigurable
  { 
    /**
     * Get the list of String keys for the configurable properties.
     */
    public String[] listConfig();
    
    /**
     * Get a configurable property by its string key.
     */
    public BValue getConfig(String key);

    /**
     * Get a facets for a configurable property.
     */
    public BFacets getConfigFacets(String key);
    
    /**
     * Set a configurable property by its string key.
     */
    public void setConfig(String key, BValue value);

    /**
     * Return a Stream of Strings that define the names of module
     * dependencies that the current typeSpec selection requires
     *
     * @since Niagara 4.0
     * @param config - The BTypeConfig instance for reference
     * @return a Stream of Strings representing the names of module
     * dependencies that are required for the current typeSpec selection
     */
    default Stream<String> getModuleDependencyNames(BTypeConfig config)
    {
      return Stream.empty();
    };
  }

}
