/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.httpClient;

import java.util.Optional;

/**
 * Interface for a http message, applicable to both http requests and responses.
 *
 * @author Nick Dodd
 * @since Niagara 4.12
 */
public interface IHttpMessage
{
  /**
   * Get a header from the http message
   *
   * @param name name of the header
   * @return the header value or {@link Optional}.empty if this does not exist.
   */
  Optional<String> getHeader(String name);

  /**
   * @return the message body as a string
   */
  @SuppressWarnings("override")
  String getBody();
}
