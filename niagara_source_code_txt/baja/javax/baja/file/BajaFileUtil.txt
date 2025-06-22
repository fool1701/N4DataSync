/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Logger;

import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.SyntaxException;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.util.FileUtil;
import javax.baja.security.PermissionException;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.util.LexiconModule;

import com.tridium.sys.Nre;

/**
 * FileUtil provides handy utility methods for
 * implementation of BIFile.
 *
 * @author    Brian Frank on 24 Jan 03
 * @version   $Revision: 20$ $Date: 8/14/09 9:56:04 AM EDT$
 * @since     Baja 1.0
 */
public class BajaFileUtil
  extends FileUtil
{

////////////////////////////////////////////////////////////////
// IO
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code read(f.getInputStream(), f.getSize())}.
   */
  public static byte[] read(BIFileStore f)
    throws IOException
  {
    return read(f.getInputStream(), f.getSize());
  }

  /**
   * Write the specified byte array to the file's output stream.
   */
  public static void write(BIFileStore f, byte[] contents)
    throws IOException
  {
    try (OutputStream out = f.getOutputStream())
    {
      out.write(contents);
    }
  }

  /**
   * Pipe data from one file to another.
   */
  public static void pipe(BIFile src, BIFile dest)
    throws IOException
  {
    try (InputStream in = src.getInputStream();
         OutputStream out = dest.getOutputStream())
    {
      long size = src.getSize();
      if (size >= 0)
      {
        pipe(in, size, out);
      }
      else
      {
        pipe(in, out);
      }
    }
  }

  /**
   * Public interface for file system operations. You can use this in
   * production as well as mock it for testing purposes.
   * @since Niagara 4.8
   */
  public interface BajaFileWriter
  {
    /**
     * Create a new file.
     *
     * @param fileOrd ORD to the file to be created
     * @param fileContents stream of the file contents. Will be consumed, but
     *                     up to the caller to close.
     * @param cx security context
     * @throws FileAlreadyExistsException if the file already exists
     * @throws IOException if the file could not be written
     */
    void createFile(BOrd fileOrd, InputStream fileContents, Context cx) throws IOException;

    /**
     * Create or replace an existing file.
     *
     * @param fileOrd ORD to the file to be replaced
     * @param fileContents stream of the file contents. Will be consumed, but
     *                     up to the caller to close.
     * @param cx security context
     * @throws IOException if the file could not be written
     */
    void replaceFile(BOrd fileOrd, InputStream fileContents, Context cx) throws IOException;

    /**
     * @param fileOrd ORD to the file to be checked
     * @param cx security context
     * @return true if the file exists
     */
    boolean exists(BOrd fileOrd, Context cx);

    /**
     * @param ord ORD to the file to be checked
     * @param cx security context
     * @return true if the file can be written to
     */
    boolean isWritable(BOrd ord, Context cx);
  }

  /**
   * Get a file writer that will work for most cases. Operator Write permissions
   * will be required on any file space to be written to.
   *
   * @return a default file writer instance
   * @since Niagara 4.8
   */
  public static BajaFileWriter getDefaultFileWriter()
  {
    return DEFAULT_FILE_WRITER;
  }

  /**
   * Create a file, possibly overwriting any existing file.
   *
   * @param fileOrd ORD to the desired file, including file name - will be
   *                resolved using default ORD base
   * @param cx the context to resolve the file ORD
   * @return a file to write to; any existing file will be overwritten
   * @throws IOException if the file could not be created
   * @since Niagara 4.8
   */
  public static BIFile createFileToOverwrite(BOrd fileOrd,
                                             Context cx)
    throws IOException
  {
    return createFileToOverwrite(fileOrd, null, null, cx);
  }

  /**
   * Create a file, possibly overwriting any existing file.
   *
   * @param fileOrd ORD to the desired file
   * @param ext desired file extension
   * @param base the base object to resolve the file ORD
   * @param cx the context to resolve the file ORD
   * @return a file to write to; any existing file will be overwritten
   * @throws IOException if the file could not be created
   * @since Niagara 4.8
   */
  public static BIFile createFileToOverwrite(BOrd fileOrd,
                                             String ext,
                                             BObject base,
                                             Context cx)
    throws IOException
  {
    return createFile(fileOrd, ext, true, base, cx);
  }

  /**
   * Create a file.
   *
   * @param fileOrd ORD to the desired file, including file name - will be
   *                resolved against default ORD base
   * @param cx the context to resolve the file ORD
   * @return a file to write to
   * @throws FileAlreadyExistsException if the file already exists
   * @throws IOException if the file could not be created
   * @since Niagara 4.8
   */
  public static BIFile createFile(BOrd fileOrd,
                                  Context cx)
    throws IOException
  {
    return createFile(fileOrd, null, null, cx);
  }

  /**
   * Create a file.
   *
   * @param fileOrd ORD to the desired file
   * @param ext desired file extension
   * @param base the base object to resolve the file ORD
   * @param cx the context to resolve the file ORD
   * @return a file to write to
   * @throws FileAlreadyExistsException if the file already exists
   * @throws IOException if the file could not be created
   * @since Niagara 4.8
   */
  public static BIFile createFile(BOrd fileOrd,
                                  String ext,
                                  BObject base,
                                  Context cx)
    throws IOException
  {
    return createFile(fileOrd, ext, false, base, cx);
  }

  private static BIFile createFile(BOrd desiredOrd,
                                   String ext,
                                   boolean overwrite,
                                   BObject base,
                                   Context cx)
    throws IOException
  {
    String ordStr = String.valueOf(desiredOrd);
    if ("null".equals(ordStr) || ordStr.isEmpty())
    {
      // Illegal filename
      throw new IllegalArgumentException("Filename required");
    }

    // Append extension if not already included
    if (ext != null && !ordStr.endsWith(ext))
    {
      ordStr += "." + ordStr;
    }

    BOrd ord = BOrd.make(ordStr);
    try
    {
      BObject obj = ord.get(base, cx);
      if (!overwrite)
      {
        throw new FileAlreadyExistsException(ordStr + " already exists");
      }
      return (BIFile) obj;
    }
    catch (UnresolvedException e)
    {
      // Does not exist, create it.
      return new SpaceAndPath(ord, cx).makeFile();
    }
    catch (SyntaxException e)
    {
      throw new IllegalArgumentException("Illegal filename: \"" + ordStr + "\"");
    }
  }

  /**
   * Create a temporary file that will be deleted when the JVM exists.
   *
   * @param ext desired file extension
   * @return a temporary file
   * @since Niagara 4.8
   */
  public static File createTempFile(String ext) throws IOException
  {
    TEMP_FILES_DIRECTORY.mkdirs();
    File file = File.createTempFile("temp", "." + ext, TEMP_FILES_DIRECTORY);
    file.deleteOnExit();
    return file;
  }

///////////////////////////////////////////////////////////////////
// String I/O
///////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code readString(new InputStreamReader(file.getInputStream()))}.
   */
  public static String readString(BIFile file)
    throws IOException
  {
    return readString(new InputStreamReader(file.getInputStream()));
  }

  /**
   * Convenience for {@code readLines(new InputStreamReader(file.getInputStream()))}.
   */
  public static String[] readLines(BIFile file)
    throws IOException
  {
    return readLines(new InputStreamReader(file.getInputStream()));
  }


  /**
   * Is the given module a 'special' module that lives in /bin/ext ?
   * Env.getSpecialModuleJar() in build.jar uses a fixed list of
   *  build, niagarad, nre, cryptoCore
   */
  public static boolean isSpecialModule(String name)
  {
    return AccessController.doPrivileged((PrivilegedAction<Boolean>) () -> {
      File binDir = new File(Nre.getNiagaraHome(), "bin");
      File binExtDir = new File(binDir, "ext");
      File file = find(binExtDir, name + ".jar");
      return file != null && file.exists();
    });
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final BajaFileWriter DEFAULT_FILE_WRITER = new BajaFileWriter()
  {
    @Override
    public void createFile(BOrd fileOrd, InputStream fileContents, Context cx) throws IOException
    {
      try (OutputStream out = BajaFileUtil.createFile(fileOrd, cx).getOutputStream())
      {
        FileUtil.pipe(fileContents, out);
      }
    }

    @Override
    public void replaceFile(BOrd fileOrd, InputStream fileContents, Context cx) throws IOException
    {
      try (OutputStream out = createFileToOverwrite(fileOrd, cx).getOutputStream())
      {
        FileUtil.pipe(fileContents, out);
      }
    }

    @Override
    public boolean exists(BOrd fileOrd, Context cx)
    {
      return new SpaceAndPath(fileOrd, cx).exists();
    }

    @Override
    public boolean isWritable(BOrd fileOrd, Context cx)
    {
      return new SpaceAndPath(fileOrd, cx).isWritable(cx);
    }
  };

  /**
   * Parse a file space and file path from a file ORD. This can be used for
   * permissions checks, existence checks, etc.
   */
  private static class SpaceAndPath
  {
    private SpaceAndPath(BOrd fileOrd, Context cx)
    {
      OrdQuery[] q = fileOrd.normalize().parse();
      if (q.length == 0)
      {
        throw new IllegalArgumentException(LEX.getText("NotAFileOrd", cx, fileOrd));
      }

      OrdQuery last = q[q.length - 1];

      if (!(last instanceof FilePath))
      {
        throw new IllegalArgumentException(LEX.getText("NotAFileOrd", cx, fileOrd));
      }

      path = (FilePath) last;
      q[q.length - 1] = new FilePath("");
      space = (BIFileSpace) BOrd.make(q).resolve().get();
    }

    /**
     * @return true if the file exists
     */
    private boolean exists()
    {
      return space.findFile(path) != null;
    }

    /**
     * @param cx security context
     * @return true if the file can be written
     */
    private boolean isWritable(Context cx)
    {
      try
      {
        space.checkWritePermission(path, cx);
        return true;
      }
      catch (PermissionException e)
      {
        return false;
      }
    }

    /**
     * @return the created file
     * @throws IOException if the file could not be created
     */
    private BIFile makeFile() throws IOException
    {
      return space.makeFile(path);
    }

    BIFileSpace space;
    FilePath path;
  }

  static Logger log = Logger.getLogger("sys.file");

  private static final LexiconModule LEX = LexiconModule.make("baja");
  private static final String TMP_DIR =
    AccessController.doPrivileged((PrivilegedAction<String>)
      () -> System.getProperty("java.io.tmpdir"));
  private static final File TEMP_FILES_DIRECTORY = new File(TMP_DIR);
}
