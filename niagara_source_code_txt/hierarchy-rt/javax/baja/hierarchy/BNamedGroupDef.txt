/*
  Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * A NamedGroupDef defines a group in a ListLevelDef..
 *
 * @author    Blake Puhak
 * @creation  4 Mar 2014
 * @since     Niagara 4.0
 */
@NiagaraType
@NiagaraProperty(
  name = "query",
  type = "String",
  defaultValue = ""
)
@NiagaraProperty(
  name = "tags",
  type = "BHierarchyTags",
  defaultValue = "new BHierarchyTags()"
)
public class BNamedGroupDef
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BNamedGroupDef(1700350515)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "query"

  /**
   * Slot for the {@code query} property.
   * @see #getQuery
   * @see #setQuery
   */
  public static final Property query = newProperty(0, "", null);

  /**
   * Get the {@code query} property.
   * @see #query
   */
  public String getQuery() { return getString(query); }

  /**
   * Set the {@code query} property.
   * @see #query
   */
  public void setQuery(String v) { setString(query, v, null); }

  //endregion Property "query"

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
  public static final Type TYPE = Sys.loadType(BNamedGroupDef.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * A NamedGroupDef can only be added to a ListLevelDef.
   */
  @Override
  public boolean isParentLegal(BComponent parent)
  {
    return parent instanceof BListLevelDef;
  }

  /**
   * A NamedGroupDef should not have any children.
   */
  @Override
  public boolean isChildLegal(BComponent child)
  {
    return false;
  }

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  @Override
  public String toString(Context cx)
  {
    return "Named Group Def: " + getQuery();
  }

  private static final BIcon icon = BIcon.make("module://hierarchy/rc/namedGroup.png");
}
