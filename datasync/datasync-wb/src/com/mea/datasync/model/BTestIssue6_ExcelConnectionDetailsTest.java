// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test 6: Can we use BExcelConnectionDetails directly in a BComponent?
 * This tests if BExcelConnectionDetails itself has any instantiation issues
 */
@NiagaraType
@NiagaraProperty(
  name = "testExcelDetails",
  type = "datasync:ExcelConnectionDetails",
  defaultValue = "null"
)
public class BTestIssue6_ExcelConnectionDetailsTest extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestIssue6_ExcelConnectionDetailsTest(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "testExcelDetails"

  /**
   * Slot for the {@code testExcelDetails} property.
   * @see #getTestExcelDetails
   * @see #setTestExcelDetails
   */
  public static final Property testExcelDetails = newProperty(0, (BValue)null, null);

  /**
   * Get the {@code testExcelDetails} property.
   * @see #testExcelDetails
   */
  public BExcelConnectionDetails getTestExcelDetails() { return (BExcelConnectionDetails)get(testExcelDetails); }

  /**
   * Set the {@code testExcelDetails} property.
   * @see #testExcelDetails
   */
  public void setTestExcelDetails(BExcelConnectionDetails v) { set(testExcelDetails, v, null); }

  //endregion Property "testExcelDetails"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestIssue6_ExcelConnectionDetailsTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestIssue6_ExcelConnectionDetailsTest() {
    super();
    System.out.println("🧪 Test6 ExcelConnectionDetails Test - BComponent with ExcelConnectionDetails property");
  }
}
