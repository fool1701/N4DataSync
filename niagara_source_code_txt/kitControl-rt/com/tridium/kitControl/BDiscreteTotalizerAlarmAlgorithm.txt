/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitControl;

import java.util.*;

import javax.baja.alarm.*;
import javax.baja.alarm.ext.*;
import javax.baja.control.*;
import javax.baja.control.enums.*;
import javax.baja.control.ext.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BDiscreteTotalizerAlarmAlgorithm implements a standard out-of-range
 * alarming algorithm
 *
 * @author    Andy Saunders
 * @creation  19 Nov 2004
 * @version   $Revision: 22$ $Date: 3/30/2004 3:31:03 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This is the DiscreteTotalizerExt used for this object.
 */
@NiagaraProperty(
  name = "discreteTotalizerSelect",
  type = "BExtensionName",
  defaultValue = "new BExtensionName(\"DiscreteTotalizerExt\")"
)
public abstract class BDiscreteTotalizerAlarmAlgorithm
  extends javax.baja.alarm.ext.offnormal.BTwoStateAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.BDiscreteTotalizerAlarmAlgorithm(3867921222)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "discreteTotalizerSelect"

  /**
   * Slot for the {@code discreteTotalizerSelect} property.
   * This is the DiscreteTotalizerExt used for this object.
   * @see #getDiscreteTotalizerSelect
   * @see #setDiscreteTotalizerSelect
   */
  public static final Property discreteTotalizerSelect = newProperty(0, new BExtensionName("DiscreteTotalizerExt"), null);

  /**
   * Get the {@code discreteTotalizerSelect} property.
   * This is the DiscreteTotalizerExt used for this object.
   * @see #discreteTotalizerSelect
   */
  public BExtensionName getDiscreteTotalizerSelect() { return (BExtensionName)get(discreteTotalizerSelect); }

  /**
   * Set the {@code discreteTotalizerSelect} property.
   * This is the DiscreteTotalizerExt used for this object.
   * @see #discreteTotalizerSelect
   */
  public void setDiscreteTotalizerSelect(BExtensionName v) { set(discreteTotalizerSelect, v, null); }

  //endregion Property "discreteTotalizerSelect"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDiscreteTotalizerAlarmAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BDiscreteTotalizerAlarmAlgorithm's grandparent must be a
   * BBooleanPoint
   */
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return (grandparent instanceof BBooleanPoint);
  }

  public void changed(Property p, Context c)
  {
    if(isRunning())
    {
      if(p.equals(discreteTotalizerSelect))
      {
        totalizerExt = getTotalizerExt();
        return;
      }
    }
    super.changed(p, c);
  }

////////////////////////////////////////////////////////////////
//  property facet checking
////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
//  Algorithm implementation
////////////////////////////////////////////////////////////////

  /**
   * Return true if the present value is normal
   */
  protected boolean isNormal(BStatusValue out)
  {
    BBooleanPoint point = (BBooleanPoint)getParentPoint();
    totalizerExt = getTotalizerExt();
    if(totalizerExt == null)
    {
      flagSetupError(true);
      return true;
    }
    flagSetupError(false);

    return isNormal(totalizerExt);
  }

  /**
  * must be overridden by subclass
  */
  protected boolean isNormal(BDiscreteTotalizerExt totalExt)
  {
    return true;
  }

  /**
   *  Write the key-value pairs defining alarm data for the
   *  alarm algorithm and state to the given Facets.
   * <p>
   *  The alarm data for an Out of Range alarm is given by
   *  BACnet table 13-3, Standard Object Property Values
   *  returned in notifications.
   *
   * @param cp The relevant control point.
   * @param map The map.
   */
  @SuppressWarnings({"rawtypes","unchecked"})
  @Override
  public void writeAlarmData(BStatusValue out, java.util.Map map)
  {
    BBooleanPoint point = (BBooleanPoint)getParentPoint();

    map.put(BAlarmRecord.PRESENT_VALUE, BString.make(getAlarmPresentValueString(totalizerExt)));
    map.put(BAlarmRecord.ERROR_LIMIT, BString.make(getAlarmErrorLimitString()));
  }

  protected String getAlarmPresentValueString(BDiscreteTotalizerExt totalizerExt)
  {
    return "";
  }

  protected String getAlarmErrorLimitString()
  {
    return "";
  }


  protected BDiscreteTotalizerExt getTotalizerExt()
  {
    String totalizerName = getDiscreteTotalizerSelect().getExtensionName();
    BControlPoint point = getParentPoint();
    if(point == null) return null;
    return (BDiscreteTotalizerExt)point.get(totalizerName);
  }

  private void flagSetupError(boolean isError)
  {
    BAlarmSourceExt almExt = getParentExt();
    if(isError)
    {
      BString setupError = (BString)almExt.get("setupError");
      if(setupError == null)
      {
        almExt.add("setupError", BString.make(SETUP_ERROR), Flags.TRANSIENT);
      }
    }
    else
    {
      try {almExt.remove("setupError"); } catch(Exception e){}
    }
    
  }


  private BDiscreteTotalizerExt[] totalizerExtensions;
  BDiscreteTotalizerExt totalizerExt = null;

  private static String SETUP_ERROR = "Need DiscreteTotalizerExt for this alarm extension to function!";
}
