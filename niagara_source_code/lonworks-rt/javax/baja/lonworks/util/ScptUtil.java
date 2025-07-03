/*
 * Copyright 2000 Tridium Inc. All Rights Reserved.
 */
package javax.baja.lonworks.util;

import com.tridium.lonworks.xml.XCpTypeDef;
import com.tridium.lonworks.xml.XUtil;

import javax.baja.lonworks.BConfigParameter;
import javax.baja.lonworks.BLonComponent;
import javax.baja.lonworks.BLonDevice;
import javax.baja.lonworks.BNetworkConfig;
import javax.baja.lonworks.datatypes.BConfigProps;
import javax.baja.lonworks.datatypes.BNcProps;
import javax.baja.lonworks.enums.BLonConfigScope;
import javax.baja.lonworks.londata.BLonData;
import javax.baja.nre.util.Array;
import javax.baja.sys.BFacets;
import java.util.StringTokenizer;


/**
 *   This class contains static methods used to manage
 * SCPTS.
 * <p>
 *
 * @author    Robert Adams
 * @creation  7 August 01
 * @version   $Revision: 2$ $Date: 9/28/01 11:22:57 AM$
 * @since     Niagara 3.0
 */
public class ScptUtil
{

  /**
   * Construct the LonData component for the specified snvt type
   * from the data in the standard.lnml file.
   */
  public static BLonData getLonData(int configIndex)
  {
    XCpTypeDef typDef = XUtil.findCpType(0,configIndex);
    if(typDef==null) throw new RuntimeException("No data definition for configIndex " + configIndex);
    return typDef.getLonData(XUtil.getStandard());

  }


////////////////////////////////////////////////////////////////
//
////////////////////////////////////////////////////////////////


  public static boolean doesSelectContain(int ndx, String sel)
  {
    try
    {
      StringTokenizer st = new StringTokenizer(sel,",-~.");

      if(sel.indexOf('-')>0)
      {
        // process range
        int min = Integer.parseInt(st.nextToken());
        int max = Integer.parseInt(st.nextToken());
        return (ndx>=min) && (ndx<=max);
      }

      while(st.hasMoreTokens())
      {
        String s = st.nextToken();
        if(Integer.parseInt(s ) == ndx) return true;
      }
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
    return false;
  }

  public static int[] decomposeSelect(String sel)
  {
    try
    {
      StringTokenizer st = new StringTokenizer(sel,",-~.");

      if(sel.indexOf('-')>0)
      {
        // process range
        int min = Integer.parseInt(st.nextToken());
        int max = Integer.parseInt(st.nextToken());
        int[] a = new int[max-min+1];
        for(int i=0 ; i<a.length ; i++) a[i]=min+i;
        return a;
      }

      int[] a = new int[st.countTokens()];
      int i=0;
      while(st.hasMoreTokens())
      {
        String s = st.nextToken();
        a[i] = Integer.parseInt(s);
      }
      return a;
    }
    catch(Throwable e)
    {
      e.printStackTrace();
    }
    return new int[0];
  }


  public static boolean isInheritedType(int scptIndex)
  {
    for(int i=0 ; i<inhTypes.length ; i++)
      if(inhTypes[i]==scptIndex) return true;

    return false;
  }

  private static int[] inhTypes = {7,9,10,11,12,13,14,18,19,20,23,26,27,33,213,257,258,276};

  public static BLonComponent findScptForNv(BLonDevice dev, int nvSelect, int scptIndex)
  {
    // Find nci/cps with inherited types or of type SCPT_NV_TYPE
    BNetworkConfig[] ncis = dev.getNetworkConfigs();
    for(int i=0 ; i<ncis.length ; i++)
    {
      BNetworkConfig nci = ncis[i];
      BNcProps ncProps = nci.getNcProps();
      if(ncProps.getScope().equals(BLonConfigScope.nv) &&
         doesSelectContain(nvSelect, ncProps.getSelect()) &&
         ncProps.getConfigIndex()==scptIndex )
      {
        return nci;
      }
    }
    BConfigParameter[] cps = dev.getConfigParameters();
    for(int i=0 ; i<cps.length ; i++)
    {
      BConfigParameter cp = cps[i];
      BConfigProps ncProps = cp.getConfigProps();
      if(ncProps.getScope().equals(BLonConfigScope.nv) &&
         doesSelectContain(nvSelect, ncProps.getSelect()) &&
         ncProps.getConfigIndex()==scptIndex )
      {
        return cp;
      }
    }

    return null;
  }

  public static BLonComponent[] findInheritedConfigsForNv(BLonDevice dev, int nvIndex)
  {
  	Array<BLonComponent> a = new Array<>(BLonComponent.class);

    // Find nci/cps with inherited types
    BNetworkConfig[] ncis = dev.getNetworkConfigs();
    for(int i=0 ; i<ncis.length ; i++)
    {
      BNetworkConfig nci = ncis[i];
      BNcProps ncProps = nci.getNcProps();
      if(ncProps.getScope().equals(BLonConfigScope.nv) &&
         doesSelectContain(nvIndex, ncProps.getSelect()) )
      {
        if(isInheritedType(ncProps.getConfigIndex()) ||
           nci.getPropertyInParent().getFacets().getb("inherited",false))
        {
          a.add(nci);
        }
      }
    }

    int objectIndex = dev.getNetworkVariable(nvIndex).getNvProps().getObjectIndex();
    BConfigParameter[] cps = dev.getConfigParameters();
    for(int i=0 ; i<cps.length ; i++)
    {
      BConfigParameter cp = cps[i];
      BConfigProps ncProps = cp.getConfigProps();
      if(ncProps.getScope().equals(BLonConfigScope.nv))
      {
        if( doesSelectContain(nvIndex, ncProps.getSelect()) &&
            ( isInheritedType(ncProps.getConfigIndex()) ||
              cp.getPropertyInParent().getFacets().getb("inherited",false) ) )
        {
          a.add(cp);
        }
      }
      else if(ncProps.getScope().equals(BLonConfigScope.object))
      {
        BFacets f = cp.getPropertyInParent().getFacets();
        if( doesSelectContain(objectIndex, ncProps.getSelect()) &&
            f.getb("inherited",false) &&
            (f.geti("sourceNv",-1)==nvIndex) )
        {
          a.add(cp);
        }
      }
    }
    return a.trim();
  }

}


