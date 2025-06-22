/*
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.pane;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;

/**
 * BLabelPane is a pane which contains a label widget and a 
 * content widget.  BLabelPane is designed to be used inside 
 * BLabelPaneContainers which layout corresponding label and 
 * widgets.  The BLabelPane has no layout implementation 
 * itself, but relies on its parent BLabelPaneContainer to 
 * perform its layout.
 *
 * @author    Brian Frank
 * @creation  5 Dec 00
 * @version   $Revision: 19$ $Date: 3/28/05 10:32:28 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 The label which describes the content.
 */
@NiagaraProperty(
  name = "label",
  type = "BLabel",
  defaultValue = "new BLabel(\"no label\")"
)
/*
 The widget for the content content.
 */
@NiagaraProperty(
  name = "content",
  type = "BWidget",
  defaultValue = "new BNullWidget()"
)
public class BLabelPane
  extends BPane
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.pane.BLabelPane(3798407970)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "label"

  /**
   * Slot for the {@code label} property.
   * The label which describes the content.
   * @see #getLabel
   * @see #setLabel
   */
  public static final Property label = newProperty(0, new BLabel("no label"), null);

  /**
   * Get the {@code label} property.
   * The label which describes the content.
   * @see #label
   */
  public BLabel getLabel() { return (BLabel)get(label); }

  /**
   * Set the {@code label} property.
   * The label which describes the content.
   * @see #label
   */
  public void setLabel(BLabel v) { set(label, v, null); }

  //endregion Property "label"

  //region Property "content"

  /**
   * Slot for the {@code content} property.
   * The widget for the content content.
   * @see #getContent
   * @see #setContent
   */
  public static final Property content = newProperty(0, new BNullWidget(), null);

  /**
   * Get the {@code content} property.
   * The widget for the content content.
   * @see #content
   */
  public BWidget getContent() { return (BWidget)get(content); }

  /**
   * Set the {@code content} property.
   * The widget for the content content.
   * @see #content
   */
  public void setContent(BWidget v) { set(content, v, null); }

  //endregion Property "content"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLabelPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Constructor with label and content widget.
   */
  public BLabelPane(BLabel label, BWidget widget)
  {
    setLabel(label);
    setContent(widget);
  }

  /**
   * Constructor with label text and content widget.
   */
  public BLabelPane(String label, BWidget widget)
  {
    getLabel().setText(label);
    setContent(widget);
  }

  /**
   * Constructor with label text and image and content widget.
   */
  public BLabelPane(String label, BImage image, BWidget widget)
  {
    getLabel().setText(label);
    if (image != null) getLabel().setImage(image);
    setContent(widget);
  }

  /**
   * No argument constructor.
   */
  public BLabelPane()
  {
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////
  
  public void doLayout(BWidget[] kids) {}

////////////////////////////////////////////////////////////////
// BComponent
////////////////////////////////////////////////////////////////

  /**
   * Only BLabelPaneContainer is a valid parent.
   */
  public boolean isParentLegal(BComponent parent)
  {
    if (!super.isParentLegal(parent)) return false;
    return (parent instanceof BLabelPaneContainer);
  }
  
////////////////////////////////////////////////////////////////
//BComponent
////////////////////////////////////////////////////////////////

  public void setEnabled(boolean enabled)
  {
    super.setEnabled(enabled);
    getLabel().setEnabled(enabled);
  }

}
