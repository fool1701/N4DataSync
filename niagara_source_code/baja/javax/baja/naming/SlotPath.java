/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.util.TextUtil;
import javax.baja.sys.IllegalNameException;
import com.tridium.util.EscUtil;

/**
 * SlotPath is a ord scheme for resolving BValues
 * using slot names.  The BNF is:
 * <pre>
 *   slotpath  := absolute | relative
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
 * @author Brian Frank
 * @version $Revision: 21$ $Date: 11/30/06 6:08:15 PM EST$
 * @creation 8 Jan 03
 * @since Baja 1.0
 */
public class SlotPath
  implements OrdQuery, Path
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct an SlotPath with the specified scheme and body.
   *
   * @throws SyntaxException if the body isn't a valid file path.
   */
  public SlotPath(String scheme, String body)
    throws SyntaxException
  {
    this.scheme = TextUtil.toLowerCase(scheme).trim();
    this.body = body.trim();
    parse();
  }

  /**
   * Construct an SlotPath with the specified scheme and names.
   */
  public SlotPath(String scheme, String[] names)
    throws SyntaxException
  {
    this.scheme = TextUtil.toLowerCase(scheme).trim();
    abs = true;
    backupDepth = 0;
    this.names = names;

    if (names.length == 0)
    {
      body = "/";
    }
    else
    {
      StringBuilder s = new StringBuilder();
      for (String name : names) s.append('/').append(name);
      body = s.toString();
    }
  }

  /**
   * Convenience with "slot" scheme.
   */
  public SlotPath(String body)
    throws SyntaxException
  {
    scheme = "slot";
    this.body = body.trim();
    parse();
  }

  /**
   * Creates a new SlotPath instance for the given scheme and body.
   * Allows subclasses a chance to create new SlotPath instances.
   *
   * @since Niagara 3.2
   */
  protected SlotPath makeSlotPath(String scheme, String body)
  {
    return new SlotPath(scheme, body);
  }

////////////////////////////////////////////////////////////////
// Path
////////////////////////////////////////////////////////////////

  /**
   * Return true if this slot path is absolute
   * starting with a leading slash.
   */
  public boolean isAbsolute()
  {
    return abs;
  }

  /**
   * Return inverse of isAbsolute.
   */
  public boolean isRelative()
  {
    return !abs;
  }

  /**
   * Get the number of leading "../" indicating a
   * relative backup.  If this path is absolute or
   * directory relative then return 0.
   */
  @Override
  public int getBackupDepth()
  {
    return backupDepth;
  }

  /**
   * Convenience for {@code getParent()}.
   */
  @Override
  public Path getParentPath()
  {
    return getParent();
  }

  /**
   * Return a SlotPath for the parent path or null if
   * there is no parent.
   */
  public SlotPath getParent()
  {
    if (names.length == 0) return null;

    StringBuilder s = new StringBuilder();

    // leading slash if absoute
    if (isAbsolute())
    {
      s.append('/');
    }

    // or do backups
    else
    {
      int backups = getBackupDepth();
      for (int i = 0; i < backups; ++i) s.append("../");
    }

    // then do names up until my name
    for (int i = 0; i < names.length - 1; ++i)
    {
      if (i > 0) s.append('/');
      s.append(names[i]);
    }

    return makeSlotPath(scheme, s.toString());
  }

  /**
   * Get the number of names in the path after the
   * absolute or relative backup prefix.
   */
  @Override
  public int depth()
  {
    return names.length;
  }

  /**
   * Get the name at the zero based index between 0 and depth()-1.
   */
  @Override
  public String nameAt(int depth)
  {
    return names[depth];
  }

  /**
   * Get a copy of the names array.
   */
  @Override
  public String[] getNames()
  {
    return names.clone();
  }

  /**
   * @since Niagara 4.3U1
   */
  @Override
  public OrdQuery makePath(String body)
  {
    return new SlotPath(body);
  }

////////////////////////////////////////////////////////////////
// Escape / Unescape
////////////////////////////////////////////////////////////////

  /**
   * Does the specified string contain a valid name.
   * Allows subclasses a chance to check for a valid path name.
   *
   * @since Niagara 3.2
   */
  protected boolean isValidPathName(String name)
  {
    return isValidName(name);
  }

  /**
   * Does the specified string contain a valid name.
   */
  public static boolean isValidName(String name)
  {
    return EscUtil.slot.isValid(name);
  }

  /**
   * If the specified name is invalid then throw an IllegalNameException.
   */
  public static void verifyValidName(String name)
  {
    if (!isValidName(name))
      throw new IllegalNameException("baja", "IllegalNameException.name", new Object[] { name });
  }

  /**
   * Escape the specified string.
   */
  public static String escape(String s)
  {
    return EscUtil.slot.escape(s);
  }

  /**
   * Unescape the specified string.
   */
  public static String unescape(String s)
  {
    return EscUtil.slot.unescape(s);
  }

////////////////////////////////////////////////////////////////
// OrdQuery
////////////////////////////////////////////////////////////////

  /**
   * Return false.
   */
  @Override
  public boolean isHost()
  {
    return false;
  }

  /**
   * Return false.
   */
  @Override
  public boolean isSession()
  {
    return false;
  }

  /**
   * Return the scheme field.
   */
  @Override
  public String getScheme()
  {
    return scheme;
  }

  /**
   * Return the body field.
   */
  @Override
  public String getBody()
  {
    return body;
  }

  /**
   * If the query at index+1 is also a SlotPath, then perform
   * a merge using the {@code merge()} method.
   */
  @Override
  public void normalize(OrdQueryList list, int index)
  {
    if (list.isSameScheme(index, index + 1))
    {
      SlotPath append = (SlotPath)list.get(index + 1);
      list.merge(index, merge(append));
    }
  }

  /**
   * Merge this path with the specified path.  If the specified
   * path is absolute, then return it since it trumps this one.
   * If the specified path is relative then create a new
   * merged path taking in account any backup.
   */
  public SlotPath merge(SlotPath a)
  {
    // if absolute then return a
    if (a.isAbsolute()) return a;

    // otherwise we have no backup or a backup
    // contained within my path
    StringBuilder s = new StringBuilder();
    if (abs) s.append('/');

    // if the backup is past me
    if (a.getBackupDepth() > 0 && a.getBackupDepth() > depth())
    {
      // can't handle backup past absolute root
      if (abs)
        throw new SyntaxException("Invalid merge " + this + " + " + a);

      int backups = a.getBackupDepth() - depth() + getBackupDepth();
      for (int i = 0; i < backups; ++i) s.append("../");
    }

    // Need to handle the case where this relative SlotPath contains a backup (that hasn't
    // already been accounted for above). For example, when "slot:../../a/b/c" is followed by
    // "slot:d/e/f", it needs to merge to "slot:../../a/b/c/d/e/f".
    if (getBackupDepth() > 0 && s.length() == 0)
    {
      for (int i = 0; i < getBackupDepth(); i++)
      {
        s.append("../");
      }
    }

    // add my path minus backup
    boolean needSlash = false;
    for (int i = 0; i < depth() - a.getBackupDepth(); ++i)
    {
      if (needSlash) s.append('/');
      else needSlash = true;
      s.append(nameAt(i));
    }

    // now add relative path
    for (int i = 0; i < a.depth(); ++i)
    {
      if (needSlash) s.append('/');
      else needSlash = true;
      s.append(a.nameAt(i));
    }

    return makeSlotPath(scheme, s.toString());
  }

  /**
   * Return the body with path names unescaped.
   */
  public String toDisplayString()
  {
    return unescape(body);
  }

  /**
   * Return {@code scheme + ":" + body}.
   */
  public String toString()
  {
    return scheme + ':' + body;
  }

////////////////////////////////////////////////////////////////
// Parsing
////////////////////////////////////////////////////////////////

  /**
   * Parse the body into the appropriate fields.
   */
  void parse()
  {
    try
    {
      if (body.isEmpty()) return;

      // prefix is either "/" or "../"
      int start = 0;
      int c = body.charAt(0);
      if (c == '/')
      {
        abs = true;
        start = 1;
      }
      else if (c == '.') start = parseBackup();

      // parse names
      parseNames(start);
    }
    catch (SyntaxException e)
    {
      throw e;
    }
    catch (Throwable e)
    {
      throw new SyntaxException(e);
    }
  }

  /**
   * Parse the names separated by slash.
   */
  void parseNames(int start)
  {
    // cache in local variables
    String body = this.body;
    int len = body.length();

    // no names
    if (start >= len)
    {
      return;
    }

    // can't end in trailing backslash
    if (body.charAt(len - 1) == '/')
    {
      throw new SyntaxException("Trailing slash");
    }

    // parse
    String[] temp = new String[DEFAULT_CAPACITY];
    int n = 0;
    for (int i = start; i < len; ++i)
    {
      int c = body.charAt(i);
      if (c == '/')
      {
        if (i == start)
        {
          throw new SyntaxException("Illegal double slashes");
        }
        String name = body.substring(start, i);
        if (!isValidPathName(name))
        {
          throw new SyntaxException("Invalid name in path:" + name);
        }

        if (n >= temp.length)
        {
          // If the number of names in this SlotPath exceeds the DEFAULT_CAPACITY,
          // we'll allow the array to grow in chunks of DEFAULT_CAPACITY up to
          // the MAX_CAPACITY. We could use ArrayList to handle all this for us,
          // but since this could be a hot path of code, doing it here reduces a
          // little overhead.
          int newCapacity = Math.min(temp.length + DEFAULT_CAPACITY, MAX_CAPACITY);
          if (newCapacity > temp.length)
          {
            String[] newTemp = new String[newCapacity];
            System.arraycopy(temp, 0, newTemp, 0, temp.length);
            temp = newTemp;
          }
        }

        temp[n++] = name;
        start = i + 1;
      }
    }
    String end = body.substring(start, len);
    if (!isValidPathName(end))
    {
      throw new SyntaxException("Invalid name in path:" + end);
    }

    if (n >= temp.length)
    {
      // If adding the last name in this SlotPath exceeds the current array
      // capacity, we'll allow the array to grow by 1 up to the MAX_CAPACITY. We
      // could use ArrayList to handle all this for us, but since this could be
      // a hot path of code, doing it here reduces a little overhead.
      int newCapacity = Math.min(temp.length + 1, MAX_CAPACITY);
      if (newCapacity > temp.length)
      {
        // If we get here, we can just set the names array to the new array
        // instance because we're on the last name in the SlotPath anyways
        names = new String[newCapacity];
        System.arraycopy(temp, 0, names, 0, temp.length);
        names[n] = end;
        return;
      }
    }
    temp[n++] = end;

    // trim and store
    names = new String[n];
    System.arraycopy(temp, 0, names, 0, n);
  }

  /**
   * Parse the .. backup depth.  Return start index of path.
   */
  int parseBackup()
  {
    String body = this.body;
    int len = body.length();
    for (int i = 0; i < len; i += 3)
    {
      int c0 = body.charAt(i);
      int c1 = (i + 1 < len) ? body.charAt(i + 1) : -1;
      int c2 = (i + 2 < len) ? body.charAt(i + 2) : '/';
      if (c0 != '.') return i;
      if (c1 != '.' || c2 != '/')
      {
        // Since we know c0 is a period ('.'), we can check to see
        // if that is a valid path name.  For SlotPath's, it
        // should always return false, so the SyntaxException
        // will be thrown.  But for subclasses (such as VirtualPath),
        // this may be a legal path name, so we don't want to throw
        // the Syntax Exception.
        if (isValidPathName(String.valueOf((char)c0)))
          return i;
        throw new SyntaxException("Expecting ../ backup");
      }
      backupDepth++;
    }
    return len;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final String[] NO_NAMES = new String[0];
  private static final int DEFAULT_CAPACITY = 64;
  private static final int MAX_CAPACITY = Integer.MAX_VALUE - DEFAULT_CAPACITY;

  // Important developer note! EMPTY_SLOT_PATH must be the last constant
  // declared in this class in order to avoid strange NPEs due to a class
  // loading issue (the NO_NAMES constant must be declared first in order for
  // this constant to initialize correctly).

  /**
   * A SlotPath constant with an empty body String
   *
   * @since Niagara 4.13
   */
  public static final SlotPath EMPTY_SLOT_PATH = new SlotPath("");

  private final String scheme;
  private final String body;
  private boolean abs;
  private int backupDepth;
  private String[] names = NO_NAMES;
}
