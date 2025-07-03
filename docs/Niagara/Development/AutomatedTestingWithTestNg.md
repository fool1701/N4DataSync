============================================================
PAGE 69
============================================================

# Niagara AX Automated Testing with TestNg
## Overview
 Prior to Niagara AX 3.8, the Niagara Framework contained its own testing framework, and test methods contained in a
 BTest subclass were required to follow a specific naming convention. These testing capabilities were closely modeled
 after JUnit, which is a well-known tool for unit testing of Java code. However, it has several shortcomings that make it
 unattractive for
Niagara testing. TestNG is a similar but more capable testing tool also widely used in the Java
 community, and it was selected as the tool we will use to generate and execute automated tests. The syntax for creating
 basic unit tests is very similar to JUnit 4. Additional functionality includes:
Flexible annotation-based test configuration (setup/teardown)
Automatic reporting of test results
Dependencies and sequencing
Data-driven testing and parameterized test methods
Parallel testing
Starting with 3.8, the Niagara build.exe tool supports co-located test code (that is, source code and test code are contained
 in the same module development folder) and the Niagara test module will support writing TestNG test methods. The
 syntax requires the use of annotations, so test classes must be compiled with at least Java 5. Review the
TestNG
 Documentation for additional information not covered here.
## Basic Test Case
### Test Package, Class, and Methods
Establish a srcTest folder in the module, and create the test packages and test source code there. Here is an example of a
 module with a test class:
Each test class should extend com.tridium.testng.BTestNg, and will include standard Baja code to declare the Type.
 TestNg will treat each test method as a single test case. A test method is defined with the @Test attribute:

```java
package com.acme.myModule.test;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import org.testng.Assert;
```

============================================================
PAGE 70
============================================================

```java
import org.testng.annotations.Test;
import com.acme.myModule.BFunctionType;
import com.tridium.testng.BTestNg;
public class BFunctionTypeTest extends BTestNg
{
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFunctionTypeTest.class);
  @Test
  public void addTest()
  { Assert.assertEquals(BFunctionType.add,
BFunctionType.make(BFunctionType.ADD)); }
}
```

There are several assert*() methods available in to test equality, null, true/false, etc. See the TestNg TestNg Javadocs for
 the complete list. The pass() and fail() methods for BTestNg have been integrated with TestNg, so the existing BTest
 verify*() methods can be used as well. Note that any test method that begins with
test will be considered a standard
 Niagara test case and will run when tests are executed with the -btest option:

```java
  @Test
  public void testAdd() { verify(BFunctionType.add ==
BFunctionType.make(BFunctionType.ADD)); }
```

### build.xml
Include a dependency on the test module and set the dependency, package, and resource attributes appropriately, so the
 resulting build will create a separate <moduleName>Test.jar module:

```xml
  <dependency name="test" vendor="Tridium" vendorVersion="3.8" test="true"/>
  <package name="com.acme.myModule.test" edition="j2se-5.0" test="true" />
```

Remember to include the edition= "j2se-5.0" attribute for any test packages.
### module-include.xml
Types for test classes must be declared, but there is nothing unique about these entries:

```xml
  <type name="FunctionTypeTest"
class="com.acme.myModule.test.BFunctionTypeTest"/>
```

### Compile and execute
Execute the build process with the -t option to generate the test module jar file:
  build <modulePath> -t
Execute the test command to run the defined tests under TestNg. The target for the tests is similar to existing Niagara
 tests:
all, <module>, <module>:<type>. Single-method execution is not currently supported. The output will look
 something like this:

```
D:\niagara\r38\dev>test myModule
 [TestNG] Running:
Command line suite
===============================================
myModuleTest_FunctionTypeTest
Total tests run: 5, Failures: 0, Skips: 0
===============================================
===============================================
Total Test Summary
Total tests run: 5, Failures: 0, Skips: 0
===============================================
```

============================================================
PAGE 71
============================================================

Output verbosity can be set using the option v:<n>, where n is an integer from 1 to 10. The higher the number, the more
 TestNG prints out.
Any existing Niagara tests (now contained in testTest.jar) can be executed with the -btest option.
## Additional TestNg Capabilities
### Test Setup/Teardown
One big advantage of TestNg over JUnit is the flexible test configuration. These configurations are also accomplished with
 annotations. Setup and teardown methods can be established to run once per method, per class, per test group, or per
 test suite. The example below shows how to initialize and destroy a test station once for all test methods in a class, calling
 methods from BTest.

```java
private TestStationHandler handler;
private BStation station;
  @BeforeClass(alwaysRun=true)
  public void setup()
    throws Exception
  {
    handler = BTest.createTestStation();
    handler.startStation();
    station = handler.getStation();
  }

  @AfterClass(alwaysRun=true)
  public void cleanup()
    throws Exception
  {
    handler.stopStation();
    handler.releaseStation();
    handler = null;
  }
```

### Groups, Dependencies and Sequencing
A set of tests may be grouped together with the groups annotation attribute. Groups naming is currently up to the
 developer. One use of groups it to identify a collection of tests to execute in a Continuous Integration (CI) environment
 (CI is not provided by Niagara, please go to
this link for more information).
An example of the group attribute is below:

```java
  @Test(groups={"ci"})
  public void ngTestSimple()
  { Assert.assertEquals(Lexicon.make("test").getText("fee.text"), "Fee"); }
```

You may declare dependencies between test methods and groups using dependsOn* annotation attributes. For example,
 if you have a group of test that should run after other sets, just attach the dependsOnGroups attribute for each method in
 the group.
If you want to explicitly define a sequence of test method execution, use the
priority annotation attribute. The value is a
 positive integer, and lower priorities will be scheduled first. See the TestNg documentation for additonal information.
Important: If you implement groups and also use BeforeClass/AfterClass methods, be sure to attach the
 (alwaysRun=true) attribute to the BeforeClass/AfterClass annotations.
### Parameterized Tests
A set of similar test cases can be parameterized with a data source class that generates input to the test class. Again, the
 relationship is achieved with parameterized annotations. First you declare a data provider that creates an object array
 containing test method arguments for each instance of the test execution. In the example below, the test class takes two
 arguments, and each entry in the data provider array contains instances of those two argument types.
============================================================
PAGE 72
============================================================

```java
  @DataProvider(name="operation")
  public Object[][] createColumnData()
  {
    return new Object[][] {
        { new Integer(BFunctionType.ADD),      BFunctionType.add },
        { new Integer(BFunctionType.SUBTRACT), BFunctionType.subtract },
        { new Integer(BFunctionType.MULTIPLY), BFunctionType.multiply },
        { new Integer(BFunctionType.DIVIDE),   BFunctionType.divide }
    };
  }
```

Then, in the test class, you declare that the test method uses the data provider with the dataProvider annotation attribute.
 The test method will be called once for each entry in the data provider array.

```java
  @Test(dataProvider="operation")
  public void operationTest(int op, BFunctionType type)
  {
    Assert.assertEquals(BFunctionType.make(op), type);
  }
```

### Expected Exceptions
If your code can generate exceptions and you want to test these execution paths, you can tell a test method to expect
 particular exception types by using the expectedExceptions attribute with a list of exception classes. In the following
 test, an occurance of a NullPointerException will successfully pass the test. Any other exception type will fail the test.

```java
  @Test(expectedExceptions={java.lang.NullPointerException.class})
  public void ngTestException()
  {
    // code that should throw NullPointerException
  }
```
### Reporting
TestNg will generate XML and HTML reports each time it runs. By default, creates these in a
(baja.home)/reports/testng folder. The HTML report index.html contains detailed information about the test results. A
junitreports folder contains XML reports that follow the same format created by Ant/JUnit. There is also a static XML
report and an email-able static HTML report. The report location can be changed using the command line option -output:<path>.

### Eclipse Integration
There is an Eclipse plugin available for TestNg. If you are familiar with JUnit, test execution and reporting with TestNG
plugin is very similar. Generally, it is not practical to execute Niagara framework test other than simple unit tests directly
with the Eclipse plugin. The reason is that the Niagara class loading behavior and NRE initialization is not compatible
with the stand-alone TestNG container.

### Additional Test Execution Options
The Niagara test executable supports several options for tailoring the execution of tests to you needs. The usage and
options are outlined below:
usage:
test <target> [testNGOptions]
target:
all
<module>
<module>:<type>
<module>:<type>.<method>
testNGOptions:
-btest                  Run tests based on BTest (not BTestNg)
-v:<n>                  Set TestNG output verbosity level (1 - 10)
-groups:<a,b,c>         Comma-separated list of TestNG group names to
test
-excludegroups:<a,b,c>  Comma-separated list of TestNG group names to
skip
-output:<path>          Set the location for TestNG output
Unit tests developed with the Niagara Test framework from 3.7 and earlier are still supported with the -btest option.
TestNg groups can be included or excluded from tests.