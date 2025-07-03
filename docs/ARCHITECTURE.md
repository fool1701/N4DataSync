# N4-DataSync Architecture

## Overview

N4-DataSync is a Niagara 4 Workbench module that automates BMS component creation from external data sources. This document describes the system architecture, component relationships, and key design decisions.

## System Architecture

### High-Level Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Excel File    │    │  Niagara        │    │  Target         │
│   (.xlsx)       │───▶│  Workbench      │───▶│  Station        │
│                 │    │  + DataSync     │    │  (Fox Protocol) │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### Component Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Niagara Workbench                        │
├─────────────────────────────────────────────────────────────┤
│  N4-DataSync Module (datasync-wb.jar)                      │
│                                                             │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │     UI      │  │    Model    │  │ Persistence │        │
│  │             │  │             │  │             │        │
│  │ DataSync    │  │ Connection  │  │ Profile     │        │
│  │ Tool        │◄─┤ Profile     │◄─┤ Manager     │        │
│  │             │  │             │  │             │        │
│  │ Profile     │  │ IDM         │  │ JSON        │        │
│  │ View        │  │ Components  │  │ Storage     │        │
│  └─────────────┘  └─────────────┘  └─────────────┘        │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## Core Components

### 1. User Interface Layer

#### BDataSyncTool
- **Purpose**: Main entry point for DataSync functionality
- **Location**: Tools menu in Niagara Workbench
- **Responsibilities**: Launch profile management and sync operations

#### BDataSyncProfileView
- **Purpose**: Profile management interface
- **Responsibilities**: Create, edit, save, and load connection profiles
- **User Experience**: Wizard-style interface for profile configuration

### 2. Model Layer

#### BConnectionProfile
- **Purpose**: Data model for connection configurations
- **Properties**:
  - Source configuration (Excel file path, sheet name)
  - Target configuration (station host, credentials, path)
  - Mapping configuration (column mappings, data types)
- **Serialization**: JSON format for persistence

#### IDM Components
- **Purpose**: Intermediate Data Model representing Niagara components
- **Types**: Networks, Devices, Points
- **Mapping**: Excel data → IDM → Niagara components

### 3. Persistence Layer

#### ProfileManager
- **Purpose**: Manages connection profile storage and retrieval
- **Storage Location**: `{niagara_user_home}/datasync/profiles/`
- **Format**: JSON files with schema validation
- **Features**: CRUD operations, profile validation, error handling

#### JSON Schema
- **Location**: `/docs/schemas/connection-profile-schema.json`
- **Purpose**: Validates profile data structure
- **Benefits**: Data integrity, version compatibility, IDE support
- **Development Schemas**: Additional schemas in `/schemas-dev/` for future features

## Data Flow

### Profile Creation Flow
```
1. User opens DataSync Tool
2. User creates new profile
3. User configures source (Excel file)
4. User configures target (Niagara station)
5. ProfileManager validates configuration
6. Profile saved as JSON file
```

### Component Sync Flow
```
1. User selects saved profile
2. ProfileManager loads profile from JSON
3. Excel data reader parses source file
4. IDM components created from Excel data
5. Fox connection established to target station
6. Niagara components created via Fox protocol
7. Sync report generated and displayed
```

## Key Design Decisions

### 1. Workbench-Only Architecture
**Decision**: Run entirely in Niagara Workbench, no station-side components
**Rationale**: 
- Simpler deployment (single JAR file)
- No station modification required
- Easier maintenance and updates
- Better security isolation

### 2. JSON Profile Persistence
**Decision**: Store profiles as JSON files instead of Niagara components
**Rationale**:
- Human-readable and editable
- Version control friendly
- Easy backup and migration
- Schema validation support
- Cross-platform compatibility

### 3. Fox Protocol Communication
**Decision**: Use Fox protocol for station communication
**Rationale**:
- Standard Niagara communication protocol
- Built-in authentication and security
- Reliable component creation
- Consistent with Niagara patterns

### 4. IDM Intermediate Layer
**Decision**: Create intermediate data model between Excel and Niagara
**Rationale**:
- Separation of concerns
- Easier testing and validation
- Future extensibility for other data sources
- Clear transformation pipeline

## Security Considerations

### Authentication
- **Fox Protocol**: Uses standard Niagara authentication
- **Credentials**: Stored in connection profiles (user responsibility)
- **Certificates**: Standard Niagara module signing

### Data Protection
- **Profile Storage**: User-specific directories
- **File Permissions**: Standard Niagara user permissions
- **Network Security**: Fox protocol encryption

## Performance Characteristics

### Scalability Limits
- **Excel File Size**: Recommended < 10,000 rows
- **Concurrent Operations**: Single-threaded by design
- **Memory Usage**: Proportional to Excel file size
- **Network Latency**: Affects Fox protocol performance

### Optimization Strategies
- **Batch Operations**: Group component creation
- **Connection Pooling**: Reuse Fox connections
- **Progress Feedback**: User experience during long operations
- **Error Recovery**: Graceful handling of partial failures

## Extension Points

### Future Data Sources
- **Google Sheets**: Web API integration
- **Database**: JDBC connectivity
- **CSV Files**: Alternative file format
- **REST APIs**: External system integration

### Enhanced Features
- **Component Comparison**: Before/after analysis
- **Bulk Updates**: Modify existing components
- **Template System**: Reusable component patterns
- **Validation Rules**: Custom data validation

## Dependencies

### Niagara Framework
- **Minimum Version**: Niagara 4.11
- **Required Modules**: baja, workbench, fox, bacnet
- **Optional Modules**: test-wb (for development)

### External Libraries
- **Gson**: JSON serialization/deserialization
- **Apache POI**: Excel file reading (future enhancement)

## References

- [Connection Profile Schema](schemas/connection-profile-schema.json)
- [Niagara Development Patterns](NiagaraPatterns.md)
- [Testing Guide](TESTING.md)
- [Certificate Setup](CERTIFICATE_SETUP.md)
- [Niagara Source Code](../niagara_source_code/) - For implementation reference
- [Official Niagara Examples](https://www.tridium.com) - Additional development patterns
