/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.util;

import javax.baja.xml.XElem;

import javax.baja.bacnet.BacnetConst;

import com.tridium.bacnet.asn.AsnConst;
import com.tridium.bacnet.asn.AsnUtil;

/**
 * PropertyInfo provides information about a Bacnet property.
 *
 * @author Craig Gemmill
 * @version $Revision$ $Date$
 * @creation 30 May 02
 * @since Niagara 3 Bacnet 1.0
 */
public class PropertyInfo
  implements BacnetConst
{
  /**
   * Constructor.
   */
  public PropertyInfo(String name, int id, int asnType)
  {
    this.name = name;
    this.id = id;
    this.asnType = asnType;
    this.facet = false;
    this.facetControl = "no";
    this.pr = 8;
  }

  public PropertyInfo(String name, int id, int asnType, int pr)
  {
    this.name = name;
    this.id = id;
    this.asnType = asnType;
    this.facet = false;
    this.facetControl = "no";
    this.pr = pr;
  }
  /**
   * Constructor.
   */
  public PropertyInfo(XElem x, int objectPR)
  {
    name = x.get("n");
    id = x.geti("i");
    pr = x.geti("pr", objectPR);
    asnType = x.geti("a", AsnConst.ASN_UNKNOWN_PROPRIETARY);
    switch (asnType)
    {
      case ASN_NULL:
        type = "bacnet:BacnetNull";
        break;
      case ASN_BOOLEAN:
        type = "baja:Boolean";
        break;
      case ASN_UNSIGNED:
        type = "bacnet:BacnetUnsigned";
        break;
      case ASN_INTEGER:
        type = "baja:Integer";
        break;
      case ASN_REAL:
        type = "baja:Float";
        break;
      case ASN_DOUBLE:
        type = "baja:Double";
        break;
      case ASN_OCTET_STRING:
        type = "bacnet:BacnetOctetString";
        break;
      case ASN_CHARACTER_STRING:
        type = "baja:String";
        break;
      case ASN_BIT_STRING:
        type = "bacnet:BacnetBitString";
        break;
      case ASN_ENUMERATED:
        break; // handled later...
      case ASN_DATE:
        type = "bacnet:BacnetDate";
        break;
      case ASN_TIME:
        type = "bacnet:BacnetTime";
        break;
      case ASN_OBJECT_IDENTIFIER:
        type = "bacnet:BacnetObjectIdentifier";
        break;
      case ASN_ANY:
        type = "bacnet:BacnetAny";
        break;
      default:
        break;
    }
    facet = x.getb("f", false);
    facetControl = x.get("c", "no");
    if (isEnum())
    {
      extensible = x.getb("e", false);
      type = x.get("t");
      facetControl = "enum";
    }
    else if (isBitString())
    {
      bs = x.get("b", null);
    }
    else if (isConstructed())
    {
      type = x.get("t");
    }
    else if (isArray())
    {
      type = x.get("t");
      size = x.geti("s", -1);
    }
    else if (isList())
    {
      type = x.get("t");
    }
    else if (isChoice())
    {
      type = x.get("t");
    }
  }

  /**
   * Basic constructor.
   */
  public PropertyInfo(String name,
                      int id,
                      int asnType,
                      boolean facet,
                      String facetControl)
  {
    this.name = name;
    this.id = id;
    this.asnType = asnType;
    this.facet = facet;
    this.facetControl = facetControl;
  }

  /**
   * Enum constructor.
   */
  public PropertyInfo(String name,
                      int id,
                      int asnType,
                      boolean extensible,
                      String type,
                      boolean facet,
                      String facetControl)
  {
	this(name, id, asnType, facet, facetControl);
    this.extensible = extensible;
    this.type = type;
    this.facet = facet;
    this.facetControl = facetControl;
  }

  /**
   * Constructor.
   *
   * @param s
   * @param bitString if true, s is used for bit string name
   *                  if false, s is used as list/constructed data type.
   */
  public PropertyInfo(String name,
                      int id,
                      int asnType,
                      String s,
                      boolean bitString,
                      boolean facet,
                      String facetControl)
  {
	this(name, id, asnType, facet, facetControl);
    if (bitString)
      this.bs = s;
    else
      this.type = s;
  }

  /**
   * Array constructor.
   */
  public PropertyInfo(String name,
                      int id,
                      int asnType,
                      String type,
                      int size,
                      boolean facet,
                      String facetControl)
  {
	this(name,id,asnType,false,type,facet,facetControl);
    this.size = size;
  }


////////////////////////////////////////////////////////////
//  Access
////////////////////////////////////////////////////////////

  public String getName()
  {
    return name;
  }

  public int getId()
    { return id; }

  public int getPr()
  { return pr; }

  public int getAsnType()
  {
    return asnType;
  }

  public String getDataType()
  {
    return AsnUtil.getAsnTypeName(asnType);
  }

  public boolean isExtensible()
  {
    if (!isEnum()) throw new IllegalStateException();
    return extensible;
  }

  public String getBitStringName()
  {
    if (!isBitString()) throw new IllegalStateException();
    return bs;
  }

  public String getType()
  {
//    switch (asnType)
//    {
//      case ASN_ENUMERATED:
//      case AsnConst.ASN_CONSTRUCTED_DATA:
//      case AsnConst.ASN_BACNET_ARRAY:
//      case AsnConst.ASN_BACNET_LIST:
    return type;
//      default:
//        throw new IllegalStateException();
//    }
  }

  public int getSize()
  {
    if (!isArray()) throw new IllegalStateException();
    return size;
  }

  public boolean isEnum()
  {
    return asnType == ASN_ENUMERATED;
  }

  public boolean isBitString()
  {
    return asnType == ASN_BIT_STRING;
  }

  public boolean isConstructed()
  {
    return asnType == AsnConst.ASN_CONSTRUCTED_DATA;
  }

  public boolean isArray()
  {
    return asnType == AsnConst.ASN_BACNET_ARRAY;
  }

  public boolean isList()
  {
    return asnType == AsnConst.ASN_BACNET_LIST;
  }

  public boolean isChoice()
  {
    return asnType == AsnConst.ASN_CHOICE;
  }

  public boolean isFacet()
  {
    return facet;
  }

  public String getFacetControl()
  {
    return facetControl;
  }

  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("\n n=" + name)
      .append("\n i=" + id)
      .append("\n a=" + asnType);
    if (facet)
      sb.append("\n f=" + facet);
    sb.append("\n c=" + facetControl);
    sb.append("\n t=" + type);
    if (isEnum())
    {
      sb.append("\n e=" + extensible);
    }
    else if (isBitString())
    {
      sb.append("\n b=" + bs);
    }
    else if (isArray())
    {
      sb.append("\n s=" + size);
    }
    return sb.toString();
  }

  public boolean isAws()
  {
    return type != null && type.indexOf("Aws") >= 0;
  }
////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////

  private String name;
  private int id;
  private int pr;
  private int asnType;
  private boolean extensible;
  private String bs;
  private String type;
  private int size;
  private boolean facet;
  private String facetControl;
}
