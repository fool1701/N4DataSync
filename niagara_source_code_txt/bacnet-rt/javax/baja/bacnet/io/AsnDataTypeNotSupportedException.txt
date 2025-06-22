/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.io;

/**
 * A AsnDataTypeNotSupportedException is thrown whenever an
 * a provided data type is not in the set of supported data types
 * for a selected field.
 *
 * @author Upender Paravastu
 * @creation 22 Feb 19
 *
 */
public class AsnDataTypeNotSupportedException extends
  DataTypeNotSupportedException
{
  /**
   * @param detailMessage the error message.
   */
  public AsnDataTypeNotSupportedException(String detailMessage)
  {
    super(detailMessage);
  }

  /**
   * Constructor with specified current asnType and detailed message.
   * @param asnType
   * @param detailMessage
   */
  public AsnDataTypeNotSupportedException(int asnType, String detailMessage)
  {
    this(detailMessage);
    this.asnType = asnType;
  }

  /**
   *
   * @return the asn type of the current data type passed
   */
  public int getAsnType()
  {
    return asnType;
  }

  private int asnType;

}
