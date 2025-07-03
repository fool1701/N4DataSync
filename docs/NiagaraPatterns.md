# Niagara Development Patterns

This document catalogs essential patterns for Niagara module development, specifically focused on the N4-DataSync module. Understanding these patterns will help ensure proper integration with the Niagara framework and efficient development.

## Table of Contents
- [Niagara-Specific Patterns](#niagara-specific-patterns)
- [General Software Design Patterns](#general-software-design-patterns)
- [Data Handling Patterns](#data-handling-patterns)
- [Concurrency Patterns](#concurrency-patterns)
- [Error Handling Patterns](#error-handling-patterns)
- [Web Development Patterns](#web-development-patterns)
- [Testing Patterns](#testing-patterns)
- [Documentation Patterns](#documentation-patterns)

## Niagara-Specific Patterns

### Component Model Pattern
- **Description**: Niagara's core architecture where everything is modeled as a self-describing component
- **Use Case**: Creating the IDM (Intermediate Data Model) hierarchy that mirrors Niagara's component tree structure
- **Relevance**: Critical for `IdmBacnetNetwork`, `IdmBacnetDeviceFolder`, `IdmBacnetDevice`, and `IdmPoint` classes

### Fox Remote Programming Pattern
- **Description**: Mechanism for interacting with remote Niagara stations via Fox protocol
- **Use Case**: Implementing the `NiagaraFoxSyncWriter` to create/update/delete components on target stations
- **Relevance**: Core to the synchronization functionality using `BOrd.make()`, `Sys.getService()`, etc.

### Workbench Tool Pattern
- **Description**: Framework for creating tools that appear in Workbench's Tools menu
- **Use Case**: Implementing the `BDataSyncTool` and its registration as an agent on Workbench
- **Relevance**: Entry point for users to access the N4-DataSync functionality

### Workbench View Pattern
- **Description**: Framework for creating views that display in the Workbench UI
- **Use Case**: Implementing profile management views and sync status displays
- **Relevance**: Critical for the DataSync Manager and profile views (V1 MVP includes basic UI)

### BajaObjectGraph (BOG) Pattern
- **Description**: Niagara's serialization mechanism for component trees
- **Use Case**: Understanding component serialization (V1 uses JSON for profiles)
- **Relevance**: Future enhancement for offline .bog generation capability

### Subscriber Pattern
- **Description**: Niagara's event notification system for component changes
- **Use Case**: Monitoring changes to connection profiles and live station components
- **Relevance**: Required for the live data comparison feature

### Ord Navigation Pattern
- **Description**: Niagara's unified resource locator system for addressing components
- **Use Case**: Specifying target paths in stations and navigating component hierarchies
- **Relevance**: Used in connection profiles to specify where components should be created

### Slot-o-matic Pattern
- **Description**: A source code preprocessor that generates Java code for Baja slots based on comment headers
- **Use Case**: Defining slots for your BComponents (like BConnectionProfile)
- **Relevance**: Essential for creating proper Niagara components with typed properties
- **Reference**: See `/docs/Niagara/Development/Slot-o-matic.md`

### Agent Registration Pattern
- **Description**: Mechanism to register components as agents on other components
- **Use Case**: Registering views on tools, tools on workbench
- **Relevance**: Required for proper tool and view integration

### Niagara Module Structure Pattern
- **Description**: Organization of module code into profile-specific JARs (rt, ux, wb, doc)
- **Use Case**: Structuring your module for different runtime environments
- **Relevance**: Important for proper deployment and runtime behavior
- **Reference**: See `/docs/Niagara/Development/Build.md`

### Theme Module Pattern
- **Description**: Structure for creating custom UI themes in Niagara
- **Use Case**: If you need to create a custom theme for your module
- **Relevance**: Optional, but useful if you want a distinctive look

### Px View Pattern
- **Description**: XML-based definition of UI presentations
- **Use Case**: Creating rich, declarative UI views for your module
- **Relevance**: Useful for complex UI layouts in your tool

### Hx Web View Pattern
- **Description**: HTML-based UI framework for station-hosted web interfaces
- **Use Case**: If you need to create station-side web interfaces
- **Relevance**: Not directly needed for V1 (Workbench-only tool), but useful for future extensions

### BajaScript Pattern
- **Description**: JavaScript library for accessing Niagara data
- **Use Case**: If you implement web-based interfaces that need to access Niagara data
- **Relevance**: Potentially useful for future web-based extensions

### Introspection Pattern
- **Description**: Niagara's mechanism for discovering slots in components
- **Use Case**: Understanding how Niagara discovers and manages component properties
- **Relevance**: Important for proper component design

### Security Pattern
- **Description**: Niagara's security framework for authentication and authorization
- **Use Case**: Implementing secure Fox connections to target stations
- **Relevance**: Critical for production deployments where credentials are required
- **Reference**: See `/docs/Niagara/Security/Security.md`

### Module Signing Pattern
- **Description**: Niagara's code signing mechanism for module validation
- **Use Case**: Ensuring module integrity and trustworthiness
- **Relevance**: Required for production module deployment
- **Reference**: See `/docs/CERTIFICATE_SETUP.md`

## General Software Design Patterns

### Model-View-Controller (MVC) Pattern
- **Description**: Separates application into three interconnected components
- **Use Case**: Structuring the UI components of the tool
- **Relevance**: Implicit in the Workbench UI architecture

### Module-View-Controller (MVC) Variant
- **Description**: Niagara's specific implementation of MVC using Manager classes
- **Use Case**: Structuring UI components with Models, Controllers, and States
- **Relevance**: Core to creating proper Workbench views

### Factory Pattern
- **Description**: Creates objects without specifying the exact class to create
- **Use Case**: Creating appropriate reader implementations based on source type
- **Relevance**: For implementing the `IExternalSourceReader` interface with multiple concrete readers

### Strategy Pattern
- **Description**: Defines a family of algorithms, encapsulates each one, and makes them interchangeable
- **Use Case**: Implementing different synchronization strategies (create-only, update, delete)
- **Relevance**: Mentioned for handling version-specific API differences

### Adapter Pattern
- **Description**: Allows incompatible interfaces to work together
- **Use Case**: Converting external data formats (Excel, Google Sheets, RDBMS) to the IDM
- **Relevance**: Core to the external data source connectivity feature

### Composite Pattern
- **Description**: Composes objects into tree structures to represent part-whole hierarchies
- **Use Case**: Modeling the hierarchical IDM that mirrors Niagara's component tree
- **Relevance**: Fundamental to the `IdmComponent` hierarchy design

### Observer Pattern
- **Description**: Defines a one-to-many dependency between objects
- **Use Case**: Updating the UI when synchronization status changes
- **Relevance**: Implicit in the "Difference Highlighting" feature

### Command Pattern
- **Description**: Encapsulates a request as an object
- **Use Case**: Implementing the synchronization operations (create, update, delete)
- **Relevance**: Useful for the "Granular Control" feature where users select individual changes

### RequireJS Module Pattern
- **Description**: JavaScript module system used in Niagara 4
- **Use Case**: Organizing JavaScript code for web interfaces
- **Relevance**: Important if you extend to web interfaces

## Data Handling Patterns

### Data Transfer Object (DTO) Pattern
- **Description**: Objects that carry data between processes
- **Use Case**: The IDM serves as DTOs between external sources and Niagara
- **Relevance**: Core to the "Intermediate Data Model" architectural decision

### Repository Pattern
- **Description**: Mediates between the domain and data mapping layers
- **Use Case**: Managing connection profiles persistence
- **Relevance**: Implicit in the ProfileManager implementation

### Mapper Pattern
- **Description**: Transforms data between incompatible domain models
- **Use Case**: Mapping between external data sources and the IDM
- **Relevance**: Central to the "Mapping Rules Definition" feature

### Decorator Pattern
- **Description**: Attaches additional responsibilities to objects dynamically
- **Use Case**: Adding validation, logging, or error handling to readers/writers
- **Relevance**: Useful for the "Data Validation" enhancement

### Persistence Strategy Pattern
- **Description**: Approaches for persisting data in Niagara applications
- **Use Case**: Implementing the ProfileManager for connection profiles
- **Relevance**: Critical for the dual persistence approach (component tree + JSON)
- **Reference**: See `/docs/Niagara/Framework/Files.md` for Niagara file handling patterns and [Architecture Guide](ARCHITECTURE.md) for persistence implementation details

## Concurrency Patterns

### Future/Promise Pattern
- **Description**: Represents the result of an asynchronous operation
- **Use Case**: Handling long-running synchronization operations
- **Relevance**: Important for UI responsiveness during sync

### Thread Pool Pattern
- **Description**: Manages a pool of worker threads
- **Use Case**: Executing multiple Fox operations concurrently
- **Relevance**: Mentioned in "Performance Optimization" for large configurations

## Error Handling Patterns

### Circuit Breaker Pattern
- **Description**: Prevents a cascade of failures
- **Use Case**: Handling network interruptions during synchronization
- **Relevance**: Important for the "Robust error handling" requirement

### Retry Pattern
- **Description**: Retries an operation in anticipation of transient failures
- **Use Case**: Handling temporary network issues with Fox connections
- **Relevance**: Important for reliability in distributed environments

## Web Development Patterns

### Moduledev Pattern
- **Description**: Development mode that allows direct file access without rebuilding
- **Use Case**: Rapid development of web interfaces
- **Relevance**: Useful for developing any web components

### Sprite Pattern
- **Description**: Technique for optimizing web UI performance by combining images
- **Use Case**: Optimizing web interface performance
- **Relevance**: Important for web-based extensions

### LESS CSS Pattern
- **Description**: CSS preprocessor used in Niagara themes
- **Use Case**: Creating maintainable CSS for web interfaces
- **Relevance**: Useful for web-based extensions

## Testing Patterns

### Niagara Test Pattern
- **Description**: Framework for automated testing of Niagara modules
- **Use Case**: Creating unit and integration tests for your module
- **Relevance**: Critical for quality assurance and maintenance
- **Reference**: See `/docs/Niagara/Development/AutomatedTestingWithTestNg.md`

### TestNG Pattern
- **Description**: Niagara's preferred testing framework
- **Use Case**: Creating automated tests for your module
- **Relevance**: Critical for quality assurance
- **Reference**: See `/docs/Niagara/Development/AutomatedTestingWithTestNg.md`

### PDF Text Extraction Pattern
- **Description**: Techniques for extracting text from PDF documentation
- **Use Case**: If you need to extract content from Niagara PDFs for documentation
- **Relevance**: Potentially useful for documentation automation

## Documentation Patterns

### Help Documentation Pattern
- **Description**: Structure for creating and deploying help documentation
- **Use Case**: Creating user documentation for your module
- **Relevance**: Important for user adoption and support
- **Reference**: See `/docs/Niagara/Development/Deploying-Help.md`

### Localization Pattern
- **Description**: Framework for internationalizing Niagara applications
- **Use Case**: Making your module support multiple languages
- **Relevance**: Important for international deployments

## References

- [N4-DataSync Full Feature Specification & Roadmap](../N4-DataSync%20Full%20Feature%20Specification%20&%20Roadmap.md)
- [Niagara Developer Documentation](Niagara/)
- [Niagara Framework Documentation](Niagara/Framework/)
- [Certificate Setup Guide](CERTIFICATE_SETUP.md)