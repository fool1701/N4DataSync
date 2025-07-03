/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.migration;

import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Type;

/**
 * DuplicateConverterException
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 10/20/14
 *         Time: 11:59 AM
 */
public class DuplicateConverterException
  extends BajaRuntimeException
{
  public DuplicateConverterException(String convertType, Type newType, Type currentType, boolean bogConverter)
  {
    targetType = convertType;
    duplicate = newType;
    existing = currentType;
    converterType = bogConverter ? "BIBogElementConverter" : "BIPxElementConverter";
  }

  public String toString()
  {
    return "Duplicate " + converterType + " registration on " + targetType + ": " + duplicate + " != " + existing;
  }

  public String getMessage()
  {
    return "Attempted to register a second " + converterType + " ("+duplicate
      +") on convertType "+targetType+"; already registered by "+existing;
  }

  private String targetType;
  private Type duplicate;
  private Type existing;
  private String converterType;
}
