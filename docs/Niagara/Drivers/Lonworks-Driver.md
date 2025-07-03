# Lonworks Driver

## Overview

The Lonworks API provides the means to model lonwork networks and devices for configuration and run time control. A network is a collection of connected lonworks devices and routers.

### Basic components

-   **BLonNetwork**: Is the top level container for `BLonDevices`. It provides manager views for commissioning, binding and trouble shooting.
-   **BLonDevice**: Provides a database model for `lonDevices` to facilitate configuration and access to run time data.
-   **BLonProxyExt**: Customizes proxy points for data elements on `lonDevices`. Proxy points provide the mechanism to interface point data in devices with Niagara control logic and graphics

### Misc components

-   **BLonRouter**: Contains the database model needed for network management of lonworks router.

## LonDevice

A `BLonDevice` contains `BDeviceData` and the means to manage a collection of `BLonComponents` and `BMessageTags`.

-   **BLonComponents** are database representation of specific components in a device. They contain one or more data elements (see LonDataModel below) and specific config information. There are three types: `BNetworkVariable`, `BNetworkConfig`, `BConfigParameter`.
-   **BMessageTags** are only for linking. There is no behavior implemented in massage tags in the station.

`BLonDevice` is an abstract class and is the root class for all lonworks devices. There are two flavors of `BLonDevice` implemented in the lonworks drivers:

-   **BLocalLonDevice** is a final class which provides the means to manage the local neuron. It is a frozen slot on `BLonNetwork`.
-   **BDynamicDevice** provides support for dynamically building the devices data and `BLonComponents`. There are two actions to accomplish this: `learnNv` uses the self documentation in the device, `importXLon` uses an xml file containing a representation of the device.

## Lon Data Model

Each `BLonComponent` contains one or more data elements which mirror the data structure of the component in the device. These data elements are managed by `BLonData` which handles conversion between the database representation and the devices binary format. `BLonData` can also contain other `BLonData` components allowing for nested data types.

**NOTE**: `BLonData` has been folded into `BLonComponent`. The effect is to place the data elements at the same tree level as the `LonComponent` config properties. This was done to improve efficiency in the workbench. The getter settor methods in `BLonComponent` access `LonData` as though it were contained by the `LonComponent`. The `getData()` method will return the `BLonComponent` as a `BLonData`. The `setData()` method will replace the current data elements with the new elements passed in data argument.

Each data element is modeled as a `BLonPrimitive`. There are `BLonPrimitives` for each primitive datatype.

-   `BLonBoolean` models a boolean element
-   `BLonEnum` models a enumeration element
-   `BLonFloat` models a numeric element
-   `BLonString` models a string element

### Special element types

-   `BLonInteger` models a numeric element when full 32 bits of data is needed
-   `BLonByteArray` is used in special cases to model data when can not be meaningfully modeled as primitive elements
-   `BLonSimple` is used in special cases to model data which has been representated by a simple. The simple must implement `BILonNetworkSimple`.

## Proxy points

Proxy points are standard Niagara control points used to access data elements in foreign devices. Proxy points have a driver specific `ProxyExt` that handles addressing data elements in a specific device and data conversion needed to present the data in a normalized format. The inputs and outputs of proxies can be linked to other control logic or graphical points.

A `BLonProxyExt` in a Proxy point makes it a lonworks proxy point. There are different `BLonProxyExts` for each primitive data type. These can be seen in `javax.baja.lonworks.proxy`.

Lon Proxy Points are managed by `LonPointManager` which is a view on the points container in each `BLonDevice`.

## Network Management

Implements a set of standard lonworks network management functions. The user has access to these functions through the following manager view.

-   **DeviceManager** - provides support for discovering and adding lonwork devices to the database, for managing device addresses, and downloading standard applications to devices.
-   **RouterManager** - provides support for discovering and adding lonwork routers to the database, and for managing device addresses
-   **LinkManagar** - provides means to manage link types and bind links.
-   **LonUtiliesManager** - provides a set of utilities useful for managing a lon network

## LonComm

The lonworks communication stack can be accessed through a call to `BLonNetwork.lonComm()`. `LonComm` is provides APIs which allow the user to send `LonMessages` with one of the LonTalk service types (unackowledged, acknowledged, unackowledged repeat, request response).

`LonComm` also provides a means to receive unsolicited messages by registering a `LonListener` for a specifed message type from an optional subnetNode address.

### LonMessage

`LonMessage` is the base class for all messages passed to/from `LonComm` APIs.

Users should subclass `LonMessage` if they wish to create a new explicit message type.

A set of LonTalk defined messages is provide in `com/tridium/lonworks/netmessages`. The definition of these message is found in Neuron Chip Data Book Appendix B, Lonworks Router User's Guide, and EIA/CEA-709.1-B.

## Mapping ProgramId to Device Representation

A mechanism is provided to associate an xml or class file with a particular device type. The device type is identifyed by its ProgramId as described in LonMark Application-Layer Interoperability Guidelines.

The association is created by putting "def" entries in a modules `module-include.xml` file. This association is used during the learn process to determine the appropriate database entity for discovered devices.

A "def" entry consists of name and value attribute.

The name has the formate "lonworks:programId" where programId is the devices ProgramId represented as 8 hex encoded bytes with leading zeros and `<space>` delimiter. Multiple mappings are allowed for the same programId. Any nibble can be replaced with an `*` to indicate a range of programIds mapped to the same object. The value field can reference a class or xml file.

The formate for a class is `cl=module:cname`. The module is the niagara module containing the class and the cname is the name as defined in the `module-include.xml` for that module. The class must be a sub class of `BLonDevice` or `BDynamicDevice`.

The formate for an xml file is `xml=module/xname`. The module is the niagara module containing the xml file and the xname is the name of the file containing the device representation. The xml file formate is described in Lon Markup Language.

Examples of def entries in lon device `module-include.xml` file.

```xml
<defs>
  <def name="lonworks.80 00 0c 50 3c 03 04 17"  value="cl=lonHoneywell:Q7300"
/>
  <def name="lonworks.80 00 16 50 0a 04 04 0a"
value="xml=lonSiebe/Mnlrv3.lnml"/>
  <def name="lonworks.80 00 8e 10 0a 04 0* **"
value="xml=lonCompany/dev.lnml"/>
</defs>
```

For further information refer to the Lonworks API.
