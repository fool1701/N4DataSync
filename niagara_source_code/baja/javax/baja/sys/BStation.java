/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.agent.*;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.*;

import com.tridium.sys.schema.SimpleType;
import com.tridium.sys.station.*;

/**
 * BStation is always the root component of a running station.
 *
 * @author    Brian Frank       
 * @creation  25 Oct 01
 * @version   $Revision: 22$ $Date: 1/3/11 2:36:00 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 This is the station name which uniquely identifies this
 station within its federation of stations on the network.
 This property is fixed at station launch by the station's
 home directory.
 */
@NiagaraProperty(
  name = "stationName",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY | Flags.OPERATOR
)
/*
 This holds the {@code sysInfo} key/value pairs.  Each key/value pair
 in {@code sysInfo} will be injected into the hello message during
 Fox session setup.  The following keys are currently defined and
 supported by Niagara:<p>
 <dl>
 <dt><b>realms</b></dt>
 <dd>A ';' seperated list of single
 sign-on realm names for the station.  If a user has successfully
 logged on to some set of stations ("connected" stations),
 and then attempts to connect to another station ("target" station),
 then he will be automatically logged in
 so long as the following conditions are met:
 <br>
 <ol><li>The target station has at least one SSO realm name that is
 a member of the set defined by the union of all the
 connected station SSO realm names.</li>
 <li>The credentials for the user on the target station match those
 used to login to the already connected station.</li>
 </ol><p>
 When multiple connected stations match the target station's
 SSO realm names, then the credentials from the most recent station
 connection are used.</dd>
 </dl>
 */
@NiagaraProperty(
  name = "sysInfo",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
/*
 The one (and only) service container for this station.  Special
 services can only live in this container, while other services
 can live anywhere in the station.
 */
@NiagaraProperty(
  name = "Services",
  type = "BServiceContainer",
  defaultValue = "new BServiceContainer()"
)
/*
 Save the current state of the system to
 persistent storage.
 */
@NiagaraAction(
  name = "save"
)
public final class BStation
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BStation(1280372827)1.0$ @*/
/* Generated Mon Nov 21 08:51:35 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "stationName"

  /**
   * Slot for the {@code stationName} property.
   * This is the station name which uniquely identifies this
   * station within its federation of stations on the network.
   * This property is fixed at station launch by the station's
   * home directory.
   * @see #getStationName
   * @see #setStationName
   */
  public static final Property stationName = newProperty(Flags.READONLY | Flags.OPERATOR, "", null);

  /**
   * Get the {@code stationName} property.
   * This is the station name which uniquely identifies this
   * station within its federation of stations on the network.
   * This property is fixed at station launch by the station's
   * home directory.
   * @see #stationName
   */
  public String getStationName() { return getString(stationName); }

  /**
   * Set the {@code stationName} property.
   * This is the station name which uniquely identifies this
   * station within its federation of stations on the network.
   * This property is fixed at station launch by the station's
   * home directory.
   * @see #stationName
   */
  public void setStationName(String v) { setString(stationName, v, null); }

  //endregion Property "stationName"

  //region Property "sysInfo"

  /**
   * Slot for the {@code sysInfo} property.
   * This holds the {@code sysInfo} key/value pairs.  Each key/value pair
   * in {@code sysInfo} will be injected into the hello message during
   * Fox session setup.  The following keys are currently defined and
   * supported by Niagara:<p>
   * <dl>
   * <dt><b>realms</b></dt>
   * <dd>A ';' seperated list of single
   * sign-on realm names for the station.  If a user has successfully
   * logged on to some set of stations ("connected" stations),
   * and then attempts to connect to another station ("target" station),
   * then he will be automatically logged in
   * so long as the following conditions are met:
   * <br>
   * <ol><li>The target station has at least one SSO realm name that is
   * a member of the set defined by the union of all the
   * connected station SSO realm names.</li>
   * <li>The credentials for the user on the target station match those
   * used to login to the already connected station.</li>
   * </ol><p>
   * When multiple connected stations match the target station's
   * SSO realm names, then the credentials from the most recent station
   * connection are used.</dd>
   * </dl>
   * @see #getSysInfo
   * @see #setSysInfo
   */
  public static final Property sysInfo = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code sysInfo} property.
   * This holds the {@code sysInfo} key/value pairs.  Each key/value pair
   * in {@code sysInfo} will be injected into the hello message during
   * Fox session setup.  The following keys are currently defined and
   * supported by Niagara:<p>
   * <dl>
   * <dt><b>realms</b></dt>
   * <dd>A ';' seperated list of single
   * sign-on realm names for the station.  If a user has successfully
   * logged on to some set of stations ("connected" stations),
   * and then attempts to connect to another station ("target" station),
   * then he will be automatically logged in
   * so long as the following conditions are met:
   * <br>
   * <ol><li>The target station has at least one SSO realm name that is
   * a member of the set defined by the union of all the
   * connected station SSO realm names.</li>
   * <li>The credentials for the user on the target station match those
   * used to login to the already connected station.</li>
   * </ol><p>
   * When multiple connected stations match the target station's
   * SSO realm names, then the credentials from the most recent station
   * connection are used.</dd>
   * </dl>
   * @see #sysInfo
   */
  public BFacets getSysInfo() { return (BFacets)get(sysInfo); }

  /**
   * Set the {@code sysInfo} property.
   * This holds the {@code sysInfo} key/value pairs.  Each key/value pair
   * in {@code sysInfo} will be injected into the hello message during
   * Fox session setup.  The following keys are currently defined and
   * supported by Niagara:<p>
   * <dl>
   * <dt><b>realms</b></dt>
   * <dd>A ';' seperated list of single
   * sign-on realm names for the station.  If a user has successfully
   * logged on to some set of stations ("connected" stations),
   * and then attempts to connect to another station ("target" station),
   * then he will be automatically logged in
   * so long as the following conditions are met:
   * <br>
   * <ol><li>The target station has at least one SSO realm name that is
   * a member of the set defined by the union of all the
   * connected station SSO realm names.</li>
   * <li>The credentials for the user on the target station match those
   * used to login to the already connected station.</li>
   * </ol><p>
   * When multiple connected stations match the target station's
   * SSO realm names, then the credentials from the most recent station
   * connection are used.</dd>
   * </dl>
   * @see #sysInfo
   */
  public void setSysInfo(BFacets v) { set(sysInfo, v, null); }

  //endregion Property "sysInfo"

  //region Property "Services"

  /**
   * Slot for the {@code Services} property.
   * The one (and only) service container for this station.  Special
   * services can only live in this container, while other services
   * can live anywhere in the station.
   * @see #getServices
   * @see #setServices
   */
  public static final Property Services = newProperty(0, new BServiceContainer(), null);

  /**
   * Get the {@code Services} property.
   * The one (and only) service container for this station.  Special
   * services can only live in this container, while other services
   * can live anywhere in the station.
   * @see #Services
   */
  public BServiceContainer getServices() { return (BServiceContainer)get(Services); }

  /**
   * Set the {@code Services} property.
   * The one (and only) service container for this station.  Special
   * services can only live in this container, while other services
   * can live anywhere in the station.
   * @see #Services
   */
  public void setServices(BServiceContainer v) { set(Services, v, null); }

  //endregion Property "Services"

  //region Action "save"

  /**
   * Slot for the {@code save} action.
   * Save the current state of the system to
   * persistent storage.
   * @see #save()
   */
  public static final Action save = newAction(0, null);

  /**
   * Invoke the {@code save} action.
   * Save the current state of the system to
   * persistent storage.
   * @see #save
   */
  public void save() { invoke(save, null, null); }

  //endregion Action "save"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStation.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  /**
   * Save the system state to persistent storage.
   */
  public void doSave(Context cx)
    throws Exception
  {
    Station.saveAsync(cx);
  }

////////////////////////////////////////////////////////////////
// Display Name
////////////////////////////////////////////////////////////////

  /**
   * Check for a dynamic BFormat property that specifies the
   * display name for the station.  If the property
   * exists, the format is resolved using the station
   * root as the base. If the property does not exist,
   * the station name is returned.
   */
  /**
   * Check for a dynamic BFormat property that specifies the
   * display name for the station. If the property
   * exists, the format is resolved using the station
   * root as the base. If the property does not exist,
   * the station name is returned.
   * @param cx
   * @return null if the passed in BFormat or value is empty. Return a valid string otherwise
   */
  public String getStationDisplayName(Context cx)
  {
    BValue displayName = get("displayName");
    if (displayName == null)
      return getStationName();
    else
    {
      if (displayName instanceof BString)
      {
        String val = ((BString)displayName).getString();
        if (val.isEmpty())
          return null;
        else
          return val;
      }
      else if (displayName instanceof BFormat)
      {
        BFormat format = (BFormat)displayName;
        if (format.getFormat().isEmpty())
          return null;
        else
        {
          try
          {
            String val = format.format(this, cx);
            if (val.isEmpty())
              return null;
            else
              return val;
          }
          catch(Exception ex)
          {
            return "Error: " + ex.getClass().getName() + ": " + ex.getMessage();
          }
        }
      }
      else
        return "Error: displayName must be one of { BString, BFormat }";
    }
  }


////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/systemDbService.png");

  @Override
  public final boolean isParentLegal(BComponent parent)
  { // There can only be one BStation as the root component of the component space
    return false;
  }

  @Override
  public void changed(Property property, Context cx)
  {
    if (isRunning() && property.equals(sysInfo))
      checkInternFacet();

    super.changed(property, cx);
  }

  @Override
  public void started()
    throws Exception
  {
    checkInternFacet();
    super.started();
  }

  private void checkInternFacet()
  {
    boolean internDisabled = getSysInfo().getb("disableSimpleIntern", SimpleType.internDisabledOnVm);
    SimpleType.setInterningEnabled(!internDisabled);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    
    list.toBottom("wbutil:MetadataBrowser");
    
    return list;  
  }

}
