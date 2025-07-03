/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.nrio.util;

import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

@NiagaraType
@NiagaraSingleton
public class BThermistorType3Type extends BAbstractThermistorType
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.nrio.util.BThermistorType3Type(2747097003)1.0$ @*/
/* Generated Thu Jun 02 14:30:04 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BThermistorType3Type INSTANCE = new BThermistorType3Type();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BThermistorType3Type.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BThermistorType3Type()
  {
  }
  
  public float convertValue(float resistance)
  {
    int band = 0, count;
    float p1, p2, v1, v2;

    count = ohms.length;
      
    // Check boundry conditions
    if (resistance < ohms[0]){
      return celcius[0];
    }

    if (resistance >= ohms[count - 1]){
      return celcius[count - 1];
    }

    // Find the band
    for (int i = 0; i < count - 1; i++){
      if ((resistance >= ohms[i]) && (resistance < ohms[i+1])){
        band = i;
        break;
      }
    }
    
    p1 = ohms[band];
    p2 = ohms[band + 1];
    v1 = celcius[band];
    v2 = celcius[band + 1];

    return v1 - ((p1 - resistance) * (v1 - v2) / (p1 - p2));
  }  

  private static float[] ohms = new float[]{0.0f, 610f, 1060f, 1690f, 2320f, 3250f, 4620f, 
                                               6240f, 8197f, 10000f, 12268f, 15136f, 18787f, 23462f, 
                                               29490f, 37316f, 47549f, 61030f, 78930f, 100000f}; 
  private static float[] celcius = new float[]{165f, 110f, 90f, 75f, 65f, 55f, 45f, 37f, 30f, 25f, 20f, 
                                               15f, 10f, 5f, 0.0f, -5f, -10f, -15f, -20f, -25f};
}
