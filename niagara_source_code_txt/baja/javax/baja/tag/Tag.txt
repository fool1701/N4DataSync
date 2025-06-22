/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import static javax.baja.tag.Id.newId;

import java.util.Objects;
import javax.baja.data.BIDataValue;
import javax.baja.sys.*;

/**
 * A Tag is an immutable ({@link Id}, {@link BIDataValue}) pair.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 * @see Taggable
 * @see Tags
 */
public final class Tag
{
  private final Id id;
  private final BIDataValue value;

  /**
   * Equivalent to <pre>new Tag(qname, BMarker.MARKER)</pre>
   */
  public static Tag newTag(String qname)
  {
    return new Tag(qname, BMarker.MARKER);
  }

  /**
   * Equivalent to <pre>new Tag(qname, BString.make(value)</pre>
   */
  public static Tag newTag(String qname, String value)
  {
    return new Tag(qname, BString.make(value));
  }

  /**
   * Equivalent to <pre>new Tag(qname, BInteger.make(value)</pre>
   */
  public static Tag newTag(String qname, int value)
  {
    return new Tag(qname, BInteger.make(value));
  }

  /**
   * Equivalent to <pre>new Tag(qname, BFloat.make(value)</pre>
   */
  public static Tag newTag(String qname, float value)
  {
    return new Tag(qname, BFloat.make(value));
  }

  /**
   * Equivalent to <pre>new Tag(qname, BLong.make(value)</pre>
   */
  public static Tag newTag(String qname, long value)
  {
    return new Tag(qname, BLong.make(value));
  }

  /**
   * Equivalent to <pre>new Tag(qname, BDouble.make(value)</pre>
   */
  public static Tag newTag(String qname, double value)
  {
    return new Tag(qname, BDouble.make(value));
  }

  /**
   * Equivalent to <pre>new Tag(qname, BBoolean.make(value)</pre>
   */
  public static Tag newTag(String qname, boolean value)
  {
    return new Tag(qname, BBoolean.make(value));
  }

  /**
   * Equivalent to <pre>new Tag(Id.newId(qname), value)</pre>
   * @see Id#newId
   */
  public Tag(String qname, BIDataValue value)
  {
    this(newId(qname), value);
  }

  /**
   * Create a new tag with the given id and value.
   *
   * @param id the id of the tag. May not by {@code null}.
   * @param value the value of the tag. May not be {@code null}.
   */
  public Tag(Id id, BIDataValue value)
  {
    Objects.requireNonNull(id);
    Objects.requireNonNull(value);
    this.id = id;
    this.value = value;
  }

  /**
   * Get the tag's id.
   *
   * @return the tag's id
   */
  public Id getId()
  {
    return id;
  }

  /**
   * Get the tag's value.
   *
   * @return the tag's value
   */
  public BIDataValue getValue()
  {
    return value;
  }

  /**
   * Two tags are equal <i>iff</i> they have the same id and their values are equal.
   *
   * @param o the object to test for equality
   * @return true if this tag is equal to the given object
   */
  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }

    Tag tag = (Tag)o;

    if (!id.equals(tag.id))
    {
      return false;
    }
    if (!value.equals(tag.value))
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int result = id.hashCode();
    result = 31 * result + value.hashCode();
    return result;
  }
}
