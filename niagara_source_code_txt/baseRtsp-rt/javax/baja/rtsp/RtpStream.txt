/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rtsp;

import java.io.IOException;
import java.io.InputStream;

import javax.baja.nre.util.ByteBuffer;
import javax.baja.rtsp.RtspConnection.SessionObject;
import javax.baja.sys.Clock;
import javax.baja.util.Queue;

public class RtpStream
    extends InputStream
{
  RtpStream()
  {
    packetQueue = new Queue();
  }

  void setRtspConnection(RtspConnection rtspConnection)
  {
    this.rtspConnection = rtspConnection;
  }

  RtspConnection getRtspConnection()
  {
    return rtspConnection;
  }

  public void close() throws IOException
  {
    clearPacketQueue();
    super.close();
  }

  public void setPortandBuffer(int port,int buffer)
  {
    this.port = port;
    this.udpbuffersize = buffer;
  }

  public int read() throws IOException
  {
    throw new IOException("Please use the readPacketMethod instead");
  }

  public void addPacket(RtpPacket pack,boolean RTP) throws Exception
  {
    if(pack == null) return;

    if(!RTP)
    {
      useRtcp(pack);
      return;
    }

    if(lastSequence == 0)
      lastSequence=pack.seq_num;

    else
    {

     if((pack.seq_num - lastSequence) > 1 && (pack.seq_num - lastSequence) < 100)
     {
       packetLost += (pack.seq_num - lastSequence -1);
       //System.out.println(packetLost);
     }

     else lastSequence = pack.seq_num;
    }

    packetCount++;
    packetQueue.enqueue(pack);

    //dataReceived += pack.pay_length;
  }

  public RtpPacket readPacket() throws Exception
  {
    RtpPacket packet = (RtpPacket)packetQueue.dequeue(100);

    synchronized(this)
    {
      notifyAll(); //notify any threads listening for dequeue notifications
    }

    return packet;
  }


  public void useRtcp(RtpPacket pack) {
    long now = Clock.ticks();
    if(lastTick==0) {
      dataReceived = 0;
    }
    else {

      if((now - lastTick) != 0)
        bandwidth = ((dataReceived/(now - lastTick))*1000)/1024;
    }

    dataReceived = 0;
    lastTick = now;
    time = this.getTime(pack.ntpTimeStamp1,pack.ntpTimeStamp2);
  }

  public long getTime()
  {
    return time;
  }
  public long time=0;


  public int size()
  {
    return packetQueue.size();

  }

  public void clearPacketQueue()
  {
    packetQueue.clear();

  }

  public byte[] readFrame() throws Exception
  {
    frameRead.reset();

    boolean finishFrame = false;

    while(!finishFrame)
    {
      RtpPacket pack = readPacket();

      if(pack!=null)
      {
        finishFrame = pack.marker;
        frameRead.write(pack.payload, 0, pack.pay_length);
        //releaseRtpPacketToPool(pack);
      }

      else finishFrame = true; //prevents thread leakage

    }

    frameCount++;

    return frameRead.toByteArray();
  }

  public long getFrameTime()
  {
    return frameTime;
  }

  public long getTime(long ntpTimeValue1,long ntpTimeValue2)
  {

          long seconds = ntpTimeValue1 & 0xffffffffL;
          long fraction = ntpTimeValue2 & 0xffffffffL;

          fraction = Math.round(1000D * fraction / 0x100000000L);
           long msb = seconds & 0x80000000L;
          if (msb == 0) {
            return msb0baseTime + seconds * 1000L + fraction;
           } else {
             return msb1baseTime + (seconds * 1000L) + fraction;
         }
  }
  protected static final long msb0baseTime = 2085978496000L;
  protected static final long msb1baseTime = -2208988800000L;


  public String getBandwidth()
  {
    if(bandwidth==0)
      return "Calculating...";
    else
      return bandwidth+" KBPS";
  }
  public double getPacketLoss()
  {
    double res = 0;
    if(this.packetLost>0) {
      res =  (this.packetLost/this.packetCount);
    }
    else {
      return 0.0;
    }
    return res;
  }
  public int getPacketCount() {
    return this.packetCount;
  }
  public int numPacketLost()
  {
    return this.packetLost;
  }
  public long getFrameCount()
  {
    return frameCount;
  }
  public int getPort()
  {
  return port;
  }

  public int getUdpBufferSize()
  {
   return udpbuffersize;
  }

  RtspConnection rtspConnection;
  long dataReceived = 0;
  long lastTick = 0;
  double bandwidth = 0;

  long frameCount = 0;
  long frameTime = 0;
  long rtcpTime = 0;
  Queue packetQueue;
  //RtpPacketQueue packetQueue;
  int packetCount=0;
  int lastSequence = 0;
  int packetLost = 0;

  int port =0;
  int udpbuffersize = 0;

  public byte[] rtpDataBuffer = new byte[0];
  public Object monitor = new Object();
  public boolean marker = false;
  public boolean useQueue = false;
  ByteBuffer frameRead = new ByteBuffer();
  public SessionObject session;

  /**
   * @author   E442476
   * @creation Nov 4, 2010
   * @version  1:49:44 PM
   * @since     Baja 1.0
   */
}
