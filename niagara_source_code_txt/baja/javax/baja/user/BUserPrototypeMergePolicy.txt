/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.user;

import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BString;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * This class describes a policy for merging user prototypes ({@link BUser} or {@link BUserPrototype})
 * when a remote user specifies multiple possible prototypes.
 *
 * @author Melanie Coggan on 2021-12-02
 * @since Niagara 4.12
 */
@NiagaraType
/*
 If true, user prototypes will be merged according to the policy.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "false"
)
/*
 Determines if and how the roles will be merged.
 */
@NiagaraProperty(
  name = "rolesMergeMode",
  type = "BRolesMergeMode",
  defaultValue = "BRolesMergeMode.union"
)
/*
 Determines if and how the expirations will be merged.
 */
@NiagaraProperty(
  name = "expirationMergeMode",
  type = "BExpirationMergeMode",
  defaultValue = "BExpirationMergeMode.preferEarliest"
)
/*
 Determines if and how the allowConcurrentSetting properties will be merged.
 */
@NiagaraProperty(
  name = "allowConcurrentSessionsMergeMode",
  type = "BAllowConcurrentSessionsMergeMode",
  defaultValue = "BAllowConcurrentSessionsMergeMode.preferFalse"
)
/*
 Determines if and how the autoLogoffSetting properties will be merged.
 */
@NiagaraProperty(
  name = "autoLogoffSettingsMergeMode",
  type = "BAutoLogoffSettingsMergeMode",
  defaultValue = "BAutoLogoffSettingsMergeMode.preferShortest"
)
public final class BUserPrototypeMergePolicy
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BUserPrototypeMergePolicy(4174821816)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * If true, user prototypes will be merged according to the policy.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, false, null);

  /**
   * Get the {@code enabled} property.
   * If true, user prototypes will be merged according to the policy.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * If true, user prototypes will be merged according to the policy.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "rolesMergeMode"

  /**
   * Slot for the {@code rolesMergeMode} property.
   * Determines if and how the roles will be merged.
   * @see #getRolesMergeMode
   * @see #setRolesMergeMode
   */
  public static final Property rolesMergeMode = newProperty(0, BRolesMergeMode.union, null);

  /**
   * Get the {@code rolesMergeMode} property.
   * Determines if and how the roles will be merged.
   * @see #rolesMergeMode
   */
  public BRolesMergeMode getRolesMergeMode() { return (BRolesMergeMode)get(rolesMergeMode); }

  /**
   * Set the {@code rolesMergeMode} property.
   * Determines if and how the roles will be merged.
   * @see #rolesMergeMode
   */
  public void setRolesMergeMode(BRolesMergeMode v) { set(rolesMergeMode, v, null); }

  //endregion Property "rolesMergeMode"

  //region Property "expirationMergeMode"

  /**
   * Slot for the {@code expirationMergeMode} property.
   * Determines if and how the expirations will be merged.
   * @see #getExpirationMergeMode
   * @see #setExpirationMergeMode
   */
  public static final Property expirationMergeMode = newProperty(0, BExpirationMergeMode.preferEarliest, null);

  /**
   * Get the {@code expirationMergeMode} property.
   * Determines if and how the expirations will be merged.
   * @see #expirationMergeMode
   */
  public BExpirationMergeMode getExpirationMergeMode() { return (BExpirationMergeMode)get(expirationMergeMode); }

  /**
   * Set the {@code expirationMergeMode} property.
   * Determines if and how the expirations will be merged.
   * @see #expirationMergeMode
   */
  public void setExpirationMergeMode(BExpirationMergeMode v) { set(expirationMergeMode, v, null); }

  //endregion Property "expirationMergeMode"

  //region Property "allowConcurrentSessionsMergeMode"

  /**
   * Slot for the {@code allowConcurrentSessionsMergeMode} property.
   * Determines if and how the allowConcurrentSetting properties will be merged.
   * @see #getAllowConcurrentSessionsMergeMode
   * @see #setAllowConcurrentSessionsMergeMode
   */
  public static final Property allowConcurrentSessionsMergeMode = newProperty(0, BAllowConcurrentSessionsMergeMode.preferFalse, null);

  /**
   * Get the {@code allowConcurrentSessionsMergeMode} property.
   * Determines if and how the allowConcurrentSetting properties will be merged.
   * @see #allowConcurrentSessionsMergeMode
   */
  public BAllowConcurrentSessionsMergeMode getAllowConcurrentSessionsMergeMode() { return (BAllowConcurrentSessionsMergeMode)get(allowConcurrentSessionsMergeMode); }

  /**
   * Set the {@code allowConcurrentSessionsMergeMode} property.
   * Determines if and how the allowConcurrentSetting properties will be merged.
   * @see #allowConcurrentSessionsMergeMode
   */
  public void setAllowConcurrentSessionsMergeMode(BAllowConcurrentSessionsMergeMode v) { set(allowConcurrentSessionsMergeMode, v, null); }

  //endregion Property "allowConcurrentSessionsMergeMode"

  //region Property "autoLogoffSettingsMergeMode"

  /**
   * Slot for the {@code autoLogoffSettingsMergeMode} property.
   * Determines if and how the autoLogoffSetting properties will be merged.
   * @see #getAutoLogoffSettingsMergeMode
   * @see #setAutoLogoffSettingsMergeMode
   */
  public static final Property autoLogoffSettingsMergeMode = newProperty(0, BAutoLogoffSettingsMergeMode.preferShortest, null);

  /**
   * Get the {@code autoLogoffSettingsMergeMode} property.
   * Determines if and how the autoLogoffSetting properties will be merged.
   * @see #autoLogoffSettingsMergeMode
   */
  public BAutoLogoffSettingsMergeMode getAutoLogoffSettingsMergeMode() { return (BAutoLogoffSettingsMergeMode)get(autoLogoffSettingsMergeMode); }

  /**
   * Set the {@code autoLogoffSettingsMergeMode} property.
   * Determines if and how the autoLogoffSetting properties will be merged.
   * @see #autoLogoffSettingsMergeMode
   */
  public void setAutoLogoffSettingsMergeMode(BAutoLogoffSettingsMergeMode v) { set(autoLogoffSettingsMergeMode, v, null); }

  //endregion Property "autoLogoffSettingsMergeMode"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUserPrototypeMergePolicy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

//region Merging

  /**
   * Merges the supplied prototypes according to the policy.
   *
   * @param mainPrototype A {@link BUser} or {@link BUserPrototypeMergePolicy}. This prototype will be used as a base for the merging.
   *                      All non-merged values will come from this prototype.
   * @param prototypes A (potentially empty) list of prototypes to be merged with the main prototype. Only enabled, mergeable properties will be merged,
   *                   according to the merge policy.
   * @return A {@link BUserPrototype} corresponding to the merge of all supplied prototypes.
   */
  public BUserPrototype mergePrototypes(BComponent mainPrototype, BComponent... prototypes)
  {
    if (!isRunning())
    {
      LOG.warning("Merge attempted on non-running UserPrototypeMergePolicy");
      throw new NotRunningException("Merge policy cannot merge prototypes outside of a running station");
    }

    if (!getEnabled())
    {
      LOG.warning("Merge attempted on disabled UserPrototypeMergePolicy");
      throw new IllegalStateException("Merge policy is disabled. Cannot merge user prototypes");
    }

    // Set up the main prototype
    BUserPrototype mergedPrototype = getUserPrototype(mainPrototype);

    // Initialized the merged settings
    BAbsTime mergedExpiration = getPrototypeExpiration(mergedPrototype);
    boolean mergedAllowConcurrentSessions = getPrototypeAllowedConcurrentSessions(mergedPrototype);
    BAutoLogoffSettings mergedAutoLogoffSettings = getPrototypeAutoLogoffSettings(mergedPrototype);
    Set<String> mergedRoles = BUser.splitRoles(((BString) mergedPrototype.getRoles().getValue()).getString());

    // Go through each supplied prototype and update the merged values
    for (BComponent prototype : prototypes)
    {
      // Check if the prototype is of the correct type
      if (!(prototype instanceof BUser || prototype instanceof BUserPrototype))
      {
        if (LOG.isLoggable(Level.FINE))
        {
          LOG.fine("Invalid type " + prototype.getType().getTypeName() + "in prototypes list. Ignoring.");
        }
        continue;
      }

      mergedExpiration = getExpirationMergeMode().getMergedValue(mergedExpiration, getPrototypeExpiration(prototype));
      mergedAllowConcurrentSessions = getAllowConcurrentSessionsMergeMode().getMergedValue(mergedAllowConcurrentSessions, getPrototypeAllowedConcurrentSessions(prototype));
      mergedAutoLogoffSettings = getAutoLogoffSettingsMergeMode().getMergedValue(mergedAutoLogoffSettings, getPrototypeAutoLogoffSettings(prototype));
      mergedRoles = getRolesMergeMode().getMergedValue(mergedRoles, getPrototypeRoles(prototype));
    }

    // Update the prototype with the merged values
    mergedPrototype.setExpiration(new BUserPrototypeProperty(mergedExpiration));
    mergedPrototype.setAllowConcurrentSessions(new BUserPrototypeProperty(BBoolean.make(mergedAllowConcurrentSessions)));
    mergedPrototype.setAutoLogoffSettings(new BUserPrototypeProperty(mergedAutoLogoffSettings.newCopy()));
    mergedPrototype.setRoles(new BUserPrototypeProperty(BString.make(String.join(",", mergedRoles))));

    return mergedPrototype;
  }

  /**
   * Generates a name for the supplied list of prototypes
   * @param prototypes A List of BComponents that should be either BUser or BUserPrototypes
   * @return A new name for the merged prototype: merged_proto1_proto2_proto3
   */
  public static String getMergedPrototypeName(List<BComponent> prototypes)
  {
    StringJoiner mergedPrototypeName = new StringJoiner("_", "merged_", "");
    prototypes.forEach(proto -> mergedPrototypeName.add(proto.getName()));
    return mergedPrototypeName.toString();
  }
//endregion Merging

//region Helpers
  private static BUserPrototype getUserPrototype(BComponent prototype)
  {
    if (prototype instanceof BUserPrototype)
    {
      return (BUserPrototype) prototype.newCopy();
    }

    if (!(prototype instanceof BUser))
    {
      throw new IllegalArgumentException("Invalid prototype type. Must be a BUser or BUserPrototype");
    }

    BUser templatePrototype = (BUser) prototype;
    BUserPrototype userPrototype = new BUserPrototype();
    userPrototype.setFullName(new BUserPrototypeProperty(BString.make(templatePrototype.getFullName())));
    userPrototype.setEnabled(new BUserPrototypeProperty(BBoolean.make(templatePrototype.getEnabled())));
    userPrototype.setExpiration(new BUserPrototypeProperty(templatePrototype.getExpiration()));
    userPrototype.setLanguage(new BUserPrototypeProperty(BString.make(templatePrototype.getLanguage())));
    userPrototype.setEmail(new BUserPrototypeProperty(BString.make(templatePrototype.getEmail())));
    userPrototype.setFacets(new BUserPrototypeProperty(templatePrototype.getFacets()));
    userPrototype.setNavFile(new BUserPrototypeProperty(templatePrototype.getNavFile()));
    userPrototype.setCellPhoneNumber(new BUserPrototypeProperty(BString.make(templatePrototype.getCellPhoneNumber())));
    userPrototype.setRoles(new BUserPrototypeProperty(BString.make(templatePrototype.getRoles())));
    userPrototype.setAllowConcurrentSessions(new BUserPrototypeProperty(BBoolean.make(templatePrototype.getAllowConcurrentSessions())));
    userPrototype.setAutoLogoffSettings((new BUserPrototypeProperty(templatePrototype.getAutoLogoffSettings().newCopy())));

    return userPrototype;
  }

  private static BAbsTime getPrototypeExpiration(BComponent prototype)
  {
    if (prototype instanceof BUser)
    {
      return ((BUser) prototype).getExpiration();
    }
    else
    {
      return (BAbsTime) ((BUserPrototype) prototype).getExpiration().getValue();
    }
  }

  private static boolean getPrototypeAllowedConcurrentSessions(BComponent prototype)
  {
    if (prototype instanceof BUser)
    {
      return ((BUser) prototype).getAllowConcurrentSessions();
    }
    else
    {
      return ((BBoolean) ((BUserPrototype) prototype).getAllowConcurrentSessions().getValue()).getBoolean();
    }
  }

  private static BAutoLogoffSettings getPrototypeAutoLogoffSettings(BComponent prototype)
  {
    if (prototype instanceof BUser)
    {
      return ((BUser) prototype).getAutoLogoffSettings();
    }
    else
    {
      return (BAutoLogoffSettings) ((BUserPrototype) prototype).getAutoLogoffSettings().getValue();
    }
  }

  private static Set<String> getPrototypeRoles(BComponent prototype)
  {
    String roles;
    if (prototype instanceof BUser)
    {
      roles =  ((BUser) prototype).getRoles();
    }
    else
    {
      roles = ((BString) ((BUserPrototype) prototype).getRoles().getValue()).getString();
    }

    return BUser.splitRoles(roles);
  }
//endregion Helpers


//region Fields
  private static final Logger LOG = Logger.getLogger("baja.user");
//endregion Fields
}
