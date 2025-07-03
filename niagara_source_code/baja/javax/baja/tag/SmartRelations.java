/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

/**
 * SmartRelations is a set of relations made of up the set of direct relations
 * on an Entity plus the set of smart relations added through a smart tagging
 * mechanism.  Most commonly, smart relations are added to an Entity by a
 * SmartTagDictionary.
 *
 * Implied relations cannot be modified or removed like normal relations can.
 * Any method that would remove or modify an implied relation is ignored for a
 * a set of smart relations.
 *
 * @author John Sublett
 * @creation 2/28/14
 * @since Niagara 4.0
 */
public interface SmartRelations
  extends Relations
{
  /**
   * Get the subset of the tags that are implied tags.  Implied tags are those
   * tags that are not defined directly on the Taggable object.
   *
   * @return Returns the subset of tags that are not defined directly
   * on the Taggable object.
   */
  Relations getImpliedRelations();

  /**
   * Get the subset of the tags that are defined directly on the Taggable
   * object.
   *
   * @return Returns the set of direct tags.
   */
  Relations getDirectRelations();
}
