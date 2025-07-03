/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.Set;
import java.util.TreeSet;

import javax.baja.naming.*;
import javax.baja.nav.*;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.*;
import javax.baja.space.*;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.*;

import com.tridium.sys.module.*;

/**
 * BModuleSpace is a FileSpace used to manage the files
 * stored in module jars.
 *
 * @author    Brian Frank
 * @creation  14 Jan 03
 * @version   $Revision: 6$ $Date: 3/28/05 9:23:11 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraSingleton
public class BModuleSpace
  extends BSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BModuleSpace(2747097003)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BModuleSpace INSTANCE = new BModuleSpace();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BModuleSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  static
  {
    BLocalHost.INSTANCE.addNavChild(INSTANCE);
    BLocalHost.INSTANCE.mountSpace(INSTANCE);
  }

////////////////////////////////////////////////////////////////
// Constructor 
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BModuleSpace()
  {
    super("modules", LexiconText.make("baja", "nav.modules"));
  }

////////////////////////////////////////////////////////////////
// BSpace
////////////////////////////////////////////////////////////////

  /**
   * Get the ord in session.
   */
  @Override
  public BOrd getOrdInSession()
  {
    return ordInSession;
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  /**
   * We always have module children.
   */
  @Override
  public boolean hasNavChildren()
  {
    return true;
  }

  /**
   * Lazy load if necessary.
   */
  @Override
  public BINavNode getNavChild(String navName)
  {
    lazyLoad();
    return super.getNavChild(navName);
  }
  
  /**
   * Lazy load if necessary.
   */
  @Override
  public BINavNode[] getNavChildren()
  {
    lazyLoad();
    return super.getNavChildren();
  }

////////////////////////////////////////////////////////////////
// Load
////////////////////////////////////////////////////////////////  

  public void lazyLoad()
  {
    if (loaded)
    {
      return;
    }

    synchronized(loadLock)
    {
      if (loaded)
      {
        return;
      }

      // create a NavModule for each ModuleInfo

      Set<String> moduleNames = new TreeSet<>();
      for (ModuleInfo m : Sys.getRegistry().getModules())
      {
        moduleNames.add(m.getModuleName());
      }
      moduleNames.forEach(moduleName -> addNavChild(new BModuleNavNode(moduleName)));

      loaded = true;
    }
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/modules.png");
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  private static final BOrd ordInSession = BOrd.make("module:");
  private final Object loadLock = new Object();
  private volatile boolean loaded;

}
