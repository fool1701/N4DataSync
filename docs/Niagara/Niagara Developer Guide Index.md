# **Niagara Developer Guide Index**

## **Getting Started**

* **[Certificate Setup](../CERTIFICATE_SETUP.md):** How to set up module signing certificates for N4-DataSync development.

## **Framework**

* **Overview:** Provides a high level overview of the Niagara Framework and its problem space.  
* **Architecture:** Provides a broad overview of the architecture and introduces key concepts such as station, workbench, daemon, fox, and modules.  
* **Directory Structure:** Provides an overview of the Niagara 4 directory structure, highlighting the differences from NiagaraAX.  
* **API Information:** Provides an overview of API stability designation and public versus implementation APIs.  
* **Modules:** Provides an introduction to modules which are the software deliverables in the Niagara Framework.  
* **Object Model:** An introduction to the Niagara type system.  
* **Component Model:** An introduction to the Niagara component model.  
* **Building Simples:** Details for building simple Types.  
* **Building Enums:** Details for building enum Types.  
* **Building Complexes:** Details for building complex struct and component Types.  
* **Registry:** The Niagara registry is used to query details about what is installed on a given system.  
* **Collections:** Details on the Niagara Collections API.  
* **Naming:** The Niagara ord naming architecture and its APIs.  
* **Links:** Discusses object links.  
* **Execution:** Discusses the runtime execution environment.  
* **Station:** Discusses station lifecycle.  
* **Remote Programming:** Describes how the component model is programmed across the network.  
* **Files:** Discusses how files are mapped into the Niagara object model.  
* **Localization:** Discusses localization in the Niagara Framework.  
* **Spy:** An overview of the Niagara diagnostics framework.  
* **Licensing:** An overview of the Niagara licensing framework.  
* **XML:** An overview of Niagara XML document model and parser APIs.  
* **Bog Format:** Details of the Bog (Baja Object Graph) XML schema and APIs used to encode and decode component graphs.  
* **Distributions:** An overview of Niagara Distributions.  
* **Test:** How to use Niagara's test framework.  
* **Virtual Components:** An overview of using transient, on-demand (virtual) components.

## **User Interface**

In Niagara AX there are three different types of User Interface Technology a developer can choose from...

* **bajaui:** Niagara's own Java based User Interface toolkit. The Workbench Applet is required to run this User Interface in the browser.  
* **Hx:** A light weight Servlet based approach to creating HTML based User Interfaces.  
* **Mobile:** An HTML5 based User Interface technology specifically designed for smart tablets and phones.

In Niagara 4 we're trying to transition away from these technologies because....

* Modern web browsers are moving away from supporting the Java plug-in.  
* Three different skills sets are required to cover all bases (bajaui, Hx and Mobile).  
* Three times the amount of effort is needed to cover all bases.

Niagara 4 has a new User Interface stack based upon HTML5...

*Please note, bajaux support for Mobile will not be available in Niagara 4.0\!*

Workbench has an HTML5 Web Browser: you can now surf the web in Workbench. We recognize a lot of Niagara AX developers have created their existing User Interfaces in Java. By adding a browser to Workbench, you can transition away from Java for User Interfaces and use HTML5 instead.

* **JavaScript:** use JavaScript, HTML and CSS to create your Web Applications. We've provided a suite of JavaScript libraries to make this as easy as possible. This includes BajaScript, bajaux and much more.  
* **Web Server:** we've switched our web server to use Jetty. We've improved our Java Servlet support along the way.  
* **bajaui and Hx:** due to the massive investment our customers have in Niagara AX User Interface technology, we still support both bajaui and Hx in Niagara 4\.

## **Niagara 4 Open Web Technologies**

Our new User Interface technology uses HTML5. This consists of HTML, JavaScript and CSS. For information on developing your own applications using this technology, please see the section on Building JavaScript Applications.

* **RequireJS:** RequireJS is used to write Modular JavaScript code. In Java, packages are used to organize your code. In JavaScript, AMD (Asynchronous Module Definition) is the mechanism for defining modules of reusable JavaScript code. All new JavaScript code in Niagara 4 is modular and uses AMD.  
* **BajaScript:** BajaScript is a JavaScript library that's used to access Niagara data. It's important to note that BajaScript isn't a User Interface library. It's just for the data.  
* **bajaux:** bajaux is used to write User Interface Widgets in JavaScript. Code once and use everywhere. A bajaux Widget will run in both Hx and Workbench. These tutorials cover a lot of ground and utilize BajaScript, lexicons, dashboards and dialogs to create HTML5 web applications.  
* **webEditors:** a library of widgets, editors and frameworks built using bajaux.  
* **lexicon:** translate your HTML5 Widgets into different languages.  
* **dialogs:** dialog boxes to your HTML5 Widgets.

## **Niagara AX \- bajaui**

* **Gx:** Provides an overview of gx graphic toolkit.  
* **Bajaui:** Provides an overview of the widget component toolkit.  
* **Workbench:** Overview of the workbench shell.  
* **Web:** Overview of web APIs.  
* **Hx:** Overview of Hx APIs.  
* **Px:** Overview of Px technology.

## **Niagara AX \- Hx**

* **Overview:** Overview of Hx Architecture  
* **HxView:** Details of HxView  
* **HxOp:** Details of HxOp  
* **HxProfile:** Details of HxProfile  
* **Events:** Detail of Events  
* **Dialogs:** Details of Dialogs  
* **Theming:** Details of Theming

## **Niagara Theme Modules**

* **Creating Niagara 4 Themes:** Details on creating Niagara 4 theme modules.

## **Web Server**

Niagara's web server can be extended in 3 different ways. In Niagara AX, you could extend via Servlet Views and Web Servlet Components. In Niagara 4, you can also extend via standard Java Servlets.

* **Niagara Web Modules:** create Java Servlets in Niagara Modules.  
* **Servlet Views:** create a view that is also a Servlet.  
* **Web Servlet Components:** create a component that's also a Servlet.

In Niagara AX 3.7, we added support for Apache Velocity to Niagara. Velocity is a powerful Server Side Templating tool.

* **Apache Velocity:** create Server Side Templates for HTML.  
* **Px Velocity:** use the Velocity engine to dynamically create Px XML.

## **Horizontal Applications**

* **Control:** Overview of the control and automation module.  
* **History:** Overview to the historical database module.  
* **Alarm:** Overview to the alarming module.  
* **Schedule:** Overview of the scheduling module.  
* **Report:** Overview of the reporting module.

## **BQL**

* **BQL Overview:** Overview of the Baja Query Language.  
* **BQL Expressions:** Details on BQL expressions.  
* **BQL Examples:** Provides BQL examples.

## **Drivers**

* **Driver Framework:** Overview of the driver framework.  
* **PointDevicelet:** For reading and writing proxy points.  
* **HistoryDevicelet:** For importing and exporting histories.  
* **AlarmDevicelet:** For routing incoming and outgoing alarms.  
* **ScheduleDevicelet:** Used to perform master/slave scheduling.  
* **Basic Driver:** APIs for the basic driver framework  
* **BACnet:** APIs for the BACnet driver  
* **Lonworks:** APIs for the Lonworks driver  
* **Lon Markup Language:** Specification for the lonml XML format

## **Development Tools**

* **Build:** Documentation on using the build tool to compile and package software modules.  
* **Building JavaScript Applications:** Documentation on creating new Niagara 4 web modules and developing views with HTML5 and Javascript.  
* **Deploying Help:** How to build and package help documentation with Niagara modules.  
* **Slot-o-matic 2000:** Documentation for the slot-o-matic tool used to aid in the generation of boiler plate code for slot definitions.

## **Architecture Diagrams**

* **Software Stack:** Provides an illustration of the major software subsystems in Niagara AX.  
* **Class Diagram:** Illustration of the class hierarchy.  
* **Communication:** Illustrates Niagara's software processes and their protocols.  
* **Remote Programming:** Provides an overview of programming with remote components over fox.  
* **Driver Hierarchy:** Illustration of driver hierarchy.  
* **ProxyExt:** Illustration of proxy point design.  
* **Driver Learn:** Illustration of AbstractManager learn with discovery job.

## **Series Transforms**

* **Working with Series Schema:** Working with Graph Node Schema.  
* **Creating a Graph Component:** Creating new graph components for inclusion in the transform graph.  
* **Extending the Graph Node:** Extending the Graph Node.  
* **Creating a Series Transform Table:** Creating a Series Transform Table.  
* **Create the Series Cursor:** Create the Series Cursor.  
* **Creating a Rounded Popup Editor:** Creating a Rounded Popup Editor.  
* **Creating Aggregate Functions:** Extending functions for the Aggregate and Rollup graph nodes.

## **Security**

* **Security:** A general overview of the security model in the Niagara Framework.  
* **Security Manager:** Working with the Security Manager.  
* **Authentication:** Discusses the authentication model in the Niagara Framework.