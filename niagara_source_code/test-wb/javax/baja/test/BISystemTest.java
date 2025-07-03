/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BInterface;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BISystemTest is a marker interface which represents a test that is
 * larger in scale than a 'normal' unit test.  A BISystemTest usually
 * runs for a very long time, or indefinitely.  In addition, it may require
 * special set-up to run.  As such, BISystemTests will be automatically
 * skipped over when all the tests in a given module are being run at once.
 *
 * @author    Mike Jarmy
 * @creation  15 Jul 10
 * @version   $Revision: 1$ $Date: 9/1/10 1:50:44 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType
public interface BISystemTest
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.BISystemTest(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:31 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  Type TYPE = Sys.loadType(BISystemTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
