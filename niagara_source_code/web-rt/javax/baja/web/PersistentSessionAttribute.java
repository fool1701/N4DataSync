/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

/**
 * A holder for attributes that should not be cleared when the session is invalidated. The owner
 * of the attribute is responsible for managing it.
 *
 * @author Melanie Coggan on 2019-08-05
 * @since Niagara 4.9
 */
public class PersistentSessionAttribute
{
  public PersistentSessionAttribute(Object attribute)
  {
    this.attribute = attribute;
  }

  public Object getAttribute()
  {
    return attribute;
  }

  private final Object attribute;
}
