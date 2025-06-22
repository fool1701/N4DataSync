/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.agent.AgentList;
import javax.baja.control.ext.BAbstractProxyExt;
import javax.baja.control.ext.BNullProxyExt;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BSpace;
import javax.baja.status.BIStatusValue;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sync.BProxyComponentSpace;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.LinkCheck;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.schema.Fw;
import com.tridium.sys.schema.NProperty;

/**
 * BControlPoint is the base class for all point types in
 * the Baja control architecture.  A ControlPoint typically
 * maps to one value being read or written via a driver.
 * All ControlPoints have a BStatusValue property called "out".
 * The most common types of ControlPoints are BBooleanPoint and
 * BNumericPoint which model a binary and analog value respectively.
 * <p>
 * Point extensions allow control point behavior to
 * be extended in a consistent manner.  Each property
 * of a BControlPoint which subclasses from BPointExtension
 * is considered an extension on the point.  Extensions allow
 * plug-in functionality such as alarming and historical
 * data collection via special hooks that BControlPoint
 * provides to BPointExtension.
 * <p>
 * If the predefined proxyExt is not a NullProxyExt then
 * the point is considered a proxy point which means that
 * it is a local representation of a point which actually
 * exists in an external device.  The driver framework is
 * is used to maintain synchronization.
 * <p>
 * Standard execution flow of ControlPoints:
 * <pre>
 *
 * 1) Input is changed (any property which isn't out)
 *
 * 2) Execute action is invoked asynchronously
 *
 * 3) The doExecute method is called when point is scheduled
 *    for execution by framework runtime engine
 *
 * 4) Point itself updates working variable via ControlPoint.onExecute
 *
 * 5) Each extension updates working variable via PointExtension.onExecute;
 *    extensions are executed in slot declaration order
 *
 * 6) Working var is used to set the out property
 *
 *</pre>
 *
 * @author    Brian Frank
 * @creation  11 Oct 00
 * @version   $Revision: 83$ $Date: 3/3/10 9:11:21 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 These facets are applied against the out property.
 */
@NiagaraProperty(
  name = "facets",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
/*
 This frozen property always holds the proxy
 extension, or an instance of BNullProxyExt
 if this point is not a proxy point.
 */
@NiagaraProperty(
  name = "proxyExt",
  type = "BAbstractProxyExt",
  defaultValue = "new BNullProxyExt()"
)
@NiagaraAction(
  name = "execute",
  flags = Flags.ASYNC | Flags.HIDDEN
)
public abstract class BControlPoint
  extends BComponent
  implements BIStatusValue
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.control.BControlPoint(4162736207)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "facets"

  /**
   * Slot for the {@code facets} property.
   * These facets are applied against the out property.
   * @see #getFacets
   * @see #setFacets
   */
  public static final Property facets = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public BFacets getFacets() { return (BFacets)get(facets); }

  /**
   * Set the {@code facets} property.
   * These facets are applied against the out property.
   * @see #facets
   */
  public void setFacets(BFacets v) { set(facets, v, null); }

  //endregion Property "facets"

  //region Property "proxyExt"

  /**
   * Slot for the {@code proxyExt} property.
   * This frozen property always holds the proxy
   * extension, or an instance of BNullProxyExt
   * if this point is not a proxy point.
   * @see #getProxyExt
   * @see #setProxyExt
   */
  public static final Property proxyExt = newProperty(0, new BNullProxyExt(), null);

  /**
   * Get the {@code proxyExt} property.
   * This frozen property always holds the proxy
   * extension, or an instance of BNullProxyExt
   * if this point is not a proxy point.
   * @see #proxyExt
   */
  public BAbstractProxyExt getProxyExt() { return (BAbstractProxyExt)get(proxyExt); }

  /**
   * Set the {@code proxyExt} property.
   * This frozen property always holds the proxy
   * extension, or an instance of BNullProxyExt
   * if this point is not a proxy point.
   * @see #proxyExt
   */
  public void setProxyExt(BAbstractProxyExt v) { set(proxyExt, v, null); }

  //endregion Property "proxyExt"

  //region Action "execute"

  /**
   * Slot for the {@code execute} action.
   * @see #execute()
   */
  public static final Action execute = newAction(Flags.ASYNC | Flags.HIDDEN, null);

  /**
   * Invoke the {@code execute} action.
   * @see #execute
   */
  public void execute() { invoke(execute, null, null); }

  //endregion Action "execute"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BControlPoint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// IStatusValue
////////////////////////////////////////////////////////////////

  /**
   * Return <code>getOutStatusValue()</code>
   */
  @Override
  public final BStatusValue getStatusValue()
  {
    return getOutStatusValue();
  }

  /**
   * Return <code>getFacets()</code>
   */
  @Override
  public final BFacets getStatusValueFacets()
  {
    return getFacets();
  }

  /**
   * Return <code>getOutStatusValue().getStatus()</code>
   */
  @Override
  public final BStatus getStatus()
  {
    return getOutStatusValue().getStatus();
  }

  /**
   * Return <code>getStatusValue().getValueValue().toString(new BasicContext(cx, getFacets()))</code>
   */
  public final String getValueWithFacets(Context cx)
  {
    return getStatusValue().getValueValue().toString(new BasicContext(cx, getFacets()));
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the output BStatusValue.
   */
  public abstract BStatusValue getOutStatusValue();

  /**
   * Get the output property.
   */
  public final Property getOutProperty()
  {
    return getOutStatusValue().getPropertyInParent();
  }

  /**
   * Apply the "facets" property to the "out" property.
   */
  @Override
  public BFacets getSlotFacets(Slot slot)
  {
    if (slot == getOutProperty()) return getFacets();
    return super.getSlotFacets(slot);
  }

  /**
   * Is this a writable point, containing an inputs?
   * Writable subclasses must override this method.
   */
  public boolean isWritablePoint()
  {
    return false;
  }

////////////////////////////////////////////////////////////////
// Execution
////////////////////////////////////////////////////////////////

  /**
   * Do execute is the action which calculates output
   * values based on changes to the inputs.  This method
   * routes to onExecute() and executeExtensions();
   */
  public final void doExecute()
  {
    // start time
    long t1 = Clock.nanoTicks();

    // initialize the working variable
    if (working == null)
      working = (BStatusValue)getOutProperty().getDefaultValue();
    else
      working.copyFrom((BStatusValue)((NProperty)getOutProperty()).value);

    // execute the point
    onExecute(working, null);

    // execute the extensions
    executeExtensions(working, null);

    // update out
    BStatusValue out = getOutStatusValue();

    if (!out.equivalent(working))
    {
      out.copyFrom(working, setOutContext);
    }

    // end time, update total
    long t2 = Clock.nanoTicks();
    totalExecuteTime += (t2-t1);
    totalExecuteCount++;
  }

  /**
   * Control points should override this method to perform
   * whatever logic / operations necessary to update the output
   * based on inputs.  Never modify the out property directly,
   * rather update the working variable parameter.
   */
  public abstract void onExecute(BStatusValue out, Context cx);

////////////////////////////////////////////////////////////////
// Extensions
////////////////////////////////////////////////////////////////

  /**
   * Get the list of child extensions.
   */
  public final BPointExtension[] getExtensions()
  {
    BPointExtension[] temp = new BPointExtension[getSlotCount()];
    int count = 0;

    SlotCursor<Property> c = getProperties();
    while(c.nextComponent())
    {
      BObject kid = c.get();
      if (kid instanceof BPointExtension)
        temp[count++] = (BPointExtension)kid;
    }

    BPointExtension[] result = new BPointExtension[count];
    System.arraycopy(temp, 0, result, 0, count);
    return result;
  }

  /**
   * Return if any of the installed extensions require
   * a permanent subscription.  This is calculated by
   * walking through all the child BPointExtensions and
   * returning true if any one of them returns true for
   * the <code>requiresPointSubscription()</code> method.
   */
  public boolean getExtensionsRequireSubscription()
  {
    SlotCursor<Property> c = getProperties();
    while(c.nextComponent())
    {
      BObject kid = c.get();
      if (kid instanceof BPointExtension)
        if (((BPointExtension)kid).requiresPointSubscription())
          return true;
    }
    return false;
  }

  /**
   * Set the permanent subscription flag from the result of
   * <code>getExtensionsRequireSubscription()</code>
   */
  public void checkExtensionsRequireSubscription()
  {
    if (!isRunning()) return;
    setPermanentlySubscribed( getExtensionsRequireSubscription() );
  }

  /**
   * The method calls onExecute() on every extension
   * in the order they are declared.
   */
  public void executeExtensions(BStatusValue out, Context cx)
  {
    SlotCursor<Property> c = getProperties();
    while(c.nextComponent())
    {
      BObject child = c.get();
      if (child instanceof BPointExtension)
      {
        BPointExtension ext = (BPointExtension)child;
        try
        {
          ext.onExecute(out, cx);
        }
        catch(Throwable e)
        {
          log.log(Level.SEVERE, "Extension failed onExecute(): " + ext.toPathString(), e);
        }
      }
    }
  }

  /**
   * Call pointFacetChanged on all the extensions.
   */
  private void pointFacetsChanged()
  {
    SlotCursor<Property> c = getProperties();
    while(c.nextComponent())
    {
      BObject child = c.get();
      if (child instanceof BPointExtension)
      {
        BPointExtension ext = (BPointExtension)child;
        ext.pointFacetsChanged();
      }
    }
  }

////////////////////////////////////////////////////////////////
// Invariants
////////////////////////////////////////////////////////////////

  /**
   * Calls doCheckParentLink on all extensions to allow
   * extensions to veto parent links
   */
  @Override
  protected LinkCheck doCheckLink(BComponent source, Slot sourceSlot, Slot targetSlot, Context cx)
  {
    SlotCursor<Property> c = getProperties();
    while(c.nextComponent())
    {
      BObject child = c.get();
      if (child instanceof BPointExtension)
      {
        LinkCheck check = ((BPointExtension)child).doCheckParentLink(source, sourceSlot, targetSlot, cx);

        if (!check.isValid())
          return check;
      }
    }

    return LinkCheck.makeValid();
  }


  /**
   * Is the specified child a legal child component for this
   * component.  BControlPoint allows BPointExtensions to restrict
   * creation of siblings via isSiblingLegal.
   */
  @Override
  public final boolean isChildLegal(BComponent newChild)
  {
    if (newChild instanceof BPointExtension)
    {
      BSpace space = getSpace();
      if(space == null || !(space instanceof BProxyComponentSpace))
      {
        SlotCursor<Property> c = getProperties();
        while(c.nextComponent())
        {
          BObject child = c.get();
          if (child instanceof BPointExtension)
          {
            if (((BPointExtension)child).isSiblingLegal(newChild))
              continue;
            else
              return false;
          }
        }
      }
      return true;
    }
    else
      return true;
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  
  /**
   * Get the control point as a String.
   */
  @Override
  public String toString(Context context)
  {
    return propertyValueToString(getOutProperty(), context);
  }

  @Override
  public AgentList getAgents(Context cx)
  {
    AgentList list = super.getAgents(cx);
    list.toBottom("webChart:ChartWidget");
    return list;
  }

  ////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  @Override
  public final Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.CHANGED:             fwChanged((Property)a, (Context)b); break;
      case Fw.ADDED:               fwAdded((Property)a, (Context)b); break;
      case Fw.REMOVED:             fwRemoved((Property)a, (BValue)b, (Context)c); break;
      case Fw.PARENTED:            fwParented((Property)a, (BValue)b, (Context)c); break;
      case Fw.UNPARENTED:          fwUnparented((Property)a, (BValue)b, (Context)c); break;
      case Fw.STARTED:             fwStarted(); break;
      case Fw.DESCENDANTS_STARTED: doExecute(); break;
      case Fw.SUBSCRIBED:          fwSubscribed(); break;
      case Fw.UNSUBSCRIBED:        fwUnsubscribed(); break;
      case Fw.TOTAL_EXECUTE_TIME:  return Long.valueOf(totalExecuteTime);
      case Fw.TOTAL_EXECUTE_COUNT:  return Long.valueOf(totalExecuteCount);
      case Fw.RESET_EXECUTE_STATS:  totalExecuteCount = 0; totalExecuteTime = 0; break;
      case Fw.CHECK_ACTION_INVOCATION: fwCheckActionInvocation((Action) a, (BValue) b, (Context) c); break;
    }
    return super.fw(x, a, b, c, d);
  }

  private void fwStarted()
  {
    getProxyExt().checkStatusValueTypes();
    checkExtensionsRequireSubscription();
  }

  private void fwAdded(Property prop, Context context)
  {
    if (!isRunning()) return;
    checkExtensionsRequireSubscription();
    execute();
  }

  private void fwRemoved(Property prop, BValue value, Context context)
  {
    if (!isRunning()) return;
    checkExtensionsRequireSubscription();
    execute();
  }

  private void fwParented(Property prop, BValue newChild, Context context)
  {
    if (!isRunning()) return;
    getProxyExt().checkStatusValueTypes();
  }

  private void fwUnparented(Property prop, BValue oldChild, Context context)
  {
    if (!isRunning()) return;
    getProxyExt().checkStatusValueTypes();
  }

  private void fwChanged(Property prop, Context context)
  {
    if (!isRunning()) return;

    // debug check to make sure nobody is setting out explicitly
    if (prop == getOutProperty())
    {
      if (context != setOutContext)
        log.log(Level.WARNING, "ControlPoint.out set explicitly: " + getType() + ": " + toPathString(), new Exception());
    }

    // control points will execute each time a non-output
    // property changes by invoking the execute asynchronously
    else
    {
      if (prop.equals(facets))
      {
        pointFacetsChanged();
      }
      if (context != noExecuteContext) execute();
    }

    if (writableSupport() != null)
      writableSupport().changed(prop);
  }

  private void fwSubscribed()
  {
    if (!isRunning()) return;
    getProxyExt().pointSubscribed();
  }

  private void fwUnsubscribed()
  {
    if (!isRunning()) return;
    getProxyExt().pointUnsubscribed();
  }

  /**
   * Package private callback when the given Action is invoked with the given argument and
   * Context. By default, this method is a no-op, but it can be optionally overridden by
   * subclasses in this package to validate the Action invocation and throw a RuntimeException
   * (preferably a {@link javax.baja.sys.LocalizableRuntimeException}) if the Action invocation
   * should be rejected for any reason.
   *
   * @since Niagara 4.10u8
   */
  void fwCheckActionInvocation(Action action, BValue arg, Context context)
  {
    // No-op
  }

  WritableSupport writableSupport()
  {
    return null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /** The "control" log. */
  public static final Logger log = Logger.getLogger("control");

  private static final Context setOutContext = new BasicContext();
  static final Context noExecuteContext = new BasicContext();
  
  private BStatusValue working;
  private long totalExecuteTime;
  private long totalExecuteCount;

}
