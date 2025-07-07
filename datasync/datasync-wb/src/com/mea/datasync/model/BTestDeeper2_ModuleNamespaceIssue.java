// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test: Is the issue with datasync module namespace references?
 * This uses only baja: types to avoid any module namespace issues
 */
@NiagaraType
@NiagaraProperty(
  name = "testString",
  type = "baja:String",
  defaultValue = "BString.make(\"test\")"
)
public class BTestDeeper2_ModuleNamespaceIssue extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTestDeeper2_ModuleNamespaceIssue(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "testString"

  /**
   * Slot for the {@code testString} property.
   * @see #getTestString
   * @see #setTestString
   */
  public static final Property testString = newProperty(0, BString.make("test"), null);

  /**
   * Get the {@code testString} property.
   * @see #testString
   */
  public BString getTestString() { return (BString)get(testString); }

  /**
   * Set the {@code testString} property.
   * @see #testString
   */
  public void setTestString(BString v) { set(testString, v, null); }

  //endregion Property "testString"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestDeeper2_ModuleNamespaceIssue.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTestDeeper2_ModuleNamespaceIssue() {
    super();
    System.out.println("🧪 TestDeeper2 - BComponent with only baja: types");
  }
}
