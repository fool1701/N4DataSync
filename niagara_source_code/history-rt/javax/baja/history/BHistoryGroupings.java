/**
 * Copyright 2009 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import java.util.Map;

import javax.baja.history.db.BHistoryDatabase;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInterface;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;

/**
 * BHistoryGroupings is used to store a list of history groups
 * that define the organization of histories under
 * the history space in the nav tree.
 *
 * @author    Scott Hoye
 * @creation  15 Sep 09
 * @version   $Revision: 1$ $Date: 10/2/09 12:43:15 PM EDT$
 * @since     Niagara 3.5
 */
@NiagaraType
public final class BHistoryGroupings
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryGroupings(2979906276)1.0$ @*/
/* Generated Thu Jan 27 19:02:05 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryGroupings.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Only allow history group children
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return (child instanceof BHistoryGroup);
  }

  /**
   * Callback when a property (or possibly a descendent of
   * that property) is modified on this component via
   * one of the <code>set</code> methods.
   */
  @Override
  public void changed(Property property, Context context)
  {
    super.changed(property, context);

    if (isRunning() && property.getType().is(BHistoryGroup.TYPE))
    {
      // Whenever changed, clear the history space's folder cache
      BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
      BHistoryDatabase space = service.getDatabase();
      if (space != null)
      {
        @SuppressWarnings("unchecked") Map<BInterface, Map<String,BINavNode>> folderCache =
          (Map<BInterface, Map<String,BINavNode>>)space.fw(Fw.USER_DEFINED_4);
        if (folderCache != null) folderCache.clear();
      }
    }
  }

  /**
   * Called when an existing property is removed from this
   * component via one of the <code>remove</code> methods.
   */
  @Override
  public void removed(Property property, BValue oldValue, Context context)
  {
    super.removed(property, oldValue, context);

    if (isRunning() && oldValue.getType().is(BHistoryGroup.TYPE))
    {
      // Whenever removed, clear the history space's folder cache
      BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
      BHistoryDatabase space = service.getDatabase();
      if (space != null)
      {
        @SuppressWarnings("unchecked") Map<BInterface, Map<String,BINavNode>> folderCache =
          (Map<BInterface, Map<String,BINavNode>>)space.fw(Fw.USER_DEFINED_4);
        if (folderCache != null) folderCache.clear();
      }
    }
  }

  /**
   * Called when an existing property is renamed via one
   * of the <code>rename</code> methods.
   */
  @Override
  public void renamed(Property property, String oldName, Context context)
  {
    super.renamed(property, oldName, context);

    if (isRunning() && property.getType().is(BHistoryGroup.TYPE))
    {
      // Whenever renamed, clear the history space's folder cache
      BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
      BHistoryDatabase space = service.getDatabase();
      if (space != null)
      {
        @SuppressWarnings("unchecked") Map<BInterface, Map<String,BINavNode>> folderCache =
          (Map<BInterface, Map<String,BINavNode>>)space.fw(Fw.USER_DEFINED_4);
        if (folderCache != null) folderCache.clear();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  private static final BIcon icon = BIcon.std("historyProperties.png");
  @Override
  public BIcon getIcon() { return icon; }
}
