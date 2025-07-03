/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.io.IOException;

import javax.baja.lonworks.enums.BAddressType;
import javax.baja.lonworks.enums.BLonReceiveTimer;
import javax.baja.lonworks.enums.BLonRepeatTimer;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIAddressEntry interface for BAddressEntry and BExtAddressEntry
 * <p>
 * @author    Robert Adams
 * @creation  14 Dec 06
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:36 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
public interface BIAddressEntry
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BIAddressEntry(2979906276)1.0$ @*/
/* Generated Tue Aug 10 10:22:20 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIAddressEntry.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BAddressType getAddressType();
  public boolean isGroupAddress()     ;
  public boolean isSubnetNodeAddress();
  public boolean isTurnAroundAddress();

  public int getSize()         ;  
  public int getGroupOrSubnet();
  public int getMemberOrNode() ;  
  public int getDescriptor()   ;
  public int getDomain()       ;

  public BLonRepeatTimer getRepeatTimer(); 
  public int getRetries();
  public BLonReceiveTimer getReceiveTimer();
  public BLonRepeatTimer getTransmitTimer();
  
  public boolean isSameAddress(BIAddressEntry iae);
  public boolean equals(Object obj);
  public String encodeToString() throws IOException;
  
  public BSubnetNode getSubnetNodeAddress();
  
  public boolean isExtended();
  
  // BObject
  public String toString(Context context);

}
