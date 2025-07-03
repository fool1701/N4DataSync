/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import java.text.NumberFormat;

import javax.baja.gx.BInsets;
import javax.baja.gx.IInsets;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Topic;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BSlider;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.event.BSliderEvent;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.util.Lexicon;

import com.tridium.ui.theme.Theme;

/**
 * BSlider provides a visual slider which is used to
 * select and integer or doubleing point value between
 * a fixed range.
 *
 * @author    Bill Smith
 * @creation  16 Jun 2008
 * @version   $Revision$ $Date: 6/17/2008 8:56:41 PM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Event fired when the position is modified.
 */
@NiagaraTopic(
  name = "valueModified",
  eventType = "BSliderEvent"
)
/*
 Event fired when the mouse is released after dragging slider.
 */
@NiagaraTopic(
  name = "actionPerformed",
  eventType = "BSliderEvent"
)
public class BTouchSlider
  extends BEdgePane
{


//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BTouchSlider(4037087129)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Topic "valueModified"

  /**
   * Slot for the {@code valueModified} topic.
   * Event fired when the position is modified.
   * @see #fireValueModified
   */
  public static final Topic valueModified = newTopic(0, null);

  /**
   * Fire an event for the {@code valueModified} topic.
   * Event fired when the position is modified.
   * @see #valueModified
   */
  public void fireValueModified(BSliderEvent event) { fire(valueModified, event, null); }

  //endregion Topic "valueModified"

  //region Topic "actionPerformed"

  /**
   * Slot for the {@code actionPerformed} topic.
   * Event fired when the mouse is released after dragging slider.
   * @see #fireActionPerformed
   */
  public static final Topic actionPerformed = newTopic(0, null);

  /**
   * Fire an event for the {@code actionPerformed} topic.
   * Event fired when the mouse is released after dragging slider.
   * @see #actionPerformed
   */
  public void fireActionPerformed(BSliderEvent event) { fire(actionPerformed, event, null); }

  //endregion Topic "actionPerformed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTouchSlider.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * No argument constructor.
   */
  public BTouchSlider()
  {
    this(0, 100, 1, 50);
  }

  /**
   * Sets the min, max, increment, initial and
   * orienation values.
   */
  public BTouchSlider(double min, double max, double inc, double val)
  {
    super();
    
    increment = new BButton(new IncrementCmd(inc));
    //increment.setPadding(BInsets.make(0));
    IInsets insets = Theme.button().getPadding(increment);
    increment.setPadding(BInsets.make(insets.top() * .7));
    slider = new BSlider(min, max, inc, val);
    decrement = new BButton(new DecrementCmd(-inc));
    decrement.setPadding(BInsets.make(insets.top() * .7));
    
    setLeft(decrement);
    setCenter(slider);
    setRight(increment);

    linkTo(null, slider, BSlider.valueModified, valueModified);
    linkTo(null, slider, BSlider.actionPerformed, actionPerformed);
  }

  public double getValue()
  {
    return slider.getValue(); 
  }

  public void setValue(double value)
  {
    slider.setValue(value); 
  }


////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////
  
  private String valueToString(double v)
  {
    NumberFormat format = NumberFormat.getInstance();
    format.setMaximumFractionDigits(3);
    return format.format(v);    
  }

////////////////////////////////////////////////////////////////
// Command Inner Classes
////////////////////////////////////////////////////////////////
    
  class IncrementCmd extends Command
  {
    public IncrementCmd(double increment) 
    { 
      super(BTouchSlider.this, Lexicon.make("bajaui"), "touchslider.increment");
      this.increment = increment;
    }

    public CommandArtifact doInvoke()
    {
      slider.setValue(slider.getValue() + this.increment);
      slider.fireValueModified(new BSliderEvent(BSliderEvent.VALUE_CHANGED, slider, slider.getValue()));
      slider.repaint();
      return null;
    }
    
    private double increment;
  }

  class DecrementCmd extends Command
  {
    public DecrementCmd(double increment) 
    { 
      super(BTouchSlider.this, Lexicon.make("bajaui"), "touchslider.decrement");
      this.increment = increment;
    }

    public CommandArtifact doInvoke()
    {
      slider.setValue(slider.getValue() + this.increment);
      slider.fireValueModified(new BSliderEvent(BSliderEvent.VALUE_CHANGED, slider, slider.getValue()));
      slider.repaint();

      return null;
    }
    
    private double increment;
  }
  
///////////////////////////////////////////////////////////
// Overrides
/////////////////////////////////////////////////////////// 

  /**
   * Get the icon.
   */
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("widgets/slider.png");
  private static final BIcon incIcon = BIcon.std("add.png");
  private static final BIcon decIcon = BIcon.std("subtract.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BButton decrement;
  private BSlider slider;
  private BButton increment;
}
