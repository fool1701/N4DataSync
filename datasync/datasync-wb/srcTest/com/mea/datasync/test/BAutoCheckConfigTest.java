// In: com.mea.datasync.test
package com.mea.datasync.test;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.mea.datasync.model.BAutoCheckConfig;
import com.mea.datasync.test.utils.BaseTestClass;

/**
 * Comprehensive unit tests for BAutoCheckConfig.
 * Tests auto-check configuration functionality, validation,
 * and factory methods for different data source types.
 */
@NiagaraType
@Test(groups = {"datasync", "unit", "autocheck", "config"})
public class BAutoCheckConfigTest extends BaseTestClass {

  private BAutoCheckConfig autoCheckConfig;

  @Override
  protected void performBaseSetup() throws Exception {
    logTestStep("Setting up auto-check configuration test");
    
    autoCheckConfig = new BAutoCheckConfig();
    
    logTestStep("Auto-check config test setup completed");
  }

  @Override
  protected void performBaseTeardown() throws Exception {
    logTestStep("Cleaning up auto-check config test resources");
    
    autoCheckConfig = null;
    
    logTestStep("Auto-check config test teardown completed");
  }

////////////////////////////////////////////////////////////////
// Default Values Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"autocheck", "defaults"})
  public void testAutoCheckConfigDefaults() {
    logTestStep("Testing auto-check configuration default values");
    
    // Given: New auto-check configuration
    // When: Checking default values
    // Then: Should have expected defaults
    Assert.assertTrue(autoCheckConfig.getEnabled());
    Assert.assertEquals(autoCheckConfig.getCheckInterval().getMillis(), 5L * 60L * 1000L); // 5 minutes
    Assert.assertEquals(autoCheckConfig.getRetryCount(), 3);
    Assert.assertEquals(autoCheckConfig.getRetryDelay().getMillis(), 30L * 1000L); // 30 seconds
    Assert.assertEquals(autoCheckConfig.getFailureThreshold(), 3);
    Assert.assertEquals(autoCheckConfig.getStartupDelay().getMillis(), 60L * 1000L); // 1 minute
  }

////////////////////////////////////////////////////////////////
// Configuration Property Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"autocheck", "properties"})
  public void testAutoCheckConfigProperties() {
    logTestStep("Testing auto-check configuration property setters/getters");
    
    // Test enabled property
    autoCheckConfig.setEnabled(false);
    Assert.assertFalse(autoCheckConfig.getEnabled());
    
    autoCheckConfig.setEnabled(true);
    Assert.assertTrue(autoCheckConfig.getEnabled());
    
    // Test check interval
    BRelTime interval = BRelTime.make(10L * 60L * 1000L); // 10 minutes
    autoCheckConfig.setCheckInterval(interval);
    Assert.assertEquals(autoCheckConfig.getCheckInterval().getMillis(), interval.getMillis());
    
    // Test retry count
    autoCheckConfig.setRetryCount(5);
    Assert.assertEquals(autoCheckConfig.getRetryCount(), 5);
    
    // Test retry delay
    BRelTime delay = BRelTime.make(60L * 1000L); // 1 minute
    autoCheckConfig.setRetryDelay(delay);
    Assert.assertEquals(autoCheckConfig.getRetryDelay().getMillis(), delay.getMillis());
    
    // Test failure threshold
    autoCheckConfig.setFailureThreshold(5);
    Assert.assertEquals(autoCheckConfig.getFailureThreshold(), 5);
    
    // Test startup delay
    BRelTime startupDelay = BRelTime.make(2L * 60L * 1000L); // 2 minutes
    autoCheckConfig.setStartupDelay(startupDelay);
    Assert.assertEquals(autoCheckConfig.getStartupDelay().getMillis(), startupDelay.getMillis());
  }

////////////////////////////////////////////////////////////////
// Configuration Summary Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"autocheck", "summary"})
  public void testConfigurationSummaryEnabled() {
    logTestStep("Testing configuration summary when enabled");
    
    // Given: Enabled auto-check configuration
    autoCheckConfig.setEnabled(true);
    autoCheckConfig.setCheckInterval(BRelTime.make(5L * 60L * 1000L)); // 5 minutes
    autoCheckConfig.setRetryCount(3);
    autoCheckConfig.setFailureThreshold(2);
    
    // When: Getting configuration summary
    String summary = autoCheckConfig.getConfigurationSummary();
    
    // Then: Should contain key information
    Assert.assertTrue(summary.contains("Auto-check enabled"));
    Assert.assertTrue(summary.contains("interval"));
    Assert.assertTrue(summary.contains("3 retries"));
    Assert.assertTrue(summary.contains("2 failure threshold"));
  }

  @Test(groups = {"autocheck", "summary"})
  public void testConfigurationSummaryDisabled() {
    logTestStep("Testing configuration summary when disabled");
    
    // Given: Disabled auto-check configuration
    autoCheckConfig.setEnabled(false);
    
    // When: Getting configuration summary
    String summary = autoCheckConfig.getConfigurationSummary();
    
    // Then: Should indicate disabled
    Assert.assertEquals(summary, "Auto-check disabled");
  }

////////////////////////////////////////////////////////////////
// Configuration Validation Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"autocheck", "validation"})
  public void testValidConfiguration() {
    logTestStep("Testing valid configuration validation");
    
    // Given: Valid configuration
    autoCheckConfig.setCheckInterval(BRelTime.make(5L * 60L * 1000L)); // 5 minutes
    autoCheckConfig.setRetryCount(3);
    autoCheckConfig.setFailureThreshold(2);
    
    // When: Validating configuration
    boolean isValid = autoCheckConfig.isConfigurationValid();
    
    // Then: Should be valid
    Assert.assertTrue(isValid);
  }

  @Test(groups = {"autocheck", "validation"})
  public void testInvalidConfigurationShortInterval() {
    logTestStep("Testing invalid configuration with short interval");
    
    // Given: Too short check interval
    autoCheckConfig.setCheckInterval(BRelTime.make(500L)); // 0.5 seconds
    
    // When: Validating configuration
    boolean isValid = autoCheckConfig.isConfigurationValid();
    
    // Then: Should be invalid
    Assert.assertFalse(isValid);
  }

  @Test(groups = {"autocheck", "validation"})
  public void testInvalidConfigurationNegativeRetryCount() {
    logTestStep("Testing invalid configuration with negative retry count");
    
    // Given: Negative retry count
    autoCheckConfig.setRetryCount(-1);
    
    // When: Validating configuration
    boolean isValid = autoCheckConfig.isConfigurationValid();
    
    // Then: Should be invalid
    Assert.assertFalse(isValid);
  }

  @Test(groups = {"autocheck", "validation"})
  public void testInvalidConfigurationHighRetryCount() {
    logTestStep("Testing invalid configuration with high retry count");
    
    // Given: Too high retry count
    autoCheckConfig.setRetryCount(15);
    
    // When: Validating configuration
    boolean isValid = autoCheckConfig.isConfigurationValid();
    
    // Then: Should be invalid
    Assert.assertFalse(isValid);
  }

  @Test(groups = {"autocheck", "validation"})
  public void testInvalidConfigurationZeroFailureThreshold() {
    logTestStep("Testing invalid configuration with zero failure threshold");
    
    // Given: Zero failure threshold
    autoCheckConfig.setFailureThreshold(0);
    
    // When: Validating configuration
    boolean isValid = autoCheckConfig.isConfigurationValid();
    
    // Then: Should be invalid
    Assert.assertFalse(isValid);
  }

  @Test(groups = {"autocheck", "validation"})
  public void testInvalidConfigurationHighFailureThreshold() {
    logTestStep("Testing invalid configuration with high failure threshold");
    
    // Given: Too high failure threshold
    autoCheckConfig.setFailureThreshold(150);
    
    // When: Validating configuration
    boolean isValid = autoCheckConfig.isConfigurationValid();
    
    // Then: Should be invalid
    Assert.assertFalse(isValid);
  }

////////////////////////////////////////////////////////////////
// Factory Method Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"autocheck", "factory", "excel"})
  public void testCreateExcelDefault() {
    logTestStep("Testing Excel default configuration factory method");
    
    // When: Creating Excel default configuration
    BAutoCheckConfig excelConfig = BAutoCheckConfig.createExcelDefault();
    
    // Then: Should have Excel-optimized settings
    Assert.assertTrue(excelConfig.getEnabled());
    Assert.assertEquals(excelConfig.getCheckInterval().getMillis(), 10L * 60L * 1000L); // 10 minutes
    Assert.assertEquals(excelConfig.getRetryCount(), 2);
    Assert.assertEquals(excelConfig.getFailureThreshold(), 2);
    Assert.assertTrue(excelConfig.isConfigurationValid());
  }

  @Test(groups = {"autocheck", "factory", "database"})
  public void testCreateDatabaseDefault() {
    logTestStep("Testing Database default configuration factory method");
    
    // When: Creating Database default configuration
    BAutoCheckConfig dbConfig = BAutoCheckConfig.createDatabaseDefault();
    
    // Then: Should have Database-optimized settings
    Assert.assertTrue(dbConfig.getEnabled());
    Assert.assertEquals(dbConfig.getCheckInterval().getMillis(), 2L * 60L * 1000L); // 2 minutes
    Assert.assertEquals(dbConfig.getRetryCount(), 3);
    Assert.assertEquals(dbConfig.getFailureThreshold(), 3);
    Assert.assertTrue(dbConfig.isConfigurationValid());
  }

  @Test(groups = {"autocheck", "factory", "web"})
  public void testCreateWebDefault() {
    logTestStep("Testing Web default configuration factory method");
    
    // When: Creating Web default configuration
    BAutoCheckConfig webConfig = BAutoCheckConfig.createWebDefault();
    
    // Then: Should have Web-optimized settings
    Assert.assertTrue(webConfig.getEnabled());
    Assert.assertEquals(webConfig.getCheckInterval().getMillis(), 5L * 60L * 1000L); // 5 minutes
    Assert.assertEquals(webConfig.getRetryCount(), 3);
    Assert.assertEquals(webConfig.getFailureThreshold(), 5); // Web services can be more flaky
    Assert.assertTrue(webConfig.isConfigurationValid());
  }

////////////////////////////////////////////////////////////////
// Factory Method Comparison Tests
////////////////////////////////////////////////////////////////

  @Test(groups = {"autocheck", "factory", "comparison"})
  public void testFactoryMethodDifferences() {
    logTestStep("Testing differences between factory method configurations");
    
    BAutoCheckConfig excelConfig = BAutoCheckConfig.createExcelDefault();
    BAutoCheckConfig dbConfig = BAutoCheckConfig.createDatabaseDefault();
    BAutoCheckConfig webConfig = BAutoCheckConfig.createWebDefault();
    
    // Excel should have longer intervals (files change less frequently)
    Assert.assertTrue(excelConfig.getCheckInterval().getMillis() > dbConfig.getCheckInterval().getMillis());
    
    // Web should have higher failure threshold (more tolerance for network issues)
    Assert.assertTrue(webConfig.getFailureThreshold() > dbConfig.getFailureThreshold());
    Assert.assertTrue(webConfig.getFailureThreshold() > excelConfig.getFailureThreshold());
    
    // Database should have shortest interval (real-time data)
    Assert.assertTrue(dbConfig.getCheckInterval().getMillis() <= excelConfig.getCheckInterval().getMillis());
    Assert.assertTrue(dbConfig.getCheckInterval().getMillis() <= webConfig.getCheckInterval().getMillis());
  }
}
