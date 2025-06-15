# Creating Aggregate Functions

The Aggregate and Rollup graph nodes provide a few base functions for aggregating or performing a "rollup" on the incoming data. Additional functions can be created and will automatically be included by the Aggregate and Rollup graph nodes by extending the `BTransformFunction` base class.

Of the five required method implementations, four of these are required to integrate with the GUI workbench editors. These methods provide a name and description of the function as well as acceptable argument types (numeric, Boolean, etc.) and the return type.

The method of most importance is the `applyFunction()` method. This method is called by the aggregate and rollup cursors to perform the data calculation for each data record or set of records in a cursor iteration.

## The applyFunction Method

The method includes four arguments: a map of series names to records lists an array of source property names, the destination property where the final calculated value will be stored, and the result record that the calculated value in which the value will be stored.

### The Series Map

The `series` argument is a map of `BComponent` records contained in a `java.util.List` object that is keyed by the `String` name of the series that the records are associated with. Each source property name contained in the array of source property names is namespaced with the name of the series that the property is associated with.

The list of records associated with a value in the source property array can be obtained using the `getSeriesRecords` method available in the transform function API:

```java
for( int i = 0; i < srcProps.length; i++)
{
  String name = srcProps[i];

  //get our records for the series associated with this input field
  List records = getSeriesRecords(series, getSeriesName(name));

  ...
}
```

### The Source Properties Array

The `srcProps` array is an array of `String` values that represent the arguments for the transform function. Each `String` value contains the name of the Property containing the argument value and the name of the series that contains the record from which to pull the value. Each `String` value in the array of source properties uses the following format:

    SeriesName.PropertyName

Prefixing the source property name with the associated series name allows implementing transform function to pull data from multiple input sources at cursor resolve time.

### Example Implementing applyFunction

The following code snippet is an example of implementing the `applyFunction` method using the methods available in the Transform Function API. This example uses the Max function from the transform framework:

```java
public void applyFunction(Map series, String[] srcProps,
                          Property destProp, BComplex resultRecord)
throws TransformException
{
  BNumber maxValue = null;

  // Use the getUnits static method of the Transform Function to get
  // the Unit metadata of the destination property.
  BUnit dstUnits = getUnits(destProp);

  // iterate through each argument property name. For each argument,
  // get the series that contains the list of records that the property
  // is associated with and the name of the property.
  for( int i = 0; i < srcProps.length; i++)
  {
    String name = srcProps[i];

    // Use the getSeriesRecord method to retrieve the list of records
    // associated with this input argument. The static getSeriesName
    // method parses the series name from the argument name.
    List records = getSeriesRecords(series, getSeriesName(name));

    // iterate over our list of records and find our max for the
    // given field value
    if( null == records)continue;
    for( Iterator it = records.iterator(); it.hasNext();)
    {
      BComplex record = (BComplex)it.next();
      Property field = record.getProperty(getFieldName(name));

      // calculate our current maximum value, taking into account our
      // previous maximum and the source and destination Unit
      // information
      maxValue = getMaxValue(maxValue, field, dstUnits, record);

      // In the case of working with numeric data, if we have an
      // an invalid value returned as our maximum, we quit all attempts
      // to recalculate the max value and return the NaN to signify
      // that invalid data is included in the data set.
      if( maxValue == BDouble.NaN) break;
    }

  }

  // if we have no records, we have no max
  if( null == maxValue)
    maxValue = BDouble.NaN;

  // Use the setRecordValue method to handle setting facet information
  // and creating a new dynamic property in the BComponent if
  // necessary.
  setRecordValue(resultRecord, destProp, maxValue);

}
```

## Helper Functions

The `BTransformFunction` abstract base class includes several useful static functions that may be used when implementing the `applyFunction` method.

### getUnits( javax.baja.sys.Property )

This method retrieves the `BUnit` facet data from the given `Property` slot. If no unit facet information is found, the method returns `BUnit.NULL`.

### convertToUnits( javax.baja.sys.BNumber , javax.baja.units.BUnit sourceUnit, javax.baja.units.BUnit targetUnit)

This method converts the given number from the source unit type to the destination unit type. An example is when the source unit value is defined in Fahrenheit while the target unit type for the destination property in the result record is Celsius. This method will convert the numeric value from the source unit to the destination unit, returning the value in the format of the target unit.

### getSeriesName( String )

This method assumes that the `String` value given is as namespaced function argument of the following format:

    SeriesName.PropertyName

This method parses the `String` value and returns the series name.

### getFieldName( String )

This method assumes that the `String` value given is as namespaced function argument of the following format:

    SeriesName.PropertyName

This method parses the `String` value and returns the Property name.
