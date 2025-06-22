/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BIMixIn;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeConfig;
import javax.baja.util.BTypeSpec;
import javax.baja.util.Lexicon;

/**
 * BWebProfileConfig is the MixIn added to each User to store the 
 * WebProfile configuration for the user's web experience.
 *
 * @author    Brian Frank on 6 Dec 01
 * @version   $Revision: 10$ $Date: 2/27/09 9:56:44 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = { "baja:User", "baja:UserPrototype" }
  )
)
@SuppressWarnings("unused")
public class BWebProfileConfig
  extends BTypeConfig                                       
  implements BIMixIn
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BWebProfileConfig(2366969065)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BWebProfileConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  public BWebProfileConfig()
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
    return Lexicon.make(TYPE.getModule(), cx).getText("webProfileConfig");
  }

////////////////////////////////////////////////////////////////
// TypeConfig
////////////////////////////////////////////////////////////////

  @Override
  public void configChanged()
  {
    // NCCB-12224: Rename of ShellHxProfile - this is a temporary fix to handle
    // old N4 stations prior to the rename (avoids migration requirement).  We can remove
    // this check in the future once everyone has upgraded
//    if (getTypeSpec().getModuleName().equals("hx") &&
//        getTypeSpec().getTypeName().equals("ShellHxProfile"))
    if (getTypeSpec().toString().equals("hx:ShellHxProfile"))
      setTypeSpec(BTypeSpec.make("hx", "HTML5HxProfile"));

    super.configChanged();
  }

  @Override
  public void changed(Property prop, Context cx)
  {
    if (prop.getType().is(BDynamicEnum.TYPE) &&
        prop.getName().toLowerCase().contains("theme"))
    { // Ensure that theme property changes also trigger a module dependency re-calculation
      updateModuleDependencies(/*forceUpdate*/false);
    }
    super.changed(prop, cx);
  }

  /**
   * The target type is <code>web:IWebProfile</code>.
   */
  public TypeInfo getTargetType()
  {                                    
    return BIWebProfile.TYPE.getTypeInfo();
  }              
  
  public String toString(Context cx)
  {
    try
    {
      return getTypeSpec().getTypeInfo().getDisplayName(cx);
    }
    catch(Exception e)
    {
      //NCCB-10332/Pacman 23796 - Type is not available so just use the TypeSpec.toString()
      return getTypeSpec().toString(cx);
    }
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
  
  static final BTypeSpec DEFAULT_TYPE_SPEC = BTypeSpec.make("hx:HTML5HxProfile");

}
