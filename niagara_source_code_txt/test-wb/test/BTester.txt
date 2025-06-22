/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package test;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BTester is a quick and dirty component for
 * hand run testing.
 *
 * @author    Brian Frank
 * @creation  1 Apr 01
 * @version   $Revision: 3$ $Date: 12/9/02 8:53:01 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "none"
)
@NiagaraAction(
  name = "booleanSimple",
  parameterType = "BBoolean",
  defaultValue = "BBoolean.TRUE"
)
@NiagaraAction(
  name = "intSimple",
  parameterType = "BInteger",
  defaultValue = "BInteger.make(2)"
)
@NiagaraAction(
  name = "floatSimple",
  parameterType = "BFloat",
  defaultValue = "BFloat.make(3)"
)
@NiagaraAction(
  name = "stringSimple",
  parameterType = "BString",
  defaultValue = "BString.make(\"hello world\")"
)
@NiagaraAction(
  name = "timeSimple",
  parameterType = "BAbsTime",
  defaultValue = "BAbsTime.make()"
)
@NiagaraAction(
  name = "linkBaby",
  parameterType = "BLink",
  defaultValue = "new BLink()"
)
public class BTester
  extends BComponent
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $test.BTester(3901675676)1.0$ @*/
/* Generated Thu Jun 02 14:30:06 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "none"

  /**
   * Slot for the {@code none} action.
   * @see #none()
   */
  public static final Action none = newAction(0, null);

  /**
   * Invoke the {@code none} action.
   * @see #none
   */
  public void none() { invoke(none, null, null); }

  //endregion Action "none"

  //region Action "booleanSimple"

  /**
   * Slot for the {@code booleanSimple} action.
   * @see #booleanSimple(BBoolean parameter)
   */
  public static final Action booleanSimple = newAction(0, BBoolean.TRUE, null);

  /**
   * Invoke the {@code booleanSimple} action.
   * @see #booleanSimple
   */
  public void booleanSimple(BBoolean parameter) { invoke(booleanSimple, parameter, null); }

  //endregion Action "booleanSimple"

  //region Action "intSimple"

  /**
   * Slot for the {@code intSimple} action.
   * @see #intSimple(BInteger parameter)
   */
  public static final Action intSimple = newAction(0, BInteger.make(2), null);

  /**
   * Invoke the {@code intSimple} action.
   * @see #intSimple
   */
  public void intSimple(BInteger parameter) { invoke(intSimple, parameter, null); }

  //endregion Action "intSimple"

  //region Action "floatSimple"

  /**
   * Slot for the {@code floatSimple} action.
   * @see #floatSimple(BFloat parameter)
   */
  public static final Action floatSimple = newAction(0, BFloat.make(3), null);

  /**
   * Invoke the {@code floatSimple} action.
   * @see #floatSimple
   */
  public void floatSimple(BFloat parameter) { invoke(floatSimple, parameter, null); }

  //endregion Action "floatSimple"

  //region Action "stringSimple"

  /**
   * Slot for the {@code stringSimple} action.
   * @see #stringSimple(BString parameter)
   */
  public static final Action stringSimple = newAction(0, BString.make("hello world"), null);

  /**
   * Invoke the {@code stringSimple} action.
   * @see #stringSimple
   */
  public void stringSimple(BString parameter) { invoke(stringSimple, parameter, null); }

  //endregion Action "stringSimple"

  //region Action "timeSimple"

  /**
   * Slot for the {@code timeSimple} action.
   * @see #timeSimple(BAbsTime parameter)
   */
  public static final Action timeSimple = newAction(0, BAbsTime.make(), null);

  /**
   * Invoke the {@code timeSimple} action.
   * @see #timeSimple
   */
  public void timeSimple(BAbsTime parameter) { invoke(timeSimple, parameter, null); }

  //endregion Action "timeSimple"

  //region Action "linkBaby"

  /**
   * Slot for the {@code linkBaby} action.
   * @see #linkBaby(BLink parameter)
   */
  public static final Action linkBaby = newAction(0, new BLink(), null);

  /**
   * Invoke the {@code linkBaby} action.
   * @see #linkBaby
   */
  public void linkBaby(BLink parameter) { invoke(linkBaby, parameter, null); }

  //endregion Action "linkBaby"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTester.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Testing
////////////////////////////////////////////////////////////////

  public void doNone()
    { System.out.println("none"); }

  public void doBooleanSimple(BBoolean x)
    { System.out.println("booleanSimple: " + x); }

  public void doIntSimple(BInteger x)
    { System.out.println("intSimple: " + x); }

  public void doFloatSimple(BFloat f)
    { System.out.println("floatSimple: " + f); }

  public void doStringSimple(BString x)
    { System.out.println("stringSimple: " + x); }

  public void doTimeSimple(BAbsTime x)
    { System.out.println("timeSimple: " + x); }

  public void doLinkBaby(BLink x)
    { System.out.println("linkBaby: " + x); }

}
