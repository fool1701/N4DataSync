/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.wizard;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.enums.*;

import com.tridium.ui.theme.*;

/**
 * BWizardHeader is a reusable widget which provides a standard
 * look and feel for wizard headers.  It is designed to be placed
 * along the top of dialogs.
 *
 * @author    Brian Frank
 * @creation  29 Jan 03
 * @version   $Revision: 11$ $Date: 3/23/05 11:34:56 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BWizardHeader
  extends BWidget
{    
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.wizard.BWizardHeader(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWizardHeader.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Constructor with icon, title, and description.
   */
  public BWizardHeader(BImage icon, String title, String description)
  {
    this.icon = new BLabel(icon);
    add("icon", this.icon);
    
    this.title = new BLabel(title, BHalign.left);
    this.title.setFont(Theme.widget().getLargeBoldFont());
    add("title", this.title);
    
    this.description = new BLabel(description, BHalign.left);
    add("description", this.description);
  }
  
  /**
   * Constructor with icon and title.
   */
  public BWizardHeader(BImage icon, String title)
  {
    this(icon, title, "");
  }

  /**
   * Default constructor.
   */
  public BWizardHeader()
  {
    this(null, "", "");
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////  
  
  /**
   * Get the title text.
   */
  public String getTitle()
  {
    return title.getText();
  }

  /**
   * Set the title text or null for no title.
   */
  public void setTitle(String title)
  {
    if (title == null) title = "";
    this.title.setText(title);
    relayout();
  }

  /**
   * Get the description text.
   */
  public String getDescription()
  {
    return description.getText();
  }

  /**
   * Set the description text or null for no description
   */
  public void setDescription(String description)
  {
    if (description == null) description = "";
    this.description.setText(description);
    relayout();
  }
  
  /**
   * Get the icon image.  The icon should be a 32x32 icon.
   */
  public BImage getImage()
  {
    return icon.getImage();
  }

  /**
   * Set the icon image.  The icon should be a 32x32 icon.
   */
  public void setIcon(BImage image)
  {
    this.icon.setImage(image);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////  

  /**
   * Preferred size.
   */
  public void computePreferredSize()
  {
    icon.computePreferredSize();
    double iw = icon.getPreferredWidth();
    double ih = icon.getPreferredHeight();
    
    boolean titleVisible = !title.getText().equals("");
    title.computePreferredSize();
    double tw = title.getPreferredWidth();
    double th = title.getPreferredHeight();
    if (!titleVisible) tw = th = 0;
    
    boolean descriptionVisible = !description.getText().equals("");
    description.computePreferredSize();
    double dw = description.getPreferredWidth();
    double dh = description.getPreferredHeight();
    if (!descriptionVisible) dw = dh = 0;
    
    double w = margin + iw + margin + Math.max(tw, dw) + margin;
    double h = margin + Math.max(ih, th + dh) + margin;
    setPreferredSize(w, h);
  }

  /**
   * Do layout.
   */
  public void doLayout(BWidget[] kids)
  {
    double h = getHeight();
    
    icon.computePreferredSize();
    double iw = icon.getPreferredWidth();
    double ih = icon.getPreferredHeight();
    double ix = margin;
    double iy = (h-ih)/2;
    
    boolean titleVisible = !title.getText().equals("");
    title.computePreferredSize();
    double tw = title.getPreferredWidth();
    double th = title.getPreferredHeight();
    if (!titleVisible) tw = th = 0;
    
    boolean descriptionVisible = !description.getText().equals("");
    description.computePreferredSize();
    double dw = description.getPreferredWidth();
    double dh = description.getPreferredHeight();
    if (!descriptionVisible) dw = dh = 0;
    
    double tx = ix + iw + margin;
    double dx = tx;
    
    double ty = 0, dy = 0;
    if (titleVisible)
    {
      if (descriptionVisible) 
      {
        double tdh = th + dh;
        ty = (h-tdh)/2;
        dy = ty + th;
      }
      else 
      {
        ty = (h-th)/2;
      }
    }
    else
    {
      if (descriptionVisible) dy = (h-dh)/2;
    }
    
    icon.setBounds(ix, iy, iw, ih);
    title.setBounds(tx, ty, tw, th);
    description.setBounds(dx, dy, dw, dh);
  }
  
  /**
   * Paint
   */
  public void paint(Graphics g)
  {
    double w = getWidth();
    double h = getHeight();
    
    g.setBrush(Theme.widget().getControlHeader());
    g.fillRect(0, 0, w, h-1);
    
    paintChildren(g);
    
    g.setBrush(BColor.black);
    g.strokeLine(0, h-1, w, h-1);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static final double margin = 5;

  private BLabel title;
  private BLabel description;
  private BLabel icon;
  
}
