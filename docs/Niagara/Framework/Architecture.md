# **Architecture**

## **Overview**

This chapter introduces key concepts and terminology used in the Niagara architecture\[cite: 122\].

## **Programs**

There are typically four different programs (or processes) associated with a Niagara system\[cite: 123\]. These programs and their network communication are illustrated via the Communications Diagram\[cite: 124\]:

* **Station:** is the Niagara runtime \- a Java VM which runs a Niagara component application\[cite: 124\].  
* **Workbench:** is the Niagara tool \- a Java VM which hosts Niagara plugin components\[cite: 125\].  
* **Daemon:** is a native daemon process. The daemon is used to boot stations and to manage platform configuration such as IP settings\[cite: 126\].  
* **Web Browser:** is standard web browser such as IE or FireFox that hosts one of Niagara's web user interfaces\[cite: 127\].

## **Protocols**

There are typically three network protocols that are used to integrate the four programs described above:

* **Fox:** is the proprietary TCP/IP protocol used for station-to-station and workbench-to-station communication\[cite: 128\].  
* **HTTP:** is the standard protocol used by web browsers to access web pages from a station\[cite: 129\].  
* **Niagarad:** is the proprietary protocol used for workbench-to-daemon communication\[cite: 130\].

## **Platforms**

Niagara is hosted on a wide range of platforms from small embedded controllers to high end servers:

* **Jace:** the term Jace (Java Application Control Engine) is used to describe a variety of headless, embedded platforms\[cite: 131\]. Typically a Jace runs on a Flash file system and provides battery backup\[cite: 132\]. Jaces usually host a station and a daemon process, but not workbench\[cite: 133\]. Jaces typically run QNX or embedded Windows XP as their operating system\[cite: 134\].  
* **Supervisor:** the term Supervisor is applied to a station running on a workstation or server class machine\[cite: 135\]. Supervisors are typically stations that provide support services to other stations within a system such as history or alarm concentration\[cite: 136\]. Supervisors by definition run a station, and may potentially run the daemon or workbench\[cite: 137\].  
* **Client:** most often clients running a desktop OS such as Windows or Linux access Niagara using the workbench or a web browser\[cite: 138\].

## **Stations**

The Niagara architecture is designed around the concept of component oriented programming\[cite: 139\]. Components are self contained units of code written in Java and packaged up for deployment as modules\[cite: 140\]. Components are then wired together to define an application and executed using the station runtime\[cite: 141\].  
A Niagara application designed to be run as a station is stored in an XML file called config.bog\[cite: 142\]. The bog file contains a tree of components, their property configuration, and how they are wired together using links\[cite: 143\]. Station databases can be created using a variety of mechanisms\[cite: 144\]:

* Created on the fly and in the field using workbench graphical programming tools\[cite: 144\].  
* Created offline using workbench graphical programming tools\[cite: 145\].  
* Predefined and installed at manufacturing time\[cite: 145\].  
* Programmatically generated in the field, potentially from a learn operation\[cite: 146\].

Stations which restrict their programmability to accomplish a dedicated task are often called appliances\[cite: 147\]. Often the term Supervisor or Jace will be used interchangeably with station\[cite: 148\]. Technically the term station describes the component runtime environment common to all platforms, and Supervisor and Jace describe the hosting platform\[cite: 149\].

## **Daemon**

The Niagara daemon is the one piece of Niagara written in native code, not Java\[cite: 151\]. The daemon provides functionality used to commission and bootstrap a Niagara platform\[cite: 152\]:

* Manages installing and backing up station databases\[cite: 152\].  
* Manages launching and monitoring stations\[cite: 153\].  
* Manages configuration of TCP/IP settings\[cite: 153\].  
* Manages installation and upgrades of the operating system (QNX only)\[cite: 154\].  
* Manages installation and upgrades of the Java virtual machine\[cite: 155\].  
* Manages installation and upgrades of the Niagara software\[cite: 155\].  
* Manages installation of lexicons for localization\[cite: 156\].  
* Manages installation of licenses\[cite: 156\].

On Windows platforms, the daemon is run in the background as a Window's service\[cite: 157\]. On QNX it is run as a daemon process on startup\[cite: 158\]. The most common way to access daemon functionality is through the workbench\[cite: 159\]. A connection to the daemon is established via the "Open Platform" command which opens a PlatformSession to the remote machine\[cite: 160\]. A suite of views on the PlatformSession provides tools for accomplishing the tasks listed above\[cite: 161\]. Another mechanism used to access daemon functionality is via the plat.exe command line utility\[cite: 162\]. This utility provides much of the functionality of the workbench tools, but via a command line program suitable for scripting\[cite: 163\]. Run plat.exe in a console for more information\[cite: 164\].

## **Workbench**

Niagara includes a powerful tool framework called the workbench\[cite: 164\]. The workbench is built using the bajaui widget framework which is itself built using the standard Niagara component model\[cite: 165\]. The workbench architecture is designed to provide a common shell used to host plugins written by multiple vendors\[cite: 166\]. The most common type of plugin is a view which is a viewer or editor for working with a specific type of object such as a component or file\[cite: 167\]. Other plugins include sidebars and tools\[cite: 168\].

Workbench itself may be morphed into new applications using the BWbProfile API\[cite: 168\]. Profiles allow developers to reuse the workbench infrastructure to create custom applications by adding or removing menu items, toolbar buttons, sidebars, and views\[cite: 169\].

## **Web UI**

An important feature of Niagara is the ability to provide a user interface via a standard web browser such as IE or FireFox\[cite: 170\]. Niagara provides both server side and client side technologies to build web UIs\[cite: 171\].  
On the server side, the WebService component provides HTTP and HTTPS support in a station runtime\[cite: 172\]. The WebService provides a standard servlet engine\[cite: 173\]. Servlets are deployed as components subclassed from BWebServlet\[cite: 173\]. Additional classes and APIs are built upon this foundation to provide higher level abstractions such as BServletView\[cite: 174\].  
There are two client side technologies provided by Niagara\[cite: 175\]. The first is web workbench which allows the standard workbench software to be run inside a web browser using the Java Plugin\[cite: 175\]. The web workbench uses a small applet called wbapplet to download modules as needed to the client machine and to host the workbench shell\[cite: 176\]. These modules are cached locally on the browser's hard drive\[cite: 177\].  
In addition to the web workbench, a suite of technology called hx is available\[cite: 178\]. The hx framework is a set of server side servlets and a client side JavaScript library\[cite: 179\]. Hx allows a real-time user interface to be built without use of the Java Plugin\[cite: 180\]. It requires only web standards: HTML, CSS, and JavaScript\[cite: 181\].

## **Fox**

The Niagara Framework includes a proprietary protocol called Fox which is used for all network communication between stations as well as between Workbench and stations\[cite: 182\]. Fox is a multiplexed peer to peer protocol which sits on top of a TCP connection\[cite: 183\]. The default port for Fox connections is 1911\[cite: 184\]. Fox features include\[cite: 184\]:

* Layered over a single TCP socket connection\[cite: 184\].  
* Digest authentication (username/passwords are encrypted)\[cite: 184\].  
* Peer to peer\[cite: 184\].  
* Request / response\[cite: 184\].  
* Asynchronous eventing\[cite: 184\].  
* Streaming\[cite: 184\].  
* Ability to support multiple applications over a single socket via channel multiplexing\[cite: 184\].  
* Text based framing and messaging for easy debugging\[cite: 184\].  
* Unified message payload syntax\[cite: 184\].  
* High performance\[cite: 184\].  
* Java implementation of the protocol stack\[cite: 184\].

## **API Stack**

Niagara provides a broad suite of Java APIs used to customize and extend the station and workbench\[cite: 184\]. The software stack diagram illustrates the various software layers of the architecture\[cite: 185\]:

* **Baja:** The foundation of the architecture is defined via the baja module APIs\[cite: 185\]. These APIs define the basics such as modules, component model, naming, navigation, and security\[cite: 186\].  
* **Horizontal:** Niagara includes an extensive library of prebuilt components applicable to various M2M domains\[cite: 187\]. The modules provide standard components and APIs, including: control, alarming, historical data collection, scheduling, and BQL\[cite: 188\].  
* **Drivers:** Niagara is designed from the ground up to support multiple heterogeneous protocols\[cite: 189\]. Modules designed to model and synchronize data with external devices or systems are called drivers and are typically built with the driver framework\[cite: 190\]. Drivers integrate both fieldbus protocols like BACnet and Lonworks as well as enterprise systems like relational databases and web services\[cite: 191\].  
* **Human Interfaces:** An extensive software stack is provided for user interfaces\[cite: 192\]. The gx framework provides a standard model and APIs for low level graphics\[cite: 193\]. Built upon gx is the bajaui module which provides a professional toolkit of standard widgets\[cite: 194\]. Built upon bajaui is the workbench framework which provides the standard APIs for writing plugin tools\[cite: 195\]. The px framework and tools are used to enable non-programmers and developers alike to create new user interfaces via XML\[cite: 196\].

## **Software Stack**

## **Class Diagram**

## **Communication**

## **Remote Programming**

## **Driver Hierarchy**

## **ProxyExt**

## **Driver Learn**