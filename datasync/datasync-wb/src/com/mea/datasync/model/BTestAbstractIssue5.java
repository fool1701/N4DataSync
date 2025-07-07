// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Can we extend BAbstractDataSource if we don't reference abstract types?
 * This extends BAbstractDataSource but doesn't override any properties
 */
@NiagaraType
public class BTestAbstractIssue5 extends BAbstractDataSource {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestAbstractIssue5(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestAbstractIssue5.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestAbstractIssue5() {
    super();
    System.out.println("🧪 TestAbstractIssue5 created - BAbstractDataSource without property override");
  }
}
