/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tagdictionary;

import javax.baja.tag.Entity;

/**
 * A TagRuleScope scopes a BScopedTagRule so that the rule only applies
 * to an {@link Entity} if a TagRuleScope includes the Entity.
 *
 * @author Scott Newton
 * @creation 28 Oct 16
 * @since Niagara 4.3
 */
public interface TagRuleScope
{
  /**
   * Determine whether this scope includes the specified entity.
   *
   * @param entity the entity to check against this scope
   * @return {@code true} if this scope includes the entity, {@code false} otherwise.
   */
  boolean includes(Entity entity);
}
