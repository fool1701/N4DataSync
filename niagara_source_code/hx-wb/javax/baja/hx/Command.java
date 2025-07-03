/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import javax.baja.io.HtmlWriter;
import javax.baja.naming.BOrd;

/**
 * Command is an Event that is invoked by the user, typically
 * from a browser control such as a button. 
 *
 * @author    Andy Frank
 * @creation  29 Jun 05
 * @version   $Revision: 2$ $Date: 12/18/08 11:03:30 AM EST$
 * @since     Baja 1.0
 */
public abstract class Command
  extends Event
{ 
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Create a new Event with this id.
   */
  public Command(BHxView view)
  {
    super(view);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Return the display name for this event. 
   * Returns id by default.
   */
  public String getDisplayName(HxOp op)
  {
    return getId();
  }
  
  /**
   * Convenience method to invoke a page refresh in 
   * the client browser.
   */
  public void refresh(HxOp op)
    throws Exception
  {
    HtmlWriter out = op.getHtmlWriter();
    out.w("window.location.reload(true);");  
  }

  /**
   * Convenience method to invoke a page redirect in 
   * the client browser.
   */
  public void redirect(HxOp op, String location)
    throws Exception
  {
    HtmlWriter out = op.getHtmlWriter();
    out.w("hx.hyperlink('").w(HxUtil.escapeJsStringLiteral(location)).w("');");
  }
  
  /**
   * Convienence for <code>redirect(op, op.toUri(ord))</code>.
   */
  public void redirect(HxOp op, BOrd ord)
    throws Exception
  {
    redirect(op, op.toUri(ord));
  }
}



