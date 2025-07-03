/*                          
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.table.binding;

import javax.baja.agent.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.table.*;

/**
 * BBoundTable is a BTable implementation that is
 * ready to use with TableBindings to populate 
 * its model.
 *
 * @author    Brian Frank
 * @creation  31 May 04
 * @version   $Revision: 5$ $Date: 3/28/05 10:32:32 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BBoundTable
  extends BTable
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.table.binding.BBoundTable(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:35 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBoundTable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////  

  /**
   * Constructor.
   */
  public BBoundTable()
  {
    super(new BoundTableModel(), new BoundTableController());
    setHeaderRenderer(new BoundTableHeaderRenderer());
    setCellRenderer(new BoundTableCellRenderer());
    setSelection(new BoundTableSelection());
  }         

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public BoundTableModel getBoundModel()
  {
    return (BoundTableModel)getModel();
  }                  

  
////////////////////////////////////////////////////////////////
// Callbacks
////////////////////////////////////////////////////////////////

  public void bindingsChanged()
  {
    getBoundModel().rebind();
  }

////////////////////////////////////////////////////////////////
// Agents
////////////////////////////////////////////////////////////////

  public AgentList getAgents(Context cx)
  {
    AgentList agents = super.getAgents(cx);
    agents.toTop("bajaui:TableBinding");
    return agents;
  }
  
////////////////////////////////////////////////////////////////
// Test
////////////////////////////////////////////////////////////////
  
  /*
  public static void main(String[] args)
    throws Exception
  {                     
    // build query model
    BComponent root = new BComponent();
    javax.baja.space.BComponentSpace space = new javax.baja.space.BComponentSpace("foo", null, BOrd.make("foo:"));
    space.setRootComponent(root); 
    BLocalHost.INSTANCE.addNavChild(space);
    root.add(null, new BLabel("one",   BHalign.left));
    root.add(null, new BLabel("two",   BHalign.center));
    root.add(null, new BLabel("three", BHalign.right));
    root.add(null, new BButton("button"));
    
    // binding a                 
    OrdTarget av = BOrd.make("bql:select text, fill, halign, valign from bajaui:Label").resolve(root);
    BTableBinding a = new BTableBinding();
    a.fw(com.tridium.sys.schema.Fw.UPDATE_BINDING, av, null, null, null);

    // binding b                 
    OrdTarget bv = BOrd.make("bql:select halign, text from bajaui:Label").resolve(root);
    BTableBinding b = new BTableBinding();
    b.fw(com.tridium.sys.schema.Fw.UPDATE_BINDING, bv, null, null, null);

    // binding c                 
    OrdTarget cv = BOrd.make("bql:select type, text from bajaui:Button").resolve(root);
    BTableBinding c = new BTableBinding();
    c.fw(com.tridium.sys.schema.Fw.UPDATE_BINDING, cv, null, null, null);
    
    // table
    BBoundTable t = new BBoundTable();
    t.add(null, a);
    t.add(null, b);
    t.add(null, c);

    // frame
    BFrame frame = new BFrame("BoundTable Test");
    frame.setContent(t);
    frame.open(100,100,500,400);
  }   
  */

///////////////////////////////////////////////////////////
// Attributes
///////////////////////////////////////////////////////////

}
