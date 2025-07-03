/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rtsp;

public class RtpPacket
{
  
  public RtpPacket()
  {}
  
  public void read(byte[] rtpData, int dataLength)
  {
    version = (rtpData[0] & 0xC0);
    padding = ((rtpData[0] & 0x20)==0x20);
    extension = ((rtpData[0] & 0x10)==0x10);
    csr_count = (rtpData[0] & 0x0F);
    marker =((rtpData[1] & 0x80)==0x80);
    payload_type = (rtpData[1] & 0x7F);
    seq_num = (((rtpData[2] & 0xFF)<< 8) | (rtpData[3] & 0xFF));

    int t1 = rtpData[4];
    int t2 = rtpData[5];
    int t3 = rtpData[6];
    int t4 = rtpData[7];
    
    time_stamp = ((t1 & 0xFF)<<24 | (t2&0xFF)<<16 | (t3&0xFF)<< 8 | t4&0xFF)&0xFFFFFFFF;
    pay_length = dataLength - 12;
    
    System.arraycopy(rtpData, 12, payload, 0, pay_length);
    
  }
  
  
  RtpPacket(byte[] data,boolean rtp)
  {
    version = (data[0]&0xC0);
    padding = ((data[0]&0x20)==0x20);
    
    if(rtp) 
    {
      extension = ((data[0]&0x10)==0x10);
      csr_count = (data[0]&0x0F);
      
      marker =((data[1]&0x80)==0x80);
      payload_type = (data[1]&0x7F);
      seq_num = (((data[2]&0xFF)<< 8) | (data[3]&0xFF));
  
      int t1 = data[4];
      int t2 = data[5];
      int t3 = data[6];
      int t4 = data[7];
      
      time_stamp = ((t1&0xFF)<<24 | (t2&0xFF)<<16 | (t3&0xFF)<< 8 | t4&0xFF)&0xFFFFFFFF;
      pay_length = data.length-12;
      
      payload = new byte[pay_length];
      System.arraycopy(data, 12, payload, 0, pay_length);
      
    }
    
    else
    {
      rr_count = (data[0]&0x1F);
      packet_type = (data[1]&0xFF);
      packet_length = ((data[2]&0xFF)<<8) |( data[3]&0xFF);
      sender_ssrc = ((data[4]&0xFF)<<24) | ((data[5]&0xFF)<<16) | ((data[6]&0xFF)<<8) | (data[7]&0xFF);
      ntpTimeStamp1 = ((data[8]&0xFF)<<24) | ((data[9]&0xFF)<<16) | ((data[10]&0xFF)<<8) | (data[11]&0xFF);      
      ntpTimeStamp2 = ((data[12]&0xFF)<<24) | ((data[13]&0xFF)<<16) | ((data[14]&0xFF)<<8) | (data[15]&0xFF);
      timeStamp = ((data[16]&0xFF)<<24) | ((data[17]&0xFF)<<16) | ((data[18]&0xFF)<<8) | (data[19]&0xFF);
    }
    
    //printDebug();
  }
  public void printDebug() 
  {
    System.out.println("-----------------------------------------------------------------------");
    System.out.println("Padding\tMarker\tPT\tTime\t\tLength\tSequence");
    System.out.println(padding+"\t"+marker+"\t"+payload_type+"\t"+ time_stamp +"\t"+pay_length+"\t"+seq_num);
  }
  
  public int version;
  public boolean padding;
  
  //RTP Properties
  public boolean extension;
  public boolean marker;
  public int csr_count;
  public int payload_type;
  public int seq_num;
  public int ext_seq_num;
  public long time_stamp = 0;
  public long ssrc_id = 0;
  public int[] csrc_id;
  public byte[] payload = new byte[1600];
  public int pay_length;
  
  // SR Report Properties
  int rr_count = 0;
  int packet_type =0;
  int packet_length = 0;
  int sender_ssrc=0;
  long ntpTimeStamp1 = 0;
  long ntpTimeStamp2 = 0;
  long timeStamp = 0;
  /**
   * @author   E442476 
   * @creation Nov 4, 2010
   * @version  11:02:42 AM
   * @since     Baja 1.0
   */
}
