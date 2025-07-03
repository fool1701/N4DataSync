/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BConstrainedPane is a pane with a constrained size.  It allows the
 * pane to be restricted by minimum width, maximum width, minimum height,
 * and maximum height.
 *
 * @author    John Sublett
 * @creation  26 Apr 2003
 * @version   $Revision: 9$ $Date: 7/13/11 3:13:19 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The content of the pane.
 */
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
/*
 The smallest size that the pane will return as its
 preferred size.
 */
@NiagaraProperty(
  name = "minSize",
  type = "BSize",
  defaultValue = "BSize.make(0, 0)"
)
/*
 The largest size that the pane will return as its
 preferred size.
 */
@NiagaraProperty(
  name = "maxSize",
  type = "BSize",
  defaultValue = "BSize.make(Integer.MAX_VALUE, Integer.MAX_VALUE)"
)
public class BConstrainedPane
  extends BPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BConstrainedPane(1236571090)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * The content of the pane.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * The content of the pane.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * The content of the pane.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Property "minSize"

  /**
   * Slot for the {@code minSize} property.
   * The smallest size that the pane will return as its
   * preferred size.
   * @see #getMinSize
   * @see #setMinSize
   */
  public static final Property minSize = newProperty(0, BSize.make(0, 0), null);

  /**
   * Get the {@code minSize} property.
   * The smallest size that the pane will return as its
   * preferred size.
   * @see #minSize
   */
  public BSize getMinSize() { return (BSize)get(minSize); }

  /**
   * Set the {@code minSize} property.
   * The smallest size that the pane will return as its
   * preferred size.
   * @see #minSize
   */
  public void setMinSize(BSize v) { set(minSize, v, null); }

  //endregion Property "minSize"

  //region Property "maxSize"

  /**
   * Slot for the {@code maxSize} property.
   * The largest size that the pane will return as its
   * preferred size.
   * @see #getMaxSize
   * @see #setMaxSize
   */
  public static final Property maxSize = newProperty(0, BSize.make(Integer.MAX_VALUE, Integer.MAX_VALUE), null);

  /**
   * Get the {@code maxSize} property.
   * The largest size that the pane will return as its
   * preferred size.
   * @see #maxSize
   */
  public BSize getMaxSize() { return (BSize)get(maxSize); }

  /**
   * Set the {@code maxSize} property.
   * The largest size that the pane will return as its
   * preferred size.
   * @see #maxSize
   */
  public void setMaxSize(BSize v) { set(maxSize, v, null); }

  //endregion Property "maxSize"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BConstrainedPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////


  /**
   * Default constructor.
   */
  public BConstrainedPane()
  {
  }

  /**
   * Constructor with content.
   *
   * @param content The widget that will occupy the entire pane.
   */
  public BConstrainedPane(BWidget content)
  {
    setContent(content);
  }

  /**
   * Constructor with content and max width and height.
   *
   * @param content The widget that will occupy the entire pane.
   * @since Niagara 3.7
   */
  public BConstrainedPane(BWidget content, double maxWidth, double maxHeight)
  {
    setContent(content);
    setMaxSize(maxWidth, maxHeight);
  }
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get <code>minSize.width</code>
   */
  public double getMinWidth() 
  {
    return getMinSize().width();
  }

  /**
   * Get <code>minSize.height</code>
   */
  public double getMinHeight() 
  {
    return getMinSize().height();
  }

  /**
   * Get <code>maxSize.width</code>
   */
  public double getMaxWidth() 
  {
    return getMaxSize().width();
  }

  /**
   * Get <code>maxSize.height</code>
   */
  public double getMaxHeight() 
  {
    return getMaxSize().height();
  }                                    
  
  /**
   * Set the minSize property.
   */
  public void setMinSize(double w, double h)
  {
    setMinSize(BSize.make(w, h));
  }

  /**
   * Set the maxSize property.
   */
  public void setMaxSize(double w, double h)
  {
    setMaxSize(BSize.make(w, h));
  }

  /**
   * Set <code>minSize.width</code>.
   */
  public void setMinWidth(double w) 
  {
    setMinSize(BSize.make(w, getMinHeight()));
  }

  /**
   * Set <code>minSize.height</code>.
   */
  public void setMinHeight(double h) 
  {
    setMinSize(BSize.make(getMinWidth(), h));
  }

  /**
   * Set <code>maxSize.width</code>.
   */
  public void setMaxWidth(double w) 
  {
    setMaxSize(BSize.make(w, getMaxHeight()));
  }

  /**
   * Set <code>maxSize.height</code>.
   */
  public void setMaxHeight(double h) 
  {
    setMaxSize(BSize.make(getMaxWidth(), h));
  }                         
  
  /**
   * Set both minSize and maxSize
   */
  public void setFixedSize(double w, double h)
  {                             
    setFixedSize(BSize.make(w, h));
  }

  /**
   * Set both minSize and maxSize
   */
  public void setFixedSize(BSize size)
  {                             
    setMinSize(size);
    setMaxSize(size);
  }
  
////////////////////////////////////////////////////////////////
// Widget
////////////////////////////////////////////////////////////////

  /**
   * Compute the preferred size of the pane accounting for the
   * configured minimum and maximum dimensions.
   */
  public void computePreferredSize()
  {
    double w;
    double h;
    
    BWidget content = getContent();
    if (content.isNull())
    {
      w = getMinWidth();
      h = getMinHeight();
    }
    else
    {
      content.computePreferredSize();

      w = content.getPreferredWidth();
      w = Math.max(w, getMinWidth());
      w = Math.min(w, getMaxWidth());
      
      h = content.getPreferredHeight();
      h = Math.max(h, getMinHeight());
      h = Math.min(h, getMaxHeight());
    }
    
    setPreferredSize(w, h);
  }

  /**
   * Layout the pane.
   */
  public void doLayout(BWidget[] kids)
  {
    BWidget content = getContent();
    if (content.isNull())
    {
      content.setBounds(0,0,0,0);
    }
    else
    {
      content.setBounds(0, 0, getWidth(), getHeight());
    }
  }

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/constrainedPane.png");  

}
