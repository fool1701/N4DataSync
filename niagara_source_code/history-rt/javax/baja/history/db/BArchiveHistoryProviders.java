/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.baja.history.BHistoryService;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.IllegalParentException;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIRestrictedComponent;

import com.tridium.sys.schema.Fw;

/**
 * BArchiveHistoryProviders is a container for {@link BArchiveHistoryProvider}
 * instances installed in a station.
 *
 * @author Scott Hoye on 03/22/2021
 * @since Niagara 4.11
 */
@NiagaraType
public final class BArchiveHistoryProviders
  extends BComponent
  implements BIRestrictedComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.db.BArchiveHistoryProviders(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BArchiveHistoryProviders.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Lookup and return a list of any optionally installed (dynamic)
   * {@link BArchiveHistoryProvider} children. When the operationalOnly
   * parameter is true, the returned list will be filtered to contain only
   * operational BArchiveHistoryProvider children.
   *
   * @param operationalOnly When true, only operational BArchiveHistoryProvider
   *                        children will be included in the result.  When
   *                        false, all BArchiveHistoryProvider children will be
   *                        returned, even those that are currently
   *                        nonoperational.
   */
  public List<BArchiveHistoryProvider> listProviders(boolean operationalOnly)
  {
    if (operationalOnly)
    {
      List<BArchiveHistoryProvider> operationalProviders = operationalArchiveHistoryProviders;
      if (operationalProviders == null)
      {
        SlotCursor<Property> c = getProperties();
        while (c.next(BArchiveHistoryProvider.class))
        {
          BArchiveHistoryProvider provider = (BArchiveHistoryProvider)c.get();
          if (provider.isOperational())
          {
            if (operationalProviders == null)
            {
              operationalProviders = new ArrayList<>();
            }
            operationalProviders.add(provider);
          }
        }

        if (operationalProviders == null)
        {
          operationalProviders = Collections.emptyList();
        }
        else
        {
          operationalProviders = Collections.unmodifiableList(operationalProviders);
        }
        operationalArchiveHistoryProviders = operationalProviders;
      }
      return operationalProviders;
    }
    else
    {
      List<BArchiveHistoryProvider> allProviders = allArchiveHistoryProviders;
      if (allProviders == null)
      {
        SlotCursor<Property> c = getProperties();
        while (c.next(BArchiveHistoryProvider.class))
        {
          if (allProviders == null)
          {
            allProviders = new ArrayList<>();
          }
          allProviders.add((BArchiveHistoryProvider)c.get());
        }

        if (allProviders == null)
        {
          allProviders = Collections.emptyList();
        }
        else
        {
          allProviders = Collections.unmodifiableList(allProviders);
        }
        allArchiveHistoryProviders = allProviders;
      }
      return allProviders;
    }
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Only allow {@link BArchiveHistoryProvider} children
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return child instanceof BArchiveHistoryProvider;
  }

////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    if (x == Fw.DESCENDANTS_STARTED || (isRunning() && (x == Fw.REORDERED ||
        ((x == Fw.ADDED || x == Fw.CHANGED) &&
         a instanceof Property &&
         get((Property) a) instanceof BArchiveHistoryProvider) ||
        (x == Fw.REMOVED && b instanceof BArchiveHistoryProvider))))
    {
      // Clear/Reload BArchiveHistoryProvider cache
      allArchiveHistoryProviders = null;
      operationalArchiveHistoryProviders = null;
      listProviders(false);
      listProviders(true);
    }

    return super.fw(x, a, b, c, d);
  }

////////////////////////////////////////////////////////////////
// BIRestrictedComponent
////////////////////////////////////////////////////////////////

  /**
   * Prevent multiple BArchiveHistoryProviders container instances and ensure
   * only one (frozen) container can live under the BHistoryService.
   */
  @Override
  public void checkParentForRestrictedComponent(BComponent parent, Context cx)
  {
    if (!parent.getType().is(BHistoryService.TYPE))
    {
      throw new IllegalParentException("baja", "IllegalParentException.parentAndChild",
        new Object[] { parent.getType(), getType() });
    }
    BIRestrictedComponent.checkForDuplicates(parent, this, /*allowNonExactTypes*/false);
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon()
  {
    BValue dynamic = get("icon");
    if (dynamic instanceof BIcon)
    {
      return (BIcon) dynamic;
    }
    return ICON;
  }

  private static final BIcon ICON = BIcon.make(BIcon.std("historyFolder.png"),
    BIcon.std("badges/db.png"));

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private List<BArchiveHistoryProvider> allArchiveHistoryProviders;
  private List<BArchiveHistoryProvider> operationalArchiveHistoryProviders;
}
