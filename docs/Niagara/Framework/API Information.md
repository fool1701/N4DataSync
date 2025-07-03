# **API Information**

## **Overview**

There are a huge number of APIs available which are documented to varying degrees. In working with a specific API there are a couple key points to understand:

* **Stability:** a designation for the maturity of the API and its likelihood for incompatible changes;  
* **Baja vs Tridium:** public APIs are published under java.baja packages, and implementation specific code is published under com.tridium;

## **Stability**

Public APIs are classified into three categories:

* **Stable:** this designation is for mature APIs which have been thoroughly evaluated and locked down. Every attempt is made to keep stable APIs source compatible between releases (a recompile may be necessary). Only critical bug fixes or design flaws are just cause to break compatibility, and even then only between major revisions (such 3.0 to 3.1). This does not mean that stable APIs are frozen, they will continue to be enhanced with new classes and new methods. But no existing classes or methods will be removed.  
* **Evaluation:** this designation is for a functionally complete API published for public use. Evaluation APIs are mature enough to use for production development. However, they have not received enough utilization and feedback to justify locking them down. Evaluation APIs will likely undergo minor modification between major revisions (such 3.0 to 3.1). These changes will likely break both binary and source compatibility. However, any changes should be easily incorporated into production code with reasonable refactoring of the source code (such as a method being renamed).  
* **Development:** this designation is for code actively under development. It is published for customers who need the latest development build of the framework. Non-compatible changes should be expected, with the potential for large scale redesign.

## **What is Baja?**

Baja is a term coined from Building Automation Java Architecture. The core framework built by Tridium is designed to be published as an open standard. This standard is being developed through Sun's Java Community Process as JSR 60\. This JSR is still an ongoing effort, but it is important to understand the distinction between Baja and Niagara.

### **Specification versus Implementation**

Fundamentally Baja is an open specification and the Niagara Framework is an implementation of that specification. As a specification, Baja is not a set of software, but rather purely a set of documentation. The Baja specification will include:

* Standards for how Baja software modules are packaged;  
* The component model and its APIs;  
* Historical database components and APIs;  
* Alarming components and APIs;  
* Control logic components and APIs;  
* Scheduling components and APIs;  
* BACnet driver components and APIs;  
* Lonworks driver components and APIs;

Over time many more specifications for features will be added to Baja. But what is important to remember is that Baja is only a specification. Niagara is an implementation of that specification. Furthermore you will find a vast number of features in Niagara, that are not included under the Baja umbrella. In this respect Niagara provides a superset of the Baja features.

## **javax.baja versus com.tridium**

Many features found in Niagara are exposed through a set of Java APIs. In the Java world APIs are grouped together into packages, which are scoped using DNS domain names. Software developed through the Java Community Process is usually scoped by packages starting with java or javax. The APIs developed for Baja are all grouped under javax.baja. These are APIs that will be part of the open Baja specification and maybe implemented by vendors other than Tridium. Using these APIs guarantees a measure of vendor neutrality and backward compatibility.

Software developed by Tridium which is proprietary and outside of the Baja specification is grouped under the com.tridium packages. The com.tridium packages contain code specific to how Niagara implements the Baja APIs. The com.tridium code may or may not be documented. Most often these packages have their components and slots documented (doc=bajaonly), but not their low level fields and methods. In general com.tridium APIs should never be used by developers, and no compatibility is guaranteed.

*Note: Tridium has developed some APIs under javax.baja even though they are not currently part of the Baja specification. These are APIs that Tridium feels may eventually be published through Baja, but are currently in a development stage.*