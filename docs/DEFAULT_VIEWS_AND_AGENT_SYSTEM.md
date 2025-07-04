# Default Views and Agent System in Niagara Framework

## Overview

The Niagara framework provides a sophisticated **Agent System** that automatically registers views and functionality on component types. Understanding this system is crucial for creating professional Niagara applications with rich user interfaces.

## What Are Default Views?

Default views are pre-built UI components that the Niagara framework automatically provides for all `BComponent`-based classes. These views appear in the dropdown menu when you select a component in the Workbench.

### Standard Default Views

| View Name | Agent ID | Purpose | When to Use |
|-----------|----------|---------|-------------|
| **Property Sheet** | `workbench:PropertySheet` | Standard property editor with validation | Primary configuration interface |
| **AX Property Sheet** | `webEditors:MultiSheet` | Web-based property editor | Web interface, mobile access |
| **Wire Sheet** | `wiresheet:WireSheet` | Visual component linking interface | Creating data flows, component connections |
| **AX Wire Sheet** | `wiresheet:WebWiresheet` | Web-based wire sheet | Web interface wiring |
| **Category Sheet** | `wbutil:CategorySheet` | Properties grouped by category | Large components with many properties |
| **AX Slot Sheet** | `workbench:SlotSheet` | All slots (properties, actions, topics) | Advanced debugging, introspection |
| **Relation Sheet** | `workbench:RelationSheet` | Component relationships and hierarchy | Understanding component structure |

## How the Agent System Works

### 1. Automatic Registration

The framework automatically registers default views through the `BComponent.getAgents()` method:

```java
// In BComponent.java (framework code)
@Override
public AgentList getAgents(Context cx) {
  AgentList list = super.getAgents(cx);
  
  // Framework automatically includes these agents:
  // - workbench:PropertySheet (highest priority)
  // - wiresheet:WireSheet 
  // - webEditors:MultiSheet (AX Property Sheet)
  // - wiresheet:WebWiresheet (AX Wire Sheet)
  // - wbutil:CategorySheet (lower priority)
  // - workbench:SlotSheet (lower priority)
  // - workbench:RelationSheet (lowest priority)
  
  return list;
}
```

### 2. Agent Priority System

The framework uses a priority system to order views in the dropdown:

```java
// Higher priority views appear first
list.toTop("customView");           // Move to top
list.toBottom("workbench:SlotSheet"); // Move to bottom

// Specific ordering logic in BComponent
list.toBottom("wbutil:CategorySheet");
list.toBottom("workbench:SlotSheet");
list.toBottom("workbench:LinkSheet");
list.toBottom("workbench:RelationSheet");
```

### 3. Custom Agent Registration

You can register custom views as agents in `module-include.xml`:

```xml
<type class="com.mea.datasync.ui.BDataSyncProfileView" name="DataSyncProfileView">
  <agent>
    <on type="datasync:DataSyncTool"/>
  </agent>
</type>
```

## Why BWbNavNodeTool Hides Default Views

**BWbNavNodeTool** (the base class for workbench tools) intentionally hides most default views:

```java
// In BWbNavNodeTool.java (framework code)
public AgentList getAgents(Context cx) {
  AgentList agents = super.getAgents(cx);
  AgentList supers = Sys.getRegistry().getAgents(TYPE.getTypeInfo());
  agents.remove(supers);  // This removes ALL inherited default views!
  
  if (agents.size() == 0)
    agents.add("workbench:PropertySheet");  // Only PropertySheet as fallback
  return agents;
}
```

**Why?** Tools are typically designed for specific workflows and don't need the full complexity of component editing views like Wire Sheets.

## Enabling Default Views for Tools

To restore the full set of default views for a tool (like making `BDataSyncTool` behave like `BEnhancedConnectionProfile`), override `getAgents()`:

### Implementation in BDataSyncTool

```java
@Override
public AgentList getAgents(Context cx) {
  AgentList agents = new AgentList();
  
  // Add all the standard BComponent views
  agents.add("workbench:PropertySheet");      // Standard property editor
  agents.add("webEditors:MultiSheet");        // AX Property Sheet
  agents.add("wiresheet:WireSheet");          // Wire Sheet
  agents.add("wiresheet:WebWiresheet");       // AX Wire Sheet
  agents.add("wbutil:CategorySheet");         // Category Sheet
  agents.add("workbench:SlotSheet");          // AX Slot Sheet
  agents.add("workbench:RelationSheet");      // Relation Sheet
  
  // Add our custom views
  agents.add("datasync:DataSyncProfileView");
  
  // Set view order (custom views first)
  agents.toTop("datasync:DataSyncProfileView");
  
  return agents;
}
```

## Advanced Agent System Features

### 1. Conditional Agent Registration

You can conditionally include agents based on context:

```java
@Override
public AgentList getAgents(Context cx) {
  AgentList agents = super.getAgents(cx);
  
  // Only add advanced views for admin users
  if (isAdminUser(cx)) {
    agents.add("datasync:AdvancedConfigView");
  }
  
  return agents;
}
```

### 2. Permission-Based Agents

Agents can require specific permissions:

```xml
<type class="com.example.AdminView" name="AdminView">
  <agent requiredPermissions="w">
    <on type="baja:Component"/>
  </agent>
</type>
```

### 3. Application-Specific Agents

Agents can be registered for specific applications:

```xml
<type class="com.example.SpecialView" name="SpecialView">
  <agent app="myApplication">
    <on type="baja:Component"/>
  </agent>
</type>
```

## Best Practices

### 1. Tool Design Philosophy

- **Simple Tools**: Use minimal views (PropertySheet only) for focused workflows
- **Complex Tools**: Enable full default views for comprehensive component management
- **Specialized Tools**: Create custom views for domain-specific tasks

### 2. View Ordering Strategy

```java
// Recommended ordering:
// 1. Primary custom view (most important)
// 2. PropertySheet (always useful)
// 3. Other custom views
// 4. Standard framework views
// 5. Advanced/debug views (SlotSheet, RelationSheet)

agents.toTop("datasync:DataSyncProfileView");    // Primary view
// PropertySheet stays in default position
agents.toBottom("workbench:SlotSheet");          // Debug view
agents.toBottom("workbench:RelationSheet");      // Debug view
```

### 3. Performance Considerations

- Don't add unnecessary views (they consume memory)
- Use lazy loading for complex custom views
- Consider caching agent lists for frequently accessed components

## Testing Your Agent Configuration

### 1. Verify Agent Registration

```java
// Debug code to check what agents are registered
AgentList agents = myComponent.getAgents(null);
System.out.println("Available views:");
for (int i = 0; i < agents.size(); i++) {
  System.out.println("  " + agents.get(i).getAgentId());
}
```

### 2. Test View Loading

1. Build and deploy your module
2. **Restart Workbench** (agent registration requires restart)
3. Navigate to your component
4. Check the view dropdown menu
5. Test each view loads correctly

## Common Issues and Solutions

### Issue 1: Views Not Appearing
**Cause**: Agent not properly registered or Workbench not restarted
**Solution**: Check `module-include.xml` and restart Workbench

### Issue 2: Views in Wrong Order
**Cause**: Incorrect priority settings in `getAgents()`
**Solution**: Use `toTop()` and `toBottom()` methods to control order

### Issue 3: Too Many Views
**Cause**: Inheriting all default views when you only need a few
**Solution**: Selectively add only needed agents instead of using `super.getAgents()`

## Summary

The Agent System is a powerful feature that provides:

1. **Automatic UI Generation**: Default views for all components
2. **Extensibility**: Easy addition of custom views
3. **Flexibility**: Conditional and permission-based view registration
4. **Professional UX**: Rich, consistent user interface across all components

Understanding and leveraging this system is key to creating professional Niagara applications that feel integrated with the platform.

---

*This document explains the agent system as implemented in the N4-DataSync project. Update as new patterns are discovered.*
