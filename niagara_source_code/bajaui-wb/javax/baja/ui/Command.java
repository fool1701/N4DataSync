/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.*;
import javax.baja.sys.*;
import javax.baja.util.*;
import javax.baja.gx.*;
import javax.baja.ui.util.*;

import com.tridium.util.*;

/**
 * Command wraps up all the information associated with
 * displaying a command including its label, icon, accelerator,
 * and description.  It also provides the callback for
 * processing the command when it is invoked.
 *
 * @author    Brian Frank
 * @creation  4 Dec 00
 * @version   $Revision: 39$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
public class Command
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Create a new command by populating the command's
   * visualization information from a module's lexicon.  
   * The command is initialized by looking for the following
   * keys:  keyBase+".label", keyBase+".icon", 
   * keyBase+".accelerator", and keyBase+".description".
   * The icon value should be an ord to a 16x16 png file.
   */
  public Command(BWidget owner, BModule module, String keyBase)
  {  
    this(owner, module.getLexicon(), keyBase);
  }
  
  /**
   * Create a new command by populating the command's
   * visualization information from a module's lexicon.  
   * The command is initialized by looking for the following
   * keys:  keyBase+".label", keyBase+".icon", 
   * keyBase+".accelerator", and keyBase+".description".
   * The icon value should be an ord to a 16x16 png file.
   */
  public Command(BWidget owner, Lexicon lexicon, String keyBase)
  {
    if (owner == null) throw new NullPointerException("Null owner");
    this.owner = owner;
    this.keyBase = keyBase;
    
    String label = lexicon.get(keyBase+".label");
    String icon  = lexicon.get(keyBase+".icon");
    String acc   = lexicon.get(keyBase+".accelerator");
    String desc  = lexicon.get(keyBase+".description");
    
    this.label = label;
    
    if (acc != null) 
    {
      try 
      { 
        this.accelerator = BAccelerator.make(acc); 
      } 
      catch(RuntimeException e) 
      {                
        System.out.println("Invalid accelerator for Command " + keyBase);
        e.printStackTrace(); 
      }
    }
    
    this.description = desc;  
    
    if (icon != null)
      this.icon = BImage.make(icon);
  }

  /**
   * Create a new command by populating the command's
   * visualization information from a properties file.  
   * The command is initialized by looking for the following
   * keys:  keyBase+".label", keyBase+".icon", 
   * keyBase+".accelerator", and keyBase+".description".
   * The icon value should be an ord to a 16x16 png file.
   */
  public Command(BWidget owner, Properties props, String keyBase)
  {
    if (owner == null) throw new NullPointerException("Null owner");
    this.owner = owner;
    this.keyBase = keyBase;
    
    String label = props.getProperty(keyBase+".label");
    String icon  = props.getProperty(keyBase+".icon");
    String acc   = props.getProperty(keyBase+".accelerator");
    String desc  = props.getProperty(keyBase+".description");
    
    this.label = label;
    
    if (acc != null) 
    {
      try 
      { 
        this.accelerator = BAccelerator.make(acc); 
      } 
      catch(RuntimeException e) 
      { 
        System.out.println("Invalid accelerator for Command " + keyBase);
        e.printStackTrace(); 
      }
    }               
    
    this.description = desc;  
    
    if (icon != null)
      this.icon = BImage.make(icon);
  }
  
  /**
   * Create a new Command with the specified 
   * visualization information.
   */
  public Command(BWidget owner, String label, BImage icon, BAccelerator acc, String description)
  {
    if (owner == null) throw new NullPointerException("Null owner");
    this.owner = owner;
    this.label = label;
    this.icon = icon;
    this.accelerator = acc;
    this.description = description;
  }

  /**
   * Create a new Command with the specified label and 
   * default all other fields to null.
   */
  public Command(BWidget owner, String label)
  {
    if (owner == null) throw new NullPointerException("Null owner");
    this.owner = owner;
    this.label = label;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Return the widget owner.
   */
  public final BWidget getOwner() { return owner; }

  /**
   * Return the shell associated with this command,
   * which is the shell of the owner.
   */
  public final BWidgetShell getShell() { return owner.getShell(); }

  /**
   * Get a label to display for the command 
   * on buttons and menu items, or return
   * null if there is no label.
   */
  public String getLabel() { return label; }
  
  /**
   * Get the keyBase initially used to create this
   * command (e.g. "commands.home"). This will also be
   * used as a style ID for the button executing this command.
   */
  public String getKeyBase() { return keyBase; }
    
  /**
   * Get the accelerator for the command or
   * null if this command doesn't support an
   * accelerator.
   */
  public BAccelerator getAccelerator() { return accelerator; }
  
  /**
   * Get an BImage to use for drawing the command's
   * icon or null if this command doesn't have an icon.
   */
  public BImage getIcon() { return icon; }
  
  /**
   * Get a short description of the command which can
   * be used in tooltips and displayed in the status 
   * bar for rollovers, or null if no description is
   * available.
   */
  public String getDescription() { return description; }

////////////////////////////////////////////////////////////////
// Enabled
////////////////////////////////////////////////////////////////

  /**
   * Is this command currently enabled.
   */
  public synchronized boolean isEnabled()
  {
    return enabled;
  }

  /**
   * Enable or disable the command and all
   * of its registered widgets.
   */
  public synchronized void setEnabled(boolean enabled)
  {
    if (this.enabled == enabled) return;
    this.enabled = enabled;
    for(BWidget widget : getRegistry())
      widget.setEnabled(enabled);
  }

////////////////////////////////////////////////////////////////
// Registry
////////////////////////////////////////////////////////////////
  
  /**
   * Get the list of BWidget's that are currently
   * registered on this command.
   */
  public synchronized BWidget[] getRegistry()
  {
    return registry.keySet().toArray(new BWidget[]{});
  }

  /**
   * Register the specified widget to be enabled
   * and disabled based on the state of the command.
   */
  public synchronized void register(BWidget widget)
  {
    registry.put(widget, null);
    widget.setEnabled(enabled);
  }

  /**
   * Unregister the specified widget from this command
   * so that its enable and disable state is no longer
   * maintained by this command.
   */
  public synchronized void unregister(BWidget widget)
  {
    registry.remove(widget);
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  /**
   * Convenience for <code>invoke(null)</code>.
   */
  public final void invoke()
  {
    invoke(null);                        
  }

  /**
   * Invoke the action and if undoable register the
   * CommandArtifact with the shell's undo manager.
   */
  public final void invoke(CommandEvent event)
  {              
    if (event == null) event = CommandEvent.NULL;
    BWidgetShell shell = owner.getShell();
    boolean doBusy = (shell != null) && isBusyForInvoke();
    if (doBusy) shell.enterBusy();
    try
    {
      CommandArtifact artifact = doInvoke(event);
      if (artifact != null && owner != null) 
      {
        UndoManager um = owner.getUndoManager();
        if (um != null) um.addArtifact(artifact);
      }
    }
    catch(Throwable e)
    {
      ThrowableUtil.dump(e);
      String title = UiLexicon.bajaui().getText("dialog.error");
      String msg = UiLexicon.bajaui().getText("command.error", new Object[] { getLabel() });
      BDialog.error(owner, title, msg, e);
    }    
    finally
    {
      if (doBusy) shell.exitBusy();
    }
  }
  
  /**
   * Implementation of the invoke.  If the command 
   * invocation is undoable (and redoable), then return
   * an new instance of CommandArtifact.  If the
   * command is not undoable, then return null.  This
   * method routes to <code>doInvoke()</code>.
   */
  public CommandArtifact doInvoke(CommandEvent event)
    throws Exception
  {
    return doInvoke();
  }                

  /**
   * This is the default implementation of <code>doInvoke(CommandEvent)</code>.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    return null;
  }                
  
  /**
   * By default whenever a command is invoked the shell is
   * set into the busy state.  This behavior may be changed
   * by overriding this method to return false.
   */
  protected boolean isBusyForInvoke()
  {
    return true;
  }  

////////////////////////////////////////////////////////////////
// Merge
////////////////////////////////////////////////////////////////  
  
  /**
   * If this command can be merged with the specified command
   * to produce a new command that does both tasks, then return 
   * the new compound command.  If this command cannot be merged
   * with the given command return null.  Neither this command
   * nor the specified command should be modified or produce
   * side effects.  This method checks common basics, then routes
   * to doMerge().
   */
  public Command merge(Command c)
  {               
    if (getClass() == c.getClass() && owner == c.owner)
      return doMerge(c);
    else
      return null;
  }               
  
  /**
   * This is the implementation method for merge().  When this
   * method is called it has already been determined that the
   * given command has the same class and owner as myself.
   */
  protected Command doMerge(Command c)
  {
    return null;
  }
  
////////////////////////////////////////////////////////////////
// Misc
////////////////////////////////////////////////////////////////  

  public String toString()
  {
    return "Command: " + label;
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private String keyBase;
  protected String label;
  protected BImage icon;
  protected BAccelerator accelerator;
  protected String description;
  
  private final BWidget owner;

  //NCCB-11274: used to store weak references registered widgets to prevent memory leak when we can't unregister
  private WeakHashMap<BWidget, Object> registry = new WeakHashMap<>();
  private boolean enabled = true;

}
