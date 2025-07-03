/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.net;

import javax.baja.util.Lexicon;

/**
 * Http contains constant definitions and utility methods for use
 * with HTTP 1.1.
 *
 * @author    John Sublett on 17 Mar 00
 * @version   $Revision: 3$ $Date: 3/28/05 9:49:34 AM EST$
 * @since     Niagara 3.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Http
{
  public static final String PROTOCOL_HTTP      = "http";
  public static final int    DEFAULT_HTTP_PORT  = 80;
  public static final String PROTOCOL_HTTPS     = "https";
  public static final int    DEFAULT_HTTPS_PORT = 443;

  public static final int CR = 13;
  public static final int LF = 10;
  public static final String CRLF = "" + (char)CR + (char)LF;

  public static final String METHOD_OPTIONS = "OPTIONS";
  public static final String METHOD_GET     = "GET";
  public static final String METHOD_HEAD    = "HEAD";
  public static final String METHOD_POST    = "POST";
  public static final String METHOD_PUT     = "PUT";
  public static final String METHOD_DELETE  = "DELETE";
  public static final String METHOD_TRACE   = "TRACE";

  public static final String TRANSFER_CHUNKED = "chunked";
  public static final String SESSION_ID       = "sessionId";

  public static final String FORM_CONTENT_TYPE = "application/x-www-form-encoded";

  public static final int SC_CONTINUE                      = 100;
  public static final int SC_SWITCHING_PROTOCOLS           = 101;
  public static final int SC_OK                            = 200;
  public static final int SC_CREATED                       = 201;
  public static final int SC_ACCEPTED                      = 202;
  public static final int SC_NON_AUTHORITATIVE             = 203;
  public static final int SC_NO_CONTENT                    = 204;
  public static final int SC_RESET_CONTENT                 = 205;
  public static final int SC_PARTIAL_CONTENT               = 206;
  public static final int SC_MULTIPLE_CHOICES              = 300;
  public static final int SC_MOVED_PERMANENTLY             = 301;
  public static final int SC_MOVED_TEMPORARILY             = 302;
  public static final int SC_SEE_OTHER                     = 303;
  public static final int SC_NOT_MODIFIED                  = 304;
  public static final int SC_USE_PROXY                     = 305;
  public static final int SC_BAD_REQUEST                   = 400;
  public static final int SC_UNAUTHORIZED                  = 401;
  public static final int SC_PAYMENT_REQUIRED              = 402;
  public static final int SC_FORBIDDEN                     = 403;
  public static final int SC_NOT_FOUND                     = 404;
  public static final int SC_METHOD_NOT_ALLOWED            = 405;
  public static final int SC_NOT_ACCEPTABLE                = 406;
  public static final int SC_PROXY_AUTHENTICATION_REQUIRED = 407;
  public static final int SC_REQUEST_TIME_OUT              = 408;
  public static final int SC_CONFLICT                      = 409;
  public static final int SC_GONE                          = 410;
  public static final int SC_LENGTH_REQUIRED               = 411;
  public static final int SC_PRECONDITION_FAILED           = 412;
  public static final int SC_REQUEST_ENTITY_TOO_LARGE      = 413;
  public static final int SC_REQUEST_URI_TOO_LARGE         = 414;
  public static final int SC_UNSUPPORTED_MEDIA_TYPE        = 415;
  public static final int SC_INTERNAL_SERVER_ERROR         = 500;
  public static final int SC_NOT_IMPLEMENTED               = 501;
  public static final int SC_BAD_GATEWAY                   = 502;
  public static final int SC_SERVICE_UNAVAILABLE           = 503;
  public static final int SC_GATEWAY_TIME_OUT              = 504;
  public static final int SC_HTTP_VERSION_NOT_SUPPORTED    = 505;

  private static Lexicon lex = Lexicon.make("net");


  /**
   * Return a reason phrase for the specified status code. If the phrase
   * doesn't exist then return an unknown reason.
   *
   * @see #codeToPhrase(int)
   *
   * @param statusCode The HTTP status code.
   * @return The reason for the status code.
   */
  public static String getReasonPhrase(int statusCode)
  {
    String phrase = codeToPhrase(statusCode);
    if (phrase == null)
    {
      phrase = codeToPhrase((statusCode / 100) * 100);
      if (phrase == null)
      {
        phrase = lex.getText("Http.statusCode.unknown", String.valueOf(statusCode));
      }
    }
    return phrase;
  }

  /**
   * Return a reason phrase for the specified status code. If the phrase
   * doesn't exist return null.
   * <p>
   * Prefer to use {@link #getReasonPhrase(int)} that doesn't return null.
   * </p>
   *
   * @param statusCode The HTTP status code.
   * @return The reason for the status code or null if it doesn't exist.
   */
  public static String codeToPhrase(int statusCode)
  {
    return lex.get("Http.statusCode." + statusCode);
  }
}