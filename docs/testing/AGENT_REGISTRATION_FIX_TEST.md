# Agent Registration Fix - Manager View Should Appear

## 🎯 **What We Just Fixed**

**Root Cause**: Manager views need BOTH `@AgentOn` annotation AND XML agent registration in `module-include.xml`.

**What Was Missing**: The XML agent registration in module-include.xml was incomplete.

### **Changes Made:**
- ✅ **Added XML agent registration** for BDataSyncProfileView on "datasync:DataSyncTool"
- ✅ **Added XML agent registration** for BDataSyncTool on "workbench:Workbench"  
- ✅ **Added XML agent registration** for BEnhancedConnectionProfileView on "datasync:EnhancedConnectionProfile"
- ✅ **Removed incorrect palette file** (that was wrong approach)

## 🚨 **CRITICAL: Full Niagara Restart Required**

Agent registration changes require a **complete Niagara restart**, not just module reload!

## 🚀 **Test Steps (5 minutes)**

### Step 1: Stop Niagara Completely
1. **Close Workbench** completely
2. **Stop any Niagara services** if running
3. **Wait 10 seconds** for complete shutdown

### Step 2: Build and Copy Module
1. Try building: `./gradlew jar` 
2. If jar is locked, copy from `build/classes/java/main/` manually
3. OR delete the old jar from Niagara modules first, then build

### Step 3: Start Niagara Fresh
1. **Start Niagara Workbench** fresh
2. **Wait for complete startup**
3. Check console for any module loading errors

### Step 4: Test Manager View
1. Go to **Tools** menu
2. **EXPECTED**: "DataSync Tool" should appear in Tools menu
3. Click **"DataSync Tool"**
4. **EXPECTED**: Should see **Manager View** with:
   - Table with columns (Status, Last Sync, Components Created, Last Error)
   - **"New"** button in toolbar
   - Empty table initially

### Step 5: Test Full Workflow
1. **Create Profile**: Click "New" → "EnhancedConnectionProfile" → Name "AgentTest1"
2. **Manager View**: Profile should appear in table
3. **Double-click Profile**: Should show custom Enhanced Profile View
4. **Nav Tree**: Expand profile → double-click dataSourceConnection → should show property sheet

## 🔍 **What You Should See**

### **Tools Menu**:
```
Tools
├── DataSync Tool  ← Should appear here
├── Other tools...
```

### **Manager View (When opening DataSync Tool)**:
```
DataSync Tool - Profile Manager
┌─────────────────────────────────────────────────────────┐
│ [New] [Edit] [Delete] [Refresh]                         │
├─────────────────────────────────────────────────────────┤
│ Name          │ Status      │ Last Sync │ Components    │
├─────────────────────────────────────────────────────────┤
│ (empty table initially)                                 │
└─────────────────────────────────────────────────────────┘
```

### **NOT Property Sheet**: 
If you still see only the AX Property Sheet, the agent registration didn't work.

## 🐛 **If Manager View Still Doesn't Appear**

### Check 1: Module Loading
1. **Console Output**: Look for module loading errors
2. **Module Manager**: Verify datasync-wb module is loaded
3. **Type Registry**: Check if types are registered

### Check 2: Agent Registration
1. **Console**: Look for "agent registration" messages
2. **Tools Menu**: DataSync Tool should appear
3. **View Registration**: Manager view should be default for tool

### Check 3: XML Syntax
Verify module-include.xml has correct syntax:
```xml
<type class="com.mea.datasync.ui.BDataSyncTool" name="DataSyncTool">
  <agent>
    <on type="workbench:Workbench" />
  </agent>
</type>
<type class="com.mea.datasync.ui.BDataSyncProfileView" name="DataSyncProfileView">
  <agent>
    <on type="datasync:DataSyncTool" />
  </agent>
</type>
```

## 📋 **Debug Steps If Still Not Working**

1. **Check Niagara Console**: Look for registration errors
2. **Module Manager**: Verify module loads without errors  
3. **Type Browser**: Check if datasync:DataSyncTool type exists
4. **Agent Browser**: Check if agents are registered correctly
5. **Restart Again**: Sometimes takes 2 restarts for agent changes

## ✅ **Success Indicators**

- [ ] DataSync Tool appears in Tools menu
- [ ] Opening DataSync Tool shows Manager View (not property sheet)
- [ ] Manager View has table with "New" button
- [ ] Can create enhanced profiles through manager
- [ ] Double-clicking profiles shows custom view
- [ ] Nav tree navigation works correctly

## 🎯 **Why This Fix Should Work**

**Agent Registration Pattern**: Niagara requires both annotation and XML registration for views to appear as default views on their target types.

**Examples from Niagara Source**:
- envCtrlDriver uses this exact pattern
- All working manager views have both registrations
- This is the standard Niagara pattern

---

**Expected Result**: Manager View should appear when opening DataSync Tool!

**Key**: Complete Niagara restart is essential for agent registration changes.
