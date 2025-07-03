/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.rtsp;

import javax.baja.nre.util.Array;
import javax.baja.nre.util.TextUtil;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.Clock;
import javax.baja.util.Queue;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * Implements the basic logic for RTSP Connectivity
 * this porvides the functions for doing the rtsp connectivity to a server
 * like describe / setup / etc..
 * this doesnt give any complete implementation of a socket, but provides the programmer
 * with the fucntions to do that. This fucntions are used in ddfRtsp.
 * Author : Althaf M
 */
/*
 * This is implemented in a similar way as that of using the socket connection,
 * whcih was recommended by Lenar Perkins. Hence the programmer need to Make an
 * object of this class Rtsp Connection passing the parameters required.
 *
 * @params
 * hostname  - Host name of the rtsp server or camera this will ipaddress followed
 * by the path of the server eg : 199.63.39.254/mpeg4/1/amp
 * controlport - the RTCP port which is configured in the server (default 554)
 * dataport - can be any port above 1024, which will be used for UDP data transmission
 * and will be negotiated with the server
 * udpSock - Socket object for udp communication
 * rtcpSock - Socket for control signals via RTCP
 */

public class RtspConnection extends Object
{


  public RtspConnection(String hostname,
                        int controlPort,
                        int dataPort,
                        boolean useTcp
                        /*boolean useQueue,
                        int rtpPacketQueueSize*/) throws Exception
  {
    this.hostname = hostname;
    this.controlPort = controlPort;
    this.dataPort = dataPort;
    this.state = init;
    this.rtspUri = "rtsp://" + bsplit(this.hostname,"/")[0].trim() + ':' + controlPort + "/" +bsplit(this.hostname,"/",2)[1] ;
    this.pipein = new RtpStream();
    this.useTcp = useTcp;

    pipein.session = session = new SessionObject();

    if(useTcp)
    {
      if(tcpRtspPacketQueue == null)
        tcpRtspPacketQueue = new Queue();
    }
    else
    {
      if(udpRtspPacketQueue == null)
        udpRtspPacketQueue = new Queue();
    }

    if(logger.isLoggable(Level.FINER))
      logger.finer("rtspUri = " + rtspUri);
  }



  /*
   * Method for setting the user name and iPhrase for the rtsp communication
   * Need to be done for those which doesn't need one
   */
  public void setAuthorization(String uname,String pwd) {
    this.username = uname;
    this.iPhrase = pwd;

  }

  /*
   * Method for setting up the control socket.
   * This method will be called before doing any other operation.
   * this will setup the tcp socket with the server for control signals
   * and set up a data reader thread.
   */
  public void setupControlSocket()
  {

    try
    {
      if(this.hostname!=null)
      {
        if(controlSocket==null)
        {
          controlSocket = new Socket(bsplit(this.hostname,"/")[0],this.controlPort);
          controlSocket.setSoTimeout(100);

        }

        else
        {
          controlSocket.close();
          controlInput = null;
          controlOut = null;
          controlSocket = new Socket(bsplit(this.hostname,"/")[0],this.controlPort);
          controlSocket.setSoTimeout(100);
        }
      }

      else
      {
        throw new Exception("Unknown / Invalid Host");
      }

      controlOut = controlSocket.getOutputStream();
      controlInput = controlSocket.getInputStream();

      if(useTcp)
      {
        buffIn = new BufferedInputStream(controlInput);
        bufferedReader = new BufferedReader(new InputStreamReader(buffIn));
        din = new DataInputStream(buffIn);

        if(tcpReader == null)
        {
          tcpReader = new TcpInputStreamReader();
          tcpReader.start();

          if(logger.isLoggable(Level.FINER))
            logger.finer("***** TCP STREAM READER STARTED *****");

          tcpReader.setPriority(Thread.MAX_PRIORITY);
        }
      }

      else
      {
        while(udpReader==null /*|| rtcpReader==null*/)
        {
          try
          {
            udpReader = new UdpDataReader("RtspUdpReader", dataPort, pipein, true);
            udpReader.setPriority(Thread.MAX_PRIORITY);

            //this.rtcpReader = new UdpDataReader("RtcpReader", this.dataPort+1,this,this.pipein,false);
          }

          catch(Exception e)
          {
            if(this.udpReader!=null && udpReader.isAlive())
              this.udpReader.done = true;

            this.udpReader = null;

            this.dataPort += 2;
          }
        }
      }

    }
    catch (Exception e) {

      if(logger.isLoggable(Level.SEVERE))
        logger.log(Level.SEVERE,"Error while setting up control socket", e);
    }
  }
  /******************************************************************************************/
  /* Fucntions to send the OPTIONS Command to the rtsp server and parse the response.       */
  /* Called from  connect method, initially while setting up.                               */
  /*                                                                             -Althaf M  */
  /******************************************************************************************/
  protected int getOptions()
  {
    while(true)
    {
      int stat =  sendRtspCommand(getOptionsString());

      int ret = -1;

      if(stat==1)
      {
        ret = readOptions();
//        CSeq++;
        if(ret >= 0)
          return ret;
        try{Thread.sleep(100);}catch(Exception e){}
      }
      else
      {
        return ret;
      }
    }
  }
  protected String getOptionsString() {

    if(useBasic)
      return "OPTIONS " + rtspUri + "?" + videoParams + " RTSP/1.0\r\nUser-Agent: Niagara\r\nCSeq:"+CSeq+"\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes()) +"\r\n\r\n";

    return "OPTIONS " + rtspUri + "?" + videoParams + " RTSP/1.0\r\nUser-Agent: Niagara\r\nCSeq:"+CSeq+"\r\nAuthorization: " + authorization +"\r\n";

  }
  /*
   * Fucntion to parse the spcific Options response with the status.
   */
  protected int readOptions()
  {

    int status = readStatus();

    if(useTcp)
    {
      String[] optionsBody = currentRtspResponsePacket.body();

      if(status == 200)
      {
        String optionsLine = null;

        for(int i = 0; i < optionsBody.length; i++)
        {
          if(optionsBody[i].startsWith("Public:"))
          {
            optionsLine = optionsBody[i];
            break;
          }
        }

        if(optionsLine != null)
        {
          is_describe      = optionsLine.indexOf("DESCRIBE") > -1;
          is_get_parameter = optionsLine.indexOf("GET_PARAMETER") > -1;
          is_announce      = optionsLine.indexOf("ANNOUNCE") > -1;
          is_setup         = optionsLine.indexOf("SETUP") > -1;
          is_play          = optionsLine.indexOf("PLAY") > -1;
          is_pause         = optionsLine.indexOf("PAUSE") > -1;
          is_teardown      = optionsLine.indexOf("TEARDOWN") > -1;
          is_set_parameter = optionsLine.indexOf("SET_PARAMETER") > -1;
          is_record        = optionsLine.indexOf("RECORD") > -1;
          is_redirect      = optionsLine.indexOf("REDIRECT") > -1;

        }
      }

      else
      {
        createAuthorizationStringFromResponse("OPTIONS", currentRtspResponsePacket.body());
      }

      return status;
    }

    if(status == 200)
    {
      // Checking the response status, we need to parse only if the response status is 200
      String readLine;

      //keep reading response lines till the CSeq field is encountered
      while(!(readLine = readResponseLine(MAX_WAIT_TIME)).trim().startsWith("CSeq:"));

      //String readLine = readResponseLine(MAX_WAIT_TIME);

      if(Integer.parseInt(bsplit(readLine,":")[1].trim()) == CSeq)
      {
        //keep reading response lines till the 'Public' field is encountered. This field contains the
        //various OPTIONS supported by the server
        //String responseLine;
        //String line;

        while(! (readLine = readResponseLine(MAX_WAIT_TIME).trim()).startsWith("Public:"));

        String[] commands = bsplit(bsplit(readLine,":")[1],",");

         for(int i=0;i<commands.length;i++) {

          if(commands[i].trim().indexOf("DESCRIBE")>=0) {
            this.is_describe = true;
          }
          else if(commands[i].indexOf("GET_PARAMETER")>=0) {
            this.is_get_parameter = true;
          }
          else if(commands[i].indexOf("ANNOUNCE")>=0) {
            this.is_announce = true;
          }
          else if(commands[i].indexOf("SETUP")>=0) {
            this.is_setup = true;
          }
          else if(commands[i].indexOf("PLAY")>=0) {
            this.is_play = true;
          }
          else if(commands[i].indexOf("PAUSE")>=0) {
            this.is_pause = true;
          }
          else if(commands[i].indexOf("TEARDOWN")>=0) {
            this.is_teardown = true;
          }
          else if(commands[i].indexOf("SET_PARAMETER")>=0) {
            this.is_set_parameter = true;
          }
          else if(commands[i].indexOf("RECORD")>=0) {
            this.is_record = true;
          }
          else if(commands[i].indexOf("REDIRECT")>=0) {
            this.is_redirect = true;
          }
         } // For loop
       } // Sequence check

      /*while(true)
      {
        String readLines = readResponseLine(MAX_WAIT_TIME);
        if(readLines=="") {
          break;
        }
      }*/

      //consume the rest of the lines till the end
      while(!(readResponseLine(MAX_WAIT_TIME)).equals(""));

      return status;

    } // Status check
    else
    {
      // if response in not 200 just read all the lines till end of packet and ignore it.
      createAuthorizationStringFromResponse("OPTIONS", currentRtspResponsePacket.body());

      // Store the error code and error command.
      lastError = status;
      lastCcommand = "OPTIONS";
      return status;
    } // Status check
  }

  /******************************************************************************************/
  /* Fucntions to send the describe command and get the response and parse it efficiently   */
  /* this function may or may not be called depending on the options response. This gives   */
  /* response in SDP - Session Description Protocol, hence the response is store as object  */
  /*                                                                             -Althaf M  */
  /******************************************************************************************/
  protected int getDescribe() {
    int ret = 0;
    int stat =  sendRtspCommand(getDescribeString());
    if(stat>0)
    {
      ret =  readDescribe();
    }
    if(ret!=200) {
      stat =  sendRtspCommand(getDescribeString());
      if(stat>0)
        ret = readDescribe();
    }

    return ret;
  }
  protected String getDescribeString()
  {
    if(useBasic)
    {
      if(!videoParams.equals(""))
        return "DESCRIBE " + rtspUri + "?" + videoParams + " RTSP/1.0\r\nUser-Agent: Niagara\r\nCSeq:"+CSeq+"\r\nAccept: application/sdp\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes()) +"\r\n\r\n";

      return "DESCRIBE " + rtspUri +  " RTSP/1.0\r\nUser-Agent: Niagara\r\nCSeq:"+CSeq+"\r\nAccept: application/sdp\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes()) +"\r\n\r\n";
    }

    if(!videoParams.equals(""))
      return "DESCRIBE " + rtspUri + "?" + videoParams + " RTSP/1.0\r\nUser-Agent: Niagara\r\nCSeq:"+CSeq+"\r\nAccept: application/sdp\r\nAuthorization: " + authorization +"\r\n";
    else
      return "DESCRIBE " + rtspUri + " RTSP/1.0\r\nUser-Agent: Niagara\r\nCSeq:"+CSeq+"\r\nAccept: application/sdp\r\nAuthorization: " + authorization +"\r\n";

  }

  protected int readDescribe()
  {
    int status = readStatus();
    if(useTcp)
    {

      String[] describeBody = currentRtspResponsePacket.body();
      if(status == 200)
      {
        int state = WAIT_MEDIA_VIDEO;
        for(int i = 0; i< describeBody.length; i++)
        {
          String describeLine = describeBody[i];
          session.parseLine(describeLine.trim());
          // look for media type video, i.e. "m=video"
          // look for media type application for video motion detection i.e, "m=application"
          // then look for trackId/streamid by
          // 1. if content url starts with rtsp schema compare with Content-Base and extract trackId/streamid
          // 2. if it is relative control url extra the aggregate control url string as append with request url

          switch(state)
          {
            case WAIT_MEDIA_VIDEO:
              if(describeLine.startsWith("m=video") || describeLine.startsWith("m=application"))
                state = WAIT_VIDEO_TRACKID;
              break;

            case WAIT_VIDEO_TRACKID:
              state = getState(describeLine, state);
              break;

            default:
              break;
          }
        }
      }
      else
      {
        // if response in not 200 just read all the lines till end of packet and ignore it.
        createAuthorizationStringFromResponse("DESCRIBE", currentRtspResponsePacket.body());
        lastError = status;
        lastCcommand = "DESCRIBE";
      }


      CSeq++;
      return status;
    }
    if(status==200)
    {

      String readLine;

      //read response lines until the first empty line. The next line marks the start
      //of the SDP packet
      while(! (readLine = readResponseLine(MAX_WAIT_TIME)).equals(""));

      //this next line marks the start of the Session Description Protocol block RFC 2327
      readLine = readResponseLine(MAX_WAIT_TIME);
      int state = WAIT_MEDIA_VIDEO;

      while(!readLine.equals(""))
      {
        session.parseLine(readLine.trim());

        // look for media type video, i.e. "m=video"
        // look for media type application for video motion detection i.e, "m=application"
        // then look for trackId/streamid by
        // 1. if content url starts with rtsp schema compare with Content-Base and extract trackId/streamid
        // 2. if it is relative control url extra the aggregate control url string as append with request url

        switch(state)
        {
          case WAIT_MEDIA_VIDEO:
            if(readLine.startsWith("m=video") || readLine.startsWith("m=application") )
              state = WAIT_VIDEO_TRACKID;
            break;
          case WAIT_VIDEO_TRACKID:
            state = getState(readLine, state);
            break;

          default:
            break;
        }

        readLine = readResponseLine(MAX_WAIT_TIME);
      }

      CSeq++;
      return status;
    }

    else
    {
     // if response in not 200 just read all the lines till end of packet and ignore it.
      createAuthorizationStringFromResponse("DESCRIBE", currentRtspResponsePacket.body());
      // Store the error code and error command.
      lastError = status;
      lastCcommand = "DESCRIBE";
      return status;
    }

     /*
      while(true)
      {
        String readLine = readResponseLine(MAX_WAIT_TIME);
        if(readLine=="")
        {
          break;
        }
      }

      if(status == 401)
      {

        String realm = parseValue(sb, "realm");
        nonce = parseValue(sb, "nonce");
        authorization = "";
        try
        {
          String response = getAuthorization(realm, nonce, rtspUri, username, iPhrase, "OPTIONS");
          authorization = response;
          returnValue = -1;
          useBasic = false;

        }
        catch(Exception e)
        {
          e.printStackTrace();
        }

      }
      // Store the error code and error command.
      lastError = status;
      lastCcommand = "DESCRIBE";
      return 0;
    }*/

  }

  private int getState(String readLine, int state) {
    if (readLine.startsWith("a=control")) {
      int index = readLine.indexOf(":");
      if (index > 0) {
        String controlUrl = readLine.substring(index + 1);
        if (controlUrl.startsWith("rtsp:")) {
          controlUrl = bsplit(controlUrl, "?", 2)[0];

          if ((controlUrl.indexOf(currentRtspResponsePacket.contentBase)) > -1) {
            trackid = controlUrl.substring(currentRtspResponsePacket.contentBase.length());
            state = GOT_VIDEO_TRACKID;
          }
        } else {
          this.trackid = readLine.substring(index + 1);
          state = GOT_VIDEO_TRACKID;
        }
      }
    } else if (readLine.startsWith("m=")) {
      state = WAIT_MEDIA_VIDEO;
    }
    return state;
  }

  /******************************************************************************************/
  /* Fucntions to send the setup command and get the response and parse it efficiently      */
  /* this function may or may not be called depending on the options response.              */
  /*                                                                                        */
  /*                                                                             -Althaf M  */
  /******************************************************************************************/
  protected int getSetup()
  {
    int ret = 0;
    int stat =  sendRtspCommand(getSetupString(videoParams));
    if(stat>0)
      ret = readSetup();
    while(ret == 401 || ret == 404) {
      stat =  sendRtspCommand(getSetupString(videoParams));
      if(stat>0)
      ret = readSetup();
    }
    return ret;
  }

  protected String getSetupString(String vid)
  {
    String udpTransportParams = "RTP/AVP/UDP;unicast;client_port="+this.dataPort+"-"+(this.dataPort+1);
    // String tcpTransportParams = "RTP/AVP/TCP;interleaved=0-1";
    String tcpTransportParams = "RTP/AVP/TCP;unicast";
    String transport = (useTcp ? tcpTransportParams : udpTransportParams);

    //the trackid value may already have a query string embedded in it. That query string
    //needs to be removed and replaced with the video param string 'vid' if the param string
    //isn't empty

    int queryIndex = -1;

    if((queryIndex = trackid.indexOf("?")) != -1 && !vid.equals(""))
      trackid = trackid.substring(0, queryIndex);

    if(useBasic)
    {
      if(!vid.equals(""))
        return "SETUP " + rtspUri + "/"+this.trackid+"?"+vid+ " RTSP/1.0\r\nCSeq:"+CSeq+"\r\nBlocksize: "+this.chunksize+"\r\nTransport: "+ transport + "\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes()) + "\r\n\r\n";

      else
        return "SETUP " + rtspUri + "/"+this.trackid+" RTSP/1.0\r\nCSeq:"+CSeq+"\r\nBlocksize: "+this.chunksize+"\r\nTransport: " + transport + "\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes())+ "\r\n\r\n";
    }

    if (!vid.equals(""))
      return "SETUP " + rtspUri + "/"+this.trackid+"?"+vid+" RTSP/1.0\r\nCSeq:"+CSeq+"\r\nBlocksize: "+this.chunksize+"\r\nTransport: " + transport + "\r\nAuthorization: " + authorization +  "\r\n";

    return "SETUP " + rtspUri + "/"+this.trackid+" RTSP/1.0\r\nCSeq:"+CSeq+"\r\nBlocksize: "+this.chunksize+"\r\nTransport: " + transport + "\r\nAuthorization: " + authorization +  "\r\n";

  }

  protected int readSetup()
  {
    int status = readStatus();
    if(useTcp)
    {
      String[] setupBody = currentRtspResponsePacket.body();
      if(status == 200)
      {
        for(int i = 0; i < setupBody.length; i++)
        {
          String setupLine = setupBody[i];
          if(setupLine.startsWith("Session:"))
          {
            String sesId = bsplit(setupLine,":",2)[1];
            sesId = bsplit(sesId, ";", 2)[0].trim();
            session.setSessionId(sesId);
            session.setSessionTimeout(Integer.parseInt(bsplit(setupLine,"timeout=",2)[1]));
          }

          else if(setupLine.startsWith("Transport:"))
          {
            String[] transportTokens = bsplit(setupLine,";");

            for(int j=0;j<transportTokens.length;j++)
            {
              if(transportTokens[j].startsWith("ssrc"))
                session.SSRC = bsplit(transportTokens[j],"=")[1];
            }

          }

        }
      }
      else
      {
        // if response in not 200 just read all the lines till end of packet and ignore it.
        createAuthorizationStringFromResponse("SETUP", currentRtspResponsePacket.body());
        lastError = status;
        lastCcommand = "SETUP";
      }


      CSeq++;
      return status;
    }

    if(status==200)
    {

      String readLine = readResponseLine(MAX_WAIT_TIME);
      if(Integer.parseInt(bsplit(readLine,":")[1].trim()) == CSeq)
      {
      readLine = readResponseLine(MAX_WAIT_TIME);
      if(readLine.indexOf("Date:")>=0) {
        String dateString = readLine;
      }

      while(readLine!="") {
          if(readLine.startsWith("Session")) {
            String sesId = bsplit(readLine,":",2)[1];
            sesId = bsplit(sesId, ";", 2)[0].trim();
            session.setSessionId(sesId);
            session.setSessionTimeout(Integer.parseInt(bsplit(readLine,"timeout=",2)[1]));

          }
         if(readLine.startsWith("Transport"))
         {
         String[] spliStr = bsplit(readLine,";");
         for(int i=0;i<spliStr.length;i++) {
           if(spliStr[i].startsWith("server_port=")) {
             String[] ports =bsplit(bsplit(spliStr[i],"=")[1],"-");
             session.server_port_rtp = Integer.parseInt(ports[0]);
             session.server_port_rtcp = Integer.parseInt(ports[1]);
          }

         if(spliStr[i].startsWith("ssrc")) {
           session.SSRC = bsplit(spliStr[i],"=")[1];
           }
         }
         }
         readLine = readResponseLine(MAX_WAIT_TIME);
      }

      }
      CSeq++;
      return status;
    }
    else {
      // if response in not 200 just read all the lines till end of packet and ignore it.
      createAuthorizationStringFromResponse("SETUP", currentRtspResponsePacket.body());

      // Store the error code and error command.
      lastError = status;
      lastCcommand = "SETUP";
      return status;


     /* while(true) {
        String readLine = readResponseLine(MAX_WAIT_TIME);
        if(readLine=="") {
          break;
        }
      }
      // Store the error code and error command.
      lastError = status;
      lastCcommand = "SETUP";


      return 0;*/
    }

  }

  /******************************************************************************************/
  /* Fucntions to send the play command and get the response and parse it efficiently      */
  /* this function may or may not be called depending on the options response.              */
  /*                                                                                        */
  /*                                                                             -Althaf M  */
  /******************************************************************************************/
  protected int getPlay() {
    int ret = 0;
    int stat =  sendRtspCommand(getPlayString(videoParams));
    if(stat>0)
    ret = readPlay();
    if(ret!=200) {
      stat =  sendRtspCommand(getPlayString(videoParams));
      if(stat>0)
      ret = readPlay();
    }
    return ret;
  }
  protected String getPlayString(String vid)
  {
    if(useBasic)
    {
      if(!vid.equals(""))
        return "PLAY " + rtspUri + "?" + vid + " RTSP/1.0\r\nCSeq: "+CSeq+"\r\nSession: "+session.sessionId+"\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes())+"\r\n\r\n";
      else
        return "PLAY /"+bsplit(this.hostname,"/",2)[1]+" RTSP/1.0\r\nCSeq: "+CSeq+"\r\nSession: "+session.sessionId+"\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes())+"\r\n\r\n";
    }

     if(!vid.equals(""))
       return "PLAY " + rtspUri + "?" + vid + " RTSP/1.0\r\nCSeq: "+CSeq+"\r\nSession: "+session.sessionId + "\r\nRange: npt=0.000-" + "\r\nAuthorization: " + authorization + "\r\n";

      return "PLAY " + rtspUri + " RTSP/1.0\r\nCSeq: "+CSeq+"\r\nSession: "+session.sessionId + "\r\nRange: npt=0.000-" + "\r\nAuthorization: " + authorization + "\r\n";
  }

  protected int readPlay()
  {
    int status = readStatus();
    if(useTcp)
    {
      if(status != 200)
      {
        // if response in not 200 just read all the lines till end of packet and ignore it.
        createAuthorizationStringFromResponse("PLAY", currentRtspResponsePacket.body());

        // Store the error code and error command.
        lastError = status;
        lastCcommand = "PLAY";

      }
      CSeq++;
      return status;
    }

    if(status==200) {
      String readLine = readResponseLine(MAX_WAIT_TIME);
      if(Integer.parseInt(bsplit(readLine,":")[1].trim()) == CSeq)
      {
      readLine = readResponseLine(MAX_WAIT_TIME);
      if(readLine.indexOf("Date:")>=0) {
        String dateString = readLine;
      }
      while(readLine!="") {
        readLine = readResponseLine(MAX_WAIT_TIME);
      }

      }
      CSeq++;
      return status;
    }
    else {

      // if response in not 200 just read all the lines till end of packet and ignore it.
      createAuthorizationStringFromResponse("PLAY", currentRtspResponsePacket.body());
      // Store the error code and error command.
      lastError = status;
      lastCcommand = "PLAY";
      return status;
    }

  }

  public String createAuthorizationStringFromResponse(String methodString, String[] responseBody)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = responseBody.length - 1; i >= 0; i--) {
      sb.append(responseBody[i]);
    }
    realm = parseValue(sb, "realm");
    nonce = parseValue(sb, "nonce");
    authorization = "";
    try
    {
      String response = getAuthorization(realm, nonce, rtspUri, username, iPhrase, methodString);
      authorization = response;
      useBasic = false;
    }
    catch(Exception e)
    {
      if(logger.isLoggable(Level.SEVERE))
        logger.log(Level.SEVERE, "Could not create the authorization string from the response", e);
    }
    return authorization;
  }


  /******************************************************************************************/
  /* Fucntions to send the TEARDOWN command and get the response and parse it efficiently      */
  /* this function may or may not be called depending on the options response.              */
  /*                                                                                        */
  /*                                                                             -Althaf M  */
  /******************************************************************************************/
  protected int doStop()
  {
    //Thread.dumpStack();
    int ret = 0;

    int stat =  sendRtspCommand(getTearDownString());

    if(stat>0)
    {
      //int stopResult = readStop();
      int stopResult = 200;

      return stopResult;
    }

    return ret;
  }
  protected String getTearDownString()
  {
    if(useBasic)
      return "TEARDOWN /"+bsplit(this.hostname,"/",2)[1]+" RTSP/1.0\r\nCSeq: "+CSeq+"\r\nSession: "+session.sessionId+"\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes())+"\r\n\r\n";

    return "TEARDOWN " + rtspUri + " RTSP/1.0\r\nCSeq: "+CSeq+"\r\nSession: "+session.sessionId+"\r\n\r\n";

  }
  protected int readStop()
  {
    int status = readStatus();

    if(useTcp && status == 200)
    {
      CSeq++;
      return status;
    }

    if(status==200)
    {
      String readLine = readResponseLine(MAX_WAIT_TIME);

      if(Integer.parseInt(bsplit(readLine,":")[1].trim()) == CSeq)
      {
        readLine = readResponseLine(MAX_WAIT_TIME);
        if(readLine.indexOf("Date:")>=0) {
          String dateString = readLine;
        }

        while(readLine!="") {
          readLine = readResponseLine(MAX_WAIT_TIME);
        }

      }
      CSeq++;
      return status;
    }

     else {
      while(true) {
        String readLine = readResponseLine(MAX_WAIT_TIME);
        if(readLine=="") {
          break;
        }
      }
      // Store the error code and error command.
      lastError = status;
      lastCcommand = "TEARDOWN";
      return 0;
    }

  }

  /******************************************************************************************/
  /* Fucntions to send the PAUSE command and get the response and parse it efficiently      */
  /* this function may or may not be called depending on the options response.              */
  /*                                                                                        */
  /*                                                                             -Althaf M  */
  /******************************************************************************************/
  protected int doPause() {
    int ret = 0;
    int stat =  sendRtspCommand(getPauseString());
    if(stat>0)
    return readPause();
    return ret;
  }
  protected String getPauseString()
  {
    if(useBasic) {
      return "PAUSE /" + bsplit(this.hostname, "/", 2)[1] + " RTSP/1.0\r\nCSeq: " + CSeq + "\r\nSession: " + session.sessionId + "\r\nAuthorization: Basic " + Base64.getEncoder().encodeToString((this.username + ":" + this.iPhrase).getBytes()) + "\r\n\r\n";
    }

    return "PAUSE " + rtspUri + " RTSP/1.0\r\nCSeq: "+CSeq+"\r\nSession: "+session.sessionId+"\r\n\r\n";
  }

  protected int readPause() {
    int status = readStatus();
    if(status==200) {
      String readLine = readResponseLine(MAX_WAIT_TIME);
      if(Integer.parseInt(bsplit(readLine,":")[1].trim()) == CSeq)
      {
      readLine = readResponseLine(MAX_WAIT_TIME);
      if(readLine.indexOf("Date:")>=0) {
        String dateString = readLine;
      }
      while(readLine!="") {
        readLine = readResponseLine(MAX_WAIT_TIME);
      }

      }
      CSeq++;
      return status;
    }
    else {
      while(true) {
        String readLine = readResponseLine(MAX_WAIT_TIME);
        if(readLine=="") {
          break;
        }
      }
      // Store the error code and error command.
      lastError = status;
      lastCcommand = "PAUSE";
      return 0;
    }

  }



  // Parameter Setup
  public int setParameter(String parameter)
  {
    int ret = 0;
    int stat =  sendRtspCommand(getSetParameterString(parameter));

    if(stat>0)
    {
      ret = readSetParameter();

    }

    return ret;
  }

  protected String getSetParameterString(String parameter)
  {
    String udpTransportParams = "RTP/AVP/UDP;unicast;client_port="+this.dataPort+"-"+(this.dataPort+1);
    String tcpTransportParams = "RTP/AVP/TCP;interleaved=0-1";
    String transport = (useTcp ? tcpTransportParams : udpTransportParams);

    if(useBasic)
      return "SET_PARAMETER rtsp://"+this.hostname+" RTSP/1.0\r\nCSeq:"+CSeq+"\r\nTransport: " + transport +"\r\nAuthorization: Basic "+ Base64.getEncoder().encodeToString((this.username+":"+this.iPhrase).getBytes())+"\r\nContent-Type: text/parameters\r\nContent-Length:"+parameter.length()+"\r\n\r\n"+parameter+"\r\n\r\n";

    return "SET_PARAMETER " + rtspUri + " RTSP/1.0\r\nCSeq:"+CSeq+"\r\nTransport: " + transport +"\r\nContent-Type: text/parameters\r\nContent-Length:"+parameter.length()+"\r\n\r\n"+parameter+"\r\n\r\n";
  }

  protected int readSetParameter()
  {
    int status = readStatus();
    if(status==200) {
      String readLine = readResponseLine(MAX_WAIT_TIME);
      if(Integer.parseInt(bsplit(readLine,":")[1].trim()) == CSeq)
      {
      readLine = readResponseLine(MAX_WAIT_TIME);
      if(readLine.indexOf("Date:")>=0) {
        String dateString = readLine;
      }
      while(readLine!="") {
        if(readLine.startsWith("Session")) {
          session.setSessionId(bsplit(readLine,":",2)[1]);
        }
         if(readLine.startsWith("Transport")) {

        }
         readLine = readResponseLine(MAX_WAIT_TIME);
      }

      }
      CSeq++;
      return status;
    }
    else {
      while(true) {
        String readLine = readResponseLine(MAX_WAIT_TIME);
        if(readLine=="") {
          break;
        }
      }
      // Store the error code and error command.
      lastError = status;
      lastCcommand = "SET_PARAMETER";
      return 0;
    }

  }

  // Keep Alive
  protected int sendKeepAlive()
  {
    return sendRtspCommand(getAliveString());
  }

  protected String getAliveString()
  {
     if(keepAliveCommandString == null)
      keepAliveCommandString = "OPTIONS " + rtspUri + "?" + videoParams + " RTSP/1.0\r\nUser-Agent: Niagara\r\nCSeq:"+CSeq+"\r\nSession :"+session.sessionId+"\r\n\r\n";
     return keepAliveCommandString;
  }





  /******************************************************************************/
  /* Function for connecting which will make this similar to net package or     */
  /* normal tcp socket connection, also having an isConnected method to test    */
  /* whether the initial steps are done properly.                               */
  /******************************************************************************/

  public void connect(String vid) throws Exception
  {
    setupControlSocket();

    this.videoParams = vid;

    int returned = getOptions();

    if(returned==200)
    {
      this.state = describe;

      if(this.is_describe == true)
      {
        returned = getDescribe();

        if(returned==200)
        {
          this.state = initsetup;
        }
        else
        {
          throw new Exception("Unable to get a proper response to DESCRIBE");
        }
      }
    }
    else
    {
      throw new Exception("Unable to get a proper response to OPTIONS");
    }

  }

  public boolean isConnected() {
    if(this.state == initsetup) {
      return true;
    }
    return false;
  }

  public void play() throws Exception
  {

    int status=0;
    if(isConnected() && is_play)
    {
      status = getSetup();

      if(status==200)
      {
        this.state = setup;

        status = getPlay();

        if(status==200)
        {
          this.state = play;

          try
          {
            if(keepAliveTask == null)
              keepAliveTask = new KeepAliveTask();

            if(keepAliveTimer == null)
              keepAliveTimer = new Timer();

            long keepAlivePeriod = ((long)session.timeout) * 500;

            keepAliveTimer.scheduleAtFixedRate(keepAliveTask, keepAlivePeriod, keepAlivePeriod);

            if(logger.isLoggable(Level.FINER))
              logger.finer("***** KEEP ALIVE TIMER THREAD STARTED ******");
            if(!useTcp)
            {
              udpReader.start();
              //this.rtcpReader.start();
              if(logger.isLoggable(Level.FINER))
                logger.finer("***** RTP PACKET READER STARTED ******");
            }

           }
           catch(Exception e)
           {
             if(logger.isLoggable(Level.SEVERE))
               logger.log(Level.SEVERE, "There was an error starting the data readers", e);
           }
        }

        else
        {
           throw new Exception("Cannot PLAY");
        }
      }

      else
      {
        throw new Exception("Cannot SETUP");
      }
    }

  }

  public boolean isPlaying()
  {
    if(this.state == play) {
      return true;
    }
    return false;
  }

  public void pause() throws Exception {
    if(isPlaying() && is_pause) {
      int ret = doPause();
      if(ret!=200) {
        throw new Exception("Cannot Pause");

      }else {

        if(!useTcp)
          synchronized (this)
          {
            this.wait();
          }
        this.state = paused;
      }

    }
  }
  public boolean isPaused() {
    if(this.state == paused) {
      return true;
    }
    return false;
  }

  public void stop() throws Exception
  {
    if((isPlaying() || isPaused()) && is_teardown)
    {
      this.state = init;

      //send the TEARDOWN command
      logger.log(Level.FINE, "TEARDOWN command sent on calling stop()");
      doStop();

      /*//stop the keep alive timer thread
      if(keepAliveTimer != null)
      {
        keepAliveTimer.cancel();
        keepAliveTimer = null;
      }
      */

      //stop the TCP or UDP thread (this will stop the keep alive thread as well)
      if(useTcp && tcpReader != null) tcpReader.done = true;
      else if(udpReader != null) udpReader.done = true;

      //finally close the control socket
      if(controlSocket != null)
        controlSocket.close();

    }

  }


  public void cleanUp() {

    try {
      this.controlSocket.close();
      }
    catch(Exception e) {

      if(logger.isLoggable(Level.SEVERE))
        logger.log(Level.SEVERE,"Socket has been closed on Cleanup()...", e);
    }

  }

  public boolean isStopped() {
    if(this.state == init)
      return true;
    else
      return false;
  }
  public InputStream getInputStream() throws IOException{
    if(this.pipein != null) {
    return this.pipein;
    }
    else
      throw new IOException("Null Stream");
  }


  protected int readStatus()
  {

    if(!useTcp)
    {
      String statusLine;

      while((statusLine = readResponseLine(MAX_WAIT_TIME)).indexOf("RTSP/1.0") == -1)
      {
        if(logger.isLoggable(Level.FINER))
          logger.finer("Finished waiting for RTSP response status code...");
      }

      String[] tokens = TextUtil.split(statusLine, ' ');

      for(int i = 0; i < tokens.length; i++)
        if(tokens[i].indexOf("RTSP/1.0") != -1)
          return Integer.parseInt(tokens[i+1]);

    }

    else
    {
      //return the status from the current rtsp response packet
      if(currentRtspResponsePacket != null && currentRtspResponsePacket.cseq == CSeq)
        return currentRtspResponsePacket.status;
    }

    return -1;


  }

  protected String readResponseLine(int timeout)
  {

      String line = "";
      try
      {
        controlSocket.setSoTimeout(2000);

      }

      catch(Exception e){}
      try
      {
        long maxWaitTicks = Clock.ticks() + timeout;

        while(true && !controlSocket.isClosed())
        {
          if(Clock.ticks() > maxWaitTicks)
          {
            line = " ";
            break;
          }

          synchronized(readMonitor)
          {
            int readByte = controlInput.read();
            if(readByte == 0x0D) {
            int nextByte = controlInput.read();
              if(nextByte == 0x0A) {
                break;
              }
              else {
                line += (char)readByte;
                line += (char)nextByte;
              }
            }
            else if(readByte ==0x0A) {
              break;
            }
            else {
              if(readByte!=-1)
              {
                line += (char)readByte;
                maxWaitTicks = Clock.ticks() + timeout;
              }
            }
          }
        }

      }
      catch(Exception e)
      {
        if(logger.isLoggable(Level.SEVERE))
          logger.log(Level.SEVERE, "Could not read bytes from the response stream...", e);
      }
      if(logger.isLoggable(Level.FINER))
        logger.finer("received = " + line);
      return line.trim();
  }

  public int sendRtspCommand(String command)
  {
    int ret = 1;

    try
    {
      if(controlSocket != null && !controlSocket.isClosed())
      {

        controlOut.write(command.getBytes());
        controlOut.flush();

        //cache the current rtsp response from the rtsp response packet queue
        if(useTcp && tcpReader.isAlive())
        {

          synchronized(tcpRtspPacketQueue)
          {
            // NCCB-35961 - Could not do RTSP live streaming after subscribing for the motion detection events.
            // Uncommented this wait statement... when live streaming and motion detection messages are coming simultaniously,
            // RTP packet are getting created little slow. So, added 2 seconds of wait before RTP packet is dequeued to process.
            // NCCB-36441 - Preset and PTZ operations under Axis camera live view in JACE are inconsistent.
            // Changed 2 seconds waiting time to 1 second to reduce to the latency on showing Preset and PTZ positions.
            tcpRtspPacketQueue.wait(1000);
            // End.
            currentRtspResponsePacket = (RtspResponsePacket) tcpRtspPacketQueue.dequeue(1000);
          }

        }
        else
        {
          if(null != udpReader && udpReader.isAlive())
          {
            synchronized (udpRtspPacketQueue) {
              currentRtspResponsePacket = (RtspResponsePacket) udpRtspPacketQueue.dequeue(1000);
            }
          }

        }
      }
    }

    catch(Exception ex)
    {
      if(logger.isLoggable(Level.SEVERE))
        logger.log(Level.SEVERE,"Exception while sending RTSP command", ex);
      ret = -1;
    }

    return ret;
  }

  /************************************************************************/
  /* Thread which will read continuously from the udp socket for filling  */
  /* the stream. In the constructor it creates the socket, and run it     */
  /* reads the data                                                       */
  /************************************************************************/
  public class UdpDataReader extends Thread
  {

    public UdpDataReader(String threadName,
                         int dataport,
                         RtpStream rts,
                         boolean rtp) throws Exception
    {

      setName(threadName);
      this.dataport = dataport;
      rtpStream = rts;
      RTP = rtp;

      try
      {
         udpSocket =  new DatagramSocket(dataport);
         udpSocket.setReceiveBufferSize(8192000);
         udpSocket.setSoTimeout(1000);
      }

      catch(Exception e)
      {
        throw e;
      }

      rtpStream.setPortandBuffer(dataport, udpSocket.getReceiveBufferSize());
      done = false;

    }


    public void run()
    {

      while(! (done || controlSocket.isClosed()))
      {

        try
        {

          //Receive a new UDP packet
          if(udpSocket !=null && !udpSocket.isClosed())
          {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
              public Void run() {
                try {
                  udpSocket.receive(udpPacket);
                } catch (IOException ex) {
                  logger.log(Level.SEVERE, "UDP Reader receive timeout",ex);
                }
                return null;
              }
            });

          }
          //copy the UDP packet's payload into the RTP data buffer
          System.arraycopy(udpPacket.getData(), 0, rtpData, 0, udpPacket.getLength());

          RtpPacket rtpPacket = new RtpPacket();

          rtpPacket.read(rtpData, udpPacket.getLength());

          numRetries = 0; //reset the retries

          rtpStream.addPacket(rtpPacket, true);

          synchronized(rtpStream)
          {
            long startTime = Clock.ticks();

            rtpStream.wait(PACKET_DEQUEUE_TIMEOUT); //wait a maximum of 5 seconds to be notified by rtpStream

            long waitTime = Clock.ticks() - startTime;

            //the thread woke up from the wait after the PACKET_DEQUEUE_TIMEOUT expiration which
            //means it wasn't notified. This implies the video decoder isn't processing
            //packets from the queue anymore
            if(waitTime >= PACKET_DEQUEUE_TIMEOUT)
              break;

          }
        }

        catch(Exception ex)
        {
          if(ex instanceof SocketTimeoutException && numRetries++ < MAX_READ_RETRIES) continue;

          if(logger.isLoggable(Level.SEVERE))
            logger.log(Level.SEVERE, "UDP Reader read timeout",ex);
          break;
        }

      }

      logger.log(Level.FINE, "TEARDOWN command sent from UDPDataReader thread...");
      doStop();

      if(keepAliveTimer != null)
        keepAliveTimer.cancel();

      try
      {
        udpSocket.close();
        rtpStream.close();
        controlSocket.close();
      }
      catch (IOException e)
      {
        // TODO Auto-generated catch block
        if(logger.isLoggable(Level.SEVERE))
          logger.log(Level.SEVERE,"Could not close UDP sockets and streams", e);
      }

    }

    boolean done = false;
    int dataport;
    DatagramSocket udpSocket;
    RtpStream rtpStream;
    boolean RTP;
    byte[] buf  = new byte[1600];
    byte[] rtpData = new byte[1600];
    DatagramPacket udpPacket = new DatagramPacket(buf, buf.length);
    int numRetries = 0;
  }

  public class KeepAliveTask extends TimerTask
  {
    public void run()
    {
      sendKeepAlive();
    }
  }

  public class TcpInputStreamReader extends Thread
  {
    public TcpInputStreamReader()
    {
      setName("RtspTcpReader");
    }

    public void run()
    {
      while(!(done || controlSocket.isClosed()))
      {
          try
          {
            byte[] prefixBytes = new byte[8];

            //read the first two bytes to test the start of a video frame.
            buffIn.read(prefixBytes, 0, 2);

            //check if the prefix indicates the start of a video frame RTP payload
            if(prefixBytes[0] == 0x24 && prefixBytes[1] == 0x00)
            {

              //read the next 2 bytes to determine payload size
              int rtpPayloadSize = din.readUnsignedShort();
              if(tcpRtpPayloadBuffer == null || tcpRtpPayloadBuffer.length != rtpPayloadSize)
                tcpRtpPayloadBuffer = new byte[rtpPayloadSize];

              din.readFully(tcpRtpPayloadBuffer);
              RtpPacket rtpPacket = new RtpPacket(tcpRtpPayloadBuffer,true);

              rtpPacket.read(tcpRtpPayloadBuffer, rtpPayloadSize);

              pipein.addPacket(rtpPacket, true);

              numRetries = 0; //reset the retries

              synchronized(pipein)
              {

                long startTime = Clock.ticks();

                pipein.wait(PACKET_DEQUEUE_TIMEOUT); //wait to be notified by rtpStream

                long waitTime = Clock.ticks() - startTime;

                //the thread woke up from the wait after the PACKET_DEQUEUE_TIMEOUT expiration which
                //means it wasn't notified. This implies the video decoder isn't processing
                //packets from the queue anymore
                if(waitTime >= PACKET_DEQUEUE_TIMEOUT)
                    break;



              }
            }

            else
            {

              //we didn't encounter a video frame so read the remaining six bytes to check if this is the 'RTSP/1.0' string
              buffIn.read(prefixBytes, 2, 6);

              //compare the bytes in rtspPrefix to "RTSP/1.0"
              String rtspPrefix = new String(prefixBytes);
              if(rtspPrefix.equals("RTSP/1.0"))
              {
                //initialize an RTSP packet
                RtspResponsePacket rtspResponsePacket = new RtspResponsePacket();
                String line = rtspPrefix + bufferedReader.readLine(); //append the rest of the line from the stream to the prefix
                boolean isSdp = false;
                int sdpContentLength = 0;
                //keep reading lines until the blank line is encountered
                while(!line.equals(""))
                {
                  rtspResponsePacket.body.add(line);

                  String[] lineTokens = TextUtil.split(line, ' ');

                  if(lineTokens[0].equals("RTSP/1.0")) {
                    rtspResponsePacket.status = Integer.parseInt(lineTokens[1]);
                  }
                  else if(lineTokens[0].equals("CSeq:")) {
                    rtspResponsePacket.cseq = Integer.parseInt(lineTokens[1]);
                  }
                  else if(lineTokens[0].equals("Content-Length:")) {
                    sdpContentLength = Integer.parseInt(lineTokens[1]);
                  }
                  else if(lineTokens[0].equals("Content-Base:")) {
                    rtspResponsePacket.contentBase = lineTokens[1];
                  }
                  else if(line.startsWith("Content-Type: application/sdp")) {
                    isSdp = true;
                  }
                  line = bufferedReader.readLine();
                }
                if(isSdp)
                {
                  while(sdpContentLength > 0)
                  {
                    line = bufferedReader.readLine();
                    rtspResponsePacket.body.add(line);
                    sdpContentLength -= (line.getBytes().length + 2); //2 bytes added to account for 0x0d 0x0a
                  }

                }

                synchronized(tcpRtspPacketQueue)
                {
                  tcpRtspPacketQueue.enqueue(rtspResponsePacket);
                  tcpRtspPacketQueue.notifyAll();
                }
              }
            }

          }

          catch (Exception ex)
          {
            if(ex instanceof SocketTimeoutException && numRetries++ < MAX_READ_RETRIES) continue;

            // NCCB-36632 Motion detection alarms not flowing under Axis video integration
            // Commenting this logger off since motion detection event shall be streamed on RTSP where
            //  the a dedicated thread is monitoring for it. This shall throw SocketTimeoutException when
            // there is NO event. So, the entire station console shall be filled with this trace only.
            // if(logger.isLoggable(Level.SEVERE))
            //    logger.log(Level.SEVERE, "TCP Stream Reader read timeout", ex);
            // End.
            //NCCB-35530: Commented to ignore the SocketTimeoutException to continue with the connection. Changed for motion detection over RTSP.
            //  break;
          }
      }

      logger.log(Level.FINE, "TEARDOWN command sent on TCPStreamReader thread...");
      doStop();

      if(keepAliveTimer != null)
        keepAliveTimer.cancel();

      try
      {
        tcpRtspPacketQueue.clear();
        pipein.close();
        controlSocket.close();
      }
      catch (IOException e)
      {
        if(logger.isLoggable(Level.SEVERE))
          logger.log(Level.SEVERE,"Could not close tcp stream or sockets", e);
      }
    }

    private boolean done = false;
    private int numRetries = 0;

  }

  public class RtspResponsePacket
  {
    public int status;
    public int cseq;
    public String contentBase = "";
    public Array<String> body = new Array<>(String.class);

    public String[] body()
    {
      return body.trim();
    }
  }

  /*
   *  Class in which i will store the data of session, for internal use as well
   *  as for advanced programming.
   *                                                                  Althaf M
   */

  public static class SessionObject
  {
    public SessionObject() {

    }
    public void setSessionId(String id)
    {
      sessionId = id;
    }
    public void setSessionTimeout(int time)
    {
      this.timeout = time;
      updated = BAbsTime.now().getMillis();
    }
    public void parseLine(String str) {
      this.sdpString += (str + "\n");

    }
    public long getCurrentTime() {
      return currentTime;
    }

    public long currentTime =0;
    public long updated =0;
    public int timeout = 0,server_port_rtp,server_port_rtcp;
    public String sdpString="";
    public String sessionId="",SSRC="";
  }


///////////////////////////////////////////////////////////////////////////////
//Utilities
///////////////////////////////////////////////////////////////////////////////
  /**
   * Sets the authorization header using the digest authentication scheme
   * described in RFC 2617
   */
  public String getAuthorization(String realm, String nonce, String uri, String user, String psw, String method)
    throws IOException
  {
    CSeq++;

    // response is H(H(A1) ":" unq(nonce) ":" nc ":"  H(A2))
    //   where A1 is unq(username) ":" realm ":" passwd
    //   and   A2 is method ":" unq(uri)


    StringBuilder response = new StringBuilder();
    response.append("Digest username=").append("\"").append(user ).append("\"").append(", ");
    response.append("realm=")          .append("\"").append(realm).append("\"").append(", ");
    response.append("nonce=")          .append("\"").append(nonce).append("\"").append(", ");
    response.append("uri=")            .append("\"").append(uri  ).append("\"").append(", ");
    response.append("response=\"");

    String A1 = user + ':' + realm + ':' + iPhrase;
    String hA1 = hexMD5(A1);
    String A2 = method + ':' + uri;
    String hA2 = hexMD5(A2);

    response.append(hexMD5( hA1 + ':' + nonce + ':' + hA2));

//    response.append(hexMD5(hexMD5(user  + ':' +
//                                  realm + ':' +
//                                  psw)   + ':' +
//                           hexMD5(method + ':' + uri)));

    response.append("\"\r\n");
    return response.toString();
  }


 protected String hexMD5(String in)
 {
   StringBuilder result = new StringBuilder();
   try
   {
     MessageDigest md5 = MessageDigest.getInstance("MD5");
     byte[] toHash = md5.digest(in.getBytes());
     for (int i = 0; i < toHash.length; i++)
     {
       result.append(TextUtil.toLowerCase(TextUtil.byteToHexString(toHash[i])));
     }
   }
   catch (Exception e)
   {
     if(logger.isLoggable(Level.SEVERE))
       logger.log(Level.SEVERE, "Error in generating hexMD5 for digest authentication...", e);
   }
   return result.toString();
 }




  public String[] bsplit(String splitStr,String delimiter) {
    return bsplit(splitStr,delimiter,0);
  }

  public String[] bsplit(String splitStr,String delimiter,int limit) {
    if(null != delimiter && delimiter.length() == 0) {
      char[] chars = splitStr.toCharArray();
      StringBuilder tk = new StringBuilder();
      Vector<String> nulldelimString = new Vector<>();
      for(int i=0;i<chars.length;i++){
        tk.append(chars[i]);
        nulldelimString.addElement(tk.toString());
        tk.setLength(0);
      }
      String[] splitArray = new String[nulldelimString.size()];
      for (int i = 0; i < splitArray.length; i++)
      {
          splitArray[i] = nulldelimString.elementAt(i);
      }

      return splitArray;
    }
    else if (delimiter == null)
    {
      throw new NullPointerException("Delimiter cannot be null");
    }
    else if (splitStr == null)
    {
        throw new NullPointerException("String source cannot be null");
    }

    int maximumSplits = Integer.MAX_VALUE;

    boolean dropTailingDelimiters = true;
    if(limit<=0) {
      dropTailingDelimiters = false;
    }
    else {
      dropTailingDelimiters = true;
      maximumSplits = limit -1;
    }
    StringBuilder token = new StringBuilder();
    Vector<String> tokens = new Vector<>();
    char[] stringChars = splitStr.toCharArray();
    int splicounter = 0;
    boolean lastWasDelimiter = false;
    for(int i=0;i<stringChars.length;i++) {
      if (i + delimiter.length() <= stringChars.length && splicounter < maximumSplits)
      {
        String candidate = new java.lang.String(stringChars, i, delimiter.length());
        if (candidate.equals(delimiter))
        {
            tokens.addElement(token.toString());
            token.setLength(0);
            lastWasDelimiter = true;
            splicounter++;
            i = i + delimiter.length() - 1;
            continue; // continue the for-loop
        }
      }
      token.append(stringChars[i]);
      lastWasDelimiter = false;
    }
    if (token.length() > 0 || (lastWasDelimiter && !dropTailingDelimiters))
    {
        tokens.addElement(token.toString());
    }
    // convert the vector into an array
    String[] splitArray = new String[tokens.size()];
    for (int i = 0; i < splitArray.length; i++)
    {
        splitArray[i] = tokens.elementAt(i);
    }

    return splitArray;
  }

  private String parseValue(StringBuilder sb, String key)
  {
    int index = sb.indexOf(key);
    if(index < 0)
      return "";
    int start = sb.indexOf("\"", index);
    int end = sb.indexOf("\"", start+1);
    if(start < 0 || end < 0)
      return "";
    return sb.substring(start+1, end);
 }

  protected Socket controlSocket;
  protected OutputStream controlOut; // output stream for the tcp socket
  protected InputStream controlInput; // input stream for the tcp socket

  protected int state = 0; // represents the state of the current connection / statemachine

  protected int CSeq=0,present_session=0;
  protected String trackid = "0";

  // represents the various states
  static final int init = 0;
  static final int describe = 1;
  static final int initsetup=2;
  static final int setup = 3;
  static final int ready = 4;
  static final int play = 5;
  static final int paused = 6;

  static final int WAIT_MEDIA_VIDEO = 0;
  static final int WAIT_VIDEO_TRACKID = 1;
  static final int GOT_VIDEO_TRACKID = 2;

  protected int lastError = 0;
  protected String lastCcommand = "";
  private boolean is_describe,is_setup,is_play,is_pause,is_teardown,is_get_parameter,is_set_parameter,is_announce,is_record,is_redirect;
  protected String hostname = "",username="", iPhrase ="";
  protected int controlPort=554,dataPort=9000;
  protected String rtspUri = "";
  public int chunksize=1500;
  protected int timeout=1000;
  String videoParams = "";
  boolean useBasic = true;
  String nonce = "", realm = "";
  String authorization = "";
  protected SessionObject session;
  static int MAX_WAIT_TIME = 5000;

  //////////////////////////////////////////////////////////////////
  //STREAM READERS
  /////////////////////////////////////////////////////////////////

  //UDP
  protected UdpDataReader udpReader; // Object for reader thread
  protected UdpDataReader rtcpReader;

  //TCP
  boolean useTcp = false;
  protected KeepAliveTask keepAliveTask; //sends keep alive signals at regular intervals
  protected TcpInputStreamReader tcpReader; //decapsulates control socket in stream into RTP and RTSP packets and enqueues them to
  private static final int MAX_READ_RETRIES = 20;                                                     //tcpRtpPacketQueue and tcpRtspPacketQueue

  private static final int PACKET_DEQUEUE_TIMEOUT = 3000;
  private String keepAliveCommandString;
  boolean useQueue;
  int rtpPacketQueueSize;
  BufferedInputStream buffIn;
  BufferedReader bufferedReader;
  Queue tcpRtpPacketQueue;
  Queue tcpRtspPacketQueue;
  Queue udpRtspPacketQueue;
  private Object readMonitor = new Object();
  private RtspResponsePacket currentRtspResponsePacket;
  DataInputStream din;
  RtpStream pipein;
  Timer keepAliveTimer;
  byte[] tcpRtpPayloadBuffer;

  private static final Logger logger = Logger.getLogger("RtspConnection.file");
}
