package com.mea.datasync.test.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Factory for creating test Excel files and data processing scenarios.
 * 
 * This factory provides utilities for testing Excel file processing, including:
 * - Creating test Excel files with various data scenarios
 * - Generating malformed files for error testing
 * - Creating large files for performance testing
 * - Simulating different Excel formats and structures
 * 
 * Note: This is a placeholder implementation. In a real implementation,
 * you would use Apache POI to create actual Excel files.
 * 
 * @author N4-DataSync Team
 * @version 1.0
 * @since 2025-01-01
 */
public class ExcelTestDataFactory {
    
    private static final String TEST_FILES_DIR = "test-files";
    private static final String EXCEL_EXTENSION = ".xlsx";
    
    /**
     * Create a valid Excel file with standard BACnet data structure.
     * 
     * @return File object pointing to the created test Excel file
     */
    public static File createValidBACnetExcelFile() {
        String fileName = "Valid_BACnet_" + UUID.randomUUID() + EXCEL_EXTENSION;
        File testFile = new File(TEST_FILES_DIR, fileName);
        
        try {
            createTestFileDirectory();
            
            // Placeholder: In real implementation, use Apache POI to create Excel file
            // with proper BACnet data structure (Network, Device, Point columns)
            createPlaceholderFile(testFile, "Valid BACnet Excel data");
            
            return testFile;
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to create test Excel file", e);
        }
    }
    
    /**
     * Create an Excel file with invalid data for error testing.
     * 
     * @return File object pointing to the created invalid Excel file
     */
    public static File createInvalidExcelFile() {
        String fileName = "Invalid_Data_" + UUID.randomUUID() + EXCEL_EXTENSION;
        File testFile = new File(TEST_FILES_DIR, fileName);
        
        try {
            createTestFileDirectory();
            createPlaceholderFile(testFile, "Invalid Excel data");
            return testFile;
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to create invalid test Excel file", e);
        }
    }
    
    /**
     * Create a large Excel file for performance testing.
     * 
     * @param rowCount number of data rows to include
     * @return File object pointing to the created large Excel file
     */
    public static File createLargeExcelFile(int rowCount) {
        String fileName = "Large_" + rowCount + "_rows_" + UUID.randomUUID() + EXCEL_EXTENSION;
        File testFile = new File(TEST_FILES_DIR, fileName);
        
        try {
            createTestFileDirectory();
            createPlaceholderFile(testFile, "Large Excel file with " + rowCount + " rows");
            return testFile;
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to create large test Excel file", e);
        }
    }
    
    /**
     * Create an Excel file with special characters for robustness testing.
     * 
     * @return File object pointing to the created special character Excel file
     */
    public static File createSpecialCharacterExcelFile() {
        String fileName = "Special_Chars_äöü_测试_" + UUID.randomUUID() + EXCEL_EXTENSION;
        File testFile = new File(TEST_FILES_DIR, fileName);
        
        try {
            createTestFileDirectory();
            createPlaceholderFile(testFile, "Excel data with special characters: äöü 测试 🚀");
            return testFile;
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to create special character test Excel file", e);
        }
    }
    
    /**
     * Create an empty Excel file for edge case testing.
     * 
     * @return File object pointing to the created empty Excel file
     */
    public static File createEmptyExcelFile() {
        String fileName = "Empty_" + UUID.randomUUID() + EXCEL_EXTENSION;
        File testFile = new File(TEST_FILES_DIR, fileName);
        
        try {
            createTestFileDirectory();
            createPlaceholderFile(testFile, "");
            return testFile;
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to create empty test Excel file", e);
        }
    }
    
    /**
     * Create a corrupted Excel file for error handling testing.
     * 
     * @return File object pointing to the created corrupted Excel file
     */
    public static File createCorruptedExcelFile() {
        String fileName = "Corrupted_" + UUID.randomUUID() + EXCEL_EXTENSION;
        File testFile = new File(TEST_FILES_DIR, fileName);
        
        try {
            createTestFileDirectory();
            // Create a file with invalid Excel content
            createPlaceholderFile(testFile, "This is not valid Excel content - corrupted file");
            return testFile;
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to create corrupted test Excel file", e);
        }
    }
    
    /**
     * Clean up all test files created by this factory.
     */
    public static void cleanupTestFiles() {
        File testDir = new File(TEST_FILES_DIR);
        if (testDir.exists() && testDir.isDirectory()) {
            File[] files = testDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().contains("Test_") || 
                        file.getName().contains("Valid_") ||
                        file.getName().contains("Invalid_") ||
                        file.getName().contains("Large_") ||
                        file.getName().contains("Special_") ||
                        file.getName().contains("Empty_") ||
                        file.getName().contains("Corrupted_")) {
                        file.delete();
                    }
                }
            }
        }
    }
    
    /**
     * Get the test files directory path.
     * 
     * @return String path to test files directory
     */
    public static String getTestFilesDirectory() {
        return TEST_FILES_DIR;
    }
    
    // Helper methods
    
    private static void createTestFileDirectory() {
        File testDir = new File(TEST_FILES_DIR);
        if (!testDir.exists()) {
            testDir.mkdirs();
        }
    }
    
    private static void createPlaceholderFile(File file, String content) throws IOException {
        // This is a placeholder implementation
        // In a real implementation, you would use Apache POI to create proper Excel files
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content.getBytes());
        }
    }
    
    /**
     * Builder pattern for creating customized Excel test files.
     */
    public static class ExcelFileBuilder {
        private String fileName;
        private int rowCount = 10;
        private boolean includeHeaders = true;
        private boolean includeSpecialChars = false;
        private boolean makeCorrupted = false;
        
        public ExcelFileBuilder withFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }
        
        public ExcelFileBuilder withRowCount(int rowCount) {
            this.rowCount = rowCount;
            return this;
        }
        
        public ExcelFileBuilder withHeaders(boolean includeHeaders) {
            this.includeHeaders = includeHeaders;
            return this;
        }
        
        public ExcelFileBuilder withSpecialCharacters(boolean includeSpecialChars) {
            this.includeSpecialChars = includeSpecialChars;
            return this;
        }
        
        public ExcelFileBuilder asCorrupted(boolean makeCorrupted) {
            this.makeCorrupted = makeCorrupted;
            return this;
        }
        
        public File build() {
            if (fileName == null) {
                fileName = "Custom_" + UUID.randomUUID() + EXCEL_EXTENSION;
            }
            
            File testFile = new File(TEST_FILES_DIR, fileName);
            
            try {
                createTestFileDirectory();
                
                StringBuilder content = new StringBuilder();
                if (includeHeaders) {
                    content.append("Network,Device,Point,Type,Units\n");
                }
                
                for (int i = 0; i < rowCount; i++) {
                    content.append("Network").append(i).append(",");
                    content.append("Device").append(i).append(",");
                    content.append("Point").append(i).append(",");
                    content.append("Numeric,");
                    content.append("degC");
                    
                    if (includeSpecialChars) {
                        content.append("_äöü_测试");
                    }
                    
                    content.append("\n");
                }
                
                if (makeCorrupted) {
                    content = new StringBuilder("CORRUPTED_DATA_NOT_EXCEL");
                }
                
                createPlaceholderFile(testFile, content.toString());
                return testFile;
                
            } catch (IOException e) {
                throw new RuntimeException("Failed to create custom test Excel file", e);
            }
        }
    }
    
    /**
     * Create a builder for custom Excel files.
     * 
     * @return ExcelFileBuilder instance
     */
    public static ExcelFileBuilder builder() {
        return new ExcelFileBuilder();
    }
}
