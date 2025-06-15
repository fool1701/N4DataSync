// Package declaration for your device-level IDM components
package com.mea.n4datasync.model.device;

import com.mea.n4datasync.model.IdmComponent;
import com.mea.n4datasync.model.point.IdmPoint; // Import IdmPoint if devices directly contain points

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BACnet Device Folder in the Intermediate Data Model.
 * Extends IdmComponent to inherit common properties.
 *
 * This class primarily acts as a container for devices within the BACnet Network hierarchy.
 * In Niagara, device folders are typically 'sys:Folder' components.
 */
public class IdmBacnetDeviceFolder extends IdmComponent {

    // --- Specific Fields for BacnetDeviceFolder (minimal, as it's a container) ---
    // No unique fields beyond inherited 'name', 'type', 'parentPath' for V1.

    /**
     * List to hold child BACnet Device components (e.g., Meki22P Controllers).
     * This list will be populated from your external data source.
     */
    private List<IdmBacnetDevice> devices; // Using IdmBacnetDevice base class

    /**
     * Constructor for IdmBacnetDeviceFolder.
     * @param name The simple name of the device folder.
     * @param parentPath The absolute Niagara slot path of its parent (e.g., "station:|slot:/Drivers/BacnetNetwork1").
     */
    public IdmBacnetDeviceFolder(String name, String parentPath) {
        // Call superclass constructor with appropriate Niagara type string
        // "sys:Folder" is the common Niagara type for generic folders.
        super(name, "sys:Folder", parentPath);
        this.devices = new ArrayList<>();
    }

    // --- Getters and Setters for specific fields ---

    /**
     * Gets the list of child BACnet devices.
     * @return A list of IdmBacnetDevice objects.
     */
    public List<IdmBacnetDevice> getDevices() {
        return devices;
    }

    /**
     * Adds an IdmBacnetDevice as a child to this folder.
     * Also sets the child's parent path and adds it to the generic children list.
     * @param device The IdmBacnetDevice to add.
     */
    public void addDevice(IdmBacnetDevice device) {
        if (device != null) {
            this.devices.add(device);
            // Crucial: Set the child's parent path to this component's absolute path
            device.setParentPath(this.getAbsolutePath());
            // Add to generic children list for polymorphic traversal if needed
            super.addChild(device);
        }
    }

    @Override
    public String toString() {
        return "IdmBacnetDeviceFolder{" +
                "name='" + name + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                ", devices=" + devices.size() + " children" +
                '}';
    }
}
