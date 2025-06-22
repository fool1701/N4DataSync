/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.hx;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.baja.file.BFileSystem;
import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.naming.BOrd;

/**
 * MultiPartForm handles decoded multipart form submissions.
 *
 * @author    Andy Frank
 * @creation  31 Aug 05
 * @version   $Revision: 5$ $Date: 7/8/09 2:20:13 PM EDT$
 * @since     Baja 1.0
 */
class MultiPartForm
{
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Decode a multipart form into a hashmap of name/value pairs.
   * Files store the filename as the the value.  You can retrieve
   * the <code>BIFile</code> from <code>HxOp.getFile()</code>.
   */
  public Map<String, String> decode(HxOp op)
    throws Exception
  {
    HashMap<String, String> hash = new HashMap<>();

    // Get boundary string
    String contentType = op.getRequest().getContentType();
    int index = contentType.indexOf("boundary=");
    boundary = contentType.substring(index + 9).getBytes();

    try(InputStream in = new BufferedInputStream(op.getRequest().getInputStream()))
    {
      // Read first boundry line
      String line = readLine(in);
      while (!endStream && line != null)
      {
        // Read part header
        line = readLine(in);
        if(line!=null)
        {
          PartHeader header = parseHeader(line);
          if (header.filename == null)
          {
            // Eat rest of header
            String temp = readLine(in);
            while (temp.length() != 0) temp = readLine(in);

            // Plain form value
            try(ByteArrayOutputStream out = new ByteArrayOutputStream())
            {
              readPartContents(in, out);
              String value = decodeUtf8(out.toByteArray());
              hash.put(header.name, value);
            }
          }
          else
          {
            // If no filename was specifed, do not store value
            // in the op, but still need to read the part
            boolean valid = (header.filename.length() > 0);

            // File - store filename as key value
            if (valid) hash.put(header.name, header.filename);

            // Eat rest of header
            String temp = readLine(in);
            while (temp.length() != 0) temp = readLine(in);

            // Write file contents to temp file
            if (valid)
            {
              BIFile file = makeTempFile(op);
              try (OutputStream out = new BufferedOutputStream(file.getOutputStream(), 16000))
              {
                op.setFile(header.name, file);
                readPartContents(in, out);
              }
            }
            else
            {
              // Dump bytes to temp buffer
              try (OutputStream out = new ByteArrayOutputStream())
              {
                readPartContents(in, out);
              }
            }
          }
        }
      }
    }

    return hash;
  }

  /**
   * Return a temp file.
   */
  private synchronized BIFile makeTempFile(HxOp op)
    throws Exception
  {
    String base = "^temp_hx_";
    int tries = 0;
    while (tries < MAX_TEMP_FILES)
    {
      FilePath path = new FilePath(base + counter);//BAbsTime.now().getMillis());

      counter++;
      if (counter >= MAX_TEMP_FILES) { counter = 0; }

      try
      {
        // If file already exists, try again        
        BOrd.make(path).resolve(op.get());
        tries++;
      }
      catch (Exception e)
      {
        // File does not exist, create and return it
        return BFileSystem.INSTANCE.makeFile(path, null);
      }
    }
    throw new RuntimeException("Too many temp files in file system");
  }

////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  /**
   * Read in the next part to the given output stream.
   */
  private void readPartContents(InputStream in, OutputStream out)
    throws Exception
  {
    // Use this array to check for boundary conditions
    byte[] check = new byte[boundary.length + 4];
    int len = 0;

    int b = 0;
    while ((b = in.read()) >= 0)
    {
      // Record next byte
      check[len++] = (byte)(0xff & b);

      // Can only match boundary if starts with CR
      if (check[0] == 0x0d)
      {
        // Not enough data - keep filling buffer
        if (len < boundary.length + 4) continue;

        // See if have a match
        if (check[1] == 0xa && check[2] == '-' && check[3] == '-')
        {
          boolean match = true;
          for (int i=0; i<boundary.length; i++)
          {
            if (check[i+4] != boundary[i])
              match = false;
          }

          // Boundary matched check string, got everything
          // we need.  Check for end of stream and/or reset
          // buffer for next part read.
          if (match)
          {
            int b1 = in.read();
            int b2 = in.read();

            // End of stream
            if (b1 == '-' && b2 == '-')
              endStream = true;

            return;
          }
        }
      }

      // Was not a match roll check string and record first byte.
      out.write(check[0]);
      System.arraycopy(check, 1, check, 0, --len);
    }
  }

  /**
   * Read a line from the input stream and return it as a String.   
   * Assumes 8-bit character encoding, and assumes that lines are 
   * terminated with CRLF. Returns null if stream is already at EOF.
   */
  private String readLine(InputStream in)
    throws Exception
  {
    StringBuilder result = null;
    int b;
    while ((b = in.read()) >= 0)
    {
      if (result == null) result = new StringBuilder();
      if ((b == '\r') || (b == '\n'))
      {
        if (b == '\r') in.read(); // read the '\n'
        break;
      }
      else
      {
        result.append((char)b);
      }
    }
    return (result == null) ? null : result.toString();
  }

  /**
   * Parse string into PartHeader.
   */
  private PartHeader parseHeader(String str)
  {
    PartHeader h = new PartHeader();

    // Get disposition
    int start = str.indexOf(":") + 2;
    int end = str.indexOf(";");
    h.contentDisposition = str.substring(start, end);

    // Get name
    start = str.indexOf("name=");
    if (start != -1)
    {
      end = str.indexOf(";", start);
      if (end == -1) end = str.length();
      h.name = str.substring(start+6, end-1);
    }

    // Get filename
    start = str.indexOf("filename=");
    if (start != -1)
    {
      end = str.indexOf(";", start);
      if (end == -1) end = str.length();
      h.filename = str.substring(start+10, end-1);
    }

    return h;
  }

////////////////////////////////////////////////////////////////
// UTF-8
////////////////////////////////////////////////////////////////

  /**
   * Decode a UTF-8 byte array to a String.
   */
  private String decodeUtf8(byte[] c)
  {
    StringBuilder buf = new StringBuilder(c.length + 10);

    for(int i=0; i<c.length; ++i)
    {
      int val = c[i];

      // UTF-8 (0x80-0x7FF)
      if ((val & 0xe0) == 0xc0)
      {
        int high = val;
        int low  = c[++i];

        low  = ((high & 0x0001) << 6) | (low & 0x3f);
        high = (high >> 1) & 0x0f;

        val = (high << 7) | low;
      }
      // UTF-8 (0x800-0xFFFF)
      else if ((val & 0xe0) == 0xe0)
      {
        int three = val;
        int two   = c[++i];
        int one   = c[++i];

        val = ((three & 0x0f) << 12) | ((two & 0x3f) << 6) | ((one & 0x3f) << 0);
      }

      buf.append((char)val);
    }
    return buf.toString();
  }

////////////////////////////////////////////////////////////////
// Header
////////////////////////////////////////////////////////////////

  private static class PartHeader
  {
    private String contentDisposition = null;
    private String name = null;
    private String filename = null;
    private String contentType = null;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private byte[] boundary;
  private boolean endStream = false;

  private static long counter = 0;
  private static final int MAX_TEMP_FILES = 10000;
}

