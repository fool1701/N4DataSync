/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import javax.baja.naming.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.event.*;

/**
 * BHyperlinkLabel is a label which performs a hyperlink
 * when clicked.  It automatically changes the mouse
 * cursor to the standard link cursor. 
 *
 * @author    Brian Frank       
 * @creation  29 Sep 04
 * @version   $Revision: 3$ $Date: 8/30/07 2:17:31 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The ord to hyperlink.
 */
@NiagaraProperty(
  name = "ord",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
public class BHyperlinkLabel
  extends BLabel
{                      

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BHyperlinkLabel(1156712617)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "ord"

  /**
   * Slot for the {@code ord} property.
   * The ord to hyperlink.
   * @see #getOrd
   * @see #setOrd
   */
  public static final Property ord = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code ord} property.
   * The ord to hyperlink.
   * @see #ord
   */
  public BOrd getOrd() { return (BOrd)get(ord); }

  /**
   * Set the {@code ord} property.
   * The ord to hyperlink.
   * @see #ord
   */
  public void setOrd(BOrd v) { set(ord, v, null); }

  //endregion Property "ord"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHyperlinkLabel.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BHyperlinkLabel()
  {
  }
  
  public BHyperlinkLabel(String label, BOrd ord)
  {
    super(label);
    setOrd(ord);
  }

////////////////////////////////////////////////////////////////
// State
////////////////////////////////////////////////////////////////
    
  /**
   * Return true if the mouse is currently over the button.
   */
  public boolean isMouseOver()
  {
    return mouseOver;
  }
          
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////  
  
  /**
   * Return true.
   */
  public boolean receiveInputEvents()
  {
    return true;
  }  
    
  /**
   * Handle mousePressed event.
   */
  public void mousePressed(BMouseEvent event) 
  {                             
    BWidgetShell shell = getShell();
    BOrd ord = getOrd();
    
    if (mouseOver && !ord.isNull() && shell instanceof BIHyperlinkShell)
      ((BIHyperlinkShell)shell).hyperlink(new HyperlinkInfo(ord, event));
  }
    
  /**
   * Handle mouseEntered event.
   */
  public void mouseEntered(BMouseEvent event) 
  {
    BWidgetShell shell = getShell();
    BOrd ord = getOrd();
    
    if (shell != null && !ord.isNull()) 
    {
      shell.showStatus(ord.toString());      
      setMouseCursor(MouseCursor.hand);
    }
      
    mouseOver = true;
  }
  
  /**
   * Handle mouseExited event.
   */
  public void mouseExited(BMouseEvent event) 
  {
    BWidgetShell shell = getShell();
    if (shell != null) shell.showStatus(null);
    
    mouseOver = false;
    setMouseCursor(MouseCursor.normal);
  }

////////////////////////////////////////////////////////////////
// Presentation
////////////////////////////////////////////////////////////////

  
  /**
   * Get the icon.
   */
  public BIcon getIcon() { return getImage().isNull()?icon:imageIcon; }
  private static final BIcon icon = BIcon.std("widgets/hyperlinkLabel.png");
  private static final BIcon imageIcon = BIcon.std("widgets/image.png");
              
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private boolean mouseOver = false;
  
}
