/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

/**
 * HxConst defines constants for the hx framework.
 *
 * @author    Andy Frank
 * @creation  17 Aug 05
 * @version   $Revision: 3$ $Date: 10/20/09 10:32:40 AM EDT$
 * @since     Baja 1.0
 */
public class HxConst
{
////////////////////////////////////////////////////////////////
// MIME Types
////////////////////////////////////////////////////////////////
  
  /** Mime type for url encoded form submissions. */
  public static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";

  /** Mime type for multipart form submissions. */
  public static final String FORM_MULTI_PART = "multipart/form-data";
  
  /** Mime type for a <code>BHxView.update()</code> request. */
  public static final String UPDATE = "application/x-niagara-hx-update";

  /** Mime type for an event invocation. */
  public static final String EVENT = "application/x-niagara-hx-event";
  
  /** Header used to used to indentify HxOp path for an event. */
  public static final String EVENT_PATH = "x-niagara-hx-path";
  
  /** Header used to identify the event id for an event. */
  public static final String EVENT_ID = "x-niagara-hx-eventId";

  /** Header so specify the csrfToken */
  public static final String CSRF_TOKEN = "x-niagara-csrfToken";
}



