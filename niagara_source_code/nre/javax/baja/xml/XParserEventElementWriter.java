/*
 * Copyright 2017, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.xml;

import java.util.logging.Logger;

/**
 * Writes XML data to an XWriter, based on received XParserEvents.
 *
 * @author Hugh Eaves
 * @since Niagara 4.6
 */
public class XParserEventElementWriter implements XParserEventListener
{

  /**
   * The Constant logger.
   */
  private static final Logger logger = Logger.getLogger(XParserEventElementWriter.class.getName());
  /**
   * The writer.
   */
  private final XWriter writer;

  /**
   * Instantiates a new x event writer.
   *
   * @param writer the writer
   */
  public XParserEventElementWriter(final XWriter writer)
  {
    this.writer = writer;
  }

  /*
   * (non-Javadoc)
   *
   * @see javax.baja.xml.XParserEventListener#handleEvent(int, java.util.List,
   * javax.baja.xml.XContent, boolean)
   */
  @Override
  public void handleEvent(final XParserEvent event)
  {
    switch (event.getEventId())
    {
      case XParser.EOF:
        break;

      case XParser.ELEM_START:
        ((XElem)event.getContent()).write(writer, 0, event.isSelfClosing());
        break;

      case XParser.ELEM_END:
        if (!event.isSelfClosing())
        {
          writer.write("</" + ((XElem)event.getContent()).qname() + ">");
        }
        break;

      case XParser.TEXT:
        event.getContent().write(writer);
        break;
    }

    return;
  }

  @Override
  public String toString()
  {
    final StringBuilder sb = new StringBuilder("XParserEventElementWriter{");
    sb.append("writer=").append(writer);
    sb.append('}');
    return sb.toString();
  }

}
