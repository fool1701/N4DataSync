////////////////////////////////////////////////////////////////
// File: Psychrometric.java
//
// Revision History:
//    30 Nov 01   Sajaad A. Chaudry     Creation.
//    05 Apr 02   Frank Smith           Moved input validations to "isXxxValid" methods
//                                      Added check for NaN values
//
// Copyright 2001 Tridium, Inc.
////////////////////////////////////////////////////////////////

package com.tridium.kitControl.hvac;


public abstract class Psychrometric 
{
    public Psychrometric() {}

   /**
   * This routine calculates the dew point temperature (deg. F) for the dry bulb temperature and the
   * RH(humidity) passed to it. It uses August-Roche-Magnus approximation method/formula to calculate the dew
   * point.
   * 
   * @param inputTemp
   * @param rh
   * @return dewpoint as float
   */
    public static float dewpointTemperature( float inputTemp, float rh )
    {

      double tempCelcius = (5 * (inputTemp -32)) / 9; // convert the input temperature in Fahrenheit to celcius

      double temp1 = (17.625 * tempCelcius) / (243.04 + tempCelcius);

      double lnValue  = Math.log(rh/100);

      double dewPoint =  243.04 * (lnValue + temp1) / (17.625 - lnValue - temp1);

      dewPoint  =  ((9 * dewPoint) / 5 ) + 32; // converting the input_temperature to Fahrenheit from celcius

      return Float.parseFloat(String.valueOf(dewPoint));

    }
    
    public static float enthalpy( float temp, float RH )
    {
    /*
    ** PURPOSE:
    **  This routine calculates the enthalpy (Btu/lbm) for the dry bulb
    **  temperature (-10.0 - 150.0 deg. F) and the RH (0.1 - 100 %) passed to it.
    **
    ** RETURN:
    **  -9999.99 if a temperature out of range or a RH out of range is passed to it.
    */

        float pw, w;

        if ( !isTempValid( temp ) || !isRHValid( RH ) ) return( -9999.99f );

        pw = vaporPressure( temp, RH );
        w  = 0.62198f * pw / ( 14.696f - pw );
        return( ( 0.240f * temp ) + w * ( 1061.0f + ( 0.444f * temp ) ) );
    }

    public static float relativeHumidity( float temp, float td )
    {
    /*
    **  This routine calculates the Relative Humidity ( % ) for the dry
    **  bulb temperature (-10.0 - +150.0 deg. F) and the Dewpoint temperature
    **  (-10.0 - +150 deg. F) passed to it. It returns a -9999.99 if a dry bulb
    **  temperature or a Dewpoint temperature out of range is passed to it.
    */

        float RH;

        if ( !isTempValid( temp ) || !isDewpointValid( td ) ) return( -9999.99f );

        RH = ( saturationPressure( td ) / saturationPressure( temp ) ) * 100.0f;
        if ( RH > 100.0f ) RH = 100.0f;
        return( RH );
    }
    
    public static float saturationPressure( float temp )
    {
    /*
    ** PURPOSE:
    **  This routine calculates the saturation pressure (psia) for the
    **  temperature range -10.0 to +150 degrees F. It returns a -9999.99 if a
    **  temperature outside the range is passed to it.
    */

        int i, t1, t2, t3;
        float dx;

        if ( !isTempValid( temp ) ) return( -9999.99f );

        dx = 0.01f;
        if ( temp < 0.0f ) dx = 0.0f - dx;

        t1 = (int)temp * 10;
        t2 = (int)( temp * 10.0f + dx );
        t3 = t2 - t1;

        i = ( t1 / 10 ) + 10;
        return( ( ( p[ i + 1 ] - p[ i ] ) * ( (float)( t3 * 10 ) / 100.0f ) ) + p[ i ] );
    }
    
    public static float vaporPressure( float temp, float RH )
    {
    /*
    ** PURPOSE:
    **  This routine calculates the vapor pressure (psia) for the dry bulb
    **  temperature (-10.0 - 150.0 deg. F) and the RH (0.1 - 100 %) passed to it.
    **  It returns a -9999.99 if a temperature out of range or a RH out of range is
    **  passed to it.
    */

        if ( !isTempValid( temp ) || !isRHValid( RH ) ) return( -9999.99f );

        return( saturationPressure( temp ) * RH / 100.0f );
    }
    
    public static float vaporTemperature( float press )
    {

    /*
    ** PURPOSE:
    **  This routine calculates the vapor temperature (deg. F) for the pressure
    **  (0.010830 - 3.72283 psia) passed to it.
    ** RETURN:
    **  It returns a -9999.99 if a pressure out of range is passed to it.
    */
        if ( !isPressureValid( press ) ) return( -9999.99f );
        
        int i = 0;
        while ( press > p[ i ] ) i++;

        if ( press == p[ i ] ) return( (float)( i - 10 ) );

        return( ( press - p[ i - 1 ] ) / ( p[ i ] - p[ i - 1 ] ) + ( (float)(i - 11 ) ) );
    } 
    
    public static float wetbulbTemperature( float temp, float RH )
    {

        float pw, pww, w, tw = 0.0f, ww, wn, err, tn, t;
        int i = 0;

    /*
    ** PURPOSE:
    **  This routine calculates the Wet Bulb Temperature (deg. F) for the dry
    **  bulb temperature (-10.0 - +150.0 deg. F) and the Relative Humidity
    **  (0.1 - 100.0 %) passed to it. It returns a -9999.99 if a dry bulb
    **  temperature or a Relative Humidity out of range is passed to it.
    */

        if ( !isTempValid( temp ) || !isRHValid( RH ) ) return( -9999.99f );

        pw = RH * saturationPressure( temp ) / 100.0f;
        w = 0.62198f * pw / ( 14.7f - pw );
        err = 1.0f;
        t = temp;
        tn = -11.0f;

        while ( err > 0.000001f && i < 20 ) {
            i += 1;
            tw = ( t + tn ) * 0.5f;
            pww = saturationPressure( tw );
            ww = 0.62198f * pww / (14.7f - pww );

            wn = ( ( ( 1093.0f - ( 0.556f * tw ) ) * ww ) - ( 0.240f * ( temp - tw ) ) ) /
                     ( 1093.0f + ( 0.444f * temp ) - tw );

            if ( wn < 0.0f ) {
                tn += 5.0;
                continue;
            }

            err = w - wn;
            if ( err < 0.0 )
                t = tw;
            else
                tn = tw;
                
            err = ( ( err < 0.0f ) ? ( 0.0f - err) : err );
        }
        return( tw );
    }
    
	public static boolean isTempValid(float temp)
  {
    return !( ( temp < -10.0f ) || ( temp > 150.0f ) || ( Float.isNaN(temp) ) );

  }
  
	public static boolean isRHValid(float RH)
  {
    return !( ( RH   < 0.1f  ) || ( RH   > 100.0f ) || ( Float.isNaN(RH) ) );
  }
  
	public static boolean isDewpointValid(float td)
  {
    return !( ( td   < -10.0f ) || ( td   > 150.0f ) || ( Float.isNaN(td) ) );
  }
  
	public static boolean isPressureValid(float press)
  {
    return !( ( press < 0.010830f ) || ( press > 3.72283f ) || ( Float.isNaN(press) ) );
  }
  
  private static float[] p = {   0.010830f,   0.011438f,
							0.012078f,   0.012750f,   0.013456f,   0.014198f,
							0.014977f,   0.015795f,   0.016655f,   0.017556f,
							0.018502f,   0.019496f,   0.020537f,   0.021629f,
							0.022775f,   0.023975f,   0.025233f,   0.026552f,
							0.027933f,   0.029380f,   0.030894f,   0.032480f,
							0.034140f,   0.035878f,   0.037697f,   0.039598f,
							0.041587f,   0.043667f,   0.045841f,   0.048114f,
							0.050489f,   0.052971f,   0.055564f,   0.058273f,
							0.061097f,   0.064053f,   0.067133f,   0.070349f,
							0.073705f,   0.077208f,   0.080859f,   0.084670f,
							0.088643f,   0.09229f,    0.09607f,    0.09998f,
							0.10403f,    0.10823f,    0.11257f,    0.11707f,
							0.12173f,    0.12655f,    0.13153f,    0.13669f,
							0.14203f,    0.14755f,    0.15327f,    0.15917f,
							0.16528f,    0.17159f,    0.17811f,    0.18485f,
							0.19181f,    0.19900f,    0.20643f,    0.21410f,
							0.22203f,    0.23020f,    0.23865f,    0.24736f,
							0.25635f,    0.26563f,    0.27520f,    0.28507f,
							0.29525f,    0.30575f,    0.31657f,    0.32772f,
							0.33922f,    0.35107f,    0.36329f,    0.37587f,
							0.38883f,    0.40217f,    0.41593f,    0.43009f,
							0.44466f,    0.45967f,    0.47511f,    0.49100f,
							0.50737f,    0.52420f,    0.54150f,    0.55933f,
							0.57763f,    0.59648f,    0.61585f,    0.63576f,
							0.65625f,    0.67726f,    0.69890f,    0.72113f,
							0.74395f,    0.76742f,    0.79151f,    0.81628f,
							0.84170f,    0.86779f,    0.89459f,    0.92211f,
							0.95034f,    0.97934f,    1.00906f,    1.03958f,
							1.07090f,    1.10304f,    1.13602f,    1.16980f,
							1.20446f,    1.24003f,    1.27646f,    1.31383f,
							1.35219f,    1.39139f,    1.43167f,    1.47288f,
							1.51515f,    1.55845f,    1.60281f,    1.64824f,
							1.69473f,    1.74243f,    1.79125f,    1.84119f,
							1.89240f,    1.94477f,    1.99832f,    2.05324f,
							2.10939f,    2.16687f,    2.22565f,    2.28581f,
							2.34731f,    2.41036f,    2.47464f,    2.54053f,
							2.60789f,    2.67671f,    2.74713f,    2.81907f,
							2.89262f,    2.96782f,    3.04475f,    3.12324f,
							3.20350f,    3.28548f,    3.36935f,    3.45494f,
							3.54239f,    3.63165f,    3.72283f
							};
}


    