// Package declaration for your device-level IDM components
package com.mea.n4datasync.model.device;

// IdmMeki22PController extends IdmBacnetDevice as it's a specific type of BACnet device
// No new imports needed if IdmBacnetDevice already imports IdmPoint

/**
 * Represents a Meki22P Controller in the Intermediate Data Model.
 * Extends IdmBacnetDevice as it's a specific type of BACnet device.
 *
 * This class holds properties unique to the Meki22P Controller.
 */
public class IdmMeki22PController extends IdmBacnetDevice {

    // --- Specific Fields for Meki22P Controller ---
    // For V1 MVP, assuming no additional unique fields beyond BacnetDevice common ones.
    // If there were Meki-specific properties (e.g., 'firmwareVersion'), they would go here.
    // private String firmwareVersion;

    /**
     * Constructor for IdmMeki22PController.
     * @param name The simple name of the controller.
     * @param parentPath The absolute Niagara slot path of its parent (e.g., "station:|slot:/Drivers/BacnetNetwork1/Floor1Devices").
     * @param deviceId The BACnet device ID.
     * @param macAddress The BACnet MAC address.
     */
    public IdmMeki22PController(String name, String parentPath, String deviceId, String macAddress) {
        // Call superclass constructor with appropriate Niagara type string
        // "bacnet:BacnetDevice" is the common type for a generic BACnet device.
        // If Meki22P had a custom Niagara type, you'd use that here (e.g., "meki:Meki22PController").
        super(name, "bacnet:BacnetDevice", parentPath, deviceId, macAddress);
        // Initialize any Meki-specific fields here
    }

    // --- Getters and Setters for specific fields (if any were added) ---
    // public String getFirmwareVersion() { return firmwareVersion; }
    // public void setFirmwareVersion(String firmwareVersion) { this.firmwareVersion = firmwareVersion; }

    @Override
    public String toString() {
        return "IdmMeki22PController{" +
                "name='" + name + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", points=" + points.size() + " children" +
                '}';
    }
}
