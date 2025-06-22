/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.sys.Property;
import javax.baja.ui.BAccelerator;
import javax.baja.ui.Command;
import javax.baja.ui.text.BTextEditor;
import javax.baja.ui.util.UiLexicon;

/**
 * TextEditorCommand provides a common base class for
 * the standard BTextEditor commands.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 9$ $Date: 11/4/10 1:42:28 PM EDT$
 * @since     Baja 1.0
 */
public abstract class TextEditorCommand
  extends Command
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public TextEditorCommand(BTextEditor editor, String keyName)
  {
    super(editor, UiLexicon.bajaui().module, "commands."+keyName);
    this.editor = editor;
  }  

  public TextEditorCommand(BTextEditor editor, Property keyBinding)
  {
    this(editor, keyBinding.getName());
    this.accelerator = (BAccelerator)editor.getOptions().getKeyBindings().get(keyBinding);
  }  

////////////////////////////////////////////////////////////////
// Command
////////////////////////////////////////////////////////////////

  /**
   * Override getAccelerator to use the ones from 
   * the BTextEditor's configured options.
   */
  public BAccelerator getAccelerator() 
  { 
    return accelerator; 
  }

  /**
   * Return false.
   */
  protected boolean isBusyForInvoke()
  {
    return false;
  }  
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  BTextEditor editor;
  BAccelerator accelerator;
  
  //Issue 11106, Attempt to lock dialog creation. Return true
  //if you should create a dialog. Return false if
  //you should not. 
  protected boolean tryLockModal()
  {
    if (!dialogOpen)
    {
      synchronized(dialogLock)
      {
        if (!dialogOpen)
        {
          dialogOpen = true;
          return true;
        }
      }
    }
    
    return false;
  }
  
  //Free the dialog lock so others can create dialogs
  protected void unlockModal()
  {
    if (dialogOpen)
    {
      synchronized(dialogLock)
      {
        if (dialogOpen)
        {
          dialogOpen = false;
        }
      }
    }
  }
  
  //Enforce modal behavior of dialogs in children
  protected static volatile boolean dialogOpen = false;
  protected static Object dialogLock = new Object();
}

