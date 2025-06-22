/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet;

import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;

import javax.baja.util.Lexicon;

/**
 * BacnetConst contains all constants needed for use in the
 * javax.baja.bacnet module.
 * <p>
 *
 * @author Craig Gemmill
 * @version $Revision: 1$ $Date: 12/17/01 9:07:56 AM$
 * @creation 15 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */
public interface BacnetConst
{
  // BACnet Lexicon
  Lexicon bacnetLexicon = Lexicon.make("bacnet");

/////////////////////////////////////////////////////////////////
// Niagara AX BACnet constants
/////////////////////////////////////////////////////////////////

  int VENDOR_ID_TRIDIUM = 36;

/////////////////////////////////////////////////////////////////
//  BACnet Constants
/////////////////////////////////////////////////////////////////

  /**
   * @deprecated As of 3.5.  Use <code>NOT_USED</code> instead.
   */
  @Deprecated
  int PROPERTY_ID_NOT_USED = -1;

  /**
   * @deprecated As of 3.5.  Use <code>NOT_USED</code> instead.
   */
  @Deprecated
  int PROPERTY_ARRAY_INDEX_NOT_USED = -1;

  /**
   * @deprecated As of 3.5.  Use <code>NOT_USED</code> instead.
   */
  @Deprecated
  int NO_PRIORITY = -1;

  /**
   * @deprecated As of 3.5.  Use <code>NOT_USED</code> instead.
   */
  @Deprecated
  int RANGE_NOT_USED = -1;

  /**
   * @deprecated As of 3.5.  Use <code>NOT_USED</code> instead.
   */
  @Deprecated
  int SEQUENCE_NUMBER_NOT_USED = -1;

  /**
   * @deprecated As of 3.5.  Use <code>NOT_USED</code> instead.
   */
  @Deprecated
  int REFERENCE_INDEX_NOT_USED = -1;

  /**
   * Generic flag to indicate an optional parameter is not being used.
   */
  int NOT_USED = -1;

  /**
   * @deprecated as of 3.5
   */
  @Deprecated
  int BACNET_SF_MASK = 0xF;

  /**
   * @deprecated as of 3.5
   */
  @Deprecated
  int NIAGARA_SF_MASK = 0x70;

  /**
   * BStatus bits used in BACnetStatusFlags
   */
  int BACNET_SBITS_MASK = 0x2B; // overridden, alarm, fault, disabled

  /**
   * Maximum Unsigned32 value
   *
   * @deprecated as of 3.6; use BBacnetUnsigned.MAX_UNSIGNED_VALUE instead.
   */
  @Deprecated
  long MAX_BACNET_UNSIGNED = 2L * Integer.MAX_VALUE + 1;

  // Prefixes for naming new enumerations.
  String PROPRIETARY_PREFIX = bacnetLexicon.getText("enum.proprietary");
  String ASHRAE_PREFIX = bacnetLexicon.getText("enum.ashrae");
  int PROPRIETARY_PREFIX_LENGTH = PROPRIETARY_PREFIX.length();//11;
  int ASHRAE_PREFIX_LENGTH = ASHRAE_PREFIX.length();//6;


/////////////////////////////////////////////////////////////////
//  Bacnet Month, Week and Day Constants
/////////////////////////////////////////////////////////////////

  /**
   * Months
   */
  int BAC_JANUARY = 1;
  int BAC_FEBRUARY = 2;
  int BAC_MARCH = 3;
  int BAC_APRIL = 4;
  int BAC_MAY = 5;
  int BAC_JUNE = 6;
  int BAC_JULY = 7;
  int BAC_AUGUST = 8;
  int BAC_SEPTEMBER = 9;
  int BAC_OCTOBER = 10;
  int BAC_NOVEMBER = 11;
  int BAC_DECEMBER = 12;

  /**
   * Weeks
   */
  int FIRST_WEEK = 1;
  int SECOND_WEEK = 2;
  int THIRD_WEEK = 3;
  int FOURTH_WEEK = 4;
  int FIFTH_WEEK = 5;
  int LAST_SEVEN_DAYS = 6;

  /**
   * Days
   */
  int BAC_MONDAY = 1;
  int BAC_TUESDAY = 2;
  int BAC_WEDNESDAY = 3;
  int BAC_THURSDAY = 4;
  int BAC_FRIDAY = 5;
  int BAC_SATURDAY = 6;
  int BAC_SUNDAY = 7;

  int NIAGARA_SUNDAY = 0;


/////////////////////////////////////////////////////////////////
//  Asn Data Types
/////////////////////////////////////////////////////////////////

  /** Application Tag Constants */
  /**
   * see Bacnet Spec, clause 20.2.1.4
   */
  int ASN_NULL = 0;
  int ASN_BOOLEAN = 1;
  int ASN_UNSIGNED = 2;
  int ASN_INTEGER = 3;
  int ASN_REAL = 4;
  int ASN_DOUBLE = 5;
  int ASN_OCTET_STRING = 6;
  int ASN_CHARACTER_STRING = 7;
  int ASN_BIT_STRING = 8;
  int ASN_ENUMERATED = 9;
  int ASN_DATE = 10;
  int ASN_TIME = 11;
  int ASN_OBJECT_IDENTIFIER = 12;
  int ASHRAE_RESERVED_13 = 13;
  int ASHRAE_RESERVED_14 = 14;
  int ASHRAE_RESERVED_15 = 15;

  /**
   * Special data types
   */
  int ASN_CONSTRUCTED_DATA = -1;
  int ASN_BACNET_ARRAY = -2;
  int ASN_BACNET_LIST = -3;
  int ASN_ANY = -4;
  int ASN_CHOICE = -5;
  int ASN_UNKNOWN_PROPRIETARY = -6;

  String[] ASN_PRIMITIVE_TAGS = new String[]
    {
      "NULL",
      "BOOLEAN",
      "Unsigned",
      "INTEGER",
      "REAL",
      "Double",
      "OCTET STRING",
      "CharacterString",
      "BIT STRING",
      "ENUMERATED",
      "Date",
      "Time",
      "BacnetObjectIdentifier",
    };

  /////////////////////////////////////////////////////////////////
  //  Objects Dynamic Creation Deletion Constants
  /////////////////////////////////////////////////////////////////
  String DYNAMIC_OBJECTS_FOLDER_NAME = "dynamicObjects";

  String DYNAMIC_POINTS_CREATED_FOR_EVENT_ENROLLMENT = "dynamicPoints";

  ////////////////////////////////////////////////////////////////
  ///////// Magic numbers ////////////////////////////////////////
  ////////////////////////////////////////////////////////////////
  int ZERO = 0;

  int ONE = 1;

  int TWO = 2;

  int THREE = 3;

  int FOUR = 4;

  int FIVE = 5;

  int THOUSAND = 1000;

  int COV_LIFETIME_LIMIT = 28800;
/////////////////////////////////////////////////////////////////
//  Context instances
/////////////////////////////////////////////////////////////////
  //No-op hashCode is better than an absent hashCode.
  //If there are performance problems with maps
  //add a better hash method.
  /**
   * Context used when database values set by
   * reading from the physical device - used to
   * short circuit writing the data back to the device.
   */
  Context noWrite = new BasicContext()
  {
    public boolean equals(Object o)
    {
      return this == o;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:noWrite";
    }
  };

  /**
   * Name context: If used, the object should make sure its toString returns
   * a String suitable for use as a Baja name.
   * <p>
   * Note to the caller: This is only a SUGGESTION - it is not guaranteed by
   * compilation rules that the implementing class will have followed this.
   */
  Context nameContext = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:nameContext";
    }
  };

  /**
   * Facets context: If used, the object should make sure its toString returns
   * a String suitable for inclusion in a Facets list.
   * <p>
   * Note to the caller: This is only a SUGGESTION - it is not guaranteed by
   * compilation rules that the implementing class will have followed this.
   */
  Context facetsContext = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:facetsContext";
    }
  };

  /**
   * ObjectID context: Used to indicate an Object ID that has an editable
   * instance number, but a read-only object type.
   */
  Context objectIdContext = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:objectIdContext";
    }
  };

  /**
   * Fallback context: Used when setting an object property back to its
   * previous value after a property change to an invalid value.
   */
  Context fallback = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:fallbackContext";
    }
  };

  /**
   * Debug context: Used when setting a property or invoking an action
   * where debug information should be displayed about the operation.
   */
  Context deviceRegistryContext = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:deviceRegistryContext";
    }
  };

  /**
   * Debug context: Used when setting a property or invoking an action
   * where debug information should be displayed about the operation.
   */
  Context debugContext = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:debugContext";
    }
  };
}