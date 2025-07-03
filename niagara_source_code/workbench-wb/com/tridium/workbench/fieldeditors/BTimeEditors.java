/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.workbench.fieldeditors;

import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BFacets;
import javax.baja.sys.BLink;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BWidget;
import javax.baja.ui.list.BList;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

/**
 * BTimeEditors is a widget for editing the start and end times
 * of a BDynamicTimeRange.
 * 
 * @author    John Sublett
 * @creation  25 Aug 2004
 * @version   $Revision: 5$ $Date: 8/15/07 3:46:03 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "updateEnabledStates"
)
public class BTimeEditors
  extends BWidget
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BTimeEditors(3929774359)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "updateEnabledStates"

  /**
   * Slot for the {@code updateEnabledStates} action.
   * @see #updateEnabledStates()
   */
  public static final Action updateEnabledStates = newAction(0, null);

  /**
   * Invoke the {@code updateEnabledStates} action.
   * @see #updateEnabledStates
   */
  public void updateEnabledStates() { invoke(updateEnabledStates, null, null); }

  //endregion Action "updateEnabledStates"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTimeEditors.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BTimeEditors()
  {
  }
  
  public BTimeEditors(BAbsTime startTime, BAbsTime endTime, Context context)
  {
    this.context = new BasicContext(context, FACETS);
    Lexicon lex = Lexicon.make("bql");
    
    startLabel = new BLabel(lex.getText("dynamicTimeRangeFE.startTime") + ":");
    add("startLabel", startLabel);
    startModeChoice = makeModeList(lex);
    add("startMode", startModeChoice);
    startEd = BWbFieldEditor.makeFor(BAbsTime.DEFAULT);
    add("startEd", startEd);

    setStartTime(startTime);
    
    endLabel = new BLabel(lex.getText("dynamicTimeRangeFE.endTime") + ":");
    add("endLabel", endLabel);
    endModeChoice = makeModeList(lex);
    add("endMode", endModeChoice);
    endEd = BWbFieldEditor.makeFor(BAbsTime.DEFAULT);
    add("endEd", endEd);

    setEndTime(endTime);
    
    add("startLink", new BLink(startModeChoice, BListDropDown.listActionPerformed, updateEnabledStates));
    add("endLink", new BLink(endModeChoice, BListDropDown.listActionPerformed, updateEnabledStates));
  }

  public BAbsTime getStartTime()
  {
    int startMode = startModeChoice.getSelectedIndex();
    if (startMode == NONE)
      return BAbsTime.NULL;
    else
    {
      BAbsTime t = null;
      try { t = (BAbsTime)startEd.saveValue(); } catch(Exception e) {}
      return t;
    }
  }
  
  public BAbsTime getEndTime()
  {
    int endMode = endModeChoice.getSelectedIndex();
    if (endMode == NONE)
      return BAbsTime.NULL;
    else
    {
      BAbsTime t = null;
      try { t = (BAbsTime)endEd.saveValue(); } catch(Exception e) {}
      return t;
    }
  }

  public void setStartTime(BAbsTime startTime)
  {
    if ((startTime == null) || startTime.isNull())
    {
      startModeChoice.setSelectedIndex(NONE);
      startEd.loadValue(Clock.time().timeOfDay(0,0,0,0), context);
      startEd.setReadonly(true);
    }
    else
    {
      startModeChoice.setSelectedIndex(FIXED);
      startEd.loadValue(startTime, context);
      startEd.setReadonly(false);
    }
  }

  public void setEndTime(BAbsTime endTime)
  {
    if ((endTime == null) || endTime.isNull())
    {
      endModeChoice.setSelectedIndex(NONE);
      endEd.loadValue(Clock.time(), context);
      endEd.setReadonly(true);
    }
    else
    {
      endModeChoice.setSelectedIndex(FIXED);
      endEd.loadValue(endTime, context);
      endEd.setReadonly(false);
    }
  }

  private BListDropDown makeModeList(Lexicon lex)
  {
    BListDropDown result = new BListDropDown();
    BList modeList = result.getList();
    modeList.addItem(lex.getText("none"));
    modeList.addItem(lex.getText("fixed"));
    result.setSelectedIndex(0);
    return result;
  }
  
  public void computePreferredSize()
  {
    startLabel.computePreferredSize();
    startModeChoice.computePreferredSize();
    startEd.computePreferredSize();
    endLabel.computePreferredSize();
    endModeChoice.computePreferredSize();
    endEd.computePreferredSize();
    
    double pw = 0;
    double ph = 0;
    
    double maxLabel =
      Math.max(startLabel.getPreferredWidth(), endLabel.getPreferredWidth());
    
    pw = maxLabel;
    pw += HGAP + startModeChoice.getPreferredWidth();
    pw += HGAP + startEd.getPreferredWidth();

    ph = startLabel.getPreferredHeight();
    ph = Math.max(ph, startModeChoice.getPreferredHeight());
    ph = Math.max(ph, startEd.getPreferredHeight());
    ph *= 2;
    
    setPreferredSize(pw+50, ph);
  }

  public void doLayout(BWidget[] kids)
  {
    double w = getWidth();
    double h = getHeight();

    
    startLabel.computePreferredSize();
    startModeChoice.computePreferredSize();
    startEd.computePreferredSize();
    endLabel.computePreferredSize();
    endModeChoice.computePreferredSize();
    endEd.computePreferredSize();
    
    double row0 = 0;
    double row1 = h / 2;
    double rowHeight = h / 2;
    double maxLabel =
      Math.max(startLabel.getPreferredWidth(), endLabel.getPreferredWidth());

    double x = 0;
    startLabel.setBounds(x, row0 + (rowHeight - startLabel.getPreferredHeight()) / 2,
                         startLabel.getPreferredWidth(),
                         startLabel.getPreferredHeight());
    x += HGAP + maxLabel;
    
    startModeChoice.setBounds(x, row0 + (rowHeight - startModeChoice.getPreferredHeight()) / 2,
                              startModeChoice.getPreferredWidth(),
                              startModeChoice.getPreferredHeight());
    x += HGAP + startModeChoice.getPreferredWidth();
    
    startEd.setBounds(x, row0 + (rowHeight - startEd.getPreferredHeight()) / 2,
                      startEd.getPreferredWidth(),
                      startEd.getPreferredHeight());

    x = 0;
    endLabel.setBounds(x, row1 + (rowHeight - endLabel.getPreferredHeight()) / 2,
                       endLabel.getPreferredWidth(),
                       endLabel.getPreferredHeight());
    x += HGAP + maxLabel;
    
    endModeChoice.setBounds(x, row1 + (rowHeight - endModeChoice.getPreferredHeight()) / 2,
                            endModeChoice.getPreferredWidth(),
                            endModeChoice.getPreferredHeight());
    x += HGAP + endModeChoice.getPreferredWidth();
    
    endEd.setBounds(x, row1 + (rowHeight - endEd.getPreferredHeight()) / 2,
                    endEd.getPreferredWidth(),
                    endEd.getPreferredHeight());
  }
 
  public void doUpdateEnabledStates()
  {
    int mode = startModeChoice.getSelectedIndex();
    startEd.setReadonly(mode == NONE);
    
    mode = endModeChoice.getSelectedIndex();
    endEd.setReadonly(mode == NONE);    
  }
 
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final int NONE  = 0;
  private static final int FIXED = 1;
  
  private static final double HGAP = 8;

  private static final BFacets FACETS = BFacets.make(
    BFacets.SHOW_SECONDS, BBoolean.FALSE,
    BFacets.SHOW_MILLISECONDS, BBoolean.FALSE);

  private Context context;

  private BLabel startLabel;
  private BListDropDown startModeChoice;
  private BWbFieldEditor startEd;
  
  private BLabel endLabel;
  private BListDropDown endModeChoice;
  private BWbFieldEditor endEd;

  
}
