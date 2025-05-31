// Package declaration for your device-level IDM components
package com.mea.n4datasync.model.device;

import com.mea.n4datasync.model.IdmComponent;
import com.mea.n4datasync.model.point.IdmPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all BACnet Devices in the Intermediate Data Model.
 * Extends IdmComponent to inherit common properties.
 *
 * This class holds properties common to all BACnet devices and manages their child points.
 */
public abstract class IdmBacnetDevice extends IdmComponent {

    // --- Common Fields for BacnetDevice ---

    /**
     * The BACnet device ID. Corresponds to BacnetDevice.deviceId in Niagara.
     */
    protected String deviceId; // Using String for simplicity in V1, can be int/long later

    /**
     * The BACnet MAC Address. Corresponds to BacnetDevice.macAddress in Niagara.
     * Assuming MSTP or IP - for IP, this might be the IP address.
     */
    protected String macAddress;

    /**
     * List to hold child points.
     * This list will be populated from your external data source.
     */
    protected List<IdmPoint> points;

    /**
     * Constructor for IdmBacnetDevice.
     * @param name The simple name of the BACnet Device.
     * @param type The Niagara Type specification string for the device (e.g., "bacnet:BacnetDevice").
     * @param parentPath The absolute Niagara slot path of its parent (e.g., "station:|slot:/Drivers/BacnetNetwork1/Floor1Devices").
     * @param deviceId The BACnet device ID.
     * @param macAddress The BACnet MAC address.
     */
    public IdmBacnetDevice(String name, String type, String parentPath, String deviceId, String macAddress) {
        super(name, type, parentPath);
        this.deviceId = deviceId;
        this.macAddress = macAddress;
        this.points = new ArrayList<>();
    }

    // --- Getters and Setters for common fields ---

    /**
     * Gets the BACnet device ID.
     * @return The device ID.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Sets the BACnet device ID.
     * @param deviceId The new device ID.
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Gets the BACnet MAC Address.
     * @return The MAC address.
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * Sets the BACnet MAC Address.
     * @param macAddress The new MAC address.
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * Gets the list of child points.
     * @return A list of IdmPoint objects.
     */
    public List<IdmPoint> getPoints() {
        return points;
    }

    /**
     * Adds an IdmPoint as a child to this device.
     * Also sets the child's parent path and adds it to the generic children list.
     * @param point The IdmPoint to add.
     */
    public void addPoint(IdmPoint point) {
        if (point != null) {
            this.points.add(point);
            // Crucial: Set the child's parent path to this component's absolute path
            point.setParentPath(this.getAbsolutePath());
            // Add to generic children list for polymorphic traversal if needed
            super.addChild(point);
        }
    }

    @Override
    public String toString() {
        return "IdmBacnetDevice{" +
                "name='" + name + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", points=" + points.size() + " children" +
                '}';
    }
}
