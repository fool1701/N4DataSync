/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitControl.util;

import javax.baja.control.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.*;
import javax.baja.sys.*;

/**
 * BStringSubstring is a component that return substring from of a string.
 *
 * @author    Andy Saunders
 * @creation  05 April 2004
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "out",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.TRANSIENT | Flags.SUMMARY
)
@NiagaraProperty(
  name = "in",
  type = "BStatusString",
  defaultValue = "new BStatusString()",
  flags = Flags.SUMMARY
)
@NiagaraProperty(
  name = "beginIndex",
  type = "int",
  defaultValue = "0"
)
@NiagaraProperty(
  name = "endIndex",
  type = "int",
  defaultValue = "-1"
)
public class BStringSubstring
  extends BComponent
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitControl.util.BStringSubstring(1151735066)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "out"

  /**
   * Slot for the {@code out} property.
   * @see #getOut
   * @see #setOut
   */
  public static final Property out = newProperty(Flags.TRANSIENT | Flags.SUMMARY, new BStatusString(), null);

  /**
   * Get the {@code out} property.
   * @see #out
   */
  public BStatusString getOut() { return (BStatusString)get(out); }

  /**
   * Set the {@code out} property.
   * @see #out
   */
  public void setOut(BStatusString v) { set(out, v, null); }

  //endregion Property "out"

  //region Property "in"

  /**
   * Slot for the {@code in} property.
   * @see #getIn
   * @see #setIn
   */
  public static final Property in = newProperty(Flags.SUMMARY, new BStatusString(), null);

  /**
   * Get the {@code in} property.
   * @see #in
   */
  public BStatusString getIn() { return (BStatusString)get(in); }

  /**
   * Set the {@code in} property.
   * @see #in
   */
  public void setIn(BStatusString v) { set(in, v, null); }

  //endregion Property "in"

  //region Property "beginIndex"

  /**
   * Slot for the {@code beginIndex} property.
   * @see #getBeginIndex
   * @see #setBeginIndex
   */
  public static final Property beginIndex = newProperty(0, 0, null);

  /**
   * Get the {@code beginIndex} property.
   * @see #beginIndex
   */
  public int getBeginIndex() { return getInt(beginIndex); }

  /**
   * Set the {@code beginIndex} property.
   * @see #beginIndex
   */
  public void setBeginIndex(int v) { setInt(beginIndex, v, null); }

  //endregion Property "beginIndex"

  //region Property "endIndex"

  /**
   * Slot for the {@code endIndex} property.
   * @see #getEndIndex
   * @see #setEndIndex
   */
  public static final Property endIndex = newProperty(0, -1, null);

  /**
   * Get the {@code endIndex} property.
   * @see #endIndex
   */
  public int getEndIndex() { return getInt(endIndex); }

  /**
   * Set the {@code endIndex} property.
   * @see #endIndex
   */
  public void setEndIndex(int v) { setInt(endIndex, v, null); }

  //endregion Property "endIndex"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringSubstring.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BStringSubstring()
  {
  }
  
  /**
   * Init if started after steady state has been reached.
   */
  public void started()
  {
    getOut().setValue(calculate());
  }

/**
   * setoutput on in change.
   */
  public void changed(Property p, Context cx)
  {
    if (!isRunning()) return;

    if (p == in || p == beginIndex || p == endIndex)
    {
      getOut().setValue( calculate() );
    }
  }

  private String calculate()
  {
    try
    {
      if(!getIn().getStatus().isValid())
        return getOut().getValue();
      String input = getIn().getValue();
      int begin = getBeginIndex();
      int end = getEndIndex();
      if(end < 0)
        end = Integer.MAX_VALUE;
      if(begin < 0 || begin >= input.length()) return "";
      if( end >= input.length() )
        return input.substring(begin);
      return input.substring(begin, end);
    }
    catch(Exception e)
    {
      return "";
    }

  }

  public String toString(Context cx)
  {
    return getOut().toString(cx);
  }

////////////////////////////////////////////////////////////////
// BIStatus interface
////////////////////////////////////////////////////////////////

  public BStatus getStatus() { return getOut().getStatus(); }
  

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("control/control.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  
}
