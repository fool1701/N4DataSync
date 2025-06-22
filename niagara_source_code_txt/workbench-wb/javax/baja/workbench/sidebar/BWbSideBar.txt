/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.sidebar;

import java.util.ArrayList;

import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.workbench.BWbPlugin;

import com.tridium.ui.theme.Theme;
import com.tridium.workbench.util.TypeInfoSpec;

/**
 * BWbSideBar is a plugin used to display a tool in the workbench
 * sidebar panel.  Sidebars are tools which have utility being 
 * open at all times independent of the current ord or view.  The
 * workbench automatically makes all installed sidebars available
 * to the user.  
 * <p>
 * Sidebars must declare the following entries in their module's 
 * lexicon:
 * <ul>
 * <li>{typename}.displayName</li>
 * <li>{typename}.icon</li>
 * </ul>
 *
 * @author    Brian Frank       
 * @creation  11 Jul 03
 * @version   $Revision: 6$ $Date: 11/29/06 8:18:41 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public abstract class BWbSideBar
  extends BWbPlugin implements BIWbSideBar
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.sidebar.BWbSideBar(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbSideBar.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Get an array of TypeInfos for all the implementations 
   * of BWbSideBar currently installed on the system.
   */
  public static TypeInfo[] getInstalled()
  {
    if (installed == null) 
    {
      // get concrete classes from registry
      TypeInfo[] list = Sys.getRegistry().getTypes(TYPE.getTypeInfo());
      ArrayList<TypeInfo> v = new ArrayList<TypeInfo>();
      for (TypeInfo t : list)
      {
        if (!t.isAbstract())
        {
          v.add(t);
        }
      }
      installed = v.toArray(new TypeInfo[v.size()]);
      
      // sort
      String[] names = new String[installed.length];
      for (int i = 0; i < names.length; ++i)
      {
        names[i] = installed[i].getDisplayName(null);
      }
      SortUtil.sort(names, installed);
    }
    return installed;
  }
  private static TypeInfo[] installed;




////////////////////////////////////////////////////////////////
// Painting
////////////////////////////////////////////////////////////////

  /**
   * By default, paint a 1px border with the outline color.
   */
  @Override
  public void paint(Graphics g)
  {
    super.paint(g);
    g.setBrush(Theme.toolPane().getControlForeground(this));
    g.strokeRect(0, 0, getWidth() - 1, getHeight() - 1);
  }

////////////////////////////////////////////////////////////////
// BIWbSideBar
////////////////////////////////////////////////////////////////

  /**
   * Get an array of TypeInfos for all the implementations
   * of BWbSideBar currently installed on the system. Delegates to
   * static method implementation for BWbSideBar
   */
  @Override
  public TypeInfo[] getInstalledSideBars()
  {
    return getInstalled();
  }

  /**
   * @return true if this sidebar should have a 'Close' command.
   * The default is true -- override this method for different
   * behavior.
   */
  @Override
  public boolean hasCloseCommand()
  {
    return true;
  }

  /**
   * This callback is invoked when the active view is 
   * modified via a hyperlink operation.
   */
  @Override
  public void activeViewChanged()
  {
  }

  @Override
  public BWidget asWidget()
  {
    return this;
  }

  @Override
  public String getLabel()
  {
    return new TypeInfoSpec(getType().getTypeInfo()).label;
  }

}
