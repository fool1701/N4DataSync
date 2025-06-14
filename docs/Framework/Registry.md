## Niagara Registry

```markdown
# Registry

## Overview

The registry is a term for a small database built by the Niagara runtime whenever it detects that a module has been added, changed, or removed[cite: 497]. During the registry build process all the types in all the modules are scanned[cite: 498]. Their classfiles are parsed to build an index for the class hierarchy of all the Niagara types available in the installation[cite: 499]. Some of the functions the registry provides:

* query modules installed without opening each jar file [cite: 500]
* query class hiearhcy without loading actual Java classes [cite: 500]
* query agents registered on a given Type [cite: 500]
* map file extensions to their BIFile Types [cite: 500]
* map ord schemes to their BordScheme Types [cite: 500]
* defs provide a global map of name/value pairs [cite: 500]
* query lexicons registered on a given module [cite: 500]

## API

The Registry database may be accessed via `Sys.getRegistry()`[cite: 501]. Since the primary use of the registry is to interrogate the system about modules and types without loading them into memory, the registry API uses light weight wrappers:

| Registry Wrapper | Real McCoy |
| :--------------- | :--------- |
| ModuleInfo       | BModule    |
| TypeInfo         | Type       |
[cite: 502]

## Agents

An agent is a special BObject type that provides services for other BObject types[cite: 503, 504]. Agents are registered on their target types via the module manifest and queried via the Registry interface[cite: 505]. Agents are used extensively in the framework for late binding - such as defining views, popup menus, or exporters for specified target types[cite: 506]. Typically agent queries are combined with a type filter. For example, to find all the BExporters registered on a given file:

```java
AgentFilter filter = AgentFilter.is(BExporter.TYPE); [cite: 507]
AgentList exporters = file.getAgents(null).filter(filter); [cite: 507]
A couple of examples of how an agent type is registered on a target type in the module manifest (module-include.xml):

XML

<type name="ValueBinding" class="javax.baja.ui.BValueBinding">
  <agent><on type="bajaui:Widget"/></agent>
  <agent><on type="baja:Value"/></agent>
</type>

<type name="PropertySheet"
      class="com.tridium.workbench.propsheet.BPropertySheet">
  <agent requiredPermissions="r"><on type="baja:Component"/></agent>
</type>


Agents can be registered on a target only for a specific application using the app attribute within the agent tag. The application name can be queried at runtime via the AgentInfo.getAppName() method. Agent application names are used in conjunction with the getAppName() method of BWbProfile and BHxProfile. An example application specific agent:


XML

<type name="ApplianceUserManager"
      class="appliance.ui.BApplianceUserManager">
  <agent app="demoAppliance">
    <on type="baja:UserService"/>
  </agent>
</type>


Defs
Module's can declare zero or more defs in their module manifest. Defs are simple String name/value pairs that are collapsed into a single global map by the registry. A good use of defs is to map a device id to a typespec, bog file, or some other metadata file. Then the registry may be used to map devices to Niagara information at learn time. Since the defs of all modules are collapsed into a single map, it is important to avoid name collisions. Convention is to prefix your defs using module name plus a dot, for example "lonworks.". When using Niagara's standard build tools, defs are defined in your "module-include.xml":





XML

<defs>
  <def name="test.a" value="alpha"/>
  <def name="test.b" value="beta"/>
</defs>


Use the registry API to query for defs:

Java

String val = Sys.getRegistry().getDef("test.a"); [cite: 517]
Spy
A good way to learn about the registry is to navigate its spy pages.