// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Using the CORRECT syntax from working examples
 * This uses the exact @NiagaraProperty syntax from envCtrlDriver examples
 */
@NiagaraType
@NiagaraProperty(
  name = "testString",
  type = "String",
  defaultValue = "test value"
)
public class BTestCorrectSyntax extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestCorrectSyntax(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "testString"

  /**
   * Slot for the {@code testString} property.
   * @see #getTestString
   * @see #setTestString
   */
  public static final Property testString = newProperty(0, "test value", null);

  /**
   * Get the {@code testString} property.
   * @see #testString
   */
  public String getTestString() { return getString(testString); }

  /**
   * Set the {@code testString} property.
   * @see #testString
   */
  public void setTestString(String v) { setString(testString, v, null); }

  //endregion Property "testString"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestCorrectSyntax.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestCorrectSyntax() {
    super();
    System.out.println("🧪 TestCorrectSyntax - Using working example syntax");
  }
}
