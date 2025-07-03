/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIBacnetDataType is the interface implemented by Bacnet constructed data types.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 22 Mar 02
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
public interface BIBacnetDataType
  extends BInterface, BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BIBacnetDataType(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:31 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIBacnetDataType.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Conversion
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  void writeAsn(AsnOutput out);

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  void readAsn(AsnInput in)
    throws AsnException;
}
