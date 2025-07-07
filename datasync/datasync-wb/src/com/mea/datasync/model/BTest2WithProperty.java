// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Test 2: BAbstractDataSource with simple property (no override, simple default)
 * Tests if the issue is with property definitions on inherited classes
 */
@NiagaraType
@NiagaraProperty(
  name = "testProperty",
  type = "baja:String",
  defaultValue = "BString.make(\"test\")"
)
public class BTest2WithProperty extends BAbstractDataSource {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BTest2WithProperty(2018014743)1.0$ @*/
/* Generated Mon Jul 07 07:30:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "testProperty"

  /**
   * Slot for the {@code testProperty} property.
   * @see #getTestProperty
   * @see #setTestProperty
   */
  public static final Property testProperty = newProperty(0, BString.make("test"), null);

  /**
   * Get the {@code testProperty} property.
   * @see #testProperty
   */
  public BString getTestProperty() { return (BString)get(testProperty); }

  /**
   * Set the {@code testProperty} property.
   * @see #testProperty
   */
  public void setTestProperty(BString v) { set(testProperty, v, null); }

  //endregion Property "testProperty"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTest2WithProperty.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BTest2WithProperty() {
    super();
    System.out.println("🧪 Test2 With Property created");
  }
}
