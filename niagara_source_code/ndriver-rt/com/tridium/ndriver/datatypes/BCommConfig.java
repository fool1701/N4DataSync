/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.datatypes;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.spy.SpyWriter;
import javax.baja.status.BIStatus;
import javax.baja.status.BStatus;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BajaException;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.ndriver.BNNetwork;
import com.tridium.ndriver.comm.IComm;
import com.tridium.ndriver.comm.ICommListener;
import com.tridium.ndriver.comm.ILinkLayer;
import com.tridium.ndriver.comm.IMessageFactory;
import com.tridium.ndriver.comm.LinkMessage;
import com.tridium.ndriver.comm.NComm;
import com.tridium.ndriver.comm.NLinkMessageFactory;
import com.tridium.ndriver.comm.NMessage;

/**
 * BCommConfig is the base class for configuration classes of specific comm
 * stack implementations.
 *
 * @author Robert A Adams
 * @creation Oct 25, 2011
 */
@NiagaraType
/*
 Provides a short message why the network is in fault.
 */
@NiagaraProperty(
  name = "faultCause",
  type = "String",
  defaultValue = "",
  flags = Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE
)
/*
 Viewing this property in spy page resets comm statistics
 */
@NiagaraProperty(
  name = "statisticsReset",
  type = "BSpyReset",
  defaultValue = "new BSpyReset()",
  flags = Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT
)
public class BCommConfig
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BCommConfig(2279908999)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "faultCause"

  /**
   * Slot for the {@code faultCause} property.
   * Provides a short message why the network is in fault.
   * @see #getFaultCause
   * @see #setFaultCause
   */
  public static final Property faultCause = newProperty(Flags.TRANSIENT | Flags.READONLY | Flags.DEFAULT_ON_CLONE, "", null);

  /**
   * Get the {@code faultCause} property.
   * Provides a short message why the network is in fault.
   * @see #faultCause
   */
  public String getFaultCause() { return getString(faultCause); }

  /**
   * Set the {@code faultCause} property.
   * Provides a short message why the network is in fault.
   * @see #faultCause
   */
  public void setFaultCause(String v) { setString(faultCause, v, null); }

  //endregion Property "faultCause"

  //region Property "statisticsReset"

  /**
   * Slot for the {@code statisticsReset} property.
   * Viewing this property in spy page resets comm statistics
   * @see #getStatisticsReset
   * @see #setStatisticsReset
   */
  public static final Property statisticsReset = newProperty(Flags.HIDDEN | Flags.READONLY | Flags.TRANSIENT, new BSpyReset(), null);

  /**
   * Get the {@code statisticsReset} property.
   * Viewing this property in spy page resets comm statistics
   * @see #statisticsReset
   */
  public BSpyReset getStatisticsReset() { return (BSpyReset)get(statisticsReset); }

  /**
   * Set the {@code statisticsReset} property.
   * Viewing this property in spy page resets comm statistics
   * @see #statisticsReset
   */
  public void setStatisticsReset(BSpyReset v) { set(statisticsReset, v, null); }

  //endregion Property "statisticsReset"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCommConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Empty constructor.
   */
  public BCommConfig() {}
  
////////////////////////////////////////////////////////////////
// BComponent overrides
////////////////////////////////////////////////////////////////

  /**
   * Calls start() on comm stack. Calls ccStarted()to provide override point
   */
  @Override
  public final void started() throws Exception
  {
    if (commStarted)
    {
      return;  // Don't allow two starts without a stop
    }

    // Check if parent is okay for comm to start
    if (!okayToRun())
    {
      setFault(getStatusFault(), false);
      return;
    }

    init(); // create and start
    commStarted = true;

    ccStarted();
  }

  /**
   * Calls stop() on comm stack. Calls ccStopped()to provide override point
   */
  @Override
  public final void stopped() throws Exception
  {
    if (comm != null)
    {
      comm.stop();
    }
    ccStopped();
    commStarted = false;
  }

  /**
   * Calls verifySettings() on comm stack. Calls ccChanged()to provide override
   * point
   */
  @Override
  public final void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (p == faultCause)
    {
      return; // ignore - not a config change
    }

    if (comm != null)
    {
      try
      {
        comm.verifySettings(this);
        clearFault();
      }
      catch (Exception e)
      {
        setFault(e, true);
        throw new LocalizableRuntimeException("ndriver", "commConfig.configError", e);
      }
    }
    ccChanged(p, cx);
  }

////////////////////////////////////////////////////////////////
// Override points
////////////////////////////////////////////////////////////////

  /**
   * Override point called from BComponent.started() callback.
   */
  public void ccStarted()
  {
  }

  /**
   * Override point called from BComponent.stopped() callback.
   */
  public void ccStopped()
  {
  }

  /**
   * Override point called from BComponent.changed() callback.
   */
  public void ccChanged(Property p, Context cx)
  {
  }


  /**
   * Override point to create implementation of IComm.  Called from start()
   * callback or if not in a running station then called when comm stack access
   * by call to comm().
   * <p>
   * Default implementation returns NComm.
   */
  public IComm createComm()
  {
    return new NComm(this, defaultListener);
  }

  /**
   * Access the comm stack. 
   * 
   * @throws BajaRuntimeException If this could not be started
   */
  public final IComm comm()
  {
    if (comm == null)
    {
      try
      {
        started();
      }
      catch (Exception e)
      {
      }
    }
    if (startExp != null)
    {
      throw new BajaRuntimeException("Comm error", startExp);
    }
    return comm;
  }

  // Create and start the comm stack.  If exception store for later access.
  private void init()
  {
    try
    {
      if (comm == null)
      {
        comm = createComm();
      }
      comm.start();
      clearFault();
    }
    catch (Exception e)
    {
      setFault(e, true);
    }
  }

  private void clearFault()
  {
    setFaultCause("");
    if (startExp != null)
    {
      startExp = null;
      notifyParent();
    }
  }

  private void setFault(Exception e, boolean configFault)
  {
    startExp = e;
    setFaultCause(e.getMessage());
    if (configFault)
    {
      notifyParent();
    }
  }

  private void notifyParent()
  {
    BComponent c = getParent().asComponent();
    if (c == null)
    {
      return;
    }
    c.changed(getPropertyInParent(), null);
  }

////////////////////////////////////////////////////////////////
// Implementation
////////////////////////////////////////////////////////////////

  /**
   * Called from parent when parents status changed.  Means to notify comm to
   * allow start or stop based on parent fault/enable changes.
   *
   * @since 3.7.107
   */
  public final void statusUpdate()
  {
    boolean ok2Run = okayToRun();
    if (!ok2Run && commStarted)
    {
      try
      {
        stopped();
      }
      catch (Exception e)
      {
      }
      setFault(getStatusFault(), false);
    }
    else if (ok2Run && !commStarted)
    {
      try
      {
        started();
      }
      catch (Exception e)
      {
      }
    }
  }

  private boolean okayToRun()
  {
    if (!(getParent() instanceof BIStatus))
    {
      return true;
    }
    BStatus st = ((BIStatus)getParent()).getStatus();
    return !(st.isDisabled() || st.isFault());
  }

  private Exception getStatusFault()
  {
    BStatus st = ((BIStatus)getParent()).getStatus();
    if (st.isFault())
    {
      return new BajaRuntimeException("Comm error: parent in fault");
    }

    return new BajaRuntimeException("Comm error: parent is disabled");
  }

  /**
   * Set the default listener.
   */
  public void setDefaultListener(ICommListener listener)
  {
    defaultListener = listener;
    if (comm != null)
    {
      comm.setDefaultListener(listener);
    }
  }

  /**
   * Get message factory. This returns the instance of  {@code IMessageFactory}
   * created by {@code makeMessageFactory()}. The message factory is used
   * by ncomm to create the appropriate {@code NMessage} from an incoming
   * {@code LinkMessage}.
   */
  public final IMessageFactory getMessageFactory()
  {
    if (fac == null)
    {
      fac = makeMessageFactory();
    }
    return fac;
  }

  IMessageFactory fac = null;

  /**
   * Override point for subclasses to provide custom message factory. The
   * message factory is used by ncomm to create the appropriate NMessage from
   * incoming
   * {@code LinkMessage}.<p>
   * Developers must override this method to supply an implementation of
   * {@code IMessageFactory} that will create the appropriate driver
   * specific
   * subclass of {@code NMessage} for any incoming messages.
   */
  protected IMessageFactory makeMessageFactory()
  {
    // Dummy IMessageFactory
    return new IMessageFactory()
    {
      @Override
      public NMessage makeMessage(LinkMessage lm) throws Exception
      {
        NMessage nmsg = new NMessage((BAddress)lm.address);
        nmsg.fromInputStream(lm.getInputStream());
        return nmsg;
      }
    };
  }

  /**
   * Override point for subclasses to create appropriate LinkLayer.
   */
  public ILinkLayer makeLinkLayer(NComm comm)
  {
    throw new BajaRuntimeException("Not Implemented: Must implement specific comm config object");
  }

  /**
   * Override point for subclasses to configure the maximum number of request
   * messages that can be outstanding at the same time.  Default implementation
   * returns 32.
   */
  public int getMaxOutstandingTransactions()
  {
    return 32;
  }

  /**
   * Override point for subclasses to configure the maximum time to wait for a
   * free transaction in seconds. Default implementation returns 30 seconds.
   */
  public int getMaxTransactionWait()
  {
    return 30;
  }

  /**
   * Get NLinkMessageFactory
   */
  public final NLinkMessageFactory getLinkMessageFactory()
  {
    if (lnkFac == null)
    {
      lnkFac = makeLinkMessageFactory();
    }
    return lnkFac;
  }

  NLinkMessageFactory lnkFac = null;

  /**
   * Override point for subclasses to provide custom LinkMessage factory.<p> A
   * customized link message factory will override {@code NLinkMessageFactory}
   * which provides apis to maintain a cache of NLinkMessage instances.
   */
  protected NLinkMessageFactory makeLinkMessageFactory()
  {
    return new NLinkMessageFactory(32, 1500);
  }

  /**
   * Override point for subclasses to provide a driver specific prefix for logs
   * and thread names.<p> This default implementation will return a name derived
   * from this objects parentage.  If a parent network can be found that is an
   * instance of {@code BNNetwork} the network.{@code getNetworkName()}
   * will be returned. If not found then the class name of the immediate parent
   * will be returned.
   */
  public String getResourcePrefix()
  {
    String n;

    // Find BNNetwork parent
    BComplex p = getParent();
    while (p != null && !p.getType().is(BNNetwork.TYPE))
    {
      p = p.getParent();
    }

    // If found use network name
    if (p != null)
    {
      n = ((BNNetwork)p).getNetworkName();
    }
    // If not use parent class name
    else
    {
      n = getParent().getClass().getName();
      if (n.indexOf('.') >= 0)
      {
        n = n.substring(n.lastIndexOf('.') + 1);
      }
    }

    return n;
  }

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Provide some spy debug
   */
  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);

    if (comm == null && startExp == null)
    {
      // out.thTitle("start exception");
      out.thTitle("Comm stack not initialized.");
      return;
    }

    out.startProps("Comm Config");
    out.prop("okayToRun", okayToRun());
    out.prop("commStarted", commStarted);
    out.prop("defaultListener", (defaultListener != null) ? defaultListener.getClass().getName() : "null");
    out.endProps();

    if (startExp != null)
    {
      // There is a startup exception - dump that info to spy page 
      out.startProps("start exception");
      Throwable e = startExp;
      while (e != null)
      {
        out.tr(e.toString());
        if (e instanceof BajaException || e instanceof BajaRuntimeException)
        {
          e = e.getCause();
        }
        else 
        {
          break;
        }
      }
      out.endProps();
    }
    else if (comm != null)
    {
      comm.spy(out);
      resetRef(out);
    }
  }

  // Create a hotlink to statisticsReset which will call spyReset().
  private void resetRef(SpyWriter out)
  {
    // Create a ref from current page to statisticsReset property - this is a BSpyReset which will
    // call spyReset() below.
    out.startTable(false).tr().td().w("<b>")
      .a(out.getPath().getBody() + "/statisticsReset", "reset statistics")
      .w("</b>").endTd().endTr().endTable();
  }

  /**
   * Callback from BSpyReset if it's spy page is invoked.
   */
  public void spyReset()
  {
    if (comm != null)
    {
      comm.resetStats();
    }
  }

  @Override
  public BIcon getIcon()
  {
    return icon;
  }

  private static final BIcon icon = BIcon.std("gears.png");

  Exception startExp = null;
  IComm comm = null;
  protected ICommListener defaultListener = null;
  boolean commStarted = false;
}
