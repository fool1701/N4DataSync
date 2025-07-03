/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.license.Feature;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BNavRoot;
import javax.baja.nav.NavEvent;
import javax.baja.nav.NavListener;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbstractService;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BComponentEventMask;
import javax.baja.sys.BIcon;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableException;
import javax.baja.sys.Property;
import javax.baja.sys.RelationKnob;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeSubscriber;
import javax.baja.tag.DataPolicy;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.RelationInfo;
import javax.baja.tag.Relations;
import javax.baja.tag.SmartTagDictionary;
import javax.baja.tag.Tag;
import javax.baja.tag.TagDictionary;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;
import javax.baja.util.BIRestrictedComponent;
import javax.baja.util.CoalesceQueue;
import javax.baja.util.ICoalesceable;
import javax.baja.util.Worker;
import javax.baja.virtual.BVirtualComponent;

import com.tridium.sys.license.LicenseUtil;
import com.tridium.sys.schema.Fw;
import com.tridium.sys.tag.ComponentRelations;
import com.tridium.tagdictionary.BNiagaraTagDictionary;
import com.tridium.tagdictionary.TagRuleIndex;
import com.tridium.tagdictionary.neqlize.BNeqlizeOptions;
import com.tridium.tagdictionary.util.EntityTagIndex;
import com.tridium.tagdictionary.util.TagDictionaryUtil;
import com.tridium.util.CompUtil;

/**
 * BTagDictionaryService is the {@code BAbstractService} implementation of
 * {@code TagDictionaryService}.
 *
 * @author John Sublett on 2/14/14
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The default namespace for this service.  This is typically the namespace of a dictionary that is
 installed in the service.  If a default dictionary is defined, global tags that appear in queries
 are treated as tags in the default namespace.
 */
@NiagaraProperty(
  name = "defaultNamespaceId",
  type = "String",
  defaultValue = ""
)
/*
 If the tag rule cache is enabled, the rules which imply an implied tag are cached by the tag's id.
 */
@NiagaraProperty(
  name = "tagRuleIndexEnabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 Semicolon separated list of tag ids to index when implied on an entity.
 @since Niagara 4.3
 */
@NiagaraProperty(
  name = "indexedTags",
  type = "String",
  defaultValue = ""
)
/*
 Options to use when converting PX bound ORDs from slots to NEQL queries.
 @since Niagara 4.9
 */
@NiagaraProperty(
  name = "neqlizeOptions",
  type = "BNeqlizeOptions",
  defaultValue = "BNeqlizeOptions.DEFAULT"
)
/*
 The Niagara Tag Dictionary required for many built in functions based on tags.
 */
@NiagaraProperty(
  name = "Niagara",
  type = "BNiagaraTagDictionary",
  defaultValue = "new BNiagaraTagDictionary()"
)
/*
 @since Niagara 4.3
 */
@NiagaraProperty(
  name = "schemaVersion",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraAction(
  name = "clearTagRuleIndex"
)
/*
 Clear the indexed values for all tags being indexed so tag rules and tag groups are re-evaluated
 the next time those tags are searched for.
 @since Niagara 4.3
 */
@NiagaraAction(
  name = "invalidateAllTagIndexes"
)
/*
 Clear the indexed values for a single tag being indexed so tag rules and tag groups are
 re-evaluated the next time that tag is searched for.
 @since Niagara 4.3
 */
@NiagaraAction(
  name = "invalidateSingleTagIndex",
  parameterType = "BString",
  defaultValue = "BString.DEFAULT"
)
/*
 @deprecated since Niagara 4.3 and will be removed in future release; no alternative available
 */
@NiagaraAction(
  name = "query",
  parameterType = "BString",
  defaultValue = "BString.make(\"\")",
  flags = Flags.ASYNC | Flags.HIDDEN,
  deprecated = true
)
/*
 Convert direct tags to tagGroups
 @since Niagara 4.3
 */
@NiagaraAction(
  name = "tagsToTagGroup",
  returnType = "BComponent",
  flags = Flags.HIDDEN
)
public class BTagDictionaryService
  extends BAbstractService
  implements TagDictionaryService, BIRestrictedComponent, NavListener
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BTagDictionaryService(3634887393)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "defaultNamespaceId"

  /**
   * Slot for the {@code defaultNamespaceId} property.
   * The default namespace for this service.  This is typically the namespace of a dictionary that is
   * installed in the service.  If a default dictionary is defined, global tags that appear in queries
   * are treated as tags in the default namespace.
   * @see #getDefaultNamespaceId
   * @see #setDefaultNamespaceId
   */
  public static final Property defaultNamespaceId = newProperty(0, "", null);

  /**
   * Get the {@code defaultNamespaceId} property.
   * The default namespace for this service.  This is typically the namespace of a dictionary that is
   * installed in the service.  If a default dictionary is defined, global tags that appear in queries
   * are treated as tags in the default namespace.
   * @see #defaultNamespaceId
   */
  public String getDefaultNamespaceId() { return getString(defaultNamespaceId); }

  /**
   * Set the {@code defaultNamespaceId} property.
   * The default namespace for this service.  This is typically the namespace of a dictionary that is
   * installed in the service.  If a default dictionary is defined, global tags that appear in queries
   * are treated as tags in the default namespace.
   * @see #defaultNamespaceId
   */
  public void setDefaultNamespaceId(String v) { setString(defaultNamespaceId, v, null); }

  //endregion Property "defaultNamespaceId"

  //region Property "tagRuleIndexEnabled"

  /**
   * Slot for the {@code tagRuleIndexEnabled} property.
   * If the tag rule cache is enabled, the rules which imply an implied tag are cached by the tag's id.
   * @see #getTagRuleIndexEnabled
   * @see #setTagRuleIndexEnabled
   */
  public static final Property tagRuleIndexEnabled = newProperty(0, true, null);

  /**
   * Get the {@code tagRuleIndexEnabled} property.
   * If the tag rule cache is enabled, the rules which imply an implied tag are cached by the tag's id.
   * @see #tagRuleIndexEnabled
   */
  public boolean getTagRuleIndexEnabled() { return getBoolean(tagRuleIndexEnabled); }

  /**
   * Set the {@code tagRuleIndexEnabled} property.
   * If the tag rule cache is enabled, the rules which imply an implied tag are cached by the tag's id.
   * @see #tagRuleIndexEnabled
   */
  public void setTagRuleIndexEnabled(boolean v) { setBoolean(tagRuleIndexEnabled, v, null); }

  //endregion Property "tagRuleIndexEnabled"

  //region Property "indexedTags"

  /**
   * Slot for the {@code indexedTags} property.
   * Semicolon separated list of tag ids to index when implied on an entity.
   * @since Niagara 4.3
   * @see #getIndexedTags
   * @see #setIndexedTags
   */
  public static final Property indexedTags = newProperty(0, "", null);

  /**
   * Get the {@code indexedTags} property.
   * Semicolon separated list of tag ids to index when implied on an entity.
   * @since Niagara 4.3
   * @see #indexedTags
   */
  public String getIndexedTags() { return getString(indexedTags); }

  /**
   * Set the {@code indexedTags} property.
   * Semicolon separated list of tag ids to index when implied on an entity.
   * @since Niagara 4.3
   * @see #indexedTags
   */
  public void setIndexedTags(String v) { setString(indexedTags, v, null); }

  //endregion Property "indexedTags"

  //region Property "neqlizeOptions"

  /**
   * Slot for the {@code neqlizeOptions} property.
   * Options to use when converting PX bound ORDs from slots to NEQL queries.
   * @since Niagara 4.9
   * @see #getNeqlizeOptions
   * @see #setNeqlizeOptions
   */
  public static final Property neqlizeOptions = newProperty(0, BNeqlizeOptions.DEFAULT, null);

  /**
   * Get the {@code neqlizeOptions} property.
   * Options to use when converting PX bound ORDs from slots to NEQL queries.
   * @since Niagara 4.9
   * @see #neqlizeOptions
   */
  public BNeqlizeOptions getNeqlizeOptions() { return (BNeqlizeOptions)get(neqlizeOptions); }

  /**
   * Set the {@code neqlizeOptions} property.
   * Options to use when converting PX bound ORDs from slots to NEQL queries.
   * @since Niagara 4.9
   * @see #neqlizeOptions
   */
  public void setNeqlizeOptions(BNeqlizeOptions v) { set(neqlizeOptions, v, null); }

  //endregion Property "neqlizeOptions"

  //region Property "Niagara"

  /**
   * Slot for the {@code Niagara} property.
   * The Niagara Tag Dictionary required for many built in functions based on tags.
   * @see #getNiagara
   * @see #setNiagara
   */
  public static final Property Niagara = newProperty(0, new BNiagaraTagDictionary(), null);

  /**
   * Get the {@code Niagara} property.
   * The Niagara Tag Dictionary required for many built in functions based on tags.
   * @see #Niagara
   */
  public BNiagaraTagDictionary getNiagara() { return (BNiagaraTagDictionary)get(Niagara); }

  /**
   * Set the {@code Niagara} property.
   * The Niagara Tag Dictionary required for many built in functions based on tags.
   * @see #Niagara
   */
  public void setNiagara(BNiagaraTagDictionary v) { set(Niagara, v, null); }

  //endregion Property "Niagara"

  //region Property "schemaVersion"

  /**
   * Slot for the {@code schemaVersion} property.
   * @since Niagara 4.3
   * @see #getSchemaVersion
   * @see #setSchemaVersion
   */
  public static final Property schemaVersion = newProperty(Flags.READONLY | Flags.HIDDEN, 0, null);

  /**
   * Get the {@code schemaVersion} property.
   * @since Niagara 4.3
   * @see #schemaVersion
   */
  public int getSchemaVersion() { return getInt(schemaVersion); }

  /**
   * Set the {@code schemaVersion} property.
   * @since Niagara 4.3
   * @see #schemaVersion
   */
  public void setSchemaVersion(int v) { setInt(schemaVersion, v, null); }

  //endregion Property "schemaVersion"

  //region Action "clearTagRuleIndex"

  /**
   * Slot for the {@code clearTagRuleIndex} action.
   * @see #clearTagRuleIndex()
   */
  public static final Action clearTagRuleIndex = newAction(0, null);

  /**
   * Invoke the {@code clearTagRuleIndex} action.
   * @see #clearTagRuleIndex
   */
  public void clearTagRuleIndex() { invoke(clearTagRuleIndex, null, null); }

  //endregion Action "clearTagRuleIndex"

  //region Action "invalidateAllTagIndexes"

  /**
   * Slot for the {@code invalidateAllTagIndexes} action.
   * Clear the indexed values for all tags being indexed so tag rules and tag groups are re-evaluated
   * the next time those tags are searched for.
   * @since Niagara 4.3
   * @see #invalidateAllTagIndexes()
   */
  public static final Action invalidateAllTagIndexes = newAction(0, null);

  /**
   * Invoke the {@code invalidateAllTagIndexes} action.
   * Clear the indexed values for all tags being indexed so tag rules and tag groups are re-evaluated
   * the next time those tags are searched for.
   * @since Niagara 4.3
   * @see #invalidateAllTagIndexes
   */
  public void invalidateAllTagIndexes() { invoke(invalidateAllTagIndexes, null, null); }

  //endregion Action "invalidateAllTagIndexes"

  //region Action "invalidateSingleTagIndex"

  /**
   * Slot for the {@code invalidateSingleTagIndex} action.
   * Clear the indexed values for a single tag being indexed so tag rules and tag groups are
   * re-evaluated the next time that tag is searched for.
   * @since Niagara 4.3
   * @see #invalidateSingleTagIndex(BString parameter)
   */
  public static final Action invalidateSingleTagIndex = newAction(0, BString.DEFAULT, null);

  /**
   * Invoke the {@code invalidateSingleTagIndex} action.
   * Clear the indexed values for a single tag being indexed so tag rules and tag groups are
   * re-evaluated the next time that tag is searched for.
   * @since Niagara 4.3
   * @see #invalidateSingleTagIndex
   */
  public void invalidateSingleTagIndex(BString parameter) { invoke(invalidateSingleTagIndex, parameter, null); }

  //endregion Action "invalidateSingleTagIndex"

  //region Action "query"

  /**
   * Slot for the {@code query} action.
   * @deprecated since Niagara 4.3 and will be removed in future release; no alternative available
   * @see #query(BString parameter)
   */
  @Deprecated
  public static final Action query = newAction(Flags.ASYNC | Flags.HIDDEN, BString.make(""), null);

  /**
   * Invoke the {@code query} action.
   * @deprecated since Niagara 4.3 and will be removed in future release; no alternative available
   * @see #query
   */
  @Deprecated
  public void query(BString parameter) { invoke(query, parameter, null); }

  //endregion Action "query"

  //region Action "tagsToTagGroup"

  /**
   * Slot for the {@code tagsToTagGroup} action.
   * Convert direct tags to tagGroups
   * @since Niagara 4.3
   * @see #tagsToTagGroup()
   */
  public static final Action tagsToTagGroup = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code tagsToTagGroup} action.
   * Convert direct tags to tagGroups
   * @since Niagara 4.3
   * @see #tagsToTagGroup
   */
  public BComponent tagsToTagGroup() { return (BComponent)invoke(tagsToTagGroup, null, null); }

  //endregion Action "tagsToTagGroup"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagDictionaryService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Add a non-BSmartTagDictionary SmartTagDictionary to a list when one might have been added.
   */
  @Override
  public void added(Property property, Context context)
  {
    super.added(property, context);

    if (isRunning())
    {
      rebuildPlainSmartDictionaryList();
    }
  }

  /**
   * Remove a non-BSmartTagDictionary SmartTagDictionary from a list when one might have been
   * removed.
   */
  @Override
  public void removed(Property property, BValue oldValue, Context context)
  {
    super.removed(property, oldValue, context);

    if (isRunning())
    {
      rebuildPlainSmartDictionaryList();
    }
  }

  /**
   * Populate the list of non-BSmartTagDictionary SmartTagDictionaries for use with the tag rule
   * index.
   */
  private void rebuildPlainSmartDictionaryList()
  {
    List<SmartTagDictionary> plainDictionaries = new ArrayList<>();
    for (SmartTagDictionary smart : getSmartTagDictionaries())
    {
      if (!(smart instanceof BSmartTagDictionary))
      {
        plainDictionaries.add(smart);
      }
    }

    plainSmartDictionaries.set(plainDictionaries);
  }

  /**
   * Starts the service (see fwServiceStarted()) if the {@link #enabled}
   * property is set or stops the service (see {@link #fwServiceStopped()}) if the
   * property is cleared. Clears the tag rule index whenever the {@link #tagRuleIndexEnabled}
   * property value changes.  Updates tags that are indexed when the {@link #indexedTags} property
   * value changes.
   *
   * @param property property that has changed
   * @param context execution context
   */
  @Override
  public void changed(Property property, Context context)
  {
    super.changed(property, context);

    if (property.equals(enabled))
    {
      if (getEnabled())
      {
        fwServiceStarted();
      }
      else
      {
        fwServiceStopped();
      }
    }
    else if (isRunning() && property.equals(tagRuleIndexEnabled))
    {
      doClearTagRuleIndex();
    }
    else if (property.equals(indexedTags))
    {
      updateIndexedTags();
    }
  }

  /**
   * If the service is operational (see {@link BAbstractService#isOperational()}), check the enabled
   * property. If enabled, set the tag dictionary service in the component space (see {@link
   * BComponent#getComponentSpace()}) to this object (see {@link
   * BComponentSpace#setTagDictionaryService(TagDictionaryService)}). Otherwise, remove this object
   * as the tag dictionary service (see {@link
   * BComponentSpace#removeTagDictionaryService(TagDictionaryService)}).
   */
  private void fwServiceStarted()
  {
    if (isOperational())
    {
      BComponentSpace space = getComponentSpace();
      if (space != null)
      {
        if (getEnabled())
        {
          space.setTagDictionaryService(this);

          smartTagDictionaryCache = new ConcurrentHashMap<>();

          initTagRuleIndexTypeSubscriber(space);
          initNeqlizeOptionsTypeSubscriber(space);
        }
        else
        {
          space.removeTagDictionaryService(this);

          smartTagDictionaryCache = null;
        }
      }

      if (worker == null)
      {
        worker = new Worker(taskQueue);
        worker.start("TagDictionary:ServiceWorker");
      }
    }

    // Previously, tag group relation endpoint ords were converted from slot path to handle ords if
    // the schema version was 0.  While the use of handle ords allowed tag groups and tag
    // dictionaries to be renamed without breaking the tag group relations, it prevented the tag
    // groups from being copied with the components on which they were placed because the tag groups
    // were outside of the object tree being copied and could not be remapped.  Therefore, slot path
    // ords are used again and no conversion routine is required here.
    //
    // The tag group relation endpoint ord is examined in the relationKnobAdded
    //
    // Any tag group monitors do still need to be removed, if present.
    if (getSchemaVersion() < 2)
    {
      removeTagGroupMonitor();
      setSchemaVersion(2);
    }
  }

  /**
   * Prior to Niagara 4.3U1, the tag group monitor was used to convert direct tags to tag groups.
   * This helped with the migration of stations in 4.0 to 4.1.  In 4.0, the tags in a tag groups
   * were added as separate direct tags and the connection to the tag group was not preserved.  The
   * tag group monitor also helped with template deployment.  Tag groups get converted to individual
   * direct tags when the template is deployed.  The tag group monitor would then re-group these
   * individual tags.
   * <p />
   * The tag group monitor would not always regroup correctly.  When there were multiple group defs
   * with the same tags in them (but default values differed), all of those groups would be applied
   * to the component.  For these and other reasons, the tag group monitor was removed from the tag
   * dictionary service.
   */
  @SuppressWarnings({ "deprecation", "UnnecessaryFullyQualifiedName" })
  private void removeTagGroupMonitor()
  {
    javax.baja.tagdictionary.data.BTagGroupMonitor[] tagGroupMonitors =
      CompUtil.getDescendants(this, javax.baja.tagdictionary.data.BTagGroupMonitor.class);
    for (javax.baja.tagdictionary.data.BTagGroupMonitor tagGroupMonitor : tagGroupMonitors)
    {
      remove(tagGroupMonitor);
    }
  }

  /**
   * Updates the tag index based on indexedTags property after all tag dictionaries, such as the
   * Niagara dictionary, have been started so the tag ids in the property can be checked against the
   * tag dictionaries.
   */
  @Override
  public void descendantsStarted() throws Exception
  {
    super.descendantsStarted();

    // Initialize the list of components that implement SmartTagDictionary but are not of type
    // BSmartTagDictionary
    rebuildPlainSmartDictionaryList();

    if (!disableTagIndexing)
    {
      updateIndexedTags();

      // Show the invalidate index actions if they were previously hidden and saved that way
      setFlags(invalidateAllTagIndexes,  getFlags(invalidateAllTagIndexes)  & ~Flags.HIDDEN);
      setFlags(invalidateSingleTagIndex, getFlags(invalidateSingleTagIndex) & ~Flags.HIDDEN);
    }
    else
    {
      // Hide the invalidate index actions
      setFlags(invalidateAllTagIndexes,  getFlags(invalidateAllTagIndexes)  | Flags.HIDDEN);
      setFlags(invalidateSingleTagIndex, getFlags(invalidateSingleTagIndex) | Flags.HIDDEN);

      // Do not hide or readonly the indexedTags property because the user may want to edit its
      // value to prevent a station reboot loop.

      // Add a dynamic slot that indicates tag indexing is disabled due to a system property
      String message = getLexicon().getText("tagIndexing.disabled.message", new Object[] {DISABLE_TAG_INDEXING_SYS_PROP});
      if (get("TagIndexingDisabled") == null)
      {
        add("TagIndexingDisabled", BString.make(message), Flags.READONLY | Flags.TRANSIENT);
      }

      logger.info(message);
    }
  }

  /**
   * Listen for renames that could affect the endpoint ords of any tag group definitions.
   * @since Niagara 4.4u1
   */
  private void fwStarted()
  {
    BNavRoot.INSTANCE.addNavListener(this);
  }

  /**
   * Removes this object as the tag dictionary service
   * (see {@link BComponentSpace#removeTagDictionaryService(TagDictionaryService)})
   * in the component space (see {@link BComponent#getComponentSpace()}).
   */
  private void fwServiceStopped()
  {
    BComponentSpace space = getComponentSpace();
    if (space != null)
    {
      space.removeTagDictionaryService(this);

      smartTagDictionaryCache = null;

      doClearTagRuleIndex();
      removeTagRuleIndexTypeSubscriber();
      removeNeqlizeOptionsTypeSubscriber();

      impliedTagIndex.invalidateAllIds();
    }

    if (worker != null)
    {
      worker.stop();
      worker = null;
    }
  }

  /**
   * Enforce the presence of the "tags" license feature.
   *
   * @return the license {@code Feature} for the {@code BTagDictionaryService}.
   */
  @Override
  public Feature getLicenseFeature()
  {
    return Sys.getLicenseManager().getFeature(LicenseUtil.TRIDIUM_VENDOR, "tags");
  }

  /**
   * Call fwServiceStarted() when moving from the enabled to disabled
   * state.
   */
  @Override
  protected void enabled()
  {
    fwServiceStarted();
  }

  /**
   * Call {@link #fwServiceStopped()} when moving from the disabled to enabled
   * state.
   */
  @Override
  protected void disabled()
  {
    fwServiceStopped();
  }

  /**
   * @since Niagara 4.4u1
   */
  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch (x)
    {
      case Fw.SERVICE_STARTED: fwServiceStarted(); break;
      case Fw.SERVICE_STOPPED: fwServiceStopped(); break;
      case Fw.STARTED: fwStarted(); break;
    }

    return super.fw(x, a, b, c, d);
  }

  /**
   * Get the default namespace for this service.  This is typically the namespace of a dictionary
   * installed in the service.  If a default namespace is defined, global tags that appear in tag
   * queries are assumed to be tags in the dictionary that owns the default namespace.
   *
   * @return {@link #defaultNamespaceId} property value if not empty; {@code null} otherwise
   */
  @Override
  public String getDefaultNamespace()
  {
    String ns = getDefaultNamespaceId();
    return ns.isEmpty() ? null : ns;
  }

  /**
   * Set the default namespace for this service.  See {@link #getDefaultNamespace()}.
   */
  @Override
  public void setDefaultNamespace(String defaultNamespace)
  {
    if (defaultNamespace == null)
    {
      setDefaultNamespaceId("");
    }
    else
    {
      setDefaultNamespaceId(defaultNamespace);
    }
  }

  /**
   * If a {@code TagDictionary} implements {@link BIStatus}, include it only
   * if it is valid (see {@link BStatus#isValid()}). Include all
   * {@code TagDictionary}s that do not implement {@code BIStatus}.
   *
   * @return a collection of installed and valid {@code TagDictionary}s
   */
  @Override
  public Collection<TagDictionary> getTagDictionaries()
  {
    Collection<TagDictionary> result = new ArrayList<>();
    SlotCursor<Property> c = getProperties();
    while (c.next(TagDictionary.class))
    {
      TagDictionary dictionary = (TagDictionary)c.get();
      if (dictionary instanceof BIStatus)
      {
        if (((BIStatus)dictionary).getStatus().isValid())
        {
          result.add(dictionary);
        }
      }
      else
      {
        result.add(dictionary);
      }
    }

    return result;
  }

  /**
   * Get the TagDictionary for the specified namespace, if it exists. If the TagDictionary
   * implements {@link BIStatus}, it must be valid (see {@link BStatus#isValid()}) to be returned.
   *
   * @param namespace The string id of the namespace defined by the target dictionary.
   * @return the TagDictionary with the specified namespace or {@link Optional#empty()} if the
   * dictionary for the specified namespace is not installed
   */
  @Override
  public Optional<TagDictionary> getTagDictionary(String namespace)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next(TagDictionary.class))
    {
      TagDictionary dictionary = (TagDictionary)c.get();
      if (!dictionary.getNamespace().equals(namespace))
      {
        continue;
      }

      if (dictionary instanceof BIStatus)
      {
        if (((BIStatus)dictionary).getStatus().isValid())
        {
          return Optional.of(dictionary);
        }
      }
      else
      {
        return Optional.of(dictionary);
      }
    }

    return Optional.empty();
  }

  /**
   * If a {@code SmartTagDictionary} implements {@link BIStatus}, include it only
   * if it is valid (see {@link BStatus#isValid()}). Include all
   * {@code SmartTagDictionary}s that do not implement {@code BIStatus}.
   *
   * @return a collection of installed and valid {@code SmartTagDictionary}s
   */
  @Override
  public Collection<SmartTagDictionary> getSmartTagDictionaries()
  {
    Collection<SmartTagDictionary> result = new ArrayList<>();
    SlotCursor<Property> c = getProperties();
    while (c.next(SmartTagDictionary.class))
    {
      SmartTagDictionary dictionary = (SmartTagDictionary)c.get();
      if (dictionary instanceof BIStatus)
      {
        if (((BIStatus)dictionary).getStatus().isValid())
        {
          result.add(dictionary);
        }
      }
      else
      {
        result.add(dictionary);
      }
    }

    return result;
  }

  /**
   * Get the {@code SmartTagDictionary} for the specified namespace, if it exists. If the
   * SmartTagDictionary implements {@link BIStatus}, it must be valid (see
   * {@link BStatus#isValid()}) to be returned.
   *
   * @param namespace namespace defined by an installed tag dictionary, if one exists
   * @return an {@code Optional} that contains the {@code SmartTagDictionary} for the specified
   * namespace, if it exists; an empty {@code Optional} otherwise
   */
  @Override
  public Optional<SmartTagDictionary> getSmartTagDictionary(String namespace)
  {
    if (smartTagDictionaryCache == null)
    {
      return getSmartTagDictionary2(namespace);
    }

    Optional<SmartTagDictionary> smartDictionary = smartTagDictionaryCache.getOrDefault(namespace, Optional.empty());

    // Namespace not found
    if (!smartDictionary.isPresent())
    {
      return smartDictionary;
    }

    SmartTagDictionary d = smartDictionary.get();
    if (!(d instanceof BIStatus) || ((BIStatus)d).getStatus().isValid())
    {
      return smartDictionary;
    }

    // If not BIStatus or not valid
    return Optional.empty();
  }

  /**
   * Return the cache containing SmartTagDictionaries
   *
   * @return cache
   * @since Niagara 4.2
   */
  Map<String, Optional<SmartTagDictionary>> getSmartTagDictionaryCache()
  {
    return smartTagDictionaryCache;
  }

  /**
   * This is the original version of getSmartTagDictionary() which doesn't use the cache. We still
   * use it for offline usage.  The cache is only used on a running Station.
   *
   * @param namespace namespace defined by an installed tag dictionary, if one exists
   * @return an {@code Optional} that contains the {@code SmartTagDictionary} for the specified
   * namespace, if it exists; an empty {@code Optional} otherwise
   */
  private Optional<SmartTagDictionary> getSmartTagDictionary2(String namespace)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next(SmartTagDictionary.class))
    {
      SmartTagDictionary d = (SmartTagDictionary)c.get();
      if (!d.getNamespace().equals(namespace))
      {
        continue;
      }

      if (d instanceof BIStatus)
      {
        if (((BIStatus)d).getStatus().isValid())
        {
          return Optional.of(d);
        }
      }
      else
      {
        return Optional.of(d);
      }
    }

    return Optional.empty();
  }

///////////////////////////////////////////////////////////
// Implied tags
///////////////////////////////////////////////////////////

  /**
   * Get an implied tag for the specified id on the specified entity.
   *
   * <p>If indexing of the tag has been enabled, check the tag index of the given entity. If the tag
   * has never been added to the index, look through the entity's direct tag groups (represented by
   * an "n:tagGroup" relation) and rules of the smart tag dictionaries.</p>
   *
   * @param id id of the implied tag
   * @param entity entity to evaluate for the implied tag
   * @return an implied tag or {@link Optional#empty()} if the tag is not implied on the specified
   * entity
   */
  @Override
  public Optional<Tag> getImpliedTag(Id id, Entity entity)
  {
    // Check to see if the tag for the given id should be indexed; if so, get the index of tags for
    // the given entity.  Nothing should be in the index if tag indexing is disabled the a system
    // property but check whether the system property is set here, too.
    boolean indexingTag = impliedTagIndex.isIndexed(id) && !disableTagIndexing;
    if (indexingTag)
    {
      // If the tag info is null, it probably means the given id has never been evaluated on the
      // given entity.  It could also mean that the tag was removed from indexing between the
      // isIndexed check above this getTagInfo call.  In either case, evaluate tag groups and tag
      // rules.  If the tag is no longer being indexed, any result from the evaluation will just be
      // ignored and isIndexed will return false next time.
      Optional<TagInfo> tagInfo = impliedTagIndex.getTagInfo(id, entity);
      //noinspection OptionalAssignedToNull
      if (tagInfo != null)
      {
        return mapTagInfoToTag(tagInfo, entity);
      }
    }

    Optional<TagInfo> tagInfo = searchSmartDictionariesForTag(id, entity);

    // Search for the tag within the entity's direct (not implied) tag groups
    if (!tagInfo.isPresent())
    {
      tagInfo = searchDirectTagGroups(id, entity);
    }

    // Add to the index of tags for the given entity if indexing is enabled for the tag.
    if (indexingTag)
    {
      impliedTagIndex.setTagInfo(id, entity, tagInfo);
    }

    return mapTagInfoToTag(tagInfo, entity);
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private static Optional<Tag> mapTagInfoToTag(Optional<TagInfo> tagInfo, Entity entity)
  {
    if (tagInfo.isPresent())
    {
      Tag tag = tagInfo.get().getTag(entity);
      if (tag != null)
      {
        return Optional.of(tag);
      }
    }

    return Optional.empty();
  }

  private Optional<TagInfo> searchSmartDictionariesForTag(Id id, Entity entity)
  {
    if (canUseTagRuleIndex())
    {
      // Check for rules in the index
      Set<TagRule> rules = getCachedTagRules(id);
      Optional<TagInfo> tagInfo = testRulesForImpliedTagInfo(id, entity, rules);
      if (tagInfo.isPresent())
      {
        return tagInfo;
      }

      // If not found with a rule from a BSmartTagDictionary, look through any
      // non-BSmartTagDictionaries, which do not necessarily contain rules but may still imply the
      // tag.
      return searchPlainSmartDictionariesForTag(id, entity);
    }
    else
    {
      // Tag rule indexing is not enabled.  Look through all SmartTagDictionaries.
      return searchAllSmartDictionariesForTag(id, entity);
    }
  }

  /**
   * Are the TagRuleIndexes enabled and is the list of non-BSmartTagDictionaries initialized?
   */
  private boolean canUseTagRuleIndex()
  {
    return getTagRuleIndexEnabled() && (plainSmartDictionaries.get() != null);
  }

  private Set<TagRule> getCachedTagRules(Id id)
  {
    Optional<Set<TagRule>> rulesMaybe = tagRuleIndex.get(id);
    if (rulesMaybe.isPresent())
    {
      return rulesMaybe.get();
    }
    else
    {
      // If not found, gather them from installed BSmartTagDictionaries.
      Set<TagRule> rules = new HashSet<>();
      SlotCursor<Property> smartDictionaries = getProperties();
      while (smartDictionaries.next(BSmartTagDictionary.class))
      {
        BSmartTagDictionary smartDictionary = (BSmartTagDictionary) smartDictionaries.get();
        if (smartDictionary.getEnabled())
        {
          Iterator<TagRule> dictionaryRules = smartDictionary.getRulesForTagId(id);
          while (dictionaryRules.hasNext())
          {
            rules.add(dictionaryRules.next());
          }
        }
      }

      tagRuleIndex.put(id, rules);
      return rules;
    }
  }

  /**
   * Test each rule to see if it implies the tag with the supplied id for the supplied entity
   */
  private static Optional<TagInfo> testRulesForImpliedTagInfo(Id id, Entity entity, Collection<TagRule> rules)
  {
    Iterator<TagRule> iterator = rules.iterator();
    while (iterator.hasNext())
    {
      TagRule rule = iterator.next();
      if (evaluateRuleOnEntity(rule, entity))
      {
        // Rule applies to the entity
        Optional<TagInfo> tagInfo = rule.getTag(id);
        if (tagInfo.isPresent())
        {
          // Rule contains the id.
          // The tag info getTag method may return null
          // TODO NCCB-40696 Evaluate the tagInfo based on the entity, go to the next one if it is not present
          return tagInfo;
        }
        else
        {
          // For whatever reason, the rule does not contain the id.  This should never be the case
          // if the tag rule index is being maintained correctly or the getRulesForId method is
          // working properly.  If it is the case, remove from the collection so the index can be
          // corrected.
          if (logger.isLoggable(Level.FINE))
          {
            logger.fine("Id " + id + " not found in tag rule " + rule +
              " retrieved from the tag rule index or using getRulesForId");
          }
          iterator.remove();
        }
      }
    }

    return Optional.empty();
  }

  private Optional<TagInfo> searchPlainSmartDictionariesForTag(Id id, Entity entity)
  {
    List<SmartTagDictionary> plainDictionaries = plainSmartDictionaries.get();
    for (SmartTagDictionary dictionary : plainDictionaries)
    {
      if (dictionary.getEnabled())
      {
        Optional<TagInfo> tagInfo = dictionary.getImpliedTagInfo(id, entity);
        if (tagInfo.isPresent())
        {
          return tagInfo;
        }
      }
    }

    return Optional.empty();
  }

  private Optional<TagInfo> searchAllSmartDictionariesForTag(Id id, Entity entity)
  {
    SlotCursor<Property> smartDictionaries = getProperties();
    while (smartDictionaries.next(SmartTagDictionary.class))
    {
      SmartTagDictionary smartDictionary = (SmartTagDictionary)smartDictionaries.get();
      if (smartDictionary.getEnabled())
      {
        Optional<TagInfo> tagInfo = smartDictionary.getImpliedTagInfo(id, entity);
        if (tagInfo.isPresent())
        {
          return tagInfo;
        }
      }
    }

    return Optional.empty();
  }

  private static Optional<TagInfo> searchDirectTagGroups(Id id, Entity entity)
  {
    // Implied tag groups are handled when evaluating tag rules. Only direct tag groups need to be
    // considered here.
    // See same code in getDirectTagGroupImpliedTags(Entity entity, Set<Tag> tags)
    for (Relation tagGroupRelation : getTagGroupRelations(entity))
    {
      Entity endpoint = getTagGroupEndpoint(entity, tagGroupRelation);
      if (endpoint instanceof TagGroupInfo)
      {
        Optional<TagInfo> tagInfo = searchTagGroup(id, (TagGroupInfo) endpoint);
        if (tagInfo.isPresent())
        {
          return tagInfo;
        }
      }
    }

    return Optional.empty();
  }

  private static Optional<TagInfo> searchTagGroup(Id id, TagGroupInfo tagGroupInfo)
  {
    if (isDictionaryValid(tagGroupInfo))
    {
      // Check the tag group's name tag
      Optional<TagInfo> nameTagInfo = tagGroupInfo.getNameTagInfo();
      if (nameTagInfo.isPresent() && nameTagInfo.get().getTagId().equals(id))
      {
        return nameTagInfo;
      }

      // Check the tags within the tag group
      Iterator<TagInfo> groupTagInfos = tagGroupInfo.getTags();
      while (groupTagInfos.hasNext())
      {
        TagInfo groupTagInfo = groupTagInfos.next();
        if (groupTagInfo.getTagId().equals(id))
        {
          return Optional.of(groupTagInfo);
        }
      }
    }

    return Optional.empty();
  }

  private static boolean isDictionaryValid(TagGroupInfo tagGroup)
  {
    // Ensure the tag group's dictionary exists and, if implements BIStatus, is valid
    Optional<TagDictionary> dictionary = tagGroup.getDictionary();
    return dictionary.isPresent() &&
      (!(dictionary.get() instanceof BIStatus) || ((BIStatus)dictionary.get()).getStatus().isValid());
  }

  /**
   * Get all tags implied on the specified entity. The resulting collection is populated by, first,
   * adding tags implied by tag groups on the entity. Then, the installed SmartTagDictionaries are
   * iterated and the implied tags are added from each. See {@link
   * SmartTagDictionary#addAllImpliedTags(Entity, Collection)} for further details.
   *
   * @param entity The entity to evaluate for implied tags.
   * @return a collection of tags implied on the entity
   */
  @Override
  public Collection<Tag> getImpliedTags(Entity entity)
  {
    // first imply any tags through a "n:TagGroup" relation
    Set<Tag> related = new LinkedHashSet<>();
    getDirectTagGroupImpliedTags(entity, related);

    Collection<Tag> result = new ArrayList<>(related);

    for (SmartTagDictionary smartTagDictionary : getSmartTagDictionaries())
    {
      if (smartTagDictionary.getEnabled())
      {
        smartTagDictionary.addAllImpliedTags(entity, result);
      }
    }

    return result;
  }

  /**
   * Add the tags implied by the entity's direct tag groups. Implied tag groups are handled when
   * evaluating tag rules.
   *
   * @param entity entity whose direct tag groups are evaluated
   * @param tags set of tags to which are added the tag implied by the entity's direct tag groups
   */
  private static void getDirectTagGroupImpliedTags(Entity entity, Collection<Tag> tags)
  {
    // See same code in getImpliedTags(Id, Entity)
    for (Relation tagGroupRelation : getTagGroupRelations(entity))
    {
      Entity endpoint = getTagGroupEndpoint(entity, tagGroupRelation);
      if (endpoint instanceof TagGroupInfo)
      {
        TagGroupInfo tagGroup = (TagGroupInfo)endpoint;
        if (isDictionaryValid(tagGroup))
        {
          tagGroup.addAllImpliedTags(entity, tags);
        }
      }
    }
  }

  private static Collection<Relation> getTagGroupRelations(Entity entity)
  {
    // The handle ord in a direct tag group relation on a virtual component may not correspond to
    // handle ord of the tag group in the virtual space.  Therefore, skip tag group relations on
    // virtual components.
    if (entity instanceof BVirtualComponent)
    {
      return Collections.emptyList();
    }

    try
    {
      Relations relations = entity instanceof BComponent ?
        new ComponentRelations((BComponent)entity) :
        entity.relations();
      return relations.getAll(BNiagaraTagDictionary.TAG_GROUP_RELATION, Relations.OUT);
    }
    catch (IllegalStateException | UnresolvedException e)
    {
      logger.log(Level.FINE, "Could not retrieve the tag group relations for entity " +
        entity.getOrdToEntity().orElse(BOrd.NULL), e);
      return Collections.emptyList();
    }
  }

  private static Entity getTagGroupEndpoint(Entity entity, Relation tagGroupRelation)
  {
    try
    {
      return tagGroupRelation.getEndpoint();
    }
    catch (IllegalStateException | UnresolvedException e)
    {
      logger.log(Level.FINE, "Could not resolve the endpoint of the tag group relation " +
        tagGroupRelation + " for entity " + entity.getOrdToEntity().orElse(BOrd.NULL), e);
    }

    return null;
  }

///////////////////////////////////////////////////////////
// Tag Rule Index
///////////////////////////////////////////////////////////

  /**
   * Clear all entries of the tag rule index.
   */
  public void doClearTagRuleIndex()
  {
    tagRuleIndex.clear();
    relationRuleIndex.clear();
  }

///////////////////////////////////////////////////////////
// Implied tag index management
///////////////////////////////////////////////////////////

  /**
   * Clear the indexed values for all tags being indexed so tag rules and tag groups are
   * re-evaluated the next time those tags are searched for.
   *
   * @throws LocalizableException an error occurs while resetting index values
   *
   * @since Niagara 4.3
   */
  public void doInvalidateAllTagIndexes() throws LocalizableException
  {
    try
    {
      logger.fine("Invalidating all tag indexes");
      impliedTagIndex.invalidateAllIds();
    }
    catch (Exception e)
    {
      UUID uuid = UUID.randomUUID();
      logger.warning("Error attempting to invalidate all tag indexes (" + uuid + "): " + e);
      logger.log(Level.FINE, e, uuid::toString);
      throw new LocalizableException("tagdictionary", "invalidateAllTagIndexes.error", new Object[] { uuid });
    }
  }

  /**
   * Clear the indexed values for a single tag being indexed so tag rules and tag groups are
   * re-evaluated the next time that tag is searched for.
   *
   * @param qName qualified name of the tag for which index values should be reset
   * @throws LocalizableException format of qName is incorrect or an error occurs while resetting
   * index values
   *
   * @since Niagara 4.3
   */
  public void doInvalidateSingleTagIndex(BString qName) throws LocalizableException
  {
    // Attempt to convert the supplied string to an ID
    Id id;
    try
    {
      id = TagDictionaryUtil.prependDefaultIfMissingNamespace(qName.getString(), getDefaultNamespace());
    }
    catch (Exception e)
    {
      UUID uuid = UUID.randomUUID();
      logger.warning("Error converting supplied tag id string \"" + qName + "\" to ID (" + uuid + "): " + e);
      logger.log(Level.FINE, uuid.toString(), e);
      throw new LocalizableException("tagdictionary", "invalidateSingleTagIndex.badQNameFormat", new Object[] { qName, e.getLocalizedMessage() });
    }

    try
    {
      if (logger.isLoggable(Level.FINE))
      {
        logger.fine("Reset the implied tag index for " + id);
      }
      impliedTagIndex.invalidateSingleId(id);
    }
    catch (Exception e)
    {
      UUID uuid = UUID.randomUUID();
      logger.warning("Error attempting to reset the index for tag id " + qName + " (" + uuid + "): " + e);
      logger.log(Level.FINE, uuid.toString(), e);
      throw new LocalizableException("tagdictionary", "invalidateSingleTagIndex.error", new Object[] { qName, uuid });
    }
  }

  /**
   * Parse the indexed tags property.  Remove tags from being indexed that are no longer listed in
   * the property and add tags to be indexed that are added to the property.  (Server side only)
   *
   * @since Niagara 4.3
   */
  private void updateIndexedTags()
  {
    // Run on the server side only
    if (!isRunning())
    {
      return;
    }

    if (!disableTagIndexing)
    {
      Set<Id> ids = TagDictionaryUtil.parseIndexedTags(getIndexedTags(), this);
      impliedTagIndex.setIndexedIds(ids);
    }
  }

///////////////////////////////////////////////////////////
// Implied relations
///////////////////////////////////////////////////////////

  /**
   * Add to the supplied collection all relations with the specified ID implied by a {@link
   * SmartTagDictionary} for the specified source entity. The smart tag dictionary must be enabled
   * and its namespace must match that of the ID.  See {@link
   * SmartTagDictionary#addImpliedRelations(Id, Entity, Collection)} for further details.
   *
   * @param id The id of the relation.
   * @param source The source entity of the relations.
   * @param relations The collection of relations to populate.
   */
  @Override
  public void addImpliedRelations(Id id, Entity source, Collection<Relation> relations)
  {
    if (canUseTagRuleIndex())
    {
      // Check for rules in the index
      Iterator<TagRule> rules = getCachedRelationRules(id).iterator();
      while (rules.hasNext())
      {
        TagRule rule = rules.next();
        if (evaluateRuleOnEntity(rule, source))
        {
          // Rule applies to the entity
          Iterator<RelationInfo> relationInfos = rule.getRelations(id);
          if (relationInfos.hasNext())
          {
            while (relationInfos.hasNext())
            {
              relationInfos.next().addRelations(source, relations);
            }
          }
          else
          {
            // For whatever reason, the rule does not contain the id.  This should never be the case
            // if the relation rule index is being maintained correctly or the getRelationRulesForId
            // method is working properly. If it is the case remove from the collection so the index
            // can be corrected.
            if (logger.isLoggable(Level.FINE))
            {
              logger.fine("Id " + id + " not found in tag rule " + rule +
                " retrieved from the relation rule index or using getRulesForRelationId");
            }
            rules.remove();
          }
        }
      }

      // Look through any non-BSmartTagDictionaries, which do not necessarily contain rules but may
      // still imply relations.
      List<SmartTagDictionary> plainDictionaries = plainSmartDictionaries.get();
      for (SmartTagDictionary dictionary : plainDictionaries)
      {
        if (dictionary.getEnabled())
        {
          relations.addAll(dictionary.getImpliedRelations(id, source));
        }
      }
    }
    else
    {
      // Relation rule indexing is not enabled.  Look through all SmartTagDictionaries.
      SlotCursor<Property> smartDictionaries = getProperties();
      while (smartDictionaries.next(SmartTagDictionary.class))
      {
        SmartTagDictionary smartDictionary = (SmartTagDictionary) smartDictionaries.get();
        if (smartDictionary.getEnabled())
        {
          relations.addAll(smartDictionary.getImpliedRelations(id, source));
        }
      }
    }
  }

  /**
   * Get the first or only relation with the specified id with the specified entity
   * as the source.
   * <p>
   * <p>An installed {@link SmartTagDictionary} that matches the namespace of the id
   * (see {@link #getSmartTagDictionary(String)}) and is enabled
   * (see {@link SmartTagDictionary#getEnabled()}), if one exists, is used to find
   * the implied relation.</p>
   *
   * @param id id of the relation
   * @param source source entity for the relation
   * @return an {@code Optional} that contains the implied {@code Relation} for the
   * entity that matches the id, if it exists; an empty {@code Optional} otherwise.
   */
  @Override
  public Optional<Relation> getImpliedRelation(Id id, Entity source)
  {
    if (canUseTagRuleIndex())
    {
      // Check for rules in the index
      Set<TagRule> rules = getCachedRelationRules(id);
      Optional<Relation> relation = testRulesForImpliedRelation(id, source, rules);
      if (relation.isPresent())
      {
        return relation;
      }

      // If not found with a rule from a BSmartTagDictionary, look through any
      // non-BSmartTagDictionaries, which do not necessarily contain rules but may still imply the
      // relation.
      return searchPlainSmartDictionariesForRelation(id, source);
    }
    else
    {
      // Relation rule indexing is not enabled.  Look through all SmartTagDictionaries.
      return searchAllSmartDictionariesForRelation(id, source);
    }
  }

  private Set<TagRule> getCachedRelationRules(Id id)
  {
    Optional<Set<TagRule>> rulesMaybe = relationRuleIndex.get(id);
    if (rulesMaybe.isPresent())
    {
      return rulesMaybe.get();
    }
    else
    {
      // If not found, gather them from installed BSmartTagDictionaries.
      Set<TagRule> rules = new HashSet<>();
      SlotCursor<Property> smartDictionaries = getProperties();
      while (smartDictionaries.next(BSmartTagDictionary.class))
      {
        BSmartTagDictionary smartDictionary = (BSmartTagDictionary) smartDictionaries.get();
        if (smartDictionary.getEnabled())
        {
          Iterator<TagRule> dictionaryRules = smartDictionary.getRulesForRelationId(id);
          while (dictionaryRules.hasNext())
          {
            rules.add(dictionaryRules.next());
          }
        }
      }

      relationRuleIndex.put(id, rules);
      return rules;
    }
  }

  private static Optional<Relation> testRulesForImpliedRelation(Id id, Entity source, Iterable<TagRule> rules)
  {
    Iterator<TagRule> iterator = rules.iterator();
    while (iterator.hasNext())
    {
      TagRule rule = iterator.next();
      if (evaluateRuleOnEntity(rule, source))
      {
        // Rule applies to the entity
        Iterator<RelationInfo> relationInfos = rule.getRelations(id);
        if (relationInfos.hasNext())
        {
          while (relationInfos.hasNext())
          {
            RelationInfo relationInfo = relationInfos.next();
            Optional<Relation> relation = relationInfo.getRelation(source);
            if (relation.isPresent())
            {
              return relation;
            }
          }
        }
        else
        {
          // For whatever reason, the rule does not contain the id.  This should never be the case
          // if the relation rule index is being maintained correctly or the getRelationRulesForId
          // method is working properly. If it is the case, remove from the collection so the index
          // can be corrected.
          if (logger.isLoggable(Level.FINE))
          {
            logger.fine("Id " + id + " not found in tag rule " + rule +
              " retrieved from the relation rule index or using getRulesForRelationId");
          }
          iterator.remove();
        }
      }
    }

    return Optional.empty();
  }

  private Optional<Relation> searchPlainSmartDictionariesForRelation(Id id, Entity source)
  {
    List<SmartTagDictionary> plainDictionaries = plainSmartDictionaries.get();
    for (SmartTagDictionary dictionary : plainDictionaries)
    {
      if (dictionary.getEnabled())
      {
        Optional<Relation> relation = dictionary.getImpliedRelation(id, source);
        if (relation.isPresent())
        {
          return relation;
        }
      }
    }

    return Optional.empty();
  }

  private Optional<Relation> searchAllSmartDictionariesForRelation(Id id, Entity source)
  {
    SlotCursor<Property> smartDictionaries = getProperties();
    while (smartDictionaries.next(SmartTagDictionary.class))
    {
      SmartTagDictionary smartDictionary = (SmartTagDictionary) smartDictionaries.get();
      if (smartDictionary.getEnabled())
      {
        Optional<Relation> relation = smartDictionary.getImpliedRelation(id, source);
        if (relation.isPresent())
        {
          return relation;
        }
      }
    }

    return Optional.empty();
  }

  /**
   * Add all implied relations for the specified source entity to the collection of relations. See
   * {@link SmartTagDictionary#addAllImpliedRelations(Entity, Collection)} for further details.
   *
   * @param source The entity that is the source of the relation.
   * @param relations The collection of relations to populate.
   */
  @Override
  public void addAllImpliedRelations(Entity source, Collection<Relation> relations)
  {
    SlotCursor<Property> c = getProperties();
    while (c.next(SmartTagDictionary.class))
    {
      SmartTagDictionary td = (SmartTagDictionary)c.get();
      if (td.getEnabled())
      {
        td.addAllImpliedRelations(source, relations);
      }
    }
  }

  /**
   * Convenience method to call {@link TagRule#evaluate(Entity)} on the given Entity with
   * exception handling.  The return value will either be the result of calling evaluate,
   * or false if any exception occurred.
   *
   * @since Niagara 4.13u1
   */
  static boolean evaluateRuleOnEntity(TagRule rule, Entity entity)
  {
    try
    {
      return rule.evaluate(entity);
    }
    catch (Throwable t)
    {
      logger.log(Level.WARNING, "Unexpected error evaluating tag rule: " + rule, logger.isLoggable(Level.FINE) ? t : null);
      // If any errors occur during evaluation, return false to assume the evaluation doesn't match
      // the given Entity
      return false;
    }
  }

///////////////////////////////////////////////////////////
// Data policies
///////////////////////////////////////////////////////////

  /**
   * Searches within the tags of the dictionary that matches the namespace of
   * tag id, if it exists (see {@link #getTagDictionary(String)}). Returns the
   * data policy of the first tag with a matching ID, even if that policy is
   * empty.
   *
   * @param tagId id of the tag
   * @return an {@code Optional} that contains the data policy for the
   * entity that matches the id, if it exists; an empty {@code Optional} otherwise.
   */
  @Override
  public Optional<DataPolicy> getDataPolicyForTag(Id tagId)
  {
    Optional<TagDictionary> optional = getTagDictionary(tagId.getDictionary());
    if (optional.isPresent())
    {
      Iterator<TagInfo> tags = optional.get().getTags();
      while (tags.hasNext())
      {
        BTagInfo tagInfo = (BTagInfo)tags.next();
        if (tagInfo.getTagId().equals(tagId))
        {
          return tagInfo.getDataPolicy();
        }
      }
    }

    return Optional.empty();
  }

  /**
   * Call back when a tag with the given Id has been added to the given target entity.
   * <p>
   * <p>Since Niagara 4.3U1, this no longer calls BTagGroupMonitor#tagAdded(Entity, Id) on the
   * monitor property.</p>
   *
   * @param target target entity
   * @param tagId  id of the tag that has been added
   */
  @Override
  public void tagAdded(Entity target, Id tagId)
  {
  }

  /**
   * Call back when a tag with the given Id has been removed from the given target entity.
   * <p>
   * <p>Since Niagara 4.3U1, this no longer calls BTagGroupMonitor#tagRemoved(Entity, Id) on the
   * monitor property.</p>
   *
   * @param target target entity
   * @param tagId  id of the tag that has been removed
   */
  @Override
  public void tagRemoved(Entity target, Id tagId)
  {
  }

///////////////////////////////////////////////////////////
// Query
///////////////////////////////////////////////////////////

  /**
   * Perform the query action and print results to the console
   *
   * @param query query to execute
   * @deprecated will be removed in future release; no alternative available
   */
  @Deprecated
  @SuppressWarnings("unused")
  public void doQuery(BString query)
  {
    throw new UnsupportedOperationException();
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Add implied tag index information to the spy.
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    if (isRunning())
    {
      // Tag and relation rule index info
      int numIndexedTags = 0;
      int numTagRules = 0;
      int numIndexedRelations = 0;
      int numRelationRules = 0;
      if (getTagRuleIndexEnabled())
      {
        numIndexedTags = tagRuleIndex.getIndexedIds().size();
        numTagRules = getNumIndexedTagRules(tagRuleIndex);
        numIndexedRelations = relationRuleIndex.getIndexedIds().size();
        numRelationRules = getNumIndexedTagRules(relationRuleIndex);
      }

      out.startProps();
      out.trTitle("Tag rule index info", 2);
      out.prop("# of indexed tags", numIndexedTags);
      out.prop("# of tag rules", numTagRules);
      out.prop("# of indexed relations", numIndexedRelations);
      out.prop("# of relation rules", numRelationRules);
      out.endProps();

      // Implied tag index info
      out.startProps();
      out.trTitle("Implied tag index info", 2);

      if (disableTagIndexing)
      {
        out.prop("tag indexing disabled; system property " + DISABLE_TAG_INDEXING_SYS_PROP + " is true", "");
      }

      Collection<Id> idsBeingIndexed = impliedTagIndex.getIndexedIds();
      if (!idsBeingIndexed.isEmpty())
      {
        out.prop("# of tags being indexed", idsBeingIndexed.size());
        out.prop("tags being indexed", idsBeingIndexed.toString());
        // TODO out.prop("size of implied tag index", impliedTagIndex.getMemorySize() + " kB");
      }
      else
      {
        out.prop("no tags being indexed", "");
      }

      out.endProps();

      out.startProps();
      out.trTitle("Service Worker Queue", 2);
      out.prop("workInQueue", taskQueue.size());
      out.endProps();

      if (worker != null)
      {
        worker.spy(out);
      }
    }

    super.spy(out);
  }

  private static int getNumIndexedTagRules(TagRuleIndex tagRuleIndex)
  {
    int numTagRules = 0;
    for (Id id : tagRuleIndex.getIndexedIds())
    {
      Optional<Set<TagRule>> tagRules = tagRuleIndex.get(id);
      if (tagRules.isPresent())
      {
        numTagRules += tagRules.get().size();
      }
    }
    return numTagRules;
  }

///////////////////////////////////////////////////////////
// Types
///////////////////////////////////////////////////////////

  @Override
  public Type[] getServiceTypes()
  {
    return TYPES;
  }
  private static final Type[] TYPES = { TYPE };

///////////////////////////////////////////////////////////
// BIRestrictedComponent
///////////////////////////////////////////////////////////

  /**
   * Only one {@code BTagDictionaryService} allowed to live under the station's
   * BServiceContainer.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkParentForRestrictedComponent(parent, this);
  }

///////////////////////////////////////////////////////////
// NavListener
///////////////////////////////////////////////////////////

  /**
   * Check for a rename that would affect tag groups and update the endpoint ord of any relations to
   * affected tag groups.  Only considering tag dictionary renames, tag group renames in the tag
   * dictionary tag group definitions only (not tag groups in a tag rule tag group list), tag
   * dictionary service renames, and renames of any ancestors of the tag dictionary service.
   * @since Niagara 4.4u1
   */
  @Override
  public final void navEvent(NavEvent event)
  {
    if (event.getId() == NavEvent.RENAMED && event.getParent() instanceof BComponent)
    {
      BComponent parent = (BComponent)event.getParent();

      if (parent instanceof BTagDictionaryService)
      {
        BValue child = parent.get(event.getNewChildName());
        if (child instanceof BTagDictionary)
        {
          // Rename of a tag dictionary
          fixDictionaryTagGroups((BTagDictionary)child);
        }
      }
      else if (parent instanceof BTagGroupInfoList && parent.getParent() instanceof BTagDictionary)
      {
        // Parent is the tag group list immediately under a tag dictionary and not the tag group
        // list under a tag rule.  The tag groups under a tag rule are implied and do not have hard
        // relations that need to be updated.
        BValue child = parent.get(event.getNewChildName());
        if (child instanceof BTagGroupInfo)
        {
          // Rename of a tag group
          fixTagGroup((BTagGroupInfo)child);
        }
      }
      else
      {
        String childNewSlotPath = parent.getSlotPath().toString() + '/' + event.getNewChildName();
        final SlotPath serviceSlotPath = getSlotPath();
        if (serviceSlotPath != null && serviceSlotPath.toString().startsWith(childNewSlotPath))
        {
          // Parent is an ancestor of the tag dictionary service so fix all tag groups in all
          // dictionaries.
          for (BTagDictionary dictionary : getChildren(BTagDictionary.class))
          {
            fixDictionaryTagGroups(dictionary);
          }
        }
      }
    }
  }

  /**
   * Fix tag groups in the tag group definitions of the tag dictionary.  Do not fix tag groups in
   * the tag group list of a tag rule.
   * @since Niagara 4.4u1
   */
  private static void fixDictionaryTagGroups(BTagDictionary dictionary)
  {
    for (BTagGroupInfo tagGroup : dictionary.getTagGroupDefinitions().getChildren(BTagGroupInfo.class))
    {
      fixTagGroup(tagGroup);
    }
  }

  /**
   * Update the endpoint ord of any relations to the tag group with the current slot path ord of the
   * tag group.
   * @since Niagara 4.4u1
   */
  private static void fixTagGroup(BTagGroupInfo tagGroup)
  {
    BOrd slotPathOrd = tagGroup.getSlotPathOrd();
    for (RelationKnob knob : tagGroup.getRelationKnobs())
    {
      if (BTagGroupInfo.isTagGroupRelationKnob(knob))
      {
        try
        {
          knob.getRelation().setEndpointOrd(slotPathOrd);
        }
        catch (Exception e)
        {
          logger.log(Level.WARNING, "Failed to update endpoint ord for tag group " + slotPathOrd, e);
        }
      }
    }
  }

///////////////////////////////////////////////////////////
// Tag Rule Index Type Subscriber
///////////////////////////////////////////////////////////

  /**
   * Initialize the type subscriber with all types and their events that
   * could affect the validity of entries in the tag rule index.
   *
   * @param space The component space
   */
  private void initTagRuleIndexTypeSubscriber(BComponentSpace space)
  {
    // Note: if this method is called more than once, it will not do anything
    // after a tagRuleIndexTypeSubscriber has been initialized on the service.
    // In the very unlikely case that the component space changes without
    // fwServiceStopped() getting called, the tag rule index or the service would
    // need to be disabled and then re-enabled to reinitialize the type subscriber.
    if (tagRuleIndexTypeSubscriber == null)
    {
      logger.fine("TypeSubscriber: initializing type subscriber to keep tag rule index current");
      tagRuleIndexTypeSubscriber = new TagRuleIndexTypeSubscriber(space);

      // set mask with events that could necessitate clearing the tag rule index
      tagRuleIndexTypeSubscriber.setMask(BComponentEventMask.make(new int[] {
        BComponentEvent.COMPONENT_PARENTED,
        BComponentEvent.COMPONENT_UNPARENTED,
        BComponentEvent.COMPONENT_RENAMED,
        BComponentEvent.COMPONENT_STARTED,
        BComponentEvent.COMPONENT_STOPPED,
        BComponentEvent.PROPERTY_CHANGED // for namespace changes
      }));

      // set the types to watch and subscribe to those types
      tagRuleIndexTypeSubscriber.subscribe(new Type[] {
        BTagDictionaryService.TYPE,
        BSmartTagDictionary.TYPE,
        BTagInfo.TYPE,
        BTagInfoList.TYPE,
        BTagGroupInfo.TYPE,
        BTagGroupInfoList.TYPE,
        BRelationInfo.TYPE,
        BRelationInfoList.TYPE,
        BTagRule.TYPE,
        BTagRuleList.TYPE
      }, null);
    }
  }

  /**
   * Unsubscribe all types and remove the type subscriber.
   */
  private void removeTagRuleIndexTypeSubscriber()
  {
    if (tagRuleIndexTypeSubscriber != null)
    {
      logger.fine("TypeSubscriber: removing tag rule index type subscriber");
      tagRuleIndexTypeSubscriber.unsubscribeAll();
      tagRuleIndexTypeSubscriber = null;
    }
  }

///////////////////////////////////////////////////////////
// Neqlize Default Exclusions Type Subscriber
///////////////////////////////////////////////////////////

  private void initNeqlizeOptionsTypeSubscriber(BComponentSpace space)
  {
    if (neqlizeOptionsTypeSubscriber == null)
    {
      neqlLogger.fine("TypeSubscriber: initializing type subscriber to update neqlize default exclusions");
      neqlizeOptionsTypeSubscriber = new NeqlizeOptionsTypeSubscriber(space);

      // set mask with events that could necessitate clearing the tag rule index
      neqlizeOptionsTypeSubscriber.setMask(BComponentEventMask.make(new int[] {
        BComponentEvent.COMPONENT_PARENTED,
        BComponentEvent.COMPONENT_UNPARENTED,
        BComponentEvent.COMPONENT_STARTED,
        BComponentEvent.PROPERTY_CHANGED // for dictionary default exclusion changes
      }));

      // set the types to watch and subscribe to those types
      neqlizeOptionsTypeSubscriber.subscribe(new Type[] {
        BTagDictionary.TYPE
      }, null);
    }
  }

  private void removeNeqlizeOptionsTypeSubscriber()
  {
    if (neqlizeOptionsTypeSubscriber != null)
    {
      neqlLogger.fine("TypeSubscriber: removing type subscriber to update neqlize default exclusions");
      neqlizeOptionsTypeSubscriber.unsubscribeAll();
      neqlizeOptionsTypeSubscriber = null;
    }
  }

///////////////////////////////////////////////////////////
// Service worker
///////////////////////////////////////////////////////////

  <T extends ICoalesceable & Runnable> void addTask(T task)
  {
    taskQueue.enqueue(task);
  }

///////////////////////////////////////////////////////////
// Tags to TagGroup migration / conversion
///////////////////////////////////////////////////////////

  public BComponent doTagsToTagGroup()
  {
    final List<TagDictionaryUtil.ComponentTagGroupChoices> retList = new ArrayList<>();
    BComponent root = getComponentSpace().getRootComponent();
    List<BComponent> entities = TagDictionaryUtil.getComponents(root);
    List<BTagGroupInfo> tagGroups = TagDictionaryUtil.getTagGroups(this);
    TagDictionaryUtil.listPotentialTagGroupsFromTags(entities, tagGroups, retList);
    BComponent rtnComp = new BComponent();
    if (!retList.isEmpty())
    {
      TagDictionaryUtil.ComponentTagGroupChoices.encodeToComponent(retList, rtnComp);
    }
    return rtnComp;
  }

///////////////////////////////////////////////////////////
// Icon
///////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    return icon;
  }
  private static final BIcon icon = BIcon.std("navOnly/tagDictionaryService.png");

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  /** Non-BSmartTagDictionary components that implement SmartTagDictionary. */
  private final AtomicReference<List<SmartTagDictionary>> plainSmartDictionaries =
    new AtomicReference<>();

  private final TagRuleIndex tagRuleIndex = new TagRuleIndex();
  private final TagRuleIndex relationRuleIndex = new TagRuleIndex();

  private TagRuleIndexTypeSubscriber tagRuleIndexTypeSubscriber;
  private NeqlizeOptionsTypeSubscriber neqlizeOptionsTypeSubscriber;

  private final EntityTagIndex impliedTagIndex = new EntityTagIndex();

  private static final String DISABLE_TAG_INDEXING_SYS_PROP = "niagara.tagdictionary.disableTagIndexing";
  /**
   * Allow the user to disable tag indexing using a system property in case memory usage causes the
   * station to crash before the user can clear the {@link BTagDictionaryService#indexedTags}
   * property.
   */
  private final boolean disableTagIndexing =
    AccessController.doPrivileged((PrivilegedAction<Boolean>) () ->
      Boolean.getBoolean(DISABLE_TAG_INDEXING_SYS_PROP));

  public static final String LOGGER_NAME = "tagdictionary";
  private static final Logger logger = Logger.getLogger(LOGGER_NAME);
  private static final Logger neqlLogger = Logger.getLogger("tagdictionary.neqlize");

  private static Map<String, Optional<SmartTagDictionary>> smartTagDictionaryCache;

  /**
   * General worker and task queue used now for fixing tag group relation endpoint ords. Could not
   * use an asynchronous action on BTagGroupInfo and queued in the control engine because the
   * actions are coalesced based on component and action only and the argument is not considered.
   * The same tag group could have many endpoint ords to fix, especially at start up.
   */
  private Worker worker;
  private final CoalesceQueue taskQueue = new CoalesceQueue();

///////////////////////////////////////////////////////////
// TagRuleIndexTypeSubscriber
///////////////////////////////////////////////////////////

  /**
   * Events on certain Niagara types in the tagdictionary module may
   * invalidate parts of the tag rule index. This Type Subscriber listens
   * for such events and clears the tag rule index when they occur,
   * guaranteeing that the tag rule index is kept up to date as changes
   * to the station are made. Note, however, that it may unnecessarily
   * clear the index in some circumstances.
   */
  private class TagRuleIndexTypeSubscriber extends TypeSubscriber
  {
    /**
     * Initialize the type subscriber.
     *
     * @param space The component space
     */
    TagRuleIndexTypeSubscriber(BComponentSpace space)
    {
      super(space);
    }

    /**
     * Clears the tag rule index if the event could have invalidated part of the
     * tag rule index.
     *
     * @param event An event on a component of a subscribed type
     */
    @Override
    public void event(BComponentEvent event)
    {
      //short-circuit if index is disabled
      if (!getTagRuleIndexEnabled())
      {
        return;
      }

      // Here we check for any events we know we can ignore.
      int id = event.getId();
      Type type = event.getSourceComponent().getType();

      if (id == BComponentEvent.COMPONENT_RENAMED)
      {
        // If a component has been renamed and that component is one of the types below, then none
        // of the tag rule index has been invalidated so we do not need to clear it.
        if (type.is(BTagDictionaryService.TYPE) ||
            type.is(BSmartTagDictionary.TYPE) ||
            type.is(BTagInfoList.TYPE) ||
            type.is(BTagGroupInfoList.TYPE) ||
            type.is(BRelationInfoList.TYPE) ||
            type.is(BTagRule.TYPE) ||
            type.is(BTagRuleList.TYPE))
        {
          return;
        }

        // The following types when renamed require invalidating the index because the name affects
        // the id:
        // TODO NCCB-47112 Probably only matters if under a tag rule
        // * BTagInfo
        // * BTagGroupInfo
        // * BRelationInfo
      }
      else if (id == BComponentEvent.PROPERTY_CHANGED)
      {
        // If a property has been changed on a component that is one of the types below, then none
        // of the tag rule index has been invalidated, so we do not need to clear it.
        if (type.is(BTagInfo.TYPE) ||
            type.is(BTagInfoList.TYPE) ||
            type.is(BTagGroupInfo.TYPE) ||
            type.is(BTagGroupInfoList.TYPE) ||
            type.is(BRelationInfo.TYPE) ||
            type.is(BRelationInfoList.TYPE) ||
            type.is(BTagRule.TYPE) ||
            type.is(BTagRuleList.TYPE))
        {
          return;
        }

        // A property change on the following types invalidates the index because it may be the
        // dictionary's namespace or the tag dictionary service's default namespace.
        // * BTagDictionaryService
        // * BSmartTagDictionary
      }
      else if (id == BComponentEvent.COMPONENT_STARTED ||
               id == BComponentEvent.COMPONENT_PARENTED)
      {
        // If a component of the below types is added outside of a tag rule, then none of the tag
        // rule index has been invalidated, so we do not need to clear it.
        if (type.is(BTagInfo.TYPE) ||
            type.is(BTagInfoList.TYPE) ||
            type.is(BTagGroupInfo.TYPE) ||
            type.is(BTagGroupInfoList.TYPE) ||
            type.is(BRelationInfo.TYPE) ||
            type.is(BRelationInfoList.TYPE))
        {
          if (!hasTagRuleAncestor(event.getSourceComponent())) // walk up tree looking for rule
          {
            return;
          }
        }

        // If any of the following types are started or parented, the index must be invalidated.
        // * BTagDictionaryService- (not possible without removing this one)
        // * BSmartTagDictionary
        // * BTagRule
        // * BTagRuleList- (will probably be ignored, though)
      }

      // If the event is COMPONENT_STOPPED or COMPONENT_UNPARENTED for any of the following types,
      // invalidate the cache:
      // * BTagDictionaryService
      // * BSmartTagDictionary
      // TODO NCCB-47112 Probably only matters if under a tag rule
      // * BTagInfo
      // * BTagInfoList
      // * BTagGroupInfo
      // * BTagGroupInfoList
      // * BRelationInfo
      // * BRelationInfoList
      // * BTagRule
      // * BTagRuleList

      if (logger.isLoggable(Level.FINER))
      {
        logger.finer("TypeSubscriber: clearing tag rule index on event " + event + " for type " + type);
      }
      clearTagRuleIndex();
    }

    /**
     * Determine if a component has a TagRule as an ancestor.
     *
     * @param comp the component to check
     * @return {@code true} if the component has a tag rule ancestor,
     *   otherwise {@code false}.
     */
    private boolean hasTagRuleAncestor(BComponent comp)
    {
      BComponent parent = comp;
      while (parent != null)
      {
        if (parent instanceof TagRule)
        {
          return true;
        }

        parent = (BComponent)parent.getParent();
      }

      return false;
    }
  }

///////////////////////////////////////////////////////////
// NeqlizeOptionsTypeSubscriber
///////////////////////////////////////////////////////////

  /**
   * Events on certain Niagara types in the tagdictionary module may
   * require cached tag and relation exclusion lists to be regenerated.
   * This Type Subscriber listens for such events and regenerates the
   * exclusion lists as needed, guaranteeing that they are kept up to date
   * as changes to the station are made.
   */
  private class NeqlizeOptionsTypeSubscriber extends TypeSubscriber
  {
    /**
     * Initialize the type subscriber.
     * @param space The component space
     */
    NeqlizeOptionsTypeSubscriber(BComponentSpace space)
    {
      super(space);
    }

    @Override
    public void event(BComponentEvent event)
    {
      // Check for events indicating a tag dictionary has been added, removed, or edited.
      int id = event.getId();
      Type type = event.getSourceComponent().getType();
      boolean isNeqlizeOptionChanged = id == BComponentEvent.PROPERTY_CHANGED &&
        (BTagDictionary.neqlizeExcludedTags.equals(event.getSlot()) ||
         BTagDictionary.neqlizeExcludedRelations.equals(event.getSlot()));
      if (type.is(BTagDictionary.TYPE) &&
          (id == BComponentEvent.COMPONENT_PARENTED ||
           id == BComponentEvent.COMPONENT_UNPARENTED ||
           id == BComponentEvent.COMPONENT_STARTED ||
           isNeqlizeOptionChanged))
      {
        updateNeqlizeOptions();
      }
    }

    private void updateNeqlizeOptions()
    {
      neqlLogger.fine("Regenerating cached default exclusions");

      // Generate the updated tag and relation exclusion lists
      StringJoiner newExcludedTags = new StringJoiner("\n");
      StringJoiner newExcludedRelations = new StringJoiner("\n");

      // Not using getTagDictionaries because disabled dictionaries would be ignored when they are
      // added or modified but not included when they were enabled.  Also, even if disabled so no tags
      // are implied, there may be direct tags or relations that should still be excluded.
      SlotCursor<Property> c = getProperties();
      while (c.next(BTagDictionary.class))
      {
        BTagDictionary dictionary = (BTagDictionary) c.get();

        String dictionaryExcludedTags = dictionary.getNeqlizeExcludedTags().trim();
        if (!dictionaryExcludedTags.isEmpty())
        {
          newExcludedTags.add(dictionaryExcludedTags);
        }

        String dictionaryExcludedRelations = dictionary.getNeqlizeExcludedRelations().trim();
        if (!dictionaryExcludedRelations.isEmpty())
        {
          newExcludedRelations.add(dictionaryExcludedRelations);
        }
      }

      // Set the property values to the generated list results
      BNeqlizeOptions options = getNeqlizeOptions();
      options.setDefaultExcludedTags(newExcludedTags.toString());
      options.setDefaultExcludedRelations(newExcludedRelations.toString());
    }
  }
}
