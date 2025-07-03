/*
 * Copyright 2008 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

/**
 * Used to communicate to a long-running operation whether
 * it should continue or cancel processing.
 * 
 * @author    Matt Boon       
 * @creation  5 Feb 08
 * @version   $Revision: 7$ $Date: 2/13/08 1:43:35 PM EST$
 * @since     Baja 1.0
 */
public interface ICancelHint
{
  /**
   * Return true if the cancelable operation should terminate.
   * 
   * Some operations check isCanceled() more frequently than others,
   * and operations aren't required to check at all.
   */
  boolean isCanceled();

  /**
   * A long-running method may throw CanceledException to signify
   * that its invocation did not complete
   */
  class CanceledException
    extends javax.baja.sys.LocalizableRuntimeException 
  {                                    
    public CanceledException() { super("platform", "CanceledException"); }
  }
}
