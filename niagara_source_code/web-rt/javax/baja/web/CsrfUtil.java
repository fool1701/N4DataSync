/*
 * Copyright 2016 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;
import javax.baja.session.CsrfException;
import javax.baja.util.Lexicon;
import javax.servlet.http.HttpServletRequest;
import com.tridium.session.NiagaraSuperSession;
import com.tridium.session.SessionManager;

/**
 * A utility class to get the Csrf token for a session and
 * also verify an incoming token against the session token.
 */
public final class CsrfUtil
{
  /**
   * Verify Csrf token by extracting an incoming token from a client http servlet request
   *
   * @param req - The incoming http request
   * @return boolean true if the token is valid
   * @throws IOException   Thrown if there is a problem parsing the request parameters
   * @throws CsrfException Throws if the Csrf token is invalid or missing
   */
  public static final boolean verifyCsrfToken(HttpServletRequest req) throws IOException, CsrfException
  {
    String requestToken = getCsrfTokenFromRequest(req);
    return verifyCsrfToken(requestToken);
  }

  /**
   * Verify an incoming csrf token against a passed in session token
   *
   * @param sessionToken - The csrf token for this session
   * @param req          The incoming HttpServletRequest to extract the token from
   * @return boolean true if the token is valid
   * @throws IOException   Thrown if there is a problem parsing the request parameters
   * @throws CsrfException Throws if the Csrf token is invalid or missing
   */
  public static boolean verifyCsrfToken(String sessionToken, HttpServletRequest req) throws IOException, CsrfException
  {
    String requestToken = getCsrfTokenFromRequest(req);
    return verifyCsrfToken(sessionToken, requestToken);
  }

  /**
   * @param token - The incoming token to check for validity
   * @return boolean true if the token is valid
   * @throws IOException   Thrown if there is a problem parsing the request parameters
   * @throws CsrfException Throws if the Csrf token is invalid or missing
   */
  public static boolean verifyCsrfToken(String token) throws IOException, CsrfException
  {
    NiagaraSuperSession session = SessionManager.getCurrentNiagaraSuperSession();

    if (session == null)
    {
      throw new CsrfException(WEBLEX.get("csrf.token.verify.error"));
    }
    return verifyCsrfToken(session.getCsrfToken(), token);
  }

  /**
   * Verify an incoming csrf token value with the session token value
   *
   * @param sessionToken The csrf token for this session
   * @param token        The incoming token to check for validity
   * @return boolean true if the token is valid
   * @throws IOException   Thrown if there is a problem parsing the request parameters
   * @throws CsrfException Throws if the Csrf token is invalid or missing
   */
  public static boolean verifyCsrfToken(String sessionToken, String token) throws IOException, CsrfException
  {
    if (Objects.isNull(token) || Objects.isNull(sessionToken))
    {
      throw new CsrfException(WEBLEX.get("csrf.token.missing.error"));
    }

    if (!sessionToken.equals(token))
    {
      throw new CsrfException(WEBLEX.get("csrf.token.invalid.error"));
    }
    else
    {
      return true;
    }
  }

  /////////////////////////////////////////////////////////////////////
  ////Private
  ////////////////////////////////////////////////////////////////////

  /**
   * Get the CSRF token from a HttpServletRequest. The token can be
   * passed in a query string, embedded in the form or added to the http
   * header.
   *
   * @param req - The servlet request
   * @return String - The CSRF token string if available or null
   */
  private static String getCsrfTokenFromRequest(HttpServletRequest req) throws IOException
  {
    /**
     * Read the CSRF token from the header first and only if not available read it
     * from the stream (via getParamter).
     */
    String csrfToken = null;
    String hCsrfToken = req.getHeader(CSRF_TOKEN_HTTP_HEADER);

    if (hCsrfToken != null)
    {
      csrfToken = URLDecoder.decode(hCsrfToken, "UTF-8");
    }
    else
    {
      //Read from query string or form field
      csrfToken = req.getParameter(CSRF_TOKEN_NAME);
    }
    return csrfToken;
  }

  public static final String CSRF_TOKEN_NAME = "csrfToken";
  public static final String CSRF_TOKEN_HTTP_HEADER = "x-niagara-csrfToken";

  private static final Lexicon WEBLEX = Lexicon.make("web");
}
