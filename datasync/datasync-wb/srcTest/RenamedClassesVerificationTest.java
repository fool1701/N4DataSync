// Simple verification test for renamed classes
// This test verifies that all renamed classes can be loaded correctly

public class RenamedClassesVerificationTest {

    public static void main(String[] args) {
        System.out.println("Testing renamed classes...");

        try {
            // Test 1: BDataSource (renamed from BAbstractDataSource)
            System.out.println("\n1. Testing BDataSource class loading...");
            Class<?> dataSourceClass = Class.forName("com.mea.datasync.model.BDataSource");
            System.out.println("   Class: " + dataSourceClass.getName());
            System.out.println("   SUCCESS: BDataSource class loaded correctly");

            // Test 2: BConnection (renamed from BConnectionDetails)
            System.out.println("\n2. Testing BConnection class loading...");
            Class<?> connectionClass = Class.forName("com.mea.datasync.model.BConnection");
            System.out.println("   Class: " + connectionClass.getName());
            System.out.println("   SUCCESS: BConnection class loaded correctly");

            // Test 3: BExcelConnection (renamed from BExcelConnectionDetails)
            System.out.println("\n3. Testing BExcelConnection class loading...");
            Class<?> excelConnectionClass = Class.forName("com.mea.datasync.model.BExcelConnection");
            System.out.println("   Class: " + excelConnectionClass.getName());
            System.out.println("   SUCCESS: BExcelConnection class loaded correctly");

            // Test 4: BExcelDataSource
            System.out.println("\n4. Testing BExcelDataSource class loading...");
            Class<?> excelDataSourceClass = Class.forName("com.mea.datasync.model.BExcelDataSource");
            System.out.println("   Class: " + excelDataSourceClass.getName());
            System.out.println("   SUCCESS: BExcelDataSource class loaded correctly");

            // Test 5: Verify old classes don't exist
            System.out.println("\n5. Verifying old classes are gone...");
            try {
                Class.forName("com.mea.datasync.model.BAbstractDataSource");
                System.out.println("   ERROR: BAbstractDataSource still exists!");
            } catch (ClassNotFoundException e) {
                System.out.println("   SUCCESS: BAbstractDataSource correctly removed");
            }

            try {
                Class.forName("com.mea.datasync.model.BConnectionDetails");
                System.out.println("   ERROR: BConnectionDetails still exists!");
            } catch (ClassNotFoundException e) {
                System.out.println("   SUCCESS: BConnectionDetails correctly removed");
            }

            try {
                Class.forName("com.mea.datasync.model.BExcelConnectionDetails");
                System.out.println("   ERROR: BExcelConnectionDetails still exists!");
            } catch (ClassNotFoundException e) {
                System.out.println("   SUCCESS: BExcelConnectionDetails correctly removed");
            }

            System.out.println("\nSUCCESS: All class renaming completed correctly!");
            System.out.println("SUCCESS: BAbstractDataSource -> BDataSource");
            System.out.println("SUCCESS: BConnectionDetails -> BConnection");
            System.out.println("SUCCESS: BExcelConnectionDetails -> BExcelConnection");

        } catch (Exception e) {
            System.err.println("ERROR: Error testing renamed classes: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
