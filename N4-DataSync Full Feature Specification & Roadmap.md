# **N4-DataSync Full Feature Specification & Roadmap**

## **Introduction**

This document outlines the complete vision for the N4-DataSync module, detailing all planned features, architectural decisions, and technical considerations. While Version 1 (V1) focuses on a Minimum Viable Product (MVP) for component creation from Excel, this specification serves as a comprehensive roadmap for future development, ensuring that current design choices support long-term extensibility and functionality.

## **1\. Project Overview**

N4-DataSync is a Niagara 4 Workbench module aimed at revolutionizing BMS project setup and maintenance. It provides a powerful, user-friendly interface to bridge the gap between external planning data (spreadsheets, databases) and live Niagara stations. The ultimate goal is to eliminate manual data entry, reduce configuration errors, and significantly streamline project workflows for BMS engineers and technicians.

## **2\. Architectural Decisions (Recap & Rationale)**

* **Workbench-Only Tool (Primary):**  
  * **Rationale:** Simplifies deployment (no station-side installation required for basic sync), leverages familiar Workbench UI, and allows for offline .bog generation.  
  * **Mechanism:** All logic runs within the Workbench JVM. Communication with live Niagara stations is via the Fox protocol using standard Niagara client APIs for CRUD operations.  
* **"Best-Effort" Synchronization (Default):**  
  * **Rationale:** Simplifies V1 implementation by avoiding complex distributed transaction management.  
  * **Future Consideration:** An *optional* station-side service (rt.jar) may be introduced in future versions to provide true ACID transactionality for critical, large-scale sync operations, but this will be user-selectable.  
* **Intermediate Data Model (IDM) \- Custom Java POJOs:**  
  * **Rationale:** Provides a clean, type-safe, and decoupled representation of Niagara component configuration. It abstracts away the complexity of BComponent internals, simplifies comparison logic, and enhances testability.  
  * **Structure:** Hierarchical, mirroring Niagara's component tree. Uses standard Java types for properties (String, double, boolean) and custom POJOs for complex BStructs (like BFacet) or nested BComponents (like AlarmExtension).  
* **Single Module Project Structure (V1, Extensible):**  
  * **Rationale:** Simplifies initial Gradle setup and development for a single developer.  
  * **Extensibility:** Code will be organized into logical Java packages (.model, .connectors, .core, .ui). If the project grows substantially or requires distinct runtime profiles, it can be refactored into a multi-module Gradle project.  
* **Gradle Build System:** Standardized build process for compilation and packaging.  
* **IDE Integration:** Optimized for development in IntelliJ IDEA with Java 8 JDK.

## **3\. Core Feature Breakdown (V1 MVP & Full Scope)**

### **3.1. External Data Source Connectivity**

* **V1 MVP:**  
  * **Local Microsoft Excel files (.xlsx)**: Reads data based on a fixed, predefined column name template.  
* **Full Scope Enhancements:**  
  * **Google Sheets:** Authenticate via Google API, read specified sheet IDs and ranges.  
  * **Microsoft Excel Online:** Authenticate via Microsoft Graph API, read specified workbooks and sheets.  
  * **Grist:** Integrate with Grist API to read data from Grist documents.  
  * **Relational Databases (RDBMS):** Connect via JDBC to PostgreSQL, MySQL, SQL Server, etc., to read data from specified tables/views.  
  * **Data Validation:** Enhanced validation during ingestion (e.g., type checks, range checks, required field presence).

### **3.2. Niagara Workbench Integration**

* **V1 MVP:**  
  * Accessible via a new menu item under Tools (e.g., N4-DataSync Manager).  
  * Basic UI for Connection Profiles and initiating creation.  
* **Full Scope Enhancements:**  
  * Dedicated "N4-DataSync Manager" view with a table to list, add, edit, delete, and duplicate connection profiles.  
  * Integrated "Compare & Sync" view with a sophisticated three-pane layout (Source Tree, Niagara Tree, Details/Log).  
  * Contextual menus and actions within Workbench for common tasks.

### **3.3. Live Data Comparison (Full Scope Feature)**

* **V1 MVP:** **Not supported.**  
* **Full Scope Enhancements:**  
  * **Side-by-Side Tree View:** Visual comparison of the hierarchical IDM from the external source against the live Niagara station's component tree.  
  * **Difference Highlighting:** Clear visual cues (icons, colors) to indicate:  
    * **New:** Component exists in source but not Niagara.  
    * **Deleted:** Component exists in Niagara but not source.  
    * **Modified:** Component exists in both, but properties or children differ.  
    * **Identical:** Components match perfectly.  
    * **Type Mismatch:** Components at the same path have different Niagara types (critical error).  
    * **Parent Mismatch:** Component has different parent in source vs. Niagara.  
  * **Detailed Difference Breakdown:** When a "Modified" item is selected, a lower pane will show a tabular list of specific properties that differ (Property Name, Source Value, Niagara Value).

### **3.4. Flexible Synchronization**

* **V1 MVP:**  
  * **Creation (One-Way):** Only supports creating new components in Niagara based on the external source.  
* **Full Scope Enhancements:**  
  * **Update:** Modify properties and settings of existing Niagara components to match the external source.  
  * **Delete:** Remove components from Niagara that are no longer present in the external source (with explicit user confirmation and safeguards).  
  * **Granular Control:** Allow users to select/deselect individual components or even individual property changes for synchronization.  
  * **Two-Way Sync (Future Consideration):** Potentially synchronize changes from Niagara back to the external source (more complex, likely a later phase).

### **3.5. Component Support**
a
* **V1 MVP:**  
  * **Networks:** BACnet Networks (bacnet:BacnetNetwork).  
  * **Folders:** BACnet Device Folders (bacnet:BacnetDevicFolder).  
  * **Devices:** Meki22P Controllers (as bacnet:BacnetDevice).  
  * **Points:** Generic Points (control:NumericWritable, control:BooleanWritable, control:EnumWritable) with basic properties (name, type, units, defaultValue, min, max, enabled).  
* **Full Scope Enhancements:**  
  * **Expanded Point Properties:** Support more point facets (e.g., precision, range).  
  * **Extensions:** Full support for creating, updating, and deleting Alarm Extensions (alarm:AlarmSourceExt) and History Extensions (history:IntervalHistoryExt, history:CovHistoryExt) with their detailed configurations.  
  * **Other Network/Device Types:** Modbus, LonWorks, etc.  
  * **Custom Component Types:** Ability to define and manage custom BComponent types.  
  * **Links & Relations:** Potentially manage links and relations between components.

### **3.6. Runtime Operations**

* **V1 MVP:** Performs creation directly on a *running* Niagara station.  
* **Full Scope Enhancements:**  
  * All sync operations (create, update, delete) will be performed against a live, running station.  
  * Robust error handling for network interruptions or station-side issues during sync.

## **4\. Configuration & Management Details**

### **4.1. Connection Profiles**

* **V1 MVP:** Basic ConnectionProfile POJO with fields for name, source type, source path/sheet, target station path, username, password.  
* **Full Scope Enhancements:**  
  * **Persistence:** Profiles will be saved to a user-specific location (e.g., niagara\_user\_home/shared/N4DataSync/profiles/) using Niagara's BajaObjectGraph (BOG) serialization or simple JSON.  
  * **Security:** Passwords will be encrypted or handled securely (e.g., via Niagara's credential management if exposed).  
  * **Dynamic Source Parameters:** Profiles will store all necessary authentication and connection details for each source type (API keys, JDBC URLs, etc.).

### **4.2. Mapping Rules Definition**

* **V1 MVP:** **Fixed column name template** in Excel files. No UI for mapping.  
* **Full Scope Enhancements:**  
  * **UI-Driven Mapping Interface:** A dedicated Workbench UI for defining mappings.  
    * Displays detected columns/fields from the external source.  
    * Displays a navigable tree/list of supported Niagara IDM slots.  
    * Allows drag-and-drop or selection-based mapping of source fields to IDM slots.  
    * Supports mapping to nested properties (e.g., Point.units, AlarmExtension.enabled).  
  * **Auto-Mapping:** Automatically suggests mappings based on naming conventions.  
  * **Mapping Persistence:** Mappings will be saved as part of the ConnectionProfile.  
  * **Validation:** Real-time validation of mapping (e.g., type compatibility).  
  * **Template Generation:** Ability to generate sample Excel/CSV templates based on selected Niagara component types and desired properties.

## **5\. Data Model (IDM) Specification**

* **Base Class:** IdmComponent (abstract)  
  * **Fields:** name (String), type (String, Niagara type spec), parentPath (String, absolute path of parent), absolutePath (String, calculated full path), children (List\<IdmComponent\>, for generic traversal).  
  * **Full Scope Additions:** handle (String, Niagara's unique ID for updates/deletes), syncStatus (Enum: NEW, MODIFIED, DELETED, IDENTICAL, SKIPPED), diffDetails (List\<IdmPropertyDiff\>).  
  * **Methods:** Constructor, getters/setters.  
  * **Full Scope Additions:** Overridden equals(), hashCode() for robust comparison.  
* **Specific IDM Subclasses:**  
  * **IdmBacnetNetwork**: Extends IdmComponent. Fields: networkNumber (String). Children: List\<IdmBacnetDeviceFolder\>.  
  * **IdmBacnetDeviceFolder**: Extends IdmComponent. Children: List\<IdmBacnetDevice\>.  
  * **IdmBacnetDevice (Abstract Base Class)**: Extends IdmComponent. Fields: deviceId (String), macAddress (String). Children: List\<IdmPoint\>.  
  * **IdmMeki22PController**: Extends IdmBacnetDevice. (Initially no unique fields beyond base device, but can add Meki-specific properties later).  
  * **IdmPoint**: Extends IdmComponent. Fields (V1): units (String), defaultValue (double), min (double), max (double), enabled (boolean).  
  * **Full Scope IdmPoint Additions:**  
    * IdmFacet facets; (for BFacet properties like precision).  
    * IdmAlarmExtension alarmExtension; (new POJO for alarm config).  
    * IdmHistoryExtension historyExtension; (new POJO for history config).  
  * **New POJOs for Complex Types/Extensions (Full Scope):** IdmStatusNumeric, IdmFacet, IdmAlarmExtension, IdmHistoryExtension, etc., each mirroring their respective Niagara BStruct/BComponent properties with standard Java types.

## **6\. Connector & Sync Writer Details**

* **IExternalSourceReader Interface:** Defines read(ConnectionProfile profile) method returning List\<IdmBacnetNetwork\>.  
  * **V1 MVP Implementation:** ExcelFileReader (uses Apache POI, fixed column template).  
  * **Full Scope Implementations:** GoogleSheetsReader, GristReader, RdbmsReader, etc., each implementing IExternalSourceReader.  
* **INiagaraSyncWriter Interface:** Defines methods for writing IDM to Niagara.  
  * **V1 MVP Method:** createComponents(List\<IdmBacnetNetwork\> networks, ConnectionProfile profile).  
  * **Full Scope Methods:** updateComponents(...), deleteComponents(...).  
  * **Implementation:** NiagaraFoxSyncWriter will use BOrd.make(), Sys.getService(), BComponent.add(), BComponent.set(), BComponent.remove() methods via Fox protocol.  
* **Error Handling & Logging:**  
  * **V1 MVP:** Basic System.err.println for errors in Excel reader, minimal post-sync summary.  
  * **Full Scope:** Robust try-catch blocks for all Niagara API calls, catching specific exceptions (IllegalChildException, PermissionException, etc.). Detailed, timestamped log messages (Success, Warning, Error) in a dedicated Workbench log pane.

## **7\. Future Considerations & Technical Challenges**

* **Transactionality:** Explore implementing an *optional* station-side service (rt.jar) to provide atomic commit/rollback for large sync operations, if the "best-effort" Workbench-only approach proves insufficient in practice.  
* **UI Complexity:** Developing the sophisticated Tree Table views for comparison and dynamic mapping UI will be a significant effort, requiring careful design of Java Swing/JavaFX components within Workbench.  
* **Performance Optimization:** For very large station configurations, optimizing Fox calls (e.g., using Transaction for batching changes, BatchResolve for reading) will be critical.  
* **Version Compatibility:** Maintaining a single codebase that builds for N4.11 and N4.13+ will require careful use of conditional logic (Sys.getNiagaraVersion()) or strategy patterns for version-specific API differences.  
* **Unit & Integration Testing:** Establishing a robust testing framework (TestNG for unit tests, potentially simulated Niagara environments or dedicated test stations for integration tests) will be crucial for quality assurance.  
* **Module Signing:** Ensuring the module is correctly signed for deployment.

## **8\. V1 MVP Scope (Reference)**

For what is being built in the immediate first version, please refer to the N4-DataSync (Version 1 MVP) README.md document. This current specification outlines the full vision for the product's evolution.