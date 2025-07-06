// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BConnectionDetails serves as the base class for storing connection-specific
 * configuration details for different data source types. This abstract class
 * provides common properties while allowing subclasses to define their own
 * specific connection parameters.
 * 
 * This follows the composition pattern where each data source connection type
 * has its own specific connection details component.
 * 
 * Subclasses should extend this for specific data source types:
 * - BExcelConnectionDetails: File path, sheet selection
 * - BDatabaseConnectionDetails: JDBC URL, credentials, schema
 * - BGoogleSheetsConnectionDetails: API credentials, sheet ID
 * - BCSVConnectionDetails: File path, delimiter, encoding
 */
@NiagaraType
@NiagaraProperty(
  name = "connectionName",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "description",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "connectionTimeout",
  type = "baja:Integer",
  defaultValue = "BInteger.make(30000)"
)
public abstract class BConnectionDetails extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BConnectionDetails(786065583)1.0$ @*/
/* Generated Mon Jul 07 05:25:51 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "connectionName"

  /**
   * Slot for the {@code connectionName} property.
   * @see #getConnectionName
   * @see #setConnectionName
   */
  public static final Property connectionName = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code connectionName} property.
   * @see #connectionName
   */
  public String getConnectionName() { return getString(connectionName); }

  /**
   * Set the {@code connectionName} property.
   * @see #connectionName
   */
  public void setConnectionName(String v) { setString(connectionName, v, null); }

  //endregion Property "connectionName"

  //region Property "description"

  /**
   * Slot for the {@code description} property.
   * @see #getDescription
   * @see #setDescription
   */
  public static final Property description = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code description} property.
   * @see #description
   */
  public String getDescription() { return getString(description); }

  /**
   * Set the {@code description} property.
   * @see #description
   */
  public void setDescription(String v) { setString(description, v, null); }

  //endregion Property "description"

  //region Property "connectionTimeout"

  /**
   * Slot for the {@code connectionTimeout} property.
   * @see #getConnectionTimeout
   * @see #setConnectionTimeout
   */
  public static final Property connectionTimeout = newProperty(0, BInteger.make(30000).as(BInteger.class).getInt(), null);

  /**
   * Get the {@code connectionTimeout} property.
   * @see #connectionTimeout
   */
  public int getConnectionTimeout() { return getInt(connectionTimeout); }

  /**
   * Set the {@code connectionTimeout} property.
   * @see #connectionTimeout
   */
  public void setConnectionTimeout(int v) { setInt(connectionTimeout, v, null); }

  //endregion Property "connectionTimeout"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BConnectionDetails.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BConnectionDetails() {
    // Base constructor
  }

////////////////////////////////////////////////////////////////
// Abstract Methods
////////////////////////////////////////////////////////////////

  /**
   * Get a human-readable summary of the connection details.
   * This should be implemented by subclasses to provide specific
   * connection information relevant to their data source type.
   * 
   * @return connection summary string
   */
  public abstract String getConnectionSummary();

  /**
   * Validate the connection details configuration.
   * Subclasses should implement this to perform type-specific validation.
   * 
   * @return validation result with success status and error message
   */
  public abstract ValidationResult validateConfiguration();

////////////////////////////////////////////////////////////////
// Common Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the display name for this connection.
   * Uses connectionName if set, otherwise falls back to a default.
   */
  public String getDisplayName() {
    String name = getConnectionName();
    if (name != null && !name.trim().isEmpty()) {
      return name;
    }
    return "Unnamed Connection";
  }

////////////////////////////////////////////////////////////////
// Inner Classes
////////////////////////////////////////////////////////////////

  /**
   * Result of a configuration validation operation.
   */
  public static class ValidationResult {
    private final boolean valid;
    private final String errorMessage;
    
    public ValidationResult(boolean valid, String errorMessage) {
      this.valid = valid;
      this.errorMessage = errorMessage != null ? errorMessage : "";
    }
    
    public boolean isValid() { return valid; }
    public String getErrorMessage() { return errorMessage; }
    
    public static ValidationResult success() {
      return new ValidationResult(true, "");
    }
    
    public static ValidationResult error(String message) {
      return new ValidationResult(false, message);
    }
  }
}
