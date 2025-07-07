// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test 6: Exact copy of current BExcelDataSource (with simplified constructor)
 * Tests if the issue is with the specific class name or some other factor
 */
@NiagaraType
@NiagaraProperty(
  name = "connectionDetails",
  type = "datasync:ExcelConnectionDetails",
  defaultValue = "null",
  flags = Flags.READONLY | Flags.SUMMARY,
  override = true
)
public class BTest6ExactCopy extends BAbstractDataSource {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTest6ExactCopy(2018014743)1.0$ @*/
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
  public static final Type TYPE = Sys.loadType(BTest6ExactCopy.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTest6ExactCopy() {
    super();
    
    // Initialize connection details
    setConnectionDetails(new BExcelConnectionDetails());
    
    // Simplified constructor for palette compatibility
    System.out.println("🧪 Test6 Exact Copy created");
  }
}
