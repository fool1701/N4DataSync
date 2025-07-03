# Schedule

## Overview

A schedule is effective or it is not. When it becomes effective, it will do something like fire an event or change an output. When a schedule is not effective, it will have some default configurable behavior.

Most schedules will be a hierarchy of many schedules. Container schedules combine the effective state of their descendants to determine effectiveness. Atomic schedules use some internal criteria to determine effectiveness. An example of an atomic schedule is the month schedule. It can be configured to be effective in some months and not in others.

## Creating New Schedule Types

### BAbstractSchedule

All schedules subclass this.

-   **Subclassing:** To create a new schedule type, one simply needs to implement methods `isEffective(BAbsTime)` and `nextEvent(BAbsTime)`. See the API documentation for details.
-   **New Properties:** Properties on new schedule types should have the `user_defined_1` flag set. This is important for properties who when changed, should cause supervisor (master) schedules to update their subordinates (slaves).
-   **Output:** If the new schedule is going to be used in a control schedule, it will be necessary to assign an effective value to it. A control schedule finds output by searching child schedules, in order, for the first effective schedule with a dynamic property named "effectiveValue". The `effectiveValue` may be 10 levels deep, it will be found. Just remember the order of schedules in a composite is important.

### BCompositeSchedule

Composite schedules shouldn't need to be subclassed. However, they will be used (frequently) in building new schedule hierarchies.

These schedules perform a simple function, they determine their effective state by combining the effective state of their children. A composite can either perform a union or an intersection of it's children. A union means only one child has to be effective for the parent composite to be effective. An intersection means all children have to be effective.

## Using Existing Schedules

There are six preconfigured schedules. The weekly schedules look like control objects, the calendar schedule helps configure special events that will be used by multiple schedules. Lastly, the trigger schedule enables sophisticated scheduling of topics (events) which can be linked to actions on other components.

### BBooleanSchedule, BEnumSchedule, BNumericSchedule and BStringSchedule

These are all `BWeeklySchedules` whose output matches their name. There is one input who if linked and not null, completely overrides the schedule.

**Example: Adding a special event**

```java
BDailySchedule specialEvent = new BDailySchedule(
     new BDateSchedule(5,BMonth.may,-1),
     BTime.make(11,0,0),
     BTime.make(12,0,0), //first excluded time
     BStatusBoolean.make(true));
myBooleanSchedule.addSpecialEvent(specialEvent);
```

**Example: Adding to the normal weekly schedule**

```java
BDaySchedule day = myBooleanSchedule.get(BWeekday.monday);
day.add(BTime.make(11,0,0),BTime.make(12,0,0),BStatusBoolean.make(true));
```

**Example: Retrieving all schedules in a normal weekday**

```java
BDaySchedule day = myBooleanSchedule.get(BWeekday.monday);
BTimeSchedule[] schedules = day.getTimesInOrder();
```

**Example: Modifying the day schedule of a special event**

```java
BDailySchedule specEvent = (BDailySchedule)
 myWeeklySchedule.getSpecialEvents().get("cincoDiMayo");
BDaySchedule day = specEvent.getDay();
```

**Example: Retrieving all special events**

```java
BDailySchedule[] specEvents = myWeeklySchedule.getSpecialEventsChildren();
```

**Legal special event schedule types:**

-   `BDateSchedule`
-   `BDateRangeSchedule`
-   `BWeekAndDaySchedule`
-   `BCustomSchedule`
-   `BScheduleReference`

### BCalendarSchedule

This schedule has a boolean output. However, it's most common use is for special events in the four weekly schedules discussed above. The weekly schedule can store a special reference to any calendar in the same station and assign their own output to it.

**Example: Adding a date schedule event**

```java
myCalendarSchedule.add("cincoDiMayo",aDateSchedule);
```

**Legal event schedule types:**

-   `BDateSchedule`
-   `BDateRangeSchedule`
-   `BWeekAndDaySchedule`
-   `BCustomSchedule`

### BTriggerSchedule

This schedule fires an event when a schedule becomes effective. There is also an event signifying that a normal event has been missed.

**Example: Add a date schedule event**

```java
myTriggerSchedule.getDates().add("cincoDiMayo",aDateSchedule);
```

**Example: Add a trigger time**

```java
myTriggerSchedule.getTimes().addTrigger(11,00);
```

**Legal event schedule types:**

-   `BDateSchedule`
-   `BDateRangeSchedule`
-   `BWeekAndDaySchedule`
-   `BCustomSchedule`
