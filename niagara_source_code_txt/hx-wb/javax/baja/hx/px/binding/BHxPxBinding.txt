/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx.px.binding;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.BIAgent;
import javax.baja.agent.NoSuchAgentException;
import javax.baja.hx.BHxFieldEditor;
import javax.baja.hx.Event;
import javax.baja.hx.HxConst;
import javax.baja.hx.HxOp;
import javax.baja.hx.PropertiesCollection;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.BSingleton;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.enums.BDegradeBehavior;
import javax.baja.ui.event.BInputEvent;

/**
 * BHxPxBinding is responsible for mapping events on a BBinding to a
 * BHxPxWidget. Also note that in module-include.xml this type should be
 * registered as agent on the BBinding it is supposed to model.
 * 
 * @author Lee Adcock
 * @creation 29 Sept 09
 * @version $Revision: 7$ $Date: 6/22/10 1:15:36 PM EDT$
 * @since Niagara 3.5
 */
@NiagaraType
public abstract class BHxPxBinding
  extends BSingleton
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hx.px.binding.BHxPxBinding(2979906276)1.0$ @*/
/* Generated Fri Nov 19 13:59:13 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxBinding()
  {
  }

  ////////////////////////////////////////////////////////////////
  // Factory
  ////////////////////////////////////////////////////////////////

  /**
   * Return the HxPxBinding registered on the given object, or throws a
   * NoSuchAgentException if no agent can be found. Since no context is passed
   * in, the widget that is created will not be appName specific.
   */
  public static BHxPxBinding makeFor(BBinding obj)
  {
    return makeFor(obj, null);    
  }
  
  /**
   * Return the HxPxBinding registered on the given object, or throws a
   * NoSuchAgentException if no agent can be found. If the context can get to
   * valid HxProfile, app specific widgets will be retrieved.
   */  
  public static BHxPxBinding makeFor(BBinding obj, Context cx)
  {
    try
    {
      return (BHxPxBinding)obj.getAgents().filter(BHxFieldEditor.getAgentFilter(agentFilter, cx)).getDefault().getInstance();
    }
    catch (NoSuchAgentException e)
    {
      throw new NoSuchAgentException("No HxPxBinding for " + obj.getType());
    }
  }

  /**
   * Write any initialization code required by the binding. Calls to op.get() will return the
   * BBinding that HxPxBinding models.
   */
  public void write(HxOp op)
    throws Exception
  { }
  
  /**
   * Update the widget this binding is on. Calls to op.get() will return the
   * BBinding that HxPxBinding models.
   * The default implementation will help ensure that degrade behavior is observed.
   */
  public void update(int width, int height, boolean forceUpdate, HxOp op)
    throws Exception
  {
    BBinding binding = (BBinding)op.get();
    HxOp baseOp = ((HxOp)op.getBase().getBase());
    if (!binding.getWidget().getVisible() ||
      ((binding.getDegradeBehavior() == BDegradeBehavior.hide && (!binding.getOrd().isNull() && !binding.isBound()))))
    {
      PropertiesCollection style = new PropertiesCollection.Styles();
      style.add("visibility", "hidden");
      style.write(baseOp);
    }
    else
    {
      PropertiesCollection style = new PropertiesCollection.Styles();
      style.add("visibility", "inherit");
      style.write(baseOp);
    }

    if (!binding.getWidget().getEnabled() ||
      ((binding.getDegradeBehavior() == BDegradeBehavior.disable && (!binding.getOrd().isNull() && !binding.isBound()))))
    {
      binding.getWidget().setEnabled(false);
    }
    else
    {
      binding.getWidget().setEnabled(true);
    }
  }

  /**
   * Handle an input event that occurred on the widget this binding is on. Calls
   * to op.get() will return the BBinding that HxPxBinding models.
   */
  public void handle(BInputEvent event, HxOp op)
    throws Exception
  { }

  /**
   * Process post request. Return true if this binding handled the message - if
   * not return false. The default implemenation routes event requests to the
   * registered event handler if one can be found, otherwise return false.
   */
  public boolean process(HxOp op)
    throws Exception
  {
    // Check if this is an event request.
    String contentType = op.getRequest().getContentType();
    if (contentType.startsWith(HxConst.EVENT))
    {
      // Paths must match
      String path = op.getRequest().getHeader(HxConst.EVENT_PATH);

      if (path.equals(op.getPath()))
      {
        
        // Get event id from header
        String eventId = op.getRequest().getHeader(HxConst.EVENT_ID);
        Event event = null;

        // If not a root path, or if no profile
        // event found, check for a view event
        if (event == null) event = events.get(eventId);

        // If an event was found, invoke its handler
        if (event != null)
        {
          // Invoke handler
          op.decodeFormValues();
          event.handle(op);

          // Flush content buffer
          byte[] bytes = op.getContent().toString().getBytes(UTF_8);
          op.getResponse().setContentLength(bytes.length);
          op.getResponse().getOutputStream().write(bytes, 0, bytes.length);
          return true;
        }
      }
    }

    // Did not handle this request.
    return false;
  }

  /**
   * Save the value of the binding
   */
  public void save(BObject value, HxOp op)
    throws Exception
  {
    
  }

  /**
   * Return a non-null Localized String if something in the bindings's configuration is not configured correctly
   * to work with the target media. To show multiple warnings separate them with a newline character for best display
   * results.
   * @since Niagara 4.3
   */
  public String validateBinding(BBinding binding, Context cx)
  {
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Events
////////////////////////////////////////////////////////////////

  /**
   * Register this event. Events are registered by the event id - which is used
   * to route requests from the browser to the correct event handler.
   */
  public void registerEvent(Event event)
  {
    registerEvent(event, "event" + eventCounter++);
  }

  private void registerEvent(Event event, String id)
  {
    event.setId(id);
    events.put(event.getId(), event);
  }

// //////////////////////////////////////////////////////////////
// Attributes
// //////////////////////////////////////////////////////////////

  private int eventCounter = 0;
  protected HashMap<String, Event> events = new HashMap<>();

  private static final String UTF_8 = StandardCharsets.UTF_8.name();
  private static AgentFilter agentFilter = AgentFilter.is(TYPE);
}
