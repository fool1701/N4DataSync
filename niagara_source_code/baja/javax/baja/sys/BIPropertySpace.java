/*
 * Copyright 2009, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIPropertySpace is an interface implemented by spaces that contain
 * BIPropertyContainer objects.  It is used for searching the space for objects
 * based on their properties.
 *
 * @author    Scott Hoye
 * @creation  28 Jun 2009
 * @version   $Revision: 1$ $Date: 8/18/09 4:26:55 PM EDT$
 * @since     Niagara 3.5
 */
@NiagaraType
public interface BIPropertySpace
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIPropertySpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIPropertySpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Find the objects with a property with the specified name.
   *
   * @param objectType The common type of the objects to return in the result.
   *   If null, the result is not filtered by object type.
   * @param baseOrd The base ord from which to start
   *   searching (only objects that are descendants of this base
   *   will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param cx The context for the search.
   */
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, Context cx);

  /**
   * Find the objects with a property with the specified name and type.
   *
   * @param objectType The common type of the objects to return in the result.
   *   If null, the result is not filtered by object type.
   * @param baseOrd The base ord from which to start
   *   searching (only objects that are descendants of this base
   *   will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param propertyType The type of the property to search for.  If null,
   *   any types will be included that have the given propertyName.
   * @param cx The context for the search.
   */
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, Type propertyType, Context cx);

  /**
   * Find the objects with the specified property name and value.
   *
   * @param objectType The common type of the objects to return in the result.
   *   If null, the result is not filtered by object type.
   * @param baseOrd The base ord from which to start
   *   searching (only objects that are descendants of this base
   *   will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param propertyValue The value of the property to search for.
   * @param cx The context for the search.
   */
  public Cursor<? extends BIObject> findObjects(Type objectType, BOrd baseOrd, String propertyName, BValue propertyValue, Context cx);

  /**
   * Find the distinct values for the specified property name within the space.
   *
   * @param objectType The common type of the objects to return in the result.
   *   If null, the result is not filtered by object type.
   * @param baseOrd The base ord from which to start
   *   searching (only objects that are descendants of this base
   *   will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param cx The context for the search.
   */
  public Cursor<? extends BIObject> findDistinctValues(Type objectType, BOrd baseOrd, String propertyName, Context cx);

  /**
   * Find the distinct values for the specified property name and type within the space.
   *
   * @param objectType The common type of the objects to return in the result.
   *   If null, the result is not filtered by object type.
   * @param baseOrd The base ord from which to start
   *   searching (only objects that are descendants of this base
   *   will be included in the result).  If null, the search will include the entire space.
   * @param propertyName The name of the property to search for.
   * @param propertyType The type of the property to search for.  If null,
   *   any types will be included that have the given propertyName.
   * @param cx The context for the search.
   */
  public Cursor<? extends BIObject> findDistinctValues(Type objectType, BOrd baseOrd, String propertyName, Type propertyType, Context cx);

  /**
   * Index the specified property.  Indexed properties should be more efficient to search.
   * Implementation of indexing is optional.
   *
   * @param propertyName The name of the property to index.
   * @param cx The context for the operation.
   *
   * @return Returns true if the requested index was created, false otherwise.
   */
  public boolean addIndex(String propertyName, Context cx);

  /**
   * Remove the index for the specified property.
   *
   * @param propertyName The name of the property that should no longer be indexed.
   * @param cx The context for the operation.
   */
  public void removeIndex(String propertyName, Context cx);
}
