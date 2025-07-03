/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.space;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * The BHandleScheme is identified via scheme id of "h".  It
 * is used to identify a BComponent within a BComponentSpace
 * via its unique handle.
 *
 * @author    Brian Frank
 * @creation  30 Mar 03
 * @version   $Revision: 3$ $Date: 3/28/05 9:23:03 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  ordScheme = "h"
)
@NiagaraSingleton
public class BHandleScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.space.BHandleScheme(2714245262)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHandleScheme INSTANCE = new BHandleScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHandleScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Constructor with scheme id.
   */
  private BHandleScheme()
  {
    super("h");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////

  /**
   * See documentation in class header for how resolve works.
   */
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
    throws SyntaxException, UnresolvedException
  {
    // need to enhance SpaceScheme
    BObject baseObject = base.get();
    BComponentSpace space = null;
    String handle = query.getBody();
    
    if (baseObject instanceof BComponentSpace)
    {
      space = (BComponentSpace)baseObject;
    }
    else if (baseObject instanceof BComponent)
    {                                     
      space = baseObject.asComponent().getComponentSpace();
    }
    
    if (space == null)
      throw new InvalidOrdBaseException("Not based via ComponentSpace");
    
    return new OrdTarget(base, space.resolveByHandle(handle));
  }
  
}
