/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web;

import java.util.HashSet;
import java.util.stream.Stream;

import javax.baja.agent.AgentInfo;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BInterface;
import javax.baja.sys.BObject;
import javax.baja.sys.InvalidEnumException;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BTypeConfig;
import javax.baja.util.BTypeSpec;
import javax.baja.web.mobile.BIMobileTheme;

import com.tridium.util.CustomThemeModuleManager;

/**
 * BIWebProfile is used to define a thematic web user interface.
 * Each user profile may have a tailored IWebProfile using the
 * BWebProfileConfig component.
 *
 * @author    Brian Frank       
 * @creation  4 Oct 04
 * @version   $Revision: 4$ $Date: 10/28/09 11:24:59 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public interface BIWebProfile
  extends BInterface, BTypeConfig.IConfigurable
{                                                         
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.BIWebProfile(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:43 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIWebProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Return the application name(s) for this profile. The appName(s)
   * serve as registry keys to enable certain agents/views
   * that only make sense within a specific application.  See
   * AgentInfo.getAppName() for more details.  
   *
   * @since Niagara 3.4
   */
  public String[] getAppNames();
  
  
  /**
   * Return if this profile supports the specified view.
   *
   * @since Niagara 3.4 
   */
  public boolean hasView(BObject target, AgentInfo agentInfo);

  /**
   * Return a Stream of Strings that define the names of module
   * dependencies that the current typeSpec selection requires
   *
   * @since Niagara 4.0
   * @param config - The BTypeConfig instance for reference
   * @return a Stream of Strings representing the names of module
   * dependencies that are required for the current typeSpec selection
   */
  @Override
  default Stream<String> getModuleDependencyNames(BTypeConfig config)
  {
    HashSet<String> result = new HashSet<>(2);
    result.add(this.getType().getModule().getModuleName());

    Property[] props = config.getPropertiesArray();
    for (Property prop: props)
    {
      if (prop.isDynamic() &&
          prop.getName().toLowerCase().contains("theme"))
      {
        if (prop.getType().is(BDynamicEnum.TYPE))
        {
          if (config.isRunning())
          {
            BDynamicEnum currentThemeEnum = (BDynamicEnum)config.get(prop);
            BDynamicEnum defaultThemeEnum = CustomThemeModuleManager.getDefaultThemeEnum();
            if (!currentThemeEnum.getRange().equals(defaultThemeEnum.getRange()))
            {
              try
              {
                currentThemeEnum = BDynamicEnum.make(defaultThemeEnum.getRange().get(currentThemeEnum.getTag()));
                config.set(prop, currentThemeEnum);
              } catch (InvalidEnumException ignore)
              {
                // TODO: In the rare event that someone manually removes a theme module that was
                // selected for use, should we change the theme selection for them to line up with
                // the theme modules that are available?  Right now, in such a scenario it will
                // ignore it and keep the missing theme selection for the user's web profile.
                // The user will have to manually fix it either by updating the selected theme to
                // one that is available, or by re-installing the missing theme module.
              }
            }
          }

          String themeName = ((BDynamicEnum) config.get(prop)).getTag();
          if (themeName != null && !themeName.isEmpty())
            result.add("theme" + themeName);
        }
        else if (prop.getType().is(BTypeSpec.TYPE))
        { // The mobile theme selection is a type spec, not a dynamic enum like other web profiles
          BTypeSpec typeSpec = ((BTypeSpec) config.get(prop));
          result.add(typeSpec.getModuleName());
          if (typeSpec.getResolvedType().is(BIMobileTheme.TYPE))
          {
            BObject themeInstance = typeSpec.getInstance();
            String bajauxTheme = ((BIMobileTheme) themeInstance).getBajauxThemeName((BWebProfileConfig) config);
            if (bajauxTheme != null && !bajauxTheme.isEmpty())
              result.add("theme" + bajauxTheme);
          }
        }
      }
    }

    return result.stream();
  };
    

  

}
