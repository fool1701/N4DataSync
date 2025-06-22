/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.datatypes;

import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.lighting.BBacnetLightingOperation;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.AsnInput;
import javax.baja.bacnet.io.AsnOutput;
import javax.baja.bacnet.util.worker.IBacnetAddress;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnConst;

/**
 * Recipient for an alarm to be exported to Bacnet.
 * <p>
 * BBacnetLightingCommand represents the Bacnet LightingCommand
 * choice.
 *
 * @author Joseph Chandler
 * @creation 15 Apr 15
 * @since Niagara 4
 */

/*
 * BACnetLightingCommand ::= SEQUENCE {
 *    operation      [0] BACnetLightingOperation,
 *    target-level   [1] REAL (0.0..100.0) OPTIONAL,
 *    ramp-rate      [2] REAL (0.1..100.0) OPTIONAL,
 *    step-increment [3] REAL (0.1..100.0) OPTIONAL,
 *    fade-time      [4] Unsigned (100.. 86400000) OPTIONAL,
 *    priority       [5] Unsigned (1..16) OPTIONAL
 * }
 */

@NiagaraType
@NiagaraProperty(
  name = "operation",
  type = "BBacnetLightingOperation",
  defaultValue = "BBacnetLightingOperation.DEFAULT"
)
public class BBacnetLightingCommand
  extends BComponent
  implements BIBacnetDataType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.datatypes.BBacnetLightingCommand(3591767634)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "operation"

  /**
   * Slot for the {@code operation} property.
   * @see #getOperation
   * @see #setOperation
   */
  public static final Property operation = newProperty(0, BBacnetLightingOperation.DEFAULT, null);

  /**
   * Get the {@code operation} property.
   * @see #operation
   */
  public BBacnetLightingOperation getOperation() { return (BBacnetLightingOperation)get(operation); }

  /**
   * Set the {@code operation} property.
   * @see #operation
   */
  public void setOperation(BBacnetLightingOperation v) { set(operation, v, null); }

  //endregion Property "operation"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetLightingCommand.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////////

  /**
   * Default constructor.
   */
  public BBacnetLightingCommand()
  {
  }


  public BBacnetLightingCommand(BBacnetLightingOperation operation)
  {
    setOperation(operation);
  }

  public BBacnetLightingCommand(BBacnetLightingOperation operation,
                                Float targetLevel,
                                Float rampRate,
                                Float stepIncrement,
                                Long fadeTime,
                                Integer priority)
  {
    setOperation(operation);

    if (targetLevel != null)
      setTargetLevel(targetLevel);

    if (rampRate != null)
      setRampRate(rampRate);

    if (stepIncrement != null)
      setStepIncrement(stepIncrement);

    if (fadeTime != null)
      setFadeTime(fadeTime);

    if (priority != null)
      setPriority(priority);
  }

////////////////////////////////////////////////////////////////
//  Access Methods
////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////
//  BIBacnetDataType
////////////////////////////////////////////////////////////////

  /**
   * Write the value to the Asn output stream.
   *
   * @param out the AsnOutput stream.
   */
  public void writeAsn(AsnOutput out)
  {
    out.writeEnumerated(OPERATION_TAG, getOperation());

    Float targetLevel = getTargetLevel();
    if (targetLevel != null)
      out.writeReal(TARGET_LEVEL_TAG, targetLevel);

    Float rampRate = getRampRate();
    if (rampRate != null)
      out.writeReal(RAMP_RATE_TAG, rampRate);

    Float stepIncrement = getStepIncrement();
    if (stepIncrement != null)
      out.writeReal(STEP_INCREMENT_TAG, stepIncrement);

    Long fadeTime = getFadeTime();
    if (fadeTime != null)
      out.writeUnsignedInteger(FADE_TIME_TAG, fadeTime);

    Integer priority = getPriority();
    if (priority != null)
      out.writeUnsignedInteger(PRIORITY_TAG, priority);
  }

  /**
   * Read the value from the Asn input stream.
   *
   * @param in the AsnInput stream.
   */
  public void readAsn(AsnInput in)
    throws AsnException
  {
    set(operation, BLong.make(in.readEnumerated(OPERATION_TAG)), noWrite);

    if (in.peekTag() == TARGET_LEVEL_TAG)
      setTargetLevel(in.readReal(TARGET_LEVEL_TAG));

    if (in.peekTag() == RAMP_RATE_TAG)
      setRampRate(in.readReal(RAMP_RATE_TAG));

    if (in.peekTag() == STEP_INCREMENT_TAG)
      setStepIncrement(in.readReal(STEP_INCREMENT_TAG));

    if (in.peekTag() == FADE_TIME_TAG)
      setFadeTime(in.readUnsignedInteger(FADE_TIME_TAG));

    if (in.peekTag() == PRIORITY_TAG)
      setPriority(in.readUnsignedInt(PRIORITY_TAG));
  }

////////////////////////////////////////////////////////////////
// Optional fields
////////////////////////////////////////////////////////////////

  public Float getTargetLevel()
  {
    return getFloat("targetLevel");
  }

  public void setTargetLevel(Float targetLevel)
  {
    add("targetLevel", BFloat.make(targetLevel));
  }

  public Float getRampRate()
  {
    return getFloat("rampRate");
  }

  public void setRampRate(Float rampRate)
  {
    add("rampRate", BFloat.make(rampRate));
  }

  public Float getStepIncrement()
  {
    return getFloat("stepIncrement");
  }

  public void setStepIncrement(Float stepIncrement)
  {
    add("stepIncrement", BFloat.make(stepIncrement));
  }

  public Long getFadeTime()
  {
    BLong fadeTime = (BLong)get("fadeTime");
    if (fadeTime != null)
      return fadeTime.getLong();

    return null;
  }

  public void setFadeTime(Long fadeTime)
  {
    add("fadeTime", BLong.make(fadeTime));
  }

  public Integer getPriority()
  {
    BInteger priority = (BInteger)get("priority");
    if (priority != null)
      return priority.getInt();

    return null;
  }

  public void setPriority(Integer priority)
  {
    add("priority", BInteger.make(priority));
  }

////////////////////////////////////////////////////////////////
//  Utility
////////////////////////////////////////////////////////////////

  public Float getFloat(String name)
  {
    BFloat bFloat = (BFloat)get(name);
    if (bFloat != null)
      return bFloat.getFloat();

    return null;
  }


  /**
   * To String.
   */
  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder("" + getOperation());

    sb.append("\n\tTargetLevel: ").append(getTargetLevel())
      .append("\n\tRampRate: ").append(getRampRate())
      .append("\n\tStepIncrement: ").append(getStepIncrement())
      .append("\n\tFadeTime: ").append(getFadeTime())
      .append("\n\tPriority: ").append(getPriority());

    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int OPERATION_TAG = 0;
  public static final int TARGET_LEVEL_TAG = 1;
  public static final int RAMP_RATE_TAG = 2;
  public static final int STEP_INCREMENT_TAG = 3;
  public static final int FADE_TIME_TAG = 4;
  public static final int PRIORITY_TAG = 5;
}
