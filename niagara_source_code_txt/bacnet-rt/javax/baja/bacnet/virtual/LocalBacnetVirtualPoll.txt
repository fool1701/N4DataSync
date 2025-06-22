/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.Type;

import javax.baja.status.BStatus;

import javax.baja.bacnet.datatypes.BBacnetPropertyReference;
import javax.baja.bacnet.export.BIBacnetExportObject;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.LocalBacnetPoll;
import javax.baja.bacnet.util.PollListEntry;

import com.tridium.bacnet.asn.NErrorType;

/**
 * LocalBacnetVirtualPoll
 * This class is used for polling local virtual properties pointing to local
 * BACnet export properties.  It allows them to be handled in a similar fashion
 * to regular BACnet virtual properties that exist in remote devices.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation Nov 11, 2008
 * @since NiagaraAX 3.5
 */
public class LocalBacnetVirtualPoll
  extends LocalBacnetPoll
{

  LocalBacnetVirtualPoll(BLocalBacnetVirtualGateway gateway)
  {
    this.gateway = gateway;
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  protected BRelTime getPollRate()
  {
    return gateway.getPollRate();
  }

  protected String getThreadName()
  {
    return "Local Bacnet Virtual Poll";
  }

  protected Type getPolledType()
  {
    return BLocalBacnetVirtualProperty.TYPE;
  }

  protected boolean poll(BObject o)
    throws Exception
  {
    BLocalBacnetVirtualProperty lbvp = (BLocalBacnetVirtualProperty)o;
    BIBacnetExportObject export = ((BLocalBacnetVirtualObject)lbvp.getParent()).getExport();

    PollListEntry[] ples = lbvp.getPollListEntries();
    for (int i = 0; i < ples.length; i++)
    {
      PollListEntry ple = ples[i];
      try
      {
        BBacnetPropertyReference pr = new BBacnetPropertyReference(ple.getPropertyId(),
          ple.getPropertyArrayIndex());
        PropertyValue pv = export.readProperty(pr);

        if (!pv.isError())
        {
          lbvp.fromEncodedValue(pv.getPropertyValue(), BStatus.ok, ple);
        }
        else
        {
          lbvp.readFail(NErrorType.toString(pv.getErrorClass(), pv.getErrorCode()));
        }
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, "Exception occurred in LocalBacnetVirtualPoll poll", e);
        lbvp.readFail(e.toString());
      }
    }
    return true;
  }


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private BLocalBacnetVirtualGateway gateway = null;
  private static final Logger logger = Logger.getLogger("bacnet.virtual");

}
