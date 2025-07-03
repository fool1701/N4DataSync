/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.virtual;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.datatypes.BBacnetArray;
import javax.baja.bacnet.datatypes.BBacnetPriorityValue;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.AuditEvent;
import javax.baja.security.Auditor;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnUtil;

@NiagaraType
public class BVirtualPropertyWrite
  extends BAction
  implements BacnetConst
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.virtual.BVirtualPropertyWrite(2979906276)1.0$ @*/
/* Generated Thu Dec 16 19:44:32 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVirtualPropertyWrite.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BVirtualPropertyWrite()
  {
  }

  /**
   * Get the default parameter to use for the
   * action, or null if the action takes no
   * arguments.
   */
  public BValue getParameterDefault()
  {
    BBacnetVirtualProperty bvp = (BBacnetVirtualProperty)getParent();
    BValue v = bvp.getValue();
    if (bvp.object().getPrioritizedPoint() && (bvp.getPropertyId() == BBacnetPropertyIdentifier.PRESENT_VALUE))
    {
      BBacnetPriorityValue pv = new BBacnetPriorityValue();
      pv.setPriorityValue(v);
      return pv;
    }
    else
    {
      return v.newCopy();
    }
  }

  /**
   * Get the parameter type for the action, or
   * if the action takes no arguments return null.
   */
  public Type getParameterType()
  {
    BBacnetVirtualProperty bvp = (BBacnetVirtualProperty)getParent();
    return bvp.getValue().getType();

  }

  /**
   * Get the return type for the action, or
   * null if the action doesn't return a value.
   */
  public Type getReturnType()
  {
    return null;
  }

  /**
   * Invoke the action on the specified target with
   * given argument array.
   */
  public BValue invoke(BComponent target, BValue arg)
    throws Exception
  {
    BBacnetVirtualProperty bvp = (BBacnetVirtualProperty)target;
    BValue currentValue = bvp.getValue();
    boolean audit = bvp.auditWrites();

    // Determine which type of write this is.
    if (bvp.object().getPrioritizedPoint() && (bvp.getPropertyId() == BBacnetPropertyIdentifier.PRESENT_VALUE))
    {
      // Prioritized write to a commandable point.
      byte[] encodedValue = AsnUtil.toAsn(arg);
      bvp.write(NOT_USED, encodedValue, bvp.object().getWritePriority());
    }
    else
    {
      // Non-command write.
      if (!currentValue.equals(arg))
      {
        // First we need to check if the value is an array.  These writes need
        // to be handled differently depending on whether the argument is the
        // full array or just one element.
        if (bvp.getValue() instanceof BBacnetArray)
        {
          if (arg instanceof BBacnetArray)
          {
            // Compare the current array and the argument array and manually
            // issue writes for the differences.
            BBacnetArray cur = (BBacnetArray)currentValue;
            BBacnetArray nue = (BBacnetArray)arg;

            // Start with the last element and work towards the first.  This
            // is so that in the case of a priority array, the highest priority
            // command will take effect and prevent the lower-priority commands
            // from causing rapid changes in the output as each higher priority
            // command takes effect.
            int len = nue.getSize();
            for (int i = 1; i <= len; i++)
            {
              if (!cur.getElement(i).equivalent(nue.getElement(i)))
              {
                // write this element
                bvp.write(i, AsnUtil.toAsn(nue.getElement(i)), NOT_USED);
              }
            }

            // Now set the virtual property value with the noWrite context
            // since we've already performed the BACnet write.
            bvp.setValue(nue, noWrite);
          }
          else
          {
            // This is a write of one element of an array, so the individual
            // array index configured on the virtual property needs to be 
            // used in the write request.
            int index = bvp.getArrayIndex();
            bvp.write(index, AsnUtil.toAsn(arg), NOT_USED);
          }
        }
        else
        {
          // Regular non-array case.  The property change will generate its
          // own audit event, so we do not need to generate one.
          bvp.setValue(arg);
          audit = false;
        }
      }
      else
      {
        // Re-write the same value.
        byte[] encodedValue = AsnUtil.toAsn(arg);
        bvp.write(NOT_USED, encodedValue, NOT_USED);
      }
    }

    // Now create an audit log entry if needed.
    if (audit)
    {
      AuditEvent event = new AuditEvent(AuditEvent.CHANGED,
        bvp.getAuditName(), "value",
        currentValue.toString(),
        arg.toString(),
        "");
      try
      {
        Auditor a = Sys.getAuditor();
        a.audit(event);
      }
      catch (ServiceNotFoundException e)
      {
        logger.info("Could not find AuditHistoryService to log audit event:" + event);
      }
    }
    return null;
  }

  private static final Logger logger = Logger.getLogger("bacnet.virtual");
}
