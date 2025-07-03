/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.sys.TypeSubscriber;
import javax.baja.util.LexiconText;

/**
 * BProxyComponentSpace is a BComponentSpace which uses the sync
 * framework to keep itself synchronized with a remote master
 * BComponentSpace.
 *
 * @author    Brian Frank       
 * creation  21 Jan 03
 * @version   $Revision: 5$ $Date: 3/10/11 11:30:59 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BProxyComponentSpace
  extends BComponentSpace
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sync.BProxyComponentSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BProxyComponentSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Constructor.
   */
  protected BProxyComponentSpace(String name, LexiconText lexText, BOrd ordInSession)
  {
    super(name, lexText, ordInSession);
  }                     

  
////////////////////////////////////////////////////////////////
// ComponentSpace
////////////////////////////////////////////////////////////////

  /**
   * Return true.
   */
  @Override
  public final boolean isProxyComponentSpace()
  {
    return true;
  }

  /**
   * Attempt to lookup a component by handle.  If it is
   * not found in the cache and autoLoad is true, then 
   * route to {@code loadByHandle()}.
   */
  @Override
  public final BComponent findByHandle(Object handle, boolean autoLoad)
  {                                           
    // see it is cached in base class lookup tables
    BComponent c = super.findByHandle(handle, autoLoad);     
    if (c != null) return c;  
    
    // if autoLoad is false, then bail
    if (!autoLoad) return null;
    
    // let subclass attempt a load
    return loadByHandle(handle);
  }    
  
  /**
   * Subclasses should use this hook to load an object by handle.
   */
  protected abstract BComponent loadByHandle(Object handle);
  
  /**
   * Sync with the master space.
   */
  @Override
  public abstract void sync()
    throws Exception;

  /**
   * Override to return false by default.
   */
  @Override
  public boolean fireDirectCallbacks()
  {
    return false;
  }
  
  @Override
  public void subscribe(Type[] t, TypeSubscriber s)
  {
    //TODO (see issue 19011)
    throw new UnsupportedOperationException();
  }

}
