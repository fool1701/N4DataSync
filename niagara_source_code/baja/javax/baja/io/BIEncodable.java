/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.io;

import java.io.*;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BIEncodable is implemented by BObjects which can be
 * serialized and unserialized into both a binary and
 * String format.
 *
 * @author    Brian Frank       
 * @creation  6 Nov 00
 * @version   $Revision: 1$ $Date: 2/27/03 5:12:35 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIEncodable
  extends BInterface
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.io.BIEncodable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIEncodable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public void encode(DataOutput out)
    throws IOException;

  public BObject decode(DataInput in)
    throws IOException;

  public String encodeToString()
    throws IOException;

  public BObject decodeFromString(String s)
    throws IOException;
}
