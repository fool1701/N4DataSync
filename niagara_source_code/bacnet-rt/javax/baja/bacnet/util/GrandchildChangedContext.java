/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import javax.baja.sys.BasicContext;

import javax.baja.nre.util.ByteArrayUtil;


public class GrandchildChangedContext
  extends BasicContext
{

  public GrandchildChangedContext(int arrayIndex, byte[] encodedValue)
  {
    this.arrayIndex = arrayIndex;
    this.encodedValue = encodedValue;
  }

  public int getArrayIndex()
  {
    return arrayIndex;
  }

  public byte[] getEncodedValue()
  {
    return encodedValue; // copy?
  }

  public String toString()
  {
    return "GrandchildChangedContext: ndx=" + arrayIndex + " ev=" + ByteArrayUtil.toHexString(encodedValue);
  }

  private int arrayIndex;
  private byte[] encodedValue;
}
