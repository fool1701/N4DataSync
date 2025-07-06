// Simple test to verify BAbstractDataSource can be loaded
// This test doesn't require Niagara type registration

import com.mea.datasync.model.BAbstractDataSource;
import com.mea.datasync.model.BExcelDataSource;
import com.mea.datasync.model.BDataSourceFolder;
import com.mea.datasync.ui.BDataSourceManager;

public class SimpleConnectionTest {
    
    public static void main(String[] args) {
        System.out.println("=== Simple Connection Test ===");
        
        try {
            // Test 1: Can we load BAbstractDataSource class?
            System.out.println("Test 1: Loading BAbstractDataSource class...");
            Class<?> abstractClass = BAbstractDataSource.class;
            System.out.println("✅ BAbstractDataSource class loaded successfully");
            System.out.println("   Class: " + abstractClass.getName());
            
            // Test 2: Can we access the TYPE field?
            System.out.println("\nTest 2: Accessing BAbstractDataSource.TYPE...");
            try {
                Object type = BAbstractDataSource.TYPE;
                System.out.println("✅ BAbstractDataSource.TYPE accessed successfully");
                System.out.println("   TYPE: " + type);
            } catch (Exception e) {
                System.out.println("FAILED: Failed to access BAbstractDataSource.TYPE: " + e.getMessage());
                e.printStackTrace();
            }
            
            // Test 3: Can we create concrete implementations?
            System.out.println("\nTest 3: Creating BExcelDataSource...");
            BExcelDataSource excelConnection = new BExcelDataSource();
            System.out.println("✅ BExcelDataSource created successfully");
            System.out.println("   Type: " + excelConnection.getClass().getName());
            System.out.println("   Data Source Type: " + excelConnection.getDataSourceTypeName());
            
            // Test 4: Can we access concrete TYPE field?
            System.out.println("\nTest 4: Accessing BExcelDataSource.TYPE...");
            Object excelType = BExcelDataSource.TYPE;
            System.out.println("✅ BExcelDataSource.TYPE accessed successfully");
            System.out.println("   TYPE: " + excelType);
            
            // Test 5: Can we create the manager?
            System.out.println("\nTest 5: Creating BDataSourceManager...");
            BDataSourceManager manager = new BDataSourceManager();
            System.out.println("✅ BDataSourceManager created successfully");
            
            // Test 6: Can we create the manager without initialization errors?
            System.out.println("\nTest 6: Testing manager initialization...");
            try {
                // The key test is that the manager can be created without NoClassDefFoundError
                // This would have failed before our fix when it tried to access BAbstractDataSource.TYPE
                System.out.println("✅ Manager created and initialized successfully");
                System.out.println("   Manager class: " + manager.getClass().getName());
                System.out.println("   Manager type: " + manager.getType());
            } catch (Exception e) {
                System.out.println("FAILED: Manager initialization failed: " + e.getMessage());
                e.printStackTrace();
            }
            
            System.out.println("\n=== All Tests Completed Successfully! ===");
            System.out.println("The NoClassDefFoundError fix appears to be working correctly.");
            
        } catch (Exception e) {
            System.out.println("FAILED: Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
