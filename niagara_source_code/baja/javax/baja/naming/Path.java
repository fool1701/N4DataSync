/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

/**
 * Path is the interface implemented by OrdQueries that contain a path in a hierarchical naming
 * system.
 *
 * @author    Brian Frank
 * @creation  4 Jan 03
 * @version   $Revision: 3$ $Date: 3/28/05 9:23:01 AM EST$
 * @since     Baja 1.0
 */
public interface Path
{
  /**
   * Get the parent path or null if there is no parent.
   */
  Path getParentPath();
    
  /**
   * Get the number of names in the path.
   */
  int depth();
  
  /**
   * Get the name at the zero based index between 0 and depth()-1.
   */
  String nameAt(int depth);
  
  /**
   * Get a copy of the names array.
   */
  String[] getNames();

  /**
   * Get the number of relative backups.  If this path is absolute or directory relative then return
   * zero.  The default implementation returns -1 to indicate that backups are not supported.
   * @since Niagara 4.3U1
   */
  default int getBackupDepth()
  {
    return -1;
  }

  /**
   * Creates a new OrdQuery instance for the given body. Each implementer of this interface should
   * override this method and create an instance of itself.
   * @since Niagara 4.3U1
   */
  default OrdQuery makePath(String body)
  {
    throw new UnsupportedOperationException();
  }
}
