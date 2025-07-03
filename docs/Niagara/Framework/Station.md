============================================================
PAGE 46
============================================================

# Station
## Overview
A station is the main unit of server processing in the Niagara architecture:
A station database is defined by a single .bog file "file:!stations/{name}/config.bog";
Stations are booted from their config.bog file into a single VM/process on the host machine;
There is usually a one to one correspondance between stations and host machines (Supervisors or Jaces). However
 it is possible to run two stations on the same machine if they are configured to use different IP ports;

## Bootstrap
The following defines the station boot process:
1. Load: The first phase of bootstrap is to deserialize the config.bog database into memory as a BStation and
 mount it into the ord namespace as "local:|station:".
2. Service Registration: Once the bog file has been loaded into memory and mounted, the framework registers all
 services. Services are defined by implementing the BIService. After this step is complete each service from the
 bog file may be resolved using the Sys.getService() and Sys.getServices() methods.
3. Service Initialization: Once all services are registered by the framework, each service is initialized via the
   Service.serviceStarted() callback. This gives services a chance to initialize themselves after other services
   have been registered, but before general components get started.
4. Component Start: After service initialization the entire component tree under "local:|station:" is started
   using BComponent.start(). This call in turn results in the started() and descendentsStarted()
   callbacks. Once this phase is complete the entire station database is in the running state and all active links
   continue propagation until the station is shutdown.
5. Station Started: After all the components under the BStation have been started, each component receives the
   stationStarted() callback. As a general rule, external communications should wait until this stage so that all
   components get a chance to initialize themselves.
6. Steady State: Some control algorithms take a few seconds before the station should start sending control
   commands to external devices. To handle this case there is a built-in timer during station bootstrap that waits a
   few seconds, then invokes the BComponent.atSteadyState() callback. The steady state timer may be
   configured using the "nre.steadystate" system property. Use Sys.atSteadyState() to check if a station VM
   has completed its steady state wait period.