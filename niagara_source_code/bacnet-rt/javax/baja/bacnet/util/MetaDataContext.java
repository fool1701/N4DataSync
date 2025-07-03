/**
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import javax.baja.sys.BasicContext;

public class MetaDataContext
  extends BasicContext
{
  public MetaDataContext(String propName)
  {
    propertyName = propName;
  }

  public boolean equals(Object o)
  {
    if (o == null) return false;
    if (o instanceof MetaDataContext)
    {
      MetaDataContext mcx = (MetaDataContext)o;
      return propertyName.equals(mcx.propertyName);
    }
    return false;
  }

  public int hashCode()
  {
    return 1;
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder("Bacnet:MetaData:");
    sb.append(propertyName);
    return sb.toString();
  }

  public String getPropertyName()
  {
    return propertyName;
  }

  private String propertyName;
}
