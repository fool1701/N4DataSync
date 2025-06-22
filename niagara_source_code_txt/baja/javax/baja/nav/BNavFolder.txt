/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nav;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

/**
 * BNavFolder is general purpose BNavContainer with localization support.
 * In workbench, a BNavFolder can be used to group BHosts and this configuration will be
 * persisted to the navTree xml file.
 *
 * @author JJ Frankovich
 * @since Niagara 4.13
 */
@NiagaraType
public class BNavFolder
  extends BNavContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.nav.BNavFolder(2979906276)1.0$ @*/
/* Generated Tue Apr 19 16:09:34 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNavFolder.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNavFolder(String name)
  {
    this(name, null, null, null);
  }

  public BNavFolder(String name, String navDescriptionFormat, BIcon icon, String navDisplayNameFormat)
  {
    super(name);

    if (navDescriptionFormat != null && !navDescriptionFormat.isEmpty())
    {
      this.navDescriptionFormat = navDescriptionFormat;
    }

    if (icon == null || icon.isNull()) {icon = defaultIcon;}
    this.icon = icon;
    if (navDisplayNameFormat != null && !navDisplayNameFormat.isEmpty())
    {
      this.navDisplayNameFormat = navDisplayNameFormat;
    }
  }

  /**
   * Get the normalized absolute ord for this node.
   */
  @Override
  public BOrd getNavOrd()
  {
    BINavNode parent = getNavParent();
    if (parent == null)
    {
      return null;
    }
    BOrd ord = parent.getNavOrd();
    if (ord == null)
    {
      return null;
    }
    return BOrd.make(ord, "nav:" + getNavName()).normalize();
  }

  /**
   * Get the nav display name set by the user or the default name.
   */
  @Override
  public String getNavDisplayName(Context cx)
  {
    if (navDisplayNameFormat != null && !navDisplayNameFormat.equals(""))
    {
      BFormat navDisplayFormat = BFormat.make(navDisplayNameFormat);
      return navDisplayFormat.format(this, cx);
    }
    return super.getNavDisplayName(cx);
  }

  @Override
  public String getNavDescription(Context cx)
  {
    if (navDescriptionFormat != null && !navDescriptionFormat.equals(""))
    {
      BFormat format = BFormat.make(navDescriptionFormat);
      return format.format(this, cx);
    }
    return "";
  }

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  public String getNavDisplayFormat()
  {
    return navDisplayNameFormat;
  }

  /**
   * Set a Display Name for the Folder as viewed in the Nav Tree.
   *
   * @param navDisplayName A string or a BFormat string
   */
  public void setNavDisplayName(String navDisplayName)
  {
    if (navDisplayName == null || navDisplayName.trim().isEmpty())
    {
      navDisplayNameFormat = "";
    }
    else
    {
      navDisplayNameFormat = navDisplayName;
    }
  }

  public String getNavDescriptionFormat()
  {
    return navDescriptionFormat;
  }

  /**
   * Set a Display Name for the Folder as viewed in the Nav Tree.
   *
   * @param navDescription A string or a BFormat string
   */
  public void setNavDescription(String navDescription)
  {
    if (navDescription == null || navDescription.trim().isEmpty())
    {
      navDescriptionFormat = "";
    }
    else
    {
      navDescriptionFormat = navDescription;
    }
  }

  public void setIcon(BIcon icon)
  {
    if (icon == null)
    {
      this.icon = defaultIcon;
    }
    else
    {
      this.icon = icon;
    }
  }

  public void rename(String newName)
  {
    if (newName.equals(name))
    {
      return;
    }

    BNavContainer parent = ((BNavContainer) getNavParent());
    if (parent == null)
    {
      name = newName;
      return;
    }
    BINavNode[] originalOrder = parent.getNavChildren();
    for (BINavNode child : originalOrder)
    {
      if (child.getNavName().equals(newName))
      {
        throw new IllegalArgumentException("duplicate name " + newName);
      }
    }
    parent.removeNavChild(this);
    name = newName;
    parent.addNavChild(this);
    parent.reorderNavChildren(originalOrder);
  }

  private BIcon icon;
  private String navDisplayNameFormat = "";
  private String navDescriptionFormat = "";

  private static final BIcon defaultIcon = BIcon.std("folder.png");
}
