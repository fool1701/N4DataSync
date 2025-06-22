/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.Context;
import javax.baja.sys.Property;

/**
 * ReorderOp is the implementation of SyncOp for a properties reorder.
 *
 * @author    Brian Frank
 * @creation  16 Jul 01
 * @version   $Revision: 11$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class ReorderOp
  extends SyncOp
{ 

  public ReorderOp(BComponent c, String[] order)
  {
    super(c);
    this.order = order;
  }

  public ReorderOp()
  {
  }

  @Override
  public int getId()
  { 
    return REORDER; 
  }

  public String[] getOrder() { return order.clone(); }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  {               
    Property[] props = new Property[order.length];
    for(int i=0; i<props.length; ++i)
      props[i] = component.getProperty(order[i]);

    try
    {
      component.reorder(props, context);
    }
    catch(ArrayIndexOutOfBoundsException e)
    { // This can throw a nuisance ArrayIndexoutOfBoundsException if a previously loaded proxy
      // component is unsubscribed when this reorder comes in AND the number of dynamic properties
      // also changed while the proxy component was in the unsubscribed state. Whenever the proxy
      // component is re-subscribed, the LoadOp will now take care of ensuring the slot order, so we
      // can suppress this nuisance exception only when the component is unsubscribed.
      if (component.isSubscribed())
      {
        throw e;
      }
    }
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    super.encode(out);
    
    StringBuilder buff = new StringBuilder();
    for(int i=0; i<order.length; ++i)
    {
      if (i > 0) buff.append(';');
      buff.append(order[i]);
    }
    
    out.attr("o", buff.toString()).end().newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    String list = in.elem().get("o");                        
    List<String> v = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(list, ";");
    while(st.hasMoreTokens())
      v.add(st.nextToken());
    order = v.toArray(new String[v.size()]);
  }
  
  public String toString()
  {
    return "Reorder: " + componentToString();
  }
  
  String[] order;
    
}
