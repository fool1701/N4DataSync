/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.agent;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.*;
import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * BDynamicPxView is a BAbstractPxView which dynamically
 * generates Presentation XML on demand.
 *
 * @author    Mike Jarmy
 * @creation  18 Dec 06
 * @version   $Revision: 5$ $Date: 6/11/07 12:41:23 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BDynamicPxView
  extends BAbstractPxView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.agent.BDynamicPxView(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDynamicPxView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with all fields.
   */
  protected BDynamicPxView(BIcon icon, BPermissions permissions, BTypeSpec media)
  {                
    super(icon, permissions, media);
  }

  /**
   * No argument constructor.
   */
  protected BDynamicPxView()
  {
  }

////////////////////////////////////////////////////////////////
// protected
////////////////////////////////////////////////////////////////

  /**
   * <p>
   * Generate the Presentation XML for this view.
   * </p>
   *
   * <p>
   * If you are overriding this method, add the following annotation
   * above the implemented version of this method:
   * </p>
   *
   * <pre>
   * &#64;NiagaraRpc(
   *   transports = @Transport(type = TransportType.fox),
   *   permissions = "r"
   * )
   * </pre>
   */
  public abstract String generateXml(Object arg, Context context);
  
  /**
   * Return an unique id string for this instance of PxView. 
   * This string is used to cache the widget tree produced
   * from the XML returned by {@code generateXml()}.
   * So the id returned here must always match up to the
   * correct XML for this target.  The default implementation
   * returns the ord of the target.
   */
  public String getUniqueId(OrdTarget target)
  {
    return target.getOrd().toString();
  }

}
