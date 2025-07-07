// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test 1: Can we extend BAbstractDataSource now that BConnectionDetails is concrete?
 * This tests if the abstract class issue is fully resolved
 */
@NiagaraType
public class BTestIssue1_ConcreteInheritance extends BAbstractDataSource {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestIssue1_ConcreteInheritance(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestIssue1_ConcreteInheritance.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestIssue1_ConcreteInheritance() {
    super();
    System.out.println("🧪 Test1 Concrete Inheritance - BAbstractDataSource with concrete BConnectionDetails");
  }
}
