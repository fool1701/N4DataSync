/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.commands;

import java.lang.reflect.*;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.util.*;

/**
 * InvokeActionCommand is a specialized command which 
 * can be used in widgets to invoke a specified action
 * on a target.
 *
 * @author    Brian Frank
 * @creation  16 Feb 01
 * @version   $Revision: 12$ $Date: 11/22/06 4:41:17 PM EST$
 * @since     Baja 1.0
 */
public class InvokeActionCommand
  extends Command
{ 

  /**
   * Create a InvokeActionCommand for the specified
   * target and action.
   */
  public InvokeActionCommand(BWidget owner, BComponent target, Action action)
  {
    this(owner, target, action, null);
  }  

  /**
   * Create a InvokeActionCommand for the specified
   * target, action, and argument (skips prompt).
   */
  public InvokeActionCommand(BWidget owner, BComponent target, Action action, BValue actionArg)
  {          
    super(owner, target.getDisplayName(action, null));
    this.target = target;
    this.action = action;
    this.actionArg = actionArg;
  }  

  /**
   * Invoke the action.
   */
  public CommandArtifact doInvoke()
    throws Exception
  {
    // If mounted, sync to ensure the Component Space is up to date.
    // This happens because of Niagara Proxy Points. The subscription event
    // can cause the dynamic Actions to update.
    if (target.isMounted()) target.getComponentSpace().sync();
    
    // Get the Action again, just in case this is an Action Property that has been updated.
    action = target.getAction(action.getName());
        
    // check for confirm
    if (Flags.isConfirmRequired(target, action))
    {
      //no user is available for confirmation
      if (Sys.isStation())
      {
        return null;
      }

      String title = target.getDisplayName(action, null);
      Object[] args = new Object[] { target.getDisplayName(action, null) };
      String msg = UiLexicon.bajaui().getText("invoke.confirm", args);
      if (BDialog.confirm(getShell(), title, msg) != BDialog.YES)
        return null;
    }

    // if an arg wasn't specified in the constructor
    BValue arg = actionArg;
    if (arg == null) 
    {
      // prompt for the argument (we need to use code
      // in workbench which we access via reflection)
      arg = target.getActionParameterDefault(action);
      if (arg != null)
      {
        //no user is available for choosing an action parameter
        if (Sys.isStation())
        {
          return null;
        }

        while(true)
        {
          BFacets facets = target.getSlotFacets(action);
          String title = target.getDisplayName(action, null);
          
          try
          {             
            // use reflection on workbench:BWbFieldEditor                
            Class<?> cls = Sys.loadClass("workbench", "javax.baja.workbench.fieldeditor.BWbFieldEditor");
            Method method = cls.getMethod("dialog", new Class<?>[] { BWidget.class, String.class, BObject.class, Context.class });
            arg = (BValue)method.invoke(null, new Object[] { getShell(), title, arg, facets} );
            if (arg == null) return null;
            break;
          }
          catch(Throwable e)
          {          
            if (e instanceof InvocationTargetException)
              e = ((InvocationTargetException)e).getTargetException();
                      
            String err = UiLexicon.bajaui().getText("invoke.input.error");
            if (e.getClass().getName().equals("javax.baja.workbench.CannotSaveException"))
            {
              BDialog.error(getShell(), title, err + "  " + e.getMessage(), e);
            }
            else
            {
              BDialog.error(getShell(), title, err, e);
              break;
            }
          }        
        }      
      }
    }
        
    // invoke the action
    target.invoke(action, arg, null);
      
    // not undoable
    return null;
  }
  
  private BComponent target;
  private Action action;
  private BValue actionArg;
  
}

