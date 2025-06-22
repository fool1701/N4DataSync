/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.nio.charset.StandardCharsets;

/**
 * Text utilities.
 *
 * @author    Brian Frank
 * @creation  17 Mar 00
 * @version   $Revision: 51$ $Date: 8/13/09 11:00:33 AM EDT$
 * @since     Baja 1.0
 */
public class TextUtil
{

////////////////////////////////////////////////////////////////
// Byte Conversions
////////////////////////////////////////////////////////////////

  /**
   * Convert the passed in String to a UTF-8 encoded byte array.
   * 
   * @param string the string to be converted
   * @return a byte array representation
   */
  public static byte[] stringToBytes(String string)
  {
    return string.getBytes(StandardCharsets.UTF_8);
  }

  /**
   * @return byte to decimal string.
   */
  public static String byteToString(int b)
  {
    return Integer.toString(b);
  }
  
  public static String bytesToHexString(byte [] bs)
  {
    if (bs == null) return "";
    
    StringBuilder workingBuffer = new StringBuilder();
    for (byte b : bs)
    {
      workingBuffer.append(byteToHexString(b));
    }
    
    return workingBuffer.toString();
  }

  /**
   * @return byte to two character hex string.
   */
  public static String byteToHexString(int b)
  {
    return intToHexString(b&0xff, 2);
  }

  /**
   * Translate the byte into an ASCII char if it
   * is printable.  If not return the given
   * unprintable char.
   */
  public static char byteToChar(int b, char unprintable)
  {
    if ((b < 32) || (b > 126))
    {
      return unprintable;
    }
    else
    {
      return (char)b;
    }
  }

////////////////////////////////////////////////////////////////
// Numeric conversions
////////////////////////////////////////////////////////////////
  
  /**
   * @return int to eight character hex string
   * 
   * @since Niagara 3.4
   */
  public static String intToHexString(int i)
  {
    return padZeros(Integer.toHexString(i), 8);
  }
  
  /**
   * @return int to hex string of given length.
   * 
   * @since Niagara 3.4
   */
  public static String intToHexString(int i, int len)
  {
    return padZeros(Integer.toHexString(i), len);
  }

////////////////////////////////////////////////////////////////
// Character Conversions
////////////////////////////////////////////////////////////////

  /**
   * Convert a character to a digit.  For example
   * the char '3' will return 3.
   */
  public static int charToInt(char c)
  {
    int x = c - '0';
    if (x < 0 || x > 9)
    {
      throw new IllegalArgumentException("'" + c + "'");
    }
    return x;
  }

  /**
   * Convert the given hex character to a digit.
   * For instance {@code '0' -> 0, 'A' -> 10, 'f' -> 15}.
   */
  public static int hexCharToInt(char c)
  {
    int x = c - '0';
    if (x >= 0 && x <= 9) return x;
    x = c - 'A' + 10;
    if (x >= 10 && x <= 15) return x;
    x = c - 'a' + 10;
    if (x >= 10 && x <= 15) return x;
    throw new IllegalArgumentException("'" + c + "'");
  }

  /**
   * check if all the characters in a given string
   * are hexidecimal, i.e [0-9], [A-F]
   */
  public static boolean isHex(String str)
  {
    for (int i = 0; i < str.length(); i++)
    {
      char c = str.charAt(i);

      if (!(((c >= '0') && (c <= '9')) ||
            ((c >= 'A') && (c <= 'F')) ||
            ((c >= 'a') && (c <= 'f'))))
      {
        return false;
      }
    }
    return true;
  }

////////////////////////////////////////////////////////////////
// Padding
////////////////////////////////////////////////////////////////

  /**
   * Get a string which is a number of spaces.
   */
  public static String getSpaces(int num)
  {
    try
    {
      // 99.9% of the time num is going to be
      // smaller than 50, so just try it
      return SPACES[num];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      if (num < 0)
      {
        return "";
      }

      // too big!
      int len = SPACES.length;
      StringBuilder buf;
      buf  = new StringBuilder(num);
      int rem = num;
      while(true)
      {
        if (rem < len)
        {
          buf.append(SPACES[rem]);
          break;
        }
        else
        {
          buf.append(SPACES[len-1]);
          rem -= len-1;
        }
      }
      return buf.toString();
    }
  }

  private static String[] SPACES;
  static
  {
    SPACES = new String[50];
    SPACES[0] = "";
    for(int i=1; i<50; ++i)
    {
      SPACES[i] = SPACES[i - 1] + " ";
    }
  }

  /**
   * Pad to the right.
   * @see #padRight(String, int)
   */
  public static String pad(String s, int width)
  {
    return padRight(s, width);
  }

  /**
   * Pad the given string with spaces to the right
   * so that it is the given width.  If s.length()
   * is greater than width, s is returned.
   */
  public static String padRight(String s, int width)
  {
    if (s.length() >= width) return s;
    return s + getSpaces(width - s.length());
  }

  /**
   * Pad the given string with spaces to the left
   * so that it is the given width.  If s.length()
   * is greater than width, s is returned.
   */
  public static String padLeft(String s, int width)
  {
    if (s.length() >= width) return s;
    return getSpaces(width - s.length()) + s;
  }

  /**
   * Ensure that the given string is no more than 'max' characters long.
   */
  public static String truncate(String str, int max)
  {
    return (str.length() > max) ?  str.substring(0, max) : str;
  }

  /**
   * Pad the given string with zeros to the left
   * so that it is the given width.  If s.length()
   * is greater than width, s is returned.
   */
  public static String padZeros(String s, int width)
  {
    if (s.length() >= width) return s;
    return getZeros(width - s.length()) + s;
  }
  
  /**
   * Get a string which is a number of zeros.
   * 
   * @since Niagara 3.4
   */
  public static String getZeros(int num)
  {
    try
    {
      // 99.9% of the time num is going to be
      // smaller than 16, so just try it
      return ZEROS[num];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      if (num < 0)
      {
        return "";
      }

      // too big!
      int len = ZEROS.length;
      StringBuilder buf;
      buf  = new StringBuilder(num);
      int rem = num;
      while(true)
      {
        if (rem < len)
        {
          buf.append(ZEROS[rem]);
          break;
        }
        else
        {
          buf.append(ZEROS[len-1]);
          rem -= len-1;
        }
      }
      return buf.toString();
    }
  }
  private static String[] ZEROS;
  static
  {
    ZEROS = new String[16];
    ZEROS[0] = "";
    for(int i=1; i<16; ++i)
    {
      ZEROS[i] = ZEROS[i - 1] + "0";
    }
  }
  
////////////////////////////////////////////////////////////////
// Case Conversions
////////////////////////////////////////////////////////////////
  
  /**
   * Do an ASCII only upper case conversion.  Case conversion 
   * with Locale can result in unexpected side effects.
   */
  public static char toUpperCase(char c)
  {                  
    if ('a' <= c && c <= 'z')
    {
      return (char)(c & ~0x20);
    }
    else
    {
      return c;
    }
  }

  /**
   * Do an ASCII only lower case conversion.  Case conversion 
   * with Locale can result in unexpected side effects.
   */
  public static char toLowerCase(char c)
  {
    if ('A' <= c && c <= 'Z')
    {
      return (char)(c | 0x20);
    }
    else
    {
      return c;
    }
  }

  /**
   * Do an ASCII only upper case conversion.  Case conversion 
   * with Locale can result in unexpected side effects.
   */
  public static String toUpperCase(String s)
  {                                        
    // first scan to see if string isn't already ok 
    int len = s.length();
    int first = -1;
    for(int i=0; i<len; ++i)
    {
      char a = s.charAt(i);
      char b = toUpperCase(a);
      if (a != b) { first = i; break; }
    }                                  
    if (first == -1) return s;
    
    // allocate new char buf and copy up to first change
    char[] buf = new char[len];
    s.getChars(0, first, buf, 0);
    
    // change remainder of string
    for(int i=first; i<len; ++i)
    {
      buf[i] = toUpperCase(s.charAt(i));
    }
      
    return new String(buf);
  }

  /**
   * Do an ASCII only lower case conversion.  Case conversion 
   * with Locale can result in unexpected side effects.
   */
  public static String toLowerCase(String s)
  {                                        
    // first scan to see if string isn't already ok 
    int len = s.length();
    int first = -1;
    for(int i=0; i<len; ++i)
    {
      char a = s.charAt(i);
      char b = toLowerCase(a);
      if (a != b) { first = i; break; }
    }                                  
    if (first == -1) return s;
    
    // allocate new char buf and copy up to first change
    char[] buf = new char[len];
    s.getChars(0, first, buf, 0);
    
    // change remainder of string
    for(int i=first; i<len; ++i)
    {
      buf[i] = toLowerCase(s.charAt(i));
    }
      
    return new String(buf);
  }

  /**
   * Capitalize a string using java bean
   * conventions.  For instance "fooBar" becomes
   * "FooBar", for use such as "getFooBar".
   */
  public static String capitalize(String s)
  {
    char[] c = s.toCharArray();
    c[0] = toUpperCase( c[0] );
    return new String(c);
  }

  /**
   * Decapitalize a string using java bean
   * conventions.  For instance "FooBar" becomes
   * "fooBar".
   */
  public static String decapitalize(String s)
  {
    char[] c = s.toCharArray();
    c[0] = toLowerCase( c[0] );
    return new String(c);
  }

////////////////////////////////////////////////////////////////
// Names
////////////////////////////////////////////////////////////////

  /**
   * Translate a programatic name to a friendly
   * name.  This is done based on standard identifier
   * capitalization.  So the string "fooBar" would
   * be translated as "Foo Bar".
   */
  public static String toFriendly(String s)
  {
    StringBuilder buf = new StringBuilder();
    buf.append( Character.toUpperCase(s.charAt(0)) );

    int len = s.length();
    for(int i=1; i<len; ++i)
    {
      char c = s.charAt(i);
      if ((c & 0x20) == 0 && i > 0)
      {
        buf.append(' ').append(c);
      }
      else
      {
        buf.append(c);
      }
    }
    return buf.toString();
  }

  /**
   * Translate a friendly string back into its
   * programatic name.
   */
  public static String fromFriendly(String s)
  {
    StringBuilder buf = new StringBuilder( s.length() );
    buf.append( Character.toLowerCase(s.charAt(0)) );

    int len = s.length();
    int i = 1;
    for(; i<len; ++i)
    {
      char c = s.charAt(i);
      if (c == ' ') break;
      buf.append(c);
    }

    for(; i<len; ++i)
    {
      char c = s.charAt(i);
      if (c != ' ')
      {
        buf.append(c);
      }
    }

    return buf.toString();
  }

////////////////////////////////////////////////////////////////
// Class
////////////////////////////////////////////////////////////////

  /**
   * Get the simple classname without the package name.
   */
  public static String getClassName(Class<?> cls)
  {
    return getClassName(cls.getName());
  }

  /**
   * Get the simple classname without the package name,
   * and if an array type, then strip off the trailing
   * semicolon.
   */
  public static String getClassName(String className)
  {
    // strip off everything before last dot
    int x = className.lastIndexOf('.');
    if (x >= 0) className = className.substring(x+1);

    // strip off semicolon
    if (className.charAt(className.length()-1) == ';')
    {
      className = className.substring(0, className.length() - 1);
    }

    return className;
  }

  /**
   * Get the package name.
   */
  public static String getPackageName(Class<?> cls)
  {
    return getPackageName(cls.getName());
  }

  /**
   * Get the package name from a fully qualified classname.
   *
   * @return null if no package is specified.
   */
  public static String getPackageName(String className)
  {
    int x = className.lastIndexOf('.');
    if (x < 0) return null;
    else return className.substring(0, x);
  }

////////////////////////////////////////////////////////////////
// Markup
////////////////////////////////////////////////////////////////

  /**
   * Strip all the XML or HTML markup out of a
   * piece of text and return the plain text.
   */
  public static String stripMarkup(String text)
  {
    StringBuilder s = new StringBuilder();
    char[] buf = text.toCharArray();
    for(int i=0; i<buf.length; ++i)
    {
      char c = buf[i];
      if (c != '<') { s.append(c); continue; }

      // find matching brace
      int end = -1;
      for(int j=i+1; j<buf.length; ++j)
      {
        if (buf[j] == '>')
        {
          end = j;
          break;
        }
      }

      // if no matching end brace, then
      // don't treat this as markup
      if (end == -1) continue;

      // otherwise, skip all the characters
      i = end;
    }
    return s.toString();
  }

/////////////////////////////////////////////////////////////////
// perl-like stuff
/////////////////////////////////////////////////////////////////

  /**
   * Parse a string into an array, using a given delimiter.
   */
  public static String[] split(String str, char delim)
  {                  
    if (str.indexOf(delim) == -1) 
    {
      if (str.length() == 0) return new String[0];
      else return new String[] { str };
    }

    String[] list = new String[8];

    int a = 0;
    int b = 0;
    int n = 0;
    while (b < str.length())
    {
      if (str.charAt(b) == delim)
      {
        list = ensureCapacity(list, n);
        list[n++] = str.substring(a, b);
        a = ++b;
      }
      else
      {
        b++;
      }
    }
    list = ensureCapacity(list, n);
    list[n++] = str.substring(a, str.length());

    if (n == list.length)
    {
      return list;
    }
    else
    {
      String[] trim = new String[n];
      System.arraycopy(list, 0, trim, 0, n);
      return trim;
    }
  }

  /**
   * Parse a string into an array, using a given delimiter, and
   * trim the whitespace from ends each string.
   */
  public static String[] splitAndTrim(String str, char delim)
  {
    return trim(split(str, delim));
  }

  /**
   * Ensure the given string has the specified capacity. If
   * so then return x, otherwise return a bigger String array
   * with the existing contents.
   */
  public static String[] ensureCapacity(String[] x, int len)
  {
    if (len < x.length) return x;
    String[] expand = new String[x.length*2];
    System.arraycopy(x, 0, expand, 0, x.length);
    return expand;
  }

  /**
   * Trim all the Strings in the specified array.
   */
  public static String[] trim(String[] list)
  {
    if (list == null) return null;
    for(int i=0; i<list.length; ++i)
    {
      if (list[i] != null)
      {
        list[i] = list[i].trim();
      }
    }
    return list;
  }

  /**
   * Join an array into a string, using a given delimiter.
   */
  public static String join(String[] v, char delim)
  {
    if (v.length == 0) return "";

    StringBuilder sb = new StringBuilder();

    int n = v.length - 1;
    for (int i = 0; i < n; i++)
    {
      sb.append(v[i]).append(delim);
    }
    sb.append(v[n]);

    return sb.toString();
  }

  /**
   * Trim the whitespace from the beginning of a string.
   */
  public static String trimLeft(String s)
  {
    StringBuilder sb = new StringBuilder(s);

    while ((sb.length() > 0) && (Character.isWhitespace(sb.charAt(0))))
    {
      sb.deleteCharAt(0);
    }

    return sb.toString();
  }

  /**
   * Trim the whitespace from the end of a string.
   */
  public static String trimRight(String s)
  {
    StringBuilder sb = new StringBuilder(s);

    int len = sb.length();
    while ((len > 0) && (Character.isWhitespace(sb.charAt(len - 1))))
    {
      sb.deleteCharAt(len - 1);
      len--;
    }

    return sb.toString();
  }

  /**
   * Replace, in the given text, all occurrences of the old string
   * with the new string.
   *
   * @throws NullPointerException - if text, oldStr or newStr are null.
   */
  public static String replace(String text, String oldStr, String newStr)
  {
    if (text == null) throw new NullPointerException();
    if (oldStr == null) throw new NullPointerException();
    if (newStr == null) throw new NullPointerException();

    int b = text.indexOf(oldStr, 0);
    if (b == -1) return text;

    return doReplace(new StringBuffer(text), oldStr, newStr, b).toString();
  }

  /**
   * Replace, in the given text buffer, all occurrences of the old string
   * with the new string.
   *
   * @throws NullPointerException - if text, oldStr or newStr are null.
   */
  public static StringBuffer replace(StringBuffer text, String oldStr, String newStr)
  {
    if (text == null) throw new NullPointerException();
    if (oldStr == null) throw new NullPointerException();
    if (newStr == null) throw new NullPointerException();

    int b = indexOf(text, oldStr, 0);
    if (b == -1) return text;

    return doReplace(text, oldStr, newStr, b);
  }

  /**
   * doReplace
   */
  private static StringBuffer doReplace(
    StringBuffer text, String oldStr, String newStr, int b)
  {
    int n1 = oldStr.length();
    int n2 = newStr.length();
    while (b != -1)
    {
      text.replace(b, b+n1, newStr);
      b = indexOf(text, oldStr, b+n2);
    }

    return text;
  }

  /**
   * Returns the index within the StringBuffer of the first occurrence 
   * of the specified substring. 
   *
   * @return if the string argument occurs as a substring within the 
   * StringBuffer, then the index of the first character of the first 
   * such substring is returned; If it does not occur as a substring, 
   * -1 is returned.
   * 
   * @throws NullPointerException - if buffer or str is null.
   */
  public static int indexOf(StringBuffer buffer, String pattern)
  {
    return indexOf(buffer, pattern, 0);
  }

  /**
   * Returns the index within the StringBuffer of the first occurrence 
   * of the specified substring, starting at the specified index. 
   * <p>
   * There is no restriction on the value of fromIndex. If it is negative, 
   * it has the same effect as if it were zero: this entire string may be searched. 
   * If it is greater than the length of this string, it has the same effect as if 
   * it were equal to the length of this string: -1 is returned.  
   *
   * @return if the string argument occurs as a substring within the 
   * StringBuffer at a starting index no smaller than fromIndex, 
   * then the index of the first character of the first 
   * such substring is returned; If it does not occur as a substring
   * starting at fromIndex or beyond, -1 is returned.
   * 
   * @throws NullPointerException - if buffer or str is null.
   */
  public static int indexOf(StringBuffer buffer, String pattern, int fromIndex)
  {
    if (buffer == null) throw new NullPointerException();
    if (pattern == null) throw new NullPointerException();

    // via Knuth-Morris-Pratt algorithm.

    int[] overlap = computeOverlap(pattern);    

    int j = 0;
    int n = buffer.length();
    int m = pattern.length();
    int z = (fromIndex < 0) ? 0 : fromIndex;
    for (int i = z; i < n; i++)
    {
      while (true)
      {      
        if (buffer.charAt(i) == pattern.charAt(j))
        {          
          j++;        
          // we found a match
          if (j == m)
          { 
            return i-m+1;
          }
          break;
        } 
        else if (j == 0) break;  
        else j = overlap[j];    
      }
    }

    return -1;
  }

  /**
   * figure out how the pattern overlaps with itself
   */
  private static int[] computeOverlap(String pattern)    
  {
    int m = pattern.length();

    // use reusable array, or malloc if pattern is too long
    int[] overlap = new int[m + 1];    

    // compute the overlap
    overlap[0] = -1;
    for (int i = 0; i < m; i++) 
    {
      overlap[i + 1] = overlap[i] + 1;
      while ((overlap[i + 1] > 0) &&
          (pattern.charAt(i) != pattern.charAt(overlap[i + 1] - 1)))
      {             
        overlap[i + 1] = overlap[overlap[i + 1] - 1] + 1;
      }        
    }

    return overlap;
  }

  /**
   * Remove double quotes surrounding the provided string. Returns the provided
   * string unmodified if it is not surrounded in double quotes.
   *
   * @param value the string to unquote
   * @return the unquoted string, or null if the provided string was null
   * @since Niagara 4.9
   */
  public static String unquote(String value)
  {
    if (value != null && value.length() >= 2 && value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"')
    {
      value = value.substring(1, value.length()-1);
    }
    return value;
  }
}
