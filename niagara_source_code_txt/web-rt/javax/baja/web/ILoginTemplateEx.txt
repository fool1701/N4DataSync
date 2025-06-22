/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ILoginTemplateEx
{
  public void processLoginGet(BWebService service,
                              HttpServletRequest req,
                              HttpServletResponse resp)
                throws Exception;

  public void processLoginPost(BWebService service,
                               HttpServletRequest req,
                               HttpServletResponse resp)
                throws Exception;
}
