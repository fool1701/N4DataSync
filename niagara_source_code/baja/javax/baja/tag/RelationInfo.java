/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.Collection;
import java.util.Optional;

/**
 * RelationInfo is a definition of a Relation in a TagDictionary.
 *
 * @author John Sublett
 * @creation 2/28/14
 * @since Niagara 4.0
 */
public interface RelationInfo
{
  /**
   * Get the dictionary that includes this tag.
   *
   * @return Returns an optional that contains the TagDictionary for this tag if
   * the tag is part of a tag dictionary.
   */
  Optional<TagDictionary> getDictionary();

  /**
   * Get the name of the tag.
   *
   * @return Returns the name of the tag.  This is the simple name not including the namespace.
   */
  String getName();

  /**
   * Get the Id for this tag.  This constructs the id from the dictionary namespace, if
   * available, and the tag name.
   *
   * @return Returns a tag id for this tag.
   */
  default Id getRelationId()
  {
    Optional<TagDictionary> d = getDictionary();
    if (d.isPresent())
      return Id.newId(d.get().getNamespace(), getName());
    else
      return Id.newId(Id.NO_DICT, getName());
  }

  /**
   * Get the first or only relation for the specified source entity.
   *
   * @param source The source entity for the relation.
   * @return a relation with the specified entity as the source or {@link Optional#empty()} if this
   * relation is not implied by the specified entity.
   */
  default Optional<Relation> getRelation(Entity source)
  {
    return Optional.empty();
  }

  /**
   * Add all of the relations for this entity to the specified collection.  The relations will not
   * be added to the Entity itself.  Default behavior is to add no relations to the collection.
   *
   * @param source The source entity for the relations.
   * @param relations A collection of relations that are implied by the specified entity.
   */
  default void addRelations(Entity source, Collection<Relation> relations)
  {
  }
}
