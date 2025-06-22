/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.io;

import java.io.*;

/**
 * RandomAccessFileOutputStream is an output stream for writing to
 * a RandomAccessFile.  It manages the file pointer for the
 * RandomAccessFile internally so that file pointer is always in
 * the correct position for writing with respect to the output stream.
 *
 * @author    John Sublett
 * @creation  10 Oct 2002
 * @version   $Revision: 1$ $Date: 10/11/02 2:12:47 PM EDT$
 * @since     Baja 1.0
 */
public class RandomAccessFileOutputStream
  extends java.io.OutputStream
{
  /**
   * Construct an output stream for the specified file.
   */
  public RandomAccessFileOutputStream(RandomAccessFile out)
  {
    this(out, 0L);
  }
  
  /**
   * Construct an output stream for the specified file.
   */
  public RandomAccessFileOutputStream(RandomAccessFile out, long initFp)
  {
    this.out = out;
    fp = initFp;
  }
  
  /**
   * This method DOES NOT actually close the underlying file.  It is
   * assumed that if this class is being used, the file should not
   * be closed.  If this is not the case, a normal FileOutputStream
   * can likely be used instead.
   */
  @Override
  public void close()
  {
  }

  /**
   * @see OutputStream#flush()
   */
  @Override
  public void flush()
    throws IOException
  {
    out.getFD().sync();
  }

  /**
   * @see java.io.RandomAccessFile#seek(long)
   */  
  public void seek(long fp)
  {
    this.fp = fp;
  }
  
  /**
   * @see OutputStream#write(int)
   */
  @Override
  public void write(int b)
    throws IOException
  {
    out.seek(fp);
    out.write(b);
    fp+=1;
  }
  
  /**
   * @see OutputStream#write(byte[])
   */
  @Override
  public void write(byte[] b)
    throws IOException
  {
    out.seek(fp);
    out.write(b);
    fp+=b.length;
  }
  
  /**
   * @see OutputStream#write(byte[], int, int)
   */
  @Override
  public void write(byte[] b, int offset, int len)
    throws IOException
  {
    out.seek(fp);
    out.write(b, offset, len);
    fp+=len;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private RandomAccessFile out;
  private long             fp;

}