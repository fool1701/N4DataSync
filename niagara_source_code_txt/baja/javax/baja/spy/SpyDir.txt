/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.spy;

import java.util.*;

/**
 * SpyDir is a subclass of Spy which implements the ISpyDir
 * interface.  SpyDir provides permanent directory structure
 * using a in-memory table.  Spy children may be added and
 * removed.
 *
 * @author    Brian Frank
 * @creation  5 Mar 03
 * @version   $Revision: 2$ $Date: 3/28/05 9:23:05 AM EST$
 * @since     Baja 1.0
 */
public class SpyDir
  extends Spy
  implements ISpyDir
{

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  /**
   * Write a page with hyperlinks to children returned via list().
   */
  @Override
  public void write(SpyWriter out)
    throws Exception
  {
    out.w("<ul>");
    String[] list = list();
    for(int i=0; i<list.length; ++i)
    {
      String name = list[i];
      String title = find(name).getTitle();
      if (title == null) title = name;
      out.w("<li>").a(list[i], title).w("</li>\n");
    }
    out.w("</ul>");
  }

////////////////////////////////////////////////////////////////
// ISpyDir
////////////////////////////////////////////////////////////////

  /**
   * List the child spy names.
   */
  @Override
  public String[] list()
  {
    if (list == null) return new String[0];
    return list.toArray(new String[list.size()]);
  }

  /**
   * Get the specified child by name or return null.
   */
  @Override
  public Spy find(String name)
  {
    if (map == null) return null;
    return map.get(name);
  }

////////////////////////////////////////////////////////////////
// Children Management
////////////////////////////////////////////////////////////////  

  /**
   * Add a spy page as a child keyed by name.
   */
  public void add(String name, Spy child)
  {
    if (map == null) { map = new HashMap<>(); list = new ArrayList<>(); }
    map.put(name, child);
    list.add(name);
  }
  
  /**
   * Remove the child spy page keyed by name.
   */
  public void remove(String name)
  {
    if (map == null) return;
    map.remove(name);
    list.remove(name);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private Map<String, Spy> map;
  private List<String> list;
  
}
