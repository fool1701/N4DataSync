// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Is the issue with BAbstractDataSource being concrete but originally abstract?
 * This tests if there's an issue with the class design itself
 */
@NiagaraType
public class BTestAbstractIssue2 extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestAbstractIssue2(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestAbstractIssue2.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestAbstractIssue2() {
    super();
    // Call the same constructor logic as BAbstractDataSource
    // (empty constructor, no initialization)
    System.out.println("🧪 TestAbstractIssue2 created - mimicking BAbstractDataSource constructor");
  }
}
