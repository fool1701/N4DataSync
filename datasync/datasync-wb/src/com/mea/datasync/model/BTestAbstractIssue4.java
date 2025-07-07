// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Does using concrete type instead of abstract fix the issue?
 * This extends BComponent and has a property that references concrete BExcelConnectionDetails
 */
@NiagaraType
@NiagaraProperty(
  name = "connectionDetails",
  type = "datasync:ExcelConnectionDetails",
  defaultValue = "null"
)
public class BTestAbstractIssue4 extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestAbstractIssue4(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "connectionDetails"

  /**
   * Slot for the {@code connectionDetails} property.
   * @see #getConnectionDetails
   * @see #setConnectionDetails
   */
  public static final Property connectionDetails = newProperty(0, (BValue)null, null);

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
  public static final Type TYPE = Sys.loadType(BTestAbstractIssue4.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestAbstractIssue4() {
    super();
    System.out.println("🧪 TestAbstractIssue4 created - BComponent with concrete type property");
  }
}
