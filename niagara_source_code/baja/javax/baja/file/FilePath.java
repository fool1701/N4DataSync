/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdQueryList;
import javax.baja.naming.Path;
import javax.baja.naming.SyntaxException;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.IllegalNameException;

/**
 * FilePath is a specialization of OrdScheme for file queries.  
 * The file body BNF is:
 * <pre>{@code
 *   file      := path [fragment]
 *   path      := absolute | relative
 *   fragment  := "#" name
 *
 *   absolute     := authorityAbs | localAbs | homeAbs
 *   authorityAbs := "//" authority "/" [names]
 *   localAbs     := "/" [names]
 *   sysHomeAbs   := "!" [names]
 *   userHomeAbs  := "~" [names]
 *   stationHomeAbs  := "^" [names]
 *   protectedStationHomeAbs  := "^^" [names]
 *
 *   relative  := backup | dirRel
 *   rel       := dirRel
 *   backup    := ( "../" )* path
 *
 *   names     := names [ "/" path ]
 *   name      := nameChar (nameChar)*
 *   nameChar  := (a-z) | (A-Z) | (0-9) | specials
 *   specials  := space | . | : | - | _ | $ | + | ( | ) | & | ` | ' | @ | [ | ]
 * 
 * }</pre>
 *
 * @author    Brian Frank
 * @creation  3 Jan 03
 * @version   $Revision: 29$ $Date: 10/19/06 3:21:46 PM EDT$
 * @since     Baja 1.0
 */
public class FilePath
  implements OrdQuery, Path, Comparable<FilePath>
{
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct an FilePath with the specified scheme and body.
   *
   * @throws SyntaxException if the body isn't a valid file path.
   */
  public FilePath(String scheme, String body)
    throws SyntaxException
  {
    this.scheme = TextUtil.toLowerCase(scheme).trim();
    this.body = body.trim();
    parse();
  }

  /**
   * Convenience with "file" scheme.
   */
  public FilePath(String body)
    throws SyntaxException
  {
    scheme = "file";
    this.body = body.trim();
    parse();
  }

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////  

  protected FilePath newInstance(String body)
  {
    return new FilePath(getScheme(), body);
  }

  /**
   * @since Niagara 4.3U1
   */
  @Override
  public OrdQuery makePath(String body)
  {
    return new FilePath(body);
  }

////////////////////////////////////////////////////////////////
// File
////////////////////////////////////////////////////////////////  

  /**
   * Return true if this file path is authority absolute, 
   * local absolute, or home absolute.
   */
  public boolean isAbsolute()
  {
    return absMode != RELATIVE;
  }

  /**
   * Return inverse of isAbsolute.
   */
  public boolean isRelative()
  {
    return absMode == RELATIVE;
  }
  
  /**
   * Return one of the absolute mode constants or 
   * RELATIVE if this file path is not absolute.
   */
  public int getAbsoluteMode()
  {
    return absMode;
  }
  
  /**
   * Convenience for <code>getAbsoluteMode() == AUTHORITY_ABSOLUTE</code>.
   */
  public boolean isAuthorityAbsolute()
  {
    return absMode == AUTHORITY_ABSOLUTE;
  }

  /**
   * Convenience for <code>getAbsoluteMode() == LOCAL_ABSOLUTE</code>.
   */
  public boolean isLocalAbsolute()
  {
    return absMode == LOCAL_ABSOLUTE;
  }

  /**
   * Convenience for <code>getAbsoluteMode() == SYS_HOME_ABSOLUTE</code>.
   */
  public boolean isSysHomeAbsolute()
  {
    return absMode == SYS_HOME_ABSOLUTE;
  }
  
  /**
   * Convenience for <code>getAbsoluteMode() == USER_HOME_ABSOLUTE</code>.
   */
  public boolean isUserHomeAbsolute()
  {
    return absMode == USER_HOME_ABSOLUTE;
  }

  /**
   * Convenience for <code>getAbsoluteMode() == STATION_HOME_ABSOLUTE</code>.
   * Only applicable for station VMs.
   */
  public boolean isStationHomeAbsolute()
  {
    return absMode == STATION_HOME_ABSOLUTE;
  }
  
  public boolean isProtectedStationHomeAbsolute()
  {
    return absMode == PROTECTED_STATION_HOME_ABSOLUTE;
  }
  
  /**
   * If this path is:
   * <ul>
   * <li>authority absolute: return <code>"//" + authority "/"</code></li>
   * <li>local absolute: return <code>"/"</code></li>
   * <li>sys home absolute: return <code>"!"</code></li>
   * <li>station home absolute: return <code>"^"</code></li>
   * <li>relative: return <code>""</code></li>
   * </ul>
   */
  public String getAbsoluteBase()
  {
    switch(absMode)
    {
      case RELATIVE:           return "";
      case AUTHORITY_ABSOLUTE: return "//" + authority + '/';
      case LOCAL_ABSOLUTE:     return "/";
      case SYS_HOME_ABSOLUTE:  return "!";
      case USER_HOME_ABSOLUTE:  return "~";
      case STATION_HOME_ABSOLUTE: return "^";
      case PROTECTED_STATION_HOME_ABSOLUTE: return "^^";
      default: throw new IllegalStateException();
    }
  }
  
  /**
   * Return the authority name if this a AUTHORITY_ABSOLUTE 
   * path, otherwise return null.
   */
  public String getAuthority()
  {
    return authority;
  }
  
  /**
   * Get the fragment name or return null if not found.
   */
  public String getFragment()
  {
    return fragment;
  }
  
  /**
   * Get the simple name of the file.  This is the last 
   * name in the path.  Or if the path length is zero
   * return return <code>getAbsoluteBase()</code>.
   */
  public String getName()
  {
    if (names.length == 0) return getAbsoluteBase();
    else return names[names.length-1];
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
   * Convenience for <code>getParent()</code>.
   */
  @Override
  public Path getParentPath()
  {
    return getParent();
  }

  /**
   * Return a FilePath for the parent path or null if 
   * there is no parent.
   */
  public FilePath getParent()
  {
    if (names.length == 0) return null;
    
    StringBuilder s = new StringBuilder();
    
    // get base if absoute
    if (isAbsolute())
    {
      s.append(getAbsoluteBase());
    }
    
    // or do backups
    else
    {
      int backups = getBackupDepth();
      for(int i=0; i<backups; ++i) s.append("../");    
    }    
    
    // then do names up until my name
    for(int i=0; i<names.length-1; ++i) 
    {
      if (i > 0) s.append('/');
      s.append(names[i]);
    }
    
    return newInstance(s.toString());
  }

////////////////////////////////////////////////////////////////
// Naming
////////////////////////////////////////////////////////////////

  /**
   * Does the specified string contain a file name.
   */
  public static boolean isValidName(String name)
  {
    try
    {
      int len = name.length();
      if (len == 0) return false;
      for(int i=0; i<len; ++i)
      {     
        int ch = name.charAt(i);
        if (!isName(ch) || ch == ':')
          return false;
      }
      return true;
    }
    catch(Exception e)
    {
      return false; // not ascii
    }
  }

  /**
   * If the specified name is invalid then throw an IllegalNameException.
   */
  public static void verifyValidName(String name)
  {
    if (!isValidName(name))
      throw new IllegalNameException("baja", "IllegalNameException.name", new Object[] { name });
  }
  
////////////////////////////////////////////////////////////////
// OrdQuery
////////////////////////////////////////////////////////////////
  
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
   * If the query at index+1 is also a FilePath, then perform 
   * a merge using the <code>merge()</code> method.
   */
  @Override
  public void normalize(OrdQueryList list, int index)
  {
    // if two like paths are next to one another then merge
    if (list.isSameScheme(index, index+1))
    {
      FilePath append = (FilePath)list.get(index+1);
      list.merge(index, merge(append));
    }
    
    // strip any non-sessions to my left
    list.shiftToSession(index);
  }
    
  /**
   * Convenience for <code>merge(new FilePath(getScheme(), a))</code>.
   */
  public FilePath merge(String a)
  {
    return merge(newInstance(a));
  }
  
  /**
   * Merge this path with the specified path.  If the specified
   * path is absolute, then return it since it trumps this one.
   * If the specified path is relative then create a new merged 
   * path taking in account any backup.  If the specified path
   * has a fragment, then the merged path will contain the
   * fragment too.
   */
  public FilePath merge(FilePath a)
  {
    // if it is absolute
    if (a.isAbsolute())
    {
      // if this is authAbs + !authAbs, then we
      // handle it special
      if (isAuthorityAbsolute() && !a.isAuthorityAbsolute())
        return newInstance("//" + authority + a.body);
      
      // otherwise just let a trump
      return a;
    }
          
    // for now only support join if I am absolute
    // or the relative doesn't backup past me
    if (isAbsolute() || a.getBackupDepth() == 0 || a.getBackupDepth() < depth())
    {
      // now we need to get my base
      StringBuilder s = new StringBuilder(getAbsoluteBase());
      
      // add my path minus backup
      boolean needSlash = false;
      for(int i=0; i<depth()-a.getBackupDepth(); ++i)
      {
        if (needSlash) s.append('/');
        else needSlash = true;
        s.append(nameAt(i));
      }
        
      // now add relative path
      for(int i=0; i<a.depth(); ++i)
      {
        if (needSlash) s.append('/'); 
        else needSlash = true;
        s.append(a.nameAt(i));
      }
      
      // add fragment
      if (a.fragment != null)
        s.append('#').append(a.fragment);
        
      return newInstance(s.toString());
    }
    
    // bomb
    throw new SyntaxException("Invalid merge " + this + " + " + a);
  }

  
  /**
   * Return <code>scheme + ":" + body</code>.
   */  
  public String toString()
  {
    return scheme + ':' + body;
  }

////////////////////////////////////////////////////////////////
// Parsing
////////////////////////////////////////////////////////////////  

  /**
   * Parse the body into the appropiate fields.
   */
  void parse()
  {
    try
    {
      if (body.isEmpty()) return;
      String fullBody = body;
      
      // check for fragment, if so then change body
      // to be without fragment during parse
      int frag = body.indexOf('#');
      if (frag >= 0)
      {
        fragment = body.substring(frag+1);
        body = body.substring(0, frag);
      }
      
      int start = parsePrefix();     
      parseNames(start);
      
      // in case we temp changed body without frag
      body = fullBody;
    }
    catch(SyntaxException e)
    {
      throw e;
    }
    catch(Throwable e)
    {
      throw new SyntaxException(body, e);
    }
  }
  
  /**
   * Parse the starting absolute or backup.  
   * Return start index of path.
   */
  int parsePrefix()
  {  
    String body = this.body;
    int len = body.length();
    
    int c0 = -1; if (len > 0) c0 = body.charAt(0);
    int c1 = -1; if (len > 1) c1 = body.charAt(1);
        
    // leading slash
    if (c0 == '/')
    {
      // authority absolute
      if (c1 == '/')
      {
        absMode = AUTHORITY_ABSOLUTE;
        int slash = body.indexOf('/', 3);
        if (slash < 0) 
        {
          authority = body.substring(2, len);
          checkAuthority(authority);
          return len;
        }
        else
        {
          authority = body.substring(2, slash);
          checkAuthority(authority);
          return slash+1;
        }
      }
      
      // local absolute
      else
      {
        absMode = LOCAL_ABSOLUTE;
        return skipSlash(body, 1);
      }
    }
    
    // system home or library absolute
    if (c0 == '!') 
    {
      // system home absolute
      absMode = SYS_HOME_ABSOLUTE;
      return 1; 
    }

    // user home absolute
    if (c0 == '~') 
    {
      absMode = USER_HOME_ABSOLUTE;
      return 1; 
    }
    
    // station home absolute
    if (c0 == '^') 
    {
      if (c1 == '^')
      {
        absMode = PROTECTED_STATION_HOME_ABSOLUTE;
        return 2;
      }
      absMode = STATION_HOME_ABSOLUTE;
      return 1; 
    }
    
    // station home absolute
    // if (c0 == '$') 
    // {
    //   absMode = PROTECTED_STATION_HOME_ABSOLUTE;
    //   return 1; 
    // }
        
    // backup path
    if (c0 == '.')
    {        
      absMode = RELATIVE;
      return parseBackup();
    }
    
    // must be relative
    absMode = RELATIVE;
    return 0;
  }
  
  /**
   * Parse the .. backup depth.  Return start index of path.
   */
  int parseBackup()
  {
    String body = this.body;
    int len = body.length();
    
    // check if this really is a backup 
    if (len == 1)
      throw new SyntaxException("Dot not supported: " + body);
    char x = body.charAt(1);
    if (x != '.')
    {
      if (x == '/')
        throw new SyntaxException("Dot/ not supported: " + body);
      return 0;
    }
    
    // otherwise this is a backup
    for(int i=0; i<len; i+=3)
    {
      int c0 = body.charAt(i);
      int c1 = (i+1 < len) ? body.charAt(i+1) : -1;
      int c2 = (i+2 < len) ? body.charAt(i+2) : '/';
      if (c0 != '.') return i;
      if (c1 != '.')
      { 
        if (c1 == '/')
          throw new SyntaxException("Single dot unsupported: " + body);
        return i;
      }
      if (c2 != '/')
        throw new SyntaxException("Expecting ../ backup: " + body);
      backupDepth++;
    }
    return len;    
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
    if (start >= len) return;
    
    // can't end in trailing backslash
    if (body.charAt(len-1) == '/')
      throw new SyntaxException("Trailing slash: " + body);
    
    // parse
    String[] temp = new String[64];
    int n = 0;   
    String name;
    for(int i=start; i<len; ++i)
    {
      int c = body.charAt(i);
      if (c == '/')
      {
        if (i == start)
          throw new SyntaxException("Illegal double slashes: " + body);
        name = body.substring(start, i);
        if (name.startsWith("..")) throw new SyntaxException("Illegal backup in path: " + body);
        temp[n++] = name;
        start = i+1;
      }
      else if (!isName(c))
      {
        throw new SyntaxException("Illegal char '" + (char)c + "' in body: " + body);
      } 
    }               
    name = body.substring(start, len);
    if (name.startsWith("..")) throw new SyntaxException("Illegal backup in path: " + body);
    temp[n++] = name;
    
    // trim and store
    names = new String[n];
    System.arraycopy(temp, 0, names, 0, n);    
  }
  
  /**
   * If the character at index is a slash then return
   * index+1.  If index is past the end then return index.
   */
  int skipSlash(String body, int index)
  {
    if (index >= body.length()) return index;
    if (body.charAt(index) == '/') return index+1;
    return index;
  }
  
  /**
   * Verify authority name.
   */
  void checkAuthority(String authority)
  {
    if (authority.isEmpty())
        throw new SyntaxException("Missing authority name: " + body);
        
    for(int i=0; i<authority.length(); ++i)
      if (!isAuthority(authority.charAt(i)))
        throw new SyntaxException("Illegal char '" + authority.charAt(i) + "' in authority: " + body);
  }
  
////////////////////////////////////////////////////////////////
// Object
////////////////////////////////////////////////////////////////
  
  /**
   * Returns a hash code value for the file path string. Should not
   * be used to uniquely identify the path's target file.
   */
  public int hashCode()
  {
    return (scheme + ':' + body).hashCode();
  }

  /**
   * Provide a textual comparison of the file path returning true
   * if the provided FilePath matches the current object. The returned
   * value does not indicate whether the two paths resolve to the 
   * same file.
   */  
  public boolean equals(Object other)
  {
    if(!(other instanceof FilePath))
      return false;
    
    if(!body.equals(((FilePath)other).body))
      return false;
    if(!scheme.equals(((FilePath)other).scheme))
      return false;
    
    return true;
  }
  
////////////////////////////////////////////////////////////////
// Comparable
////////////////////////////////////////////////////////////////

  /**
   * Provide a textual comparison of the file path.  Compares this 
   * object with the specified object for order. Returns a negative 
   * integer, zero, or a positive integer as this object is less than, 
   * equal to, or greater than the specified object. 
   */
  @Override
  public int compareTo(FilePath other)
  {
    int result = scheme.compareTo(other.scheme);
    if (result != 0)
    {
      return result;
    }
    // Using a case-insensitive comparison first will improve the sort order but risk
    // efficiency. If you don't care about sort you should be using equals() instead...
    result = body.compareToIgnoreCase(other.body);
    if (result != 0)
    {
      return result;
    }
    // At this point, they are either the same or they differ only by case.
    return body.compareTo(other.body);
  }
  
////////////////////////////////////////////////////////////////
// Character Map
////////////////////////////////////////////////////////////////

  static boolean isName(int c)
  { 
    return !(
        c<=31 || // control characters
        c==127 || // del
        c=='\"' || c=='\\' ||
        c=='<' || c=='>' ||
        c=='?' ||
        c=='*' ||
        c=='/' ||
        c=='|'
    );    
  }
  
  static boolean isAuthority(int c)
  {
    return (
        (c>='a' && c<='z') ||
        (c>='A' && c<='Z') ||
        (c>='0' && c<='9') ||
        c=='_' ||
        c=='.' ||
        c=='-'
    );    
  }  
  
////////////////////////////////////////////////////////////////
// Absolute/Relative Modes
////////////////////////////////////////////////////////////////

  public static final int RELATIVE              = 0;
  public static final int AUTHORITY_ABSOLUTE    = 1;
  public static final int LOCAL_ABSOLUTE        = 2;
  public static final int SYS_HOME_ABSOLUTE     = 3;
  public static final int STATION_HOME_ABSOLUTE = 4;
  public static final int USER_HOME_ABSOLUTE    = 5;
  public static final int PROTECTED_STATION_HOME_ABSOLUTE = 6;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private static final String[] NO_NAMES = new String[0];

  private final String scheme;
  private String body;
  private int absMode;
  private String authority;
  private int backupDepth;
  private String[] names = NO_NAMES;
  private String fragment;
    
}

