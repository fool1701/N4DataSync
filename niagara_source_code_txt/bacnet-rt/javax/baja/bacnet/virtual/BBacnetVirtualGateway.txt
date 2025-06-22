/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.*;
import javax.baja.bacnet.config.BBacnetDeviceObject;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetListOf;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.util.PropertyInfo;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.space.LoadCallbacks;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.*;
import javax.baja.util.BTypeSpec;
import javax.baja.virtual.BVirtualComponent;
import javax.baja.virtual.BVirtualComponentSpace;
import javax.baja.virtual.BVirtualGateway;

import com.tridium.bacnet.asn.*;

/**
 * BBacnetVirtualGateway is the gateway to the BACnet virtual component
 * space.  It defines the root virtual component, and implements the
 * discovery and creation of virtual components.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 12 Dec 2006
 * @since NiagaraAX 3.2
 */
@NiagaraType
public class BBacnetVirtualGateway
  extends BVirtualGateway
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BBacnetVirtualGateway(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetVirtualGateway.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * @deprecated
   */
  @Deprecated
  protected BBacnetVirtualComponent makeBacnetVirtualComponent(String virtualPathName)
  {
    return new BBacnetVirtualComponent(virtualPathName);
  }

  protected BBacnetVirtualObject makeBacnetVirtualObject(String virtualPathName)
  {
    return new BBacnetVirtualObject(virtualPathName);
  }

  protected BBacnetVirtualProperty makeBacnetVirtualProperty(int propertyId,
                                                             BValue value,
                                                             String readFault,
                                                             boolean useFacets)
  {
    return new BBacnetVirtualProperty(propertyId, value, readFault, useFacets);
  }

  /**
   * Should writes performed through the BVirtualPropertyWrite dynamic action
   * generate separate audit log events?
   *
   * @return false by default, subclasses may override.
   */
  protected boolean getAuditWrites()
  {
    return auditWrites;
  }

  /**
   * Set the flag indicating if BVirtualPropertyWrite invocations should generate
   * separate audit log events.
   *
   * @param aw
   */
  protected void setAuditWrites(boolean aw)
  {
    auditWrites = aw;
  }


////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Creates a new virtual component space to use for this virtual gateway.
   * Subclasses can override this method if they wish to insert their
   * own custom virtual component space, otherwise a default one will be used.
   * <p>
   * NOTE: The virtual space created should have its root component
   * assigned in this method.  This is done by using the makeVirtualRoot()
   * method.
   */
  protected BVirtualComponentSpace makeVirtualSpace()
  {
    BVirtualComponentSpace vspc = super.makeVirtualSpace();
    vspc.setLoadCallbacks(new MyLoadCallbacks());
    return vspc;
  }

  /**
   * This method is called by the virtual space's LoadCallbacks when a
   * slot should be loaded/resolved under the given parent.
   * This is the time to load an individual slot, or return the existing
   * slot if already loaded.
   * <p>
   * The default implementation of this method is sufficient for most
   * scenarios.  By default, if the specified virtual path name does not
   * specify an existing slot under the given parent, then the addVirtualSlot()
   * callback will be made to give subclasses a chance to generate and add
   * the appropriate virtual slot to the the parent component.
   * <p>
   * NOTE:  Virtual slots added should always use a slot name that is the
   * escaped virtual path name ie. use SlotPath.escape(virtualPathName) as
   * the name of the virtual slot added.
   * <p>
   * NOTE:  Due to the possibility of a partial loaded state supported by
   * virtuals, subclasses should also be aware of the subscription state.
   * This means that a loadVirtualSlot() call for a new virtual slot
   * could occur while the parent is already subscribed.  So this could affect
   * how the new virtual slot should be handled (ie. added to a poll scheduler).
   * Subclasses should always be aware of this potential state.
   */
  public Slot loadVirtualSlot(BVirtualComponent parent, String virtualPathName)
  {
    if (parent == null) return null;

    Slot result = null;
    if (virtualPathName != null)
    {
      synchronized (SLOT_LOCK)
      {
        String virtualSlotName = SlotPath.escape(virtualPathName);
        result = parent.getSlot(virtualSlotName);
        if (result == null)
        {
          // Generate the virtual instance to add as a slot to the parent
          result = addVirtualSlot(parent, virtualPathName);
          if (result != null)
          { // Quick check to make sure the virtual naming contract is followed...
            String nameAssigned = result.getName();
            if (!nameAssigned.equals(virtualSlotName))
              log.warning("Name of virtual slot added is inconsistent: \"" + nameAssigned + "\" was expected to be \"" + virtualSlotName + "\"");
          }
        }
        else
        {
          // Only do this check for virtual properties.
          if (parent instanceof BBacnetVirtualProperty)
          {
            BBacnetVirtualProperty vp = (BBacnetVirtualProperty)parent;
            if (result.isProperty())
            {
              BValue v = parent.get(result.asProperty());
              if (v instanceof BBacnetArray)
              {
                BBacnetArray a = (BBacnetArray)v;
                if (!a.getFixedSize())
                {
                  int size = readArraySize(vp);
                  a.setSize(size);
                }
              }
            }
          }
        }
      }
    }
    return result;
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
  protected Property addVirtualSlot(BVirtualComponent parent, String virtualPathName)
  {
    try
    {
      if (parent instanceof BBacnetVirtualProperty)
      {

      }
      else if (parent instanceof BBacnetVirtualObject)
      {
        BBacnetVirtualObject o = (BBacnetVirtualObject)parent;
        int scndx = virtualPathName.indexOf(";");
        String propertyName = (scndx > 0) ? virtualPathName.substring(0, scndx) : virtualPathName;
        String virtualPropertyName = SlotPath.escape(virtualPathName);

        // get propInfo & use that
        int propertyId = BBacnetPropertyIdentifier.ordinal(propertyName);
        PropertyInfo pi = device().getPropertyInfo(
          o.getObjectId().getObjectType(), propertyId);

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
            else if (pi.getType() != null)
            {
              spec = BTypeSpec.make(pi.getType());
              v = (BValue)spec.getInstance();
            }
            else
            {
              v = BString.make("--");
            }

            if (!pi.getFacetControl().equals("no"))
            {
              useFacets = true;
            }
          }
          catch (Exception e)
          {
            String s = "Unknown Type:" + spec + " for "
              + BBacnetObjectType.tag(o.getObjectId().getObjectType()) + ":" + propertyName;
            log.info("addVirtualSlot:" + s);
            if (log.isLoggable(Level.FINE))
            {
              log.log(Level.FINE, "Exception occurred in addVirtualSlot: ", e);
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
        // Create a new virtual object.
        return parent.add(SlotPath.escape(virtualPathName),
          makeBacnetVirtualObject(virtualPathName),
          Flags.TRANSIENT);
      }
    }
    catch (Exception e)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception occurred in addVirtualSlot: ", e);
      }
    }
    return null;
  }

  /**
   * This method is called by the virtual space's LoadCallbacks when
   * it is time for a virtual component to lazy load all of its dynamic
   * slots.  Subclasses should implement this method to
   * "discover" and dynamically add the direct children of the parent
   * virtual component instance provided.
   * <p>
   * NOTE:  Due to the possibility of a "partial" loaded state, this
   * method may be called when the parent instance already has some
   * of its dynamic child slots loaded, but not all.  So this means
   * that it is important to check for existing slots on the parent
   * before adding any new dynamic slots of the same name.
   * <p>
   * NOTE:  Due to the possibility of a partial loaded state supported by
   * virtuals, subclasses should also be aware of the subscription state.
   * This means that a loadVirtualSlots() call
   * could occur while the parent is already subscribed.  So this could affect
   * how any new virtual slots added should be handled (ie. added to a poll scheduler).
   * Subclasses should always be aware of this potential state.
   */
  public void loadVirtualSlots(BVirtualComponent parent)
  {
    //To avoid a network call when device is down. Ref: NCCB-52767.
    if (device() == null || !device().isOperational())
    {
      return;
    }

    if (parent instanceof BBacnetVirtualProperty)
    {
      return;
    }
    if (parent instanceof BBacnetVirtualObject)
    {
      loadProperties((BBacnetVirtualObject) parent);
    }
    else
    {
      loadObjects(parent);
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public void updateStatus()
  {
    BComponentSpace space = getVirtualSpace();
    if (space != null)
    {
      BComponent root = space.getRootComponent();
      if (root != null)
      {
        SlotCursor<Property> sc = root.getProperties();
        while (sc.next(BBacnetVirtualObject.class))
          ((BBacnetVirtualObject)sc.get()).updateStatus();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Get the containing device object which will poll this object.
   *
   * @return the containing BBacnetDevice
   */
  BBacnetDevice device()
  {
    return (BBacnetDevice)getParent();
  }

  /**
   * Convenience to get BacnetNetwork.
   */
  BBacnetNetwork network()
  {
    return (BBacnetNetwork)device().getNetwork();
  }

// updated to use object instead of component, but keep commented for now
//  static boolean isMsPV(BBacnetVirtualObject object, int pid)
//  {
//    if (object == null) return false;
//    if (pid == BBacnetPropertyIdentifier.PRESENT_VALUE)
//    {
//      int ot = object.getObjectId().getObjectType();
//      if (ot == BBacnetObjectType.MULTI_STATE_INPUT
//          || ot == BBacnetObjectType.MULTI_STATE_OUTPUT
//          || ot == BBacnetObjectType.MULTI_STATE_VALUE)
//        return true;
//    }
//    return false;
//  }


  /**
   * Load objects into the gateway by reading the device's Object_List property.
   *
   * @param parent
   */
  protected void loadObjects(BVirtualComponent parent)
  {
    try
    {
      BBacnetDeviceObject deviceObject = device().getConfig().getDeviceObject();
      deviceObject.readProperty(BBacnetDeviceObject.objectList);

      BBacnetObjectIdentifier[] objectList =
        deviceObject.getObjectList().getChildren(BBacnetObjectIdentifier.class);

      for (int i = 0; i < objectList.length; i++)
      {
        BBacnetObjectIdentifier objectId = objectList[i];
        String virtualPathName = objectId.toString(nameContext);

        // Skip this one if we already have it.
        String virtualObjectName = SlotPath.escape(virtualPathName);
        Property p = parent.getProperty(virtualObjectName);
        if (p == null)
        {
          p = parent.add(virtualObjectName,
            makeBacnetVirtualObject(virtualPathName),
            Flags.TRANSIENT);
        }
      }
    }
    catch (Exception e)
    {
      log.info("Unable to loadObjects in BacnetVirtualGateway!");
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception occurred in loadObjects: ", e);
      }      
    }
  }

  /**
   * Load properties for a BacnetVirtualComponent by reading all its properties.
   * This can be done with RPM(all), or by reading the list of possible properties.
   *
   * @param parent
   */
  @SuppressWarnings("unchecked")
  protected void loadProperties(BBacnetVirtualObject parent)
  {
    boolean propertiesLoaded = false;
    AsnInputStream asnIn = new AsnInputStream();
//    HashMap m = new HashMap();
    try
    {
      if (device().isServiceSupported("readPropertyMultiple"))
      {
        @SuppressWarnings("rawtypes") Vector refs = new Vector();
        refs.add(new NBacnetPropertyReference(BBacnetPropertyIdentifier.ALL));
        @SuppressWarnings("rawtypes") Vector vals = network().getBacnetComm().readPropertyMultiple(device().getAddress(),
          parent.getObjectId(),
          refs);
        @SuppressWarnings("rawtypes") Iterator it = vals.iterator();
        while (it.hasNext())
        {
          NReadPropertyResult rpr = (NReadPropertyResult)it.next();

          // Skip this one if we already have it.
          int propertyId = rpr.getPropertyId();
          String propertyName = BBacnetPropertyIdentifier.tag(propertyId);
          String virtualPropertyName = SlotPath.escape(propertyName);
          Property p = parent.getProperty(virtualPropertyName);
          if (p != null) continue;

          PropertyInfo pi = device().getPropertyInfo(
            parent.getObjectId().getObjectType(), propertyId);
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
              else if (pi.getType() != null)
              {
                spec = BTypeSpec.make(pi.getType());
                v = (BValue)spec.getInstance();
              }
              else
              {
                v = BString.make("--");
              }

              if (!pi.getFacetControl().equals("no"))
              {
                useFacets = true;
              }
            }
            catch (Exception e)
            {
              String s = "Unknown Type:" + spec + " for "
                + BBacnetObjectType.tag(parent.getObjectId().getObjectType()) + ":" + propertyName;
              log.info("addVirtualSlot:" + s);
              if (log.isLoggable(Level.FINE))
              {
                log.log(Level.FINE, "Exception occurred in loadProperties: ", e);
              }
              v = BString.make(s);
            }
          } // pi != null
          else
            v = BString.make("");

          if (!rpr.isError())
          {
            asnIn.setBuffer(rpr.getPropertyValue());
            v = BacnetVirtualUtil.readValue(asnIn, v);
          }
          else
          {
            readFault = NErrorType.toString(rpr.getErrorClass(), rpr.getErrorCode());
            v = BString.make("???");
          }
//          
//          if ((pi != null) && pi.isFacet())
//          {
//            BacnetDiscoveryUtil.addFacet(pi.getId(), rpr.getPropertyValue(), m, device());
//          }
//
//          BFacets facets = null;
//          if (useFacets)
//            facets = BFacets.make(BBacnetVirtualComponent.PROPERTY_ID, BInteger.make(rpr.getPropertyId()),
//                                  BBacnetVirtualComponent.USE_FACETS, BBoolean.TRUE);
//          else
//            facets = BFacets.make(BBacnetVirtualComponent.PROPERTY_ID, BInteger.make(rpr.getPropertyId()));

          parent.add(virtualPropertyName,
            makeBacnetVirtualProperty(propertyId, v, readFault, useFacets),
            Flags.TRANSIENT,
//                     facets,
            null);
        } // while

        propertiesLoaded = true;
//        parent.setFacets(BFacets.make(m));
      } // if rpm supp
    } // try
    catch (Exception e)
    {
      log.info("Unable to loadProperties using RPM in BacnetVirtualGateway for " + parent + ":" + e);
      if (log.isLoggable(Level.FINE))
      {
        log.log(Level.FINE, "Exception occurred in loadProperties: ", e);
      }      
    }
    if (!propertiesLoaded)
    {
      try
      {
        int[] propertyIds = device().getRequiredProperties(parent.getObjectId());
        for (int i = 0; i < propertyIds.length; i++)
        {
          // Skip this one if we already have it.
          int propertyId = propertyIds[i];
          String propertyName = BBacnetPropertyIdentifier.tag(propertyId);
          String virtualPropertyName = SlotPath.escape(propertyName);
          Property p = parent.getProperty(virtualPropertyName);
          if (p != null) continue;

          BValue v;
          String readFault = null;

          PropertyInfo pi = device().getPropertyInfo(parent.getObjectId().getObjectType(), propertyId);
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
              else if (pi.getType() != null)
              {
                spec = BTypeSpec.make(pi.getType());
                v = (BValue)spec.getInstance();
              }
              else
              {
                v = BString.make("--");
              }

              if (!pi.getFacetControl().equals("no"))
              {
                useFacets = true;
              }
            }
            catch (Exception e)
            {
              String s = "Unknown Type:" + spec + " for "
                + BBacnetObjectType.tag(parent.getObjectId().getObjectType()) + ":" + propertyName;
              log.info("addVirtualSlot:" + s);
              if (log.isLoggable(Level.FINE))
              {
                log.log(Level.FINE, "Exception occurred in loadProperties: ", e);
              }   
              v = BString.make(s);
            }
          } // pi != null
          else
            v = BString.make("");

          try
          {
            byte[] encodedValue = network().getBacnetComm().readProperty(device().getAddress(),
              parent.getObjectId(),
              propertyId);

            asnIn.setBuffer(encodedValue);
            v = BacnetVirtualUtil.readValue(asnIn, v);
          }
          catch (BacnetException e)
          {
            readFault = e.toString();
            v = BString.make("???");
          }
//          
//          if ((pi != null) && pi.isFacet())
//          {
//            BacnetDiscoveryUtil.addFacet(pi.getId(), encodedValue, m, device());
//          }
//
//          BFacets facets = null;
//          if (useFacets)
//            facets = BFacets.make(BBacnetVirtualComponent.PROPERTY_ID, BInteger.make(propertyId),
//                                  BBacnetVirtualComponent.USE_FACETS, BBoolean.TRUE);
//          else
//            facets = BFacets.make(BBacnetVirtualComponent.PROPERTY_ID, BInteger.make(propertyId));

          parent.add(virtualPropertyName,
            makeBacnetVirtualProperty(propertyId, v, readFault, useFacets),
            Flags.TRANSIENT,
//                     facets,
            null);
        }
      }
      catch (Exception e)
      {
        log.info("Unable to loadProperties using RP in BacnetVirtualGateway for " + parent + ":" + e);
        if (log.isLoggable(Level.FINE))
        {
          log.log(Level.FINE, "Exception occurred in loadProperties: ", e);
        }         
      }
    }

  }

  protected int readArraySize(BBacnetVirtualProperty vp)
  {
    try
    {
      byte[] encodedValue = network().getBacnetComm().readProperty(device().getAddress(),
        vp.object().getObjectId(),
        vp.getPropertyId(),
        0);
      return AsnUtil.fromAsnInteger(encodedValue);
    }
    catch (BacnetException e)
    {
      if (log.isLoggable(Level.FINE))
        log.log(Level.FINE, "Exception reading array size in BacnetVirtualProperty " + vp.object().getObjectId() + ":" + vp.getPropertyId() + "[" + vp.debugString(null) + "]", e);
      return 0;
    }
  }


////////////////////////////////////////////////////////////////
// LoadCallbacks
////////////////////////////////////////////////////////////////

  static class MyLoadCallbacks extends LoadCallbacks
  {
    public void loadSlots(BComponent c)
    {
      BComponentSpace space = c.getComponentSpace();
      if (space instanceof BVirtualComponentSpace)
      {
        if (c instanceof BVirtualComponent)
        {
          BVirtualGateway vGate = ((BVirtualComponentSpace)space).getVirtualGateway();
          if (vGate != null)
            vGate.loadVirtualSlots((BVirtualComponent)c);
        }
        else if (c instanceof BBacnetArray)
        {
          BBacnetArray a = (BBacnetArray)c;
          Property e0 = a.getProperty(BBacnetArray.ELEMENT_0);
          if (e0 == null)
          {
            // Add property for element0.  The actual setting of element0 is done
            // by the array whenever the size property changes.
            a.add(BBacnetArray.ELEMENT_0, BInteger.make(-1), Flags.TRANSIENT);
          }
        }
      }
    }
  }


////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    out.startProps();
    out.prop("auditWrites", auditWrites);
    out.endProps();
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  protected static final Logger log = Logger.getLogger("bacnet.virtual");


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private boolean auditWrites = false;
  private Object SLOT_LOCK = new Object();
}
