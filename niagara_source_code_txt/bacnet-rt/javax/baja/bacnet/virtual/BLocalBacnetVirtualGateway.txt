/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.util.logging.Level;

import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.export.BIBacnetExportObject;
import javax.baja.bacnet.export.BLocalBacnetDevice;
import javax.baja.bacnet.io.PropertyValue;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;
import javax.baja.util.BTypeSpec;
import javax.baja.virtual.BVirtualComponent;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.asn.NBacnetPropertyReference;
import com.tridium.bacnet.asn.NErrorType;
import com.tridium.bacnet.stack.server.BBacnetExportTable;

@NiagaraType
@NiagaraProperty(
  name = "pollRate",
  type = "BRelTime",
  defaultValue = "BRelTime.makeSeconds(5)"
)
public class BLocalBacnetVirtualGateway
  extends BBacnetVirtualGateway
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BLocalBacnetVirtualGateway(2548871499)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "pollRate"

  /**
   * Slot for the {@code pollRate} property.
   * @see #getPollRate
   * @see #setPollRate
   */
  public static final Property pollRate = newProperty(0, BRelTime.makeSeconds(5), null);

  /**
   * Get the {@code pollRate} property.
   * @see #pollRate
   */
  public BRelTime getPollRate() { return (BRelTime)get(pollRate); }

  /**
   * Set the {@code pollRate} property.
   * @see #pollRate
   */
  public void setPollRate(BRelTime v) { set(pollRate, v, null); }

  //endregion Property "pollRate"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalBacnetVirtualGateway.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  public void started()
    throws Exception
  {
    super.started();
    localPoll = new LocalBacnetVirtualPoll(this);
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Used during addVirtualSlot.
   */
  protected BBacnetVirtualObject makeBacnetVirtualObject(String virtualPathName)
  {
    return new BLocalBacnetVirtualObject(this, virtualPathName);
  }

  /**
   * Used during loadObjects.
   *
   * @param export
   * @return localBacnetVirtualObject
   */
  protected BLocalBacnetVirtualObject makeLocalBacnetVirtualObject(BIBacnetExportObject export)
  {
    return new BLocalBacnetVirtualObject(export);
  }

  protected BBacnetVirtualProperty makeBacnetVirtualProperty(int propertyId,
                                                             BValue value,
                                                             String readFault,
                                                             boolean useFacets)
  {
    return new BLocalBacnetVirtualProperty(propertyId, value, readFault, useFacets);
  }

  /**
   * This method is called by the default implementation of loadVirtualSlot()
   * and should be implemented by subclasses to add a new
   * instance of a virtual component (or a BValue instance to be a property
   * on a virtual component).  The value added should be based on the
   * given virtual path name and relative to the parent virtual component.
   * The contract is that the new slot added for the generated virtual
   * instance MUST be named by the escaped virtual path name
   * (ie. always use SlotPath.escape(virtualPathName) as the name of the slot added
   * to the parent).  This method should return the property for the slot added.
   * <p>
   * NOTE:  Due to the possibility of a partial loaded state supported by
   * virtuals, subclasses should also be aware of the subscription state.
   * This means that an addVirtualSlot() call for a new virtual slot
   * could occur while the parent is already subscribed.  So this could affect
   * how the new virtual slot should be handled (ie. added to a poll scheduler).
   * Subclasses should always be aware of this potential state.
   */
  protected Property addVirtualSlot(BVirtualComponent parent,
                                    String virtualPathName)
  {
    try
    {
      if (parent instanceof BLocalBacnetVirtualProperty)
      {

      }
      else if (parent instanceof BLocalBacnetVirtualObject)
      {
        BLocalBacnetVirtualObject object = (BLocalBacnetVirtualObject)parent;
        int scndx = virtualPathName.indexOf(";");
        String propertyName = (scndx > 0) ? virtualPathName.substring(0, scndx) : virtualPathName;
        String virtualPropertyName = SlotPath.escape(virtualPathName);

        int propertyId = BBacnetPropertyIdentifier.ordinal(propertyName);
        PropertyInfo pi = localDevice().getPropertyInfo(
          object.getExport().getObjectId().getObjectType(), propertyId);

//        NBacnetPropertyReference ref
//          = new NBacnetPropertyReference(propertyId);
//        PropertyValue pv = object.getExport().readProperty(ref);
        BValue v;
        boolean useFacets = false;
        BTypeSpec spec = null;
        if (pi != null)
        {
          try
          {
            if (pi.isArray())
            {
              spec = BTypeSpec.make(pi.getType());
              Type t = spec.getResolvedType();
              int size = pi.getSize();
              if (size > 0)
                v = new BBacnetArray(t, size);
              else
                v = new BBacnetArray(t);
            }
            else if (pi.isList())
            {
              spec = BTypeSpec.make(pi.getType());
              Type t = spec.getResolvedType();
              v = new BBacnetListOf(t);
            }
            else
            {
              spec = BTypeSpec.make(pi.getType());
              v = (BValue)spec.getInstance();
            }

            if (!pi.getFacetControl().equals("no"))
            {
              useFacets = true;
            }
          } // try
          catch (Exception e)
          {
            String s = "Unknown Type:" + spec + " for "
              + BBacnetObjectType.tag(object.getObjectId().getObjectType()) + ":" + propertyName;
            log.severe("addVirtualSlot:" + s);
            if (log.isLoggable(Level.FINE))
            {
              log.log(Level.FINE, "Exception occurred in addVirtualSlot", e);
            }
            v = BString.make(s);
          }
        } // pi != null
        else
        {
          v = BString.make("???");
        }

        return parent.add(virtualPropertyName,
          makeBacnetVirtualProperty(propertyId, v, null, useFacets),
          Flags.TRANSIENT);
      }
      else
      {
        // Create a new local virtual object.
        return parent.add(SlotPath.escape(virtualPathName),
          makeBacnetVirtualObject(virtualPathName),
          Flags.TRANSIENT);
      }
    }
    catch (Exception e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception occurred in addVirtualSlot", e);
      }      
    }
    return null;
  }

  /**
   * Load objects into the gateway by reading the device's Object_List property.
   * Overrides the defined behavior in BBacnetVirtualGateway to retrieve the
   * BIBacnetExportObjects from the export table.
   *
   * @param parent
   */
  protected void loadObjects(BVirtualComponent parent)
  {
    BBacnetExportTable exports = (BBacnetExportTable)localDevice().getExportTable();
    BBacnetObjectIdentifier[] ids = exports.getObjectIds();
    for (int i = 0; i < ids.length; i++)
    {
      BBacnetObjectIdentifier id = ids[i];
      String name = SlotPath.escape(id.toString(nameContext));
      Property p = parent.getProperty(name);
      if (p != null) continue;
      BIBacnetExportObject o = localDevice().lookupBacnetObject(id);
      if (o != null)
      {
        parent.add(SlotPath.escape(name),
          makeLocalBacnetVirtualObject(o),
          Flags.TRANSIENT);
      }
    }
  }

  /**
   * Load properties for a BacnetVirtualComponent by reading all its properties.
   * This can be done with RPM(all), or by reading the list of possible properties.
   * Overrides the behavior defined in BBacnetVirtualGateway
   *
   * @param object
   */
  protected void loadProperties(BBacnetVirtualObject object)
  {
    BLocalBacnetVirtualObject local = (BLocalBacnetVirtualObject)object;
    try
    {
      PropertyValue[] pvs = local.getExport().readPropertyMultiple(ALL);
      for (int i = 0; i < pvs.length; i++)
      {
        try
        {
          PropertyValue pv = pvs[i];
          int propertyId = pv.getPropertyId();
          String propertyName = BBacnetPropertyIdentifier.tag(propertyId);
          String virtualPropertyName = SlotPath.escape(propertyName);
          Property p = local.getProperty(virtualPropertyName);
          if (p != null) continue;

          PropertyInfo pi = localDevice().getPropertyInfo(
            local.getExport().getObjectId().getObjectType(), pv.getPropertyId());
          BValue v;
          String readFault = null;
          boolean useFacets = false;
          BTypeSpec spec = null;
          if (pi != null)
          {
            try
            {
              if (pi.isArray())
              {
                spec = BTypeSpec.make(pi.getType());
                Type t = spec.getResolvedType();
                int size = pi.getSize();
                if (size > 0)
                  v = new BBacnetArray(t, size);
                else
                  v = new BBacnetArray(t);
              }
              else if (pi.isList())
              {
                spec = BTypeSpec.make(pi.getType());
                Type t = spec.getResolvedType();
                v = new BBacnetListOf(t);
              }
              else
              {
                spec = BTypeSpec.make(pi.getType());
                v = (BValue)spec.getInstance();
              }

              if (!pi.getFacetControl().equals("no"))
              {
                useFacets = true;
              }
            }
            catch (Exception e)
            {
              String s = "Unknown Type:" + spec + " for "
                + BBacnetObjectType.tag(object.getObjectId().getObjectType()) + ":" + propertyName;
              log.info("addVirtualSlot:" + s);
              if (log.isLoggable(Level.FINE))
              {
                log.log(Level.FINE, "Exception occurred in loadProperties", e);
              }
              v = BString.make(s);
            }
          } // pi != null
          else
            v = BString.make("");

          if (!pv.isError())
          {
            v = AsnUtil.asnToValue(pi, pv.getPropertyValue());
          }
          else
          {
            readFault = NErrorType.toString(pv.getErrorClass(), pv.getErrorCode());
            v = BString.make("???");
          }
          object.add(virtualPropertyName,
            makeBacnetVirtualProperty(propertyId, v, readFault, useFacets),
            Flags.TRANSIENT,
//                     facets,
            null);
        } // try inside for
        catch (Exception e)
        {
          if (log.isLoggable(Level.FINE))
          {
            log.log(Level.FINE, "Exception occurred in loadProperties", e);
          }
        }
      } // for
    }
    catch (Exception e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception occurred in loadProperties", e);
      }
    }
  }

  protected int readArraySize(BBacnetVirtualProperty vp)
  {
    try
    {
      PropertyValue propVal = localDevice().readProperty(new NBacnetPropertyReference(vp.getPropertyId(), 0));
      return AsnUtil.fromAsnInteger(propVal.getPropertyValue());
    }
    catch (BacnetException e)
    {
      if (log.isLoggable(Level.FINE))
        log.log(Level.FINE, "Exception reading array size in BacnetVirtualProperty " + vp.object().getObjectId() + ":" + vp.getPropertyId() + "[" + vp.debugString(null) + "]", e);
      return 0;
    }
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context cx)
  {
    return "LocalBacnetVirtualGateway";
  }

  public LocalBacnetVirtualPoll getLocalPoll()
  {
    return localPoll;
  }


////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Get the containing device object which will poll this object.
   *
   * @return the containing BBacnetDevice
   */
  BLocalBacnetDevice localDevice()
  {
    return (BLocalBacnetDevice)getParent();
  }

  /**
   * Convenience to get BacnetNetwork.
   */
  BBacnetNetwork network()
  {
    return (BBacnetNetwork)getParent().getParent();
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    localPoll.spy(out);
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  private static final BBacnetPropertyReference[] ALL =
    { new BBacnetPropertyReference(BBacnetPropertyIdentifier.ALL) };


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private LocalBacnetVirtualPoll localPoll;

}
