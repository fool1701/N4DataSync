/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hierarchy;

import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdQueryList;
import javax.baja.naming.SlotPath;
import javax.baja.naming.SyntaxException;

/**
 * A HierarchyQuery is the OrdQuery for hierarchies.  The grammar for the body is:
 *
 * <pre>
 *   hierarchypath  := absolute | relative
 *   absolute  := "/" path
 *   relative  := backup | path
 *   backup    := ( "../" )* path
 *   path      := name [ "/" path]
 *   name      := nameStart (namePart)*
 *
 *   nameStart  := alpha | escape
 *   namePart   := alpha | digit | safe | escape
 *   safe       := "_"
 *   alpha      := "a"-"z" | "A-Z"
 *   digit      := "0"-"9"
 *   escape     := asciiEsc | unicodeEsc
 *   asciiEsc   := "$" hex hex
 *   unicodeEsc := "$u" hex hex hex hex
 *   hex        := 'a'-'f' | 'A'-'F' | digit
 * </pre>
 *
 * @author    Andrew Saunders
 * @creation  19 Aug 13
 * @since     Baja 4.0
 */
public class HierarchyQuery
  extends SlotPath
{
  public HierarchyQuery(String scheme, String body)
    throws SyntaxException
  {
    super(scheme, body);
  }

  public HierarchyQuery(String body)
  {
    this("hierarchy", body);
  }

  @Override
  protected SlotPath makeSlotPath(String scheme, String body)
  {
    return new HierarchyQuery(scheme, body);
  }

  /**
   * @since Niagara 4.3U1
   */
  @Override
  public OrdQuery makePath(String body)
  {
    return new HierarchyQuery(body);
  }

  @Override
  public void normalize(OrdQueryList list, int index)
  {
    // if two like paths are next to one another then merge
    if (list.isSameScheme(index, index+1))
    {
      HierarchyQuery append = (HierarchyQuery)list.get(index+1);
      list.merge(index, merge(append));
    }

    // strip any non-sessions to my left
    list.shiftToSession(index);
  }
}
