/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.baja.io.ValueDocDecoder;
import javax.baja.naming.BOrd;
import javax.baja.security.BPermissions;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.Context;
import javax.baja.sys.Slot;
import com.tridium.nre.util.IElement;
import com.tridium.sys.engine.ProxyKnob;
import com.tridium.sys.engine.ProxyRelationKnob;
import com.tridium.sys.schema.ComponentSlotMap;
import com.tridium.sys.schema.Fw;

/**
 * ValueDocDecoder used for SyncBuffer decoding.
 *
 * @author    Gareth Johnson
 * @creation  19 Nov 03
 * @version   $Revision: 8$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Niagara 3.7
 */
public class SyncDecoder
  extends ValueDocDecoder
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
                                          
  public SyncDecoder(IDecoderPlugin plugin)
    throws Exception
  {             
    super(plugin);
  }
  
  public SyncDecoder(InputStream in)
    throws Exception     
  {
    super(in);
  }

  public SyncDecoder(IDecoderPlugin plugin, Context cx)
    throws Exception
  {
    super(plugin, cx);
  }

  public SyncDecoder(InputStream in, Context cx)
    throws Exception
  {
    super(in, cx);
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  @Override
  protected void decodingComponent(BComponent c)
    throws Exception
  {                        
    IElement elem = elem();
    String elemName = elem.name();
    
    if (elemName.equals("load")) decodingLoadInfo(c, elem);
    if (elemName.equals("nknob")) decodingKnob(c, elem);
    if (elemName.equals("nrknob")) decodingRelationKnob(c, elem);

    skip();
  }                     
  
  /**
   * Decode the load info which contains information 
   * specific to the load operation. 
   */                                                 
  private void decodingLoadInfo(BComponent c, IElement elem)
  { 
    // create a knobs entry for this component               
    if (knobs == null) knobs = new HashMap<>();
    knobs.put(c, new ArrayList<>(4));
    if (rknobs == null) rknobs = new HashMap<>();
    rknobs.put(c, new ArrayList<>(4));

    String permissionsStr = elem.get("p", null);
    if (permissionsStr != null)
    {                                 
      try
      {
        BPermissions permissions = BPermissions.make(permissionsStr);
        ((ComponentSlotMap)c.fw(Fw.SLOT_MAP)).setCachedPermissions(permissions);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
  }

  /**
   * If this is a nknob element, then save the xml
   * away in a hash map until we can decode it into
   * a proxy knob (which we can't do right now because
   * we don't have the right component instance, nor 
   * the right property instance).
   */
  private void decodingKnob(BComponent c, IElement elem)
  {
    // we should never call into this method without first
    // decoding a <load> element via decodingLoadInfo() which
    // initializes the map with an empty ArrayList (in case
    // there are no knobs)
    if (knobs == null)
      throw new IllegalStateException("unexpected <nknob> without <load>");

    List<IElement> list = knobs.get(c);
    list.add(elem().copy());
  }

  /**
   * If this is a nrelationknob element, then save the xml
   * away in a hash map until we can decode it into
   * a proxy knob (which we can't do right now because
   * we don't have the right component instance, nor
   * the right property instance).
   */
  private void decodingRelationKnob(BComponent c, IElement elem)
  {
    // we should never call into this method without first
    // decoding a <load> element via decodingLoadInfo() which
    // initializes the map with an empty ArrayList (in case
    // there are no knobs)
    if (rknobs == null)
      throw new IllegalStateException("unexpected <nrknob> without <load>");

    List<IElement> list = rknobs.get(c);
    list.add(elem().copy());
  }

////////////////////////////////////////////////////////////////
// Package Scope
////////////////////////////////////////////////////////////////

  /**
   * Decode current element into knob or return null if
   * source slot is not found.
   */
  ProxyKnob decodeKnob(BComponent component)
    throws Exception
  {
    return decodeKnob(component, elem());
  }

  /**
   * Decode a knob or return null if the source slot not found.
   */
  static ProxyKnob decodeKnob(BComponent component, IElement elem)
    throws Exception
  {

    Slot sourceSlot = component.getSlot(elem.get("ss"));
    if (sourceSlot == null)
      return null;

    int id = elem.geti("id");
    BOrd targetOrd    = BOrd.make(elem.get("to"));
    String targetSlot = (elem.get("ts"));
    return new ProxyKnob(id, component, sourceSlot, targetOrd, targetSlot);
  }

  /**
   * Decode current element into relationKnob or return null if
   * source slot is not found.
   */
  ProxyRelationKnob decodeRelationKnob(BComponent component)
    throws Exception
  {
    return decodeRelationKnob(component, elem());
  }

  /**
   * Decode a knob or return null if the source slot not found.
   */
  static ProxyRelationKnob decodeRelationKnob(BComponent component, IElement elem)
    throws Exception
  {

    int id = elem.geti("id");
    BOrd relationOrd    = BOrd.make(elem.get("ro"));
    String rid = elem.get("ri");
    BFacets facets = BFacets.make(elem.get("rt"));
    return new ProxyRelationKnob(id, rid, facets, component, relationOrd);
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  Map<BComponent, List<IElement>> knobs;    // populated if nknob elements encountered
  Map<BComponent, List<IElement>> rknobs;    // populated if nrknob elements encountered
}
