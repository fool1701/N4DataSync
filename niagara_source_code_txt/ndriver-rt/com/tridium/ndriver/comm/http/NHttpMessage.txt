/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.http;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.baja.nre.util.ByteArrayUtil;

import com.tridium.ndriver.comm.NMessage;
import com.tridium.ndriver.datatypes.BIpAddress;

/**
 * NHttpMessage provides an NMessage for http comm stack.
 *
 * @author Robert A Adams
 * @creation Nov 1, 2011
 */
public class NHttpMessage
  extends NMessage
{
  /**
   * @param address
   */
  public NHttpMessage(BIpAddress address)
  {
    super(address);
  }

  /**
   * Add the following named header.  If a header already exist with this name
   * replace with new value value.
   *
   * @param name  the name of the header to add
   * @param value the value of the header to add
   */
  public void addHeader(String name, String value)
  {
    addHeader(name, value, true);
  }

  /**
   * Add the following named header.  If a header already exist with this name
   * replace with new value value.
   *
   * @param name    the name of the header to add
   * @param value   the value of the header to add
   * @param replace if true, replace any existing headers with the name,
   *                otherwise add to the existing headers
   * @since Niagara 4.11
   */
  public void addHeader(String name, String value, boolean replace)
  {
    List<String> values = headerMap.computeIfAbsent(name, key -> new ArrayList<>());
    if (replace)
    {
      values.clear();
    }
    values.add(value);
  }

  /**
   * Return a map of header names to List of header values. Keys are
   * case-insensitive.
   *
   * @return header map
   * @since Niagara 4.11
   */
  public Map<String, List<String>> getHeaderMap()
  {
    Map<String, List<String>> headerMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    for (Map.Entry<String, List<String>> entry : this.headerMap.entrySet())
    {
      headerMap.put(entry.getKey(), Collections.unmodifiableList(entry.getValue()));
    }
    return Collections.unmodifiableMap(headerMap);
  }

  /**
   * Return an array of NVPairs of header values. This array will only contain
   * one header value for each name. If there are multiple headers with the same
   * name, only the last header added for that name will be present in the
   * array. Use {@link #getHeaderMap()}getHeaderMap() to get multiple headers
   * with the same name.
   *
   * @return array of NVPairs of header values.
   */
  public NVPair[] getHeaders()
  {
    NVPair[] headers = new NVPair[headerMap.size()];
    int i = 0;
    for (Map.Entry<String, List<String>> entry : headerMap.entrySet())
    {
      List<String> headerValues = entry.getValue();
      headers[i] = new NVPair(entry.getKey(), headerValues.get(headerValues.size() - 1));
      i++;
    }

    return headers;
  }

  /**
   * Get a list of all header values with the specified name, case insensitive,
   * or null if no headers with the specified name are present.
   *
   * @param name the name to get header values for, case insensitive
   * @return list of header values, or null
   * @since Niagara 4.11
   */
  public List<String> getValues(String name)
  {
    List<String> values = headerMap.get(name);
    if (values == null)
    {
      return null;
    }
    return Collections.unmodifiableList(headerMap.get(name));
  }

  /**
   * Return the value of the header with the specified name, or null if header
   * is not present. If there are multiple headers with the specified name, the
   * last header added will be returned. Use {@link #getValues(String)}getValues()
   * to get multiple headers with the same name.
   *
   * @param name the header name
   * @return the value of the header, or null
   */
  public String getValue(String name)
  {
    List<String> headerValues = headerMap.get(name);
    if (headerValues != null)
    {
      return headerValues.get(headerValues.size() - 1);
    }
    return null;
  }

  public byte[] getData()
  {
    return buf;
  }

  @Override
  public String toTraceString()
  {
    StringWriter sw = new StringWriter();
    StringBuffer sb = sw.getBuffer();

    for (Map.Entry<String, List<String>> entry : headerMap.entrySet())
    {
      String name = entry.getKey();
      List<String> headerValues = entry.getValue();
      if (HEADERS_TO_REDACT.contains(name.toLowerCase(Locale.ENGLISH)))
      {
        sb.append(name).append(": ********").append('\n');
      }
      else
      {
        for (String header : headerValues)
        {
          sb.append(name).append(": ").append(header).append('\n');
        }
      }
    }

    if (buf != null && buf.length > 0)
    {
      ByteArrayUtil.hexDump(new PrintWriter(sw), buf, 0, buf.length);
    }

    return sw.toString();
  }

  // Use TreeMap with case insensitive order so the keys will be case insensitive
  // but still preserve the original case of the first entry added.
  private final Map<String, List<String>> headerMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
  byte[] buf = null;

  /**********************************************
   * Encapsulation of a name/value pair.
   ***********************************************/
  public static class NVPair
  {
    public NVPair(String name, String value)
    {
      this.name = name;
      this.value = value;
    }

    public String name;
    public String value;
  }


  // Fields are case-insensitive (as per https://www.rfc-editor.org/rfc/rfc9110.html#name-field-names)
  // Store them all lower-case to make lookup easier, even if it looks... wrong, somehow
  private static final List<String> HEADERS_TO_REDACT = Collections.unmodifiableList(Arrays.asList(
    "authorization",
    "cookie",
    "proxy-authorization"
  ));
}
