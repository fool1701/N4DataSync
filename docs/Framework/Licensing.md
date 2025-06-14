============================================================
PAGE 58
============================================================

# Licensing
## Overview
 The Niagara licensing model is based upon the following elements:
HostId A short String id which uniquely identifies a physical box which runs Niagara. This could a Windows
 workstation, Jace-NP, or any Jace-XXX embedded platform. You can always check your hostId using the command
 "nre -version".
Certificate: A file ending in "certificate" which matches a vendor id to a public key. Certificates are granted by
 Tridium, and digitally signed to prevent tampering. Certificates are stored in the "{home}\certificates" directory.
License File: A file ending in "license" which enables a set of vendor specific features. A licenses file is only valid for
 a machine which matches its hostId. Licenses are digitally signed by a specific vendor to prevent tampering.
 License files are stored in the "{home}\licenses" directory.
Feature: A feature is a unique item in the license database keyed by a vendor id and feature name. For example
 "Tridium:jade" is required to run the Jade tool.
API: The javax.baja.license package provides a simple API to perform checks against the license database.
## License File
A license file is an XML file with a ".license" extension. License files are placed in "{home}\licenses". The filename itself
 can be whatever you like, but convention is to name the file based on the file's vendor id. The following is an example
 license file:

```xml
<license
     version="1.0"
     vendor="Acme"
     generated="2002-06-01"
     expiration="never"
     hostId="Win-0000-1111-2222-3333">
 <feature name="alpha"/>
 <feature name="beta" expiration="2003-01-15"/>
 <feature name="gamma" count="10"/>
 <signature>MC0CFACwUvUwA+mNXMfogNb6PVURneerAhUAgZnTYb6kBCsvsmC2by1tUe/5k/4=
</signature>
</license>
```

## Validation
During bootstrap, the Niagara Framework loads its license database based on the files found in the "{home}\licenses"
 directory. Each license file is validated using the following steps:
1. The hostId attribute matches the license file to a specific machine. If this license file is placed onto a machine
 with a different hostId, then the license is automatically invalidated.
2. The expiration attribute in the root element specifies the master expiration. Expiration must be a format of
 "YYYY-MM-DD". If the current time is past the expiration, the license file is invalidated. The string "never" may be
 used to indicate no expiration.
3. The generated attribute in the root element specifies the license file generation date as "YYYY-MM-DD". If the
 current time is before the generated date, the license file is invalidated.
4. The vendor attribute is used to inform the framework who has digitally signed this license file. In order to use a
 license file, there must be a corresponding certificate file for that vendor in the "{home}\certificates" directory.
5. The signature element contains the digital signature of the license file. The digital signature is created by the
 vendor using the vendor's private key. The signature is verified against the vendor's public key as found in the
 vendor's certificate. If the digital signature indicates tampering, the license file is invalid.
============================================================
PAGE 59
============================================================

# Features
A license database is a list of features merged from the machine's license files that are validated using the procedure
 discussed above. Each feature is defined using a single XML element called feature. Features are identified by the
 vendor id which is signed into the license file and a feature name defined by the name attribute.
The expiration attribute may be specified in the feature element to declare a feature level expiration. Expiration is a
 string in the format of "never" or "YYYY-MM-DD". If expiration is not specified then never is assumed.
Each feature may declare zero or more name/value properties as additional XML attributes. In the example license above
 the "gamma" feature has one property called "count" with a value of "10".
## Predefined Features
The following is a list of predefined features used by the Niagara Framework. All of these features require a vendor id of
 "Tridium":
workbench: Required to run the workbench tool.
station: Required to run a station database.
## API Usage
The following are some snippets of Java code used to access the license database:

```java
// verify that the "Acme:CoolFeature" is licensed on this machine
try
{
  Sys.getLicenseManager().checkFeature("Acme", "CoolFeature");
  System.out.println("licensed!");
}
catch(LicenseException e)
{
  System.out.println("not licensed!");
}
// get some feature properties
Feature f = Sys.getLicenseManager().getFeature("Acme", "gamma");
f.check();
String count = f.get("count");
```

## Checking Licenses
You may use the following mechanisms to check your license database:
1. Use the console command "nre -licenses".
2. Use the spy page .
