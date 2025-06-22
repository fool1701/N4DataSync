/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIEncodable is implemented by BObjects which can be
 * serialized and unserialized into both a binary and
 * String format, and can accept a context to their
 * encodeToString method
 *
 * @author    Melanie Coggan       
 * @creation  03/01/2013
 */
@NiagaraType
public interface BIContextEncodable
    extends BIEncodable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.io.BIContextEncodable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIContextEncodable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  void encode(DataOutput out, Context context)
    throws IOException;
  
  BObject decode(DataInput in, Context context)
    throws IOException;

  String encodeToString(Context context)
    throws IOException;
  
  BObject decodeFromString(String s, Context context)
    throws IOException;

  @Override
  default void encode(DataOutput out)
    throws IOException
  {
    encode(out, null);
  }

  @Override
  default BObject decode(DataInput in)
    throws IOException
  {
    return decode(in, null);
  }

  @Override
  default String encodeToString()
    throws IOException
  {
    return encodeToString(null);
  }

  @Override
  default BObject decodeFromString(String s)
    throws IOException
  {
    return decodeFromString(s, null);
  }
}
