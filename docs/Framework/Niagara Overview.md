# **Niagara Overview**

## **Mile High View**

Niagara: a Java software framework and infrastructure with a focus on three major problems:

* Integrating heterogeneous systems, protocols, and fieldbuses;  
* Empowering non-programmers to build applications using graphical programming tools;  
* Targeted for highly distributed, embedded systems;

## **Problem Space**

### **Java**

The framework uses the Java VM as a common runtime environment across various operating systems and hardware platforms. The core framework scales from small embedded controllers to high end servers. The framework runtime is targeted for Java 8 SE compact3 profile compliant VMs. The user interface toolkit and graphical programming tools are targetted for Java 8 SE VMs.

### **Integrating Heterogeneous Systems**

Niagara is designed from the ground up to assume that there will never be any one "standard" network protocol, distributed architecture, or fieldbus. Niagara's design center is to integrate cleanly with all networks and protocols. The Niagara Framework standardizes what's inside the box, not what the box talks to.

### **Programming for Non-programmers**

Most features in the Niagara Framework are designed for dual use. These features are designed around a set of Java APIs to be accessed by developers writing Java code. At the same, most features are also designed to be used through high level graphical programming and configuration tools. This vastly increases the scope of users capable of building applications on the Niagara platform.

### **Embedded Systems**

Niagara is targeted for embedded systems capable of running a Java VM. This excludes low devices without 32-bit processors or several megs of RAM. But even embedded systems with the horsepower of low end workstations have special needs. They are always headless and require remote administration. Embedded systems also tend to use solid state storage with limited write cycles and much smaller volume capacities than hard drives.

### **Distributed Systems**

The framework is designed to provide scalability to highly distributed systems composed of 10,000s of nodes running the Niagara Framework software. Systems of this size span a wide range of network topologies and usually communicate over unreliable Internet connections. Niagara is designed to provide an infrastructure for managing systems of this scale.

### **Component Software**

Niagara tackles these challenges by using an architecture centered around the concept of "Component Oriented Development". Components are pieces of self-describing software that can be assembled like building blocks to create new applications. A component centric architecture solves many problems in Niagara:

* Components provide a model used to normalize the data and features of heterogeneous protocols and networks so that they can be integrated seamlessly.  
* Applications can be assembled with components using graphical tools. This allows new applications to be built without requiring a Java developer.  
* Components provide unsurpassed visibility into applications. Since components are self-describing, it is very easy for tools to introspect how an application is assembled, configured, and what is occurring at any point in time. This provides immense value in debugging and maintaining Niagara applications.  
* Components enable software reuse.