/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import javax.baja.naming.SlotPath;

/**
 * An id is an immutable <code>(dictionary, name)</code> pair. An id uniquely identifies
 * {@link Tag}s and {@link Relation}s.
 * <p>
 * The dictionary name can be used to "namespace" or "bind" a particular tag or relation to a
 * tag dictionary. For example, the id <code>("d1", "temp")</code> is in dictionary {@code d1} and
 * is different than id <code>("d2", "temp")</code> which has the same name, but is in dictionary
 * {@code d2}.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 * @see Tag
 * @see Relation
 */
public final class Id
  implements Comparable<Id>
{
  /**
   * Creates a new id by parsing the given qname.
   *
   * @param qname the qualified name of the id. May not be {@code null}.
   * @return an Id with a qualified name eqivalent to qname
   * @throws IllegalArgumentException if the qname is not valid for an id.
   * @see #getQName()
   */
  public static Id newId(String qname)
  {
    Objects.requireNonNull(qname);
    final int sepIdx = qname.indexOf(DICT_SEP);
    if (sepIdx == 0)
    {
      throw new IllegalArgumentException(String.format("qname cannot start with '%c'. %s", DICT_SEP, qname));
    }
    String dictionary = NO_DICT;
    String name = qname;
    if (sepIdx > 0)
    {
      dictionary = qname.substring(0, sepIdx);
      name = qname.substring(sepIdx + 1);
    }
    return new Id(dictionary, name);
  }

  /**
   * Create an id with the given dictionary and name.
   *
   * @param dictionary the dictionary the id is in. May not be {@code null}.
   * @param name       the name of the id. May not be {@code null}.
   * @throws IllegalArgumentException if the dictionary or name is not valid for an id.
   * @see #verifyTagId(String, String)
   */
  public static Id newId(String dictionary, String name)
  {
    return new Id(dictionary, name);
  }

  private Id(String dictionary, String name)
  {
    verifyTagId(dictionary, name);
    this.dictionary = dictionaries.computeIfAbsent(dictionary, v -> dictionary);
    this.name = names.computeIfAbsent(name, v -> name);
  }

  /**
   * Checks if the given dictionary and tag meet the specification for a valid id.
   * If they do not, an exception is thrown.
   * <p>
   * A valid dictionary
   * <ol>
   * <li>Must not be {@code null}</li>
   * <li>Must not contain {@link #DICT_SEP}</li>
   * </ol>
   * <p>
   * A valid name
   * <ol>
   * <li>Must not be {@code null}</li>
   * <li>Must not be zero-length</li>
   * <li>Must not contain {@link #DICT_SEP}</li>
   * </ol>
   *
   * @param dictionary the dictionary to verify
   * @param name       the name to verify
   * @throws IllegalArgumentException if either the dictionary or name is not valid for an id
   */
  public static void verifyTagId(String dictionary, String name)
  {
    Objects.requireNonNull(dictionary);
    Objects.requireNonNull(name);
    if (dictionary.indexOf(DICT_SEP) >= 0)
    {
      throw new IllegalArgumentException(String.format("dictionary name cannot contain '%c'. %s ", DICT_SEP, dictionary));
    }
    if (name.isEmpty())
    {
      throw new IllegalArgumentException("tag name is empty.");
    }
    if (name.indexOf(DICT_SEP) >= 0)
    {
      throw new IllegalArgumentException(String.format("tag name cannot contain '%c'. %s", DICT_SEP, name));
    }
  }

  /**
   * Get the dictionary for the id.
   *
   * @return the dictionary for the id
   */
  public String getDictionary()
  {
    return dictionary;
  }

  /**
   * An id has a dictionary if it the dictionary is not {@link #NO_DICT}.
   *
   * @return true if the id has a dictionary
   */
  public boolean hasDictionary()
  {
    return !NO_DICT.equals(dictionary);
  }

  /**
   * Get the id name.
   *
   * @return the id name
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get the qualified name (qname) for this id.
   * <p>
   * If the id does not have a dictionary, the qname is simply the value
   * returned by {@link #getName()}
   * <p>
   * If the id is in a dictionary the qname is
   * "{@link #getDictionary()}" + "{@link #DICT_SEP}" + "{@link #getName()}"
   *
   * @return the qname of the id
   */
  public String getQName()
  {
    return hasDictionary() ? (dictionary + DICT_SEP + name) : name;
  }

  /**
   * @return {@link #getQName()}
   */
  @Override
  public String toString()
  {
    return getQName();
  }

  /**
   * Two Ids are equal <i>iff</i> they have the same dictionary and name.
   *
   * @param o the object to test for equality
   * @return true if this id equals the given object
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

    Id tagId = (Id)o;

    if (!name.equals(tagId.name))
    {
      return false;
    }

    return dictionary.equals(tagId.dictionary);
  }

  @Override
  public int hashCode()
  {
    int result = dictionary.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }

  /**
   * Compares this Id to another. Ids are ordered lexicographically first by dictionary, then by name.
   *
   * @param o the id to compare against. Must not be {@code null}.
   * @return negative if this id is less than {@code o}, zero if they are equal, positive
   * if this id is greater than the {@code o}.
   */
  @Override
  public int compareTo(Id o)
  {
    Objects.requireNonNull(o, "cannot compare null tag");
    if (equals(o))
    {
      return 0;
    }
    int result = dictionary.compareTo(o.dictionary);
    if (result == 0)
    {
      result = name.compareTo(o.name);
    }
    return result;
  }

  public static String idToFacetKey(Id id)
  {
    return SlotPath.escape(id.getQName());
  }

  public static String idToFacetKey(String id)
  {
    return SlotPath.escape(newId(id).getQName());
  }

  public static Id facetKeyToId(String facetKey)
  {
    return newId(SlotPath.unescape(facetKey));
  }

///////////////////////////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////////////////////////

  /**
   * The empty string indicates that an id is not in a dictionary.
   */
  public static final String NO_DICT = "";

  /**
   * When an id has a dictionary, the qname will separate the dictionary from the name with this
   * character.
   *
   * @see #getQName()
   */
  public static final char DICT_SEP = ':';

  private final String dictionary;
  private final String name;

  // dictionaries and names are String caches used to enhance Id performance when Id#newId is
  // called. These caches are used instead of String#intern and operates faster since a subset of
  // strings are tracked when inserting and retrieving these strings.
  private static final Map<String, String> dictionaries = new ConcurrentHashMap<>();
  private static final Map<String, String> names = new ConcurrentHashMap<>();
}
