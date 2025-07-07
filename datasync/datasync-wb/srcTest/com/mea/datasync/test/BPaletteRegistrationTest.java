package com.mea.datasync.test;

import javax.baja.sys.*;
import javax.baja.test.BTestNg;
import javax.baja.nre.annotations.NiagaraType;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.Assert;

/**
 * BPaletteRegistrationTest programmatically tests if our components
 * are properly registered in the Niagara type system and accessible
 * for palette registration.
 */
@NiagaraType
@Test(groups = {"ci"})
public class BPaletteRegistrationTest extends BTestNg {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.test.BPaletteRegistrationTest(2018014743)1.0$ @*/
/* Generated Mon Jul 07 21:00:00 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPaletteRegistrationTest.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Setup/Teardown
////////////////////////////////////////////////////////////////

  @BeforeClass(alwaysRun = true)
  public void setUp() {
    System.out.println("🧪 Starting Palette Registration Test");
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() {
    System.out.println("🧪 Palette Registration Test Complete");
  }

////////////////////////////////////////////////////////////////
// Component Registration Tests
////////////////////////////////////////////////////////////////

  @Test
  public void testComponentRegistration() {
    System.out.println("🔍 Testing component registration...");

    // Test ExcelDataSource
    try {
      Type excelType = Sys.getType("datasync:ExcelDataSource");
      if (excelType != null) {
        BObject instance = excelType.getInstance();
        System.out.println("✅ ExcelDataSource: " + excelType.getTypeName() + " -> " + instance.getClass().getSimpleName());
      } else {
        System.out.println("❌ ExcelDataSource type not found");
      }
    } catch (Exception e) {
      System.out.println("❌ ExcelDataSource error: " + e.getClass().getSimpleName() + ": " + e.getMessage());
      if (e.getCause() != null) {
        System.out.println("   Caused by: " + e.getCause().getClass().getSimpleName() + ": " + e.getCause().getMessage());
        if (e.getCause().getCause() != null) {
          System.out.println("   Root cause: " + e.getCause().getCause().getClass().getSimpleName() + ": " + e.getCause().getCause().getMessage());
        }
      }
      e.printStackTrace();
    }

    // Test TestSimplest
    try {
      Type testType = Sys.getType("datasync:TestSimplest");
      if (testType != null) {
        BObject instance = testType.getInstance();
        System.out.println("✅ TestSimplest: " + testType.getTypeName() + " -> " + instance.getClass().getSimpleName());
      } else {
        System.out.println("❌ TestSimplest type not found");
      }
    } catch (Exception e) {
      System.out.println("❌ TestSimplest error: " + e.getMessage());
    }

    // Test DataSourceFolder
    try {
      Type folderType = Sys.getType("datasync:DataSourceFolder");
      if (folderType != null) {
        BObject instance = folderType.getInstance();
        System.out.println("✅ DataSourceFolder: " + folderType.getTypeName() + " -> " + instance.getClass().getSimpleName());
      } else {
        System.out.println("❌ DataSourceFolder type not found");
      }
    } catch (Exception e) {
      System.out.println("❌ DataSourceFolder error: " + e.getMessage());
    }
  }

////////////////////////////////////////////////////////////////
// Module and Palette Tests
////////////////////////////////////////////////////////////////

  @Test
  public void testModuleAndPalette() {
    System.out.println("🔍 Testing datasync module and palette...");

    try {
      // Try to load the module
      BModule module = Sys.loadModule("datasync");
      if (module != null) {
        System.out.println("✅ Module loaded: " + module.getModuleName());

        // Test if module has palette
        boolean hasPalette = module.hasPalette();
        System.out.println("📋 Module has palette: " + hasPalette);

        if (hasPalette) {
          try {
            BObject palette = (BObject) module.getNavChild("module.palette");
            System.out.println("✅ Module palette accessible: " + palette.getClass().getSimpleName());

            // Try to get palette structure
            System.out.println("📋 Palette object: " + palette.toString());

          } catch (Exception pe) {
            System.out.println("❌ Palette access error: " + pe.getMessage());
            pe.printStackTrace();
          }
        } else {
          System.out.println("⚠️ Module does not have palette registered");
        }

        // Test module information
        System.out.println("📊 Module info: " + module.getModuleName());

      } else {
        System.out.println("❌ Module not found");
      }

    } catch (Exception e) {
      System.out.println("❌ Module test failed: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
