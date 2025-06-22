/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.Optional;
import javax.baja.data.BIDataValue;
import javax.baja.data.TypeMismatchException;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Type;

/**
 * TagInfo is a definition of a Tag in a TagDictionary.
 *
 * @author John Sublett
 * @creation 2/13/14
 * @since Niagara 4.0
 */
public interface TagInfo
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
  default Id getTagId()
  {
    Optional<TagDictionary> d = getDictionary();
    if (d.isPresent())
      return Id.newId(d.get().getNamespace(), getName());
    else
      return Id.newId(Id.NO_DICT, getName());
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
   * Get the default value of the tag.  This also restricts the data type for the tag value.
   * See getTagType().
   */
  BIDataValue getDefaultValue();

  /**
   * Get a Tag from this TagInfo for the specified entity.  This method allows the
   * TagInfo to be a "smart" tag that can change its value based on the entity.
   *
   * @param entity The entity that the resulting Tag is associated with.
   * @return Returns a Tag for the specified entity.
   */
  default Tag getTag(Entity entity)
  {
    return makeTag();
  }

  /**
   * Get the type of the tag value.  This is equivalent to
   * getDefaultValue().getType().getTypeInfo().
   *
   * @return Returns the TypeInfo for the tag value type.
   */
  default TypeInfo getTagType()
  {
    return getDefaultValue().getType().getTypeInfo();
  }

  /**
   * Make a Tag from this TagInfo.
   *
   * @return Returns the Tag represented by this TagInfo with the default value.
   */
  default Tag makeTag()
  {
    Optional<TagDictionary> td = getDictionary();
    if (td.isPresent())
      return new Tag(getTagId(), getDefaultValue());
    else
      throw new IllegalStateException("TagInfo must be in a dictionary.");
  }

  /**
   * Set the tag on the specified target.  This does not enforce any validity rules.  It just
   * sets the tag on the specified Taggable object with the default value.
   *
   * @param target The object to set the marker tag on.
   */
  default void setTagOn(Taggable target)
  {
    target.tags().set(getTagId(), getDefaultValue());
  }

  /**
   * Set the tag on the specified target.  This does not enforce target type validity rules.  It just
   * sets the tag on the specified Taggable object.  The value type must match the tag type or a
   * javax.baja.data.TypeMismatchException is thrown.
   *
   * @param target The object to set the market tag on.
   */
  default void setTagOn(Taggable target, BIDataValue value)
  {
    if (getDefaultValue().getType() == value.getType())
      target.tags().set(getTagId(), value);
    else
      throw new TypeMismatchException(getDefaultValue().getType(), value.getType());
  }
}
