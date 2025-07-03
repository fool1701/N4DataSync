/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.view;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;

import com.tridium.ui.BPxCacheShell;

/**
 * BWbViewBinding is used to bind WbViews to an object. 
 *
 * @author    Brian Frank on 5 Jul 04          
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "workbench:WbView"
  )
)
/*
 * The required permissions or the degrade behavior will take effect.
 */
@NiagaraProperty(
  name = "requiredPermissions",
  type = "BPermissions",
  defaultValue = "BPermissions.operatorRead"
)

public class BWbViewBinding
  extends BBinding
{



//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.view.BWbViewBinding(1472180696)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "requiredPermissions"

  /**
   * Slot for the {@code requiredPermissions} property.
   * * The required permissions or the degrade behavior will take effect.
   * @see #getRequiredPermissions
   * @see #setRequiredPermissions
   */
  public static final Property requiredPermissions = newProperty(0, BPermissions.operatorRead, null);

  /**
   * Get the {@code requiredPermissions} property.
   * * The required permissions or the degrade behavior will take effect.
   * @see #requiredPermissions
   */
  public BPermissions getRequiredPermissions() { return (BPermissions)get(requiredPermissions); }

  /**
   * Set the {@code requiredPermissions} property.
   * * The required permissions or the degrade behavior will take effect.
   * @see #requiredPermissions
   */
  public void setRequiredPermissions(BPermissions v) { set(requiredPermissions, v, null); }

  //endregion Property "requiredPermissions"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbViewBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the parent widget cast as a BWbView.
   */
  public final BWbView getView()
  {                                           
    return (BWbView)getWidget();
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  @Override
  public boolean isDegraded()
  {
    return !isBound() || !getTarget().getPermissionsForTarget().has(getRequiredPermissions());
  }

  @Override
  public void targetChanged()
  {
    super.targetChanged();

    // don't call load unless we are exporting or we really changed
    // the ord because views typically handle changes
    // at a more detailed level using component events
    if ((getView().getShell() instanceof BPxCacheShell || get() != getView().getCurrentValue()) && isPrimaryBinding())
    {
      getView().loadValue(get(), getTarget());
    }

  }

  private boolean isPrimaryBinding(){
    BComplex parent = getParent();
    if(parent instanceof BComponent)
    {
      BWbViewBinding[] bindings = parent.asComponent().getChildren(BWbViewBinding.class);
      if (bindings.length > 0 && bindings[0] != this)
      {
        return false;
      }
    }
    return true;
  }

  @Override
  public void save(Context cx)
    throws Exception
  {
    BWbView view = getView();
    if (!view.isModified() || !isPrimaryBinding())
    {
      return;
    }
    view.saveValue(cx);
  }
  
////////////////////////////////////////////////////////////////
// Component
////////////////////////////////////////////////////////////////

  @Override
  public boolean isParentLegal(BComponent c)
  {
    return c instanceof BWbView;
  }
}
