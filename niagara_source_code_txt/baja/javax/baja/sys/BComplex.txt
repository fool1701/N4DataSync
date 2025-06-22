/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.HashMap;

import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.security.PermissionException;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFacetsMap;
import javax.baja.util.BFormat;
import javax.baja.util.BNameMap;
import javax.baja.util.CannotValidateException;

import com.tridium.sys.engine.NRelationKnob;
import com.tridium.sys.schema.ComplexSlotMap;
import com.tridium.sys.schema.ComplexType;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.schema.NProperty;

/**
 * BComplex is the BValue which is defined by one or more
 * property slots.  BComplex is never used directly, rather
 * it is the base class for BStruct and BComponent.
 *
 * @author    Brian Frank
 * @creation  1 Feb 00
 * @version   $Revision: 95$ $Date: 7/15/11 11:42:43 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BComplex
  extends BValue
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BComplex(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BComplex.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Package scoped constructor initializes all
   * the properties to their default value.
   */
  BComplex()
  {
    ComplexType type = (ComplexType)getType();
    if (type.getTypeClass() != getClass())
    {
      // make an exception for classes which are auto-generated
      // by AbstractStubGen for prototype purposes
      // or that are declared as inner or anonymous classes
      if (!getClass().getName().startsWith("auto.") && getClass().getEnclosingClass()==null)
        throw new TypeIntrospectionException(getClass(), "Class does override the getType() method");
    }
    slotMap = type.newSlotMap();
    slotMap.init(type, this);
  }

////////////////////////////////////////////////////////////////
// Identity
////////////////////////////////////////////////////////////////

  /**
   * The name of this object is based on containment in
   * its parent which is accessed via the getParentProperty()
   * method.  If the object is not parented, then name is null.
   */
  public final String getName()
  {
    return slotMap.getName();
  }

  /**
   * The display name of this object is based on the display
   * name of containment in its parent which is accessed via
   * the getParentProperty() method.  If the object is not
   * parented, then null is returned.
   */
  public final String getDisplayName(Context c)
  {
    return slotMap.getDisplayName(c);
  }

  /**
   * Get the display name of the specified slot.  The
   * default implementation is as follows:
   * <ol>
   * <li>If there is BNameMap property called "displayNames",
         then use the map's value if found.</li>
   * <li>Return slot.getDefaultDisplayName(cx)</li>
   * </ol>
   */
  public String getDisplayName(Slot slot, Context cx)
  {
    // check displayNames
    BValue displayNames = get("displayNames");
    if (displayNames instanceof BNameMap)
    {
      BNameMap map = (BNameMap)displayNames;
      BFormat format = map.get(slot.getName());
      if (format != null) return format.format((Object)null, cx);
    }

    // if the slot is a dynamic property and the value is
    // a MixIn, then route to the MixIn child
    if (slot.isDynamic())
    {
      BValue val = get((Property)slot);
      if (val instanceof BIMixIn)
        return ((BIMixIn)val).getDisplayNameInParent(cx);
    }

    // use default
    return slot.getDefaultDisplayName(cx);
  }

  /**
   * Get the parent object which contains this object.  If
   * this object is not currently the value of any slot, then
   * return null.
   */
  public final BComplex getParent()
  {
    return slotMap.getParent();
  }

  /**
   * Get the Property which is used to describe how this object is
   * contained by its parent.  If this object is not currently
   * parented, then return null.
   */
  public final Property getPropertyInParent()
  {
    return slotMap.getPropertyInParent();
  }

  /**
   * Get the nearest ancestor of this object which is
   * an instance of BComponent.  If this object is itself
   * a BComponent then return {@code this}.  Return
   * null if this object doesn't exist under a component.
   */
  public final BComponent getParentComponent()
  {
    BComplex p = this;
    while(!p.isComponent())
    {
      p = p.getParent();
      if (p == null) return null;
    }
    return p.asComponent();
  }

  /**
   * Get the property this object occupies within its
   * parent component's slot namespace.  If the object
   * is a component, then this is null indicating it
   * occupies the entire namespace.  If this object
   * is a direct child of the parent component then
   * this property is the same as getPropertyInParent().
   * Otherwise this object is actually a grandchild
   * or further descendent of the parent component, and
   * this method returns the property in the component
   * which represents the root of this object's path inside
   * the component.  Return null if this object doesn't
   * exist under a component.
   */
  public final Property getPropertyInParentComponent()
  {
    if (isComponent() || getParent() == null) return null;
    BComplex p = this;
    while(!p.getParent().isComponent())
    {
      p = p.getParent();
      if (p.getParent() == null) return null;
    }
    return p.getPropertyInParent();
  }
  
  /*
   * Returns true if this complex is a descendent of specified
   * component.
   */
  
  public boolean isDescendentOf(BComponent ancestor)
  {
    BComplex parent = getParent();
    while(parent != null)
    {
      if(parent == ancestor)
        return true;
      parent = parent.getParent();
    }
    return false;
  }

////////////////////////////////////////////////////////////////
// Copying
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code newCopy(CopyHints)}
   * using CopyHint defaults.
   */
  @Override
  public synchronized final BValue newCopy()
  {
    return slotMap.newCopy(copyHintsDefault, copySerialNum());
  }

  /**
   * Convenience for {@code newCopy(CopyHints)} where
   * {@code CopyHints.defaultOnClone = !exact}.
   */
  @Override
  public synchronized final BValue newCopy(boolean exact)
  {
    return slotMap.newCopy(exact ? copyHintsExact : copyHintsDefault, copySerialNum());
  }

  // statically cached CopyHints for convenience methods
  private static CopyHints copyHintsDefault = new CopyHints();
  private static CopyHints copyHintsExact   = new CopyHints();
  static
  {
    copyHintsDefault.defaultOnClone = true;
    copyHintsExact.defaultOnClone   = false;
  }

  /**
   * This method creates a clone of the BValue using the
   * specified CopyHints.  Refer to the public fields
   * of CopyHints for details on copy options.
   */
  @Override
  public synchronized final BValue newCopy(CopyHints hints)
  {
    return slotMap.newCopy(hints, copySerialNum());
  }

  /**
   * This operation copies the frozen property values from
   * the specified object to this instance.  The
   * specified object must be the same class or a
   * subclass of this instance.  Only properties declared
   * on this instance are copied, even though the specified
   * object may contain additional properties (frozen
   * or dynamic).  If this instance contains dynamic properties
   * then they are never copied since there can be no
   * guarantee about their existence or context on the
   * specified object.
   * <p>
   * This operation does not copy tags from the specified object.
   * <p>
   * When actually copying properties, this method is
   * applied recursively.  For properties which are
   * BSimples, then the value is simply a set using
   * a get from object.  But for all other properties
   * which are BComplexes, then copyFrom is applied.
   *
   * @param object the BComplex which contains the
   *   property values to copy into this instance's
   *   properties.
   * @throws ClassCastException if object is
   *   not the same class or a subclass of this
   *   instance.
   */
  public final void copyFrom(BComplex object, Context context)
  {
    slotMap.copyFrom( object.slotMap, context );
  }

  /**
   * Convenience for {@code copyFrom(object, Context.copying)}.
   * @see #copyFrom(BComplex, Context)
   */
  public final void copyFrom(BComplex object)
  {
    slotMap.copyFrom( object.slotMap, Context.copying );
  }

////////////////////////////////////////////////////////////////
// Equality
////////////////////////////////////////////////////////////////

  /**
   * Return built-in {@code java.lang.Object.hashCode()}.
   */
  public final int hashCode()
  {
    return super.hashCode();
  }

  /**
   * BComplex.equals() is defined as "this == obj".
   * To compare equality between the properites of
   * of this instance and the specified instance use
   * equivalent().
   */
  public final boolean equals(Object obj)
  {
    return this == obj;
  }

  /**
   * Compare if all of this object's properties are
   * equal to the specified object.
   */
  @Override
  public synchronized boolean equivalent(Object obj)
  {
    if (obj == null) return false;
    if (obj.getClass() != getClass()) return false;
    return slotMap.equivalent( ((BComplex)obj).slotMap );
  }

////////////////////////////////////////////////////////////////
// Slot Access
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code setFacets(slot, facets, null)}.
   *
   * @since Niagara 3.7
   */
  public final void setFacets(Slot slot, BFacets facets)
  {
    setFacets(slot, facets, null);
  }

  /**
   * For Complexes mounted in a component tree, change the facets of a slot.
   * The facets will be merged with the facets on the frozen slot giving
   * the slot's facets priority over the facets being set. The facets are stored
   * on the parent component in its slotFacets FacetsMap.
   *
   * @param slot Slot to change.
   * @param facets The BFacets instance to use as new facets for slot.
   * @param context Used to provide additional contextual info.
   *
   * @throws FrozenSlotException if specified slot is frozen.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this component.
   *
   * @since Niagara 3.7
   */
  public final void setFacets(Slot slot, BFacets facets, Context context)
  {
    if (slot.isFrozen())
    {
      BComplex p = this;
      BComponent parent = null;
      StringBuilder path = new StringBuilder();
      while(!p.isComponent())
      {
        path.insert(0, '/');
        path.insert(0, p.getName());
        p = p.getParent();
        if (p == null) break;
      }
      if (p != null)
        parent = p.asComponent();

      if (parent != null && (parent instanceof BComponent))
      {
        BFacetsMap facetsMap = (BFacetsMap)parent.get("slotFacets_");
        HashMap<String,BFacets> newMap = new HashMap<>();

        // extract names into a temporary hash map
        if (facetsMap != null)
        {
          String[] keys = facetsMap.list();
          for (String key : keys)
          {
            newMap.put(key, facetsMap.get(key));
          }
        }

        // if the new facets are null, then remove the slot facets
        // mapping for the slot
        if (facets.isNull())
          newMap.remove(path.toString()+slot.getName());
        else
        {
          //merge the set facets with the frozen slot facets
          // so that the facets in the map do NOT contain any of the frozen facets
          newMap.put(path.toString()+slot.getName(), BFacets.makeRemove(facets, slot.getFacets().list()));
        }

        // if there are no more mapped slot facets, then remove the
        // slotFacets slot if necessary
        if (newMap.isEmpty())
        {
          if (facetsMap != null)
            parent.remove("slotFacets_", context);
        }
        // otherwise replace or add the slotFacets map
        else
        {
          boolean toAdd = facetsMap == null;
          facetsMap = BFacetsMap.make(newMap);
          if (toAdd)
            parent.add("slotFacets_", facetsMap, Flags.HIDDEN, context);
          else
            parent.set("slotFacets_", facetsMap, context);
        }
      }
    }
    else
    {
      slotMap.setFacets(slot, facets, context);
    }
  }

////////////////////////////////////////////////////////////////
// Get Facets
////////////////////////////////////////////////////////////////

  /**
   * Get the facets for the specified slot.  The default
   * implementation is to return slot.getFacets().  You
   * should *never* return null, but rather BFacets.DEFAULT.
   *
   * Since Niagara 3.7 - If this Object is mounted in a component tree, it merges
   * the slot's facets with the facets defined for the slot in the slotFacets BFacetsMap
   * on the Object's parent Component (or the Object itself if it is a Component).
   *
   */
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot.isFrozen())
    {
      BComplex p = this;
      StringBuilder path = null;

      while (!p.isComponent())
      {
        if (path == null) path = new StringBuilder();

        path.insert(0, '/');
        path.insert(0, p.getName());

        p = p.getParent();
        if (p == null) break;
      }

      if (p != null)
      {
        BFacetsMap facetsMap = (BFacetsMap)p.get("slotFacets_");
        if (facetsMap != null)
        {
          BFacets facets = facetsMap.get(path == null ? slot.getName() : path.toString() + slot.getName());

          if (facets != null)
            return BFacets.make(facets, slot.getFacets());
        }
      }
    }
    return slot.getFacets();
  }

  /**
   * This is a convenience for:
   * {@code get(prop).toString(getSlotFacets(prop))}
   */
  public String propertyValueToString(Property prop, Context cx)
  {
    BFacets facets = getSlotFacets(prop);
    if (cx == null)
      cx = facets;
    else if (!facets.isNull())
      cx = new BasicContext(cx, facets);
    return get(prop).toString(cx);
  }

  /**
   * Make sure that the children slots are loaded.  This method
   * allows component spaces to lazy load their components via
   * the LoadCallbacks API.
   * @return this
   */
  public final BComplex loadSlots()
  {
    slotMap.loadSlots();
    return this;
  }

  /**
   * Get the Slot for specified name, or return null if name
   * does not map to a Slot.
   *
   * @param name String name of slot to find.
   */
  public final Slot getSlot(String name)
  {
    return slotMap.getSlot(name);
  }

  /**
   * Get the Property for specified name, or return null if
   * name does not map to a Slot.
   *
   * @param name String name of property to find.
   */
  public final Property getProperty(String name)
  {
    return (Property)slotMap.getSlot(name);
  }

  /**
   * Get the Action for specified name, or return
   * null if name does not map to a Slot.
   */
  public final Action getAction(String name)
  {
    return (Action)slotMap.getSlot(name);
  }

  /**
   * Get the Topic for specified name, or return
   * null if name does not map to a Slot.
   */
  public final Topic getTopic(String name)
  {
    return (Topic)slotMap.getSlot(name);
  }

  /**
   * Get the number of slots on this object.
   */
  public final int getSlotCount()
  {
    return slotMap.getSlotCount();
  }

  /**
   * Convenience method to get the number of slots
   * of a particular class.
   */
  public final int getSlotCount(Class<?> cls)
  {
    int count = 0;
    SlotCursor<Property> c = getProperties();
    while (c.next(cls))
      count++;
    return count;
  }

  /**
   * Get a array listing all the slots.
   */
  public final Slot[] getSlotsArray()
  {
    return slotMap.getSlotsArray();
  }

  /**
   * Get a SlotCursor to iterate through all the slots.
   */
  public final SlotCursor<Slot> getSlots()
  {
    return slotMap.getSlots();
  }

  /**
   * Get an array of all the properties.
   */
  public final Property[] getPropertiesArray()
  {
    return slotMap.getPropertiesArray();
  }

  /**
   * Get an array of all the frozen properties.
   */
  public final Property[] getFrozenPropertiesArray()
  {
    return slotMap.getFrozenPropertiesArray();
  }

  /**
   * Get an array of all the dynamic properties.
   */
  public final Property[] getDynamicPropertiesArray()
  {
    return slotMap.getDynamicPropertiesArray();
  }

  /**
   * Get the number of properties including both frozen and dynamic.
   *
   * @return Returns the number of properties.
   */
  public int getPropertyCount()
  {
    return slotMap.getPropertyCount();
  }

  /**
   * Get a cursor to iterate through all the properties.
   */
  public final SlotCursor<Property> getProperties()
  {
    return slotMap.getProperties();
  }

  /**
   * Get an array of all the actions.
   */
  public final Action[] getActionsArray()
  {
    return slotMap.getActionsArray();
  }

  /**
   * Get a cursor to iterate through all the actions.
   */
  public final SlotCursor<Action> getActions()
  {
    return slotMap.getActions();
  }

  /**
   * Get an array of all the topics.
   */
  public final Topic[] getTopicsArray()
  {
    return slotMap.getTopicsArray();
  }

  /**
   * Get a cursor to iterate through all the topics.
   */
  public final SlotCursor<Topic> getTopics()
  {
    return slotMap.getTopics();
  }

////////////////////////////////////////////////////////////////
// Getters
////////////////////////////////////////////////////////////////

  /** Get the specified property. */
  public final BValue get(Property property) { return slotMap.get(property); }

  /** Get the specified boolean property. */
  public final boolean getBoolean(Property property) { return slotMap.getBoolean(property); }

  /** Get the specified int property. */
  public final int getInt(Property property) { return slotMap.getInt(property); }

  /** Get the specified long property. */
  public final long getLong(Property property) { return slotMap.getLong(property); }

  /** Get the specified float property. */
  public final float getFloat(Property property) { return slotMap.getFloat(property); }

  /** Get the specified double property. */
  public final double getDouble(Property property) { return slotMap.getDouble(property); }

  /** Get the specified String property. */
  public final String getString(Property property) { return slotMap.getString(property); }

  /**
   * Get a property by it's String name, or return
   * null if the name doesn't map to a property.
   *
   * @param propertyName String name of property to get.
   * @throws NoSuchSlotException if the propertyName maps
   *    to a slot, but it is not a Property.
   */
  public final BValue get(String propertyName)
  {
    return slotMap.get(propertyName);
  }

////////////////////////////////////////////////////////////////
// Invariant Callbacks
////////////////////////////////////////////////////////////////

  /**
   * When a user-invoked batch {@code set()} of properties is about to be
   * committed on this complex, this callback will be made to allow any subclasses 
   * who wish to validate the new property values to be given a chance to do so
   * BEFORE the changes are committed.  If a non-null IPropertyValidator instance 
   * is returned, it will be given a chance to validate the new property values that 
   * are pending a batch set.
   * 
   * This callback only occurs when it is detected that a user caused the batch property 
   * change AND the context associated with the batch {@code set()} is not
   * one that would cause validation to be skipped.  In particular, this callback
   * will be made for a pending batch set when:
   * 
   * The given Context is not equal to {@code Context.skipValidate},
   * {@code Context.commit}, {@code Context.decoding}, or
   * {@code Context.copying}
   * AND one or more of the following conditions are true: 
   * 1. The given Context is equal to {@code Context.forceValidate}
   * 2. The given Context contains a non-null user (indicating the server side
   * handling of a user-invoked client side change)
   * 3. The space in which this complex lives is non-null and of type 
   * {@code BBogSpace} (indicating an offline change)
   * 4. The space in which this complex lives is non-null and returns true
   * for {@code isProxyComponentSpace()} (indicating a client side change)
   * 
   * It is worth noting that since this callback is made in both the proxy and 
   * master component spaces for a user-invoked batch property change, it gives you
   * the option to perform validation on the client and/or server side. If you only 
   * want to perform validation on the master component space (server side), you 
   * could check for {@code getParentComponent().isRunning()} prior to returning
   * a non-null IPropertyValidator instance from this method.  
   *
   * @param properties - The array of properties that are about to be set to new values (in batch)
   * @param context - The context associated with the pending set.
   * @return an IPropertyValidator instance that should be used to validate the pending batch
   * set, or null if validation is not needed (at the batch level) for this pending batch set.  If
   * a non-null value is returned, then the individual property {@code getPropertyValidator()}
   * callback will not be called for each individual property in the batch set.  Otherwise if null 
   * is returned, then the individual property {@code getPropertyValidator()} callback will be
   * called for each individual property in the batch set.
   * @since Niagara 4.0
   */
  public IPropertyValidator getPropertyValidator(Property[] properties, Context context)
  {
    return null;
  }
  
  /**
   * When a user-invoked {@code set()} of a property is about to be committed
   * on this complex, this callback will be made to allow any subclasses who wish 
   * to validate the new property value to be given a chance to do so BEFORE the change
   * is committed.  If a non-null IPropertyValidator instance is returned, it will be 
   * given a chance to validate the new property value that is pending a set.
   * 
   * This callback only occurs when it is detected that a user caused the property 
   * change AND the context associated with the {@code set()} is not
   * one that would cause validation to be skipped.  In particular, this callback
   * will be made for a pending set when:
   * 
   * The given Context is not equal to {@code Context.skipValidate},
   * {@code Context.commit}, {@code Context.decoding}, or
   * {@code Context.copying}
   * AND one or more of the following conditions are true:
   * 1. The given Context is equal to {@code Context.forceValidate}
   * 2. The given Context contains a non-null user (indicating the server side
   * handling of a user-invoked client side change)
   * 3. The space in which this complex lives is non-null and of type 
   * {@code BBogSpace} (indicating an offline change)
   * 4. The space in which this complex lives is non-null and returns true
   * for {@code isProxyComponentSpace()} (indicating a client side change)
   * 
   * It is worth noting that since this callback is made in both the proxy and 
   * master component spaces for a user-invoked property change, it gives you
   * the option to perform validation on the client and/or server side. If you only 
   * want to perform validation on the master component space (server side), you 
   * could check for {@code getParentComponent().isRunning()} prior to returning
   * a non-null IPropertyValidator instance from this method.  
   *
   * @param property - The property that is about to be set to a new value
   * @param context - The context associated with the pending set.
   * @return an IPropertyValidator instance that should be used to validate the pending
   * set, or null if validation is not needed for this pending set.
   * @since Niagara 4.0
   */
  public IPropertyValidator getPropertyValidator(Property property, Context context)
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Setters
////////////////////////////////////////////////////////////////

 /**
   * Set a group of properties on this BComplex in batch.  This is not
   * a transactional operation. Instead, it will attempt to set all properties
   * given, and for any that fail, they will be recorded in a BatchSetException while
   * successful ones will remain committed regardless of any failures.
   *
   * @param properties - The properties that should have their values set in batch
   *    (the array's entries should map one to one to the values array argument).
   * @param values - The new values that should be set in batch on the properties.
   *    (the array's entries should map one to one to the properties array argument).
   * @param context - The context associated with this set operation.
   * @throws BatchSetException if any problems occurred while committing the
   *    individual property sets that make up this batch set.  This exception gives
   *    feedback on the properties that were successfully committed, as well as those
   *    that failed.
   * @throws CannotValidateException if this batch set operation fails during
   *    validation. This exception indicates that the entire batch set operation failed
   *    validation, so none of the properties would be changed.   
   * @throws IllegalArgumentException if the properties and values array arguments aren't
   *    the same size.
   * @throws LocalizableRuntimeException under the same conditions that a 
   *    CannotValidateException occurs, but allows for more user friendly exception reporting
   *    
   * @since Niagara 4.0
   */
  public final void set(Property[] properties, BValue[] values, Context context)
  {
    slotMap.set(properties, values, context);    
  }
 
 /**
   * Set the specified property value.
   *
   * @throws NoSuchSlotException if property is an invalid property
   *    for this object.
   * @throws NullPointerException if value is null.  Properties may
   *    never be set to null.
   * @throws AlreadyParentedException if value is already contained
   *    by another object.  If this is the case you should copy the
   *    value first.
   * @throws PermissionException if the context user does not have
   *    write permission on the property slot.
   * @throws CannotValidateException if this set operation fails during
   *    validation. This exception indicates that the property value
   *    was not changed.   
   * @throws LocalizableRuntimeException under the same conditions that a 
   *    CannotValidateException occurs, but allows for more user friendly 
   *    exception reporting
   */
  public final void set(Property property, BValue value, Context context)
  {
    BValue oldValue = get(property);
    ComplexSlotMap oldMap = (oldValue == null) ? null : oldValue.getSlotMap();
    slotMap.set(property, oldValue, oldMap,
                value, value.getSlotMap(), context);
  }

  /** Set with null context */
  public final void set(Property property, BValue value) { set(property, value, null); }

  /** Set for a boolean */
  public final void setBoolean(Property property, boolean value, Context context)  { slotMap.setBoolean(property, value, context); }
  /** Set for an int */
  public final void setInt(Property property, int value, Context context)  { slotMap.setInt(property, value, context); }
  /** Set for a long */
  public final void setLong(Property property, long value, Context context)  { slotMap.setLong(property, value, context); }
  /** Set for a float */
  public final void setFloat(Property property, float value, Context context)  { slotMap.setFloat(property, value, context); }
  /** Set for a double */
  public final void setDouble(Property property, double value, Context context)  { slotMap.setDouble(property, value, context); }
  /** Set for a String */
  public final void setString(Property property, String value, Context context)  { slotMap.setString(property, value, context); }

  /** Set for a boolean with null context */
  public final void setBoolean(Property property, boolean value)  { slotMap.setBoolean(property, value, null); }
  /** Set for an int with null context */
  public final void setInt(Property property, int value)  { slotMap.setInt(property, value, null); }
  /** Set for a long with null context */
  public final void setLong(Property property, long value)  { slotMap.setLong(property, value, null); }
  /** Set for a float with null context */
  public final void setFloat(Property property, float value)  { slotMap.setFloat(property, value, null); }
  /** Set for a double with null context */
  public final void setDouble(Property property, double value)  { slotMap.setDouble(property, value, null); }
  /** Set for a String with null context */
  public final void setString(Property property, String value)  { slotMap.setString(property, value, null); }

  /**
   * Set using a String property name.
   */
  public final void set(String propertyName, BValue value)
  {
    Property prop = getProperty(propertyName);
    if (prop == null) throw new NoSuchSlotException(propertyName);
    set(prop, value);
  }

  /**
   * Set using a String property name.
   */
  public final void set(String name, BValue value, Context context)
  {
    Property p = getProperty(name);
    if (p == null)
      throw new NoSuchSlotException("No such slot: " + name);
    set(p, value, context);
  }


////////////////////////////////////////////////////////////////
// Flags
////////////////////////////////////////////////////////////////

  /**
   * Get the bitmask of flags for the specified slot.
   *
   * @throws NoSuchSlotException if the slot is invalid.
   */
  public final int getFlags(Slot slot)
  {
    return slotMap.getFlags(slot);
  }

  /**
   * Convenience for {@code setFlags(slot, flags, null)}.
   */
  public final void setFlags(Slot slot, int flags)
  {
    slotMap.setFlags(slot, flags, null);
  }

  /**
   * Set the bitmask of flags for the specified slot.  This
   * method is only supported on BComponent instances.  BSimples
   * have no slots and BStruct properties have fixed flags
   * which may not be modified on a per instance basis.
   *
   * @throws NoSuchSlotException if the slot is invalid.
   * @throws UnsupportedOperationException if called on a BStruct.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this component.
   */
  public final void setFlags(Slot slot, int flags, Context context)
  {
    slotMap.setFlags(slot, flags, context);
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Dump slots and information common to all BComplex's.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps();
    spyProperties(out);
    spyActions(out);
    spyTopics(out);
    spyKnobs(out);
    spySubscribers(out);
    spySpaceNode(out);
    spyTags(out);
    spyRelations(out);
    spyDebug(out);
    out.endProps();
  }

  void spyProperties(SpyWriter out)
  {
    loadSlots();
    
    BComponent comp = (BComponent)this;
    BPermissions perm = comp.getPermissions(out.getContext());
    
    SlotCursor<Property> c = getProperties();
    boolean firstProp = true;
    while(c.next())
    {
      Property p = c.property();
      BObject v = c.get();
      
      //Permissions check
      boolean canSpy = false;
      if(!perm.hasAdminRead() && perm.hasOperatorRead())
      {
        if(     (v.isSimple() || v.isStruct()) 
            &&  Flags.isOperator(this, c.slot())  )
        {
          canSpy = true;
        }
        else if(v.isComponent() && ((BComponent)v).getPermissions(out.getContext()).hasOperatorRead())
        {
          canSpy = true;
        }
      }
      else if(perm.hasAdminRead())
      {
        canSpy = true;
      }
      if(canSpy)
      {
        //This is here for cases such as
        //If the user is an operator and no properties have the operator flag
        if(firstProp)
        {
          out.trTitle("Properties", 2);
          firstProp = false;
        }
        String name = p.getName();
        spySlot(out, name, getFlags(p), v);
      }

      /*if (!v.isSimple())*/
      // escape on a slot name which is probably already escaped
      // is required because we *always* escape nav names to build
      // valid file paths
    }
  }

  void spyActions(SpyWriter out)
  {
    Action[] actions = getActionsArray();
    if (actions.length == 0) return;
    boolean firstAction = true;
    BComponent comp = (BComponent)this;
    BPermissions perm = comp.getPermissions(out.getContext());
    for (Action action : actions)
    {//Permissions check
      if (perm.hasAdminRead()
        || (!perm.hasAdminRead()
        && perm.hasOperatorRead()
        && Flags.isOperator(this, action)))
      {
        //This is here for cases such as
        //If the user is an operator and no actions have the operator flag
        if (firstAction)
        {
          out.trTitle("Actions", 2);
          firstAction = false;
        }
        spySlot(out, action.getName(), getFlags(action), action);

      }
    }
  }

  void spyTopics(SpyWriter out)
  {
    Topic[] topics = getTopicsArray();
    if (topics.length == 0) return;
    boolean firstTopic = true;
    BComponent comp = (BComponent)this;
    BPermissions perm = comp.getPermissions(out.getContext());
    for (Topic topic : topics)
    {//Permissions check
      if (perm.hasAdminRead()
        || (!perm.hasAdminRead()
        && perm.hasOperatorRead()
        && Flags.isOperator(this, topic)))
      {
        //This is here for cases such as
        //If the user is an operator and no topics have the operator flag
        if (firstTopic)
        {
          out.trTitle("Topics", 2);
          firstTopic = false;
        }
        spySlot(out, topic.getName(), getFlags(topic), topic);
      }
    }

  }

  void spySlot(SpyWriter out, String name, int flags, Object value)
  {
    Slot slot = this.getSlot(name);
    if(slot.isProperty())
      name = "<a href='" + out.href(SlotPath.escape(name)) + "'>" + name + "</a>";

    out.w("<tr><td align='left' nowrap='true'><b>").w(name).w("</b>");
    if (flags == 0) out.w("{0}");
    else out.w(" {").w(Flags.encodeToString(flags)).w("}");
    out.w("</td><td align='left' nowrap='true'>")
    .safe(value.toString())
    .w("</td></tr>\n");
  }

  void spyKnobs(SpyWriter out)
  {
    if (!isComponent()) return;
    Knob[] knobs = asComponent().getKnobs();
    if (knobs.length > 0)
    {
      out.trTitle("Knobs", 2);
      for (Knob knob : knobs)
      {
        out.prop(knob.getSourceSlotName(), knob.getLink());
      }
    }
    RelationKnob[] rknobs = asComponent().getRelationKnobs();
    if (rknobs.length == 0) return;
    out.trTitle("RelationKnobs", 2);
    for (RelationKnob rknob : rknobs)
    {
      if (rknob == null)
      {
        break;
      }
      BRelation relation = null;
      if (((NRelationKnob) rknob).isProxy())
      {
        BComponent relationParent = (BComponent) rknob.getRelationOrd().resolve(
          asComponent().getComponentSpace()).get();
        for (BRelation bRelation : relationParent.getComponentRelations())
        {
          if (bRelation.getId().getQName().equals(rknob.getRelationId()))
          {
            relation = bRelation;
            break;
          }
        }
        if (relation == null)
        {
          continue;
        }
      }
      else
      {
        if (asComponent().isRunning())
        {
          relation = rknob.getRelation();
        }
      }

      if (relation != null)
      {
        try
        {
          SlotPath slotPath = relation.getParent().asComponent().getSlotPath();
          BOrd targetOrd = rknob.getRelationOrd();
          out.prop(rknob.getRelationId(), IN + slotPath);
        }
        catch (Exception e)
        {
          out.prop(rknob.getRelationId(), e);

        }
      }
    }
  }

  void spySubscribers(SpyWriter out)
  {
    if (!isComponent()) return;
    Subscriber[] subs = asComponent().getSubscribers();
    if (subs.length == 0) return;
    out.trTitle("Subscribers", 2);
    for (Subscriber sub : subs)
    {
      out.w("<tr><td colspan='2'>").safe(sub).w("</td></tr>");
    }
  }

  void spySpaceNode(SpyWriter out)
  {
    if (!isComponent()) return;
    BComponent c = (BComponent)this;
    out.trTitle("SpaceNode", 2);
    out.prop("isMounted",      c.isMounted());
    out.prop("handle",         c.getHandle());
    out.prop("slotPath",       c.getSlotPath());
    out.prop("host",           c.getHost());
    out.prop("session",        c.getSession());
    out.prop("space",          c.getSpace());
    out.prop("absoluteOrd",    c.getAbsoluteOrd());
    out.prop("ordInHost",      c.getOrdInHost());
    out.prop("ordInSession",   c.getOrdInSession());
    out.prop("ordInSpace",     c.getOrdInSpace());
    out.prop("handleOrd",      c.getHandleOrd());
    out.prop("slotPathOrd",    c.getSlotPathOrd());
    out.prop("navOrd",         c.getNavOrd());
  }

  protected void spyRelations(SpyWriter out)
  {
  }

  protected void spyTags(SpyWriter out)
  {
  }

  void spyDebug(SpyWriter out)
  {
    out.trTitle("Debug", 2);
    out.prop("debugString", toDebugString());
    out.prop("type", getType());

    out.prop("parent", getParent());
    out.prop("propertyInParent", getPropertyInParent());

    if (isComponent())
    {
      BComponent comp = (BComponent)this;
      out.prop("running", comp.isRunning());
      out.prop("subscribed", comp.isSubscribed());
      out.prop("permanentlySubscribed", comp.isPermanentlySubscribed());
      out.prop("knobCount", comp.getKnobCount());
      out.prop("relationKnobCount", comp.getRelationKnobCount());
      out.prop("permissions", comp.getPermissions(out.getContext()));
      out.prop("slotsLoaded", ((ComponentSlotMap)slotMap).isBrokerPropsLoaded());
      out.prop("categoryMask", comp.getCategoryMask());
      out.prop("appliedCategoryMask", comp.getAppliedCategoryMask());
      out.prop("deepOrCategoryMask", ((ComponentSlotMap)slotMap).getDeepOrCategoryMask());
    }
  }

  protected static String IN = "(In) ";
  protected static String OUT = "(Out) ";


////////////////////////////////////////////////////////////////
// Slot Factory
////////////////////////////////////////////////////////////////

  protected static Property newProperty(int flags, BValue defaultValue, BFacets facets)
  {
    if (defaultValue == null)
      throw new NullPointerException("Null defaultValue");
    if (facets == null)
      facets = BFacets.NULL;
    return new NProperty(flags, defaultValue, facets);
  }

  protected static Property newProperty(int flags, boolean defaultValue, BFacets facets) { return newProperty(flags, BBoolean.make(defaultValue), facets); }
  protected static Property newProperty(int flags, int defaultValue, BFacets facets) { return newProperty(flags, BInteger.make(defaultValue), facets); }
  protected static Property newProperty(int flags, long defaultValue, BFacets facets) { return newProperty(flags, BLong.make(defaultValue), facets); }
  protected static Property newProperty(int flags, float defaultValue, BFacets facets) { return newProperty(flags, BFloat.make(defaultValue), facets); }
  protected static Property newProperty(int flags, double defaultValue, BFacets facets) { return newProperty(flags, BDouble.make(defaultValue), facets); }
  protected static Property newProperty(int flags, String defaultValue, BFacets facets) { return newProperty(flags, BString.make(defaultValue), facets); }

  protected static Property newProperty(int flags, BValue defaultValue) { return newProperty(flags, defaultValue, null); }
  protected static Property newProperty(int flags, boolean defaultValue) { return newProperty(flags, BBoolean.make(defaultValue), null); }
  protected static Property newProperty(int flags, int defaultValue) { return newProperty(flags, BInteger.make(defaultValue), null); }
  protected static Property newProperty(int flags, long defaultValue) { return newProperty(flags, BLong.make(defaultValue), null); }
  protected static Property newProperty(int flags, float defaultValue) { return newProperty(flags, BFloat.make(defaultValue), null); }
  protected static Property newProperty(int flags, double defaultValue) { return newProperty(flags, BDouble.make(defaultValue), null); }
  protected static Property newProperty(int flags, String defaultValue) { return newProperty(flags, BString.make(defaultValue), null); }

////////////////////////////////////////////////////////////////
// Framework Support
////////////////////////////////////////////////////////////////

  /**
   * Framework support access; this method should
   * never be used by developers.
   */
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.SLOT_MAP: return slotMap;
    }
    return super.fw(x, a, b, c, d);
  }

  /**
   * Package private method to obtain the slot map.
   */
  @Override
  final ComplexSlotMap getSlotMap()
  {
    return slotMap;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * Slot map is an auto-generated class which
   * provides the storage for each property and
   * handles routing actions and topics
   */
  ComplexSlotMap slotMap;

}
