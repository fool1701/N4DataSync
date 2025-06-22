/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.wizard;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.enums.*;
import javax.baja.ui.event.*;
import javax.baja.ui.pane.*;
import javax.baja.ui.util.*;

/**
 * BWizard is the dialog class used to manage and display a wizard.  
 * A WizardModel class is implemented for each wizard to control the 
 * steps involved.
 *
 * @author    Mike Jarmy
 * @creation  28 May 02
 * @version   $Revision: 20$ $Date: 11/22/06 4:41:19 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BWizard
  extends BDialog
{ 

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.wizard.BWizard(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWizard.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  public static void open(BWidget owner, WizardModel model)
  {
    open(owner, model, BInsets.make(0, 0, 0, 0));
  }
    
  public static void open(BWidget owner, WizardModel model, BInsets stepInsets)
  {
    BWizard wizard = new BWizard(owner, model, stepInsets);
    model.setWizard(wizard);
    wizard.setBoundsCenteredOnOwner();
    model.init();
    wizard.open();
    model.setWizard(null);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  
  
  /**
   * Package private constructor.
   */
  BWizard(BWidget owner, WizardModel model, BInsets stepInsets)
  {
    super(owner, model.getTitle(), true);
    
    this.model = model;   
    this.stepPane = new BBorderPane(new BNullWidget(), stepInsets); 

    // build buttons
    BGridPane b = new BGridPane(4);
    b.setColumnAlign(BHalign.fill);
    b.setUniformColumnWidth(true);
    b.setHalign(BHalign.right);
    b.add("back", back);
    b.add("next", next);
    b.add("finish", finish);
    b.add("cancel", cancel);
    BEdgePane buttons = new BEdgePane();
    buttons.setTop(new BSeparator(BOrientation.horizontal));
    buttons.setCenter(new BBorderPane(b));
    
    // put it all together
    BEdgePane content = new BEdgePane();
    content.setCenter(stepPane);
    content.setBottom(buttons);
    setContent(content);
  }     
  
  /**
   * Public constructor.  Do not use directly.
   */
  public BWizard()
  {
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public WizardModel getModel() { return model; }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////  

  public void computePreferredSize()
  {
    super.computePreferredSize();
    double pw = getPreferredWidth();
    double ph = getPreferredHeight();
    Size steps = model.getPreferredSizeOfSteps();
    pw = Math.max(pw, steps.width);
    ph = Math.max(ph, steps.height);
    setPreferredSize(pw, ph);
  }

////////////////////////////////////////////////////////////////
// Enables
////////////////////////////////////////////////////////////////

  public BButton getBackButton() { return back; }  
  public void setBackVisible(boolean visible) { back.setVisible(visible); relayout(); }
  public void setBackEnabled(boolean enabled) { back.setEnabled(enabled); }
  public void setBackAsDefault() { setDefaultButton(back); back.requestFocus(); }

  public BButton getNextButton() { return next; }  
  public void setNextVisible(boolean visible) { next.setVisible(visible); relayout(); }
  public void setNextEnabled(boolean enabled) { next.setEnabled(enabled); }
  public void setNextAsDefault() { setDefaultButton(next); next.requestFocus(); }

  public BButton getCancelButton() { return cancel; }  
  public void setCancelVisible(boolean visible) { cancel.setVisible(visible); relayout(); }
  public void setCancelEnabled(boolean enabled) { cancel.setEnabled(enabled); }
  public void setCancelAsDefault() { setDefaultButton(cancel); }

  public BButton getFinishButton() { return finish; }  
  public void setFinishVisible(boolean visible) { finish.setVisible(visible); relayout(); }
  public void setFinishEnabled(boolean enabled) { finish.setEnabled(enabled); }
  public void setFinishAsDefault() { setDefaultButton(finish); finish.requestFocus(); }
  
  public void setCurrentStep(BWidget step)
  {
    if (stepPane.getContent() == step) return;
    stepPane.setContent(step);
    step.relayout();
  }
  
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////  

  /*
  public void windowOpened(BWindowEvent event)
  {
    BButton b = getDefaultButton();
    if (b != null) b.requestFocus();
    model.windowOpened();
  }
  */ 
  
  /**
   * Window closing is treated as a cancel.
   */
  public void windowClosing(BWindowEvent event)
  {
    cancelCommand.doInvoke();
  }
  
////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////  

  class BackCommand extends Command
  {
    BackCommand(BWizard wiz) { super(wiz, lex.module, "wizard.back"); }
    public CommandArtifact doInvoke() { model.back(); return null; }
  }

  class NextCommand extends Command
  {
    NextCommand(BWizard wiz) { super(wiz, lex.module, "wizard.next"); }
    public CommandArtifact doInvoke() { model.next(); return null; }
  }

  class FinishCommand extends Command
  {
    FinishCommand(BWizard wiz) { super(wiz, lex.module, "wizard.finish"); }
    public CommandArtifact doInvoke() 
    { 
      if (model.finish()) close();
      return null; 
    }
  }

  class CancelCommand extends Command
  {
    CancelCommand(BWizard wiz) { super(wiz, lex.module, "wizard.cancel"); }
    public CommandArtifact doInvoke() 
    { 
      if (model.cancel()) close(); 
      return null; 
    }
  }

////////////////////////////////////////////////////////////////
// Test Driver
////////////////////////////////////////////////////////////////  

  /*
  public static void main(String[] args)
  {
    WizardModel model = new TestModel();
    BWizard.open(new BFrame(), model);
  }
  
  static class TestModel
    extends WizardModel
  {
    public String getTitle() { return "Test Wizard"; }
    public boolean cancel() 
    { 
      return BDialog.confirm(getWizard(), "confirm", "Cancel?") == BDialog.YES;  
    }
    
    public void init() { update(); }
    
    public void back() { step--; update(); }
    
    public void next() { step++; update(); }

    public void update()
    {
      int mode = 0;
      if (step > 0) mode |= CAN_BACK;
      if (step == 3) mode |= CAN_FINISH;
      else mode |= CAN_NEXT;
      update(new BLabel("Step " + step), mode);      
    }
    
    public boolean finish()
    {
      return BDialog.confirm(getWizard(), "confirm", "Finish?") == BDialog.YES;  
    }
    
    int step = 0;
  }
  */

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  final UiLexicon lex = UiLexicon.bajaui();
  
  WizardModel model;
  BBorderPane stepPane;
  
  BackCommand backCommand = new BackCommand(this);
  NextCommand nextCommand = new NextCommand(this);
  FinishCommand finishCommand = new FinishCommand(this);
  CancelCommand cancelCommand = new CancelCommand(this);
  
  BButton back = new BButton(backCommand);
  BButton next = new BButton(nextCommand);
  BButton finish = new BButton(finishCommand);
  BButton cancel = new BButton(cancelCommand);
  
}
