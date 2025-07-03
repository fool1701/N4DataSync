/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.baja.agent.BIAgent;
import javax.baja.hx.HxOp;
import javax.baja.hx.HxUtil;
import javax.baja.hx.PropertiesCollection;
import javax.baja.hx.px.MouseEventCommand;
import javax.baja.hx.px.binding.BHxPxBinding;
import javax.baja.io.HtmlWriter;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.ViewQuery;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;

import com.tridium.kitpx.BPopupBinding;
import com.tridium.net.BHttpScheme;
import com.tridium.net.BHttpsScheme;
import com.tridium.util.FormatUtil;
import com.tridium.web.WebUtil;

/**
 * @author Lee Adcock
 * @creation Sept 10
 * @version $Revision$ $Date: 9/29/2009 9:51:02 AM$
 * @since Niagara 3.7
 */  
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:PopupBinding"
  )
)
@NiagaraSingleton
public class BHxPxPopupBinding
  extends BHxPxBinding
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxPxPopupBinding(1281329207)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxPopupBinding INSTANCE = new BHxPxPopupBinding();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxPopupBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxPxPopupBinding() {}
  
  public void update(int width, int height, boolean forceUpdate, HxOp op)
    throws Exception
  {
    super.update(width, height, forceUpdate, op);
    BPopupBinding popupBinding = (BPopupBinding)op.get();

    // Set the cursor
    if(!popupBinding.getOrd().isNull() && MouseEventCommand.isMouseEnabled(popupBinding.getWidget()))
    {
      HxOp baseOp = ((HxOp)op.getBase().getBase());
      PropertiesCollection style= new PropertiesCollection.Styles();
      style.add("cursor","pointer");
      style.write(baseOp);
      
      PropertiesCollection events = new PropertiesCollection.Events();

      events.add("onmouseover","window.status=\""+HxUtil.escapeJsStringLiteral(popupBinding.getOrd().toString())+"\"; return true;");
      events.add("onmouseout","window.status=\"\"; return true;");
      events.write(baseOp);
    }
  }    

  public void handle(BInputEvent event, HxOp op)
      throws Exception
  {

    BPopupBinding popupBinding = (BPopupBinding)op.get();
    if (event instanceof BMouseEvent && event.getId() == BMouseEvent.MOUSE_PRESSED && !popupBinding.getOrd().isNull())
    {
      BMouseEvent mouseEvent = (BMouseEvent)event;
      if(mouseEvent.isButton1Down()) {

        boolean externalLink = isExternalLink(popupBinding.getOrd());

        String url = WebUtil.toUri(op, op.getRequest(), BOrd.make(op.getOrd(), popupBinding.getOrd()).normalize());
        HtmlWriter out = op.getHtmlWriter();
        out.w("hx.popup('" + HxUtil.escapeJsStringLiteral(url) + "'");
        if(!ignorePopupPositioning)
        {
          out.w(",").w(popupBinding.getPosition().x());
          out.w(",").w(popupBinding.getPosition().y());
          out.w(",").w(popupBinding.getSize().width());
          out.w(",").w(popupBinding.getSize().height());
        }
        else
        {
          out.w(",undefined");
          out.w(",undefined");
          out.w(",undefined");
          out.w(",undefined");
        }

        out.w(",").w(externalLink);
        out.w(",").w(popupBinding.getModal());
        out.w(",'").w(HxUtil.escapeJsStringLiteral(FormatUtil.formatForStringProperty(popupBinding.getTitle(), op)));
        out.w("');");
      }
    }
  }

  /**
   * Determine whether the ord is for an external website or not.
   * Http/Https OrdQueries are considered 'external' by default so that we don't
   * append a view Query parameter for fullScreen. Also for Http and Http Schemes,
   * an 'external=false' override can be provided as a viewQuery parameter.
   * @param ord
   * @return
   */
  public static boolean isExternalLink(BOrd ord)
  {
    if(ord.isNull()) return false;

    boolean external = false;
    OrdQuery[] q = ord.parse();
    for (int i = 0; i<q.length; i++)
    {
      if (q[i].getScheme().equals(BHttpScheme.INSTANCE.getId()) ||
        q[i].getScheme().equals(BHttpsScheme.INSTANCE.getId()))
      {
        external=true;
      }
      //If the ViewQuery contains the "|view?external=false", this can be used to designate that
      // its a popup to another station with the http or https schemes
      if(external && q[i] instanceof ViewQuery)
      {
        ViewQuery viewQuery = (ViewQuery) q[i];
        if("false".equals(viewQuery.getParameter("external", "")))
        {
          external = false;
        }
      }
    }
    return external;
  }

  //NCCB-18915: If a 3rd party Profile doesn't yet provide ability to remove the chrome around the view, provide this as a
  //workaround.
  private static boolean ignorePopupPositioning =
    AccessController.doPrivileged((PrivilegedAction<Boolean>)
      () -> Boolean.getBoolean("hx.ignorePopupPositioning"));
}
