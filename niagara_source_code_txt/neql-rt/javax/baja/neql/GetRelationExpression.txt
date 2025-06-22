/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import java.util.Collection;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;

import com.tridium.neql.NeqlUtil;

/**
 * A GetRelationExpression gets a relation on the evaluated entity.
 *
 * @author John Sublett
 * @creation 01/16/2014
 * @since Niagara 4.0
 */
public class GetRelationExpression
  extends Expression
{
  public GetRelationExpression(String namespace, String key)
  {
    this(namespace, key, Relations.BOTH);
  }

  /**
   * A constructor for the GetRelationExpression
   *
   * @param namespace the namespace for this relation. This is the text before the colon
   * @param key the key (or name) of the relation. This is the text after the colon, if there is one.
   * @param direction indicates the direction of the relation to be created.
   * @since Niagara 4.10
   */
  public GetRelationExpression(String namespace, String key, int direction)
  {
    this.namespace = namespace;
    this.key = key;
    this.direction = direction;

    cachedId = ((namespace != null) && !namespace.isEmpty()) ? Id.newId(namespace, key) : null;
  }

  /**
   * Get the node type.
   *
   * @return AST_GET_RELATION
   */
  @Override
  public int getNodeType()
  {
    return AST_GET_RELATION;
  }

  @Override
  public String getNodeDisplay()
  {
    String directionStr;
    switch (direction)
    {
      case Relations.IN:
        directionStr = "<-";
        break;
      case Relations.OUT:
        directionStr = "->";
        break;
      default:
        directionStr = "";
        break;
    }

    if (namespace == null)
    {
      return key + directionStr;
    }
    else
    {
      return namespace + ':' + key + directionStr;
    }
  }

  /**
   * Get the namespace for the tag or null if it is a global tag.
   */
  public String getNamespace()
  {
    return namespace;
  }

  /**
   * Get the tag key.
   */
  public String getKey()
  {
    return key;
  }

  @Override
  public Collection<Relation> evaluate(Entity entity, Context context)
  {
    Id id = NeqlUtil.getExpressionId(key, namespace, context);
    return entity.relations().getAll(id, direction);
  }

  /**
   * Returns true if the entity contains a relation (inbound or outbound) with this expression's
   * relation id.
   *
   * @param entity entity on which to search for a relation with this expression's relation id
   * @param context context that may contain the default namespace to form the relation id
   * @return true if the entity contains a relation (inbound or outbound) with this expression's
   *   relation id
   * @since Niagara 4.9
   */
  @Override
  protected boolean evalBoolean(Entity entity, Context context)
  {
    Id id = getId(context);
    return entity.relations().get(id, direction).isPresent();
  }

  /**
   * Return this expression's relation id.
   *
   * @param context context that may contain the default namespace to form the relation id
   * @return expression's relation id, if fully specified; if the expression's namespace is not
   *   specified, look for one in the context
   * @since Niagara 4.9
   */
  public Id getId(Context context)
  {
    if (cachedId != null)
    {
      return cachedId;
    }

    String contextNamespace = (context != null) ? NeqlUtil.getNamespaceFromContext(context) : Id.NO_DICT;
    return Id.newId(contextNamespace, key);
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private final String namespace;
  private final String key;
  private final int direction;
  private final Id cachedId;
}
