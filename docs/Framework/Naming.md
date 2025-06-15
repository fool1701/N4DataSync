============================================================
PAGE 39
============================================================

# Naming
## Overview
Niagara provides a uniform naming system to identify any resource which may be represented using an instance of
BObject. These names are called ords for Object Resolution Descriptor. You can think of a ords as URIs on steriods.
An ord is a list of one or more queries separated by the "|" pipe symbol. Each query is an ASCII string formatted as "
<scheme>:<body>". The scheme name is a globally unique identifier which instructs Niagara how to find a piece of code
to lookup an object from the body string. The body string is opaque and is formatted differently depending on the
scheme. The only rule is that it can't contain a pipe symbol.
Queries can be piped together to let each scheme focus on how to lookup a specific type of object. In general absolute
ords are of the format: host | session | space. Some examples:
ip:somehost|fox:|file:/dir/somefile.txt
ip:somehost|fox:1912|station:|slot:/Graphics/Home
local:|module://icons/x16/cut.png
In the examples above note that the "ip" scheme is used to identify a host machine using an IP address. The "fox" scheme
specifies a session to that machine usually on a specific IP port number. In the first example we identify an instance of a
file within somehost's file system. In the second example we identify a specific component in the station database.
The third example illustrates a special case. The scheme "local" which always resolves to BLocalHost.INSTANCE is
both a host scheme and a session scheme. It represents objects found within the local VM.
## APIs
The core naming APIs are defined in the javax.baja.naming package. Ords are represented using the BOrd class.
Ords may be resolved using the BOrd.resolve() or BOrd.get() methods. The resolve method returns an
intermediate OrdTarget that provides contextual information about how the ord was resolved. The get method is a
convenience for resolve().get().
Ords may be absolute or relative. When resolving a relative ord you must pass in a base object. If no base object is
specified then BLocalHost.INSTANCE is assumed. Some simple examples of resolving an ord:

BIFile f1 = (BIFile)BOrd.make("module://icons/x16/cut.png").get();
BIFile f2 = (BIFile)BOrd.make("file:somefile.txt").get(baseDir);

## Parsing
Ords may be parsed into their constituent queries using the method BOrd.parse() which returns OrdQuery[]. In
many cases you migth cast a OrdQuery into a concrete class. For example:

// dump the names in the file path
BOrd ord = BOrd.make("file:/a/b/c.txt");
OrdQuery[] queries = ord.parse();
FilePath path = (FilePath)queries[0];
for(int i=0; i<path.depth(); ++i)
System.out.println("path[" + i + "] = " + path.nameAt(i));

## Common Schemes
Niagara Developer Guide
8/26/2015
37



============================================================
PAGE 40
============================================================

The following is an informal introduction some common ord schemes used in Niagara.
### ip:
The "ip" scheme is used to identify a BIpHost instance. Ords starting with "ip" are always absolute and ignore any base
which may be specified. The body of a "ip" query is a DNS hostname or an IP address of the format "dd.dd.dd.dd".
### fox:
The "fox" scheme is used to establish a Fox session. Fox is the primary protocol used by Niagara for IP communication. A
"fox" query is formatted as "fox:" or "fox:<port>". If port is unspecified then the default 1911 port is assumed.
### file:
The "file" scheme is used to identify files on the file system. All file ords resolve to instances of
javax.baja.file.BIFile. File queries always parse into a FilePath File ords come in the following flavors:
Authority Absolute: "//hostname/dir1/dir2"
Local Absolute: "/dir1/dir2"
Sys Absolute: "!lib/system.properties"
User Absolute: "^config.bog"
Relative: "myfile.txt"
Relative with Backup: "../myfile.txt"
Sys absolute paths indicate files rooted under the Niagara installation directory identified via Sys.getBajaHome().
User absolute paths are rooted under the user home directory identified via Sys.getUserHome(). In the case of
station VMs, user home is the directory of the station database.
### module:
The "module" scheme is used to access BIFiles inside the module jar files. The module scheme uses the "file:" scheme's
formating where the authority name is the module name. Module queries can be relative also. If the query is local
absolute then it is assumed to be relative to the current module. Module queries always parse into a FilePath
module://icons/x16/file.png
module://baja/javax/baja/sys/BObject.bajadoc
module:/doc/index.html
### station:
The "station" scheme is used to resolve the BComponentSpace of a station database.
slot:
The "slot" scheme is used to resolve a BValue within a BComplex by walking down a path of slot names. Slot queries
always parse into a SlotPath.
### h:
The "h" scheme is used to resolve a BComponent by its handle. Handles are unique String identifiers for BComponents
within a BComponentSpace. Handles provide a way to persistently identify a component independent of any renames
which modify a component's slot path.
### service:
The "service" scheme is used to resolve a BComponent by its service type. The body of the query should be a type spec.
### spy:
Niagara Developer Guide
8/26/2015
38



============================================================
PAGE 41
============================================================

The "spy" scheme is used to navigate spy pages. The javax.baja.spy APIs provide a framework for making
diagnostics information easily available.
### bql:
The "bql" scheme is used to encapsulate a BQL query.
Niagara Developer Guide
8/26/2015
39