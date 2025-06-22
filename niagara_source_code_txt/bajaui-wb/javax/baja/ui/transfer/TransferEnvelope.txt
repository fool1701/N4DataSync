/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui.transfer;

import javax.baja.space.*;

/**
 * TransferEnvelope wraps the data to transfer between
 * applications using either the clipboard or drag and drop.
 * It supports access to the data in multiple formats,
 * each identified via a TransferFormat.
 *
 * @author    Brian Frank       
 * @creation  6 Mar 02
 * @version   $Revision: 4$ $Date: 3/28/05 10:32:40 AM EST$
 * @since     Baja 1.0
 */
public final class TransferEnvelope
{      

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////   

  /**
   * Make a TransferEnvelope instance for string data.
   */
  public static TransferEnvelope make(String string)
  {
    return new TransferEnvelope(
     new TransferFormat[] { TransferFormat.string },
     new Object[] { string }
    );
  }

  /**
   * Make a TransferEnvelope instance for a Mark.
   */
  public static TransferEnvelope make(Mark mark)
  {
    return new TransferEnvelope(
      new TransferFormat[] { TransferFormat.mark, TransferFormat.string },
      new Object[] { mark, mark.toStringFormat() }
    );
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Private for now.
   */
  private TransferEnvelope(TransferFormat[] formats, Object[] data)
  {
    this.formats = formats;
    this.data = data;
  }
 
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Does this envelope support the specified format 
   * using <code>getData(format)</code>.
   */
  public final boolean supports(TransferFormat format)
  {
    for(int i=0; i<formats.length; ++i)
      if (formats[i].equals(format))
        return true;
    return false;
  }

  /**
   * Get the formats this data may be transfered as.
   */
  public final TransferFormat[] getFormats()
  {
    return formats.clone();
  }
  
  /**
   * Get the data in the specified format.  If the envelope
   * doesn't support the specified format then throw
   * an UnsupportedFlavorException.
   */
  public final Object getData(TransferFormat format)
  {
    for(int i=0; i<formats.length; ++i)
      if (formats[i].equals(format))
        return data[i];
    throw new UnsupportedFormatException(format.toString());
  }
    
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private TransferFormat[] formats;
  private Object[] data;
  
}
