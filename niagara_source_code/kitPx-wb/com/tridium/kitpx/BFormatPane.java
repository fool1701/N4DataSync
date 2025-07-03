/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitpx;

import java.util.ArrayList;
import java.util.List;

import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BNullWidget;
import javax.baja.ui.BWidget;
import javax.baja.ui.pane.BPane;
import javax.baja.util.BFormat;

/**
 * Format pane
 *
 * Processes all child labels 'text' Properties through BFormat and updates them.
 * This is useful if you're translating a Px page with lots of lexicon references as it
 * avoids the need to create lots of bindings.
 * 
 * @author    Gareth Johnson
 * @creation  20 Aug 2007
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Widget content to display inside border.
 */
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
public final class BFormatPane extends BPane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BFormatPane(3371639444)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * Widget content to display inside border.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * Widget content to display inside border.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * Widget content to display inside border.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFormatPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
 

  /**
   * Called to start the component
   */
  public void started() throws Exception
  {
    super.started();  
    
    // If we are not running in Px editor mode then process the widgets
    if (!isDesignTime())
    {     
      processWidgets(null);  
    }
  } 
  
  /**
   * Process the widgets
   */
  public void processWidgets(Context cx)
  {
    //
    // Find all of the children
    //
    
    List<BLabel> labels = new ArrayList<>();
    findLabels(this, labels);
    
    //
    // Adjust all of the text on the children
    //

    for (BLabel label : labels)
    {
      label.setText(BFormat.format(label.getText(), this, cx));
    }
  } 
  
  /**
   * Recursive method to find all of the labels
   */
  private void findLabels(BWidget w, List<BLabel> a)
  {
    BWidget[] widgets = w.getChildren(BWidget.class);
    
    for(int i=0; i < widgets.length; ++i)
    {
      if (widgets[i] instanceof BLabel)
        a.add((BLabel) widgets[i]);
      
      findLabels(widgets[i], a);
    }
  }
 
////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
  {
    BWidget c = getContent(); 
    c.computePreferredSize();
    setPreferredSize(c.getPreferredWidth(), c.getPreferredHeight()); 
  }

  public void doLayout(BWidget[] kids)
  {
    double w = getWidth(), h = getHeight();
    BWidget c = getContent(); 
    c.setBounds(0,0,w,h);    
  }  
  
  public void paint(Graphics g)
  {               
    if (isVisible() && getContent().isVisible())
      paintChild(g, getContent());
  }
}
