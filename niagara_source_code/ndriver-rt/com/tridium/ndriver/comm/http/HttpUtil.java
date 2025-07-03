/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.http;

import javax.baja.data.BIDataValue;
import javax.baja.sys.BFacets;
import javax.baja.sys.BString;
import com.tridium.ndriver.comm.http.NHttpMessage.NVPair;
import com.tridium.util.EscUtil;

/**
 * HttpUtil class
 *
 * @author Robert Adams
 * @creation June 27, 2012
 */
public final class HttpUtil
{
  private HttpUtil() {}

  /**
   * @return BFacets object containing field for each http header in response
   * message
   */
  public static BFacets responseHeaderToFacets(NHttpResponse rsp)
  {
    // Modified from DdfHttpResponseHeaderUtil
    NVPair[] a = rsp.getHeaders();

    String[] facetKeys = new String[a.length + 1];
    BIDataValue[] facetValues = new BIDataValue[a.length + 1];

    // Gets the std http response version 
    String responseVersion = rsp.responseVersion;
    if (responseVersion == null)
    {
      responseVersion = "";
    }

    // Add the HttpConnection's std http response version to the set of key/value
    facetKeys[0] = HTTP_HEADER_RESPONSE_VERSION_KEY;
    facetValues[0] = (BIDataValue)BString.make(responseVersion);

    // Loops through all header names
    for (int i = 0; i < a.length; ++i)
    {
      facetKeys[i + 1] = EscUtil.slot.escape(a[i].name);
      facetValues[i + 1] = ((BIDataValue)BString.make(EscUtil.slot.escape(a[i].value)));
    }

    // Returns a set of facets for the response header key/value pairs
    return BFacets.make(facetKeys, facetValues);
  }

  public static final String HTTP_HEADER_RESPONSE_VERSION_KEY = "ResponseVersion";
}
