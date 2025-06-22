/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/*
 * This source code file is public domain
 * http://sourceforge.net/projects/uxparser
 */
package javax.baja.xml;

/**
 * XException is used to indicate a problem parsing XML.
 *
 * @author    Brian Frank on 6 Apr 02
 * @since     Baja 1.0
 */
public class XException
  extends RuntimeException
{
  private static final long serialVersionUID = 879536511108390186L;

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct using specified line and column.
   *
   * @param msg exception message
   * @param line line number
   * @param column column number
   * @param cause cause of this XException
   */
  public XException(String msg, int line, int column, Throwable cause)
  {
    super(format(msg, line, column));
    this.line  = line;
    this.col   = column;
    this.cause = cause;
  }

  /**
   * Construct using specified line and column.
   *
   * @param msg exception message
   * @param line line number
   * @param column column number
   */
  public XException(String msg, int line, int column)
  {
    this(msg, line, column, null);
  }

  /**
   * Construct using specified line number.
   *
   * @param msg exception message
   * @param line line number
   * @param cause cause of this XException
   */
  public XException(String msg, int line, Throwable cause)
  {
    this(msg, line, 0, cause);
  }

  /**
   * Construct using specified line number.
   *
   * @param msg exception message
   * @param line line number
   */
  public XException(String msg, int line)
  {
    this(msg, line, 0, null);
  }

  /**
   * Construct using current line and column of parser.
   *
   * @param msg exception message
   * @param parser parser to get line and column from
   * @param cause cause of this XException
   */
  public XException(String msg, XParser parser, Throwable cause)
  {
    this(msg, parser.line(), parser.column(), cause);
  }

  /**
   * Construct using current line and column of parser.
   *
   * @param msg exception message
   * @param parser parser to get line and column from
   */
  public XException(String msg, XParser parser)
  {
    this(msg, parser.line(), parser.column(), null);
  }

  /**
   * Construct using element's line number.
   *
   * @param msg exception message
   * @param elem element to get line and column from
   * @param cause cause of this XException
   */
  public XException(String msg, XElem elem, Throwable cause)
  {
    this(msg, elem.line(), 0, cause);
    this.elem = elem;
  }

  /**
   * Construct using element's line number.
   *
   * @param msg exception message
   * @param elem element to get line and column from
   */
  public XException(String msg, XElem elem)
  {
    this(msg, elem.line(), 0, null);
    this.elem = elem;
  }

  /**
   * Construct with no line number.
   *
   * @param msg exception message
   * @param cause cause of this XException
   */
  public XException(String msg, Throwable cause)
  {
    this(msg, 0, 0, cause);
  }

  /**
   * Construct with no line number.
   *
   * @param msg exception message
   */
  public XException(String msg)
  {
    this(msg, 0, 0, null);
  }

  /**
   * Constructor with cause only.
   *
   * @param cause cause of this XException
   */
  public XException(Throwable cause)
  {
    this("", 0, 0, cause);
  }

  /**
   * Default constructor.
   */
  public XException()
  {
    this("", 0, 0, null);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the line number of 0 if unknown.
   *
   * @return line number or 0
   */
  public int line()
  {
    return line;
  }

  /**
   * Get the column number of 0 if unknown.
   *
   * @return column number or 0
   */
  public int column()
  {
    return col;
  }

  /**
   * Return the XElem passed to the constructor or null.
   *
   * @return Element causing exception, if available, or null
   */
  public XElem getElem()
  {
    return null;
  }

  /**
   * Get the nested exception for or return null if no
   * cause exception is provided.
   *
   * @return cause of this exception
   */
  @Override
  public Throwable getCause()
  {
    return cause;
  }

  /**
   * Get the standard message format given a text message
   * and a line/column location.
   *
   * @param msg   base message
   * @param line  0 if unknown
   * @param col   0 if unknown
   * @return formatted message
   */
  public static String format(String msg, int line, int col)
  {
    if (line == 0 && col == 0) return msg;
    if (col == 0) return msg + " [line " + line + ']';
    return msg + " [" + line + ':' + col + ']';
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int line;
  private int col;
  private Throwable cause;
  private XElem elem;

}
