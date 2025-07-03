/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import javax.baja.sys.BValue;

/**
 * IllegalConfigChangeException is thrown when an attempt is made to
 * reconfigure a history in an unsupported way.
 *
 * @author    John Sublett
 * @creation  24 Jul 2002
 * @version   $Revision: 2$ $Date: 4/4/03 5:28:18 PM EST$
 * @since     Baja 1.0
 */
public class IllegalConfigChangeException
  extends HistoryException
{
  public IllegalConfigChangeException(BHistoryId id,
                                      String propertyName,
                                      BValue currentValue,
                                      BValue proposedValue)
  {
    super("Changing the " + propertyName + " property of a history is not allowed.");
    this.id = id;
    this.propertyName = propertyName;
    this.currentValue = currentValue.newCopy(true);
    this.proposedValue = proposedValue.newCopy(true);
  }

  /**
   * Get the history.
   */
  public BHistoryId getHistoryId()
  {
    return id;
  }

  /**
   * Get the name of the property of the BHistoryConfig.
   */
  public String getPropertyName()
  {
    return propertyName;
  }

  public BValue getCurrentValue()
  {
    return currentValue;
  }

  public BValue getProposedValue()
  {
    return proposedValue;
  }

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

  private BHistoryId id;
  private String propertyName;
  private BValue currentValue;
  private BValue proposedValue;
}
