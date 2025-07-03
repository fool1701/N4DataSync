/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import static javax.baja.tagdictionary.BTagDictionaryService.evaluateRuleOnEntity;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;

import javax.baja.collection.FilteredIterator;
import javax.baja.collection.SlotCursorIterator;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatus;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.BasicRelation;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.RelationInfo;
import javax.baja.tag.SmartTagDictionary;
import javax.baja.tag.Tag;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;

import com.tridium.sys.schema.Fw;
import com.tridium.tagdictionary.BNiagaraTagDictionary;

/**
 * BSmartTagDictionary is an extension of {@code BTagDictionary} that implements
 * {@code SmartTagDictionary}.
 *
 * @author John Sublett
 * @creation 2/18/14
 * @since Niagara 4.0
 */
@NiagaraType
/*
 The collection of tag rules defined for this dictionary.
 */
@NiagaraProperty(
  name = "tagRules",
  type = "BTagRuleList",
  defaultValue = "new BTagRuleList()"
)
public class BSmartTagDictionary
  extends BTagDictionary
  implements SmartTagDictionary
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.tagdictionary.BSmartTagDictionary(3962753346)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "tagRules"

  /**
   * Slot for the {@code tagRules} property.
   * The collection of tag rules defined for this dictionary.
   * @see #getTagRules
   * @see #setTagRules
   */
  public static final Property tagRules = newProperty(0, new BTagRuleList(), null);

  /**
   * Get the {@code tagRules} property.
   * The collection of tag rules defined for this dictionary.
   * @see #tagRules
   */
  public BTagRuleList getTagRules() { return (BTagRuleList)get(tagRules); }

  /**
   * Set the {@code tagRules} property.
   * The collection of tag rules defined for this dictionary.
   * @see #tagRules
   */
  public void setTagRules(BTagRuleList v) { set(tagRules, v, null); }

  //endregion Property "tagRules"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSmartTagDictionary.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Default constructor
   */
  public BSmartTagDictionary()
  {
  }

  /**
   * Constructor that initializes the {@link #namespace} property.
   *
   * @param namespace initial value of the namespace property
   */
  public BSmartTagDictionary(String namespace)
  {
    super(namespace);
  }

///////////////////////////////////////////////////////////
// Rules
///////////////////////////////////////////////////////////

  /**
   * Get an iterator of all of the rules that contain the specified tag
   * id.
   *
   * @param id id of the tag to search for
   * @return an iterator of the rules that include the specified tag id
   */
  public Iterator<TagRule> getRulesForTagId(Id id)
  {
    Predicate<TagRule> test = rule -> rule.containsTagId(id);
    return new FilteredIterator<>(test,
      SlotCursorIterator.iterator(getTagRules().getProperties(), TagRule.class));
  }

  /**
   * Get an iterator of all of the rules that contain the specified relation id.
   *
   * @param id id of the relation to search for
   * @return an iterator of the rules that include the specified relation id
   */
  public Iterator<TagRule> getRulesForRelationId(Id id)
  {
    Predicate<TagRule> test = rule -> rule.containsRelationId(id);
    return new FilteredIterator<>(test,
        SlotCursorIterator.iterator(getTagRules().getProperties(), TagRule.class));
  }

///////////////////////////////////////////////////////////
// Tags
///////////////////////////////////////////////////////////

  /**
   * Get an implied tag with the specified id for the specified entity, if one is implied. If
   * {@link Optional#empty()} is returned, it may mean a {@link TagInfo} is not implied on the
   * entity or that {@link TagInfo#getTag(Entity)} of an implied TagInfo returns null.
   * <p>
   * The tag dictionary must be valid (see {@link BStatus#isValid()})
   *
   * @param id The id of the tag to retrieve
   * @param entity The entity to evaluate.
   * @return an implied tag or {@link Optional#empty()} if the tag is not implied on the entity
   */
  @Override
  public Optional<Tag> getImpliedTag(Id id, Entity entity)
  {
    return getImpliedTagInfo(id, entity).map(tagInfo -> tagInfo.getTag(entity));
  }

  /**
   * Get a tag info for the specified entity if a tag with the specified ID is implied by this smart
   * tag dictionary on the entity. The tag info can later be used to make a tag for the entity.
   * <p>
   * The tag dictionary must be valid (see {@link BStatus#isValid()})
   *
   * @param id id of the tag to retrieve
   * @param entity entity to evaluate
   * @return a tag info if it is implied for the specified entity; optional.empty otherwise
   *
   * @since Niagara 4.3
   */
  @Override
  public Optional<TagInfo> getImpliedTagInfo(Id id, Entity entity)
  {
    if (getStatus().isValid())
    {
      for (TagRule rule : getTagRules())
      {
        Optional<TagInfo> tagInfo = rule.getTag(id);
        if (tagInfo.isPresent() && evaluateRuleOnEntity(rule, entity))
        {
          return tagInfo;
        }
      }
    }

    return Optional.empty();
  }

  /**
   * Add all of the tags implied by this dictionary to the specified collection of tags. Tags are
   * not added if {@link TagInfo#getTag(Entity)} of an implied TagInfo returns null.
   *
   * @param entity The target entity for the tags.
   * @param tags The collection of tags to populate.
   */
  @Override
  public void addAllImpliedTags(Entity entity, Collection<Tag> tags)
  {
    // imply tags from tag rules
    SlotCursor<Property> c = getTagRules().getProperties();
    while (c.next(TagRule.class))
    {
      TagRule rule = (TagRule)c.get();
      if (evaluateRuleOnEntity(rule, entity))
      {
        for (TagInfo tagInfo : rule.getTags())
        {
          Tag tag = tagInfo.getTag(entity);
          if (tag != null)
            tags.add(tag);
        }

        for (TagGroupInfo tagGroupInfo : rule.getTagGroups())
          tagGroupInfo.addAllImpliedTags(entity, tags);
      }
    }
  }

///////////////////////////////////////////////////////////
// Relations
///////////////////////////////////////////////////////////

  /**
   * Get the first or only relation with the specified id with the specified entity as the source.
   * If {@link Optional#empty()} is returned, it may mean a {@link RelationInfo} is not implied on
   * the entity or that {@link RelationInfo#getRelation(Entity)} of an implied RelationInfo returns
   * Optional.empty().
   *
   * @param id The id of the relation.
   * @param source The source entity for the relation.
   * @return a single relation with the specified id or {@link Optional#empty()} if the relation
   * is not implied for the specified source.
   */
  @Override
  public Optional<Relation> getImpliedRelation(Id id, Entity source)
  {
    Iterator<TagRule> rules = getRulesForRelationId(id);
    while (rules.hasNext())
    {
      TagRule rule = rules.next();
      if (evaluateRuleOnEntity(rule, source))
      {
        Iterator<RelationInfo> relations = rule.getRelations(id);
        if (relations.hasNext())
        {
          Optional<Relation> rel = relations.next().getRelation(source);
          if (rel.isPresent())
            return rel;
        }
      }
    }

    return Optional.empty();
  }

  /**
   * Add all implied relations for the specified source entity to the collection of relations.
   * Relations are not added if {@link RelationInfo#getRelation(Entity)} of an implied RelationInfo
   * returns {@link Optional#empty()}.
   *
   * @param source The entity that is the source of the relation.
   * @param relations The collection of relations to populate.
   */
  @Override
  public void addAllImpliedRelations(Entity source, Collection<Relation> relations)
  {
    for (TagRule rule : getTagRules())
    {
      if (evaluateRuleOnEntity(rule, source))
      {
        for (RelationInfo rel : rule.getRelations())
          rel.addRelations(source, relations);

        for (TagGroupInfo tagGroupInfo : rule.getTagGroups())
        {
          if (tagGroupInfo instanceof Entity)
          {
            relations.add(new BasicRelation(BNiagaraTagDictionary.TAG_GROUP_RELATION, (Entity)tagGroupInfo, Relation.OUTBOUND));
          }
        }
      }
    }
  }

  /**
   * Add all implied relations with the specified id for the specified source entity to the
   * collection of relations. Relations are not added if {@link RelationInfo#getRelation(Entity)} of
   * an implied RelationInfo returns {@link Optional#empty()}.
   *
   * @param id The id of the relation.
   * @param source The entity that is the source of the relation.
   * @param relations The collection of relations to populate.
   */
  @Override
  public void addImpliedRelations(Id id, Entity source, Collection<Relation> relations)
  {
    Iterator<TagRule> rules = getRulesForRelationId(id);
    while (rules.hasNext())
    {
      TagRule rule = rules.next();
      if (evaluateRuleOnEntity(rule, source))
      {
        Iterator<RelationInfo> rr = rule.getRelations(id);
        while (rr.hasNext())
          rr.next().addRelations(source, relations);
      }
    }
  }

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    // Only create the cache on the server side.
    if (isRunning())
    {
      Map<String, Optional<SmartTagDictionary>> cache = null;
      TagDictionaryService tagDictionaryService = getTagDictionaryService();
      if (tagDictionaryService instanceof BTagDictionaryService)
      {
        cache = ((BTagDictionaryService)tagDictionaryService).getSmartTagDictionaryCache();
      }

      if (cache != null)
      {
        switch (x)
        {
        case Fw.STARTED:
          // Add ourselves to the Cache in BTagDictionaryService
          if (logger.isLoggable(Level.FINE))
          {
            logger.fine("Adding SmartTagDictionary " + toString() + " (" + getNamespace() + ") to cache");
          }
          cache.put(getNamespace(), Optional.of(this));
          break;

        case Fw.STOPPED:
          // Remove ourselves from the Cache in BTagDictionaryService
          if (logger.isLoggable(Level.FINE))
            logger.fine(String.format("Removing SmartTagDictionary %s (%s) from cache", toString(), getNamespaceFromCache(cache)));

          cache.remove(getNamespace());
          break;

        case Fw.CHANGED:
          if (isRunning() && (((Property)a).getName()).equals("namespace"))
          {
            //System.out.printf("Searching for %s in map: %s%n", this, BTagDictionaryService.getSmartTagDictionaryCache());
            String oldNs = getNamespaceFromCache(cache);
            if (logger.isLoggable(Level.FINE))
            {
              logger.fine("Updating SmartTagDictionary " + toString() + " (" + oldNs + ") to namespace " + getNamespace());
            }
            cache.remove(oldNs);
            cache.put(getNamespace(), Optional.of(this));
          }
          break;
        }
      }
    }

    return super.fw(x, a, b, c, d);
  }

  /**
   * @return namespace
   * @since 4.2
   */
  private String getNamespaceFromCache(Map<String, Optional<SmartTagDictionary>> cache)
  {
    Objects.requireNonNull(cache, "cache");

    // Get the entry whose value matches smart tag dictionary.
    Optional<Entry<String, Optional<SmartTagDictionary>>> entry = cache.entrySet().stream()
      .filter(e -> e.getValue().get() == this)
      .findFirst();

    // If found, return the namespace the dictionary was cached with or an empty string if this
    // dictionary is not in the cache.  This can occur if the station is stopped before it has
    // started fully.
    return entry.map(Entry::getKey).orElse("");
  }
}
