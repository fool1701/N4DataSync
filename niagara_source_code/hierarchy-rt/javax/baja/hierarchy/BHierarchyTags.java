/*
  Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import java.util.ArrayList;
import java.util.List;

import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.BVector;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
public class BHierarchyTags
  extends BVector
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hierarchy.BHierarchyTags(2979906276)1.0$ @*/
/* Generated Tue Jan 18 11:31:27 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHierarchyTags.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Override
  public void checkAdd(String name, BValue value, int flags, BFacets facets, Context context)
  {
    if (!(value instanceof BIDataValue))
      throw new LocalizableRuntimeException("hierarchy", "hierarchy.tagsMustBeDataValues");
  }

  @Override
  public BIcon getIcon()
  {
    return ICON;
  }

  public void addOrSet(String name, BValue value)
  {
    if (get(name) == null)
      add(name, value);
    else
      set(name, value);
  }

  /**
   * Add the tags from toAdd into the existing tags.
   * Tags in toAdd take precedence over existing tags.
   */
  public void merge(BHierarchyTags toAdd)
  {
    merge(toAdd, true);
  }

  public void merge(BHierarchyTags toAdd, boolean replace)
  {
    try (SlotCursor<Property> c = toAdd.getProperties())
    {
      while (c.next())
      {
        Property p = c.property();
        BValue tagToAdd = toAdd.get(p).newCopy();
        if (get(p.getName()) == null)
        {
          add(p.getName(), tagToAdd);
        }
        else if (replace)
        {
          set(p.getName(), tagToAdd);
        }
      }
    }
  }

  /**
   * Add the tags from toAdd into the existing tags.
   * Tags in toAdd take precedence over existing tags.
   */
  public void merge(BFacets toAdd)
  {
    merge(toAdd, true);
  }

  public void merge(BFacets toAdd, boolean replace)
  {
    String[] keys = toAdd.list();
    for (String key : keys)
    {
      if (get(key) == null)
        add(key, (BValue)toAdd.get(key));
      else if (replace)
        set(key, (BValue)toAdd.get(key));
    }
  }

  /**
   * Return the child BIDataValues as a BFacets with the property names as keys.
   */
  public BFacets toFacets()
  {
    List<String> keys = new ArrayList<>();
    List<BIDataValue> values = new ArrayList<>();

    try (SlotCursor<Property> c = getProperties())
    {
      while (c.next())
      {
        Property p = c.property();
        keys.add(p.getName());
        values.add((BIDataValue)get(p));
      }
    }

    return BFacets.make(keys.toArray(new String[keys.size()]), values.toArray(new BIDataValue[values.size()]));
  }

  private static final BIcon ICON = BIcon.std("tag.png");
}
