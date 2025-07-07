// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Is the issue with the com.mea.datasync.model package?
 * This is the simplest possible component in this package
 */
@NiagaraType
public class BTestDeeper3_PackageIssue extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestDeeper3_PackageIssue(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestDeeper3_PackageIssue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestDeeper3_PackageIssue() {
    super();
    System.out.println("🧪 TestDeeper3 - Simplest possible component in model package");
  }
}
