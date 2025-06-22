/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.user.BUser;

import javax.baja.bacnet.BBacnetDevice;
import javax.baja.bacnet.BacnetConst;
import javax.baja.bacnet.datatypes.BBacnetObjectIdentifier;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.point.BBacnetProxyExt;

public class PollListEntry
  implements Context
{
  /**
   * Constructor.
   * Used for proxy point primary value.
   *
   * @param pt
   */
  public PollListEntry(BBacnetProxyExt pt)
  {
    objectId = pt.getObjectId();
    propertyId = pt.getPropertyId().getOrdinal();
    propertyArrayIndex = pt.getPropertyArrayIndex();
    device = pt.device();
    pollable = pt;
    context = pointCx;
  }

  /**
   * Constructor.
   * Used for proxy point additional metadata from same object.
   *
   * @param pt
   * @param pId
   */
  public PollListEntry(BBacnetProxyExt pt, int pId)
  {
    objectId = pt.getObjectId();
    propertyId = pId;
    propertyArrayIndex = pt.getPropertyArrayIndex();
    device = pt.device();
    pollable = pt;
    context = null;
  }

  /**
   * Constructor.
   * Used for BBacnetObject polled properties.
   *
   * @param oid
   * @param pid
   * @param dev
   * @param p
   */
  public PollListEntry(BBacnetObjectIdentifier oid,
                       int pid,
                       BBacnetDevice dev,
                       BIBacnetPollable p)
  {
    objectId = oid;
    propertyId = pid;
    propertyArrayIndex = BacnetConst.NOT_USED;
    device = dev;
    pollable = p;
    context = null;
  }

  /**
   * Metadata Constructor.
   * Used for any additional poll properties, such as event enrollment
   * objects monitoring a proxy point.
   *
   * @param oid
   * @param pid
   * @param ndx
   * @param dev
   * @param p
   */
  public PollListEntry(BBacnetObjectIdentifier oid,
                       int pid,
                       int ndx,
                       BBacnetDevice dev,
                       BIBacnetPollable p)
  {
    objectId = oid;
    propertyId = pid;
    propertyArrayIndex = ndx;
    device = dev;
    pollable = p;
    context = null;
  }

  /**
   * Full Constructor.
   * Used for virtual components.
   *
   * @param oid
   * @param pid
   * @param ndx
   * @param dev
   * @param p
   * @param cx
   */
  public PollListEntry(BBacnetObjectIdentifier oid,
                       int pid,
                       int ndx,
                       BBacnetDevice dev,
                       BIBacnetPollable p,
                       Context cx)
  {
    objectId = oid;
    propertyId = pid;
    propertyArrayIndex = ndx;
    device = dev;
    pollable = p;
    context = cx;
  }

  /**
   * @return the objectId
   */
  public final BBacnetObjectIdentifier getObjectId()
  {
    return objectId;
  }

  /**
   * @return the propertyId
   */
  public final int getPropertyId()
  {
    return propertyId;
  }

  /**
   * @return the propertyArrayIndex
   */
  public final int getPropertyArrayIndex()
  {
    return propertyArrayIndex;
  }

  /**
   * @return the device
   */
  public final BBacnetDevice getDevice()
  {
    return device;
  }

  /**
   * @return the address
   */
  public final int getAddressHash()
  {

    return device == null ? 0 : device.getAddress().hash();
  }

  /**
   * @return the pollable
   */
  public final BIBacnetPollable getPollable()
  {
    return pollable;
  }

  /**
   * @return the data size for this poll list entry
   */
  public final int getDataSize()
  {
    return dataSize;
  }

  /**
   * Set the data size for this poll list entry.
   *
   * @param dataSize
   */
  public final void setDataSize(int dataSize)
  {
    this.dataSize = dataSize;
  }

  public final void doubleDataSize(int max)
  {
    this.dataSize = Math.min(max, dataSize * 2);
  }

  /**
   * @return the context if not null, otherwise return this.
   */
  public final Context getContext()
  {
    return (context == null) ? this : context;
  }

  /**
   * @param context the context to set
   */
  public final void setContext(Context context)
  {
    this.context = context;
  }

  /**
   * @return the pollList
   */
  public final PollList getPollList()
  {
    return pollList;
  }

  /**
   * @param pollList the pollList to set
   */
  public final void setPollList(PollList pollList)
  {
    this.pollList = pollList;
  }

  public boolean equals(Object o)
  {
    if (o == null) return false;
    if (!(o instanceof PollListEntry)) return false;
    PollListEntry ple = (PollListEntry)o;

    // does not check: device, dataSize, pollList
    if (propertyId != ple.propertyId) return false;
    if (propertyArrayIndex != ple.propertyArrayIndex) return false;
    if (context == null)
    {
      if (ple.context != null) return false;
    }
    else
    {
      if (!context.equals(ple.context)) return false;
    }
    if (objectId == null)
    {
      if (ple.objectId != null) return false;
    }
    else
    {
      if (ple.objectId == null) return false;
      if (objectId.hashCode() != ple.objectId.hashCode()) return false;
    }
    if (pollable == null)
    {
      if (ple.pollable != null) return false;
    }
    else
    {
      if (!pollable.equals(ple.pollable)) return false;
    }
    return true;
  }

  public int hashCode()
  {
    return 1;
  }

  public String toString()
  {
    return string(false);
  }

  public String debugString()
  {
    return string(true);
  }

  private String string(boolean debug)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(objectId.toString()).append(' ')
      .append(BBacnetPropertyIdentifier.tag(propertyId));
    if (propertyArrayIndex >= 0)
      sb.append(" [").append(propertyArrayIndex).append(']');

    if (debug)
      sb.append(" in " + device.getName())
        .append(" [").append(getAddressHash()).append(']')
        .append(" -> " + pollable)
        .append(" {").append(dataSize).append("}; ")
        .append(context);

    return sb.toString();
  }

  ////////////////////////////////////////////////////////////////
  // Context
  ////////////////////////////////////////////////////////////////

  /**
   * If this Context wraps another Context, then return
   * the base Context.  Otherwise return null.
   */
  public Context getBase()
  {
    return context;
  }

  /**
   * Get the user for the context of this operation,
   * or null if no user information is available.
   */
  public BUser getUser()
  {
    return (context == null) ? null : context.getUser();
  }

  /**
   * Get the facets table.  This should never be null.
   * Return BFacets.DEFAULT if no facets are available.
   */
  public BFacets getFacets()
  {
    return (context == null) ? null : context.getFacets();
  }

  /**
   * Get a facet value by name, or null.
   */
  public BObject getFacet(String name)
  {
    return (context == null) ? null : context.getFacet(name);
  }

  /**
   * Get the language code for the context operation.
   * This method should never return null.  As a default
   * return Sys.getLanguage().
   */
  public String getLanguage()
  {
    return (context == null) ? null : context.getLanguage();
  }

  private BBacnetObjectIdentifier objectId;
  private int propertyId;
  private int propertyArrayIndex;
  private BBacnetDevice device;
  private BIBacnetPollable pollable;
  private int dataSize = DEFAULT_DATASIZE;

  private Context context;
  private PollList pollList;

  public static final int DEFAULT_DATASIZE = 11;


  /**
   * PointContext: used for proxy point primary value.
   */
  public static final Context pointCx = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:pointCx";
    }
  };

  /**
   * ForceContext: used for proxy point force reads.
   */
  public static final Context forceCx = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:forceCx";
    }
  };

  /**
   * MetaContext: used for proxy point metadata.
   */
  public static final Context metaCx = new BasicContext()
  {
    public boolean equals(Object obj)
    {
      return this == obj;
    }

    public int hashCode()
    {
      return 1;
    }

    public String toString()
    {
      return "Bacnet:metaCx";
    }
  };
}
