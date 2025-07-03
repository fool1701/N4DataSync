/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.history;

import java.util.Vector;

import javax.baja.history.BCapacity;
import javax.baja.history.BFullPolicy;
import javax.baja.history.BHistoryConfig;
import javax.baja.history.BHistoryId;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BConfigRule is used to determine the overrides for
 * an existing history configuration.
 *
 * @author    John Sublett
 * @creation  17 Apr 2003
 * @version   $Revision: 3$ $Date: 11/3/09 4:41:05 PM EST$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 A pattern to match against the device
 name for incoming histories.
 */
@NiagaraProperty(
  name = "devicePattern",
  type = "String",
  defaultValue = "*"
)
/*
 A pattern to match against the history name
 for incoming histories.
 */
@NiagaraProperty(
  name = "historyNamePattern",
  type = "String",
  defaultValue = "*"
)
public class BConfigRule
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.driver.history.BConfigRule(3550130350)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "devicePattern"

  /**
   * Slot for the {@code devicePattern} property.
   * A pattern to match against the device
   * name for incoming histories.
   * @see #getDevicePattern
   * @see #setDevicePattern
   */
  public static final Property devicePattern = newProperty(0, "*", null);

  /**
   * Get the {@code devicePattern} property.
   * A pattern to match against the device
   * name for incoming histories.
   * @see #devicePattern
   */
  public String getDevicePattern() { return getString(devicePattern); }

  /**
   * Set the {@code devicePattern} property.
   * A pattern to match against the device
   * name for incoming histories.
   * @see #devicePattern
   */
  public void setDevicePattern(String v) { setString(devicePattern, v, null); }

  //endregion Property "devicePattern"

  //region Property "historyNamePattern"

  /**
   * Slot for the {@code historyNamePattern} property.
   * A pattern to match against the history name
   * for incoming histories.
   * @see #getHistoryNamePattern
   * @see #setHistoryNamePattern
   */
  public static final Property historyNamePattern = newProperty(0, "*", null);

  /**
   * Get the {@code historyNamePattern} property.
   * A pattern to match against the history name
   * for incoming histories.
   * @see #historyNamePattern
   */
  public String getHistoryNamePattern() { return getString(historyNamePattern); }

  /**
   * Set the {@code historyNamePattern} property.
   * A pattern to match against the history name
   * for incoming histories.
   * @see #historyNamePattern
   */
  public void setHistoryNamePattern(String v) { setString(historyNamePattern, v, null); }

  //endregion Property "historyNamePattern"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BConfigRule.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public static BConfigRule makeDefault()
  {
    BConfigRule result = new BConfigRule();
    result.add("capacity", BCapacity.UNLIMITED);
    result.add("fullPolicy", BFullPolicy.roll);
    return result;
  }

  /**
   * Does this rule match the specified id.
   */
  public boolean isMatch(BHistoryId id)
  {
    if (dp == null)
      dp = new Pattern(getDevicePattern());
    if (np == null)
      np = new Pattern(getHistoryNamePattern());

    return dp.isMatch(id.getDeviceName()) && np.isMatch(id.getHistoryName());
  }

  public BHistoryConfig makeConfig(BHistoryConfig remoteConfig)
  {
    BHistoryConfig localConfig = (BHistoryConfig)remoteConfig.newCopy(true);
    Property[] ruleProps = loadSlots().getPropertiesArray();
    for (int i = 0; i < ruleProps.length; i++)
    {
      Property prop = ruleProps[i];
      Property configProp = localConfig.loadSlots().getProperty(prop.getName());
      if (configProp != null)
        localConfig.set(configProp, get(prop));
      else
        localConfig.add(prop.getName(), get(prop).newCopy(true), getFlags(prop), getSlotFacets(prop), null);
    }
    
    return localConfig;
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  /**
   * Handle a property change.
   */
  public void changed(Property p, Context c)
  {
    if (p == devicePattern) dp = null;
    if (p == historyNamePattern) np = null;
  }

////////////////////////////////////////////////////////////////
// Pattern
////////////////////////////////////////////////////////////////

  /**
   * Pattern matches a pattern string against any number of
   * test strings.  A match is determined by the rules for
   * "like" matching in SQL except that in addition to
   * the '%' wildcard character, '*' can also be used with
   * the same meaning.  So:
   * <p>
   * % or * will match zero or more characters in the test string<br>
   * _ will match exactly one character in the test string<br>
   * <br>
   * All other characters must match exactly with the test string.
   */
  private static class Pattern
  {
    /**
     * Create Pattern for the specified pattern string.
     */
    public Pattern(String patternString)
    {
      init(patternString.toCharArray());
    }
    
    /**
     * Parse the string into an internal representation of
     * the pattern.  The internal representation of the
     * pattern is a list of character arrays.  Each element
     * of the list represents a distinct part of the pattern.
     * The first character of each element indicates the
     * element type.  The possible types are:
     * <p>
     * <pre>
     * '*': A zero or more character wildcard
     * ' ': A normal element for which an exact match of all
     *      characters is required.  The '_' character is a
     *      special case.  If it appears unescaped in the
     *      pattern string it is replaced by the ONE_CHAR
     *      character in the element.  ONE_CHAR matches
     *      any single character in the test and must match
     *      exactly one character.
     * </pre>
     *
     * %, *, and _ can be escaped in the input by preceding them
     * with a \.  Other supported escape sequences are:
     * <p>
     * <pre>
     * '\n': line break
     * '\r': carriage return
     * '\t': tab
     * '\\': backslash
     * 
     * @param patternStr The LIKE template character array.
     */
    private void init(char[] patternStr)
    {
      // first check to see if the pattern matches everything
      matchAll = true;
      for (int i = 0; i < patternStr.length; i++)
      {
        if ((patternStr[i] != '*') && (patternStr[i] != '%'))
        {
          matchAll = false;
          break;
        }
      }
      if (matchAll) return;
      
      // parse the pattern into a set of character arrays representing
      // substrings of the original pattern.
      Vector<String> v = new Vector<>();
  
      int i=0;
      StringBuilder buf = new StringBuilder(16);
  
      while (true)
      {
        // if the end of the pattern has been reached, add the last
        // segment if there are any characters in it
        if (i==patternStr.length)
        {
          if (buf.length() > 0)
            v.addElement(buf.toString());
          break;
        }

        // check for an escape of a special character
        if (patternStr[i] == '\\')
        {
          if (buf.length() == 0) buf.append(' ');
            
          if (i < patternStr.length - 1)
          {
            char next = patternStr[i+1];
            if ((next == '%') || (next == '*') ||
                (next == '_') || (next == '\\'))
              buf.append(next);
            else if (next == 'n')
              buf.append('\n');
            else if (next == 'r')
              buf.append('\r');
            else if (next == 't')
              buf.append('\t');
            else
              throw new IllegalArgumentException("Invalid escape sequence: \\" + next);
            
            i+=2;
          }
          else
            throw new IllegalArgumentException("Invalid escape sequence: \\");
        }

        // if it's a special character, give it special handling
        else if (patternStr[i]=='%' || patternStr[i] == '*' || patternStr[i]=='_')
        {
          if (patternStr[i] == '_')
          {
            if (buf.length() == 0) buf.append(' ');
            buf.append(ONE_CHAR);
            i++;
          }
          else
          {
            if (buf.length() > 0) v.addElement(buf.toString());
  
            v.addElement("*");
            // multiple "zero or more" wildcards in a row can be
            // coalesced into one
            while((i < patternStr.length) && 
                  ((patternStr[i] == '%') || (patternStr[i] == '*')))
              i++;
            
            buf.setLength(0);
          }
        }
        else
        {
          if (buf.length() == 0) buf.append(' ');
          buf.append(patternStr[i++]);
        }
      }
  
      pattern = new char[v.size()][];
      for (int j=0; j<v.size(); j++)
        pattern[j] = v.elementAt(j).toCharArray();
    }
    
    /**
     * Test the specified string against the pattern.
     */
    public boolean isMatch(String testStr)
    {
      // short circuit if the pattern matches everything
      if (matchAll) return true;
      
      char[] test = testStr.toCharArray();
      int ti=0;
      int lastStart=0;
      int len = test.length;
      boolean afterWildcard = false;
      
      // boundary condition of 0 length test      
      if (len == 0) return pattern.length == 0; // fix issue 8528

      // loop through the pattern matching each element against
      // the test, pi = pattern index, ti = test index
      for (int pi = 0; pi < pattern.length; pi++)
      {
        if (pattern[pi][0] == '*')
        {
          // if I'm at the end of the pattern and it's a wildcard,
          // then it's a match no matter what comes next in the
          // test so return true
          if (pi == pattern.length-1)
            return true;
          
          // otherwise move to the next element in the pattern
          // and start checking the rest of test
          else
          {
            afterWildcard = true;
            continue;
          }
        }

        while (ti<len)
        {
          // find the current pattern element in the test, skip the
          // first character in the element since it just indicates
          // that this is a normal element and not a wildcard
          int start = ti;
          int ei = 1; // element index
          while(((ti < len) && (ei < pattern[pi].length)) &&
                ((pattern[pi][ei] == ONE_CHAR) || (pattern[pi][ei] == test[ti])))
          {
            ti++;
            ei++;
          }
  
          // if the element index equals the length of the current
          // pattern element, then the whole element was matched
          if (ei == pattern[pi].length)
          {
            // if I'm at the end of the pattern, then I better
            // be at the end of the test
            if (pi == pattern.length-1)
            {
              // if I'm at the end of the test, then match
              if (ti == len)
                return true;
              // if this pattern element is after a wildcard
              // then try one more time to see if this pattern
              // element matches the end of the test
              else if (afterWildcard)
              {
                // the actual length of element is (pattern[pi].length - 1)
                // because pattern elements start with a type character
                ti = len - (pattern[pi].length - 1);
                continue;
              }
              // otherwise the match fails
              else
                return false;
            }
            
            // if I'm not at the end of the pattern, then break out
            // and move to the next pattern element
            else
            {
              afterWildcard = false;
              break;
            }
          }
          // if the whole element could not be matched and the end
          // of the test has been reached, then the match fails
          else if (ti==len)
            return false;

          // if I'm not at the end of the test, then a match may
          // still be possible
          else
          {
            // if the previous element was not a wildcard, then
            // the match fails, otherwise try the match again
            // starting at the next character in the test
            if (!afterWildcard)
              return false;
            else
              ti = start + 1;
          }
  
          if (ti==len)
            return false;
        }
      }
  
      return false;
    }

    private char[][] pattern;
    private boolean matchAll;
  }
  
  private static final char ONE_CHAR = '\0';
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private Pattern dp;
  private Pattern np;

}
