/*
 * Copyright 2007, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.timezone;

import java.time.zone.ZoneRulesException;
import java.time.zone.ZoneRulesProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * TimeZoneDatabase is responsible for creating, returning and caching timezones.
 *
 * @author    Mike James
 * @creation  22 May 07
 * @since     Baja 3.3
 */

public class TimeZoneDatabase
{
  /////////////////////////////////////////////////////////////////
  // Static Attributes
  ////////////////////////////////////////////////////////////////

  private static String supportedZones[];

  private static TimeZoneDatabase INSTANCE;

  ////////////////////////////////////////////////////////////////
  // Static Initializer
  ////////////////////////////////////////////////////////////////

  static
  {
    //The supported zones in the Niagara platform are only those
    //which have a Olson Time Zone definition. This will be a subset
    //of the zones provided by the virtual machine.
    
    List<String> olsonSupportedZones = new ArrayList<>();
    String [] javaSupportedZones = TimeZone.getAvailableIDs();
    
    for (int i = 0; i < javaSupportedZones.length; i++)
    {
      try
      {
        //Determine if the rule is supported
        ZoneRulesProvider.getRules(javaSupportedZones[i], false);
      
        //If no exception is thrown, it is
        olsonSupportedZones.add(javaSupportedZones[i]);
      }
      catch (ZoneRulesException zre)
      {
        //Not supported by Olson, ignore this zone
      }
    }
    
    //Assign as supported zones
    supportedZones = olsonSupportedZones.toArray(new String[olsonSupportedZones.size()]);
  }

  ////////////////////////////////////////////////////////////////
  // Constructors
  ////////////////////////////////////////////////////////////////

  private TimeZoneDatabase()
  {
  }

  ////////////////////////////////////////////////////////////////
  // Singleton
  ////////////////////////////////////////////////////////////////

  public static synchronized final TimeZoneDatabase get()
  {
    if (INSTANCE == null)
    {
      INSTANCE = new TimeZoneDatabase();
    }
    return INSTANCE;
  }

  ////////////////////////////////////////////////////////////////
  // Inherited
  ////////////////////////////////////////////////////////////////

  @Override
  public final Object clone() throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }

  /**
   * Get a timezone from the default database.  If the timezone
   * does not exist or has not previously been defined using
   * BTimeZone.make() then throw a TimeZoneException.
   */
  public static BTimeZone getTimeZone(String id)
  {
    //force a load of the database
    get();

    return BTimeZone.getTimeZone(id);
  }

  /**
   *  Determine if the provided TimeZone String is supported by the Java TimeZone database
   */
  public static final boolean isZoneIdSupported(final String id)
  {
    if (id.equals("NULL")) return true;

    if (supportedZones != null)
    {
      for (short zoneIdx = 0; zoneIdx < supportedZones.length; zoneIdx++)
      {
        if (supportedZones[zoneIdx].equals(id))
        {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Get all of the TimeZone Strings found in the timezone.jar
   */
  public static final String[] getAllSupportedZoneIds()
  {
    return supportedZones;
  }

  /**
   * Get the list of current timezones available in this VM
   */
  public synchronized final BTimeZone[] getTimeZones()
  {
    //Load items directly from TimeZone
    List<BTimeZone> timeZones = new ArrayList<>();
    for (int i = 0; i < supportedZones.length; i++)
    {
      timeZones.add(BTimeZone.getTimeZone(supportedZones[i]));  
    }            

    return timeZones.toArray(new BTimeZone[timeZones.size()]);
  }
}
