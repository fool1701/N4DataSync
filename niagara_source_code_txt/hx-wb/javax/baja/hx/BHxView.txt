/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.session.INiagaraSuperSession;
import javax.baja.session.SessionUtil;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.BServletView;
import javax.baja.web.WebOp;
import javax.baja.web.hx.BIHxProfile;

import org.eclipse.jetty.io.EofException;
import org.owasp.encoder.Encode;

import com.tridium.hx.HxHyperlinkInfo;
import com.tridium.web.WebProcessException;
import com.tridium.web.WebUtil;

/**
 * BHxView is a component HTML view used to view and edit
 * BObjects in a browser.
 *
 * @author Andy Frank
 * @version $Revision: 43$ $Date: 10/6/10 5:10:43 PM EDT$
 * @creation 4 Jan 05
 * @since Baja 1.0
 */
@NiagaraType
public abstract class BHxView
  extends BServletView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hx.BHxView(2979906276)1.0$ @*/
/* Generated Fri Nov 19 13:59:13 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxView.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




  /**
   * Subclasses must call super() in their constructor if they
   * wish to use menu and dialog based action invocation
   */
  public BHxView()
  {
  }

////////////////////////////////////////////////////////////////
// ServletView
////////////////////////////////////////////////////////////////

  /**
   * GET is used to return the HTML that renders this view.
   * Sub-classes should not override this method, instead
   * using the write,save,update,process methods.
   */
  public void doGet(WebOp c)
    throws Exception
  {
    BHxProfile profile = (BHxProfile)c.getProfileConfig(BIHxProfile.PREFER_HX_FACETS).make();
    HxOp op = profile.createOp(c);
    op.setProfile(profile);

    try
    {
      op.getResponse().setHeader("transfer-encoding", "chunked");
      op.getResponse().setHeader("Cache-Control", "no-cache, no-store");

      BHxView view = HxHyperlinkInfo.getView(this, op);
      profile.writeDocument(view, op);
    }
    catch (WebProcessException wpe)
    {
      log.log(Level.SEVERE, "Unable to process request.", wpe);
      WebUtil.sendSafeErrorToUser(c.getResponse(), wpe.code, wpe, op);
    }
    catch (Exception err)
    {
      // GET failed, so we can't use the HxProfile.setError
      // hook, so use proper HTTP error response.
      log.log(Level.SEVERE, "Unable to process request.", err);
      op.sendError(err);
    }
    finally
    {
      profile.destroyOp(op);
    }
  }

  /**
   * POST is used for communicating between station and
   * client. Sub-classes should not override this method,
   * instead using the write,save,update,process methods.
   */
  public void doPost(WebOp c)
    throws Exception
  {
    // URL back to self
    String self = WebUtil.getRedirect(c.getRequest(), c.getRequest().getRequestURI());
    String query = c.getRequest().getQueryString();
    if (query != null) self += "?" + query;

    BHxProfile profile = (BHxProfile)c.getProfileConfig(BIHxProfile.PREFER_HX_FACETS).make();
    HxOp op = profile.createOp(c);

    op.setProfile(profile);
    boolean processException = false;

    try
    {
      INiagaraSuperSession session = SessionUtil.getCurrentNiagaraSuperSession();
      //for form posts, token should be available as a form field
      String csrfToken = op.getFormValue("csrfToken");
      if(csrfToken == null)
      {
         //for custom posts, a custom header for "x-niagara-csrfToken" should be provided
        String header = op.getRequest().getHeader(HxConst.CSRF_TOKEN);
        if(header != null)
          csrfToken=HxUtil.decode(header);
      }
      session.verifyCsrfToken(csrfToken);

      BHxView view = HxHyperlinkInfo.getView(this, op);

      // Save
      if (op.isFormPost())
      {
        profile.saveDocument(view, op);
        String url = op.getRedirect();
        if (url == null) url = self;
        op.getResponse().sendRedirect(url);
      }

      // Update
      else if (op.isUpdate())
      {
        PrintWriter out = c.getResponse().getWriter();
        profile.updateDocument(view, op);
        String content = op.getContent().toString();
        op.getResponse().setContentType("text/javascript");
        op.getResponse().setContentLength(content.getBytes(UTF_8).length);
        out.print(content);
      }
      // Process/Error
      else
      {
        boolean processResult = false;

        // Process
        try
        {
          processResult = profile.processDocument(view, op);
        }
        catch (Exception e)
        {
          if (e instanceof EofException && e.getCause() instanceof IOException && ("Broken pipe".equals(e.getCause().getMessage()) ||
            "An existing connection was forcibly closed by the remote host".equals(e.getCause().getMessage())))
          {
            // This can happen is the client disconnects before the write finishes.
            if (log.isLoggable(Level.FINE))
              log.log(Level.WARNING, "Unable to process request. Client disconnected.", e);
            else
              log.log(Level.WARNING, "Unable to process request. Client disconnected.");
            // Set this to processed so we don't do error handling below
            processResult = true;
          }
          else
          {
            processException = true;
            // Added some checks to make sure we can still write the response, otherwise
            // the real exception may get suppressed
            if (!op.getResponse().isCommitted())
            {
              op.getResponse().reset();
              PrintWriter out = op.getResponse().getWriter();
              out.print("window.location.reload(true);");
            }
            throw e;
          }
        }

        // Error
        if (!processResult)
        {
          StringBuilder error = new StringBuilder();
          error.append(op.getRequest().getHeader(HxConst.EVENT_PATH));
          error.append(".").append(op.getRequest().getHeader(HxConst.EVENT_ID));
          WebUtil.sendSafeErrorToUser(op.getResponse(), HttpServletResponse.SC_NOT_FOUND, error.toString());
        }
      }
    }
    catch (Exception err)
    {
      profile.setError(err, op);
      log.log(Level.SEVERE, "Unable to process request.", err);

      // If we came across an exception, force a redirect
      // back to self to make sure error is displayed.
      // Only do this for post requests, process exceptions
      // are handled by sending a page refresh script back
      // to the browser.
      if (!processException)
        op.getResponse().sendRedirect(self);
    }
    finally
    {
      op.deleteTempFiles();
      profile.destroyOp(op);
    }
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the preferred page title. Generally, the top level
   * view gets precendence for the page title, though the
   * HxProfile gets the final say.
   */
  public String getPageTitle(HxOp op)
    throws Exception
  {
    if (op.get() instanceof BINavNode)
      return Encode.forHtml(((BINavNode)op.get()).getNavDisplayName(op));
    return "";
  }

  /**
   * Write out the view to the content stream.
   */
  public void write(HxOp op)
    throws Exception
  {
  }

  /**
   * Update the view. The content stream is expected to
   * be executable JavaScript.
   */
  public void update(HxOp op)
    throws Exception
  {
  }

  /**
   * Process a non-update post request.  Return true if
   * this view or a child view handled the message - if
   * not return false. The default implementation routes
   * event requests to the registered event handler if
   * one can be found, otherwise return false.
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

      if (path.equals("*")) path = ""; // see fireEvent() in hx.js

      if (path.equals(op.getPath()))
      {
        // Get event id from header
        String eventId = op.getRequest().getHeader(HxConst.EVENT_ID);
        Event event = null;

        // If path is empty, give profile events precedence
        if (path.length() == 0)
          event = op.getProfile().events.get(eventId);

        // If not a rooth path, or if no profile
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
   * Save the view.  BSimples should return a new instance.
   * BComponents should modify the existing instance and
   * return themselves.
   */
  public BObject save(HxOp op)
    throws Exception
  {
    return op.get();
  }

////////////////////////////////////////////////////////////////
// Events
////////////////////////////////////////////////////////////////

  /**
   * Register this event.  Events are registered by
   * the event id - which is used to route requests
   * from the browser to the correct event handler.
   */
  public void registerEvent(Event event)
  {
    registerEvent(event, "event" + eventCounter++);
  }

  public boolean isEventRegistered(Event event)
  {
    return events.containsValue(event);
  }

  public void registerEvent(Event event, String id)
  {
    event.setId(id);
    events.put(event.getId(), event);
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private int eventCounter = 0;
  protected HashMap<String, Event> events = new HashMap<>();
  private static final String UTF_8 = StandardCharsets.UTF_8.name();
  public static final Logger log = Logger.getLogger("hx");

}
