/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Logger;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.NotRunningException;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.tag.RelationInfo;
import javax.baja.tag.TagDictionary;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;
import javax.baja.util.Lexicon;

import com.tridium.sys.schema.Fw;
import com.tridium.tagdictionary.util.ImportUtil;

/**
 * BTagDictionary is a {@code BComponent} implementation of {@code TagDictionary}.
 *
 * @author John Sublett
 * @creation 2/13/14
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The status of the dictionary.
 */
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 The fault cause of the dictionary, if it is in fault
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT | Flags.DEFAULT_ON_CLONE
)
/*
 The namespace for all tags in the dictionary.
 */
@NiagaraProperty(
  name = "namespace",
  type = "String",
  defaultValue = ""
)
/*
 This value should be updated whenever tags, tag groups, or tag rules are added, removed, or
 changed. The format of this field is not defined and its value is not automatically changed.
 @since Niagara 4.12
 */
@NiagaraProperty(
  name = "version",
  type = "String",
  defaultValue = "Undefined"
)
/*
 The list of default tags to exclude when converting bindings to NEQL.
 Items in the value string are pattern filters separated by ',', ';', '\n', '\t', or ' '
 Items that follow the pattern must be fully qualified (include a namespace).
 They will not inherit the namespace of the dictionary or the default namespace of the tag
 dictionary service.
 Example tag patterns:
 <ul>
 <li>{@code "hs:*"}: exclude all tags in the dictionary with the "hs" namespace (Haystack).</li>
 <li>{@code "*"}: exclude all tags in all dictionaries (not very helpful).</li>
 <li>{@code "*temp*"}: exclude any tags in any dictionary that contains "temp".</li>
 <li>{@code "*:device"}: exclude any tag named "device" from any dictionary.</li>
 </ul>
 @since Niagara 4.9
 */
@NiagaraProperty(
  name = "neqlizeExcludedTags",
  type = "String",
  defaultValue = "",
  facets = @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE")
)
/*
 The list of default relations to exclude when converting bindings to NEQL.
 Items in the value string are pattern filters separated by ',', ';', '\n', '\t', or ' '
 Items that follow the pattern must be fully qualified (include a namespace).
 They will not inherit the namespace of the dictionary or the default namespace of the tag
 dictionary service.
 Relations also have a direction and that can be incorporated into the filter.
 Example relation patterns:
 <ul>
 <li>{@code "n:parent"}: exclude both inbound and outbound relations with that id.</li>
 <li>{@code "n:parent*"}: exclude any relations (inbound or outbound) that starts with "n:parent", such as "n:parentDevice".</li>
 <li>{@code "n:parent->"}: exclude outbound relations with the id "n:parent".</li>
 <li>{@code "n:parent<-"}: exclude inbound relations with the id "n:parent".</li>
 <li>{@code "n:parent*->"}: exclude outbound relations with an id that starts with "n:parent".</li>
 <li>{@code "n:parent->*"}: is not a valid filter; a {@code "->"} or {@code "<-"} can only appear at the end.</li>
 </ul>
 @since Niagara 4.9
 */
@NiagaraProperty(
  name = "neqlizeExcludedRelations",
  type = "String",
  defaultValue = "",
  facets = @Facet(name = "BFacets.MULTI_LINE", value = "BBoolean.TRUE")
)
/*
 The enabled state of the dictionary.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Defines this dictionary as a "frozen" dictionary. If frozen, the dictionary can only be
 imported; otherwise, manual changes will be allowed.
 */
@NiagaraProperty(
  name = "frozen",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.NON_CRITICAL
)
/*
 The definitions of all tags in the dictionary.
 */
@NiagaraProperty(
  name = "tagDefinitions",
  type = "BTagInfoList",
  defaultValue = "new BTagInfoList()"
)
/*
 The definitions of all tag groups in the dictionary.
 */
@NiagaraProperty(
  name = "tagGroupDefinitions",
  type = "BTagGroupInfoList",
  defaultValue = "new BTagGroupInfoList()"
)
/*
 The definitions of all relations in the dictionary.
 */
@NiagaraProperty(
  name = "relationDefinitions",
  type = "BRelationInfoList",
  defaultValue = "new BRelationInfoList()"
)
/*
 The default {@link #importDictionary} action argument.
 */
@NiagaraProperty(
  name = "importDictionaryOrd",
  type = "BOrd",
  defaultValue = "BOrd.make(\"local:|file:~\")",
  flags = Flags.HIDDEN
)
/*
 Reimport the dictionary from the standard configuration file.
 */
@NiagaraAction(
  name = "importDictionary",
  parameterType = "BOrd",
  defaultValue = "BOrd.make(\"local:|file:~\")",
  flags = Flags.ASYNC | Flags.HIDDEN
)
public class BTagDictionary
  extends BComponent
  implements TagDictionary, BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagDictionary(2247596631)1.0$ @*/
/* Generated Wed Nov 16 15:24:54 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * The status of the dictionary.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * The status of the dictionary.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * The status of the dictionary.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * The fault cause of the dictionary, if it is in fault
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT | Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code faultCause} property.
   * The fault cause of the dictionary, if it is in fault
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * The fault cause of the dictionary, if it is in fault
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "namespace"

  /**
   * Slot for the {@code namespace} property.
   * The namespace for all tags in the dictionary.
   * @see #getNamespace
   * @see #setNamespace
   */
  public static final Property namespace = newProperty(0, "", null);

  /**
   * Get the {@code namespace} property.
   * The namespace for all tags in the dictionary.
   * @see #namespace
   */
  public String getNamespace() { return getString(namespace); }

  /**
   * Set the {@code namespace} property.
   * The namespace for all tags in the dictionary.
   * @see #namespace
   */
  public void setNamespace(String v) { setString(namespace, v, null); }

  //endregion Property "namespace"

  //region Property "version"

  /**
   * Slot for the {@code version} property.
   * This value should be updated whenever tags, tag groups, or tag rules are added, removed, or
   * changed. The format of this field is not defined and its value is not automatically changed.
   * @since Niagara 4.12
   * @see #getVersion
   * @see #setVersion
   */
  public static final Property version = newProperty(0, "Undefined", null);

  /**
   * Get the {@code version} property.
   * This value should be updated whenever tags, tag groups, or tag rules are added, removed, or
   * changed. The format of this field is not defined and its value is not automatically changed.
   * @since Niagara 4.12
   * @see #version
   */
  public String getVersion() { return getString(version); }

  /**
   * Set the {@code version} property.
   * This value should be updated whenever tags, tag groups, or tag rules are added, removed, or
   * changed. The format of this field is not defined and its value is not automatically changed.
   * @since Niagara 4.12
   * @see #version
   */
  public void setVersion(String v) { setString(version, v, null); }

  //endregion Property "version"

  //region Property "neqlizeExcludedTags"

  /**
   * Slot for the {@code neqlizeExcludedTags} property.
   * The list of default tags to exclude when converting bindings to NEQL.
   * Items in the value string are pattern filters separated by ',', ';', '\n', '\t', or ' '
   * Items that follow the pattern must be fully qualified (include a namespace).
   * They will not inherit the namespace of the dictionary or the default namespace of the tag
   * dictionary service.
   * Example tag patterns:
   * <ul>
   * <li>{@code "hs:*"}: exclude all tags in the dictionary with the "hs" namespace (Haystack).</li>
   * <li>{@code "*"}: exclude all tags in all dictionaries (not very helpful).</li>
   * <li>{@code "*temp*"}: exclude any tags in any dictionary that contains "temp".</li>
   * <li>{@code "*:device"}: exclude any tag named "device" from any dictionary.</li>
   * </ul>
   * @since Niagara 4.9
   * @see #getNeqlizeExcludedTags
   * @see #setNeqlizeExcludedTags
   */
  public static final Property neqlizeExcludedTags = newProperty(0, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code neqlizeExcludedTags} property.
   * The list of default tags to exclude when converting bindings to NEQL.
   * Items in the value string are pattern filters separated by ',', ';', '\n', '\t', or ' '
   * Items that follow the pattern must be fully qualified (include a namespace).
   * They will not inherit the namespace of the dictionary or the default namespace of the tag
   * dictionary service.
   * Example tag patterns:
   * <ul>
   * <li>{@code "hs:*"}: exclude all tags in the dictionary with the "hs" namespace (Haystack).</li>
   * <li>{@code "*"}: exclude all tags in all dictionaries (not very helpful).</li>
   * <li>{@code "*temp*"}: exclude any tags in any dictionary that contains "temp".</li>
   * <li>{@code "*:device"}: exclude any tag named "device" from any dictionary.</li>
   * </ul>
   * @since Niagara 4.9
   * @see #neqlizeExcludedTags
   */
  public String getNeqlizeExcludedTags() { return getString(neqlizeExcludedTags); }

  /**
   * Set the {@code neqlizeExcludedTags} property.
   * The list of default tags to exclude when converting bindings to NEQL.
   * Items in the value string are pattern filters separated by ',', ';', '\n', '\t', or ' '
   * Items that follow the pattern must be fully qualified (include a namespace).
   * They will not inherit the namespace of the dictionary or the default namespace of the tag
   * dictionary service.
   * Example tag patterns:
   * <ul>
   * <li>{@code "hs:*"}: exclude all tags in the dictionary with the "hs" namespace (Haystack).</li>
   * <li>{@code "*"}: exclude all tags in all dictionaries (not very helpful).</li>
   * <li>{@code "*temp*"}: exclude any tags in any dictionary that contains "temp".</li>
   * <li>{@code "*:device"}: exclude any tag named "device" from any dictionary.</li>
   * </ul>
   * @since Niagara 4.9
   * @see #neqlizeExcludedTags
   */
  public void setNeqlizeExcludedTags(String v) { setString(neqlizeExcludedTags, v, null); }

  //endregion Property "neqlizeExcludedTags"

  //region Property "neqlizeExcludedRelations"

  /**
   * Slot for the {@code neqlizeExcludedRelations} property.
   * The list of default relations to exclude when converting bindings to NEQL.
   * Items in the value string are pattern filters separated by ',', ';', '\n', '\t', or ' '
   * Items that follow the pattern must be fully qualified (include a namespace).
   * They will not inherit the namespace of the dictionary or the default namespace of the tag
   * dictionary service.
   * Relations also have a direction and that can be incorporated into the filter.
   * Example relation patterns:
   * <ul>
   * <li>{@code "n:parent"}: exclude both inbound and outbound relations with that id.</li>
   * <li>{@code "n:parent*"}: exclude any relations (inbound or outbound) that starts with "n:parent", such as "n:parentDevice".</li>
   * <li>{@code "n:parent->"}: exclude outbound relations with the id "n:parent".</li>
   * <li>{@code "n:parent<-"}: exclude inbound relations with the id "n:parent".</li>
   * <li>{@code "n:parent*->"}: exclude outbound relations with an id that starts with "n:parent".</li>
   * <li>{@code "n:parent->*"}: is not a valid filter; a {@code "->"} or {@code "<-"} can only appear at the end.</li>
   * </ul>
   * @since Niagara 4.9
   * @see #getNeqlizeExcludedRelations
   * @see #setNeqlizeExcludedRelations
   */
  public static final Property neqlizeExcludedRelations = newProperty(0, "", BFacets.make(BFacets.MULTI_LINE, BBoolean.TRUE));

  /**
   * Get the {@code neqlizeExcludedRelations} property.
   * The list of default relations to exclude when converting bindings to NEQL.
   * Items in the value string are pattern filters separated by ',', ';', '\n', '\t', or ' '
   * Items that follow the pattern must be fully qualified (include a namespace).
   * They will not inherit the namespace of the dictionary or the default namespace of the tag
   * dictionary service.
   * Relations also have a direction and that can be incorporated into the filter.
   * Example relation patterns:
   * <ul>
   * <li>{@code "n:parent"}: exclude both inbound and outbound relations with that id.</li>
   * <li>{@code "n:parent*"}: exclude any relations (inbound or outbound) that starts with "n:parent", such as "n:parentDevice".</li>
   * <li>{@code "n:parent->"}: exclude outbound relations with the id "n:parent".</li>
   * <li>{@code "n:parent<-"}: exclude inbound relations with the id "n:parent".</li>
   * <li>{@code "n:parent*->"}: exclude outbound relations with an id that starts with "n:parent".</li>
   * <li>{@code "n:parent->*"}: is not a valid filter; a {@code "->"} or {@code "<-"} can only appear at the end.</li>
   * </ul>
   * @since Niagara 4.9
   * @see #neqlizeExcludedRelations
   */
  public String getNeqlizeExcludedRelations() { return getString(neqlizeExcludedRelations); }

  /**
   * Set the {@code neqlizeExcludedRelations} property.
   * The list of default relations to exclude when converting bindings to NEQL.
   * Items in the value string are pattern filters separated by ',', ';', '\n', '\t', or ' '
   * Items that follow the pattern must be fully qualified (include a namespace).
   * They will not inherit the namespace of the dictionary or the default namespace of the tag
   * dictionary service.
   * Relations also have a direction and that can be incorporated into the filter.
   * Example relation patterns:
   * <ul>
   * <li>{@code "n:parent"}: exclude both inbound and outbound relations with that id.</li>
   * <li>{@code "n:parent*"}: exclude any relations (inbound or outbound) that starts with "n:parent", such as "n:parentDevice".</li>
   * <li>{@code "n:parent->"}: exclude outbound relations with the id "n:parent".</li>
   * <li>{@code "n:parent<-"}: exclude inbound relations with the id "n:parent".</li>
   * <li>{@code "n:parent*->"}: exclude outbound relations with an id that starts with "n:parent".</li>
   * <li>{@code "n:parent->*"}: is not a valid filter; a {@code "->"} or {@code "<-"} can only appear at the end.</li>
   * </ul>
   * @since Niagara 4.9
   * @see #neqlizeExcludedRelations
   */
  public void setNeqlizeExcludedRelations(String v) { setString(neqlizeExcludedRelations, v, null); }

  //endregion Property "neqlizeExcludedRelations"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * The enabled state of the dictionary.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * The enabled state of the dictionary.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * The enabled state of the dictionary.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "frozen"

  /**
   * Slot for the {@code frozen} property.
   * Defines this dictionary as a "frozen" dictionary. If frozen, the dictionary can only be
   * imported; otherwise, manual changes will be allowed.
   * @see #getFrozen
   * @see #setFrozen
   */
  public static final Property frozen = newProperty(Flags.NON_CRITICAL, false, null);

  /**
   * Get the {@code frozen} property.
   * Defines this dictionary as a "frozen" dictionary. If frozen, the dictionary can only be
   * imported; otherwise, manual changes will be allowed.
   * @see #frozen
   */
  public boolean getFrozen() { return getBoolean(frozen); }

  /**
   * Set the {@code frozen} property.
   * Defines this dictionary as a "frozen" dictionary. If frozen, the dictionary can only be
   * imported; otherwise, manual changes will be allowed.
   * @see #frozen
   */
  public void setFrozen(boolean v) { setBoolean(frozen, v, null); }

  //endregion Property "frozen"

  //region Property "tagDefinitions"

  /**
   * Slot for the {@code tagDefinitions} property.
   * The definitions of all tags in the dictionary.
   * @see #getTagDefinitions
   * @see #setTagDefinitions
   */
  public static final Property tagDefinitions = newProperty(0, new BTagInfoList(), null);

  /**
   * Get the {@code tagDefinitions} property.
   * The definitions of all tags in the dictionary.
   * @see #tagDefinitions
   */
  public BTagInfoList getTagDefinitions() { return (BTagInfoList)get(tagDefinitions); }

  /**
   * Set the {@code tagDefinitions} property.
   * The definitions of all tags in the dictionary.
   * @see #tagDefinitions
   */
  public void setTagDefinitions(BTagInfoList v) { set(tagDefinitions, v, null); }

  //endregion Property "tagDefinitions"

  //region Property "tagGroupDefinitions"

  /**
   * Slot for the {@code tagGroupDefinitions} property.
   * The definitions of all tag groups in the dictionary.
   * @see #getTagGroupDefinitions
   * @see #setTagGroupDefinitions
   */
  public static final Property tagGroupDefinitions = newProperty(0, new BTagGroupInfoList(), null);

  /**
   * Get the {@code tagGroupDefinitions} property.
   * The definitions of all tag groups in the dictionary.
   * @see #tagGroupDefinitions
   */
  public BTagGroupInfoList getTagGroupDefinitions() { return (BTagGroupInfoList)get(tagGroupDefinitions); }

  /**
   * Set the {@code tagGroupDefinitions} property.
   * The definitions of all tag groups in the dictionary.
   * @see #tagGroupDefinitions
   */
  public void setTagGroupDefinitions(BTagGroupInfoList v) { set(tagGroupDefinitions, v, null); }

  //endregion Property "tagGroupDefinitions"

  //region Property "relationDefinitions"

  /**
   * Slot for the {@code relationDefinitions} property.
   * The definitions of all relations in the dictionary.
   * @see #getRelationDefinitions
   * @see #setRelationDefinitions
   */
  public static final Property relationDefinitions = newProperty(0, new BRelationInfoList(), null);

  /**
   * Get the {@code relationDefinitions} property.
   * The definitions of all relations in the dictionary.
   * @see #relationDefinitions
   */
  public BRelationInfoList getRelationDefinitions() { return (BRelationInfoList)get(relationDefinitions); }

  /**
   * Set the {@code relationDefinitions} property.
   * The definitions of all relations in the dictionary.
   * @see #relationDefinitions
   */
  public void setRelationDefinitions(BRelationInfoList v) { set(relationDefinitions, v, null); }

  //endregion Property "relationDefinitions"

  //region Property "importDictionaryOrd"

  /**
   * Slot for the {@code importDictionaryOrd} property.
   * The default {@link #importDictionary} action argument.
   * @see #getImportDictionaryOrd
   * @see #setImportDictionaryOrd
   */
  public static final Property importDictionaryOrd = newProperty(Flags.HIDDEN, BOrd.make("local:|file:~"), null);

  /**
   * Get the {@code importDictionaryOrd} property.
   * The default {@link #importDictionary} action argument.
   * @see #importDictionaryOrd
   */
  public BOrd getImportDictionaryOrd() { return (BOrd)get(importDictionaryOrd); }

  /**
   * Set the {@code importDictionaryOrd} property.
   * The default {@link #importDictionary} action argument.
   * @see #importDictionaryOrd
   */
  public void setImportDictionaryOrd(BOrd v) { set(importDictionaryOrd, v, null); }

  //endregion Property "importDictionaryOrd"

  //region Action "importDictionary"

  /**
   * Slot for the {@code importDictionary} action.
   * Reimport the dictionary from the standard configuration file.
   * @see #importDictionary(BOrd parameter)
   */
  public static final Action importDictionary = newAction(Flags.ASYNC | Flags.HIDDEN, BOrd.make("local:|file:~"), null);

  /**
   * Invoke the {@code importDictionary} action.
   * Reimport the dictionary from the standard configuration file.
   * @see #importDictionary
   */
  public void importDictionary(BOrd parameter) { invoke(importDictionary, parameter, null); }

  //endregion Action "importDictionary"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagDictionary.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Default constructor
   */
  public BTagDictionary()
  {
  }

  /**
   * Constructor that initializes the {@link #namespace} property.
   *
   * @param namespace initial value of the namespace property
   */
  public BTagDictionary(String namespace)
  {
    setNamespace(namespace);
  }

  /**
   * Checks that the namespace is not empty and not the same as another
   * {@code BTagDictionary}.
   */
  @Override
  public void started()
    throws Exception
  {
    super.started();
    
    // Version property is set to read-only when the dictionary is frozen.
    setVersionReadonlyFlag();
    
    // NCCB-16609: Ensure status is read-only in the event that the station
    // bog has been edited to remove this field.
    if (!Flags.isReadonly(this, status))
    {
      setFlags(status, getFlags(status) | Flags.READONLY);
    }

    setFaultCause("");

    BTagDictionaryService service = (BTagDictionaryService)Sys.getService(BTagDictionaryService.TYPE);
    String dictionaryLimitFault = (String)service.fw(Fw.CHECK_LICENSE_LIMIT, "dictionary.limit", null, null, null);
    if (dictionaryLimitFault != null || service.isFault())
    {
      fatalFault = true;
      setFaultCause(dictionaryLimitFault != null ? dictionaryLimitFault : service.getFaultCause());
    }
    else if (getNamespace().isEmpty())
    {
      namespaceFault = true;
      if (!fatalFault)
      {
        setFaultCause("namespace cannot be empty");
      }
    }
    else
    {
      // check for a dictionary with the same namespace
      BTagDictionary duplicate = getDuplicateNamespace();
      if (duplicate != null)
      {
        namespaceFault = true;
        if (!fatalFault)
        {
          setFaultCause("namespace: " + getNamespace() + " must be unique.  See: " + duplicate.getSlotPath());
        }
      }
    }

    updateStatus();
  }

  /**
   * Updates the {@link #status} property.
   *
   * <p>If the {@link #namespace} property has changed, check that the value is not empty and not
   * the same as another {@code BTagDictionary}.</p>
   *
   * @param p property that has changed
   * @param cx execution context
   */
  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    
    if (!isRunning())
    {
      return;
    }

    if (p.equals(enabled))
    {
      updateStatus();
    }
    else if (p.equals(status))
    {
      // NCCB-16609: Make sure status always has the fault bit set when in fatalFault
      if (fatalFault && !getStatus().isFault())
      {
        updateStatus();
      }
    }
    else if (p.equals(namespace))
    {
      if (getNamespace().isEmpty())
      {
        namespaceFault = true;
        if (!fatalFault)
        {
          setFaultCause("namespace cannot be empty");
        }
      }
      else
      {
        // check for a dictionary with the same namespace; reset namespaceFault if not
        BTagDictionary duplicate = getDuplicateNamespace();
        namespaceFault = duplicate != null;
        if (namespaceFault)
        {
          if (!fatalFault)
          {
            setFaultCause("namespace: " + getNamespace() + " must be unique.  See: " + duplicate.getSlotPath());
          }
        }
        else
        {
          if (!fatalFault)
          {
            setFaultCause("");
          }
        }
      }

      updateStatus();
    }
    else if (p.equals(frozen))
    {
      setVersionReadonlyFlag();
    }
  }

  private void setVersionReadonlyFlag()
  {
    if (getFrozen())
    {
      setFlags(version, getFlags(version) | Flags.READONLY);
    }
    else
    {
      setFlags(version, getFlags(version) & ~Flags.READONLY);
    }
  }

  /**
   * Do not allow the read-only flag on the {@link #status} or
   * {@link #faultCause} slots to be cleared.
   *
   * @param slot slot on which flags are being set
   * @param flags {@link Flags} to be added to the slot
   * @param context execution context
   */
  @Override
  public void checkSetFlags(Slot slot, int flags, Context context)
  {
    super.checkSetFlags(slot, flags, context);

    if (context != Context.decoding)
    {
      //NCCB-16609: Disallow making status and faultCause non-read-only
      if (slot.equals(status) || slot.equals(faultCause))
      {
        if (!((flags & Flags.READONLY) == Flags.READONLY))
        {
          throw new LocalizableRuntimeException("baja", "cannotChangeReadOnly", new Object[] { slot.getName() });
        }
      }
    }
  }

  static Optional<TagDictionary> getParentDictionary(BComponent component)
  {
    BComplex parent = component.getParent();
    while (parent != null && !(parent instanceof TagDictionary))
    {
      parent = parent.getParent();
    }

    if (parent != null)
    {
      return Optional.of((TagDictionary)parent);
    }
    else
    {
      return Optional.empty();
    }
  }

  /**
   * For "frozen" dictionaries, get an instance of the gold standard for this
   * dictionary. The default implementation here just returns {@code null}.
   *
   * @return {@code null}
   */
  public BTagDictionary getStandardDictionary()
  {
    return null;
  }

  /**
   * Find an installed tag dictionary with same namespace as this dictionary.
   *
   * @return tag dictionary that has a duplicate namespace, if one exists;
   * otherwise, {@code null}
   */
  private BTagDictionary getDuplicateNamespace()
  {
    if (getStatus().isDisabled())
    {
      return null;
    }

    BTagDictionaryService service = (BTagDictionaryService)Sys.getService(BTagDictionaryService.TYPE);
    Optional<TagDictionary> optional = service.getTagDictionary(getNamespace());
    if (optional.isPresent())
    {
      BTagDictionary comp = (BTagDictionary)optional.get();
      if (!comp.getName().equals(getName()))
      {
        return comp;
      }
    }

    return null;
  }

  /**
   * Returns the lexicon property "tag.dictionary.shortName" applied to the
   * display name of this component (see {@link BComplex#getDisplayName(Context)})
   *
   * @param cx execution context
   * @return if the display name exists, the lexicon version that; otherwise see
   * {@link BObject#toString(Context)}
   */
  @Override
  public String toString(Context cx)
  {
    String name = getDisplayName(cx);
    if (name != null)
    {
      return lex.getText("tag.dictionary.shortName", name);
    }
    else
    {
      return super.toString(cx);
    }
  }

  /**
   * Checks that the parent to which this component is being added is of type
   * {@link TagDictionaryService}.
   *
   * @param parent component to which this object is being added
   * @return {@code true} if the parent is an instance of {@code TagDictionaryService};
   * otherwise, {@code false}
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof TagDictionaryService;
  }

  /**
   * Checks that the tag dictionary is operational (see
   * {@link #isOperational()}) before returning an {@code Iterator}
   * on the tags.
   *
   * @return an iterator for the tags
   */
  @Override
  public Iterator<TagInfo> getTags()
  {
    if (!isOperational())
    {
      throw new NotRunningException(String.format("TagDictionary: %s is not operational", getName()));
    }

    return getTagDefinitions().iterator();
  }

  /**
   * Checks that the tag dictionary is operational (see
   * {@link #isOperational()}) before returning an {@code Iterator}
   * on the tag groups.
   *
   * @return an iterator for the tag groups
   */
  @Override
  public Iterator<TagGroupInfo> getTagGroups()
  {
    if (!isOperational())
    {
      throw new NotRunningException(String.format("TagDictionary: %s is not operational", getName()));
    }

    return getTagGroupDefinitions().iterator();
  }

  /**
   * Checks that the tag dictionary is operational (see
   * {@link BTagDictionary#isOperational()}) before returning an {@code Iterator}
   * on the relations.
   *
   * @return an iterator of the relations
   */
  @Override
  public Iterator<RelationInfo> getRelations()
  {
    if (!isOperational())
    {
      throw new NotRunningException(String.format("TagDictionary: %s is not operational", getName()));
    }

    return getRelationDefinitions().iterator();
  }

  /**
   * Get the tags from this dictionary that are valid for the specified entity.
   *
   * @param entity entity to evaluate for valid tags
   * @return a collection of the valid tag groups from this
   *   dictionary for the entity.
   */
  @Override
  public Collection<TagInfo> getValidTags(Entity entity)
  {
    ArrayList<TagInfo> result = new ArrayList<>();

    Iterator<TagInfo> i = getTags();
    while (i.hasNext())
    {
      TagInfo tag = i.next();
      if (tag.isValidFor(entity))
      {
        result.add(tag);
      }
    }

    return result;
  }

  /**
   * Get the tag groups from this dictionary that are valid for the specified entity.
   *
   * @param entity entity to evaluate for valid tag groups
   * @return a collection of the valid tag groups from this
   *   dictionary for the entity.
   */
  @Override
  public Collection<TagGroupInfo> getValidTagGroups(Entity entity)
  {
    ArrayList<TagGroupInfo> result = new ArrayList<>();

    Iterator<TagGroupInfo> i = getTagGroups();
    while (i.hasNext())
    {
      TagGroupInfo group = i.next();
      if (group.isValidFor(entity))
      {
        result.add(group);
      }
    }

    return result;
  }


////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  /**
   * Returns whether the dictionary is disabled based on the
   * {@link #enabled} property.
   *
   * @return {@code true} if the dictionary is disabled; {@code false} otherwise
   */
  public final boolean isDisabled()
  {
    return getStatus().isDisabled();
  }

  /**
   * Returns whether the dictionary is faulted based on whether a fatal or
   * namespace fault has occurred.
   *
   * <p>See {@link #changed(Property, Context)} for more information
   * on a namespace fault. Refer to the {@link #faultCause}
   * property for the fault reason.</p>
   *
   * @return {@code true} if the dictionary is faulted; {@code false} otherwise
   */
  public final boolean isFault()
  {
    return getStatus().isFault();
  }

  /**
   * Test whether the dictionary is not disabled (see {@link #isDisabled()}) and not faulted (see
   * {@link #isFault()}).
   *
   * @return {@code true} if not disabled and not faulted; {@code false} otherwise.
   */
  public final boolean isOperational()
  {
    BStatus status = getStatus();
    return !fatalFault && !status.isDisabled() && !status.isFault();
  }

  /**
   * Recompute the status property based on the current enable and fault states.
   * See {@link #isDisabled()} and {@link #isFault()}.
   */
  public final void updateStatus()
  {
    int oldStatus = getStatus().getBits();
    int newStatus = 0;

    // disabled bit
    if (!getEnabled())
    {
      newStatus |= BStatus.DISABLED;
    }
    else
    {
      newStatus &= ~BStatus.DISABLED;
    }

    // fault bit
    if (fatalFault || namespaceFault)
    {
      newStatus |= BStatus.FAULT;
    }
    else
    {
      newStatus &= ~BStatus.FAULT;
    }

    // short circuit if nothing has changed since last time
    if (oldStatus == newStatus)
    {
      return;
    }

    setStatus(BStatus.make(newStatus));
  }

///////////////////////////////////////////////////////////
// Import
///////////////////////////////////////////////////////////

  /**
   * Always returns true for {@code BTagDictionary} but may be customized
   * by subclasses.
   *
   * @return {@code true} if the dictionary can be imported; {@code false} otherwise
   */
  public boolean isImportable()
  {
    return true;
  }

  /**
   * Import this tag dictionary from an external source.
   *
   * <p>Sets the {@link #importDictionaryOrd} property to the
   * specified file and calls {@link ImportUtil#ImportTagDictionary(BTagDictionary, BOrd)}.</p>
   *
   * @param file external source
   */
  @SuppressWarnings("unused")
  public void doImportDictionary(BOrd file)
  {
    System.out.println(file);
    setImportDictionaryOrd(file);
    ImportUtil.ImportTagDictionary(this, file);
  }

  /**
   * For the {@link #importDictionary} action slot, return the
   * value of the {@link #importDictionaryOrd} slot.
   *
   * @param action action for which a default value is required
   * @return a {@link BOrd} if for the {@code importDictionary} action slot;
   * otherwise, see {@link BComponent#getActionParameterDefault(Action)}.
   */
  @Override
  public BValue getActionParameterDefault(Action action)
  {
    if (action.equals(importDictionary))
    {
      return getImportDictionaryOrd();
    }

    return super.getActionParameterDefault(action);
  }

///////////////////////////////////////////////////////////
// Icon
///////////////////////////////////////////////////////////

  @Override public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("tag.png");

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  /**
   * Context used during import actions.
   */
  public static Context importContext = new BasicContext();
  /**
   * This logger is named "tagdictionary".
   */
  public static Logger logger = Logger.getLogger(BTagDictionaryService.LOGGER_NAME);
  /**
   * The lexicon for the tagdictionary module.
   */
  public static final Lexicon lex = Lexicon.make(TYPE.getModule().getModuleName());

  private boolean fatalFault;
  private boolean namespaceFault;
}
