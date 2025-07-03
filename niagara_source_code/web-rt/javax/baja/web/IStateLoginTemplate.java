/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IStateLoginTemplate
{
  public void write(BWebService service, HttpServletRequest req, HttpServletResponse resp, LoginState state)
          throws Exception;
}
