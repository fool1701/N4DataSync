/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks;

import java.util.logging.*;

import javax.baja.lonworks.datatypes.BImportParameters;
import javax.baja.lonworks.datatypes.BLearnNvParameters;
import javax.baja.lonworks.datatypes.BNvConfigData;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraTopic;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

import com.tridium.lonworks.device.BLonLearnNvJob;
import com.tridium.lonworks.device.DynaDev;
import com.tridium.lonworks.netmgmt.BLonNetmgmt;
import com.tridium.lonworks.xml.LonXMLReader;
import com.tridium.lonworks.xml.XLonInterfaceFile;

/**
 * BDynamicDevice adds support to BLonDevice for dynamically create
 * components from the devices self documentation or an xml file.
 *
 * @author    Robert Adams
 * @creation  22 June 01
 * @version   $Revision: 5$ $Date: 10/18/01 2:56:40 PM$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 The name of xml file used by importXml action.
 */
@NiagaraProperty(
  name = "xmlFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.READONLY
)
/*
 Create/update components from device self documentation
 */
@NiagaraAction(
  name = "learnNv",
  flags = Flags.HIDDEN
)
/*
 Create/update components from device self documentation
 */
@NiagaraAction(
  name = "learnNv_",
  parameterType = "BLearnNvParameters",
  defaultValue = "new BLearnNvParameters()",
  returnType = "BOrd"
)
/*
 Remove unlinked/unproxied nvs
 */
@NiagaraAction(
  name = "trim"
)
@NiagaraAction(
  name = "importXml",
  parameterType = "BImportParameters",
  defaultValue = "new BImportParameters()",
  flags = Flags.HIDDEN
)
@NiagaraTopic(
  name = "dynamicOpComplete"
)
public class BDynamicDevice
  extends BLonDevice
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.BDynamicDevice(152784700)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "xmlFile"

  /**
   * Slot for the {@code xmlFile} property.
   * The name of xml file used by importXml action.
   * @see #getXmlFile
   * @see #setXmlFile
   */
  public static final Property xmlFile = newProperty(Flags.READONLY, BOrd.NULL, null);

  /**
   * Get the {@code xmlFile} property.
   * The name of xml file used by importXml action.
   * @see #xmlFile
   */
  public BOrd getXmlFile() { return (BOrd)get(xmlFile); }

  /**
   * Set the {@code xmlFile} property.
   * The name of xml file used by importXml action.
   * @see #xmlFile
   */
  public void setXmlFile(BOrd v) { set(xmlFile, v, null); }

  //endregion Property "xmlFile"

  //region Action "learnNv"

  /**
   * Slot for the {@code learnNv} action.
   * Create/update components from device self documentation
   * @see #learnNv()
   */
  public static final Action learnNv = newAction(Flags.HIDDEN, null);

  /**
   * Invoke the {@code learnNv} action.
   * Create/update components from device self documentation
   * @see #learnNv
   */
  public void learnNv() { invoke(learnNv, null, null); }

  //endregion Action "learnNv"

  //region Action "learnNv_"

  /**
   * Slot for the {@code learnNv_} action.
   * Create/update components from device self documentation
   * @see #learnNv_(BLearnNvParameters parameter)
   */
  public static final Action learnNv_ = newAction(0, new BLearnNvParameters(), null);

  /**
   * Invoke the {@code learnNv_} action.
   * Create/update components from device self documentation
   * @see #learnNv_
   */
  public BOrd learnNv_(BLearnNvParameters parameter) { return (BOrd)invoke(learnNv_, parameter, null); }

  //endregion Action "learnNv_"

  //region Action "trim"

  /**
   * Slot for the {@code trim} action.
   * Remove unlinked/unproxied nvs
   * @see #trim()
   */
  public static final Action trim = newAction(0, null);

  /**
   * Invoke the {@code trim} action.
   * Remove unlinked/unproxied nvs
   * @see #trim
   */
  public void trim() { invoke(trim, null, null); }

  //endregion Action "trim"

  //region Action "importXml"

  /**
   * Slot for the {@code importXml} action.
   * @see #importXml(BImportParameters parameter)
   */
  public static final Action importXml = newAction(Flags.HIDDEN, new BImportParameters(), null);

  /**
   * Invoke the {@code importXml} action.
   * @see #importXml
   */
  public void importXml(BImportParameters parameter) { invoke(importXml, parameter, null); }

  //endregion Action "importXml"

  //region Topic "dynamicOpComplete"

  /**
   * Slot for the {@code dynamicOpComplete} topic.
   * @see #fireDynamicOpComplete
   */
  public static final Topic dynamicOpComplete = newTopic(0, null);

  /**
   * Fire an event for the {@code dynamicOpComplete} topic.
   * @see #dynamicOpComplete
   */
  public void fireDynamicOpComplete(BValue event) { fire(dynamicOpComplete, event, null); }

  //endregion Topic "dynamicOpComplete"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BDynamicDevice.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


////////////////////////////////////////////////////////////
//  LonDevice api
////////////////////////////////////////////////////////////



  /**
   * Returns true to indicates that programId may be changed
   * during commissioning.
  public boolean programIdChanges() { return true; }
   */

  /**  If there is a non-null xmlFile and no nvs then importXml. */
  public void started()
    throws Exception
  {
    super.started();
    
    if( (getXmlFile()!=BOrd.NULL) && 
        (getNetworkVariables().length==0) && 
        getNeuronIdAddress().isZero() )
    {    
      doImportXml(new BImportParameters(false,getLonNetwork().netmgmt().getUseLonObjects()));
    }  
  }
  
////////////////////////////////////////////////////////////
//  Learn Nvs
////////////////////////////////////////////////////////////
  /**
   * Overridden here to provide the default value for the
   * learnNv action.
   */
  public BValue getActionParameterDefault(Action action)
  {
    if (action == learnNv_)
    {
      BLonNetmgmt nm = getLonNetwork().netmgmt();
      getComponentSpace().update(nm,1);
      return new BLearnNvParameters(nm.getUseLonObjects());
    }
    return super.getActionParameterDefault(action);
  }
  
  /**
   * DEPRECATED
   */
  public void doLearnNv()
  {
    if(getXmlFile()!=BOrd.NULL)
      throw new LocalizableRuntimeException("lonworks","learnNv.block");

    BLearnNvParameters param = new BLearnNvParameters(getLonNetwork().netmgmt().getUseLonObjects());
    new BLonLearnNvJob(getLonNetwork(), this, param).submit(null);
  }
  
  /**
   * Build database representation from devices self documentation. 
   * Use user specified parameters
   */
  public BOrd doLearnNv_(BLearnNvParameters param)
  {
    if(getXmlFile()!=BOrd.NULL)
      throw new LocalizableRuntimeException("lonworks","learnNv.block");

    return new BLonLearnNvJob(getLonNetwork(), this, param).submit(null);
  }

////////////////////////////////////////////////////////////
//  Xml support
////////////////////////////////////////////////////////////

  /**
   * Read the xml file and update device components.  Add nvs,
   * ncis, cps and mtags as needed.
   */
  public void doImportXml(BImportParameters param)
  {
    if(getXmlFile().isNull()) return;
    XLonInterfaceFile root = LonXMLReader.decode(getXmlFile());
    DynaDev.importXLon(this, root, param);
  }
  
  /** Allows specification of log when importing an unmounted DynamicDevice. */
  public final void doImportXml(BImportParameters param, Logger log)
  {
    this.log = log;
    doImportXml(param);
  }
  // This was removed. public void importXLon(XLonInterfaceFile root, BImportParameters param)

  /**
   * Not currently implemented.
   */
  public void doExportXml()
  {
    log().fine("doExportXml not implemented");
  }

 
  /**
   * Remove all nvs that do not have a proxy, are not linked and are not bound.
   */
  public final void doTrim()
  {
    BINetworkVariable[] nvs =  getNetworkVariables();
    for(int i=0 ; i<nvs.length ; i++)
    {
      if(nvs[i]==null || !nvs[i].isNetworkVariable()) continue;
      
      BNetworkVariable nv = (BNetworkVariable)nvs[i];
      BComponent p = (BComponent)nv.getParent();
      Property nvProp = nv.getPropertyInParent();
      BNvConfigData nvCfg = nv.getNvConfigData();
      if( !nv.hasProxies() &&
          !nvCfg.isBoundNv() &&
          ( (nvCfg.isInput() && p.getLinks(nvProp).length==0) ||
            (nvCfg.isOutput() && p.getKnobs(nvProp).length==0) ) ) 
      {
         p.remove(nv);
         log().fine("trim " + getDisplayName(null) + ":" + nv.getDisplayName(null)); 
      }   
    }
  }
}
