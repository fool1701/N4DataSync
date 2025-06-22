/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * TagDictionaryService
 *
 * @author John Sublett
 * @creation 2/17/14
 * @since Niagara 4.0
 */
public interface TagDictionaryService
{
  /**
   * Get the default namespace for this service.  This is typically the namespace of a dictionary
   * installed in the service.  If a default namespace is defined, global tags that appear in tag
   * queries are assumed to be tags in the dictionary that owns the default namespace.
   *
   * @return the default namespace or null if no default namespace is defined.
   */
  String getDefaultNamespace();

  /**
   * Set the default namespace for this service.  See {@link #getDefaultNamespace()}.
   */
  void setDefaultNamespace(String defaultNamespace);

  /**
   * Get the TagDictionary for the specified namespace.
   *
   * @param namespace The string id of the namespace defined by the target dictionary.
   * @return the TagDictionary with the specified namespace or {@link Optional#empty()} if the
   * dictionary for the specified namespace is not installed
   */
  Optional<TagDictionary> getTagDictionary(String namespace);

  /**
   * Get all installed TagDictionaries.
   *
   * @return a collection of all installed TagDictionaries.
   */
  Collection<TagDictionary> getTagDictionaries();

  /**
   * Get the SmartTagDictionary for the specified namespace.
   *
   * @param namespace The string id of the namespace defined by the target dictionary.
   * @return the SmartTagDictionary with the specified namespace or {@link Optional#empty()} if a
   * SmartTagDictionary for the specified namespace is not installed
   */
  Optional<SmartTagDictionary> getSmartTagDictionary(String namespace);

  /**
   * Get all installed SmartTagDictionaries.
   *
   * @return a collection of all installed SmartTagDictionaries
   */
  Collection<SmartTagDictionary> getSmartTagDictionaries();

///////////////////////////////////////////////////////////
// Tags
///////////////////////////////////////////////////////////

  /**
   * Get an implied tag for the specified id on the specified entity. {@link Optional#empty()} is
   * returned if no smart tag dictionaries imply a {@link TagInfo} on the entity; see {@link
   * SmartTagDictionary#getImpliedTagInfo(Id, Entity)}. {@link Optional#empty()} is also returned if
   * a smart tag dictionaries implies a TagInfo on the entity but that TagInfo does not result in a
   * tag; see {@link TagInfo#getTag(Entity)}.
   *
   * @param id id of the implied tag
   * @param entity entity to evaluate for the implied tag
   * @return an implied tag or {@link Optional#empty()} if the tag is not implied on the specified
   * entity
   */
  default Optional<Tag> getImpliedTag(Id id, Entity entity)
  {
    for (SmartTagDictionary smartDictionary : getSmartTagDictionaries())
    {
      if (smartDictionary.getEnabled())
      {
        Optional<TagInfo> tagInfo = smartDictionary.getImpliedTagInfo(id, entity);
        if (tagInfo.isPresent())
          return Optional.ofNullable(tagInfo.get().getTag(entity));
      }
    }

    return Optional.empty();
  }

  /**
   * Get all tags implied on the specified entity. The resulting collection is populated by
   * iterating through the installed SmartTagDictionaries and adding the implied tags from each. See
   * {@link SmartTagDictionary#addAllImpliedTags(Entity, Collection)} for further details.
   *
   * @param entity The entity to evaluate for implied tags.
   * @return a collection of tags implied on the entity
   */
  default Collection<Tag> getImpliedTags(Entity entity)
  {
    List<Tag> result = new ArrayList<>();
    for (SmartTagDictionary smartDictionary : getSmartTagDictionaries())
    {
      if (smartDictionary.getEnabled())
      {
        smartDictionary.addAllImpliedTags(entity, result);
      }
    }
    return result;
  }

///////////////////////////////////////////////////////////
// Relations
///////////////////////////////////////////////////////////

  /**
   * Get the first or only relation with the specified id with the specified entity as the source.
   * See {@link SmartTagDictionary#getImpliedRelation(Id, Entity)} for further details.
   *
   * @param id The id of the relation.
   * @param source The source entity for the relation.
   * @return a single relation with the specified id or {@link Optional#empty()} if the relation
   * is not implied on the specified entity
   */
  Optional<Relation> getImpliedRelation(Id id, Entity source);

  /**
   * Add all implied relations for the specified source entity to the collection of relations. See
   * {@link SmartTagDictionary#addAllImpliedRelations(Entity, Collection)} for further details.
   *
   * @param source The entity that is the source of the relation.
   * @param relations The collection of relations to populate.
   */
  void addAllImpliedRelations(Entity source, Collection<Relation> relations);

  /**
   * Get the collection of relations implied for the specified source entity.  See {@link
   * #addAllImpliedRelations(Entity, Collection)}.
   *
   * @param source The source entity of the relations.
   * @return a collection of relations where the specified entity is the source
   */
  default Collection<Relation> getAllImpliedRelations(Entity source)
  {
    ArrayList<Relation> relations = new ArrayList<>();
    addAllImpliedRelations(source, relations);
    return relations;
  }

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
  void addImpliedRelations(Id id, Entity source, Collection<Relation> relations);

  /**
   * Get the collection of relations with the specified id implied by the specified entity. See
   * {@link #addImpliedRelations(Id, Entity, Collection)}.
   *
   * @param id The id of the relations.
   * @param entity The source entity for the relations.
   * @return a collection of relations with the specified id where the specified entity is the
   * source
   */
  default Collection<Relation> getImpliedRelations(Id id, Entity entity)
  {
    ArrayList<Relation> relations = new ArrayList<>();
    addImpliedRelations(id, entity, relations);
    return relations;
  }

  /**
   * Call back when a tag with the given Id has been added to the given target entity.
   *
   * @param target The target entity.
   * @param tagId The Id of the tag that has been added.
   */
  void tagAdded(Entity target, Id tagId);

  /**
   * Call back when a tag with the given Id has been removed from the given target entity.
   *
   * @param target The target entity.
   * @param tagId The Id of the tag that has been added.
   */
  void tagRemoved(Entity target, Id tagId);

///////////////////////////////////////////////////////////
// DataPolicy
///////////////////////////////////////////////////////////

  /**
   * Get the DataPolicy of the given tag ID.
   *
   * @param tagId The source entity of the relations.
   * @return Returns a {@code Optional<DataPolicy>}.
   */
  Optional<DataPolicy> getDataPolicyForTag(Id tagId);
}
