/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.BBacnetPropertyReference;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.export.BIBacnetExportObject;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.job.BacnetDiscoveryUtil;

@NiagaraType
public class BLocalBacnetVirtualObject
  extends BBacnetVirtualObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BLocalBacnetVirtualObject(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalBacnetVirtualObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BLocalBacnetVirtualObject()
  {
  }

  public BLocalBacnetVirtualObject(BLocalBacnetVirtualGateway lgw, String virtualPathName)
  {
    super(virtualPathName);
    export = lgw.localDevice().lookupBacnetObject(getObjectId());
  }

  public BLocalBacnetVirtualObject(BIBacnetExportObject o)
  {
    super();
    export = o;
    if (o != null)
      setObjectId(o.getObjectId());
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  public boolean isChildLegal(BComponent child)
  {
    return child instanceof BLocalBacnetVirtualProperty;
  }

  public BBacnetDevice device()
  {
    throw new UnsupportedOperationException("Not supported for LocalBacnetVirtualObject");
  }

  protected void discoverFacets()
  {
    // Discover the facets to be used for this point.
    HashMap<String, BIDataValue> map = new HashMap<>();
    if (getObjectId().isValid())
    {
      int[] facetProps = BacnetDiscoveryUtil.getFacetProps(getObjectId().getObjectType());
      if (facetProps != null)
      {
        for (int i = 0; i < facetProps.length; i++)
        {
          try
          {
            PropertyValue pv = export.readProperty(new BBacnetPropertyReference(facetProps[i]));

            if (!pv.isError())
              BacnetDiscoveryUtil.addFacet(facetProps[i], pv.getPropertyValue(), map, null);
          }
          catch (BacnetException e)
          {
            loggerBacnetClient.info("BacnetException reading property " + BBacnetPropertyIdentifier.tag(facetProps[i]) + " in " + objectId + ": " + e);
          }
          catch (Exception e)
          {
            loggerBacnetClient.info("Exception reading property " + BBacnetPropertyIdentifier.tag(facetProps[i]) + " in " + objectId + ": " + e);
          }
        } // for
      } // facetProps
    } // objectId
    setFacets(BFacets.make(map));

    // Check if this is a prioritized point.
    int objectType = getObjectId().getObjectType();
    if ((objectType == BBacnetObjectType.ANALOG_OUTPUT)
      || (objectType == BBacnetObjectType.BINARY_OUTPUT)
      || (objectType == BBacnetObjectType.MULTI_STATE_OUTPUT))
      setPrioritizedPoint(true);
    else if ((objectType == BBacnetObjectType.ANALOG_VALUE)
      || (objectType == BBacnetObjectType.BINARY_VALUE)
      || (objectType == BBacnetObjectType.MULTI_STATE_VALUE))
    {
      try
      {
        PropertyValue pv = export.readProperty(PRIORITY_ARRAY_SIZE);
        if (!pv.isError())
          setPrioritizedPoint(true);
      }
      catch (BacnetException e)
      {
        loggerBacnetVirtual.info("BacnetException reading priorityArray size in " + getObjectId() + ": " + e);
      }
      catch (Exception e)
      {
        loggerBacnetVirtual.info("Exception reading priorityArray size in " + getObjectId() + ": " + e);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  BIBacnetExportObject getExport()
  {
    return export;
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final BBacnetPropertyReference PRIORITY_ARRAY_SIZE =
    new BBacnetPropertyReference(BBacnetPropertyIdentifier.PRIORITY_ARRAY, 0);


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BIBacnetExportObject export;

  private static final Logger loggerBacnetVirtual = Logger.getLogger("bacnet.virtual");
  private static final Logger loggerBacnetClient = Logger.getLogger("bacnet.client");
}
