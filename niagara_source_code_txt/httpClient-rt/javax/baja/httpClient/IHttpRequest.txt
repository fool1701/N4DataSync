/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.httpClient;

import java.util.Optional;

/**
 * Interface for a http request.
 *
 * @author Nick Dodd
 * @since Niagara 4.12
 */
public interface IHttpRequest extends IHttpMessage
{

  /**
   * Get a parameter from the http message
   *
   * @param name name of the parameter
   * @return the parameter value or {@link Optional}.empty if it does not exist, or this is not a request.
   */
  Optional<String> getParameter(String name);
}
