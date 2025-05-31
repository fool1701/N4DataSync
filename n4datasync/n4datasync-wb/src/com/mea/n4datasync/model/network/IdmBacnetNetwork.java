// Package declaration for your network-level IDM components
package com.mea.n4datasync.model.network;

import com.mea.n4datasync.model.IdmComponent;
import com.mea.n4datasync.model.device.IdmBacnetDevice; // Import base device if needed
import com.mea.n4datasync.model.device.IdmBacnetDeviceFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BACnet Network in the Intermediate Data Model.
 * Extends IdmComponent to inherit common properties.
 *
 * In Niagara, a BacnetNetwork can directly contain BacnetDevices or BacnetDeviceFolders.
 * For V1, we'll model it to contain device folders, which then contain devices.
 * This can be extended later if direct device containment is needed.
 */
public class IdmBacnetNetwork extends IdmComponent {

    // --- Specific Fields for BacnetNetwork ---

    /**
     * The BACnet network number. Corresponds to BacnetNetwork.networkNumber in Niagara.
     */
    private String networkNumber;

    /**
     * List to hold child device folders.
     * Niagara's BacnetNetwork can contain both folders and devices directly.
     * For V1, we'll assume a structure of Network -> Folder -> Device.
     * This list will be populated from your external data source.
     */
    private List<IdmBacnetDeviceFolder> deviceFolders;

    // --- Future Scope Fields (e.g., for comms config like MSTP) ---
    // private IdmCommsConfig commsConfig; // Example: for BacnetNetwork.comms.network configuration

    /**
     * Constructor for IdmBacnetNetwork.
     * @param name The simple name of the BACnet Network.
     * @param parentPath The absolute Niagara slot path of its parent (e.g., "station:|slot:/Drivers").
     * @param networkNumber The BACnet network number.
     */
    public IdmBacnetNetwork(String name, String parentPath, String networkNumber) {
        // Call superclass constructor with appropriate Niagara type string
        // "bacnet:BacnetNetwork" is the common Niagara type for BACnet IP Networks.
        super(name, "bacnet:BacnetNetwork", parentPath);
        this.networkNumber = networkNumber;
        this.deviceFolders = new ArrayList<>();
        // If you had a commsConfig, you'd initialize it here as a new IdmCommsConfig()
    }

    // --- Getters and Setters for specific fields ---

    /**
     * Gets the BACnet network number.
     * @return The network number.
     */
    public String getNetworkNumber() {
        return networkNumber;
    }

    /**
     * Sets the BACnet network number.
     * @param networkNumber The new network number.
     */
    public void setNetworkNumber(String networkNumber) {
        this.networkNumber = networkNumber;
    }

    /**
     * Gets the list of child device folders.
     * @return A list of IdmBacnetDeviceFolder objects.
     */
    public List<IdmBacnetDeviceFolder> getDeviceFolders() {
        return deviceFolders;
    }

    /**
     * Adds an IdmBacnetDeviceFolder as a child to this network.
     * Also sets the child's parent path and adds it to the generic children list.
     * @param folder The IdmBacnetDeviceFolder to add.
     */
    public void addDeviceFolder(IdmBacnetDeviceFolder folder) {
        if (folder != null) {
            this.deviceFolders.add(folder);
            // Crucial: Set the child's parent path to this component's absolute path
            folder.setParentPath(this.getAbsolutePath());
            // Add to generic children list for polymorphic traversal if needed
            super.addChild(folder);
        }
    }

    // --- Future Scope: Add methods for comms config if needed ---
    // public IdmCommsConfig getCommsConfig() { return commsConfig; }
    // public void setCommsConfig(IdmCommsConfig config) { this.commsConfig = config; }

    @Override
    public String toString() {
        return "IdmBacnetNetwork{" +
               "name='" + name + '\'' +
               ", absolutePath='" + absolutePath + '\'' +
               ", networkNumber='" + networkNumber + '\'' +
               ", deviceFolders=" + deviceFolders.size() + " children" +
               '}';
    }
}
