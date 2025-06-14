# Point Devicelet Framework

## Overview

The `javax.baja.driver.point` API is used to perform point IO with logical or physical control points. Drivers use the standard control points found in the control module. But each driver provides a specialization of `BProxyExt` for driver specific addressing, tuning, and IO.

Refer to Architecture - Driver Hierarchy for an illustration of the component slot hierarchy.

Refer to Architecture - ProxyExt for an illustration of the design.

## Point Modes

There are three modes which a proxy point may operate in:

-   **Readonly**: These points are read from the device, but never written.
-   **ReadWrite**: These are points which the driver can both read from and write to.
-   **Writeonly**: These are points which the driver can write to, but cannot read.

A `ProxyExt` must indicate which mode it is operating by overriding the `getMode()` method.

## Proxy Ext

The `ProxyExt` component contains two properties used for managing read and write values.

The `readValue` property indicates the last value read from the device. For writeonly points this is the last value successfully written. This value is used to feed the parent point's extensions and out property. If numeric, it is in device units.

The `writeValue` property stores the value currently desired to be written to the device. If numeric, it is in device units.

## Framework to Driver Callbacks

Driver developers have three callbacks which should be used to manage reads and writes:

-   **ProxyExt.readSubscribed()**: This callback is made when the point enters the subscribed state. This is an indication to the driver that something is now interested in this point. Drivers should begin polling or register for changes.
-   **ProxyExt.readUnsubscribed()**: This callback is made when the point enters the unsubscribed state. This is an indication to the driver that no one is interested in the point's current value anymore. Drivers should cease polling or unregister for changes.
-   **ProxyExt.write()**: This callback is made when the framework determines that a point should be written. The tuning policy is used to manage write scheduling.

**Note**: All three callbacks should be handled quickly and should never perform IO on the callers thread. Instead drivers should use queues and asynchronous threads to perform the actual IO.

## Driver to Framework Callbacks

The `ProxyExt` contains a standard API which the driver should call once a read or write operation has been attempted. If a read operation completes successfully then `readOk()` method should be called with the value read. If the read fails then call the `readFail()` method.

If a write operation completes successfully then the `writeOk()` method should be called with the value written. If the write fails for any reason then call `writeFail()`.

## Tuning

All `ProxyExts` contain a Tuning property that manages how read and writes are tuned. All drivers which implement proxy points should create a "tuningPolicies" property of type `TuningPolicyMap` on their `DeviceNetwork`. The Tuning structure on each `ProxyExt` identifies its `TuningPolicy` within the network by slot name. `TuningPolicies` allow users to configure which state transitions result in a `write()` callback. `TuningPolicies` may also be used to setup a `minWriteTime` to throttle writes and a `maxWriteTime` to do rewrites.

## Utilities

The driver framework provides a suite of APIs to aid developers in building their drivers:

-   **BPollScheduler**: This is a prebuild component that manages polling the points using a set of configurable buckets. To use this feature have your `ProxyExt` implement the `BIPollable` interface.
-   **ByteBuffer**: This class provides a wealth of methods when working with byte buffers such as reading and writing integers using big or little endian.
