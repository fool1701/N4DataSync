/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.driver.history;

/**
 * An ArchiveException is thrown when an error occurs
 * during history archiving.
 *
 * @author    John Sublett
 * @creation  03 Sep 2002
 * @version   $Revision: 2$ $Date: 2/2/04 2:21:05 PM EST$
 * @since     Baja 1.0
 */
public class ArchiveException
  extends javax.baja.sys.BajaRuntimeException
{
  /**
   * Constructor with message and nested exception.
   */
  public ArchiveException(String msg, Throwable cause)
  {  
    super(msg, cause);
  }

  /**
   * Constructor with nested exception.
   */
  public ArchiveException(Throwable cause)
  {  
    super(cause);
  }

  /**
   * Constructor with message.
   */
  public ArchiveException(String msg)
  {  
    super(msg);
  }

  /**
   * Default constructor.
   */
  public ArchiveException()
  {  
  }
}