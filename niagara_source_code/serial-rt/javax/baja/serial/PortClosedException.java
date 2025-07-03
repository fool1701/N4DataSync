/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.serial;

import javax.baja.sys.*;

/**
 * PortNotFoundException is thrown when an attempt is made to access
 * a port that has been closed.
 *
 * @author    Dan Giorgis
 * @creation  6 Jan 2004
 * @version   $Revision: 2$ $Date: 2/6/04 9:49:45 AM EST$
 * @since     Baja 1.0
 */

public class PortClosedException 
  extends BajaRuntimeException
{
  public PortClosedException()
  {
    super();
  }

  public PortClosedException(String msg)
  {
    super(msg);
  }

}
