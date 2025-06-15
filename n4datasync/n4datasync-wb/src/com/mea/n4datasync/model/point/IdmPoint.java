// Package declaration for your point-level IDM components
package com.mea.n4datasync.model.point;

import com.mea.n4datasync.model.IdmComponent;

// No specific imports needed for V1 MVP as we're using basic Java types

/**
 * Represents a Point in the Intermediate Data Model.
 * Extends IdmComponent to inherit common properties.
 *
 * This class holds properties essential for point creation in V1.
 * For V1 MVP, we are explicitly omitting Alarm and History extensions.
 */
public class IdmPoint extends IdmComponent {

    // --- Specific Fields for Point (V1 MVP Scope) ---

    /**
     * The engineering units of the point (e.g., "DegC", "Percent", "psi").
     * Corresponds to the 'units' facet on Niagara points.
     */
    private String units;

    /**
     * The default value for the point.
     * For NumericWritable, this is the 'out' property's default value.
     * For BooleanWritable, typically 0.0 or 1.0.
     */
    private double defaultValue; // Using double for simplicity, can be more specific later

    /**
     * The minimum value for the point (e.g., for NumericWritable).
     * Corresponds to 'min' facet.
     */
    private double min;

    /**
     * The maximum value for the point (e.g., for NumericWritable).
     * Corresponds to 'max' facet.
     */
    private double max;

    /**
     * Indicates if the point is enabled.
     * Corresponds to the 'enabled' property on many Niagara components.
     */
    private boolean enabled;

    // --- Future Scope Fields (for full-feature version) ---

    // private IdmAlarmExtension alarmExtension; // For Alarm Extension properties
    // private IdmHistoryExtension historyExtension; // For History Extension properties
    // private int precision; // For numeric point display precision (from facet)
    // private String range; // For enum points (from enum facet)

    /**
     * Constructor for IdmPoint.
     * @param name The simple name of the point.
     * @param type The Niagara Type specification string for the point (e.g., "control:NumericWritable").
     * @param parentPath The absolute Niagara slot path of its parent (e.g., "station:|slot:/Drivers/BacnetNetwork1/Floor1Devices/AHU-1_Controller").
     */
    public IdmPoint(String name, String type, String parentPath) {
        super(name, type, parentPath);
        // Initialize default values for V1 properties
        this.units = "";
        this.defaultValue = 0.0;
        this.min = Double.MIN_VALUE; // Sensible defaults
        this.max = Double.MAX_VALUE; // Sensible defaults
        this.enabled = true; // Default to enabled
    }

    // --- Getters and Setters for specific fields ---

    /**
     * Gets the engineering units of the point.
     * @return The units string.
     */
    public String getUnits() {
        return units;
    }

    /**
     * Sets the engineering units of the point.
     * @param units The new units string.
     */
    public void setUnits(String units) {
        this.units = units;
    }

    /**
     * Gets the default value for the point.
     * @return The default value.
     */
    public double getDefaultValue() {
        return defaultValue;
    }

    /**
     * Sets the default value for the point.
     * @param defaultValue The new default value.
     */
    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * Gets the minimum value for the point.
     * @return The minimum value.
     */
    public double getMin() {
        return min;
    }

    /**
     * Sets the minimum value for the point.
     * @param min The new minimum value.
     */
    public void setMin(double min) {
        this.min = min;
    }

    /**
     * Gets the maximum value for the point.
     * @return The maximum value.
     */
    public double getMax() {
        return max;
    }

    /**
     * Sets the maximum value for the point.
     * @param max The new maximum value.
     */
    public void setMax(double max) {
        this.max = max;
    }

    /**
     * Checks if the point is enabled.
     * @return True if enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled status of the point.
     * @param enabled The new enabled status.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // --- Future Scope: Getters/Setters for extensions if added ---
    // public IdmAlarmExtension getAlarmExtension() { return alarmExtension; }
    // public void setAlarmExtension(IdmAlarmExtension alarmExtension) { this.alarmExtension = alarmExtension; }
    // public IdmHistoryExtension getHistoryExtension() { return historyExtension; }
    // public void setHistoryExtension(IdmHistoryExtension historyExtension) { this.historyExtension = historyExtension; }

    @Override
    public String toString() {
        return "IdmPoint{" +
                "name='" + name + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                ", type='" + type + '\'' +
                ", units='" + units + '\'' +
                ", defaultValue=" + defaultValue +
                ", min=" + min +
                ", max=" + max +
                ", enabled=" + enabled +
                '}';
    }
}
