/*
 * Copyright 2011, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.web.mobile;

import javax.baja.agent.AgentFilter;
import javax.baja.agent.AgentInfo;
import javax.baja.agent.AgentList;
import javax.baja.agent.BAbstractPxView;
import javax.baja.naming.BOrd;
import javax.baja.nav.BNavFileNode;
import javax.baja.nav.NavFileDecoder;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDynamicEnum;
import javax.baja.sys.BEnumRange;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.user.BUser;
import javax.baja.util.BTypeSpec;
import javax.baja.web.BIWebProfile;
import javax.baja.web.BServletView;
import javax.baja.web.BWebProfileConfig;
import javax.baja.web.IWebEnv;
import javax.baja.web.WebOp;

import com.tridium.util.PxUtil;
import com.tridium.web.IHxConfigProvider;
import com.tridium.web.IWebEnvProvider;
import com.tridium.web.WebEnv;
import com.tridium.web.WebProcessException;
import com.tridium.web.WebUtil;
import com.tridium.web.servlets.BFileUploadView;

/**
 * Web Profile for Mobile
 *
 * @author  gjohnson
 * @creation  27 Jul 2011
 * @version  1
 * @since   Niagara 3.7
 */
@NiagaraType
public abstract class BMobileWebProfile
    extends BObject
    implements BIMobileWebProfile, IWebEnvProvider, IHxConfigProvider
{    
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.web.mobile.BMobileWebProfile(2979906276)1.0$ @*/
/* Generated Mon Nov 22 10:19:44 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BMobileWebProfile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  
  
////////////////////////////////////////////////////////////////
// WebProfile
////////////////////////////////////////////////////////////////
  
  public String[] listConfig() { return emptyStringArray; }
  public BValue getConfig(String key) { return null; }
  public BFacets getConfigFacets(String key) { return BFacets.NULL; }
  public void setConfig(String key, BValue value) {}
  
  public String[] getAppNames() { return emptyStringArray; }
  
  public boolean hasView(BObject target, AgentInfo agentInfo) { return true; }

  /**
   * Override this method if you want to change the HxProfileConfig or the HxProfile used for
   * hxPx Media. The default is a HandheldHxProfile to prevent any Profile chrome in the iframe.
   * @since Niagara 4.6
   */
  @Override
  public BWebProfileConfig getHxProfileConfig(BWebProfileConfig mobileConfig)
  {
    BWebProfileConfig hxConfig = new BWebProfileConfig();
    hxConfig.setTypeSpec(BTypeSpec.make("hx:HandheldHxProfile"));
    String themeName = getBajauxThemeName(mobileConfig);
    hxConfig.add(WebUtil.hxThemeKey, BDynamicEnum.make(0, BEnumRange.make(new String[]{themeName})));
    return hxConfig;
  }

  public String getBajauxThemeName(BWebProfileConfig mobileConfig)
  {
    BTypeSpec spec = (BTypeSpec)mobileConfig.get("theme");
    if (spec != null)
    {
      if (spec.getTypeName().contains("Zebra"))
      {
        return "Zebra";
      }
      else if (spec.getTypeName().contains("Lucid"))
      {
        return "Lucid";
      }
    }
    //fallback to Zebra for now
    return "Zebra";
  }
        
////////////////////////////////////////////////////////////////
// WebEnv
////////////////////////////////////////////////////////////////
  
  public static IWebEnv webEnv()
  {
    return WebEnvHolder.INSTANCE;
  }
   
  @Override
  public final IWebEnv getWebEnv(WebOp op) throws WebProcessException
  {
    return webEnv();
  }
  
  private final static class MobileEnv implements IWebEnv
  {
    private MobileEnv()
    {
    }
    
    @Override
    public AgentInfo getDefaultView(WebOp op, AgentList views)
    {
      // Try to use Px first
      for(int i=0; i<views.size(); ++i)
      {                                  
        AgentInfo agent = views.get(i);
        TypeInfo agentType = agent.getAgentType();
        if (agentType.is(WebEnv.pxView)) return agent;
      }

      // Use default
      return views.getDefault();
    }

    @Override
    public AgentInfo translate(WebOp op, AgentInfo agentInfo)
    {      
      // Translate PxView into a MobilePxView...
      if (agentInfo instanceof BAbstractPxView)
      {
        AgentList agentList =  Sys.getRegistry().getAgents(((BAbstractPxView)agentInfo).getType().getTypeInfo());
        agentList = agentList.filter(new MobileViewFilter(op));
        
        AgentInfo pxInfo = agentList.getDefault();
        TypeInfo typeInfo = pxInfo.getAgentType();
        
        // If the Mobile View is really for a Px page then return the correct AgentInfo
        if (typeInfo.is(BIMobilePxView.TYPE))
          return new PxUtil.PxMobile((BAbstractPxView)agentInfo, typeInfo);
        else
          return pxInfo;
      }
      
      // Translate from Workbench view to a Mobile view
      if (agentInfo.getAgentType().is(WebEnv.wbView))
      {
        AgentList views = Sys.getRegistry().getAgents(agentInfo.getAgentType());
        agentInfo = views.filter(new MobileViewFilter(op)).getDefault();
      }
      
      return agentInfo;
    }
    
    @Override
    public AgentList getViews(WebOp op)
    {
      return op.get().getAgents(op).filter(new MobileFilter(op));
    }

    @Override
    public AgentInfo getView(AgentList allViews, String viewId)
    {
      return allViews.get(viewId);
    }

    @Override
    public BWebProfileConfig makeWebProfileConfig()
    {
      return new BMobileWebProfileConfig();
    }
    
    @Override
    public BWebProfileConfig getWebProfileConfig(BUser user)
    {
      return (BWebProfileConfig)user.getMixIn(BMobileWebProfileConfig.TYPE);
    }
    
    @Override
    public BIWebProfile getWebProfile(WebOp op)
    {
      // Get the profile config that was used to create the current profile.  It will
      // be cached on the session.  If not, then get the profile config the
      // old fashioned way and then cache the profileConfig on the session for
      // next time.
      BWebProfileConfig profileConfig =
        (BWebProfileConfig)op.getRequest().getSession(true).getAttribute("profileConfig");
      if (profileConfig == null)
      {
        profileConfig = getWebProfileConfig(op.getUser());
        op.getRequest().getSession(true).setAttribute("profileConfig", profileConfig);
      }

      BIWebProfile profile = (BIWebProfile)profileConfig.make();
      return profile;
    }

    /**
     * Get the Hx specific WebProfileConfig for this environment from the standard WebProfileConfig.
     */
    @Override
    public BWebProfileConfig getHxProfileConfig(BWebProfileConfig config)
    {
      BIWebProfile webProfile = config.makeWebProfile();
      if(webProfile instanceof IHxConfigProvider)
      {
        return ((IHxConfigProvider) webProfile).getHxProfileConfig(config);
      }
      else
      {
        return WebUtil.getHandheldHxProfileConfig();
      }
    }
        
    public BOrd getHomePage(WebOp op)
    {
      // Attempt to get the Web Profile Config from the WebOp's HttpSession. 
      BWebProfileConfig profileConfig = op.getProfileConfig();
      
      // Attempt to use the mobile profile config.
      if (profileConfig != null && profileConfig instanceof BMobileWebProfileConfig)
      {
        try
        {      
          BOrd navFile = ((BMobileWebProfileConfig)profileConfig).getMobileNavFile();               
          if (!navFile.isNull()) 
          {
            BNavFileNode root = NavFileDecoder.load(navFile).getRootNode();
            return root.getOrdInSession();
          }
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      }
      else
      {
        // Use the standard user's nav file if the Mobile one isn't being used.
        try
        {  
          BOrd navFile = op.getUser().getNavFile();               
          if (!navFile.isNull()) 
          {
            BNavFileNode root = NavFileDecoder.load(navFile).getRootNode();
            return root.getOrdInSession();
          }
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
      }

      // If Apps container is available then use that for the home page...
      try
      {
        BComponent comp = Sys.getService(Sys.getType("app:AppContainer"));
        return comp.getNavOrd().relativizeToSession();
      }
      catch(Throwable ignore) {}
      
      // Otherwise default back to the user's home page.
      return op.getUser().getHomePage();
    }
  }
  
////////////////////////////////////////////////////////////////
// Mobile View Filtering
////////////////////////////////////////////////////////////////
  
  private final static class MobileFilter extends AgentFilter
  {
    private MobileFilter(Context cx)
    {
      this.cx = cx;
      if(cx instanceof WebOp)
      {
        WebOp op = (WebOp) cx;
        BIWebProfile profile = (BIWebProfile)op.getProfileConfig(cx).make();
        BObject target = op.get();
        profileFilter = new WebUtil.ProfileFilter(profile, target);
      }
    }
    
    public boolean include(AgentInfo agentInfo)
    {
      if(profileFilter != null && !profileFilter.include(agentInfo)){
        return false;
      }
      TypeInfo agentType = agentInfo.getAgentType();
      MobileViewFilter viewFilter = new MobileViewFilter(cx);


      // If a Workbench View then find the Mobile Web View...
      if (agentType.is(WebEnv.wbView))
      {
        AgentList agentList = Sys.getRegistry().getAgents(agentType).filter(viewFilter);
        if (agentList.size() == 0) return false;
        agentInfo = agentList.getDefault();
      }
      else if (agentType.is(BFileUploadView.TYPE))
      {
        return false;
      }
      else if (!agentType.is(BIMobileWebView.TYPE))
      {
        if (agentType.is(WebEnv.hxView))
        {
          // No support for standard Hx in Mobile...
          return false;
        }
        else if (agentType.is(servlet))
        {
          // If this is a general Servlet that's not Hx or Wb then we support it (i.e. the file servlet)...
          return true;
        }
      }
          
      // If a Px View then find the Mobile Px View...
      if (agentInfo instanceof BAbstractPxView)
      {
        AgentList agentList =  Sys.getRegistry().getAgents(((BAbstractPxView)agentInfo).getType().getTypeInfo());
        agentList = agentList.filter(viewFilter);
        if (agentList.size() == 0) return false;
        agentInfo = agentList.getDefault();
        
        return agentInfo.getAgentType().is(BIMobilePxView.TYPE);
      }
      
      return viewFilter.include(agentInfo);
    }
    
    private Context cx;
    private AgentFilter profileFilter;
  }
  
  private final static class MobileViewFilter extends AgentFilter
  {
    private MobileViewFilter(Context cx) {
      this.cx = cx;
      if(cx instanceof WebOp)
      {
        WebOp op = (WebOp) cx;
        BIWebProfile profile = (BIWebProfile)op.getProfileConfig(cx).make();
        BObject target = op.get();
        profileFilter = new WebUtil.ProfileFilter(profile, target);
      }
    }
    
    public boolean include(AgentInfo agent)
    {
      if(profileFilter != null && !profileFilter.include(agent)){
        return false;
      }

      TypeInfo agentType = agent.getAgentType();
      boolean include = false;
      
      if (agentType.is(BIMobileWebView.TYPE))
      {
        // If the Mobile View is an operational one then query to see if it's currently operational
        if (agentType.is(BIMobileWebOperationalView.TYPE))
        {
          try
          {
            // Make sure the view is operational
            BIMobileWebOperationalView opView = (BIMobileWebOperationalView)agentType.getInstance();
            include = opView.isOperational(cx);
          }
          catch(Throwable ignore) {}
        }
        else 
          include = true;
      }
      return include;
    }
    
    private Context cx;
    private AgentFilter profileFilter;

  }
    
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * Starting in Niagara 4.9, when the Context provided is a WebOp, this method will return an AgentFilter that ensures
   * that the agents are appropriate for the profile of the User.
   */
  public final AgentFilter getMobileAgentFilter(Context cx)
  {
    return new MobileFilter(cx);
  }
    
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private static final String[] emptyStringArray = new String[0];
  private static final TypeInfo servlet  = BServletView.TYPE.getTypeInfo();

  private interface WebEnvHolder
  {
    MobileEnv INSTANCE = new MobileEnv();
  }
}
