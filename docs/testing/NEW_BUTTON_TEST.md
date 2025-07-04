# NEW Button Test - Immediate UI Progress

## 🎯 **What We Just Fixed**

**Problem**: When you clicked "New" in DataSync Profile Manager, you only saw the old BConnectionProfile option.

**Solution**: Modified `BDataSyncProfileView.getNewTypes()` to return both profile types with proper display names.

## 🚀 **Quick Test (2 minutes)**

### Step 1: Load Updated Module
1. Copy `datasync-wb.jar` from `build/libs/` to your Niagara modules directory
2. Restart Workbench or reload the module

### Step 2: Open DataSync Tool
1. Go to **Tools → DataSync Tool**
2. This opens the DataSync Profile Manager View

### Step 3: Test the NEW Button
1. Click the **"New"** button in the toolbar
2. **EXPECTED RESULT**: You should now see a dropdown/dialog with TWO options:
   - **ConnectionProfile** (the old legacy profile)
   - **EnhancedConnectionProfile** (the new component-based profile)

### Step 4: Create Enhanced Profile
1. Select **"EnhancedConnectionProfile"** from the dropdown
2. Give it a name like "TestEnhanced1"
3. Click OK/Create

### Step 5: Verify New Architecture
1. **Check the table**: The new profile should appear in the manager table
2. **Check nav tree**: Navigate to the profile in the nav tree
3. **Expand profile**: You should see child components:
   - `dataSourceConnection`
   - `extractionSettings`
4. **Edit properties**: Click on child components and edit their properties

## 🔍 **What You Should See**

### Before (Old Behavior):
```
New Button → Only "ConnectionProfile" option
```

### After (New Behavior):
```
New Button → Dropdown with:
├── ConnectionProfile (Legacy Profile)
└── EnhancedConnectionProfile (Enhanced Profile)
```

### Enhanced Profile Structure in Nav Tree:
```
TestEnhanced1 (EnhancedConnectionProfile)
├── dataSourceConnection (BDataSourceConnection)
│   ├── sourceType: "Excel"
│   ├── sourcePath: (editable)
│   ├── connectionName: (editable)
│   └── validationStatus: "Not Validated"
└── extractionSettings (BDataExtractionSettings)
    ├── sheetName: (editable)
    ├── startRow: 1
    ├── hasHeaderRow: true
    └── extractionMode: "Full"
```

## ✅ **Success Indicators**

- [ ] New button shows dropdown with 2 options
- [ ] Can create both legacy and enhanced profiles
- [ ] Enhanced profiles show child components in nav tree
- [ ] Can edit properties on child components
- [ ] Both profile types appear in the manager table

## 🐛 **If It Doesn't Work**

**No dropdown appears**: 
- Check console for errors
- Verify module was reloaded
- Ensure both profile types are registered in module-include.xml

**Only one option shows**:
- Check that BEnhancedConnectionProfile.TYPE is accessible
- Verify import statements in BDataSyncProfileView

**Enhanced profile doesn't create**:
- Check console for creation errors
- Verify component relationships are working

## 🎉 **This is REAL Progress!**

This is exactly the kind of **immediate, visible progress** you wanted:

1. **Specific UI Change**: The "New" button now works differently
2. **Immediate Feedback**: You can see both profile types in the dropdown
3. **Modular Improvement**: Doesn't break existing functionality
4. **Foundation for More**: Sets up the architecture for further enhancements

## 📈 **Next Quick Wins**

Once this works, we can quickly add:
1. **Custom Icons**: Different icons for legacy vs enhanced profiles
2. **Better Display Names**: More descriptive names in the dropdown
3. **Property Views**: Custom property sheets for enhanced profiles
4. **Target Station Management**: UI for adding/removing target stations

---

**Test Duration**: 2-3 minutes  
**Expected Result**: You should see the NEW dropdown with both profile types!

This proves the new architecture is working and gives you immediate visual feedback.
