/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag.util;

import javax.baja.collection.CompoundIterator;
import javax.baja.collection.FilteredIterator;
import javax.baja.data.BIDataValue;
import javax.baja.tag.Id;
import javax.baja.tag.SmartTags;
import javax.baja.tag.Tag;
import javax.baja.tag.Tags;
import java.util.*;
import java.util.function.Predicate;

/**
 * A SmartTagSet is an implementation of the SmartTags interface.  It maintains
 * separate subsets of tags for smart tags and direct tags.  Any methods that
 * would modify the tag set only operate on the direct tags.
 *
 * @author John Sublett
 * @creation 2/16/14
 * @since Niagara 4.0
 */
public class SmartTagSet
  implements SmartTags
{
  /**
   * Create a new SmartTagSet that includes the specified set of direct tags.
   *
   * @param directTags The direct tags included in this set.
   */
  public SmartTagSet(Tags impliedTags, Tags directTags)
  {
    this.impliedTags = impliedTags;
    this.directTags = directTags;
  }

  /**
   * Get the direct tags.  The direct tags are the tags that are defined
   * directly on the Taggable object.
   *
   * @return Returns the set of direct tags.
   */
  @Override
  public Tags getDirectTags()
  {
    return directTags;
  }

  /**
   * Get the set of implied tags.  The implied tags are the tags that are not
   * defined directly on the Taggable object.
   *
   * @return Returns the set of implied tags.
   */
  @Override
  public Tags getImpliedTags()
  {
    return impliedTags;
  }

  /**
   * Check if the given id is present in the collection <strong>and</strong> contains multi-valued tags.
   * This method <strong>MUST</strong> return false if {@link #contains(javax.baja.tag.Id) contains(id)} return false.
   * <p>
   * If this method return {@code true} it is always safe to use the addMulti methods.
   *
   * @param id the id to check
   * @return {@code true} if the id exists in the collection and supports multi-value tags;
   * {@code false} otherwise.
   */
  @Override
  public boolean isMulti(Id id)
  {
    if (directTags.contains(id))
    {
      return directTags.get(id).get() instanceof Collection;
    }
    else
    {
      return impliedTags.isMulti(id);
    }
  }

  /**
   * Adds the given tag to the direct tags as a single-value tag. Any existing
   * tags with the same id are overwritten.
   *
   * @param tag the tag to set
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   */
  @Override
  public boolean set(Tag tag)
  {
    return directTags.set(tag);
  }

  /**
   * Set a direct multi tag.
   *
   * @param id the id for each tag
   * @param values the values for each new tag
   * @return
   */
  @Override
  public boolean setMulti(Id id, Collection<? extends BIDataValue> values)
  {
    return directTags.setMulti(id, values);
  }

  /**
   * Creates tags with the given id for each value and adds them to the direct tags.
   *
   * @param id the id for each tag
   * @param values the values for each new tag
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   * @throws IllegalArgumentException if id is not multi-value
   */
  @Override
  public boolean addMulti(Id id, Collection<? extends BIDataValue> values)
  {
    return directTags.addMulti(id, values);
  }

  /**
   * Merges the tags from the given collection into the direct tags
   * in this collection.
   *
   * @param tags the tags to merge into the collection
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   */
  @Override
  public boolean merge(Collection<Tag> tags)
  {
    return directTags.merge(tags);
  }

  /**
   * Remove the the tag with the given id and value from the direct tags.
   *
   * @param id the id of the tag to remove
   * @param value the value for the tag to remove
   * @return {@code true} if the tag was removed
   */
  @Override
  public boolean remove(Id id, BIDataValue value)
  {
    return directTags.remove(id, value);
  }

  /**
   * Remove every tag with the given id from the set of direct tags.
   *
   * @param id the id of the tags to remove
   * @return {@code true} if the collection was modified; {@code false} otherwise.
   */
  @Override
  public boolean removeAll(Id id)
  {
    return directTags.removeAll(id);
  }

  /**
   * Get an iterator for all tags in the set.
   *
   * @return Returns an iterator for all tags, both implied and direct.
   */
  @Override
  public Iterator<Tag> iterator()
  {
    @SuppressWarnings({"unchecked", "rawtypes"})
    Iterator<Tag>[] subs = new Iterator[2];
    subs[0] = new FilteredIterator<>(new ExcludeDirectDupsFilter(), impliedTags.iterator());
    subs[1] = directTags.iterator();
    return new CompoundIterator<>(subs);
  }

  /**
   * Get all of the tags in the set.
   *
   * @return Returns a collection of all tags in the set.
   */
  @Override
  public Collection<Tag> getAll()
  {
    List<Tag> all = new ArrayList<>();
    Iterator<Tag> i = impliedTags.filter(new ExcludeDirectDupsFilter()).iterator();
    while (i.hasNext())
      all.add(i.next());
    i = directTags.iterator();
    while (i.hasNext())
      all.add(i.next());

    return all;
  }

  @Override
  public Collection<Tag> filter(Predicate<Tag> f)
  {
    List<Tag> all = new ArrayList<>();
    Collection<Tag> filteredSmart =
        impliedTags.filter(new CompoundPredicate(new ExcludeDirectDupsFilter(), f));
    Collection<Tag> filteredDirect =
        directTags.filter(f);

    all.addAll(filteredSmart);
    all.addAll(filteredDirect);
    return all;
  }

  /**
   * Get the value of the tag with the given id if it exists.
   * <p>
   * If the tag is multi-value the returned value is non-deterministic.
   *
   * @param id the id to search for
   * @return a Optional containing the value of the tag with the given id if it exists
   */
  @Override
  public Optional<BIDataValue> get(Id id)
  {
    Optional<BIDataValue> o = directTags.get(id);
    if (o.isPresent())
      return o;
    else
      return impliedTags.get(id);
  }

///////////////////////////////////////////////////////////
// Predicates
///////////////////////////////////////////////////////////

  private class ExcludeDirectDupsFilter
    implements Predicate<Tag>
  {
    /**
     * Test the tag against the filter.
     *
     * @param tag Returns true if the tag is NOT in the direct tags.
     * @return
     */
    @Override
    public boolean test(Tag tag)
    {
      return !directTags.get(tag.getId()).isPresent();
    }
  }

  private class CompoundPredicate
    implements Predicate<Tag>
  {
    public CompoundPredicate(Predicate<Tag> p1, Predicate<Tag> p2)
    {
      this.p1 = p1;
      this.p2 = p2;
    }

    @Override
    public boolean test(Tag tag)
    {
      return p1.test(tag) && p2.test(tag);
    }

    private Predicate<Tag> p1;
    private Predicate<Tag> p2;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private Tags directTags;
  private Tags impliedTags;
}
