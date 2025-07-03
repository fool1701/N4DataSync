/*
 * Copyright 2004, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.control.trigger;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;

/**
 * A BManualTriggerMode fires no scheduled triggers.  The trigger action
 * must be invoked manually or via a link.
 *
 * @author    John Sublett
 * @creation  07 Jan 2004
 * @version   $Revision: 3$ $Date: 7/30/08 10:53:55 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BManualTriggerMode
  extends BTriggerMode
{
  private BManualTriggerMode()
  {
  }
  
  public static BManualTriggerMode make()
  {
    return DEFAULT;
  }
  
  public String getDisplayName(Context cx)
  {
    return dispName.getText(cx);
  }

  public TriggerScheduler makeScheduler(BTimeTrigger trigger)
  {
    return new ManualTriggerScheduler(trigger);
  }
  
////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Hash is based on <code>System.identityHashCode()</code>.
   * Added override for this method in Niagara 3.4.
   */
  public int hashCode()
  {
    // System's identityHashCode is fine since this is a singleton
    return System.identityHashCode(this);
  }
  
  public boolean equals(Object o)
  {
    return o instanceof BManualTriggerMode;
  }

  public void encode(DataOutput out)
    throws IOException
  {
    // just to write something
    out.writeBoolean(true);
  }
  
  public BObject decode(DataInput in)
    throws IOException
  {
    in.readBoolean();
    return DEFAULT;
  }

  public String encodeToString()
  {
    return "manual";
  }

  public BObject decodeFromString(String s)
    throws IOException
  {
    return DEFAULT;
  }

  public String toString(Context cx)
  {
    return getDisplayName(cx);
  }

////////////////////////////////////////////////////////////////
// ManualTriggerScheduler
////////////////////////////////////////////////////////////////

  private class ManualTriggerScheduler
    extends TriggerScheduler
  {
    public ManualTriggerScheduler(BTimeTrigger trigger)
    {
      super(trigger);
    }
    
    public void start() {}
    public void stop() {}
    public boolean isTriggerTime(BAbsTime time) { return false; }

    public BAbsTime getNextTriggerTime(BAbsTime after, BAbsTime previous)
    {
      return BAbsTime.END_OF_TIME;
    }
    
    BAbsTime getScheduledTriggerTime() { return BAbsTime.END_OF_TIME; }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  public static final BManualTriggerMode DEFAULT = new BManualTriggerMode();
  
  public static final Type TYPE = Sys.loadType(BManualTriggerMode.class);
  public Type getType() { return TYPE; }
  
  private static LexiconText dispName = LexiconText.make("control", "trigger.manual");
}
