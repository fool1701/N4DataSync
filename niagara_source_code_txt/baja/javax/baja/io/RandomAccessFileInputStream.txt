/*
 * Copyright 2002, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.io;

import java.io.*;

/**
 * RandomAccessFileInputStream is an input stream for reading from
 * a RandomAccessFile.  It manages the file pointer for the
 * RandomAccessFile internally so that file pointer is always in
 * the correct position for reading with respect to the input stream.
 *
 * @author    John Sublett
 * @creation  10 Oct 2002
 * @version   $Revision: 1$ $Date: 10/11/02 2:12:47 PM EDT$
 * @since     Baja 1.0
 */
public class RandomAccessFileInputStream
  extends java.io.InputStream
{
  /**
   * Construct an input stream for the specified file.
   */
  public RandomAccessFileInputStream(RandomAccessFile in)
  {
    this(in, 0L);
  }
  
  /**
   * Construct an input stream for the specified file.
   */
  public RandomAccessFileInputStream(RandomAccessFile in, long initFp)
  {
    this.in = in;
    fp = initFp;
  }
  
  /**
   * @see InputStream#available()
   */
  @Override
  public int available()
    throws IOException
  {
    long remainder = in.length() - fp;
    if (remainder > Integer.MAX_VALUE)
      return Integer.MAX_VALUE;
    else
      return (int)remainder;
  }
  
  /**
   * This method DOES NOT actually close the underlying file.  It is
   * assumed that if this class is being used, the file should not
   * be closed.  If this is not the case, a normal FileInputStream
   * can likely be used instead.
   */
  @Override
  public void close()
  {
  }

  /**
   * Mark is not supported on this type.
   */
  @Override
  public boolean markSupported()
  {
    return false;
  }
  
  /**
   * @see java.io.RandomAccessFile#seek(long)
   */  
  public void seek(long fp)
  {
    this.fp = fp;
  }

  /**
   * @see InputStream#read()
   */
  @Override
  public int read()
    throws IOException
  {
    in.seek(fp);
    int b = in.read();
    fp+=1;
    return b;
  }

  /**
   * @see java.io.InputStream#read(byte[])
   */
  @Override
  public int read(byte[] b)
    throws IOException
  {
    in.seek(fp);
    int result = in.read(b);
    if (result != -1)
      fp+=result;
    else
      fp = in.length();

    return result;
  }

  /**
   * @see java.io.InputStream#read(byte[], int, int)
   */
  @Override
  public int read(byte[] b, int offset, int len)
    throws IOException
  {
    in.seek(fp);
    int result = in.read(b, offset, len);
    if (result != -1)
      fp+=result;
    else
      fp = in.length();

    return result;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private RandomAccessFile in;
  private long             fp;
}