/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.app;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.ServiceNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BServiceContainer;
import javax.baja.web.app.BWebApp;

import com.tridium.box.BBoxService;
import com.tridium.sys.schema.Fw;

/**
 * BajaScript Web App
 * 
 *
 * @author		gjohnson
 * @creation 	27 Jul 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public abstract class BBajaScriptWebApp
    extends BWebApp
    implements BIBajaScriptWebApp
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.app.BBajaScriptWebApp(2979906276)1.0$ @*/
/* Generated Fri Jan 14 13:34:19 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBajaScriptWebApp.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////

  /**
   * Framework use only.
   */
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {  
    switch(x)
    {
      case Fw.SERVICE_STARTED: fwServiceStarted(); break;
    }
    return super.fw(x, a, b, c, d);
  }  

  private void fwServiceStarted()
  {
    // For convenience, if the BoxService isn't available then automatically add it
    try
    {
      Sys.getService(BBoxService.TYPE);
    }
    catch(ServiceNotFoundException e)
    {
      try
      {
        // Add BoxService to services container...
        BServiceContainer services = (BServiceContainer)Sys.getService(BServiceContainer.TYPE);
        services.add("BoxService", new BBoxService());
      }
      catch(Throwable ignore) {}
    }
  }
  
////////////////////////////////////////////////////////////////
// App
////////////////////////////////////////////////////////////////
  
  public Type[] getRequiredServices()
  {
    // BajaScript requires the BOX Service to be installed...
    return append(super.getRequiredServices(), BBoxService.TYPE);
  }
}
