/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm.ext.offnormal;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.baja.alarm.BAlarmRecord;
import javax.baja.alarm.ext.fault.BTwoStateFaultAlgorithm;
import javax.baja.control.BStringPoint;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusString;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BComponent;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BStringChangeOfStateAlgorithm implements a change of
 * state alarm detection algorithm for text strings.
 *
 * @author    Lee Adcock
 * @creation  17 June 10
 * @version   $Revision: 5$ $Date: 3/16/11 10:13:52 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 A regular expression
 */
@NiagaraProperty(
  name = "expression",
  type = "String",
  defaultValue = ".*"
)
/*
 If true, a match to the regular expression indicates a normal condition and
 a non-match is fault.  If false, a match is fault and a non-match is
 normal.
 */
@NiagaraProperty(
  name = "normalOnMatch",
  type = "boolean",
  defaultValue = "true"
)
/*
 Should text matching  be text sensitive
 */
@NiagaraProperty(
  name = "caseSensitive",
  type = "boolean",
  defaultValue = "true"
)
public class BStringChangeOfStateFaultAlgorithm
  extends BTwoStateFaultAlgorithm
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.ext.offnormal.BStringChangeOfStateFaultAlgorithm(4006759074)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "expression"

  /**
   * Slot for the {@code expression} property.
   * A regular expression
   * @see #getExpression
   * @see #setExpression
   */
  public static final Property expression = newProperty(0, ".*", null);

  /**
   * Get the {@code expression} property.
   * A regular expression
   * @see #expression
   */
  public String getExpression() { return getString(expression); }

  /**
   * Set the {@code expression} property.
   * A regular expression
   * @see #expression
   */
  public void setExpression(String v) { setString(expression, v, null); }

  //endregion Property "expression"

  //region Property "normalOnMatch"

  /**
   * Slot for the {@code normalOnMatch} property.
   * If true, a match to the regular expression indicates a normal condition and
   * a non-match is fault.  If false, a match is fault and a non-match is
   * normal.
   * @see #getNormalOnMatch
   * @see #setNormalOnMatch
   */
  public static final Property normalOnMatch = newProperty(0, true, null);

  /**
   * Get the {@code normalOnMatch} property.
   * If true, a match to the regular expression indicates a normal condition and
   * a non-match is fault.  If false, a match is fault and a non-match is
   * normal.
   * @see #normalOnMatch
   */
  public boolean getNormalOnMatch() { return getBoolean(normalOnMatch); }

  /**
   * Set the {@code normalOnMatch} property.
   * If true, a match to the regular expression indicates a normal condition and
   * a non-match is fault.  If false, a match is fault and a non-match is
   * normal.
   * @see #normalOnMatch
   */
  public void setNormalOnMatch(boolean v) { setBoolean(normalOnMatch, v, null); }

  //endregion Property "normalOnMatch"

  //region Property "caseSensitive"

  /**
   * Slot for the {@code caseSensitive} property.
   * Should text matching  be text sensitive
   * @see #getCaseSensitive
   * @see #setCaseSensitive
   */
  public static final Property caseSensitive = newProperty(0, true, null);

  /**
   * Get the {@code caseSensitive} property.
   * Should text matching  be text sensitive
   * @see #caseSensitive
   */
  public boolean getCaseSensitive() { return getBoolean(caseSensitive); }

  /**
   * Set the {@code caseSensitive} property.
   * Should text matching  be text sensitive
   * @see #caseSensitive
   */
  public void setCaseSensitive(boolean v) { setBoolean(caseSensitive, v, null); }

  //endregion Property "caseSensitive"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStringChangeOfStateFaultAlgorithm.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Parent checking
////////////////////////////////////////////////////////////////

  /**
   * A BBooleanChangeOfStateAlgorithm's grandparent must implement
   * the StringPoint interface
   */
  @Override
  public boolean isGrandparentLegal(BComponent grandparent)
  {
    return (grandparent instanceof BStringPoint);
  }

////////////////////////////////////////////////////////////////
//  property changed processing
////////////////////////////////////////////////////////////////

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning()) return;

    if (p.equals(expression) || p.equals(caseSensitive))
    {
      init();
    }

    executePoint();
  }

  @Override
  public void started()
  {
    init();
    super.started();
  }

  private void init()
  {
    try
    {
      int flags = Pattern.DOTALL;
      if (!getCaseSensitive())
      {
        flags |= Pattern.CASE_INSENSITIVE;
      }
      pattern = Pattern.compile(getExpression(), flags);
    }
    catch (PatternSyntaxException e)
    {
      log.log(Level.SEVERE, "Invalid Expression: " + getExpression(), e);
      pattern = null;
    }
  }

////////////////////////////////////////////////////////////////
//  Offnormal transition checking
////////////////////////////////////////////////////////////////

  /**
   * Return true if the present value
   */
  @Override
  protected boolean isNormal(BStatusValue o)
  {
    BStatusString out = (BStatusString)o;
    if (out.getStatus().isNull() || pattern == null)
    {
      return true;
    }
    Matcher matcher = pattern.matcher(out.getValue());
    final boolean found = matcher.find();
    return getNormalOnMatch() ? found : !found;
  }

  /**
   *  Write the key-value pairs defining alarm data for the
   *  alarm algorithm and state to the given Facets.
   *
   * @param out The relevant control point.
   * @param map The map.
   */
  @SuppressWarnings({"rawtypes","unchecked"})
  @Override
  public void writeAlarmData(BStatusValue out, java.util.Map map)
  {
    map.put(BAlarmRecord.STATUS, BString.make(out.getStatus().toString(null)));
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  protected static Logger log = Logger.getLogger("control");
  private Pattern pattern;
}
