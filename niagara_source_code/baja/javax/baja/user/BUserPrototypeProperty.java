/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.user;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.IPropertyValidator;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.Validatable;

/**
 * @author Patrick sager
 * @creation 11/3/2016
 * @since Niagara 4.3
 *
 * {@code BUserPrototypeProperty} is used by {@code BUserPrototype} to store the value and overridable
 * option to be set on a {@code BUser}'s property when creating from {@code BUserPrototype}
 */
@NiagaraType
@NiagaraProperty(
  name = "overridable",
  type = "boolean",
  defaultValue = "false"
)
public class BUserPrototypeProperty
  extends BComponent
  implements IPropertyValidator
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BUserPrototypeProperty(3628645257)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "overridable"

  /**
   * Slot for the {@code overridable} property.
   * @see #getOverridable
   * @see #setOverridable
   */
  public static final Property overridable = newProperty(0, false, null);

  /**
   * Get the {@code overridable} property.
   * @see #overridable
   */
  public boolean getOverridable() { return getBoolean(overridable); }

  /**
   * Set the {@code overridable} property.
   * @see #overridable
   */
  public void setOverridable(boolean v) { setBoolean(overridable, v, null); }

  //endregion Property "overridable"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserPrototypeProperty.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BUserPrototypeProperty()
  {

  }

  /**
   * Create {@code BUserPrototypeProperty} with value only
   *
   * @param value value for the {@code value} property
   */
  public BUserPrototypeProperty(BValue value)
  {
    setValue(value);
  }

  /**
   * Create {@code BUserPrototypeProperty} with value and facets
   *
   * @param value value for the {@code value} property
   * @param facets facets for the {@code value} property
   */
  public BUserPrototypeProperty(BValue value, BFacets facets)
  {
    setValue(value);
    setFacets(getProperty("value"), facets);
  }

  public final void setValue(BValue value)
  {
    if (getProperty("value") != null)
    {
      set("value", value);
    }
    else
    {
      add("value", value);
    }
  }

  public final BValue getValue()
  {
    return get("value");
  }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final IPropertyValidator getPropertyValidator(Property property, Context context)
  {
    if (isRunning() && context != null && context.getUser() != null)
    {
      return this;
    }
    else
    {
      return null;
    }
  }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public void validateSet(Validatable validatable, Context context)
  {
    BValue value = validatable.getProposedValue("value");
    if (value != null)
    {
      checkChanged("value", value, context);
    }
  }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    checkChanged(name, value, context);
    super.checkAdd(name, value, flags, facets, context);
  }

  /**
   * @since Niagara 4.4U1
   */
  @Override
  public final void checkRename(Property property, String newName, Context context)
  {
    checkChanged(newName, get(property), context);
    super.checkRename(property, newName, context);
  }

  /**
   * Validate a change from a set, add, or rename before it takes effect.
   * Throws a RuntimeException if the change is not allowed.
   *
   * @param name the name of the property that is changing
   * @param newValue the proposed value of the property
   * @param context the context that the change is executing with
   * @since Niagara 4.4U1
   */
  private void checkChanged(String name, BValue newValue, Context context)
  {
    BComplex parent = getParent();
    if (isRunning() && context != null && context.getUser() != null
      && parent instanceof BUserPrototype && BUserPrototype.roles.equals(getPropertyInParent())
      && name.equals("value"))
    {
      ((BUserPrototype)parent).checkRoleChange(newValue, context);
    }
  }
}
