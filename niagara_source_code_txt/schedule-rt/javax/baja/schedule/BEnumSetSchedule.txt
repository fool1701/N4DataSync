/*
 * Copyright 2001 Tridium, Inc.  All rights reserved.
 */

package javax.baja.schedule;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.schedule.*;

/**
 * Any schedule whose effectiveness can be determined by a set of
 * of integers.
 * @author Aaron Hansen
 * @creation Sept 2001
 * @version $Revision: 21$ $Date: 7/17/09 10:08:35 AM EDT$
 */
@NiagaraType
@NiagaraProperty(
  name = "effectiveWhenEmpty",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "set",
  type = "BEnumSet",
  defaultValue = "BEnumSet.DEFAULT",
  flags = Flags.USER_DEFINED_1
)
@NiagaraProperty(
  name = "singleSelection",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.USER_DEFINED_1
)
public abstract class BEnumSetSchedule
  extends BAbstractSchedule
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.schedule.BEnumSetSchedule(151981148)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "effectiveWhenEmpty"

  /**
   * Slot for the {@code effectiveWhenEmpty} property.
   * @see #getEffectiveWhenEmpty
   * @see #setEffectiveWhenEmpty
   */
  public static final Property effectiveWhenEmpty = newProperty(Flags.USER_DEFINED_1, true, null);

  /**
   * Get the {@code effectiveWhenEmpty} property.
   * @see #effectiveWhenEmpty
   */
  public boolean getEffectiveWhenEmpty() { return getBoolean(effectiveWhenEmpty); }

  /**
   * Set the {@code effectiveWhenEmpty} property.
   * @see #effectiveWhenEmpty
   */
  public void setEffectiveWhenEmpty(boolean v) { setBoolean(effectiveWhenEmpty, v, null); }

  //endregion Property "effectiveWhenEmpty"

  //region Property "set"

  /**
   * Slot for the {@code set} property.
   * @see #getSet
   * @see #setSet
   */
  public static final Property set = newProperty(Flags.USER_DEFINED_1, BEnumSet.DEFAULT, null);

  /**
   * Get the {@code set} property.
   * @see #set
   */
  public BEnumSet getSet() { return (BEnumSet)get(set); }

  /**
   * Set the {@code set} property.
   * @see #set
   */
  public void setSet(BEnumSet v) { set(set, v, null); }

  //endregion Property "set"

  //region Property "singleSelection"

  /**
   * Slot for the {@code singleSelection} property.
   * @see #getSingleSelection
   * @see #setSingleSelection
   */
  public static final Property singleSelection = newProperty(Flags.USER_DEFINED_1, false, null);

  /**
   * Get the {@code singleSelection} property.
   * @see #singleSelection
   */
  public boolean getSingleSelection() { return getBoolean(singleSelection); }

  /**
   * Set the {@code singleSelection} property.
   * @see #singleSelection
   */
  public void setSingleSelection(boolean v) { setBoolean(singleSelection, v, null); }

  //endregion Property "singleSelection"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BEnumSetSchedule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public BEnumSetSchedule() {}


  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /** @return this */
  public BEnumSetSchedule add(BEnum e)
  {
    return add(e, null);
  }

  /** @return this */
  public BEnumSetSchedule add(BEnum e, Context cx)
  {
    return add(e.getOrdinal());
  }

  /** @return this */
  public BEnumSetSchedule add(int i)
  {
   return add(i, null);
  }

  /** @return this */
  public BEnumSetSchedule add(int i, Context cx)
  {
    if (!isLegalValue(i))
      throw new IllegalArgumentException("Illegal value: " + i);
    if (getSet().contains(i))
      return this;
    if (getSingleSelection())
      clear();
    IntSet tmp = IntSet.wrap(getSet().getOrdinals());
    tmp.add(i);
    set(set, BEnumSet.make(tmp.intern()), cx);
    return this;
  }

  /** @return this */
  public BEnumSetSchedule clear()
  {
    setSet(BEnumSet.DEFAULT);
    return this;
  }

  /** @return this */
  public BEnumSetSchedule clear(Context cx)
  {
    set(set, BEnumSet.DEFAULT, cx);
    return this;
  }

  public boolean contains(BEnum e)
  {
    return contains(e.getOrdinal());
  }

  public boolean contains(int i)
  {
    return getSet().contains(i);
  }

  public int first()
  {
    return getSet().getOrdinals()[0];
  }

  /** The largest possible value. */
  public abstract int getMax();

  /** The smallest possible value. */
  public abstract int getMin();

  /**
   * Returns true if always effective, or empty and effective when empty.
   */
  public boolean isAlwaysEffective()
  {
    if (getAlwaysEffective())
      return true;
    if (getEffectiveWhenEmpty() && isEmpty())
      return true;
    return false;
  }

  public BEnumSetSchedule initEffectiveWhenEmpty(boolean arg)
  {
    setEffectiveWhenEmpty(arg);
    return this;
  }

  public BEnumSetSchedule initSingleSelection(boolean arg)
  {
    setSingleSelection(arg);
    return this;
  }

  public boolean isEmpty()
  {
    return getSet().isNull();
  }

  /**
   * The default implementation tests the arg against getMin() and getMax().
   * This assumes the entire range between min and max is valid.  Override
   * this method if that is not the case.
   */
  public boolean isLegalValue(int i)
  {
    if (i < getMin()) return false;
    if (i > getMax()) return false;
    return true;
  }

  /**
   * Return false if always effective, otherwise returns true if
   * empty.
   */
  public boolean isNeverEffective()
  {
    if (isAlwaysEffective())
      return false;
    return isEmpty();
  }

  /** @return this */
  public BEnumSetSchedule remove(BEnum e)
  {
    return remove(e.getOrdinal());
  }

  /** @return this */
  public BEnumSetSchedule remove(int i)
  {
    if (!contains(i))
      return this;
    IntSet tmp = IntSet.wrap(getSet().getOrdinals());
    tmp.remove(i);
    setSet(BEnumSet.make(tmp.intern()));
    return this;
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  protected boolean isEffective(int i)
  {
    if (contains(i))
      return true;
    return isAlwaysEffective();
  }


  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//BEnumSetSchedule
