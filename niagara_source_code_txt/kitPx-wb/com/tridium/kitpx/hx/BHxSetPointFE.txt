/*
 * Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.kitpx.hx;

import java.util.logging.Level;

import javax.baja.agent.NoSuchAgentException;
import javax.baja.hx.BHxFieldEditor;
import javax.baja.hx.HxOp;
import javax.baja.io.HtmlWriter;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.status.BIStatusValue;
import javax.baja.sys.BAbsTime;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BDouble;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIBoolean;
import javax.baja.sys.BIEnum;
import javax.baja.sys.BINumeric;
import javax.baja.sys.BObject;
import javax.baja.sys.BValue;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBinding;
import javax.baja.ui.BWidget;

import com.tridium.hx.fieldeditors.BHxBajauxFE;
import com.tridium.hx.fieldeditors.BHxGenericFE;
import com.tridium.kitpx.BSetPointBinding;
import com.tridium.kitpx.BSetPointFieldEditor;


/**
 * BHxSetPointFE.
 *
 * @author    Andy Frank
 * @creation  31 May 05
 * @version   $Revision$ $Date$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "kitPx:SetPointFieldEditor",
    requiredPermissions = "r"
  )
)
@NiagaraSingleton
public class BHxSetPointFE
  extends BHxGenericFE
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.kitpx.hx.BHxSetPointFE(3408339917)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BHxSetPointFE INSTANCE = new BHxSetPointFE();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHxSetPointFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  protected BHxSetPointFE() {}
  
////////////////////////////////////////////////////////////////
// HxView
////////////////////////////////////////////////////////////////

  public void write(HxOp op)
    throws Exception
  {
    HxOp editorOp = makeEditorOp(op);
    if(editorOp == null)
      return;
    BHxFieldEditor editor = makeEditor(editorOp);
    
    HtmlWriter out = op.getHtmlWriter();      
    
    if(log.isLoggable(Level.FINE))
    {
      BWidget widget = (BWidget)op.get();    
      out.w("\n\n");
      out.w("<!-- path: ") .w(op.getPath()).w(" -->\n");
      if(editor!=null)
        out.w("<!-- view: ") .w(editor.getType()).w(" -->\n");
      out.w("<!-- widget: ") .w(widget.getType()).w(" -->\n");
      out.w("<!-- shell: ") .w(widget.getShell()).w(" -->\n");
    }    
    
    out.w("<span class='-t-hx-setpoint-fe-wrapper' id='" + editorOp.getPath() + "'>");
    editor.write(editorOp);
    out.w("</span>");
  }

  @Override
  public boolean needsUpdate(BAbsTime lastUpdate, BAbsTime lastModified)
  {
    //underlying BHxBajauxFE may need an update with any BWidget modifications
    return true;
  }
  
  public void update(int width, int height, boolean forceUpdate, HxOp op)
    throws Exception
  {
    BSetPointFieldEditor fieldEditor = (BSetPointFieldEditor)op.get();    

    BSetPointBinding bind = getSetPointBinding(fieldEditor);
    if (bind != null && !bind.getOrd().isNull())
    {
      HxOp editorOp = makeEditorOp(op);
      if(editorOp == null)
        return;
      BHxFieldEditor editor = makeEditor(editorOp);
      
      // Set content
      editor.update(editorOp);
    }     
  }
  
  public boolean process(HxOp op)
    throws Exception
  {
    if(super.process(op))
      return true;    
    
    BWidget w = (BWidget)op.get();    
    BSetPointBinding bind = getSetPointBinding(w);
    if (bind != null && !bind.getOrd().isNull())
    {
      HxOp editorOp = makeEditorOp(op);
      if(editorOp == null)
        return false;

      BHxFieldEditor editor = makeEditor(editorOp);
    
      // save editor
      return editor.process(editorOp);  
    }
    return false;
  }


  public BObject save(HxOp op)
    throws Exception
  {
    HxOp editorOp = makeEditorOp(op);
    if(editorOp == null)
      return op.get();
    BHxFieldEditor editor = makeEditor(editorOp);
  
    // save editor
    BValue value = (BValue)editor.save(editorOp);

    // pass value to binding to apply it
    return value;
  }
    
////////////////////////////////////////////////////////////////
// Util
////////////////////////////////////////////////////////////////

  private BSetPointBinding getSetPointBinding(BWidget w)
  {
    BBinding[] bindings = w.getBindings();
    for(int i=0; i<bindings.length; ++i)
      if (bindings[i] instanceof BSetPointBinding)
        return (BSetPointBinding)bindings[i];
    return null;
  }  
  
  private HxOp makeEditorOp(HxOp op)
  {
    BWidget w = (BWidget)op.get();    
    BSetPointBinding bind = getSetPointBinding(w);
    if (bind == null) return null;
    if (bind.getOrd().isNull()) return null;

    OrdTarget target = null;
    try
    {
      target = bind.getOrd().resolve(op.getWebOp().get(), op);
    }
    catch(Exception e)
    {
      return null;
    }
    
    BValue value = (BValue)target.get();
    BFacets facets = null;     
    
    if (value instanceof BIBoolean)
    {
      BIBoolean x = (BIBoolean)target.get();
      value = BBoolean.make(x.getBoolean());
      facets = x.getBooleanFacets();
    }            
    else if (value instanceof BINumeric)
    {
      BINumeric x = (BINumeric)value;
      value = BDouble.make(x.getNumeric());
      facets = x.getNumericFacets();
    }            
    else if (value instanceof BIEnum)
    {
      BIEnum x = (BIEnum)value;
      value = x.getEnum();
      facets = x.getEnumFacets();
    }          
    else if (value instanceof BIStatusValue) // assume string point 
    {
      BIStatusValue x = (BIStatusValue)value;
      value = x.getStatusValue().getValueValue();
      facets = x.getStatusValueFacets();
    }
    else if(value instanceof BComponent)
    {
      BComponent c = value.asComponent();
      //This is required for NiagaraVirtuals that happen to be representing kitControl:NumericConst
      c.loadSlots();
      BValue out = c.get("out");
      if (out instanceof BIStatusValue)
      {
        BIStatusValue x = (BIStatusValue)out;
        value = x.getStatusValue().getValueValue();
        facets = x.getStatusValueFacets();
      }
    }
    
    // fallback to OrdTarget facets     
    if (facets == null || facets.isNull())
      facets = target.getFacets();
    
    HxOp editorOp = op.make("input", new OrdTarget(target, value));

    BHxGenericFE.matchReadonlyAndEnabled(w, target, BHxBajauxFE.INSTANCE, editorOp);
    
    editorOp.mergeFacets(facets);
    editorOp.mergeFacets(BFacets.make(BFacets.UX_FIELD_EDITOR, "kitPx:SetPointEditor"));
    return editorOp;    
  }
  
  private BHxFieldEditor makeEditor(HxOp op)
    throws NoSuchAgentException
  {
    op.mergeFacets(BHxFieldEditor.PREFER_BAJAUX);
    return BHxFieldEditor.makeFor(op.get(), op);
  }
}
