/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import java.io.IOException;
import java.io.OutputStream;
import javax.baja.io.ValueDocEncoder;
import javax.baja.security.BPermissions;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import com.tridium.sys.engine.NKnob;
import com.tridium.sys.engine.NRelationKnob;

/**
 * ValueDocEncoder used for SyncBuffer encoding.
 *
 * @author    Brian Frank
 * @creation  19 Nov 03
 * @version   $Revision: 15$ $Date: 6/15/11 6:26:43 AM EDT$
 * @since     Baja 1.0
 */
public class SyncEncoder
  extends ValueDocEncoder
{                        

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  public SyncEncoder(IEncoderPlugin plugin, Context cx)
    throws Exception     
  {
    super(plugin, cx);
    setEncodeComments(false);
    setEncodeTransients(true);
  }
  
  public SyncEncoder(OutputStream out, Context cx)
    throws Exception     
  {
    super(out, cx);
    setEncodeComments(false);
    setEncodeTransients(true);
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  @Override
  public boolean isSyncEncoder()
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  @Override
  protected void encodingComponent(BComponent c)
    throws IOException
  {               
    if (encodeForLoad)
    {    
      // always put <load> first so that we can init knob map                   
      encodeLoadInfo(c);

      // next do <nknob> elements
      NKnob[] knobs = (NKnob[])c.getKnobs();

      if (knobs.length > 0)
      {
        startArray("nk");

        for(int i=0; i<knobs.length; ++i)
          encodeKnob(knobs[i]);

        endArray();
      }
      // next do <nrknob> elements
      NRelationKnob[] rknobs = (NRelationKnob[])c.getRelationKnobs();

      if (rknobs.length > 0)
      {
        startArray("nrk");

        for(int i=0; i<rknobs.length; ++i)
        {
          if(rknobs[i] != null && rknobs[i].getRelationComponent()!=null)
            encodeRelationKnob(rknobs[i]);
        }

        endArray();
      }
    }
  }

  @Override
  protected void encodingComponentStub(BComponent c)
    throws IOException
  {
    encodeLoadInfo(c);
      
    // if the component has a dynamic icon property, we 
    // treat that as special case so that the nav tree 
    // can display it properly even though this is just 
    // a stubbed component
    BValue value = c.get("icon");
    if (value instanceof BIcon)          
    {        
      newLine().incrementIndent().indent();
      startArray("s");
      encode("icon", value, 0);
      endArray();
      decrementIndent().indent();
    }
  } 
    
  /**
   * The load element is used to encode the permissions
   * the user has on component so that they can be cached
   * on the client/proxy side.
   */
  private void encodeLoadInfo(BComponent c)
    throws IOException
  { 
    BPermissions perm = getPermissionsFor(c); 
    key("l").start("load").attr("p", perm.encodeToString()).end();
  }
  
  /**
   * Called when a SyncOp is being encoded
   */
  protected void encodingSyncOp(SyncOp op)
    throws IOException
  { 
  }
  
////////////////////////////////////////////////////////////////
// Package Scope
////////////////////////////////////////////////////////////////

  /**
   * Encode a knob.
   */

  void encodeKnob(NKnob knob)
    throws IOException
  {
    indent();

    this.start("nknob");
    this.attr("id", String.valueOf(knob.id));
    this.attr("ss", knob.getSourceSlotName());
    this.attr("to", knob.getTargetOrd().toString());
    this.attr("ts", knob.getTargetSlotName()).end().newLine();
  }

   /**
   * Encode a relationKnob.
   */

  void encodeRelationKnob(NRelationKnob knob)
    throws IOException
  {
    indent();

    this.start("nrknob");
    this.attr("id", String.valueOf(knob.id));
    this.attr("ri", knob.getRelationId());
    this.attr("rt", knob.getRelationTags().encodeToString());
    this.attr("ro", knob.getRelationOrd().toString()).end().newLine();
  }




////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  boolean encodeForLoad;
  
}

