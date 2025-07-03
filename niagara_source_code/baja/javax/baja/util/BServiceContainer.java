/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.space.Mark;
import javax.baja.sys.*;

import com.tridium.sys.schema.Fw;
import com.tridium.util.CompUtil;
import com.tridium.util.ObjectUtil;

/**
 * BServiceContainer is used by convention to store all
 * the BIServices (except drivers) in a station database.
 *
 * @author    Brian Frank
 * @creation  18 Dec 03
 * @version   $Revision: 3$ $Date: 1/21/04 12:46:02 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BServiceContainer
  extends BComponent
  implements BIService, BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BServiceContainer(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BServiceContainer.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public Type[] getServiceTypes() { return new Type[] { TYPE }; }
  @Override
  public void serviceStarted() throws Exception {}
  @Override
  public void serviceStopped() throws Exception {}

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("services.png");

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Only one BServiceContainer is allowed as the frozen slot of the BStation, and then
   * sub-ServiceContainers are allowed only as descendants of the frozen one.
   * Only Super Users are allowed to add an instance of this type to the station.
   */
  @Override
  public final void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    BIRestrictedComponent.checkContextForSuperUser(this, cx);
    Property prop = getPropertyInParent();
    BComponentSpace space = parent.getComponentSpace();
    if (space == null || !space.isProxyComponentSpace())
    {
      if (!isParentAncestryLegal(getStation(parent), parent, prop))
      {
        throw new IllegalChildException("baja", "RestrictedToServiceContainer",
            new Object[]{parent.getType(), getType()});
      }
    }
  }

  private static boolean isParentAncestryLegal(BStation station, BComplex parent, Property prop)
  { // To be a legal parent, it must be the station's frozen ServiceContainer, or a sub
    // ServiceContainer of it.
    if ((station == null) || (parent == null) || (prop == null))
      return false;

    if (parent.getType().equals(TYPE))
      return isParentAncestryLegal(station, parent.getParent(), parent.getPropertyInParent());
    else if ((parent == station) && BStation.Services.equals(prop))
      return true;

    return false;
  }

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  @Override
  public final boolean completesStarted()
  {
    BComponent parent = getParent().asComponent();
    Property propInParent = getPropertyInParent();
    return !isParentAncestryLegal(getStation(parent), parent, propInParent);
  }

  /**
   * Framework use only.
   */
  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.STARTED:
      case Fw.SERVICE_STARTED:
        Logger logger = log;
        if (a instanceof Logger)
          logger = (Logger)a;
        fwServiceStarted(logger);
        break;
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwServiceStarted(Logger logger)
  {
    BComponent parent = getParent().asComponent();
    Property propInParent = getPropertyInParent();
    BStation station = getStation(parent);
    if (!isParentAncestryLegal(station, parent, propInParent))
    { // This service container is not allowed, so we must remove it.
      boolean remove = true;
      try
      {
        if (!Sys.isStationStarted())
        { // Indicates the special case in ServiceManager (and the migration tool) where an
          // illegal ServiceContainer is given this callback first in order to give it a chance
          // to move its contents to the frozen ServiceContainer on BStation
          BServiceContainer serviceContainer = station.getServices();
          ArrayList<BObject> values = new ArrayList<>();
          ArrayList<String> names = new ArrayList<>();
          ArrayList<Integer> flags = new ArrayList<>();
          ArrayList<BFacets> facets = new ArrayList<>();

          if ((parent == station) && (serviceContainer.getDynamicPropertiesArray().length == 0))
          { // Indicates the case where it wasn't named "Services", so we should set the display
            // name on the frozen one and move the contents to the frozen one (deleting the old).
            BFormat displayNameFormat = station.getDisplayNameFormat(propInParent);
            if (displayNameFormat == null)
              displayNameFormat = BFormat.make(getDisplayName(null));
            station.setDisplayName(BStation.Services, displayNameFormat, null);

            Property[] props = getDynamicPropertiesArray();
            int len = props.length;
            if (len > 0)
            {
              for (int i = 0; i < len; i++)
              {
                Property property = props[i];
                String name = property.getName();
                BValue value = get(property);
                int slotFlags = getFlags(property);
                BFacets slotFacets = getSlotFacets(property);

                if (!value.isComplex())
                {
                  if (name.equals("displayNames") && value.getType().is(BNameMap.TYPE))
                  { // Display names must be merged (rare use case of multiple ServiceContainers)
                    BValue existing = serviceContainer.get("displayNames");
                    if ((existing != null) && existing.getType().is(BNameMap.TYPE))
                      value = BNameMap.make((BNameMap)existing, (BNameMap)value);
                  }
                  CompUtil.setOrAdd(serviceContainer, name, value, slotFlags, slotFacets, null);
                }
                else
                {
                  names.add(name);
                  values.add(value);
                  flags.add(slotFlags);
                  facets.add(slotFacets);
                  if (value.isComponent())
                    logger.warning("The component at '" + value.asComponent().toDisplayPathString(null) +
                      "' is located in an illegal dynamic ServiceContainer. It is being " +
                      "moved to a new location under the station's frozen Services container.");
                }
              }
            }
          }
          else
          {
            remove = false;
            names.add(propInParent.getName());
            values.add(this);
            flags.add(parent.getFlags(propInParent));
            facets.add(parent.getSlotFacets(propInParent));
            logger.warning("The ServiceContainer at '" + toDisplayPathString(null) +
              "' is located in an illegal location. It is being " +
              "moved to a new location under the station's frozen Services container.");
          }

          BObject[] vals = values.toArray(new BObject[values.size()]);
          String[] propNames = names.toArray(new String[names.size()]);
          new Mark(vals, propNames).moveTo(serviceContainer, null);

          for (int i = 0; i < facets.size(); i++)
          {
            Property property = serviceContainer.getProperty(names.get(i));
            serviceContainer.setFlags(property, flags.get(i));
            serviceContainer.setFacets(property, facets.get(i));
          }
        }
      }
      catch(Throwable t)
      {
        logger.log(Level.SEVERE, "Error during ServiceContainer migration.", t);
      }
      finally
      {
        if (remove)
        {
          logger.warning("Found an illegal dynamic ServiceContainer at '" +
            toDisplayPathString(null) + "'. It will now be removed.");
          parent.remove(propInParent);
        }
      }

      new IllegalParentException("baja", "IllegalParentException.parentAndChild",
                                 new Object[] { parent.getType(),  getType() });
    }
  }

  private BStation getStation(BComponent parent)
  {
    BComponentSpace space = parent.getComponentSpace();
    if (space != null)
      return ObjectUtil.getStation(space);

    if (parent instanceof BStation)
      return (BStation) parent;

    return null;
  }

  static final Logger log = Logger.getLogger("sys.service");

}
