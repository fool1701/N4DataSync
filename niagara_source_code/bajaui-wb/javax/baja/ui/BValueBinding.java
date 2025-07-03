/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.lang.reflect.Method;

import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BString;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Clock.Ticket;
import javax.baja.sys.Context;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.commands.HyperlinkCommand;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.BConverter;
import javax.baja.util.BFormat;

import com.tridium.sys.schema.Fw;
import com.tridium.ui.UiEnv;

/**
 * BValueBinding is used to bind to BValues typically under
 * a BComponent.  Widget properties may be overridden by
 * creating dynamic slots of the same name with a BConverter
 * that maps the bound target to the property value.
 *
 * @author    Brian Frank       
 * @creation  18 May 04
 * @version   $Revision: 16$ $Date: 6/10/10 1:13:24 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = {
    @AgentOn(
      types = "bajaui:Widget"
    ),
    @AgentOn(
      types = "baja:Value"
    )
  }
)
/*
 If this ord is non-null then clicking inside the
 bound widget will perform a hyperlink.
 */
@NiagaraProperty(
  name = "hyperlink",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
/*
 This property is used to format the summary string
 which is displayed in the status bar on mouse over.
 */
@NiagaraProperty(
  name = "summary",
  type = "BFormat",
  defaultValue = "BFormat.make(\"%displayName?typeDisplayName% = %.%\")"
)
/*
 If this property is true and this binding is bound
 to a component, a popup menu is displayed to invoke
 the actions.  If false this feature is disabled.
 */
@NiagaraProperty(
  name = "popupEnabled",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraAction(
  name = "updateStatus",
  flags = Flags.HIDDEN
)
public class BValueBinding
  extends BBinding
{                             

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BValueBinding(1664325211)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "hyperlink"

  /**
   * Slot for the {@code hyperlink} property.
   * If this ord is non-null then clicking inside the
   * bound widget will perform a hyperlink.
   * @see #getHyperlink
   * @see #setHyperlink
   */
  public static final Property hyperlink = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code hyperlink} property.
   * If this ord is non-null then clicking inside the
   * bound widget will perform a hyperlink.
   * @see #hyperlink
   */
  public BOrd getHyperlink() { return (BOrd)get(hyperlink); }

  /**
   * Set the {@code hyperlink} property.
   * If this ord is non-null then clicking inside the
   * bound widget will perform a hyperlink.
   * @see #hyperlink
   */
  public void setHyperlink(BOrd v) { set(hyperlink, v, null); }

  //endregion Property "hyperlink"

  //region Property "summary"

  /**
   * Slot for the {@code summary} property.
   * This property is used to format the summary string
   * which is displayed in the status bar on mouse over.
   * @see #getSummary
   * @see #setSummary
   */
  public static final Property summary = newProperty(0, BFormat.make("%displayName?typeDisplayName% = %.%"), null);

  /**
   * Get the {@code summary} property.
   * This property is used to format the summary string
   * which is displayed in the status bar on mouse over.
   * @see #summary
   */
  public BFormat getSummary() { return (BFormat)get(summary); }

  /**
   * Set the {@code summary} property.
   * This property is used to format the summary string
   * which is displayed in the status bar on mouse over.
   * @see #summary
   */
  public void setSummary(BFormat v) { set(summary, v, null); }

  //endregion Property "summary"

  //region Property "popupEnabled"

  /**
   * Slot for the {@code popupEnabled} property.
   * If this property is true and this binding is bound
   * to a component, a popup menu is displayed to invoke
   * the actions.  If false this feature is disabled.
   * @see #getPopupEnabled
   * @see #setPopupEnabled
   */
  public static final Property popupEnabled = newProperty(0, true, null);

  /**
   * Get the {@code popupEnabled} property.
   * If this property is true and this binding is bound
   * to a component, a popup menu is displayed to invoke
   * the actions.  If false this feature is disabled.
   * @see #popupEnabled
   */
  public boolean getPopupEnabled() { return getBoolean(popupEnabled); }

  /**
   * Set the {@code popupEnabled} property.
   * If this property is true and this binding is bound
   * to a component, a popup menu is displayed to invoke
   * the actions.  If false this feature is disabled.
   * @see #popupEnabled
   */
  public void setPopupEnabled(boolean v) { setBoolean(popupEnabled, v, null); }

  //endregion Property "popupEnabled"

  //region Action "updateStatus"

  /**
   * Slot for the {@code updateStatus} action.
   * @see #updateStatus()
   */
  public static final Action updateStatus = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code updateStatus} action.
   * @see #updateStatus
   */
  public void updateStatus() { invoke(updateStatus, null, null); }

  //endregion Action "updateStatus"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BValueBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////
  
  public void targetChanged()
  {
    super.targetChanged();
    toSummary = null;  // reset to be lazy loaded again
  }
  
  public BValue getOnWidget(Property prop) 
  {
    BValue override = get(prop.getName());
    if (override instanceof BConverter)
    {                       
      BConverter converter = (BConverter)override;
      try
      {                
        BObject from = get();
        BObject to   = prop.getDefaultValue().newCopy();
        
        //check for null component before attempting to set base of From 
        OrdTarget target = getTarget();
        if( null != target)
        {
          BComponent component = target.getComponent();
          if( null != component)
          {
            BOrd navOrd = component.getNavOrd();
            from.fw(Fw.SET_BASE_ORD,navOrd,null,null,null);
          }
        }

        to = converter.convert(from, to, getConverterContext());
        if(to instanceof BString)
          to = BString.make(TextUtil.replace(to.toString(), "\\n", "\n"));
        return (BValue)to;
      }
      catch(Exception e)
      {                                         
        // don't log if same problem as last time
        if (lastGetOnWidgetException == null || lastGetOnWidgetException.getClass() != e.getClass())
        {
          System.out.println("WARNING: " + converter.getType().getTypeName() + " cannot convert " + getWidget().getName() + "." + prop.getName());
          System.out.println("  " + e);                                 
          lastGetOnWidgetException = e;
        }
      }
    } 
    return null;                         
  }

  /**
   * Returns the context to be passed to the BConverter's convert() method in getOnWidget(). 
   * This defaults to the binding's target.
   * Overriding implementations can augment the default target context with additional facet information
   * if required. See kitPx:MouseOverBinding for an example of a context override.
   *
   * @return Context OrdTarget
   */
  protected Context getConverterContext()
  {
    return getTarget();
  }
  
  public void changedOnWidget(Property property, Context context) 
  {
  }

  public boolean invokedOnWidget(Action action, BValue value, Context context) 
  {             
    return super.invokedOnWidget(action, value, context);
  }

  public boolean firedOnWidget(Topic topic, BValue event, Context context) 
  {                              
    if (event instanceof BMouseEvent)
      return handleMouseEvent((BMouseEvent)event);
    else
      return super.firedOnWidget(topic, event, context);
  } 

////////////////////////////////////////////////////////////////
//Events
////////////////////////////////////////////////////////////////  
  
  public void doUpdateStatus()
  {
    if(!isOver)
    {
      // Just to be safe, although this shouldn't be possible
      if(shellUpdateTicket!=null && !shellUpdateTicket.isExpired())
        shellUpdateTicket.cancel();
      return;
    }
    
    BWidgetShell shell = getShell();
    if (shell != null) shell.showStatus(toShowStatus());
  }
  
////////////////////////////////////////////////////////////////
// Mouse Eventing
////////////////////////////////////////////////////////////////

  boolean handleMouseEvent(BMouseEvent event)
  {
    switch(event.getId())
    {  
      case BMouseEvent.MOUSE_ENTERED:  return entered(event);
      case BMouseEvent.MOUSE_EXITED:   return exited(event);
      case BMouseEvent.MOUSE_PRESSED:  return pressed(event);
      case BMouseEvent.MOUSE_RELEASED: return released(event);
    }       
    return false;
  }
  
  boolean entered(BMouseEvent event)
  {       
    BWidgetShell shell = getShell();
    isOver = true;
    if(shellUpdateTicket==null || shellUpdateTicket.isExpired())
      shellUpdateTicket = Clock.schedulePeriodically(this, BRelTime.makeSeconds(1), BValueBinding.updateStatus, null);

    if (shell != null)
    {
      shell.showStatus(toShowStatus());
      if (!getHyperlink().isNull() && UiEnv.get().hasMouse())    
        restoreCursor = getWidget().setMouseCursor(MouseCursor.hand);
      else
        restoreCursor = null;
    }
    return true;
  }
  
  boolean exited(BMouseEvent event)
  {
    BWidgetShell shell = getShell();
    isOver = false;
    if(shellUpdateTicket!=null && !shellUpdateTicket.isExpired())
      shellUpdateTicket.cancel();
    
    if (shell != null)
      shell.showStatus(""); 
      
    if (restoreCursor != null)
      getWidget().setMouseCursor(restoreCursor);
    return true;
  }                 

  boolean pressed(BMouseEvent event)
  {                    
    if (isPopupTrigger(event)) 
    {
      BMenu menu = getMenu();
      if (menu != null) return popup(event, menu);
    }
    return false;
  }

  boolean released(BMouseEvent event)
  {                                                
    if (isPopupTrigger(event)) 
    {
      BMenu menu = getMenu();
      if (menu != null) return popup(event, menu);
      else if (UiEnv.get().hasMouse()) return false;
      // drop thru if no mouse
    }
    
    if (isOver && !getHyperlink().isNull())
    {                                     
      BOrd ord = getHyperlink();
      BWidgetShell shell = getShell();
      if (shell instanceof BIHyperlinkShell)
        ((BIHyperlinkShell)shell).hyperlink(new HyperlinkInfo(ord, event));
      return true;
    }             
    
    return false;
  }

  BMenu getMenu()
  {
    if (!getPopupEnabled()) return null;
    if (!isBound()) return null;
    
    OrdTarget target = getTarget();
    BComponent component = target.getComponent();
    if (component == null) return null;
  
    // use reflection to call workbench util 
    // to build an action menu
    try
    {
      Class<?> cls = Sys.loadClass("workbench", "javax.baja.workbench.nav.menu.NavMenuUtil");
      Method method = cls.getMethod("makeActionsMenu", new Class<?>[] { BWidget.class, BComponent.class });
      BMenu menu = (BMenu)method.invoke(null, new Object[] { getWidget(), component });
      return menu.isEnabled() ? menu : null;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }
  }

  boolean popup(BMouseEvent event, BMenu menu)
  {                              
    if (addLinkCommand())
    {
      // if no mouse, add hyperlink to menu
      menu.add("hyperlink", new HyperlinkCommand(getWidget(), 
        UiLexicon.bajaui().getText("hyperlinkTo"), getHyperlink()));
      menu.add("hyperlinkSep", new BSeparator());
      
      // reorder to top
      menu.reorderToTop(menu.getProperty("hyperlinkSep"));
      menu.reorderToTop(menu.getProperty("hyperlink"));
    }
    
    menu.open(getWidget(), event.getX(), event.getY());
    return true;
  }

  boolean isPopupTrigger(BMouseEvent event)
  {
    BWidget w = getWidget();
    boolean v = event.isPopupTrigger();
    
    // Keep button behavoir the same
    if (w instanceof BAbstractButton) 
      return v;
      
    // If no mouse, left clicks are popup triggers too
    return v || !UiEnv.get().hasMouse();
  }
  
  boolean addLinkCommand()
  {
    boolean v = true;
    v &= !UiEnv.get().hasMouse();
    v &= !getHyperlink().isNull();
    v &= !(getWidget() instanceof BAbstractButton);
    return  v;
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  String toShowStatus()
  {
    String summary = toSummary();
    BOrd hyperlink = getHyperlink();
    if (hyperlink.isNull()) return summary;
    return "Link to " + hyperlink + " | " + summary;
  }

  String toSummary()
  {
    if (toSummary == null)
    { 
      try
      {                               
        if (isBound())
          toSummary = getSummary().format(get());
        else
          toSummary = "";
      }
      catch(Exception e)
      {
        e.printStackTrace();
        toSummary = "";
      }
    }
    return toSummary;              
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Ticket shellUpdateTicket;
  boolean isOver;
  MouseCursor restoreCursor; 
  String toSummary = null;
  Exception lastGetOnWidgetException;
}
