/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.util;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.event.*;

/**
 * BAuxWidget exposes the painting and mouse events
 * as supports, so its easy to create new widgets
 * w/o having to add a new concrete type, because
 * BObjects as inner classes is not supported.
 *
 * @author    Andy Frank       
 * @creation  09 Sep 04
 * @version   $Revision: 3$$Date: 12/21/05 10:50:39 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BAuxWidget
  extends BWidget
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.util.BAuxWidget(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAuxWidget.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BAuxWidget()
  {
  }
  
  public BAuxWidget(Support s)
  {
    setSupport(s);
  }
  
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  public Support getSupport() { return support; }
  public void setSupport(Support s) { this.support = s; }  

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
  {
    BSize size = support.getPreferredSize();
    setPreferredSize(size.width, size.height);
  }

  public void animate()
  {                   
    super.animate();
    support.animate(this);
  }  
  
  public void paint(Graphics g)
  {
    support.paint(g, getWidth(), getHeight());
  }
    
  public void mousePressed(BMouseEvent event)  { support.mousePressed(event);  }
  public void mouseReleased(BMouseEvent event) { support.mouseReleased(event); }
  public void mouseEntered(BMouseEvent event)  { support.mouseEntered(event);  }
  public void mouseExited(BMouseEvent event)   { support.mouseExited(event);   }
  public void mouseMoved(BMouseEvent event)    { support.mouseMoved(event);    }
  public void mouseDragged(BMouseEvent event)  { support.mouseDragged(event);  }    
  public void mousePulsed(BMouseEvent event)   { support.mousePulsed(event);   }  
  
////////////////////////////////////////////////////////////////
// support
////////////////////////////////////////////////////////////////

  public static class Support
  {
    public BSize getPreferredSize() { return BSize.make(10,10); }
    public void paint(Graphics g, double width, double height) {}
    public void animate(BWidget w) {}
    
    public void mousePressed(BMouseEvent event)  {}
    public void mouseReleased(BMouseEvent event) {}
    public void mouseEntered(BMouseEvent event)  {}
    public void mouseExited(BMouseEvent event)   {}
    public void mouseMoved(BMouseEvent event)    {}
    public void mouseDragged(BMouseEvent event)  {}
    public void mousePulsed(BMouseEvent event)   {}    
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private Support support = new Support();
}
