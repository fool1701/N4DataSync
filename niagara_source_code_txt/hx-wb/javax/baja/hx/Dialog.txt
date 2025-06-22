/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import javax.baja.io.HtmlWriter;
import javax.baja.util.Lexicon;
import javax.baja.util.LexiconText;

import javax.baja.util.LexiconModule;

/**
 * Dialog models a modal dialog box in the browser.
 *
 * @author    Andy Frank on 23 Jun 05
 * @since     Baja 1.0
 */
public class Dialog
{
////////////////////////////////////////////////////////////////
// Constrctor
////////////////////////////////////////////////////////////////

  /**
   * Construct a new dialog. {@code handler} is the
   * command that will be invoked if 'Ok' is selected
   * in the dialog.
   */
  public Dialog(String title, Command handler)
  {
    this.title = title;
    this.handler = handler;
  }

  /**
   * Construct a new dialog. {@code handler} is the
   * command that will be invoked if 'Ok' is selected
   * in the dialog.
   */
  public Dialog(LexiconText title, Command handler)
  {
    this.title = title;
    this.handler = handler;
  }

  /**
   * Construct a new dialog using a LexiconModule and string key. handler {@code handler} is the
   * command that will be invoked if 'Ok' is selected
   * in the dialog.
   * @since Niagara 4.8
   */
  public Dialog(LexiconModule lexiconModule, String key, Command handler)
  {
    this.title = lexiconModule;
    this.handler = handler;
    this.key = key;
  }
  /**
   * Construct a new dialog using a blank displayName. It is best to override getTitle(HxOp)
   * if you don't know the name at the time of the Dialog Construction.
   * {@code handler} is the command that will be invoked if 'Ok' is selected
   * in the dialog.
   * @since Niagara 4.8
   */
  public Dialog(Command handler)
  {
    this.handler = handler;
  }

////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Return true if this a submit from a previous dialog.
   */
  public boolean isSubmit(HxOp op)
  {
    Command[] cmds = getCommands();
    for (Command cmd : cmds)
    {
      if ((cmd instanceof DialogCommand) &&
        ((DialogCommand) cmd).isSubmit(op))
      { return true; }
    }

    return false;
  }

  /**
   * Open this dialog in the client browser.
   */
  public void open(HxOp op)
    throws Exception
  {

    HtmlWriter out = op.getHtmlWriter();
    out.w("/* @noSnoop */"); //prevent double snoop
    out.w("var body = \"");

    // Write out the content to a buffer.
    out.w(HxUtil.escapeJsStringLiteral(HxUtil.marshal(this::write, op)));

    out.w("\";");
    out.w("hx.showDialog(body);");

    //always add static onload code (eventhough quote requirements are different for post and get
    if (onload.length() > 0) //hopefully this is never used, but is here for backwards compatibility.
      out.w(onload.toString());

    //If this is part of a GET request, all javascript ords, onloads, and globals should already be written to the dom.
    if(!HxUtil.isPost(op))
      return;

    HxUtil.addJavascriptOnload(op);
  }
  
  /**
   * Get the title to use for the dialog.
   */
  protected String getTitle(HxOp op)
  {
    if (title instanceof LexiconModule)
    {
      return ((LexiconModule)title).getText(key, op);
    }
    else if (title instanceof LexiconText)
      return ((LexiconText)title).getText(op);
    else if(title != null)
      return title.toString();
    else
      return "";
  }
  
  /**
   * Write the content for the dialog box.
   */
  protected void writeContent(HxOp op)
    throws Exception
  {    
  }
  
  /**
   * Add javascript to be executed after the dialog
   * is displayed in client browser.
   * @deprecated since Niagara 4.4. Use HxOp.addOnload instead.
   */

  @SuppressWarnings("RedundantThrows")
  @Deprecated
  protected void addOnload(String s)
    throws Exception
  {    
    onload.append(s);
  }
  
  /**
   * Return the Command used to handle submits.
   */
  public Command getHandler()
  {
    return handler;
  }
  
  /**
   * Since 3.5, but still support original syntax
   */
  public Command[] getCommands(HxOp op)
  {
    return getCommands();
  }

  /**
   * Get the commands to display on the dialog. Defaults
   * to OK and Cancel, where OK invokes the Handler event.
   * Ensure that an OK Button is first in the Array since this is the command
   * that will fire when enter is pressed in a text input within the dialog.
   */
  public Command[] getCommands()
  {
    return new Command[] { new Submit(), new Cancel() };  
  }
  
  /**
   * Return an instance of the Submit command.
   */
  public Command makeSubmitCommand()
  {
    return new Submit("submit", LexiconText.make("hx", "ok"));
  }

  /**
   * Return an instance of the Submit command with the
   * specified name.
   */
  public Command makeSubmitCommand(String name)
  {
    return new Submit(name, LexiconText.make("hx", "ok"));
  }

  /**
   * Return an instance of the Submit command with the
   * specified display name.
   */
  public Command makeSubmitCommand(LexiconText displayName)
  {
    return new Submit("submit", displayName);
  }
  
  /**
   * Return an instance of the Submit command with the
   * specified name and display name.
   */
  public Command makeSubmitCommand(String name, LexiconText displayName)
  {
    return new Submit(name, displayName);
  }
  
  /**
   * Return an instance of the Cancel command.
   */
  public Command makeCancelCommand()
  {
    return new Cancel();
  }  

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Start dialog.
   */
  public void write(HxOp op)
    throws Exception
  {
    Command[] commands = getCommands(op);
    
    HtmlWriter out = op.getHtmlWriter();
    out.w("<table border='0' align='center' height='100%'>");
    out.w("<tr><td valign='middle'>");
    out.w("<div class='control-bg dialog-outerBorder'>");
    out.w("<div class='dialog-innerBorder'>");
    
    // Titlebar
    out.w("<div class='dialog-header'>").safe(getTitle(op)).w("</div>");
    
    // Padding
    out.w("<div class='dialog-content'>");
    
    // maxHeight
    out.w("<div ");      
    out.w(" id='dialog-maxHeight'");
    out.w(">");
    // Content
    writeContent(op);
    out.w("</div>");
        
    // invisible span with a single value so that onclick is not skippd
    out.w("<span style='display:none;'>");      
    out.w(" <input type='text' name='dialog-fix'/>");
    out.w("</span>");
    
    // Buttons
    out.w("<div class='dialog-button-content-hx'>");
    for (Command command : commands)
    {
      out.w(" <input type='submit' class='button'");
      if (command instanceof DialogCommand)
      {
        out.w(" name='");
        out.w(op.scope(((DialogCommand) command).getName()));
        out.w("'");
      }
      out.w(" value='").safe(command.getDisplayName(op)).w("'");

      //If there is more than 1 dialog open, you must actually click on the submit button
      //IE7's press <enter> on any input to submit is bad practice if
      //there are multiple dialog windows open. That could cause the wrong
      //submit button to be submited.

      out.w(" onmousedown=' this.allowSubmit=true;' ");
      out.w(" onclick=' if(this.allowSubmit == true || hx.dialogCounter == 0){")
        .w(command.getInvokeCode(op)).w("} return false;'/>");
    }
    out.w("</div>");    
      
    // End padding
    out.w("</div>");
    
    // End dialog
    out.w("</div>"); // inner border
    out.w("</div>"); // outer border
    out.w("</td></tr>");
    out.w("</table>");
  }    
  
////////////////////////////////////////////////////////////////
// Dialog Command
////////////////////////////////////////////////////////////////
  
  public abstract class DialogCommand extends Command
  { 
    public DialogCommand(String name) 
    { 
      super(null); 
      this.name = name;
    }
    public boolean isSubmit(HxOp op) { return false; }
    public String getName() { return name; }
    private String name;
  }
  
////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////
  
  /**
   * Close the dialog and submit form results back to station.
   */
  protected class Submit extends DialogCommand
  {
    public Submit()
    {
      super("submit");
      this.displayName = LexiconText.make("hx", "ok");
    }
    
    public Submit(String name, LexiconText displayName)
    {
      super(name);
      this.displayName = displayName;
    }
    
    public String getDisplayName(HxOp op) 
    { 
      if (displayName == null)
        return Lexicon.make("hx", op).getText("ok");
      else
        return displayName.getText(op);
    }
    
    public String getInvokeCode(HxOp op) 
    { 
      return "hx.closeDialog(&quot;" + op.getPath() + "&quot;,&quot;"
         + handler.getId() + "&quot;, event);";
    }
    
    public boolean isSubmit(HxOp op)
    {
      return op.getFormValue(getName()) != null;
    }
    
    private LexiconText displayName;
  }
  
  /** 
   * Close the dialog taking no further action.
   */
  protected class Cancel extends DialogCommand
  {
    public Cancel() { super("cancel"); }
    public String getDisplayName(HxOp op) 
    { 
      return Lexicon.make("hx", op).getText("cancel");
    }
    public String getInvokeCode(HxOp op) 
    { 
      return "hx.closeDialog(null,null,null);"; 
    }
  }
  
////////////////////////////////////////////////////////////////
// Attribute
////////////////////////////////////////////////////////////////

  private String key;
  private Object title;
  private Command handler;
  protected StringBuilder onload = new StringBuilder();
}


 
