package com.mea.datasync.test.utils;

import javax.baja.test.BTestNg;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

/**
 * Base class for all DataSync tests providing common setup and utilities.
 *
 * This class establishes the foundation for enterprise-grade testing by providing:
 * - Common test lifecycle management
 * - Standardized test naming and identification
 * - Performance tracking for test execution
 * - Consistent setup and teardown patterns
 *
 * All DataSync test classes should extend this base class to ensure
 * consistent testing practices across the project.
 *
 * Note: This is a utility base class and is not registered as a Niagara type.
 * Only concrete test classes should be registered as Niagara types.
 *
 * @author N4-DataSync Team
 * @version 1.0
 * @since 2025-01-01
 */
public abstract class BaseTestClass extends BTestNg {
    
    // Test naming constants
    protected static final String TEST_PREFIX = "Test_";
    protected static final String SEPARATOR = "_";
    
    // Test execution tracking
    protected long testStartTime;
    protected String testClassName;
    
    /**
     * Base setup method called before all tests in the class.
     * Performs common initialization and delegates to subclass-specific setup.
     */
    @BeforeClass(alwaysRun = true)
    public void baseSetUp() throws Exception {
        testStartTime = System.currentTimeMillis();
        testClassName = getClass().getSimpleName();
        
        System.out.println("🧪 === Starting " + testClassName + " ===");
        System.out.println("📅 Test execution started at: " + new java.util.Date());
        
        // Delegate to subclass-specific setup
        performBaseSetup();
        
        System.out.println("✅ " + testClassName + " setup completed");
    }
    
    /**
     * Base teardown method called after all tests in the class.
     * Performs cleanup and delegates to subclass-specific teardown.
     */
    @AfterClass(alwaysRun = true)
    public void baseTearDown() throws Exception {
        System.out.println("🧹 Starting " + testClassName + " cleanup...");
        
        // Delegate to subclass-specific teardown
        performBaseTeardown();
        
        // Calculate and report execution time
        long duration = System.currentTimeMillis() - testStartTime;
        System.out.println("⏱️  " + testClassName + " completed in " + duration + "ms");
        System.out.println("🏁 === Completed " + testClassName + " ===");
    }
    
    /**
     * Subclasses must implement this method to perform their specific setup.
     * This is called during the base setup phase.
     * 
     * @throws Exception if setup fails
     */
    protected abstract void performBaseSetup() throws Exception;
    
    /**
     * Subclasses must implement this method to perform their specific teardown.
     * This is called during the base teardown phase.
     * 
     * @throws Exception if teardown fails
     */
    protected abstract void performBaseTeardown() throws Exception;
    
    /**
     * Generate a unique test identifier for test data.
     * Format: Test_{ClassName}_{Timestamp}
     * 
     * @return unique test identifier
     */
    protected String generateTestId() {
        return TEST_PREFIX + testClassName + SEPARATOR + System.currentTimeMillis();
    }
    
    /**
     * Generate a unique test identifier with custom suffix.
     * Format: Test_{ClassName}_{Suffix}_{Timestamp}
     * 
     * @param suffix custom suffix for the identifier
     * @return unique test identifier with suffix
     */
    protected String generateTestId(String suffix) {
        return TEST_PREFIX + testClassName + SEPARATOR + suffix + SEPARATOR + System.currentTimeMillis();
    }
    
    /**
     * Generate a test name for profile creation.
     * Format: Test_{ClassName}_Profile_{Timestamp}
     * 
     * @return unique profile test name
     */
    protected String generateProfileTestName() {
        return generateTestId("Profile");
    }
    
    /**
     * Generate a test name for file operations.
     * Format: Test_{ClassName}_File_{Timestamp}
     * 
     * @return unique file test name
     */
    protected String generateFileTestName() {
        return generateTestId("File");
    }
    
    /**
     * Check if a string starts with the test prefix.
     * Useful for identifying test data during cleanup.
     * 
     * @param name the name to check
     * @return true if the name starts with test prefix
     */
    protected boolean isTestData(String name) {
        return name != null && name.startsWith(TEST_PREFIX);
    }
    
    /**
     * Log a test step for debugging and traceability.
     * 
     * @param step the step description
     */
    protected void logTestStep(String step) {
        System.out.println("🔍 [" + testClassName + "] " + step);
    }
    
    /**
     * Log test data for debugging.
     * 
     * @param label the data label
     * @param value the data value
     */
    protected void logTestData(String label, Object value) {
        System.out.println("📊 [" + testClassName + "] " + label + ": " + value);
    }
    
    /**
     * Assert that a test operation completed within expected time.
     * 
     * @param startTime the operation start time in nanoseconds
     * @param maxDurationMs maximum allowed duration in milliseconds
     * @param operation description of the operation
     */
    protected void assertPerformance(long startTime, long maxDurationMs, String operation) {
        long durationMs = (System.nanoTime() - startTime) / 1_000_000;
        if (durationMs > maxDurationMs) {
            throw new AssertionError(operation + " took " + durationMs + "ms, expected < " + maxDurationMs + "ms");
        }
        logTestData(operation + " duration", durationMs + "ms");
    }
    
    /**
     * Sleep for a specified duration with proper exception handling.
     * Useful for timing-sensitive tests.
     * 
     * @param milliseconds duration to sleep
     */
    protected void safeSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrupted during sleep", e);
        }
    }
    
    /**
     * Get the current test execution time in milliseconds.
     * 
     * @return milliseconds since test class started
     */
    protected long getTestExecutionTime() {
        return System.currentTimeMillis() - testStartTime;
    }
}
