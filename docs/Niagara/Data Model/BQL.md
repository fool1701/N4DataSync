### Aggregate Functions

BQL provides the following built-in aggregate functions:

1.  `COUNT(<expresion>)`: count the number of items in the result set. Supports special syntax `COUNT(*)`.
2.  `MAX(<expression>)`: evaluates the expression for every item in the result set and returns the maximum value. The expression must evaluate to a `BNumber` or `BStatusNumeric`.
3.  `MIN(<expression>)`: evaluates the expression for every item in the result set and returns the minimum value. The expression must evaluate to a `BNumber` or `BStatusNumeric`.
4.  `SUM(<expression>)`: evaluates the expression for every item in the result set and returns the sum of all the values. The expression must evaluate to a `BNumber` or `BStatusNumeric`.
5.  `AVG(<expression>)`: evaluates the expression for every item in the result set and returns the average of all the values. The expression must evaluate to a `BNumber` or `BStatusNumeric`.

```
select MAX(out), MIN(out), AVG(out), SUM(out) from control:NumericWritable
select substr(displayName, 0, 1), COUNT(*) from baja:Folder
```

The first query returns the max, min, average, and sum of all the out properties of all `control:NumericWritables`. The resulting table will have a single row with four columns. The second query gets the first letter of every folder and then counts how many folders start with that letter.

### User-defined Aggregate Functions

**Note:** The ability to create user-defined aggregate functions is still considered experimental. The steps to create aggregate functions may change in the future.

In this example we show how to create and implement the `AVG()` aggregate function provided by BQL. Creating an aggregate function is a two-step process.

**Step 1: Create the aggregator class**

-   Create a class that extends `BObject` and implements the "marker" `javax.baja.bql.BIAggregator` interface. This interface has no methods, it just serves to signal the BQL engine that the class has aggregator semantics.
-   By convention, you must have a public void method called "aggregate" with a single parameter that is the type you want to aggregate on. This method will be called for each object in the result set. This is where the aggregating should be done.
-   By convention, you must have a "public <Type>" method called "commit()" that returns the aggregate value. This will be called on your class when all the objects have been aggregated. This gives you a chance to do any further calculation before returning the result.

**Step 2: Declare the aggregator**

-   Declare a `public static final Type[] <function name>` in one of your module's classes so that BQL can find it when given a `BTypeSpec` invocation of the aggregate function. The `<function name>` is the actual function name that would be used in a bql statement. If you have multiple implementations of the aggregate function (perhaps to support different argument types), include them all in the array. The BQL engine will search the list of implementing classes until it finds one that implements an `aggregate(<type>)` method that matches the type of the current object.

Here is an implementation of AVG that supports averaging `BNumber`s and `BStatusNumeric`s (Step 1):

```java
public final class BAverage extends BObject implements BIAggregator {
  /** Aggregate a BNumber */
  public void aggregate(BNumber value) {
    ++count;
    sum += value.getDouble();
  }
  /** Only aggregates if the status is valid. Otherwise it is skipped */
  public void aggregate(BStatusNumeric value) {
    if (value.getStatus().isValid()) {
      ++count;
      sum += value.getValue();
    }
  }
  /** Calculate the average and return the result */
  public BDouble commit() {
    if (count == 0)
      return BDouble.NaN;
    else
      return BDouble.make(sum/count);
  }
  public static final Type TYPE = Sys.loadType(BAvg.class);
  public Type getType() { return TYPE; }
  private double sum;
  private long count;
}
```

Here is how we can modify the `BLib` class to define the AVG function (Step 2):

```java
public BLib extends BObject {
  /** Define the strlen function */
  public static BInteger strlen(BObject target, BString str) {
    return BInteger.make(str.getString().length());
  }
  // Declare the AVG aggregate function (step 2)
  public static final Type[] avg = { BAverage.TYPE };
  public static final Type TYPE = Sys.loadType(BBLib.class);
  public Type getType() { return TYPE; }
}
```

Note that the name of the aggregate function is determined by its declaration in step 2, it is NOT the name of the class that implements the aggregation logic. Also, aggregate names are case-insensitive. Here is how you would call your implementation of the average aggregate function (note the use of the `BTypeSpec`):

```
select MyBql:Lib.avg(out) from control:NumericWritable
```

## BQL from Java

BQL query results can easily be displayed in a table or chart in a user interface. However, the results may also be examined in code using the Baja API. The result of a "select" query is always a `BITable`. The items in the table depend on the query. If the projection is omitted, the result is a table of objects in the extent that matched the predicate requirements.

```java
BOrd ord = BOrd.make("slot:/foo/bar|bql:select from control:NumericPoint");
BITable result = (BITable)ord.resolve(base).get();
Cursor c = result.cursor();
double total = 0d;
while (c.next())
{
  total += ((BNumericPoint)c.get()).getOut().getValue();
}
```

If the query has a projection, the result is a `BITable` and must be accessed that way to get the column data.

```java
BOrd ord = BOrd.make("slot:/foo/bar|bql:select name, out.value from control:NumericPoint");
BITable result = (BITable)ord.resolve(base).get();
ColumnList columns = result.getColumns();
Column valueColumn = columns.get(1);
TableCursor c = (TableCursor)result.cursor();
double total = 0d;
while (c.next())
{
  total += ((BINumeric)c.get(valueColumn)).getNumeric();
}
```

Since Niagara AX 3.5 you have been able to perform BQL queries against unmounted components. This is useful when you are programmatically constructing component trees, and want to query the tree structure, but the components are not mounted in a station or bog. The example below illustrates how to do this.

```java
// NOTE: using setOut() for numeric writables because set() doesn't work when not mounted.
BFolder folder = new BFolder();
BNumericWritable nw1 = new BNumericWritable();
nw1.setOut(new BStatusNumeric(50.0));
folder.add("a", nw1);
nw1 = new BNumericWritable();
nw1.setOut(new BStatusNumeric(100.0));
folder.add("b", nw1);
String bql = "select sum(out.value) from control:NumericWritable";
// Create the unmounted OrdTarget using new "unmounted" factory method
OrdTarget target = OrdTarget.unmounted(folder);
// Query the unmounted folder to get the sum of all children
// control:NumericWritables out.value values.
BITable coll = (BITable)BqlQuery.make(bql).resolve(target).get();
```

## BQL Expressions

[Back to BQL Overview](#bql)

BQL Expressions are used in the `where` clause of a BQL query to further qualify a result by narrowing the set of objects in the extent.

### Operator Precedence

BQL supports the following set of operators ordered by precedence:

1.  `!`, `not`, `-` (logical not, numeric negation)
2.  `*`, `/` (multiplication, division)
3.  `+`, `-` (addition, subtraction)
4.  `=`, `!=`, `>`, `>=`, `<`, `<=`
5.  `like`, `in` (comparisons)
6.  `and`, `or` (logical operators)

Parentheses can be used to override the normal precedence.

### Typed Literals

All primitive types and `BSimple` types can be expressed as literals in BQL. The syntax for primitives types is:

-   **String**: single quoted string
    -   Example: `'This is a string literal'`
-   **number**: a numeric value, unquoted
    -   Example: `10`
-   **boolean**: `true` or `false`, unquoted
    -   Example: `true`
-   **enum**: The enum type spec followed by the tag separated by a dot.
    -   Example: `alarm:SourceState.normal`

Expressing other `BSimple` types in BQL is more verbose because a type specifier is required. The syntax for a `BSimple` value is the type spec (i.e. `moduleName:typeName`) followed by a string literal with the string encoding of the value (the result of `encodeToString()` for the type).

-   Example: `baja:RelTime '10000'`

Baja types are expressed in BQL using the type spec. Any type spec that is not followed by a quoted string refers to the type itself.

-   Example: `where out.type = baja:StatusNumeric`

## BQL Examples

[Back to BQL Overview](#bql)

This document is a collection of example queries that illustrate how to identify some common sets of data with BQL. While each example in this document only presents a single solution, keep in mind that in most cases there are several different ways get the same result.

### All points

```
select slotPath, out from control:ControlPoint
```

The result is the slot path and output value of all control points. Since we specified "out" the result is the combination of value and status. If we wanted just the value, we would have used `out.value`. Or if we wanted value and status in separate columns we would have specified `out.value` and `out.status`.

### All points in alarm

```
select slotPath, out from control:ControlPoint where status.alarm
```

The result is the slot path and output value of all control points currently in the alarm state. In the where clause, the path "status.alarm" evaluates to true if the alarm status bit is set and false otherwise. This mechanism can be used to check the state of any of the status bits. See `BStatus` for more information on status flags.

### All points with "Meter" in their name

```
select slotPath, out from control:ControlPoint where name like '%Meter%'
```

The result is the slot path and output value of all points whose name includes the substring "Meter". BQL supports simple pattern matching. A `%` or `*` matches zero or more characters. A `_` matches exactly one character. The normal character matching is case sensitive.

### All points with a totalizer extension

```
select parent.slotPath, total from control:NumericTotalizerExt
```

The result is the slot path of every point that has a totalizer extension and the total for each totalizer. Note that the extent is the set of all totalizers. To get the point path, we look at the parent of each object in the extent.

### All current schedule output values

```
select slotPath, out from schedule:AbstractSchedule stop
```

The result is the slot path and output value of all schedules. Note the keyword "stop". The schedule component model makes the "stop" keyword necessary. All of the common schedule (`BooleanSchedule`, `NumericSchedule`, etc.) are actually composed of many more precise schedules. Without the "stop", the result would include all of the inner schedules in addition to the top level schedules that this query is actually looking for. The "stop" tells the query processor to stop the recursion when it reaches a component whose type matches the extent type.

### All points overridden at priority level 8

```
select slotPath, out from control:IWritablePoint
  where activeLevel = control:PriorityLevel.level_8
```

The result is the slot path and output value of all writable points that are currently overridden at priority level 8. I know that every writable point is an instance of `BIWritablePoint`. All writable points provide access to their active level with a method called `getActiveLevel()`. Following the pattern for translating method names to BQL fields, I can access the active level on writable points using "activeLevel". In this case I know that active level is represented by a `PriorityLevel` enum. The level 8 value of the enum is specified by `control:PriorityLevel.level_8`.

### All points with units of degrees fahrenheit

```
select slotPath from control:NumericPoint
  where facets.units.unitName = 'fahrenheit'
```

The key to this query is understanding how units are associated with a point. All control points have facets. For numeric points, the units are defined as a facet. So `facets.units` gets the units for the point. `BUnit` has a method called `getUnitName()` so "unitName" gets the result of that method.

### All points linked to a specific schedule

```
select targetComponent.slotPath from baja:Link
  where sourceSlotName = 'out' and
        sourceComponent.slotPath = 'slot:/app/MainSchedule'
```

This one is tricky. Because links are dynamic, they do not have a fixed name that we can search for. There is also no way to access just the links to a schedule output from BQL. Instead we have to look at all of the links and check the endpoints. So the extent is all links. Then we check for a source slot of "out". Finally we check the source slot path.

### All points that generate alarms of a specific class

```
select parent.slotPath from alarm:AlarmSourceExt where alarmClass = 'hvac'
```

The result is the slot path of all control points that generate alarms for the "hvac" alarm class. The extent is all alarm source extensions. We find the extensions that specify "hvac" for the alarm class and get the parent slot path from those. The parent of an alarm source extension is always a control point.

### All points with a history extension

```
select parent.slotPath from history:HistoryExt
```

This one is simple. We find all of the history extensions by using `history:HistoryExt` as the extent. Then we just get the slot path of the parent. The parent of a history extension is always a control point.

### All points that collect a history with a capacity greater than 1000 records.

```
select parent.slotPath, historyConfig.capacity from history:HistoryExt
  where historyConfig.capacity.isUnlimited or
        historyConfig.capacity.maxRecords > 1000
```

For this query you have to understand how history extensions are configured. The capacity is a property of `HistoryConfig`. However, Capacity is not a simple numeric value. To exceed 1000 records of capacity, the configured capacity may either be unlimited or limited to a value greater than 1000. So first we check for unlimited and then we check for a limit of more than 1000 records.

### The number of unacked alarms in all alarm classes

```
select name, unackedAlarmCount from alarm:AlarmClass
```

This query just looks at all of the alarm classes and for each one returns the name and the `unackedAlarmCount`. In this case, it will be much more efficient to narrow the search by making the alarm service be the query base. All alarm classes must be children of the `AlarmService`. So it is much better to only search the `AlarmService` container.

```
slot:/Services/Alarm|bql:select name, numberOfUnackedAlarms from alarm:AlarmClass
