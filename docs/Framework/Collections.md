# **Collections**

## **Overview of Changes from Niagara AX**

There are several inadequacies in the Baja Collections API \- javax.baja.collection and javax.baja.sys.Cursor. \[cite: 518\] The current API suffers from a number of problems that hinder performance and encourage inefficient implementations for cases where data sets are large. \[cite: 519\] The API changes aim to help developers be more productive with the collection API, and to pave the way for better implementation when underlying data sets are large. \[cite: 520\]

## **Impacts**

Any module that makes use of the javax.baja.collection classes or javax.baja.sys.Cursor is impacted by these changes. \[cite: 521\] Depending on what methods and classes of the API the code uses you may need to refactor your code. \[cite: 522\] Any implementations of BICollection, BIList, and BITable will be impacted by these changes. \[cite: 523\] In the unlikely event that you implemented the javax.baja.bql.BIRelational interface in your code, you will also be impacted. \[cite: 524\]

## **Changes**

### **Removed BICollection**

One of the biggest issues with Niagara AX's Collections API is the BICollection interface. BICollection requires every implementation to model itself as a collection, a list, and a table. \[cite: 525, 526\] This puts a heavy burden on developers implementing a collection, and in many cases it does not make sense to model a list as a table, and vice-versa. \[cite: 527\] So this interface has been removed entirely. \[cite: 528\]

#### **Code Impacts**

The interface only had methods for converting the underlying collection to a list or table. \[cite: 529\] Every implementation of BICollection in the framework now implements BITable. \[cite: 529\] If you were casting objects to BICollection you should be able to safely cast them to BITable now. \[cite: 530\] Almost invariably this was due to ord resolution of a bql query: \[cite: 531\]

**Niagara AX**

BICollection result \= (BICollection)BOrd.make("bql:select displayName").get(base);  
BITable table \= result.toTable();

**Niagara 4**

BITable table \= (BITable)BOrd.make("bql:select displayName").get(base); \[cite: 532\]

Any public methods that took a BICollection will need to be refactored to expect a BITable. \[cite: 532\]

### **Removed BIList**

This change is probably the most significant in terms of fundamental philosophy change. \[cite: 533\] As part of the design philosophy for collections in Niagara 4, we wanted to discourage random-access methods. \[cite: 534\] In fact, they have essentially been removed from the collection API in favour of cursor-based access. \[cite: 535\] Don't worry, you can still work with a table in a random-access way (details below). \[cite: 536\] The BIList interface essentially required random-access support for every collection. \[cite: 537\] Further, an analysis of the entire framework showed that there were zero concrete implementations of BIList/BICollection in the public API that did not also implement BITable. \[cite: 538\] This indicates that the BITable API is more useful to the framework as a whole. \[cite: 539\]

#### **Code Impacts**

Similar to BICollection above, you should be able to cast any reference to a BIList to a BITable now. \[cite: 540\] If by chance you had a public method that expected a BIList, you will need to refactor that API to take a BITable. \[cite: 541\]

### **Refactored BITable**

The BITable interface has been greatly simplified and all random-access methods have been removed. \[cite: 542\] You can iterate the rows in the table by obtaining a TableCursor. \[cite: 543\] The TableCursor gives you access to the table that contains the row, the Row object itself (see below), and a convenience method to obtain a cell value for the current row. \[cite: 544\] Each row in a table is modelled as a Row object. \[cite: 545\] The row object gives you direct access to the underlying BIObject backing the row, as well as column cell values, flags, and facets. \[cite: 546\]

#### **Code Impacts**

The biggest impact will occur if your code was iterating a BITable using the random-access methods of the old API. \[cite: 547\] You have a few options. \[cite: 548\]

First, change your code to iterate the table using a cursor. \[cite: 549\] This is the best option. \[cite: 549\]

// Iterate a BITable using a TableCursor  
BITable table \= (BITable)bqlOrd.resolve().get();  
Column\[\] columns \= table.getColumns().list(); \[cite: 550\]  
try(TableCursor\<BIObject\> cursor \= table.cursor())  
{  
  // Just for printing purposes, not for random access.  
  int row \= 0;  
  while (cursor.next())  
  {  
    System.out.print(row \+ ": ");  
    for (Column col : columns) \[cite: 551\]  
    {  
      System.out.print(cursor.cell(col) \+ ", ");  
    }  
    System.out.println();  
    \++row;  
  }  
}

If you must access the table using random-access indexing, you can convert it to a BIRandomAccessTable using the javax.baja.collection.Tables utility class. \[cite: 552\]

BITable table \= (BITable)ordThatResolvesToTable.resolve().get();  
BIRandomAccessTable rat \= Tables.slurp(table);  
System.out.println(String.format("This table has %d rows", rat.size())); \[cite: 552\]  
for (int i=0; i\<rat.size(); \++i) \[cite: 553\]  
{  
  Row row \= rat.get(i);  
  // Do something with each row...  
}

### **Refactored Cursor Interface**

There are a few major changes to the javax.baja.sys.Cursor interface. \[cite: 554\]

1. javax.baja.sys.Cursor now implements java.lang.AutoCloseable. This means you should be a good citizen of every cursor you work with. \[cite: 554, 555\] Failing to close a cursor may result in a resource leak and degraded system performance. \[cite: 555\] The try-with-resources statement introduced in Java 7 can help manage opening and closing cursors. \[cite: 556\]  
2. Cursor is now generic: public interface Cursor\<E\> extends AutoCloseable. \[cite: 557\]  
3. This means it can iterate over any type; not just Niagara types. \[cite: 557\]  
4. Since it can iterate any type, we removed the nextComponent() method from the interface and moved it into SlotCursor. \[cite: 558\] This seems to be its primary use case anyway. \[cite: 559\]  
5. A new javax.baja.sys.IterableCursor interface has been added that extends Cursor and implements Java's Iterable interface. \[cite: 560\] This enables a Cursor to be used in a for each statement as well as accessing the Cursor as an Iterator, Spliterator or Stream. \[cite: 560\]  
6. javax.baja.sys.SlotCursor also implements the Iterable interface so it can be used to iterate over a collection of Slots (not BValue). \[cite: 561\]

If you need to implement your own Cursor, use the utility class javax.baja.collection.AbstractCursor, which stubs out all methods in the interface and handles close semantics for you. \[cite: 563\] You only need to provide an implementation of advanceCursor() and doGet(). \[cite: 564\]

Here are some examples of the new SlotCursor design that use Java 8's Stream API:

// Remove all dynamic Properties from a point...  
point.getProperties()  
  .stream()  
  .filter(Slot::isDynamic)  
  .forEach(point::remove); \[cite: 565\]

// Print out the path string of all folders under a point...  
point.getProperties()  
  .stream()  
  .map(point::get)  
  .filter(v \-\> v.getType().is(BFolder.TYPE))  
  .forEach(v \-\> System.out.println(v.asComponent().toPathString())); \[cite: 566\]

### **BIRelational Interface Breaking Changes**

In the unlikely event that you implemented the javax.baja.bql.BIRelational interface in your code, you will need to add a Context argument to its single method. \[cite: 567\] The updated interface class is shown below: \[cite: 568\]

public interface BIRelational\<T extends BIObject\>  
{  
  /\*\*  
  \* Get the relation with the specified identifier. \[cite: 569\]  
  \* @param id A string identifier for the relation. The format  
  \* of the string is implementation specific. \[cite: 569\]  
  \* @param cx The Context associated with this request.  
  \* This parameter was added starting in Niagara 4.0. \[cite: 570\]  
  \* @return Returns the relation identified by id or null if the relation  
  \* cannot be found. \[cite: 571\]  
  \*/  
  BITable\<T\> getRelation(String id, Context cx); \[cite: 571\]

  Type TYPE \= Sys.loadType(BIRelational.class); \[cite: 572\]  
}  
