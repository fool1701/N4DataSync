============================================================
PAGE 65
============================================================

# Distributions
## Overview
 Niagara distributions are JAR files that contain all the files necessary to install a Niagara application on a host. A
 distribution is typically created for a specific release of a product, and may contain modules, lexicons, documentation,
 graphics, and other miscellaneous files.
The distribution manifest is found in the meta-inf/dist.xml JAR entry. It
provides some high-level descriptive information about the distribution,
specifies the distribution's external dependencies and exclusions,
provides a summary description of its contents to assist installer software in dependency analysis,
identifies contents that should never be copied to the host,
specifies the rules by which files are replaced, and identifies directories and files which must be removed before
 installation begins.
## dist.xml Syntax
The root element of the manifest is dist. It has the following required attributes:
name: a unique name for the distribution
version: the distribution's version string
vendor: the distribution's vendor name
vendorVersion: the distribution's vendor version string
description: a brief description of the distribution
The dist element may also have the following optional attributes:
installTime: the time the distribution was created, in milliseconds since Jan 1, 1970
hostid: a hostid string that may be used to restrict installation to specific hosts
absoluteElementPaths: if "true", all path names in fileHandling elements are absolute paths. By default, paths are
 relative to the Niagara home directory.
The dist element may contain the following sub-elements:
dependency element [optional]
Describes a module dependency. Attributes are the same as those for the dependency element in the module.xml
 manifest (see modules.html), and the optional rel option is supported.
part element [optional]
Describes a dependency on any other kind of part, such as a piece of hardware. Required attribute name is a unique name
 for the part, and required attribute description provides a brief description. Optional version attribute, if present,
============================================================
PAGE 66
============================================================

 specifies the version of the part.
exclude element [optional]
Describes a module that should not be installed with this distribution, even if it is a dependency of one of the modules in the
 distribution. Attributes are the same as those for the dependency element in the module.xml manifest (see
 modules.html).
contents element [optional]
Provides a summary description of the distribution's contents. It may contain the following sub-elements:
module element
 Describes a module contained in the distribution. Attributes are the same as those for the dependency element in the
 module.xml manifest (see modules.html).
file element
 Describes any other file contained in the distribution. Required attribute name is the path to the file (according to
 absoluteElementPaths element). Optional attributes include size (in bytes) and crc (a CRC32 checksum).
fileHandling element [optional]
 Specifies the rules by which files are replaced, and identifies directories and files which must be removed before
 installation begins. The optional replace attribute specifies the rules for replacing existing files - its values can be "always"
 if the file is always to be replaced, "never" if it is never to be replaced, and "crc" if the file is to be replaced only if the CRC
 checksums for the distribution and current versions of the file are not the same. By default, no files or directories are to
 be removed by the installer prior to installation, and the "crc" replacement rule is used. The fileHandling element may
 contain the following sub-elements:
remove element
 Specifies a file or directory to be removed prior to installation. Its required name attribute specifies the path (according
 to absoluteElementPaths element) to the file/directory. If name specifies a directory, exceptions may be specified using
 the keep sub-element.
keep element [optional]
 Specifies a file or directory that should not be removed as the result of a remove element. Its required name attribute
 specifies the path.
file element [optional]
 Specifies a file replacement rule that differs from the default in the fileHandling element or any dir element that might
 apply to the file's path. Its required name attribute specifies the file path, and the required replace attribute specifies the
 rules for replacing the existing file - its values can be "always" if the file is always to be replaced, "never" if it is never to be
 replaced, "crc" if the file is to be replaced only if the CRC checksums for the distribution and current versions of the file
 are not the same, "oscrc" if the file's CRC checksum is to be checked against a CRC value returned by the niagarad for the
 OS image, "nocopy" if the file is never to be copied to the host, or "hostid" if the file is to be copied only if the host's
 hostid matches the value of the
dist element's hostid attribute.
dir element [optional]
 Specifies a file replacement rule for a given directory path that differs from the default in the fileHandling element or in
 dir elements for parent paths. Its required name attribute specifies the directory's path. Its optional replace attribute can
 be "always", "never", "crc", "nocopy" or "hostid". Its optional clean attribute, if present and equal to "true", indicates that
 any file or subdirectory that isn't part of the distribution should be deleted by the installer.
