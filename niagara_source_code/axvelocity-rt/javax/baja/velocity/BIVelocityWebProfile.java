/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.velocity;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.web.BIWebProfile;
import javax.baja.web.WebOp;

import org.apache.velocity.VelocityContext;

/**
 * Interface for a Velocity Web Profile.
 * <p>
 * All Velocity Web Profiles must implement this interface.
 *
 * @author		gjohnson
 * @creation 	29 Jul 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public interface BIVelocityWebProfile
    extends BIWebProfile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.velocity.BIVelocityWebProfile(2979906276)1.0$ @*/
/* Generated Thu Nov 18 10:14:51 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIVelocityWebProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
  /**
   * Return a new VelocityContext.
   * 
   * @param view  the view the Context is being created for
   * @param op  the current request's WebOp
   * 
   * @return VelocityContext
   */
  VelocityContext makeVelocityContext(BVelocityView view, WebOp op) throws Exception;
  
  /**
   * Write the view.
   * 
   * @param view  the view that needs to be written by the Profile
   * @param vContext  the Velocity Context used by the template generator
   * @param op  the current request's WebOp 
   */
  void write(BVelocityView view, VelocityContext vContext, WebOp op) throws Exception;

}
