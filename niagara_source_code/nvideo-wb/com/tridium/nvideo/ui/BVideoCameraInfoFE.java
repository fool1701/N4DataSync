/*
 * copyright 2012 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nvideo.ui;

import javax.baja.gx.BInsets;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.BBorder;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.Lexicon;
import javax.baja.workbench.CannotSaveException;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.nvideo.datatypes.BVideoCameraInfo;

@NiagaraType(
  agent = @AgentOn(
    types = "nvideo:VideoCameraInfo"
  )
)
@NiagaraAction(
  name = "processAllEnabledCheckBox"
)
public class BVideoCameraInfoFE
    extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nvideo.ui.BVideoCameraInfoFE(1879546996)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "processAllEnabledCheckBox"

  /**
   * Slot for the {@code processAllEnabledCheckBox} action.
   * @see #processAllEnabledCheckBox()
   */
  public static final Action processAllEnabledCheckBox = newAction(0, null);

  /**
   * Invoke the {@code processAllEnabledCheckBox} action.
   * @see #processAllEnabledCheckBox
   */
  public void processAllEnabledCheckBox() { invoke(processAllEnabledCheckBox, null, null); }

  //endregion Action "processAllEnabledCheckBox"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVideoCameraInfoFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BVideoCameraInfoFE()
  {
    // Creates a checkbox
    allEnabledCheckBox = new BCheckBox(ALL_ENABLED_CHECKBOX_LABEL);
    supportsPanTilt = new BCheckBox(LEX.getText("videoCameraInfo.supportsPanTilt"));
    supportsZoom = new BCheckBox(LEX.getText("videoCameraInfo.supportsZoom"));
    supportsFocus = new BCheckBox(LEX.getText("videoCameraInfo.supportsFocus"));
    supportsIris = new BCheckBox(LEX.getText("videoCameraInfo.supportsIris"));
    supportsMoveToPreset = new BCheckBox(LEX.getText("videoCameraInfo.supportsMoveToPreset"));
    supportsStorePreset = new BCheckBox(LEX.getText("videoCameraInfo.supportsStorePreset"));
    
    linkTo(allEnabledCheckBox, BCheckBox.selected, processAllEnabledCheckBox);
    
    linkTo(allEnabledCheckBox, BCheckBox.selected, setModified);
    linkTo(supportsPanTilt, BCheckBox.selected, setModified);
    linkTo(supportsZoom, BCheckBox.selected, setModified);
    linkTo(supportsFocus, BCheckBox.selected, setModified);
    linkTo(supportsIris, BCheckBox.selected, setModified);
    linkTo(supportsMoveToPreset, BCheckBox.selected, setModified);
    linkTo(supportsStorePreset, BCheckBox.selected, setModified);
    
    // Creates a border pane that will use the checkbox as the label and the
    // loadedValueFE as the content
    mainPane = new BBorderPane();
    
    mainPane.setBorder( BBorder.solid );
    
    // Uses the checkbox as the label for the mainPane
    mainPane.setLabel(new BBorderPane(allEnabledCheckBox, BBorder.none, BInsets.make(0,5,0,5)));
    
    BGridPane supportBoxes = new BGridPane(3);
    supportBoxes.add("supportsPanTilt",supportsPanTilt);
    supportBoxes.add("supportsIris",supportsIris);
    supportBoxes.add("supportsMoveToPreset",supportsMoveToPreset);
    supportBoxes.add("supportsZoom",supportsZoom);
    supportBoxes.add("supportsFocus",supportsFocus);
    supportBoxes.add("supportsStorePreset",supportsStorePreset);
    
    BBorderPane supportBoxesWithPadding = new BBorderPane(supportBoxes, BBorder.none, BInsets.make(10));
    
    mainPane.setContent(supportBoxesWithPadding);
    
    // Adds a nice, default amount of padding to the content
    mainPane.setPadding(BInsets.make(5));
    
    // Uses the mainPane as the root widget for this custom field-editor
    setContent(mainPane);
  }
  
  /**
   * This method implements the {@link #processAllEnabledCheckBox} action. This
   * method is called whenever the {@link #allEnabledCheckBox} is checked
   * or unchecked. This happens because the {@link #allEnabledCheckBox}'s
   * selected property is programmatically linked to the {@link #processAllEnabledCheckBox}
   * action in this object' constructor.
   */
  public void doProcessAllEnabledCheckBox()
  {
    // Determines if the checkbox is currently checked
    boolean isNowChecked = allEnabledCheckBox.getSelected();
    
    supportsPanTilt.setSelected( isNowChecked );
    supportsZoom.setSelected( isNowChecked );
    supportsFocus.setSelected( isNowChecked );
    supportsIris.setSelected( isNowChecked );
    supportsMoveToPreset.setSelected( isNowChecked );
    supportsStorePreset.setSelected( isNowChecked );
    
    updateEnabledFlagOnCheckboxes();
  }
  
  
  /**
   * This method is automatically called when the user first views the
   * property sheet or when the property sheet is refreshed.
   */
  protected void doLoadValue(BObject value, Context cx) throws Exception
  {
    BVideoCameraInfo loadedValue = (BVideoCameraInfo)value;
    
    allEnabledCheckBox.setSelected( loadedValue.areAllEnabled() );
    
    supportsPanTilt.setSelected( loadedValue.getSupportsPanTilt() );
    supportsZoom.setSelected( loadedValue.getSupportsZoom());
    supportsFocus.setSelected( loadedValue.getSupportsFocus());
    supportsIris.setSelected( loadedValue.getSupportsIris());
    supportsMoveToPreset.setSelected( loadedValue.getSupportsMoveToPreset());
    supportsStorePreset.setSelected( loadedValue.getSupportsStorePreset());

    updateEnabledFlagOnCheckboxes();
    
  }
  
  /**
   * This method is automatically called when the user clicks the 'Save'
   * button from the property sheet.
   */
  protected BObject doSaveValue(BObject val, Context cx) throws CannotSaveException, Exception
  {
    // Retrieves the 'loadedValue' structure from the loadedValueFE and
    // returns it.
    BVideoCameraInfo loadedValue = (BVideoCameraInfo)val;
    
    loadedValue.setBoolean( BVideoCameraInfo.supportsPanTilt,      supportsPanTilt.getSelected(),      cx );
    loadedValue.setBoolean( BVideoCameraInfo.supportsZoom,         supportsZoom.getSelected(),         cx );
    loadedValue.setBoolean( BVideoCameraInfo.supportsFocus,        supportsFocus.getSelected(),        cx );
    loadedValue.setBoolean( BVideoCameraInfo.supportsIris,         supportsIris.getSelected(),         cx );
    loadedValue.setBoolean( BVideoCameraInfo.supportsMoveToPreset, supportsMoveToPreset.getSelected(), cx );
    loadedValue.setBoolean( BVideoCameraInfo.supportsStorePreset,  supportsStorePreset.getSelected(),  cx );
    
    if (loadedValue.areAllEnabled())
    {
      allEnabledCheckBox.setSelected( true );
    }
    
    updateEnabledFlagOnCheckboxes();
    
    return loadedValue;
  }
  
  /**
   * This method is automatically called by the property sheet field editor
   * based on whether the property (whose value is of type BVideoCameraInfo)
   * is flagged as read-only.
   */
  protected void doSetReadonly(boolean readOnly)
  {
    this.readOnly = readOnly;
    updateEnabledFlagOnCheckboxes();
    
  }
  
  protected void updateEnabledFlagOnCheckboxes()
  {
    allEnabledCheckBox.setEnabled(!readOnly);
    if (allEnabledCheckBox.getSelected())
    {
      supportsPanTilt.setEnabled(false);
      supportsZoom.setEnabled(false);
      supportsFocus.setEnabled(false);
      supportsIris.setEnabled(false);
      supportsMoveToPreset.setEnabled(false);
      supportsStorePreset.setEnabled(false);
    }
    else
    {
      supportsPanTilt.setEnabled(!readOnly);
      supportsZoom.setEnabled(!readOnly);
      supportsFocus.setEnabled(!readOnly);
      supportsIris.setEnabled(!readOnly);
      supportsMoveToPreset.setEnabled(!readOnly);
      supportsStorePreset.setEnabled(!readOnly);
    }
  }
  
  /**
   * This is the Lexicon that is used for localizing text strings.
   */
  protected static final Lexicon LEX = Lexicon.make(BVideoCameraInfoFE.class);
  
  /**
   * This is the 'All Enabled' text string as determined in the local
   * language from the Lexicon LEX.
   */
  protected static final String ALL_ENABLED_CHECKBOX_LABEL = LEX.getText("videoCameraInfo.allEnabled");
  
  /**
   * This is assigned in the {@link #doSetReadonly(boolean)} method and reviewed in the
   * {@link #doLoadValue(BObject, Context)} method.
   */
  protected boolean readOnly = false;
  
  /**
   * This is the check box for this field editor.
   */
  protected BCheckBox allEnabledCheckBox;
  
  /**
   * This is the pane whose border consists of the {@link #allEnabledCheckBox}
   * and the {@link #loadedValueFE}.
   */
  protected BBorderPane mainPane;
  
  protected BCheckBox supportsPanTilt,
                      supportsZoom,
                      supportsFocus,
                      supportsIris,
                      supportsMoveToPreset,
                      supportsStorePreset;
}
