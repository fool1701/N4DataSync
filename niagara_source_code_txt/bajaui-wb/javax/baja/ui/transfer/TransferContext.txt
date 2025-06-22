/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.transfer;

import javax.baja.sys.*;

/**
 * TransferContext provides contextual information 
 * during a clipboard or drag-and-drop operation.
 *
 * @author    Brian Frank       
 * @creation  6 Mar 02
 * @version   $Revision: 9$ $Date: 3/28/05 10:32:40 AM EST$
 * @since     Baja 1.0
 */
public class TransferContext
  extends BasicContext
  implements TransferConst
{       

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with source context.
   */
  public TransferContext(Context sourceContext, int action, TransferEnvelope envelope)
  {
    super(sourceContext);
    this.action = action;
    this.envelope = envelope;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////  

  /**
   * Get the action constant which is one of 
   * ACTION_COPY or ACTION_MOVE.
   */
  public int getAction()
  {
    return action;
  }
  
  /**
   * Return if the action is ACTION_COPY.
   */
  public boolean isCopy()
  {
    return action == ACTION_COPY;
  }

  /**
   * Return if the action is ACTION_MOVE.
   */
  public boolean isMove()
  {
    return action == ACTION_MOVE;
  }

  /**
   * Get transfer envelope obtained from the source 
   * widget via the getTransferEnvelope() method.
   */
  public TransferEnvelope getEnvelope()
  {
    return envelope;
  }
  
  /**
   * Get x coordinate of the drop operation.
   */
  public double getX()
  {
    return x;
  }

  /**
   * Get y coordinate of the drop operation.
   */
  public double getY()
  {
    return y;
  }

  /**
   * Set the action constant.
   */
  public void setAction(int action)
  {
    this.action = action;
  }
  
  /**
   * Set the current x, y drop coordinate.
   */
  public void setPosition(double x, double y)
  {
    this.x = x;
    this.y = y;
  }
  
  /**
   * Return if this is mouse a pulse.
   */
  public boolean isPulse()
  {                                  
    return pulse;
  }

  /**
   * Return if this is mouse a pulse.
   */
  public void setPulse(boolean pulse)
  {
    this.pulse = pulse;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  int action;
  TransferEnvelope envelope;
  double x;
  double y;
  boolean pulse;
  
}
