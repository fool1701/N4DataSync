# WbTool
Tools are plugins to the workbench Tools menu. Tools provide functionality independent of the active ord. Typically tools are dialogs or wizards used to accomplish a task. There are three types of tools:

## BWbTool: is the base class of all tools. It provides a single invoke(BWbShell shell) callback when the tool is selected from the Tools menu. Often invoke is used to launch a dialog or wizard.
## BWbNavNodeTool: is a tool which gets mounted into the ord namespace as "tool:{typespec}|slot:/". Selecting the tool from the Tools menu hyperlinks as the tool's ord and then standard WbViews are used to interact with the tool. Typically in this scenario the tool itself is just a dummy component used to register one or more views.
## BWbService: is the most sophisticated type of tool. Services are WbNavNodeTools, so selecting them hyperlinks to the tool's ord. Services also provide the ability to run continuously in the background independent of the active ord. This is useful for monitoring tools or to run drivers locally inside the workbench VM. Services can be configured to start, stop, and auto start via the WbServiceManager.
The rules for building a tool:

Create a subclass of BWbTool, BWbNavNodeTool, or BWbService.
Provide display name and icon in your lexicon according to TypeInfo rules:

NewModuleTool.displayName=New Module
NewModuleTool.icon=module://icons/x16/newModule.png