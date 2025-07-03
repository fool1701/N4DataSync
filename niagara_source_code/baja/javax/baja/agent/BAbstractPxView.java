/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.agent;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.*;
import javax.baja.security.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BAbstractPxView is a dynamic view which may be added to BComponents
 * as a property or by overriding getAgents(). 
 *
 * @author    Mike Jarmy
 * @creation  18 Dec 06
 * @version   $Revision: 2$ $Date: 6/11/07 12:41:23 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Defines the icon to use for the view
 */
@NiagaraProperty(
  name = "icon",
  type = "BIcon",
  defaultValue = "BIcon.std(\"views/view.png\")"
)
/*
 Defaults the permissions needed to access the view
 */
@NiagaraProperty(
  name = "requiredPermissions",
  type = "BPermissions",
  defaultValue = "BPermissions.operatorRead"
)
/*
 Specified the target media for the view.
 */
@NiagaraProperty(
  name = "media",
  type = "BTypeSpec",
  defaultValue = "BTypeSpec.make(\"workbench:WbPxMedia\")",
  facets = @Facet("BFacets.make(BFacets.TARGET_TYPE, \"bajaui:PxMedia\")")
)
public abstract class BAbstractPxView
  extends BStruct
  implements AgentInfo, BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.agent.BAbstractPxView(591820240)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "icon"

  /**
   * Slot for the {@code icon} property.
   * Defines the icon to use for the view
   * @see #getIcon
   * @see #setIcon
   */
  public static final Property icon = newProperty(0, BIcon.std("views/view.png"), null);

  /**
   * Get the {@code icon} property.
   * Defines the icon to use for the view
   * @see #icon
   */
  public BIcon getIcon() { return (BIcon)get(icon); }

  /**
   * Set the {@code icon} property.
   * Defines the icon to use for the view
   * @see #icon
   */
  public void setIcon(BIcon v) { set(icon, v, null); }

  //endregion Property "icon"

  //region Property "requiredPermissions"

  /**
   * Slot for the {@code requiredPermissions} property.
   * Defaults the permissions needed to access the view
   * @see #getRequiredPermissions
   * @see #setRequiredPermissions
   */
  public static final Property requiredPermissions = newProperty(0, BPermissions.operatorRead, null);

  /**
   * Get the {@code requiredPermissions} property.
   * Defaults the permissions needed to access the view
   * @see #requiredPermissions
   */
  public BPermissions getRequiredPermissions() { return (BPermissions)get(requiredPermissions); }

  /**
   * Set the {@code requiredPermissions} property.
   * Defaults the permissions needed to access the view
   * @see #requiredPermissions
   */
  public void setRequiredPermissions(BPermissions v) { set(requiredPermissions, v, null); }

  //endregion Property "requiredPermissions"

  //region Property "media"

  /**
   * Slot for the {@code media} property.
   * Specified the target media for the view.
   * @see #getMedia
   * @see #setMedia
   */
  public static final Property media = newProperty(0, BTypeSpec.make("workbench:WbPxMedia"), BFacets.make(BFacets.TARGET_TYPE, "bajaui:PxMedia"));

  /**
   * Get the {@code media} property.
   * Specified the target media for the view.
   * @see #media
   */
  public BTypeSpec getMedia() { return (BTypeSpec)get(media); }

  /**
   * Set the {@code media} property.
   * Specified the target media for the view.
   * @see #media
   */
  public void setMedia(BTypeSpec v) { set(media, v, null); }

  //endregion Property "media"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractPxView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with all fields.
   */
  protected BAbstractPxView(BIcon icon, BPermissions permissions, BTypeSpec media)
  {                
    setIcon(icon);      
    setRequiredPermissions(permissions);
    setMedia(media);
  }

  /**
   * No argument constructor.
   */
  protected BAbstractPxView()
  {
  }

////////////////////////////////////////////////////////////////
// AgentInfo
////////////////////////////////////////////////////////////////

  /**
   * Return {@code this}.
   */
  @Override
  public BObject getInstance()
  {
    return this;
  }
  
  /**
   * Return {@code getName()}.
   */
  @Override
  public String getAgentId()
  {
    return getName();
  }
  
  /**
   * Return {@code getType().getTypeInfo()}.
   */
  @Override
  public TypeInfo getAgentType()
  {
    return getType().getTypeInfo();
  }                        
  
  /**
   * Return null.
   */
  @Override
  public String getAppName()
  {
    return null;
  }

  /**
   * Return an empty array.
   */
  @Override
  public TypeInfo[] getAgentOn()
  {
    return emptyTypeInfo;
  }

  /**
   * Return icon property.
   */
  @Override
  public BIcon getIcon(Context cx)
  {
    return getIcon();
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static TypeInfo[] emptyTypeInfo = new TypeInfo[0];
}
