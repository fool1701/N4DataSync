Building Complexes
Markdown

# Building Complexes

## BStructs vs BComponents

BComplex is the base class for both BStruct and BComponent[cite: 426]. Classes never subclass BComplex directly (it doesn't support any public or protected constructors)[cite: 427]. Rather developers subclass from BStruct or BComponent depending on their needs[cite: 428]. In general structs are used as complex data types[cite: 429]. BStructs can be built only using frozen properties. BComponents support much more flexibility and are built using frozen and dynamic slots of all types:

|                     | BStruct | BComponent |
| :------------------ | :------ | :--------- |
| Frozen Property     | X       | X          |
| Frozen Action       |         | X          |
| Frozen Topic        |         | X          |
| Dynamic Property    |         | X          |
| Dynamic Action      |         | X          |
| Dynamic Topic       |         | X          |
[cite: 430]

As you will learn, BComponents are also the basis for many other features such as Bords, links, and the event model[cite: 431]. You may wonder why you would use a BStruct? There are two main reasons[cite: 432]. The first is that because of its limited feature set, it is more memory efficient[cite: 433]. The other reason is that properties containing BComponents cannot be linked, but BStructs can be (see Links)[cite: 434].

## Building BComplexes

All concrete subclasses of BComplex must meet the following requirements:

* Meet the common rules applicable to all BObjects[cite: 435].
* Must declare a public constructor which takes no arguments[cite: 436].
* Declare frozen slots using the introspection patterns defined below[cite: 437].

## Introspection Patterns

We have discussed how frozen slots are defined at compile time[cite: 438]. Let's take a look at the frameworks knows when frozen slots have been declared[cite: 439]. Every slot is composed of two or three Java members[cite: 440]. A member is the technical term for a Java field, method, or constructor[cite: 441]. At runtime the framework uses Java reflection to examine the members of each class, looking for patterns to self-discover slots[cite: 442]. These patterns are based on the patterns used by JavaBeans, with significant extensions[cite: 443]. Remember introspection is used only to define frozen slots, dynamic slots are not specified in the classfile itself[cite: 444]. There is a different pattern for each slot type.

These introspection patterns require a fair amount of boiler plate code[cite: 445]. Although it is not too painful to write this code by hand, you may use Slot-o-matic to generate the boiler plate code for you[cite: 446].

### Frozen Properties

#### Rules

Every frozen property must follow these rules:

* Declare a public static final Property field where the field name is the property name[cite: 447].
* The property field must be allocated a Property instance using the BComplex.newProperty() method[cite: 448]. This method takes a set of flags for the property, and a default value[cite: 448].
* Declare a public getter method with JavaBean conventions: `type getCapitalizedName()`[cite: 449].
* Declare a public setter method with JavaBean conventions: `void setCapitalizedName(type v)`[cite: 450].
* The getter must call `BObject.get(Property)`[cite: 451]. The method must not perform any addition behavior[cite: 451].
* The setter must call `BObject.set(Property, BObject)`[cite: 452]. The method must not perform any additional behavior[cite: 452].
* The only types which may be used in a property are: subclasses of BValue, boolean, int, long, float, double, and String[cite: 453]. The six non-BValue types have special accessors which should be used in the getter and setter implementations[cite: 454].

#### Semantics

The introspection rules map Property meta-data as follows:

* **Name**: The Property name is the same as the field name[cite: 455].
* **Type**: The Property type is the one declared in the getter and setter methods[cite: 456].
* **Flags**: The Property flags are the ones passed to `newProperty()`[cite: 457].
* **Default Value**: The Property's default value is the instance passed to `newProperty()`[cite: 457].

#### Example

The following illustrates an example for different property types:

```java
// boolean property: fooBar
public static final Property fooBar = newProperty(0, true); [cite: 458]
public boolean getFooBar() { return getBoolean(fooBar); } [cite: 458]
public void setFooBar(boolean v) { setBoolean(fooBar, v); } [cite: 459]

// int property: cool
public static final Property cool = newProperty(0, 100); [cite: 460]
public int getCool() { return getInt(cool); } [cite: 460]
public void setCool(int v) { setInt(cool, v); } [cite: 460]

// double property: analog
public static final Property analog = newProperty(0, 75.0); [cite: 461]
public double getAnalog() { return getDouble(analog); } [cite: 461]
public void setAnalog(double v) { setDouble(analog, v); } [cite: 462]

// String property: description  (Corrected from float in original text to String based on usage)
public static final Property description = newProperty(0, "describe me"); [cite: 463]
public String getDescription() { return getString(description); } [cite: 463]
public void setDescription(String v) { setString(description, v); } // Corrected parameter v from x

// BObject property: timestamp
public static final Property timestamp = newProperty(0, BAbsTime.DEFAULT); [cite: 464]
public BAbsTime getTimestamp() { return (BAbsTime)get(timestamp); } [cite: 464]
public void setTimestamp(BAbsTime v) { set(timestamp, v); } [cite: 465]
Frozen Actions
Rules
Every frozen action must follow these rules:

Declare a public static final Action field where the field name is the action name.
The action must be allocated an Action instance using the BComponent.newAction() method. This method takes a set of flags for the action and an optional default argument.

Declare a public invocation method with the action name. This method must return void or a BObject type. This method must take zero or one parameters. If it takes a parameter, it should be a BObject type.
Declare a public implementation method, which is named doCapitalizedName. This method must have the same return type as the invocation method. This method must have the same parameter list as the invocation method.


The implementation of the invocation method must call BComponent.invoke(). No other behavior is permitted in the method.
Semantics
The introspection rules map Action meta-data as follows:

Name: The Action name is the same as the field name.
Return Type: The Action return type is the one declared in the invocation method.
Parameter Type: The Action parameter type is the one declared in the invocation method.
Flags: The Action flags are the ones passed to newAction().
Example
The following illustrates two examples. The first action contains neither a return value nor an argument value. The second declares both a return and argument value:


Java

// action: makeMyDay
public static final Action makeMyDay = newAction(0); [cite: 480]
public void makeMyDay() {
    invoke(makeMyDay, null, null); } [cite: 480]
public void doMakeMyDay() {
    System.out.println("Make my day!"); [cite: 481]
}

// action: increment
public static final Action increment = newAction(0, new BInteger(1)); [cite: 482]
public BInteger increment(BInteger v) {
    return (BInteger)invoke(increment, v, null); } [cite: 482]
public BInteger doIncrement(BInteger i) {
    return new BInteger(i.getInt()+1); [cite: 482]
}
Frozen Topics
Rules
Every frozen topic must follow these rules:

Declare a public static final Topic field where the field name is the topic name.
Declare a fire method of the signature: void fireCapitalizedName(EventType).
The implementation of the fire method is to call BComponent.fire(). No other behavior is permitted in the method.
Semantics
The introspection rules map Topic meta-data as follows:

Name: The Topic name is the same as the field name.
Event Type: The Topic event type is the one declared in the fire method.
Flags: The Topic flags are the ones passed to newTopic().
Example
The following code example illustrates declaring a frozen topic:

Java

// topic: exploded
public static final Topic exploded = newTopic(0); [cite: 489]
public void fireExploded(BString event) { fire(exploded, event, null); } [cite: 489]
Dynamic Slots
Dynamic slots are not declared as members in the classfile, but rather are managed at runtime using a set of methods on BComponent. These methods allow you to add, remove, rename, and reorder dynamic slots. A small sample of these methods follows:


Java

Property add(String name, BValue value, int flags); [cite: 493]
void remove(Property property); [cite: 493]
void rename(Property property, String newName); [cite: 494]
void reorder(Property[] properties); [cite: 494]
Note: You will notice that methods dealing with dynamic slots take a Property, not a Slot. This is because all dynamic slots including dynamic Actions and Topics are also Properties. Dynamic Actions and Topics are implemented by subclassing BAction and BTopic respectively.