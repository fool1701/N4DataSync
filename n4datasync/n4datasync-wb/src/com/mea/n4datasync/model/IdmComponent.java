// Package declaration for your core IDM classes
package com.mea.n4datasync.model;

import java.util.ArrayList;
import java.util.List;

/**
 * IdmComponent is the abstract base class for all Intermediate Data Model (IDM)
 * components in the N4-DataSync tool. It represents a conceptual Niagara component
 * and holds common identification and hierarchical information.
 *
 * This class uses standard Java types to maintain independence from Niagara's
 * Baja API in the core IDM, simplifying development and testing.
 */
public abstract class IdmComponent {

    // --- Core Identification and Hierarchy Fields ---

    /**
     * The simple name of the component (e.g., "SupplyAirTemp", "BacnetNetwork1").
     * This directly corresponds to the Niagara component's 'name' slot.
     */
    protected String name;

    /**
     * The Niagara Type specification string for this component (e.g., "control:NumericWritable",
     * "bacnet:BacnetNetwork"). This helps in creating the correct Niagara component type
     * during synchronization.
     */
    protected String type;

    /**
     * The absolute Niagara slot path of this component's parent.
     * For top-level components (like a Network directly under 'station:|slot:/Drivers'),
     * this would be the parent's path (e.g., "station:|slot:/Drivers").
     */
    protected String parentPath;

    /**
     * The calculated absolute Niagara slot path for this component itself.
     * This is derived from the parentPath and the component's name.
     * Example: "station:|slot:/Drivers/BacnetNetwork1/Device1/SupplyAirTemp"
     */
    protected String absolutePath;

    /**
     * A list to hold child IDM components. This field supports the hierarchical
     * nature of Niagara components. Subclasses will typically use more specific
     * lists (e.g., List<IdmDevice> devices) but this provides a generic way
     * to traverse the IDM tree.
     */
    protected List<IdmComponent> children;

    // --- Future Scope Fields (for full-feature version) ---

    // /**
    //  * Represents the synchronization status of this component after comparison.
    //  * (e.g., NEW, MODIFIED, DELETED, IDENTICAL, SKIPPED).
    //  * Omitted for V1 MVP as it's creation-only.
    //  */
    // protected SyncStatus syncStatus;

    // /**
    //  * A list of specific properties that differ between the source and Niagara
    //  * for 'MODIFIED' components.
    //  * Omitted for V1 MVP.
    //  */
    // protected List<IdmPropertyDiff> diffDetails;


    /**
     * Constructor for IdmComponent.
     *
     * @param name The simple name of the component.
     * @param type The Niagara Type specification string for the component.
     * @param parentPath The absolute Niagara slot path of this component's parent.
     * Use an empty string or specific placeholder for root-level components
     * whose parent is the station itself (e.g., if a Network is directly under 'station:|slot:/Drivers').
     */
    public IdmComponent(String name, String type, String parentPath) {
        this.name = name;
        this.type = type;
        this.parentPath = parentPath;
        this.children = new ArrayList<>(); // Initialize children list
        calculateAbsolutePath(); // Calculate absolute path upon creation
    }

    // --- Getters and Setters ---

    /**
     * Gets the simple name of the component.
     * @return The component's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the simple name of the component.
     * @param name The new name for the component.
     */
    public void setName(String name) {
        this.name = name;
        calculateAbsolutePath(); // Recalculate absolute path if name changes
    }

    /**
     * Gets the Niagara Type specification string for the component.
     * @return The component's type string.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the Niagara Type specification string for the component.
     * @param type The new type string for the component.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the absolute Niagara slot path of this component's parent.
     * @return The parent's absolute path.
     */
    public String getParentPath() {
        return parentPath;
    }

    /**
     * Sets the absolute Niagara slot path of this component's parent.
     * @param parentPath The new parent's absolute path.
     */
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
        calculateAbsolutePath(); // Recalculate absolute path if parent path changes
    }

    /**
     * Gets the calculated absolute Niagara slot path for this component.
     * @return The component's absolute path.
     */
    public String getAbsolutePath() {
        return absolutePath;
    }

    /**
     * Gets the list of child IDM components.
     * @return A list of child IdmComponent objects.
     */
    public List<IdmComponent> getChildren() {
        return children;
    }

    /**
     * Adds a child IdmComponent to this component's list of children.
     * This method is generic for all IdmComponents. Subclasses might
     * provide more specific 'add' methods (e.g., addDevice, addPoint).
     * @param child The IdmComponent to add as a child.
     */
    public void addChild(IdmComponent child) {
        if (child != null) {
            this.children.add(child);
            // Ensure the child's parentPath is correctly set
            // This is crucial for building the hierarchy correctly
            child.setParentPath(this.absolutePath);
        }
    }

    // --- Helper Methods ---

    /**
     * Calculates and sets the absolute path of this component based on its
     * parentPath and name. This method should be called whenever name or
     * parentPath changes.
     *
     * Note: This simplified calculation assumes standard slot path naming.
     * For complex Niagara paths with escaped characters, SlotPath.escape()
     * from Baja API would be needed in the conversion layer.
     */
    protected void calculateAbsolutePath() {
        if (parentPath == null || parentPath.isEmpty()) {
            // This component is a root-level component (e.g., directly under station:|slot:/)
            // Its absolute path is just its name, or a specific root path if defined.
            // For Niagara, top-level components are often under a specific service or folder.
            // We'll assume the parentPath explicitly defines the full path to the parent container.
            this.absolutePath = name; // This needs refinement based on actual root component handling
        } else {
            // Append name to parent path. Handle existing trailing slashes or colons.
            // For Niagara paths like "station:|slot:/Drivers", we need to append "/Name"
            // For paths like "station:|slot:/", we need to append "Name"
            if (parentPath.endsWith("/") || parentPath.endsWith(":")) {
                this.absolutePath = parentPath + name;
            } else {
                this.absolutePath = parentPath + "/" + name;
            }
        }
    }

    // --- Object Overrides (for future comparison and debugging) ---

    @Override
    public String toString() {
        return "IdmComponent{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                '}';
    }

    // For future comparison features (V2), you will need to implement
    // robust equals() and hashCode() methods across your IDM hierarchy.
    // For V1, these are not strictly necessary for creation-only sync.
    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdmComponent that = (IdmComponent) o;
        return Objects.equals(name, that.name) &&
               Objects.equals(type, that.type) &&
               Objects.equals(absolutePath, that.absolutePath); // Compare based on unique identity
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, absolutePath);
    }
    */
}

