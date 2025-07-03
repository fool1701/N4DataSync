/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.mobile;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;
import javax.baja.web.BIWebProfile;
import javax.baja.web.BWebProfileConfig;

/**
 * BWebProfileConfig is the MixIn added to each User to store the 
 * WebProfile configuration for the user's web experience.
 *
 * @author    John Sublett
 * @creation  02 Apr 2012
 * @version   $Revision$ $Date$
 * @since     Niagara 3.7
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "baja:User", "baja:UserPrototype" }
  )
)
/*
 This ord references a mobile nav file to use for this user.
 */
@NiagaraProperty(
  name = "mobileNavFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
public final class BMobileWebProfileConfig
  extends BWebProfileConfig                                       
  implements BIMixIn
{                                                         
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.mobile.BMobileWebProfileConfig(31066642)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "mobileNavFile"

  /**
   * Slot for the {@code mobileNavFile} property.
   * This ord references a mobile nav file to use for this user.
   * @see #getMobileNavFile
   * @see #setMobileNavFile
   */
  public static final Property mobileNavFile = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code mobileNavFile} property.
   * This ord references a mobile nav file to use for this user.
   * @see #mobileNavFile
   */
  public BOrd getMobileNavFile() { return (BOrd)get(mobileNavFile); }

  /**
   * Set the {@code mobileNavFile} property.
   * This ord references a mobile nav file to use for this user.
   * @see #mobileNavFile
   */
  public void setMobileNavFile(BOrd v) { set(mobileNavFile, v, null); }

  //endregion Property "mobileNavFile"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMobileWebProfileConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  public BMobileWebProfileConfig()
  {
    setTypeSpec(DEFAULT_TYPE_SPEC);
  }

////////////////////////////////////////////////////////////////
// IMixIn
////////////////////////////////////////////////////////////////
  
  /**
   * Return the display name.
   */
  public String getDisplayNameInParent(Context cx)
  {
    return Lexicon.make(TYPE.getModule(), cx).getText("mobileWebProfileConfig");
  }

////////////////////////////////////////////////////////////////
// TypeConfig
////////////////////////////////////////////////////////////////
  
  /**
   * The target type is <code>web:IWebProfile</code>.
   */
  public TypeInfo getTargetType()
  {                                    
    return BIMobileWebProfile.TYPE.getTypeInfo();
  }

////////////////////////////////////////////////////////////////
// BIWebProfileConfig
////////////////////////////////////////////////////////////////

  /**
   * Make a BIWebProfile from the current configuration.
   */
  public BIWebProfile makeWebProfile()
  {
    return (BIWebProfile)make(); 
  }

    
////////////////////////////////////////////////////////////////
// Attribtes
////////////////////////////////////////////////////////////////
  
  private static final BTypeSpec DEFAULT_TYPE_SPEC = BTypeSpec.make("hx:HTML5HxProfile");
}
