/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.transfer;

import javax.baja.sys.*;
import javax.baja.space.*;
import javax.baja.gx.*;
import com.tridium.ui.theme.*;

/**
 * SimpleDragRenderer is an implementation of 
 * DragRenderer which paints a list of icon/String 
 * pairs as the drag effect.
 *
 * @author    Brian Frank       
 * @creation  7 Mar 02
 * @version   $Revision: 13$ $Date: 3/28/05 10:32:40 AM EST$
 * @since     Baja 1.0
 */
public class SimpleDragRenderer
  implements DragRenderer
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Use the icon and display name of the specified BComponent.
   */
  public SimpleDragRenderer(BComponent value)
  {
    this(new BComponent[] { value });
  }

  /**
   * Use the icon and display name of each of the 
   * specified BComponents.
   */
  public SimpleDragRenderer(BComponent[] values)
  {
    BImage[] icons = new BImage[values.length];
    String[] text = new String[values.length];
    for(int i=0; i<values.length; ++i)
    {
      icons[i] = BImage.make(values[i].getIcon());
      text[i] = values[i].getDisplayName(null);
    }
    
    init(icons, text);
  }

  /**
   * Construct with an array of icon/text pairs.
   */
  public SimpleDragRenderer(BImage[] icons, String[] text)
  {
    init(icons, text);
  }

  /**
   * Construct with an array of text.
   */
  public SimpleDragRenderer(String[] text)
  {
    init(null, text);
  }

  /**
   * Construct with a single icon/name pair.
   */
  public SimpleDragRenderer(BImage icon, String text)
  {
    init(new BImage[] { icon }, new String[] { text });
  }

  /**
   * Construct with a single text.
   */
  public SimpleDragRenderer(String text)
  {
    init(null, new String[] { text });
  }

  /**
   * Construct with a mark.
   */
  public SimpleDragRenderer(Mark mark)
  {
    String[] text    = mark.getNames();
    BObject[] values = mark.getValues();
    BImage[] icons   = new BImage[values.length];
    for(int i=0; i<values.length; ++i)
      icons[i] = BImage.make(values[i].getIcon());
    init(icons, text);
  }
  
  /**
   * Initialization.
   */
  private void init(BImage[] icons, String[] text)
  {
    this.icons = icons;
    this.text  = text;
  }

////////////////////////////////////////////////////////////////
// DragRenderer
////////////////////////////////////////////////////////////////

  public RectGeom getDragEffectRectGeom()
  {
    if (rect == null)
    {
      double h = 16 * text.length;
      double w = 0;
      for(int i=0; i<text.length; ++i)
      {
        double linew = 0;
        if (icons != null) linew = 20;
        linew += font.width(text[i]);
        w = Math.max(w, linew);
      }
      rect = new RectGeom(xCursorOffset, yCursorOffset, w, h);
    }
    return new RectGeom(rect);
  }
  
  /**
   * Paint the drag effect.
   */    
  public void paintDragEffect(Graphics g)
  {
    g.translate(xCursorOffset, yCursorOffset);
    
    g.setFont(font);
    g.setBrush(BColor.make(100,100,100));
    
    double fh = font.getHeight();
    
    double y = 0;
    for(int i=0; i<text.length; ++i)
    {
      double tx = 0;
      
      BImage icon = null;
      if (icons != null) icon = icons[i];
      if (icon != null && !icon.isNull())
      {
        g.drawImage(icon.getDisabledImage(), 0, y);
        tx = 20;
      }
      
      String text = this.text[i];
      g.drawString(text, tx, y+fh-2);
      y += 16;
    }

    g.translate(-xCursorOffset, -yCursorOffset);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  /** This is used to translate the graphics context during paint */
  public double xCursorOffset = 0;
  
  /** This is used to translate the graphics context during paint */
  public double yCursorOffset = 0;
  
  /** Array of "disabled" images passed to constructor */
  public BImage[] icons;
  
  /** Array of text passed to constructor */
  public String[] text;
  
  /** Font to paint text with */
  public BFont font = Theme.widget().getTextFont();
  
  private RectGeom rect;
}
