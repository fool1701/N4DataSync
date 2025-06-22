/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag.util;

import java.util.Objects;
import java.util.Optional;
import javax.baja.naming.BOrd;
import javax.baja.tag.Entity;
import javax.baja.tag.Relations;
import javax.baja.tag.Tags;

/**
 * A basic implementation of the {@link Entity} interface.
 *
 * @author Matthew Giannini
 */
public class BasicEntity implements Entity
{
  private final BOrd ord;
  private final Tags tags;
  private final Relations relations;

  public BasicEntity()
  {
    this(null);
  }

  public BasicEntity(BOrd ord)
  {
    this(ord, new TagSet());
  }

  public BasicEntity(BOrd ord, Tags tags)
  {
    this(ord, tags, new RelationSet());
  }

  public BasicEntity(BOrd ord, Tags tags, Relations relations)
  {
    Objects.requireNonNull(tags);
    Objects.requireNonNull(relations);

    this.ord = ord;
    this.tags = tags;
    this.relations = relations;
  }

  @Override
  public Optional<BOrd> getOrdToEntity()
  {
    return Optional.ofNullable(ord);
  }

  @Override
  public Relations relations()
  {
    return relations;
  }

  @Override
  public Tags tags()
  {
    return tags;
  }

  @Override
  public String toString()
  {
    return "BasicEntity; ord: " + (ord != null ? ord : BOrd.NULL);
  }
}
