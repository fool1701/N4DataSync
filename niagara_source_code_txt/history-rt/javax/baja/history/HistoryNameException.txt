/*
 * Copyright 2005, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

/**
 * A HistoryNameException is thrown when an attempt is made to
 * create a history with an invalid name.
 *
 * @author    John Sublett
 * @creation  16 Mar 2005
 * @version   $Revision: 1$ $Date: 3/17/05 5:19:19 PM EST$
 * @since     Baja 1.0
 */
public class HistoryNameException
  extends HistoryException
{
  public HistoryNameException()
  {
  }

  public HistoryNameException(String msg)
  {
    super(msg);
  }
}