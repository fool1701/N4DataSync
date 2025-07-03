/*
 * Copyright 2017, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.xml;

/**
 * The listener interface for receiving XParserEvent events.
 *
 * @see XParserEvent
 *
 *  @author Hugh Eaves
 * @since Niagara 4.6
 */
@FunctionalInterface
public interface XParserEventListener
{

  /**
   * Handle event.
   */
  public void handleEvent(XParserEvent event);
}
