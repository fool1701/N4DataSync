/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;
 
import javax.baja.security.*;
import javax.baja.file.*;

import java.net.*;
import java.io.*;

/**
 * Interface for PlatformDaemon file access and transfer functions
 * 
 * A FileManager instance can be obtained by calling 
 * PlatformDaemon.getFileManager()
 * 
 * @author    Matt Boon       
 * @creation  24 Feb 05
 * @version   $Revision: 5$ $Date: 12/14/05 10:16:00 AM EST$
 * @since     Baja 1.0
 */
public interface FileManager
{
  /**
   * Returns a read-only file space which exposes the files on the remote
   * host
   */
  BFileSpace getFileSpace()
    throws ConnectException,
           AuthenticationException;
  
  /**
   * Perform a file transfer operation
   */
  void transfer(FileTransferOperation      transferOperation,
                IPlatformOperationListener listener)
    throws AuthenticationException,
           IOException;
}

