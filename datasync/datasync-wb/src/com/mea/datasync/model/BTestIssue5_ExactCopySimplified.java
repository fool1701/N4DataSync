// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test 5: Exact copy of current BExcelDataSource but with minimal constructor
 * This tests if there's something specific about the BExcelDataSource implementation
 */
@NiagaraType
@NiagaraProperty(
  name = "connectionDetails",
  type = "datasync:ExcelConnectionDetails",
  defaultValue = "null",
  flags = Flags.READONLY | Flags.SUMMARY,
  override = true
)
public class BTestIssue5_ExactCopySimplified extends BAbstractDataSource {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestIssue5_ExactCopySimplified(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "connectionDetails"

  /**
   * Slot for the {@code connectionDetails} property.
   * @see #getConnectionDetails
   * @see #setConnectionDetails
   */
  public static final Property connectionDetails = newProperty(Flags.READONLY | Flags.SUMMARY, (BValue)null, null);

  /**
   * Get the {@code connectionDetails} property.
   * @see #connectionDetails
   */
  public BExcelConnectionDetails getConnectionDetails() { return (BExcelConnectionDetails)get(connectionDetails); }

  /**
   * Set the {@code connectionDetails} property.
   * @see #connectionDetails
   */
  public void setConnectionDetails(BExcelConnectionDetails v) { set(connectionDetails, v, null); }

  //endregion Property "connectionDetails"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestIssue5_ExactCopySimplified.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestIssue5_ExactCopySimplified() {
    super();
    System.out.println("🧪 Test5 Exact Copy Simplified - Minimal BExcelDataSource copy");
  }
}
