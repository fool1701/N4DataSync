/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.sys.*;
import javax.baja.util.*;

/**
 * IFileFilter.
 *
 * @author    Andy Frank
 * @creation  14 Nov 02
 * @version   $Revision: 3$ $Date: 4/29/03 2:34:54 PM EDT$
 * @since     Baja 1.0
 */
public interface IFileFilter
{ 
  /** 
   * Accept the specified FileNode.
   */
  public boolean accept(BIFile file);
  
  /**
   * Return the description of this filter.
   */
  public String getDescription(Context cx);
  
////////////////////////////////////////////////////////////////
// Default Implemenations
////////////////////////////////////////////////////////////////

  /**
   * Accept only directories.
   */
  static IFileFilter directory = new IFileFilter()
  {
    @Override
    public boolean accept(BIFile file) { return (file instanceof BIDirectory); }
    @Override
    public String getDescription(Context cx)
    { 
      return Lexicon.make("baja", cx).getText("fileFilter.dirOnly");
    }
  };
  
  /**
   * Accept all files except directories.
   */
  static IFileFilter all = new IFileFilter()
  {
    @Override
    public boolean accept(BIFile file) { return true; }
    @Override
    public String getDescription(Context cx)
    { 
      return Lexicon.make("baja", cx).getText("fileFilter.all");
    }
  };
}
