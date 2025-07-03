/*
 * Copyright 2001-4 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.serial;

import javax.baja.sys.*;

/**
 * PortNotFoundException is thrown when someone tries to open a serial
 * port that does not exist.
 *
 * @author    Bill Smith       
 * @creation  25 June 2002
 * @version   $Revision: 2$ $Date: 2/6/04 9:49:52 AM EST$
 * @since     Baja 1.0
 */

public class PortNotFoundException 
  extends BajaException
{
  public PortNotFoundException()
  {
    super();
  }

  public PortNotFoundException(String msg)
  {
    super(msg);
  }

}
