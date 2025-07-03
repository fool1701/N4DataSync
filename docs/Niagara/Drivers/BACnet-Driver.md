# BACnet Driver

## Overview

The Niagara AX BACnet driver provides both client and server side BACnet functionality. On the server side, the Niagara station is represented as a BACnet device on the network. Certain objects in Niagara can be exposed as BACnet Objects. Niagara will respond to BACnet service requests for these objects, according to the BACnet specification. On the client side, Niagara can represent other BACnet devices in the Framework. Properties of BACnet objects can be brought into Niagara as BACnet Proxy Points. In addition, the BACnet driver provides client side schedule and trend log access. The BACnet objects can also be viewed as a whole, using the Config views. Both client-side and server-side alarm support is provided, using the intrinsic alarming mechanism.

The basic components in the BACnet driver are:

-   **BBacnetNetwork**: This represents the BACnet network in Niagara.
-   **BLocalBacnetDevice**: This represents Niagara as a BACnet device.
-   **BBacnetDevice**: This models a remote BACnet device.

## Server

The server side functionality of the driver is accomplished by using export descriptors to map Niagara objects as BACnet Objects. The Local BACnet Device contains an export table from where all of the export descriptors are managed. The `javax.baja.bacnet.export` package contains the standard export descriptors. The base interface for an export descriptor, which must be implemented by all export descriptors, is `BIBacnetServerObject`. This contains the methods that are used by the comm stack and export mechanisms to access the BACnet Object properties of whatever Niagara object is being exported.

The primary classes implementing this interface are:

-   **BBacnetPointDescriptor** and its subclasses - for exporting control points.
-   **BBacnetScheduleDescriptor** and its subclasses - for exporting schedules.
-   **BBacnetTrendLogDescriptor** and **BBacnetNiagaraHistoryDescriptor** - for exporting native BACnet trend logs and Niagara histories, respectively.
-   **BBacnetFileDescriptor** - for exporting file system files.
-   **BBacnetNotificationClassDescriptor** - for exporting Niagara `BAlarmClass`es as Notification Class objects.

Wherever a BACnet property is available directly from the exported Niagara object, this property is used. In some cases, a BACnet-required property is not available on the Niagara object being exported. In those cases, the property is defined within the export descriptor itself.

To export an object, the Bacnet Export Manager is used. A BQL query is made against the station to find components of a particular type, and the results are displayed. When a decision is made to add an export descriptor for a particular component, the registry is searched for export descriptors that are registered as agents on the component's Type. If any are found, these are presented to the user in the Add dialog.

For accepting writes, the BACnet driver requires that a BACnet user be defined in the User Service. The password for this user is not important, except for Device Management functions such as `DeviceCommunicationControl` and `ReinitializeDevice`. The permissions assigned for this user define what level of access is allowed for BACnet devices. Reads are always allowed; writes and modifications (such as `AddListElement`) are governed by the permissions of the BACnet user. If no BACnet user is defined, writes are not allowed.

The main area where the server side of the BACnet driver is extensible is through the creation of new export descriptor types. To create export descriptors for object types that are not currently exportable (such as a String Point), you simply need to create a class that implements `BIBacnetServerObject`. You may find that you want to subclass one of the base export descriptor classes mentioned above, or you may find it easier to create your own, using these classes as a guide.

## Client

The client side functionality of the driver is accomplished with the `BBacnetDevice` and its device extensions. There are extensions for each of the normalized models:

-   **BBacnetPointDeviceExt** - for modeling properties of BACnet objects into Niagara control points.
-   **BBacnetScheduleDeviceExt** - for representing BACnet Schedules as Niagara schedules for monitor or control.
-   **BBacnetHistoryDeviceExt** - for representing BACnet Trend Logs as Niagara histories for configuration and archiving.
-   **BBacnetAlarmDeviceExt** - for managing BACnet alarms from the device.
-   **BBacnetConfigDeviceExt** - for viewing and modifying BACnet Objects in their native model - as an entire object, rather than by individual properties.

BACnet Proxy Points are configured by using a `BBacnetProxyExt`. There are four subclasses of this, one for each type of Niagara control point. The extensions are polymorphic, in that they know how to convert data from any of the primitive data types to the data type of their parent point. Any proxy point can be written to if it is of the proper type. The BACnet proxy extensions manage writes for both priority-array and non-prioritized points.

BACnet client-side Scheduling can be accomplished in two ways:

-   **BBacnetScheduleExport** - This descriptor is used when Niagara is the supervisor, driving the schedule in the device. It contains the object identifier of the remote schedule, and the ord to the Niagara schedule that is to be the source of scheduling data. At configurable times this data is written down to the remote schedule.
-   **BBacnetScheduleImportExt** - This extension is used when the remote schedule is the source of data, and Niagara is simply reading scheduling information from the device. The schedule is queried at configurable times to update the Niagara schedule.

BACnet client-side Trending is accomplished by using the `BBacnetHistoryImport`. This descriptor periodically archives data from the Trend Log object in the remote device for storage by the Niagara station.

The operation of BACnet objects is sometimes easier to understand when the object is viewed as a whole, with all of its properties viewed together. For this reason, the Config device extension is provided. This allows you to view, for example, all of the properties of an Analog Input object together, without having to create proxy points for all of them. The expected use case is initial configuration or commissioning. The base object for representing BACnet Objects is `BBacnetObject`. Specific subclasses for BACnet standard object types exist in `javax.baja.bacnet.config`.

The main areas where the client side of the BACnet driver is extensible are:

1.  **BBacnetDevice**: For specialized device behavior, the `BBacnetDevice` can be subclassed. This is not for adding additional BACnet properties; the device object properties are contained in the `BBacnetDeviceObject`. Each `BBacnetDevice` has an enumeration list which contains all of the extensions known to that device. Specific device classes might have preconfigured entries for these enumerations, that allow it to better interpret and represent proprietary enumeration values received from this device.
2.  **BBacnetObject**: For specialized object types, such as a representation of a proprietary object type, the `BBacnetObject` class should be subclassed. This includes any specific device object properties, which would be contained in a subclass of `BBacnetDeviceObject`.
3.  **Proprietary data types**: If any proprietary data types are created, they can be modelled corresponding to the data types in `javax.baja.bacnet.datatypes`. Primitive data types are generally modelled as simples. Constructed data types are generally modelled as a subclass of `BComplex`. The data type must implement `BIBacnetDataType`.
4.  **Proprietary enumerations**: Proprietary enumerations can also be created. If a property in an object is of an extensible enumeration, it should be modelled as a dynamic enum whose range is defined by the specified frozen enum. Examples of both extensible and non-extensible enumerations exist in `javax.baja.bacnet.enum`.

For additional information, refer to the BACnet API.
