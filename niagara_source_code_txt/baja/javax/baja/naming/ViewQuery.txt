/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.baja.nre.util.TextUtil;

/**
 * ViewQuery defines user agent information.
 * <pre>
 *   view       := [viewId] ["?" params] 
 *   params     := param ( ";" param )*
 *   param      := name "=" value
 *   name       := spaces paramToken spaces
 *   value      := paramToken
 *   paramToken := paramChar (paramChar)*
 *   paramChar  := (0x32 - 0x127) except | ; = % ?
 * </pre>
 *
 * @author    Brian Frank on 6 Jan 03
 * @version   $Revision: 10$ $Date: 1/18/06 10:29:27 AM EST$
 * @since     Baja 1.0
 */
public class ViewQuery
  implements OrdQuery
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct an ViewQuery with the specified scheme and body.
   *
   * @throws SyntaxException if the body isn't a valid view body.
   */
  public ViewQuery(String scheme, String body)
    throws SyntaxException
  {
    this.scheme = TextUtil.toLowerCase(scheme).trim();
    this.body = body.trim();
    parse();
  }

  /**
   * Convenience with "view" scheme.
   */
  public ViewQuery(String body)
    throws SyntaxException
  {
    this.scheme = "view";
    this.body = body.trim();
    parse();
  }

////////////////////////////////////////////////////////////////
// View
////////////////////////////////////////////////////////////////

  /**
   * Get the view id string or return null if not specified.
   */
  public String getViewId()
  {
    return viewId;
  }

  /**
   * Get the array of parameter names.
   */
  public String[] getParameterNames()
  {
    if (parameters == null) return NO_NAMES;
    return parameters.keySet().toArray(new String[parameters.size()]);
  }

  /**
   * Get the parameter with the specified key name.
   * @throws IllegalArgumentException if the key is not found.
   */
  public String getParameter(String name)
  {
    String value = getParameter(name, null);
    if (value == null) throw new IllegalArgumentException("Missing parameter \"" + name + "\"");
    return value;
  }

  /**
   * Get the parameter with the specified key name.  If
   * not found then return the default value.
   */
  public String getParameter(String name, String defaultValue)
  {
    if (parameters != null)
    {
      String value = parameters.get(name);
      if (value != null) return value;
    }
    return defaultValue;
  }

  /**
   * Return the parameters for the view query.
   *
   * @since Niagara 4.8
   *
   * @return An unmodifiable map of parameter names to values.
   */
  public Map<String, String> getParameters()
  {
    return parameters == null ? Collections.emptyMap() :
      Collections.unmodifiableMap(parameters);
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
   * If the query at index+1 is also a ViewQuery, then perform
   * a merge using the {@code merge()} method.  Otherwise
   * if this query is not the last query in the list, strip it.
   */
  @Override
  public void normalize(OrdQueryList list, int index)
  {
    if (list.isSameScheme(index, index+1))
    {
      ViewQuery append = (ViewQuery)list.get(index+1);
      list.merge(index, merge(append));
    }
    else if (index < list.size()-1)
    {
      list.remove(index);
    }
  }

  /**
   * Merge this view query with the specified view query.
   * If both queries specify a view id, or a parameter with
   * the same name, then the specified query trumps this one.
   */
  public ViewQuery merge(ViewQuery a)
  {
    // merge view type
    String mergeViewId = null;
    if (viewId != null)
    {
      if (a.viewId == null)  mergeViewId = viewId;
    }
    if (a.viewId != null)
    {
      mergeViewId = a.viewId;
    }

    // merge params
    Map<String, String> mergeParams = null;
    if (parameters != null || a.parameters != null)
    {
      mergeParams = (parameters != null) ? parameters : new HashMap<>();
      if (a.parameters != null)
      {
        for (String name : a.getParameterNames())
        {
          mergeParams.put(name, a.getParameter(name));
        }
      }
    }

    // write string
    StringBuilder s = new StringBuilder();
    if (mergeViewId != null) s.append(mergeViewId);
    if (mergeParams != null)
    {
      s.append('?');
      for (String name : mergeParams.keySet())
      {
        s.append(name).append('=').append(mergeParams.get(name)).append(';');
      }
    }

    return new ViewQuery(scheme, s.toString());
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
   * Parse the body into the appropiate fields.
   */
  void parse()
  {
    try
    {
      String body = this.body;
      int len = body.length();

      // must have something
      if (len == 0)
        throw new SyntaxException("no body");

      // if question mark then split and parse params
      int question = body.indexOf('?');
      if (question < 0)
      {
        viewId = body;
      }
      else
      {
        viewId = body.substring(0, question).trim();
        parseParams(question);
      }

      // validate correct view type
      int viewLen = viewId.length();
      if (viewLen == 0)
      {
        viewId = null;
      }
      else
      {
        for(int i=0; i<viewLen; ++i)
        {
          int c = viewId.charAt(i);
          if (c == '|' || c == ';' || c == '=')
            throw new SyntaxException("invalid view id char '" + (char)c + "'");
        }
      }
    }
    catch(SyntaxException e)
    {
      throw e;
    }
    catch(Throwable e)
    {
      throw new SyntaxException(e);
    }
  }

  /**
   * Parse params after question mark.
   */
  private void parseParams(int question)
  {
    String body = this.body;
    int len = body.length();
    int lastEqual     = -1;
    int lastSemicolon = question;
    for(int i=question+1; i<len; ++i)
    {
      int c = body.charAt(i);
      if (c == ';')
      {
        addParam(lastSemicolon, lastEqual, i);
        lastEqual = -1;
        lastSemicolon = i;
      }
      else if (c == '=')
      {
        lastEqual = i;
      }
      else if (c == '%' || c == '|' || c == '?')
      {
        throw new SyntaxException("invalid param char '" + (char)c + "'");
      }
    }

    if (lastSemicolon < len-1)
      addParam(lastSemicolon, lastEqual, len);
   }

  /**
   * Parser out params, do error checking, and add to map.
   */
  private void addParam(int lastSemicolon, int lastEqual, int i)
  {
    if (lastEqual < 0)
      throw new SyntaxException("invalid param, missing =");

    String name   = body.substring(lastSemicolon+1, lastEqual).trim();
    String value  = body.substring(lastEqual+1, i).trim();

    if (name.isEmpty())
      throw new SyntaxException("missing name");

    /* allow empty string value
    if (value.length() == 0)
      throw new SyntaxException("missing value");
    */

    if (parameters == null)
      parameters = new HashMap<>();

    parameters.put(name, value);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final String[] NO_NAMES = new String[0];

  private String scheme;
  private String body;
  private String viewId;
  private Map<String, String> parameters;
    
}

