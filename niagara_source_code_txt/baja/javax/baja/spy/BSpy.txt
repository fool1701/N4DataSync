/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.spy;

import javax.baja.agent.*;
import javax.baja.category.*;
import javax.baja.file.*;
import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.*;
import javax.baja.sys.*;

/**
 * BSpy is a BObject wrapper for an instance of Spy.
 *
 * @author    Brian Frank
 * @creation  5 Mar 03
 * @version   $Revision: 7$ $Date: 3/28/05 9:23:04 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public final class BSpy
  extends BObject        
  implements BICategorizable, BIProtected
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.spy.BSpy(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BSpy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Make a BSpy instance for the specified Spy.
   */
  public static BSpy make(FilePath path, Spy spy)
  {
    return new BSpy(path, spy);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private constructor.
   */
  private BSpy(FilePath path, Spy spy) 
  { 
    this.path = path;
    this.spy  = spy; 
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the file path used to resolve the wrapped spy instance.
   */
  public FilePath getPath()
  {
    return path;
  }

  /**
   * Return the spy instance.
   */
  public Spy get()
  {
    return spy;
  }                   

////////////////////////////////////////////////////////////////
// ICategorizable
////////////////////////////////////////////////////////////////
  
  /**
   * Spy pages are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getCategoryMask()
  {
    return BCategoryService.getService().getCategoryMask(BOrd.make("spy:" + path.getBody()));
  }  

  /**
   * Spy pages are mapped to categories by ord in <code>CategoryService.ordMap</code>.
   */
  @Override
  public BCategoryMask getAppliedCategoryMask()
  {
    return BCategoryService.getService().getAppliedCategoryMask(BOrd.make("spy:" + path.getBody()));
  }  
  
////////////////////////////////////////////////////////////////
// IProtected
////////////////////////////////////////////////////////////////
    
  @Override
  public BPermissions getPermissions(Context cx)
  { 
    if (cx != null && cx.getUser() != null)
      return cx.getUser().getPermissionsFor(this);
    else
      return BPermissions.all;
  }
  
  @Override
  public boolean canRead(OrdTarget cx)
  {
    return cx.getPermissionsForTarget().hasAdminRead();
  }
  
  @Override
  public boolean canWrite(OrdTarget cx)
  {
    return false;
  }

  @Override
  public boolean canInvoke(OrdTarget cx)
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// BObject
////////////////////////////////////////////////////////////////  

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("web:SpyServlet");
    return agents;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private FilePath path;
  private Spy spy;

}
