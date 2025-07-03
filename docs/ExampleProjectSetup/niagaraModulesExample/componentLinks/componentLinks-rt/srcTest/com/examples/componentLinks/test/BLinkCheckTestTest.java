/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.componentLinks.test;

import javax.baja.control.BNumericWritable;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BInteger;
import javax.baja.sys.BLink;
import javax.baja.sys.BStation;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BTestNg;

import com.examples.componentLinks.BLinkCheckTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.tridium.kitControl.util.BRamp;
import com.tridium.kitControl.util.BSineWave;

@NiagaraType
@Test(groups = { "examples" })
public class BLinkCheckTestTest
  extends BTestNg
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.examples.componentLinks.test.BLinkCheckTestTest(2979906276)1.0$ @*/
/* Generated Thu Dec 01 20:57:46 IST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLinkCheckTestTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  @Test(enabled = true)
  public void ngTestLinkCheck1()
  {
    // Clear any existing components and rebuild the station and grid
    root.removeAll();
    buildStation();

    // Link from ramp to linkCheckTest should succeed
    ramp.add("link", new BLink(linkCheckTest.getHandleOrd(), "p", "p", true));
    Assert.assertEquals(ramp.getLinks().length, 1);
  }

  @Test(enabled = true)
  public void ngTestLinkCheck2()
  {
    // Clear any existing components and rebuild the station and grid
    root.removeAll();
    buildStation();

    // Link from sineWave to linkCheckTest should fail
    sineWave.add("link", new BLink(linkCheckTest.getHandleOrd(), "p", "p", true));
    Assert.assertEquals(ramp.getLinks().length, 0);
  }

  @BeforeClass(alwaysRun = true)
  public void setupBeforeClass()
    throws Exception
  {
    // init station
    handler = createTestStation();

    handler.startStation();
    station = handler.getStation();

    root = new BComponent();
    station.add("root", root);
  }

  @AfterClass(alwaysRun = true)
  public void teardownAfterClass()
    throws Exception
  {
    // cleanup                                                    
    handler.releaseStation();
  }

  private void buildStation()
  {
    // Add BRamp, BSineWave, BNumericWritables
    ramp = new BRamp();
    root.add("ramp", ramp);
    sineWave = new BSineWave();
    root.add("sineWave", sineWave);

    p1 = ramp.add("p", BInteger.DEFAULT);
    p2 = sineWave.add("p", BInteger.DEFAULT);

    // Add BLinkCheckTest
    linkCheckTest = new BLinkCheckTest();
    root.add("linkCheckTest", linkCheckTest);
    p4 = linkCheckTest.add("p", BInteger.DEFAULT);
  }

////////////////////////////////////////////////////////////////
//Attributes
////////////////////////////////////////////////////////////////

  private BStation station;
  private BComponent root, ramp, sineWave, linkCheckTest;
  BLink link;
  private TestStationHandler handler;
  private Property p1, p2, p3, p4;
}
