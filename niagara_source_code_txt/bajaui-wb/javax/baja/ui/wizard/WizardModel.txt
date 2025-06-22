/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.wizard;

import javax.baja.gx.*;
import javax.baja.ui.*;

/**
 * WizardModel implements the code required to track 
 * a wizard process.
 *
 * @author    Mike Jarmy
 * @creation  28 May 02
 * @version   $Revision: 8$ $Date: 3/28/05 10:32:43 AM EST$
 * @since     Baja 1.0
 */
public abstract class WizardModel
{

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get the wizard managing this model.  This is only
   * valid while the wizard operation is active.
   */
  public final BWizard getWizard()
  {
    return wizard;
  }

  public void setWizard(BWizard value)
  {
    wizard = value;
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////  
  
  /**
   * Get a preferred size which will handle all the steps.
   */
  public Size getPreferredSizeOfSteps()
  {
    return new Size(300, 200);
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////  

  /**
   * Get the title to display in the wizard dialog's title bar.
   */
  public abstract String getTitle(); 
  
  /**
   * This callback is invoked to initialize the wizard's 
   * first step and enable the correct buttons.  You may
   * use the <code>update()</code> method.
   */
  public abstract void init();
  
  /**
   * This callback is invoke when the user invokes the back button.
   * It the model's responsibility to update the current step and
   * update the button's enabled and visibility states.  You may
   * do this using the <code>update()</code> method.
   */
  public abstract void back();

  /**
   * This callback is invoke when the user invokes the next button.
   * It the model's responsibility to update the current step and
   * update the button's enabled and visibility states.  You may
   * do this using the <code>update()</code> method.
   */
  public abstract void next();

  /**
   * This callback is invoke when the user invokes the finish 
   * button.  The model should finish the operation and return
   * true to close the wizard dialog.  The default implementation
   * returns true.
   */
  public boolean finish()
  {
    return true;
  }
  
  /**
   * Cancel is called if the user invokes the cancel button.  If
   * this method returns true then the dialog is closed and the
   * wizard operation terminates.  If this method returns false the 
   * cancel is aborted.  The default implementation returns true.
   */
  public boolean cancel()
  {
    return true;
  }
  
  /**
   * This method updates the wizard's current step to use the
   * specified widget and also updates the back, next, finish
   * buttons using a standard algorithm.  The mode is a bitmask
   * of the CAN_BACK, CAN_NEXT, and CAN_FINISH constants. 
   */
  public void update(BWidget step, int mode)
  {
    wizard.setCurrentStep(step);
    update(mode);

    wizard.setDefaultButton(null);
    if ((mode & CAN_FINISH) != 0) wizard.setFinishAsDefault();
    else if ((mode & CAN_NEXT) != 0)wizard.setNextAsDefault();
  }
  
  /**
   * Update the back, next, and finish button enabled states
   * without changing the current step. The mode is a bitmask
   * of the CAN_BACK, CAN_NEXT, and CAN_FINISH constants. 
   */
  public void update(int mode)
  {
    wizard.setBackEnabled( (mode & CAN_BACK) != 0 );
    wizard.setNextEnabled( (mode & CAN_NEXT) != 0 );
    wizard.setFinishEnabled( (mode & CAN_FINISH) != 0 );
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final int CAN_BACK   = 0x01;
  public static final int CAN_NEXT   = 0x02;
  public static final int CAN_FINISH = 0x04;

  private BWizard wizard;
  
}

