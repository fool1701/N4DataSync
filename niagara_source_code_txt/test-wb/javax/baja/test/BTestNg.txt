/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.test;

import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import com.tridium.testng.ConfigurationFailureListener;
import com.tridium.testng.TestRunnerNg;

@NiagaraType
@Listeners({ ConfigurationFailureListener.class })
public abstract class BTestNg
  extends BTest
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.test.BTestNg(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:31 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTestNg.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * @deprecated in Niagara 4.3
   */
  @Deprecated
  protected void pass()
  {
    // Call super.pass() if this is NOT a JUnit/TestNG test.
    TestRunnerNg.pass();
  }

  /**
   * @deprecated in Niagara 4.3 - Use org.testng.Assert instead.
   */
  @Deprecated
  protected void fail(String msg)
  {
    // Call super.fail(String msg) if this is NOT a JUnit/TestNG test.
    TestRunnerNg.fail(msg);
  }

  /**
   * PA.invokeMethod will not find any BObject classes when TestNg is running
   * with a non-ModuleClassLoader as the current context class loader.
   */
  @BeforeMethod(alwaysRun = true)
  public void setClassLoader() {
    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
      origClassLoader = Thread.currentThread().getContextClassLoader();
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
      return null;
    });
  }
  
  @AfterMethod(alwaysRun = true)
  public void restoreClassLoader() {
    AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
      Thread.currentThread().setContextClassLoader(origClassLoader);
      return null;
    });
  }
  
  private ClassLoader origClassLoader;
}
