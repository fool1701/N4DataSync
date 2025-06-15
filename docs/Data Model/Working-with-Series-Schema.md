# Working with Series Schema
 The data schema concept is heavily relied upon throughout the transform framework. A data schema defines the
 structure of some piece of data. An everyday example of a data schema is a BStruct or BComponent that defines frozen
 property slots. These frozen slots are guaranteed to be present for each instance of the defining struct or component
 object, and so other applications may reliably make use of these frozen slots.
 In the series transform framework, the record schema is defined much the same way. However, rather than dealing with
 frozen slots on a defined component object, the transform framework defines the schema as the dynamic property slots
 that will be present on the record cursor object at the time the graph is resolved.
## Schema Composition
 A series transform schema is a collection of field names with each field name associated with a BTypeSpec to represent
 the data type and BFacets to represent metadata for the field.
### The Schema Field
 Each schema field is stored in a BSeriesSchema instance as a dynamic property. The schema field name is the name of
 the Property slot. The data type of the schema field, the type of data that is represented by the field, is the value of the
 Property slot. This value is a BTypeSpec to allow the value to be stored in a property slot.
 When a graph node is resolved to a cursor, the schema information is used to construct the record BComponent object
 that is returned from the Cursor.get() method. The record component properties are the same properties of the schema,
 with the exception that the value of the properties are data values of the Type represented by the schema field's
 BTypeSpec value.
### Key Field
 Each schema includes a key field. The key field is the data field that will include a unique value for each data row at the
 time that the graph node cursor is resolved. The key field is akin to the primary key field used in relational databases.
 In the case of histories, the key field is the Timestamp field. Each record returned by a history is guaranteed to have a
 unique Timestamp value. By using the Timestamp field as the key field, the transform framework can perform tasks such
 as grouping a collection of records together in five minute intervals. This is possible because key field allows the
 framework to uniquely identify each cursor record, or "table row", as a unique record.
### Input and Output Schema
 Each graph node deals with two separate schemas: the schema, or schemas, of the data source or sources for the graph
 node, and the schema of the record data that the graph node cursor will return.
 The Incoming schema data is defined by the outgoing schema of the node source. In cases where a graph node has
 multiple incoming schemas, such as the Composite graph node, the schemas should be namespaced by the name of the
 graph node that the schema originates from.
 The BGraphNode base class includes the getSources() method which returns an array of BGraphNode objects that are
 the inputs of the current graph node instance. This method is used to obtain the incoming schema of the graph node.
 Below is a code snippet from the composite editor that is used to create the fields available for selection to configure the
 composite node:

```java
        private String[] getCompositeFields()
        {
          Set srcFields = new HashSet();

          //get the input sources for our node
          BGraphNode[] sources = srcNode.getSources();
          for( int i = 0 ; i < sources.length; i++ )
          {

              BGraphNode src = sources[i];
              String srcNodeName = src.getName();
              BSeriesSchema nodeSchema = src.getSchema();

              String keyField = nodeSchema.getKeyField();

              String[] fieldNames = nodeSchema.getFieldNames();
              for( int j = 0; j < fieldNames.length; j++ )
              {
                  String fieldName = fieldNames[j];
                  if( fieldName.equals(keyField)) continue;
                  String colName = srcNodeName + "." + fieldName;
                  srcFields.add(colName);
              }
          }

          return (String[])srcFields.toArray(new String[]{});
        }
```

 In the above example, the schema fields are obtained from all schemas associated with the composite node. These fields are later used to build the composite editor interface.
 The output schema is the result of configuring the incoming schema or schemas in conjunction with the functionality of the transform graph node. In the case of the Composite node, the output schema is the collection of renamed input schema fields that are composited together to create a new data structure. The output schema is the representation of the dynamic properties of the BComponent that will be returned by the CompositeCursor.get() method.
 It is not possible to know the composition of a series cursor record until the graph node of the cursor is resolved. This is a direct result of the end user's ability to dynamically alter data structures through the transform graph. The schema is absolutely necessary to grant each graph node cursor the knowledge of how to handle the incoming data.

## BSeriesSchema
 The BSeriesSchema is the framework component that represents the schema for a graph node. The series schema component stores the schema fields as dynamic Property slots. By definition, this requires that each schema field has a unique name in the schema.
 The series schema offers several convenience methods for working with the schema in an intuitive manner.
 The `getKeyField()` method returns the key field for the entire schema. The key field is the data field that will include a unique value for each data row at the time that the graph node cursor is resolved. The key field is akin to the primary key field used in relational databases.
 The `getFieldNames()` returns all schema field names. Each schema field name is unique and is the name of the Property Slot that will be set on the series cursor record BComponent returned from the cursor's get() method.
 The facets and type information for each field is retrieved using the `getFieldFacets( )` and `getFieldType( )` methods. Each method takes a string Field name as the method argument.
 Below is a code snippet demonstrating how to create a cursor template record using a series schema supplied from a graph node:

```java
        public static BComponent createTemplateRecord(BSeriesSchema
recordSchema)
        {
          //make sure our composite Template includes the time stamp column
          BComponent templateRecord = new BComponent();

          //use our schema to initialize our template
          String[] fieldNames = recordSchema.getFieldNames();
          for( int i = 0; i < fieldNames.length; i++)
          {
            String fieldName = fieldNames[i];

            BTypeSpec fieldType = recordSchema.getFieldType(fieldName);
            BFacets fieldFacets = recordSchema.getFieldFacets(fieldName);
            BValue defaultValue = getDefaultValue(fieldType.getResolvedType());

            templateRecord.add(fieldName,defaultValue,0,fieldFacets,null);
          }

          return templateRecord;
        }
```

 Note that once the field names are obtained from the schema, the field type and field facets information is retrieved for each field name to construct the dynamic properties of the template BComponent record.
