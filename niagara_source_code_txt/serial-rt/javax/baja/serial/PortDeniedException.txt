/*
 * Copyright 2001-4 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.serial;

import javax.baja.sys.*;

/**
 * PortDeniedException is thrown when someone tries to open a BSerialPort
 * that is already owned.
 *
 * @author    Bill Smith       
 * @creation  25 June 2002
 * @version   $Revision: 2$ $Date: 2/6/04 9:49:49 AM EST$
 * @since     Baja 1.0
 */

public class PortDeniedException 
  extends BajaException
{
  public PortDeniedException()
  {
    super();
  }

  public PortDeniedException(String msg)
  {
    super(msg);
  }

}
