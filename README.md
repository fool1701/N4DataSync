# **N4-DataSync**

## **Overview**

N4-DataSync is a comprehensive Niagara 4 Workbench module designed to revolutionize BMS project setup and maintenance. It provides a powerful, user-friendly interface to bridge the gap between external planning data (spreadsheets, databases) and live Niagara stations. The ultimate goal is to eliminate manual data entry, reduce configuration errors, and significantly streamline project workflows for BMS engineers and technicians.

## **Quick Start**

**New to N4-DataSync?** Follow our [5-minute Quick Start Guide](docs/QUICK_START.md) to get up and running immediately.

**Need help?** Check our [Troubleshooting Guide](docs/TROUBLESHOOTING.md) for common issues and solutions.

## **Key Features**

### **External Data Source Connectivity**
* **Microsoft Excel files (.xlsx, .xls)**: Local and Excel Online support with authentication via Microsoft Graph API
* **Google Sheets**: Authenticate via Google API, read specified sheet IDs and ranges
* **Grist**: Integrate with Grist API to read data from Grist documents
* **Relational Databases (RDBMS)**: Connect via JDBC to PostgreSQL, MySQL, SQL Server, etc.
* **Data Validation**: Enhanced validation during ingestion (type checks, range checks, required field presence)

### **Comprehensive Component Management**
* **Networks**: BACnet Networks, Modbus, LonWorks, and other network types
* **Devices**: BACnet devices, Meki22P Controllers, and custom device types
* **Points**: Full support for all point types with comprehensive properties (units, ranges, precision, facets)
* **Extensions**: Complete support for Alarm Extensions and History Extensions with detailed configurations
* **Custom Components**: Ability to define and manage custom BComponent types

### **Advanced Synchronization**
* **Live Data Comparison**: Side-by-side tree view with visual difference highlighting
* **Flexible Operations**: Create, update, and delete components with granular control
* **Two-Way Sync**: Synchronize changes from Niagara back to external sources
* **Transactional Support**: Optional station-side service for ACID transactionality

### **Intelligent Mapping & Configuration**
* **UI-Driven Mapping Interface**: Drag-and-drop mapping of source fields to Niagara components
* **Auto-Mapping**: Automatically suggests mappings based on naming conventions
* **Template Generation**: Generate sample Excel/CSV templates based on selected component types
* **Connection Profiles**: Secure storage of connection details with encryption support

### **Workbench Integration**
* **Dedicated Manager View**: Comprehensive interface for managing connection profiles and synchronization
* **Compare & Sync View**: Sophisticated three-pane layout (Source Tree, Niagara Tree, Details/Log)
* **Contextual Menus**: Right-click actions within Workbench for common tasks
* **Real-time Feedback**: Detailed logging and synchronization reports

## **Prerequisites**

Before installing and using N4-DataSync, please ensure you have the following:

* **Niagara 4:** N4.11 or later (Developed and tested on N4.13.3.48+)
* **Java Development Kit (JDK):** Java 8 JDK (e.g., Zulu JDK 8) for development
* **Niagara Workbench:** Administrative or engineering access to Niagara Workbench
* **Data Sources:** Access to your configuration data (Excel files, Google Sheets, databases, etc.)
* **Network Connectivity:** Network access to target Niagara stations if remote

## **Installation**

To install N4-DataSync, you will need the N4-DataSync module file.

1. **Launch Niagara Workbench:** Start your Niagara 4 Workbench application
2. **Connect to Station (Optional but Recommended):** Connect to the Supervisor or JACE where you intend to manage components
3. **Open Software Manager:** From the Tools menu in Workbench, select Software Manager
4. **Initiate Install:** In the Software Manager window, click the Install button
5. **Select Module:** Browse your computer, locate the N4-DataSync module file, and click Open
6. **Follow Prompts:** Proceed through the installation wizard
7. **Restart Workbench:** After installation completes, close and restart Niagara Workbench to load the new module's user interface components

## **Configuration**

### **Connection Profiles**
1. **Access N4-DataSync Manager:** After restarting Workbench, navigate to Tools > N4-DataSync Manager
2. **Create Connection Profile:** In the manager, create a new connection profile:
   * **Source Configuration**: Select your data source type (Excel, Google Sheets, Grist, Database)
   * **Authentication**: Provide necessary credentials (file paths, API keys, database connections)
   * **Target Station**: Specify Niagara station connection details (IP/hostname, username, password)
   * **Target Path**: Define where components should be created (e.g., station:|slot:/Drivers)

### **Data Mapping**
3. **Configure Mapping**: Use the intuitive mapping interface to:
   * **Auto-detect**: Automatically map columns based on naming conventions
   * **Manual Mapping**: Drag-and-drop source fields to Niagara component properties
   * **Validation**: Real-time validation ensures mapping compatibility
   * **Templates**: Generate sample templates for your preferred data source format

## **Usage**

### **Basic Synchronization**
1. **Select Profile:** In the N4-DataSync Manager, select your configured connection profile
2. **Open Compare & Sync View:** Launch the main synchronization interface
3. **Review Differences:** Examine the side-by-side comparison of source data vs. current Niagara configuration
4. **Select Operations:** Choose which components to create, update, or delete
5. **Execute Sync:** Run the synchronization with real-time progress feedback
6. **Review Results:** Examine detailed reports showing successful operations and any issues

### **Advanced Features**
* **Granular Control:** Select individual components or properties for synchronization
* **Batch Operations:** Process multiple profiles or large datasets efficiently
* **Rollback Support:** Undo changes with transactional rollback capabilities
* **Scheduled Sync:** Automate regular synchronization tasks

## **Architecture**

N4-DataSync is built with a modular, extensible architecture:

### **Core Components**
* **Workbench-Only Operation**: Runs entirely within Niagara Workbench using Fox protocol for station communication
* **Intermediate Data Model (IDM)**: Clean, type-safe representation of Niagara components using Java POJOs
* **Pluggable Connectors**: Extensible architecture supporting multiple data source types
* **Transaction Support**: Optional station-side service for ACID transactionality

### **Data Flow**
1. **Source Reading**: Connectors read data from external sources into IDM format
2. **Comparison Engine**: Analyzes differences between source data and live Niagara configuration
3. **Sync Engine**: Executes create, update, delete operations via Fox protocol
4. **Reporting**: Provides detailed feedback on synchronization results

## **Development & Extensibility**

N4-DataSync is designed for easy extension and customization:

* **Custom Data Sources**: Implement `IExternalSourceReader` interface for new source types
* **Custom Components**: Define new IDM classes for specialized Niagara components
* **Plugin Architecture**: Add new functionality through modular plugin system
* **Comprehensive Testing**: Built-in TestNG framework with extensive test coverage

For detailed technical specifications, see the [Full Feature Specification & Roadmap](N4-DataSync%20Full%20Feature%20Specification%20&%20Roadmap.md).

## **Documentation**

### **User Guides**
* **[Quick Start Guide](docs/QUICK_START.md)** - Get up and running in 5 minutes
* **[Troubleshooting Guide](docs/TROUBLESHOOTING.md)** - Common issues and solutions
* **[Certificate Setup](docs/CERTIFICATE_SETUP.md)** - Module signing certificate configuration

### **Developer Resources**
* **[Developer Documentation](docs/README.md)** - Comprehensive development guide
* **[Testing Guide](docs/TESTING.md)** - Testing framework and best practices
* **[Contributing Guide](docs/CONTRIBUTING.md)** - How to contribute to the project
* **[Architecture Documentation](docs/ARCHITECTURE.md)** - Detailed system architecture

### **Technical Specifications**
* **[Full Feature Specification](N4-DataSync%20Full%20Feature%20Specification%20&%20Roadmap.md)** - Complete technical roadmap
* **[Component Patterns](docs/COMPONENT_RELATIONSHIP_PATTERNS.md)** - Niagara component relationship patterns
* **[Framework Integration](docs/NIAGARA_FRAMEWORK_PATTERNS_IMPLEMENTATION.md)** - Niagara framework integration details

## **Support**

* **Issues**: Report bugs and request features via GitHub Issues
* **Documentation**: Comprehensive guides available in the `/docs` directory
* **Community**: Join discussions and get help from other users
* **Professional Support**: Contact for enterprise support and custom development