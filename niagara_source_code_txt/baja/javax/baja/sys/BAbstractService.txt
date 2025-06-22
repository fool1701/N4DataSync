/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.license.BILicensed;
import javax.baja.license.Feature;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;

/**
 * BAbstractService provides a basic template for building
 * consistent service components.
 *
 * @author    Brian Frank
 * @creation  8 Jan 04
 * @version   $Revision: 8$ $Date: 4/7/06 10:15:30 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.ok",
  flags = Flags.READONLY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
public abstract class BAbstractService
  extends BComponent
  implements BIService, BIStatus, BILicensed
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BAbstractService(191856533)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY | Flags.TRANSIENT, BStatus.ok, null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.READONLY | Flags.TRANSIENT, "", null);

  /**
   * Get the {@code faultCause} property.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// IService
////////////////////////////////////////////////////////////////

  /**
   * Service started callback.
   */
  @Override
  public void serviceStarted()
    throws Exception
  {
  }

  /**
   * Service stopped callback.
   */
  @Override
  public void serviceStopped()
    throws Exception
  {
  }

////////////////////////////////////////////////////////////////
// Status
////////////////////////////////////////////////////////////////

  /**
   * Return true if the service is disabled.  A service is
   * disabled if the user has manually set the enabled property
   * to false.
   */
  public final boolean isDisabled()
  {
    return getStatus().isDisabled();
  }

  /**
   * Return true if the service is in fault.  A service
   * is in fault if either a fatal fault was detected or if
   * {@code configFail()} has been called more recently
   * than {@code configOk()}.  Refer to {@code faultCause}
   * for the fault reason.
   */
  public final boolean isFault()
  {
    return getStatus().isFault();
  }

  /**
   * Return if the service is neither disabled, nor in fault.
   */
  public final boolean isOperational()
  {
    BStatus status = getStatus();
    return !isFatalFault() && !status.isDisabled() && !status.isFault();
  }

  /**
   * Update status is called to recompute the status
   * property based on the current enable and fault states.
   */
  public void updateStatus()
  {
    boolean oldEnabled = !isDisabled();
    int oldStatus = getStatus().getBits();
    int newStatus = 0;

    // ... disabled bit
    if (!getEnabled())
    {
      newStatus |= BStatus.DISABLED;
    }
    else
    {
      newStatus &= ~BStatus.DISABLED;
    }

    // ... fault bit
    if (fatalFault || configFault)
    {
      newStatus |= BStatus.FAULT;
    }
    else
    {
      newStatus &= ~BStatus.FAULT;
    }

    // short circuit if nothing has changed since last time
    if (oldStatus == newStatus)
    {
      return;
    }
    setStatus(BStatus.make(newStatus));

    // check for enabled/disabled callbacks
    boolean newEnabled = !isDisabled();
    if (oldEnabled != newEnabled)
    {
      if (newEnabled)
      {
        enabled();
      }
      else
      {
        disabled();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Fault
////////////////////////////////////////////////////////////////

  /**
   * Return true if the service detected a fatal fault.
   * Fatal faults cannot be recovered until the service
   * is restarted.  Fatal faults trump config faults.
   */
  public final boolean isFatalFault()
  {
    return fatalFault;
  }

  /**
   * Clear the configuration fault status.  If there are no fatal
   * faults then clear the entire service's fault status, otherwise
   * the service remains in fault.
   */
  public final void configOk()
  {
    // clear config fault flag
    configFault = false;

    // fatal faults always trump
    if (fatalFault)
    {
      return;
    }

    // update props
    setFaultCause("");
    updateStatus();
  }

  /**
   * Set the service into configuration fault.  If the service was
   * previously not in fault, then this sets the service into fault.
   */
  public final void configFail(String cause)
  {
    // set config fault flag
    configFault = true;

    // fatal faults always trump
    if (fatalFault)
    {
      return;
    }

    // update props
    setFaultCause(cause);
    updateStatus();
  }

  /**
   * Set the service into the fatal fault condition.  Unlike
   * configFail(), the fatal fault condition cannot be cleared
   * until station restart.
   */
  public final void configFatal(String cause)
  {
    fatalFault = true;
    setFaultCause(cause);
    updateStatus();
  }

////////////////////////////////////////////////////////////////
// Licensing
////////////////////////////////////////////////////////////////

  /**
   * If this service is to be licensed using the standard licensing
   * mechanism then override this method to return the Feature or
   * return null for no license checks.  Convention is that the
   * vendor and feature name matches the declaring module.
   */
  @Override
  public Feature getLicenseFeature()
  {
    return null;
  }

  private void checkLicense()
  {
    try
    {
      // check feature
      Feature feature = getLicenseFeature();
      if (feature == null)
      {
        return;
      }

      feature.check();


      // use the feature to map to a global limit pool
      // so that you can't by-pass limits by using multiple
      // network instances in the same station
      String globalKey = feature.getVendorName() + ":" + feature.getFeatureName();
      globalKey = TextUtil.toLowerCase(globalKey);
      limits = globalLimits.get(globalKey);
      if (limits != null)
      {
        return;
      }

      // this is the first pass for this specific feature,
      // so we need to create a new HashMap for the global pool
      limits = new HashMap<>();
      globalLimits.put(globalKey, limits);

      // map all the *.limit properties to LicenseLimit itels
      for (String key : feature.list())
      {
        // process all *.limit properties
        if (!key.endsWith(".limit"))
        {
          continue;
        }

        // parse limit
        String val = feature.get(key);
        int limit = Integer.MAX_VALUE;
        if (val != null && !TextUtil.toLowerCase(val).equals("none"))
        {
          limit = Integer.parseInt(val);
        }

        // store in a hashtable
        LicenseLimit lic = new LicenseLimit();
        lic.key = key;
        lic.used = 0;
        lic.limit = limit;
        limits.put(key, lic);
      }
    }
    catch(Exception e)
    {
      fatalFault = true;
      Logger.getLogger("service").log(Level.SEVERE, "Unlicensed: " + toPathString(), e);
      setFaultCause("Unlicensed: " + e);
    }
  }



  /**
   * Get the count for the current key
   */
  private int getLicenseCount(String key)
  {
    if(limits == null)
    {
      return 0;
    }
    synchronized(limits)
    {
      LicenseLimit lic = limits.get(key);
      // if not specified, consider it used as of 0
      if (lic == null)
      {
        return 0;
      }

      return lic.used;
    }
  }

  /**
   * Increment the limit counter and return null if the specified
   * limit is ok, otherwise return the fault cause message.
   */
  private String checkLicenseLimit(String key)
  {
    if(limits == null)
    {
      return null;
    }
    synchronized(limits)
    {
      LicenseLimit lic = limits.get(key);

      // if not specified, consider it a limit of 0
      if(lic == null)
      {
        return "Unlicensed: " + key;
      }

      // increment used and check if we have exceeded capacity
      lic.used++;
      if(lic.used > lic.limit)
      {
        return "Exceeded " + lic.key + " of " + lic.limit;
      }

      // everything is a-okay
      return null;
    }
  }

  static class LicenseLimit
  {
    String key;
    int limit;
    int used;
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  /**
   * This method is called when moving from disabled
   * state into the enabled state.
   */
  protected void enabled()
  {
  }

  /**
   * This method is called when moving from enabled
   * state into the disabled state.
   */
  protected void disabled()
  {
  }

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////


  @Override
  public void checkSetFlags(Slot slot, int flags, Context context)
  {
    super.checkSetFlags(slot, flags, context);
    if (context != Context.decoding)
    {
      if (slot.equals(status) || slot.equals(faultCause))
      {
        // NCCB-16609: Make sure someone can't strip the read-only
        // bit from our fault cause
        if (!((Flags.READONLY & flags) == Flags.READONLY))
        {
          throw new LocalizableRuntimeException("baja", "cannotChangeReadOnly", new Object[] { slot.getName() });
        }
      }
    }
  }

  /**
   * Framework use only.
   */
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.SERVICE_STARTED:      fwServiceStarted(); break;
      case Fw.DESCENDANTS_STARTED:  fwDescendantsStarted(); break;
      case Fw.STOPPED:              updateStatus(); break;
      case Fw.CHANGED:              fwChanged((Property)a); break;
      case Fw.CHECK_LICENSE_LIMIT:  return checkLicenseLimit((String)a);
      case Fw.GET_LICENSE_COUNT:    return getLicenseCount((String) a);
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwServiceStarted()
  {
    checkLicense();
    updateStatus();
  }

  private void fwDescendantsStarted()
  {
    updateStatus();
  }

  private void fwChanged(Property prop)
  {
    if (!isRunning())
    {
      return;
    }

    if (prop.equals(enabled))
    {
      updateStatus();
    }
    
    if (prop.equals(status))
    {
      if (!getStatus().isFault() && fatalFault)
      {
        updateStatus();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private boolean fatalFault;
  private boolean configFault;

  private static HashMap<String,HashMap<String,LicenseLimit>> globalLimits = new HashMap<>();
  private HashMap<String,LicenseLimit> limits;

}
