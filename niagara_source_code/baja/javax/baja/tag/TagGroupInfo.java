/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import javax.baja.sys.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * TagGroupInfo defines a group of tags within the same namespace.
 *
 * @author John Sublett
 * @creation 2/21/14
 * @since Niagara 4.0
 */
public interface TagGroupInfo
{
  /**
   * Get the dictionary that includes this tag group.
   *
   * @return Returns an optional that contains the TagDictionary for this group if
   * the group is part of a tag dictionary.
   */
  Optional<TagDictionary> getDictionary();

  /**
   * Get the name of the group.
   *
   * @return Returns the name of the group.  This is the simple name not including the namespace.
   */
  String getName();

  /**
   * Get the Id for this tag.  This constructs the id from the dictionary namespace, if
   * available, and the tag name.
   *
   * @return Returns a tag id for this tag.
   */
  default Id getGroupId()
  {
    if (getName() == null)
      throw new IllegalStateException("TagGroup is not mounted.");

    Optional<TagDictionary> d = getDictionary();
    return Id.newId(d.isPresent() ? d.get().getNamespace() : Id.NO_DICT, getName());
  }

  /**
   * Test whether this tag and ideal match for the specified entity.  Tag validity
   * is not enforced by the API.  Validity provides a guideline for tagging tools.
   *
   * @param type The Type to test for the ideal match for this tag.
   * @return Returns true if the tag is an ideal match for the specified type, false otherwise.
   */
  default boolean isIdealFor(Type type)
  {
    return true;
  }

  /**
   * Test whether this tag is valid for the specified entity.  Tag validity
   * is not enforced by the API.  Validity provides a guideline for tagging tools.
   *
   * @param entity The entity to test for validity for this tag.
   * @return Returns true if the tag is valid for the specified entity, false otherwise.
   */
  default boolean isValidFor(Entity entity)
  {
    return true;
  }

  /**
   * Get the tags in this group.
   *
   * @return The collection of tags in the group.
   */
  Iterator<TagInfo> getTags();

  /**
   * Get the DataPolicy associated with this tagGroup.
   *
   * @return Returns an optional that contains the DataPolicy for this tagGroup if
   * it exist.
   */
  Optional<DataPolicy> getDataPolicy();

  /**
   * Get the TagGroupInfo associated with the given Entity.
   *
   * @return Returns an optional that contains the tagGroup if
   * it exist.
   */
  static Optional<TagGroupInfo> getTagGroup(Entity entity)
  {
    Optional<Relation> optRelation = entity.relations().get(Id.newId("n:tagGroup"));
    if (optRelation.isPresent())
    {
      Entity endpoint = optRelation.get().getEndpoint();
      if (endpoint instanceof TagGroupInfo)
      {
        return Optional.of((TagGroupInfo)endpoint);
      }
    }
    return Optional.empty();
  }

  /**
   * Add all tags from this tag group to the supplied tags collection to be implied on the entity.
   * Also adds a tag for the tag group itself depending on the implementation of
   * {@link #getNameTagInfo() getNameTagInfo}.
   *
   * @param entity The entity that contains tags to imply.
   * @param tags The collection of tags .
   */
  default void addAllImpliedTags(Entity entity, Collection<Tag> tags)
  {
    if (getDictionary().isPresent())
    {
      // endpoint is a tag group within a valid dictionary
      // add a marker tag with the endpoint's group ID
      getNameTagInfo().map(tagInfo -> tagInfo.getTag(entity)).ifPresent(tags::add);

      // add the tags within the tag group
      getTags().forEachRemaining(tagInfo ->
      {
        Tag tag = tagInfo.getTag(entity);
        if (tag != null)
          tags.add(tag);
      });
    }
  }

  /**
   * Get a TagInfo object that represents the tag group itself. The default implementation returns
   * an empty Optional. This prevents addAllImpliedTags from adding the TagGroupInfo's name as an
   * implied tag. Classes that implement this interface should override this method and return
   * TagInfo. The TagInfo should be used to generate a tag find instances of the tag group in a
   * station. The preferred implementation is to return a TagInfo that will result in a marker tag
   * with the same ID as the tag group's ID.
   *
   * @return Returns an optional that contains the tagGroup's name, if it exists.
   */
  default Optional<TagInfo> getNameTagInfo()
  {
    return Optional.empty();
  }
}
