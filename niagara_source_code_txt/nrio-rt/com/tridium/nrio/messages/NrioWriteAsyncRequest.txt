/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;

import javax.baja.status.BStatusValue;
import javax.baja.util.ICoalesceable;
import com.tridium.nrio.BIWritable;

/**
 * The NrioWriteAsyncRequest class is used to initiate a call to 
 * writeData() on GeM6ProxyExts on the coalescing write worker thread.
 *
 * @author    Andy Saunders  
 * @creation  31 Jan 03
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$  
 * @since     Niagara 3.0 modbusAscii 1.0     
 */ 

public class NrioWriteAsyncRequest
  implements Runnable, ICoalesceable
{

  /**
   * Constructor
   */
  public NrioWriteAsyncRequest(BIWritable proxyExt, BStatusValue out)
  {
    this.hashCode = proxyExt.hashCode();
    this.proxyExt = proxyExt;
    this.out = out;
  }

 /**
  * Calls writeData() on the GeM6ProxyExt.
  */
  public void run()
  {
    if (proxyExt != null)
      proxyExt.writeData(out);
  }

////////////////////////////////////////////////////////////////
// ICoalesceable
////////////////////////////////////////////////////////////////
  
  /**
   * Hash code is based on the source BGpOutPutProxyExt.
   */
  public int hashCode()
  {                             
    return hashCode;
  }             
  
  /**
   * Equality is based on the source BGpOutPutProxyExt.
   */
  public boolean equals(Object object)
  {                                  
    if (object instanceof NrioWriteAsyncRequest)
    {                               
      NrioWriteAsyncRequest o = (NrioWriteAsyncRequest)object;
      return proxyExt == o.proxyExt;
    }
    return false;
  }
  
  /**
   * Return this.
   */
  public Object getCoalesceKey()
  {
    return this;
  }
  
  /**
   * Return c - last Invocation wins.
   */
  public ICoalesceable coalesce(ICoalesceable c)
  {                                 
    return c;  
  }
                 
////////////////////////////////////////////////////////////////
// Debug
////////////////////////////////////////////////////////////////
  
  /**
   * Get a String representation.
   */
  public String toString()
  {
    return "NrioWriteAsyncRequest (proxyExt=" + proxyExt + ", writeValue=" + out + ")";
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private int hashCode;
  private BIWritable proxyExt;
  private BStatusValue out;
  
}
