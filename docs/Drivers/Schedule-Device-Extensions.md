# Schedule Device Extensions

**API Reference**: `javax.baja.schedule.driver`

## Overview

Schedule device extensions manage remote schedule synchonization. A subordinate schedule is a read-only copy of a supervisor schedule. Subordinate schedules must be children of the schedule device extension.

## BScheduleDeviceExt

Container of supervisor schedule export descriptors and subordinate schedules.

### Subscription

At a random time after station startup and within the `subscribeWindow` property value, all subordinate schedules who have not communicated with their supervisor will have their `execute` action invoked. For drivers where remote supervisors do not persist information about local subordinates, the subscribe window should be some small value rather than the default of a day.

### Retries

Periodically the `execute` action of all `BScheduleExports` and `BScheduleImportExts` who are in fault is invoked. The retry interval is controled by the `retryTrigger` property.

### Subclasses

-   Implement `makeExport(String supervisorId)` to create `BScheduleExport` objects for incoming subscription requests from remote subordinates.
-   Implement `makeImportExt()` to create the schedule extension for new subordinate schedules.
-   Can call `processImport()` to handle requests from remote subordinates.
-   Can call `processExport()` to handle updates from remote supervisors.

## BScheduleExport

Maps a local supervisor to a remote subordinate. Will be a child of a `BScheduleDeviceExt`.

### Execution

The `execute` action is where the the local supervisor schedule configuration is sent to the remote subordinate. It is only invoked if the local supervisor schedule has been modified since the last time it was sent to the remote subordinate. The `executionTime` property controls when the local supervisor version is compared to the remote subordinate.

### Subclasses

-   Implement `doExecute()` to upload the supervisor schedule configuration.
-   Implement `postExecute()` to enqueue the `execute` action on an async thread.
-   Always call `getExportableSchedule()` before encoding a schedule for transmission. This inlines schedule references.

## BScheduleImportExt

Maps a local subordinate to a remote supervisor. Will be a child of the subordinate schedule.

### Execution

The `execute` action is where the local subordinate makes a request to the remote supervisor for a configuration update. The `executionTime` property controls when `execute` is invoked but it is turned off by default. Since `BScheduleImportExt.execute` will always result in a message to the remote supervisor, it is more efficient to have the supervisor push changes only when necessary.

When the schedule device extension performs subscription, it is simply invoking the `execute` action on `BScheduleImportExt`.

### Subclasses

-   Implement `doExecute()` to download the supervisor schedule configuration.
-   Implement `postExecute()` to enqueue the `execute` action on an async thread.
-   Can call `processExport()` to handle configuration updates from the remote supervisor.

## BScheduleExportManager

This is the manager view for local supervisor schedules. This is a convenience and can be ignored.

### Subclasses

-   Subclass `ScheduleExportModel` to add `MgrColumns` for properties added to your `BScheduleExport`.
-   Override `makeModel()` to return your new model.
-   Make the manager an agent on your schedule device extenstion.

## BScheduleImportManager

This is the manager view for local subordinate schedules. This is a convenience and can be ignored.

### Subclasses

-   Subclass `ScheduleImportModel` to add `MgrColumns` for properties added to your `BScheduleImportExt`.
-   Override `makeModel()` to return your new model.
-   Make the manager an agent on your schedule device extenstion.
