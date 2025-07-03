/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * A SmartTagDictionary is a TagDictionary that can provide additional tags for an Entity based on
 * retrievable information about the Entity.  Common information used to determine additional tags
 * are the Entity's existing tags or the Entity's type.
 *
 * @author John Sublett
 * @creation 2/13/14
 * @since Niagara 4.0
 */
public interface SmartTagDictionary
  extends TagDictionary
{
///////////////////////////////////////////////////////////
// Implied Tags
///////////////////////////////////////////////////////////

  /**
   * Get an implied tag with the specified id for the specified entity, if one is implied. If
   * {@link Optional#empty()} is returned, it may mean a {@link TagInfo} is not implied on the
   * entity or that {@link TagInfo#getTag(Entity)} of an implied TagInfo returns null.
   *
   * @param id id of the tag to retrieve
   * @param entity entity to evaluate
   * @return an implied tag or {@link Optional#empty()} if the tag is not implied on the entity
   */
  Optional<Tag> getImpliedTag(Id id, Entity entity);

  /**
   * Get a tag info for the specified entity if a tag with the specified ID is implied by this smart
   * tag dictionary on the entity. The tag info can later be used to make a tag for the entity.
   *
   * @param id id of the tag to retrieve
   * @param entity entity to evaluate
   * @return a tag info or {@link Optional#empty()} if the tag is not implied on the entity
   *
   * @since Niagara 4.3
   */
  Optional<TagInfo> getImpliedTagInfo(Id id, Entity entity);

  /**
   * Add all of the tags implied by this dictionary to the specified collection of tags. Tags are
   * not added if {@link TagInfo#getTag(Entity)} of an implied TagInfo returns null.
   *
   * @param entity target entity for the tags.
   * @param tags collection of tags to populate.
   */
  void addAllImpliedTags(Entity entity, Collection<Tag> tags);

  /**
   * Get the collection of all implied tags for the specified entity within this dictionary. The
   * collection will not include tags where {@link TagInfo#getTag(Entity)} of an implied TagInfo
   * returns null.
   *
   * @param entity The entity to evaluate.
   * @return a collection of tags implied on the entity
   */
  default Collection<Tag> getAllImpliedTags(Entity entity)
  {
    List<Tag> result = new ArrayList<>();
    addAllImpliedTags(entity, result);
    return result;
  }

///////////////////////////////////////////////////////////
// Implied Relations
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
  Optional<Relation> getImpliedRelation(Id id, Entity source);

  /**
   * Add all implied relations for the specified source entity to the collection of relations.
   * Relations are not added if {@link RelationInfo#getRelation(Entity)} of an implied RelationInfo
   * returns {@link Optional#empty()}.
   *
   * @param source The entity that is the source of the relation.
   * @param relations The collection of relations to populate.
   */
  void addAllImpliedRelations(Entity source, Collection<Relation> relations);

  /**
   * Get the collection of relations implied on the specified entity. The collection will not
   * include relations where {@link RelationInfo#getRelation(Entity)} of an implied RelationInfo
   * returns {@link Optional#empty()}.
   *
   * @param source The source entity of the relations.
   * @return a collection of relations implied on the source
   */
  default Collection<Relation> getAllImpliedRelations(Entity source)
  {
    ArrayList<Relation> relations = new ArrayList<>();
    addAllImpliedRelations(source, relations);
    return relations;
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
  void addImpliedRelations(Id id, Entity source, Collection<Relation> relations);

  /**
   * Get the collection of relations with the specified id implied by the specified entity. The
   * collection will not include relations where {@link RelationInfo#getRelation(Entity)} of an
   * implied RelationInfo returns {@link Optional#empty()}.
   *
   * @param id The id of the relation.
   * @param source The entity that is the source of the relation.
   * @return a collection of relations with the specified id implied on the source
   */
  default Collection<Relation> getImpliedRelations(Id id, Entity source)
  {
    ArrayList<Relation> relations = new ArrayList<>();
    addImpliedRelations(id, source, relations);
    return relations;
  }
}
