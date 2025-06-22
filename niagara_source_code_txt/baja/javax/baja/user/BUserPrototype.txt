/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.user;

import java.util.Map;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInteger;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.IPropertyValidator;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.Validatable;

/**
 * @author Patrick Sager on 11/3/2016
 * @since Niagara 4.4
 *
 * {@code BUserPrototype} acts as a template to create a {@link BUser} from a set of default values.
 * Each property on {@code BUser}, excluding properties that should not be created from a template,
 * has a matching property on {@code BUserPrototype} of type {@link BUserPrototypeProperty}. The
 * {@code BUserPrototypeProperty} stores the value that the {@code BUser}'s property should be set
 * to, as well as an {@code overridable} property that determines whether or not the {@code BUser}
 * property can be modified after being set by the {@code BUserPrototype}
 */
@NiagaraType
@NiagaraProperty(
  name = "fullName",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BString.make(\"\"))"
)
@NiagaraProperty(
  name = "enabled",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BBoolean.TRUE)"
)
@NiagaraProperty(
  name = "expiration",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BAbsTime.NULL, BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:ExpirationFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"webEditors:ExpirationEditor\")))"
)
@NiagaraProperty(
  name = "language",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BString.make(\"\"), BFacets.make(BFacets.FIELD_WIDTH, BInteger.make(6)))"
)
@NiagaraProperty(
  name = "email",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BString.make(\"\"))"
)
@NiagaraProperty(
  name = "facets",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BFacets.NULL, BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:UserFacetsFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"webEditors:UserFacetsEditor\"),\"enablePopOut\", BBoolean.FALSE))"
)
@NiagaraProperty(
  name = "navFile",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BOrd.NULL)"
)
@NiagaraProperty(
  name = "cellPhoneNumber",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BString.make(\"\"))"
)
@NiagaraProperty(
  name = "roles",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BString.make(\"\"), BFacets.make(BFacets.FIELD_EDITOR, BString.make(\"wbutil:RoleFE\"), BFacets.UX_FIELD_EDITOR, BString.make(\"webEditors:RolesEditor\"), BFacets.SECURITY, BBoolean.TRUE))"
)
@NiagaraProperty(
  name = "allowConcurrentSessions",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(BBoolean.TRUE, BFacets.make(BFacets.SECURITY, BBoolean.TRUE))"
)
@NiagaraProperty(
  name = "autoLogoffSettings",
  type = "BUserPrototypeProperty",
  defaultValue = "new BUserPrototypeProperty(new BAutoLogoffSettings(), BFacets.make(BFacets.SECURITY, BBoolean.TRUE))"
)

public class BUserPrototype
  extends BComponent
  implements IPropertyValidator
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BUserPrototype(680958646)1.0$ @*/
/* Generated Fri Sep 09 08:30:40 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "fullName"

  /**
   * Slot for the {@code fullName} property.
   * @see #getFullName
   * @see #setFullName
   */
  public static final Property fullName = newProperty(0, new BUserPrototypeProperty(BString.make("")), null);

  /**
   * Get the {@code fullName} property.
   * @see #fullName
   */
  public BUserPrototypeProperty getFullName() { return (BUserPrototypeProperty)get(fullName); }

  /**
   * Set the {@code fullName} property.
   * @see #fullName
   */
  public void setFullName(BUserPrototypeProperty v) { set(fullName, v, null); }

  //endregion Property "fullName"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, new BUserPrototypeProperty(BBoolean.TRUE), null);

  /**
   * Get the {@code enabled} property.
   * @see #enabled
   */
  public BUserPrototypeProperty getEnabled() { return (BUserPrototypeProperty)get(enabled); }

  /**
   * Set the {@code enabled} property.
   * @see #enabled
   */
  public void setEnabled(BUserPrototypeProperty v) { set(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "expiration"

  /**
   * Slot for the {@code expiration} property.
   * @see #getExpiration
   * @see #setExpiration
   */
  public static final Property expiration = newProperty(0, new BUserPrototypeProperty(BAbsTime.NULL, BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:ExpirationFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:ExpirationEditor"))), null);

  /**
   * Get the {@code expiration} property.
   * @see #expiration
   */
  public BUserPrototypeProperty getExpiration() { return (BUserPrototypeProperty)get(expiration); }

  /**
   * Set the {@code expiration} property.
   * @see #expiration
   */
  public void setExpiration(BUserPrototypeProperty v) { set(expiration, v, null); }

  //endregion Property "expiration"

  //region Property "language"

  /**
   * Slot for the {@code language} property.
   * @see #getLanguage
   * @see #setLanguage
   */
  public static final Property language = newProperty(0, new BUserPrototypeProperty(BString.make(""), BFacets.make(BFacets.FIELD_WIDTH, BInteger.make(6))), null);

  /**
   * Get the {@code language} property.
   * @see #language
   */
  public BUserPrototypeProperty getLanguage() { return (BUserPrototypeProperty)get(language); }

  /**
   * Set the {@code language} property.
   * @see #language
   */
  public void setLanguage(BUserPrototypeProperty v) { set(language, v, null); }

  //endregion Property "language"

  //region Property "email"

  /**
   * Slot for the {@code email} property.
   * @see #getEmail
   * @see #setEmail
   */
  public static final Property email = newProperty(0, new BUserPrototypeProperty(BString.make("")), null);

  /**
   * Get the {@code email} property.
   * @see #email
   */
  public BUserPrototypeProperty getEmail() { return (BUserPrototypeProperty)get(email); }

  /**
   * Set the {@code email} property.
   * @see #email
   */
  public void setEmail(BUserPrototypeProperty v) { set(email, v, null); }

  //endregion Property "email"

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, new BUserPrototypeProperty(BFacets.NULL, BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:UserFacetsFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:UserFacetsEditor"),"enablePopOut", BBoolean.FALSE)), null);

  /**
   * Get the {@code facets} property.
   * @see #facets
   */
  public BUserPrototypeProperty getFacets() { return (BUserPrototypeProperty)get(facets); }

  /**
   * Set the {@code facets} property.
   * @see #facets
   */
  public void setFacets(BUserPrototypeProperty v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "navFile"

  /**
   * Slot for the {@code navFile} property.
   * @see #getNavFile
   * @see #setNavFile
   */
  public static final Property navFile = newProperty(0, new BUserPrototypeProperty(BOrd.NULL), null);

  /**
   * Get the {@code navFile} property.
   * @see #navFile
   */
  public BUserPrototypeProperty getNavFile() { return (BUserPrototypeProperty)get(navFile); }

  /**
   * Set the {@code navFile} property.
   * @see #navFile
   */
  public void setNavFile(BUserPrototypeProperty v) { set(navFile, v, null); }

  //endregion Property "navFile"

  //region Property "cellPhoneNumber"

  /**
   * Slot for the {@code cellPhoneNumber} property.
   * @see #getCellPhoneNumber
   * @see #setCellPhoneNumber
   */
  public static final Property cellPhoneNumber = newProperty(0, new BUserPrototypeProperty(BString.make("")), null);

  /**
   * Get the {@code cellPhoneNumber} property.
   * @see #cellPhoneNumber
   */
  public BUserPrototypeProperty getCellPhoneNumber() { return (BUserPrototypeProperty)get(cellPhoneNumber); }

  /**
   * Set the {@code cellPhoneNumber} property.
   * @see #cellPhoneNumber
   */
  public void setCellPhoneNumber(BUserPrototypeProperty v) { set(cellPhoneNumber, v, null); }

  //endregion Property "cellPhoneNumber"

  //region Property "roles"

  /**
   * Slot for the {@code roles} property.
   * @see #getRoles
   * @see #setRoles
   */
  public static final Property roles = newProperty(0, new BUserPrototypeProperty(BString.make(""), BFacets.make(BFacets.FIELD_EDITOR, BString.make("wbutil:RoleFE"), BFacets.UX_FIELD_EDITOR, BString.make("webEditors:RolesEditor"), BFacets.SECURITY, BBoolean.TRUE)), null);

  /**
   * Get the {@code roles} property.
   * @see #roles
   */
  public BUserPrototypeProperty getRoles() { return (BUserPrototypeProperty)get(roles); }

  /**
   * Set the {@code roles} property.
   * @see #roles
   */
  public void setRoles(BUserPrototypeProperty v) { set(roles, v, null); }

  //endregion Property "roles"

  //region Property "allowConcurrentSessions"

  /**
   * Slot for the {@code allowConcurrentSessions} property.
   * @see #getAllowConcurrentSessions
   * @see #setAllowConcurrentSessions
   */
  public static final Property allowConcurrentSessions = newProperty(0, new BUserPrototypeProperty(BBoolean.TRUE, BFacets.make(BFacets.SECURITY, BBoolean.TRUE)), null);

  /**
   * Get the {@code allowConcurrentSessions} property.
   * @see #allowConcurrentSessions
   */
  public BUserPrototypeProperty getAllowConcurrentSessions() { return (BUserPrototypeProperty)get(allowConcurrentSessions); }

  /**
   * Set the {@code allowConcurrentSessions} property.
   * @see #allowConcurrentSessions
   */
  public void setAllowConcurrentSessions(BUserPrototypeProperty v) { set(allowConcurrentSessions, v, null); }

  //endregion Property "allowConcurrentSessions"

  //region Property "autoLogoffSettings"

  /**
   * Slot for the {@code autoLogoffSettings} property.
   * @see #getAutoLogoffSettings
   * @see #setAutoLogoffSettings
   */
  public static final Property autoLogoffSettings = newProperty(0, new BUserPrototypeProperty(new BAutoLogoffSettings(), BFacets.make(BFacets.SECURITY, BBoolean.TRUE)), null);

  /**
   * Get the {@code autoLogoffSettings} property.
   * @see #autoLogoffSettings
   */
  public BUserPrototypeProperty getAutoLogoffSettings() { return (BUserPrototypeProperty)get(autoLogoffSettings); }

  /**
   * Set the {@code autoLogoffSettings} property.
   * @see #autoLogoffSettings
   */
  public void setAutoLogoffSettings(BUserPrototypeProperty v) { set(autoLogoffSettings, v, null); }

  //endregion Property "autoLogoffSettings"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserPrototype.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Override to add {@link BIMixIn}s as a {@link BUserPrototypeProperty}
   */
  @Override
  public void added(Property property, Context context)
  {
    BValue value = get(property);
    if (value instanceof BIMixIn)
    {
      String name = property.getName();
      remove(property);
      BUserPrototypeProperty prop = new BUserPrototypeProperty(value, getSlotFacets(property));
      add(name, prop);
    }
  }

  /**
   * Override to get {@link BIMixIn} from {@link BUserPrototypeProperty}
   */
  @Override
  public BValue getMixIn(Type type)
  {
    BValue mixin = get(type.toString().replace(':', '_'));
    if (mixin instanceof BUserPrototypeProperty)
    {
      return ((BUserPrototypeProperty)mixin).getValue();
    }
    return mixin;
  }

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("user.png");

  /**
   * Convenience method for {@code updateUserFromPrototype(user, values, false)}
   *
   * @param user User to update from prototype
   * @param values Map of name-value pairs to take precedence over prototype properties
   */
  public void updateUserFromPrototype(BUser user, Map<String, BValue> values)
  {
    updateUserFromPrototype(user, values, false);
  }

  /**
   * Update a {@code BUser} with values from this prototype. If a property exists in
   * the {@code values} map, the user's property will be set from it, if not, it will
   * be set from this {@code BUserPrototype}'s property. Properties that are overridable
   * and overridden on the user (Flags.USER_DEFINED_1 set) will remain set to the current value.
   * Properties that are not overridable will be set to readonly.
   *
   * @param user User to update from prototype
   * @param values Map of name-value pairs to take precedence over prototype properties
   * @param clearOverrides Clear any overridden properties if true
   */
  public void updateUserFromPrototype(BUser user, Map<String, BValue> values, boolean clearOverrides)
  {
    SlotCursor<Property> c = user.getProperties();
    while (c.next())
    {
      boolean overridable = true;
      String name = c.property().getName();
      BValue value = values.get(name);

      BValue prop = get(name);
      if (prop instanceof BUserPrototypeProperty)
      {
        if (value == null)
        {
          value = ((BUserPrototypeProperty)prop).getValue();
        }
        overridable = ((BUserPrototypeProperty)prop).getOverridable();
      }
      if (value != null)
      {
        if (!overridable)
        {
          user.setFlags(c.property(), user.getFlags(c.property()) | Flags.READONLY);
        }
        else if ((Flags.READONLY & c.property().getDefaultFlags()) == 0)
        {
          user.setFlags(c.property(), user.getFlags(c.property()) &~ Flags.READONLY);
        }
        if (!overridable || !Flags.isUserDefined1(user, c.property()) || clearOverrides)
        {
          user.set(name, value.newCopy());
          user.setFlags(c.property(), user.getFlags(c.property()) &~ Flags.USER_DEFINED_1);
        }
      }
    }
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
    if (isRunning() && context != null && context.getUser() != null)
    {
      checkRoleChange(((BUserPrototypeProperty)validatable.getProposedValue(roles)).getValue(), context);
    }
  }

  /**
   * Validate that this role change is allowed. Throws a RuntimeException if the change is not allowed.
   *
   * @param newValue the value of the new roles property
   * @param context the context that the change is executing with
   * @since Niagara 4.4U1
   */
  final void checkRoleChange(BValue newValue, Context context)
  {
    String existingRoles = "";
    BValue existingValue = getRoles().getValue();
    if (existingValue instanceof BString)
    {
      existingRoles = ((BString)existingValue).getString();
    }
    String proposedRoles = "";
    if (newValue instanceof BString)
    {
      proposedRoles = ((BString)newValue).getString();
    }
    BUser.checkRoleChange(context.getUser(), existingRoles, proposedRoles);
  }


}
