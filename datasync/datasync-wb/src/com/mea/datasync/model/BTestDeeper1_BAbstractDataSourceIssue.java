// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Is BAbstractDataSource itself the problem?
 * This extends BComponent and copies BAbstractDataSource structure without inheritance
 */
@NiagaraType
public class BTestDeeper1_BAbstractDataSourceIssue extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestDeeper1_BAbstractDataSourceIssue(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestDeeper1_BAbstractDataSourceIssue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestDeeper1_BAbstractDataSourceIssue() {
    super();
    System.out.println("🧪 TestDeeper1 - BComponent avoiding BAbstractDataSource completely");
  }
}
