/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.neql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.tag.EntityWrapper;
import javax.baja.tag.Relation;
import javax.baja.tag.Relations;
import javax.baja.util.CloseableIterator;
import javax.baja.util.CloseableIteratorWrapper;

import com.tridium.neql.BooleanFilterIterator;
import com.tridium.neql.NamespaceContext;
import com.tridium.neql.RelatedEntityIterator;

/**
 * BNeqlEntityQueryHandler is an abstract query handler for querying a collection of Entities
 * provided by a cursor.
 *
 * @author John Sublett
 * @creation 6/4/14
 * @since Niagara 4.0
 */
@NiagaraType
public abstract class BNeqlEntityQueryHandler
    extends BObject
    implements BINeqlQueryHandler
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.neql.BNeqlEntityQueryHandler(2979906276)1.0$ @*/
/* Generated Tue Jan 18 15:26:55 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNeqlEntityQueryHandler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BNeqlEntityQueryHandler()
  {
    evaluator = new NeqlEntityEvaluator();
  }

///////////////////////////////////////////////////////////
// Iterator
///////////////////////////////////////////////////////////

  /**
   * Subclasses must override this method to return an Iterator of the Entity
   * for the defined scope.  This method will only be called if the subclass
   * returns true for canHandle(scope).
   *
   * @param scope An OrdTarget for the scope.  The scope identifies a
   *              collection of entities to be queried.
   * @return Returns an iterator of the entities in the scope.
   */
  protected abstract Iterator<? extends Entity> getIteratorForScope(OrdTarget scope);

///////////////////////////////////////////////////////////
// Query
///////////////////////////////////////////////////////////

  /**
   * Process a query against the scope.
   *
   * @param scope The scope of the query to be processed.  This represents
   *              the collection of objects that the query will be processed
   *              against.  In most cases, the scope object will either be
   *              a BISpace or a BISpaceNode.
   * @param query The query to be processed.
   * @return Returns an iterator of Entities that satisfy the query.
   */
  @Override
  public CloseableIterator<Entity> query(OrdTarget scope, OrdQuery query)
  {
    AstNode ast = ((NeqlQuery)query).getAst();

    if (ast.getNodeType() == AstNode.AST_SELECT)
    {
      return new CloseableIteratorWrapper<>(selectQuery(scope, (Select)ast));
    }
    else if (ast.getNodeType() == AstNode.AST_TRAVERSE)
    {
      return new CloseableIteratorWrapper<>(traverseQuery(scope, (Traverse)ast));
    }
    else
      throw new UnresolvedException();
  }

  /**
   * Handle a select query against the specified scope.
   *
   * @param scope       The scope for the query.  The scope defines the collection
   *                    of objects that the select query evaluates.
   * @param selectQuery The query to execute.
   * @return Returns an iterator of entities within the scope that match the query.
   */
  @SuppressWarnings("unchecked")
  private Iterator<Entity> selectQuery(OrdTarget scope, Select selectQuery)
  {
    Iterator<Entity> scopeIterator = (Iterator<Entity>)getIteratorForScope(scope);

    BObject scopeObj = scope.get();
    Entity scopeEntity = null;
    if (scopeObj instanceof Entity)
      scopeEntity = (Entity)scopeObj;

    return new BooleanFilterIterator(scopeIterator,
            new NamespaceContext(scope),
            scopeEntity,
            selectQuery.getPredicate(),
            evaluator);
  }

  /**
   * Handle a traverse query against the specified scope.
   *
   * @param scope         The scope for the query.  The scope defines the root object
   *                      that the traverse query evaluates.
   * @param traverseQuery The query to execute.
   * @return Returns an iterator of entities within the scope that match the query.
   */
  private Iterator<Entity> traverseQuery(OrdTarget scope, Traverse traverseQuery)
  {
    BObject scopeObj = scope.get();
    Entity scopeEntity;
    if (scopeObj instanceof Entity)
      scopeEntity = (Entity)scopeObj;
    else
      return new ArrayList<Entity>().iterator();

    Collection<Relation> rels =
        scopeEntity.relations().getAll(traverseQuery.getRelationId(),
            traverseQuery.isOutbound() ? Relations.OUT : Relations.IN);

    RelatedEntityIterator related = new RelatedEntityIterator(rels.iterator(), scope);
    if (traverseQuery.getPredicate() == null)
      return related;
    else
    {
      // Passing the scope into the related entity iterator ensures that only items that the user
      // has permission to read will be passed to the boolean filter iterator.  The boolean filter
      // iterator will also check permissions and that is redundant.  But, the evaluation of the
      // filter could be very complex so it is probably better overall to evaluate the filter on
      // only entities the user has permission to read.
      BooleanFilterIterator filter =
          new BooleanFilterIterator(related,
              new NamespaceContext(scope),
              new EntityWrapper(scopeEntity, scope.getFacets()),
              traverseQuery.getPredicate(),
              evaluator);

      return filter;
    }
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private NeqlEntityEvaluator evaluator;
}
