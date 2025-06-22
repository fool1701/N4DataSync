/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarmOrion;

import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.alarmOrion.OrionObjectCache;
import com.tridium.orion.BOrionObject;
import com.tridium.orion.OrionCursor;
import com.tridium.orion.OrionSession;
import com.tridium.orion.OrionType;
import com.tridium.orion.annotations.NiagaraOrionType;
import com.tridium.orion.sql.PropertyValue;

@NiagaraType
@NiagaraOrionType
@NiagaraProperty(
  name = "id",
  type = "int",
  defaultValue = "-1",
  flags = Flags.READONLY | Flags.SUMMARY,
  facets = @Facet("ID_KEY")
)
/*
 The path to the source of the alarm.
 */
@NiagaraProperty(
  name = "source",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.SUMMARY,
  facets = {
    @Facet(name = "UNIQUE", value = "true"),
    @Facet(name = "WIDTH", value = "SOURCE_LENGTH")
  }
)
public class BOrionAlarmSource
  extends BOrionObject
{
  public final static int OLD_SOURCE_LENGTH  = 256; //This number is too big for MySql 5.6
  public final static int SOURCE_LENGTH      = AccessController.doPrivileged((PrivilegedAction<Integer>)
    () -> Integer.getInteger("niagara.alarmOrion.maxSourceLength", 255).intValue());
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarmOrion.BOrionAlarmSource(2969466239)1.0$ @*/
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

  //region Property "source"

  /**
   * Slot for the {@code source} property.
   * The path to the source of the alarm.
   * @see #getSource
   * @see #setSource
   */
  public static final Property source = newProperty(Flags.SUMMARY, BOrd.NULL, BFacets.make(BFacets.make(UNIQUE, true), BFacets.make(WIDTH, SOURCE_LENGTH)));

  /**
   * Get the {@code source} property.
   * The path to the source of the alarm.
   * @see #source
   */
  public BOrd getSource() { return (BOrd)get(source); }

  /**
   * Set the {@code source} property.
   * The path to the source of the alarm.
   * @see #source
   */
  public void setSource(BOrd v) { set(source, v, null); }

  //endregion Property "source"

  //region Type

  @Override
  public Type getType() { return getTypeFromSpace(TYPE); }
  public static final Type TYPE = Sys.loadType(BOrionAlarmSource.class);

  //endregion Type
  public static final OrionType ORION_TYPE = (OrionType)TYPE;

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static BOrionAlarmSource get(BOrd ord, OrionSession session)
  {
    if(cache.contains(ord))
      return (BOrionAlarmSource)cache.get(ord);
    
    OrionCursor cursor = session.select(BOrionAlarmSource.ORION_TYPE, new PropertyValue(BOrionAlarmSource.source, ord));
    BOrionAlarmSource alarmSource;
    if(cursor.next())
    {
      alarmSource = (BOrionAlarmSource)cursor.get();
      cursor.close();
    } else {
      alarmSource = new BOrionAlarmSource();
      alarmSource.setSource(ord);
      session.insert(alarmSource);
    }
    cache.put(ord, alarmSource);
    return alarmSource;
  }
  
  public void afterDelete(OrionSession session)
  {
    cache.remove(getSource());
  }

  public void afterInsert(OrionSession session)
  {
    cache.put(getSource(), this);
  }

  public void afterUpdate(OrionSession session)
  {
    cache.update(getSource(), this);
  }  
  
  private static OrionObjectCache cache = new OrionObjectCache(100);  
}
