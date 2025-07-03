/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.data.BIDataValue;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BIObject is the super type of all Baja types in the Niagara Framework.
 *
 * @author <a href="mailto:mgiannini@tridium.com">Matthew Giannini</a>
 * @since Niagara 4.0
 */

@NiagaraType
public interface BIObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BIObject(2979906276)1.0$ @*/
/* Generated Wed Dec 29 23:18:15 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIObject.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Narrow to the given type.
   */
  <T extends BIObject> T as(Class<T> cls);

  /**
   * Get a BIDataValue instance for this instance.  If this type is
   * already a BIDataValue then this instance will be returned.
   * Otherwise, the returned instance will be the result of converting
   * this type into a BIDataValue.  Data types are defined in the
   * Baja Data API.
   */
  BIDataValue toDataValue();

  /**
   * Return a String version of the object based on the
   * specified context.  If context is null then a
   * non-localized debug String designed for engineering
   * use should be returned.  But if context is not null
   * then the object should return a localized String
   * designed for end user display.   The default
   * implementation routes to the various toXString()
   * methods.
   */
  String toString(Context context);

  /**
   * Equivalent is used to compare if two objects have equivalent
   * state, but might not want to return true for equals since it
   * it has implied semantics for many operations.  The default
   * implementation returns the result of {@code equals()}.
   */
  boolean equivalent(Object obj);

  /**
   * Get the type of this instance.  By convention this
   * type is stored in a public static final field named
   * {@code TYPE}.  It should be loaded only once
   * for a given class using the {@link Sys#loadType(Class)} method.
   */
  Type getType();
}
