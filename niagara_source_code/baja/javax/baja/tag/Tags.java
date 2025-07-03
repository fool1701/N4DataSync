/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import static java.util.stream.Collectors.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.baja.data.BIDataValue;

/**
 * Tags is used to store a collection of {@link Tag} objects. A Tags collection is semantically
 * equivalent to a mathematical <i>set</i>: it is an unordered collection of distinct {@link Tag}
 * objects.
 * <p>
 * The general behavioral contract of Tags is
 * <ul>
 *   <li>Depending on the concrete implementation, all methods that set or add tags
 *   to the collection may throw {@link IllegalArgumentException} if some property of the
 *   input arguments prevents the method from succeeding. This is most likely if you attempt
 *   to add a tag that is single-valued (see below). {@link #isMulti(Id)} can help prevent this.
 *   It could also happen if a Tag is "implied" or "frozen" by the concrete
 *   implementation of Tags.</li>
 *   <li>Because of the above property, none of the removal operations are guaranteed to
 *   succeed. However, they <strong><i>SHOULD</i></strong> never throw exceptions. Instead
 *   they should return {@code false} in that case.</li>
 *   <li>Because of the above properties it should be evident that a Tags collection
 *   might never be empty.</li>
 *   <li>This interface requires non-null arguments for all methods and must be enforced
 *   by the implementation.</li>
 * </ul>
 * <p>
 * A tag with a particular {@link Id id} can be either single-value or multi-value. A single-value
 * tag is guaranteed to have only one Tag with that id. A multi-value tag may contain multiple tags
 * with that id. The arity of an id is determined either implicitly by the object containing the
 * tags, or explicitly when a tag is first set or added to the collection.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public interface Tags extends Iterable<Tag>
{
  /**
   * @return {@code true} if the tags collection is empty; {@code false} otherwise.
   */
  default boolean isEmpty()
  {
    return !iterator().hasNext();
  }

  /**
   * Check if a tag with the given id exists in the collection.
   *
   * @param id the id to check for
   * @return {@code true} if there is at least one tag with the given id in the collection;
   * {@code false} otherwise.
   */
  default boolean contains(Id id)
  {
    return get(id).isPresent();
  }

  /**
   * Check if the given id is present in the collection <strong>and</strong> contains multi-valued
   * tags. This method <strong>MUST</strong> return false if {@link #contains(Id) contains(id)}
   * return false.
   * <p>
   * If this method returns {@code true} it is safe to use the addMulti methods.
   *
   * @param id the id to check
   * @return {@code true} if the id exists in the collection and supports multi-value tags;
   * {@code false} otherwise.
   */
  boolean isMulti(Id id);

  /**
   * Adds the given tag to the collection as a single-value tag. Any existing tags with the same
   * id are overwritten.
   *
   * @param tag the tag to set
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   */
  boolean set(Tag tag);

  /**
   * Equivalent to <pre>set(new Tag(id, value)}</pre>
   * @see #set(Tag)
   */
  default boolean set(Id id, BIDataValue value)
  {
    return set(new Tag(id, value));
  }

  /**
   * Creates tags with the given id for each value and adds them to collection as multi-value
   * tags. Any existing tags with the same id are overwritten.
   *
   * @param id the id for each tag
   * @param values the values for each new tag
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   */
  boolean setMulti(Id id, Collection<? extends BIDataValue> values);

  /**
   * Creates tags with the given id for each value and adds them to collection.
   *
   * @param id the id for each tag
   * @param values the values for each new tag
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   * @throws IllegalArgumentException if id is not multi-value
   */
  boolean addMulti(Id id, Collection<? extends BIDataValue> values);

  /**
   * Equivalent to <pre>addMulti(tag.getId(), Collections.singletonList(tag.getValue())</pre>
   * @see #addMulti(Id, Collection)
   */
  default boolean addMulti(Tag tag)
  {
    return addMulti(tag.getId(), Collections.singletonList(tag.getValue()));
  }

  /**
   * Equivalent to <pre>addMulti(new Tag(id, value))</pre>
   * @see #addMulti(Tag)
   */
  default boolean addMulti(Id id, BIDataValue value)
  {
    return addMulti(new Tag(id, value));
  }

  /**
   * Merges the tags from the given collection into this Tags collection.
   * <p>
   * Merge rules
   * <ol>
   *   <li>{@code addMulti} is used to add the tag
   *     <ol>
   *       <li>If {@link #isMulti(Id)} for a given tag's id returns {@code true}.</li>
   *       <li>If there are more than one tags in the input collection with the same id.</li>
   *     </ol>
   *   </li>
   *   <li>Otherwise the tag is inserted into the collection with {@link #set(Tag)}</li>
   * </ol>
   * @param tags the tags to merge into the collection
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   */
  default boolean merge(Collection<Tag> tags)
  {
    if (tags.isEmpty())
    {
      return false;
    }

    // First, group tag values by Id
    //
    Map<Id, List<BIDataValue>> grouped = tags
      .stream()
      .collect(groupingBy(Tag::getId, mapping(Tag::getValue, toList())));

    boolean changed = false;
    for (Entry<Id, List<BIDataValue>> entry : grouped.entrySet())
    {
      final Id id = entry.getKey();
      final List<BIDataValue> values = entry.getValue();
      if (isMulti(id) || values.size() > 1)
      {
        changed |= addMulti(id, values);
      }
      else
      {
        changed |= set(id, values.get(0));
      }
    }
    return changed;
  }

  /**
   * Remove the the tag with the given id and value from the collection.
   *
   * @param id the id of the tag to remove
   * @param value the value for the tag to remove
   * @return {@code true} if the tag was removed
   */
  boolean remove(Id id, BIDataValue value);

  /**
   * Remove every tag with the given id.
   *
   * @param id the id of the tags to remove
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   */
  default boolean removeAll(Id id)
  {
    boolean changed = false;
    for (Tag t : filter(it -> it.getId().equals(id)))
    {
      changed |= remove(t);
    }
    return changed;
  }

  /**
   * Equivalent to <pre>remove(tag.getId(), tag.getValue())</pre>
   * @see #remove(Id, BIDataValue)
   */
  default boolean remove(Tag tag)
  {
    return remove(tag.getId(), tag.getValue());
  }

  /**
   * Get the subset of tags in the collection that satisfy the given predicate.
   * <p>
   * For example:
   * <pre>{@code
   *   // Find all marker tags
   *   //
   *   Collection<Tag> markers = myentity.tags().filter(t -> t.getValue() instanceof BMarker);
   * }</pre>
   *
   * @param condition the condition to test for each tag
   * @return a {@code Collection<Tag>} containing all tags that satisfy the condition
   */
  Collection<Tag> filter(Predicate<Tag> condition);

  /**
   * Get all tags in the collection.
   *
   * @return a {@code Collection<Tag>} containing all tags in the collection
   */
  default Collection<Tag> getAll()
  {
    return filter(Objects::nonNull);
  }

  /**
   * Get the value of the tag with the given id if it exists.
   * <p>
   * If the tag is multi-value the returned value is non-deterministic.
   *
   * @param id the id to search for
   * @return a Optional containing the value of the tag with the given id if it exists
   */
  Optional<BIDataValue> get(Id id);

  /**
   * Get all values for tags with the given id.
   *
   * @param id the id of tags to search for
   * @return a {@code Collection<BIDataValue>} that contains all values for tags with the given id.
   */
  default Collection<BIDataValue> getValues(Id id)
  {
    return getAll().stream()
      .filter(tag -> tag.getId().equals(id))
      .map(Tag::getValue)
      .collect(Collectors.toList());
  }

  /**
   * Get all tags that have an {@link Id} with the given dictionary.
   *
   * @param dictionary the dictionary name of tags to search for
   * @return a {@code Collection<Tag>} that contains all tags in the given dictionary
   */
  default Collection<Tag> getInDictionary(String dictionary)
  {
    return getAll().parallelStream()
      .filter(tag -> tag.getId().getDictionary().equals(dictionary))
      .collect(Collectors.toList());
  }

  /**
   * Get an iterator over all tags in the collection.
   *
   * @return an Iterator over all tags in the collection
   */
  @Override
  default Iterator<Tag> iterator()
  {
    return getAll().iterator();
  }
}
