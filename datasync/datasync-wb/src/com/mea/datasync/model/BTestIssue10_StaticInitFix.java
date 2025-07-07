// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test 10: Fix the static initialization issue
 * This uses (BValue)null instead of new BExcelConnectionDetails() in static property
 */
@NiagaraType
@NiagaraProperty(
  name = "connectionDetails",
  type = "datasync:ExcelConnectionDetails",
  defaultValue = "null",
  flags = Flags.READONLY | Flags.SUMMARY,
  override = true
)
public class BTestIssue10_StaticInitFix extends BAbstractDataSource {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestIssue10_StaticInitFix(2018014743)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BTestIssue10_StaticInitFix.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestIssue10_StaticInitFix() {
    super();
    // Initialize connection details in constructor instead of static property
    setConnectionDetails(new BExcelConnectionDetails());
    System.out.println("📊 Test10 Static Init Fix created");
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
    return "Test10 Static Init Fix";
  }

  @Override
  public String getConnectionSummary() {
    BExcelConnectionDetails details = getConnectionDetails();
    StringBuilder summary = new StringBuilder();
    
    summary.append("Test10 Connection: ");
    summary.append(details.getDisplayName());
    
    String filePath = details.getFilePath();
    if (filePath != null && !filePath.trim().isEmpty()) {
      summary.append(" (").append(filePath).append(")");
    }
    
    return summary.toString();
  }
}
