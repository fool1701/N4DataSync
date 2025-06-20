# Alarm

## Introduction

The Alarm module provides core functionality for lifecycle management of alarms within the Niagara Framework. Alarms are used to indicate that some value is not within an appropriate or expected range. Alarms may be routed from the system to a variety of external sources, be it email, a printer or a console application.

## Object Model

All alarms in the Niagara Framework are generated by objects implementing the `BIAlarmSource` interface. Those alarms (`BAlarmRecord`) are then routed to the `BAlarmService`. The service for storing and routing of alarms. Alarms are then routed to one or more recipients (`BAlarmRecipient`) via their `BAlarmClass`.

### Alarm Sources

While `BIAlarmSource` is an interface, most alarm sources are instances of `javax.baja.control.alarm.BAlarmSourceExt`, the alarm point extension. The alarm extension determines when it’s parent point is in an alarmable condition, and uses the `AlarmSupport` class to take care of routing and issuing alarms to the alarm service. The alarm source updates the alarm when the parent point goes back to its normal condition as well as notifies the point that an acknowledgement has been received.

Objects implementing `BIAlarmSource` that have a status (`BStatus`) should use the following rules when setting the status bits.

-   **Generate Offnormal alarm:** set the `BStatus.ALARM` and `BStatus.UNACKED_ALARM` bits.
-   **Generate Fault alarm:** set the `BStatus.ALARM`, `BStatus.UNACKED_ALARM` and `BStatus.FAULT` bits.
-   **AckAlarm methods is called:** if the alarm is the last one generated clear the `BStatus.UNACKED_ALARM` bit.
-   **Generate Normal alarm:** clear the `BStatus.ALARM` and `BStatus.FAULT` bits.

Note that `BStatus.UNACKED_ALARM` should only be set if the `BAlarmClass.ackRequired` bit is set for that transition in the AlarmSource’s AlarmClass. This can easily be obtained if using the `AlarmSupport` class by calling `BAlarmSupport.ackRequired(BSourceState state)`.

### Alarm Service

The `BAlarmService` coordinates routing of alarms within the framework. It routes alarms from their source to the appropriate recipients, and alarm acknowledgements from the recipients back to the source. The alarm service routes individual alarms via their alarm class. All alarm classes available to the system are maintained as slots on `BAlarmService`. The `BAlarmService` also maintains the Alarm Database. It is acessed though the `getAlarmDb()` method.

### Alarm Class

The alarm classes, as stated above, are maintained as slots on the alarm service and serve to route alarms with similar sets of properties along common routes - they serve as channels for like data. `BAlarmClass` manages the persistence of the alarms as needed via the alarm database. The `AlarmClass` manages the priority of an alarm and also which alarm require acknowledgement. Each alarm class can be linked to one or more alarm recipients.

### Alarm Recipients

Alarm recipients are linked to an alarm class (from the alarm topic on the alarm class to the `routeAlarm` action on `BAlarmRecipient`.) Recipients may be configured to receive alarms only at certain times of day, certain days of the week, and receiving alarms of only certain transitions (eg. `toOffnormal`, `toFault`, `toNormal`, `toAlert`).

Three subclasses of `BAlarmRecipient` are worth noting: `BConsoleRecipient`, `BStationRecipient` and `BEmailRecipient`.

-   **BConsoleRecipient:** This recipient manages the transfer of alarms between the alarm history and the alarm console, i.e. it gets open alarms from the alarm history for the console and updates the history when they are acknowledged.
-   **BStationRecipient:** This recipient manages the transfer of alarms between the alarm service and a remote Niagara station.
-   **BEmailRecipient:** The email recipient is part of the email package. It allows alarms to be sent to users via email.

## Lifecycle

Each alarm is a single `BAlarmRecord` that changes throughout its lifecycle. An alarm has four general states that it may be in:

1.  New Alarm
2.  Acknowledged Alarm
3.  Normal Alarm
4.  Acknowledged Normal Alarm

All alarms start as New Alarms and end as Acknowledged Normal Alarms. They may be acknowledged then go back to normal or go back to normal then be acknowledged.

An Alert is an alarm that does not have a normal state and thus its lifecycle consists of New Alarm and Acknowledged Alarm.

### Alarm Routing Overview

#### New Alarms

1.  `BIAlarmSource` generates an offnormal alarm (or fault alarm).
2.  It is sent to the `BAlarmService`.
3.  `BAlarmService` routes it to its `BAlarmClass`.
4.  The `BAlarmClass` sets the alarm’s priority, `ackRequired` bit, and optional data.
5.  It is then routed to any number of `BAlarmRecipients`.

The normal alarm is sent along this same path.

#### Alarm Acks

1.  When a `BAlarmRecipient` acknowledges an alarm, the acknowledgement is sent to the `BAlarmService`.
2.  The `BAlarmService` routes back to the `BIAlarmSource` (if an ack is required).
3.  The Alarm Acknowledgement is then routed to `AlarmRecipients` along the same path as a New Alarm.

## Usage

### Setup

The most basic piece needed is a control point. Then add an alarm extension from the alarm module palette. There are several types of extensions depending upon the type of point selected. The `AlarmExtension` are disabled by default. You must enabled `toOffnormal` or `toFault` alamrs and configure and enable the alarm algorithms.

An Alarm Service is also required. Depending on your needs, it may require some of the following slots:

-   Any desired `BAlarmClasses` should be added.
-   A `BConsoleRecipient` should be added if an alarm console is required.

Link any of the slots as needed. The alarm recipients must be linked to an alarm class in order to receive alarms from that alarm class.

To generate an alarm, go to a point with an alarm extension and put it an alarm condition.

### Console Recipient / Alarm Console

To view all of the outstanding alarms in the system, double click on the console recipient on the alarm service. The alarm console manages alarms on a per point basis. Each row in the alarm console is the most recent alarm from a point. To view all the current alarms from that point, double click the row.

To acknowledge an alarm, select the desired alarm and hit the ack button. An alarm is cleared from the alarm console when the alarm is acknowledged AND the point is in its normal state.

To view more information about an unacknowledged alarm, right click and select View Details.

### Station Recipient

A `BStationRecipient` allows sending alarms to remote Niagara stations. A remote station is selected from the stations you have configured in your Niagara Network. This recipient require that the remote station be properly configured in the Niagara Network.

### Printer Recipient

A `BPrinterRecipient` allows printing of alarms on an ink-jet or laser printer. This recipient is only available on Win32 Platforms. It supports both local and remote printers.

### Line Printer Recipient

A `BLinePrinterRecipient` allows printing of alarms on a Line Printer. This recipient is only available on Win32 Platforms. It supports both local and remote printers.

## Niagara AX to Niagara 4 API Changes

### Overview

The Alarm API has been re-factored to better support pluggable persistent storage. This will allow for better scaling of the Niagara Alarm Service since the JACE and the Supervisor will be able to have different backing databases. The new API is connection oriented in order to support the use to RDBMS and ODBMS back-ends.

`BAlarmDatabase` now extends `BSpace`. This is now consistent with how other storage mechanisms are `BSpace`s with their Service defining the configuration of the space. As part of this change, database configuration properties on `BAlarmService` were refactored.

### BAlarmService & BAlarmDatabase

#### javax.baja.alarm.BAlarmService.capacity & javax.baja.alarm.BAlarmDbConfig

A `BAlarmDbConfig` property named `alarmDbConfig` was added to `BAlarmService`. This property will allow a greater flexibility in defining alarm storage configurations in the future. For the standard file-based Alarm Service, the `capacity` property was moved to the `BFileAlarmDbConfig` subclass of `BAlarmDbConfig`.

#### javax.baja.alarm.BAlarmDatabase

`BAlarmDatabase` now extends `BSpace` and implements `BIProtected`. This allows the `AlarmDatabase` to appear in the Nav Tree as a peer to the History and System Databases and be categorized via the `CategoryBrowser`. Since `BAlarmDbConfig` now defines the configuration of the alarm database, the following method was added to `BAlarmDatabase` to handle changes to the configuration.

```java
/**
 * Update the database with the new configuration.
 *
 * @param config new BAlarmDbConfig
 * @param p Property to update
 * @since Niagara 4.0
 */
public abstract void updateConfig(BAlarmDbConfig config, Property p)
  throws AlarmException;
```

The `BAlarmDatabase` gets a callback to `updateConfig()` for each property change on the `BAlarmDbConfig` object.

#### javax.baja.alarm.BAlarmDbView & javax.baja.alarm.BAlarmDbMaintenanceView

`BAlarmDbView` & `BAlarmDbMaintenanceView` have been moved to be views on `BAlarmDatabase` instead of `BAlarmService`.

#### javax.baja.alarm.BAlarmRecord

`getSchema()` and `getRecordSize()` methods were added to `BAlarmRecord`. These are currently placeholders for future use.

The default behaviour of the previously existing `BAlarmRecord` constructors was changed to not create a new `BUuid`. New constructors were created accepting a `BUuid` as an argument.

### Connection Oriented API

#### javax.baja.alarm.BIAlarmSpace

The `BIAlarmSpace` interface as added to provide access to the Alarm Space via a connection oriented API. It provides the following method:

- `AlarmSpaceConnection getConnection(Context)`

#### javax.baja.alarm.BAlarmService

The following methods have been moved to `javax.baja.alarm.AlarmSpaceConnection`

- `public void append(BAlarmRecord record)`
- `public void update(BAlarmRecord record)`
- `public int getRecordCount();`
- `public BAlarmRecord getRecord(BUuid uuid)`
- `public Cursor<BAlarmSource> getOpenAlarmSources()`
- `public Cursor<BAlarmRecord> getOpenAlarms()`
- `public Cursor<BAlarmRecord> getAckPendingAlarms()`
- `public Cursor<BAlarmRecord> getAlarmsForSource(BOrdList alarmSource)`
- `public Cursor<BAlarmRecord> scan()`
- `public Cursor<BAlarmRecord> timeQuery(BAbsTime start, BAbsTime end)`

The following methods have been moved to `javax.baja.alarm.AlarmDbConnection`

- `public abstract void clearAllRecords(Context cx)`
- `public abstract void clearOldRecords(BAbsTime before, Context cx)`
- `public abstract void clearRecord(BUuid uuid, Context cx)`

The following method was added to provide access to the `AlarmDatabase`

- `AlarmDbConnection getDbConnection(Context)`

#### javax.baja.alarm.AlarmSpaceConnection

The `AlarmSpaceConnection` interface is `AutoCloseable`. It provides access to the `IAlarmSpace` and allows management of connection boundaries. Alarms are obtained, queried and updated via the `AlarmSpaceConnection`.

#### javax.baja.alarm.AlarmDbConnection

`AlarmDbConnection` implements `AlarmSpaceConnection` and provides additional methods and implementations for working with `BAlarmDatabase`s.

### Code Samples

The following code examples demonstrate how to convert common alarm operations from the NiagaraAX Alarm API to the Niagara 4 Alarm API.

#### Query Record by UUID

**Niagara AX**

```java
BUuid uuid = getAlarmUuid();
BAlarmRecord alarm = null;
BAlarmService alarmService = getAlarmService();
alarm = alarmService.getAlarmDb().getRecord(uuid);
```

**Niagara 4**

```java
BUuid uuid = getAlarmUuid();
BAlarmRecord alarm = null;
BAlarmService as = getAlarmService();
try (AlarmDbConnection conn = alarmService.getAlarmDb().getDbConnection(null))
{
  alarm = conn.getRecord(uuid);
}
```

#### Alarm Query

**Niagara AX**

```java
BAlarmDatabase alarmDb = alarmService.getAlarmDb();
Cursor cur = alarmDb.getOpenAlarms();
while (cur.next())
{
  BAlarmRecord alarm = (BAlarmRecord)cur.get();
}
```

**Niagara 4**

```java
try (AlarmDbConnection conn = alarmService.getAlarmDb().getDbConnection(null))
{
  Cursor<BAlarmRecord> cur = conn.getOpenAlarms();
  while (cur.next())
  {
    BAlarmRecord alarm = cur.get();
  }
}
```

#### Alarm Db Maintenance

**Niagara AX**

```java
BAbsTime before = getTimeOfLastRecordToKeep();
BAlarmService service = (BAlarmService)Sys.getService(BAlarmService.TYPE);
if (service != null)
{
  service.getAlarmDb().clearOldRecords(before, getSessionContext());
}
```

**Niagara 4**

```java
BAbsTime before = getTimeOfLastRecordToKeep();
BAlarmService service = (BAlarmService)Sys.getService(BAlarmService.TYPE);
if (service != null)
{
  try (AlarmDbConnection conn = service.getAlarmDb().getDbConnection(null))
  {
    conn.clearOldRecords(before, getSessionContext());
  }
}
```

#### Create New BAlarmRecord

**Niagara AX**

```java
BAlarmRecord record = new BAlarmRecord();
```

**Niagara 4**

```java
BAlarmRecord recordWithNewUuid = new BAlarmRecord(BUuid.make());
BAlarmRecord recordWithDefaultUuid = new BAlarmRecord();
