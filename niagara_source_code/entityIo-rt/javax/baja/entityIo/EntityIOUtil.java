/**
 * Copyright (c) 2014 Tridium, Inc.  All Rights Reserved.
 */
package javax.baja.entityIo;

import java.util.Optional;
import javax.baja.naming.BOrd;
import javax.baja.tag.Entity;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;
import javax.baja.tag.Tag;
import javax.baja.tag.Tags;

/**
 * EntityIOUtil
 *
 * @author <a href="mailto:craig.gemmill@tridium.com">Craig Gemmill</a>
 *         Date: 2/18/14
 *         Time: 10:28 AM
 */
public final class EntityIOUtil
{

  public static String str(Entity e)
  {
    return e.getOrdToEntity()+"; tags:"+e.tags().getAll().size()+"; relations:"+e.relations().getAll().size();
  }

  public static String str(Relation r)
  {
    return r.getId()+":"+r.getEndpoint()+" tags:"+r.tags().getAll().size();
  }

  public static String str(Tag t)
  {
    return t.getId()+":"+t.getValue();
  }

  public static void dumpEntity(Entity e)
  {
    dumpEntity("", e, "");
  }

  public static void dumpEntity(String msg, Entity e)
  {
    dumpEntity(msg, e, "");
  }

  public static void dumpEntity(String msg, Entity e, String indent)
  {
    if (msg != null) System.out.println(indent+msg);
    Optional<BOrd> o2e = e.getOrdToEntity();
    if (o2e == null || !o2e.isPresent()) System.out.println(indent+"no ord");
    else System.out.println(indent+"ord="+o2e.get());
    if (e.tags().isEmpty()) System.out.println(indent+"no tags");
    else dumpTags("tags", e.tags(), indent);
    if (e.relations().isEmpty()) System.out.println(indent+"no relations");
    else dumpRelations("relations", e.relations(), indent);
  }

  public static void dumpTags(String msg, Entity entity)
  {
    if (entity == null)
      System.out.println(msg+": null entity!!");
    else
      dumpTags(msg, entity.tags());
  }

  public static void dumpTags(String msg, Tags tags)
  {
    dumpTags(msg, tags, "");
  }

  public static void dumpTags(String msg, Tags tags, String indent)
  {
    if (msg != null) System.out.println(indent+msg);
    if (tags == null) System.out.println(" null tags!");
    for (Tag t : tags)
    {
      System.out.println(indent + " id=" + t.getId() + "; val=" + t.getValue() + " [" + t.getValue().getType() + "]");
    }
  }

  public static void dumpRelations(String msg, Relations relations)
  {
    dumpRelations(msg, relations, "");
  }

  public static void dumpRelations(String msg, Relations relations, String indent)
  {
    if (msg != null) System.out.println(indent+msg);
    for (Relation r : relations)
    {
      System.out.println(indent+" id="+r.getId());
      System.out.println(indent+" endpointOrd="+r.getEndpointOrd());
      System.out.println(indent+" inbound="+r.isInbound());
      System.out.println(indent+" outbound="+r.isOutbound());
      Entity e = r.getEndpoint();
      if (e != null) dumpEntity("relEntity", r.getEndpoint(), indent+" ");
      dumpTags("relTags", r.tags(), indent+" ");
      System.out.println();
    }
  }
}
