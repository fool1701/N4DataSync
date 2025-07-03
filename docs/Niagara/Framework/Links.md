============================================================
PAGE 42
============================================================

# Links
## Overview
Links are the basic mechanism of execution flow in the Niagara Framework. Links allow components to be wired
together graphically by propogating an event on a one slot to another slot. An event occurs:
When property slot of a BComponent is modified.
When an action slot is invoked.
When a topic slot is fired.
## Links
A link is used to establish an event relationship between two slots. There are two sides to the relationship:
### Source: The source of the link is the BComponent generating the event either because one its properties is
modified or one its topics is fired. The source of a link is always passive in that is has no effect on the component
itself.
### Target: The target is the active side of the link. The target BComponent responds to an event from the source.
A link is established using a property slot on the target BComponent which is an instance of BLink . The BLink struct
stores:
### Source Ord: identifier for the source BComponent;
### Source Slot: name of the source component's slot;
### Target Slot: name of the target component's slot to act upon;
Note: The target ord is not stored explictly in a BLink because it is implicitly derived by being a direct child of the target
component.
The following table diagrams how slots may be linked together:
Source Target Semantics
Property Property When source property changes, set the target property
Property Action When source property changes, invoke the action
Action Action When source action is invoked, invoke target action (action chaining)
Action Topic When source action fires, fire target topic
Topic Action When source topic fires, invoke the action
Topic Topic When source topic fires, fire target topic (topic chaining)
## Link Check
Every component has a set of predefined rules which allow links to be established. These rules are embodied in the
LinkCheck class. Subclasses may override the BComponent.doLinkCheck() method to provide additional link
checking.
## Direct and Indirect Links
Links are constructed as either direct or indirect. A direct link is constructed with a direct Java reference to its source
BComponent, source slot, and target slot. A direct link may be created at anytime. Neither the source nor target
components are required to be mounted or running. These links must be explicitly removed by the developer. Direct
Niagara Developer Guide
8/26/2015
40



============================================================
PAGE 43
============================================================

links are never persisted. Examples of creating direct links:

target.linkTo("linkA", source, source.slot, target.slot);

...or...

BLink link = new BLink(source, source.slot, target.slot);
target.add("linkA", link);
link.activate();

An indirect link is created through indirect names. A BOrd specifies the source component and Strings are used for the
source and target slot names. Since an indirect link requires resolution of a BOrd to get its source component, the source
is required to be mounted when the link is activated. Indirect links are automatically removed if their source component
is unmounted while the link is activated. Examples of creating an indirect link:

BLink link = new BLink(BOrd.make("h:77"), "sourceSlot", "targetSlot");
target.add("linkA", link);

Note: Links are rarely created programmatically, but rather are configured using the graphical programming tools. The
major exception to this rule is building GUIs in code. In this case it is best to establish direct links in your constructor.
## Activation
Links exist in either an activated or deactivated state. When a link is activated it is actively propagating events from the
source slot to the target slot. Activated links also maintain a Knob on the source component. Knobs are basically a mirror
image of a link stored on the source component to indicate the source is actively propagating events over one or more
links.
When a link is deactivated event propagation ceases and the Knob is removed from the source component.
Activation:
1. Links are activated when the BLink.activate() method is called. If the link is indirect, then the source ord
   must be resolvable otherwise an UnresolvedException is thrown.
2. If creating a direct link using the BComponent.linkTo() method the link is automatically activated.
3. Enabled links are activated during BComponent start. This is how most indirect links are activated (at station boot
   time).
4. Anytime a BLink value is added as a dynamic property on a running BComponent it is activated.
   Deactivation:
1. Links are deactivated when the BLink.deactivate() method is called.
2. Anytime a property with a BLink value is removed from BComponent it is deactivated and the target property is
   set back to its default value.
3. Anytime the source component of a active indirect link is unmounted, the link is deactivated and removed from the
   target component.
   Niagara Developer Guide
   8/26/2015
   41