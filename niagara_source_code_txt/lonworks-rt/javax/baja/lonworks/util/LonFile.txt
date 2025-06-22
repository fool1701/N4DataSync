/*
 * Copyright 2000 Tridium Inc. All Rights Reserved.
 */
package javax.baja.lonworks.util;

import com.tridium.lonworks.device.DeviceFacets;
import com.tridium.lonworks.file.LonFileReadWrite;
import com.tridium.lonworks.file.LonFileTransfer;
import com.tridium.lonworks.file.NoDirectoryException;

import javax.baja.lonworks.BINetworkVariable;
import javax.baja.lonworks.BLonDevice;
import javax.baja.lonworks.BNetworkVariable;
import javax.baja.lonworks.LonException;
import javax.baja.lonworks.enums.BLonSnvtType;

/**
 *    This abstract class defines an api for accessing
 *  files in lonworks devices.  
 * <p>
 *  
 * @author    Robert Adams
 * @creation  25 June 01
 * @version   $Revision: 3$ $Date: 10/18/01 2:56:45 PM$
 * @since     Niagara 3.0
 */
public abstract class LonFile
{

////////////////////////////////////////////////////////////
//  Constructor
////////////////////////////////////////////////////////////
  /**
   *  Factory method for lonfiles. <p>
   *  @param dev the londevice containing file(s).
   *  @return the appropriate file type for the dev.
   */
  public static LonFile createFile (BLonDevice dev)
    throws LonException
  {
    LonFile file = null;
    BNetworkVariable dirNv;
    BNetworkVariable reqNv;
    BNetworkVariable statNv;
    BINetworkVariable[] nvs = dev.getNetworkVariables();

    //
    // Determine file access type by checking available snvts
    // in the node object:
    //  - if node object contains nv8 fileDirectory then use
    //    readWrite file access
    //  - if node object contains nv5 fileReq and nv6 fileStatus
    //    then use fileTransfer access
    //  - if fileTransfer device also contains nv7 filePos then
    //    use random access fileTransfer access
    if( (dirNv = findNvByObjectAndType(dev, nvs,BLonSnvtType.SNVT_ADDRESS)) != null )
    {
      // Using readWrite access
      file = new LonFileReadWrite(dev,dirNv);
    }
    else if( ( (reqNv = findNvByObjectAndType(dev, nvs,BLonSnvtType.SNVT_FILE_REQ)) != null ) &&
             ( (statNv = findNvByObjectAndType(dev, nvs,BLonSnvtType.SNVT_FILE_STATUS)) != null ) )
    {
      // Using fileXfer access
      BNetworkVariable posNv = findNvByObjectAndType(dev, nvs,BLonSnvtType.SNVT_FILE_POS);
      try
      {
        file = new LonFileTransfer(dev, reqNv, statNv, posNv);
      } 
      catch(NoDirectoryException e) 
      {
        dev.log().warning(dev.getDisplayName(null) + " has file snvts but reports no file directory"); 
        return null; 
      }  
    }
    else
    {
      // No file access
      return null;
    }
    
    return file;
  }

  private static BNetworkVariable findNvByObjectAndType(BLonDevice dev, BINetworkVariable[] nvs, int snvtType)
  {
    // NodeObject ndx is normally 0 - may be facet to override this
    int nodObjNdx = DeviceFacets.getNodeObjectIndex(dev);
    
    for(int i=0 ; i<nvs.length ; i++)
    {
      if((nvs[i]==null) || !nvs[i].isNetworkVariable()) continue;
      BNetworkVariable nv = (BNetworkVariable)nvs[i];
      if( (nv.getNvProps().getObjectIndex() == nodObjNdx) &&
          (nv.getSnvtType()==snvtType) )
      {
        return nv;
      }
    }

    // index not found
    return null;
  }
  
  /**
   *  Create a copy of this file. This will copy the appropriate nv
   *  references and the template file.
   */
  public abstract LonFile copy();

////////////////////////////////////////////////////////////
//  File Access Methods
////////////////////////////////////////////////////////////
  /**
  *  Open will do any initial processing required
  *  to make a file in and lonworks device accessible.
  * <p>
  * @param  file      the index of the file in the lon
  *                    device
  * @param  newFile   if true then create a zeroed file
  *                     which can then be modified as needed.
  * @param  randomAccess  set false when the entire file is to
  *                        be updated.  This will allow more
  *                        efficient handling of files using
  *                        fileXfer access.	
  **/
  public abstract void open(int file, boolean newFile, boolean randomAccess)
        throws LonException;

  /**
  *  Read length bytes from an opened file at the
  *  specified offset. <p>
  *
  * @param  offset  offset from beginning of file to
  *                   first byte to read
  * @param  length  number of bytes to read
  **/
  public abstract byte[] read( int offset, int length)
        throws LonException;

  /**
  *  Read length bytes from an opened file beginning
  *  atthe byte following the last byte read.
  * <p>
  * @param  length  number of bytes to read
  **/
  public abstract byte[] read( int length )
        throws LonException;

  /**
  *  Read the entire file.
  **/
  public abstract byte[] read()
        throws LonException;

  /**
  *  Write the given byte array to an opened file at the
  *  specified offset.
  * <p>
  * @param  offset  offset from beginning of file to
  *                   position of first byte to write
  **/
  public abstract void write(byte[] data, int offset)
        throws LonException;

  /**
  *  Write the given byte array to an opened file 
  *  beginning at the position following the last
  *  byte written to the file.
  **/
  public abstract void write(byte[] data)
        throws LonException;

  /**
  *  Do any post processing to finalize any access
  *  to a file in a lonworks device.  If data was
  *  written to the file which has not been committed
  *  in the physical device then complete the update.
  **/
  public abstract void close()
        throws LonException;

  /**
  *  Write any data to the device which has not 
  *  already been written to the physical device.
  **/
  public abstract void flush()
        throws LonException;

  /**
  *  Get a string discribing the files available
  *  for access.
  **/
  public abstract String getDirectoryString()
        throws LonException;

  /**
  * Find the first file of the specified type.
  * <p>
  * @return The index of the first file of specified type or -1 if none.
  */
  public abstract int findFileNum(int type);
  /**
  * Find the first file of the specified type begining
  * at "lastFile".  This allows discovery of index for multiple
  * files of the same type.
  * <p>
  * @return The index of the next file of specified type or -1 if none.
  */
  public abstract int findFileNum(int type, int lastFile);

  /**
   * Is the file already opened.
   */
  public abstract boolean isOpen();
  
  /** return true if the files supports random access. */
  public boolean supportsRandomAccess() {return true;}

////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////
  // Flags used in lonFile open() calls for readability 
  public static final boolean ALLOW_RANDOM_ACCESS = true;
  public static final boolean NO_RANDOM_ACCESS = false;
  
  /** Static used in open(). Create a blank copy of file. */
  public static final boolean CREATE_FILE  = true;
  /** Static used in open(). Read copy of before making changes. */
  public static final boolean ACCESS_FILE  = false;
  
  // Lon file index
  /** Index of config template file. */
  public static final int CONFIG_TEMPLATE_FILE    = 0;
  /** Index of read write config parameter file. */
  public static final int READ_WRITE_CONFIG_FILE  = 1;
  /** Index of read only config parameter file if it exists in device. */
  public static final int READ_ONLY_CONFIG_FILE   = 2;

  // Lon file type
  /** Comfig parameter template file type. */
  public static final int CONFIG_PARAM_TEMPLATE_FILE  = 2;
  /** Comfig parameter vale file type. */
  public static final int CONFIG_PARAM_VALUE_FILE     = 1;
 
 // private BLonDevice  device = null;
}