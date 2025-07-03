/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx;

import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.naming.OrdTarget;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.Adapter;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BConverter;

/**
 * The OrdToImage converter inputs a Ord and outputs a BImage.
 *
 * @author    Brian Frank
 * @creation  8 Jan 05
 * @version   $Revision: 4$ $Date: 6-Dec-04 9:12:52 AM$
 * @since     Baja 1.0
 */
@NiagaraType(
  adapter = @Adapter(from = "baja:Ord", to = "gx:Image")
)
public class BOrdToImage
  extends BConverter
{                           
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.BOrdToImage(876506874)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BOrdToImage.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Converter
////////////////////////////////////////////////////////////////

  public BObject convert(BObject from, BObject to, Context cx)
  {      
    synchronized(lock)
    {
      // short circuit if cached                
      if (from == cacheFrom) return cacheImage;
      
      // get image         
      BImage image = toImage(from);
  
      // check if we have a base ord to use
      if (cx instanceof OrdTarget)
      {
        OrdTarget target = (OrdTarget)cx;
        BOrd baseOrd = target.getOrd();
        BObject base = target.get();
        if (target.getComponent() != null)
          baseOrd = target.getComponent().getNavOrd();
        else if (base instanceof BINavNode)
          baseOrd = ((BINavNode)base).getNavOrd();
        image.setBaseOrd(baseOrd);
      }                                  
  
      // cache
      cacheFrom = from;
      cacheImage = image;
      return image;     
    }
  }                  
  
  BImage toImage(BObject from)
  {
    if (from instanceof BOrd)     return BImage.make((BOrd)from);
    if (from instanceof BOrdList) return BImage.make((BOrdList)from);
    if (from instanceof BImage)   return (BImage)from;
    return BImage.make(from.toString());
  }
                
////////////////////////////////////////////////////////////////
// Attribues
////////////////////////////////////////////////////////////////
  
  Object lock = new Object();
  BObject cacheFrom;
  BImage cacheImage;
} 
