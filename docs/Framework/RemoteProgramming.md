============================================================
PAGE 47
============================================================

# Remote Programming
## Overview
 Remote programming is one of the most powerful features of Niagara. It is also the number one cause of confusion and
 performance problems. The term remote programming broadly applies to using the component model across a network
 connection. Some topics like subscription are critical concepts for many subsystems. But most often remote
 programming applies to programming with components in the workbench across a fox connection to a station
 (illustration).
 The component model provides a number features for network programming:
Lazy loading of a component tree across the network;
Automatic synchronization of database tree structure over network;
Ability to subscribe to real-time property changes and topic events;
Ability to invoke an action over the network like an RPC;
Support for timed subscriptions called leasing;
Automatic support for propagating components changes over network;
Ability to batch most network calls;
Fundamentals
 The component model has the ability to make remote programming virtually transparent. In this diagram, the
 component "/a/b" is accessed in the workbench VM, but actually lives and is executing in the station VM. The instance of
 the component in the workbench is called the proxy and the instance in the station is called the master.
 The first thing to note in Niagara is that both the proxy and master are instances of the same class. This is unlike
 technologies such as RMI where the proxy is accessed using a special interface. Also unlike RMI and its brethren, nothing
 special is required to make a component remote accessible. All Niagara components are automatically remotable by
 virtue of subclassing BComponent.
 From an API perspective there is no difference between programming against a proxy or a master component. Both are
 instances of the same class with the same methods. However, sometimes it is important to make a distinction. The most
 common way to achieve this is via the
BComponent.isRunning() method. A master component will return true and
 a proxy false. Although isRunning() is usually suitable for most circumstances, technically it covers other semantics
 such as working offline. The specific call for checking proxy status is via

============================================================
PAGE 48
============================================================

 BComponent.getComponentSpace().isProxyComponentSpace().
 Note that proxy components receive all the standard change callbacks like changed() or added(). Typically
 developers should short circuit these callbacks if the component is not running since executing callback code within a
 proxy can produce unintended side effects.
Proxy Features
 The framework provides a host of features which lets you program against a proxy component transparently:
The proxy can maintain the state of the master by synchronizing all properties in real-time;
Actions on the proxy act like RPCs;
Any changes to the proxy are automatically propagated to the master;
 The framework provides the ability to keep a proxy's properties completely synchronized in real-time to the master using
 subscription. While subscribed all property changes are immediately reflected in the proxy. This enables easy
 development of user interfaces that reflect the current state of a component. Note that only properties support this
 feature - other fields of your class will not be synchronized, and likely will be invalid if they are populated via station
 execution. Subscription is covered in more detail later.
 Another feature of Niagara is that all actions automatically act like RPCs (Remote Procedure Calls). When you invoke an
 action on a proxy, it automatically marshals the argument across the network, invokes the action on the master, and then
 marshals the result back to the proxy VM. Note that all other methods are invoked locally.
 Perhaps the most powerful feature of proxies is the ability to transparently and automatically propagate proxy side
 changes to the master. For example when you set a property on a proxy, it actually marshals the change over the network
 and makes the set on the master (which in turn synchronizes to the proxy once complete). This functionality works for
 all component changes: sets, adds, removes, renames, reorders, flag sets, and facet sets. Note that if making many
 changes it is more economical to batch the changes using a Transaction; this is discussed later.
Proxy States
 A proxy component exists in three distinct states:
Unloaded: in this state the proxy has not even been loaded across the network.
Loaded: in this state the proxy is loaded across the network and is known to the proxy VM; it may or may not be
 out-of-date with the master.
Subscribed: in this state the proxy is actively synchronized with the master.
 When a session is first opened to a station, none of the components in the station are known in the workbench. Rather
 components are lazily loaded into the workbench only when needed. Components which haven't been loaded yet are
 called
unloaded.
 Components become loaded via the BComplex.loadSlots() method. Components must always be loaded according
 to their tree structure, thus once loaded it is guaranteed that all a component's ancestors are also loaded. Rarely does a
 developer use the loadSlots() method. Rather components are loaded as the user expands the navigation tree or a
 component is resolved by ord.
 A loaded component means that a proxy instance representing the master component has been created in the
 workbench. The proxy instance is of the same class as the master, and occupies a slot in the tree structure identical to the
 master (remember all ancestors must also be loaded). The proxy has the same identity as the master. That means calling
 methods such as getName(), getHandle(), and getSlotPath() return the same result. However, note that the
 absolute ords of a proxy and master will be different since the proxy's ord includes how it was accessed over the network
 (see diagram).
 Once a proxy component has been loaded, it remains cached in the loaded state until the session is closed. Loaded
 proxies maintain their structure and identity automatically through the use of NavEvents. NavEvents are always routed
 across the network to maintain the proxy tree structure independent of the more fine grained component eventing. For
 example if a loaded component is
renamed, it always reflects the new name independent of subscription state. Or if
 removed it is automatically removed from the cache.
 Loaded components provide a cache of structure and identity, but they do not guarantee access to the current state of the

============================================================
PAGE 49
============================================================

 master via its properties. The subscribed state is used to synchronize a proxy with it's master. Subscription is achieved
 using a variety of mechanisms discussed next. Once subscribed a component is guaranteed to have all its property values
 synchronized and kept up-to-date with the master. Subscription is an expensive state compared to just being loaded,
 therefore it is imporant to unsubscribe when finished working with a proxy.
Subscription
 Subscription is a concept used throughout the framework. Components commonly model entities external to the VM.
 For example, proxy components model a master component in the station VM. Likewise, components in a station often
 model an external system or device. Keeping components synchronized with their external representations is usually
 computationally expensive. Therefore all components are built with a mechanism to be notified when they really need to
 be synchronized. This mechanism is called
subscription.
 Subscription is a boolean state. A component can check it's current state via the BComponent.isSubscribed()
 method. The subscribed() callback is invoked when entering the subscribed state, and unsubscribed() when
 exiting the subscribed state. The subscribed state means that something is currently interested in the component.
 Subscribed usually means the component should attempt to keep itself synchronized through polling or eventing. The
 unsubscribed state may be used to disable synchronization to save CPU, memory, or bandwidth resources.

Subscriptions often chain across multiple tiers. For example when you subscribe to a component in the workbench, that
 subscribes to the master in a station. Suppose the station component is a proxy point for a piece of data running in a Jace.
 That causes a subscription over the station-to-station connection resulting in the Jace's component to be subscribed. If
 the Jace component models an external device, that might initiate a polling operation. Keep in mind that n-tier
 subscribes might introduce delays. The stale status bit is often used with subscription to indicate that a value hasn't yet
 been updated from an external device.
 A component is moved into the subscribed state if any of the following are true:
If the component is running and any slot in the component is used as the source of an active link: isRunning()
 && getKnobs().length > 0.
There are one or more active Subscribers.
The component is permanently subscribed via the setPermanentlySubscribed() method. A typical example
 is a control point with an extension that returns true for requiresPointSubscription().
 Collectively these three cases are used by the framework to indicate interest in a component. The framework does not
 make a distinction between how a component is subscribed, rather all three cases boil down to a simple boolean
 condition: subscribed or unsubscribed.
 The Subscriber API is the standard mechanism to register for component events. You can think of Subscriber as the
 BComponent listener API. Subscriber maintains a list of all the components it is subscribed to, which makes cleanup easy
 via the unsubscribeAll() method. Subscribers receive the event() callback for any component event in their
 subscription list. Note that workbench developers typically use BWbComponentView which wraps the Subscriber API
 and provides automatic cleanup.
Leasing
 A common need is to ensure that a component is synchronized, but only as a snapshot for immediate use. The
 framework provides a feature called leasing to handle this problem. A lease is a temporary subscription, typically for one
 minute. After one minute, the component automatically falls back to the unsubscribed state. However, if the component
 is leased again before the minute expires, then the lease time is reset.
 Leasing is accomplished via the BComponent.lease() method.
Batch Calls
 Although the framework provides a nice abstraction for remote programming, you must be cognizant that network calls
 are occuring under the covers and that network calls are extremely expensive operations. The number one cause of
 performance problems is too many round robin network calls. The golden rule for remote programming is that one large
 batch network call is almost always better performing than multiple small network calls. Niagara provides APIs to batch
 many common operations.

============================================================
PAGE 50
============================================================

Batch Resolve
 The first opportunity to batch network calls is when resolving more than one ord to a component. Resolving a
 component deep down in the tree for the first time requires loading the component and all it's ancestors across the
 network. And if the ord is a handle ord, a network call is needed to translate the handle into a slot path. The most
 efficient way to batch resolve is the via the BatchResolve API.
Batch Subscribe
 Subscription is another key area to perform batch network calls. There are three mechanisms for batch subscribe:
1. The first mechanism is to subscribe using a depth. The common case for subscription is when working with a
 subsection of the component tree. Depth based subscribe allows a component and a number of descendent levels
 to be subscribed via one operation. For example if working with the children and grandchildren of a component,
 then subscribe with a depth of 2.
2. On rare occasions you may need to subscribe to a set of components scattered across the database. For this case
 there is a subscribe method that accepts an array of BComponents. Both the Subscriber and BWbComponentView
 classes provide methods that accept a depth or an array.
3. The third mechanism for batch subscribe is do a batch lease. Batch leasing  is accomplished via the static
 BComponent.lease() method.
Transactions
 By default, when making changes to a proxy component, each change is immediately marshaled over the network to the
 master. However, if making many changes, then it is more efficient to batch these changes using Transaction. Note
 most Transactions are used to batch a network call, but do not provide atomic commit capability like a RDBMS
 transaction.
 Transactions are passed as the Context to the various change methods like set() or add(). Instead of committing the
 change, the change is buffered up in the Transaction. Note that Transaction implements Context and is a
 SyncBuffer. Refer to Transaction's class header documentation for code examples.
Debugging
 The following provides some tips for debugging remote components:
 The spy pages provide a wealth of information about both proxy and master components including their subscribe state.
 A component spy's page also contains information about why a component is subscribed including the knobs and
 registered Subscribers. Note that right clicking a proxy component in the workbench causes a local lease, so it does
 introduce a Heisenberg effect; one work around is to bookmark the spy page to avoid right clicks.
 The outstanding leases of a VM can be accessed via the LeaseManager spy page.
 The most common performance problem is not batching up network calls. The mechanism for diagnosis is to turn on fox
 tracing.
Specially the "fox.broker" log will illustrate network calls for loads, subscribes (sub), unsubscribes (unsub), and
 proxy side changes (syncToMaster). The simplest way to turn on this tracing is Log Setup spy page.
