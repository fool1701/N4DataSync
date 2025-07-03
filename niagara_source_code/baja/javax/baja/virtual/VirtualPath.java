/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.virtual;

import javax.baja.naming.*;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.*;
import com.tridium.util.EscUtil;

/**
 * VirtualPath is a ord scheme (subclasses SlotPath) for resolving BVirtualComponents
 * using unescaped slot names.  The '/', '|', '$', and ':' characters are NOT 
 * allowed in a virtual path.  Also, the "../" is reserved for backups.
 *
 * Starting in Niagara 4.6, there is a method {@link VirtualPath#toVirtualPathName(String)} used to
 * convert any SlotPath name whose unescaped form may contain unsupported characters ('/', '|', '$',
 * ':', and the '~' escape character itself) and return a proper VirtualPath name with unsupported
 * characters escaped using a "~" hex hex form.  There is also a method
 * {@link VirtualPath#toSlotPathName(String)} used to convert a VirtualPath name back to its
 * original SlotPath name form.
 *
 * The BNF is:
 * <pre>
 *   virtualpath := absolute
 *   absolute    := "/" path
 *   path        := name [ "/" path]
 *   name        := asciiEsc | ascii, '/' '|' '$' and ':' characters NOT allowed ("../" is also reserved for backups)
 *   asciiEsc    := "~" hex hex, only used to escape the '/' '|' '$', ':', and '~' characters
 * </pre>
 *
 * @author    Scott Hoye
 * @creation  20 Nov 06
 * @version   $Revision: 7$ $Date: 9/24/10 5:10:42 AM EDT$
 * @since     Niagara 3.2
 */
public final class VirtualPath
  extends SlotPath
{
////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct an VirtualPath with the specified scheme and body.
   *
   * @throws SyntaxException if the body isn't a valid file path.
   */
  public VirtualPath(String scheme, String body)
    throws SyntaxException
  {
    super(scheme, body);
  }

  /**
   * Construct an VirtualPath with the specified scheme and names.
   */
  public VirtualPath(String scheme, String[] names)
    throws SyntaxException
  {
    super(scheme, names);
  }

  /**
   * Convenience with "virtual" scheme.
   */
  public VirtualPath(String body)
    throws SyntaxException
  {
    super("virtual", body);
  }

////////////////////////////////////////////////////////////////
// Conversion
////////////////////////////////////////////////////////////////  

  /**
   * The conversion from a SlotPath is basically just to
   * unescape all of the path names and escape any unsupported
   * characters ('/', '|', '$', ':', and '~').
   */
  public static VirtualPath convertFromSlotPath(SlotPath slotPath)
  {
    String[] escapedNames = slotPath.getNames();
    int len = escapedNames.length;
    String[] unescapedNames = new String[len];
    for (int i = 0; i < len; i++)
      unescapedNames[i] = toVirtualPathName(escapedNames[i]);
    if (slotPath.isAbsolute())
    { // absolute case
      return new VirtualPath("virtual", unescapedNames);
    }
    else
    { // relative case, so check for backups
      int backup = slotPath.getBackupDepth();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < backup; i++)
        sb.append("../");
      
      for (int i = 0; i < len; i++)
      {
        if (i != 0) sb.append('/');
        sb.append(unescapedNames[i]);
      }
      return new VirtualPath(sb.toString());
    }
  }

////////////////////////////////////////////////////////////////
// SlotPath Overrides
////////////////////////////////////////////////////////////////

  /**
   * Creates a new SlotPath instance for the given scheme and body.
   * Overridden to return a new VirtualPath instance.
   */
  @Override
  protected SlotPath makeSlotPath(String scheme, String body)
  {
    return new VirtualPath(scheme, body);
  }

  /**
   * @since Niagara 4.3U1
   */
  @Override
  public OrdQuery makePath(String body)
  {
    return new VirtualPath(body);
  }

  /**
   * Does the specified string contain a valid name.
   * Overridden here to check for invalid characters
   * in a virtual path.
   * The '/', '|', '$', and ':' characters are NOT 
   * allowed in a virtual path.
   */
  @Override
  protected boolean isValidPathName(String name)
  {   
    return VirtualPath.isValidName(name);
  }
  
  /**
   * Does the specified string contain a valid name.
   */
  public static boolean isValidName(String name)
  {   
    // The name must not be null
    if (name == null) return false;
    
    int len = name.length();
    if (len < 1) return false; // The name must not be empty (blank)
      
    // The '/', '|', '$', and ':' characters are NOT 
    // allowed in a virtual path.
    for (int i = 0; i < len; i++)
    {
      char c = name.charAt(i);
      switch(c)
      {
        case '/':
        case '|':
        case '$':
        case ':': return false;
      }
    }
    return true;
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
    throw new UnsupportedOperationException("escape() not implemented for VirtualPath. Use toVirtualPathName() instead.");
  }

  /**
   * Unescape the specified string.
   */
  public static String unescape(String s)
  {                            
    throw new UnsupportedOperationException("unescape() not implemented for VirtualPath. Use toSlotPathName() instead.");
  }
    
  /**
   * Return the body.
   */
  @Override
  public String toDisplayString()
  { 
    return getBody();
  }


////////////////////////////////////////////////////////////////
// Conversion of Slot/Virtual Path Names
////////////////////////////////////////////////////////////////

  /**
   * Converts the given SlotPath name to a valid VirtualPath name.  The SlotPath name argument
   * must be in its proper slot escaped form - don't call SlotPath.unescape() on the SlotPath name
   * argument before calling this method.  This method will unescape the given SlotPath name and
   * look for any unsupported virtual characters ('/', '|', '$', ':', and the '~' escape character
   * itself) and escape those using the '~' escape character.
   *
   * @param slotPathName The SlotPath name to be converted to a valid VirtualPath name.  This
   *                     argument must be in its proper slot escaped form - don't call
   *                     SlotPath.unescape() on this SlotPath name argument before passing it to
   *                     this method.
   * @return A valid VirtualPath name where any unsupported virtual characters have been escaped
   *         using the '~' escape character.
   *
   * @since Niagara 4.6
   */
  public static String toVirtualPathName(String slotPathName)
  { // Note that much of this code is copied/modified from SlotPath.java's unescape() method
    // because I wanted to make sure the processing happens in a *single* pass through the
    // characters of the slotPathName String. However, there are some key differences that made it
    // difficult to pull out the common code, so it was cleaner to just copy/modify.

    // lazy allocate buffer
    char[] buf = null;
    int pos = 0;

    // walk the string
    int len = slotPathName.length();
    for(int i=0; i<len; ++i)
    {
      char c = slotPathName.charAt(i);
      if (c != '$')
      {
        if (buf != null)
        {
          buf[pos++] = c;
        }
        continue;
      }

      // create char buffer lazily
      if (buf == null)
      {
        buf = new char[len]; // can't be longer than len
        slotPathName.getChars(0, i, buf, 0);
        pos = i;
      }

      // is this 2 digit or 4 digit hex
      char c1 = slotPathName.charAt(++i);

      // perform 2 digit
      if (c1 != 'u')
      {
        char c2 = slotPathName.charAt(++i);
        char unescapedChar = (char)(TextUtil.hexCharToInt(c1) << 4 | TextUtil.hexCharToInt(c2));
        switch(unescapedChar)
        {
          case '/':
          case '|':
          case '$':
          case ':':
          case ESCAPE_CHAR:
            buf[pos++] = ESCAPE_CHAR;
            buf[pos++] = c1;
            buf[pos++] = c2;
            break;
          default:
            buf[pos++] = unescapedChar;
            break;
        }
      }

      // perform 4 digit
      else
      {
        buf[pos++] = (char)(TextUtil.hexCharToInt(slotPathName.charAt(++i)) << 12 |
                            TextUtil.hexCharToInt(slotPathName.charAt(++i)) << 8 |
                            TextUtil.hexCharToInt(slotPathName.charAt(++i)) << 4 |
                            TextUtil.hexCharToInt(slotPathName.charAt(++i)));
      }
    }

    return buf == null?slotPathName:new String(buf, 0, pos);
  }

  /**
   * Converts the given VirtualPath name to a valid SlotPath name. This method will unescape any
   * unsupported virtual characters ('/', '|', '$', ':', and the '~' escape character itself) in the
   * given VirtualPath name that were previously escaped using the '~' escape character.  It will
   * also ensure that the result is in proper SlotPath escaped form before returning it.
   *
   * In order to support legacy uses of the '~' escape character in pre-4.6 VirtualPath names,
   * this method will only consider '~' as an escape character if it is followed by the code for
   * one of the unsupported virtual characters ('/', '|', '$', ':', and '~') in the given
   * VirtualPath name.
   *
   * @param virtualPathName The VirtualPath name to be converted to a valid SlotPath name.
   * @return A valid SlotPath name.
   *
   * @since Niagara 4.6
   */
  public static String toSlotPathName(String virtualPathName)
  { // Note that much of this code is copied/modified from SlotPath.java's escape() method
    // because I wanted to make sure the processing happens in a *single* pass through the
    // characters of the virtualPathName String. However, there are some key differences that made
    // it difficult to pull out the common code, so it was cleaner to just copy/modify.

    int len = virtualPathName.length();
    if (len == 0)
    {
      return virtualPathName;
    }

    // lazy allocate the buffer
    char[] buf = null;
    int pos = 0;
    int maxLen = len*6; // if every char was unicode escaped

    char c = virtualPathName.charAt(0);

    int offset = 0;
    if (c == ESCAPE_CHAR)
    {
      // it is a 2 digit hex
      char unescapedChar = 0;
      try
      {
        unescapedChar = (char)(TextUtil.hexCharToInt(virtualPathName.charAt(++offset)) << 4 |
                               TextUtil.hexCharToInt(virtualPathName.charAt(++offset)));
      }
      catch(Exception ignore) { } // An Exception here is an indication of a legacy name

      switch(unescapedChar)
      { // Make sure that the unescapedChar is one of the unsupported chars. If not, then this must
        // be a legacy name, so let it pass.
        case '/':
        case '|':
        case '$':
        case ':':
        case ESCAPE_CHAR:
          c = unescapedChar; // It checks out, so swap c for the unescapedChar
          buf = new char[maxLen]; // Also, we always need to use the buf if we unescape anything
          break;
        default:
          // The unescaped char wasn't one of the expected ones, indicating it must be a legacy
          // name, so skip the unescape processing
          offset = 0;
          break;
      }
    }

    if (!EscUtil.slot.isStart(c))
    {
      if (buf == null)
      {
        buf = new char[maxLen];
      }
      pos = EscUtil.escape(c, buf, pos);
    }
    else if (buf != null)
    {
      buf[pos++] = c;
    }

    for(int i=1+offset; i<len; ++i)
    {
      c = virtualPathName.charAt(i);
      if (c == ESCAPE_CHAR)
      {
        // it is a 2 digit hex
        char unescapedChar = 0;
        int idx = i;
        try
        {
          unescapedChar = (char)(TextUtil.hexCharToInt(virtualPathName.charAt(++idx)) << 4 |
                                 TextUtil.hexCharToInt(virtualPathName.charAt(++idx)));
        }
        catch(Exception ignore) { } // An Exception here is an indication of a legacy name

        switch(unescapedChar)
        { // Make sure that the unescapedChar is one of the unsupported chars. If not, then this
          // must be a legacy name, so let it pass.
          case '/':
          case '|':
          case '$':
          case ':':
          case ESCAPE_CHAR:
            c = unescapedChar; // It checks out, so swap c for the unescapedChar
            if (buf == null)
            { // If we encounter a virtual escape char, we always need to use the buf
              buf = new char[maxLen];
              virtualPathName.getChars(0, i, buf, 0);
              pos = i;
            }
            i += 2;
            break;
          default: // The unescaped char wasn't one of the expected ones, indicating it must be a legacy
                   // name, so skip the unescape processing
        }
      }

      if (EscUtil.slot.isPart(c))
      {
        if (buf != null)
        {
          buf[pos++] = c;
        }
      }
      else
      {
        if (buf == null)
        {
          buf = new char[maxLen];
          virtualPathName.getChars(0, i, buf, 0);
          pos = i;
        }
        pos = EscUtil.escape(c, buf, pos);
      }
    }

    return buf == null?virtualPathName:new String(buf, 0, pos);
  }

  private static final char ESCAPE_CHAR = '~';

  /**
   * A VirtualPath constant with an empty body String
   *
   * @since Niagara 4.13
   */
  public static final VirtualPath EMPTY_VIRTUAL_PATH = new VirtualPath("");

}
