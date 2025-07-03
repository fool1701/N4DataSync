# Driver Framework

## Overview

The driver framework provides a common model for abstracting how information is imported and exported from the station VM. The model is built upon the following concepts:

-   **BDeviceNetwork**: This models a physical or logical network of devices.
-   **BDevice**: This models a physical or logical device such as a fieldbus device or an IP host.
-   **BDeviceExt**: This models a functional integration at the device level which imports and/or exports a specific type of information such a points, histories, alarms, or schedules.
-   **BNetworkExt**: This models an functional extension at the network level.

## Driver Hierarchy

Drivers are always structured according to a fixed slot hierarchy as illustrated the Driver Hierarchy Diagram:

-   **DriverContainer**: Typically all drivers are located in this folder directly under the station root.
-   **DeviceNetwork**: Models the specific driver's protocol stack.
-   **DeviceFolder**: Zero or more levels of `DeviceFolder` can be used to organize the driver's Devices.
-   **Device**: Devices model the physical or logical device of the driver. Devices are descendents of the `DeviceNetwork` either as direct children or inside `DeviceFolder`s.
-   **DeviceExt**: `DeviceExt`s are always direct children of Devices, typically declared as frozen slots.

Within each `DeviceExt`, there is usually a well defined hierarchy. For example the `PointDeviceExt` follows a similar model with `PointDeviceExt`, `PointFolders`, `ControlPoints`, and `ProxyExt`.

## Status

A key function of the driver framework is providing normalized management of status. The follows semantics are defined for status flags:

-   **Disabled**: the user manually disabled the driver component
-   **Fault**: a configuration, hardware, or software error is detected
-   **Down**: a communication error has occurred
-   **Stale**: a situation has occurred (such as elapsed time since a read) which renders the current value untrustworthy

The driver framework provides a standard mechanism to manage each of these status flags. A component is disabled when a user manually sets the `enabled` property to false. Disable automatically propagates down the tree. For example setting the network level disabled automatically sets all devices and points under it disabled.

The fault status is typically a merge of multiple fault situations. The driver framework does its own fault detection to detect fatal faults. Fatal faults typically occur because a device or component has been placed inside the wrong container (such as putting a `ModbusDevice` under a `LonworksNetwork`). Licensing failures can also trigger fatal faults. Driver developers can set their own fault conditions in networks and devices using the `configFail()` and `configOk()` methods. A `faultCause` method provides a short description of why a component is in fault. Fault conditions automaticlly propagate down the tree.

The down status indicates a communication failure at the network or device level. Down status is managed by the ping APIs using `pingFail()` and `pingOk()`. Ping status is maintained in the `health` property. The driver framework includes a `PingMonitor` which automatically pings devices on a periodic basis to check their health. The `PingMonitor` can generate alarms if it detects a device has gone down.

## DeviceExts

The following standard device extensions provide a framework for working specific types of data:

-   **Point**: For reading and writing proxy points.
-   **History**: For importing and exporting histories.
-   **Alarm**: For routing incoming and outgoing alarms.
-   **Schedule**: Used to perform master/slave scheduling.

## User Interfaces

The driver framework provides a comprehensive set of APIs for building tools for managing configuration and learns based on the `AbstractManager` API. Also see the Driver Learn illustration.
