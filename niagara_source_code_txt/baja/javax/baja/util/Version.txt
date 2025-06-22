/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Encapsulation of a dewey decimal version string.
 * This version string must be a sequence of positive 
 * decimal integers separated by "."'s and may have 
 * leading zeros (per java.lang.Package specification).
 *
 * @author Brian Frank on 24 May 00
 * @since Niagara 3.0
 */
public class Version
  implements Comparable<Version>
{ 

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Create a version using given version nodes
   * 
   * @since Niagara 3.5
   */
  public Version(int[] versions)
  {
    this.versions = new int[versions.length];
    System.arraycopy(versions, 0, this.versions, 0, versions.length);
  }

  /**
   * Parse a version string. 
   *
   * @throws IllegalArgumentException is version
   *    string is incorrectly formatted.
   */
  public Version(String s)
  {
    try
    {
      int[] buf = new int[16];
      int c = 0;
            
      StringTokenizer st = new StringTokenizer(s, ".");
      while(st.hasMoreTokens())
      {
        int x = Integer.parseInt(st.nextToken());
        if (x < 0)
          throw new IllegalArgumentException();
        buf[c++] = x;
      }
      
      versions = new int[c];
      System.arraycopy(buf, 0, versions, 0, c);
    }
    catch(Exception e)
    {
      throw new IllegalArgumentException("Invalid version string \"" + s + "\"");
    }
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Get version at index 0 or -1
   */                      
  public int major()
  {
    if (versions.length < 1) return -1;
    return versions[0];
  }

  /**
   * Get version at index 1 or -1
   */                      
  public int minor()
  {
    if (versions.length < 2) return -1;
    return versions[1];
  }

  /**
   * Get version at index 2 or -1
   */                      
  public int build()
  {
    if (versions.length < 3) return -1;
    return versions[2];
  }

  /**
   * Get version at index 3 or -1
   */                      
  public int patch()
  {
    if (versions.length < 4) return -1;
    return versions[3];
  }

  /**
   * Get the version at index.
   */
  public int get(int index)
  {
    return versions[index];
  }

  /**
   * Get the number of numbers in this Version.
   */
  public int size()
  {
    return versions.length;
  }

  /**
   * The null version is "0".
   */
  public boolean isNull()
  {
    if (versions.length == 1) return versions[0] == 0;
    return false;
  }

  /**
   * Return a negative integer, zero, or a positive 
   * integer as this Version is less than, equal to, 
   * or greater than the specified Version.  If the two 
   * versions are equal in the number of digits they 
   * contain, but one version has additional digits then 
   * that one is considered greater (ie, {@code 1.0.1 > 1.0}).
   */
  @Override
  public int compareTo(Version ver)
  {
    Version v = ver;
    
    // check digits we have
    int len = versions.length;
    int vLen = v.versions.length;
    for(int i=0; i<len && i<vLen; ++i)
    {
      if (versions[i] > v.versions[i]) return 1;
      if (versions[i] < v.versions[i]) return -1;
    }
    
    // if equal lengths they are equal
    if (len == vLen) return 0;
    if (len > vLen) return 1;
    return -1;        
  }
    
  /**
   * Return true if the versions equal.
   */
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }

    if (this == obj)
    {
      return true;
    }

    if (obj instanceof Version)
    {
      Version v = (Version)obj;
      
      if (versions.length != v.versions.length)
        return false;
        
      for(int i=0; i<versions.length; ++i)
        if (versions[i] != v.versions[i])
          return false;
       
      return true;
    }
    return false;
  }
  
  /**
   * Hash code
   */  
  public int hashCode()
  {
    int hash = 0;
    for(int i=0; i<versions.length; ++i)
      hash = (hash += versions[i]) << 8;
    hash += versions.length;
    return hash;
  }

  /**
   * To string.
   */
  public String toString()
  {
    return toString(versions.length);
  }

  /**
   * To string using the first 'len' versions.
   */
  public String toString(int len)
  {
    StringBuilder s = new StringBuilder();
    for (int i = 0; i < len && i < versions.length; ++i)
    {
      if (i > 0)
      {
        s.append('.');
      }
      s.append(versions[i]);
    }
    return s.toString();
  }

  /**
   * @since Niagara 4.1
   */
  public Version toMinorVersion()
  {
    return new Version(Arrays.copyOfRange(versions, 0, 2));
  }

  /** Version of "0" */
  public static Version ZERO = new Version("0");

  /** Version of "0" */
  public static Version NULL = ZERO;

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private int[] versions;
}
