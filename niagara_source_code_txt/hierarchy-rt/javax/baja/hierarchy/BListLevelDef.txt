/*
  Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import static com.tridium.hierarchy.HierarchyUtil.getGroupingBase;
import static com.tridium.hierarchy.MakeElemUtil.getGroupIcon;
import static com.tridium.hierarchy.MakeElemUtil.makeListElem;

import java.util.ArrayList;
import java.util.List;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A ListLevelDef defines a set of named groups based on distinct tag names.
 *
 * @author    Blake Puhak
 * @creation  4 Mar 2014
 * @since     Niagara 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "sort",
  type = "BLevelSort",
  defaultValue = "BLevelSort.none"
)
@NiagaraProperty(
  name = "tags",
  type = "BHierarchyTags",
  defaultValue = "new BHierarchyTags()"
)
public class BListLevelDef
  extends BLevelDef
  implements BIGroupingLevelDef
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BListLevelDef(205790461)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "sort"

  /**
   * Slot for the {@code sort} property.
   * @see #getSort
   * @see #setSort
   */
  public static final Property sort = newProperty(0, BLevelSort.none, null);

  /**
   * Get the {@code sort} property.
   * @see #sort
   */
  public BLevelSort getSort() { return (BLevelSort)get(sort); }

  /**
   * Set the {@code sort} property.
   * @see #sort
   */
  public void setSort(BLevelSort v) { set(sort, v, null); }

  //endregion Property "sort"

  //region Property "tags"

  /**
   * Slot for the {@code tags} property.
   * @see #getTags
   * @see #setTags
   */
  public static final Property tags = newProperty(0, new BHierarchyTags(), null);

  /**
   * Get the {@code tags} property.
   * @see #tags
   */
  public BHierarchyTags getTags() { return (BHierarchyTags)get(tags); }

  /**
   * Set the {@code tags} property.
   * @see #tags
   */
  public void setTags(BHierarchyTags v) { set(tags, v, null); }

  //endregion Property "tags"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BListLevelDef.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public BLevelElem[] getElements(BLevelElem parent, Context cx)
  {
    String groupingBase = getGroupingBase(this, true, parent);

    // TODO NCCB-22986 Restrict viewing of hierarchy named groups
    List<BLevelElem> levelElemList = new ArrayList<>();
    for (BNamedGroupDef namedGroupDef : getChildren(BNamedGroupDef.class))
    {
      levelElemList.add(makeListElem(
        /* levelDef */ this,
        namedGroupDef,
        parent,
        getGroupIcon(namedGroupDef),
        groupingBase,
        cx));
    }

    BLevelElem[] elems = levelElemList.toArray(new BLevelElem[levelElemList.size()]);
    sortElems(elems, getSort());
    return elems;
  }

  /**
   * A ListLevelDef can have multiple NamedGroupDefs.
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return child instanceof BNamedGroupDef || super.isChildLegal(child);
  }

  @Override
  public String toString(Context cx)
  {
    return "List Level Def";
  }
}
