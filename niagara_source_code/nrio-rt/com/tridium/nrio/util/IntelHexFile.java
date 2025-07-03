/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Vector;
import javax.baja.file.BIFile;
import javax.baja.log.Log;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.Clock;
import javax.baja.util.*;
import javax.baja.job.*;
import com.tridium.nrio.BNrioDevice;
import com.tridium.nrio.BNrioNetwork;
import com.tridium.nrio.messages.NrioInputStream;
import com.tridium.nrio.messages.NrioMessage;
import com.tridium.nrio.messages.NrioMessageConst;
import com.tridium.nrio.messages.PingMessage;
import com.tridium.nrio.messages.PingResponse;
import com.tridium.nrio.messages.WriteDownLoadData;
import com.tridium.nrio.messages.WriteDownLoadStart;
import com.tridium.nrio.messages.WriteDownLoadStop;

/**
 * Created by E333968 on 11/16/2016.
 */
public class IntelHexFile
  implements NrioMessageConst

{
  public static IntelHexFile make(BIFile file)
  {
    return new IntelHexFile(file);
  }

  private IntelHexFile(BIFile file)
  {
    this.file = file;
    try
    {
      this.inStream = file.getInputStream();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  public Vector<MemoryBlock> readMemoryBlocks()
  {
    try
    {
      // get next hex line
      IntelHexLine hexLine;
      Vector<MemoryBlock> v = new Vector<>();
      int nextAddress = -1;
      // read first hex line
      hexLine = readHexLine();
      if(hexLine == null)
        return null;
      // start first block
      MemoryBlock memBlock = new MemoryBlock(hexLine.getBaseAddress());
      memBlock.write(hexLine.getData());
      nextAddress = hexLine.getBaseAddress() + hexLine.getData().length;
      boolean done = false;
      while(!done)
      {
        hexLine = readHexLine();
        if(hexLine == null || hexLine.isEndOfFile())
        {
          v.addElement(memBlock);
          done = true;
          break;
        }
        while(hexLine.getBaseAddress() == nextAddress)
        {
          memBlock.write(hexLine.getData());
          nextAddress = hexLine.getBaseAddress() + hexLine.getData().length;
          hexLine = readHexLine();
        }
        // close block & start new
        v.addElement(memBlock);
        memBlock = new MemoryBlock(hexLine.getBaseAddress());
        memBlock.write(hexLine.getData());
        nextAddress = hexLine.getBaseAddress() + hexLine.getData().length;
      }
      return v;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }

  }

  public IntelHexLine readHexLine()
  {
    IntelHexLine line = new IntelHexLine();
    int colon = 0;
    try
    {
      // it will return from within this while loop
      while(true)
      {
        //advance to ':' if not there
        while (colon != ':')
        {
          colon = inStream.read();
        }

        int length = readByte();
        int baseAddress = readWord();
        line.setBaseAddress(baseAddress);
        boolean endOfFile = readByte() == 0x01;
        line.setEndOfFile(endOfFile);
        line.setDataSize(length);
        if(endOfFile)
          return line;
        if(length != 0)
        {
          byte[] data = line.getData();
          for(int i = 0; i < length; i++)
            data[i] = (byte)readByte();
          return line;
        }
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      return null;
    }


  }

  private int readByte()
    throws Exception
  {
    int hexChar = inStream.read();
    int value = TextUtil.hexCharToInt((char)hexChar);
    hexChar = inStream.read();
    value = value << 4 | TextUtil.hexCharToInt((char)hexChar);
    return value;
  }

  private int readWord()
    throws Exception
  {
    int value = readByte();
    value = value << 8 | readByte();
    return value;
  }



  public class MemoryBlock
  {
    public MemoryBlock(int baseAddress)
    {
      this.baseAddress = baseAddress;
    }

    private void writeByte(int value)
    {
      out.write(value);
    }

    private void write(byte[] data)
    {
      out.write(data, 0, data.length);
    }

    public String toString()
    {
      return "baseAddress = " + Integer.toHexString(baseAddress) + " size = " + Integer.toHexString(out.size());
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int baseAddress;
  }

  private class IntelHexLine
  {
    IntelHexLine()
    {
    }

    void setDataSize(int size)
    {
      data = new byte[size];
    }

    void setBaseAddress(int address) {this.address = address;}
    void setData(byte[] data) {this.data = data;}
    void setEndOfFile(boolean endOfFile) {this.endOfFile = endOfFile;}
    int getBaseAddress() {return address;}
    byte[] getData() {return data;}
    boolean isEndOfFile() { return endOfFile; }

    boolean endOfFile = false;
    int address= 0;
    byte[] data;
  }

  private BIFile file;
  private InputStream inStream;

}
