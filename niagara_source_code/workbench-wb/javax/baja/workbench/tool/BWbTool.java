/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.tool;

import java.util.ArrayList;

import javax.baja.license.Feature;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.CommandArtifact;
import javax.baja.workbench.BWbShell;

/**
 * BWbTool is a plugin which provides a stand alone
 * utility which is automatically made available in
 * the workbench environment.
 *
 * @author    Brian Frank       
 * @creation  14 Oct 03
 * @version   $Revision: 4$ $Date: 3/28/05 1:41:02 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWbTool
  extends BComponent
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.tool.BWbTool(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbTool.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get an array of TypeInfos for all the implementations 
   * of BWbTool currently installed on the system.
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

        try 
        { 
          BWbTool tool = (BWbTool) t.getInstance();
          Feature feature = tool.getLicenseFeature();

          if (feature != null)
            feature.check();

          v.add(t);
        }
        catch (Exception e) { }
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

  /**
   * If this tool is to be licensed using the standard licensing
   * mechanism then override this method to return the Feature or 
   * return null for no license checks.  Convention is that the
   * vendor and feature name matches the declaring module.
   */
  public Feature getLicenseFeature()                              
  {              
    return null;
  }                  

  
////////////////////////////////////////////////////////////////
// Invoke
////////////////////////////////////////////////////////////////
  
  /**
   * Invoke the tool.
   */
  public abstract CommandArtifact invoke(BWbShell shell)
    throws Exception;
   
}
