/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BacnetException is the base class for exceptions that
 * occur while performing any Bacnet operations.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 26 Sep 02
 * @since Niagara 3 Bacnet 1.0
 */
public class BacnetException
  extends BajaException
{
  /**
   * Constructor with specified message and nested exception.
   */
  public BacnetException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * Constructor with specified nested exception.
   */
  public BacnetException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Constructor with specified message.
   */
  public BacnetException(String msg)
  {
    super(msg);
  }

  /**
   * No argument constructor.
   */
  public BacnetException()
  {
    super();
  }

  protected static Lexicon lex = Lexicon.make("bacnet");
}
