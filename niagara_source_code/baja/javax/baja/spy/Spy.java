/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.spy;

/**
 * Spy is a single HTML page of diagnostics information.  Spies
 * can also be organized into a tree using the ISpyDir interface.
 * SpyDir is the default implementation of ISpyDir.
 *
 * @author    Brian Frank
 * @creation  5 Mar 03
 * @version   $Revision: 3$ $Date: 3/28/05 9:23:05 AM EST$
 * @since     Baja 1.0
 */
public abstract class Spy
{

////////////////////////////////////////////////////////////////
// Root
////////////////////////////////////////////////////////////////

  /**
   * This is the root of the spy tree for the VM.  Use this
   * directory to add new top level spy directories.
   */
  public static final SpyDir ROOT = new SpyDir();

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the title of the spy page.  Return null for no title.
   */
  public String getTitle()
  {
    return null;
  }

  /**
   * Write the spy page content in HTML.  The HTML 
   * should *not* include header or body tags.   
   */
  public abstract void write(SpyWriter out)
    throws Exception;
  
  /**
   * Equality uses == operator.
   */
  public boolean equals(Object obj)
  {
    return this == obj;
  }
      
}
