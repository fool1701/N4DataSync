# Creating a Graph Component

Transform Graphs are made of a series of Graph Nodes. These nodes interconnect to represent data transformations. These transformations may include scaling the data, reconstructing the schema of the data, performing mathematical transformations on the data to produce new data, and so on.

A graph node has two separate states that must be considered at time of implementation: the design time configuration state and the graph resolve time state.

The configuration state includes setting custom properties of the graph node and defining both the data input schema that defines the structure of the data that will be processed by the node at graph resolve time, and the output data schema the describes the structure of the data returned by the node at resolve time.

New Graph Nodes can be created and integrated into the transform graph framework by following these simple steps:

1.  Create a `BComponent` that extends the `BGraphNode` abstract base class.
2.  Create a concrete implementation of the `BSeriesTransformTable` class.
3.  Create a Cursor implementation.
4.  Optionally provide a wiresheet popup editor for the graph node component.

## Extending the Graph Node

All graph node implementations must extend from `BGraphNode`. This base class provides several key elements necessary to integrate with the transform graph.

### Graph Properties

Each graph node includes 4 properties which are used by the transform graph as follows:

-   **Status** - Provides the configuration status of the graph node. If the node is currently misconfigured due to a change in the incoming series schema or node property settings made by the end user, this status should be set to fault.
-   **faultCause** - The fault cause provides a description of why the node is currently in a fault state. This message should provide a description of what settings of the node are incorrectly set to assist the end user in properly configuring your graph node within the transform graph.
-   **transformInputs** - The transform inputs property is a target property to allow the end user to connect one or more data input schemas to your graph node. An input source will most often be the output of another graph node.
    This property defines the flow of data from a data source to a graph node and also defines the structure of data that the graph node can expect when processing the data at graph resolve time.
    By default, this property does not declare the `fanIn` flag. You can set the fan in flag, or any other flag, on this property in the constructor of your graph node using the following code snippet:

    ```java
           Slot slot = getSlot(transformInputs.getName());
           if( null != slot)
              setFlags(slot, Flags.TRANSIENT|Flags.SUMMARY|Flags.FAN_IN);
    ```

-   **transformOutput** - The transform output is a target property that allows an end user to connect the output schema of the node as a data source to another node.

While these 4 properties may not be overridden, a graph node implementation may include as many additional properties as desired.

Node properties should be thought of as design time configuration settings for the graph node. These settings will be used at the time that the graph is resolved to determine how the incoming data for the node should be processed.

### Abstract Methods

When extending the `BGraphNode` base class, a handful of methods must be overridden and implemented.

#### getSchema( )

This method allows the node to return the expected output data schema of the record object produced by the resolve time cursor. The schema is returned as a `BSeriesSchema` object.

Returning the schema via method call rather than a property on the node allows the schema to be built dynamically at the time the schema is requested based on the node configurations.

#### doCheckSchema( )

This method is called by the transform framework to allow the graph node to check the current incoming schema or schemas against its current outgoing schema configuration. In cases where the input schemas are no longer sufficient to support the configuration of the output schema, a `ConfigException` should be thrown and the node placed in fault to notify the end user that the node is currently misconfigured.

This should be implemented by calling `getSchema()` on the source input nodes of the graph node and comparing that input schema to the configurations of the graph node. If the graph node configurations are supported by the incoming schemas we return from our method.

Below is a snippet from the `BScaleNode` class that shows how the incoming schema is checked against the current scale node configuration. What is important to node is that the method first retrieves all the input sources for the node using the `getSources()` method of `BGraphNode`, then retrieves the schema for each input node calling `getSchema()` on each source node. Each field of the schema that is used in the Scale Node's configuration is then checked to ensure that the value represented by the schema field is a numeric type.

```java
    //get the sources for our node by calling BGraphNode.getSources()
    BGraphNode[] sources = getSources();

    //get the configuration of our node. In the case of the Scale Node,
    //the configuration is stored as a collection of BScaleFactor
    //objects. Each scale factor is a simple mapping of an incoming
    //schema field with a numeric scale factor.
    BScaleFactors scaleFactorContainer = getScaleFactors();
    Object[] factors =
                   scaleFactorContainer.getChildren(BScaleFactor.class);

    //check if we have any sources
    if( sources.length == 0)
    {
      //if we have factors but no sources, we are in fault
      if( factors.length > 0)
      {
        getTransformInputs().setStatus(BStatus.fault);
        getTransformInputs().setValue(
            lex.getText(SCHEMA_FAULT_UNMATCHED_FIELD));
      }
      return;
    }

    BGraphNode src = sources[0];
    src.lease();
    BSeriesSchema srcSchema = src.getSchema();

    // brute force check of all schema inputs to make sure that all our
    // scale maps use schema values that are still present
    Set fields = getScaleInputFields();
    String keyField = srcSchema.getKeyField();

    for( int i = 0 ; i < factors.length; i++)
    {
      BScaleFactor factor = (BScaleFactor)factors[i];
      String fieldName = factor.getInputFieldName();

      //ignore our key field
      if( fieldName.equals(keyField)) continue;

      //check that the incoming schema field exists in our set. If
      //the field is not present in our set of configured fields,
      //throw an exception indicating that the schema does not
      //correspond with our configuration.
      if( !fields.contains(fieldName) )
      {
        purge.add(factor);
        getTransformInputs().setStatus(BStatus.fault);
        getTransformInputs().setValue(
            lex.getText(SCHEMA_FAULT_INVALID_FIELD,
            new Object[]{fieldName}));

        continue;
      }

      //check that the type value of the field is numeric
      BTypeSpec fieldType = srcSchema.getFieldType(fieldName);
      if( !fieldType.getTypeInfo().is(BINumeric.TYPE)){
          getTransformInputs().setStatus(BStatus.fault);
          getTransformInputs().setValue(
              lex.getText(SCHEMA_FAULT_INVALID_TYPE,
                 new Object[]{
                         fieldType.toString(),
                         fieldName,
                         BScaleNode.TYPE.toString()
                  }
               )
           );

        continue;
      }
    }
```

#### doResolve( BSeriesTransformTable[] , GraphNodeParams, BOrd )
This method is called when the graph node is resolved to a data cursor. This is the method which brings together the Series Table defined for our graph node, the node Cursor, and the configurations of the node itself.

When this method is called, the graph node is expected to return an array of `BSeriesTable` objects. This array will usually consist of one series table value.

When creating the series table instance or instances to return, the method will use the configuration data of the node to generate the table instance. This configuration data is gathered in one of two ways in the following order:

1.  The configuration data is obtained from the `GraphNodeParams` object passed into the method. This object is map of the properties of the graph node to a value passed in at run time. This value overrides the current property setting for this node.
2.  If no value is present for the property in the `GraphNodeParams` object, the value currently set for the node property is used.

Below is a code snippet from the `BHistorySourceNode` class that demonstrates overriding the `doResolve` method. Note that the `GraphNodeParams` object is first checked to see if it contains a value for the given property name. If not, the value is obtained directly from our property.

```java
      protected BSeriesTransformTable[] doResolve(
                         BSeriesTransformTable[] inputs,
                         GraphNodeParams args,
                         BOrd base) throws TransformException
      {

          ...

        //Attempt to construct a history ORD from our node settings. First
        //attempt to retrieve the value from our graph node args. If the
        //value is not present, return the value found in our graph node
        //property.
        BOrd schemaOrd = BOrd.NULL;
        BFormat relativeSource =
                 (BFormat)args.get(dataSource.getName(), getDataSource());
        BDynamicTimeRange range =
(BDynamicTimeRange)args.get(dateRange.getName(), getDateRange());
          String historyOrdParams = getHistoryOrdParams(range);

          //construct our ORD from our format string and date range

        ...

        // If we have a source ORD, process the source
        if( schemaOrd != BOrd.DEFAULT )
        {

          //get our Ord Target
          BObject object = schemaOrd.get(base.get());
          if( object instanceof BICollection)
          {
            BICollection c = (BICollection)object;

            //create our quantization cursor for this source
            BITable table = c.toTable();
            BSeriesInterval interval = getQuantInterval();
            BSeriesTransformTable table;
            if( interval.getDesc() == BSeriesIntervalEnum.none)
            {
              table = new BNonQuantizedTable(getName(),
                                             getSchema(),
                                             table));
            }
            else
            {
               table = new BQuantizationTable( getName(),
                                               getSchema(),
                                               interval,
                                               table) );
            }

            return new BSeriesTransformTable[]{table};
          }

        }

        //If we reach this point, we have an invalid data source
        throw new SourceException(INVALID_DATA_SRC);
      }
