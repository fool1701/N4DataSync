# Lon Markup Language

## Overview

This document defines the content of a lon XML interface file. Basic syntax: The xml interface file represents a collection of objects. These objects may contain predefined elements and/or undefined elements. LonDevice contains the predefined element deviceData and any number of undefined NetworkVariables. As a general rule, elements which are not a defined element of the parent must have a type attribute. Defined elements must provide a value "v" attribute or contain defined elements.

```xml
<name  type="XLonXmlType">
<name  v="value">
```

**Example:**

```xml
<T7300h type="XLonDevice">
 <!-- Defined element deviceData with no type specified -->
 <deviceData
  <!-- Defined element with value -->
  <programID v="80 0 c 50 3c 3 4 17"/>
  . . .
 </deviceData>

 <!-- Undefined element with specified type -->
 <nviRequest type="XNetworkVariable">
  <index v="0"/>
 </nviRequest>
<T7300h type="XLonDevice">
```

The set of valid LonXmlTypes are: `XLonXMLInterfaceFile`, `XLonDevice`, `XEnumDef`, `XTypeDef`, `XNetworkVariable`, `XNetworkConfig`, `XConfigProperty`, `XMessageTag`

## LonXMLInterfaceFile

The root type is `LonXMLInterfaceFile`. It may contain `EnumDefs`, `TypeDefs`, and `LonDevices`. It may also reference other `LonXMLInterfaceFiles` to allow for `EnumDefs`, and `TypeDefs` to be shared. The file attribute indicates the element is an included file

```xml
<!-- Example with reference to other interface files. -->
<T7300h type="XLonXMLInterfaceFile">
 <HwTherm file="datatypes\HwTherm.lnml"/>
 <HwCommon file="datatypes\HwCommon.lnml"/>
 <T7300h type="XlonDevice">
 </T7300h>
<!-- Example with enumDefs and typeDefs included in single file. -->
<T7300h type="XLonXMLInterfaceFile">
 <HwThermAlarmEnum type="XenumDef"> . . . </HwThermAlarmEnum>
 <HwThermAlarm type="XTypeDef"> . . . </HwThermAlarm>
 <T7300h type="XLonDevice"> . . . </T7300h>
</T7300h>
```

## TypeDefs

`EnumDefs` and `TypeDefs` elements are needed to define the data portion of nvs, ncis, and config properties. An `EnumDef` contains a set of tag/id pairs where the name of the element is the tag and the value is the id.

```xml
<HwThermAlarmEnum type="XEnumDef">
 <NoAlarm v="0"/>
 <T7300CommFailed v="2"/>
 <AlarmNotifyDisabled v="255"/>
 </HwThermAlarmEnum>
```

A `TypeDef` contains a set of data elements. Each data element contains a name and set of qualifiers. The "qual" attribute contains a type field(u8, s8, b8, e8 ..), type restrictions (min,max) and encoding (resolution, byteOffset, bitOffset, len) information. For a description of valid element values see Appendix B. If an element is an enumeration then the enumDef attribute must be included to specify the name of the EnumDef used.

```xml
<HwThermAlarm type="XTypeDef">
 < elem n="subnet" qual="u8 res=1.0 off=0.0"/>
 < elem n="type" qual="e8" enumDef="HwThermAlarmEnum"/>
</HwThermAlarm>
```

A `TypeDef` element may also include "default" and "engUnit" attributes.

```xml
<HwThermConfig type="XTypeDef">
 <TODOffset qual="u8 byt=0 bit=0 len=4 min=0.0 max=15.0 "
    default="0" engUnit="F"/>
 <DeadBand qual="ub byt=0 bit=4 len=4 min=2.0 max=10.0 "
    default="2" engUnit="F"/>
</HwThermConfig>
```

A `TypeDef` may have nonstandard features which require a software implementation. This is the case for typedefs with unions. Unions are not currently supported. A `typeSpec` attribute can be used to specify a class file in a baja module as the implementation of the TypeDef. The class must be a subclass of `BLonData` and provide overrides to `byte[] toNetBytes()` and `fromNetBytes(byte[] netBytes)`.

```xml
<FileStatus type="XTypeDef">
 < typeSpec v="lonworks:LonFileStatus"/>
</FileStatus>
```

## LonDevice

A `LonDevice` consists of a defined element `deviceData` and sets of 0 or more of each `XNetworkVariable`, `XNetworkConfig`, `XConfigProperty`, and `XMessageTag` type elements.

```xml
<T7300h type="XLonDevice">
 <deviceData> . . . </deviceData>
 <nviRequest type="XNetworkVariable"> . . . </nviRequest>
 <nvoAlarmLog type="XNetworkVariable"> . . . </nvoAlarmLog>
  <nciApplVer type="XNetworkConfig"> . . . </nciApplVe>
 <ScheduleFile type="XConfigProperty"> . . . </ScheduleFile>
 <fx_explicit_tag type="XMessageTag"> . . . </fx_explicit_tag>
</T7300h>
```

## DeviceData

`DeviceData` is a defined set of values need to describe or qualify a lonworks device. A complete list of elements and their default values provided later.

```xml
<deviceData>
 <majorVersion v="4"/>
 <programID v="80 0 c 50 3c 3 4 17"/>
 <addressTableEntries v="15"/>
 . . .
</deviceData>
```

## NVs, NCIs, ConfigProps

`NetworkVariable`, `NetworkConfig` (nci), and `ConfigProperty` elements share a common structure. Each one consists of a set of defined elements and a data definition. See Appendix A for a complete list of defined elements and their default values. The data definition can take one of three forms:

1.  for standard types a `snvtType`(for nv/nci) or `scptType`(for nci/cp) element
2.  a `typeDef` element to specify the `XTypeDef` containing the data elements
3.  a set of `elem` entries contained in the nv,nci,cp with the same definition as used for `TypeDef`

```xml
<nvoAlarmLog type="XNetworkVariable>
 <index v="38"/>
 <direction v="output"/>
 <typeDef="HwThermAlarmLog"/>
</nvoAlarmLog>
<nviRequest type="XNetworkVariable">
 <index v="0"/>
 <snvtType v="objRequest"/>
 . . .
</nviRequest>
<nciSetpoints type="XNetworkConfig">
 <index v="17"/>
 <snvtType v="tempSetpt"/>
 . . .
</nciSetpoints>
<bypassTime type="XConfigProperty">
  <scptType v="CpBypassTime"/>
 <scope v="object"/>
 <select v="0"/>
 . . .
  </bypassTime>
```

## File Attribute

There will be cases where it is desirable to nest interface files. This will provide a means to share type definitions between multiple device interface files. It may also ease the process of auto generating the files when the data is contained in multiple forms (i.e. xif files, resource files, ...).

To include a file an element with the "file" attribute is included in the root. The path in the file attribute entry is specified relative to the containing file.

The following is an example of nested files. File #1 contains enum definitions, File #2 contains type definitions which use the enumDefs and file #3 contains the device definition which may use both.

**File #1 ..\honeywell\enum\ HwThermEnum.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<HwThermEnum type="XLonXMLInterfaceFile">
 <HwThermAlarmEnum type="XEnumDef">
  <NoAlarm v="0"/>
  <InvalidSetPtAlrm v="1"/>
  . . .
 </HwThermAlarmEnum>
 . . .
</HwThermEnum>
```

**File #2 ..\honeywell\datatypes\ HwTherm.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<HwTherm type="XLonXMLInterfaceFile">
 <HwThermEnum  file="..\enum\HwThermEnum.xml"/>
 <HwThermAlarm  type="XTypeDef">
  <elem n="subnet" qual="us res=1.0 off=0.0 "/>
  <elem n="node" qual="us res=1.0 off=0.0 "/>
  <elem n="alarmType" qual="en " enumDef="HwThermAlarmEnum"/>
 </HwThermAlarm>
 . . .
</HwTherm>

```

**File #3 ..\honeywell\**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<T7300h type="XLonXMLInterfaceFile">
 <HwTherm  file="..\datatypes\HwTherm.xml"/>
 <T7300h type="XLonDevice">
   <nvoAlarm type="XNetworkVariable" >
   <typeDef="HwThermAlarm"/>
   <index v="36"/>
   <direction v="output"/>
...
  </nvoAlarm>
 </T7300h>
</T7300h>
```

## XDeviceData Definition

See LonMark External Interface File Reference Guide 4.0B

| Type | Name | Default | Valid Values |
| --- | --- | --- | --- |
| int | majorVersion | 0 | - |
| int | minorVersion | 0 | - |
| byte[] | programID | 0 0 0 0 0 0 0 0 | - |
| int | domains | 2 | - |
| int | addressTableEntries | 0 | - |
| boolean | handlesIncomingExplicitMessages | false | - |
| int | numNvDeclarations | 0 | - |
| int | numExplicitMessageTags | 0 | - |
| int | networkInputBuffers | 0 | - |
| int | networkOutputBuffers | 0 | - |
| int | priorityNetworkOutputBuffers | 0 | - |
| int | priorityApplicationOutputBuffers | 0 | - |
| int | applicationOutputBuffers | 0 | - |
| int | applicationInputBuffers | 0 | - |
| int | sizeNetworkInputBuffer | 0 | - |
| int | sizeNetworkOutputBuffer | 0 | - |
| int | sizeAppOutputBuffer | 0 | - |
| int | sizeAppInputBuffer | 0 | - |
| String | applicationType | unknown | unknown,mip,neuron,hostSelect,hostNISelect |
| int | numNetworkVariablesNISelect | 0 | - |
| int | rcvTransactionBuffers | 0 | - |
| int | aliasCount | 0 | - |
| boolean | bindingII | false | - |
| boolean | allowStatRelativeAddressing | false | - |
| int | maxSizeWrite | 11 | - |
| int | maxNumNvSupported | 0 | - |
| int | neuronChipType | 0 | - |
| int | clockRate | 0 | - |
| int | firmwareRevision | 0 | - |
| int | rcvTransactionBlockSize | 0 | - |
| int | transControlBlockSize | 0 | - |
| int | neuronFreeRam | 0 | - |
| int | domainTableEntrySize | 0 | - |
| int | addressTableEntrySize | 0 | - |
| int | nvConfigTableEntrySize | 0 | - |
| int | domainToUserSize | 0 | - |
| int | nvAliasTableEntrySize | 0 | - |
| boolean | standardTransceiverTypeUsed | true | - |
| int | standardTransceiverTypeId | 0 | - |
| int | transceiverType | 0 | - |
| int | transceiverInterfaceRate | 0 | - |
| int | numPrioritySlots | 0 | - |
| int | minimumClockRate | 0 | - |
| int | averagePacketSize | 0 | - |
| int | oscillatorAccuracy | 0 | - |
| int | oscillatorWakeupTime | 0 | - |
| int | channelBitRate | 0 | - |
| boolean | specialBitRate | false | - |
| boolean | specialPreambleControl | false | - |
| String | specialWakeupDirection | input | input,output |
| boolean | overridesGenPurposeData | false | - |
| int | generalPurposeData1 | 0 | - |
| int | generalPurposeData2 | 0 | - |
| int | generalPurposeData3 | 0 | - |
| int | generalPurposeData4 | 0 | - |
| int | generalPurposeData5 | 0 | - |
| int | generalPurposeData6 | 0 | - |
| int | generalPurposeData7 | 0 | - |
| int | rcvStartDelay | 0 | - |
| int | rcvEndDelay | 0 | - |
| int | indeterminateTime | 0 | - |
| int | minInterpacketTime | 0 | - |
| int | preambleLength | 0 | - |
| int | turnaroundTime | 0 | - |
| int | missedPreambleTime | 0 | - |
| int | packetQualificationTime | 0 | - |
| boolean | rawDataOverrides | false | - |
| int | rawDataClockRate | 0 | - |
| int | rawData1 | 0 | - |
| int | rawData2 | 0 | - |
| int | rawData3 | 0 | - |
| int | rawData4 | 0 | - |
| int | rawData5 | 0 | - |
| String | nodeSelfID | "" | - |

### NetworkVariable and Network Config common elements

| Type | Name | Default | Valid Values |
| --- | --- | --- | --- |
| String | snvtType | "xxx" | from SNVT Master List."SNVT_angle_vel" becomes "angleVel". |
| int | index | -1 | - |
| int | averateRate | 0 | - |
| int | maximumRate | 0 | - |
| int | arraySize | 1 | - |
| boolean | offline | false | - |
| boolean | bindable | true | - |
| String | direction | "input" | input,output |
| String | serviceType | "unacked" | acked, repeat, unacked, unackedRpt |
| boolean | serviceTypeConfigurable | true | - |
| boolean | authenticated | false | - |
| boolean | authenticatedConfigurable | true | - |
| boolean | priority | false | - |
| boolean | priorityConfigurable | true | - |

### NetworkVariable only elements

| Type | Name | Default | Valid Values |
| --- | --- | --- | --- |
| String | objectIndex | "" | - |
| int | memberIndex | -1 | - |
| int | memberArraySize | 1 | - |
| boolean | mfgMember | false | - |
| boolean | changeType | false | - |

### NetworkConfig only elements

| Type | Name | Default | Valid Values |
| --- | --- | --- | --- |
| String | scptType | "" | - |
| String | scope | "node" | node,object,nv |
| String | select | "" | If for node select=-1. Possible formates are n n~m n-m n.m n/m |
| String | modifyFlag | "anytime" | anytime, mfgOnly, reset, constant, offline, objDisable, deviceSpecific |
| float | max | Float.NaN | - |
| float | min | Float.NaN | - |
| boolean | changeType | false | - |

### ConfigParameter elements

| Type | Name | Default | Valid Values |
| --- | --- | --- | --- |
| String | scptType | "" | - |
| String | scope | "node" | node,object,nv |
| String | select | "" | If for node select=-1. Possible formates are n n~m n-m n.m n/m |
| String | modifyFlag | "anytime" | anytime, mfgOnly, reset, constant, offline, objDisable, deviceSpecific |
| int | length | 0 | - |
| int | dimension | 1 | - |
| float | max | Float.NaN | - |
| float | min | Float.NaN | - |
| String | principalNv | "" | if the scope is object and the scpt is inherited then this specifies the memberNumber of the principalNv in the selected object. Prefixed with '#' if mfgDefined member '|' if standard member. |

### Element Qualifier

The format for an element attribute is:
`qual="Type [qualifier=xx]"`

example: `qual="u8 res=0.1 min=5 max=12"`

| Type | Device Data Type | Valid Qualifiers |
| --- | --- | --- |
| c8 | character - 1 byte | - |
| s8 | signed short - 1 byte | res, off, min, max, invld |
| u8 | unsigned short - 1 byte | res, off, min, max, invld |
| s16 | signed long - 2 byte | res, off, min, max, invld |
| u16 | unsigned lon - 2 byte | res, off, min, max, invld |
| f32 | float - 4 byte | res, off, min, max, invld |
| s32 | signed int - 4 bytes | res, off, min, max, invld |
| b8 | boolean - 1 byte | - |
| e8 | enumeration - 1 byte | - |
| bb | boolean in bit field | byt, bit, len |
| eb | enumeration in bit field | byt, bit, len |
| ub | unsigned int in bit field | byt, bit, len, min, max, invld |
| sb | signed int in bit field | byt, bit, len, min, max, invld |
| st | string | len |
| na | no type - byte array | len |

| Qualifier Code | Description | Default |
| --- | --- | --- |
| res | resolution float | 1.0 |
| off | Offset | 0.0 |
| min | Minimum legal value | Not specified |
| max | Maximum legal value | Not specified |
| invld | Invalid value | Not specified |
| byt | Byte offset - 0 based | -1 |
| bit | Bit offset - 0 based, 7 for msb, 0 for lsb | 0 |
| len | Number of bytes(na), char(st) or bits(bb,eb,ub) | 1 |
