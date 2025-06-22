/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util.worker;

import javax.baja.bacnet.datatypes.BBacnetAddress;

/**
 * The IBacnetAddress interface allows
 * a WorkerPool to distribute work
 * across a worker pool based on BBacnetAddress.
 *
 * @author Joseph Chandler
 * @version $Revision$ $Date$
 * @creation 26 Aug 13
 * @since Niagara 3.8 Bacnet 1.0
 */
public interface IBacnetAddress
{
  public BBacnetAddress getAddress();
}
