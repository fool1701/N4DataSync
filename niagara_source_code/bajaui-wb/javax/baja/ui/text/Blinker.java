/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.text;

import javax.baja.ui.*;

/**
 * Blinker is a thread responsible for blinking
 * the caret cursor.
 *
 * @author    Brian Frank       
 * @creation  9 Dec 00
 * @version   $Revision: 10$ $Date: 3/28/05 10:32:33 AM EST$
 * @since     Baja 1.0
 */
class Blinker
  extends Thread
{ 

  static synchronized void startBlinking(BTextEditor widget)
  {
    stopBlinking();
    blinker = new Blinker(widget);
    blinker.start();
  }

  static synchronized void stopBlinking()
  {
    if (blinker != null)
    {
      blinker.isAlive = false;
      blinker.widget = null;
      blinker = null;
    }
  }
  
  static synchronized void pauseBlinking()
  {
    if (blinker != null) blinker.interrupt();
  }
  
  static BWidget getBlinkingWidget()
  {
    Blinker b = blinker;
    if (b == null) return null;
    return b.widget; 
  }
  
  private Blinker(BTextEditor widget)
  {
    super("Ui:CaretBlinker");
    this.widget = widget;
    this.isAlive = true;
  }
  
  public void run()
  {
    while(isAlive)
    {
      try
      {
        visible = !visible;
        widget.blinkCaret(visible);
        sleep(500);
      }
      catch(InterruptedException e)
      {
        try
        {
          if (widget != null) widget.blinkCaret(true);
          visible = false;
        }
        catch(Exception e2)
        {
          e2.printStackTrace();
        }
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  private static Blinker blinker;
  
  private BTextEditor widget;
  private boolean isAlive;
  private boolean visible;
  
}
