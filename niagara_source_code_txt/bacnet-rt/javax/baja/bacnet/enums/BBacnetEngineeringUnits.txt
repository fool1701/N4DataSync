/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.enums;

import java.util.HashMap;
import java.util.logging.Logger;

import javax.baja.bacnet.BacnetConst;
import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.*;
import javax.baja.units.BUnit;
import javax.baja.units.UnitDatabase;
import javax.baja.units.UnitException;

/**
 * BBacnetEngineeringUnits represents the Bacnet
 * Engineering Units enumeration.
 * <p>
 * BBacnetEngineeringUnits is an "extensible" enumeration.
 * Values 0-255 are reserved for use by ASHRAE.
 * Values from 256-65535
 * can be used for proprietary extensions.
 * <p>
 * Note that for proprietary extensions, a given ordinal is not
 * globally mapped to the same enumeration.  Type X from vendor
 * A will be different than type X from vendor B.  Extensions are
 * also not guaranteed unique within a vendor's own products, so
 * type Y in device A from vendor A will in general be different
 * than type Y in device B from vendor A.
 * <p>
 * Bacnet Engineering Units differ from Niagara Engineering Units.
 * The <code>niagaraName</code> field provides the String name to
 * be passed to the Niagara unit database to get the correct <code>BUnit</code>.
 *
 * @author Craig Gemmill
 * @version $Revision: 5$ $Date: 12/19/01 4:35:56 PM$
 * @creation 07 Aug 01
 * @since Niagara 3 Bacnet 1.0
 */

@NiagaraType
@NoSlotomatic
@NiagaraEnum(
  range = {
    @Range("squareMeters"),
    @Range("squareFeet"),
    @Range("milliamperes"),
    @Range("amperes"),
    @Range("ohms"),
    @Range("volts"),
    @Range("kilovolts"),
    @Range("megavolts"),
    @Range("voltAmperes"),
    @Range("kilovoltAmperes"),
    @Range("megavoltAmperes"),
    @Range("voltAmperesReactive"),
    @Range("kilovoltAmperesReactive"),
    @Range("megavoltAmperesReactive"),
    @Range("degreesPhase"),
    @Range("powerFactor"),
    @Range("joules"),
    @Range("kilojoules"),
    @Range("wattHours"),
    @Range("kilowattHours"),
    @Range("btus"),
    @Range("therms"),
    @Range("tonHours"),
    @Range("joulesPerKilogramDryAir"),
    @Range("btusPerPoundDryAir"),
    @Range("cyclesPerHour"),
    @Range("cyclesPerMinute"),
    @Range("hertz"),
    @Range("gramsOfWaterPerKilogramDryAir"),
    @Range("percentRelativeHumidity"),
    @Range("millimeters"),
    @Range("meters"),
    @Range("inches"),
    @Range("feet"),
    @Range("wattsPerSquareFoot"),
    @Range("wattsPerSquareMeter"),
    @Range("lumens"),
    @Range("luxes"),
    @Range("footCandles"),
    @Range("kilograms"),
    @Range("poundsMass"),
    @Range("tons"),
    @Range("kilogramsPerSecond"),
    @Range("kilogramsPerMinute"),
    @Range("kilogramsPerHour"),
    @Range("poundsMassPerMinute"),
    @Range("poundsMassPerHour"),
    @Range("watts"),
    @Range("kilowatts"),
    @Range("megawatts"),
    @Range("btusPerHour"),
    @Range("horsepower"),
    @Range("tonsRefrigeration"),
    @Range("pascals"),
    @Range("kilopascals"),
    @Range("bars"),
    @Range("poundsForcePerSquareInch"),
    @Range("centimetersOfWater"),
    @Range("inchesOfWater"),
    @Range("millimetersOfMercury"),
    @Range("centimetersOfMercury"),
    @Range("inchesOfMercury"),
    @Range("degreesCelsius"),
    @Range("degreesKelvin"),
    @Range("degreesFahrenheit"),
    @Range("degreeDaysCelsius"),
    @Range("degreeDaysFahrenheit"),
    @Range("years"),
    @Range("months"),
    @Range("weeks"),
    @Range("days"),
    @Range("hours"),
    @Range("minutes"),
    @Range("seconds"),
    @Range("metersPerSecond"),
    @Range("kilometersPerHour"),
    @Range("feetPerSecond"),
    @Range("feetPerMinute"),
    @Range("milesPerHour"),
    @Range("cubicFeet"),
    @Range("cubicMeters"),
    @Range("imperialGallons"),
    @Range("liters"),
    @Range("usGallons"),
    @Range("cubicFeetPerMinute"),
    @Range("cubicMetersPerSecond"),
    @Range("imperialGallonsPerMinute"),
    @Range("litersPerSecond"),
    @Range("litersPerMinute"),
    @Range("usGallonsPerMinute"),
    @Range("degreesAngular"),
    @Range("degreesCelsiusPerHour"),
    @Range("degreesCelsiusPerMinute"),
    @Range("degreesFahrenheitPerHour"),
    @Range("degreesFahrenheitPerMinute"),
    @Range("noUnits"),
    @Range("partsPerMillion"),
    @Range("partsPerBillion"),
    @Range("percent"),
    @Range("percentPerSecond"),
    @Range("perMinute"),
    @Range("perSecond"),
    @Range("psiPerDegreeFahrenheit"),
    @Range("radians"),
    @Range("revolutionsPerMinute"),
    @Range("currency1"),
    @Range("currency2"),
    @Range("currency3"),
    @Range("currency4"),
    @Range("currency5"),
    @Range("currency6"),
    @Range("currency7"),
    @Range("currency8"),
    @Range("currency9"),
    @Range("currency10"),
    @Range("squareInches"),
    @Range("squareCentimeters"),
    @Range("btusPerPound"),
    @Range("centimeters"),
    @Range("poundsMassPerSecond"),
    @Range("deltaDegreesFahrenheit"),
    @Range("deltaDegreesKelvin"),
    @Range("kilohms"),
    @Range("megohms"),
    @Range("millivolts"),
    @Range("kilojoulesPerKilogram"),
    @Range("megajoules"),
    @Range("joulesPerDegreeKelvin"),
    @Range("joulesPerKilogramDegreeKelvin"),
    @Range("kilohertz"),
    @Range("megahertz"),
    @Range("perHour"),
    @Range("milliwatts"),
    @Range("hectopascals"),
    @Range("millibars"),
    @Range("cubicMetersPerHour"),
    @Range("litersPerHour"),
    @Range("kilowattHoursPerSquareMeter"),
    @Range("kilowattHoursPerSquareFoot"),
    @Range("megajoulesPerSquareMeter"),
    @Range("megajoulesPerSquareFoot"),
    @Range("wattsPerSquareMeterDegreeKelvin"),
    @Range("cubicFeetPerSecond"),
    @Range("percentObscurationPerFoot"),
    @Range("percentObscurationPerMeter"),
    @Range("milliohms"),
    @Range("megawattHours"),
    @Range("kiloBtus"),
    @Range("megaBtus"),
    @Range("kilojoulesPerKilogramDryAir"),
    @Range("megajoulesPerKilogramDryAir"),
    @Range("kilojoulesPerDegreeKelvin"),
    @Range("megajoulesPerDegreeKelvin"),
    @Range("newton"),
    @Range("gramsPerSecond"),
    @Range("gramsPerMinute"),
    @Range("tonsPerHour"),
    @Range("kiloBtusPerHour"),
    @Range("hundredthsSeconds"),
    @Range("milliseconds"),
    @Range("newtonMeters"),
    @Range("millimetersPerSecond"),
    @Range("millimetersPerMinute"),
    @Range("metersPerMinute"),
    @Range("metersPerHour"),
    @Range("cubicMetersPerMinute"),
    @Range("metersPerSecondPerSecond"),
    @Range("amperesPerMeter"),
    @Range("amperesPerSquareMeter"),
    @Range("ampereSquareMeters"),
    @Range("farads"),
    @Range("henrys"),
    @Range("ohmMeters"),
    @Range("siemens"),
    @Range("siemensPerMeter"),
    @Range("teslas"),
    @Range("voltsPerDegreeKelvin"),
    @Range("voltsPerMeter"),
    @Range("webers"),
    @Range("candelas"),
    @Range("candelasPerSquareMeter"),
    @Range("degreesKelvinPerHour"),
    @Range("degreesKelvinPerMinute"),
    @Range("jouleSeconds"),
    @Range("radiansPerSecond"),
    @Range("squareMetersPerNewton"),
    @Range("kilogramsPerCubicMeter"),
    @Range("newtonSeconds"),
    @Range("newtonsPerMeter"),
    @Range("wattsPerMeterPerDegreeKelvin"),
    @Range("microsiemens"),
    @Range("cubicFeetPerHour"),
    @Range("usGallonsPerHour"),
    @Range("kilometers"),
    @Range("micrometers"),
    @Range("grams"),
    @Range("milligrams"),
    @Range("milliliters"),
    @Range("millilitersPerSecond"),
    @Range("decibels"),
    @Range("decibelsMillivolt"),
    @Range("decibelsVolt"),
    @Range("millisiemens"),
    @Range("wattHourReactive"),
    @Range("kilowattHoursReactive"),
    @Range("megawattHoursReactive"),
    @Range("millilitersOfWater"),
    @Range("perMile"),
    @Range("gramsPerGram"),
    @Range("kilogramsPerKilogram"),
    @Range("gramsPerKilogram"),
    @Range("milligramsPerGram"),
    @Range("milligramsPerKilogram"),
    @Range("gramsPerMilliliter"),
    @Range("gramsPerLiter"),
    @Range("milligramsPerLiter"),
    @Range("microgramsPerLiter"),
    @Range("gramsPerCubicMeter"),
    @Range("milligramsPerCubicMeter"),
    @Range("microgramsPerCubicMeter"),
    @Range("nanogramsPerCubicMeter"),
    @Range("gramsPerCubicCentimeter"),
    @Range("becquerels"),
    @Range("kilobecquerels"),
    @Range("megabecquerels"),
    @Range("gray"),
    @Range("milligray"),
    @Range("microgray"),
    @Range("sieverts"),
    @Range("millisieverts"),
    @Range("microsieverts"),
    @Range("microsievertsPerHour"),
    @Range("decibelsA"),
    @Range("nephelometricTurbidityUnit"),
    @Range("ph"),
    @Range("gramsPerSquareMeter"),
    @Range("minutesPerDegreeKelvin"),
    @Range("ohmMeterSquaredPerMeter"),
    @Range("ampereSeconds"),
    @Range("voltAmpereHour"),
    @Range("kilovoltAmpereHour"),
    @Range("megavoltAmpereHour"),
    @Range("voltAmpereReactiveHour"),
    @Range("kilovoltAmpereReactiveHour"),
    @Range("megavoltAmpereReactiveHour"),
    @Range("voltSquaredHours"),
    @Range("ampereSquaredHours"),
    @Range("joulesPerHour"),
    @Range("cubicFeetPerDay"),
    @Range("cubicMetersPerDay"),
    @Range("wattHourPerCubicMeter"),
    @Range("joulesPerCubicMeter"),
    @Range("molesPercent"),
    @Range("pascalSecond"),
    @Range("millionStandardCubicFeetPerMinute"),
    @Range(value = "standardCubicFeetPerDay", ordinal = 47808),
    @Range(value = "millionStandardCubicFeetPerDay", ordinal = 47809),
    @Range(value = "thousandCubicFeetPerDay", ordinal = 47810),
    @Range(value = "thousandStandardCubicFeetPerDay", ordinal = 47811),
    @Range(value = "poundsMassPerDay", ordinal = 47812),
    @Range(value = "millirems", ordinal = 47814),
    @Range(value = "milliremsPerHour", ordinal = 47815)
  }
)
public final class BBacnetEngineeringUnits
  extends BFrozenEnum
  implements BacnetConst
{
  // DEVELOPER NOTE:
  // Whenever this file needs to be changed, you need to do the following steps:
  // 1. Comment out @NoSlotomatic
  // 2. Re-slot the file
  // 3. Copy the new ordinal values to the definitions BELOW the auto-gen footer.
  // 4. Add entries for the Niagara names for the new values.
  //    * If possible, add entries into rel/lib/units.xml for new units.
  // 5. Add entries for the enum definitions for the new values.
  // 6. Delete all of the auto-generated code.
  // 7. Add the units with Niagara names into the map at the end.
  // 8. Don't forget to update the MAX_ASHRAE_ID value!
  // 9. Uncomment @NoSlotomatic
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.enums.BBacnetEngineeringUnits(3431789727)1.0$ @*/
/* Generated Thu Dec 16 12:23:21 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  /** Ordinal value for squareMeters. */
  public static final int SQUARE_METERS = 0;
  /** Ordinal value for squareFeet. */
  public static final int SQUARE_FEET = 1;
  /** Ordinal value for milliamperes. */
  public static final int MILLIAMPERES = 2;
  /** Ordinal value for amperes. */
  public static final int AMPERES = 3;
  /** Ordinal value for ohms. */
  public static final int OHMS = 4;
  /** Ordinal value for volts. */
  public static final int VOLTS = 5;
  /** Ordinal value for kilovolts. */
  public static final int KILOVOLTS = 6;
  /** Ordinal value for megavolts. */
  public static final int MEGAVOLTS = 7;
  /** Ordinal value for voltAmperes. */
  public static final int VOLT_AMPERES = 8;
  /** Ordinal value for kilovoltAmperes. */
  public static final int KILOVOLT_AMPERES = 9;
  /** Ordinal value for megavoltAmperes. */
  public static final int MEGAVOLT_AMPERES = 10;
  /** Ordinal value for voltAmperesReactive. */
  public static final int VOLT_AMPERES_REACTIVE = 11;
  /** Ordinal value for kilovoltAmperesReactive. */
  public static final int KILOVOLT_AMPERES_REACTIVE = 12;
  /** Ordinal value for megavoltAmperesReactive. */
  public static final int MEGAVOLT_AMPERES_REACTIVE = 13;
  /** Ordinal value for degreesPhase. */
  public static final int DEGREES_PHASE = 14;
  /** Ordinal value for powerFactor. */
  public static final int POWER_FACTOR = 15;
  /** Ordinal value for joules. */
  public static final int JOULES = 16;
  /** Ordinal value for kilojoules. */
  public static final int KILOJOULES = 17;
  /** Ordinal value for wattHours. */
  public static final int WATT_HOURS = 18;
  /** Ordinal value for kilowattHours. */
  public static final int KILOWATT_HOURS = 19;
  /** Ordinal value for btus. */
  public static final int BTUS = 20;
  /** Ordinal value for therms. */
  public static final int THERMS = 21;
  /** Ordinal value for tonHours. */
  public static final int TON_HOURS = 22;
  /** Ordinal value for joulesPerKilogramDryAir. */
  public static final int JOULES_PER_KILOGRAM_DRY_AIR = 23;
  /** Ordinal value for btusPerPoundDryAir. */
  public static final int BTUS_PER_POUND_DRY_AIR = 24;
  /** Ordinal value for cyclesPerHour. */
  public static final int CYCLES_PER_HOUR = 25;
  /** Ordinal value for cyclesPerMinute. */
  public static final int CYCLES_PER_MINUTE = 26;
  /** Ordinal value for hertz. */
  public static final int HERTZ = 27;
  /** Ordinal value for gramsOfWaterPerKilogramDryAir. */
  public static final int GRAMS_OF_WATER_PER_KILOGRAM_DRY_AIR = 28;
  /** Ordinal value for percentRelativeHumidity. */
  public static final int PERCENT_RELATIVE_HUMIDITY = 29;
  /** Ordinal value for millimeters. */
  public static final int MILLIMETERS = 30;
  /** Ordinal value for meters. */
  public static final int METERS = 31;
  /** Ordinal value for inches. */
  public static final int INCHES = 32;
  /** Ordinal value for feet. */
  public static final int FEET = 33;
  /** Ordinal value for wattsPerSquareFoot. */
  public static final int WATTS_PER_SQUARE_FOOT = 34;
  /** Ordinal value for wattsPerSquareMeter. */
  public static final int WATTS_PER_SQUARE_METER = 35;
  /** Ordinal value for lumens. */
  public static final int LUMENS = 36;
  /** Ordinal value for luxes. */
  public static final int LUXES = 37;
  /** Ordinal value for footCandles. */
  public static final int FOOT_CANDLES = 38;
  /** Ordinal value for kilograms. */
  public static final int KILOGRAMS = 39;
  /** Ordinal value for poundsMass. */
  public static final int POUNDS_MASS = 40;
  /** Ordinal value for tons. */
  public static final int TONS = 41;
  /** Ordinal value for kilogramsPerSecond. */
  public static final int KILOGRAMS_PER_SECOND = 42;
  /** Ordinal value for kilogramsPerMinute. */
  public static final int KILOGRAMS_PER_MINUTE = 43;
  /** Ordinal value for kilogramsPerHour. */
  public static final int KILOGRAMS_PER_HOUR = 44;
  /** Ordinal value for poundsMassPerMinute. */
  public static final int POUNDS_MASS_PER_MINUTE = 45;
  /** Ordinal value for poundsMassPerHour. */
  public static final int POUNDS_MASS_PER_HOUR = 46;
  /** Ordinal value for watts. */
  public static final int WATTS = 47;
  /** Ordinal value for kilowatts. */
  public static final int KILOWATTS = 48;
  /** Ordinal value for megawatts. */
  public static final int MEGAWATTS = 49;
  /** Ordinal value for btusPerHour. */
  public static final int BTUS_PER_HOUR = 50;
  /** Ordinal value for horsepower. */
  public static final int HORSEPOWER = 51;
  /** Ordinal value for tonsRefrigeration. */
  public static final int TONS_REFRIGERATION = 52;
  /** Ordinal value for pascals. */
  public static final int PASCALS = 53;
  /** Ordinal value for kilopascals. */
  public static final int KILOPASCALS = 54;
  /** Ordinal value for bars. */
  public static final int BARS = 55;
  /** Ordinal value for poundsForcePerSquareInch. */
  public static final int POUNDS_FORCE_PER_SQUARE_INCH = 56;
  /** Ordinal value for centimetersOfWater. */
  public static final int CENTIMETERS_OF_WATER = 57;
  /** Ordinal value for inchesOfWater. */
  public static final int INCHES_OF_WATER = 58;
  /** Ordinal value for millimetersOfMercury. */
  public static final int MILLIMETERS_OF_MERCURY = 59;
  /** Ordinal value for centimetersOfMercury. */
  public static final int CENTIMETERS_OF_MERCURY = 60;
  /** Ordinal value for inchesOfMercury. */
  public static final int INCHES_OF_MERCURY = 61;
  /** Ordinal value for degreesCelsius. */
  public static final int DEGREES_CELSIUS = 62;
  /** Ordinal value for degreesKelvin. */
  public static final int DEGREES_KELVIN = 63;
  /** Ordinal value for degreesFahrenheit. */
  public static final int DEGREES_FAHRENHEIT = 64;
  /** Ordinal value for degreeDaysCelsius. */
  public static final int DEGREE_DAYS_CELSIUS = 65;
  /** Ordinal value for degreeDaysFahrenheit. */
  public static final int DEGREE_DAYS_FAHRENHEIT = 66;
  /** Ordinal value for years. */
  public static final int YEARS = 67;
  /** Ordinal value for months. */
  public static final int MONTHS = 68;
  /** Ordinal value for weeks. */
  public static final int WEEKS = 69;
  /** Ordinal value for days. */
  public static final int DAYS = 70;
  /** Ordinal value for hours. */
  public static final int HOURS = 71;
  /** Ordinal value for minutes. */
  public static final int MINUTES = 72;
  /** Ordinal value for seconds. */
  public static final int SECONDS = 73;
  /** Ordinal value for metersPerSecond. */
  public static final int METERS_PER_SECOND = 74;
  /** Ordinal value for kilometersPerHour. */
  public static final int KILOMETERS_PER_HOUR = 75;
  /** Ordinal value for feetPerSecond. */
  public static final int FEET_PER_SECOND = 76;
  /** Ordinal value for feetPerMinute. */
  public static final int FEET_PER_MINUTE = 77;
  /** Ordinal value for milesPerHour. */
  public static final int MILES_PER_HOUR = 78;
  /** Ordinal value for cubicFeet. */
  public static final int CUBIC_FEET = 79;
  /** Ordinal value for cubicMeters. */
  public static final int CUBIC_METERS = 80;
  /** Ordinal value for imperialGallons. */
  public static final int IMPERIAL_GALLONS = 81;
  /** Ordinal value for liters. */
  public static final int LITERS = 82;
  /** Ordinal value for usGallons. */
  public static final int US_GALLONS = 83;
  /** Ordinal value for cubicFeetPerMinute. */
  public static final int CUBIC_FEET_PER_MINUTE = 84;
  /** Ordinal value for cubicMetersPerSecond. */
  public static final int CUBIC_METERS_PER_SECOND = 85;
  /** Ordinal value for imperialGallonsPerMinute. */
  public static final int IMPERIAL_GALLONS_PER_MINUTE = 86;
  /** Ordinal value for litersPerSecond. */
  public static final int LITERS_PER_SECOND = 87;
  /** Ordinal value for litersPerMinute. */
  public static final int LITERS_PER_MINUTE = 88;
  /** Ordinal value for usGallonsPerMinute. */
  public static final int US_GALLONS_PER_MINUTE = 89;
  /** Ordinal value for degreesAngular. */
  public static final int DEGREES_ANGULAR = 90;
  /** Ordinal value for degreesCelsiusPerHour. */
  public static final int DEGREES_CELSIUS_PER_HOUR = 91;
  /** Ordinal value for degreesCelsiusPerMinute. */
  public static final int DEGREES_CELSIUS_PER_MINUTE = 92;
  /** Ordinal value for degreesFahrenheitPerHour. */
  public static final int DEGREES_FAHRENHEIT_PER_HOUR = 93;
  /** Ordinal value for degreesFahrenheitPerMinute. */
  public static final int DEGREES_FAHRENHEIT_PER_MINUTE = 94;
  /** Ordinal value for noUnits. */
  public static final int NO_UNITS = 95;
  /** Ordinal value for partsPerMillion. */
  public static final int PARTS_PER_MILLION = 96;
  /** Ordinal value for partsPerBillion. */
  public static final int PARTS_PER_BILLION = 97;
  /** Ordinal value for percent. */
  public static final int PERCENT = 98;
  /** Ordinal value for percentPerSecond. */
  public static final int PERCENT_PER_SECOND = 99;
  /** Ordinal value for perMinute. */
  public static final int PER_MINUTE = 100;
  /** Ordinal value for perSecond. */
  public static final int PER_SECOND = 101;
  /** Ordinal value for psiPerDegreeFahrenheit. */
  public static final int PSI_PER_DEGREE_FAHRENHEIT = 102;
  /** Ordinal value for radians. */
  public static final int RADIANS = 103;
  /** Ordinal value for revolutionsPerMinute. */
  public static final int REVOLUTIONS_PER_MINUTE = 104;
  /** Ordinal value for currency1. */
  public static final int CURRENCY_1 = 105;
  /** Ordinal value for currency2. */
  public static final int CURRENCY_2 = 106;
  /** Ordinal value for currency3. */
  public static final int CURRENCY_3 = 107;
  /** Ordinal value for currency4. */
  public static final int CURRENCY_4 = 108;
  /** Ordinal value for currency5. */
  public static final int CURRENCY_5 = 109;
  /** Ordinal value for currency6. */
  public static final int CURRENCY_6 = 110;
  /** Ordinal value for currency7. */
  public static final int CURRENCY_7 = 111;
  /** Ordinal value for currency8. */
  public static final int CURRENCY_8 = 112;
  /** Ordinal value for currency9. */
  public static final int CURRENCY_9 = 113;
  /** Ordinal value for currency10. */
  public static final int CURRENCY_10 = 114;
  /** Ordinal value for squareInches. */
  public static final int SQUARE_INCHES = 115;
  /** Ordinal value for squareCentimeters. */
  public static final int SQUARE_CENTIMETERS = 116;
  /** Ordinal value for btusPerPound. */
  public static final int BTUS_PER_POUND = 117;
  /** Ordinal value for centimeters. */
  public static final int CENTIMETERS = 118;
  /** Ordinal value for poundsMassPerSecond. */
  public static final int POUNDS_MASS_PER_SECOND = 119;
  /** Ordinal value for deltaDegreesFahrenheit. */
  public static final int DELTA_DEGREES_FAHRENHEIT = 120;
  /** Ordinal value for deltaDegreesKelvin. */
  public static final int DELTA_DEGREES_KELVIN = 121;
  /** Ordinal value for kilohms. */
  public static final int KILOHMS = 122;
  /** Ordinal value for megohms. */
  public static final int MEGOHMS = 123;
  /** Ordinal value for millivolts. */
  public static final int MILLIVOLTS = 124;
  /** Ordinal value for kilojoulesPerKilogram. */
  public static final int KILOJOULES_PER_KILOGRAM = 125;
  /** Ordinal value for megajoules. */
  public static final int MEGAJOULES = 126;
  /** Ordinal value for joulesPerDegreeKelvin. */
  public static final int JOULES_PER_DEGREE_KELVIN = 127;
  /** Ordinal value for joulesPerKilogramDegreeKelvin. */
  public static final int JOULES_PER_KILOGRAM_DEGREE_KELVIN = 128;
  /** Ordinal value for kilohertz. */
  public static final int KILOHERTZ = 129;
  /** Ordinal value for megahertz. */
  public static final int MEGAHERTZ = 130;
  /** Ordinal value for perHour. */
  public static final int PER_HOUR = 131;
  /** Ordinal value for milliwatts. */
  public static final int MILLIWATTS = 132;
  /** Ordinal value for hectopascals. */
  public static final int HECTOPASCALS = 133;
  /** Ordinal value for millibars. */
  public static final int MILLIBARS = 134;
  /** Ordinal value for cubicMetersPerHour. */
  public static final int CUBIC_METERS_PER_HOUR = 135;
  /** Ordinal value for litersPerHour. */
  public static final int LITERS_PER_HOUR = 136;
  /** Ordinal value for kilowattHoursPerSquareMeter. */
  public static final int KILOWATT_HOURS_PER_SQUARE_METER = 137;
  /** Ordinal value for kilowattHoursPerSquareFoot. */
  public static final int KILOWATT_HOURS_PER_SQUARE_FOOT = 138;
  /** Ordinal value for megajoulesPerSquareMeter. */
  public static final int MEGAJOULES_PER_SQUARE_METER = 139;
  /** Ordinal value for megajoulesPerSquareFoot. */
  public static final int MEGAJOULES_PER_SQUARE_FOOT = 140;
  /** Ordinal value for wattsPerSquareMeterDegreeKelvin. */
  public static final int WATTS_PER_SQUARE_METER_DEGREE_KELVIN = 141;
  /** Ordinal value for cubicFeetPerSecond. */
  public static final int CUBIC_FEET_PER_SECOND = 142;
  /** Ordinal value for percentObscurationPerFoot. */
  public static final int PERCENT_OBSCURATION_PER_FOOT = 143;
  /** Ordinal value for percentObscurationPerMeter. */
  public static final int PERCENT_OBSCURATION_PER_METER = 144;
  /** Ordinal value for milliohms. */
  public static final int MILLIOHMS = 145;
  /** Ordinal value for megawattHours. */
  public static final int MEGAWATT_HOURS = 146;
  /** Ordinal value for kiloBtus. */
  public static final int KILO_BTUS = 147;
  /** Ordinal value for megaBtus. */
  public static final int MEGA_BTUS = 148;
  /** Ordinal value for kilojoulesPerKilogramDryAir. */
  public static final int KILOJOULES_PER_KILOGRAM_DRY_AIR = 149;
  /** Ordinal value for megajoulesPerKilogramDryAir. */
  public static final int MEGAJOULES_PER_KILOGRAM_DRY_AIR = 150;
  /** Ordinal value for kilojoulesPerDegreeKelvin. */
  public static final int KILOJOULES_PER_DEGREE_KELVIN = 151;
  /** Ordinal value for megajoulesPerDegreeKelvin. */
  public static final int MEGAJOULES_PER_DEGREE_KELVIN = 152;
  /** Ordinal value for newton. */
  public static final int NEWTON = 153;
  /** Ordinal value for gramsPerSecond. */
  public static final int GRAMS_PER_SECOND = 154;
  /** Ordinal value for gramsPerMinute. */
  public static final int GRAMS_PER_MINUTE = 155;
  /** Ordinal value for tonsPerHour. */
  public static final int TONS_PER_HOUR = 156;
  /** Ordinal value for kiloBtusPerHour. */
  public static final int KILO_BTUS_PER_HOUR = 157;
  /** Ordinal value for hundredthsSeconds. */
  public static final int HUNDREDTHS_SECONDS = 158;
  /** Ordinal value for milliseconds. */
  public static final int MILLISECONDS = 159;
  /** Ordinal value for newtonMeters. */
  public static final int NEWTON_METERS = 160;
  /** Ordinal value for millimetersPerSecond. */
  public static final int MILLIMETERS_PER_SECOND = 161;
  /** Ordinal value for millimetersPerMinute. */
  public static final int MILLIMETERS_PER_MINUTE = 162;
  /** Ordinal value for metersPerMinute. */
  public static final int METERS_PER_MINUTE = 163;
  /** Ordinal value for metersPerHour. */
  public static final int METERS_PER_HOUR = 164;
  /** Ordinal value for cubicMetersPerMinute. */
  public static final int CUBIC_METERS_PER_MINUTE = 165;
  /** Ordinal value for metersPerSecondPerSecond. */
  public static final int METERS_PER_SECOND_PER_SECOND = 166;
  /** Ordinal value for amperesPerMeter. */
  public static final int AMPERES_PER_METER = 167;
  /** Ordinal value for amperesPerSquareMeter. */
  public static final int AMPERES_PER_SQUARE_METER = 168;
  /** Ordinal value for ampereSquareMeters. */
  public static final int AMPERE_SQUARE_METERS = 169;
  /** Ordinal value for farads. */
  public static final int FARADS = 170;
  /** Ordinal value for henrys. */
  public static final int HENRYS = 171;
  /** Ordinal value for ohmMeters. */
  public static final int OHM_METERS = 172;
  /** Ordinal value for siemens. */
  public static final int SIEMENS = 173;
  /** Ordinal value for siemensPerMeter. */
  public static final int SIEMENS_PER_METER = 174;
  /** Ordinal value for teslas. */
  public static final int TESLAS = 175;
  /** Ordinal value for voltsPerDegreeKelvin. */
  public static final int VOLTS_PER_DEGREE_KELVIN = 176;
  /** Ordinal value for voltsPerMeter. */
  public static final int VOLTS_PER_METER = 177;
  /** Ordinal value for webers. */
  public static final int WEBERS = 178;
  /** Ordinal value for candelas. */
  public static final int CANDELAS = 179;
  /** Ordinal value for candelasPerSquareMeter. */
  public static final int CANDELAS_PER_SQUARE_METER = 180;
  /** Ordinal value for degreesKelvinPerHour. */
  public static final int DEGREES_KELVIN_PER_HOUR = 181;
  /** Ordinal value for degreesKelvinPerMinute. */
  public static final int DEGREES_KELVIN_PER_MINUTE = 182;
  /** Ordinal value for jouleSeconds. */
  public static final int JOULE_SECONDS = 183;
  /** Ordinal value for radiansPerSecond. */
  public static final int RADIANS_PER_SECOND = 184;
  /** Ordinal value for squareMetersPerNewton. */
  public static final int SQUARE_METERS_PER_NEWTON = 185;
  /** Ordinal value for kilogramsPerCubicMeter. */
  public static final int KILOGRAMS_PER_CUBIC_METER = 186;
  /** Ordinal value for newtonSeconds. */
  public static final int NEWTON_SECONDS = 187;
  /** Ordinal value for newtonsPerMeter. */
  public static final int NEWTONS_PER_METER = 188;
  /** Ordinal value for wattsPerMeterPerDegreeKelvin. */
  public static final int WATTS_PER_METER_PER_DEGREE_KELVIN = 189;
  /** Ordinal value for microsiemens. */
  public static final int MICROSIEMENS = 190;
  /** Ordinal value for cubicFeetPerHour. */
  public static final int CUBIC_FEET_PER_HOUR = 191;
  /** Ordinal value for usGallonsPerHour. */
  public static final int US_GALLONS_PER_HOUR = 192;
  /** Ordinal value for kilometers. */
  public static final int KILOMETERS = 193;
  /** Ordinal value for micrometers. */
  public static final int MICROMETERS = 194;
  /** Ordinal value for grams. */
  public static final int GRAMS = 195;
  /** Ordinal value for milligrams. */
  public static final int MILLIGRAMS = 196;
  /** Ordinal value for milliliters. */
  public static final int MILLILITERS = 197;
  /** Ordinal value for millilitersPerSecond. */
  public static final int MILLILITERS_PER_SECOND = 198;
  /** Ordinal value for decibels. */
  public static final int DECIBELS = 199;
  /** Ordinal value for decibelsMillivolt. */
  public static final int DECIBELS_MILLIVOLT = 200;
  /** Ordinal value for decibelsVolt. */
  public static final int DECIBELS_VOLT = 201;
  /** Ordinal value for millisiemens. */
  public static final int MILLISIEMENS = 202;
  /** Ordinal value for wattHourReactive. */
  public static final int WATT_HOUR_REACTIVE = 203;
  /** Ordinal value for kilowattHoursReactive. */
  public static final int KILOWATT_HOURS_REACTIVE = 204;
  /** Ordinal value for megawattHoursReactive. */
  public static final int MEGAWATT_HOURS_REACTIVE = 205;
  /** Ordinal value for millilitersOfWater. */
  public static final int MILLILITERS_OF_WATER = 206;
  /** Ordinal value for perMile. */
  public static final int PER_MILE = 207;
  /** Ordinal value for gramsPerGram. */
  public static final int GRAMS_PER_GRAM = 208;
  /** Ordinal value for kilogramsPerKilogram. */
  public static final int KILOGRAMS_PER_KILOGRAM = 209;
  /** Ordinal value for gramsPerKilogram. */
  public static final int GRAMS_PER_KILOGRAM = 210;
  /** Ordinal value for milligramsPerGram. */
  public static final int MILLIGRAMS_PER_GRAM = 211;
  /** Ordinal value for milligramsPerKilogram. */
  public static final int MILLIGRAMS_PER_KILOGRAM = 212;
  /** Ordinal value for gramsPerMilliliter. */
  public static final int GRAMS_PER_MILLILITER = 213;
  /** Ordinal value for gramsPerLiter. */
  public static final int GRAMS_PER_LITER = 214;
  /** Ordinal value for milligramsPerLiter. */
  public static final int MILLIGRAMS_PER_LITER = 215;
  /** Ordinal value for microgramsPerLiter. */
  public static final int MICROGRAMS_PER_LITER = 216;
  /** Ordinal value for gramsPerCubicMeter. */
  public static final int GRAMS_PER_CUBIC_METER = 217;
  /** Ordinal value for milligramsPerCubicMeter. */
  public static final int MILLIGRAMS_PER_CUBIC_METER = 218;
  /** Ordinal value for microgramsPerCubicMeter. */
  public static final int MICROGRAMS_PER_CUBIC_METER = 219;
  /** Ordinal value for nanogramsPerCubicMeter. */
  public static final int NANOGRAMS_PER_CUBIC_METER = 220;
  /** Ordinal value for gramsPerCubicCentimeter. */
  public static final int GRAMS_PER_CUBIC_CENTIMETER = 221;
  /** Ordinal value for becquerels. */
  public static final int BECQUERELS = 222;
  /** Ordinal value for kilobecquerels. */
  public static final int KILOBECQUERELS = 223;
  /** Ordinal value for megabecquerels. */
  public static final int MEGABECQUERELS = 224;
  /** Ordinal value for gray. */
  public static final int GRAY = 225;
  /** Ordinal value for milligray. */
  public static final int MILLIGRAY = 226;
  /** Ordinal value for microgray. */
  public static final int MICROGRAY = 227;
  /** Ordinal value for sieverts. */
  public static final int SIEVERTS = 228;
  /** Ordinal value for millisieverts. */
  public static final int MILLISIEVERTS = 229;
  /** Ordinal value for microsieverts. */
  public static final int MICROSIEVERTS = 230;
  /** Ordinal value for microsievertsPerHour. */
  public static final int MICROSIEVERTS_PER_HOUR = 231;
  /** Ordinal value for decibelsA. */
  public static final int DECIBELS_A = 232;
  /** Ordinal value for nephelometricTurbidityUnit. */
  public static final int NEPHELOMETRIC_TURBIDITY_UNIT = 233;
  /** Ordinal value for ph. */
  public static final int PH = 234;
  /** Ordinal value for gramsPerSquareMeter. */
  public static final int GRAMS_PER_SQUARE_METER = 235;
  /** Ordinal value for minutesPerDegreeKelvin. */
  public static final int MINUTES_PER_DEGREE_KELVIN = 236;
  /** Ordinal value for ohmMeterSquaredPerMeter. */
  public static final int OHM_METER_SQUARED_PER_METER = 237;
  /** Ordinal value for ampereSeconds. */
  public static final int AMPERE_SECONDS = 238;
  /** Ordinal value for voltAmpereHour. */
  public static final int VOLT_AMPERE_HOUR = 239;
  /** Ordinal value for kilovoltAmpereHour. */
  public static final int KILOVOLT_AMPERE_HOUR = 240;
  /** Ordinal value for megavoltAmpereHour. */
  public static final int MEGAVOLT_AMPERE_HOUR = 241;
  /** Ordinal value for voltAmpereReactiveHour. */
  public static final int VOLT_AMPERE_REACTIVE_HOUR = 242;
  /** Ordinal value for kilovoltAmpereReactiveHour. */
  public static final int KILOVOLT_AMPERE_REACTIVE_HOUR = 243;
  /** Ordinal value for megavoltAmpereReactiveHour. */
  public static final int MEGAVOLT_AMPERE_REACTIVE_HOUR = 244;
  /** Ordinal value for voltSquaredHours. */
  public static final int VOLT_SQUARED_HOURS = 245;
  /** Ordinal value for ampereSquaredHours. */
  public static final int AMPERE_SQUARED_HOURS = 246;
  /** Ordinal value for joulesPerHour. */
  public static final int JOULES_PER_HOUR = 247;
  /** Ordinal value for cubicFeetPerDay. */
  public static final int CUBIC_FEET_PER_DAY = 248;
  /** Ordinal value for cubicMetersPerDay. */
  public static final int CUBIC_METERS_PER_DAY = 249;
  /** Ordinal value for wattHourPerCubicMeter. */
  public static final int WATT_HOUR_PER_CUBIC_METER = 250;
  /** Ordinal value for joulesPerCubicMeter. */
  public static final int JOULES_PER_CUBIC_METER = 251;
  /** Ordinal value for molesPercent. */
  public static final int MOLES_PERCENT = 252;
  /** Ordinal value for pascalSecond. */
  public static final int PASCAL_SECOND = 253;
  /** Ordinal value for millionStandardCubicFeetPerMinute. */
  public static final int MILLION_STANDARD_CUBIC_FEET_PER_MINUTE = 254;
  /** Ordinal value for standardCubicFeetPerDay. */
  public static final int STANDARD_CUBIC_FEET_PER_DAY = 47808;
  /** Ordinal value for millionStandardCubicFeetPerDay. */
  public static final int MILLION_STANDARD_CUBIC_FEET_PER_DAY = 47809;
  /** Ordinal value for thousandCubicFeetPerDay. */
  public static final int THOUSAND_CUBIC_FEET_PER_DAY = 47810;
  /** Ordinal value for thousandStandardCubicFeetPerDay. */
  public static final int THOUSAND_STANDARD_CUBIC_FEET_PER_DAY = 47811;
  /** Ordinal value for poundsMassPerDay. */
  public static final int POUNDS_MASS_PER_DAY = 47812;
  /** Ordinal value for millirems. */
  public static final int MILLIREMS = 47814;
  /** Ordinal value for milliremsPerHour. */
  public static final int MILLIREMS_PER_HOUR = 47815;


  /**
   * Niagara unit name for squareMeters.
   */
  public static final String SQUARE_METERS_NAME = "square meter";
  /**
   * Niagara unit name for squareFeet.
   */
  public static final String SQUARE_FEET_NAME = "square foot";
  /**
   * Niagara unit name for milliamperes.
   */
  public static final String MILLIAMPERES_NAME = "milliampere";
  /**
   * Niagara unit name for amperes.
   */
  public static final String AMPERES_NAME = "ampere";
  /**
   * Niagara unit name for ohms.
   */
  public static final String OHMS_NAME = "ohm";
  /**
   * Niagara unit name for volts.
   */
  public static final String VOLTS_NAME = "volt";
  /**
   * Niagara unit name for kilovolts.
   */
  public static final String KILOVOLTS_NAME = "kilovolt";
  /**
   * Niagara unit name for megavolts.
   */
  public static final String MEGAVOLTS_NAME = "megavolt";
  /**
   * Niagara unit name for voltAmperes.
   */
  public static final String VOLT_AMPERES_NAME = "volt ampere";
  /**
   * Niagara unit name for kilovoltAmperes.
   */
  public static final String KILOVOLT_AMPERES_NAME = "kilovolt ampere";
  /**
   * Niagara unit name for megavoltAmperes.
   */
  public static final String MEGAVOLT_AMPERES_NAME = "megavolt ampere";
  /**
   * Niagara unit name for voltAmperesReactive.
   */
  public static final String VOLT_AMPERES_REACTIVE_NAME = "volt ampere reactive";
  /**
   * Niagara unit name for kilovoltAmperesReactive.
   */
  public static final String KILOVOLT_AMPERES_REACTIVE_NAME = "kilovolt ampere reactive";
  /**
   * Niagara unit name for megavoltAmperesReactive.
   */
  public static final String MEGAVOLT_AMPERES_REACTIVE_NAME = "megavolt ampere reactive";
  /**
   * Niagara unit name for degreesPhase.
   */
  public static final String DEGREES_PHASE_NAME = "degrees phase";
  /**
   * Niagara unit name for powerFactor.
   */
  public static final String POWER_FACTOR_NAME = "power factor";
  /**
   * Niagara unit name for joules.
   */
  public static final String JOULES_NAME = "joule";
  /**
   * Niagara unit name for kilojoules.
   */
  public static final String KILOJOULES_NAME = "kilojoule";
  /**
   * Niagara unit name for wattHours.
   */
  public static final String WATT_HOURS_NAME = "watt hour";
  /**
   * Niagara unit name for kilowattHours.
   */
  public static final String KILOWATT_HOURS_NAME = "kilowatt hour";
  /**
   * Niagara unit name for btus.
   */
  public static final String BTUS_NAME = "btu";
  /**
   * Niagara unit name for therms.
   */
  public static final String THERMS_NAME = "therm";
  /**
   * Niagara unit name for tonHours.
   */
  public static final String TON_HOURS_NAME = "tons refrigeration hour";
  /**
   * Niagara unit name for joulesPerKilogramDryAir.
   */
  public static final String JOULES_PER_KILOGRAM_DRY_AIR_NAME = "joules per kilogram dry air";
  /**
   * Niagara unit name for btusPerPoundDryAir.
   */
  public static final String BTUS_PER_POUND_DRY_AIR_NAME = "btus per pound dry air";
  /**
   * Niagara unit name for cyclesPerHour.
   */
  public static final String CYCLES_PER_HOUR_NAME = "cycles per hour";
  /**
   * Niagara unit name for cyclesPerMinute.
   */
  public static final String CYCLES_PER_MINUTE_NAME = "cycles per minute";
  /**
   * Niagara unit name for hertz.
   */
  public static final String HERTZ_NAME = "hertz";
  /**
   * Niagara unit name for gramsOfWaterPerKilogramDryAir.
   */
  public static final String GRAMS_OF_WATER_PER_KILOGRAM_DRY_AIR_NAME = "grams of water per kilogram dry air";
  /**
   * Niagara unit name for percentRelativeHumidity.
   */
  public static final String PERCENT_RELATIVE_HUMIDITY_NAME = "percent relative humidity";
  /**
   * Niagara unit name for millimeters.
   */
  public static final String MILLIMETERS_NAME = "millimeter";
  /**
   * Niagara unit name for meters.
   */
  public static final String METERS_NAME = "meter";
  /**
   * Niagara unit name for inches.
   */
  public static final String INCHES_NAME = "inch";
  /**
   * Niagara unit name for feet.
   */
  public static final String FEET_NAME = "foot";
  /**
   * Niagara unit name for wattsPerSquareFoot.
   */
  public static final String WATTS_PER_SQUARE_FOOT_NAME = "watts per square foot";
  /**
   * Niagara unit name for wattsPerSquareMeter.
   */
  public static final String WATTS_PER_SQUARE_METER_NAME = "watts per square meter";
  /**
   * Niagara unit name for lumens.
   */
  public static final String LUMENS_NAME = "lumen";
  /**
   * Niagara unit name for luxes.
   */
  public static final String LUXES_NAME = "lux";
  /**
   * Niagara unit name for footCandles.
   */
  public static final String FOOT_CANDLES_NAME = "footcandle";
  /**
   * Niagara unit name for kilograms.
   */
  public static final String KILOGRAMS_NAME = "kilogram";
  /**
   * Niagara unit name for poundsMass.
   */
  public static final String POUNDS_MASS_NAME = "pound";
  /**
   * Niagara unit name for tons.
   */
  public static final String TONS_NAME = "metric ton"; //"short ton"?
  /**
   * Niagara unit name for kilogramsPerSecond.
   */
  public static final String KILOGRAMS_PER_SECOND_NAME = "kilograms per second";
  /**
   * Niagara unit name for kilogramsPerMinute.
   */
  public static final String KILOGRAMS_PER_MINUTE_NAME = "kilograms per minute";
  /**
   * Niagara unit name for kilogramsPerHour.
   */
  public static final String KILOGRAMS_PER_HOUR_NAME = "kilograms per hour";
  /**
   * Niagara unit name for poundsMassPerMinute.
   */
  public static final String POUNDS_MASS_PER_MINUTE_NAME = "pounds per minute";
  /**
   * Niagara unit name for poundsMassPerHour.
   */
  public static final String POUNDS_MASS_PER_HOUR_NAME = "pounds per hour";
  /**
   * Niagara unit name for watts.
   */
  public static final String WATTS_NAME = "watt";
  /**
   * Niagara unit name for kilowatts.
   */
  public static final String KILOWATTS_NAME = "kilowatt";
  /**
   * Niagara unit name for megawatts.
   */
  public static final String MEGAWATTS_NAME = "megawatt";
  /**
   * Niagara unit name for btusPerHour.
   */
  public static final String BTUS_PER_HOUR_NAME = "btus per hour";
  /**
   * Niagara unit name for horsepower.
   */
  public static final String HORSEPOWER_NAME = "horsepower";
  /**
   * Niagara unit name for tonsRefrigeration.
   */
  public static final String TONS_REFRIGERATION_NAME = "tons refrigeration";
  /**
   * Niagara unit name for pascals.
   */
  public static final String PASCALS_NAME = "pascal";
  /**
   * Niagara unit name for kilopascals.
   */
  public static final String KILOPASCALS_NAME = "kilopascal";
  /**
   * Niagara unit name for bars.
   */
  public static final String BARS_NAME = "bar";
  /**
   * Niagara unit name for poundsForcePerSquareInch.
   */
  public static final String POUNDS_FORCE_PER_SQUARE_INCH_NAME = "pounds per square inch";
  /**
   * Niagara unit name for centimetersOfWater.
   */
  public static final String CENTIMETERS_OF_WATER_NAME = "centimeters of water";
  /**
   * Niagara unit name for inchesOfWater.
   */
  public static final String INCHES_OF_WATER_NAME = "inches of water";
  /**
   * Niagara unit name for millimetersOfMercury.
   */
  public static final String MILLIMETERS_OF_MERCURY_NAME = "millimeters of mercury";
  /**
   * Niagara unit name for centimetersOfMercury.
   */
  public static final String CENTIMETERS_OF_MERCURY_NAME = "centimeters of mercury";
  /**
   * Niagara unit name for inchesOfMercury.
   */
  public static final String INCHES_OF_MERCURY_NAME = "inches of mercury";
  /**
   * Niagara unit name for degreesCelsius.
   */
  public static final String DEGREES_CELSIUS_NAME = "celsius";
  /**
   * Niagara unit name for degreesKelvin.
   */
  public static final String DEGREES_KELVIN_NAME = "kelvin";
  /**
   * Niagara unit name for degreesFahrenheit.
   */
  public static final String DEGREES_FAHRENHEIT_NAME = "fahrenheit";
  /**
   * Niagara unit name for degreeDaysCelsius.
   */
  public static final String DEGREE_DAYS_CELSIUS_NAME = "degree days celsius";
  /**
   * Niagara unit name for degreeDaysFahrenheit.
   */
  public static final String DEGREE_DAYS_FAHRENHEIT_NAME = "degree days fahrenheit";
  /**
   * Niagara unit name for years.
   */
  public static final String YEARS_NAME = "year";
  /**
   * Niagara unit name for months.
   */
  public static final String MONTHS_NAME = "julian month";
  /**
   * Niagara unit name for weeks.
   */
  public static final String WEEKS_NAME = "week";
  /**
   * Niagara unit name for days.
   */
  public static final String DAYS_NAME = "day";
  /**
   * Niagara unit name for hours.
   */
  public static final String HOURS_NAME = "hour";
  /**
   * Niagara unit name for minutes.
   */
  public static final String MINUTES_NAME = "minute";
  /**
   * Niagara unit name for seconds.
   */
  public static final String SECONDS_NAME = "second";
  /**
   * Niagara unit name for metersPerSecond.
   */
  public static final String METERS_PER_SECOND_NAME = "meters per second";
  /**
   * Niagara unit name for kilometersPerHour.
   */
  public static final String KILOMETERS_PER_HOUR_NAME = "kilometers per hour";
  /**
   * Niagara unit name for feetPerSecond.
   */
  public static final String FEET_PER_SECOND_NAME = "feet per second";
  /**
   * Niagara unit name for feetPerMinute.
   */
  public static final String FEET_PER_MINUTE_NAME = "feet per minute";
  /**
   * Niagara unit name for milesPerHour.
   */
  public static final String MILES_PER_HOUR_NAME = "miles per hour";
  /**
   * Niagara unit name for cubicFeet.
   */
  public static final String CUBIC_FEET_NAME = "cubic foot";
  /**
   * Niagara unit name for cubicMeters.
   */
  public static final String CUBIC_METERS_NAME = "cubic meter";
  /**
   * Niagara unit name for imperialGallons.
   */
  public static final String IMPERIAL_GALLONS_NAME = "imperial gallon";
  /**
   * Niagara unit name for liters.
   */
  public static final String LITERS_NAME = "liter";
  /**
   * Niagara unit name for usGallons.
   */
  public static final String US_GALLONS_NAME = "gallon";
  /**
   * Niagara unit name for cubicFeetPerMinute.
   */
  public static final String CUBIC_FEET_PER_MINUTE_NAME = "cubic feet per minute";
  /**
   * Niagara unit name for cubicMetersPerSecond.
   */
  public static final String CUBIC_METERS_PER_SECOND_NAME = "cubic meters per second";
  /**
   * Niagara unit name for imperialGallonsPerMinute.
   */
  public static final String IMPERIAL_GALLONS_PER_MINUTE_NAME = "imperial gallons per minute";
  /**
   * Niagara unit name for litersPerSecond.
   */
  public static final String LITERS_PER_SECOND_NAME = "liters per second";
  /**
   * Niagara unit name for litersPerMinute.
   */
  public static final String LITERS_PER_MINUTE_NAME = "liters per minute";
  /**
   * Niagara unit name for usGallonsPerMinute.
   */
  public static final String US_GALLONS_PER_MINUTE_NAME = "gallons per minute";
  /**
   * Niagara unit name for degreesAngular.
   */
  public static final String DEGREES_ANGULAR_NAME = "degrees angular";
  /**
   * Niagara unit name for degreesCelsiusPerHour.
   */
  public static final String DEGREES_CELSIUS_PER_HOUR_NAME = "degrees celsius per hour";
  /**
   * Niagara unit name for degreesCelsiusPerMinute.
   */
  public static final String DEGREES_CELSIUS_PER_MINUTE_NAME = "degrees celsius per minute";
  /**
   * Niagara unit name for degreesFahrenheitPerHour.
   */
  public static final String DEGREES_FAHRENHEIT_PER_HOUR_NAME = "degrees fahrenheit per hour";
  /**
   * Niagara unit name for degreesFahrenheitPerMinute.
   */
  public static final String DEGREES_FAHRENHEIT_PER_MINUTE_NAME = "degrees fahrenheit per minute";
  /**
   * Niagara unit name for noUnits.
   */
  public static final String NO_UNITS_NAME = "null";
  /**
   * Niagara unit name for partsPerMillion.
   */
  public static final String PARTS_PER_MILLION_NAME = "parts per million";
  /**
   * Niagara unit name for partsPerBillion.
   */
  public static final String PARTS_PER_BILLION_NAME = "parts per billion";
  /**
   * Niagara unit name for percent.
   */
  public static final String PERCENT_NAME = "percent";
  /**
   * Niagara unit name for percentPerSecond.
   */
  public static final String PERCENT_PER_SECOND_NAME = "percent per second";
  /**
   * Niagara unit name for perMinute.
   */
  public static final String PER_MINUTE_NAME = "per minute";
  /**
   * Niagara unit name for perSecond.
   */
  public static final String PER_SECOND_NAME = "per second";
  /**
   * Niagara unit name for psiPerDegreeFahrenheit.
   */
  public static final String PSI_PER_DEGREE_FAHRENHEIT_NAME = "psi per degree fahrenheit";
  /**
   * Niagara unit name for radians.
   */
  public static final String RADIANS_NAME = "radian";
  /**
   * Niagara unit name for revolutionsPerMinute.
   */
  public static final String REVOLUTIONS_PER_MINUTE_NAME = "revolutions per minute";
  /**
   * Niagara unit name for currency1.
   */
  public static final String CURRENCY_1_NAME = "currency1";
  /**
   * Niagara unit name for currency2.
   */
  public static final String CURRENCY_2_NAME = "currency2";
  /**
   * Niagara unit name for currency3.
   */
  public static final String CURRENCY_3_NAME = "currency3";
  /**
   * Niagara unit name for currency4.
   */
  public static final String CURRENCY_4_NAME = "currency4";
  /**
   * Niagara unit name for currency5.
   */
  public static final String CURRENCY_5_NAME = "currency5";
  /**
   * Niagara unit name for currency6.
   */
  public static final String CURRENCY_6_NAME = "currency6";
  /**
   * Niagara unit name for currency7.
   */
  public static final String CURRENCY_7_NAME = "currency7";
  /**
   * Niagara unit name for currency8.
   */
  public static final String CURRENCY_8_NAME = "currency8";
  /**
   * Niagara unit name for currency9.
   */
  public static final String CURRENCY_9_NAME = "currency9";
  /**
   * Niagara unit name for currency10.
   */
  public static final String CURRENCY_10_NAME = "currency10";
  /**
   * Niagara unit name for squareInches.
   */
  public static final String SQUARE_INCHES_NAME = "square inch";
  /**
   * Niagara unit name for squareCentimeters.
   */
  public static final String SQUARE_CENTIMETERS_NAME = "square centimeter";
  /**
   * Niagara unit name for btusPerPound.
   */
  public static final String BTUS_PER_POUND_NAME = "btus per pound";
  /**
   * Niagara unit name for centimeters.
   */
  public static final String CENTIMETERS_NAME = "centimeter";
  /**
   * Niagara unit name for poundsMassPerSecond.
   */
  public static final String POUNDS_MASS_PER_SECOND_NAME = "pounds per second";
  /**
   * Niagara unit name for deltaDegreesFahrenheit.
   */
  public static final String DELTA_DEGREES_FAHRENHEIT_NAME = "fahrenheit degrees";
  /**
   * Niagara unit name for deltaDegreesKelvin.
   */
  public static final String DELTA_DEGREES_KELVIN_NAME = "kelvin degrees";
  /**
   * Niagara unit name for kilohms.
   */
  public static final String KILOHMS_NAME = "kilohm";
  /**
   * Niagara unit name for megohms.
   */
  public static final String MEGOHMS_NAME = "megohm";
  /**
   * Niagara unit name for millivolts.
   */
  public static final String MILLIVOLTS_NAME = "millivolt";
  /**
   * Niagara unit name for kilojoulesPerKilogram.
   */
  public static final String KILOJOULES_PER_KILOGRAM_NAME = "kilojoules per kilogram";
  /**
   * Niagara unit name for megajoules.
   */
  public static final String MEGAJOULES_NAME = "megajoule";
  /**
   * Niagara unit name for joulesPerDegreeKelvin.
   */
  public static final String JOULES_PER_DEGREE_KELVIN_NAME = "joules per degree kelvin";
  /**
   * Niagara unit name for joulesPerKilogramDegreeKelvin.
   */
  public static final String JOULES_PER_KILOGRAM_DEGREE_KELVIN_NAME = "joules per kilogram degree kelvin";
  /**
   * Niagara unit name for kilohertz.
   */
  public static final String KILOHERTZ_NAME = "kilohertz";
  /**
   * Niagara unit name for megahertz.
   */
  public static final String MEGAHERTZ_NAME = "megahertz";
  /**
   * Niagara unit name for perHour.
   */
  public static final String PER_HOUR_NAME = "per hour";
  /**
   * Niagara unit name for milliwatts.
   */
  public static final String MILLIWATTS_NAME = "milliwatt";
  /**
   * Niagara unit name for hectopascals.
   */
  public static final String HECTOPASCALS_NAME = "hectopascal";
  /**
   * Niagara unit name for millibars.
   */
  public static final String MILLIBARS_NAME = "millibar";
  /**
   * Niagara unit name for cubicMetersPerHour.
   */
  public static final String CUBIC_METERS_PER_HOUR_NAME = "cubic meters per hour";
  /**
   * Niagara unit name for litersPerHour.
   */
  public static final String LITERS_PER_HOUR_NAME = "liters per hour";
  /**
   * Niagara unit name for kilowattHoursPerSquareMeter.
   */
  public static final String KILOWATT_HOURS_PER_SQUARE_METER_NAME = "kilowatt hours per square meter";
  /**
   * Niagara unit name for kilowattHoursPerSquareFoot.
   */
  public static final String KILOWATT_HOURS_PER_SQUARE_FOOT_NAME = "kilowatt hours per square foot";
  /**
   * Niagara unit name for megajoulesPerSquareMeter.
   */
  public static final String MEGAJOULES_PER_SQUARE_METER_NAME = "megajoules per square meter";
  /**
   * Niagara unit name for megajoulesPerSquareFoot.
   */
  public static final String MEGAJOULES_PER_SQUARE_FOOT_NAME = "megajoules per square foot";
  /**
   * Niagara unit name for wattsPerSquareMeterDegreeKelvin.
   */
  public static final String WATTS_PER_SQUARE_METER_DEGREE_KELVIN_NAME = "watts per square meter degree kelvin";
  /**
   * Niagara unit name for cubicFeetPerSecond.
   */
  public static final String CUBIC_FEET_PER_SECOND_NAME = "cubic feet per second";
  /**
   * Niagara unit name for percentObscurationPerFoot.
   */
  public static final String PERCENT_OBSCURATION_PER_FOOT_NAME = "percent obscuration per foot";
  /**
   * Niagara unit name for percentObscurationPerMeter.
   */
  public static final String PERCENT_OBSCURATION_PER_METER_NAME = "percent obscuration per meter";
  /**
   * Niagara unit name for milliohms.
   */
  public static final String MILLIOHMS_NAME = "milliohm";
  /**
   * Niagara unit name for megawattHours.
   */
  public static final String MEGAWATT_HOURS_NAME = "megawatt hour";
  /**
   * Niagara unit name for kiloBtus.
   */
  public static final String KILO_BTUS_NAME = "kilobtu";
  /**
   * Niagara unit name for megaBtus.
   */
  public static final String MEGA_BTUS_NAME = "megabtu";
  /**
   * Niagara unit name for kilojoulesPerKilogramDryAir.
   */
  public static final String KILOJOULES_PER_KILOGRAM_DRY_AIR_NAME = "kilojoules per kilogram dry air";
  /**
   * Niagara unit name for megajoulesPerKilogramDryAir.
   */
  public static final String MEGAJOULES_PER_KILOGRAM_DRY_AIR_NAME = "megajoules per kilogram dry air";
  /**
   * Niagara unit name for kilojoulesPerDegreeKelvin.
   */
  public static final String KILOJOULES_PER_DEGREE_KELVIN_NAME = "kilojoules per degree kelvin";
  /**
   * Niagara unit name for megajoulesPerDegreeKelvin.
   */
  public static final String MEGAJOULES_PER_DEGREE_KELVIN_NAME = "megajoules per degree kelvin";
  /**
   * Niagara unit name for newton.
   */
  public static final String NEWTON_NAME = "newton";
  /**
   * Niagara unit name for gramsPerSecond.
   */
  public static final String GRAMS_PER_SECOND_NAME = "grams per second";
  /**
   * Niagara unit name for gramsPerMinute.
   */
  public static final String GRAMS_PER_MINUTE_NAME = "grams per minute";
  /**
   * Niagara unit name for tonsPerHour.
   */
  public static final String TONS_PER_HOUR_NAME = "metric tons per hour"; //"short tons per hour"?
  /**
   * Niagara unit name for kiloBtusPerHour.
   */
  public static final String KILO_BTUS_PER_HOUR_NAME = "kilobtus per hour";
  /**
   * Niagara unit name for hundredthsSeconds.
   */
  public static final String HUNDREDTHS_SECONDS_NAME = "hundredths second";
  /**
   * Niagara unit name for milliseconds.
   */
  public static final String MILLISECONDS_NAME = "millisecond";
  /**
   * Niagara unit name for newtonMeters.
   */
  public static final String NEWTON_METERS_NAME = "newton meter";
  /**
   * Niagara unit name for millimetersPerSecond.
   */
  public static final String MILLIMETERS_PER_SECOND_NAME = "millimeters per second";
  /**
   * Niagara unit name for millimetersPerMinute.
   */
  public static final String MILLIMETERS_PER_MINUTE_NAME = "millimeters per minute";
  /**
   * Niagara unit name for metersPerMinute.
   */
  public static final String METERS_PER_MINUTE_NAME = "meters per minute";
  /**
   * Niagara unit name for metersPerHour.
   */
  public static final String METERS_PER_HOUR_NAME = "meters per hour";
  /**
   * Niagara unit name for cubicMetersPerMinute.
   */
  public static final String CUBIC_METERS_PER_MINUTE_NAME = "cubic meters per minute";
  /**
   * Niagara unit name for metersPerSecondPerSecond.
   */
  public static final String METERS_PER_SECOND_PER_SECOND_NAME = "meters per second squared";
  /**
   * Niagara unit name for amperesPerMeter.
   */
  public static final String AMPERES_PER_METER_NAME = "amperes per meter";
  /**
   * Niagara unit name for amperesPerSquareMeter.
   */
  public static final String AMPERES_PER_SQUARE_METER_NAME = "amperes per square meter";
  /**
   * Niagara unit name for ampereSquareMeters.
   */
  public static final String AMPERE_SQUARE_METERS_NAME = "ampere square meter";
  /**
   * Niagara unit name for farads.
   */
  public static final String FARADS_NAME = "farad";
  /**
   * Niagara unit name for henrys.
   */
  public static final String HENRYS_NAME = "henry";
  /**
   * Niagara unit name for ohmMeters.
   */
  public static final String OHM_METERS_NAME = "ohm meter";
  /**
   * Niagara unit name for siemens.
   */
  public static final String SIEMENS_NAME = "siemens";
  /**
   * Niagara unit name for siemensPerMeter.
   */
  public static final String SIEMENS_PER_METER_NAME = "siemens per meter";
  /**
   * Niagara unit name for teslas.
   */
  public static final String TESLAS_NAME = "tesla";
  /**
   * Niagara unit name for voltsPerDegreeKelvin.
   */
  public static final String VOLTS_PER_DEGREE_KELVIN_NAME = "volts per degree kelvin";
  /**
   * Niagara unit name for voltsPerMeter.
   */
  public static final String VOLTS_PER_METER_NAME = "volts per meter";
  /**
   * Niagara unit name for webers.
   */
  public static final String WEBERS_NAME = "weber";
  /**
   * Niagara unit name for candelas.
   */
  public static final String CANDELAS_NAME = "candela";
  /**
   * Niagara unit name for candelasPerSquareMeter.
   */
  public static final String CANDELAS_PER_SQUARE_METER_NAME = "candelas per square meter";
  /**
   * Niagara unit name for degreesKelvinPerHour.
   */
  public static final String DEGREES_KELVIN_PER_HOUR_NAME = "degrees kelvin per hour";
  /**
   * Niagara unit name for degreesKelvinPerMinute.
   */
  public static final String DEGREES_KELVIN_PER_MINUTE_NAME = "degrees kelvin per minute";
  /**
   * Niagara unit name for jouleSeconds.
   */
  public static final String JOULE_SECONDS_NAME = "joule second";
  /**
   * Niagara unit name for radiansPerSecond.
   */
  public static final String RADIANS_PER_SECOND_NAME = "radians per second";
  /**
   * Niagara unit name for squareMetersPerNewton.
   */
  public static final String SQUARE_METERS_PER_NEWTON_NAME = "square meters per newton";
  /**
   * Niagara unit name for kilogramsPerCubicMeter.
   */
  public static final String KILOGRAMS_PER_CUBIC_METER_NAME = "kilograms per cubic meter";
  /**
   * Niagara unit name for newtonSeconds.
   */
  public static final String NEWTON_SECONDS_NAME = "newton second";
  /**
   * Niagara unit name for newtonsPerMeter.
   */
  public static final String NEWTONS_PER_METER_NAME = "newtons per meter";
  /**
   * Niagara unit name for wattsPerMeterPerDegreeKelvin.
   */
  public static final String WATTS_PER_METER_PER_DEGREE_KELVIN_NAME = "watts per meter degree kelvin";
  /**
   * Niagara unit name for microSiemens.
   */
  public static final String MICROSIEMENS_NAME = "microsiemens";
  /**
   * Niagara unit name for cubicFeetPerHour,.
   */
  public static final String CUBIC_FEET_PER_HOUR_NAME = "cubic feet per hour";
  /**
   * Niagara unit name for usGallonsPerHour,.
   */
  public static final String US_GALLONS_PER_HOUR_NAME = "gallons per hour";
  /**
   * Niagara unit name for kilometers.
   */
  public static final String KILOMETERS_NAME = "kilometer";
  /**
   * Niagara unit name for micrometers.
   */
  public static final String MICROMETERS_NAME = "micrometer";
  /**
   * Niagara unit name for grams.
   */
  public static final String GRAMS_NAME = "gram";
  /**
   * Niagara unit name for milligrams.
   */
  public static final String MILLIGRAMS_NAME = "milligram";
  /**
   * Niagara unit name for microSiemens.
   */
  public static final String MILLILITERS_NAME = "milliliter";
  /**
   * Niagara unit name for millilitersPerSecond.
   */
  public static final String MILLILITERS_PER_SECOND_NAME = "milliliters per second";
  /**
   * Niagara unit name for decibels.
   */
  public static final String DECIBELS_NAME = "decibel";
  /**
   * Niagara unit name for decibelsMillivolt.
   */
  public static final String DECIBELS_MILLIVOLT_NAME = "db milliVolt";
  /**
   * Niagara unit name for decibelsVolt.
   */
  public static final String DECIBELS_VOLT_NAME = "db volt";
  /**
   * Niagara unit name for millisiemens.
   */
  public static final String MILLISIEMENS_NAME = "millisiemens";
  /**
   * Niagara unit name for wattHourReactive.
   */
  public static final String WATT_HOUR_REACTIVE_NAME = "watt reactive hour";
  /**
   * Niagara unit name for kilowattHoursReactive.
   */
  public static final String KILOWATT_HOURS_REACTIVE_NAME = "kilowatt reactive hour";
  /**
   * Niagara unit name for megawattHoursReactive.
   */
  public static final String MEGAWATT_HOURS_REACTIVE_NAME = "megawatt reactive hour";
  /**
   * Niagara unit name for millilitersOfWater.
   */
  public static final String MILLILITERS_OF_WATER_NAME = "milliliters of water";
  /**
   * Niagara unit name for perMile.
   */
  public static final String PER_MILE_NAME = "per mile";
  /**
   * Niagara unit name for gramsPerGram.
   */
  public static final String GRAMS_PER_GRAM_NAME = "grams per gram";
  /**
   * Niagara unit name for kilogramsPerKilogram.
   */
  public static final String KILOGRAMS_PER_KILOGRAM_NAME = "kilograms per kilogram";
  /**
   * Niagara unit name for gramsPerKilogram.
   */
  public static final String GRAMS_PER_KILOGRAM_NAME = "grams per kilogram";
  /**
   * Niagara unit name for milligramsPerGram.
   */
  public static final String MILLIGRAMS_PER_GRAM_NAME = "milligrams per gram";
  /**
   * Niagara unit name for milligramsPerKilogram.
   */
  public static final String MILLIGRAMS_PER_KILOGRAM_NAME = "milligrams per kilogram";
  /**
   * Niagara unit name for gramsPerMilliliter.
   */
  public static final String GRAMS_PER_MILLILITER_NAME = "grams per milliliter";
  /**
   * Niagara unit name for gramsPerLiter.
   */
  public static final String GRAMS_PER_LITER_NAME = "grams per liter";
  /**
   * Niagara unit name for milligramsPerLiter.
   */
  public static final String MILLIGRAMS_PER_LITER_NAME = "milligrams per liter";
  /**
   * Niagara unit name for microgramsPerLiter.
   */
  public static final String MICROGRAMS_PER_LITER_NAME = "micrograms per liter";
  /**
   * Niagara unit name for gramsPerCubicMeter.
   */
  public static final String GRAMS_PER_CUBIC_METER_NAME = "grams per cubic meter";
  /**
   * Niagara unit name for gramsPerCubicMeter.
   */
  public static final String MILLIGRAMS_PER_CUBIC_METER_NAME = "milligrams per cubic meter";
  /**
   * Niagara unit name for gramsPerCubicMeter.
   */
  public static final String MICROGRAMS_PER_CUBIC_METER_NAME = "micrograms per cubic meter";
  /**
   * Niagara unit name for nanogramsPerCubicMeter.
   */
  public static final String NANOGRAMS_PER_CUBIC_METER_NAME = "nanograms per cubic meter";
  /**
   * Niagara unit name for gramsPerCubicCentimeter.
   */
  public static final String GRAMS_PER_CUBIC_CENTIMETER_NAME = "grams per cubic centimeter";
  /**
   * Niagara unit name for becquerels.
   */
  public static final String BECQUERELS_NAME = "becquerel";
  /**
   * Niagara unit name for kilobecquerels.
   */
  public static final String KILOBECQUERELS_NAME = "kilobecquerel";
  /**
   * Niagara unit name for megabecquerels.
   */
  public static final String MEGABECQUERELS_NAME = "megabecquerel";
  /**
   * Niagara unit name for gray.
   */
  public static final String GRAY_NAME = "gray";
  /**
   * Niagara unit name for milligray.
   */
  public static final String MILLIGRAY_NAME = "milligray";
  /**
   * Niagara unit name for microgray.
   */
  public static final String MICROGRAY_NAME = "microgray";
  /**
   * Niagara unit name for sieverts.
   */
  public static final String SIEVERTS_NAME = "sievert";
  /**
   * Niagara unit name for millisieverts.
   */
  public static final String MILLISIEVERTS_NAME = "millisievert";
  /**
   * Niagara unit name for microsieverts.
   */
  public static final String MICROSIEVERTS_NAME = "microsievert";
  /**
   * Niagara unit name for microsievertsPerHour.
   */
  public static final String MICROSIEVERTS_PER_HOUR_NAME = "microsievert per hour";
  /**
   * Niagara unit name for decibelsA.
   */
  public static final String DECIBELS_A_NAME = "db ampere";
  /**
   * Niagara unit name for nephelometricTurbidityUnit.
   */
  public static final String NEPHELOMETRIC_TURBIDITY_UNIT_NAME = "nephelometric turbidity units";
  /**
   * Niagara unit name for ph.
   */
  public static final String PH_NAME = "pH";
  /**
   * Niagara unit name for gramsPerSquareMeter.
   */
  public static final String GRAMS_PER_SQUARE_METER_NAME = "grams per square meter";
  /**
   * Niagara unit name for minutesPerDegreeKelvin.
   */
  public static final String MINUTES_PER_DEGREE_KELVIN_NAME = "minutes per degree kelvin";
  /**
   * Niagara unit name for ohm meter squared per meter.
   */
  public static final String OHM_METER_SQUARED_PER_METER_NAME = "ohm meter squared per meter";
  /**
   * Niagara unit name for ampere seconds.
   */
  public static final String AMPERE_SECONDS_NAME = "ampere seconds";
  /**
   * Niagara unit name for volt ampere hour.
   */
  public static final String VOLT_AMPERE_HOUR_NAME = "volt ampere hour";
  /**
   * Niagara unit name for kilovolt ampere hour.
   */
  public static final String KILOVOLT_AMPERE_HOUR_NAME = "kilovolt ampere hour";
  /**
   * Niagara unit name for megavolt ampere hour.
   */
  public static final String MEGAVOLT_AMPERE_HOUR_NAME = "megavolt ampere hour";
  /**
   * Niagara unit name for volt ampere reactive hour.
   */
  public static final String VOLT_AMPERE_REACTIVE_HOUR_NAME = "volt ampere reactive hour";
  /**
   * Niagara unit name for kilovolt ampere reactive hour.
   */
  public static final String KILOVOLT_AMPERE_REACTIVE_HOUR_NAME = "kilovolt ampere reactive hour";
  /**
   * Niagara unit name for megavolt ampere reactive hour.
   */
  public static final String MEGAVOLT_AMPERE_REACTIVE_HOUR_NAME = "megavolt ampere reactive hour";
  /**
   * Niagara unit name for volt squared hours.
   */
  public static final String VOLT_SQUARED_HOURS_NAME = "volt squared hours";
  /**
   * Niagara unit name for ampere squared hours.
   */
  public static final String AMPERE_SQUARED_HOURS_NAME = "ampere squared hours";
  /**
   * Niagara unit name for joules per hour.
   */
  public static final String JOULES_PER_HOUR_NAME = "joules per hour";
  /**
   * Niagara unit name for cubic feet per day.
   */
  public static final String CUBIC_FEET_PER_DAY_NAME = "cubic feet per day";
  /**
   * Niagara unit name for cubic meters per day.
   */
  public static final String CUBIC_METERS_PER_DAY_NAME = "cubic meters per day";
  /**
   * Niagara unit name for watt hour per cubic meter.
   */
  public static final String WATT_HOUR_PER_CUBIC_METER_NAME = "watt hour per cubic meter";
  /**
   * Niagara unit name for joules per cubic meter.
   */
  public static final String JOULES_PER_CUBIC_METER_NAME = "joules per cubic meter";
  /**
   * Niagara unit name for moles percent.
   */
  public static final String MOLES_PERCENT_NAME = "moles percent";
  /**
   * Niagara unit name for pascal Second.
   */
  public static final String PASCAL_SECOND_NAME = "pascal second";
  /**
   * Niagara unit name for million standard cubic feet per minute.
   */
  public static final String MILLION_STANDARD_CUBIC_FEET_PER_MINUTE_NAME = "million standard cubic feet per minute";
  /**
   * Niagara unit name for standard cubic feet per day.
   */
  public static final String STANDARD_CUBIC_FEET_PER_DAY_NAME = "standard cubic feet per day";
  /**
   * Niagara unit name for million standard cubic feet per day.
   */
  public static final String MILLION_STANDARD_CUBIC_FEET_PER_DAY_NAME = "million standard cubic feet per day";
  /**
   * Niagara unit name for thousand cubic feet per day.
   */
  public static final String THOUSAND_CUBIC_FEET_PER_DAY_NAME = "thousand cubic feet per day";
  /**
   * Niagara unit name for thousand standard cubic feet per day.
   */
  public static final String THOUSAND_STANDARD_CUBIC_FEET_PER_DAY_NAME = "thousand standard cubic feet per day";
  /**
   * Niagara unit name for pounds mass per day.
   */
  public static final String POUNDS_MASS_PER_DAY_NAME = "pounds mass per day";
  /**
   * Niagara unit name for millirems.
   */
  public static final String MILLIREMS_NAME = "millirems";
  /**
   * Niagara unit name for millirems per hour.
   */
  public static final String MILLIREMS_PER_HOUR_NAME = "millirems per hour";


  /**
   * BBacnetEngineeringUnits constant for squareMeters.
   */
  public static final BBacnetEngineeringUnits squareMeters = new BBacnetEngineeringUnits(SQUARE_METERS, SQUARE_METERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for squareFeet.
   */
  public static final BBacnetEngineeringUnits squareFeet = new BBacnetEngineeringUnits(SQUARE_FEET, SQUARE_FEET_NAME);
  /**
   * BBacnetEngineeringUnits constant for milliamperes.
   */
  public static final BBacnetEngineeringUnits milliamperes = new BBacnetEngineeringUnits(MILLIAMPERES, MILLIAMPERES_NAME);
  /**
   * BBacnetEngineeringUnits constant for amperes.
   */
  public static final BBacnetEngineeringUnits amperes = new BBacnetEngineeringUnits(AMPERES, AMPERES_NAME);
  /**
   * BBacnetEngineeringUnits constant for ohms.
   */
  public static final BBacnetEngineeringUnits ohms = new BBacnetEngineeringUnits(OHMS, OHMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for volts.
   */
  public static final BBacnetEngineeringUnits volts = new BBacnetEngineeringUnits(VOLTS, VOLTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilovolts.
   */
  public static final BBacnetEngineeringUnits kilovolts = new BBacnetEngineeringUnits(KILOVOLTS, KILOVOLTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for megavolts.
   */
  public static final BBacnetEngineeringUnits megavolts = new BBacnetEngineeringUnits(MEGAVOLTS, MEGAVOLTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for voltAmperes.
   */
  public static final BBacnetEngineeringUnits voltAmperes = new BBacnetEngineeringUnits(VOLT_AMPERES, VOLT_AMPERES_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilovoltAmperes.
   */
  public static final BBacnetEngineeringUnits kilovoltAmperes = new BBacnetEngineeringUnits(KILOVOLT_AMPERES, KILOVOLT_AMPERES_NAME);
  /**
   * BBacnetEngineeringUnits constant for megavoltAmperes.
   */
  public static final BBacnetEngineeringUnits megavoltAmperes = new BBacnetEngineeringUnits(MEGAVOLT_AMPERES, MEGAVOLT_AMPERES_NAME);
  /**
   * BBacnetEngineeringUnits constant for voltAmperesReactive.
   */
  public static final BBacnetEngineeringUnits voltAmperesReactive = new BBacnetEngineeringUnits(VOLT_AMPERES_REACTIVE, VOLT_AMPERES_REACTIVE_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilovoltAmperesReactive.
   */
  public static final BBacnetEngineeringUnits kilovoltAmperesReactive = new BBacnetEngineeringUnits(KILOVOLT_AMPERES_REACTIVE, KILOVOLT_AMPERES_REACTIVE_NAME);
  /**
   * BBacnetEngineeringUnits constant for megavoltAmperesReactive.
   */
  public static final BBacnetEngineeringUnits megavoltAmperesReactive = new BBacnetEngineeringUnits(MEGAVOLT_AMPERES_REACTIVE, MEGAVOLT_AMPERES_REACTIVE_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesPhase.
   */
  public static final BBacnetEngineeringUnits degreesPhase = new BBacnetEngineeringUnits(DEGREES_PHASE, DEGREES_PHASE_NAME);
  /**
   * BBacnetEngineeringUnits constant for powerFactor.
   */
  public static final BBacnetEngineeringUnits powerFactor = new BBacnetEngineeringUnits(POWER_FACTOR, POWER_FACTOR_NAME);
  /**
   * BBacnetEngineeringUnits constant for joules.
   */
  public static final BBacnetEngineeringUnits joules = new BBacnetEngineeringUnits(JOULES, JOULES_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilojoules.
   */
  public static final BBacnetEngineeringUnits kilojoules = new BBacnetEngineeringUnits(KILOJOULES, KILOJOULES_NAME);
  /**
   * BBacnetEngineeringUnits constant for wattHours.
   */
  public static final BBacnetEngineeringUnits wattHours = new BBacnetEngineeringUnits(WATT_HOURS, WATT_HOURS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilowattHours.
   */
  public static final BBacnetEngineeringUnits kilowattHours = new BBacnetEngineeringUnits(KILOWATT_HOURS, KILOWATT_HOURS_NAME);
  /**
   * BBacnetEngineeringUnits constant for btus.
   */
  public static final BBacnetEngineeringUnits btus = new BBacnetEngineeringUnits(BTUS, BTUS_NAME);
  /**
   * BBacnetEngineeringUnits constant for therms.
   */
  public static final BBacnetEngineeringUnits therms = new BBacnetEngineeringUnits(THERMS, THERMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for tonHours.
   */
  public static final BBacnetEngineeringUnits tonHours = new BBacnetEngineeringUnits(TON_HOURS, TON_HOURS_NAME);
  /**
   * BBacnetEngineeringUnits constant for joulesPerKilogramDryAir.
   */
  public static final BBacnetEngineeringUnits joulesPerKilogramDryAir = new BBacnetEngineeringUnits(JOULES_PER_KILOGRAM_DRY_AIR, JOULES_PER_KILOGRAM_DRY_AIR_NAME);
  /**
   * BBacnetEngineeringUnits constant for btusPerPoundDryAir.
   */
  public static final BBacnetEngineeringUnits btusPerPoundDryAir = new BBacnetEngineeringUnits(BTUS_PER_POUND_DRY_AIR, BTUS_PER_POUND_DRY_AIR_NAME);
  /**
   * BBacnetEngineeringUnits constant for cyclesPerHour.
   */
  public static final BBacnetEngineeringUnits cyclesPerHour = new BBacnetEngineeringUnits(CYCLES_PER_HOUR, CYCLES_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for cyclesPerMinute.
   */
  public static final BBacnetEngineeringUnits cyclesPerMinute = new BBacnetEngineeringUnits(CYCLES_PER_MINUTE, CYCLES_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for hertz.
   */
  public static final BBacnetEngineeringUnits hertz = new BBacnetEngineeringUnits(HERTZ, HERTZ_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsOfWaterPerKilogramDryAir.
   */
  public static final BBacnetEngineeringUnits gramsOfWaterPerKilogramDryAir = new BBacnetEngineeringUnits(GRAMS_OF_WATER_PER_KILOGRAM_DRY_AIR, GRAMS_OF_WATER_PER_KILOGRAM_DRY_AIR_NAME);
  /**
   * BBacnetEngineeringUnits constant for percentRelativeHumidity.
   */
  public static final BBacnetEngineeringUnits percentRelativeHumidity = new BBacnetEngineeringUnits(PERCENT_RELATIVE_HUMIDITY, PERCENT_RELATIVE_HUMIDITY_NAME);
  /**
   * BBacnetEngineeringUnits constant for millimeters.
   */
  public static final BBacnetEngineeringUnits millimeters = new BBacnetEngineeringUnits(MILLIMETERS, MILLIMETERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for meters.
   */
  public static final BBacnetEngineeringUnits meters = new BBacnetEngineeringUnits(METERS, METERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for inches.
   */
  public static final BBacnetEngineeringUnits inches = new BBacnetEngineeringUnits(INCHES, INCHES_NAME);
  /**
   * BBacnetEngineeringUnits constant for feet.
   */
  public static final BBacnetEngineeringUnits feet = new BBacnetEngineeringUnits(FEET, FEET_NAME);
  /**
   * BBacnetEngineeringUnits constant for wattsPerSquareFoot.
   */
  public static final BBacnetEngineeringUnits wattsPerSquareFoot = new BBacnetEngineeringUnits(WATTS_PER_SQUARE_FOOT, WATTS_PER_SQUARE_FOOT_NAME);
  /**
   * BBacnetEngineeringUnits constant for wattsPerSquareMeter.
   */
  public static final BBacnetEngineeringUnits wattsPerSquareMeter = new BBacnetEngineeringUnits(WATTS_PER_SQUARE_METER, WATTS_PER_SQUARE_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for lumens.
   */
  public static final BBacnetEngineeringUnits lumens = new BBacnetEngineeringUnits(LUMENS, LUMENS_NAME);
  /**
   * BBacnetEngineeringUnits constant for luxes.
   */
  public static final BBacnetEngineeringUnits luxes = new BBacnetEngineeringUnits(LUXES, LUXES_NAME);
  /**
   * BBacnetEngineeringUnits constant for footCandles.
   */
  public static final BBacnetEngineeringUnits footCandles = new BBacnetEngineeringUnits(FOOT_CANDLES, FOOT_CANDLES_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilograms.
   */
  public static final BBacnetEngineeringUnits kilograms = new BBacnetEngineeringUnits(KILOGRAMS, KILOGRAMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for poundsMass.
   */
  public static final BBacnetEngineeringUnits poundsMass = new BBacnetEngineeringUnits(POUNDS_MASS, POUNDS_MASS_NAME);
  /**
   * BBacnetEngineeringUnits constant for tons.
   */
  public static final BBacnetEngineeringUnits tons = new BBacnetEngineeringUnits(TONS, TONS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilogramsPerSecond.
   */
  public static final BBacnetEngineeringUnits kilogramsPerSecond = new BBacnetEngineeringUnits(KILOGRAMS_PER_SECOND, KILOGRAMS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilogramsPerMinute.
   */
  public static final BBacnetEngineeringUnits kilogramsPerMinute = new BBacnetEngineeringUnits(KILOGRAMS_PER_MINUTE, KILOGRAMS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilogramsPerHour.
   */
  public static final BBacnetEngineeringUnits kilogramsPerHour = new BBacnetEngineeringUnits(KILOGRAMS_PER_HOUR, KILOGRAMS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for poundsMassPerMinute.
   */
  public static final BBacnetEngineeringUnits poundsMassPerMinute = new BBacnetEngineeringUnits(POUNDS_MASS_PER_MINUTE, POUNDS_MASS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for poundsMassPerHour.
   */
  public static final BBacnetEngineeringUnits poundsMassPerHour = new BBacnetEngineeringUnits(POUNDS_MASS_PER_HOUR, POUNDS_MASS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for watts.
   */
  public static final BBacnetEngineeringUnits watts = new BBacnetEngineeringUnits(WATTS, WATTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilowatts.
   */
  public static final BBacnetEngineeringUnits kilowatts = new BBacnetEngineeringUnits(KILOWATTS, KILOWATTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for megawatts.
   */
  public static final BBacnetEngineeringUnits megawatts = new BBacnetEngineeringUnits(MEGAWATTS, MEGAWATTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for btusPerHour.
   */
  public static final BBacnetEngineeringUnits btusPerHour = new BBacnetEngineeringUnits(BTUS_PER_HOUR, BTUS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for horsepower.
   */
  public static final BBacnetEngineeringUnits horsepower = new BBacnetEngineeringUnits(HORSEPOWER, HORSEPOWER_NAME);
  /**
   * BBacnetEngineeringUnits constant for tonsRefrigeration.
   */
  public static final BBacnetEngineeringUnits tonsRefrigeration = new BBacnetEngineeringUnits(TONS_REFRIGERATION, TONS_REFRIGERATION_NAME);
  /**
   * BBacnetEngineeringUnits constant for pascals.
   */
  public static final BBacnetEngineeringUnits pascals = new BBacnetEngineeringUnits(PASCALS, PASCALS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilopascals.
   */
  public static final BBacnetEngineeringUnits kilopascals = new BBacnetEngineeringUnits(KILOPASCALS, KILOPASCALS_NAME);
  /**
   * BBacnetEngineeringUnits constant for bars.
   */
  public static final BBacnetEngineeringUnits bars = new BBacnetEngineeringUnits(BARS, BARS_NAME);
  /**
   * BBacnetEngineeringUnits constant for poundsForcePerSquareInch.
   */
  public static final BBacnetEngineeringUnits poundsForcePerSquareInch = new BBacnetEngineeringUnits(POUNDS_FORCE_PER_SQUARE_INCH, POUNDS_FORCE_PER_SQUARE_INCH_NAME);
  /**
   * BBacnetEngineeringUnits constant for centimetersOfWater.
   */
  public static final BBacnetEngineeringUnits centimetersOfWater = new BBacnetEngineeringUnits(CENTIMETERS_OF_WATER, CENTIMETERS_OF_WATER_NAME);
  /**
   * BBacnetEngineeringUnits constant for inchesOfWater.
   */
  public static final BBacnetEngineeringUnits inchesOfWater = new BBacnetEngineeringUnits(INCHES_OF_WATER, INCHES_OF_WATER_NAME);
  /**
   * BBacnetEngineeringUnits constant for millimetersOfMercury.
   */
  public static final BBacnetEngineeringUnits millimetersOfMercury = new BBacnetEngineeringUnits(MILLIMETERS_OF_MERCURY, MILLIMETERS_OF_MERCURY_NAME);
  /**
   * BBacnetEngineeringUnits constant for centimetersOfMercury.
   */
  public static final BBacnetEngineeringUnits centimetersOfMercury = new BBacnetEngineeringUnits(CENTIMETERS_OF_MERCURY, CENTIMETERS_OF_MERCURY_NAME);
  /**
   * BBacnetEngineeringUnits constant for inchesOfMercury.
   */
  public static final BBacnetEngineeringUnits inchesOfMercury = new BBacnetEngineeringUnits(INCHES_OF_MERCURY, INCHES_OF_MERCURY_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesCelsius.
   */
  public static final BBacnetEngineeringUnits degreesCelsius = new BBacnetEngineeringUnits(DEGREES_CELSIUS, DEGREES_CELSIUS_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesKelvin.
   */
  public static final BBacnetEngineeringUnits degreesKelvin = new BBacnetEngineeringUnits(DEGREES_KELVIN, DEGREES_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesFahrenheit.
   */
  public static final BBacnetEngineeringUnits degreesFahrenheit = new BBacnetEngineeringUnits(DEGREES_FAHRENHEIT, DEGREES_FAHRENHEIT_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreeDaysCelsius.
   */
  public static final BBacnetEngineeringUnits degreeDaysCelsius = new BBacnetEngineeringUnits(DEGREE_DAYS_CELSIUS, DEGREE_DAYS_CELSIUS_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreeDaysFahrenheit.
   */
  public static final BBacnetEngineeringUnits degreeDaysFahrenheit = new BBacnetEngineeringUnits(DEGREE_DAYS_FAHRENHEIT, DEGREE_DAYS_FAHRENHEIT_NAME);
  /**
   * BBacnetEngineeringUnits constant for years.
   */
  public static final BBacnetEngineeringUnits years = new BBacnetEngineeringUnits(YEARS, YEARS_NAME);
  /**
   * BBacnetEngineeringUnits constant for months.
   */
  public static final BBacnetEngineeringUnits months = new BBacnetEngineeringUnits(MONTHS, MONTHS_NAME);
  /**
   * BBacnetEngineeringUnits constant for weeks.
   */
  public static final BBacnetEngineeringUnits weeks = new BBacnetEngineeringUnits(WEEKS, WEEKS_NAME);
  /**
   * BBacnetEngineeringUnits constant for days.
   */
  public static final BBacnetEngineeringUnits days = new BBacnetEngineeringUnits(DAYS, DAYS_NAME);
  /**
   * BBacnetEngineeringUnits constant for hours.
   */
  public static final BBacnetEngineeringUnits hours = new BBacnetEngineeringUnits(HOURS, HOURS_NAME);
  /**
   * BBacnetEngineeringUnits constant for minutes.
   */
  public static final BBacnetEngineeringUnits minutes = new BBacnetEngineeringUnits(MINUTES, MINUTES_NAME);
  /**
   * BBacnetEngineeringUnits constant for seconds.
   */
  public static final BBacnetEngineeringUnits seconds = new BBacnetEngineeringUnits(SECONDS, SECONDS_NAME);
  /**
   * BBacnetEngineeringUnits constant for metersPerSecond.
   */
  public static final BBacnetEngineeringUnits metersPerSecond = new BBacnetEngineeringUnits(METERS_PER_SECOND, METERS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilometersPerHour.
   */
  public static final BBacnetEngineeringUnits kilometersPerHour = new BBacnetEngineeringUnits(KILOMETERS_PER_HOUR, KILOMETERS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for feetPerSecond.
   */
  public static final BBacnetEngineeringUnits feetPerSecond = new BBacnetEngineeringUnits(FEET_PER_SECOND, FEET_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for feetPerMinute.
   */
  public static final BBacnetEngineeringUnits feetPerMinute = new BBacnetEngineeringUnits(FEET_PER_MINUTE, FEET_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for milesPerHour.
   */
  public static final BBacnetEngineeringUnits milesPerHour = new BBacnetEngineeringUnits(MILES_PER_HOUR, MILES_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicFeet.
   */
  public static final BBacnetEngineeringUnits cubicFeet = new BBacnetEngineeringUnits(CUBIC_FEET, CUBIC_FEET_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicMeters.
   */
  public static final BBacnetEngineeringUnits cubicMeters = new BBacnetEngineeringUnits(CUBIC_METERS, CUBIC_METERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for imperialGallons.
   */
  public static final BBacnetEngineeringUnits imperialGallons = new BBacnetEngineeringUnits(IMPERIAL_GALLONS, IMPERIAL_GALLONS_NAME);
  /**
   * BBacnetEngineeringUnits constant for liters.
   */
  public static final BBacnetEngineeringUnits liters = new BBacnetEngineeringUnits(LITERS, LITERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for usGallons.
   */
  public static final BBacnetEngineeringUnits usGallons = new BBacnetEngineeringUnits(US_GALLONS, US_GALLONS_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicFeetPerMinute.
   */
  public static final BBacnetEngineeringUnits cubicFeetPerMinute = new BBacnetEngineeringUnits(CUBIC_FEET_PER_MINUTE, CUBIC_FEET_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicMetersPerSecond.
   */
  public static final BBacnetEngineeringUnits cubicMetersPerSecond = new BBacnetEngineeringUnits(CUBIC_METERS_PER_SECOND, CUBIC_METERS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for imperialGallonsPerMinute.
   */
  public static final BBacnetEngineeringUnits imperialGallonsPerMinute = new BBacnetEngineeringUnits(IMPERIAL_GALLONS_PER_MINUTE, IMPERIAL_GALLONS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for litersPerSecond.
   */
  public static final BBacnetEngineeringUnits litersPerSecond = new BBacnetEngineeringUnits(LITERS_PER_SECOND, LITERS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for litersPerMinute.
   */
  public static final BBacnetEngineeringUnits litersPerMinute = new BBacnetEngineeringUnits(LITERS_PER_MINUTE, LITERS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for usGallonsPerMinute.
   */
  public static final BBacnetEngineeringUnits usGallonsPerMinute = new BBacnetEngineeringUnits(US_GALLONS_PER_MINUTE, US_GALLONS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesAngular.
   */
  public static final BBacnetEngineeringUnits degreesAngular = new BBacnetEngineeringUnits(DEGREES_ANGULAR, DEGREES_ANGULAR_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesCelsiusPerHour.
   */
  public static final BBacnetEngineeringUnits degreesCelsiusPerHour = new BBacnetEngineeringUnits(DEGREES_CELSIUS_PER_HOUR, DEGREES_CELSIUS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesCelsiusPerMinute.
   */
  public static final BBacnetEngineeringUnits degreesCelsiusPerMinute = new BBacnetEngineeringUnits(DEGREES_CELSIUS_PER_MINUTE, DEGREES_CELSIUS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesFahrenheitPerHour.
   */
  public static final BBacnetEngineeringUnits degreesFahrenheitPerHour = new BBacnetEngineeringUnits(DEGREES_FAHRENHEIT_PER_HOUR, DEGREES_FAHRENHEIT_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesFahrenheitPerMinute.
   */
  public static final BBacnetEngineeringUnits degreesFahrenheitPerMinute = new BBacnetEngineeringUnits(DEGREES_FAHRENHEIT_PER_MINUTE, DEGREES_FAHRENHEIT_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for noUnits.
   */
  public static final BBacnetEngineeringUnits noUnits = new BBacnetEngineeringUnits(NO_UNITS, NO_UNITS_NAME);
  /**
   * BBacnetEngineeringUnits constant for partsPerMillion.
   */
  public static final BBacnetEngineeringUnits partsPerMillion = new BBacnetEngineeringUnits(PARTS_PER_MILLION, PARTS_PER_MILLION_NAME);
  /**
   * BBacnetEngineeringUnits constant for partsPerBillion.
   */
  public static final BBacnetEngineeringUnits partsPerBillion = new BBacnetEngineeringUnits(PARTS_PER_BILLION, PARTS_PER_BILLION_NAME);
  /**
   * BBacnetEngineeringUnits constant for percent.
   */
  public static final BBacnetEngineeringUnits percent = new BBacnetEngineeringUnits(PERCENT, PERCENT_NAME);
  /**
   * BBacnetEngineeringUnits constant for percentPerSecond.
   */
  public static final BBacnetEngineeringUnits percentPerSecond = new BBacnetEngineeringUnits(PERCENT_PER_SECOND, PERCENT_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for perMinute.
   */
  public static final BBacnetEngineeringUnits perMinute = new BBacnetEngineeringUnits(PER_MINUTE, PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for perSecond.
   */
  public static final BBacnetEngineeringUnits perSecond = new BBacnetEngineeringUnits(PER_SECOND, PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for psiPerDegreeFahrenheit.
   */
  public static final BBacnetEngineeringUnits psiPerDegreeFahrenheit = new BBacnetEngineeringUnits(PSI_PER_DEGREE_FAHRENHEIT, PSI_PER_DEGREE_FAHRENHEIT_NAME);
  /**
   * BBacnetEngineeringUnits constant for radians.
   */
  public static final BBacnetEngineeringUnits radians = new BBacnetEngineeringUnits(RADIANS, RADIANS_NAME);
  /**
   * BBacnetEngineeringUnits constant for revolutionsPerMinute.
   */
  public static final BBacnetEngineeringUnits revolutionsPerMinute = new BBacnetEngineeringUnits(REVOLUTIONS_PER_MINUTE, REVOLUTIONS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency1.
   */
  public static final BBacnetEngineeringUnits currency1 = new BBacnetEngineeringUnits(CURRENCY_1, CURRENCY_1_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency2.
   */
  public static final BBacnetEngineeringUnits currency2 = new BBacnetEngineeringUnits(CURRENCY_2, CURRENCY_2_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency3.
   */
  public static final BBacnetEngineeringUnits currency3 = new BBacnetEngineeringUnits(CURRENCY_3, CURRENCY_3_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency4.
   */
  public static final BBacnetEngineeringUnits currency4 = new BBacnetEngineeringUnits(CURRENCY_4, CURRENCY_4_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency5.
   */
  public static final BBacnetEngineeringUnits currency5 = new BBacnetEngineeringUnits(CURRENCY_5, CURRENCY_5_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency6.
   */
  public static final BBacnetEngineeringUnits currency6 = new BBacnetEngineeringUnits(CURRENCY_6, CURRENCY_6_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency7.
   */
  public static final BBacnetEngineeringUnits currency7 = new BBacnetEngineeringUnits(CURRENCY_7, CURRENCY_7_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency8.
   */
  public static final BBacnetEngineeringUnits currency8 = new BBacnetEngineeringUnits(CURRENCY_8, CURRENCY_8_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency9.
   */
  public static final BBacnetEngineeringUnits currency9 = new BBacnetEngineeringUnits(CURRENCY_9, CURRENCY_9_NAME);
  /**
   * BBacnetEngineeringUnits constant for currency10.
   */
  public static final BBacnetEngineeringUnits currency10 = new BBacnetEngineeringUnits(CURRENCY_10, CURRENCY_10_NAME);
  /**
   * BBacnetEngineeringUnits constant for squareInches.
   */
  public static final BBacnetEngineeringUnits squareInches = new BBacnetEngineeringUnits(SQUARE_INCHES, SQUARE_INCHES_NAME);
  /**
   * BBacnetEngineeringUnits constant for squareCentimeters.
   */
  public static final BBacnetEngineeringUnits squareCentimeters = new BBacnetEngineeringUnits(SQUARE_CENTIMETERS, SQUARE_CENTIMETERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for btusPerPound.
   */
  public static final BBacnetEngineeringUnits btusPerPound = new BBacnetEngineeringUnits(BTUS_PER_POUND, BTUS_PER_POUND_NAME);
  /**
   * BBacnetEngineeringUnits constant for centimeters.
   */
  public static final BBacnetEngineeringUnits centimeters = new BBacnetEngineeringUnits(CENTIMETERS, CENTIMETERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for poundsMassPerSecond.
   */
  public static final BBacnetEngineeringUnits poundsMassPerSecond = new BBacnetEngineeringUnits(POUNDS_MASS_PER_SECOND, POUNDS_MASS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for deltaDegreesFahrenheit.
   */
  public static final BBacnetEngineeringUnits deltaDegreesFahrenheit = new BBacnetEngineeringUnits(DELTA_DEGREES_FAHRENHEIT, DELTA_DEGREES_FAHRENHEIT_NAME);
  /**
   * BBacnetEngineeringUnits constant for deltaDegreesKelvin.
   */
  public static final BBacnetEngineeringUnits deltaDegreesKelvin = new BBacnetEngineeringUnits(DELTA_DEGREES_KELVIN, DELTA_DEGREES_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilohms.
   */
  public static final BBacnetEngineeringUnits kilohms = new BBacnetEngineeringUnits(KILOHMS, KILOHMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for megohms.
   */
  public static final BBacnetEngineeringUnits megohms = new BBacnetEngineeringUnits(MEGOHMS, MEGOHMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for millivolts.
   */
  public static final BBacnetEngineeringUnits millivolts = new BBacnetEngineeringUnits(MILLIVOLTS, MILLIVOLTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilojoulesPerKilogram.
   */
  public static final BBacnetEngineeringUnits kilojoulesPerKilogram = new BBacnetEngineeringUnits(KILOJOULES_PER_KILOGRAM, KILOJOULES_PER_KILOGRAM_NAME);
  /**
   * BBacnetEngineeringUnits constant for megajoules.
   */
  public static final BBacnetEngineeringUnits megajoules = new BBacnetEngineeringUnits(MEGAJOULES, MEGAJOULES_NAME);
  /**
   * BBacnetEngineeringUnits constant for joulesPerDegreeKelvin.
   */
  public static final BBacnetEngineeringUnits joulesPerDegreeKelvin = new BBacnetEngineeringUnits(JOULES_PER_DEGREE_KELVIN, JOULES_PER_DEGREE_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for joulesPerKilogramDegreeKelvin.
   */
  public static final BBacnetEngineeringUnits joulesPerKilogramDegreeKelvin = new BBacnetEngineeringUnits(JOULES_PER_KILOGRAM_DEGREE_KELVIN, JOULES_PER_KILOGRAM_DEGREE_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilohertz.
   */
  public static final BBacnetEngineeringUnits kilohertz = new BBacnetEngineeringUnits(KILOHERTZ, KILOHERTZ_NAME);
  /**
   * BBacnetEngineeringUnits constant for megahertz.
   */
  public static final BBacnetEngineeringUnits megahertz = new BBacnetEngineeringUnits(MEGAHERTZ, MEGAHERTZ_NAME);
  /**
   * BBacnetEngineeringUnits constant for perHour.
   */
  public static final BBacnetEngineeringUnits perHour = new BBacnetEngineeringUnits(PER_HOUR, PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for milliwatts.
   */
  public static final BBacnetEngineeringUnits milliwatts = new BBacnetEngineeringUnits(MILLIWATTS, MILLIWATTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for hectopascals.
   */
  public static final BBacnetEngineeringUnits hectopascals = new BBacnetEngineeringUnits(HECTOPASCALS, HECTOPASCALS_NAME);
  /**
   * BBacnetEngineeringUnits constant for millibars.
   */
  public static final BBacnetEngineeringUnits millibars = new BBacnetEngineeringUnits(MILLIBARS, MILLIBARS_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicMetersPerHour.
   */
  public static final BBacnetEngineeringUnits cubicMetersPerHour = new BBacnetEngineeringUnits(CUBIC_METERS_PER_HOUR, CUBIC_METERS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for litersPerHour.
   */
  public static final BBacnetEngineeringUnits litersPerHour = new BBacnetEngineeringUnits(LITERS_PER_HOUR, LITERS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilowattHoursPerSquareMeter.
   */
  public static final BBacnetEngineeringUnits kilowattHoursPerSquareMeter = new BBacnetEngineeringUnits(KILOWATT_HOURS_PER_SQUARE_METER, KILOWATT_HOURS_PER_SQUARE_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilowattHoursPerSquareFoot.
   */
  public static final BBacnetEngineeringUnits kilowattHoursPerSquareFoot = new BBacnetEngineeringUnits(KILOWATT_HOURS_PER_SQUARE_FOOT, KILOWATT_HOURS_PER_SQUARE_FOOT_NAME);
  /**
   * BBacnetEngineeringUnits constant for megajoulesPerSquareMeter.
   */
  public static final BBacnetEngineeringUnits megajoulesPerSquareMeter = new BBacnetEngineeringUnits(MEGAJOULES_PER_SQUARE_METER, MEGAJOULES_PER_SQUARE_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for megajoulesPerSquareFoot.
   */
  public static final BBacnetEngineeringUnits megajoulesPerSquareFoot = new BBacnetEngineeringUnits(MEGAJOULES_PER_SQUARE_FOOT, MEGAJOULES_PER_SQUARE_FOOT_NAME);
  /**
   * BBacnetEngineeringUnits constant for wattsPerSquareMeterDegreeKelvin.
   */
  public static final BBacnetEngineeringUnits wattsPerSquareMeterDegreeKelvin = new BBacnetEngineeringUnits(WATTS_PER_SQUARE_METER_DEGREE_KELVIN, WATTS_PER_SQUARE_METER_DEGREE_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicFeetPerSecond.
   */
  public static final BBacnetEngineeringUnits cubicFeetPerSecond = new BBacnetEngineeringUnits(CUBIC_FEET_PER_SECOND, CUBIC_FEET_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for percentObscurationPerFoot.
   */
  public static final BBacnetEngineeringUnits percentObscurationPerFoot = new BBacnetEngineeringUnits(PERCENT_OBSCURATION_PER_FOOT, PERCENT_OBSCURATION_PER_FOOT_NAME);
  /**
   * BBacnetEngineeringUnits constant for percentObscurationPerMeter.
   */
  public static final BBacnetEngineeringUnits percentObscurationPerMeter = new BBacnetEngineeringUnits(PERCENT_OBSCURATION_PER_METER, PERCENT_OBSCURATION_PER_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for milliohms.
   */
  public static final BBacnetEngineeringUnits milliohms = new BBacnetEngineeringUnits(MILLIOHMS, MILLIOHMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for megawattHours.
   */
  public static final BBacnetEngineeringUnits megawattHours = new BBacnetEngineeringUnits(MEGAWATT_HOURS, MEGAWATT_HOURS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kiloBtus.
   */
  public static final BBacnetEngineeringUnits kiloBtus = new BBacnetEngineeringUnits(KILO_BTUS, KILO_BTUS_NAME);
  /**
   * BBacnetEngineeringUnits constant for megaBtus.
   */
  public static final BBacnetEngineeringUnits megaBtus = new BBacnetEngineeringUnits(MEGA_BTUS, MEGA_BTUS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilojoulesPerKilogramDryAir.
   */
  public static final BBacnetEngineeringUnits kilojoulesPerKilogramDryAir = new BBacnetEngineeringUnits(KILOJOULES_PER_KILOGRAM_DRY_AIR, KILOJOULES_PER_KILOGRAM_DRY_AIR_NAME);
  /**
   * BBacnetEngineeringUnits constant for megajoulesPerKilogramDryAir.
   */
  public static final BBacnetEngineeringUnits megajoulesPerKilogramDryAir = new BBacnetEngineeringUnits(MEGAJOULES_PER_KILOGRAM_DRY_AIR, MEGAJOULES_PER_KILOGRAM_DRY_AIR_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilojoulesPerDegreeKelvin.
   */
  public static final BBacnetEngineeringUnits kilojoulesPerDegreeKelvin = new BBacnetEngineeringUnits(KILOJOULES_PER_DEGREE_KELVIN, KILOJOULES_PER_DEGREE_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for megajoulesPerDegreeKelvin.
   */
  public static final BBacnetEngineeringUnits megajoulesPerDegreeKelvin = new BBacnetEngineeringUnits(MEGAJOULES_PER_DEGREE_KELVIN, MEGAJOULES_PER_DEGREE_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for newton.
   */
  public static final BBacnetEngineeringUnits newton = new BBacnetEngineeringUnits(NEWTON, NEWTON_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerSecond.
   */
  public static final BBacnetEngineeringUnits gramsPerSecond = new BBacnetEngineeringUnits(GRAMS_PER_SECOND, GRAMS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerMinute.
   */
  public static final BBacnetEngineeringUnits gramsPerMinute = new BBacnetEngineeringUnits(GRAMS_PER_MINUTE, GRAMS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for tonsPerHour.
   */
  public static final BBacnetEngineeringUnits tonsPerHour = new BBacnetEngineeringUnits(TONS_PER_HOUR, TONS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for kiloBtusPerHour.
   */
  public static final BBacnetEngineeringUnits kiloBtusPerHour = new BBacnetEngineeringUnits(KILO_BTUS_PER_HOUR, KILO_BTUS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for hundredthsSeconds.
   */
  public static final BBacnetEngineeringUnits hundredthsSeconds = new BBacnetEngineeringUnits(HUNDREDTHS_SECONDS, HUNDREDTHS_SECONDS_NAME);
  /**
   * BBacnetEngineeringUnits constant for milliseconds.
   */
  public static final BBacnetEngineeringUnits milliseconds = new BBacnetEngineeringUnits(MILLISECONDS, MILLISECONDS_NAME);
  /**
   * BBacnetEngineeringUnits constant for newtonMeters.
   */
  public static final BBacnetEngineeringUnits newtonMeters = new BBacnetEngineeringUnits(NEWTON_METERS, NEWTON_METERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for millimetersPerSecond.
   */
  public static final BBacnetEngineeringUnits millimetersPerSecond = new BBacnetEngineeringUnits(MILLIMETERS_PER_SECOND, MILLIMETERS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for millimetersPerMinute.
   */
  public static final BBacnetEngineeringUnits millimetersPerMinute = new BBacnetEngineeringUnits(MILLIMETERS_PER_MINUTE, MILLIMETERS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for metersPerMinute.
   */
  public static final BBacnetEngineeringUnits metersPerMinute = new BBacnetEngineeringUnits(METERS_PER_MINUTE, METERS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for metersPerHour.
   */
  public static final BBacnetEngineeringUnits metersPerHour = new BBacnetEngineeringUnits(METERS_PER_HOUR, METERS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicMetersPerMinute.
   */
  public static final BBacnetEngineeringUnits cubicMetersPerMinute = new BBacnetEngineeringUnits(CUBIC_METERS_PER_MINUTE, CUBIC_METERS_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for metersPerSecondPerSecond.
   */
  public static final BBacnetEngineeringUnits metersPerSecondPerSecond = new BBacnetEngineeringUnits(METERS_PER_SECOND_PER_SECOND, METERS_PER_SECOND_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for amperesPerMeter.
   */
  public static final BBacnetEngineeringUnits amperesPerMeter = new BBacnetEngineeringUnits(AMPERES_PER_METER, AMPERES_PER_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for amperesPerSquareMeter.
   */
  public static final BBacnetEngineeringUnits amperesPerSquareMeter = new BBacnetEngineeringUnits(AMPERES_PER_SQUARE_METER, AMPERES_PER_SQUARE_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for ampereSquareMeters.
   */
  public static final BBacnetEngineeringUnits ampereSquareMeters = new BBacnetEngineeringUnits(AMPERE_SQUARE_METERS, AMPERE_SQUARE_METERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for farads.
   */
  public static final BBacnetEngineeringUnits farads = new BBacnetEngineeringUnits(FARADS, FARADS_NAME);
  /**
   * BBacnetEngineeringUnits constant for henrys.
   */
  public static final BBacnetEngineeringUnits henrys = new BBacnetEngineeringUnits(HENRYS, HENRYS_NAME);
  /**
   * BBacnetEngineeringUnits constant for ohmMeters.
   */
  public static final BBacnetEngineeringUnits ohmMeters = new BBacnetEngineeringUnits(OHM_METERS, OHM_METERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for siemens.
   */
  public static final BBacnetEngineeringUnits siemens = new BBacnetEngineeringUnits(SIEMENS, SIEMENS_NAME);
  /**
   * BBacnetEngineeringUnits constant for siemensPerMeter.
   */
  public static final BBacnetEngineeringUnits siemensPerMeter = new BBacnetEngineeringUnits(SIEMENS_PER_METER, SIEMENS_PER_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for teslas.
   */
  public static final BBacnetEngineeringUnits teslas = new BBacnetEngineeringUnits(TESLAS, TESLAS_NAME);
  /**
   * BBacnetEngineeringUnits constant for voltsPerDegreeKelvin.
   */
  public static final BBacnetEngineeringUnits voltsPerDegreeKelvin = new BBacnetEngineeringUnits(VOLTS_PER_DEGREE_KELVIN, VOLTS_PER_DEGREE_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for voltsPerMeter.
   */
  public static final BBacnetEngineeringUnits voltsPerMeter = new BBacnetEngineeringUnits(VOLTS_PER_METER, VOLTS_PER_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for webers.
   */
  public static final BBacnetEngineeringUnits webers = new BBacnetEngineeringUnits(WEBERS, WEBERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for candelas.
   */
  public static final BBacnetEngineeringUnits candelas = new BBacnetEngineeringUnits(CANDELAS, CANDELAS_NAME);
  /**
   * BBacnetEngineeringUnits constant for candelasPerSquareMeter.
   */
  public static final BBacnetEngineeringUnits candelasPerSquareMeter = new BBacnetEngineeringUnits(CANDELAS_PER_SQUARE_METER, CANDELAS_PER_SQUARE_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesKelvinPerHour.
   */
  public static final BBacnetEngineeringUnits degreesKelvinPerHour = new BBacnetEngineeringUnits(DEGREES_KELVIN_PER_HOUR, DEGREES_KELVIN_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for degreesKelvinPerMinute.
   */
  public static final BBacnetEngineeringUnits degreesKelvinPerMinute = new BBacnetEngineeringUnits(DEGREES_KELVIN_PER_MINUTE, DEGREES_KELVIN_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for jouleSeconds.
   */
  public static final BBacnetEngineeringUnits jouleSeconds = new BBacnetEngineeringUnits(JOULE_SECONDS, JOULE_SECONDS_NAME);
  /**
   * BBacnetEngineeringUnits constant for radiansPerSecond.
   */
  public static final BBacnetEngineeringUnits radiansPerSecond = new BBacnetEngineeringUnits(RADIANS_PER_SECOND, RADIANS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for squareMetersPerNewton.
   */
  public static final BBacnetEngineeringUnits squareMetersPerNewton = new BBacnetEngineeringUnits(SQUARE_METERS_PER_NEWTON, SQUARE_METERS_PER_NEWTON_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilogramsPerCubicMeter.
   */
  public static final BBacnetEngineeringUnits kilogramsPerCubicMeter = new BBacnetEngineeringUnits(KILOGRAMS_PER_CUBIC_METER, KILOGRAMS_PER_CUBIC_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for newtonSeconds.
   */
  public static final BBacnetEngineeringUnits newtonSeconds = new BBacnetEngineeringUnits(NEWTON_SECONDS, NEWTON_SECONDS_NAME);
  /**
   * BBacnetEngineeringUnits constant for newtonsPerMeter.
   */
  public static final BBacnetEngineeringUnits newtonsPerMeter = new BBacnetEngineeringUnits(NEWTONS_PER_METER, NEWTONS_PER_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for wattsPerMeterPerDegreeKelvin.
   */
  public static final BBacnetEngineeringUnits wattsPerMeterPerDegreeKelvin = new BBacnetEngineeringUnits(WATTS_PER_METER_PER_DEGREE_KELVIN, WATTS_PER_METER_PER_DEGREE_KELVIN_NAME);
  /**
   * BBacnetEngineeringUnits constant for microSiemens.
   */
  public static final BBacnetEngineeringUnits microSiemens = new BBacnetEngineeringUnits(MICROSIEMENS, MICROSIEMENS_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicFeetPerHour.
   */
  public static final BBacnetEngineeringUnits cubicFeetPerHour = new BBacnetEngineeringUnits(CUBIC_FEET_PER_HOUR, CUBIC_FEET_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for usGallonsPerHour.
   */
  public static final BBacnetEngineeringUnits usGallonsPerHour = new BBacnetEngineeringUnits(US_GALLONS_PER_HOUR, US_GALLONS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilometers.
   */
  public static final BBacnetEngineeringUnits kilometers = new BBacnetEngineeringUnits(KILOMETERS, KILOMETERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for micrometers.
   */
  public static final BBacnetEngineeringUnits micrometers = new BBacnetEngineeringUnits(MICROMETERS, KILOMETERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for grams.
   */
  public static final BBacnetEngineeringUnits grams = new BBacnetEngineeringUnits(GRAMS, GRAMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for milligrams.
   */
  public static final BBacnetEngineeringUnits milligrams = new BBacnetEngineeringUnits(MILLIGRAMS, MILLIGRAMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for milliliters.
   */
  public static final BBacnetEngineeringUnits milliliters = new BBacnetEngineeringUnits(MILLILITERS, MILLILITERS_NAME);
  /**
   * BBacnetEngineeringUnits constant for millilitersPerSecond.
   */
  public static final BBacnetEngineeringUnits millilitersPerSecond = new BBacnetEngineeringUnits(MILLILITERS_PER_SECOND, MILLILITERS_PER_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for decibels.
   */
  public static final BBacnetEngineeringUnits decibels = new BBacnetEngineeringUnits(DECIBELS, DECIBELS_NAME);
  /**
   * BBacnetEngineeringUnits constant for decibelsMillivolt.
   */
  public static final BBacnetEngineeringUnits decibelsMillivolt = new BBacnetEngineeringUnits(DECIBELS_MILLIVOLT, DECIBELS_MILLIVOLT_NAME);
  /**
   * BBacnetEngineeringUnits constant for decibelsVolt.
   */
  public static final BBacnetEngineeringUnits decibelsVolt = new BBacnetEngineeringUnits(DECIBELS_VOLT, DECIBELS_VOLT_NAME);
  /**
   * BBacnetEngineeringUnits constant for millisiemens.
   */
  public static final BBacnetEngineeringUnits millisiemens = new BBacnetEngineeringUnits(MILLISIEMENS, MILLISIEMENS_NAME);
  /**
   * BBacnetEngineeringUnits constant for wattHourReactive.
   */
  public static final BBacnetEngineeringUnits wattHourReactive = new BBacnetEngineeringUnits(WATT_HOUR_REACTIVE, WATT_HOUR_REACTIVE_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilowattHoursReactive.
   */
  public static final BBacnetEngineeringUnits kilowattHoursReactive = new BBacnetEngineeringUnits(KILOWATT_HOURS_REACTIVE, KILOWATT_HOURS_REACTIVE_NAME);
  /**
   * BBacnetEngineeringUnits constant for megawattHoursReactive.
   */
  public static final BBacnetEngineeringUnits megawattHoursReactive = new BBacnetEngineeringUnits(MEGAWATT_HOURS_REACTIVE, MEGAWATT_HOURS_REACTIVE_NAME);
  /**
   * BBacnetEngineeringUnits constant for millilitersOfWater.
   */
  public static final BBacnetEngineeringUnits millilitersOfWater = new BBacnetEngineeringUnits(MILLILITERS_OF_WATER, MILLILITERS_OF_WATER_NAME);
  /**
   * BBacnetEngineeringUnits constant for perMile.
   */
  public static final BBacnetEngineeringUnits perMile = new BBacnetEngineeringUnits(PER_MILE, PER_MILE_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerGram.
   */
  public static final BBacnetEngineeringUnits gramsPerGram = new BBacnetEngineeringUnits(GRAMS_PER_GRAM, GRAMS_PER_GRAM_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilogramsPerKilogram.
   */
  public static final BBacnetEngineeringUnits kilogramsPerKilogram = new BBacnetEngineeringUnits(KILOGRAMS_PER_KILOGRAM, KILOGRAMS_PER_KILOGRAM_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerKilogram.
   */
  public static final BBacnetEngineeringUnits gramsPerKilogram = new BBacnetEngineeringUnits(GRAMS_PER_KILOGRAM, GRAMS_PER_KILOGRAM_NAME);
  /**
   * BBacnetEngineeringUnits constant for milligramsPerGram.
   */
  public static final BBacnetEngineeringUnits milligramsPerGram = new BBacnetEngineeringUnits(MILLIGRAMS_PER_GRAM, MILLIGRAMS_PER_GRAM_NAME);
  /**
   * BBacnetEngineeringUnits constant for milligramsPerKilogram.
   */
  public static final BBacnetEngineeringUnits milligramsPerKilogram = new BBacnetEngineeringUnits(MILLIGRAMS_PER_KILOGRAM, MILLIGRAMS_PER_KILOGRAM_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerMilliliter.
   */
  public static final BBacnetEngineeringUnits gramsPerMilliliter = new BBacnetEngineeringUnits(GRAMS_PER_MILLILITER, GRAMS_PER_MILLILITER_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerLiter.
   */
  public static final BBacnetEngineeringUnits gramsPerLiter = new BBacnetEngineeringUnits(GRAMS_PER_LITER, GRAMS_PER_LITER_NAME);
  /**
   * BBacnetEngineeringUnits constant for milligramsPerLiter.
   */
  public static final BBacnetEngineeringUnits milligramsPerLiter = new BBacnetEngineeringUnits(MILLIGRAMS_PER_LITER, MILLIGRAMS_PER_LITER_NAME);
  /**
   * BBacnetEngineeringUnits constant for microgramsPerLiter.
   */
  public static final BBacnetEngineeringUnits microgramsPerLiter = new BBacnetEngineeringUnits(MICROGRAMS_PER_LITER, MICROGRAMS_PER_LITER_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerCubicMeter.
   */
  public static final BBacnetEngineeringUnits gramsPerCubicMeter = new BBacnetEngineeringUnits(GRAMS_PER_CUBIC_METER, GRAMS_PER_CUBIC_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for milligramsPerCubicMeter.
   */
  public static final BBacnetEngineeringUnits milligramsPerCubicMeter = new BBacnetEngineeringUnits(MILLIGRAMS_PER_CUBIC_METER, MILLIGRAMS_PER_CUBIC_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for microgramsPerCubicMeter.
   */
  public static final BBacnetEngineeringUnits microgramsPerCubicMeter = new BBacnetEngineeringUnits(MICROGRAMS_PER_CUBIC_METER, MICROGRAMS_PER_CUBIC_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for nanogramsPerCubicMeter.
   */
  public static final BBacnetEngineeringUnits nanogramsPerCubicMeter = new BBacnetEngineeringUnits(NANOGRAMS_PER_CUBIC_METER, NANOGRAMS_PER_CUBIC_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerCubicCentimeter.
   */
  public static final BBacnetEngineeringUnits gramsPerCubicCentimeter = new BBacnetEngineeringUnits(GRAMS_PER_CUBIC_CENTIMETER, GRAMS_PER_CUBIC_CENTIMETER_NAME);
  /**
   * BBacnetEngineeringUnits constant for becquerels.
   */
  public static final BBacnetEngineeringUnits becquerels = new BBacnetEngineeringUnits(BECQUERELS, BECQUERELS_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilobecquerels.
   */
  public static final BBacnetEngineeringUnits kilobecquerels = new BBacnetEngineeringUnits(KILOBECQUERELS, KILOBECQUERELS_NAME);
  /**
   * BBacnetEngineeringUnits constant for megabecquerels.
   */
  public static final BBacnetEngineeringUnits megabecquerels = new BBacnetEngineeringUnits(MEGABECQUERELS, MEGABECQUERELS_NAME);
  /**
   * BBacnetEngineeringUnits constant for gray.
   */
  public static final BBacnetEngineeringUnits gray = new BBacnetEngineeringUnits(GRAY, GRAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for milligray.
   */
  public static final BBacnetEngineeringUnits milligray = new BBacnetEngineeringUnits(MILLIGRAY, MILLIGRAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for microgray.
   */
  public static final BBacnetEngineeringUnits microgray = new BBacnetEngineeringUnits(MICROGRAY, MICROGRAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for sieverts.
   */
  public static final BBacnetEngineeringUnits sieverts = new BBacnetEngineeringUnits(SIEVERTS, SIEVERTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for millisieverts.
   */
  public static final BBacnetEngineeringUnits millisieverts = new BBacnetEngineeringUnits(MILLISIEVERTS, MILLISIEVERTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for microsieverts.
   */
  public static final BBacnetEngineeringUnits microsieverts = new BBacnetEngineeringUnits(MICROSIEVERTS, MICROSIEVERTS_NAME);
  /**
   * BBacnetEngineeringUnits constant for microsievertsPerHour.
   */
  public static final BBacnetEngineeringUnits microsievertsPerHour = new BBacnetEngineeringUnits(MICROSIEVERTS_PER_HOUR, MICROSIEVERTS_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for decibelsA.
   */
  public static final BBacnetEngineeringUnits decibelsA = new BBacnetEngineeringUnits(DECIBELS_A, DECIBELS_A_NAME);
  /**
   * BBacnetEngineeringUnits constant for nephelometricTurbidityUnit.
   */
  public static final BBacnetEngineeringUnits nephelometricTurbidityUnit = new BBacnetEngineeringUnits(NEPHELOMETRIC_TURBIDITY_UNIT, NEPHELOMETRIC_TURBIDITY_UNIT_NAME);
  /**
   * BBacnetEngineeringUnits constant for ph.
   */
  public static final BBacnetEngineeringUnits ph = new BBacnetEngineeringUnits(PH, PH_NAME);
  /**
   * BBacnetEngineeringUnits constant for gramsPerSquareMeter.
   */
  public static final BBacnetEngineeringUnits gramsPerSquareMeter = new BBacnetEngineeringUnits(GRAMS_PER_SQUARE_METER, GRAMS_PER_SQUARE_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for minutesPerDegreeKelvin.
   */
  public static final BBacnetEngineeringUnits minutesPerDegreeKelvin = new BBacnetEngineeringUnits(MINUTES_PER_DEGREE_KELVIN, MINUTES_PER_DEGREE_KELVIN_NAME);

  /**
   * BBacnetEngineeringUnits constant for ohmMeterSquaredPerMeter.
   */
  public static final BBacnetEngineeringUnits ohmMeterSquaredPerMeter = new BBacnetEngineeringUnits(OHM_METER_SQUARED_PER_METER, OHM_METER_SQUARED_PER_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for ampereSeconds.
   */
  public static final BBacnetEngineeringUnits ampereSeconds = new BBacnetEngineeringUnits(AMPERE_SECONDS, AMPERE_SECONDS_NAME);
  /**
   * BBacnetEngineeringUnits constant for voltAmpereHour.
   */
  public static final BBacnetEngineeringUnits voltAmpereHour = new BBacnetEngineeringUnits(VOLT_AMPERE_HOUR, VOLT_AMPERE_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilovoltAmpereHour.
   */
  public static final BBacnetEngineeringUnits kilovoltAmpereHour = new BBacnetEngineeringUnits(KILOVOLT_AMPERE_HOUR, KILOVOLT_AMPERE_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for megavoltAmpereHour.
   */
  public static final BBacnetEngineeringUnits megavoltAmpereHour = new BBacnetEngineeringUnits(MEGAVOLT_AMPERE_HOUR, MEGAVOLT_AMPERE_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for voltAmpereReactiveHour.
   */
  public static final BBacnetEngineeringUnits voltAmpereReactiveHour = new BBacnetEngineeringUnits(VOLT_AMPERE_REACTIVE_HOUR, VOLT_AMPERE_REACTIVE_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for kilovoltAmpereReactiveHour.
   */
  public static final BBacnetEngineeringUnits kilovoltAmpereReactiveHour = new BBacnetEngineeringUnits(KILOVOLT_AMPERE_REACTIVE_HOUR, KILOVOLT_AMPERE_REACTIVE_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for megavoltAmpereReactiveHour.
   */
  public static final BBacnetEngineeringUnits megavoltAmpereReactiveHour = new BBacnetEngineeringUnits(MEGAVOLT_AMPERE_REACTIVE_HOUR, MEGAVOLT_AMPERE_REACTIVE_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for voltSquaredHours.
   */
  public static final BBacnetEngineeringUnits voltSquaredHours = new BBacnetEngineeringUnits(VOLT_SQUARED_HOURS, VOLT_SQUARED_HOURS_NAME);
  /**
   * BBacnetEngineeringUnits constant for ampereSquaredHours.
   */
  public static final BBacnetEngineeringUnits ampereSquaredHours = new BBacnetEngineeringUnits(AMPERE_SQUARED_HOURS, AMPERE_SQUARED_HOURS_NAME);
  /**
   * BBacnetEngineeringUnits constant for joulesPerHour.
   */
  public static final BBacnetEngineeringUnits joulesPerHour = new BBacnetEngineeringUnits(JOULES_PER_HOUR, JOULES_PER_HOUR_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicFeetPerDay.
   */
  public static final BBacnetEngineeringUnits cubicFeetPerDay = new BBacnetEngineeringUnits(CUBIC_FEET_PER_DAY, CUBIC_FEET_PER_DAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for cubicMetersPerDay.
   */
  public static final BBacnetEngineeringUnits cubicMetersPerDay = new BBacnetEngineeringUnits(CUBIC_METERS_PER_DAY, CUBIC_METERS_PER_DAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for wattHourPerCubicMeter.
   */
  public static final BBacnetEngineeringUnits wattHourPerCubicMeter = new BBacnetEngineeringUnits(WATT_HOUR_PER_CUBIC_METER, WATT_HOUR_PER_CUBIC_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for joulesPerCubicMeter.
   */
  public static final BBacnetEngineeringUnits joulesPerCubicMeter = new BBacnetEngineeringUnits(JOULES_PER_CUBIC_METER, JOULES_PER_CUBIC_METER_NAME);
  /**
   * BBacnetEngineeringUnits constant for molesPercent.
   */
  public static final BBacnetEngineeringUnits molesPercent = new BBacnetEngineeringUnits(MOLES_PERCENT, MOLES_PERCENT_NAME);
  /**
   * BBacnetEngineeringUnits constant for pascalSecond.
   */
  public static final BBacnetEngineeringUnits pascalSecond = new BBacnetEngineeringUnits(PASCAL_SECOND, PASCAL_SECOND_NAME);
  /**
   * BBacnetEngineeringUnits constant for millionStandardCubicFeetPerMinute.
   */
  public static final BBacnetEngineeringUnits millionStandardCubicFeetPerMinute = new BBacnetEngineeringUnits(MILLION_STANDARD_CUBIC_FEET_PER_MINUTE, MILLION_STANDARD_CUBIC_FEET_PER_MINUTE_NAME);
  /**
   * BBacnetEngineeringUnits constant for standardCubicFeetPerDay.
   */
  public static final BBacnetEngineeringUnits standardCubicFeetPerDay = new BBacnetEngineeringUnits(STANDARD_CUBIC_FEET_PER_DAY, STANDARD_CUBIC_FEET_PER_DAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for millionStandardCubicFeetPerDay.
   */
  public static final BBacnetEngineeringUnits millionStandardCubicFeetPerDay = new BBacnetEngineeringUnits(MILLION_STANDARD_CUBIC_FEET_PER_DAY, MILLION_STANDARD_CUBIC_FEET_PER_DAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for thousandCubicFeetPerDay.
   */
  public static final BBacnetEngineeringUnits thousandCubicFeetPerDay = new BBacnetEngineeringUnits(THOUSAND_CUBIC_FEET_PER_DAY, THOUSAND_CUBIC_FEET_PER_DAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for thousandStandardCubicFeetPerDay.
   */
  public static final BBacnetEngineeringUnits thousandStandardCubicFeetPerDay = new BBacnetEngineeringUnits(THOUSAND_STANDARD_CUBIC_FEET_PER_DAY, THOUSAND_STANDARD_CUBIC_FEET_PER_DAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for poundsMassPerDay.
   */
  public static final BBacnetEngineeringUnits poundsMassPerDay = new BBacnetEngineeringUnits(POUNDS_MASS_PER_DAY, POUNDS_MASS_PER_DAY_NAME);
  /**
   * BBacnetEngineeringUnits constant for millirems.
   */
  public static final BBacnetEngineeringUnits millirems = new BBacnetEngineeringUnits(MILLIREMS, MILLIREMS_NAME);
  /**
   * BBacnetEngineeringUnits constant for milliremsPerHour.
   */
  public static final BBacnetEngineeringUnits milliremsPerHour = new BBacnetEngineeringUnits(MILLIREMS_PER_HOUR, MILLIREMS_PER_HOUR_NAME);

  /** Factory method with ordinal. */
  public static BBacnetEngineeringUnits make(int ordinal)
  {
    return (BBacnetEngineeringUnits)squareMeters.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BBacnetEngineeringUnits make(String tag)
  {
    return (BBacnetEngineeringUnits)squareMeters.getRange().get(tag);
  }

  /*  *//** Private constructor. *//*
  private BBacnetEngineeringUnits(int ordinal)
  {
    super(ordinal);
  }*/

  public static final BBacnetEngineeringUnits DEFAULT = squareMeters;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetEngineeringUnits.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Factory method with BUnit.
   */
  public static BBacnetEngineeringUnits make(BUnit niagaraUnits)
  {
    if (niagaraUnits == null) return noUnits;
    BBacnetEngineeringUnits bacnetUnits = map.get(niagaraUnits);
    if (bacnetUnits == null) return noUnits;
    return bacnetUnits;
  }

  /** Private constructor.
   private BBacnetEngineeringUnits(int ordinal)
   {
   super(ordinal);
   } */

  /**
   * Private constructor.
   */
  private BBacnetEngineeringUnits(int ordinal, String niagaraName)
  {
    super(ordinal);
    this.niagaraName = niagaraName;
  }


////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  public static final int MAX_ASHRAE_ID = 254;
  public static final int MAX_RESERVED_ID = 255;
  public static final int MAX_ID = 65535;
  public static final int ASHRAE_RESERVED_RANGE_MIN = 47808;
  public static final int ASHRAE_RESERVED_RANGE_MAX = 49999;

////////////////////////////////////////////////////////////////
// Static methods
////////////////////////////////////////////////////////////////

  /**
   * Create a string tag for the given ordinal.
   *
   * @return the tag for the ordinal, if it is known,
   * or construct one using standard prefixes.
   */
  public static String tag(int id)
  {
    if (DEFAULT.getRange().isOrdinal(id))
      return DEFAULT.getRange().getTag(id);
    if (isAshrae(id))
      return ASHRAE_PREFIX + id;
    if (isProprietary(id))
      return PROPRIETARY_PREFIX + id;
    throw new InvalidEnumException(id);
  }

  /**
   * Get the ordinal for the given tag.
   *
   * @return the ordinal for the tag, if it is known,
   * or generate one if the tag uses standard prefixes.
   */
  public static int ordinal(String tag)
  {
    try
    {
      return DEFAULT.getRange().tagToOrdinal(tag);
    }
    catch (InvalidEnumException e)
    {
      if (tag.startsWith(ASHRAE_PREFIX))
        return Integer.parseInt(tag.substring(ASHRAE_PREFIX_LENGTH));
      if (tag.startsWith(PROPRIETARY_PREFIX))
        return Integer.parseInt(tag.substring(PROPRIETARY_PREFIX_LENGTH));
      throw e;
    }
  }

  /**
   * Is this a proprietary extension?
   *
   * @return true if this is a proprietary extension.
   */
  public static boolean isProprietary(int id)
  {
    return (id > MAX_RESERVED_ID) && (id <= MAX_ID) && !isAshraeExtended(id);
  }

  /**
   * Is this an ASHRAE extension?
   *
   * @return true if this is an ASHRAE extension.
   */
  public static boolean isAshrae(int id)
  {
    return ((id > MAX_ASHRAE_ID) && (id <= MAX_RESERVED_ID));
  }

  /**
   * Is this an ASHRAE Extended Reserved id.
   *
   * @return true if this is an ASHRAE Extended Reversed.
   */
  public static boolean isAshraeExtended(int id)
  {
    return ((id >= ASHRAE_RESERVED_RANGE_MIN) && (id <= ASHRAE_RESERVED_RANGE_MAX));
  }
  /**
   * Is this id valid for this enumeration?
   *
   * @return true if this id is within the allowed range.
   */
  public static boolean isValid(int id)
  {
    return id <= MAX_ID;
  }

  /**
   * Is this id part of the predefined (fixed) range?
   *
   * @return true if this id is in the fixed range.
   */
  public static boolean isFixed(int id)
  {
    return id <= MAX_ASHRAE_ID;
  }


////////////////////////////////////////////////////////////////
// Units conversion
////////////////////////////////////////////////////////////////

  /**
   * Get the corresponding Niagara <code>BUnit</code> for this units type.
   */
  public BUnit getNiagaraUnits()
  {
    if (niagaraName == null) return null;
    try
    {
      return BUnit.getUnit(niagaraName);
    }
    catch (UnitException e)
    {
      logger.warning("Unknown Unit: " + niagaraName);
      return null;
    }
  }

  /**
   * @return String representation of this BEnum.
   */
  public String toString(Context context)
  {
    if ((context != null) && context.equals(BacnetConst.facetsContext))
      return getTag();
    return getDisplayTag(context);
  }

  /**
   * Get the corresponding Niagara <code>BUnit</code> for the
   * units type with the given ordinal.
   */
  public static BUnit getNiagaraUnits(int ordinal)
  {
    if (isFixed(ordinal))
      return make(ordinal).getNiagaraUnits();
    return null;
  }


////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private String niagaraName;
  private static HashMap<BUnit, BBacnetEngineeringUnits> map = new HashMap<>();
  private static final Logger logger = Logger.getLogger("bacnet.enums");


  static
  {
    // Make sure the unit database is loaded
    UnitDatabase.getDefault()/*.dump()*/;

    // Fill the map of Niagara units to Bacnet units
/* enumerations 105 through 114 are "Currency1" through "Currency10" and are not included... */

/*   0 */
    map.put(BUnit.getUnit(SQUARE_METERS_NAME), squareMeters);
/*   1 */
    map.put(BUnit.getUnit(SQUARE_FEET_NAME), squareFeet);
/*   2 */
    map.put(BUnit.getUnit(MILLIAMPERES_NAME), milliamperes);
/*   3 */
    map.put(BUnit.getUnit(AMPERES_NAME), amperes);
/*   4 */
    map.put(BUnit.getUnit(OHMS_NAME), ohms);
/*   5 */
    map.put(BUnit.getUnit(VOLTS_NAME), volts);
/*   6 */
    map.put(BUnit.getUnit(KILOVOLTS_NAME), kilovolts);
/*   7 */
    map.put(BUnit.getUnit(MEGAVOLTS_NAME), megavolts);
/*   8 */
    map.put(BUnit.getUnit(VOLT_AMPERES_NAME), voltAmperes);
/*   9 */
    map.put(BUnit.getUnit(KILOVOLT_AMPERES_NAME), kilovoltAmperes);
/*  10 */
    map.put(BUnit.getUnit(MEGAVOLT_AMPERES_NAME), megavoltAmperes);
/*  11 */
    map.put(BUnit.getUnit(VOLT_AMPERES_REACTIVE_NAME), voltAmperesReactive);
/*  12 */
    map.put(BUnit.getUnit(KILOVOLT_AMPERES_REACTIVE_NAME), kilovoltAmperesReactive);
/*  13 */
    map.put(BUnit.getUnit(MEGAVOLT_AMPERES_REACTIVE_NAME), megavoltAmperesReactive);
/*  14 */
    map.put(BUnit.getUnit(DEGREES_PHASE_NAME), degreesPhase);
/*  15 */
    map.put(BUnit.getUnit(POWER_FACTOR_NAME), powerFactor);
/*  16 */
    map.put(BUnit.getUnit(JOULES_NAME), joules);
/*  17 */
    map.put(BUnit.getUnit(KILOJOULES_NAME), kilojoules);
/*  18 */
    map.put(BUnit.getUnit(WATT_HOURS_NAME), wattHours);
/*  19 */
    map.put(BUnit.getUnit(KILOWATT_HOURS_NAME), kilowattHours);
/*  20 */
    map.put(BUnit.getUnit(BTUS_NAME), btus);
/*  21 */
    map.put(BUnit.getUnit(THERMS_NAME), therms);
/*  22 */
    map.put(BUnit.getUnit(TON_HOURS_NAME), tonHours);
/*  23 */
    map.put(BUnit.getUnit(JOULES_PER_KILOGRAM_DRY_AIR_NAME), joulesPerKilogramDryAir);
/*  24 */
    map.put(BUnit.getUnit(BTUS_PER_POUND_DRY_AIR_NAME), btusPerPoundDryAir);
/*  25 */
    map.put(BUnit.getUnit(CYCLES_PER_HOUR_NAME), cyclesPerHour);
/*  26 */
    map.put(BUnit.getUnit(CYCLES_PER_MINUTE_NAME), cyclesPerMinute);
/*  27 */
    map.put(BUnit.getUnit(HERTZ_NAME), hertz);
/*  28 */
    map.put(BUnit.getUnit(GRAMS_OF_WATER_PER_KILOGRAM_DRY_AIR_NAME), gramsOfWaterPerKilogramDryAir);
/*  29 */
    map.put(BUnit.getUnit(PERCENT_RELATIVE_HUMIDITY_NAME), percentRelativeHumidity);
/*  30 */
    map.put(BUnit.getUnit(MILLIMETERS_NAME), millimeters);
/*  31 */
    map.put(BUnit.getUnit(METERS_NAME), meters);
/*  32 */
    map.put(BUnit.getUnit(INCHES_NAME), inches);
/*  33 */
    map.put(BUnit.getUnit(FEET_NAME), feet);
/*  34 */
    map.put(BUnit.getUnit(WATTS_PER_SQUARE_FOOT_NAME), wattsPerSquareFoot);
/*  35 */
    map.put(BUnit.getUnit(WATTS_PER_SQUARE_METER_NAME), wattsPerSquareMeter);
/*  36 */
    map.put(BUnit.getUnit(LUMENS_NAME), lumens);
/*  37 */
    map.put(BUnit.getUnit(LUXES_NAME), luxes);
/*  38 */
    map.put(BUnit.getUnit(FOOT_CANDLES_NAME), footCandles);
/*  39 */
    map.put(BUnit.getUnit(KILOGRAMS_NAME), kilograms);
/*  40 */
    map.put(BUnit.getUnit(POUNDS_MASS_NAME), poundsMass);
/*  41 */
    map.put(BUnit.getUnit(TONS_NAME), tons); //short_ton?
/*  42 */
    map.put(BUnit.getUnit(KILOGRAMS_PER_SECOND_NAME), kilogramsPerSecond);
/*  43 */
    map.put(BUnit.getUnit(KILOGRAMS_PER_MINUTE_NAME), kilogramsPerMinute);
/*  44 */
    map.put(BUnit.getUnit(KILOGRAMS_PER_HOUR_NAME), kilogramsPerHour);
/*  45 */
    map.put(BUnit.getUnit(POUNDS_MASS_PER_MINUTE_NAME), poundsMassPerMinute);
/*  46 */
    map.put(BUnit.getUnit(POUNDS_MASS_PER_HOUR_NAME), poundsMassPerHour);
/*  47 */
    map.put(BUnit.getUnit(WATTS_NAME), watts);
/*  48 */
    map.put(BUnit.getUnit(KILOWATTS_NAME), kilowatts);
/*  49 */
    map.put(BUnit.getUnit(MEGAWATTS_NAME), megawatts);
/*  50 */
    map.put(BUnit.getUnit(BTUS_PER_HOUR_NAME), btusPerHour);
/*  51 */
    map.put(BUnit.getUnit(HORSEPOWER_NAME), horsepower);
/*  52 */
    map.put(BUnit.getUnit(TONS_REFRIGERATION_NAME), tonsRefrigeration);
/*  53 */
    map.put(BUnit.getUnit(PASCALS_NAME), pascals);
/*  54 */
    map.put(BUnit.getUnit(KILOPASCALS_NAME), kilopascals);
/*  55 */
    map.put(BUnit.getUnit(BARS_NAME), bars);
/*  56 */
    map.put(BUnit.getUnit(POUNDS_FORCE_PER_SQUARE_INCH_NAME), poundsForcePerSquareInch);
/*  57 */
    map.put(BUnit.getUnit(CENTIMETERS_OF_WATER_NAME), centimetersOfWater);
/*  58 */
    map.put(BUnit.getUnit(INCHES_OF_WATER_NAME), inchesOfWater);
/*  59 */
    map.put(BUnit.getUnit(MILLIMETERS_OF_MERCURY_NAME), millimetersOfMercury);
/*  60 */
    map.put(BUnit.getUnit(CENTIMETERS_OF_MERCURY_NAME), centimetersOfMercury);
/*  61 */
    map.put(BUnit.getUnit(INCHES_OF_MERCURY_NAME), inchesOfMercury);
/*  62 */
    map.put(BUnit.getUnit(DEGREES_CELSIUS_NAME), degreesCelsius);
/*  63 */
    map.put(BUnit.getUnit(DEGREES_KELVIN_NAME), degreesKelvin);
/*  64 */
    map.put(BUnit.getUnit(DEGREES_FAHRENHEIT_NAME), degreesFahrenheit);
/*  65 */
    map.put(BUnit.getUnit(DEGREE_DAYS_CELSIUS_NAME), degreeDaysCelsius);
/*  66 */
    map.put(BUnit.getUnit(DEGREE_DAYS_FAHRENHEIT_NAME), degreeDaysFahrenheit);
/*  67 */
    map.put(BUnit.getUnit(YEARS_NAME), years);
/*  68 */
    map.put(BUnit.getUnit(MONTHS_NAME), months);
/*  69 */
    map.put(BUnit.getUnit(WEEKS_NAME), weeks);
/*  70 */
    map.put(BUnit.getUnit(DAYS_NAME), days);
/*  71 */
    map.put(BUnit.getUnit(HOURS_NAME), hours);
/*  72 */
    map.put(BUnit.getUnit(MINUTES_NAME), minutes);
/*  73 */
    map.put(BUnit.getUnit(SECONDS_NAME), seconds);
/*  74 */
    map.put(BUnit.getUnit(METERS_PER_SECOND_NAME), metersPerSecond);
/*  75 */
    map.put(BUnit.getUnit(KILOMETERS_PER_HOUR_NAME), kilometersPerHour);
/*  76 */
    map.put(BUnit.getUnit(FEET_PER_SECOND_NAME), feetPerSecond);
/*  77 */
    map.put(BUnit.getUnit(FEET_PER_MINUTE_NAME), feetPerMinute);
/*  78 */
    map.put(BUnit.getUnit(MILES_PER_HOUR_NAME), milesPerHour);
/*  79 */
    map.put(BUnit.getUnit(CUBIC_FEET_NAME), cubicFeet);
/*  80 */
    map.put(BUnit.getUnit(CUBIC_METERS_NAME), cubicMeters);
/*  81 */
    map.put(BUnit.getUnit(IMPERIAL_GALLONS_NAME), imperialGallons);
/*  82 */
    map.put(BUnit.getUnit(LITERS_NAME), liters);
/*  83 */
    map.put(BUnit.getUnit(US_GALLONS_NAME), usGallons);
/*  84 */
    map.put(BUnit.getUnit(CUBIC_FEET_PER_MINUTE_NAME), cubicFeetPerMinute);
/*  85 */
    map.put(BUnit.getUnit(CUBIC_METERS_PER_SECOND_NAME), cubicMetersPerSecond);
/*  86 */
    map.put(BUnit.getUnit(IMPERIAL_GALLONS_PER_MINUTE_NAME), imperialGallonsPerMinute);
/*  87 */
    map.put(BUnit.getUnit(LITERS_PER_SECOND_NAME), litersPerSecond);
/*  88 */
    map.put(BUnit.getUnit(LITERS_PER_MINUTE_NAME), litersPerMinute);
/*  89 */
    map.put(BUnit.getUnit(US_GALLONS_PER_MINUTE_NAME), usGallonsPerMinute);
/*  90 */
    map.put(BUnit.getUnit(DEGREES_ANGULAR_NAME), degreesAngular);
/*  91 */
    map.put(BUnit.getUnit(DEGREES_CELSIUS_PER_HOUR_NAME), degreesCelsiusPerHour);
/*  92 */
    map.put(BUnit.getUnit(DEGREES_CELSIUS_PER_MINUTE_NAME), degreesCelsiusPerMinute);
/*  93 */
    map.put(BUnit.getUnit(DEGREES_FAHRENHEIT_PER_HOUR_NAME), degreesFahrenheitPerHour);
/*  94 */
    map.put(BUnit.getUnit(DEGREES_FAHRENHEIT_PER_MINUTE_NAME), degreesFahrenheitPerMinute);
/*  95 */
    map.put(BUnit.getUnit(NO_UNITS_NAME), noUnits);
/*  96 */
    map.put(BUnit.getUnit(PARTS_PER_MILLION_NAME), partsPerMillion);
/*  97 */
    map.put(BUnit.getUnit(PARTS_PER_BILLION_NAME), partsPerBillion);
/*  98 */
    map.put(BUnit.getUnit(PERCENT_NAME), percent);
/*  99 */
    map.put(BUnit.getUnit(PERCENT_PER_SECOND_NAME), percentPerSecond);
/* 100 */
    map.put(BUnit.getUnit(PER_MINUTE_NAME), perMinute);
/* 101 */
    map.put(BUnit.getUnit(PER_SECOND_NAME), perSecond);
/* 102 */
    map.put(BUnit.getUnit(PSI_PER_DEGREE_FAHRENHEIT_NAME), psiPerDegreeFahrenheit);
/* 103 */
    map.put(BUnit.getUnit(RADIANS_NAME), radians);
/* 104 */
    map.put(BUnit.getUnit(REVOLUTIONS_PER_MINUTE_NAME), revolutionsPerMinute);
/* 105 */
    map.put(BUnit.getUnit(CURRENCY_1_NAME), currency1);
/* 106 */
    map.put(BUnit.getUnit(CURRENCY_2_NAME), currency2);
/* 107 */
    map.put(BUnit.getUnit(CURRENCY_3_NAME), currency3);
/* 108 */
    map.put(BUnit.getUnit(CURRENCY_4_NAME), currency4);
/* 109 */
    map.put(BUnit.getUnit(CURRENCY_5_NAME), currency5);
/* 110 */
    map.put(BUnit.getUnit(CURRENCY_6_NAME), currency6);
/* 111 */
    map.put(BUnit.getUnit(CURRENCY_7_NAME), currency7);
/* 112 */
    map.put(BUnit.getUnit(CURRENCY_8_NAME), currency8);
/* 113 */
    map.put(BUnit.getUnit(CURRENCY_9_NAME), currency9);
/* 114 */
    map.put(BUnit.getUnit(CURRENCY_10_NAME), currency10);
/* 115 */
    map.put(BUnit.getUnit(SQUARE_INCHES_NAME), squareInches);
/* 116 */
    map.put(BUnit.getUnit(SQUARE_CENTIMETERS_NAME), squareCentimeters);
/* 117 */
    map.put(BUnit.getUnit(BTUS_PER_POUND_NAME), btusPerPound);
/* 118 */
    map.put(BUnit.getUnit(CENTIMETERS_NAME), centimeters);
/* 119 */
    map.put(BUnit.getUnit(POUNDS_MASS_PER_SECOND_NAME), poundsMassPerSecond);
/* 120 */
    map.put(BUnit.getUnit(DELTA_DEGREES_FAHRENHEIT_NAME), deltaDegreesFahrenheit);
/* 121 */
    map.put(BUnit.getUnit(DELTA_DEGREES_KELVIN_NAME), deltaDegreesKelvin);
/* 122 */
    map.put(BUnit.getUnit(KILOHMS_NAME), kilohms);
/* 123 */
    map.put(BUnit.getUnit(MEGOHMS_NAME), megohms);
/* 124 */
    map.put(BUnit.getUnit(MILLIVOLTS_NAME), millivolts);
/* 125 */
    map.put(BUnit.getUnit(KILOJOULES_PER_KILOGRAM_NAME), kilojoulesPerKilogram);
/* 126 */
    map.put(BUnit.getUnit(MEGAJOULES_NAME), megajoules);
/* 127 */
    map.put(BUnit.getUnit(JOULES_PER_DEGREE_KELVIN_NAME), joulesPerDegreeKelvin);
/* 128 */
    map.put(BUnit.getUnit(JOULES_PER_KILOGRAM_DEGREE_KELVIN_NAME), joulesPerKilogramDegreeKelvin);
/* 129 */
    map.put(BUnit.getUnit(KILOHERTZ_NAME), kilohertz);
/* 130 */
    map.put(BUnit.getUnit(MEGAHERTZ_NAME), megahertz);
/* 131 */
    map.put(BUnit.getUnit(PER_HOUR_NAME), perHour);
/* 132 */
    map.put(BUnit.getUnit(MILLIWATTS_NAME), milliwatts);
/* 133 */
    map.put(BUnit.getUnit(HECTOPASCALS_NAME), hectopascals);
/* 134 */
    map.put(BUnit.getUnit(MILLIBARS_NAME), millibars);
/* 135 */
    map.put(BUnit.getUnit(CUBIC_METERS_PER_HOUR_NAME), cubicMetersPerHour);
/* 136 */
    map.put(BUnit.getUnit(LITERS_PER_HOUR_NAME), litersPerHour);
/* 137 */
    map.put(BUnit.getUnit(KILOWATT_HOURS_PER_SQUARE_METER_NAME), kilowattHoursPerSquareMeter);
/* 138 */
    map.put(BUnit.getUnit(KILOWATT_HOURS_PER_SQUARE_FOOT_NAME), kilowattHoursPerSquareFoot);
/* 139 */
    map.put(BUnit.getUnit(MEGAJOULES_PER_SQUARE_METER_NAME), megajoulesPerSquareMeter);
/* 140 */
    map.put(BUnit.getUnit(MEGAJOULES_PER_SQUARE_FOOT_NAME), megajoulesPerSquareFoot);
/* 141 */
    map.put(BUnit.getUnit(WATTS_PER_SQUARE_METER_DEGREE_KELVIN_NAME), wattsPerSquareMeterDegreeKelvin);
/* 142 */
    map.put(BUnit.getUnit(CUBIC_FEET_PER_SECOND_NAME), cubicFeetPerSecond);
/* 143 */
    map.put(BUnit.getUnit(PERCENT_OBSCURATION_PER_FOOT_NAME), percentObscurationPerFoot);
/* 144 */
    map.put(BUnit.getUnit(PERCENT_OBSCURATION_PER_METER_NAME), percentObscurationPerMeter);
/* 145 */
    map.put(BUnit.getUnit(MILLIOHMS_NAME), milliohms);
/* 146 */
    map.put(BUnit.getUnit(MEGAWATT_HOURS_NAME), megawattHours);
/* 147 */
    map.put(BUnit.getUnit(KILO_BTUS_NAME), kiloBtus);
/* 148 */
    map.put(BUnit.getUnit(MEGA_BTUS_NAME), megaBtus);
/* 149 */
    map.put(BUnit.getUnit(KILOJOULES_PER_KILOGRAM_DRY_AIR_NAME), kilojoulesPerKilogramDryAir);
/* 150 */
    map.put(BUnit.getUnit(MEGAJOULES_PER_KILOGRAM_DRY_AIR_NAME), megajoulesPerKilogramDryAir);
/* 151 */
    map.put(BUnit.getUnit(KILOJOULES_PER_DEGREE_KELVIN_NAME), kilojoulesPerDegreeKelvin);
/* 152 */
    map.put(BUnit.getUnit(MEGAJOULES_PER_DEGREE_KELVIN_NAME), megajoulesPerDegreeKelvin);
/* 153 */
    map.put(BUnit.getUnit(NEWTON_NAME), newton);
/* 154 */
    map.put(BUnit.getUnit(GRAMS_PER_SECOND_NAME), gramsPerSecond);
/* 155 */
    map.put(BUnit.getUnit(GRAMS_PER_MINUTE_NAME), gramsPerMinute);
/* 156 */
    map.put(BUnit.getUnit(TONS_PER_HOUR_NAME), tonsPerHour);
/* 157 */
    map.put(BUnit.getUnit(KILO_BTUS_PER_HOUR_NAME), kiloBtusPerHour);
/* 158 */
    map.put(BUnit.getUnit(HUNDREDTHS_SECONDS_NAME), hundredthsSeconds);
/* 159 */
    map.put(BUnit.getUnit(MILLISECONDS_NAME), milliseconds);
/* 160 */
    map.put(BUnit.getUnit(NEWTON_METERS_NAME), newtonMeters);
/* 161 */
    map.put(BUnit.getUnit(MILLIMETERS_PER_SECOND_NAME), millimetersPerSecond);
/* 162 */
    map.put(BUnit.getUnit(MILLIMETERS_PER_MINUTE_NAME), millimetersPerMinute);
/* 163 */
    map.put(BUnit.getUnit(METERS_PER_MINUTE_NAME), metersPerMinute);
/* 164 */
    map.put(BUnit.getUnit(METERS_PER_HOUR_NAME), metersPerHour);
/* 165 */
    map.put(BUnit.getUnit(CUBIC_METERS_PER_MINUTE_NAME), cubicMetersPerMinute);
/* 166 */
    map.put(BUnit.getUnit(METERS_PER_SECOND_PER_SECOND_NAME), metersPerSecondPerSecond);
/* 167 */
    map.put(BUnit.getUnit(AMPERES_PER_METER_NAME), amperesPerMeter);
/* 168 */
    map.put(BUnit.getUnit(AMPERES_PER_SQUARE_METER_NAME), amperesPerSquareMeter);
/* 169 */
    map.put(BUnit.getUnit(AMPERE_SQUARE_METERS_NAME), ampereSquareMeters);
/* 170 */
    map.put(BUnit.getUnit(FARADS_NAME), farads);
/* 171 */
    map.put(BUnit.getUnit(HENRYS_NAME), henrys);
/* 172 */
    map.put(BUnit.getUnit(OHM_METERS_NAME), ohmMeters);
/* 173 */
    map.put(BUnit.getUnit(SIEMENS_NAME), siemens);
/* 174 */
    map.put(BUnit.getUnit(SIEMENS_PER_METER_NAME), siemensPerMeter);
/* 175 */
    map.put(BUnit.getUnit(TESLAS_NAME), teslas);
/* 176 */
    map.put(BUnit.getUnit(VOLTS_PER_DEGREE_KELVIN_NAME), voltsPerDegreeKelvin);
/* 177 */
    map.put(BUnit.getUnit(VOLTS_PER_METER_NAME), voltsPerMeter);
/* 178 */
    map.put(BUnit.getUnit(WEBERS_NAME), webers);
/* 179 */
    map.put(BUnit.getUnit(CANDELAS_NAME), candelas);
/* 180 */
    map.put(BUnit.getUnit(CANDELAS_PER_SQUARE_METER_NAME), candelasPerSquareMeter);
/* 181 */
    map.put(BUnit.getUnit(DEGREES_KELVIN_PER_HOUR_NAME), degreesKelvinPerHour);
/* 182 */
    map.put(BUnit.getUnit(DEGREES_KELVIN_PER_MINUTE_NAME), degreesKelvinPerMinute);
/* 183 */
    map.put(BUnit.getUnit(JOULE_SECONDS_NAME), jouleSeconds);
/* 184 */
    map.put(BUnit.getUnit(RADIANS_PER_SECOND_NAME), radiansPerSecond);
/* 185 */
    map.put(BUnit.getUnit(SQUARE_METERS_PER_NEWTON_NAME), squareMetersPerNewton);
/* 186 */
    map.put(BUnit.getUnit(KILOGRAMS_PER_CUBIC_METER_NAME), kilogramsPerCubicMeter);
/* 187 */
    map.put(BUnit.getUnit(NEWTON_SECONDS_NAME), newtonSeconds);
/* 188 */
    map.put(BUnit.getUnit(NEWTONS_PER_METER_NAME), newtonsPerMeter);
/* 189 */
    map.put(BUnit.getUnit(WATTS_PER_METER_PER_DEGREE_KELVIN_NAME), wattsPerMeterPerDegreeKelvin);
/* 190 */
    map.put(BUnit.getUnit(MICROSIEMENS_NAME), microSiemens);
/* 191 */
    map.put(BUnit.getUnit(CUBIC_FEET_PER_HOUR_NAME), cubicFeetPerHour);
/* 192 */
    map.put(BUnit.getUnit(US_GALLONS_PER_HOUR_NAME), usGallonsPerHour);
/* 193 */
    map.put(BUnit.getUnit(KILOMETERS_NAME), kilometers);
/* 194 */
    map.put(BUnit.getUnit(MICROMETERS_NAME), micrometers);
/* 195 */
    map.put(BUnit.getUnit(GRAMS_NAME), grams);
/* 196 */
    map.put(BUnit.getUnit(MILLIGRAMS_NAME), milligrams);
/* 197 */
    map.put(BUnit.getUnit(MILLILITERS_NAME), milliliters);
/* 198 */
    map.put(BUnit.getUnit(MILLILITERS_PER_SECOND_NAME), millilitersPerSecond);
/* 199 */
    map.put(BUnit.getUnit(DECIBELS_NAME), decibels);
/* 200 */
    map.put(BUnit.getUnit(DECIBELS_MILLIVOLT_NAME), decibelsMillivolt);
/* 201 */
    map.put(BUnit.getUnit(DECIBELS_VOLT_NAME), decibelsVolt);
/* 202 */
    map.put(BUnit.getUnit(MILLISIEMENS_NAME), millisiemens);
/* 203 */
    map.put(BUnit.getUnit(WATT_HOUR_REACTIVE_NAME), wattHourReactive);
/* 204 */
    map.put(BUnit.getUnit(KILOWATT_HOURS_REACTIVE_NAME), kilowattHoursReactive);
/* 205 */
    map.put(BUnit.getUnit(MEGAWATT_HOURS_REACTIVE_NAME), megawattHoursReactive);
/* 206 */
    map.put(BUnit.getUnit(MILLILITERS_OF_WATER_NAME), millilitersOfWater);
/* 207 */
    map.put(BUnit.getUnit(PER_MILE_NAME), perMile);
/* 208 */
    map.put(BUnit.getUnit(GRAMS_PER_GRAM_NAME), gramsPerGram);
/* 209 */
    map.put(BUnit.getUnit(KILOGRAMS_PER_KILOGRAM_NAME), kilogramsPerKilogram);
/* 210 */
    map.put(BUnit.getUnit(GRAMS_PER_KILOGRAM_NAME), gramsPerKilogram);
/* 211 */
    map.put(BUnit.getUnit(MILLIGRAMS_PER_GRAM_NAME), milligramsPerGram);
/* 212 */
    map.put(BUnit.getUnit(MILLIGRAMS_PER_KILOGRAM_NAME), milligramsPerKilogram);
/* 213 */
    map.put(BUnit.getUnit(GRAMS_PER_MILLILITER_NAME), gramsPerMilliliter);
/* 214 */
    map.put(BUnit.getUnit(GRAMS_PER_LITER_NAME), gramsPerLiter);
/* 215 */
    map.put(BUnit.getUnit(MILLIGRAMS_PER_LITER_NAME), milligramsPerLiter);
/* 216 */
    map.put(BUnit.getUnit(MICROGRAMS_PER_LITER_NAME), microgramsPerLiter);
/* 217 */
    map.put(BUnit.getUnit(GRAMS_PER_CUBIC_METER_NAME), gramsPerCubicMeter);
/* 218 */
    map.put(BUnit.getUnit(MILLIGRAMS_PER_CUBIC_METER_NAME), milligramsPerCubicMeter);
/* 219 */
    map.put(BUnit.getUnit(MICROGRAMS_PER_CUBIC_METER_NAME), microgramsPerCubicMeter);
/* 220 */
    map.put(BUnit.getUnit(NANOGRAMS_PER_CUBIC_METER_NAME), nanogramsPerCubicMeter);
/* 221 */
    map.put(BUnit.getUnit(GRAMS_PER_CUBIC_CENTIMETER_NAME), gramsPerCubicCentimeter);
/* 222 */
    map.put(BUnit.getUnit(BECQUERELS_NAME), becquerels);
/* 223 */
    map.put(BUnit.getUnit(KILOBECQUERELS_NAME), kilobecquerels);
/* 224 */
    map.put(BUnit.getUnit(MEGABECQUERELS_NAME), megabecquerels);
/* 225 */
    map.put(BUnit.getUnit(GRAY_NAME), gray);
/* 226 */
    map.put(BUnit.getUnit(MILLIGRAY_NAME), milligray);
/* 227 */
    map.put(BUnit.getUnit(MICROGRAY_NAME), microgray);
/* 228 */
    map.put(BUnit.getUnit(SIEVERTS_NAME), sieverts);
/* 229 */
    map.put(BUnit.getUnit(MILLISIEVERTS_NAME), millisieverts);
/* 230 */
    map.put(BUnit.getUnit(MICROSIEVERTS_NAME), microsieverts);
/* 231 */
    map.put(BUnit.getUnit(MICROSIEVERTS_PER_HOUR_NAME), microsievertsPerHour);
/* 232 */
    map.put(BUnit.getUnit(DECIBELS_A_NAME), decibelsA);
/* 233 */
    map.put(BUnit.getUnit(NEPHELOMETRIC_TURBIDITY_UNIT_NAME), nephelometricTurbidityUnit);
/* 234 */
    map.put(BUnit.getUnit(PH_NAME), ph);
/* 235 */
    map.put(BUnit.getUnit(GRAMS_PER_SQUARE_METER_NAME), gramsPerSquareMeter);
/* 236 */
    map.put(BUnit.getUnit(MINUTES_PER_DEGREE_KELVIN_NAME), minutesPerDegreeKelvin);
/* 237 */
    map.put(BUnit.getUnit(OHM_METER_SQUARED_PER_METER_NAME), ohmMeterSquaredPerMeter);
/* 238 */
    map.put(BUnit.getUnit(AMPERE_SECONDS_NAME), ampereSeconds);
/* 239 */
    map.put(BUnit.getUnit(VOLT_AMPERE_HOUR_NAME), voltAmpereHour);
/* 240 */
    map.put(BUnit.getUnit(KILOVOLT_AMPERE_HOUR_NAME), kilovoltAmpereHour);
/* 241 */
    map.put(BUnit.getUnit(MEGAVOLT_AMPERE_HOUR_NAME), megavoltAmpereHour);
/* 242 */
    map.put(BUnit.getUnit(VOLT_AMPERE_REACTIVE_HOUR_NAME), voltAmpereReactiveHour);
/* 243 */
    map.put(BUnit.getUnit(KILOVOLT_AMPERE_REACTIVE_HOUR_NAME), kilovoltAmpereReactiveHour);
/* 244 */
    map.put(BUnit.getUnit(MEGAVOLT_AMPERE_REACTIVE_HOUR_NAME), megavoltAmpereReactiveHour);
/* 245 */
    map.put(BUnit.getUnit(VOLT_SQUARED_HOURS_NAME), voltSquaredHours);
/* 246 */
    map.put(BUnit.getUnit(AMPERE_SQUARED_HOURS_NAME), ampereSquaredHours);
/* 247 */
    map.put(BUnit.getUnit(JOULES_PER_HOUR_NAME), joulesPerHour);
/* 248 */
    map.put(BUnit.getUnit(CUBIC_FEET_PER_DAY_NAME), cubicFeetPerDay);
/* 249 */
    map.put(BUnit.getUnit(CUBIC_METERS_PER_DAY_NAME), cubicMetersPerDay);
/* 250 */
    map.put(BUnit.getUnit(WATT_HOUR_PER_CUBIC_METER_NAME), wattHourPerCubicMeter);
/* 251 */
    map.put(BUnit.getUnit(JOULES_PER_CUBIC_METER_NAME), joulesPerCubicMeter);
/* 252 */
    map.put(BUnit.getUnit(MOLES_PERCENT_NAME), molesPercent);
/* 253 */
    map.put(BUnit.getUnit(PASCAL_SECOND_NAME), pascalSecond);
/* 254 */
    map.put(BUnit.getUnit(MILLION_STANDARD_CUBIC_FEET_PER_MINUTE_NAME), millionStandardCubicFeetPerMinute);
/* 47808 */
    map.put(BUnit.getUnit(STANDARD_CUBIC_FEET_PER_DAY_NAME), standardCubicFeetPerDay);
/* 47809 */
    map.put(BUnit.getUnit(MILLION_STANDARD_CUBIC_FEET_PER_DAY_NAME), millionStandardCubicFeetPerDay);
/* 47810 */
    map.put(BUnit.getUnit(THOUSAND_CUBIC_FEET_PER_DAY_NAME), thousandCubicFeetPerDay);
/* 47811 */
    map.put(BUnit.getUnit(THOUSAND_STANDARD_CUBIC_FEET_PER_DAY_NAME), thousandStandardCubicFeetPerDay);
/* 47812 */
    map.put(BUnit.getUnit(POUNDS_MASS_PER_DAY_NAME), poundsMassPerDay);
/* 47814 */
    map.put(BUnit.getUnit(MILLIREMS_NAME), millirems);
/* 47815 */
    map.put(BUnit.getUnit(MILLIREMS_PER_HOUR_NAME), milliremsPerHour);
  }
}

