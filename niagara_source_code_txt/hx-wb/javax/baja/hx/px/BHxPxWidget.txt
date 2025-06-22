/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx.px;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.agent.NoSuchAgentException;
import javax.baja.gx.IRectGeom;
import javax.baja.gx.Point;
import javax.baja.gx.RectGeom;
import javax.baja.hx.BHxFieldEditor;
import javax.baja.hx.BHxProfile;
import javax.baja.hx.BHxView;
import javax.baja.hx.HxOp;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BInputEvent;
import javax.baja.web.BIWebProfile;
import javax.baja.web.IWebEnv;
import javax.baja.web.WebOp;

import com.tridium.hx.BHTML5HxProfile;
import com.tridium.hx.HxHyperlinkInfo;
import com.tridium.hx.util.HxUtils;
import com.tridium.sys.registry.NAgentList;
import com.tridium.web.WebUtil;

/**
 * BHxPxWidget is responsible for creating an hx representation of a BWidget
 * used in a Px document. Note that BHxPxWidget works differently from a typical
 * HxView in that op.get() will return the BWidget that this agent is supposed
 * to model. The widget will already be mounted in a BComponentSpace and any
 * bindings will already be active and leased. Also note that in
 * module-include.xml this type should be registered as agent on the BWidget it
 * is supposed to model.
 * 
 * @author Andy Frank
 * @creation 6 Jan 05
 * @version $Revision: 18$ $Date: 12/16/09 1:28:55 PM EST$
 * @since Baja 1.0
 */
@NiagaraType
public abstract class BHxPxWidget
  extends BHxView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hx.px.BHxPxWidget(2979906276)1.0$ @*/
/* Generated Fri Nov 19 13:59:13 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxWidget.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




  protected BHxPxWidget()
  {    
  }

  ////////////////////////////////////////////////////////////////
  // Factory
  ////////////////////////////////////////////////////////////////

  /**
   * Return the HxPxWidget registered on the given object, or
   * throws a NoSuchAgentException if no agent can be found.
   * Since no context is passed in, the widget that is created will
   * not be appName specific.
   */
  public static BHxPxWidget makeFor(BObject obj)
  {
    return makeFor(obj, null);    
  }
  
  /**
   * Return the HxPxWidget registered on the given object, or
   * throws a NoSuchAgentException if no agent can be found. If the context 
   * can get to valid HxProfile, app specific widgets will be retrieved.
   * Deprecated BHxPxWidgets will be lowered in preference unless a BFacets
   * containing "deprecationFilter=false" is provided.
   * In Niagara 4.9 or higher, Bajaux widgets registered on bajaui BWidget will be
   * accepted as valid agents.
   *
   * @since Niagara 3.4
   */
  public static BHxPxWidget makeFor(BObject obj, Context cx)
  {
    try
    {
      if(!DISABLED_BAJAUX_BAJAUI_WIDGETS)
      {
        HxOp hxOp = cx instanceof HxOp ? (HxOp) cx : null;
        BHxProfile profile = BHTML5HxProfile.INSTANCE;
        if(hxOp != null)
        {
          profile = hxOp.getProfile();
        }
        BHxPxWidget uxWidget = ux((BWidget) obj, profile, cx);
        if(uxWidget != null)
        {
          return uxWidget;
        }
      }

      //fallback to old way
      AgentList agentList = obj.getAgents().filter(BHxFieldEditor.getAgentFilter(agentFilter, cx));


      if (cx == null || cx.getFacets() == null || cx.getFacets().getb(HxUtils.DEPRECATION_KEY, true))
      {
        agentList.toBottom(new HxHyperlinkInfo.DeprecatedFilter(cx));
      }

      return (BHxPxWidget) agentList.getDefault().getInstance();

    }
    catch (NoSuchAgentException e)
    {
      throw new NoSuchAgentException("No HxPxWidget for " + obj.getType());
    }
  }


  /**
   * Find any Bajaux Widgets associated with the given widget. If no bajaux widget can be found, return null.
   * @since Niagara 4.9
   */
  public static BHxPxWidget ux(BWidget widget, BIWebProfile profile, Context cx)
  {
    try
    {
      AgentList agentList = getNonWidgetAgents(widget).filter(AgentFilter.and(uxAgentFilter, new WebUtil.ProfileFilter(profile, widget)));

      if (cx == null || cx.getFacets() == null || cx.getFacets().getb(HxUtils.DEPRECATION_KEY, true))
      {
        agentList.toBottom(new HxHyperlinkInfo.DeprecatedFilter(cx));
      }

      AgentInfo agentInfo = agentList.getDefault();
      IWebEnv env = BHxProfile.webEnv();
      WebOp webOp = cx instanceof WebOp ? (WebOp) cx : null;
      agentInfo = env.translate(webOp, agentInfo);
      if (agentInfo.getAgentType().is(BHxPxWidget.TYPE))
      {
        return (BHxPxWidget) agentInfo.getInstance();
      }
    } catch (NoSuchAgentException ignore)  {}


    //fallback to original behavior to find the normal BHxPxWidget
    return null;
  }

  /**
   * Get most Agents for a specific BWidget; stop collecting agents before you get to BWidget to avoid
   * grabbing Widgets that are registered on all BComponents (like Multisheet). Note that this function does not provide
   * agents for any interfaces as its only designed for subclasses of the BWidget that you are registered on.
   * @since Niagara 4.9
   */
  private static AgentList getNonWidgetAgents(BWidget view)
  {
    AgentList list = new NAgentList();
    TypeInfo typeInfo = view.getType().getTypeInfo();
    while(!typeInfo.equals(BWidget.TYPE.getTypeInfo())){
      Arrays.stream(Sys.getRegistry().getSpecificAgents(typeInfo).list())
        .forEach(e -> {
          list.add(list.size(), e);
        });
      typeInfo = typeInfo.getSuperType();
    }
    return list;
  }

  public void write(HxOp op)
    throws Exception
  { }

  /**
   * @deprecated
   */
  @Deprecated
  public void update(HxOp op)
    throws Exception
  { }

  /**
   * Update the widget.  Implementations should write JavaScript content to the
   * op.getHtmlWriter() string writer.  Widgets may decide to skip updating to
   * save bandwidth or processing time, unless forceUpdate is true.
   * 
   * @since Niagara 3.5
   */
  public void update(int width, int height, boolean forceUpdate, HxOp op)
    throws Exception
  {
    update(op);
  }

  /**
   * Decides if the HxPxWidget should be updated, including a call to the
   * update method.
   * 
   * @param lastUpdate When this widget was last assumed up to date
   * @param lastModified When the underlying BWidget was last modified
   * @return true if an update is required
   * 
   * @since Niagara 3.5   
  */
  public boolean needsUpdate(BAbsTime lastUpdate, BAbsTime lastModified)
  {
    return lastModified.isAfter(lastUpdate) || lastModified.equals(lastUpdate);
  }

  /**
   * Decides if the HxPxWidget will get overflow set to 'auto' or 'hidden'
   */
  public boolean needsOverflowAuto(HxOp op)
  {
    return false;
  }

  /**
   * Decides if the HxPxWidget will get overflow set to 'visible' or 'hidden'.
   * If both `needsOverflowVisible` and `needsOverflowAuto` are true. Visible will take priority.
   * @since Niagara 4.10
   */
  public boolean needsOverflowVisible(HxOp op)
  {
    return false;
  }
  
  /**
   * Handle an input event that was triggered on the widget. Implementations
   * should write JavaScript content to the op.getHtmlWriter() string writer.
   * 
   * @since Niagara 3.5
   */
  public void handle(BInputEvent event, HxOp op)
    throws IOException
  { }


  /**
   * You can no longer override this method, please use and override
   * getChildWidget(Widget, Context) instead.
   * This allow the method to be used by PxValidation without a valid HxOp.
   * @since Niagara 3.5
   */
  @Deprecated
  public final BWidget[] getChildWidgets(HxOp op)
  {
    BWidget widget = (BWidget) op.get();
    return widget.getChildWidgets();
  }

  /**
   * Calls to getChildWidgets() return children that this HxPxWidget will not be
   * drawing itself. These children will be drawn by the HxPx framework. Return
   * an empty array to stop children from being drawn automatically.  The
   * default implementation returns all of the widget's children.
   * 
   * @param widget  The Widget represented by the HxPxWidget
   * @param cx If available, the HxOp, otherwise a regular Context can be used
   * @since Niagara 4.2
   */

  public BWidget[] getChildWidgets(BWidget widget, Context cx)
  {
    return widget.getChildWidgets();
  }
    
  /**
   * Get the widget's location and size
   * 
   * @since Niagara 3.5
   */
  public IRectGeom getGeom(BWidget widget, HxOp op)
  {
    Point tl = new Point(0, 0); // top-left corner
    Point br = new Point(widget.getWidth(), widget.getHeight()); // bottom-right corner
    
    if(widget.getParent()!=null)
    {
      // translate these coordinates to the parent's view window
      tl = ((BWidget)widget.getParent()).translateFromChild(widget, tl);
      br = ((BWidget)widget.getParent()).translateFromChild(widget, br);
    } else {
      // this is the root object
      tl = new Point(widget.getX(), widget.getY());
      br = new Point(widget.getWidth()+widget.getX(), widget.getHeight()+widget.getY());
    }

    // Calculate coordinates
    double width = br.x-tl.x;
    double height = br.y-tl.y;

    double top = tl.y();
    double left = tl.x();
   
    return new RectGeom(left, top, width, height);
  }  
  
  /**
   * Returns true if the the Widget is currently allowing mouse events to propogate.
   * Now like in workbench, if any parent of the widget is disabled, then mouse events will not
   * be allowed.
   * @since Niagara 3.8
   */
  public boolean isMouseEnabled(HxOp op)
  {
    BWidget widget = (BWidget) op.get();
    return MouseEventCommand.isMouseEnabled(widget);      
  }
  
  /**
   * Returns the default mouse event handler.  Return null to allow
   * the browser to handle mouse events.
   * @since Niagara 3.5
   */
  public MouseEventCommand getMouseEventHandler()
  {
    return mouseEventCommand;    
  }

  /**
   * Return a non-null Localized String if something in the widget's configuration is not configured correctly
   * to work with the target media. To show multiple warnings separate them with a newline character for best display
   * results.
   * @since Niagara 4.2
   */
  public String validateWidget(BWidget widget, Context cx)
  {
    return null;
  }

  ////////////////////////////////////////////////////////////////
  // Attributes
  ////////////////////////////////////////////////////////////////

  public static final Logger log = Logger.getLogger("hx.px");
  private static final TypeInfo uxFormFactor = Sys.getRegistry().getType("web:IFormFactor");
  private static final AgentFilter agentFilter = AgentFilter.is(TYPE);
  private static final AgentFilter uxAgentFilter = AgentFilter.or(agentFilter, AgentFilter.is(uxFormFactor));



  /**
   * This command handles mouse events that occure on the widget.  It
   * routes input events to the widget and its binding.
   * 
   * @since Niagara 3.5
   */
  public final MouseEventCommand mouseEventCommand = new MouseEventCommand(this);

  private static boolean DISABLED_BAJAUX_BAJAUI_WIDGETS =
    AccessController.doPrivileged((PrivilegedAction<Boolean>)
      () -> Boolean.getBoolean("hx.disableBajauxBajauiWidgets"));
}
