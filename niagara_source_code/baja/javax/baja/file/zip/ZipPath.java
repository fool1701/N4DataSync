/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.zip;

import java.util.logging.Logger;
import javax.baja.file.*;
import javax.baja.naming.*;

/**
 * Specialized FilePath which refers to a single entry in a zip file
 * 
 * @author    Matt Boon
 * @creation  29 Sep 03
 * @version   $Revision: 2$ $Date: 3/28/05 9:22:57 AM EST$
 * @since     Baja 1.0
 */
public class ZipPath
  extends FilePath
{
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  public ZipPath(String scheme, String body)
  {
    super(scheme, body);
  }

  public ZipPath(String body)
  {
    this("zip", body);
  }

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////  

  @Override
  protected FilePath newInstance(String body)
  {
    return new ZipPath(getScheme(), body);
  }

  /**
   * @since Niagara 4.3U1
   */
  @Override
  public OrdQuery makePath(String body)
  {
    return new ZipPath(body);
  }

////////////////////////////////////////////////////////////////
// OrdQuery
////////////////////////////////////////////////////////////////  

  @Override
  public void normalize(OrdQueryList list, int index)
  {
    // if two like paths are next to one another then merge
    if (list.isSameScheme(index, index+1))
    {
      logger.fine(() -> "ZipPath#normalize would merge " + list.get(index) + ' ' +  list.get(index+1));
    }

    // overridden version does NOT remove queries to the left between
    // index and session
  }

  private static final Logger logger = Logger.getLogger("file");
}
