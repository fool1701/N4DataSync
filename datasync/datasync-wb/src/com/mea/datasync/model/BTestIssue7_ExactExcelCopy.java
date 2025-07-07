// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test 7: Exact copy of BExcelDataSource but with different class name
 * This tests if the issue is specifically with the "ExcelDataSource" name
 */
@NiagaraType
@NiagaraProperty(
  name = "connectionDetails",
  type = "datasync:ExcelConnectionDetails",
  defaultValue = "null",
  flags = Flags.READONLY | Flags.SUMMARY,
  override = true
)
public class BTestIssue7_ExactExcelCopy extends BAbstractDataSource {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestIssue7_ExactExcelCopy(3170473770)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "connectionDetails"

  /**
   * Slot for the {@code connectionDetails} property.
   * @see #getConnectionDetails
   * @see #setConnectionDetails
   */
  public static final Property connectionDetails = newProperty(Flags.READONLY | Flags.SUMMARY, new BExcelConnectionDetails(), null);

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
  public static final Type TYPE = Sys.loadType(BTestIssue7_ExactExcelCopy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestIssue7_ExactExcelCopy() {
    super();

    // Initialize connection details
    setConnectionDetails(new BExcelConnectionDetails());

    // Simplified constructor for palette compatibility
    // Note: Auto-check configuration will be set when component is actually used
    System.out.println("📊 Test7 Excel Copy created");
  }

////////////////////////////////////////////////////////////////
// Abstract Method Implementations
////////////////////////////////////////////////////////////////

  @Override
  protected ConnectionTestResult performConnectionTest() {
    BExcelConnectionDetails details = getConnectionDetails();
    
    // Basic validation
    String filePath = details.getFilePath();
    if (filePath == null || filePath.trim().isEmpty()) {
      return new ConnectionTestResult(false, "No file path specified");
    }
    
    return new ConnectionTestResult(true, "Test connection successful");
  }

  @Override
  public String getDataSourceTypeName() {
    return "Test7 Excel Copy";
  }

  @Override
  public String getConnectionSummary() {
    return "Test7 Excel Copy Connection";
  }
}
