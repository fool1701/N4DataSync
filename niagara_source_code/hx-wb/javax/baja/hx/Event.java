/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

/**
 * Event encapsulates a event that is fired from the browser
 * and handled on the station.
 *
 * @author    Andy Frank
 * @creation  27 Jun 05
 * @version   $Revision: 2$ $Date: 9/29/09 9:51:01 AM EDT$
 * @since     Baja 1.0
 */
public abstract class Event
{ 
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Create a new Event with this id.
   */
  public Event(BHxView view)
  {
    this.view = view;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the view for this event.
   */
  public BHxView getView()
  {
    return view;
  }

  /**
   * Get the event id. Ids must be unique with the same HxView.
   */
  public final String getId()
  {
    return id;
  }
  
  /**
   * Get the JavaScript used to invoke this event from the browser.
   * Default implemenation invokes this event with the current
   * form values.
   */
  public String getInvokeCode(HxOp op)
  {
    // Escape quotes when using XmlHttp
    String method = op.getRequest().getMethod().toLowerCase();
    String quote = (method.equals("get")) ? "\"" : "&quot;";
    
    StringBuilder buf = new StringBuilder();
    buf.append("hx.fireEvent(");
    buf.append(quote).append(op.getPath()).append(quote);
    buf.append(",");
    buf.append(quote).append(getId()).append(quote);
    buf.append(");");
    return buf.toString();
  }  
  
  /**
   * Handle this event.  This method is called on the station in
   * response to an event being fired from the browser.
   */
  public void handle(HxOp op)
    throws Exception
  {
  }

  /**
   * Set the id for this command. An id is assigned when
   * the event has been registered on a HxView.
   */
  public void setId(String id)
  {
    this.id = id;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private BHxView view = null;
  private String id = null;
}



