/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import java.util.HashMap;
import java.util.Map;

import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BStatusBoolean;
import javax.baja.status.BStatusEnum;
import javax.baja.sys.Action;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BValue;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BAbstractButton;
import javax.baja.ui.BBinding;
import javax.baja.ui.BButton;
import javax.baja.ui.BRadioButton;
import javax.baja.ui.BToggleButton;
import javax.baja.ui.BValueBinding;
import javax.baja.ui.BWidget;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.ToggleCommandGroup;

import com.tridium.util.CompUtil;

/**
 * BButtonGroupBinding.
 *
 * @author    Andy Frank       
 * @creation  14 May 07
 * @version   $Revision$ $Date$
 * @since     Niagara 3.2
 */
@NiagaraType
/*
 The button style to use.
 */
@NiagaraProperty(
  name = "style",
  type = "BButtonGroupStyle",
  defaultValue = "BButtonGroupStyle.radio"
)
public class BButtonGroupBinding
  extends BBinding
{                          
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BButtonGroupBinding(3327426012)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "style"

  /**
   * Slot for the {@code style} property.
   * The button style to use.
   * @see #getStyle
   * @see #setStyle
   */
  public static final Property style = newProperty(0, BButtonGroupStyle.radio, null);

  /**
   * Get the {@code style} property.
   * The button style to use.
   * @see #style
   */
  public BButtonGroupStyle getStyle() { return (BButtonGroupStyle)get(style); }

  /**
   * Set the {@code style} property.
   * The button style to use.
   * @see #style
   */
  public void setStyle(BButtonGroupStyle v) { set(style, v, null); }

  //endregion Property "style"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BButtonGroupBinding.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  

////////////////////////////////////////////////////////////////
// BBinding
////////////////////////////////////////////////////////////////

  @Override
  public boolean isDegraded()
  {
    return !isBound() || !getTarget().canInvoke();
  }

  public void targetChanged()
  {               
    if (cmds == null) init();
    
    String type = get().getType().toString();
    if (type.equals("control:BooleanWritable")) booleanChanged();
    else if (type.equals("control:EnumWritable")) enumChanged();
    
    super.targetChanged();
  }  
          
  private void init()
  {
    String type = get().getType().toString();
    if (type.equals("control:BooleanWritable")) booleanInit();
    else if (type.equals("control:EnumWritable")) enumInit();
    else System.out.println("Unknown type: " + type);
  }          
          
////////////////////////////////////////////////////////////////
// Boolean
////////////////////////////////////////////////////////////////

  private void booleanInit()
  {
    BWidget w = getWidget();
    final OrdTarget target = getTarget();
    final BComponent c = target.get().asComponent();
    final Action action = c.getAction("set");

    String trueText  = "true";
    String falseText = "false";    
    BFacets facets = (BFacets)c.get("facets");
    if (facets != null)
    {
      String s = facets.gets(BFacets.TRUE_TEXT, null);
      if (s != null) trueText = s;
      
      s = facets.gets(BFacets.FALSE_TEXT, null);
      if (s != null) falseText = s;
    }
    
    cmds = new ToggleCommand[2];    
    cmds[0] = new ToggleCommand(w, trueText) {
        public CommandArtifact doInvoke() 
        {
          if (isSelected()) c.invoke(action, BBoolean.TRUE, target);
          return null;
        }};

    cmds[1] = new ToggleCommand(w, falseText) {
        public CommandArtifact doInvoke() 
        {
          if (isSelected()) c.invoke(action, BBoolean.FALSE, target);
          return null;
        }};

    ToggleCommandGroup<ToggleCommand> g = new ToggleCommandGroup<>();
    g.add(cmds[0]);
    g.add(cmds[1]);

    BToggleButton trueButton = makeButton(cmds[0]);
    BToggleButton falseButton = makeButton(cmds[1]);

    w.add(null, trueButton, Flags.TRANSIENT);
    w.add(null, falseButton, Flags.TRANSIENT);
  }

  private void booleanChanged()
  {
    BStatusBoolean sb = (BStatusBoolean)get().asComponent().get("out");    
    if (sb.getValue()) cmds[0].setSelected(true);
    else cmds[1].setSelected(true);
  }

////////////////////////////////////////////////////////////////
// Enum
////////////////////////////////////////////////////////////////

  private void enumInit()
  {
    BWidget w = getWidget();
    final OrdTarget target = getTarget();
    final BComponent c = target.get().asComponent();
    final Action action = c.getAction("set");

    BDynamicEnum enums = ((BStatusEnum)c.get("out")).getValue();
    BEnumRange range = enums.getRange();
    BFacets facets = (BFacets)c.get("facets");

    // if range specified via context, then that
    // trumps range on enum itself
    if (facets != null)
    {
      BEnumRange r = (BEnumRange)facets.getFacet(BFacets.RANGE);
      if (r != null) range = r;
    }

    int[] ords = range.getOrdinals();
    cmds = new ToggleCommand[ords.length];
    ToggleCommandGroup<ToggleCommand> g = new ToggleCommandGroup<>();
    // Create a HashMap to store the index of each ordinal in the cmds array
    ordinalToCmdMap = new HashMap<Integer, Integer>();
    BAbstractButton[] children = w.getChildren(BAbstractButton.class);
    // Remove all the buttons from the widget, add it later based on the Enum
    for (int j = 0; j < children.length; j++)
    {
      w.remove(children[j]);
    }

    for (int i = 0; i < ords.length; i++)
    {
      final int ord = ords[i];
      final BEnumRange r = range;
      String displayName = range.getDisplayTag(ord, null);

      cmds[i] = new ToggleCommand(w, displayName)
      {
        public CommandArtifact doInvoke()
        {
          if (isSelected())
          {
            BValue val = BDynamicEnum.make(ord, r);
            c.invoke(action, val, target);
          }
          return null;
        }
      };

      g.add(cmds[i]);
      BToggleButton button, xButton = null;
      button = makeButton(cmds[i]);
      if (children.length > 0)
      {
        xButton = (BToggleButton) children[i];
        //Copy the properties only if enum and existing button text matches
        if (xButton != null && xButton.getText().equals(displayName))
        {
          CompUtil.copyProperties(button, xButton, null);
          button.setText(displayName);
        }
      }

      w.add(null, button, 0);
      ordinalToCmdMap.put(Integer.valueOf(ord), Integer.valueOf(i));
    }
  }

  private void enumChanged()
  {
    BDynamicEnum enums = ((BStatusEnum)get().asComponent().get("out")).getValue();
    // Get the cmds[] index that corresponds to the new ordinal 
    // Check to make sure that map exists and contains the value
    // if not, just ignore the update
    if (ordinalToCmdMap != null)
    {
      Integer idx = ordinalToCmdMap.get(Integer.valueOf(enums.getOrdinal()));
      if (idx != null)
        cmds[idx.intValue()].setSelected(true);
    }
  }

////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////
  
  private BToggleButton makeButton(ToggleCommand c)
  {
    if (getStyle() == BButtonGroupStyle.radio)
    {
      return new BRadioButton(c);
    }
    else
    {
      return new BToggleButton(c);
    }
  }  

////////////////////////////////////////////////////////////////
// Fields
////////////////////////////////////////////////////////////////

  private ToggleCommand[] cmds = null;
  // Maps ordinals to an index in the cmds array
  // Necessary for enums that aren't of the form [0,x]
  Map<Integer, Integer> ordinalToCmdMap = null;
}
