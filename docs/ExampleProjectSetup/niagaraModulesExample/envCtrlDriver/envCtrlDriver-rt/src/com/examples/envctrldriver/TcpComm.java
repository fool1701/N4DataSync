/*
 * Copyright (c) 2014 Tridium, Inc. All Rights Reserved.
 */

package com.examples.envctrldriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.baja.sys.BajaRuntimeException;

/**
 * Provides {@link ServerSocket} to connect with driver network to send and
 * receive messages.
 *
 * @author J. Spangler on Feb 15, 2013
 */
public class TcpComm
{
  public TcpComm(BEnvCtrlDeviceNetwork network)
  {
    this.network = network;
  }

  /**
   * This method sends the String request to the remote server at the
   * configured network port after formatting the string message with
   * the specific protocol format of the EnvController protocol.
   *
   * @param request
   * @return String response from the server.
   */
  public String sendRequest(String request)
  {
    String response = null;

    Socket sock = new Socket();
    PrintWriter out = null;
    BufferedReader in = null;

    try
    {
      //get the IP address and port to which our socket will connect
      String addr = network.getIpAddress();
      int ipPort = network.getIpPort();

      try
      {
        //attempt to connect to our address with a 1 second timeout
        InetSocketAddress insa = new InetSocketAddress(InetAddress.getByName(addr), ipPort);
        sock.connect(insa, 1000);
      }
      catch (Exception e)
      {
        sock = null;
        throw new BajaRuntimeException("Can not connect to " + addr, e);
      }

      //Create an output and input writer and reader
      out = new PrintWriter(sock.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

      //encode the request with the starting and ending chars that
      //are specific to our device's protocol 
      StringBuilder msg = new StringBuilder();
      msg.append((char)0);
      msg.append(request);
      msg.append((char)23);

      //Flush our message to the output stream
      out.print(msg.toString());
      out.flush();

      //Listen to the input stream for the device network response 
      char val;
      StringBuilder value = new StringBuilder();
      while ((val = (char)in.read()) > -1)
      {
        if (val == (char)0)
        {
          continue;
        }
        else if (val == (char)23)
        {
          break;
        }

        value.append(val);
      }

      response = value.toString();

    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
    finally
    {
      try
      {
        if (null != out)
        {
          out.close();
        }
        if (null != in)
        {
          in.close();
        }
        if (null != sock)
        {
          sock.close();
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }

    return response;
  }

/////////////////////////////////////////////////////////////////
//  Attributes
/////////////////////////////////////////////////////////////////

  private BEnvCtrlDeviceNetwork network;
}
