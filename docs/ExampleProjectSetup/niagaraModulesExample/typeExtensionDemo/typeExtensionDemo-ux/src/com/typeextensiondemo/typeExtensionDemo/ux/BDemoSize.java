/*
 * Copyright 2020 Tridium, Inc. All Rights Reserved.
 */

package com.typeextensiondemo.typeExtensionDemo.ux;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BDouble;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
public final class BDemoSize
  extends BSimple
{
  public static final BDemoSize DEFAULT = new BDemoSize(0, 0);

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.typeextensiondemo.typeExtensionDemo.ux.BDemoSize(2979906276)1.0$ @*/
/* Generated Thu Dec 01 20:57:47 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDemoSize.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BDemoSize(double width, double height)
  {
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof BDemoSize)
    {
      BDemoSize r = (BDemoSize)obj;
      return width == r.width && height == r.height;
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return (int)(width * height);
  }

  @Override
  public void encode(DataOutput encoder)
    throws IOException
  {
    encoder.writeUTF(encodeToString());
  }

  @Override
  public BObject decode(DataInput decoder)
    throws IOException
  {
    return decodeFromString(decoder.readUTF());
  }

  @Override
  public String encodeToString()
    throws IOException
  {
    return width + "," + height;
  }

  @Override
  public BObject decodeFromString(String s)
    throws IOException
  {
    int c = s.indexOf(',');
    return new BDemoSize(
      BDouble.decode(s.substring(0, c)),
      BDouble.decode(s.substring(c + 1))
    );
  }

  public double getWidth()
  {
    return width;
  }

  public double getHeight()
  {
    return height;
  }

  private final double width;
  private final double height;
}
