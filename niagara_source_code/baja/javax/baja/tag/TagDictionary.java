/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import javax.baja.sys.Context;
import java.util.Collection;
import java.util.Iterator;

/**
 * A TagDictionary is a collection of tags and defined relationships.  A TagDictionary
 * must be installed in a station in order to have all of its features enabled.
 *
 * A TagDictionary defines:
 * 1. A set of tags within a single namespace.
 * 2. Relationships between tags within a dictionary.
 * 3. Implicit tags for any Entity based on criteria determined by the
 *    dictionary.
 *
 * @author John Sublett
 * @creation 02/13/2014
 * @since Niagara 4.0
 */
public interface TagDictionary
{
  /**
   * Is this TagDictionary enabled for use in tag-aware applications?
   *
   * @return Returns true if the dictionary is enabled, false otherwise.
   */
  boolean getEnabled();

  /**
   * Get the namespace for all tags in the dictionary.
   *
   * @return Returns the namespace string identifier.
   */
  String getNamespace();

  /**
   * Get the display name for the dictionary.
   */
  String getDisplayName(Context cx);

///////////////////////////////////////////////////////////
// Tags
///////////////////////////////////////////////////////////

  /**
   * Get the tags in this dictionary.  Each tag is defined by a TagInfo that
   * that includes the tag id and any additional tag metadata.
   *
   * @return Returns an iterator of tags in this TagDictionary.
   */
  Iterator<TagInfo> getTags();

  /**
   * Get the tag groups in this dictionary.  Each tag group is defined by a TagGroupInfo
   * that include the group id, the collection of tags, and any additional tag metadata.
   *
   * @return Returns an iterator of the groups in this TagDictionary.
   */
  Iterator<TagGroupInfo> getTagGroups();

///////////////////////////////////////////////////////////
// Valid Tags
///////////////////////////////////////////////////////////

  /**
   * Get the tags from this dictionary that are valid for the
   * specified entity.
   *
   * @param entity The entity to evaluate for valid tags.
   * @return Returns a collection of the valid tags from this dictionary for the entity.
   */
  Collection<TagInfo> getValidTags(Entity entity);

  /**
   * Get the tag groups from this dictionary that are valid for the
   * specified entity.
   *
   * @param entity The entity to evaluate for valid tag groups.
   * @return Returns a collection of the valid tag groups from this
   *   dictionary for the entity.
   */
  Collection<TagGroupInfo> getValidTagGroups(Entity entity);

///////////////////////////////////////////////////////////
// Relations
///////////////////////////////////////////////////////////

  /**
   * Get the relations defined by this dictionary.
   */
  Iterator<RelationInfo> getRelations();
}