/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.httpClient;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Public API interface for http clients.
 *
 * @author Nick Dodd
 * @since Niagara 4.12
 */
public interface IHttpClient
{
  /**
   * Synchronously send a new http request.
   * (blocks current thread until send is completed)
   *
   * @return the response body
   */
  String sendSync() throws Exception;

  /**
   * Asynchronously send a new http request and return control to calling thread.
   * @return a future which can be used to await the message response.
   */
  Future<IHttpResponse> sendAsync();

  /**
   * Asynchronously send a new http request and return control to calling thread.
   */
  @SuppressWarnings("override")
  void send();

  /**
   * Get the last result of a send for this client.
   * @return {@link Optional}.empty if no request has been made, or an {@link IHttpResponse} if the request
   * has completed, regardless of whether then request succeeded or failed.
   */
  Optional<IHttpResponse> getLastResult();

  /**
   * Check if the client has ever received a response.
   * @return true if the client has ever received a response.
   */
  boolean hasResponse();

  /**
   * Convenience to get the latest response body as a string.
   *
   * @return the latest response value as a string. This may be an empty string if the endpoint
   * returned no data in a good response, or possibly in an error response such as a 404/401/500.
   * If the request failed to send entirely than null is returned.
   */
  String getLastResponseBody();

  /**
   * Convenience to get the latest response code.
   *
   * @return the latest response code.
   */
  int getLastResponseCode();
}
