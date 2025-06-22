/*
 * Copyright 2022 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.httpClient;

/**
 * Interface for a http response.
 *
 * @author Nick Dodd
 * @since Niagara 4.12
 */
public interface IHttpResponse extends IHttpMessage
{
  /**
   * @return the http response code.
   */
  int getResponseCode();
}
