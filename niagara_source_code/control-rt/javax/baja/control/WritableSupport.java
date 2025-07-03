/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.control;

import javax.baja.control.enums.BPriorityLevel;
import javax.baja.control.util.BOverride;
import javax.baja.naming.BOrd;
import javax.baja.status.BStatus;
import javax.baja.status.BStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BRelTime;
import javax.baja.sys.BSimple;
import javax.baja.sys.BStation;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Slot;

/**
 * WritableSupport is a package private helper class used to
 * manage the common code for the various xWritable points.
 *
 * @author    Brian Frank
 * @creation  21 Jun 04
 * @version   $Revision: 11$ $Date: 10/23/09 11:41:07 AM EDT$
 * @since     Baja 1.0
 */
abstract class WritableSupport
{               

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  WritableSupport(BControlPoint point)
  {                             
    this.point = point;
  }              

////////////////////////////////////////////////////////////////
// Abstract
////////////////////////////////////////////////////////////////

  abstract Property in1();
  abstract Property in2();
  abstract Property in3();
  abstract Property in4();
  abstract Property in5();
  abstract Property in6();
  abstract Property in7();
  abstract Property in8();
  abstract Property in9();
  abstract Property in10();
  abstract Property in11();
  abstract Property in12();
  abstract Property in13();
  abstract Property in14();
  abstract Property in15();
  abstract Property in16();
  
  abstract void setValue(BStatusValue from, BStatusValue to);
  abstract BStatusValue getFallback();
  abstract void setOverrideExpiration(BAbsTime time);
  abstract BAbsTime getOverrideExpiration();
  
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  void started()
  {
    // check for timed override and set clock ticket if override active.
    BAbsTime overrideExpiration = getOverrideExpiration();
    if(overrideExpiration.getMillis() == 0l)
      return;

    BAbsTime now = BAbsTime.now();
    if( overrideExpiration.isAfter(now) )
    {
      BRelTime duration = now.delta(overrideExpiration);
      if(overrideTimer != null) overrideTimer.cancel();
      overrideTimer = Clock.schedule(point, duration, point.getAction("auto"), null);
    }
    else
    {
      auto();
    }

  }
  
  BPriorityLevel getActiveLevel()
  {
    return BPriorityLevel.make( point.getStatus().geti(BStatus.ACTIVE_LEVEL, BPriorityLevel.FALLBACK) );
  }

  boolean isInput(Slot slot)
  {
    return slot.isFrozen() && slot.getName().startsWith("in");
  }       
  
  BStatusValue getLevel(int level)
  {
    switch(level)
    {
      case 1:  return (BStatusValue)point.get( in1() );
      case 2:  return (BStatusValue)point.get( in2() );
      case 3:  return (BStatusValue)point.get( in3() );
      case 4:  return (BStatusValue)point.get( in4() );
      case 5:  return (BStatusValue)point.get( in5() );
      case 6:  return (BStatusValue)point.get( in6() );
      case 7:  return (BStatusValue)point.get( in7() );
      case 8:  return (BStatusValue)point.get( in8() );
      case 9:  return (BStatusValue)point.get( in9() );
      case 10: return (BStatusValue)point.get( in10() );
      case 11: return (BStatusValue)point.get( in11() );
      case 12: return (BStatusValue)point.get( in12() );
      case 13: return (BStatusValue)point.get( in13() );
      case 14: return (BStatusValue)point.get( in14() );
      case 15: return (BStatusValue)point.get( in15() );
      case 16: return (BStatusValue)point.get( in16() );
    }
    throw new IllegalArgumentException();
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  void changed(Property prop)
  {    
    // keep activeLevel facets clear on inputs to prevent confusion
    if (isInput(prop))
    {
      BStatusValue sv = (BStatusValue)point.get(prop);
      BStatus s = sv.getStatus();
      if (s.get(BStatus.ACTIVE_LEVEL) != null)
        sv.setStatus(BStatus.make(s.getBits(), (BFacets)(BFacets.makeRemove(s.getFacets(), BStatus.ACTIVE_LEVEL).intern())));
    }
  }

////////////////////////////////////////////////////////////////
// Execute
////////////////////////////////////////////////////////////////

  void onExecute(BStatusValue out, Context cx)
  {                                     
    // walk thru each level to find the active level                        
    BStatusValue active = null;
    int activeLevel = 17;
    for(int level=1; level<=16; ++level)
    {
      BStatusValue in = getLevel(level);
      if (in.getStatus().isValid())
      { 
        active = in; 
        activeLevel = level; 
        break; 
      }
    }

    // compute output variable   
    if (active == null)
    {                 
      BStatusValue fallback = getFallback();
      out.copyFrom(fallback);
      out.setStatus(BStatus.ACTIVE_LEVEL, BDynamicEnum.make(BPriorityLevel.fallback));
    }
    else
    {                                                                               
      setValue(active, out);                    
      int status = 0;                                    
      if (activeLevel == 1 || activeLevel == 8) status |= BStatus.OVERRIDDEN;
      out.setStatus(status);
      out.setStatus(BStatus.ACTIVE_LEVEL, BDynamicEnum.make(BPriorityLevel.make(activeLevel)));
    }           
  }                  

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  void emergencyOverride(BSimple v)
  {                    
    // notify proxy ext for force write
    notifyProxyExtForActionInvoked();
    
    // set level 1
    BStatusValue x = (BStatusValue)point.get(in1());
    x.setValueValue(v);
    x.setStatus(BStatus.ok);
  }

  void emergencyAuto()
  {
    // notify proxy ext for force write
    notifyProxyExtForActionInvoked();
    
    // clear level 1
    BStatusValue x = (BStatusValue)point.get(in1());
    x.setStatus(BStatus.nullStatus);
  }

  void override(BOverride override)
  {                  
    // notify proxy ext for force write
    notifyProxyExtForActionInvoked();
    
    // set level 8
    BStatusValue x = (BStatusValue)point.get(in8());
    BValue value = override.get("value");
    x.setValueValue(value);
    x.setStatus(BStatus.ok);
    
    // cancel existing override
    if (overrideTimer != null) overrideTimer.cancel();
    
    // setup override timer
    BRelTime duration = override.getDuration();
    
    // Make sure duration doesn't exceed maxOverrideDuration!
    BRelTime maxDuration = getMaxOverrideDuration();
    long maxDurationMillis = maxDuration.getMillis();
    if (maxDurationMillis > 0)
    {
      long desiredDurationMillis = duration.getMillis();
      if ((desiredDurationMillis <= 0) || (desiredDurationMillis > maxDurationMillis))
        duration = maxDuration;
    }
    
    if (duration.getMillis() > 0)
    {                                                    
      setOverrideExpiration(BAbsTime.make(Clock.millis() + duration.getMillis()));
      overrideTimer = Clock.schedule(point, duration, point.getAction("auto"), null);
    }                         
    else
    {
      setOverrideExpiration(BAbsTime.NULL);
    }
  }                         
  
  void auto()
  {
    // notify proxy ext for force write
    notifyProxyExtForActionInvoked();
    
    // clear level 8
    BStatusValue x = (BStatusValue)point.get(in8());
    x.setStatus(BStatus.nullStatus);
    
    // clear override timer
    if (overrideTimer != null)  overrideTimer.cancel();        
    overrideTimer = null;        
    setOverrideExpiration(BAbsTime.NULL);
  }                             

  void set(BSimple v)
  {              
    // notify proxy ext for force write
    notifyProxyExtForActionInvoked();
    
    // set fallback
    BStatusValue x = getFallback();
    x.setValueValue(v);
    x.setStatus(BStatus.ok);
  }

  void  notifyProxyExtForActionInvoked()
  {
    try
    {                     
      // notify the proxy ext so that it can
      // do a forceWrite to ensure the user commanded
      // value is written to the external device
      point.getProxyExt().writablePointActionInvoked();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  /**
   * Check the station's SysInfo facets for the existence of a "maxOverrideDuration" BRelTime facet, and if 
   * it exists, return it as the max override duration.  Otherwise returns a zero BRelTime, indicating permanent
   * overrides are allowed.
   *
   * @since Niagara 3.4.51
   */
  BRelTime getMaxOverrideDuration()
  {
    BRelTime maxDuration = BRelTime.DEFAULT;
    
    try
    {
      // First check to see if it is specified individually on the point itself
      if (!point.isRunning())
      {
        point.loadSlots();
        point.lease();
      }
      BFacets facets = point.getFacets();
      BObject obj = facets.get("maxOverrideDuration");
      if (obj instanceof BRelTime)
        maxDuration = (BRelTime)obj;
      else
      {        
        // Otherwise check to see if it has been specified globally
        BStation station = (BStation)BOrd.make("station:|slot:/").get(point);
        if (!station.isRunning())
        {
          station.loadSlots();
          station.lease();
        }
        facets = (BFacets)station.get("sysInfo");
        maxDuration = (BRelTime)facets.get("maxOverrideDuration", maxDuration);
      }
    }
    catch (Exception e) { }
    
    return maxDuration;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BControlPoint point;    
  Clock.Ticket overrideTimer;
  
}
