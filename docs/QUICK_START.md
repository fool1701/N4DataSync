# N4-DataSync Quick Start Guide

## 5-Minute Setup

This guide gets you up and running with N4-DataSync in 5 minutes.

### Prerequisites Check

Before starting, verify you have:
- ✅ Niagara 4.11+ installed
- ✅ Niagara Workbench access
- ✅ Excel file with BMS data
- ✅ Target Niagara station (local or remote)

### Step 1: Install the Module (2 minutes)

1. **Download** the N4-DataSync module file (`datasync-wb.jar`)
2. **Open Niagara Workbench**
3. **Go to Tools → Software Manager**
4. **Click Install** and select the module file
5. **Restart Workbench** (important!)

### Step 2: Set Up Certificates (1 minute)

```bash
# Export Niagara certificate
gradlew exportCertificate --alias "Niagara4Modules" --pem-file Niagara4Modules.pem

# Import in Workbench: Tools → Certificate Management → User Trust Store
```

### Step 3: Create Your First Profile (2 minutes)

1. **Open Tools → DataSync Manager**
2. **Click "New Profile"**
3. **Fill in the details:**
   - **Profile Name**: "My First Sync"
   - **Source Type**: Excel
   - **Excel File**: Browse to your .xlsx file
   - **Sheet Name**: "BACnet_Points" (or your sheet name)
   - **Target Station**: IP address or "localhost"
   - **Username/Password**: Your station credentials
   - **Target Path**: "station:|slot:/Drivers"

4. **Click "Save Profile"**

### Step 4: Run Your First Sync (30 seconds)

1. **Select your profile**
2. **Click "Create Components"**
3. **Watch the progress**
4. **Review the summary report**

## What Happens Next?

- **Components created** in your target station
- **Profile saved** for future use
- **Ready for more** complex configurations

## Sample Excel Format

Your Excel file should have these columns:
```
Network Name | Device Name | Point Name | Point Type | Units | Default Value
BACnet-1     | AHU-01      | Temp-01    | AI         | °C    | 20.0
BACnet-1     | AHU-01      | Fan-01     | BO         | -     | false
```

## Next Steps

- **[Read the full documentation](../README.md)** for advanced features
- **[Learn about patterns](NiagaraPatterns.md)** for development
- **[Set up testing](TESTING.md)** if you're a developer
- **[Check troubleshooting](TROUBLESHOOTING.md)** if you have issues

## Need Help?

- **Common issues**: See [Troubleshooting Guide](TROUBLESHOOTING.md)
- **Development**: See [Developer Documentation](README.md)
- **Technical details**: See [Technical Specification](../N4-DataSync%20Full%20Feature%20Specification%20&%20Roadmap.md)
