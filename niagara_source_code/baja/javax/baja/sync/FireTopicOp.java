/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.security.BPermissions;
import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BValue;
import javax.baja.sys.Context;
import javax.baja.sys.Topic;
import javax.baja.xml.XParser;

/**
 * FireTopicOp maps to the knob topic fired component event.
 *
 * @author    Brian Frank
 * @creation  17 Nov 03
 * @version   $Revision: 11$ $Date: 1/14/11 3:31:38 PM EST$
 * @since     Baja 1.0
 */
public class FireTopicOp
  extends SyncOp
{ 

  public FireTopicOp(BComponent c, String name, BValue event)
  {                   
    super(c);
    this.name = name;
    this.event = event;
  }

  public FireTopicOp()
  {
  }

  @Override
  public int getId()
  { 
    return FIRE_TOPIC; 
  }                
  
  public String getSlotName() { return name; }
  
  public BValue getEvent() { return event; }

  @Override
  void commit(SyncBuffer parent, BComponentSpace space, Context context)
    throws Exception
  { 
    Topic topic = component.getTopic(name);
    if (topic != null)
      component.fire(topic, event, Context.commit);    
  }

  @Override
  void encode(SyncEncoder out)
    throws Exception
  {               
    if ((event != null) && event.isComponent())
    {
      BPermissions permissions = out.getPermissionsFor(event.asComponent());
      if (!permissions.hasOperatorRead())
        throw new SyncOpSecurityException("Missing op read permission on value");
    }
    super.encode(out);
    
    out.attr("n", name).endAttr().newLine();
    
    if (event != null)
    {
      out.key("b");
      out.encode(event);
    }
    
    out.end(String.valueOf((char)getId())).newLine();
  }

  @Override
  void decode(SyncBuffer buffer, BComponentSpace space, SyncDecoder in)
    throws Exception
  {                 
    super.decode(buffer, space, in);
    name = in.elem().get("n");
    in.next();   
    if (in.type() == XParser.ELEM_START && in.elem().name().equals("p"))
      event = in.decode();
  }
  
  public String toString()
  {
    return "FireTopic: " + componentToString() + "." + name + " -> " + event;
  }
  
  String name;
  BValue event;
    
}
