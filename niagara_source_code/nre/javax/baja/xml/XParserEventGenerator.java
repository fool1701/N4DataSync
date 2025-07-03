/*
 * Copyright 2017, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.xml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * XParserEventGenerator reads all of the XML nodes available from an XParser,
 * and dispatches events to registered XParserEventListeners.
 * <p>
 * Listeners can be registered to listen on specific event ids, and for events
 * from a specific part of the XML document.
 *
 * @author Hugh Eaves
 * @since Niagara 4.6
 */
public class XParserEventGenerator
{
  /**
   * When registering an event, this constant indicates that the event
   * should match any event id, instead of a specific event id.
   */
  public static final int ANY_EVENT = -1;
  private static final Logger logger = Logger.getLogger(XParserEventGenerator.class.getName());
  /**
   * Registered event listeners
   */
  private final List<EventListenerRegistration> registrations = new LinkedList<>();
  /**
   * Single location object used for all event generation / path matching.
   */
  private final XElemLocation location = new XElemLocation();
  /**
   * Parser associated with this event generator.
   */
  private final XParser parser;
  /**
   * The current position in the registrations list. During the event
   * dispatch loop, this is the position of the current listener registration
   * being processed. Otherwise, it is equal to registrations.size();
   */
  private int currentPosition = 0;
  private int insertPosition = 0;

  /**
   * Instantiates a new x event generator.
   *
   * @param parser the parser
   */
  public XParserEventGenerator(final XParser parser)
  {
    this.parser = parser;
  }

  /**
   * Adds the listener.
   *
   * @param eventId  the event id
   * @param listener the listener
   */
  public void addListener(final int eventId, final XParserEventListener listener)
  {
    addListener(eventId, null, listener);
  }

  /**
   * Adds the listener.
   *
   * @param listener the listener
   */
  public void addListener(final XParserEventListener listener)
  {
    addListener(ANY_EVENT, null, listener);
  }

  /**
   * Adds the listener.
   *
   * @param path     the path
   * @param listener the listener
   */
  public void addListener(final XPath path, final XParserEventListener listener)
  {
    addListener(ANY_EVENT, path, listener);
  }

  /**
   * Adds the listener.
   *
   * @param eventId  the event id
   * @param path     the path
   * @param listener the listener
   */
  public void addListener(final int eventId, final XPath path, final XParserEventListener listener)
  {
    EventListenerRegistration registration = new EventListenerRegistration(eventId, path, listener, location);
    logger.fine(() -> String.format("Inserting listener @ position %d [%s]", insertPosition, registration));
    registrations.add(insertPosition, registration);
    ++insertPosition;
  }

  /**
   * Remove a listener from this event generator.
   *
   * @param listener
   */
  public void removeListener(final XParserEventListener listener)
  {
    int index = 0;
    int priorElementsRemoved = 0;
    for (Iterator<EventListenerRegistration> i = registrations.iterator(); i.hasNext(); )
    {
      EventListenerRegistration registration = i.next();
      if (registration.listener.equals(listener))
      {
        i.remove();
        int finalIndex = index; // for logging
        logger.fine(() -> String.format("Removing listener @ position %d [%s]", finalIndex, registration));
        if (index <= currentPosition)
        {
          logger.fine(() -> String.format("Element was before currentPosition %d", currentPosition));
          priorElementsRemoved++;
        }
      }
      ++index;
    }

    currentPosition -= priorElementsRemoved;
    insertPosition -= priorElementsRemoved;

    logger.fine(() -> String.format("After remove: currentPosition = %d, insertPosition = %d", currentPosition, insertPosition));
  }

  /**
   * Dispatch event.
   */
  private void dispatchEvent(final XParserEvent event)
  {
    logger.fine(() -> String.format("Starting dispatch loop, registered listeners = %d", registrations.size()));
    currentPosition = 0;
    while (currentPosition < registrations.size())
    {
      insertPosition = currentPosition + 1;
      logger.fine(() -> String.format("Calling listener @ position %d [%s]", currentPosition, registrations.get(currentPosition)));
      registrations.get(currentPosition).processEvent(event);
      ++currentPosition;
    }
  }

  /**
   * Read the entire XML document, and generate events.
   */
  public void run()
  {
    try
    {
      final XParserEventImpl event = new XParserEventImpl();
      location.clear();

      int nodeType = parser.next();
      boolean selfClosing = false;

      while (nodeType != XParser.EOF)
      {
        switch (nodeType)
        {
          case XParser.EOF:
            break;

          case XParser.ELEM_START:
          {
            selfClosing = parser.emptyElem();
            final XElem elem = parser.elem().copy();
            location.addElement(elem);
            event.setAll(XParser.ELEM_START, location, elem, selfClosing);
            dispatchEvent(event);
            break;
          }

          case XParser.ELEM_END:
          {
            event.setAll(XParser.ELEM_END, location, location.get(location.size() - 1), selfClosing);
            dispatchEvent(event);
            location.removeElement();
            selfClosing = false;
            break;
          }

          case XParser.TEXT:
          {
            final XText text = parser.text();
            event.setAll(XParser.TEXT, location, text, false);
            dispatchEvent(event);
            break;
          }

          default:
            break;
        }

        nodeType = parser.next();
      }
    }
    catch (final Exception e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * The Class EventListenerRegistration.
   */
  private static class EventListenerRegistration
  {

    /**
     * The event id.
     */
    private final int eventId;
    /**
     * The listener.
     */
    private final XParserEventListener listener;
    /**
     * The matcher.
     */
    private XPathMatcher matcher;

    /**
     * Instantiates a new event listener registration.
     *
     * @param eventId  the event id
     * @param path     the path
     * @param listener the listener
     */
    public EventListenerRegistration(final int eventId, final XPath path, final XParserEventListener listener, final XElemLocation location)
    {
      this.eventId = eventId;
      this.listener = listener;
      if (path != null)
      {
        this.matcher = path.getMatcher(location);
      }
    }

    public void processEvent(XParserEvent event)
    {
      if ((eventId == ANY_EVENT || eventId == event.getEventId())
        && (matcher == null || matcher.matches()))
      {
        listener.handleEvent(event);
      }
    }

    @Override
    public boolean equals(Object o)
    {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      EventListenerRegistration that = (EventListenerRegistration)o;

      if (eventId != that.eventId) return false;
      if (listener != null ? !listener.equals(that.listener) : that.listener != null) return false;
      return matcher != null ? matcher.equals(that.matcher) : that.matcher == null;
    }

    @Override
    public int hashCode()
    {
      int result = eventId;
      result = 31 * result + (listener != null ? listener.hashCode() : 0);
      result = 31 * result + (matcher != null ? matcher.hashCode() : 0);
      return result;
    }

    @Override
    public String toString()
    {
      final StringBuilder sb = new StringBuilder("EventListenerRegistration{");
      sb.append("eventId=").append(eventId);
      sb.append(", listener=").append(listener);
      sb.append(", matcher=").append(matcher);
      sb.append('}');
      return sb.toString();
    }
  }

  /**
   * An XParserEvent implementation.
   *
   * @author Hugh Eaves
   */
  private static class XParserEventImpl implements XParserEvent
  {

    /**
     * The content.
     */
    private XContent content;
    /**
     * The context.
     */
    private XElemLocation location;
    /**
     * The event id.
     */
    private int eventId;
    /**
     * The self closing.
     */
    private boolean selfClosing;

    /**
     * Clear.
     */
    public void clear()
    {
      setAll(0, null, null, false);

    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object o)
    {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      final XParserEventImpl that = (XParserEventImpl)o;

      if (eventId != that.eventId) return false;
      if (selfClosing != that.selfClosing) return false;
      if (location != null ? !location.equals(that.location) : that.location != null) return false;
      return content != null ? content.equals(that.content) : that.content == null;
    }

    /* (non-Javadoc)
     * @see javax.baja.xml.XParserEvent#getContent()
     */
    @Override
    public XContent getContent()
    {
      return content;
    }

    /* (non-Javadoc)
     * @see javax.baja.xml.XParserEvent#getContext()
     */
    @Override
    public XElemLocation getLocation()
    {
      return location;
    }

    /* (non-Javadoc)
     * @see javax.baja.xml.XParserEvent#getEventId()
     */
    @Override
    public int getEventId()
    {
      return eventId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
      int result = eventId;
      result = 31 * result + (location != null ? location.hashCode() : 0);
      result = 31 * result + (content != null ? content.hashCode() : 0);
      result = 31 * result + (selfClosing ? 1 : 0);
      return result;
    }

    /* (non-Javadoc)
     * @see javax.baja.xml.XParserEvent#isSelfClosing()
     */
    @Override
    public boolean isSelfClosing()
    {
      return selfClosing;
    }

    /**
     * Sets the all.
     *
     * @param eventId     the event id
     * @param location    the context
     * @param content     the content
     * @param selfClosing the self closing
     */
    public void setAll(final int eventId, XElemLocation location, final XContent content, final boolean selfClosing)
    {
      this.eventId = eventId;
      this.location = location;
      this.content = content;
      this.selfClosing = selfClosing;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
      final StringBuilder sb = new StringBuilder("XParserEventImpl{");
      sb.append("eventId=").append(eventId);
      sb.append(", location=").append(location);
      sb.append(", content=").append(content);
      sb.append(", selfClosing=").append(selfClosing);
      sb.append('}');
      return sb.toString();
    }

  }


}
