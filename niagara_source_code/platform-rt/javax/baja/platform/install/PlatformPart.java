/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform.install;

import javax.baja.util.*;

/**
 * A named, versioned item such as a piece of hardware or software, 
 * against which a PlatformDependency can be specified.
 * 
 * @author    Matt Boon       
 * @creation  3 Apr 07
 * @version   $Revision: 2$ $Date: 4/24/07 11:32:31 AM EDT$
 * @since     Baja 1.0
 */
public interface PlatformPart
{
  /**
   * Part name
   */
  String getPartName();

  /**
   * Part type
   */
  BPlatformPartType getPartType();

  /**
   * Version, or Version.ZERO if the part isn't versioned.
   */
  Version getPartVersion();
}
