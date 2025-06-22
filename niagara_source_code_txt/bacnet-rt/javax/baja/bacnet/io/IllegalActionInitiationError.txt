/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.sys.LocalizableRuntimeException;


/**
 * @author Sandipan Aich
 * @version $Revision$ $Date$
 * @creation 16 Aug 2019
 * @since Niagara 4.9
 */

public class IllegalActionInitiationError extends LocalizableRuntimeException
{
  public IllegalActionInitiationError(String lexiconModule, String lexiconKey, Object[] lexiconArgs)
  {
    super(lexiconModule, lexiconKey, lexiconArgs, null);
  }
}
