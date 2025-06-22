/*
 * Copyright 2011 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.ndriver.datatypes;

import javax.baja.net.Http;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.units.UnitDatabase;

import com.tridium.ndriver.comm.IComm;
import com.tridium.ndriver.comm.ICommListener;
import com.tridium.ndriver.comm.ILinkLayer;
import com.tridium.ndriver.comm.LinkMessage;
import com.tridium.ndriver.comm.NComm;
import com.tridium.ndriver.comm.NLinkMessageFactory;
import com.tridium.ndriver.comm.http.HttpComm;

/**
 * Communications configuration parameters for http link layer.
 *
 * @author Robert A Adams
 * @creation Nov 4, 2011
 */
@NiagaraType
@NiagaraProperty(
  name = "useTls",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.READONLY | Flags.HIDDEN
)
@NiagaraProperty(
  name = "address",
  type = "BIpAddress",
  defaultValue = "new BIpAddress(\"local\", Http.DEFAULT_HTTP_PORT)"
)
@NiagaraProperty(
  name = "connectionTimeout",
  type = "int",
  defaultValue = "0",
  facets = @Facet("BFacets.makeInt(UnitDatabase.getUnit(\"millisecond\"))")
)
public class BHttpCommConfig
  extends BCommConfig
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.ndriver.datatypes.BHttpCommConfig(2448688153)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "useTls"

  /**
   * Slot for the {@code useTls} property.
   * @see #getUseTls
   * @see #setUseTls
   */
  public static final Property useTls = newProperty(Flags.READONLY | Flags.HIDDEN, false, null);

  /**
   * Get the {@code useTls} property.
   * @see #useTls
   */
  public boolean getUseTls() { return getBoolean(useTls); }

  /**
   * Set the {@code useTls} property.
   * @see #useTls
   */
  public void setUseTls(boolean v) { setBoolean(useTls, v, null); }

  //endregion Property "useTls"

  //region Property "address"

  /**
   * Slot for the {@code address} property.
   * @see #getAddress
   * @see #setAddress
   */
  public static final Property address = newProperty(0, new BIpAddress("local", Http.DEFAULT_HTTP_PORT), null);

  /**
   * Get the {@code address} property.
   * @see #address
   */
  public BIpAddress getAddress() { return (BIpAddress)get(address); }

  /**
   * Set the {@code address} property.
   * @see #address
   */
  public void setAddress(BIpAddress v) { set(address, v, null); }

  //endregion Property "address"

  //region Property "connectionTimeout"

  /**
   * Slot for the {@code connectionTimeout} property.
   * @see #getConnectionTimeout
   * @see #setConnectionTimeout
   */
  public static final Property connectionTimeout = newProperty(0, 0, BFacets.makeInt(UnitDatabase.getUnit("millisecond")));

  /**
   * Get the {@code connectionTimeout} property.
   * @see #connectionTimeout
   */
  public int getConnectionTimeout() { return getInt(connectionTimeout); }

  /**
   * Set the {@code connectionTimeout} property.
   * @see #connectionTimeout
   */
  public void setConnectionTimeout(int v) { setInt(connectionTimeout, v, null); }

  //endregion Property "connectionTimeout"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHttpCommConfig.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Empty constructor
   */
  public BHttpCommConfig() {}

  /**
   * Create the HttpCommConfig object with the provided ICommListener default
   * listener.
   *
   * @param defaultListener An implementation of @link{ICommListener} to use to
   *                        listen for specific messages.
   * @deprecated since Niagara 3.8. NCCB-5201: ICommListener parameter is
   * ignored by IComm. Use base @link{BHttpCommConfig()} constructor and assign
   * listener after BCommConfig IComm instance is initialized by started(). To
   * be removed in a future release, see NCCB-43704.
   */
  @Deprecated
  public BHttpCommConfig(ICommListener defaultListener)
  {
    super();
    setDefaultListener(defaultListener);
  }

  /**
   * @return instance of {@code HttpComm}
   */
  @Override
  public IComm createComm()
  {
    return new HttpComm(this, defaultListener);
  }


  /**
   * BHttpComm has no linklayer - return null
   */
  @Override
  public final ILinkLayer makeLinkLayer(NComm comm)
  {
    return null;
  }

  /**
   * Override point for subclasses to provide custom LinkMessage factory.
   */
  @Override
  protected NLinkMessageFactory makeLinkMessageFactory()
  {
    // dummy message factory 
    return new NLinkMessageFactory(0, 0)
    {
      @Override
      protected LinkMessage createLinkMessage()
      {
        return null;
      }
    };
  }

}
