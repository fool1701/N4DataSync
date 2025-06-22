/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

import javax.baja.bacnet.*;
import javax.baja.bacnet.datatypes.*;

/**
 * PrivateTransferListener is the interface objects use to identify that they
 * need to be informed of incoming PrivateTransfer requests.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 23 Aug 2006
 * @since Niagara 3.2
 */
public interface PrivateTransferListener
  extends BacnetServiceListener
{
  /**
   * What Vendor ID is this listener servicing?
   *
   * @return the vendorID that this listener uses.
   */
  int getVendorId();

  /**
   * Receive a ConfirmedPrivateTransfer request.
   */
  byte[] receiveConfirmedPrivateTransfer(long vendorId,
                                         long serviceNumber,
                                         byte[] serviceParameters,
                                         BBacnetAddress sourceAddress)
    throws BacnetException;

  /**
   * Receive an UnconfirmedPrivateTransfer request.
   */
  void receiveUnconfirmedPrivateTransfer(long vendorId,
                                         long serviceNumber,
                                         byte[] serviceParameters,
                                         BBacnetAddress sourceAddress)
    throws BacnetException;
}