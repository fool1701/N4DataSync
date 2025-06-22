/**
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.messages;


/**
 * Defines some Modbus Ascii message constants.
 *
 * @author    Andy Saunders (Original R2 code)
 * @creation  23 June 1999
 * @version   $Revision: 1$ $Date: 8/29/2005 10:21:12 AM$
 * @since     Niagara 3.0
 */
public interface NrioMessageConst
{

  public static final int MAX_REMOTE_SECURITY_MODULES = 15;
  public static final int MAX_MODULE_ADDRESS = MAX_REMOTE_SECURITY_MODULES + 1;
  public static final int HEADER_SIZE = 2;

  public static final int SOH = 0x02;
  
  // module type defs
  public static final int BASE_READER   = 0x06;
  public static final int REMOTE_READER = 0x07;
  public static final int REMOTE_IO     = 0x08;
  public static final int REMOTE_GP_IO  = 0x09;
  public static final int REMOTE_IO_V1  = 0x0A;
  public static final int REMOTE_IO_34_PRI  = 0x0B;
  public static final int REMOTE_IO_34_SEC  = 0x0C;

  // SPECIAL BROADCAST ADDRESS
  public static final int BROADCAST_ADDR = 0xbc;    

  // maximum firmware upgrade down load data size.
  public static final int MAX_MEMORY_DOWN_LOAD_SIZE = 64;
  public static final int DOWN_LOAD_MESSAGE_SIZE = 128;
  
  /* MESSAGE TYPES */
  public static final int MSG_QUERY_UNCONFIG      = 1;
  public static final int MSG_SET_LOGICAL_ADDRESS = 2;    
  public static final int MSG_PING                = 3;
  public static final int MSG_RESET_CR            = 4;
  public static final int MSG_RD_BUILD_INFO       = 5;
  public static final int MSG_WR_CR_CONFIG        = 6;
  public static final int MSG_RD_CR_CONFIG        = 7;
  public static final int MSG_IO_STATUS           = 8;
  public static final int MSG_WR_DO_DATA          = 9;
  public static final int MSG_WR_CODE_DNLD_START  = 10;
  public static final int MSG_WR_CODE_DNLD_STOP   = 11;
  public static final int MSG_WR_CODE_DNLD_DATA   = 12;
  public static final int MSG_RD_INFO_MEMORY      = 14;
  public static final int MSG_CLEAR_INFO_MEMORY   = 15;
  public static final int MSG_RD_SCALE_OFFSET     = 0x12;
  // write io defaults messages
  public static final int MSG_WR_IO_DEFAULT_START = 0x14;
  public static final int MSG_WR_IO_DEFAULT_MAP   = 0x15;
  public static final int MSG_RD_IO_DEFAULT_MAP   = 0x16;

  //message status types
  public static final int STATUS_OFFSET = 3;
  public static final int MESSAGE_STATUS_OK    = 0x00;
  public static final int MESSAGE_STATUS_ERR0R = 0x01;
  public static final int DOWNLOAD_IN_PROGRESS = 0x0FF;

  // general use constants
  public static final boolean IS_REVERSE = true;
  public static final boolean IS_NORMAL = false;
  public static final boolean ADD_ALARM = true;
  public static final boolean ALARM_ON_INACTIVE = false;
  public static final boolean ALARM_ON_ACTIVE = true;
  public static final boolean IS_STRIKE = true;
  public static final boolean IS_ENABLED = true;
  public static final boolean IS_SDI = true;

  public static byte[]   NULL_BA = new byte[0];
  public static byte[] pad = { (byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0,(byte)0, };

}