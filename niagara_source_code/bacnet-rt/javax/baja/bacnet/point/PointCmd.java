/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.point;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.status.BStatusValue;
import javax.baja.util.ICoalesceable;

import javax.baja.bacnet.BBacnetNetwork;
import javax.baja.bacnet.BacnetException;
import javax.baja.bacnet.io.ErrorException;
import javax.baja.bacnet.util.PollListEntry;

import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.stack.BBacnetPoll;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.transport.TransactionException;

public class PointCmd
  implements Runnable, ICoalesceable
{
  public PointCmd(int cmd, BBacnetProxyExt px)
  {
    this(cmd, px, null, 0, 0);
  }

  public PointCmd(int cmd, BBacnetProxyExt px, Object arg)
  {
    this(cmd, px, arg, 0, 0);
  }

  public PointCmd(int cmd, BBacnetProxyExt px, Object arg, int clr, int lvl)
  {
    this.cmd = cmd;
    this.px = px;
    this.arg = arg;
    this.clr = clr;
    this.lvl = lvl;
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("Bac PtCmd: px=");
    if (px != null)
    {
      sb.append(px.getParentPoint().getName())
        .append(" hc=").append(px.getHandle().hashCode());
    }

    sb.append(" cmd=").append(cmd())
      .append(" arg=").append(arg)
      .append(" clr=").append(clr)
      .append(" lvl=").append(lvl);

    return sb.toString();
  }

  private String cmd()
  {
    switch (cmd)
    {
      case READ_POINT:
        return READ_POINT_STR;
      case SUBSCRIBE_COV_POINT:
        return SUBSCRIBE_COV_POINT_STR;
      case SUBSCRIBE_COVP_POINT:
        return SUBSCRIBE_COVP_POINT_STR;
      case WRITE_POINT:
        return WRITE_POINT_STR;
      case READ_META_DATA:
        return READ_META_DATA_STR;
      default:
        return String.valueOf(cmd);
    }
  }

  ///////////////////////////////////
  // ICoalescable
  ///////////////////////////////////

  public Object getCoalesceKey()
  {
    return this;
  }

  public boolean equals(Object o)
  {
    if (o instanceof PointCmd)
    {
      PointCmd bac = (PointCmd)o;
      if (px == bac.px && cmd == bac.cmd/* && arg == bac.arg*/ && clr == bac.clr && lvl == bac.lvl)
        return true;
    }
    return false;
  }

  public int hashCode()
  {
    if (px != null)
    {
      Object handle = px.getHandle();
      if (handle != null)
        return handle.hashCode();
    }

    return 31 * cmd +
      37 * clr +
      41 * lvl;
  }

  public ICoalesceable coalesce(ICoalesceable c)
  {
    this.arg = ((PointCmd)c).arg;
    this.clr = ((PointCmd)c).clr;
    this.lvl = ((PointCmd)c).lvl;
    return this;
  }

  ///////////////////////////////////
  // Runnable
  ///////////////////////////////////

  public void run()
  {
    switch (cmd)
    {
      case READ_POINT:
        try
        {
          byte[] encodedValue =
            ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
              .readProperty(px.getDeviceAddress(),
                px.getObjectId(),
                px.getPropertyId().getOrdinal(),
                px.getPropertyArrayIndex());
          px.device().pingOk();
          px.fromEncodedValue(encodedValue, null, PollListEntry.forceCx);
        }
        catch (TransactionException e)
        {
          px.device().ping();
          px.readFail(e.toString());
          log.log(Level.WARNING, "TransactionException reading point value for " + px + " in " + px.device() + ": " + e, e);
        }
        catch (ErrorException e)
        {
          px.readFail(e.toString());
          px.setLastReadError(e.getErrorType());
          log.log(Level.WARNING, "ErrorException reading point value for " + px + " in " + px.device() + ": " + e, e);
        }
        catch (BacnetException e)
        {
          px.readFail(e.toString());
          px.setLastReadError(BBacnetProxyExt.ERROR_DEVICE_OTHER);
          log.log(Level.WARNING, "BacnetException reading point value for " + px + " in " + px.device() + ": " + e, e);
        }
        break;

      case SUBSCRIBE_COV_POINT:
        px.device().subscribeCov(px);
        break;
      case SUBSCRIBE_COVP_POINT:
        px.device().subscribeCovProperty(px);
        break;

      case WRITE_POINT:
        try
        {
          // Case 1: New value to a prioritized point: write new value at new level
          if ((lvl >= 1) && (lvl <= 16))
          {
            ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
              .writeProperty(px.getDeviceAddress(),
                px.getObjectId(),
                px.getPropertyId().getOrdinal(),
                px.getPropertyArrayIndex(),
                px.toEncodedValue((BStatusValue)arg),
                lvl);
          }
          else
          {
            // Case 2: New value to non-prioritized point, or to prioritized
            //         point at fallback level: write with no level (=16 for
            //         prioritized points)
            if (arg != null)
            {
              ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
                .writeProperty(px.getDeviceAddress(),
                  px.getObjectId(),
                  px.getPropertyId().getOrdinal(),
                  px.getPropertyArrayIndex(),
                  px.toEncodedValue((BStatusValue)arg));
            }
            else
            {
              // Case 3: New value is null: don't write (prioritized points
              //         will clear the old level below)
              if (clr == 0)
              {
                // Case 3A(12436): need to clear the level for priority array points here
                ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
                  .writeProperty(px.getDeviceAddress(),
                    px.getObjectId(),
                    px.getPropertyId().getOrdinal(),
                    px.getPropertyArrayIndex(),
                    AsnUtil.toAsnNull());
              }
            }
          }

          // Now check if the old value needs to be cleared.
          if ((clr > 0) && (clr != lvl))
          {
            if (clr == 17) // had a value at fallback, clear this with no index
              ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
                .writeProperty(px.getDeviceAddress(),
                  px.getObjectId(),
                  px.getPropertyId().getOrdinal(),
                  px.getPropertyArrayIndex(),
                  AsnUtil.toAsnNull());
            else // clear old level
              ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
                .writeProperty(px.getDeviceAddress(),
                  px.getObjectId(),
                  px.getPropertyId().getOrdinal(),
                  px.getPropertyArrayIndex(),
                  AsnUtil.toAsnNull(),
                  clr);
          }

          px.device().pingOk();
          px.writeOk((BStatusValue)arg);
          px.setWriteStatus(BBacnetProxyExt.OK);
          ((BBacnetPoll)px.network().getPollService(px)).pollNow(px);
        }
        catch (TransactionException e)
        {
          px.device().ping();
          px.writeFail(e.toString());
          log.log(Level.WARNING, "TransactionException writing point value for " + px + " in " + px.device() + ": " + e, e);
        }
        catch (BacnetException e)
        {
          px.writeFail(e.toString());
          log.log(Level.SEVERE, "BacnetException writing point value for " + px + ": " + e, e);
        }
        break;

      case READ_META_DATA:
        try
        {
          PollListEntry[] ples = px.getPollListEntries();
          for (int i = 1; i < ples.length; i++)
          {
            byte[] encodedValue =
              ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient()
                .readProperty(ples[i].getDevice().getAddress(),
                  ples[i].getObjectId(),
                  ples[i].getPropertyId(),
                  ples[i].getPropertyArrayIndex());
            BStatusValue sv = (BStatusValue)px.getReadValue().newCopy();
            px.readMetaData(encodedValue, ples[i], sv);
            px.readOk(sv);
            px.setLastReadError(null);
          }
          px.device().pingOk();
        }
        catch (TransactionException e)
        {
          px.device().ping();
          px.readFail(e.toString());
          log.log(Level.WARNING, "TransactionException reading metadata for " + px + " in " + px.device() + ": " + e, e);
        }
        catch (ErrorException e)
        {
          px.readFail(e.toString());
          px.setLastReadError(e.getErrorType());
          log.log(Level.WARNING, "ErrorException reading metadata for " + px + " in " + px.device() + ": " + e, e);
        }
        catch (BacnetException e)
        {
          px.readFail(e.toString());
          px.setLastReadError(BBacnetProxyExt.ERROR_DEVICE_OTHER);
          log.log(Level.WARNING, "BacnetException reading metadata for " + px + " in " + px.device() + ": " + e, e);
        }
        break;

      default:
        throw new RuntimeException("Invalid cmd {" + cmd + " } in PointCmd.");
    }
  }

  // Attributes
  private int cmd;
  private BBacnetProxyExt px;
  private Object arg;
  private int clr;
  private int lvl;

  static Logger log = Logger.getLogger("bacnet.point");

  public static final int SUBSCRIBE_COVP_POINT = 0xf0000000;
  public static final int READ_POINT           = 0x80000000;
  public static final int SUBSCRIBE_COV_POINT  = 0x40000000;
  public static final int WRITE_POINT          = 0x20000000;
  public static final int READ_META_DATA       = 0x10000000;

  private static final String READ_POINT_STR = "read";
  private static final String SUBSCRIBE_COV_POINT_STR = "subscribeCov";
  private static final String SUBSCRIBE_COVP_POINT_STR = "subscribeCovProperty";
  private static final String WRITE_POINT_STR = "write";
  private static final String READ_META_DATA_STR = "readMetaData";
}
