# Creating the Series Cursor

Each series transform table must return a `Cursor` implementation. The Cursor is responsible for retrieving data in the form of records from an input source (one or more Cursors) and performing operations on the data before returning the transformed data in a component record format.

## Creating a Cursor

The only requirement of implementing a Cursor for use with the Series Transform Table is to create an object that implements the `Cursor` interface.

As the `Cursor` interface requires a number of method implementations, the transform framework provides the `SeriesCursor` base class as a convenience base class for implementing a Series cursor. This document will focus on extending the `SeriesCursor` base class.

The `SeriesCursor` requiers two method implementations: the `get()` method and the `next()` method.

-   The `get()` method will return a `BComponent` record. It is important to note that this method should not create a new record instance when it is called. Instead, the cursor should create a single instance of the record component at the time of initialization and load the record with new data each time the `get()` method is called, returning that record instance. This prevents an out of memory exception when iterating over large data sets.
-   The `next()` method is responsible for incrementing the cursor to the next record. If the cursor record cannot be incremented, due to lack of data or other causes, this method should return `false`.

## Cursor Implementation

When implementing a transform cursor, there are a few considerations to take into account.

-   The most important rule is that the `get()` method must return a reference to the same `BComponent` record each time it is called. It should never create a new instance of the component record. This implies that the Cursor will have a single `BComponent` instance instantiated and initialized at the time of class construction and returned whenever the `get()` method is called.
-   A second and equally important rule is that the `BComponent` record must conform to the Series Schema defined for the graph node that the Cursor represents. The component record must include a set of dynamic Property slots that conform to the Series Schema of the graph node.

Both of these rules are readily accomplished by creating a `BComponent` instance that is a record template and makes use of the Series Schema defined by the represented graph node as shown in the following code snippet.

```java
public class CompositeCursor
extends SeriesCursor
{
  public CompositeCursor(BSeriesSchema schema, CompositeMapping[]

                         compositeMappings, IFilter filter)
  {
    keyField = schema.getKeyField();
    this.filter = null;
    this.templateRecord = makeTemplate (schema);
    createSubCursors(compositeMappings);
  }

  public static BComponent makeTemplate( BSeriesSchema recordSchema)
  {
    //create our template instance
    BComponent templateRecord = new BComponent();

    //use our schema to initialize our template
    String[] fieldNames = recordSchema.getFieldNames();
    for( int i = 0; i < fieldNames.length; i++)
    {
      String fieldName = fieldNames[i];

      BTypeSpec fieldType = recordSchema.getFieldType(fieldName);
      BFacets fieldFacets = recordSchema.getFieldFacets(fieldName);
      BValue defaultValue =
                      getDefaultValue(fieldType.getResolvedType());

      templateRecord.add(fieldName,defaultValue,0,fieldFacets,null);
    }

    return templateRecord;
  }

  public BObject get()
  {
    //return our template instance
    return templateRecord;
  }

  public boolean next()
  {

    //get record values from our internal cursors. In the case of the
    //composite cursor, these internal cursors are passed in as part
    //of the constructors CompositeMapping values.
    ...

    //after retrieving our internal data from our sub cursors,
    // copy the composite properties to the composite record
    Set dstPropNames = propMappings.keySet();
    for( Iterator dstIt = dstPropNames.iterator(); dstIt.hasNext();)
    {
      String dstPropName = (String)dstIt.next();
      String srcPropName = (String)propMappings.get(dstPropName);
      Property property = templateRecord.getProperty(dstPropName);

      BFacets facets = property.getFacets();
      BValue value = record.get(srcPropName);
      templateRecord.set(dstPropName, value);
      Slot slot = templateRecord.getSlot(dstPropName);
      templateRecord.setFacets(slot, facets);
    }

  }

  //our only record instance
  private BComponent templateRecord;
}
```

## Wrapping Cursors

The concept of wrapping cursors is simple. When a series cursor is instantiated, one or more cursors are passed into the cursor constructor. How the cursor is passed in is determined by the implementation.

In the case of the `ScaleCursor`, the data cursor is passed into the constructor by passing in the `BSeriesTransformTable` that is the data source that the scale cursor will operate against. Remember that a Series Transform table is a specific implementation of the `BITable`. Each time the scale cursor is initialized, we want to ensure that the scale cursor gets a new cursor by calling the `cursor()` method of the underlying data source, the series transform table.

```java
public class ScaleCursor
extends SeriesCursor
{

  public ScaleCursor(BSeriesSchema schema, BSeriesTransformTable table,

                     Map scaling, IFilter filter)
  {
    fieldNames = schema.getFieldNames();
    this.scaleFactors = scaling;
    this.innerCursor = table.cursor();
    this.filter = filter;
  }

...

  private Cursor innerCursor;
}
```

The series transform table passed into the cursor constructor is itself passed into the `BScaleTable` constructor and used when creating the `ScaleCursor`.

```java
public class BScaleTable
extends BSeriesTransformTable
{

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BScaleTable.class);

  public BScaleTable(String seriesName, BSeriesSchema schema,
                     BSeriesTransformTable table, Map scaleFactors)
  {
    this(seriesName,schema,table,scaleFactors,null);
  }

  public BScaleTable(String seriesName, BSeriesSchema schema,
                     BSeriesTransformTable table, Map scaleFactors,
                     IFilter filter)
  {
    super(seriesName,schema);
    this.scaleFactors = scaleFactors;
    this.table = table;
    this.setFilter(filter);
  }

  public Cursor cursor()
  {
    return new ScaleCursor(getSchema(), table, scaleFactors,
                           getFilter());
  }

  public BICollection filter(IFilter filter)
  {
    return new BScaleTable(getSeriesName(),getSchema(), table,
                           scaleFactors, filter);
  }

  private Map scaleFactors;
  private BSeriesTransformTable table;
```
