/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.tool;

import java.util.ArrayList;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BIService;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.workbench.service.BWbServiceManager;

/**
 * BWbService is a tool which may be run as a background
 * daemon in the Workbench VM.
 *
 * @author    Brian Frank       
 * @creation  14 Oct 03
 * @version   $Revision: 4$ $Date: 3/28/05 1:41:02 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWbService
  extends BWbNavNodeTool    
  implements BIService
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.tool.BWbService(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbService.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  


////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////
  
  /**
   * Get the WbServiceManager used to manage the lifecycle
   * and configuration of the installed BWbServices.
   */
  public static WbServiceManager getManager()
  {
    return BWbServiceManager.make();
  } 

  /**
   * Get an array of TypeInfos for all the implementations 
   * of BWbService currently installed on the system.
   */
  public static TypeInfo[] getInstalled()
  {
    if (installed == null) 
    {
      // get concrete classes from registry
      TypeInfo[] list = Sys.getRegistry().getTypes(TYPE.getTypeInfo());
      ArrayList<TypeInfo> v = new ArrayList<>();
      for(int i=0; i<list.length; ++i)
      {
        TypeInfo t = list[i];
        if (t.isAbstract()) continue;
        v.add(t);
      }
      installed = v.toArray(new TypeInfo[v.size()]);
      
      // sort
      String[] names = new String[installed.length];
      for(int i=0; i<names.length; ++i)
        names[i] = installed[i].getDisplayName(null);
      SortUtil.sort(names, installed);
    }
    return installed;
  }
  private static TypeInfo[] installed;

  
////////////////////////////////////////////////////////////////
// IService
////////////////////////////////////////////////////////////////
  
  /**
   * Default is to return <code>new Type[] { getType() }</code>.
   */
  public Type[] getServiceTypes()
  {                             
    return new Type[] { getType() };
  }
  
  /**
   * Callback when the service is being started.
   */
  public void serviceStarted()
    throws Exception
  {
  }

  /**
   * Callback when the service is being stopped.
   */
  public void serviceStopped()
    throws Exception
  {
  }
  
   
}
