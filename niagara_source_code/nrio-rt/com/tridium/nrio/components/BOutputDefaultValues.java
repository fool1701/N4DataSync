/*
 * @copyright 2016 Tridium Inc.
 */
package com.tridium.nrio.components;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.name;

import javax.baja.driver.BDeviceNetwork;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusNumeric;
import javax.baja.sys.BBlob;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BFloat;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BString;
import javax.baja.sys.BStruct;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;

import com.tridium.nrio.BNrio16Module;
import com.tridium.nrio.BNrio34Module;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.enums.BDoDefaultValueSelect;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.util.DualModuleUtils;

/**
 * BOutputDefaultValues - This is a structure that is used to specify the DO and AO default values.
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


public class BOutputDefaultValues
  extends BComponent
  implements BIOutputDefaultValues
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.components.BOutputDefaultValues(2054457070)1.0$ @*/
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

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOutputDefaultValues.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BOutputDefaultValues()
  {
  }

  public static BOutputDefaultValues makeFromBytes(byte[] priDefaults)
  {
    BOutputDefaultValues outputDefaults = new BOutputDefaultValues();
    NrioInputStream priIn = new NrioInputStream(priDefaults);
    outputDefaults.setStartupTimeout(priIn.readInt());
    outputDefaults.setCommLossTimeout(priIn.readInt());
    outputDefaults.setEnableCommLossDefaults(outputDefaults.getCommLossTimeout() != 0);
    outputDefaults.setEnableStartupDefaults(outputDefaults.getStartupTimeout() != 0);
    int doValues = priIn.read();
    for(int i = 0; i < 4; ++i)
    {
      int mask = 0x01 << i;
      switch(i)
      {
        case 0: outputDefaults.setDo1((doValues & mask) != 0); break;
        case 1: outputDefaults.setDo2((doValues & mask) != 0); break;
        case 2: outputDefaults.setDo3((doValues & mask) != 0); break;
        case 3: outputDefaults.setDo4((doValues & mask) != 0); break;
      }
    }
    float[] priAoValues = DualModuleUtils.bytesToValues(priIn);

    for(int i = 0; i < 4; ++i)
    {
      switch(i)
      {
        case 0: outputDefaults.setAo1(priAoValues[i]); break;
        case 1: outputDefaults.setAo2(priAoValues[i]); break;
        case 2: outputDefaults.setAo3(priAoValues[i]); break;
        case 3: outputDefaults.setAo4(priAoValues[i]); break;
      }
    }
    return outputDefaults;
  }

  @Override
  public boolean isDualModule()
  {
    return false;
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
    if(!isRunning() || ((cx!=null) && cx.equals(Context.decoding)))
      return;
    if(p.isFrozen() && (getFlags(p) & Flags.TRANSIENT) == 0)
    {
      BComplex parent = getParent();
      if (parent instanceof BNrio16Module)
      {
        ((BNrio16Module)parent).postWriteOutputDefaults();
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
        BNrioNetwork network = (BNrioNetwork)((BNrioDevice)parent).getNetwork();
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

  public byte[] toMessageBytes(boolean ignore)
  {
    int doDefaults = 0;
    int[] aoDefaults = new int[MAX_AO];
    byte[] rtnBytes = new byte[ARRAY_SIZE];
    {
      if (getDo1()) doDefaults |= 0x01;
      if (getDo2()) doDefaults |= 0x02;
      if (getDo3()) doDefaults |= 0x04;
      if (getDo4()) doDefaults |= 0x08;
      aoDefaults[0] = getRawValue(getAo1());
      aoDefaults[1] = getRawValue(getAo2());
      aoDefaults[2] = getRawValue(getAo3());
      aoDefaults[3] = getRawValue(getAo4());
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

  private static Property[] doProps = { do1, do2, do3, do4 };
  private static Property[] aoProps = { ao1, ao2, ao3, ao4 };
//  private static BFacets aoFacets = BFacets.makeNumeric(UnitDatabase.getUnit("volt"), 1, 0, 10);
//  private static int MAX_DO = 4;
//  private static int MAX_AO = 4;
//  private static int ARRAY_SIZE = 1 + (MAX_AO * 12)/8; // 12 bit AO values + 1 byte for DO values

}
