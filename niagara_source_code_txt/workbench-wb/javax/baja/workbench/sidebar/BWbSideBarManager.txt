/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.sidebar;

import java.util.StringTokenizer;

import javax.baja.gx.BInsets;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BModule;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BMenu;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BToolPane;

import com.tridium.ui.theme.Theme;
import com.tridium.workbench.shell.BNiagaraWbShell;
import com.tridium.workbench.shell.WbCommands;
import com.tridium.workbench.util.TypeInfoSpec;

/**
 * BWbSideBarManager represents a concrete bajaui-based implementation of the IWbSideBarManager
 * interface
 * @author Danesh Kamal
 * @creation 1/19/14
 * @since Niagara 4.0
 */
@NiagaraType
public final class BWbSideBarManager extends BToolPane
    implements IWbSideBarManager, BToolPane.MenuController
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.sidebar.BWbSideBarManager(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:49 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWbSideBarManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/






  public BWbSideBarManager()
  {
    setMenuController(this);
  }

  @Override
  public BWidget asWidget()
  {
    return new BBorderPane(this, BBorder.none, BInsets.DEFAULT);
  }

  @Override
  public BIWbSideBar openSideBar(BIWbSideBar bar)
  {
    bars.add(bar);

    TypeInfoSpec spec = new TypeInfoSpec(bar.asWidget().getType().getTypeInfo());

    //add BLabelPane without icon
    addPane(
      spec.label, 
      Theme.javaFx().shouldHideNonessentialIcons() ? null : spec.icon,
      bar.asWidget());
    return bar;
  }


  @Override
  public void closeSideBar(BIWbSideBar bar)
  {
    removePane(bar.asWidget());

    for(int i=0; i<bars.size(); ++i)
    {
      if (bars.get(i) == bar)
      {
        bars.remove(i);
        commands().showSideBar.setSelected(hasOpenSideBars());
        return;
      }
    }

    throw new IllegalStateException("Sidebar: " + bar.getLabel() + " not open");
  }

  private WbCommands commands()
  {
    return ((BNiagaraWbShell)getShell()).commands;
  }

  @Override
  public void closeAllSideBars()
  {
    removeAll();
    bars.clear();
    commands().showSideBar.setSelected(false);
  }

  @Override
  public boolean hasOpenSideBars()
  {
    return !bars.isEmpty();
  }

  @Override
  public String serialize()
  {
    StringBuilder s = new StringBuilder();
    for(int i = 0; i < bars.size(); ++i)
    {
      BIWbSideBar bar = bars.get(i);
      s.append(bar.asWidget().getType().toString()).append(";");
    }
    s.append('|').append(pickle());
    return s.toString();
  }

  @Override
  public void deserialize(String serializedForm)
  {
    try
    {
      closeAllSideBars();

      if (serializedForm.isEmpty() || serializedForm.startsWith("|"))
      {
        return;
      }

      StringTokenizer top = new StringTokenizer(serializedForm, "|");

      String types = top.nextToken();
      String positions = top.nextToken();

      StringTokenizer st = new StringTokenizer(types, ";");
      while(st.hasMoreTokens())
      {
        String tok = st.nextToken();
        TypeInfo info = Sys.getRegistry().getType(tok);
        openSideBar((BIWbSideBar)info.getInstance());
      }

      unpickle(positions);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void openMode(WbCommands.Mode mode)
  {
    throw new UnsupportedOperationException("Sidebar modes are currently unsupported");
  }

  @Override
  public BIWbSideBar[] listSideBars()
  {
    return bars.trim();
  }

////////////////////////////////////////////////////////////////
// BToolPane.MenuController
////////////////////////////////////////////////////////////////

  @Override
  public BMenu getMenu(BToolPane toolPane, BWidget content)
  {
    BIWbSideBar sideBar = (BIWbSideBar)content;

    if (sideBar.hasCloseCommand())
    {
      BMenu menu = new BMenu();
      menu.add(null, new CloseCommand(sideBar));
      return menu;
    }
    else
    {
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// SideBarCommand
////////////////////////////////////////////////////////////////

  static class SideBarCommand extends Command
  {
    SideBarCommand(BIWbSideBar sideBar, String keyBase)
    {
      super(sideBar.asWidget(), module, keyBase);
      this.sideBar = sideBar;
    }

    BIWbSideBar sideBar;
  }

////////////////////////////////////////////////////////////////
// CloseCommand
////////////////////////////////////////////////////////////////

  class CloseCommand extends SideBarCommand
  {
    CloseCommand(BIWbSideBar sideBar) { super(sideBar, "sideBar.close"); }

    @Override
    public CommandArtifact doInvoke()
    {
      closeSideBar(sideBar);
      return null;
    }
  }

  private final Array<BIWbSideBar> bars = new Array<>(BIWbSideBar.class);
  private static final BModule module = Sys.loadModule("workbench");
}
