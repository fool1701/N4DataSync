/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

/**
 * A taggable object maintains a collection of tags on itself.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 */
public interface Taggable
{
  /**
   * Get the tags for the taggable.
   * <p>
   * The tags are not thread-safe unless a concrete implementation of taggable
   * makes a special guarantee of thread safety.
   *
   * @return the tags for this taggable.
   */
  Tags tags();
}
