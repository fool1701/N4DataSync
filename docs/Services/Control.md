# Control

## Overview

The control module provides normalized components for representing control points. All control points subclass from the `BControlPoint` base class. Control points are typically used with the driver framework to read and write points in external devices.

There are four normalized categories of data matching the four `BStatusValue` types. Within each of the four categories is a readonly component and a writable component. These eight components are:

| Type | Mode | Data |
|---|---|---|
| `BBooleanPoint` | RO | Models boolean data with `BStatusBoolean` |
| `BBooleanWritable` | RW / WO | Models boolean data with `BStatusBoolean` |
| `BNumericPoint` | RO | Models numeric or analog data with `BStatusNumeric` |
| `BNumericWritable` | RW / WO | Models numeric or analog data with `BStatusNumeric` |
| `BEnumPoint` | RO | Models discrete values within a fixed range with `BStatusEnum` |
| `BEnumWritable` | RW / WO | Models discrete values within a fixed range with `BStatusEnum` |
| `BStringPoint` | RO | Models unicode strings with `BStatusString` |
| `BStringWritable` | RW / WO | Models unicode strings with `BStatusString` |

## Design Patterns

All control points use `BStatusValue`s to represent their inputs and output. All points have one output called "out". The readonly points contain no inputs. Typically they model a value being read from a device via the driver framework.

The writable points all contain 16 inputs and a fallback value. These 16 inputs are prioritized with 1 being the highest and 16 being the lowest. The value to write is calculated by finding the highest valid input (1, 2, 3, down to 16). An input is considered valid if none of the following status bits are set: `disabled`, `fault`, `down`, `stale`, or `null`. If all 16 levels are invalid, then the fallback value is used. Note that the fallback value itself can have the null bit set in which case the point outputs null. The active level is indicated in the output as a status facet.

Each of the writable points reserves level 1 and level 8 for user invoked overrides. Level 1 is an emergency override which when invoked remains in effect permanently until the `emergencyAuto` action is invoked. Level 8 overrides are for normal manual overrides. Manual overrides may be timed to expire after a period of time, or may be explicitly canceled via the auto action. Whenever level 1 or 8 is the active level then the `overridden` status bit is set in the output. If a timed override is in effect then the `overrideExpiration` property indicates when the override will expire.

## Extensions

Extensions provide building blocks to extend and change the behavior of control points. Every extension must derive from `BPointExtension`. They are added as dynamic properties on a control point. Extensions can process and modify the value of a control point whenever it executes. For example, an alarm extension can monitor the value and set the alarm bit of the output's status if an alarm condition was detected. A list of extensions include:

- `BDiscreteTotalizerExt`
- `BNumericTotalizerExt`
- `BProxyExt`
- `BAlarmSourceExt`
- `BIntervalHistoryExt`
- `BCovHistoryExt`

Extensions are always invoked in the order they are declared in the slot list. They may be reordered using the standard reorder API and workbench commands.

When the `execute` method is invoked on a `BControlPoint`, the `pointChanged(ControlPoint pt)` method is in turn invoked on each extension.

Note that when using extensions with driver proxy points, only the value being read is processed by extensions.
