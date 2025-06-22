/*
 * Copyright 2022 Tridium Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

/**
 * Abstract for a file space that can translate paths to a localized file system. Supports
 * target systems that may have a read-only Niagara Home directory by redirecting system files
 * that may be subject to modification to an alternate location.
 *
 * The following system file redirections are performed, including contained files and
 * subdirectories:
 *
 * <pre>{@code
 *   !modules  -> ~modules
 *   !security -> ~security
 * }</pre>
 *
 * @author M Swainston on 4/21/2022.
 * @since Niagara 4.13
 */
@NiagaraType
public abstract class BLocalizedFileSpace
  extends BFileSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BLocalizedFileSpace(2979906276)1.0$ @*/
/* Generated Thu Apr 21 16:09:52 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalizedFileSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLocalizedFileSpace(String name, LexiconText lexText)
  {
    super(name, lexText);
  }

  public BLocalizedFileSpace(String name)
  {
    super(name);
  }

  /**
   * Determines if the target system has a read-only Niagara Home directory.
   */
  public abstract boolean isNiagaraHomeReadOnly();

  /**
   * Identifies exceptional cases to files normally kept in Niagara Home for whenever Niagara Home
   * is read-only. These exceptions exist because the files and directories are subject to modification.
   */
  private boolean redirectToNiagaraUserHome(FilePath path)
  {
    // Exceptions are for system home contents and occur in certain subdirectories, only when
    // Niagara Home is read-only.
    if (path == null || !isNiagaraHomeReadOnly() || !path.isSysHomeAbsolute() || path.depth() < 1)
    {
      // This is not an exceptional condition.
      return false;
    }

    String directory = path.nameAt(0);

    // Exception 1: When Niagara Home is read-only, modules are moved to Niagara User Home.
    if ("modules".equals(directory))
    {
      return true;
    }

    // Exception 2: When Niagara Home is read-only, the security directory is moved to Niagara User Home.
    if ("security".equals(directory))
    {
      return true;
    }

    return false;
  }

  /**
   * When Niagara Home is read-only, redirect certain system file paths to an alternate writable
   * location. For example, !modules and !security will be redirected to ~modules and ~security
   * respectively.
   * @param path the original file path
   * @return the redirected file path if the target system requires redirection;
   * otherwise the original file path
   * @since Niagara 4.13
   */
  protected FilePath getLocalizedFilePath(FilePath path)
  {
    return redirectToNiagaraUserHome(path) ? switchToUserHome(path) : path;
  }

  private FilePath switchToUserHome(FilePath path)
  {
    String body = path.getBody();
    if (body != null && !body.isEmpty() && body.charAt(0) == '!')
    {
      return new FilePath("~" + body.substring(1));
    }
    return path;
  }

  /**
   * Routes to findStore() and makeFile(), redirecting system files if needed.
   */
  @Override
  public BIFile findFile(FilePath path)
  {
    return super.findFile(getLocalizedFilePath(path));
  }

  /**
   * This method calls findFile(path), redirecting system files if needed.  If null is
   * returned then UnresolvedException() is thrown.
   */
  @Override
  public BIFile resolveFile(FilePath path)
  {
    return super.resolveFile(getLocalizedFilePath(path));
  }
}
