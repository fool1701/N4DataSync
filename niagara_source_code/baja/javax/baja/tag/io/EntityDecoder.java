/**
 * Copyright Tridium, Inc. 2014.  All rights reserved.
 */
package javax.baja.tag.io;

import java.io.IOException;
import javax.baja.tag.Entity;
import javax.baja.tag.Relation;
import javax.baja.tag.util.BasicEntity;

/**
 * EntityDecoder
 * This interface defines the APIs for decoding Niagara {@link Entity} objects,
 * which contain {@link javax.baja.tag.Tag} and {@link javax.baja.tag.Relation} objects providing metadata about Niagara
 * nodes, points, and objects, as well as their relationships with other objects.
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 1/23/14
 *         Time: 3:08 PM
 */
@SuppressWarnings("try")
public interface EntityDecoder extends AutoCloseable
{
  /**
   * Decode from the decoder's input a serialized {@link Entity} tag set.
   * This method will return a new {@link Entity},
   * decorated with the decoded {@link javax.baja.tag.Tag}s.
   * The default implementation will use a {@link BasicEntity} to hold the
   * deserialized {@link javax.baja.tag.Tag}s.
   * @return an (@code Entity} with the decoded {@link javax.baja.tag.Tag}s.
   * @throws IOException if anything fails with the decoding.
   */
  Entity decode() throws IOException;

  /**
   * Decode from the decoder's input a serialized {@link Entity} tag set and merge
   * with the supplied {@link Entity}'s current tag set.
   * This will leave the current tags in place, and simply merge the decoded tags
   * into the existing ones.
   * @see javax.baja.tag.Entity
   * @param entity an {@link Entity} to which the decoded tags should be applied.
   * @throws IOException if anything fails with the decoding.
   */
  default void merge(Entity entity) throws IOException
  {
    Entity decoded = decode();
    entity.tags().merge(decoded.tags().getAll());
    for (Relation r : decoded.relations())
    {
      entity.relations().add(r);
    }
  }

}
