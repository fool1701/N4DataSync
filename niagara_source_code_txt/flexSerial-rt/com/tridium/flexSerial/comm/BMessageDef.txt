/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.flexSerial.comm;

import java.io.*;

import javax.baja.io.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.*;
import javax.baja.status.*;
import javax.baja.sys.*;
import javax.baja.util.*;

import com.tridium.flexSerial.*;

/**
 * BMessageDef defines the message framing bytes.
 *   Defines both start bytes and end bytes.
 *
 * @author    Andy Saunders
 * @creation  22 April 2004
 * @version   $Revision: 35$ $Date: 3/31/2004 9:03:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Defines bytes all messages must start with.
 Maximum of 10 bytes in length.
 Control characters may be entered by using the '\' character.
 '\\' = '\',
 '\r' =  x0d,
 '\n' =  x0a,
 '\t' =  x09,
 '\f' =  x0c,
 '\a' =  x07,
 '\e' =  x1b, or
 '\xhh' for any hh hex value.
 */
@NiagaraProperty(
  name = "frameStart",
  type = "String",
  defaultValue = ""
)
/*
 Defines bytes all messages must end with.
 Control characters may be entered by using the '\' character.
 '\\' = '\',
 '\r' =  x0d,
 '\n' =  x0a,
 '\t' =  x09,
 '\f' =  x0c,
 '\a' =  x07,
 '\e' =  x1b, or
 '\xhh' for any hh hex value.
 */
@NiagaraProperty(
  name = "frameEnd",
  type = "String",
  defaultValue = ""
)
/*
 Displays the decoded frameStart bytes.
 */
@NiagaraProperty(
  name = "startBytes",
  type = "String",
  defaultValue = "",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
/*
 Displays the decoded frameEnd bytes.
 */
@NiagaraProperty(
  name = "endBytes",
  type = "String",
  defaultValue = "",
  flags = Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY
)
public class BMessageDef
  extends BComponent
{

//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.flexSerial.comm.BMessageDef(249937009)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "frameStart"

  /**
   * Slot for the {@code frameStart} property.
   * Defines bytes all messages must start with.
   * Maximum of 10 bytes in length.
   * Control characters may be entered by using the '\' character.
   * '\\' = '\',
   * '\r' =  x0d,
   * '\n' =  x0a,
   * '\t' =  x09,
   * '\f' =  x0c,
   * '\a' =  x07,
   * '\e' =  x1b, or
   * '\xhh' for any hh hex value.
   * @see #getFrameStart
   * @see #setFrameStart
   */
  public static final Property frameStart = newProperty(0, "", null);

  /**
   * Get the {@code frameStart} property.
   * Defines bytes all messages must start with.
   * Maximum of 10 bytes in length.
   * Control characters may be entered by using the '\' character.
   * '\\' = '\',
   * '\r' =  x0d,
   * '\n' =  x0a,
   * '\t' =  x09,
   * '\f' =  x0c,
   * '\a' =  x07,
   * '\e' =  x1b, or
   * '\xhh' for any hh hex value.
   * @see #frameStart
   */
  public String getFrameStart() { return getString(frameStart); }

  /**
   * Set the {@code frameStart} property.
   * Defines bytes all messages must start with.
   * Maximum of 10 bytes in length.
   * Control characters may be entered by using the '\' character.
   * '\\' = '\',
   * '\r' =  x0d,
   * '\n' =  x0a,
   * '\t' =  x09,
   * '\f' =  x0c,
   * '\a' =  x07,
   * '\e' =  x1b, or
   * '\xhh' for any hh hex value.
   * @see #frameStart
   */
  public void setFrameStart(String v) { setString(frameStart, v, null); }

  //endregion Property "frameStart"

  //region Property "frameEnd"

  /**
   * Slot for the {@code frameEnd} property.
   * Defines bytes all messages must end with.
   * Control characters may be entered by using the '\' character.
   * '\\' = '\',
   * '\r' =  x0d,
   * '\n' =  x0a,
   * '\t' =  x09,
   * '\f' =  x0c,
   * '\a' =  x07,
   * '\e' =  x1b, or
   * '\xhh' for any hh hex value.
   * @see #getFrameEnd
   * @see #setFrameEnd
   */
  public static final Property frameEnd = newProperty(0, "", null);

  /**
   * Get the {@code frameEnd} property.
   * Defines bytes all messages must end with.
   * Control characters may be entered by using the '\' character.
   * '\\' = '\',
   * '\r' =  x0d,
   * '\n' =  x0a,
   * '\t' =  x09,
   * '\f' =  x0c,
   * '\a' =  x07,
   * '\e' =  x1b, or
   * '\xhh' for any hh hex value.
   * @see #frameEnd
   */
  public String getFrameEnd() { return getString(frameEnd); }

  /**
   * Set the {@code frameEnd} property.
   * Defines bytes all messages must end with.
   * Control characters may be entered by using the '\' character.
   * '\\' = '\',
   * '\r' =  x0d,
   * '\n' =  x0a,
   * '\t' =  x09,
   * '\f' =  x0c,
   * '\a' =  x07,
   * '\e' =  x1b, or
   * '\xhh' for any hh hex value.
   * @see #frameEnd
   */
  public void setFrameEnd(String v) { setString(frameEnd, v, null); }

  //endregion Property "frameEnd"

  //region Property "startBytes"

  /**
   * Slot for the {@code startBytes} property.
   * Displays the decoded frameStart bytes.
   * @see #getStartBytes
   * @see #setStartBytes
   */
  public static final Property startBytes = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, "", null);

  /**
   * Get the {@code startBytes} property.
   * Displays the decoded frameStart bytes.
   * @see #startBytes
   */
  public String getStartBytes() { return getString(startBytes); }

  /**
   * Set the {@code startBytes} property.
   * Displays the decoded frameStart bytes.
   * @see #startBytes
   */
  public void setStartBytes(String v) { setString(startBytes, v, null); }

  //endregion Property "startBytes"

  //region Property "endBytes"

  /**
   * Slot for the {@code endBytes} property.
   * Displays the decoded frameEnd bytes.
   * @see #getEndBytes
   * @see #setEndBytes
   */
  public static final Property endBytes = newProperty(Flags.OPERATOR | Flags.READONLY | Flags.TRANSIENT | Flags.SUMMARY, "", null);

  /**
   * Get the {@code endBytes} property.
   * Displays the decoded frameEnd bytes.
   * @see #endBytes
   */
  public String getEndBytes() { return getString(endBytes); }

  /**
   * Set the {@code endBytes} property.
   * Displays the decoded frameEnd bytes.
   * @see #endBytes
   */
  public void setEndBytes(String v) { setString(endBytes, v, null); }

  //endregion Property "endBytes"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMessageDef.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////

  public void started()
  {
    init();
  }

  public void changed(Property property, Context context)
  {
    if(isRunning())
    {
      if(property.equals(frameStart) || property.equals(frameEnd) )
      {
        init();
      }
    }
  }

  void init()
  {
    startByteArray = getFrameBytes(getFrameStart());
    endByteArray   = getFrameBytes(getFrameEnd());
    setStartBytes(ByteArrayUtil.toHexString(startByteArray));
    setEndBytes(ByteArrayUtil.toHexString(endByteArray));
    if(getParent() instanceof BFlexSerialNetwork)
      ((BFlexSerialNetwork)getParent()).messageDefChanged();
  }

  public byte[] getFrameStartBytes() { return startByteArray; }
  public byte[] getFrameEndBytes()   { return endByteArray  ; }

  public static byte[] getFrameBytes(String string)
  {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int state = 0;
    int outValue = 0;
    for(int i = 0; i < string.length(); i++)
    {
      int inValue = string.charAt(i) & 0x0ff;
      switch(state)
      {
      case 0:
        if(inValue == '\\') {state = 1; break;}
        out.write( (byte)inValue); break;

      case 1:
        switch(inValue)
        {
        case '\\': out.write('\\'); state = 0; break;
        case 'r':  out.write(0x0d); state = 0;  break;
        case 'n':  out.write(0x0a); state = 0;  break;
        case 't':  out.write(0x09); state = 0;  break;
        case 'f':  out.write(0x0c); state = 0;  break;
        case 'a':  out.write(0x07); state = 0;  break;
        case 'e':  out.write(0x1b); state = 0;  break;
        case 'x': state = 2; break;
        }
        break;

      case 2:
        outValue = Character.digit((char)inValue, 16) & 0x0f;
        state = 3;
        break;
      case 3:
        outValue = outValue << 4 | (Character.digit((char)inValue, 16) & 0x0f);
        out.write( outValue );
        state = 0;
        break;
      }
    }
    return out.toByteArray();
  }

////////////////////////////////////////////////////////////////
// attributes
////////////////////////////////////////////////////////////////

  byte[] startByteArray = new byte[0];
  byte[] endByteArray = new byte[0];

}
