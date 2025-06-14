# History Devicelet Framework

**API Reference**: `javax.baja.driver.history`

## Overview

History device extensions manage exporting and importing histories (data logs) to and from remote devices for archiving purposes. For more information on Niagara histories, refer to the History documentation.

The `BHistoryDeviceExt` component is the container for archive descriptors which specify the details for importing/exporting histories. A concrete implementation of this component can be placed under a device (concrete implementation of `BDevice`) to specify the export/import behavior of histories to and from the device. The actual descriptions of each history export/import are contained in a subclass of `BArchiveDescriptor` which supplies the unique History Id for the history exported/imported. Since it is a `BDescriptor`, among other things it supplies the execution time for performing the export/import.

Two subclasses of `BArchiveDescriptor` are available:

-   `BHistoryExport` is used for exporting or pushing a history to a remote device (referred to as a history export descriptor).
-   `BHistoryImport` is used for importing or pulling a history from a remote device (referred to as a history import descriptor).

Currently these are the only two options, or active history descriptors. At present there are no passive history descriptors (i.e. history exported descriptor or history imported descriptor). Also, in the concrete Niagara Driver implementation, the code prevents a history export from occuring when there already exists a history import for a matching history id.

The `BHistoryNetworkExt` component manages network level functions for the history transfers. Its primary purpose is to be the container of the configuration rules (`BConfigRules`) that specify how the configuration of a history should be changed when a history is pushed (exported) into a Niagara station. Configuration rules are applied when an exported history is created. Changing a rule has no effect on existing histories. A `BConfigRule` entry has two String parameters used for matching a pushed history's device and history name, and once a match is found (the configuration rules are iterated in slot order, and the first match will be used), any override rules (properties) will be used in place of the corresponding properties on the incoming history's configuration (`BHistoryConfig`). For example, if you wanted to increase the history capacity on a history that has been received from an export for archiving purposes, you could supply an override property on a configuration rule to increase the capacity.
