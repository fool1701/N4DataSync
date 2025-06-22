/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.httpClient;

import static java.util.logging.Level.FINE;

import static com.tridium.httpClient.datatypes.auth.BBasicHttpAuth.createBasicAuthenticator;
import static com.tridium.httpClient.datatypes.auth.BBearerTokenAuth.createBearerAuthenticator;
import static com.tridium.httpClient.datatypes.auth.BHeaderTokenAuth.createHeaderTokenAuthenticator;
import static com.tridium.httpClient.datatypes.auth.BNiagaraScramShaDigestAuth.createScramShaAuthenticator;
import static com.tridium.httpClient.datatypes.auth.BParameterTokenAuth.createParameterTokenAuthenticator;
import static com.tridium.httpClient.datatypes.auth.BResponseCookieAuth.createCookieAuthenticator;
import static com.tridium.httpClient.util.HttpClientUtils.MAIN_LOGGER;
import static com.tridium.util.PrefixLogUtil.logWithPrefix;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.baja.httpClient.datatypes.BHttpRequestMethod;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.net.Http;
import javax.baja.sys.BComponent;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.BString;
import javax.baja.sys.InvalidEnumException;
import javax.baja.sys.Sys;
import javax.baja.util.BTypeSpec;

import com.tridium.httpClient.BHttpClient;
import com.tridium.httpClient.datatypes.BHttpAddress;
import com.tridium.httpClient.datatypes.auth.BHttpAuthenticator;
import com.tridium.httpClient.datatypes.enums.BHttpMode;
import com.tridium.httpClient.datatypes.payload.BFileSource;
import com.tridium.httpClient.datatypes.payload.BParameterStringSource;
import com.tridium.httpClient.datatypes.payload.BReportPayloadSource;
import com.tridium.httpClient.datatypes.payload.BSlotSource;

/**
 * Builder class for convenient construction of http client components.
 *
 * @author Nick Dodd
 * @since Niagara 4.12
 */
public class HttpClientBuilder
{
  private HttpClientBuilder()
  {
    httpClient = new BHttpClient();
  }

  /**
   * @return a new instance of HttpClientBuilder to construct a single new client.
   */
  public static HttpClientBuilder instance()
  {
    return new HttpClientBuilder();
  }

  /**
   * Specify the method type to use for the http client.
   * @param method the method type to use for the http client.
   * @return the builder instance
   */
  public HttpClientBuilder withMethod(BHttpRequestMethod method)
  {
    httpClient.setMethod(method);
    return this;
  }

  /**
   * Specify the method type to use for the http client.
   * @param methodStr the method type as a string to use for the http client. get/post/put
   * @return the builder instance
   */
  public HttpClientBuilder withMethod(String methodStr)
  {
    try
    {
      BHttpRequestMethod method = BHttpRequestMethod.make(methodStr.toLowerCase());
      httpClient.setMethod(method);
    }
    catch (InvalidEnumException e)
    {
      BEnumRange range = BHttpRequestMethod.get.getRange();
      String supportedTypes = Arrays.stream(range.getOrdinals())
        .mapToObj(ordinal -> range.getTag(ordinal).toUpperCase())
        .collect(Collectors.joining(","));

      throw new IllegalArgumentException("Supported Method Types are " + supportedTypes);
    }
    return this;
  }

  /**
   * Specify the url address of the client.
   * @param urlAddress the url address of the client. If including the parameter string, parameters will be added to the client.
   * @return the builder instance
   */
  public HttpClientBuilder withAddress(String urlAddress)
  {
    httpClient.getAddress().doPopulateFromUrl(BString.make(urlAddress));
    return this;
  }

  /**
   * Specify the address of the client.
   * @param secure true to use https, false for http.
   * @param hostAddress the address hostname or ip address string.
   * @param port the port to use.
   * @param path the path of the resource. Parameter string here will not result in parameters being added to the client.
   * @return the builder instance
   */
  public HttpClientBuilder withAddress(boolean secure, String hostAddress, int port, String path)
  {
    httpClient.setAddress(new BHttpAddress(secure ? BHttpMode.secure : BHttpMode.insecure, hostAddress, port, path));
    return this;
  }

  /**
   * Specify an insecure http address of the client.
   * @param hostAddress the address hostname or ip address string.
   * @param path the path of the resource. Parameter string here will not result in parameters being added to the client.
   * @return the builder instance
   */
  public HttpClientBuilder withHttpAddress(String hostAddress, String path)
  {
    httpClient.setAddress(new BHttpAddress(BHttpMode.insecure, hostAddress, Http.DEFAULT_HTTP_PORT, path));
    return this;
  }

  /**
   * Specify a secure https address of the client.
   * @param hostAddress the address hostname or ip address string.
   * @param path the path of the resource. Parameter string here will not result in parameters being added to the client.
   * @return the builder instance
   */
  public HttpClientBuilder withHttpsAddress(String hostAddress, String path)
  {
    httpClient.setAddress(new BHttpAddress(BHttpMode.DEFAULT, hostAddress, Http.DEFAULT_HTTPS_PORT, path));
    return this;
  }

  /**
   * Specify a header key/value pair for the client.
   * @param headerName name of the http header - this will be slot escaped.
   * @param headerValue value of the header
   * @return the builder instance
   */
  public HttpClientBuilder withHeader(String headerName, BSimple headerValue)
  {
    httpClient.getHeaders().addNewOption(headerName, headerValue);
    return this;
  }

  /**
   * Specify a parameter key/value pair for the client.
   * @param parameterName name of the http parameter - this will be slot escaped.
   * @param parameterValue value of the parameter
   * @return the builder instance
   */
  public HttpClientBuilder withParameter(String parameterName, BSimple parameterValue)
  {
    httpClient.getParameters().addNewOption(parameterName, parameterValue);
    return this;
  }

  /**
   * Specify the authenticator type to use in the client, without any config settings.
   * @param authType authenticator type to use in the client.
   * @return the builder instance
   */
  public HttpClientBuilder withAuthenticator(BTypeSpec authType)
  {
    BHttpAuthenticator authenticator = new BHttpAuthenticator();
    authenticator.setAuthType(authType);
    authenticator.checkAuthenticator();   // client is not mounted so force update
    httpClient.setAuthenticator(authenticator);
    return this;
  }

  /**
   * Specify a http basic authenticator to use in the client.
   * @param username name credential.
   * @param password password credential.
   * @return the builder instance
   */
  public HttpClientBuilder withBasicAuthenticator(String username, String password)
  {
    httpClient.setAuthenticator(createBasicAuthenticator(username, password));
    return this;
  }

  /**
   * Specify a http header token for use in authentication, such as an api key.
   * Value stored as a password.
   * @param headerName name of the heeader
   * @param headerValue value of the header token
   * @return the builder instance
   */
  public HttpClientBuilder withHeaderTokenAuthenticator(String headerName, String headerValue)
  {
    httpClient.setAuthenticator(createHeaderTokenAuthenticator(headerName, headerValue));
    return this;
  }

  /**
   * Specify a http parameter token for use in authentication, such as an api key.
   * Value stored as a password.
   * @param parameterName name of the parameter
   * @param parameterValue value of the parameter token
   * @return the builder instance
   */
  public HttpClientBuilder withParameterTokenAuthenticator(String parameterName, String parameterValue)
  {
    httpClient.setAuthenticator(createParameterTokenAuthenticator(parameterName, parameterValue));
    return this;
  }

  /**
   * Specify a bearer token authenticator to use in the client.
   * @param token token value.
   * @return the builder instance
   */
  public HttpClientBuilder withBearerAuthenticator(String token)
  {
    httpClient.setAuthenticator(createBearerAuthenticator(token));
    return this;
  }

  /**
   * Specify a response cookie authenticator to use in the client.
   * @param sourceClient an existing http client instance which will supply the cookie header.
   * @param cookieName name of the cookie to seek in the source client.
   * @return the builder instance
   */
  public HttpClientBuilder withCookieAuthenticator(IHttpClient sourceClient, String cookieName)
  {
    String handle = ((BComponent)sourceClient).getHandleOrd().toString();
    httpClient.setAuthenticator(createCookieAuthenticator(handle, cookieName));
    return this;
  }

  /**
   * Specify a Niagara scram-sha authenticator to use in the client.
   * @param username Niagara account name credential.
   * @param password Niagara account password credential.
   * @return the builder instance
   */
  public HttpClientBuilder withNiagaraAuthenticator(String username, String password)
  {
    httpClient.setAuthenticator(createScramShaAuthenticator(username, password));
    return this;
  }

  /**
   * Specify a string payload to use in the client.
   * @param data payload as a string.
   * @return the builder instance
   */
  public HttpClientBuilder withStringPayload(String data)
  {
    return withStringPayload(data, "");
  }

  /**
   * Specify a string payload to use in the client.
   * @param data payload as a string.
   * @param userContentType override for content-type header value.
   * @return the builder instance
   */
  public HttpClientBuilder withStringPayload(String data, String userContentType)
  {
    httpClient.getRequestBody().setSourceType(BSlotSource.TYPE.getTypeSpec());
    httpClient.getRequestBody().setSource(BSlotSource.make(data));
    httpClient.getRequestBody().getContentTypeHeader().setUserContentType(userContentType);
    return this;
  }

  /**
   * Specify a file to use as the request payload in the client.
   * @param fileOrd ord of the file to use.
   * @param userContentType override for content-type header value.
   * @return the builder instance
   */
  public HttpClientBuilder withFilePayload(BOrd fileOrd, String userContentType)
  {
    httpClient.getRequestBody().setSourceType(BFileSource.TYPE.getTypeSpec());
    httpClient.getRequestBody().setSource(BFileSource.make(fileOrd));
    httpClient.getRequestBody().getContentTypeHeader().setUserContentType(userContentType);
    return this;
  }

  /**
   * Specify that client should create a form-data url encoded string from the parameters as a payload.
   * @return the builder instance
   */
  public HttpClientBuilder withFormPayload()
  {
    httpClient.getRequestBody().setSourceType(BParameterStringSource.TYPE.getTypeSpec());
    httpClient.getRequestBody().setSource(new BParameterStringSource());
    return this;
  }

  /**
   * Specify a Niagara report to use as the request payload in the client.
   * @param reportSourceOrd a Niagara report to use as the request payload in the client.
   * @return the builder instance
   */
  public HttpClientBuilder withReportPayload(BOrd reportSourceOrd)
  {
    httpClient.getRequestBody().setSourceType(BReportPayloadSource.TYPE.getTypeSpec());
    httpClient.getRequestBody().setSource(BReportPayloadSource.make(reportSourceOrd));
    return this;
  }

  /**
   * Switch off the send on start tuning setting in the client.
   * @return the builder instance
   */
  public HttpClientBuilder withoutSendOnStart()
  {
    httpClient.getHttpTuningPolicy().setWriteOnStart(false);
    return this;
  }

  /**
   * Specify the location for this client within the current running station.
   * @param slotPathOrd ord path at which the client should be mounted.
   * @param clientName name to use for the client - will be slot escaped.
   * @return the builder instance
   */
  public HttpClientBuilder mountAtOrd(String slotPathOrd, String clientName)
  {
    this.slotPathOrd = slotPathOrd;
    this.clientName = clientName;
    return this;
  }

  /**
   * Build and return the current client, it will be mounted in the station at this stage if that
   * has been requested.
   * @return the configured client, possibly mounted in the station.
   */
  public IHttpClient build()
  {
    if (slotPathOrd != null && clientName != null)
    {
      if (!Sys.isStation())
      {
        throw new IllegalArgumentException("No running station detected for mounting the http client");
      }
      BObject location = BOrd.make(slotPathOrd).get(Sys.getStation());
      if (location instanceof BComponent)
      {
        String name = SlotPath.escape(clientName);
        location.asComponent().add(name, httpClient);
        logWithPrefix(MAIN_LOGGER, FINE, "Added " + name + " to " + location, this);
      }
      else
      {
        throw new IllegalArgumentException("Path supplied for client is not a component");
      }
    }

    return httpClient;
  }

  private String slotPathOrd;
  private String clientName;
  private final BHttpClient httpClient;
}
