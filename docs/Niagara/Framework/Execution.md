============================================================
PAGE 44
============================================================

# Execution
## Overview
 It is important to understand how BComponents are executed so that your components play nicely in the Niagara
 Framework. The Niagara execution model is based upon:
Running State: Every component may be started or stopped.
Links: Links allow events to propagate between components.
Timers: Timers are established using the Clock class.
Async Actions: Async actions are an important feature which prevent tight feedback loops.
Running State
 Every BComponent maintains a running state which may be checked via the BComponent.isRunning() method. A
 component may be put into the running state via the BComponent.start() method and taken out of the running
 state via the BComponent.stop() method.
 By default whenever a BComponent is started, all of its descendent components are also started recursively. This
 behavior may be suppressed using the Flags.NO_RUN flag on a property. During startup, any properties encountered
 with the noRun flag set will not be recursed.
 Every BComponent may add its component specific startup and shutdown behavior by overriding the started() and
 stopped() methods. These methods should be kept short; any lengthy tasks should be spawned off on another thread.
 Note: Developers will rarely call start() and stop() themselves. Rather these methods are automatically called
 during station bootup and shutdown. See Station Bootstrap.
Links
 The primary mechanism for execution flow is via the link mechanism. Links provide a powerful tool for configuring
 execution flow at deployment time using Niagara's graphical programming tools. Developers should design their
 components so that hooks are exposed via property, action, and topic slots.
 One of the requirements for link propagation is normalized types. Therefore Niagara establishes some standard types
 which should be used to provide normalized data. Any control point data should use one of the standard types found in
 the javax.baja.status package.
Timers
 Niagara provides a standard timer framework which should be used by components to setup periodic and one-shot
 timers. Timers are created using the schedule() and schedulePeriodically()methods on Clock. Timer
 callbacks are an action slot. The BComponent must be mounted and running in order to create a timer.
 There are four types of timers created with four different methods on Clock. Two are one-shot timers and two are
 periodic timers. The difference between the two one-shots and periodic timers is based on how the timers drift. Refer to
 the Clock bajadoc for more information.
Async Actions
 The Niagara execution model is event based. What this means is that events are chained through link propagation. This
 model allows the possibility of feedback loops when a event will loop forever in a cyclical link chain. To prevent feedback
 loops, component which might be configured with cyclical links should use async actions. An async action is an action
 slot with the Flags.ASYNC flag set.
Normal actions are invoked immediately either through a direct invocation or a link propagation. This invocation occurs
 on the callers thread synchronously. On the other hand, async actions are designed to run asynchronously on another
 thread and immediately return control to the callers thread. Typically async actions will coalesce multiple pending
 invocations.
 By default async actions are scheduled by the built in engine manager. The engine manager automatically coalesces
 action invocations, and schedules them to be run in the near future (100s of ms). Thus between actual execution times if
 the action is invoked one or one hundred times, it is only executed once every execution cycle. This makes it a very
 efficient way to handle event blasts such as dozens of property changes at one time. However all timer callbacks and async
 actions in the VM share the same engine manager thread, so developers should be cautious not to consume this thread
 except for short periods.
 Niagara also provides a hook so that async actions may be scheduled by subclasses by overriding the post() method.
 Using this method subclasses may schedule the action using their own queues and threads. A standard library for
 managing invocations, queues, and threads is provided by the following utility classes:
Invocation
Queue
CoalesceQueue
Worker
ThreadPoolWorker
BWorker
BThreadPoolWorker
System Clock Changes
 Some control algorithms are based on absolute time, for example a routine that runs every minute at the top of the
 mintue. These algorithms should ensure that they operate correctly even after system clock changes using the callback

BComponent.clockChanged(BRelTime shift).
