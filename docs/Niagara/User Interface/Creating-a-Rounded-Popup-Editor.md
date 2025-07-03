# Creating a Rounded Popup Editor

A new feature included with Niagara AX 3.7 is the wiresheet popup editor. This editor, if defined for a `BComponent` type in the wiresheet, is displayed in lieu of the Property Sheet for the component.

It is important to note that the Property Sheet view is still available for the component; the end user must select the view from the available views on the component.

The popup editor allows the application to create a `BWbEditor` for the component as a whole, rather than for each field in a component as is required by the Property Sheet.

To create a popup editor, simply create a class which extends the `BWbEditor` base abstract class and implements the new `BIPopupEditor` interface. The editor should be implemented as any other Workbench editor and will display whenever the component is clicked in the workbench wiresheet.
