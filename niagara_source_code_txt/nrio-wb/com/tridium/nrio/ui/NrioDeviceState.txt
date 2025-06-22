/*
 * @copyright 2005 Tridium Inc.
 */
package com.tridium.nrio.ui;

import javax.baja.driver.ui.device.DeviceState;
import javax.baja.job.BJob;
import javax.baja.workbench.mgr.BAbstractManager;

/**
 * NrioDeviceState - Preserves the state of the device manager when the user
 * leaves the view.
 *
 * @author    Andy Saunders
 * @creation  Nov 17, 2005
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
public class NrioDeviceState
  extends DeviceState
{
  BJob jobToRemember;
  protected void restoreForOrd(BAbstractManager manager)
  {
    super.restoreForOrd(manager);
    BNrioDeviceManager m = (BNrioDeviceManager)manager;
    if (jobToRemember!=null && jobToRemember.isMounted())
    {
      m.getLearn().setJob(jobToRemember);
      m.discoveryListSubscriber.subscribe( jobToRemember,2);
    }
    //m.discoveryListSubscriber.subscribe( m.getAaPhpNetwork(),2);
  }
  protected void saveForOrd(BAbstractManager manager)
  {
    super.saveForOrd(manager);
    BNrioDeviceManager m = (BNrioDeviceManager)manager;
    jobToRemember=m.getLearn().getJob();
    m.discoveryListSubscriber.unsubscribeAll();
  }
}
