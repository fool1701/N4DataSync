/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.history;

/**
 * Implementing classes 
 * 
 * @author    Matt Boon
 * @creation  24 Mar 08
 * @version   $Revision: 1$ $Date: 3/25/08 11:22:35 AM EDT$
 * @since     Niagara 3.4
 */
public interface ITruncatable
{
  /**
   * Truncate the object such that it can be written to a DataOutput using the given maximum number of bytes 
   * or fewer.
   * 
   * @return true if the truncation succeeded, or false if truncation is not supported or no truncated record 
   * can be encoded in maxBytes or fewer.
   */
  public boolean truncate(int maxBytes);
}
