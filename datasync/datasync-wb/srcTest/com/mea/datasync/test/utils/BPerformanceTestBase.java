package com.mea.datasync.test.utils;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for performance testing in N4-DataSync.
 * 
 * Provides specialized utilities for performance and load testing including:
 * - Execution time measurement and validation
 * - Memory usage monitoring
 * - Throughput testing
 * - Load testing with multiple concurrent operations
 * - Performance regression detection
 * 
 * This class extends BaseTestClass to inherit all standard testing utilities
 * while adding performance-specific capabilities.
 * 
 * @author N4-DataSync Team
 * @version 1.0
 * @since 2025-01-01
 */
@NiagaraType
public abstract class BPerformanceTestBase extends BaseTestClass {

    public static final Type TYPE = Sys.loadType(BPerformanceTestBase.class);
    @Override public Type getType() { return TYPE; }
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.test.utils.BPerformanceTestBase(2979906276)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPerformanceTestBase.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
    
    // Performance testing constants
    protected static final long PROFILE_CREATION_MAX_MS = 100;
    protected static final long PROFILE_LOADING_MAX_MS = 50;
    protected static final long JSON_SERIALIZATION_MAX_MS = 25;
    protected static final long UI_REFRESH_MAX_MS = 200;
    protected static final long EXCEL_PARSING_MAX_MS = 1000;
    protected static final long COMPONENT_CREATION_MAX_MS = 500;
    
    // Performance tracking
    protected List<Long> executionTimes;
    protected long memoryBefore;
    protected long memoryAfter;
    
    @Override
    protected void performBaseSetup() throws Exception {
        logTestStep("Setting up performance test environment");
        setupPerformanceTestEnvironment();
        performPerformanceSetup();
    }
    
    @Override
    protected void performBaseTeardown() throws Exception {
        logTestStep("Cleaning up performance test environment");
        performPerformanceTeardown();
        cleanupPerformanceTestEnvironment();
    }
    
    /**
     * Subclasses implement this for performance-specific setup.
     */
    protected abstract void performPerformanceSetup() throws Exception;
    
    /**
     * Subclasses implement this for performance-specific teardown.
     */
    protected abstract void performPerformanceTeardown() throws Exception;
    
    /**
     * Set up the performance testing environment.
     */
    private void setupPerformanceTestEnvironment() {
        executionTimes = new ArrayList<>();
        
        // Force garbage collection before performance tests
        System.gc();
        safeSleep(100); // Allow GC to complete
        
        memoryBefore = getUsedMemory();
        logTestData("Memory before test", memoryBefore + " bytes");
    }
    
    /**
     * Clean up the performance testing environment.
     */
    private void cleanupPerformanceTestEnvironment() {
        memoryAfter = getUsedMemory();
        logTestData("Memory after test", memoryAfter + " bytes");
        logTestData("Memory delta", (memoryAfter - memoryBefore) + " bytes");
        
        if (!executionTimes.isEmpty()) {
            double avgTime = executionTimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
            long minTime = executionTimes.stream().mapToLong(Long::longValue).min().orElse(0);
            long maxTime = executionTimes.stream().mapToLong(Long::longValue).max().orElse(0);
            
            logTestData("Average execution time", avgTime + " ms");
            logTestData("Min execution time", minTime + " ms");
            logTestData("Max execution time", maxTime + " ms");
        }
    }
    
    /**
     * Start timing an operation.
     * 
     * @return start time in nanoseconds
     */
    protected long startTiming() {
        return System.nanoTime();
    }
    
    /**
     * Stop timing and record the execution time.
     * 
     * @param startTime start time from startTiming()
     * @return execution time in milliseconds
     */
    protected long stopTiming(long startTime) {
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1_000_000;
        executionTimes.add(durationMs);
        return durationMs;
    }
    
    /**
     * Assert that an operation completed within the expected time.
     * 
     * @param startTime operation start time in nanoseconds
     * @param maxDurationMs maximum allowed duration in milliseconds
     * @param operation description of the operation
     */
    protected void assertPerformance(long startTime, long maxDurationMs, String operation) {
        long durationMs = stopTiming(startTime);
        Assert.assertTrue(durationMs <= maxDurationMs, 
                         operation + " took " + durationMs + "ms, expected <= " + maxDurationMs + "ms");
        logTestData(operation + " performance", durationMs + "ms (limit: " + maxDurationMs + "ms)");
    }
    
    /**
     * Assert profile creation performance.
     */
    protected void assertProfileCreationPerformance(long startTime) {
        assertPerformance(startTime, PROFILE_CREATION_MAX_MS, "Profile creation");
    }
    
    /**
     * Assert profile loading performance.
     */
    protected void assertProfileLoadingPerformance(long startTime) {
        assertPerformance(startTime, PROFILE_LOADING_MAX_MS, "Profile loading");
    }
    
    /**
     * Assert JSON serialization performance.
     */
    protected void assertJsonSerializationPerformance(long startTime) {
        assertPerformance(startTime, JSON_SERIALIZATION_MAX_MS, "JSON serialization");
    }
    
    /**
     * Assert UI refresh performance.
     */
    protected void assertUIRefreshPerformance(long startTime) {
        assertPerformance(startTime, UI_REFRESH_MAX_MS, "UI refresh");
    }
    
    /**
     * Assert Excel parsing performance.
     */
    protected void assertExcelParsingPerformance(long startTime) {
        assertPerformance(startTime, EXCEL_PARSING_MAX_MS, "Excel parsing");
    }
    
    /**
     * Assert component creation performance.
     */
    protected void assertComponentCreationPerformance(long startTime) {
        assertPerformance(startTime, COMPONENT_CREATION_MAX_MS, "Component creation");
    }
    
    /**
     * Assert that memory usage is within acceptable limits.
     * 
     * @param maxMemoryIncreaseMB maximum allowed memory increase in MB
     * @param operation description of the operation
     */
    protected void assertMemoryUsage(long maxMemoryIncreaseMB, String operation) {
        long memoryIncrease = memoryAfter - memoryBefore;
        long memoryIncreaseMB = memoryIncrease / (1024 * 1024);
        
        Assert.assertTrue(memoryIncreaseMB <= maxMemoryIncreaseMB,
                         operation + " used " + memoryIncreaseMB + "MB, expected <= " + maxMemoryIncreaseMB + "MB");
        logTestData(operation + " memory usage", memoryIncreaseMB + "MB (limit: " + maxMemoryIncreaseMB + "MB)");
    }
    
    /**
     * Assert throughput performance.
     * 
     * @param operationCount number of operations performed
     * @param totalTimeMs total time taken in milliseconds
     * @param minOperationsPerSecond minimum expected operations per second
     * @param operation description of the operation
     */
    protected void assertThroughput(int operationCount, long totalTimeMs, 
                                   double minOperationsPerSecond, String operation) {
        double actualOperationsPerSecond = (operationCount * 1000.0) / totalTimeMs;
        
        Assert.assertTrue(actualOperationsPerSecond >= minOperationsPerSecond,
                         operation + " throughput was " + actualOperationsPerSecond + " ops/sec, expected >= " + 
                         minOperationsPerSecond + " ops/sec");
        logTestData(operation + " throughput", actualOperationsPerSecond + " ops/sec");
    }
    
    /**
     * Perform a load test with multiple concurrent operations.
     * 
     * @param operationCount number of operations to perform
     * @param operation the operation to execute
     * @return total execution time in milliseconds
     */
    protected long performLoadTest(int operationCount, Runnable operation) {
        logTestStep("Starting load test with " + operationCount + " operations");
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < operationCount; i++) {
            operation.run();
            
            // Log progress for large tests
            if (operationCount > 100 && i % (operationCount / 10) == 0) {
                logTestStep("Load test progress: " + (i * 100 / operationCount) + "%");
            }
        }
        
        long totalTime = System.currentTimeMillis() - startTime;
        logTestData("Load test completed", operationCount + " operations in " + totalTime + "ms");
        
        return totalTime;
    }
    
    /**
     * Get current memory usage.
     * 
     * @return used memory in bytes
     */
    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    
    /**
     * Generate a performance test name.
     */
    protected String generatePerformanceTestName() {
        return generateTestId("Performance");
    }
    
    /**
     * Generate a load test name.
     */
    protected String generateLoadTestName() {
        return generateTestId("Load");
    }
    
    /**
     * Warm up the JVM before performance testing.
     */
    protected void warmUpJVM(Runnable operation, int iterations) {
        logTestStep("Warming up JVM with " + iterations + " iterations");
        for (int i = 0; i < iterations; i++) {
            operation.run();
        }
        System.gc();
        safeSleep(100);
        logTestStep("JVM warm-up completed");
    }
}
