/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.nre.util;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Spliterator;
import java.util.TimeZone;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.CRC32;
import com.tridium.nre.platform.PlatformUtil;

/**
 * FileUtil provides handy utility methods for
 * implementation of BIFile.
 *
 * @author    Brian Frank
 * @creation  24 Jan 03
 * @version   $Revision: 20$ $Date: 8/14/09 9:56:04 AM EDT$
 * @since     Baja 1.0
 */
public class FileUtil
{

////////////////////////////////////////////////////////////////
// Naming
////////////////////////////////////////////////////////////////

  /**
   * Get the base name without the extension for the
   * specified file name.  The extension appears after
   * the last '.' in the file name.  Return fileName if
   * no '.' appears in the file name.
   */
  public static String getBase(String fileName)
  {
    if (fileName == null) return null;
    int x = fileName.lastIndexOf('.');
    if (x >= 1)
      return fileName.substring(0, x);
    else
      return fileName;
  }

  /**
   * Get the extension for the specified file name.
   * The extension appears after the last '.' in
   * the file name.  Return null if no '.' appears
   * in the file name.
   */
  public static String getExtension(String fileName)
  {
    if (fileName == null) return null;
    int x = fileName.lastIndexOf('.');
    if (x >= 1)
      return fileName.substring(x+1);
    else
      return null;
  }


  /**
   * Replace the extension of the specified filename with a new extension, or add an
   * extension if the filename has no extension.
   *
   * Note: As *nix filenames starting with "." are hidden files, files with only a single period
   * at the start are treated as a filename with no extension, and an extension will be
   * added.
   *
   * @param fileName the file name in which the extension should be replaced
   * @param newExtension the new file extension (without the ".")
   * @return the file name, with the extension replaced or added
   *
   * @since Niagara 4.8
   */
  public static String replaceExtension(String fileName, String newExtension)
  {
    if (fileName == null)
    {
      return null;
    }
    // don't add an extension to an empty file to make this an idempotent operation
    // Otherwise, 2 or more calls produce different results (e.g.. "" -> ".abc" -> ".abc.abc")
    if (fileName.length() == 0)
    {
      return fileName;
    }
    int x = fileName.lastIndexOf('.');
    if (x >= 1)
    {
      return fileName.substring(0, x + 1) + newExtension;
    }
    else
    {
      return fileName + "." + newExtension;
    }
  }

////////////////////////////////////////////////////////////////
// IO
////////////////////////////////////////////////////////////////

  /**
   * Read the specified number of bytes from the input
   * stream into a byte array.  The stream is closed.
   */
  public static byte[] read(InputStream in, long size)
    throws IOException
  {
    try (InputStream input = in)
    {
      if (size < 0 || size > Integer.MAX_VALUE)
        throw new IOException("Invalid size " + size);

      int sz = (int)size;
      byte[] buf = new byte[sz];

      int count = 0;
      while (count < sz)
      {
        int n = input.read(buf, count, sz - count);
        if (n < 0)
          throw new IOException("Unexpected EOF");
        count += n;
      }

      return buf;
    }
  }

  /**
   * Read the specified number of bytes off the given input
   * stream to the specified output stream.  This does not
   * close either the input or output stream.
   */
  public static void pipe(InputStream in, long size, OutputStream out)
    throws IOException
  {
    int len = 4096;
    byte[] buf = new byte[len];
    while(size > 0)
    {
      int n = in.read(buf, 0, (int)Math.min(size, len));
      if (n <= 0)
        throw new IOException("Unexpected EOF");
      out.write(buf, 0, n);
      size -= n;
    }
  }

  /**
   * Read the specified number of bytes off the given input
   * stream to the specified output stream until the input
   * stream returns -1.  This does not close either the input
   * or output stream.
   */
  public static void pipe(InputStream in, OutputStream out)
    throws IOException
  {
    int len = 4096;
    byte[] buf = new byte[len];
    while(true)
    {
      int n = in.read(buf, 0, len);
      if (n < 0) break;
      out.write(buf, 0, n);
    }
  }

  /**
   * Return the calculated CRC
   *
   * @since Niagara 3.7
   */
  public static long getCrc(File file)
  {
    long crc = 0;

    try
    {
      boolean isFileRegular = AccessController.doPrivileged((PrivilegedAction<Boolean>)() ->
        PlatformUtil.getPlatformProvider().isFileRegular(file.getAbsolutePath()));

      //Only attempt a CRC calculation if the file "regular", having opaque content
      if (isFileRegular)
      {
        FileInputStream fin = new FileInputStream(file);
        crc = getCrc(fin);
        fin.close();
      }
    }
    catch (Exception ignored)
    {
      //Reset the CRC
      crc = 0;
    }

    return crc;
  }

  /**
   * Return the calculated CRC
   *
   * @since Niagara 3.5
   */
  public static long getCrc(InputStream in)
    throws IOException
  {
    try
    {
      int numRead;
      byte[] buf = new byte[4096];
      CRC32 crcObj = new CRC32();
      while ((numRead = in.read(buf)) > 0)
      {
        crcObj.update(buf, 0, numRead);
      }
      return crcObj.getValue();
    }
    finally
    {
      if (in != null) in.close();
    }
  }

////////////////////////////////////////////////////////////////
// java.io.File
////////////////////////////////////////////////////////////////

  /**
   * Make a backup of the save file, taking into account
   * the proper number of backups configured.  All backup
   * files are assumed to be in the same directory and
   * have the same prefix as the save file.  This method
   * automatically deletes any previous backups if they
   * exceed the maxBackup count.  Return the new name for
   * saveFile or null if the backup fails.
   */
  public static File renameToBackup(File saveFile, int maxBackups)
  {
    try
    {
      //  Prefix is everything before first .
      String name = saveFile.getName();
      String prefix = name.substring(0,name.indexOf('.'));

      // delete all the oldest backups up to max
      File[] backups = getBackups(saveFile.getParentFile(), prefix);
      for(int i=Math.max(0, maxBackups-1); i<backups.length; ++i)
        backups[i].delete();

      // make newest backup from current save file
      if (maxBackups != 0)
        return FileUtil.renameToBackup(saveFile);
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Given a File, rename it as a backup file by renaming with the
   * format "{orig}_backup_yyMMdd_HHmm.{ext}".  Return the backup file
   * or null if file doesn't exist.
   */
  public static File renameToBackup(File file)
    throws Exception
  {
    if (!file.exists()) return null;

    File parent = new File(file.getParent());
    String name = file.getName();
    String ext = "";
    int dot = name.lastIndexOf('.');
    if (dot > 0)
    {
      ext = name.substring(dot); // include dot
      name = name.substring(0, dot);
    }

    String pattern = "yyMMdd_HHmm";
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    format.setTimeZone(TimeZone.getDefault());
    String ts = format.format(new Date(file.lastModified()));

    File newFile = new File(parent, name + "_backup_" + ts + ext);
    for(int i=1; newFile.exists(); ++i)
      newFile = new File(parent, name + "_backup_" + ts + "_" + i + ext);
    if (!file.renameTo(newFile)) throw new IOException("Cannot rename");
    return newFile;
  }

  /**
   * Get the list of backups files in the given directory with
   * the given prefix.  Backups are sorted by age. Index 0 is the
   * newest backup and length-1 is the oldest backup file.
   */
  public static File[] getBackups(File dir, String prefix)
  {
    ArrayList<File> result = new ArrayList<>();
    File[] list = dir.listFiles();
    if (list != null)
    {
      for (File f : list)
      {
        String name = f.getName();

        // make sure begins with prefix
        if (!name.startsWith(prefix)) continue;

        // backups should include "_backup_"
        if (!name.contains("_backup_")) continue;

        // got it
        result.add(f);
      }
    }

    // get vector to array
    File[] sorted = result.toArray(new File[result.size()]);

    // sort by timestamp
    Long[] times = new Long[sorted.length];
    for(int i=0; i<sorted.length; ++i) times[i] = sorted[i].lastModified();
    SortUtil.rsort(times, sorted);

    return sorted;
  }

  /**
   * Move file
   */
  public static void move(File oldFile, File newFile)
    throws IOException
  {
    move(oldFile, newFile, false);
  }

  public static void move(File oldFile, File newFile, boolean deleteExisting)
    throws IOException
  {
    log.fine("Move: " + oldFile + " -> " + newFile);

    if (newFile.exists())
    {
      if (!deleteExisting)
      {
        throw new IOException("Cannot move to existing file: " + newFile);
      }
      else
      {
        if (!newFile.delete())
          throw new IOException("Cannot move: " + oldFile + " -> " + newFile + ", failed to delete existing");
      }
    }

    Files.move(oldFile.toPath(), newFile.toPath(), StandardCopyOption.ATOMIC_MOVE);
  }

  /**
   * Copy a directory or a data file by routing
   * to copyDir() or copyFile().
   */
  public static void copy(File oldFile, File newFile)
    throws IOException
  {
    log.fine("Copy: " + oldFile + " -> " + newFile);
    if (newFile.exists())
      throw new IOException("Cannot copy to existing file: " + newFile);
    if (oldFile.isDirectory())
      copyDir(oldFile, newFile);
    else
      copyFile(oldFile, newFile);
  }

  /**
   * Copy directory recursively.
   */
  public static void copyDir(File oldFile, File newFile)
    throws IOException
  {
    if (!newFile.exists() && !newFile.mkdirs())
      throw new IOException("Cannot make dir: " + newFile);

    File[] kids = oldFile.listFiles();
    if (kids != null)
    {
      for (File kid : kids) copy(kid, new File(newFile, kid.getName()));
    }

    //noinspection ResultOfMethodCallIgnored
    newFile.setLastModified(oldFile.lastModified());
  }

  /**
   * Copy file contents.
   */
  public static void copyFile(File oldFile, File newFile)
    throws IOException
  {
    FileOutputStream out = null;
    FileInputStream in = null;

    try
    {
      out = new FileOutputStream(newFile);
      in = new FileInputStream(oldFile);

      byte[] buf = new byte[4096];
      long size = oldFile.length();
      long copied = 0;
      while(copied < size)
      {
        int n = in.read(buf, 0, buf.length);
        if (n < 0) throw new EOFException();
        out.write(buf, 0, n);
        copied += n;
      }
    }
    finally
    {
      if (in != null) try { in.close(); } catch(IOException ignore) {}
      if (out != null) try { out.close(); } catch(IOException ignore) {}
    }

    if (newFile.exists())
      newFile.setLastModified(oldFile.lastModified());
  }

  /**
   * Recursively delete a file or directory.
   */
  public static void delete(File file)
    throws IOException
  {
    log.fine("Delete: " + file);

    if (!file.exists()) return;

    if (file.isDirectory())
    {
      File[] kids = file.listFiles();
      if (kids != null)
      {
        for (File kid : kids) delete(kid);
      }
    }

    if (!file.delete())
      throw new IOException("Cannot delete: " + file);
  }

////////////////////////////////////////////////////////////////
// Diff
////////////////////////////////////////////////////////////////

  /**
   * Compare two input streams to see if they are identical.
   */
  public static boolean diff(BufferedReader b1, BufferedReader b2)
    throws IOException
  {
    boolean diff = false;
    String s1 = b1.readLine();
    String s2 = b2.readLine();
    while (!((s1 == null) && (s2 == null)))
    {
      if ((s1 == null) || (s2 == null) || (!s1.equals(s2)))
      {
        diff = true;
      }

      if (s1 != null) s1 = b1.readLine();
      if (s2 != null) s2 = b2.readLine();
    }

    return diff;
  }

///////////////////////////////////////////////////////////////////
// String I/O
///////////////////////////////////////////////////////////////////

  /**
   * Convenience for <code>readString(new FileReader(file))</code>.
   */
  public static String readString(File file)
    throws IOException
  {
    return readString(new FileReader(file));
  }

  /**
   * Read the input stream into a big String, delimted with '\n'.
   * The input stream is always closed.
   */
  public static String readString(Reader in)
    throws IOException
  {
    try
    {
      StringBuilder sb = new StringBuilder();

      BufferedReader bin = new BufferedReader(in);

      // read the file
      String str;
      while ((str = bin.readLine()) != null)
      {
        sb.append(str).append("\n");
      }
      return sb.toString();
    }
    finally
    {
      in.close();
    }
  }

  /**
   * Convenience for <code>readLines(new FileReader(file))</code>.
   *
   * Note: callers should consider using {@link #streamLines(File)} instead
   */
  public static String[] readLines(File file)
    throws IOException
  {
    return readLines(new FileReader(file));
  }

  /**
   * Read the input stream into an array of lines.
   * The input stream is always closed.
   *
   * Note: callers should consider using {@link #streamLines(Reader)} instead
   */
  public static String[] readLines(Reader in)
    throws IOException
  {
    try
    {
      ArrayList<String> list = new ArrayList<>();

      BufferedReader bin = new BufferedReader(in);

      // read the file
      String str;
      while ((str = bin.readLine()) != null)
        list.add(str);

      return list.toArray(new String[list.size()]);
    }
    finally
    {
      in.close();
    }
  }

  /**
   * Convenience for <code>streamLines(new InputStreamReader(in))</code>.
   *
   * @since Niagara 4.2
   */
  public static Stream<String> streamLines(InputStream in)
  {
    return streamLines(new InputStreamReader(in));
  }

  /**
   * Convenience for <code>streamLines(new FileReader(file))</code>.
   *
   * @since Niagara 4.2
   */
  public static Stream<String> streamLines(File file)
    throws IOException
  {
    return streamLines(new FileReader(file));
  }

  /**
   * Return a Stream that provides the contents of the reader a line at a time.
   *
   * The reader is closed at end-of-stream, or when the Stream is closed.
   *
   * @since Niagara 4.2
   */
  public static Stream<String> streamLines(Reader in)
  {
    Objects.requireNonNull(in);
    return StreamSupport
      .stream(new ReaderLineSpliterator(in), false)
      .onClose(() -> {
        try
        {
          in.close();
        }
        catch (IOException ignored)
        {
        }
      });
  }

  private static class ReaderLineSpliterator
    implements Spliterator<String>
  {
    public ReaderLineSpliterator(Reader reader)
    {
      Objects.requireNonNull(reader);
      this.reader = new BufferedReader(reader);
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> action)
    {
      try
      {
        String line = reader.readLine();
        if (line == null)
        {
          reader.close();
          return false;
        }
        else
        {
          action.accept(line);
          return true;
        }
      }
      catch (RuntimeException rethrow)
      {
        throw rethrow;
      }
      catch (IOException e)
      {
        // Stream closed IOException shouldn't cause advance to throw an exception
        return false;
      }
    }

    @Override
    public Spliterator<String> trySplit()
    {
      return null; // can't split
    }

    @Override
    public long estimateSize()
    {
      return Long.MAX_VALUE; // unknown size
    }

    @Override
    public int characteristics()
    {
      return ORDERED | NONNULL | IMMUTABLE;
    }

    private final BufferedReader reader;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
  
  static Logger log = Logger.getLogger("sys.file");
  
  public static class FileInfo
  {
    public long size = 0;
    public long crc = 0;
  }

  public static FileInfo getFileInfo(File file)
  {
    FileInfo info = new FileInfo();
    info.crc = getCrc(file);
    info.size = file.length();
    return info;
  }

  public static FileInfo getFileInfo(byte[] contents)
  {
    FileInfo info = new FileInfo();
    info.size = contents.length;
    java.util.zip.CRC32 crcObj = new java.util.zip.CRC32();
    crcObj.update(contents);
    info.crc = crcObj.getValue();
    return info;
  }

  public static FileInfo getFileInfo(InputStream in)
    throws IOException
  {
    FileInfo info = new FileInfo();
    
    try
    {
      int numRead;
      byte[] buf = new byte[4096];
      java.util.zip.CRC32 crcObj = new java.util.zip.CRC32();
      while ((numRead = in.read(buf)) > 0)
      {
        info.size += numRead;
        crcObj.update(buf, 0, numRead);
      }
      info.crc = crcObj.getValue();
      return info;
    }
    finally
    {
      if (in != null) in.close();
    }
  }

  //NCCB-12159, NCCB-12282: Determine the best buffer size to use
  //for the output stream writing this file. Ideally we'd
  //like to buffer the whole file while we write it, but
  //we don't want to exhaust resources (OutOfMemoryError)
  //for the sake of performance. Try to find a fair balance
  //between required filesystem I/0 (flushes) and the size of
  //the file
  public static int getFileBufferSize(long size)
  {
    //Use linear buffer sizes until we get to files larger than 8K
    if (size < _1K) return _1K;
    else if (size < _2K) return _2K;
    else if (size < _4K) return _4K;
    else if (size < _8K) return _8K;
    else
    {
      //If the file we are trying receive is larger than 8K, try to
      //give it as large a buffer as we can manage. If we have LOTS of free
      //heap memory, give it a max buffer of 64K, if we don't think we have
      //enough memory to give it a huge buffer, return 32K.
      long freeHeapMemory = Runtime.getRuntime().freeMemory();

      if (freeHeapMemory > (1024 * 1024 * 4))
      {
        //I have plenty of free heap memory, max out at 64K
        if (size < _16K) return _16K;
        else if (size < _32K) return _32K;
        else return _64K;
      }
      else
      {
        //I am low on heap memory, max out at 32K
        if (size < _16K) return _16K;
        else return _32K;
      }
    }
  }

  //References
  private final static int _1K = 1024;
  private final static int _2K = 1024 * 2;
  private final static int _4K = 1024 * 4;
  private final static int _8K = 1024 * 8;
  private final static int _16K = 1024 * 16;
  private final static int _32K = 1024 * 32;
  private final static int _64K = 1024 * 64;

////////////////////////////////////////////////////////////////
// Third party methods
////////////////////////////////////////////////////////////////

/*
 * The following methods have the license listed below applied
 * to them and are from the apache tutorial.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */


  /**
   *
   * This is a convenience method that calls find(File, String, boolean) with
   * the last parameter set to "false" (does not match directories).
   *
   * @see #find(File, String, boolean)
   *
   */
  public static File find(File contextRoot, String fileName) {
    return find(contextRoot, fileName, false);
  }

  /**
   *
   * Searches through the directory tree under the given context directory and
   * finds the first file that matches the file name. If the third parameter is
   * true, the method will also try to match directories, not just "regular"
   * files.
   *
   * @param contextRoot
   *          The directory to start the search from.
   *
   * @param fileName
   *          The name of the file (or directory) to search for.
   *
   * @param matchDirectories
   *          True if the method should try and match the name against directory
   *          names, not just file names.
   *
   * @return The java.io.File representing the <em>first</em> file or
   *         directory with the given name, or null if it was not found.
   *
   */
  public static File find(File contextRoot, String fileName, boolean matchDirectories) {
    if (contextRoot == null)
      throw new NullPointerException("NullContextRoot");

    if (fileName == null)
      throw new NullPointerException("NullFileName");

    if (!contextRoot.isDirectory()) {
      Object[] filler = { contextRoot.getAbsolutePath() };
      String message = "NotDirectory";
      throw new IllegalArgumentException(message);
    }

    File[] files = contextRoot.listFiles();

    //
    // for all children of the current directory...
    //
    for (File file : files)
    {
      String nextName = file.getName();

      //
      // if we find a directory, there are two possibilities:
      //
      // 1. the names match, AND we are told to match directories.
      // in this case we're done
      //
      // 2. not told to match directories, so recurse
      //
      if (file.isDirectory())
      {
        if (nextName.equals(fileName) && matchDirectories)
          return file;

        File match = find(file, fileName);

        if (match != null)
          return match;
      }

      //
      // in the case of regular files, just check the names
      //
      else if (nextName.equals(fileName))
        return file;
    }

    return null;
  }

  /**
   * Get the appropriate filename for the resource pointed to by the URLConnection.
   * If the Content-Disposition header contains a filename parameter, use that, otherwise
   * use the resource name in the URL to return a URLDecoded filename.
   *
   * @param conn URLConnection to remote file
   * @return filename The filename of the resource
   * @since Niagara 4.4
   */
  public static String getDownloadFilename(URLConnection conn)
  {
    String contentDisposition = conn.getHeaderField("Content-Disposition");
    String attachmentName = null;

    if (contentDisposition != null)
    {
      // Look to see whether there was a content-disposition header of the type 'attachment'.
      // If so, we'll try to get the intended file name from it.

      attachmentName = getFileNameFromContentDisposition(contentDisposition);
    }

    return attachmentName != null ? attachmentName : getDefaultDownloadFileName(conn.getURL());
  }

  /**
   * Extract the default file name from the "Content-Disposition" header,
   * if it's an attachment type and has a filename parameter. This particular
   * header is used by things such as the hx backup service view.
   *
   * The Content-Disposition of an HTTP Response will be of the form:
   * <ul>
   *   <li>Content-Disposition: inline</li>
   *   <li>Content-Disposition: attachment</li>
   *   <li>Content-Disposition: attachment; filename="filename.jpg"</li>
   * </ul>
   *
   * @param value The value of the "Content-Disposition" header
   * @return The file name extracted from the value, or null if not found.
   */
  private static String getFileNameFromContentDisposition(String value)
  {
    value = value.trim();

    // If the type is 'attachment', look for the file name.

    if (value.toLowerCase().startsWith(ATTACHMENT_DISPOSITION_TYPE))
    {
      // Try to look for a 'filename*=' type pattern. If we find it, we'll
      // use a regex to get out the file name, then decode it.

      value = value.substring(ATTACHMENT_DISPOSITION_TYPE.length());
      Matcher matcher = CONTENT_DISP_EXT_PARAM_PATTERN.matcher(value);

      if (matcher.matches())
      {
        String encoding = matcher.group(1);
        String encoded = matcher.group(3);

        try
        {
          return URLDecoder.decode(encoded, encoding);
        }
        catch (UnsupportedEncodingException e)
        {
          log.log(Level.WARNING, "Unable to URL Decode Content-Disposition filename", e);
          return null;
        }
      }
      else
      {
        int startPos = value.toLowerCase().indexOf(FILENAME_PARAMETER);

        if (startPos > -1)
        {
          //ensure we found filename exactly (filename can only be preceded by space or semi-colon)
          if (startPos > 0 && value.charAt(startPos-1) != ' ' && value.charAt(startPos-1) != ';')
            return null;

          // Look for either a quoted or unquoted string containing the
          // file name.
          value = value.substring(startPos + FILENAME_PARAMETER.length()).trim();

          StringBuilder sb = new StringBuilder();
          boolean quoted = false;
          boolean escapeNext = false;
          int i;

          // Consume whitespace before '=' character. Begin
          // reading the filename after that.

          for (i = 0; i < value.length(); i++)
          {
            char c = value.charAt(i);

            if (Character.isWhitespace(c))
            {
              continue;
            }
            else if (c == '=')
            {
              i++;
              break;
            }
            else
            {
              return null;
            }
          }

          for ( ; i < value.length(); i++)
          {
            char c = value.charAt(i);

            if (escapeNext)
            {
              sb.append(c);
              escapeNext = false;
            }
            else if ('"' == c || '\'' == c)
            {
              if (sb.length() == 0)
              {
                quoted = true;
              }
              else if (quoted)
              {
                break;
              }
              else
              {
                sb.append(c);
              }
            }
            else if (';' == c)
            {
              if (!quoted)
              {
                break;
              }
              else
              {
                sb.append(c);
              }
            }
            else if ('\\' == c && quoted)
            {
              escapeNext = true;
            }
            else
            {
              sb.append(c);
            }
          }

          return sb.toString().trim();
        }
      }
    }

    return null;
  }

  /**
   * Get the default file name for saving a downloaded file. This will be the default
   * name presented to the user.
   */
  private static String getDefaultDownloadFileName(URL url)
  {
    String file = url.getFile();

    if (file == null) throw new IllegalArgumentException();

    if (file.startsWith("/ord?") && url.getQuery() != null)
    {
      file = url.getQuery();  // Get it from the ord contained in the query string
    }

    try { file = URLDecoder.decode(file, "UTF-8"); }
    catch (UnsupportedEncodingException ignore) {}

    file = cleanDownloadFileName(file);

    return file;
  }

  /**
   * Return a cleaned up version of a default download file name.
   * This is intended to strip things such as a station home path
   * that the browser will not recognise. The file name should
   * have been percent decoded before being passed to this method.
   */
  private static String cleanDownloadFileName(String name)
  {
    int pos = name.lastIndexOf('/');
    if (pos > -1 && name.length() > pos+1) name = name.substring(pos+1);

    // JX browser replaces the ':' character after the file ord
    // scheme with a '-' character, so we'll need to check for
    // that in the regex.

    name = name.replaceFirst("^file(:|-)(!|\\^|~|\\^\\^)", "");

    // Remove any view query part, if the file download view had
    // been specified.

    pos = name.indexOf("|view:");
    if (pos > -1) name = name.substring(0, pos);

    return name;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  //Maximum file lengths is 270 on some systems so we are being more than
  //generous with our requirement here.
  //
  // Windows: https://docs.microsoft.com/en-us/windows/win32/fileio/naming-a-file
  // Linux: https://eklitzke.org/path-max-is-tricky
  public static final int MAX_FILE_PATH_LENGTH = 4096;

  private static final String ATTACHMENT_DISPOSITION_TYPE = "attachment";
  private static final String FILENAME_PARAMETER = "filename";

  //rfc5987 - look for "filename*=<encoding>' '<filename>" pattern for percent encoding
  private static final Pattern CONTENT_DISP_EXT_PARAM_PATTERN
    = Pattern.compile(".*filename\\*(?:\\s*)=(?:\\s*)(.*)('.*')([^;]*)($|\\s*;.*)", Pattern.CASE_INSENSITIVE);

}
