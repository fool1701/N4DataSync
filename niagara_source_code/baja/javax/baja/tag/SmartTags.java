/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

/**
 * SmartTags is a set of tags made of up the set of direct tags on a Taggable
 * object plus the set of smart tags added through a smart tagging mechanism.
 * Most commonly, smart tags are added to an Entity by a SmartTagDictionary.
 *
 * Implied tags cannot be modified or removed like normal tags can.  Any method
 * that would remove or modify an implied tag is ignored for a set of smart tags.
 *
 * @author John Sublett
 * @creation 2/16/14
 * @since Niagara 4.0
 */
public interface SmartTags
  extends Tags
{
  /**
   * Get the subset of the tags that are implied tags.  Implied tags are those
   * tags that are not defined directly on the Taggable object.
   *
   * @return Returns the subset of tags that are not defined directly
   * on the Taggable object.
   */
  Tags getImpliedTags();

  /**
   * Get the subset of the tags that are defined directly on the Taggable
   * object.
   *
   * @return Returns the set of direct tags.
   */
  Tags getDirectTags();
}
