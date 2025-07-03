/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.util.Objects;

/**
 * Generic key + value 2-tuple.
 *
 * @author Matt Boon
 * @creation June 22, 2015
 * @since Niagara 4.0
 */
public class KeyValueTuple<K,V>
{
  public KeyValueTuple(K key, V value)
  {
    Objects.requireNonNull(key);
    Objects.requireNonNull(value);
    this.key = key;
    this.value = value;
  }

  @Override
  public boolean equals(Object v)
  {
    return v != null &&
      v instanceof KeyValueTuple &&
      Objects.equals(((KeyValueTuple<?,?>)v).key, key) &&
      Objects.equals(((KeyValueTuple<?,?>)v).value, value);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(key, value);
  }

  public final K key;
  public final V value;
}
