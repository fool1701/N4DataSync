/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
/*
 * This source code file is public domain
 * http://sourceforge.net/projects/uxparser
 */
package javax.baja.xml;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * XWriter is a specialized Writer that provides
 * support for generating an XML output stream.
 *
 * @author    Brian Frank on 29 Sept 00
 * @since     Baja 1.0
 */
public class XWriter
  extends Writer
{

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////

  /**
   * Construct writer for specified file.
   *
   * @param file file to write to
   * @throws IOException if opening file fails
   */
  public XWriter(File file)
    throws IOException
  {
    //  NCCB-9730
    //  retain fd reference to force sync
    FileOutputStream fos = new FileOutputStream(file);
    try
    {
      fd = fos.getFD();
    }
    catch (IOException rethrow)
    {
      fos.close();
      throw rethrow;
    }
    this.sink = new BufferedOutputStream(fos);
  }

  /**
   * Construct writer for specified output stream.
   *
   * @param out output stream to write to
   * @throws IOException if {@code out} is not valid
   */
  public XWriter(OutputStream out)
    throws IOException
  {
    this.sink = out;
    if (out instanceof FileOutputStream)
      fd = ((FileOutputStream)out).getFD();
  }

  /**
   * Construct writer for lazily initialized output when
   * using in-memory (or non-file) sink that doesn't require
   * sync() and is guaranteed not to throw a checked exception.
   *
   * You must call setOutputStream(OutputStream) prior
   * to writing anything via public API.
   *
   * @since Niagara 4.9
   */
  public XWriter()
  {
  }

////////////////////////////////////////////////////////////////
// Public
////////////////////////////////////////////////////////////////

  /**
   * Allow for lazy initialization of the output stream
   * via an API that doesn't include a checked exception
   * in the method signature. This should only be used
   * for in-memory (more specifically non-FileOutputStream)
   * based output streams. Can only be called once per
   * instance, and only for instances created via the default
   * constructor.
   *
   * @param outputStream OutputStream that is not an instance of FileOutputStream
   *
   * @since Niagara 4.9
   */
  public void setOutputStream(OutputStream outputStream)
  {
    if (sink != null)
    {
      throw new IllegalStateException("Can not re-initialize sink for same instance");
    }

    if (outputStream == null)
    {
      throw new IllegalArgumentException("Output stream is null");
    }

    if (outputStream instanceof FileOutputStream)
    {
      throw new IllegalArgumentException("Output stream is instance of FileOutputStream, must use OutputStream constructor");
    }

    this.sink = outputStream;
  }

  /**
   * Write the specified Object and return this.
   *
   * @param x object to write
   * @return this
   */
  public XWriter w(Object x) { write(String.valueOf(x)); return this; }

  /**
   * Write the specified boolean and return this.
   *
   * @param x boolean to write
   * @return this
   */
  public final XWriter w(boolean x) { write(String.valueOf(x)); return this; }

  /**
   * Write the specified char and return this.
   *
   * @param x char to write
   * @return this
   */
  public final XWriter w(char x) { write(x); return this; }

  /**
   * Write the specified int and return this.
   *
   * @param x int to write
   * @return this
   */
  public final XWriter w(int x) { write(String.valueOf(x)); return this; }

  /**
   * Write the specified long and return this.
   *
   * @param x long to write
   * @return this
   */
  public final XWriter w(long x) { write(String.valueOf(x)); return this; }

  /**
   * Write the specified float and return this.
   *
   * @param x float to write
   * @return this
   */
  public final XWriter w(float x) { write(String.valueOf(x)); return this; }

  /**
   * Write the specified double and return this.
   *
   * @param x double to write
   * @return this
   */
  public final XWriter w(double x) { write(String.valueOf(x)); return this; }

  /**
   * Write a newline character and return this.
   *
   * @return this
   */
  public final XWriter nl() { write('\n'); return this; }

  /**
   * Write the specified number of spaces.
   *
   * @param indent number of spaces to indent
   * @return this
   */
  public final XWriter indent(int indent)
  {
    write(getSpaces(indent));
    return this;
  }

  /**
   * Write an attribute pair {@code name="value"}
   * where the value is written using safe().
   *
   * @param name name of attribute
   * @param value value of attribute
   * @return this
   */
  public final XWriter attr(String name, String value)
  {
    write(name);
    write('=');
    write('"');
    safe(value);
    write('"');
    return this;
  }

  /**
   * This write the standard prolog
   * {@code <?xml version="1.0" encoding="UTF-8"?>}
   *
   * @return this
   */
  public XWriter prolog()
  {
    write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    return this;
  }

////////////////////////////////////////////////////////////////
// Safe
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code XWriter.safe(this, s, escapeWhitespace)}.
   *
   * @param s String to write
   * @param escapeWhitespace whether to escape whitespace when writing
   * @return this
   */
  public final XWriter safe(String s, boolean escapeWhitespace)
  {
    try
    {
      XWriter.safe(xout, s, escapeWhitespace);
      return this;
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  /**
   * Convenience for {@code XWriter.safe(this, s, true)}.
   *
   * @param s String to write
   * @return this
   */
  public final XWriter safe(String s)
  {
    try
    {
      XWriter.safe(xout, s, true);
      return this;
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  /**
   * Convenience for {@code XWriter.safe(this, c, escapeWhitespace)}.
   *
   * @param c character to write
   * @param escapeWhitespace whether to escape whitespace when writing
   * @return this
   */
  public final XWriter safe(int c, boolean escapeWhitespace)
  {
    try
    {
      XWriter.safe(this, c, escapeWhitespace);
      return this;
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  /**
   * This writes each character in the string to the output stream
   * using the {@code safe(Writer, int, boolean)} method.
   *
   * @param out Writer to write to
   * @param s String to write
   * @param escapeWhitespace whether to escape whitespace when writing
   * @throws IOException if writing fails
   */
  public static void safe(Writer out, String s, boolean escapeWhitespace)
    throws IOException
  {
    int len = s.length();
    for(int i=0; i<len; ++i) safe(out, s.charAt(i), escapeWhitespace);
  }

  /**
   * Write a "safe" character.  This method will escape unsafe
   * characters common in XML and HTML markup.
   * <ul>
   * <li>'&lt;' -&gt; &amp;lt;</li>
   * <li>'&gt;' -&gt; &amp;gt;</li>
   * <li>'&amp;' -&gt; &amp;amp;</li>
   * <li>'&#39;' -&gt; &amp;#x27;</li>
   * <li>'&quot;' -&gt; &amp;#x22;</li>
   * <li>Below 0x20 -&gt; &amp;#x{hex};</li>
   * <li>Above 0x7E -&gt; &amp;#x{hex};</li>
   * </ul>
   *
   * @param out Writer to write to
   * @param c character to write
   * @param escapeWhitespace whether to escape whitespace when writing
   * @throws IOException if writing fails
   */
  public static void safe(Writer out, int c, boolean escapeWhitespace)
    throws IOException
  {
    if (c < 0x20 || c > 0x7e || c == '\'' || c == '"')
    {
      if (!escapeWhitespace)
      {
        if (c == '\n') { out.write('\n'); return; }
        if (c == '\r') { out.write('\r'); return; }
        if (c == '\t') { out.write('\t'); return; }
      }
      out.write("&#x"); out.write(Integer.toHexString(c)); out.write(';');
    }
    else if (c == '<')   out.write("&lt;");
    else if (c == '>')   out.write("&gt;");
    else if (c == '&')   out.write("&amp;");
    else out.write((char)c);
  }

  /**
   * Create a "safe" version of the specified string.
   *
   * @param s String to write
   * @param escapeWhitespace whether to escape whitespace when writing
   * @return safe version of {@code s}
   */
  public static String safeToString(String s, boolean escapeWhitespace)
  {
    try
    {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      XWriter out = new XWriter(bout);
      safe(out, s, escapeWhitespace);
      out.flush();
      return new String(bout.toByteArray());
    }
    catch(IOException e)
    {
      throw new RuntimeException(e.toString());
    }
  }

////////////////////////////////////////////////////////////////
// Zip
////////////////////////////////////////////////////////////////

  /**
   * Return if this XWriter is being used to generate a PKZIP file
   * containing the XML document. See {@code setZipped()}
   *
   * @return true if this writer is writing a zip file
   */
  public boolean isZipped()
  {
    return zipped;
  }

  /**
   * If set to true, then XWriter generates a compressed PKZIP
   * file with one entry called "file.xml".  This method cannot be
   * called once bytes have been written.  Zipped XWriters should
   * only be used with stand alone files, it should not be used in
   * streams mixed with other data.  This feature is used in conjunction
   * with XParser, which automatically detects plain text XML versus
   * PKZIP documents.
   *
   * @param zipped true if this document should be zipped
   * @throws IOException never?
   * @throws IllegalStateException if this writer has already been written to
   */
  public void setZipped(boolean zipped)
    throws IOException
  {
    if (numWritten != 0)
      throw new IllegalStateException("Cannot setZipped after data has been written");

    this.zipped = zipped;
  }

////////////////////////////////////////////////////////////////
// Writer
////////////////////////////////////////////////////////////////

  @Override
  public void write(int c)
  {
    try
    {
      if (xout == null) initOut();
      numWritten++;
      xout.write(c);
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  @Override
  public void write(char[] buf)
  {
    try
    {
      if (xout == null) initOut();
      numWritten += buf.length;
      xout.write(buf);
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  @Override
  public void write(char[] buf, int off, int len)
  {
    try
    {
      if (xout == null) initOut();
      numWritten += len;
      xout.write(buf, off, len);
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  @Override
  public void write(String str)
  {
    try
    {
      if (xout == null) initOut();
      numWritten += str.length();
      xout.write(str);
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  @Override
  public void write(String str, int off, int len)
  {
    try
    {
      if (xout == null) initOut();
      numWritten += len;
      xout.write(str, off, len);
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  @Override
  public void flush()
  {
    try
    {
      if (xout == null) initOut();
      xout.flush();
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  @Override
  public void close()
  {
    try
    {
      if (xout == null) initOut();

      // we can't trust close() to throw exception if there is a problem
      // because FilterOutputStream silently ignores exceptions on flush
      // if called from close (which happens to be where ZipOutputStream
      // does most of it's work); to hack around the problem:
      //  1) flush output writer buffer to the zip stream
      //  2) close the zip entry
      //  3) flush again to make sure zip entry is written
      if (zipped)
      {
        xout.flush();
        zout.closeEntry();
        xout.flush();
      }

      // NCCB-9730 force sync prior to closing
      if (fd != null)
        fd.sync();

      xout.close();
    }
    catch(IOException e)
    {
      throw error(e);
    }
  }

  void initOut()
    throws IOException
  {
    if (sink == null)
    {
      throw new IOException("sink not initialized");
    }

    if (zipped)
    {
      zout = new ZipOutputStream(sink);
      zout.putNextEntry(new ZipEntry("file.xml"));
      this.xout = new OutputStreamWriter(zout, "UTF8");
    }
    else
    {
      this.xout = new OutputStreamWriter(sink, "UTF8");
    }
  }

  XException error(IOException e)
  {
    throw new XException(e.toString(), e);
  }

////////////////////////////////////////////////////////////////
// Spaces
////////////////////////////////////////////////////////////////

  static String getSpaces(int num)
  {
    try
    {
      // 99.9% of the time num is going to be
      // smaller than 50, so just try it
      return SPACES[num];
    }
    catch(ArrayIndexOutOfBoundsException e)
    {
      if (num < 0)
        return "";

      // too big!
      int len = SPACES.length;
      StringBuilder buf;
      buf  = new StringBuilder(num);
      int rem = num;
      while(true)
      {
        if (rem < len)
          { buf.append(SPACES[rem]); break; }
        else
          { buf.append(SPACES[len-1]); rem -= len-1; }
      }
      return buf.toString();
    }
  }

  private static String[] SPACES;
  static
  {
    SPACES = new String[50];
    SPACES[0] = "";
    for(int i=1; i<50; ++i)
      SPACES[i] = SPACES[i-1] + " ";
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private FileDescriptor fd;      // fd representing underlying file - may be null if not
                                  // writing to file
  private OutputStream sink;      // the underlying output sink
  private Writer xout;            // writer for XML markup
  private ZipOutputStream zout;   // zipped stream if zipped is true
  private boolean zipped;         // are we generating a zip file
  private int numWritten;         // number of chars written

}
