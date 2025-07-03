# N4-DataSync Schema Repository

This directory contains JSON schemas for N4-DataSync data structures and Niagara component definitions.

## Schema Versions

### V1 Schemas (Detailed Component Schemas)
Comprehensive JSON schemas for individual Niagara component types:
- **Networks**: BACnet, Modbus TCP, Modbus Async, Niagara networks
- **Devices**: BACnet devices with full configuration
- **Points**: Analog/Binary inputs/outputs with properties
- **Extensions**: Component extensions and calibrations
- **Common**: Shared schema definitions

### V2 Schemas (Simplified Component Schema)
Unified schema for hierarchical Niagara component structures:
- **BComponent**: Generic component schema with recursive nesting
- **Examples**: Sample JSON files showing usage patterns

## Current Usage

### Active Schemas
The **connection profile schema** in `/docs/schemas/connection-profile-schema.json` is actively used by:
- `ProfileManager.java` for JSON serialization/deserialization
- Profile validation in the DataSync UI
- Documentation and development reference

### Development Schemas
The schemas in this directory are for **future development** and **data modeling reference**:
- **V1**: Detailed schemas for specific component types
- **V2**: Simplified hierarchical component modeling

## Schema Relationships

### Connection Profile Schema (Active)
Located in `/docs/schemas/connection-profile-schema.json`:
- Used by N4-DataSync V1 MVP
- Defines profile structure for Excel → Niagara sync
- Validated by ProfileManager during save/load operations

### Component Schemas (Development)
Located in this directory:
- **V1**: Detailed schemas for future data validation
- **V2**: Simplified schemas for hierarchical component modeling
- **Examples**: Sample data showing schema usage

## Future Integration

These schemas may be integrated into future N4-DataSync versions for:
- **Data validation** before component creation
- **Schema-driven UI** generation
- **Multi-format support** (JSON, XML, etc.)
- **Component templates** and reusable patterns

## Schema Structure

### V1 Directory Structure
```
V1/
├── common/
│   ├── componentName.schema.json
│   ├── deviceAddress.schema.json
│   ├── networkAddress.schema.json
│   └── pointProperties.schema.json
├── networks/
│   ├── bacnetNetwork.schema.json
│   ├── modbusAsyncNetwork.schema.json
│   └── modbusTcpNetwork.schema.json
├── devices/
│   └── bacnetDevice.schema.json
├── points/
│   ├── analogInput.schema.json
│   ├── analogOutput.schema.json
│   ├── binaryInput.schema.json
│   └── binaryOutput.schema.json
└── extensions/
    ├── componentExtension.schema.json
    └── calibration.schema.json
```

### V2 Directory Structure
```
V2/
├── BComponent.schema.json
└── examples/
    ├── network-example.json
    ├── device-example.json
    └── point-example.json
```

## Usage Examples

### V1 Schema Usage
```json
{
  "$schema": "./V1/networks/bacnetNetwork.schema.json",
  "name": "BACnet-1",
  "networkNumber": 1,
  "deviceAddress": "192.168.1.100:47808"
}
```

### V2 Schema Usage
```json
{
  "$schema": "./V2/BComponent.schema.json",
  "type": "BacnetNetwork",
  "name": "BACnet-1",
  "children": [
    {
      "type": "BacnetDevice",
      "name": "AHU-01",
      "children": [...]
    }
  ]
}
```

## References

- [Active Connection Profile Schema](../docs/schemas/connection-profile-schema.json)
- [N4-DataSync Architecture](../docs/ARCHITECTURE.md)
- [Development Patterns](../docs/NiagaraPatterns.md)
- [JSON Schema Documentation](https://json-schema.org/)
