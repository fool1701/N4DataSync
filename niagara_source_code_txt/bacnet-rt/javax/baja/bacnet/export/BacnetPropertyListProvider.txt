/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import static javax.baja.bacnet.enums.BBacnetPropertyIdentifier.PROPERTY_LIST;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.io.PropertyValue;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NReadPropertyResult;

public interface BacnetPropertyListProvider
  extends BacnetConst
{
  int[] getPropertyList();

  default PropertyValue readPropertyList(int ndx)
  {
    if (ndx == NOT_USED)
    {
      return new NReadPropertyResult(PROPERTY_LIST, ndx,
                                     BacnetPropertyList.readAll(getPropertyList()));
    }
    else if (ndx == 0)
    {
      // return the array size
      byte[] length = AsnUtil.toAsnUnsigned(BacnetPropertyList.size(getPropertyList()));
      return new NReadPropertyResult(PROPERTY_LIST, ndx, length);
    }
    else if (ndx > BacnetPropertyList.size(getPropertyList()))
    {
      return BacnetPropertyList.getInvalidIdx(PROPERTY_LIST, ndx);
    }
    else
    {
      // return the specified element
      try
      {
        int propId = BacnetPropertyList.read(ndx, getPropertyList());
        return new NReadPropertyResult(PROPERTY_LIST, ndx, AsnUtil.toAsnEnumerated(propId));
      }
      catch (Exception e)
      {
        return BacnetPropertyList.getInvalidIdx(PROPERTY_LIST, ndx);
      }
    }
  }
}
