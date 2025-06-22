/**
 * Copyright 2009 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

import java.util.Map;

import javax.baja.history.db.BHistoryDatabase;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BInterface;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BNameList;

import com.tridium.sys.schema.Fw;

/**
 * BHistoryGroup is used to specify a history group definition for
 * organizing histories in the nav tree.
 *
 * @author    Scott Hoye
 * @creation  15 Sep 09
 * @version   $Revision: 1$ $Date: 10/2/09 12:43:15 PM EDT$
 * @since     Niagara 3.5
 */
@NiagaraType
/*
 Enabled is used to manually enable/disable this history group.
 */
@NiagaraProperty(
  name = "enabled",
  type = "boolean",
  defaultValue = "true"
)
/*
 The names of history properties to organize histories by for this history group.
 */
@NiagaraProperty(
  name = "historyPropertiesToGroupBy",
  type = "BNameList",
  defaultValue = "BNameList.DEFAULT",
  facets = @Facet(name = "BFacets.FIELD_EDITOR", value = "\"workbench:GenericNameListFE\"")
)
public final class BHistoryGroup
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.history.BHistoryGroup(453743066)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enabled"

  /**
   * Slot for the {@code enabled} property.
   * Enabled is used to manually enable/disable this history group.
   * @see #getEnabled
   * @see #setEnabled
   */
  public static final Property enabled = newProperty(0, true, null);

  /**
   * Get the {@code enabled} property.
   * Enabled is used to manually enable/disable this history group.
   * @see #enabled
   */
  public boolean getEnabled() { return getBoolean(enabled); }

  /**
   * Set the {@code enabled} property.
   * Enabled is used to manually enable/disable this history group.
   * @see #enabled
   */
  public void setEnabled(boolean v) { setBoolean(enabled, v, null); }

  //endregion Property "enabled"

  //region Property "historyPropertiesToGroupBy"

  /**
   * Slot for the {@code historyPropertiesToGroupBy} property.
   * The names of history properties to organize histories by for this history group.
   * @see #getHistoryPropertiesToGroupBy
   * @see #setHistoryPropertiesToGroupBy
   */
  public static final Property historyPropertiesToGroupBy = newProperty(0, BNameList.DEFAULT, BFacets.make(BFacets.FIELD_EDITOR, "workbench:GenericNameListFE"));

  /**
   * Get the {@code historyPropertiesToGroupBy} property.
   * The names of history properties to organize histories by for this history group.
   * @see #historyPropertiesToGroupBy
   */
  public BNameList getHistoryPropertiesToGroupBy() { return (BNameList)get(historyPropertiesToGroupBy); }

  /**
   * Set the {@code historyPropertiesToGroupBy} property.
   * The names of history properties to organize histories by for this history group.
   * @see #historyPropertiesToGroupBy
   */
  public void setHistoryPropertiesToGroupBy(BNameList v) { set(historyPropertiesToGroupBy, v, null); }

  //endregion Property "historyPropertiesToGroupBy"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryGroup.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Component overrides
////////////////////////////////////////////////////////////////

  /**
   * Never allow child components on a history group
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return false;
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

    if (isRunning() && (property != null) && (property.isFrozen()))
    {
      // Whenever changed, clear the history space's folder cache
      BHistoryService service = (BHistoryService)Sys.getService(BHistoryService.TYPE);
      BHistoryDatabase space = service.getDatabase();
      if (space != null)
      {
        @SuppressWarnings("unchecked") Map<BInterface, Map<String, BINavNode>> folderCache =
          (Map<BInterface, Map<String, BINavNode>>)space.fw(Fw.USER_DEFINED_4);
        if (folderCache != null) folderCache.clear();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  private static final BIcon icon = BIcon.std("historyGroup.png");
  @Override
  public BIcon getIcon() { return icon; }
}
