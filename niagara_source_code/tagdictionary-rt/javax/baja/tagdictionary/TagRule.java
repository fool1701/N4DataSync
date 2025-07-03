/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.RelationInfo;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * A TagRule defines a rule that determines when an {@link Entity} implies a tag or set of tags.
 *
 * @author John Sublett
 * @creation 2/18/14
 * @since Niagara 4.0
 */
public interface TagRule
{
////////////////////////////////////////////////////////////////
// Condition
////////////////////////////////////////////////////////////////

  /**
   * Get the predicate for this rule
   *
   * @return the predicate to evaluate for this rule
   */
  Predicate<Entity> getRuleCondition();

  /**
   * A convenience method for getRuleCondition().test(entity) that is
   * overridden by scoped tag rules.
   *
   * @param entity entity against which to evaluate the tag rule
   * @return {@code true} if the TagRule applies to the entity; {@code false} otherwise
   * @since Niagara 4.3
   */
  default boolean evaluate(Entity entity)
  {
    return getRuleCondition().test(entity);
  }

///////////////////////////////////////////////////////////
// Tags
///////////////////////////////////////////////////////////

  /**
   * Test whether this rule includes an implied tag with the specified id.
   *
   * @param id tag id to search for
   * @return {@code true} if the rule contains a tag with the specified id;
   * {@code false} otherwise.
   */
  boolean containsTagId(Id id);

  /**
   * Get the tag in this rule for the specified id.
   *
   * @param id id of the implied tag
   * @return an {@code Optional} that contains the {@code TagInfo} for the
   * specified id if the rule includes one; an empty {@code Optional} otherwise
   */
  Optional<TagInfo> getTag(Id id);

  /**
   * Get the tags associated with this rule.
   *
   * @return tags associated with this rule
   */
  Collection<TagInfo> getTags();

  /**
   * Get the tag groups associated with this rule.
   *
   * @return tag groups associated with this rule
   */
  Collection<TagGroupInfo> getTagGroups();

///////////////////////////////////////////////////////////
// Relations
///////////////////////////////////////////////////////////

  /**
   * Test whether this rule includes an implied relation with the specified id.
   *
   * @param id relation id to search for
   * @return {@code true} if the rule contains a relation with the specified id;
   * {@code false} otherwise.
   */
  boolean containsRelationId(Id id);

  /**
   * Get an iterator of the relations in this rule with the specified id.
   *
   * @param id relation id to search for
   * @return an iterator of the relations in this rule with the specified id
   */
  Iterator<RelationInfo> getRelations(Id id);

  /**
   * Get the relations that are associated with this rule.
   *
   * @return relations associated with this rule
   */
  Collection<RelationInfo> getRelations();
}
