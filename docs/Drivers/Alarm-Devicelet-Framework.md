# Alarm Devicelet Framework

The `BAlarmDeviceExt` handles the sending an receiving of alarms to and from remote devices. It is both and alarm source, implementing `BIRemoteAlarmSource`, and an alarm recipient, implementing `BIRemoteAlarmRecipient`.

## Receiving Alarms

`BAlarmDeviceExt` is used for receiving alarms from a remote device. The `BAlarmDeviceExt` should be used as the source for all incoming alarms. If more detail is needed about the actual source, the `BAlarmRecord.SOURCE_NAME` or additional fields in the `BAlarmRecord`'s `alarmData` can be used. Alarm Ack Request will be routed back to the `BAlarmDeviceExt` when it is set as the source.

In Niagara Offnormal and Normal alarms are not two separate alarms as is found in some systems. In Niagara Offnormal and Normal are two states of the same alarm. This is important to keep in mind is not using the `AlarmSupport` class as each offnormal alarm generated will need it's source state set to Normal when it's source goes back to the normal state.

## Sending Alarms

Sending alarms from the Niagara system to a remote device is accomplished by implmenting a `BAlarmRecipient`. The `BAlarmRecipient`'s `handleAlarm` method should route alarms from the Niagara system to the remote device and the originating source. The actual sending of alarms to the device network should be done on a separate thread so as to not block the control engine thread. The `DeviceExt` should not attempt to send alarms to Devices which are down or disabled.
