/*
 * @copyright 2016 Tridium Inc.
 */
package com.tridium.nrio.components;

import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.util.DualModuleUtils;

/**
 * BIo34OutputDefaultValues - This is a structure that is used to specify the DO and AO default values.
 *   These values are used by the IO module itself to put the AO's and DO's into a user defined state
 *   when it is not actively communicating with the Jace, i.e., off-line.
 *
 * @author    Andy Saunders
 * @creation  Oct 25, 2016
 */

@NiagaraType
@NiagaraProperty(
  name = "enableCommLossDefaults",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "enableStartupDefaults",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "commLossTimeout",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "startupTimeout",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.TRANSIENT
)
@NiagaraProperty(
  name = "do1",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do2",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do3",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do4",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do5",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do6",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do7",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do8",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do9",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "do10",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "ao1",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "ao2",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "ao3",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "ao4",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "ao5",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "ao6",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "ao7",
  type = "float",
  defaultValue = "0.0f"
)
@NiagaraProperty(
  name = "ao8",
  type = "float",
  defaultValue = "0.0f"
)


public class BIo34OutputDefaultValues
  extends BComponent
  implements BIOutputDefaultValues
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BIo34OutputDefaultValues(2473161724)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "enableCommLossDefaults"

  /**
   * Slot for the {@code enableCommLossDefaults} property.
   * @see #getEnableCommLossDefaults
   * @see #setEnableCommLossDefaults
   */
  public static final Property enableCommLossDefaults = newProperty(0, false, null);

  /**
   * Get the {@code enableCommLossDefaults} property.
   * @see #enableCommLossDefaults
   */
  public boolean getEnableCommLossDefaults() { return getBoolean(enableCommLossDefaults); }

  /**
   * Set the {@code enableCommLossDefaults} property.
   * @see #enableCommLossDefaults
   */
  public void setEnableCommLossDefaults(boolean v) { setBoolean(enableCommLossDefaults, v, null); }

  //endregion Property "enableCommLossDefaults"

  //region Property "enableStartupDefaults"

  /**
   * Slot for the {@code enableStartupDefaults} property.
   * @see #getEnableStartupDefaults
   * @see #setEnableStartupDefaults
   */
  public static final Property enableStartupDefaults = newProperty(0, false, null);

  /**
   * Get the {@code enableStartupDefaults} property.
   * @see #enableStartupDefaults
   */
  public boolean getEnableStartupDefaults() { return getBoolean(enableStartupDefaults); }

  /**
   * Set the {@code enableStartupDefaults} property.
   * @see #enableStartupDefaults
   */
  public void setEnableStartupDefaults(boolean v) { setBoolean(enableStartupDefaults, v, null); }

  //endregion Property "enableStartupDefaults"

  //region Property "commLossTimeout"

  /**
   * Slot for the {@code commLossTimeout} property.
   * @see #getCommLossTimeout
   * @see #setCommLossTimeout
   */
  public static final Property commLossTimeout = newProperty(Flags.READONLY | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code commLossTimeout} property.
   * @see #commLossTimeout
   */
  public int getCommLossTimeout() { return getInt(commLossTimeout); }

  /**
   * Set the {@code commLossTimeout} property.
   * @see #commLossTimeout
   */
  public void setCommLossTimeout(int v) { setInt(commLossTimeout, v, null); }

  //endregion Property "commLossTimeout"

  //region Property "startupTimeout"

  /**
   * Slot for the {@code startupTimeout} property.
   * @see #getStartupTimeout
   * @see #setStartupTimeout
   */
  public static final Property startupTimeout = newProperty(Flags.READONLY | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code startupTimeout} property.
   * @see #startupTimeout
   */
  public int getStartupTimeout() { return getInt(startupTimeout); }

  /**
   * Set the {@code startupTimeout} property.
   * @see #startupTimeout
   */
  public void setStartupTimeout(int v) { setInt(startupTimeout, v, null); }

  //endregion Property "startupTimeout"

  //region Property "do1"

  /**
   * Slot for the {@code do1} property.
   * @see #getDo1
   * @see #setDo1
   */
  public static final Property do1 = newProperty(0, false, null);

  /**
   * Get the {@code do1} property.
   * @see #do1
   */
  public boolean getDo1() { return getBoolean(do1); }

  /**
   * Set the {@code do1} property.
   * @see #do1
   */
  public void setDo1(boolean v) { setBoolean(do1, v, null); }

  //endregion Property "do1"

  //region Property "do2"

  /**
   * Slot for the {@code do2} property.
   * @see #getDo2
   * @see #setDo2
   */
  public static final Property do2 = newProperty(0, false, null);

  /**
   * Get the {@code do2} property.
   * @see #do2
   */
  public boolean getDo2() { return getBoolean(do2); }

  /**
   * Set the {@code do2} property.
   * @see #do2
   */
  public void setDo2(boolean v) { setBoolean(do2, v, null); }

  //endregion Property "do2"

  //region Property "do3"

  /**
   * Slot for the {@code do3} property.
   * @see #getDo3
   * @see #setDo3
   */
  public static final Property do3 = newProperty(0, false, null);

  /**
   * Get the {@code do3} property.
   * @see #do3
   */
  public boolean getDo3() { return getBoolean(do3); }

  /**
   * Set the {@code do3} property.
   * @see #do3
   */
  public void setDo3(boolean v) { setBoolean(do3, v, null); }

  //endregion Property "do3"

  //region Property "do4"

  /**
   * Slot for the {@code do4} property.
   * @see #getDo4
   * @see #setDo4
   */
  public static final Property do4 = newProperty(0, false, null);

  /**
   * Get the {@code do4} property.
   * @see #do4
   */
  public boolean getDo4() { return getBoolean(do4); }

  /**
   * Set the {@code do4} property.
   * @see #do4
   */
  public void setDo4(boolean v) { setBoolean(do4, v, null); }

  //endregion Property "do4"

  //region Property "do5"

  /**
   * Slot for the {@code do5} property.
   * @see #getDo5
   * @see #setDo5
   */
  public static final Property do5 = newProperty(0, false, null);

  /**
   * Get the {@code do5} property.
   * @see #do5
   */
  public boolean getDo5() { return getBoolean(do5); }

  /**
   * Set the {@code do5} property.
   * @see #do5
   */
  public void setDo5(boolean v) { setBoolean(do5, v, null); }

  //endregion Property "do5"

  //region Property "do6"

  /**
   * Slot for the {@code do6} property.
   * @see #getDo6
   * @see #setDo6
   */
  public static final Property do6 = newProperty(0, false, null);

  /**
   * Get the {@code do6} property.
   * @see #do6
   */
  public boolean getDo6() { return getBoolean(do6); }

  /**
   * Set the {@code do6} property.
   * @see #do6
   */
  public void setDo6(boolean v) { setBoolean(do6, v, null); }

  //endregion Property "do6"

  //region Property "do7"

  /**
   * Slot for the {@code do7} property.
   * @see #getDo7
   * @see #setDo7
   */
  public static final Property do7 = newProperty(0, false, null);

  /**
   * Get the {@code do7} property.
   * @see #do7
   */
  public boolean getDo7() { return getBoolean(do7); }

  /**
   * Set the {@code do7} property.
   * @see #do7
   */
  public void setDo7(boolean v) { setBoolean(do7, v, null); }

  //endregion Property "do7"

  //region Property "do8"

  /**
   * Slot for the {@code do8} property.
   * @see #getDo8
   * @see #setDo8
   */
  public static final Property do8 = newProperty(0, false, null);

  /**
   * Get the {@code do8} property.
   * @see #do8
   */
  public boolean getDo8() { return getBoolean(do8); }

  /**
   * Set the {@code do8} property.
   * @see #do8
   */
  public void setDo8(boolean v) { setBoolean(do8, v, null); }

  //endregion Property "do8"

  //region Property "do9"

  /**
   * Slot for the {@code do9} property.
   * @see #getDo9
   * @see #setDo9
   */
  public static final Property do9 = newProperty(0, false, null);

  /**
   * Get the {@code do9} property.
   * @see #do9
   */
  public boolean getDo9() { return getBoolean(do9); }

  /**
   * Set the {@code do9} property.
   * @see #do9
   */
  public void setDo9(boolean v) { setBoolean(do9, v, null); }

  //endregion Property "do9"

  //region Property "do10"

  /**
   * Slot for the {@code do10} property.
   * @see #getDo10
   * @see #setDo10
   */
  public static final Property do10 = newProperty(0, false, null);

  /**
   * Get the {@code do10} property.
   * @see #do10
   */
  public boolean getDo10() { return getBoolean(do10); }

  /**
   * Set the {@code do10} property.
   * @see #do10
   */
  public void setDo10(boolean v) { setBoolean(do10, v, null); }

  //endregion Property "do10"

  //region Property "ao1"

  /**
   * Slot for the {@code ao1} property.
   * @see #getAo1
   * @see #setAo1
   */
  public static final Property ao1 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code ao1} property.
   * @see #ao1
   */
  public float getAo1() { return getFloat(ao1); }

  /**
   * Set the {@code ao1} property.
   * @see #ao1
   */
  public void setAo1(float v) { setFloat(ao1, v, null); }

  //endregion Property "ao1"

  //region Property "ao2"

  /**
   * Slot for the {@code ao2} property.
   * @see #getAo2
   * @see #setAo2
   */
  public static final Property ao2 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code ao2} property.
   * @see #ao2
   */
  public float getAo2() { return getFloat(ao2); }

  /**
   * Set the {@code ao2} property.
   * @see #ao2
   */
  public void setAo2(float v) { setFloat(ao2, v, null); }

  //endregion Property "ao2"

  //region Property "ao3"

  /**
   * Slot for the {@code ao3} property.
   * @see #getAo3
   * @see #setAo3
   */
  public static final Property ao3 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code ao3} property.
   * @see #ao3
   */
  public float getAo3() { return getFloat(ao3); }

  /**
   * Set the {@code ao3} property.
   * @see #ao3
   */
  public void setAo3(float v) { setFloat(ao3, v, null); }

  //endregion Property "ao3"

  //region Property "ao4"

  /**
   * Slot for the {@code ao4} property.
   * @see #getAo4
   * @see #setAo4
   */
  public static final Property ao4 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code ao4} property.
   * @see #ao4
   */
  public float getAo4() { return getFloat(ao4); }

  /**
   * Set the {@code ao4} property.
   * @see #ao4
   */
  public void setAo4(float v) { setFloat(ao4, v, null); }

  //endregion Property "ao4"

  //region Property "ao5"

  /**
   * Slot for the {@code ao5} property.
   * @see #getAo5
   * @see #setAo5
   */
  public static final Property ao5 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code ao5} property.
   * @see #ao5
   */
  public float getAo5() { return getFloat(ao5); }

  /**
   * Set the {@code ao5} property.
   * @see #ao5
   */
  public void setAo5(float v) { setFloat(ao5, v, null); }

  //endregion Property "ao5"

  //region Property "ao6"

  /**
   * Slot for the {@code ao6} property.
   * @see #getAo6
   * @see #setAo6
   */
  public static final Property ao6 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code ao6} property.
   * @see #ao6
   */
  public float getAo6() { return getFloat(ao6); }

  /**
   * Set the {@code ao6} property.
   * @see #ao6
   */
  public void setAo6(float v) { setFloat(ao6, v, null); }

  //endregion Property "ao6"

  //region Property "ao7"

  /**
   * Slot for the {@code ao7} property.
   * @see #getAo7
   * @see #setAo7
   */
  public static final Property ao7 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code ao7} property.
   * @see #ao7
   */
  public float getAo7() { return getFloat(ao7); }

  /**
   * Set the {@code ao7} property.
   * @see #ao7
   */
  public void setAo7(float v) { setFloat(ao7, v, null); }

  //endregion Property "ao7"

  //region Property "ao8"

  /**
   * Slot for the {@code ao8} property.
   * @see #getAo8
   * @see #setAo8
   */
  public static final Property ao8 = newProperty(0, 0.0f, null);

  /**
   * Get the {@code ao8} property.
   * @see #ao8
   */
  public float getAo8() { return getFloat(ao8); }

  /**
   * Set the {@code ao8} property.
   * @see #ao8
   */
  public void setAo8(float v) { setFloat(ao8, v, null); }

  //endregion Property "ao8"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BIo34OutputDefaultValues.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BIo34OutputDefaultValues()
  {
  }

  public BIo34OutputDefaultValues (byte[] priDefaults, byte[] secDefaults)
  {
    NrioInputStream priIn = new NrioInputStream(priDefaults);
    NrioInputStream secIn = new NrioInputStream(secDefaults);
    setStartupTimeout(priIn.readInt());
    setCommLossTimeout(priIn.readInt());
    setEnableCommLossDefaults(getCommLossTimeout() != 0);
    setEnableStartupDefaults(getStartupTimeout() != 0);
    secIn.readInt(); // throw away secondary timer values
    secIn.readInt(); // throw away secondary timer values
    int doValues = priIn.read();
    doValues |= (secIn.read() & 0x0ff) << 4;
    for(int i = 0; i < MAX_DO*2; ++i)
    {
      int mask = 0x01 << i;
      switch(i)
      {
        case 0: setDo1((doValues & mask) != 0); break;
        case 1: setDo2((doValues & mask) != 0); break;
        case 2: setDo3((doValues & mask) != 0); break;
        case 3: setDo4((doValues & mask) != 0); break;
        case 4: setDo5((doValues & mask) != 0); break;
        case 5: setDo6((doValues & mask) != 0); break;
        case 6: setDo7((doValues & mask) != 0); break;
        case 7: setDo8((doValues & mask) != 0); break;
        case 8: setDo9((doValues & mask) != 0); break;
        case 9: setDo10((doValues & mask) != 0); break;
      }
    }
    float[] priAoValues = DualModuleUtils.bytesToValues(priIn);
    float[] secAoValues = DualModuleUtils.bytesToValues(secIn);

    for(int i = 0; i < 8; ++i)
    {
      switch(i)
      {
        case 0: setAo1(priAoValues[i]); break;
        case 1: setAo2(priAoValues[i]); break;
        case 2: setAo3(priAoValues[i]); break;
        case 3: setAo4(priAoValues[i]); break;
        case 4: setAo5(secAoValues[i-4]); break;
        case 5: setAo6(secAoValues[i-4]); break;
        case 6: setAo7(secAoValues[i-4]); break;
        case 7: setAo8(secAoValues[i-4]); break;
      }
    }
  }

  @Override
  public boolean isDualModule()
  {
    return true;
  }

  public void started()
    throws Exception
  {
    super.started();
    BComplex parent = getParent();
    if(parent instanceof BNrioDevice)
    {
      BNrioNetwork network = (BNrioNetwork)((BNrioDevice)parent).getNetwork();
      setCommLossTimeout(network.getOutputFailsafeConfig().getCommLossTimeout());
      setStartupTimeout(network.getOutputFailsafeConfig().getStartupTimeout());
    }
  }

  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if(!isRunning())
      return;
    if(p.isFrozen() && (getFlags(p) & Flags.TRANSIENT) == 0)
    {
      BComplex parent = getParent();
      if (parent instanceof BNrio34Module)
      {
        ((BNrio34Module)parent).postWriteOutputDefaults();
      }
    }
  }

  public void subscribed()
  {
    if(!isRunning())
    {
      return;
    }
    BComplex parent = getParent();
    if(parent instanceof BNrioDevice)
    {
      BDeviceNetwork net = ((BNrioDevice)parent).getNetwork();
      if(net != null && net instanceof BNrioNetwork)
      {
        BNrioNetwork network = (BNrioNetwork)net;
        setCommLossTimeout(network.getOutputFailsafeConfig().getCommLossTimeout());
        setStartupTimeout(network.getOutputFailsafeConfig().getStartupTimeout());
      }
    }
  }

  public BFacets getSlotFacets(Slot slot)
  {
    if(slot.isProperty())
    {
      BValue bValue = get(slot.asProperty());
      if(slot.asProperty().getDefaultValue() instanceof BFloat)
      {
        return aoFacets;
      }
    }
    return super.getSlotFacets(slot);
  }

  public byte[] toMessageBytes(boolean isPrimary)
  {
    int doDefaults = 0;
    int[] aoDefaults = new int[MAX_AO];
    byte[] rtnBytes = new byte[ARRAY_SIZE];
    if(isPrimary)
    {
      if (getDo1()) doDefaults |= 0x01;
      if (getDo2()) doDefaults |= 0x02;
      if (getDo3()) doDefaults |= 0x04;
      if (getDo4()) doDefaults |= 0x08;
      if (getDo5()) doDefaults |= 0x10;
      aoDefaults[0] = getRawValue(getAo1());
      aoDefaults[1] = getRawValue(getAo2());
      aoDefaults[2] = getRawValue(getAo3());
      aoDefaults[3] = getRawValue(getAo4());
    }
    else
    {
      if (getDo6()) doDefaults |= 0x01;
      if (getDo7()) doDefaults |= 0x02;
      if (getDo8()) doDefaults |= 0x04;
      if (getDo9()) doDefaults |= 0x08;
      if (getDo10()) doDefaults |= 0x10;
      aoDefaults[0] = getRawValue(getAo5());
      aoDefaults[1] = getRawValue(getAo6());
      aoDefaults[2] = getRawValue(getAo7());
      aoDefaults[3] = getRawValue(getAo8());
    }
    rtnBytes[0] = (byte)(doDefaults & 0x0ff);
    int byteIndex = 1;
    int nibbleIndex = 0;
    for(int i = 0; i < MAX_AO; ++i)
    {
      if( i%2 == 0 ) // even index
      {
        rtnBytes[byteIndex++] = (byte)((aoDefaults[i] >> 4) & 0x0ff);
        rtnBytes[byteIndex] = (byte)((aoDefaults[i] & 0x0f) << 4);
      }
      else // odd index
      {
        rtnBytes[byteIndex++] |= (byte)((aoDefaults[i] >> 8) & 0x0f);
        rtnBytes[byteIndex++] = (byte)(aoDefaults[i] & 0xff);
      }
    }
    return rtnBytes;
  }

  @Override
  public Property[] getDoProperties()
  {
    return doProps;
  }

  @Override
  public Property[] getAoProperties()
  {
    return aoProps;
  }

  private static Property[] doProps = { do1, do2, do3, do4, do5, do6, do7, do8, do9, do10 };
  private static Property[] aoProps = { ao1, ao2, ao3, ao4, ao5, ao6, ao7, ao8 };

//  private static BFacets aoFacets = BFacets.makeNumeric(UnitDatabase.getUnit("volt"), 1, 0, 10);
  private static int MAX_DO = 5;
  private static int MAX_AO = 4;
  private static int ARRAY_SIZE = 1 + (MAX_AO * 12)/8; // 12 bit AO values + 1 byte for DO values

}
