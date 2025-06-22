/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text.commands;

import javax.baja.ui.*;
import javax.baja.ui.text.*;

/**
 * InsertText inserts specified text at the caret position.
 *
 * @author    Brian Frank
 * @creation  7 Aug 01
 * @version   $Revision: 2$ $Date: 3/28/05 10:32:37 AM EST$
 * @since     Baja 1.0
 */
public class InsertText
  extends EditCommand
{ 

  public InsertText(BTextEditor widget, String text)
  {
    this(widget, text.toCharArray());
  }  

  public InsertText(BTextEditor widget, char[] text)
  {
    super(widget, BKeyBindings.paste);
    this.text = text;
  }  

  public CommandArtifact doInvoke()
  {
    return insert(text);
  }
  
  private char[] text;
  
}

