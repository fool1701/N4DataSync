// In: com.mea.datasync.model
package com.mea.datasync.model;

import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BConnectionProfile represents a saved configuration for connecting to
 * an external data source (Excel file) and target Niagara station.
 */
@NiagaraType
@NiagaraProperty(
  name = "sourceType",
  type = "baja:String",
  defaultValue = "BString.make(\"Excel\")"
)
@NiagaraProperty(
  name = "sourcePath",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "sheetName",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "targetHost",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "targetUsername",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "targetPath",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
@NiagaraProperty(
  name = "status",
  type = "baja:String",
  defaultValue = "BString.make(\"Never Synced\")"
)
@NiagaraProperty(
  name = "lastSync",
  type = "baja:AbsTime",
  defaultValue = "BAbsTime.NULL"
)
@NiagaraProperty(
  name = "componentsCreated",
  type = "baja:Integer",
  defaultValue = "BInteger.make(0)"
)
@NiagaraProperty(
  name = "lastError",
  type = "baja:String",
  defaultValue = "BString.DEFAULT"
)
public class BConnectionProfile extends BComponent {

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.mea.datasync.model.BConnectionProfile(2047217648)1.0$ @*/
/* Generated Mon Jun 23 01:47:35 AEST 2025 by Slot-o-Matic (c) Tridium, Inc. 2012-2025 */

  //region Property "sourceType"

  /**
   * Slot for the {@code sourceType} property.
   * @see #getSourceType
   * @see #setSourceType
   */
  public static final Property sourceType = newProperty(0, BString.make("Excel"), null);

  /**
   * Get the {@code sourceType} property.
   * @see #sourceType
   */
  public String getSourceType() { return getString(sourceType); }

  /**
   * Set the {@code sourceType} property.
   * @see #sourceType
   */
  public void setSourceType(String v) { setString(sourceType, v, null); }

  //endregion Property "sourceType"

  //region Property "sourcePath"

  /**
   * Slot for the {@code sourcePath} property.
   * @see #getSourcePath
   * @see #setSourcePath
   */
  public static final Property sourcePath = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code sourcePath} property.
   * @see #sourcePath
   */
  public String getSourcePath() { return getString(sourcePath); }

  /**
   * Set the {@code sourcePath} property.
   * @see #sourcePath
   */
  public void setSourcePath(String v) { setString(sourcePath, v, null); }

  //endregion Property "sourcePath"

  //region Property "sheetName"

  /**
   * Slot for the {@code sheetName} property.
   * @see #getSheetName
   * @see #setSheetName
   */
  public static final Property sheetName = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code sheetName} property.
   * @see #sheetName
   */
  public String getSheetName() { return getString(sheetName); }

  /**
   * Set the {@code sheetName} property.
   * @see #sheetName
   */
  public void setSheetName(String v) { setString(sheetName, v, null); }

  //endregion Property "sheetName"

  //region Property "targetHost"

  /**
   * Slot for the {@code targetHost} property.
   * @see #getTargetHost
   * @see #setTargetHost
   */
  public static final Property targetHost = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code targetHost} property.
   * @see #targetHost
   */
  public String getTargetHost() { return getString(targetHost); }

  /**
   * Set the {@code targetHost} property.
   * @see #targetHost
   */
  public void setTargetHost(String v) { setString(targetHost, v, null); }

  //endregion Property "targetHost"

  //region Property "targetUsername"

  /**
   * Slot for the {@code targetUsername} property.
   * @see #getTargetUsername
   * @see #setTargetUsername
   */
  public static final Property targetUsername = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code targetUsername} property.
   * @see #targetUsername
   */
  public String getTargetUsername() { return getString(targetUsername); }

  /**
   * Set the {@code targetUsername} property.
   * @see #targetUsername
   */
  public void setTargetUsername(String v) { setString(targetUsername, v, null); }

  //endregion Property "targetUsername"

  //region Property "targetPath"

  /**
   * Slot for the {@code targetPath} property.
   * @see #getTargetPath
   * @see #setTargetPath
   */
  public static final Property targetPath = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code targetPath} property.
   * @see #targetPath
   */
  public String getTargetPath() { return getString(targetPath); }

  /**
   * Set the {@code targetPath} property.
   * @see #targetPath
   */
  public void setTargetPath(String v) { setString(targetPath, v, null); }

  //endregion Property "targetPath"

  //region Property "status"

  /**
   * Slot for the {@code status} property.
   * @see #getStatus
   * @see #setStatus
   */
  public static final Property status = newProperty(0, BString.make("Never Synced"), null);

  /**
   * Get the {@code status} property.
   * @see #status
   */
  public String getStatus() { return getString(status); }

  /**
   * Set the {@code status} property.
   * @see #status
   */
  public void setStatus(String v) { setString(status, v, null); }

  //endregion Property "status"

  //region Property "lastSync"

  /**
   * Slot for the {@code lastSync} property.
   * @see #getLastSync
   * @see #setLastSync
   */
  public static final Property lastSync = newProperty(0, BAbsTime.NULL, null);

  /**
   * Get the {@code lastSync} property.
   * @see #lastSync
   */
  public BAbsTime getLastSync() { return (BAbsTime)get(lastSync); }

  /**
   * Set the {@code lastSync} property.
   * @see #lastSync
   */
  public void setLastSync(BAbsTime v) { set(lastSync, v, null); }

  //endregion Property "lastSync"

  //region Property "componentsCreated"

  /**
   * Slot for the {@code componentsCreated} property.
   * @see #getComponentsCreated
   * @see #setComponentsCreated
   */
  public static final Property componentsCreated = newProperty(0, BInteger.make(0).as(BInteger.class).getInt(), null);

  /**
   * Get the {@code componentsCreated} property.
   * @see #componentsCreated
   */
  public int getComponentsCreated() { return getInt(componentsCreated); }

  /**
   * Set the {@code componentsCreated} property.
   * @see #componentsCreated
   */
  public void setComponentsCreated(int v) { setInt(componentsCreated, v, null); }

  //endregion Property "componentsCreated"

  //region Property "lastError"

  /**
   * Slot for the {@code lastError} property.
   * @see #getLastError
   * @see #setLastError
   */
  public static final Property lastError = newProperty(0, BString.DEFAULT, null);

  /**
   * Get the {@code lastError} property.
   * @see #lastError
   */
  public String getLastError() { return getString(lastError); }

  /**
   * Set the {@code lastError} property.
   * @see #lastError
   */
  public void setLastError(String v) { setString(lastError, v, null); }

  //endregion Property "lastError"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BConnectionProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/



////////////////////////////////////////////////////////////////
// Convenience Methods
////////////////////////////////////////////////////////////////

  public void setStatusString(String statusStr) {
    setStatus(statusStr);
  }

  public String getStatusString() {
    return getStatus();
  }

  public void setComponentsCreatedInt(int count) {
    setComponentsCreated(count);
  }

  public int getComponentsCreatedInt() {
    return getComponentsCreated();
  }

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final String STATUS_NEVER_SYNCED = "Never Synced";
  public static final String STATUS_SUCCESS = "Success";
  public static final String STATUS_ERROR = "Error";
  public static final String STATUS_IN_PROGRESS = "In Progress";
}
