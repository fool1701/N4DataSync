/*
 * Copyright 2009 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.PermissionException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This interface is implemented by objects that contain dynamic properties.
 *
 * @author    Scott Hoye
 * @creation  12 Aug 09
 * @version   $Revision: 1$ $Date: 8/18/09 4:26:55 PM EDT$
 * @since     Niagara 3.5
 */
@NiagaraType
public interface BIPropertyContainer
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIPropertyContainer(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIPropertyContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Get the display name for this instance.
   */
  String getDisplayName(Context cx);

////////////////////////////////////////////////////////////////
// Slot Access
////////////////////////////////////////////////////////////////

  /**
   * Get the display name of the specified slot.
   */
  String getDisplayName(Slot slot, Context cx);
  
  /**
   * Get the facets for the specified slot.
   * You should *never* return null, but rather BFacets.DEFAULT.
   */
  BFacets getSlotFacets(Slot slot);
  
  /**
   * This is a convenience for:
   * {@code get(prop).toString(getSlotFacets(prop))}
   */
  String propertyValueToString(Property prop, Context cx);

  /**
   * Get the Slot for specified name, or return null if name
   * does map to a Slot.
   *
   * @param name String name of slot to find.
   */
  Slot getSlot(String name);
  
  /**
   * Get the Property for specified name, or return null if
   * name does map to a Property.
   *
   * @param name String name of property to find.
   */
  Property getProperty(String name);
  
  /**
   * Get the number of slots on this object.
   */
  public int getSlotCount();

  /**
   * Convenience method to get the number of slots
   * of a particular class.
   */
  int getSlotCount(Class<?> cls);

  /**
   * Get a array listing all the slots.
   */
  Slot[] getSlotsArray();

  /**
   * Get a SlotCursor to iterate through all the slots.
   */
  SlotCursor<Slot> getSlots();

  /**
   * Get an array of all the properties.
   */
  Property[] getPropertiesArray();

  /**
   * Get an array of all the frozen properties.
   */
  Property[] getFrozenPropertiesArray();

  /**
   * Get an array of all the dynamic properties.
   */
  Property[] getDynamicPropertiesArray();

  /**
   * Get a cursor to iterate through all the properties.
   */
  SlotCursor<Property> getProperties();
  
  /**
   * Make sure that the children slots are loaded.  This method
   * allows component spaces to lazy load their components via
   * the LoadCallbacks API.  It may not be applicable to all
   * BIPropertyContainer subclasses, so it may be a no op and
   * return null for some non-BComponents instances.
   *
   * @return the BComplex instance that had its properties loaded (usually this, or null if not applicable)
   */
  BComplex loadSlots();
  
  /**
   * Convenience for {@code lease(0, 60000L)}.
   */
  void lease();

  /**
   * Only used for BComponents (in other cases, this is a no op).
   * Lease this component for the specified number of milliseconds
   * so that it expires at {@code Clock.ticks() + millis}.
   * Leasing is a stateless form of subscription which automatically 
   * performs a subscribe and then an unsubscribe when the lease 
   * expires.  If the component is already under lease, then the 
   * new lease expiration is the max of the previous lease expiration
   * or {@code Clock.ticks() + millis}.  If depth is greater than
   * zero then the lease includes descendants (one is children, two is
   * children and grandchildren, etc).
   */
  void lease(int depth, long millis);


////////////////////////////////////////////////////////////////
// Getters
////////////////////////////////////////////////////////////////

  /** Get the specified property. */
  BValue get(Property property);

  /**
   * Get a property by it's String name, or return
   * null if the name doesn't map to a property.
   *
   * @param propertyName String name of property to get.
   * @throws NoSuchSlotException if the propertyName maps
   *    to a slot, but it is not a Property.
   */
  BValue get(String propertyName);
  
  
////////////////////////////////////////////////////////////////
// Setters
////////////////////////////////////////////////////////////////

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
   */
  void set(Property property, BValue value, Context context);

  /**
   * Set using a String property name.
   */
  void set(String propertyName, BValue value);


////////////////////////////////////////////////////////////////
// Flags
////////////////////////////////////////////////////////////////

  /**
   * Get the bitmask of flags for the specified slot.
   *
   * @throws NoSuchSlotException if the slot is invalid.
   */
  int getFlags(Slot slot);

  /**
   * Set the bitmask of flags for the specified slot.  This
   * method is only supported on BComponent instances.  BSimples
   * have no slots and BStruct properties have fixed flags
   * which may not be modified on a per instance basis.
   *
   * @throws NoSuchSlotException if the slot is invalid.
   * @throws UnsupportedOperationException if called on a BStruct.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this container.
   */
  void setFlags(Slot slot, int flags, Context context);
  

////////////////////////////////////////////////////////////////
// Add
////////////////////////////////////////////////////////////////

  /**
   * Add a new slot to this container.  The new slot is
   * always a dynamic (un-frozen) Property.  
   *
   * @param name the unique name to use as the String
   *    key for the slot.  If null is passed, then a
   *    unique name should automatically be generated.
   *    If the name ends with the '?' character a unique
   *    name should automatically be generated by appending
   *    numbers to the specified name.  The name must meet 
   *    the "name" production in the SlotPath BNF grammar.  
   *    Informally this means that the name must start with 
   *    an ascii letter and contain only ascii letters, ascii 
   *    digits, or '_'.  Escape sequences can be specified 
   *    using the '$' char.  Use SlotPath.escape() to escape
   *    illegal characters.
   * @param value BValue value of the new property.
   * @param flags Mask of the properties slots using constants
   *    defined in Flags.
   * @param facets Facets provide additional meta-data about
   *    the property. Maybe null or BFacets.NULL if no facets
   *    are required.
   * @param context Used to provide additional contextual info.
   *
   * @return the property the slot was added with, or
   *    null if trapped by a transaction or remote call.
   *
   * @throws DuplicateSlotException if a slot already
   *    exists with specified name.
   * @throws IllegalNameException if the name contains
   *    illegal characters.  Use SlotPath.escape() to
   *    escape invalid characters.
   * @throws AlreadyParentedException if value
   *    is already contained by another object.
   *    If this is the case you should copy the
   *    value first.
   * @throws IllegalChildException if the child type is
   *    not allowed as a property on this instance.
   * @throws IllegalParentException if this instance type
   *    is not a legal parent type for the child.
   * @throws NullPointerException if value is
   *    null.  Properties may never be set to null.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this instance.
   */
  Property add(String name, BValue value, int flags, BFacets facets, Context context);


////////////////////////////////////////////////////////////////
// Remove
////////////////////////////////////////////////////////////////
  
  /**
   * Remove the dynamic slot by the specified name.
   *
   * @throws FrozenSlotException if specified slot is frozen.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this container.
   */
  void remove(String name, Context context);

  /**
   * Remove the specified slot.
   *
   * @throws FrozenSlotException if specified slot is frozen.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this container.
   */
  void remove(Property slot, Context context);

  /**
   * Remove all dynamic properties.
   */
  void removeAll(Context context);


////////////////////////////////////////////////////////////////
// Rename
////////////////////////////////////////////////////////////////

  /**
   * Rename the specified slot.
   *
   * @param slot Property to rename.
   * @param newName New String name for the property.
   *    The name must meet the "name" production in the
   *    SlotPath BNF grammar.  Informally this means that
   *    the name must start with an ascii letter, and 
   *    contain only ascii letters, ascii digits, or '_'.  
   *    Escape sequences can be specified using the '$' char.  
   *    Use SlotPath.escape() to escape illegal characters.
   * @param context Used to provide additional contextual info.
   *
   * @throws IllegalNameException if the name contains
   *    illegal characters.  Use SlotPath.escape() to
   *    escape invalid characters.
   * @throws FrozenSlotException if specified slot is frozen.
   * @throws DuplicateSlotException if a slot already
   *    exists with specified name.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this instance.
   */
  void rename(Property slot, String newName, Context context);


////////////////////////////////////////////////////////////////
// Set Facets
////////////////////////////////////////////////////////////////

  /**
   * Change the facets of a dynamic slot.
   *
   * @param slot Dynamic slot to change.
   * @param facets The BFacets instance to use as new facets for slot.
   * @param context Used to provide additional contextual info.
   *
   * @throws FrozenSlotException if specified slot is frozen.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this container.
   */
  void setFacets(Slot slot, BFacets facets, Context context);


////////////////////////////////////////////////////////////////
// Reorder
////////////////////////////////////////////////////////////////
  
  /**
   * Reorder this instance's dynamic properties.
   *
   * @throws FrozenSlotException if any of the specified
   *    properties are frozen.
   * @throws ArrayIndexOutOfBoundsException if the array length
   *    does not match number of dynamic properties.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this instance.
   */
  void reorder(Property[] dynamicProperties, Context context);

  /**
   * Reorder the specified dynamic property to the 
   * first dynamic slot position.  
   *
   * @throws FrozenSlotException if the specified
   *    property is frozen.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this container.
   */
  void reorderToTop(Property dynamicProperty, Context context);

  /**
   * Reorder the specified dynamic property to the 
   * last dynamic slot position.  
   *
   * @throws FrozenSlotException if the specified
   *    property is frozen.
   * @throws PermissionException if the context user does not
   *    have adminWrite permission on this container.
   */
  void reorderToBottom(Property dynamicProperty, Context context);
  
}
