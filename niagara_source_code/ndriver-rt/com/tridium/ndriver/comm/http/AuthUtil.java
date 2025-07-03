/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.comm.http;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.baja.nre.util.TextUtil;
import javax.baja.security.BUsernameAndPassword;
import javax.baja.spy.SpyWriter;
import com.tridium.ndriver.datatypes.BIpAddress;

/**
 * AuthUtil maintains a cache of info to authenticate HTTP connections per
 * URI/username/password and provides apis for use by HttpComm linkSessions.
 * This class is package private.
 * <p>
 * Based on RFC 2617 - HTTP Authentication: Basic and Digest Access
 * Authentication
 * <p>
 * If a HTTP message without appropriate authentication is received by a server
 * it will send a challenge for a particular authentication. The spec defines
 * two types, basic and digest.  Authentication headers must then be added to
 * request to the same uri or others in the protection space.
 * <p>
 * Does not currently support Proxy Authentication
 * <p>
 * Ported from devHttpDriver:DdfAuthenticationHelper;
 *
 * @author Robert Adams
 * @creation June 6, 2012
 */
class AuthUtil
{
  /**
   * Process a challenge error message. Create and add info to Authenticate
   * entry for future use. <p>
   *
   * @return true to indicate challenge received and credentials added to req
   */
  boolean receiveChallenge(NHttpRequest req, NHttpErrorResponse err, Logger log)
  {
    // If no credentials then no way to respond to challenge
    if (req.getUsernamePassword() == null)
    {
      return false;
    }

    // Check for authenticate header
    String authS = err.getValue("www-authenticate");
    if (authS == null)
    {
      return false;
    }

    String key = getHashKey(req, PASSWORD_OPTION);
    String keyWithoutPassword = getHashKey(req, NO_PASSWORD_OPTION);
    if (log.isLoggable(Level.FINE))
    {
      log.fine("receiveChallenge - add auth for " + keyWithoutPassword);
    }
    AuthParams ap = new AuthParams(authS);

    // check for current entry - in any case this will be replaced, just
    // dump so debug
    Authenticate auth = hash.get(key);
    if (auth != null)
    {
      if (log.isLoggable(Level.FINE))
      {
        log.fine("Replace authentication info " +
          "\n" + auth.params.getValue("nonce") +
          "\n" + ap.getValue("nonce"));
      }
    }

    // Create new Authenticate 
    auth = new Authenticate(ap);
    int current = hash.size();
    // Hash
    hash.put(key, auth);
    //Check to see whether hash have added new key
    if (hash.size() > current)
    {
      keyTableWithoutPass.add(keyWithoutPassword);
    }
    return true;
  }

  /**
   * If info available for request uri/username/password add authorization entry
   * to request.
   */
  boolean addAuthorization(NHttpRequest req, Logger log)
  {
    // Can only authorize if there is a password
    if (req.getUsernamePassword() == null)
    {
      return false;
    }
    String key = getHashKey(req, PASSWORD_OPTION);
    String keyWithoutPassword = getHashKey(req, NO_PASSWORD_OPTION);

    // Check for cached authentication info
    Authenticate auth = hash.get(key);
    if (auth == null)
    {
      return false;
    }

    if (log.isLoggable(Level.FINE))
    {
      log.fine("addAuthorization for " + keyWithoutPassword + "\nscheme:" + auth.params.scheme);
    }
    try
    {
      // Handle Digest authentication
      if (auth.params.scheme.equalsIgnoreCase("Digest"))
      {
        return addDigestAuthorization(req, auth);
      }

      if (auth.params.scheme.equalsIgnoreCase("Basic"))
      {
        BUsernameAndPassword unpw = req.getUsernamePassword();
        req.addBasicAuthorization(unpw.getUsername(), AccessController.doPrivileged((PrivilegedAction<String>)unpw.getPassword()::getValue));
        return true;
      }

    }
    catch (Exception e)
    {
    }
    return false;
  }

  private boolean addDigestAuthorization(NHttpRequest req, Authenticate auth)
    throws Exception
  {
    BUsernameAndPassword usPass = req.getUsernamePassword();
    String uri = req.getUri();

    // Gets the realm, nonce, and qop
    String realm = auth.params.getValue("realm");
    String nonce = auth.params.getValue("nonce");
    String qop = auth.params.getValue("qop");
    String opaque = auth.params.getValue("opaque");

    if (nonce == null)
    {
      return false;
    }


    String cnonce = getCnonce(auth, req);

    // Builds up the digest authentication text to go in the request header
    StringBuilder sb = new StringBuilder();
    sb.append("Digest ");
    sb.append("username=").append(quote(usPass.getUsername())).append(", ");
    sb.append("realm=").append(realm).append(", ");
    sb.append("nonce=").append(nonce).append(", ");
    if (opaque != null)
    {
      sb.append("opaque=").append(opaque).append(", ");
    }
    sb.append("uri=").append(quote(uri)).append(", ");

    // increment the nonce count - string is 8LHEX
    String ncS = "00000000" + Integer.toHexString(++auth.nc);
    ncS = ncS.substring(ncS.length() - 8);

    if (qop != null)
    {
      sb.append("nc=").append(ncS).append(", ");
      sb.append("qop=").append(unquote(qop)).append(", ");
      sb.append("cnonce=").append(quote(cnonce)).append(", ");
    }

    // Build the response string
    StringBuilder respS = new StringBuilder();
    respS.append(hexMD5(usPass.getUsername() + ':' + unquote(realm) + ':' + AccessController.doPrivileged((PrivilegedAction<String>)usPass.getPassword()::getValue))).append(':');
    respS.append(unquote(nonce)).append(':');
    respS.append(ncS).append(':');
    respS.append(cnonce).append(':');
    respS.append(unquote(qop)).append(':');
    respS.append(hexMD5(req.getMethod() + ':' + uri));

    sb.append("response=").append(quote(hexMD5(respS.toString())));

    // Add the header to the request message
    req.addHeader("Authorization", sb.toString());
    return true;
  }

  private String unquote(String s)
  {
    return s.substring(1, s.length() - 1);
  }

  private String quote(String s)
  {
    return '\"' + s + '\"';
  }

  /**
   * Computes the CNONCE - an encrypted version of the credentials (user name /
   * password) to log into a web server.
   *
   * @return the CNONCE
   * @throws Exception
   */
  private String getCnonce(Authenticate auth, NHttpRequest req)
    throws Exception
  {
    ByteArrayOutputStream bout = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(bout);
    BIpAddress adr = (BIpAddress)req.getAddress();

    out.writeUTF("cnonce");
    out.writeInt(auth.nc);

    //pacman 24187 - This was using adr.getInetAddress().getHostName(). This causes a reverse name lookup which 
    // can take 5 seconds
    out.writeUTF(adr.getIpAddress());

    out.writeInt(adr.getPort());
    out.writeLong(System.currentTimeMillis());
    out.writeInt(clientRandom.nextInt());

    return hexMD5(new String(bout.toByteArray()));
  }

  /**
   * Encrypts the given string using the MD5 encryption algorithm. Delegates to
   * java.security.MessageDigest to perform the computation
   *
   * @throws Exception
   */
  private String hexMD5(String in)
    throws Exception
  {
    StringBuilder result = new StringBuilder();
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    byte[] toHash = md5.digest(in.getBytes());
    for (int i = 0; i < toHash.length; i++)
    {
      result.append(TextUtil.toLowerCase(TextUtil.byteToHexString(toHash[i])));
    }
    return result.toString();
  }

  private String getHashKey(NHttpRequest req, boolean passwordOption)
  {
    String uri = req.getUri();
    BUsernameAndPassword usPass = req.getUsernamePassword();
    BIpAddress ip = (BIpAddress)req.getAddress();
    // NCCB-32899 DVR status goes down after addition under Milestone network

    String uriChanged = uri;
    if (-1 != uri.indexOf('?'))
    {
      uriChanged = uri.substring(0, uri.indexOf('?'));
    }
    String ipAddress = ip.getIpAddress();
    String userName = usPass.getUsername();
    String passValue = usPass.getPassword().getValue();

    return passwordOption ?
      (ipAddress + ":" + uriChanged + ":" + userName + ":" + passValue) :
      (ipAddress + ":" + uriChanged + ":" + userName + ":" + "****");

  }

  static class Authenticate
  {
    Authenticate(AuthParams p)
    {
      params = p;
    }

    AuthParams params;
    int nc = 1;
  }

  /* Utility class to access the authentication parameters.
   *
   * From rfc2617
   * It uses an extensible, case-insensitive token to identify the authentication scheme,
   * followed by a comma-separated list of attribute-value pairs which carry the parameters
   * necessary for achieving authentication via that scheme.
   *      auth-scheme    = token
   *      auth-param  = token "=" ( token | quoted-string )
   */
  static class AuthParams
  {
    AuthParams(String s)
    {
      int ndx = s.indexOf(' ');
      scheme = s.substring(0, ndx);

      StringTokenizer st = new StringTokenizer(s.substring(ndx + 1), ",");
      int cnt = st.countTokens();
      nam = new String[cnt];
      val = new String[cnt];
      for (int i = 0; i < cnt; ++i)
      {
        String tok = st.nextToken().trim();
        ndx = tok.indexOf('=');
        nam[i] = tok.substring(0, ndx);
        val[i] = tok.substring(ndx + 1);
      }
    }

    String getValue(String tok)
    {
      for (int i = 0; i < nam.length; ++i)
      {
        if (nam[i].equals(tok))
        {
          return val[i];
        }
      }
      return null;
    }

    String scheme;
    String[] nam;
    String[] val;

    void spy(SpyWriter out)
    {
      for (int i = 0; i < nam.length; ++i)
      {
        out.w("<tr>");
        out.td((i == 0) ? scheme : ""); // Only put scheme on first entry
        out.td(nam[i]).td(val[i]);
        out.w("</tr>\n");
      }
    }

  }

  //LinkedHashMap preserves the ordering of the keys,
  // and thus matches with the ArrayList key ordering.
  Map<String, Authenticate> hash = Collections.synchronizedMap(new LinkedHashMap<>());
  ArrayList<String> keyTableWithoutPass = new ArrayList<String>();

  Random clientRandom = new SecureRandom();
  private static final boolean PASSWORD_OPTION = true;
  private static final boolean NO_PASSWORD_OPTION = false;

////////////////////////////////////////////////////////////////
//Spy
////////////////////////////////////////////////////////////////

  /**
   * Provide some spy debug
   */
  public void spy(SpyWriter out)
    throws Exception
  {
    out.startProps("AuthUtil");
    out.endProps();

    if (hash.size() <= 0)
    {
      out.write("<b>no sessions</b><br><br>");
      return;
    }

    out.startTable(true);
    out.trTitle("sessions", 3);
    Enumeration<String> it = Collections.enumeration(hash.keySet());
    Enumeration<String> itSet = Collections.enumeration(keyTableWithoutPass);
    synchronized (hash)
    {
      while (it.hasMoreElements())
      {
        String key = it.nextElement();
        Authenticate c = hash.get(key);
        // Put the key on one table line
        out.w("<tr><th scope='rowgroup' colspan='3'>").safe(itSet.nextElement()).w("</th></tr>\n");
        // Follow with params
        c.params.spy(out);
      }
    }
    out.endTable();

  }
}
