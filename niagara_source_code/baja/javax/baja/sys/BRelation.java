/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.Tags;

import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.tag.RelationTags;
import com.tridium.util.LinkUtil;

/**
 * EntitySpace
 *
 * @author Andy Saunders
 * @creation 4/7/14
 * @since Niagara 4.0
 */

@NiagaraType
/*
 relation id
 */
@NiagaraProperty(
  name = "relationId",
  type = "String",
  defaultValue = "",
  facets = {
    @Facet(name = "BFacets.FIELD_EDITOR", value = "\"workbench:RelationIdFE\""),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "\"tagdictionary:RelationIdEditor\"")
  }
)
@NiagaraProperty(
  name = "inbound",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN + Flags.READONLY
)
/*
 Facets for the relation tags
 */
@NiagaraProperty(
  name = "relationTags",
  type = "BFacets",
  defaultValue = "BFacets.NULL"
)
/*
 Ord of the other entity.  Named sourceOrd for backward compatibility with BLink
 */
@NiagaraProperty(
  name = "sourceOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL"
)
public class BRelation
  extends BStruct  implements Relation
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.sys.BRelation(3145293786)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "relationId"

  /**
   * Slot for the {@code relationId} property.
   * relation id
   * @see #getRelationId
   * @see #setRelationId
   */
  public static final Property relationId = newProperty(0, "", BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, "workbench:RelationIdFE"), BFacets.make(BFacets.UX_FIELD_EDITOR, "tagdictionary:RelationIdEditor")));

  /**
   * Get the {@code relationId} property.
   * relation id
   * @see #relationId
   */
  public String getRelationId() { return getString(relationId); }

  /**
   * Set the {@code relationId} property.
   * relation id
   * @see #relationId
   */
  public void setRelationId(String v) { setString(relationId, v, null); }

  //endregion Property "relationId"

  //region Property "inbound"

  /**
   * Slot for the {@code inbound} property.
   * @see #getInbound
   * @see #setInbound
   */
  public static final Property inbound = newProperty(Flags.HIDDEN + Flags.READONLY, false, null);

  /**
   * Get the {@code inbound} property.
   * @see #inbound
   */
  public boolean getInbound() { return getBoolean(inbound); }

  /**
   * Set the {@code inbound} property.
   * @see #inbound
   */
  public void setInbound(boolean v) { setBoolean(inbound, v, null); }

  //endregion Property "inbound"

  //region Property "relationTags"

  /**
   * Slot for the {@code relationTags} property.
   * Facets for the relation tags
   * @see #getRelationTags
   * @see #setRelationTags
   */
  public static final Property relationTags = newProperty(0, BFacets.NULL, null);

  /**
   * Get the {@code relationTags} property.
   * Facets for the relation tags
   * @see #relationTags
   */
  public BFacets getRelationTags() { return (BFacets)get(relationTags); }

  /**
   * Set the {@code relationTags} property.
   * Facets for the relation tags
   * @see #relationTags
   */
  public void setRelationTags(BFacets v) { set(relationTags, v, null); }

  //endregion Property "relationTags"

  //region Property "sourceOrd"

  /**
   * Slot for the {@code sourceOrd} property.
   * Ord of the other entity.  Named sourceOrd for backward compatibility with BLink
   * @see #getSourceOrd
   * @see #setSourceOrd
   */
  public static final Property sourceOrd = newProperty(0, BOrd.NULL, null);

  /**
   * Get the {@code sourceOrd} property.
   * Ord of the other entity.  Named sourceOrd for backward compatibility with BLink
   * @see #sourceOrd
   */
  public BOrd getSourceOrd() { return (BOrd)get(sourceOrd); }

  /**
   * Set the {@code sourceOrd} property.
   * Ord of the other entity.  Named sourceOrd for backward compatibility with BLink
   * @see #sourceOrd
   */
  public void setSourceOrd(BOrd v) { set(sourceOrd, v, null); }

  //endregion Property "sourceOrd"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRelation.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  public BRelation() {}

  public BRelation(Id id, BOrd endPoint)
  {
    setRelationId((id.getQName()));
    setEndpointOrd(endPoint);
  }

  public BRelation(Id id, BComponent endPoint)
  {
    setRelationId((id.getQName()));
    direct = endPoint;
  }

  public BRelation(Id id, BComponent endPoint, boolean isInbound)
  {
    setRelationId((id.getQName()));
    direct = endPoint;
    this.setInbound(isInbound);
  }

  /**
   * Test for inbound relation relative to entity where the
   *   relation is stored
   *
   * @return true if relation is an inbound relation
   */
  @Override
  public boolean isInbound()
  {
    return getInbound();
  }

  /**
   * Test for outbound relation relative to entity where the
   *   relation is stored
   *
   * @return true if relation is an outbound relation
   */
  @Override
  public boolean isOutbound()
  {
    return !getInbound();
  }

  /**
   * Get the {@code sourceOrd} property.
   * Ord of the other endPoint
   * @see #sourceOrd
   */
  @Override
  public BOrd getEndpointOrd() { return (BOrd)get(sourceOrd); }

  /**
   * Set the {@code sourceOrd} property.
   * Ord of the other endPoint
   * @see #sourceOrd
   */
  public void setEndpointOrd(BOrd v) { set(sourceOrd,v,null); }


  @Override
  public Id getId()
  {
    return Id.newId(getRelationId());
  }

  @Override
  public Entity getEndpoint()
  {
    if (direct != null) return direct;
    if (!active) activateRelation();
    return indirect;
  }

  /**
   * The relation object is always the same as the
   * relations's parent object.
   */
  public BComponent getRelationComponent()
  {
    return (BComponent)getParent();
  }


  @Override
  public Tags tags()
  {
    return new RelationTags(this);
  }

  /**
   * To string.
   */
  @Override
  public String toString(Context context)
  {
    String s = "null";
    BComponent sc = null;
    try{ sc = getParent().asComponent();}
    catch(Exception ignore){}
    if (sc != null)
    {
      SlotPath sp = sc.getSlotPath();
      if (sp == null) s = sc.toString();
      else s = sp.toString();
    }

    String t;
    if (direct != null)
    {
      t = "Direct: " + direct.toDebugString();
    }
    else
    {
      t = "Indirect: " + getEndpointOrd();
    }
    String dir = " (OUT) ";
    if(isInbound())
      dir = " (IN) ";
    return LinkUtil.relationText(dir + s, getRelationId(), t, context);
  }


////////////////////////////////////////////////////////////////
// Derived Properties
////////////////////////////////////////////////////////////////

  /**
   * A direct relation is one created with a direct
   * reference to the BComponent source.
   */
  public final boolean isDirect()
  {
    return direct != null;
  }

  /**
   * A indirect relation is one created with a BOrd to
   * the source BComponent.
   */
  public final boolean isIndirect()
  {
    return direct == null;
  }


////////////////////////////////////////////////////////////////
// Activation
////////////////////////////////////////////////////////////////

  /**
   * Activate the relation:
   * <ul>
   * <li>If the relation is indirect, then attempt to resolve it.</li>
    * </ul>
   *
   * @throws IllegalStateException if the component is not
   *   mounted as a property of a BComponent.
   * @throws UnresolvedException if the relation is indirect
   *   and the endPoint component cannot be resolved.
   */
  public final void activateRelation()
  {

    // if already active short circuit
    if (active) return;

    // get thisEndpoint which is my parent
    BComponent thisEndpoint = (BComponent)getParent();
    if (thisEndpoint == null) throw new IllegalStateException("not mounted in component");

    // resolve source
    BComponent endPoint = isDirect() ? direct : resolve();
    active = true;
    relationKnob = ((ComponentSlotMap)endPoint.getSlotMap()).installRelationKnob(this);

  }

  /**
   * Deactivate the relation:
    */
  public final void deactivateRelation()
  {
    // if not active short circuit
    if (!active) return;
    try
    {
      BComponent endpoint = (BComponent)getEndpoint();
      if (endpoint != null)
        ((ComponentSlotMap)endpoint.getSlotMap()).uninstallRelationKnob(this);
    }
    finally
    {
      active = false;
      relationKnob = null;
    }
  }

  /**
   * Get the relation's mirror relationKnob on the endpoint side.
   *
   */
  public RelationKnob getRelationKnob()
  {
    return relationKnob;
  }


////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Resolve an indirect relation and return the endPoint component.
   */
  private BComponent resolve()
  {
    return resolve(getParentComponent());
  }

   private BComponent resolve(BObject base)
  {
    indirect = null;
    try
    {
      BObject obj = getEndpointOrd().resolve(base).get();
      if(!(obj instanceof BComponent))
        throw new BajaRuntimeException("Target ord must resolve to a component");
      indirect = (BComponent)obj;
    }
    catch(UnresolvedException e)
    {
      throw new UnresolvedException("Cannot resolve source component");
    }

    return indirect;
  }




  protected BComponent direct;
  protected BComponent indirect;
  private boolean active;
  private RelationKnob relationKnob;



}
