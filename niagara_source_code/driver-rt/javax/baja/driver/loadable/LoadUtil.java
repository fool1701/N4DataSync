/*
 * Copyright 2000 Tridium Inc. All Rights Reserved.
 */
package javax.baja.driver.loadable;

import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;


/**
 * This class contains static methods used to support 
 * uploading and downloading classes which implement 
 * the BILoadable interface.
 *  
 * @author    Robert Adams
 * @creation  4 Feb 02
 * @version   $Revision: 7$ $Date: 2/11/04 4:14:42 PM EST$
 * @since     Niagara 3.0
 */
public class LoadUtil
{  

  /**
   * Call doUpload on all the ILoadable children.
   */
  public static void uploadChildren(BComponent comp, BUploadParameters params, Context cx)
    throws Exception
  { 
    SlotCursor<Property> c = comp.getProperties();
    while(c.nextComponent())
    {
      BObject kid = c.get();  
      if (kid instanceof BILoadable)
        ((BILoadable)kid).doUpload(params, cx);
    }
  }
  
  /**
   * Call doDownload on all the ILoadable children.
   */
  public static void downloadChildren(BComponent comp, BDownloadParameters params, Context cx)
    throws Exception
  { 
    SlotCursor<Property> c = comp.getProperties();
    while(c.nextComponent())
    {
      BObject kid = c.get();  
      if (kid instanceof BILoadable)
        ((BILoadable)kid).doDownload(params, cx);
    }
  }

  
}
