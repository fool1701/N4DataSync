/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.virtual;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BVirtualScheme provides management of the registered "virtual" ord scheme ID.
 * It resolves Ords using the "virtual" ord scheme.
 *
 * The "/" is a reserved character indicating to the virtual ord to find a child
 * slot under the resolved virtual component.
 *
 * @author    Scott Hoye
 * @creation  10 Jul 06
 * @version   $Revision: 5$ $Date: 6/11/07 12:41:24 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType(
  ordScheme = "virtual"
)
@NiagaraSingleton
public final class BVirtualScheme
  extends BSlotScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.virtual.BVirtualScheme(1268011370)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BVirtualScheme INSTANCE = new BVirtualScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVirtualScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.  Registers with the "virtual" id.
   */
  private BVirtualScheme()
  {
    super("virtual");
  }
  

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * This method is overridden to return a VirtualQuery instance.  
   */
  @Override
  public OrdQuery parse(String queryBody)
  {
    return new VirtualPath(getId(), queryBody);
  }
}
