/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform;

import com.tridium.nre.security.PBEEncodingInfo;
import com.tridium.nre.security.PBEEncodingKey;
import com.tridium.nre.security.SecretChars;
import com.tridium.platform.daemon.BDaemonSession;
import com.tridium.platform.daemon.DaemonClientEncodingInfo;
import com.tridium.platform.daemon.message.FileTransferMessage;

import javax.baja.file.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Configuration options for file transfers performed by PlatformDaemon
 * 
 * @author    Matt Boon       
 * @creation  07 Feb 05
 * @version   $Revision: 6$ $Date: 3/20/06 11:15:09 AM EST$
 * @since     Baja 1.0
 */
public final class FileTransferOperation
{
  /**
   * Create an operation which will rename a remote file or directory
   * 
   * @param remoteSource FilePath for the remote file/directory to rename.
   * @param newName new name to give the file/directory.
   * The file that is created will have the same name as the source file.
   */
  public static FileTransferOperation makeRename(FilePath                   remoteSource,
                                                 String                     newName)
  {
    return new FileTransferOperation(FILE_TRANSFER_OPER_RENAME,
                                     new FilePath[] { remoteSource },
                                     remoteSource.getParent().merge(newName),
                                     false,
                                     null);
  }

  /**
   * Create an operation which will copy remote files and/or directories
   * to a specified directory on the local filesystem
   * 
   * Local directories will be created as necessary.
   * 
   * @param remoteSource FilePath for the remote file/directory to get.
   * @param localDestDir directory to which the remote file is to be copied.
   * The file that is created will have the same name as the source file.
   */
  public static FileTransferOperation makeGet(FilePath                   remoteSource,
                                              FilePath                   localDestDir)
  {
    return makeGet(new FilePath[] { remoteSource }, localDestDir);
  }

  /**
   * Create an operation which will copy remote files and/or directories
   * to a specified directory on the local filesystem
   * 
   * Local directories will be created as necessary.
   * 
   * @param remoteSource FilePaths for the remote files/directories to get.
   * @param localDestDir directory to which the remote files are to be copied.
   * The files that are created will have the same names as the source files.
   */
  public static FileTransferOperation makeGet(FilePath[]                 remoteSource,
                                              FilePath                   localDestDir)
  {
    return makeGet(remoteSource, localDestDir, true, BOverwritePolicy.different);
  }

  /**
   * Create an operation which will copy remote files and/or directories
   * to a specified directory on the local filesystem
   * 
   * Local directories will be created as necessary.
   * 
   * @param remoteSource FilePathfor the remote file/directory to get
   * @param localDestDir directory to which the remote file is to be copied.
   * The file that is created will have the same name as the source file.
   * @param recurseDirs if true, subdirectories of the source directory will
   * be included in the copy, otherwise only the file children of a directory
   * are copied.
   * @param overwritePolicy specifies how the transfer should handle existing
   * local files having the same names as copied remote files.
   */
  public static FileTransferOperation makeGet(FilePath                   remoteSource,
                                              FilePath                   localDestDir,
                                              boolean                    recurseDirs,
                                              BOverwritePolicy           overwritePolicy)
  {
    return makeGet(new FilePath[] { remoteSource }, localDestDir, recurseDirs, overwritePolicy);
  }

  /**
   * Create an operation which will copy remote files and/or directories
   * to a specified directory on the local filesystem
   * 
   * Local directories will be created as necessary.
   * 
   * @param remoteSource FilePaths for the remote files/directories to get
   * @param localDestDir directory to which the remote files are to be copied.
   * The files that are created will have the same names as the source files.
   * @param recurseDirs if true, subdirectories of any source directories will
   * be included in the copy, otherwise only the file children of a directory
   * are copied.
   * @param overwritePolicy specifies how the transfer should handle existing
   * local files having the same names as copied remote files.
   */
  public static FileTransferOperation makeGet(FilePath[]                 remoteSource,
                                              FilePath                   localDestDir,
                                              boolean                    recurseDirs,
                                              BOverwritePolicy           overwritePolicy)
  {
    return new FileTransferOperation(FILE_TRANSFER_OPER_GET,
                                     remoteSource,
                                     localDestDir,
                                     recurseDirs,
                                     overwritePolicy);
  }

  /**
   * Create an operation which will copy files and/or directories from
   * the local filesystem to a specified remote directory
   * 
   * Remote directories will be created as necessary.
   * 
   * @param localSource FilePath for the local file/directory to be sent
   * to the remote host
   * @param remoteDestDir directory to which the local file is to be copied.
   * The file that is created will have the same name as the source file.
   */
  public static FileTransferOperation makePut(FilePath                   localSource,
                                              FilePath                   remoteDestDir)
  {
    return makePut(new FilePath[] { localSource }, remoteDestDir);
  }

  /**
   * Create an operation which will copy files and/or directories from
   * the local filesystem to a specified remote directory
   * 
   * Remote directories will be created as necessary.
   * 
   * @param localSource FilePaths for the local files/directories to be sent
   * to the remote host
   * @param remoteDestDir directory to which the local files are to be copied.
   * The files that are created will have the same names as the source files.
   */
  public static FileTransferOperation makePut(FilePath[]                 localSource,
                                              FilePath                   remoteDestDir)
  {
    return makePut(localSource, remoteDestDir, true, BOverwritePolicy.different);
  }

  /**
   * Create an operation which will copy files and/or directories from
   * the local filesystem to a specified remote directory
   * 
   * Remote directories will be created as necessary.
   * 
   * @param localSource FilePath for the local file/directory to be sent
   * to the remote host
   * @param remoteDestDir directory to which the local file is to be copied.
   * The file that is created will have the same name as the source file.
   */
  public static FileTransferOperation makePut(FilePath                   localSource,
                                              FilePath                   remoteDestDir,
                                              boolean                    recurseDirs,
                                              BOverwritePolicy           overwritePolicy)
  {
    return makePut(new FilePath[] { localSource }, remoteDestDir, recurseDirs, overwritePolicy);
  }
  /**
   * Create an operation which will copy files and/or directories from
   * the local filesystem to a specified remote directory
   * 
   * Remote directories will be created as necessary.
   * 
   * @param localSource FilePaths for the local files/directories to be sent
   * to the remote host
   * @param remoteDestDir directory to which the local files are to be copied.
   * The files that are created will have the same names as the source files.
   */
  public static FileTransferOperation makePut(FilePath[]                 localSource,
                                              FilePath                   remoteDestDir,
                                              boolean                    recurseDirs,
                                              BOverwritePolicy           overwritePolicy)
  {
    return new FileTransferOperation(FILE_TRANSFER_OPER_PUT,
                                     localSource,
                                     remoteDestDir,
                                     recurseDirs,
                                     overwritePolicy);
  }

  /**
   * Create an operation that will delete files from the remote filesystem
   * 
   * @param remoteFiles FilePaths of the remote files and/or directories
   * to be deleted
   */
  public static FileTransferOperation makeDelete(FilePath[]                 remoteFiles)
  {
    return new FileTransferOperation(FILE_TRANSFER_OPER_DELETE,
                                     remoteFiles,
                                     null,
                                     true,
                                     null);
  }

  /**
   * Constructor
   */
  private FileTransferOperation(int                        operation,
                                FilePath[]                 source,
                                FilePath                   destinationDir,
                                boolean                    recurseDirs,
                                BOverwritePolicy           overwritePolicy)
  {
    this.operation       = operation;
    this.source          = source;
    this.destinationDir  = destinationDir;
    this.recurseDirs     = recurseDirs;
    this.overwritePolicy = overwritePolicy;
  }

  /**
   * Removes the passphrase supplier and other encryption related temporary information.
   * Is invoked by {@link FileManager#transfer(FileTransferOperation, IPlatformOperationListener)}
   * after the transfer is complete.
   *
   * Note: this may mean that when a single FileTransferOperation object is used with more than one
   * invocation of FileTransferOperation, it can succeed the first time and fail in the subsequent
   * invocations unless {@link #setPassPhraseSupplier(Supplier)} is used to restore the passphrase
   * supplier beforehand.
   *
   * @since Niagara 4.1
   */
  public void close()
  {
    passPhraseSupplier = null;
    pbeKey = null;
  }

  /**
   * Provides the operation with a way to obtain a file passphrase value if needed.  Not
   * used with Niagara AX files.
   *
   * @since Niagara 4.1
   */
  public void setPassPhraseSupplier(Supplier<char[]> value)
  {
    this.passPhraseSupplier = value;
  }

  /**
   * Framework use only
   *
   * @since Niagara 4.1
   */
  public void setPBEEncodingInfo(PBEEncodingInfo value)
    throws IOException
  {
    Objects.requireNonNull(passPhraseSupplier);
    Objects.requireNonNull(value);

    if (pbeKey != null)
    {
      if (pbeKey.getEncodingIterationCount() != value.getEncodingIterationCount() ||
          !pbeKey.getEncodingSaltHex().equals(value.getEncodingSaltHex()))
      {
        throw new IllegalStateException("Transfer has multiple encoded files, but encodings are incompatible");
      }
      return;
    }

    try(SecretChars passPhrase = new SecretChars(passPhraseSupplier.get(), true))
    {
      this.pbeKey = value.makePBEKey(passPhrase);
    }
  }

  /**
   * Framework use only
   *
   * @since Niagara 4.1
   */
  public void initializeFileTransferMessage(BDaemonSession session, FileTransferMessage message)
    throws IOException
  {
    Objects.requireNonNull(session);
    Objects.requireNonNull(message);

    if (pbeKey == null) return;

    try
    {
      DaemonClientEncodingInfo.makeMessageInitializer(session, Optional.of(pbeKey), Optional.empty())
                              .accept(message);
    }
    catch (IOException rethrow)
    {
      throw rethrow;
    }
    catch (Exception e)
    {
      throw new IOException(e);
    }
  }

  public int                        operation;
  public FilePath[]                 source;
  public FilePath                   destinationDir;
  public boolean                    recurseDirs;
  public BOverwritePolicy           overwritePolicy;

  private Supplier<char[]>          passPhraseSupplier = null;
  private PBEEncodingKey            pbeKey = null;

  public static final int FILE_TRANSFER_OPER_GET    = 0;
  public static final int FILE_TRANSFER_OPER_PUT    = 1;
  public static final int FILE_TRANSFER_OPER_DELETE = 2;
  public static final int FILE_TRANSFER_OPER_RENAME = 3;
}
