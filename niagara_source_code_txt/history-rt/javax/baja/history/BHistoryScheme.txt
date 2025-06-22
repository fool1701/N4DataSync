/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.space.BSpaceScheme;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BComplex;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.history.BHistoryDeltaQuery;

/**
 * BHistoryScheme is the ord scheme for accessing histories in
 * a history database.
 *
 * @author    John Sublett
 * @creation  05 Mar 2003
 * @version   $Revision: 18$ $Date: 8/18/09 4:28:34 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "history"
)
@NiagaraSingleton
public class BHistoryScheme
  extends BSpaceScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryScheme(1584483831)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHistoryScheme INSTANCE = new BHistoryScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor with scheme id.
   */
  protected BHistoryScheme()
  {
    super("history");
  }

  /**
   * BHistoryScheme is used to access histories in a BHistorySpace.
   */
  @Override
  public Type getSpaceType()
  {
    return BHistorySpace.TYPE;
  }

  /**
   * This method gives scheme the chance to return a custom
   * subclass of OrdQuery with a scheme specific API.  The
   * default implementation returns an instance of BasicQuery.
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new HistoryQuery(getId(), queryBody);
  }

  /**
   * This is the subclass hook for resolve after the
   * default implementation has mapped the ord to an
   * instanceof BSpace.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query, BSpace space)
  {
    HistoryQuery hq = (HistoryQuery)query;
    BHistorySpace hspace = (BHistorySpace)space;

    if (hq.isHistoryFolderQuery())
    {
      // Resolve the BHistoryFolder instance...
      String[] folderNames = hq.getHistoryFolderPath();
      BINavNode child = null;
      if ((folderNames != null) && (folderNames.length > 0))
      {
        StringBuilder navName = new StringBuilder();
        navName.append("history://").append(folderNames[0]);
        child = hspace.getNavChild(navName.toString());
        int idx = 1;
        while ((child != null) && (idx < folderNames.length))
        {
          navName.append("/").append(folderNames[idx]);
          child = child.getNavChild(navName.toString());
          idx++;
        }
      }
      return new OrdTarget(base, (BObject)child);
    }
    else if (hq.isSpaceQuery())
      return new OrdTarget(base, hspace);
    else if (hq.isDeviceQuery())
    {
      String devName = hq.getDeviceName();
      if (devName.equals("^")) devName = hspace.getDeviceName();
      else if (devName.equals("@")) // issue 8631 - also check for "@" shorthand
      { // Find the parent Niagara Station's name
        BObject baseObj = base.get();
        if (baseObj.isComplex())
        {
          BComplex comp = (BComplex)baseObj;
          while (comp != null)
          {
            if (comp instanceof com.tridium.fox.sys.NiagaraStation)
            {
              devName = ((com.tridium.fox.sys.NiagaraStation)comp).getStationName();
              break;
            }
            comp = comp.getParent();
          }
        }
      }
      return new OrdTarget(base, hspace.getDevice(devName));
    }
    else
    {
      String devName = hq.getDeviceName();
      String histName = hq.getHistoryName();

      if (devName.equals("^")) devName = hspace.getDeviceName();
      else if (devName.equals("@")) // issue 8631 - also check for "@" shorthand
      { // Find the parent Niagara Station's name
        BObject baseObj = base.get();
        if (baseObj.isComplex())
        {
          BComplex comp = (BComplex)baseObj;
          while (comp != null)
          {
            if (comp instanceof com.tridium.fox.sys.NiagaraStation)
            {
              devName = ((com.tridium.fox.sys.NiagaraStation)comp).getStationName();
              break;
            }
            comp = comp.getParent();
          }
        }
      }

      BHistoryId id = BHistoryId.make(devName, histName);
      try (HistorySpaceConnection conn = hspace.getConnection(base))
      {
        BIHistory history = conn.getHistory(id);
        if (history == null)
          throw new HistoryNotFoundException();

        if (hq.hasTimeRange())
        {
          // 8/26/05 Fix pacman issue 7381 - Using client side's timezone instead of history's collected timezone
          //BAbsTime now = BAbsTime.now(); // This was the bug!  Need to use timezone of history!
          BAbsTime now = BAbsTime.make(System.currentTimeMillis(), history.getConfig().getTimeZone()); // Fixed
          BAbsTime start = hq.getStartTime(now);
          BAbsTime end = hq.getEndTime(now);
          BObject result = null;
          if (hq.isDelta())
            result = new BHistoryDeltaQuery(history, start, end, base);
          else
          {
              result = (BObject)conn.timeQuery(history, start, end, false);
          }
          return new OrdTarget(base, result);
        }
        else if (hq.isDelta())
        {
          BHistoryDeltaQuery deltaCollection = new BHistoryDeltaQuery(history, null, null, base);
          return new OrdTarget(base, deltaCollection);
        }
        else
          return new OrdTarget(base, (BObject)history);
      }
      catch(Exception e)
      {
        throw new UnresolvedException
          ("Cannot resolve history: " + id, e);
      }
    }
  }
}
