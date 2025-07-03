/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

/**
 * This interface contains constants which represent the values
 * of the BacnetUnconfirmedServiceChoice enumeration.  For more
 * information, see Bacnet spec, Clause 21, Unconfirmed Service
 * Productions.
 *
 * @author Craig Gemmill
 * @version $Revision: 2$ $Date: 12/19/01 4:35:46 PM$
 * @creation 01 Jul 97
 * @since Niagara 3 Bacnet 1.0
 */

public interface BacnetUnconfirmedServiceChoice
{

////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  int I_AM = 0;
  int I_HAVE = 1;
  int UNCONFIRMED_COV_NOTIFICATION = 2;
  int UNCONFIRMED_EVENT_NOTIFICATION = 3;
  int UNCONFIRMED_PRIVATE_TRANSFER = 4;
  int UNCONFIRMED_TEXT_MESSAGE = 5;
  int TIME_SYNCHRONIZATION = 6;
  int WHO_HAS = 7;
  int WHO_IS = 8;
  int UTC_TIME_SYNCHRONIZATION = 9;
  int WRITE_GROUP = 10;


  String[] TAGS =
    {
      "i-Am",
      "i-Have",
      "unconfirmedCovNotification",
      "unconfirmedEventNotification",
      "unconfirmedPrivateTransfer",
      "unconfirmedTextMessage",
      "timeSynchronization",
      "who-Has",
      "who-Is",
      "utcTimeSynchronization",
      "writeGroup",
    };
}