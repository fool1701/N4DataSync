/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.export;

import javax.baja.control.BPointExtension;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.driver.point.BProxyExt;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
@NiagaraProperty(
  name = "outOfService",
  type = "boolean",
  defaultValue = "false"
)
@NiagaraProperty(
  name = "presentValue",
  type = "BValue",
  defaultValue = "BBoolean.FALSE"
)
public class BOutOfServiceExt
  extends BPointExtension
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.export.BOutOfServiceExt(3606498683)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "outOfService"

  /**
   * Slot for the {@code outOfService} property.
   * @see #getOutOfService
   * @see #setOutOfService
   */
  public static final Property outOfService = newProperty(0, false, null);

  /**
   * Get the {@code outOfService} property.
   * @see #outOfService
   */
  public boolean getOutOfService() { return getBoolean(outOfService); }

  /**
   * Set the {@code outOfService} property.
   * @see #outOfService
   */
  public void setOutOfService(boolean v) { setBoolean(outOfService, v, null); }

  //endregion Property "outOfService"

  //region Property "presentValue"

  /**
   * Slot for the {@code presentValue} property.
   * @see #getPresentValue
   * @see #setPresentValue
   */
  public static final Property presentValue = newProperty(0, BBoolean.FALSE, null);

  /**
   * Get the {@code presentValue} property.
   * @see #presentValue
   */
  public BValue getPresentValue() { return get(presentValue); }

  /**
   * Set the {@code presentValue} property.
   * @see #presentValue
   */
  public void setPresentValue(BValue v) { set(presentValue, v, null); }

  //endregion Property "presentValue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOutOfServiceExt.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  public BOutOfServiceExt()
  {
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  @Override
  public void started()
    throws Exception
  {
    super.started();

    // Set bacnetValue with the right type and value.
    setPresentValue(getParentPoint().getOutStatusValue().getValueValue());
  }

  @Override
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning())
    {
      return;
    }
    if (p.equals(outOfService))
    {
      BComponent c = (BComponent)export;
      setPresentValue(getParentPoint().getOutStatusValue().getValueValue());
      int flags = c.getFlags(c.getSlot("reliability"));
      if (getOutOfService())
      {
        flags &= ~Flags.READONLY;
      }
      else
      {
        flags |= Flags.READONLY;
      }
      c.setFlags(c.getSlot("reliability"), flags);
    }
    executePoint();
    if (export != null)
    {
      if (export instanceof BIBacnetCovSource)
      {
        ((BIBacnetCovSource) export).checkCov();
      }
    }
  }

////////////////////////////////////////////////////////////////
// BPointExtension
////////////////////////////////////////////////////////////////

  @Override
  public void onExecute(BStatusValue working, Context cx)
  {
    // If the extension is "out of service", drive the working value
    // to the BACnet-written value.
    if (getOutOfService())
    {
      if (isCommandable)
      {
        BAbstractProxyExt apx = getParentPoint().getProxyExt();
        if (apx instanceof BProxyExt)
        {
          BProxyExt px = (BProxyExt)apx;
          px.writeReset();
        }
      }

      working.setValueValue(getPresentValue());
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public BIBacnetExportObject getExport()
  {
    return export;
  }

  public void setExport(BIBacnetExportObject exp)
  {
    export = exp;
  }

  public void setCommandable(boolean commandable)
  {
    isCommandable = commandable;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BIBacnetExportObject export;
  boolean isCommandable;
}
