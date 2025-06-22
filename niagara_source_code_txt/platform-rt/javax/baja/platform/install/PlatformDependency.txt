/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform.install;

/**
 * A dependency that must be met before an module or dist file is
 * allowed to be installed.
 * 
 * @author    Matt Boon       
 * @creation  3 Apr 07
 * @version   $Revision: 3$ $Date: 6/1/07 11:56:30 AM EDT$
 * @since     Baja 1.0
 */
public interface PlatformDependency
{
  /**
   * The name, type, and version of the part the dependency requires.  The 
   * part name may contain '*' wildcards to match zero or more characters, or 
   * '?' wildcards to match exactly one character in the PlatformPart's name.
   */
  PlatformPart getPartPrototype();

  /**
   * Specifies whether the dependency requires an exact match on its version,
   * or whether its version is a minimum or maximum.
   */
  BVersionRelation getVersionRelation();
}
