/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx.px.binding;

import javax.baja.agent.BIAgent;
import javax.baja.hx.BHxFieldEditor;
import javax.baja.hx.Command;
import javax.baja.hx.Dialog;
import javax.baja.hx.HxOp;
import javax.baja.hx.HxUtil;
import javax.baja.hx.PropertiesCollection;
import javax.baja.hx.px.BHxPxWidget;
import javax.baja.hx.px.MouseEventCommand;
import javax.baja.io.HtmlWriter;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Slot;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BValueBinding;
import javax.baja.ui.event.BInputEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.util.LexiconModule;

import com.tridium.hx.util.HxUtils;
import com.tridium.web.WebUtil;

/**
 * @author Lee Adcock on 29 Sept 09
 * @since Niagara 3.5
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bajaui:ValueBinding"
  )
)
@NiagaraSingleton
public class BHxPxValueBinding
  extends BHxPxBinding
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.hx.px.binding.BHxPxValueBinding(3684952000)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxPxValueBinding INSTANCE = new BHxPxValueBinding();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxPxValueBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  protected BHxPxValueBinding()
  {
    // This is invoked when an option is selected from our context
    // menu. It handles invoking the action, or prompting for the
    // parameter when required.
    registerEvent(invokeActionCommand = new InvokeActionCommand());    
  }

  @Override
  public void write(HxOp op)
    throws Exception
  {
    HxOp widgetOp = ((HxOp) op.getBase().getBase());
    BBinding binding = (BBinding) op.get();
    BHxPxWidget hxPxWidget = BHxPxWidget.makeFor(binding.getWidget());

    //register event handlers for supporting long touches
    MouseEventCommand mouseHandler = hxPxWidget.getMouseEventHandler();
    if (mouseHandler != null)
    {
      hxPxWidget.registerEvent(mouseHandler, "mouseEventHandler");
      if (hxPxWidget.isEventRegistered(hxPxWidget.mouseEventCommand))
      {
        StringBuilder onload = new StringBuilder();
        onload.append("hx.$addLongTouch(hx.$('").append(widgetOp.getPath()).append("'),function(event){").append(mouseHandler.getInvokeCode(BMouseEvent.MOUSE_RELEASED, widgetOp)).append("});");
        op.addOnload(onload.toString());
      }
    }
  }

  @Override
  public void update(int width, int height, boolean forceUpdate, HxOp op)
    throws Exception
  {
    super.update(width, height, forceUpdate, op);
    BValueBinding valueBinding = (BValueBinding)op.get();
    
    // Set the tooltip
    HxOp baseOp = ((HxOp)op.getBase().getBase());
    if(valueBinding.isBound())
    {
      PropertiesCollection properties = new PropertiesCollection.Properties();
      properties.addUnsafe("title",valueBinding.getSummary().format(valueBinding.getTarget().get(), op));
      properties.write(baseOp);
    }
    
    // Set the cursor
    if(!valueBinding.getHyperlink().isNull() && MouseEventCommand.isMouseEnabled(valueBinding.getWidget()))
    {
      PropertiesCollection style= new PropertiesCollection.Styles();
      style.add("cursor","pointer");
      style.write(baseOp);
      
      PropertiesCollection events = new PropertiesCollection.Events();
      events.add("onmouseover","window.status=\""+HxUtil.escapeJsStringLiteral(valueBinding.getHyperlink().toString())+"\"; return true;");
      events.add("onmouseout","window.status=\"\"; return true;");
      events.write(baseOp);
    }    
  }

  @Override
  public void handle(BInputEvent event, HxOp op)
    throws Exception
  {

    if (event instanceof BMouseEvent && (event.getId() == BMouseEvent.MOUSE_PRESSED || event.getId() == BMouseEvent.MOUSE_RELEASED))
    {
      BValueBinding valueBinding = (BValueBinding) op.get();
      HtmlWriter out = op.getHtmlWriter();

      BMouseEvent mouseEvent = (BMouseEvent) event;

      // Left clicks are handled if we have a hyperlink set
      if (mouseEvent.isButton1Down() && !valueBinding.getHyperlink().isNull())
      {
        out.w("hx.dynamic=false;");
        out.w("hx.hyperlink('").w(HxUtil.escapeJsStringLiteral(WebUtil.toUri(op, op.getRequest(), BOrd.make(op.getOrd(), valueBinding.getHyperlink()).normalize()))).w("');");
      }
      // Right clicks are handled if we have a context menu
      if (mouseEvent.isButton2Down() && hasContextMenu(valueBinding, op))
      {
        new ActionMenu(valueBinding).open(op);
      }
    }
  }

  @Override
  public boolean process(HxOp op) throws Exception
  {
    if(processAction(invokeActionCommand, op))
      return true;

    return super.process(op);
  }


  /**
   * Route any process callbacks to any of the Field Editors within the InvokeActionCommand Dialog.
   * Returns true if one of the field editors handled the process.
   * @since Niagara 4.6
   */
  public static boolean processAction(InvokeActionCommand invokeActionCommand, HxOp op)
  {
    BBinding valueBinding = (BBinding)op.get();
    if(valueBinding.isBound())
    {
      OrdTarget target = valueBinding.getTarget();
      BComponent component = target.getComponent();
  
      String actionName = invokeActionCommand.getActionName(op);
      if(actionName != null)
      {
        Slot slot = component.getSlot(actionName);
        if(slot == null || !slot.isAction())
          return false;
        Action action = slot.asAction();
        BValue def = component.getActionParameterDefault(action);
        if(def!=null)
        {
          HxOp childOp = op.make("param", new OrdTarget(op, def));
          childOp.mergeFacets(component.getSlotFacets(action));
      
          BHxFieldEditor editor = BHxFieldEditor.makeForUx(def, childOp);
          try
          {
            if(editor.process(childOp))
              return true;
          } 
          catch (Exception ignore)
          {
          
          }
        }
      }
    }
    
    // If not, continue as normal.
    return false;
  }  
  
  /*
   * Is the context menu available for this binding?
   */
  protected boolean hasContextMenu(BValueBinding binding, HxOp op)
  {
    if(!binding.isBound() || !binding.getPopupEnabled())
      return false;
    
    BComponent component = binding.getTarget().getComponent();   
    
    if(component==null)
      return false;
    
    BPermissions permissions = component.getPermissions(op);
    SlotCursor<Action> cur = component.getActions();
    while (cur.next())
    { 
      Action action = cur.action();
      int flags = component.getFlags(action);
      boolean hidden = (flags & Flags.HIDDEN) != 0;
      boolean oper = (flags & Flags.OPERATOR) != 0;      
      
      if (hidden) continue;
      if (oper) { if (!permissions.has(BPermissions.OPERATOR_INVOKE)) continue; }
      else { if (!permissions.has(BPermissions.ADMIN_INVOKE)) continue; }

      return true;
    }
    
    return false;
  }  
  
  // TODO should really extend a new, (non-existing) "Window" parent class
  class ActionMenu
    extends Dialog
  {
  
    ActionMenu(BValueBinding binding)
    {
      super((String)null, null);
      this.binding = binding;
      this.component = binding.getTarget().getComponent();
    }

    @Override
    public Command[] getCommands()
    {
      return new Command[] { };
    }

    @Override
    protected String getTitle(HxOp op)
    {
      return component.getDisplayName(op);
    }

    @Override
    public void open(HxOp op)
      throws Exception
    {
      HtmlWriter out = op.getHtmlWriter();
      out.w("var body = \"");
      write(op);
      out.w("\";");
      out.w("hx.doShowMenu(body);");
      
      //add static onload code
      if (onload.length() > 0)
        out.w(onload.toString());
      
      //add dynamic onload code
      String[] code = op.getOnload();
      for (int i=0; i<code.length; i++)
        out.w(code[i]); 
  
    }

    @Override
    public void write(HxOp op)
      throws Exception
    {
      writeContent(op);
    }

    @Override
    protected void writeContent(HxOp op) throws Exception
    {
      BPermissions permissions = component.getPermissions(op);
      SlotCursor<Action> cur = component.getActions();
      
      while (cur.next())
      { 
        Action action = cur.action();
        int flags = component.getFlags(action);
        boolean hidden = (flags & Flags.HIDDEN) != 0;
        boolean oper = (flags & Flags.OPERATOR) != 0;      
        
        if (hidden) continue;
        if (oper) { if (!permissions.has(BPermissions.OPERATOR_INVOKE)) continue; }
        else { if (!permissions.has(BPermissions.ADMIN_INVOKE)) continue; }
        
        String displayName = component.getDisplayName(action, op);

        HxUtil.writeContextMenuListItem(invokeActionCommand.getInvokeCode(action, op), displayName, BOrd.DEFAULT, op);
      }
    }
    
    BComponent component;
    BValueBinding binding;
  }

  /**
   * InvokeActionCommand will provide any confirmation and value prompting for firing an action.
   */
  public static class InvokeActionCommand
    extends Command
  {
    public InvokeActionCommand()
    {
      super(null);
    }

    public String getActionName(HxOp op)
    {
      return op.getFormValue("action");
    }

    /**
    * Return a non-null BValue to prevent the dialog from showing for an action that usually requires a parameter
    */
    public BValue getDefaultActionArgument(HxOp op)
    {
      return null;
    }
  
    public String getInvokeCode(Action action, HxOp op)
    {
      String quote = HxUtil.getOuterQuote(op);
      
      StringBuilder buf = new StringBuilder();
      buf.append("hx.setFormValue(");
      buf.append(quote).append(op.scope("action")).append(quote);
      buf.append(",");
      buf.append(quote).append(action.getName()).append(quote);
      buf.append(");");
      
      buf.append("hx.fireEvent(");
      buf.append(quote).append(op.getPath()).append(quote);
      buf.append(",");
      buf.append(quote).append(getId()).append(quote);
      buf.append(");");
      return buf.toString();
    }

    @Override
    public void handle(HxOp op) throws Exception
    {
      handleAction(this, op);
    }
  }

  /**
   * Handle launching a dialog for confirmation and parameters when appropriate and invoking the action when ready
   * @since 4.6
   */
  public static void handleAction(InvokeActionCommand command, HxOp op)
    throws Exception
  {
    BBinding binding = (BBinding)op.get();
    if(!binding.isBound())
      return;
    BComponent component = binding.getTarget().getComponent();

    String slotName = command.getActionName(op);
    if(slotName == null)
      return;
    Slot slot = component.getSlot(slotName);
    if(slot == null || !slot.isAction())
      return;
    Action action = slot.asAction();
    BValue def = component.getActionParameterDefault(action);

    if(Flags.isConfirmRequired(component, action))
    {
      if(op.getFormValue("confirmed")==null)
      {
        ActionConfirmation dialog = new ActionConfirmation(action, binding, command);
        dialog.open(op);
        return;
      }
    }

    if (def == null)
    {
      // No parameter, so just invoke the action
      component.invoke(action, null, op);
      HxUtils.forceUpdate(op);
    }
    else
    {
      ActionDialog dialog = new ActionDialog(action, binding, command);

      BValue defaultActionArg = command.getDefaultActionArgument(op);
      if (defaultActionArg != null)
      {
        component.invoke(action, defaultActionArg, op);
        HxUtils.forceUpdate(op);
      }
      else if (op.getFormValue("paramsDisplayed") != null)
      {
        // Parameter required, but it looks like we have it
        BValue param = dialog.saveContent(op);
        component.invoke(action, param, op);
        HxUtils.forceUpdate(op);
      }
      else
      {
        // parameter required, but we don't have it
        dialog.open(op);
      }
    }
  }
  
  public static class ActionConfirmation
    extends Dialog
  {
    
    Action action;
    BComponent component;
    BBinding binding;
    
    public ActionConfirmation(Action action, BBinding binding, Command command)
    {
      super((String)null, command);
      
      this.component = binding.getTarget().getComponent();
      this.action = action;
      this.binding = binding; 
    }

    @Override
    protected String getTitle(HxOp op)
    {
      return HX_LEX.getText("invoke.confirmTitle", op, component.getDisplayName(action, op));
    }

    @Override
    protected void writeContent(HxOp op) throws Exception
    {
      HtmlWriter out = op.getHtmlWriter();
      String prompt = BAJAUI_LEX.getText("invoke.confirm", op, component.getDisplayName(action, op));
      out.w("<div align='center'>");
      out.safe(prompt);
      out.w("</div>");
      out.w("<input type='hidden' name='").w(op.scope("confirmed")).w("' value='true'/>");
    }
    
    public Command[] getCommands()
    {
      return new Command[] {
          new Submit(),
          new Cancel()
      };
    }     
  }

  static class ActionDialog
    extends Dialog
  {
  
    ActionDialog(Action action, BBinding binding, Command command)
    {
      super((String)null, command);
      
      this.component = binding.getTarget().getComponent();
      this.action = action;
      this.binding = binding;

    }

    @Override
    protected String getTitle(HxOp op)
    {
      return component.getDisplayName(action, op);
    }
    
    protected BValue saveContent(HxOp op) throws Exception
    {
      OrdTarget target = binding.getTarget();
      BComponent c = target.getComponent();
      BValue def = c.getActionParameterDefault(action);
      HxOp childOp = op.make("param", new OrdTarget(op, def));
      childOp.mergeFacets(c.getSlotFacets(action));
  
      BHxFieldEditor editor = BHxFieldEditor.makeForUx(def, childOp);
      return (BValue)editor.fwSave(childOp);
    }

    @Override
    protected void writeContent(HxOp op) throws Exception
    {
      OrdTarget target = binding.getTarget();
      BComponent c = target.getComponent();
      BValue def = c.getActionParameterDefault(action);
      HxOp childOp = op.make("param", new OrdTarget(op, def));
      childOp.mergeFacets(component.getSlotFacets(action));
  
      HtmlWriter out = op.getHtmlWriter();
  
      // Contents
      BHxFieldEditor editor = BHxFieldEditor.makeForUx(def, childOp);
  
      out.write("<div align='center'>");
      editor.write(childOp);
      out.write("</div>");
      out.w("<input type='hidden' name='").w(op.scope("confirmed")).w("' value='true'/>");
      out.w("<input type='hidden' name='").w(op.scope("paramsDisplayed")).w("' value='true'/>");
    }

    @Override
    public Command[] getCommands()
    {
      return new Command[] {
          new ActionSubmit(),
          new Cancel()
      };
    }
  
    protected class ActionSubmit extends Dialog.Submit
    {
      @Override
      public String getInvokeCode(HxOp op)
      { 
        StringBuilder buf = new StringBuilder();
        
        buf.append("hx.setFormValue(&quot;").append(op.scope("action")).append("&quot;,&quot;")
        .append(op.getFormValue("action"))
        .append("&quot;); ");
  
        buf.append("hx.closeDialog(&quot;")
        .append(op.getPath())
        .append("&quot;,&quot;")
        .append(getHandler().getId())
        .append("&quot;,null);");
        return buf.toString();
      }
    }
  
    Action action;
    BComponent component;
    BBinding binding;
  }
    
  private InvokeActionCommand invokeActionCommand;
  private static LexiconModule HX_LEX = LexiconModule.make("hx");
  private static LexiconModule BAJAUI_LEX = LexiconModule.make("bajaui");
}
