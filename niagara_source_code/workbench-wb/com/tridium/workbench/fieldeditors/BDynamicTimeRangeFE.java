/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BFont;
import javax.baja.gx.BImage;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BIcon;
import javax.baja.sys.BLink;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BFrame;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.list.BList;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.util.Lexicon;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.bql.util.BDynamicTimeRange;
import com.tridium.bql.util.BDynamicTimeRangeType;
import com.tridium.ui.theme.Theme;

/**
 * BDynamicTimeRangeFE is a widget for viewing and editing a BDynamicTimeRange.
 * 
 * @author    John Sublett
 * @creation  22 Aug 2004
 * @version   $Revision: 8$ $Date: 7/15/10 2:51:08 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "bql:DynamicTimeRange"
  )
)
@NiagaraAction(
  name = "editTimeRange"
)
@NiagaraAction(
  name = "updateLayout"
)
public class BDynamicTimeRangeFE
  extends BWbFieldEditor
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BDynamicTimeRangeFE(3834878417)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "editTimeRange"

  /**
   * Slot for the {@code editTimeRange} action.
   * @see #editTimeRange()
   */
  public static final Action editTimeRange = newAction(0, null);

  /**
   * Invoke the {@code editTimeRange} action.
   * @see #editTimeRange
   */
  public void editTimeRange() { invoke(editTimeRange, null, null); }

  //endregion Action "editTimeRange"

  //region Action "updateLayout"

  /**
   * Slot for the {@code updateLayout} action.
   * @see #updateLayout()
   */
  public static final Action updateLayout = newAction(0, null);

  /**
   * Invoke the {@code updateLayout} action.
   * @see #updateLayout
   */
  public void updateLayout() { invoke(updateLayout, null, null); }

  //endregion Action "updateLayout"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDynamicTimeRangeFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  

  public BDynamicTimeRangeFE()
  {
    typeChoice = makeTypeChoice();
    add("typeChoice", typeChoice);
    
    timeRangeLabel = new BLabel();    
    add("rangeLabel", timeRangeLabel);
    
    add("typeLayout", new BLink(typeChoice, BListDropDown.listActionPerformed, updateLayout));
    add("typeMod", new BLink(typeChoice, BListDropDown.listActionPerformed, setModified));
    
    editButton = new BButton(BImage.make(BIcon.std("clock.png")));
    editButton.setButtonStyle(BButtonStyle.toolBar);
    add("editButton", editButton);
    add("editLink", new BLink(editButton, BButton.actionPerformed, editTimeRange));
  }

////////////////////////////////////////////////////////////////
// Load/Save
////////////////////////////////////////////////////////////////

  /**
   * Load the specified value into the editor.
   */
  public void doLoadValue(BObject value, Context cx)
  {
    BDynamicTimeRange range = (BDynamicTimeRange)value;
    typeChoice.setSelectedItem(range.getRangeType());

    if (range.getRangeType() == BDynamicTimeRangeType.timeRange)
    {
      startTime = range.getFixedStartTime();
      endTime = range.getFixedEndTime();
    }
    
    if (startTime == null) startTime = BAbsTime.NULL;
    if (endTime == null) endTime = BAbsTime.NULL;

    timeRangeLabel.setText(formatTimeRange(range.getFixedStartTime(), range.getFixedEndTime()));
    
    relayout();
  }

  /**
   * Save the current state of the editor.
   */
  public BObject doSaveValue(BObject value, Context cx)
    throws Exception, CannotSaveException
  {
    BDynamicTimeRangeType rangeType = (BDynamicTimeRangeType)typeChoice.getSelectedItem();
    
    if (rangeType != BDynamicTimeRangeType.timeRange)
      return BDynamicTimeRange.make(rangeType);
    else
      return BDynamicTimeRange.make(startTime, endTime);
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  /**
   * Compute the preferred size of the editor.
   */
  public void computePreferredSize()
  {
    double pw = 0;
    double ph = 0;

    typeChoice.computePreferredSize();
    editButton.computePreferredSize();
    BFont font = Theme.label().getTextFont();
        
    pw = typeChoice.getPreferredWidth();
    pw += HGAP + font.width(prefTemplate);
    pw += HGAP + editButton.getPreferredWidth();
    
    ph = typeChoice.getPreferredHeight();
    ph = Math.max(ph, font.getHeight());
    ph = Math.max(ph, editButton.getPreferredHeight());

    setPreferredSize(pw, ph);
  }

  /**
   * Layout the editor.
   */
  public void doLayout(BWidget[] kids)
  {
    double w = getWidth();
    double h = getHeight();

    typeChoice.computePreferredSize();
    timeRangeLabel.computePreferredSize();
    editButton.computePreferredSize();
    
    double x = 0;
    typeChoice.setBounds(x, (h - typeChoice.getPreferredHeight()) / 2,
                         typeChoice.getPreferredWidth(),
                         typeChoice.getPreferredHeight());
    x += typeChoice.getPreferredWidth() + HGAP;

    BDynamicTimeRangeType type = (BDynamicTimeRangeType)typeChoice.getSelectedItem();
    if (type == BDynamicTimeRangeType.timeRange)
    {
      timeRangeLabel.setBounds(x, (h - timeRangeLabel.getPreferredHeight()) / 2,
                               timeRangeLabel.getPreferredWidth(),
                               timeRangeLabel.getPreferredHeight());
      x += HGAP + timeRangeLabel.getPreferredWidth();

      editButton.setBounds(x, (h - editButton.getPreferredHeight()) / 2,
                           editButton.getPreferredWidth(),
                           editButton.getPreferredHeight());
      x += HGAP + editButton.getPreferredWidth();
    }
    else
    {
      timeRangeLabel.setBounds(0,0,0,0);
      editButton.setBounds(0,0,0,0);
    }
  }

  /**
   * Update the layout based on config changes.
   */
  public void doUpdateLayout()
  {
    relayout();
  }

  protected void doSetReadonly(boolean readonly)
  {
    super.setReadonly(readonly);
    typeChoice.setEnabled(!readonly);
    editButton.setEnabled(!readonly);
  }

  /**
   * Edit the time range and ensure endTime is after startTime
   */
  public void doEditTimeRange()
  {
    BAbsTime tempStartTime = startTime;
    BAbsTime tempEndTime = endTime;
    while (true)
    {
      BTimeEditors eds = new BTimeEditors(tempStartTime, tempEndTime, getCurrentContext());
      int rc = BDialog.open(this, lex.getText("dynamicTimeRangeFE.editTimeRange"), eds, BDialog.OK | BDialog.CANCEL);
      if (rc == BDialog.CANCEL) return;
      
      tempStartTime = eds.getStartTime();
      tempEndTime = eds.getEndTime();
      if (tempStartTime.equals(BAbsTime.NULL) || tempEndTime.equals(BAbsTime.NULL) ||
          tempEndTime.isAfter(tempStartTime))
        break;
      
      BDialog.error(this, lex.getText("dynamicTimeRangeFE.timeSequenceError")); 
    }
    startTime = tempStartTime;
    endTime = tempEndTime;
    timeRangeLabel.setText(formatTimeRange(startTime, endTime));
    setModified();
  }

  /**
   * Format the specified range of times.
   */
  private String formatTimeRange(BAbsTime startTime, BAbsTime endTime)
  {
    StringBuilder s = new StringBuilder(prefTemplate.length());
    if ((startTime == null) || startTime.isNull())
      s.append(lex.getText("dynamicTimeRangeFE.epoch"));
    else
      s.append(startTime.toString(getCurrentContext()));
    
    // Use two spaces on each side
    s.append("  ").append(lex.getText("dynamicTimeRangeFE.to")).append("  ");
    
    if ((endTime == null) || endTime.isNull())
      s.append(lex.getText("dynamicTimeRangeFE.now"));
    else
      s.append(endTime.toString(getCurrentContext()));

    return s.toString();
  }

////////////////////////////////////////////////////////////////
// Type Choice
////////////////////////////////////////////////////////////////

  /**
   * Make a drop down for selecting the range type.
   */
  public static BListDropDown makeTypeChoice()
  {
    BListDropDown typeChoice = new BListDropDown();
    BList typeList = typeChoice.getList();
    
    BEnumRange range = BDynamicTimeRangeType.today.getRange();
    int[] ords = range.getOrdinals();
    for (int i = 0; i < ords.length; i++)
    {
      typeList.addItem(range.get(ords[i]));
    }
    
    typeChoice.setSelectedItem(BDynamicTimeRangeType.today);

    return typeChoice;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final String prefTemplate = "WW-WWW-WWWW WW:WWWW to WW-WWW-WWWW WW:WWWW WWWWWWWWWWW";
  private static final Lexicon lex = Lexicon.make("bql");
  private static final double HGAP = 8;

  private BListDropDown typeChoice;
  private BLabel timeRangeLabel;
  private BButton editButton;
  private BAbsTime startTime;
  private BAbsTime endTime;
  
  public static void main(String[] args)
  {
    BEdgePane main = new BEdgePane();
    BDynamicTimeRangeFE ed = new BDynamicTimeRangeFE();
    ed.loadValue(BDynamicTimeRange.make(BDynamicTimeRangeType.today));
    main.setTop(ed);
    
    BFrame f = new BFrame();
    f.setContent(main);
    f.setScreenBounds(200, 10, 700, 150);
    f.open();
  }
}
