// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Is the issue with the model package specifically?
 * This component is in a different package (test instead of model)
 */
@NiagaraType
public class BTestDeeper4_DifferentPackage extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.test.BTestDeeper4_DifferentPackage(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestDeeper4_DifferentPackage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestDeeper4_DifferentPackage() {
    super();
    System.out.println("🧪 TestDeeper4 - Component in different package (test)");
  }
}
