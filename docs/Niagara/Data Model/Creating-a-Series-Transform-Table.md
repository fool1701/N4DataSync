# Creating a Series Transform Table

All graph node implementations are expected to return a `BSeriesTransformTable` implementation. The Series Transform Table is an abstract implementation of the `BITable` interface that includes additional series data information.

When creating a series transform table, two pieces of information are required: the Series Schema of the records that will be returned by the table's cursor, and the Series Name of the data.

The schema data is the same schema data that is returned for the graph node's `getSchema()` method. This schema is the format of the data of each record that will be returned by the Cursor returned by the table instance.

The Series Name is name of the graph node that creates the table instance. The series name is used to handle name space issues when combining data from multiple input sources.

```java
        public BCompositeTable(String seriesName, BSeriesSchema schema,
                       CompositeMapping[] mappings, IFilter filter)
        {
            super(seriesName, schema);
            this.mappings = mappings;
            setFilter(filter);

            //Perform other initialization operations as necessary
            ...
        }
```

The only required method implementation is the `cursor()` method. This method will return a Cursor that performs the data transformations as intended by the graph node. Below is an example implementation of the cursor method for the `BCompositeTable` class.

```java
        public Cursor cursor()
        {
          return new CompositeCursor(getSchema(),mappings,getFilter());
        }
```
