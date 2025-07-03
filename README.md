# **N4-DataSync (Version 1 MVP)**

## **Overview**

N4-DataSync is a Niagara 4 Workbench module designed to automate the initial setup of BMS projects. **Version 1 (MVP)** focuses on streamlining the process of **creating new Niagara components** from external data sources.

It addresses the inefficiency of manually configuring a large number of components (Networks, Devices, Points) by allowing bulk creation directly within Niagara Workbench.

## **Quick Start**

**New to N4-DataSync?** Follow our [5-minute Quick Start Guide](docs/QUICK_START.md) to get up and running immediately.

**Need help?** Check our [Troubleshooting Guide](docs/TROUBLESHOOTING.md) for common issues and solutions.

## **Key Features (V1 MVP)**

* **External Data Source Connectivity:**  
  * Reads configuration data from a **local Microsoft Excel file (.xlsx)**.  
  * (Future versions will support Google Sheets and Grist).  
* **Component Creation:**  
  * Automates the creation of new Niagara components: BACnet Networks, BACnet Device Folders, Meki22P Controllers, and their associated Points.  
  * Creates components with their basic properties (e.g., Network Number, Device ID, Point Units, Default Value).  
* **Fixed Column Mapping:** Relies on a **predefined column name template** in the Excel file for data interpretation.  
* **Workbench-Only Operation:** Runs entirely within Niagara Workbench and communicates with a connected Niagara station via Fox protocol. No station-side module installation is required.  
* **Connection Profiles:** Allows users to define and save connection profiles for different Excel files and target Niagara stations.  
* **Basic Synchronization Feedback:** Provides a minimal summary report after the creation process.

## **Prerequisites**

Before installing and using N4-DataSync V1, please ensure you have the following:

* **Niagara 4:** N4.11 or later. (Developed and primarily tested on N4.13.3.48).  
* **Java Development Kit (JDK):** A Java 8 JDK (e.g., Zulu JDK 8\) configured for your IntelliJ IDEA.  
* **Niagara Workbench:** Administrative or engineering access to Niagara Workbench.  
* **Microsoft Excel:** Access to the .xlsx files containing your configuration data.  
* **Network Connectivity:** If the target Niagara station is remote, ensure network access from your Workbench PC.

## **Installation**

To install N4-DataSync V1, you will need the N4-DataSync-1.0.0.module file.

1. **Launch Niagara Workbench:** Start your Niagara 4 Workbench application.  
2. **Connect to Station (Optional but Recommended):** Connect to the Supervisor or JACE where you intend to create components.  
3. **Open Software Manager:** From the Tools menu in Workbench, select Software Manager.  
4. **Initiate Install:** In the Software Manager window, click the Install button.  
5. **Select Module:** Browse your computer, locate the N4-DataSync-1.0.0.module file, and click Open.  
6. **Follow Prompts:** Proceed through the installation wizard.  
7. **Restart Workbench:** **Crucially, after the installation completes, you must close and restart Niagara Workbench.** This allows Workbench to load the new module's user interface components.

## **Configuration (V1)**

1. **Access Connection Manager:** After restarting Workbench, navigate to Tools \> N4-DataSync Manager (or similar menu entry that will be implemented).  
2. **Create a New Profile:** In the manager, create a new connection profile.  
   * Specify Source Type as "Excel".  
   * Provide the full path to your Excel file and the exact sheet name.  
   * Provide the target Niagara station's IP address/hostname, username, and password.  
   * Specify the **target path within the station** where top-level components (e.g., BACnet Networks) should be created (e.g., station:|slot:/Drivers).  
3. **Prepare Excel Data:** Ensure your Excel file uses the **exact predefined column headers** as specified in the ExcelFileReader (e.g., "Network Name", "Device Name", "Point Name", "Point Units", etc.). A sample template will be provided with the module.

## **Usage (V1)**

1. **Select Profile:** In the N4-DataSync Manager, select the connection profile you wish to use.  
2. **Open Sync View:** Click the "Open Sync View" button (or similar) to open the main synchronization interface.  
3. **Initiate Creation:** Click a "Create Components" button. The tool will read your Excel file and attempt to create the components in the specified Niagara station.  
4. **Review Report:** After the process, a basic summary report will be displayed, indicating which components were successfully created.

## **V1 MVP Limitations**

This initial version is intentionally scoped to provide core creation functionality. The following features are **NOT** included in V1:

* **No Comparison/Diffing:** The tool will not compare existing station data with your Excel data. It will only attempt to create new components.  
* **No Update/Delete:** Existing components in Niagara will not be updated or deleted based on your Excel data. Only new component creation is supported.  
* **Fixed Column Mapping:** The Excel file must strictly adhere to a predefined column structure. A UI for flexible mapping is not available.  
* **Limited Source Types:** Only local Excel files are supported.  
* **Minimal Feedback:** Post-sync report is basic; detailed error logging will be enhanced in future versions.

## **Future Enhancements**

This project is designed for extensibility. Future versions will introduce:

* Comprehensive data comparison and diffing.  
* Support for updating and deleting existing components.  
* A user-friendly UI for flexible column mapping.  
* Integration with Google Sheets, Grist, and RDBMS.  
* Enhanced logging and detailed synchronization reports.  
* (Potentially) Transactional synchronization via an optional station service.

For a detailed roadmap of all planned features, refer to the N4-DataSync Full Feature Specification & Roadmap document.