/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx.px;

import java.util.logging.Level;
import javax.baja.agent.NoSuchAgentException;
import javax.baja.hx.BHxView;
import javax.baja.hx.Command;
import javax.baja.hx.HxOp;
import javax.baja.hx.px.binding.BHxPxBinding;
import javax.baja.naming.OrdTarget;
import javax.baja.sys.BComplex;
import javax.baja.sys.Context;
import javax.baja.ui.BBinding;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;

public class MouseEventCommand 
  extends Command
{
  public MouseEventCommand(BHxView view)
  {
    super(view);
  }

  public String getInvokeCode(int event, HxOp op)
  {
    return getInvokeCode(event, 0, op);
  }

  /**
   * Get the invoke code for the MouseEventEvent. When delay is greater than zero,
   * wait the delay before contacting the station.
   * @param delay - delay in milliseconds
   */
  public String getInvokeCode(int event, long delay, HxOp op)
  {
    String quote = "\"";
    
    StringBuilder buf = new StringBuilder();

    // Save the mouse event parameters and make we won't ignore the event
    buf.append("var e=event;");
    buf.append("if(hx.setMouseEvent(e)){");

    // Upon a click event, close any existing context menu
    if(event == BMouseEvent.MOUSE_RELEASED || event == BMouseEvent.MOUSE_PRESSED)
    {
      buf.append("hx.closeMenu();");
    }

    if(delay > 0)
      buf.append("setTimeout(function(){");

    // Fire this event
    buf.append("hx.fireEvent(");
    buf.append(quote).append(op.getPath()).append(quote);
    buf.append(",");
    buf.append(quote).append(getId()).append(quote);
    buf.append(", e);");

    if(delay > 0)
      buf.append("}, ").append(delay).append(");");

    buf.append("}");

    // Stop propagation, and any native context menu
    buf.append("return hx.stopEventPropagation(e);");
    
    return buf.toString();
  }
  
  public void handle(HxOp op) throws Exception
  {    

    BWidget widget = ((BWidget)op.get());
    
    BBinding[] bindings = widget.getBindings();
    
    // Retrieve an input event from the op
    BInputEvent event = getEvent(op);
    
    // Get the BHxPxWidget for the current BWidget, and
    // call its handle() callback.
    {
      BHxPxWidget hxPxWidget = null;
      try
      {
        hxPxWidget = BHxPxWidget.makeFor(widget, op);
        
        if(!hxPxWidget.isMouseEnabled(op))
          return;
        hxPxWidget.handle(event, op);
      } 
      catch (NoSuchAgentException e) 
      {}
    }
    
    // Get the BHxPxBindings for the current BWidget, and
    // call their handle() callback.
    for(int i=0; i<bindings.length; i++)
    {
      HxOp bindingOp = op.make(new OrdTarget(op, bindings[i]));
      try
      {
        BHxPxBinding hxBinding = BHxPxBinding.makeFor(bindings[i], bindingOp);
        hxBinding.handle(event, bindingOp);
      } catch (NoSuchAgentException nsae) {}
    }
  }

  protected HxOp getRootHxOp(HxOp op){

    // Find the root context
    Context cx = op;
    while(cx.getBase()!=null)
    {
      if(cx instanceof HxOp)
      {
        if(((HxOp)cx).getPath().equals(""))
        {
          op = (HxOp)cx;
          break;
        }
      }
      cx = cx.getBase();
    }
    return op;
  }
  
  /**
   * Translate form fields stored in the provided HxOp into
   * a BInputEvent that represents the action that occurred 
   * on the browser.
   */
  protected BInputEvent getEvent(HxOp op)
  {
    BWidget widget = ((BWidget)op.get());
    
    op = getRootHxOp(op);

    if(op.getFormValue("button")==null)
      return null;

    // Which button?
    int modifiers = 0;
    if("right".equals(op.getFormValue("button")))
      modifiers |= BInputEvent.BUTTON2_DOWN_MASK;
    else
      modifiers |= BInputEvent.BUTTON1_DOWN_MASK;

    // Keyboard modifiers?
    if("true".equals(op.getFormValue("shiftModifier")))
      modifiers |= BInputEvent.SHIFT_DOWN_MASK;
    if("true".equals(op.getFormValue("altModifier")))
      modifiers |= BInputEvent.ALT_DOWN_MASK;
    if("true".equals(op.getFormValue("ctrlModifier")))
      modifiers |= BInputEvent.CTRL_DOWN_MASK;
    if("true".equals(op.getFormValue("metaModifier")))
      modifiers |= BInputEvent.META_DOWN_MASK;

    // Single or double click?
    int clickCount=1;
    if(op.getFormValue("doubleClick")!=null)
      clickCount=2;

    // Event id
    String eventIdStr = op.getFormValue("id");
    int eventId = BMouseEvent.MOUSE_RELEASED;
    switch (eventIdStr)
    {
      case "mouseenter":
        eventId = BMouseEvent.MOUSE_ENTERED;
        break;
      case "mouseleave":
        eventId = BMouseEvent.MOUSE_EXITED;
        break;
      case "touchstart":
      case "mousedown":
        eventId = BMouseEvent.MOUSE_PRESSED;
        break;

      case "touchcancel":
      case "touchend":
      case "mouseup":
        eventId = BMouseEvent.MOUSE_RELEASED;
        break;
    }

    // Coordinates (TODO these might not be right, are they
    // widget or screen relative - I don't know).
    double x = Double.parseDouble(op.getFormValue("x"));
    double y = Double.parseDouble(op.getFormValue("y"));
    
    // Return event
    BMouseEvent event = new BMouseEvent(eventId, widget, modifiers, x, y, clickCount, false);
    if(BHxPxView.log.isLoggable(Level.FINER))
      BHxPxView.log.log(Level.FINER, String.valueOf(event));
    return event;
  }
  
  /**
   * In workbench, Mouse is not only disabled if widget is disabled, but is also disabled if 
   * the any ancestor is disabled
   * 
   * @since Niagara 3.8
   */   
  public static boolean isMouseEnabled(BWidget widget)
  {
    if(!widget.getEnabled())
      return false;
    if(widget == null)
      return true;
    
    BComplex c = widget.getParent();
    if(c instanceof BWidget)
      return isMouseEnabled((BWidget) c);
    else
      return true;
  }
}
