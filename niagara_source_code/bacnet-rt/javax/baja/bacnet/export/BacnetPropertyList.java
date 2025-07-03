/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import javax.baja.bacnet.enums.BBacnetErrorClass;
import javax.baja.bacnet.enums.BBacnetErrorCode;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;

import com.tridium.bacnet.asn.AsnOutputStream;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.asn.NReadPropertyResult;

/**
 * Every BACnetObject in devices &gt; PR 14
 * will include a PropertyList.
 * <p>
 * This new property will eventually
 * allow for faster property discovery,
 * especially for devices that do not support segmentation,
 * or contain a large number of proprietary properties.
 * <p>
 * The BacnetPropertyList utility class
 * consolidates methods related to the special
 * handling of the BACnetPropertyList Array.
 *
 * @author Joseph Chandler
 */
public final class BacnetPropertyList
{
  // Prevent object instantiation, static utility only
  private BacnetPropertyList()
  {
  }

  public static int[] makePropertyList(int[]... propertyLists)
  {
    int totalSize = 0;
    for (int[] propList : propertyLists)
    {
      totalSize += propList.length;
    }

    int i = 0;
    int[] propertyList = new int[totalSize];
    for (int[] propList : propertyLists)
    {
      for (int prop : propList)
      {
        propertyList[i++] = prop;
      }
    }

    return propertyList;
  }

  public static byte[] readAll(int[] props)
  {
    AsnOutputStream out = new AsnOutputStream();
    for (int propId : props)
    {
      if (shouldInclude(propId))
      {
        out.writeEnumerated(propId);
      }
    }

    return out.toByteArray();
  }

  public static int size(int[] props)
  {
    int i = 0;
    for (int propId : props)
    {
      if (shouldInclude(propId))
      {
        i++;
      }
    }

    return i;
  }

  public static int read(int ndx, int[] props)
  {
    int[] cleanProps = new int[props.length - requiredProps.length];

    int i = 0;
    for (int propId : props)
    {
      if (shouldInclude(propId))
      {
        cleanProps[i++] = propId;
      }
    }

    if (ndx < 1 || ndx > cleanProps.length + 1)
    {
      return -1;
    }

    return cleanProps[ndx - 1];
  }

  public static NReadPropertyResult getInvalidIdx(int propId, int ndx)
  {
    return new NReadPropertyResult(propId, ndx,
                                   new NErrorType(BBacnetErrorClass.PROPERTY,
                                                  BBacnetErrorCode.INVALID_ARRAY_INDEX));
  }

  public static boolean shouldInclude(int propId)
  {
    for (int i = 0; i < requiredProps.length; i++)
    {
      if (propId == requiredProps[i])
      {
        return false;
      }
    }

    return true;
  }

  /*
   * The Object_Name, Object_Type, Object_Identifier, and 
   * Property_List properties are not included in the Property List
   */
  private static final int[] requiredProps = new int[]
    {
      BBacnetPropertyIdentifier.OBJECT_NAME,
      BBacnetPropertyIdentifier.OBJECT_TYPE,
      BBacnetPropertyIdentifier.OBJECT_IDENTIFIER,
    };
}
