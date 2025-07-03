/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.box;

import javax.baja.agent.BIAgent;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BOX Server Side Call Handler
 * <p>
 * Public methods defined by objects with this interface can be invoked from 
 * a BOX client (i.e. BajaScript). The format of the method can be like one of the following...
 * <pre><code>
 * public BValue fooBar(BComponent comp, BValue arg, Context cx) throws Exception
 * {
 *   return BString.make("I love BajaScript!");
 * }
 * 
 * public BValue fooBar(BComponent comp, Context cx) throws Exception
 * {
 *   return BString.make("I love BajaScript!");
 * }
 * 
 * public void fooBar(BComponent comp, BValue arg, Context cx) throws Exception
 * {
 *   System.out.println("I love BajaScript!");
 * }
 * 
 * public void fooBar(BComponent comp, Context cx) throws Exception
 * {
 *   System.out.println("I love BajaScript!");
 * }
 * </code></pre>
 * The component is the mounted instance the server side call is being used upon.
 * <p>
 * Any class implementing this interface needs to declare themselves as an
 * agent on a given target component. For instance, if some server-side
 * methods were being added to the User Service, any class implementing this
 * interface needs to be registered as an agent on 'baja:UserService'. Please note,
 * it's important that when declaring the Agent XML to specify the requiredPermissions
 * attribute on the 'agent' XML element. If no permissions are found then security will
 * default back to operatorInvoke.
 * 
 * 
 * @author		gjohnson
 * @creation 	27 Jan 2011
 * @version 	1
 * @since 		Niagara 3.7
 */
@NiagaraType
public interface BIServerSideCallHandler
    extends BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.box.BIServerSideCallHandler(2979906276)1.0$ @*/
/* Generated Thu Nov 18 16:22:08 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIServerSideCallHandler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
}
