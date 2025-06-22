/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.alarmOrion.OrionObjectCache;
import com.tridium.orion.*;
import com.tridium.orion.annotations.NiagaraOrionType;
import com.tridium.orion.sql.PropertyValue;

/**
 * The representation of an alarm data facet name within the orion database.
 *
 * @author Lee Adcock
 * @creation March 18, 2009
 */
@NiagaraType
@NiagaraOrionType
@NiagaraProperty(
  name = "id",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY | Flags.SUMMARY,
  facets = @Facet("ID_KEY")
)
@NiagaraProperty(
  name = "facetName",
  type = "String",
  defaultValue = "",
  flags = Flags.SUMMARY,
  facets = {
    @Facet(name = "WIDTH", value = "128"),
    @Facet(name = "UNIQUE", value = "true")
  }
)
public class BOrionAlarmFacetName
  extends BOrionObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarmOrion.BOrionAlarmFacetName(441394727)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "id"

  /**
   * Slot for the {@code id} property.
   * @see #getId
   * @see #setId
   */
  public static final Property id = newProperty(Flags.READONLY | Flags.SUMMARY, -1, ID_KEY);

  /**
   * Get the {@code id} property.
   * @see #id
   */
  public int getId() { return getInt(id); }

  /**
   * Set the {@code id} property.
   * @see #id
   */
  public void setId(int v) { setInt(id, v, null); }

  //endregion Property "id"

  //region Property "facetName"

  /**
   * Slot for the {@code facetName} property.
   * @see #getFacetName
   * @see #setFacetName
   */
  public static final Property facetName = newProperty(Flags.SUMMARY, "", BFacets.make(BFacets.make(WIDTH, 128), BFacets.make(UNIQUE, true)));

  /**
   * Get the {@code facetName} property.
   * @see #facetName
   */
  public String getFacetName() { return getString(facetName); }

  /**
   * Set the {@code facetName} property.
   * @see #facetName
   */
  public void setFacetName(String v) { setString(facetName, v, null); }

  //endregion Property "facetName"

  //region Type

  @Override
  public Type getType() { return getTypeFromSpace(TYPE); }
  public static final Type TYPE = Sys.loadType(BOrionAlarmFacetName.class);

  //endregion Type
  public static final OrionType ORION_TYPE = (OrionType)TYPE;

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Get the facet record of the specified name.  If one does not exist, create it.
   * The results are cached to speed up multiple requests for the same object.
   */  
  public static BOrionAlarmFacetName get(String key, OrionSession session)
  {
    if(cacheName.contains(key))
      return (BOrionAlarmFacetName)cacheName.get(key);

    BOrionAlarmFacetName dataName;
    try (OrionCursor cursor = session.select(BOrionAlarmFacetName.ORION_TYPE, new PropertyValue(BOrionAlarmFacetName.facetName, BString.make(key))))
    {
      if (cursor.next())
      {
        dataName = (BOrionAlarmFacetName) cursor.get();
      }
      else
      {
        dataName = new BOrionAlarmFacetName();
        dataName.setFacetName(key);
        session.insert(dataName);
      }
      cacheName.put(key, dataName);
    }
    return dataName;
  }
  
  /**
   * Get the facet record of the specified name.  If one does not exist, create it.
   * The results are cached to speed up multiple requests for the same object.
   */  
  public static BOrionAlarmFacetName get(int id, OrionSession session)
  {
    BInteger intId = BInteger.make(id);
    if(cacheId.contains(intId))
      return (BOrionAlarmFacetName)cacheId.get(intId);
    
    OrionCursor cursor = session.select(BOrionAlarmFacetName.ORION_TYPE, new PropertyValue(BOrionAlarmFacetName.id, intId));
    BOrionAlarmFacetName dataName;
    if(cursor.next())
    {
      dataName = (BOrionAlarmFacetName)cursor.get();
      cursor.close();
      cacheId.put(intId, dataName);
    } else 
      throw new RuntimeException("Invalid facet name primary key.");
    
    return dataName;
  }  
  
  public void afterDelete(OrionSession session)
  {
    cacheName.remove(getFacetName());
    cacheId.remove(BInteger.make(getId()));
  }

  public void afterInsert(OrionSession session)
  {
    cacheName.put(getFacetName(), this);
    cacheId.put(BInteger.make(getId()), this);
  }

  public void afterUpdate(OrionSession session)
  {
    cacheName.update(getFacetName(), this);
    cacheId.update(BInteger.make(getId()), this);
  }

  private static OrionObjectCache cacheName = new OrionObjectCache(100);
  private static OrionObjectCache cacheId = new OrionObjectCache(100);
}
