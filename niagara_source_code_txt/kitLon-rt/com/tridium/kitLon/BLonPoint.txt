/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package com.tridium.kitLon;

import java.util.HashMap;

import javax.baja.lonworks.enums.BLonNvDirection;
import javax.baja.lonworks.enums.BLonSnvtType;
import javax.baja.lonworks.londata.*;
import javax.baja.lonworks.util.SnvtUtil;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.status.*;
import javax.baja.sync.Transaction;
import javax.baja.sys.*;

import com.tridium.lonworks.device.NvDev;
import com.tridium.lonworks.local.BPseudoNV;
import com.tridium.lonworks.local.BPseudoNvContainer;

/**
 * BLonPoint provides a linkable Snvt source with a programmable
 * snvt type.
 *
 * @author    Robert Adams
 * @creation  27 April 2006
 * @version   $Revision: 7$ $Date: 3/3/2004 8:48:19 AM$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "snvt",
  type = "BLonSnvtType",
  defaultValue = "BLonSnvtType.SnvtTemp"
)
@NiagaraProperty(
  name = "nvo",
  type = "BPseudoNV",
  defaultValue = "new BPseudoNV(BLonSnvtType.SNVT_TEMP, BLonNvDirection.output)"
)
public class BLonPoint
  extends BPseudoNvContainer
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitLon.BLonPoint(453449059)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "snvt"

  /**
   * Slot for the {@code snvt} property.
   * @see #getSnvt
   * @see #setSnvt
   */
  public static final Property snvt = newProperty(0, BLonSnvtType.SnvtTemp, null);

  /**
   * Get the {@code snvt} property.
   * @see #snvt
   */
  public BLonSnvtType getSnvt() { return (BLonSnvtType)get(snvt); }

  /**
   * Set the {@code snvt} property.
   * @see #snvt
   */
  public void setSnvt(BLonSnvtType v) { set(snvt, v, null); }

  //endregion Property "snvt"

  //region Property "nvo"

  /**
   * Slot for the {@code nvo} property.
   * @see #getNvo
   * @see #setNvo
   */
  public static final Property nvo = newProperty(0, new BPseudoNV(BLonSnvtType.SNVT_TEMP, BLonNvDirection.output), null);

  /**
   * Get the {@code nvo} property.
   * @see #nvo
   */
  public BPseudoNV getNvo() { return (BPseudoNV)get(nvo); }

  /**
   * Set the {@code nvo} property.
   * @see #nvo
   */
  public void setNvo(BPseudoNV v) { set(nvo, v, null); }

  //endregion Property "nvo"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonPoint.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BLonPoint()
  {
  }

  /**
   * Reinitialize timer if updateTime changes
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);

    if (!isRunning()) return;

    if(p==snvt)
    {
      verifyLonData();
    }
    else if(p.getType().is(BStatusValue.TYPE))
    {
      updateSnvt(p,cx);
    }
  }

  public void started()
    throws Exception
  {
    super.started();
    verifyLonData();
    updateSnvt();
  }
  public void stopped()
    throws Exception
  {
    super.stopped();
    if(valHash!=null) valHash.clear();
    valHash = null;
  }

  public void checkRename(Property property, String newName, Context context)
  {
    throw new IllegalNameException("kitLon", "point.rename", null );
  }

////////////////////////////////////////////////////////////////
// Update
////////////////////////////////////////////////////////////////
  private void updateSnvt(Property p, Context c)
  {
    setNvPrimitive(p,(BStatusValue)get(p));
  }
  private void updateSnvt()
  {
    Property[] pa = getPropertiesArray();

    for(int i=0 ; i<pa.length ; i++)
    {
      BObject o = get(pa[i]);
      if( !(o instanceof BStatusValue) ) continue;
      setNvPrimitive(pa[i],(BStatusValue)o);
    }
  }

  private void setNvPrimitive(Property p, BStatusValue val)
  {
    // Check for change of value
    BStatusValue oldVal = getValHash().get(p);
    if(oldVal!=null && oldVal.equivalent(val))  return;

    getValHash().put(p,(BStatusValue)val.newCopy());

    String vName = p.getName();
    BComponent c = getNvo();
    int n;
    while((n = vName.indexOf(DELIMITER))>0)
    {
      c = (BComponent)c.get(vName.substring(0,n));
      vName = vName.substring(n+1);
    }

    BLonPrimitive lprim = (BLonPrimitive)c.get(vName);
    BLonElementQualifiers e = LonFacetsUtil.getQualifiers(c.getSlot(vName).getFacets());

    if( val instanceof BStatusNumeric)
      c.set(vName, lprim.makeFromDouble(((BStatusNumeric)val).getValue(), e));
    else if( val instanceof BStatusBoolean)
      c.set(vName, lprim.makeFromBoolean(((BStatusBoolean)val).getValue()));
    else if( val instanceof BStatusEnum)
      c.set(vName, lprim.makeFromEnum(((BStatusEnum)val).getValue()));
    else if( val instanceof BStatusString)
      c.set(vName, lprim.makeFromString(((BStatusString)val).getValue()));
  }

  private HashMap<Property,BStatusValue> getValHash()
  {
    if(valHash==null) valHash = new HashMap<>();
    return valHash;
  }
////////////////////////////////////////////////////////////////
// StatusValue creation
////////////////////////////////////////////////////////////////
  private void verifyLonData()
  {
    int snvt = getSnvt().getOrdinal();
    BLonData ld = SnvtUtil.getLonData(snvt);
    if(ld==null) return;

    BPseudoNV nvo = getNvo();
    if( nvo.getNvProps().getSnvtType()== snvt &&
        nvo.getData().hasEquivalentElements(ld))
    {
      return;
    }

    nvo.getNvProps().setSnvtType(snvt);
    nvo.setData(ld);

    // Get list of inputs for current londata
    Array<InputPrimitive> a = new Array<>(InputPrimitive.class);
    parsePrimitives(a,ld, "");
    InputPrimitive[] prims = a.trim();

    // Add input status values as needed
    Context tx = Transaction.start(this, new BasicContext());
    Property[] pa = getPropertiesArray();

    for(int i=0 ; i<prims.length ; i++)
    {
      Property prop = findAndRemoveProperty(prims[i], pa, tx);
      if( prop == null)
      {
        add(prims[i].name,prims[i].val,0/*flags*/,prims[i].facets,tx);
      }
    }
    removeRemainingProps(pa, tx);

    // Check if links to nvs need to be removed
    validateNvLinks(tx);

    // Make additions/deletions
    try { Transaction.end(this, tx); }
    catch(Exception e) { e.printStackTrace(); }
  }

  private void  validateNvLinks(Context tx)
  {
    // Validate all the links from nvo and delete ones that are no longer valid
    Knob[] ka = getKnobs(nvo);
    for(int i=0 ; i<ka.length ; i++)
    {
      LinkCheck lc = NvDev.doNvCheckLink(this, nvo, ka[i].getSourceComponent(), ka[i].getTargetSlot(), null);
      if(lc.isValid())continue;

      // Delete invalid links
      BLink lnk = ka[i].getLink();
      ka[i].getTargetComponent().remove(lnk.getPropertyInParent(),tx);
      lnk.deactivate();
    }
  }

  private void parsePrimitives(Array<InputPrimitive> a, BLonData ld, String pre)
  {
    SlotCursor<Property> c = ld.getProperties();
    while(c.nextObject())
    {
      Property p = c.property();
      BObject o = c.get();
      String name = (pre.length()>0) ? (pre + DELIMITER + p.getName()) : p.getName();
      if(o instanceof BLonData) parsePrimitives(a, (BLonData)o, name);
      else if(o instanceof BLonPrimitive)
      {
        BStatusValue val = null;
        if(o instanceof BLonDouble) val = new BStatusNumeric();
        else if(o instanceof BLonFloat)     val = new BStatusNumeric();
        else if(o instanceof BLonInteger)   val = new BStatusNumeric();
        else if(o instanceof BLonString)    val = new BStatusString();
        else if(o instanceof BLonBoolean)   val = new BStatusBoolean();
        else if(o instanceof BLonEnum)      val = new BStatusNumeric();
        else if(o instanceof BLonByteArray) val = new BStatusString();
        else throw new IllegalStateException("Unsupported LonPrimitive");

        a.add(new InputPrimitive(val,name,p.getFacets()));
      }
    }
  }

  static class InputPrimitive
  {
    InputPrimitive (BStatusValue v, String n,BFacets f) { val=v; name=n; facets=f; }
    BStatusValue val;
    String name;
    BFacets facets;
  }

  private Property findAndRemoveProperty(InputPrimitive prim, Property[] props,Context c)
  {
    String name = prim.name;

    for(int i=0 ; i<props.length ; i++)
    {
      Property p = props[i];
      if( (p == null) || !p.getName().equals(name) || p.isFrozen() ) continue;

      // If the name is used but the types are different remove
      // now so same name property can be added with new type.
      if(p.getType() != prim.val.getType())
      {
      //  dev.log().fine("remove property because of type change " + p.getName());
        remove(p, c);
        // Return null so new property will be added
        p = null;
      }

      // Property found remove from prop array
      props[i] = null;
      return p;
    }
    return null;
  }
  private void removeRemainingProps(Property[] props, Context c)
  {
    for(int i=0 ; i<props.length ; i++)
    {
      Property p = props[i];
      if(p==null || p.isFrozen()) continue;
      // Remove any remaining status values.
      if( p.getType().is(BStatusValue.TYPE) )
      {
        remove(p,c);
      }
    }
  }


////////////////////////////////////////////////////////////////
// Icon
////////////////////////////////////////////////////////////////

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.make("module://lonworks/com/tridium/lonworks/ui/icons/nv.png");

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private static final String DELIMITER = "_";

  private HashMap<Property,BStatusValue> valHash;

}
