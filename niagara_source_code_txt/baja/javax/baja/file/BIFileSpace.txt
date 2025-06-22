/*
 * Copyright 2013 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.file;

import java.io.IOException;

import javax.baja.agent.BIAgent;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BIProtected;
import javax.baja.security.BPermissions;
import javax.baja.space.BISpace;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * File space interface.
 *
 * @author Dan Heine
 * @creation 2013-06-27
 * @since Niagara 4.0
 */
@NiagaraType
public interface BIFileSpace
  extends BISpace, BIAgent, BIDirectory, BIProtected
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BIFileSpace(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIFileSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// API
////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>makeDir(path, null)</code>
   */
  BDirectory makeDir(FilePath path)
    throws IOException;

  /**
   * Convenience for <code>makeFile(path, null)</code>
   */
  BIFile makeFile(FilePath path)
    throws IOException;

  /**
   * Convenience for <code>move(from, to, null)</code>
   */
  void move(FilePath from, FilePath to)
    throws IOException;

  /**
   * Convenience for <code>delete(path, null)</code>
   */
  void delete(FilePath path)
    throws IOException;

  /**
   * Get an absolute ord for a file path within this space.
   */
  BOrd getAbsoluteOrd(FilePath filePath);

  /**
   * Get an ord relative to the host for a file path within this space.
   */
  BOrd getOrdInHost(FilePath filePath);

  /**
   * Get an ord relative to the session for a file path within this space.
   */
  BOrd getOrdInSession(FilePath filePath);

  /**
   * Routes to findStore() and makeFile().
   */
  BIFile findFile(FilePath path);

  /**
   * This method calls findFile(path).  If null is
   * returned then UnresolvedException() is thrown.
   */
  BIFile resolveFile(FilePath path);

  /**
   * Given an implementation of file store, create the proper
   * type of BIFile.  The standard implementation of this method
   * uses the registry to map the store's file extension to
   * a type of BIFile.  Once the instance is created, the store
   * is set using BIFile.setStore().  If the store is a directory
   * then return an instance of BDirectory.  If the file has
   * no extension, then return an instance of BDataFile.
   */
  BIFile makeFile(BIFileStore store);

  /**
   * Get the permissions for a FilePath.  This method works
   * for both existing paths and non-existing paths making
   * it useful to pre-check operations which are going to create
   * new files.
   */
  BPermissions getPermissionsFor(FilePath path, Context cx);

  /**
   * If the result of <code>getPermissionsFor(path, cx)</code>
   * doesn't contain operator read then throw a PermissionException.
   */
  void checkReadPermission(FilePath path, Context cx);

  /**
   * If the result of <code>getPermissionsFor(path, cx)</code>
   * doesn't contain operator write then throw a PermissionException.
   */
  void checkWritePermission(FilePath path, Context cx);
}
