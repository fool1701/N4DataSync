/*
  Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.role.BIRole;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;

/**
 * BHierarchyList
 *
 * @author Blake Puhak
 * @creation 09-Feb-2015
 * @since Niagara 4.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:IRole"
  )
)
/*
 Comma separated list of Hierarchy Names.
 */
@NiagaraProperty(
  name = "hierarchyNames",
  type = "String",
  defaultValue = "",
  flags = Flags.HIDDEN | Flags.READONLY
)
public class BRoleHierarchies
  extends BComponent
  implements BIMixIn
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BRoleHierarchies(3076533741)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "hierarchyNames"

  /**
   * Slot for the {@code hierarchyNames} property.
   * Comma separated list of Hierarchy Names.
   * @see #getHierarchyNames
   * @see #setHierarchyNames
   */
  public static final Property hierarchyNames = newProperty(Flags.HIDDEN | Flags.READONLY, "", null);

  /**
   * Get the {@code hierarchyNames} property.
   * Comma separated list of Hierarchy Names.
   * @see #hierarchyNames
   */
  public String getHierarchyNames() { return getString(hierarchyNames); }

  /**
   * Set the {@code hierarchyNames} property.
   * Comma separated list of Hierarchy Names.
   * @see #hierarchyNames
   */
  public void setHierarchyNames(String v) { setString(hierarchyNames, v, null); }

  //endregion Property "hierarchyNames"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRoleHierarchies.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BRoleHierarchies() { }

  public BRoleHierarchies(BHierarchy[] hierarchies)
  {
    synchronized(LIST_LOCK)
    {
      Set<String> set = new TreeSet<>();
      for (BHierarchy hierarchy : hierarchies)
        set.add(hierarchy.getName());

      setHierarchyNames(delimitHierarchies(set));
    }
  }

///////////////////////////////////////////////////////////
// BIMixIn
///////////////////////////////////////////////////////////

  @Override
  public String getDisplayNameInParent(Context cx)
  {
    return Lexicon.make(TYPE.getModule(), cx).getText("roleHierarchies");
  }

///////////////////////////////////////////////////////////
// BComponent
///////////////////////////////////////////////////////////

  @Override
  public boolean isNavChild()
  {
    return false;
  }

  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BIRole;
  }

///////////////////////////////////////////////////////////
// Methods
///////////////////////////////////////////////////////////

  public BIRole getRole()
  {
    return (BIRole)getParent();
  }

  public Set<String> getHierarchySet()
  {
    return splitHierarchies(getHierarchyNames());
  }

  public boolean hasHierarchy(BHierarchy hierarchy)
  {
    return hasHierarchy(hierarchy.getName());
  }

  public boolean hasHierarchy(String hierarchyName)
  {
    return getHierarchySet().contains(hierarchyName);
  }

  public void addHierarchy(BHierarchy hierarchy)
  {
    addHierarchy(hierarchy.getName());
  }

  public void addHierarchy(String hierarchyName)
  {
    synchronized(LIST_LOCK)
    {
      Set<String> set = getHierarchySet();
      set.add(hierarchyName);
      setHierarchyNames(delimitHierarchies(set));
    }
  }

  @Override
  public synchronized boolean equivalent(Object obj)
  {
    if (obj == null) return false;
    if (obj.getClass() != getClass()) return false;

    return getHierarchySet().equals(((BRoleHierarchies)obj).getHierarchySet());
  }

  public void removeHierarchy(BHierarchy hierarchy)
  {
    removeHierarchy(hierarchy.getName());
  }

  public void removeHierarchy(String hierarchyName)
  {
    synchronized(LIST_LOCK)
    {
      Set<String> set = getHierarchySet();
      set.remove(hierarchyName);
      setHierarchyNames(delimitHierarchies(set));
    }
  }

  public void renameHierarchy(String oldName, String newName)
  {
    synchronized(LIST_LOCK)
    {
      Set<String> set = getHierarchySet();
      if (set.remove(oldName))
      {
        set.add(newName);
        setHierarchyNames(delimitHierarchies(set));
      }
    }
  }

  private static Set<String> splitHierarchies(String hierarchies)
  {
    String[] hierarchyArray = hierarchies.split(NAMES_DELIMITER);
    if(hierarchyArray.length > 0)
    {
      Set<String> set = new TreeSet<>();
      for(String hierarchy : hierarchyArray)
      {
        if (hierarchy != null && !hierarchy.isEmpty())
          set.add(hierarchy);
      }
      return set;
    }
    return Collections.emptySet();
  }

  public static String delimitHierarchies(Set<String> hierarchies)
  {
    StringBuilder hierarchyString = new StringBuilder();
    hierarchies.stream()
      .filter(hierarchy -> hierarchy != null)
      .forEach(hierarchy -> hierarchyString.append(hierarchy).append(NAMES_DELIMITER));

    int len = hierarchyString.length();
    if (len > 1)
      hierarchyString.deleteCharAt(len - 1);

    return hierarchyString.toString();
  }

  @Override
  public String toString(Context cx)
  {
    StringBuilder buf = new StringBuilder();

    for (String s : getHierarchySet())
      buf.append(SlotPath.unescape(s) + ", ");

    int len = buf.length();
    if (len > 2)
      buf.delete(len-2, len-1);

    return buf.toString();
  }

  private final Object LIST_LOCK = new Object();
  private static final String NAMES_DELIMITER = ",";
}
