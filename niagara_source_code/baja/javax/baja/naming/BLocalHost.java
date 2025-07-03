/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.baja.nav.BNavRoot;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.platform.RuntimeProfile;
import javax.baja.nre.util.TextUtil;
import javax.baja.registry.ModuleInfo;
import javax.baja.space.BISpace;
import javax.baja.space.BISpaceContainer;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Context;
import javax.baja.sys.ModuleNotFoundException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.LexiconText;
import javax.baja.util.Version;

import com.tridium.sys.schema.Fw;

/**
 * BLocalHost is the BHost for the local machine.  There is
 * only one instance accessed via INSTANCE.
 *
 * @author    Brian Frank
 * @creation  14 Jan 03
 * @version   $Revision: 20$ $Date: 6/17/10 12:50:31 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BLocalHost
  extends BIpHost
  implements BISession, BServiceScheme.ServiceSession, BISpaceContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BLocalHost(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLocalHost.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor 
////////////////////////////////////////////////////////////////

  /**
   * Private constructor.
   */
  private BLocalHost()
  {
    super("localhost");
  }

////////////////////////////////////////////////////////////////
// BHost
////////////////////////////////////////////////////////////////  

  /**
   * Return BLocalScheme.ORD.
   */
  @Override
  public BOrd getAbsoluteOrd()
  {
    return BLocalScheme.ORD;
  }

////////////////////////////////////////////////////////////////
// BISession
////////////////////////////////////////////////////////////////

  /**
   * Return true.
   */
  @Override
  public boolean isConnected()
  {
    return true;
  }
  
  /**
   * Do nothing.
   */
  @Override
  public void connect()
    throws Exception
  {
  }

  /**
   * Return this.
   */
  @Override
  public BHost getHost()
  {
    return this;
  }

  /**
   * Return "".
   */
  @Override
  public BOrd getOrdInHost()
  {
    return BOrd.make("");
  }

  /**
   * Return null (subject to change).
   */
  @Override
  public Context getSessionContext()
  {
    return null;
  }
  
////////////////////////////////////////////////////////////////
//BISpaceContainer
////////////////////////////////////////////////////////////////

  @Override
  public BISpace mountSpace(BISpace space)
  {
    // error checking
    if(spaces.containsKey(space))
      throw new IllegalArgumentException("Space already mounted " + space);
    spaces.put(space, space);
    space.setSpaceContainer(this);
    return space;
  }
 
  @Override
  public void unmountSpace(BISpace space)
  {
    // error checking
    if( ! spaces.containsKey(space) )
      throw new IllegalArgumentException("Space not mounted " + space);
    spaces.remove(space);
    space.setSpaceContainer(null);
  }

 
  @Override
  public Iterator<BISpace> getSpaces()
  {
    Collection<BISpace> spaceCollection = spaces.values();
    return spaceCollection.iterator();
  }
  
////////////////////////////////////////////////////////////////
// Framework access
//////////////////////////////////////////////////////////////// 
  
  @Override
  public Object fw(int x, Object a, Object b, Object c, Object d)
  {
    switch(x)
    {
      case Fw.GET_REMOTE_VERSION:
      {
        if(a!=null)
        {
          try
          {
            if (b == null)
            {
              // This is an arbitrary choice!  It would be better if caller provided a
              // runtime profile
              ModuleInfo[] info = Sys.getRegistry().getModules((String)a);
              return info[0].getVendorVersion();
            }
            else
            {
              ModuleInfo info = Sys.getRegistry().getModule((String)a, RuntimeProfile.valueOf((String)b));
              return info.getVendorVersion();
            }
          } catch (ModuleNotFoundException mnfe) {
            return Version.NULL;
          }
        }
        else
          return Sys.getBajaVersion();
      }
    }
    return super.fw(x, a, b, c, d);
  }  

////////////////////////////////////////////////////////////////
// BServiceScheme.ServiceSession
////////////////////////////////////////////////////////////////  

  /**
   * Return {@code Sys.getService()}
   */
  @Override
  public BComponent getService(Type type)
  {
    return Sys.getService(type);
  }
  
////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  @Override
  public String getDefaultNavDisplayName(Context cx)
  {
    if (getLexiconText() == null)
    {
      setLexiconText(LexiconText.make("baja", "nav.localHost"));
    }

    if (navNameSuffix == null)
    {
      try
      {
        navNameSuffix = " : " + Sys.getHostName();
      }
      catch (Exception e)
      {
        navNameSuffix = "";
      }
    }

    return appendStationName(getLexiconText().getText(cx) + navNameSuffix);
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  static final BIcon icon = BIcon.std("localhost.png");

  public static final BLocalHost INSTANCE = new BLocalHost();
  static
  {
    // mount in nav root
    BNavRoot.INSTANCE.addNavChild(INSTANCE);
    
    // cache for "localhost"
    cache.put("localhost", INSTANCE);
    cachedLocalHostCount++;
    
    // cache for local hostname also
    try 
    { 
      String localhost = TextUtil.toLowerCase(Sys.getHostName());
      if (!localhost.equals("localhost"))
      {
        cache.put(localhost, INSTANCE); 
        cachedLocalHostCount++;
      }
    } 
    catch(Exception ignored) {}
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  
 
  private String navNameSuffix;
  private HashMap<BISpace, BISpace> spaces = new HashMap<BISpace, BISpace>();
  

}
