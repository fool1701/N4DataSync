/*
 * Copyright 2000-2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.file.BDirectory;
import javax.baja.file.BFileSystem;
import javax.baja.file.BLocalFileStore;
import javax.baja.file.FilePath;
import javax.baja.io.ValueDocDecoder;
import javax.baja.io.ValueDocEncoder;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Queue;

/**
 * Recipient for sending remote alarms.
 * Allows alarms that could not be sent to be queued
 * and resent at a later time.
 *
 * @author    Blake M Puhak
 * @creation  03 Sep 02
 * @version   $Revision: 36$ $Date: 7/8/11 7:32:57 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "status",
  type = "BStatus",
  defaultValue = "BStatus.DEFAULT",
  flags = Flags.READONLY
)
/*
 Last time new alarms were sucessfully sent.
 */
@NiagaraProperty(
  name = "lastSendTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY,
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE)")
)
/*
 Last time alarm acks were sucessfully sent.
 */
@NiagaraProperty(
  name = "lastAckSendTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.HIDDEN
)
/*
 Last time sending alarms failed.
 */
@NiagaraProperty(
  name = "lastFailureTime",
  type = "BAbsTime",
  defaultValue = "BAbsTime.NULL",
  flags = Flags.READONLY,
  facets = @Facet("BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE)")
)
@NiagaraProperty(
  name = "lastFailureCause",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
/*
 How often to try to resend failed alarms
 */
@NiagaraProperty(
  name = "retryInterval",
  type = "BRelTime",
  defaultValue = "BRelTime.make(15000)"
)
@NiagaraProperty(
  name = "queuedAlarmCount",
  type = "int",
  defaultValue = "0",
  flags = Flags.READONLY | Flags.TRANSIENT
)
/*
 Is this queue persistent? If not, then the
 queue is kept in-memory,  which means it is
 lost each time the station is restarted.
 */
@NiagaraProperty(
  name = "persistent",
  type = "boolean",
  defaultValue = "true"
)
/*
 Clears any Alarms that need to be resent.
 */
@NiagaraAction(
  name = "clearAlarmQueue"
)
public abstract class BRecoverableRecipient
  extends BAlarmRecipient
  implements BIStatus
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BRecoverableRecipient(83075640)1.0$ @*/
/* Generated Thu Jun 02 14:29:59 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(Flags.READONLY, BStatus.DEFAULT, null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public BStatus getStatus() { return (BStatus)get(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(BStatus v) { set(status, v, null); }

  //endregion Property "status"

  //region Property "lastSendTime"

  /**
   * Slot for the {@code lastSendTime} property.
   * Last time new alarms were sucessfully sent.
   * @see #getLastSendTime
   * @see #setLastSendTime
   */
  public static final Property lastSendTime = newProperty(Flags.READONLY, BAbsTime.NULL, BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE));

  /**
   * Get the {@code lastSendTime} property.
   * Last time new alarms were sucessfully sent.
   * @see #lastSendTime
   */
  public BAbsTime getLastSendTime() { return (BAbsTime)get(lastSendTime); }

  /**
   * Set the {@code lastSendTime} property.
   * Last time new alarms were sucessfully sent.
   * @see #lastSendTime
   */
  public void setLastSendTime(BAbsTime v) { set(lastSendTime, v, null); }

  //endregion Property "lastSendTime"

  //region Property "lastAckSendTime"

  /**
   * Slot for the {@code lastAckSendTime} property.
   * Last time alarm acks were sucessfully sent.
   * @see #getLastAckSendTime
   * @see #setLastAckSendTime
   */
  public static final Property lastAckSendTime = newProperty(Flags.HIDDEN, BAbsTime.NULL, null);

  /**
   * Get the {@code lastAckSendTime} property.
   * Last time alarm acks were sucessfully sent.
   * @see #lastAckSendTime
   */
  public BAbsTime getLastAckSendTime() { return (BAbsTime)get(lastAckSendTime); }

  /**
   * Set the {@code lastAckSendTime} property.
   * Last time alarm acks were sucessfully sent.
   * @see #lastAckSendTime
   */
  public void setLastAckSendTime(BAbsTime v) { set(lastAckSendTime, v, null); }

  //endregion Property "lastAckSendTime"

  //region Property "lastFailureTime"

  /**
   * Slot for the {@code lastFailureTime} property.
   * Last time sending alarms failed.
   * @see #getLastFailureTime
   * @see #setLastFailureTime
   */
  public static final Property lastFailureTime = newProperty(Flags.READONLY, BAbsTime.NULL, BFacets.make(BFacets.SHOW_MILLISECONDS, BBoolean.TRUE));

  /**
   * Get the {@code lastFailureTime} property.
   * Last time sending alarms failed.
   * @see #lastFailureTime
   */
  public BAbsTime getLastFailureTime() { return (BAbsTime)get(lastFailureTime); }

  /**
   * Set the {@code lastFailureTime} property.
   * Last time sending alarms failed.
   * @see #lastFailureTime
   */
  public void setLastFailureTime(BAbsTime v) { set(lastFailureTime, v, null); }

  //endregion Property "lastFailureTime"

  //region Property "lastFailureCause"

  /**
   * Slot for the {@code lastFailureCause} property.
   * @see #getLastFailureCause
   * @see #setLastFailureCause
   */
  public static final Property lastFailureCause = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code lastFailureCause} property.
   * @see #lastFailureCause
   */
  public String getLastFailureCause() { return getString(lastFailureCause); }

  /**
   * Set the {@code lastFailureCause} property.
   * @see #lastFailureCause
   */
  public void setLastFailureCause(String v) { setString(lastFailureCause, v, null); }

  //endregion Property "lastFailureCause"

  //region Property "retryInterval"

  /**
   * Slot for the {@code retryInterval} property.
   * How often to try to resend failed alarms
   * @see #getRetryInterval
   * @see #setRetryInterval
   */
  public static final Property retryInterval = newProperty(0, BRelTime.make(15000), null);

  /**
   * Get the {@code retryInterval} property.
   * How often to try to resend failed alarms
   * @see #retryInterval
   */
  public BRelTime getRetryInterval() { return (BRelTime)get(retryInterval); }

  /**
   * Set the {@code retryInterval} property.
   * How often to try to resend failed alarms
   * @see #retryInterval
   */
  public void setRetryInterval(BRelTime v) { set(retryInterval, v, null); }

  //endregion Property "retryInterval"

  //region Property "queuedAlarmCount"

  /**
   * Slot for the {@code queuedAlarmCount} property.
   * @see #getQueuedAlarmCount
   * @see #setQueuedAlarmCount
   */
  public static final Property queuedAlarmCount = newProperty(Flags.READONLY | Flags.TRANSIENT, 0, null);

  /**
   * Get the {@code queuedAlarmCount} property.
   * @see #queuedAlarmCount
   */
  public int getQueuedAlarmCount() { return getInt(queuedAlarmCount); }

  /**
   * Set the {@code queuedAlarmCount} property.
   * @see #queuedAlarmCount
   */
  public void setQueuedAlarmCount(int v) { setInt(queuedAlarmCount, v, null); }

  //endregion Property "queuedAlarmCount"

  //region Property "persistent"

  /**
   * Slot for the {@code persistent} property.
   * Is this queue persistent? If not, then the
   * queue is kept in-memory,  which means it is
   * lost each time the station is restarted.
   * @see #getPersistent
   * @see #setPersistent
   */
  public static final Property persistent = newProperty(0, true, null);

  /**
   * Get the {@code persistent} property.
   * Is this queue persistent? If not, then the
   * queue is kept in-memory,  which means it is
   * lost each time the station is restarted.
   * @see #persistent
   */
  public boolean getPersistent() { return getBoolean(persistent); }

  /**
   * Set the {@code persistent} property.
   * Is this queue persistent? If not, then the
   * queue is kept in-memory,  which means it is
   * lost each time the station is restarted.
   * @see #persistent
   */
  public void setPersistent(boolean v) { setBoolean(persistent, v, null); }

  //endregion Property "persistent"

  //region Action "clearAlarmQueue"

  /**
   * Slot for the {@code clearAlarmQueue} action.
   * Clears any Alarms that need to be resent.
   * @see #clearAlarmQueue()
   */
  public static final Action clearAlarmQueue = newAction(0, null);

  /**
   * Invoke the {@code clearAlarmQueue} action.
   * Clears any Alarms that need to be resent.
   * @see #clearAlarmQueue
   */
  public void clearAlarmQueue() { invoke(clearAlarmQueue, null, null); }

  //endregion Action "clearAlarmQueue"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRecoverableRecipient.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//  Methods
////////////////////////////////////////////////////////////////

  @Override
  public void started()
    throws Exception
  {
    //start the retry thread and see if there were any queued alarms from
    // the last time the station was running
    persistenceDirectory = BOrd.make("file:^^alarm/"+getName()+"AlarmQueue");

    retryThread = new RetryThread();
    retryThread.start();
  }

  @Override
  public void stopped()
    throws Exception
  {
    if (retryThread != null)
    {
      retryThread.kill();
      retryThread = null;
    }
  }

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps("RecoverableRecipient");
    out.prop("persistenceDirectory",              ""+persistenceDirectory);
    out.endProps();
    super.spy(out);
  }

  private void poll()
  {
    try
    {
      if (getPersistent() && getQueuedAlarmCount() > 0)
        dequeueDisk();
      else
        dequeueMemory();
    }
    catch(Exception e) {}

    if (getQueuedAlarmCount() == 0)
    {
      retryThread.kill();
      retryThread = null;
    }
  }

  @Override
  public void handleAlarm(BAlarmRecord alarmRecord)
  {
    if (logger.isLoggable(Level.FINE)) logger.fine("BRecoverableRecipient.handleAlarm " + alarmRecord.getTimestamp());
    try
    {
      if (logger.isLoggable(Level.FINE)) logger.fine("  sending ... " + alarmRecord.getTimestamp());

      //we have three possible outcomes for this method
      // 1) alarm is sent sucessfully
      // 2) alarm sending failed and we must retry
      // 3) alarm sending failed and we don't want to retry
      boolean sucess = sendAlarm(alarmRecord);
      if (!sucess) return;

      setLastSendTime(BAbsTime.make());
      if (logger.isLoggable(Level.FINE)) logger.fine("  sent @ " + getLastSendTime());
      setStatus(BStatus.ok);
    }
    catch(Exception e)
    {
      setStatus(BStatus.fault);
      setLastFailureTime(BAbsTime.make());

      if (e instanceof BajaRuntimeException)
        setLastFailureCause(e.getCause().toString());
      else
        setLastFailureCause(e.toString());
      if (logger.isLoggable(Level.FINE)) logger.fine("  failed @ " + getLastFailureTime() + " -> " + getLastFailureCause());

      if (getPersistent())
      {
        try
        {
          File file = null;
          synchronized(this)
          {
            file = new File(
              dirFile(),
              alarmRecord.getUuid().toString() + ".xml");
          }

          // encode the email
          try (ValueDocEncoder encoder = new ValueDocEncoder(file))
          {
            encoder.encodeDocument(alarmRecord);
          }

          setQueuedAlarmCount(dirFile().listFiles().length);
        }
        catch (IOException f)
        {
          f.printStackTrace();
        }
      }
      else
      {
        q.enqueue(alarmRecord);
        setQueuedAlarmCount(q.size());
      }

      if (retryThread == null)
      {
        retryThread = new RetryThread();
        retryThread.start();
      }

    }
  }

  public void doClearAlarmQueue()
  {
    if (getPersistent() && getQueuedAlarmCount() > 0)
    {
      try
      {
        File dir = dirFile();
        File[] kids = dir.listFiles();
        for (int i=0;i <kids.length; i++)
        {
          kids[i].delete();
          setQueuedAlarmCount(getQueuedAlarmCount()-1);
        }
      }
      catch (Exception e)
      {
        return;
      }
      setQueuedAlarmCount(0);
    }
    else
    {
      q.clear();
      setQueuedAlarmCount(0);
    }
  }

  private File dirFile()
    throws IOException
  {
    BFileSystem fs = BFileSystem.INSTANCE;
    FilePath path = (FilePath)persistenceDirectory.parse()[0];
    BDirectory bdir = fs.makeDir(path);
    return ((BLocalFileStore)bdir.getStore()).getLocalFile();
  }

  private void dequeueMemory()
    throws Exception
  {
    int size = q.size();
    for (int i=0; i<size; i++)
    {
      try
      {
        handleAlarm((BAlarmRecord)q.dequeue());
      }
      //catch(Exception e) { setQueuedAcks(q.size()); throw e; }
      finally { setQueuedAlarmCount(q.size()); }
    }
  }

  private void dequeueDisk()
    throws Exception
  {
    // retrieve files from the disk
    File dir = dirFile();
    File[] kids = dir.listFiles();

    // sort them by date
    Arrays.sort(kids, (o1, o2) -> {
      File f1 = o1;
      File f2 = o2;
      return (int) (f1.lastModified() - f2.lastModified());
    });

    // send them along
    try
    {
      for (int i = 0; i < kids.length; i++)
      {
        BAlarmRecord alarm;
        try (ValueDocDecoder decoder = new ValueDocDecoder(kids[i]))
        {
          alarm = (BAlarmRecord) decoder.decodeDocument();
        }

        handleAlarm(alarm);

        kids[i].delete();
      }
      setQueuedAlarmCount(0);
    }
    catch (Exception e)
    {
      //e.printStackTrace();
    }
  }

  /**
   * Override this method to do the actual sending of the alarm.
   *
   * @return true if alarm was sent successfully, false if alarm failed and no retry is needed.
   * @throws Exception if alarm fails to send - causes alarm to be added to retry queue.
   *
   */
  protected abstract boolean sendAlarm(BAlarmRecord alarm)
    throws Exception;

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("alarm.png");

  @Override
  public String[] getSubscribedAlarmClasses()
  {
    List<String> classes = new ArrayList<>();
    try
    {
      SlotCursor<Property> c = this.getProperties();
      while (c.next(BLink.class))
      {
        BLink link = (BLink)c.get();
        if (link.getSourceComponent() instanceof BAlarmClass)
        {
          classes.add(link.getSourceComponent().getName());
          // Remember, it's the name of the alarm class, not the alarm class
          // itself.  AlarmRecords store their alarm
          // class as a string, so we need to match that
        }
      }
    }
    //An exception should never be thrown but protect against a NPE
    catch (NullPointerException ignore) { }

    return classes.toArray(new String[classes.size()]);
  }

////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  public static final Logger logger = Logger.getLogger("alarm");
  private RetryThread retryThread;
  Queue q = new Queue();
  BOrd persistenceDirectory;

////////////////////////////////////////////////////////////////
//  Inner Classes
////////////////////////////////////////////////////////////////

  private class RetryThread
    extends Thread
  {
    public RetryThread()
    {
      super("alarm:RecipRetryThread");
    }

    void kill()
    {
      alive = false;
      interrupt();
    }

    @Override
    public void run()
    {
      alive = true;
      while(alive)
      {
        // wait till its time to poll again
        try
        {
          sleep(Math.max(getRetryInterval().getMillis(), 1000));
        }
        catch (InterruptedException e)
        {
          alive = false;
          break;
        }

        try
        {
          poll();
        }
        catch (Exception e) { }
      }
    }

    public boolean isRunning()
    {
      return alive;
    }

    private boolean alive = false;

  }

}
