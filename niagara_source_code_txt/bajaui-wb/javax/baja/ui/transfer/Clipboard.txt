/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.transfer;

import com.tridium.ui.*;

/**
 * Clipboard provides access to the system's clipboard for
 * cut, copy, paste operations.
 *
 * @author    Brian Frank       
 * @creation  6 Mar 02
 * @version   $Revision: 5$ $Date: 3/10/11 2:57:09 PM EST$
 * @since     Baja 1.0
 */
public final class Clipboard
{       

  /**
   * Private constructor.
   */
  private Clipboard(UiEnv.ClipboardManager manager)
  {
    this.manager = manager;
  }

  /**
   * Get the default system clipboard.
   */
  public static Clipboard getDefault()
  {
    return defaultClipboard;
  }

  /**
   * Get the current clipboard contents, or null if empty.
   */
  public TransferEnvelope getContents()
  {
    try
    {
      return manager.getContents();
    } catch (javax.baja.ui.transfer.UnsupportedFormatException usfe) {
      return null;
    }
  }
  
  /**
   * Set the current clipboard contents.
   */
  public void setContents(TransferEnvelope data)
  {
    manager.setContents(data);
  }
  
  private static Clipboard defaultClipboard = new Clipboard(UiEnv.get().makeDefaultClipboardManager());
  
  private UiEnv.ClipboardManager manager;
}
