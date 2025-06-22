/**
 * Copyright Tridium, Inc. 2014.  All rights reserved.
 */
package javax.baja.tag.io;

import java.io.IOException;
import javax.baja.tag.Entity;

/**
 * EntityEncoder
 * This interface defines the APIs for encoding Niagara {@link Entity} objects,
 * which contain {@link javax.baja.tag.Tag} and {@link javax.baja.tag.Relation} objects providing metadata about Niagara
 * nodes, points, and objects, as well as their relationships with other objects.
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 1/23/14
 *         Time: 3:07 PM
 */
@SuppressWarnings("try")
public interface EntityEncoder extends AutoCloseable
{
  /**
   * Encode an {@link Entity} to the output stream.
   * @param e the {@link Entity} to be encoded.
   * @throws IOException if an I/O error occurs.
   */
  default void encode(Entity e) throws IOException
  {
    encode(e, null);
  }

  /**
   * Encode an {@link Entity} to the output stream.
   * @param e the {@link Entity} to be encoded.
   * @param name the name of the {@link Entity} being encoded
   * @throws IOException if an I/O error occurs.
   */
  void encode(Entity e, String name) throws IOException;
}
