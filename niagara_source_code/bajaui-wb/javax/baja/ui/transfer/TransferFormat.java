/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.transfer;

import java.util.Objects;
import javax.baja.sys.*;

/**
 * TransferFormat encapsulates a format of data stored 
 * within a TransferEnvelope.
 *
 * @author    Brian Frank       
 * @creation  6 Mar 02
 * @version   $Revision: 1$ $Date: 1/13/03 11:00:06 AM EST$
 * @since     Baja 1.0
 */
public final class TransferFormat
{ 

////////////////////////////////////////////////////////////////
// Constants
//////////////////////////////////////////////////////////////// 

  /** The string format transfers a java.lang.String */
  public static final TransferFormat string = make("string");
  
  /** The mark format transfers a javax.baja.transfer.Mark
      which contains a selection of BObjects */
  public static final TransferFormat mark = make("mark");
  
////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////  

  /**
   * Private for now
   */
  private static TransferFormat make(String name)
  {
    return new TransferFormat(name);
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////   

  /**
   * Private constructor.
   */
  private TransferFormat(String name)
  {
    this.name = name;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Get the String identifier of this format.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get the human readable display name of this format.
   */
  public String getDisplayName(Context cx)
  {
    return name;
  }
  
  /**
   * TransferFormat's are equal if they have the same name.
   */
  @Override
  public boolean equals(Object object)
  {
    if (object instanceof TransferFormat)
    {
      TransferFormat x = (TransferFormat)object;
      return getName().equals(x.getName());
    }
    return false;
  }

  /**
   * Returns the hash of the object name.
   */
  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  /**
   * Return <code>getName()</code>.
   */
  @Override
  public String toString()
  {
    return name;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private String name;
                                                                
}
