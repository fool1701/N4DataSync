/*
 * Copyright 2012 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.discover;

import javax.baja.job.BJob;
import javax.baja.job.BJobState;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BComponent;
import javax.baja.sys.BModule;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Lexicon;


/**
 * BNDiscoveryJob is the discover job for auto manager views.
 * <p>
 * A  BINDiscoveryHost instance is passed in the constructor to this object. The
 * discoveryHost is responsible for executing the discovery process in its
 * implementation of the getDiscoveryObjects() callback.
 * <p>
 * The discoveryPreferences are set before the job is submitted.  The preference
 * is passed in the callback to getDiscoveryObjects().  Access to this job is
 * provided through getJob() on the discoveryPreferences.
 * <p>
 * Discovery objects are placed as children on this object unless another folder
 * is specified.
 *
 * @author lperkins (Original ddf code)
 * @author Robert Adams (rework for ndriver)
 * @creation 25 Jan 2012
 */
@NiagaraType
/*
 This references the BFolder that contains the discovery objects
 if not stored on the job
 */
@NiagaraProperty(
  name = "discoveryFolder",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
/*
 Discovery preferences, passed in by the manager.
 */
@NiagaraProperty(
  name = "discoveryPreferences",
  type = "BNDiscoveryPreferences",
  defaultValue = "new BNDiscoveryPreferences()"
)
public class BNDiscoveryJob
  extends BJob
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.discover.BNDiscoveryJob(406618329)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "discoveryFolder"

  /**
   * Slot for the {@code discoveryFolder} property.
   * This references the BFolder that contains the discovery objects
   * if not stored on the job
   * @see #getDiscoveryFolder
   * @see #setDiscoveryFolder
   */
  public static final Property discoveryFolder = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code discoveryFolder} property.
   * This references the BFolder that contains the discovery objects
   * if not stored on the job
   * @see #discoveryFolder
   */
  public BOrd getDiscoveryFolder() { return (BOrd)get(discoveryFolder); }

  /**
   * Set the {@code discoveryFolder} property.
   * This references the BFolder that contains the discovery objects
   * if not stored on the job
   * @see #discoveryFolder
   */
  public void setDiscoveryFolder(BOrd v) { set(discoveryFolder, v, null); }

  //endregion Property "discoveryFolder"

  //region Property "discoveryPreferences"

  /**
   * Slot for the {@code discoveryPreferences} property.
   * Discovery preferences, passed in by the manager.
   * @see #getDiscoveryPreferences
   * @see #setDiscoveryPreferences
   */
  public static final Property discoveryPreferences = newProperty(0, new BNDiscoveryPreferences(), null);

  /**
   * Get the {@code discoveryPreferences} property.
   * Discovery preferences, passed in by the manager.
   * @see #discoveryPreferences
   */
  public BNDiscoveryPreferences getDiscoveryPreferences() { return (BNDiscoveryPreferences)get(discoveryPreferences); }

  /**
   * Set the {@code discoveryPreferences} property.
   * Discovery preferences, passed in by the manager.
   * @see #discoveryPreferences
   */
  public void setDiscoveryPreferences(BNDiscoveryPreferences v) { set(discoveryPreferences, v, null); }

  //endregion Property "discoveryPreferences"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNDiscoveryJob.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BNDiscoveryJob() {}


  public BNDiscoveryJob(BINDiscoveryHost databaseParent)
  {
    this.discoveryHost = databaseParent;
  }

////////////////////////////////////////////////////////////////
// BJob
////////////////////////////////////////////////////////////////

  /**
   * Launch a background thread which calls {@code run()}.
   */
  @Override
  public void doRun(Context cx)
  {
    thread = new JobThread(toPathString(), cx);
    thread.start();
  }

  /**
   * Set the state to canceling, and call interrupt on the background thread.
   * Note that Thread.interrupt usually only works on threads blocked on IO or
   * in a sleep.  Subclasses should also periodically check isAlive() in case
   * they don't receive the InterruptedException.
   */
  @Override
  public void doCancel(Context cx)
  {
    if (getJobState().isRunning())
    {
      setJobState(BJobState.canceling);
      if (thread != null)
      {
        thread.interrupt();
      }
    }
  }

  class JobThread extends Thread
  {
    JobThread(String name, Context cx)
    {
      super(name);
      this.cx = cx;
    }

    @Override
    public void run()
    {
      clearRootDiscoveryObjects();

      try
      {
        BNDiscoveryPreferences prefs = getDiscoveryPreferences();

        // Stash this job on preference for access in getDiscoveryObjects() callback
        prefs.job = BNDiscoveryJob.this;

        boolean done = false;

        while (!done)
        {
          // Get discovery objects from host
          BINDiscoveryObject[] discoveryObjects = discoveryHost.getDiscoveryObjects(prefs);

          // Add these to discovery table
          discoverOk(discoveryObjects);

          // Are we done yet?
          if (!prefs.isMultiStep() || discoveryObjects == null || !getJobState().isRunning())
          {
            done = true;
          }
        }
      }
      catch (Exception e)
      {
        jobException = e;
      }

      end();
    }

    Context cx;
  }

  /**
   * Override to return name for job bar
   */
  @Override
  public String toString(Context cx)
  {
    // Gets the name of the driver
    BModule mod = getDiscoveryPreferences().getType().getModule();

    // Allow lexicon to override this name
    String lexKey = getDiscoveryPreferences().getType().getTypeName() + ".discovery.jobBar.name";
    String driverName = mod.getLexicon().get(lexKey);

    // If no lexicon entry use module name
    if (driverName == null || driverName.length() == 0)
    {
      driverName = mod.getModuleName();
      driverName = TextUtil.toFriendly(driverName) + ' ' + LEX.getText("Discovery");
    }

    return driverName;
  }

////////////////////////////////////////////////////////////////
// APIs
////////////////////////////////////////////////////////////////

  /**
   * Complete the job.  If there was an error end in failed state if canceled
   * end in canceled state else end in success state.
   *
   * @since 3.8.38.1
   */
  protected void end()
  {
    if (jobException != null)
    {
      failed(jobException);
    }
    else if (isCanceled())
    {
      canceled();
    }
    else
    {
      success();
    }
  }

  /**
   * Convenience method to check if jobState is canceled or canceling
   *
   * @since 3.8.38.1
   */
  public boolean isCanceled()
  {
    return getJobState() == BJobState.canceling || getJobState() == BJobState.canceled;
  }

  /**
   * Call if discover fails for any reason.
   */
  public void discoverFail(String reason)
  {
    log().message(reason);
  }

////////////////////////////////////////////////////////////////
// BNDiscoveryJob
////////////////////////////////////////////////////////////////

  /**
   * Process array of discovery objects.
   */
  public void discoverOk(BINDiscoveryObject[] discoveryObjects)
  {
    if (discoveryObjects == null)
    {
      return;
    }
    for (int i = 0; i < discoveryObjects.length; i++)
    {
      addDiscoveryObject(discoveryObjects[i]);
    }
  }

  /**
   * Add specified discoveryObject to table.  Check for duplicates.
   *
   * @since 3.8.38.1
   */
  public void addDiscoveryObject(BINDiscoveryObject discoveryObject)
  {
    if (discoveryObject == null)
    {
      return;
    }

    BINDiscoveryObject[] objs = getRootDiscoveryObjects();
    for (int i = 0; i < objs.length; ++i)
    {
      if (objs[i].equivalent(discoveryObject))
      {
        return;
      }
    }

    discoveryFolder().add("d?", (BValue)discoveryObject);
  }


  /**
   * Gets the root-level BIDiscoveryObjects that this job discovered.
   */
  public BINDiscoveryObject[] getRootDiscoveryObjects()
  {
    return discoveryFolder().getChildren(BINDiscoveryObject.class);
  }

  /**
   * Removes all dynamic properties from under the discovery folder.
   */
  protected void clearRootDiscoveryObjects()
  {
    discoveryFolder().removeAll();
  }

  /**
   * Get the BComponent used to store discovery objects. This will be the
   * BComponent whose ord is specified in discoveryFolder or, if that is null,
   * this object.
   */
  public BComponent discoveryFolder()
  {
    BOrd disOrd = getDiscoveryFolder();
    BComponent fldr = null;
    if (!disOrd.isNull())
    {
      fldr = (BComponent)disOrd.get(this);
    }

    // If null discoveryFolder use this object
    return (fldr != null) ? fldr : this;
  }

  ////////////////////////////////////////////////////////////////
// Statics and Attributes
////////////////////////////////////////////////////////////////
  public static final Lexicon LEX = Lexicon.make(BNDiscoveryJob.class);

  private BINDiscoveryHost discoveryHost = null;
  private Exception jobException = null;
  private JobThread thread;
}
