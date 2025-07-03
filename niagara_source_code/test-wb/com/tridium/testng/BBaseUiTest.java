/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.testng;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BTestNg;
import javax.baja.ui.BWidgetApplication;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.tridium.ui.NullUiEnv;
import com.tridium.ui.UiEnv;

/**
 * Base class for UI tests
 *
 * @author Melanie Coggan on 07 Dec 2017
 */
@NiagaraType
public abstract class BBaseUiTest extends BTestNg
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.testng.BBaseUiTest(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:46 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBaseUiTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Setup and Teardown
////////////////////////////////////////////////////////////////

  @BeforeClass(alwaysRun = true)
  public void createApp()
  {
    synchronized (UiEnv.appLock)
    {
      this.previousApp = UiEnv.app;
      UiEnv.app = mockApp();
    }
  }

  @AfterClass(alwaysRun = true)
  public void destroyApp()
  {
    synchronized (UiEnv.appLock)
    {
      UiEnv.app = previousApp;
    }
  }
  
  protected boolean hasUi()
  {
    UiEnv uiEnv = UiEnv.get();
    return !(uiEnv==null) && !(uiEnv instanceof NullUiEnv);
  }
  
  protected BWidgetApplication mockApp()
  {
    return UiEnv.app;
  }
  
  private BWidgetApplication previousApp;

}
