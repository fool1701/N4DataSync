/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.query;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.baja.collection.AbstractRow;
import javax.baja.collection.AbstractTableCursor;
import javax.baja.collection.BITable;
import javax.baja.collection.Column;
import javax.baja.collection.ColumnList;
import javax.baja.collection.Row;
import javax.baja.collection.TableCursor;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIObject;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.BIEntity;
import javax.baja.tag.Entity;
import javax.baja.util.CloseableIterator;
import javax.baja.util.Lexicon;

import com.tridium.collection.GenericColumn;
import com.tridium.data.DataColumnList;
import com.tridium.nre.diagnostics.DiagnosticUtil;
import com.tridium.query.QueryPermissionCheckIterator;
import com.tridium.sys.tag.BEntityObjectWrapper;

/**
 * QueryResult is the result of a query processed against a query scope by a QueryHandler.
 * It is a container for all of the elements required to get a query result, that is, an
 * OrdTarget representing the query scope, the query itself, and the query handler.
 *
 * @author John Sublett
 * @creation 01/15/2014
 * @since Niagara 4.0
 */
@NiagaraType
public final class BQueryResult
  extends BObject
  implements BITable<BIEntity> // Implements BITable starting in Niagara 4.4
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.query.BQueryResult(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BQueryResult.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BQueryResult(OrdQuery query, OrdTarget scope, BIQueryHandler queryHandler)
  {
    this.query = query;
    this.scope = scope;
    this.queryHandler = queryHandler;
  }

  /**
   * Get the query for this result.
   * @return Returns the OrdQuery that defines the set of objects in the result.
   */
  public OrdQuery getQuery()
  {
    return query;
  }

  /**
   * Get the OrdTarget the defines the scope of the query.  The scope is an object
   * that represents the total set of objects that the query will be processed against.
   */
  public OrdTarget getScope()
  {
    return scope;
  }

  /**
   * Get the query handler.  A query handler processes the specific query type for the
   * specified scope.
   */
  public BIQueryHandler getQueryHandler()
  {
    return queryHandler;
  }

  /**
   * Get a CloseableIterator for the results of the query.
   */
  public CloseableIterator<Entity> getResults()
  {
    long start = DiagnosticUtil.startIfLoggable("BQueryResult.getResults");
    try
    {
      CloseableIterator<Entity> results = queryHandler.query(scope, query);
      if (scope.getUser() != null && !BIQueryHandler.TRUSTED_QUERY_HANDLERS.contains(queryHandler.getType().getTypeSpec()))
        results = QueryPermissionCheckIterator.make(results, scope.getUser());

      return results;
    }
    finally
    {
      if (start > -1)
      {
        DiagnosticUtil.complete(start, "BQueryResult.getResults", scope + ", "  + query);
      }
    }
  }

  /**
   * Get a Stream for the results of the query.
   */
  public Stream<Entity> stream()
  {
    CloseableIterator<Entity> it = getResults();
    Spliterator<Entity> split = Spliterators.spliteratorUnknownSize(it, /*characteristics*/0);
    return StreamSupport.stream(split, /*parallel*/false).onClose(() ->
    {
      try
      {
        it.close();
      }
      catch(RuntimeException re)
      {
        throw re;
      }
      catch(Exception e)
      {
        throw new BajaRuntimeException(e);
      }
    });
  }


///////////////////////////////////////////////////////////
// BITable
///////////////////////////////////////////////////////////

  /**
   * For BQueryResult support of the {@code BITable} interface, returns a cursor of
   * {@code BIEntity} query results.
   *
   * @since Niagara 4.4
   */
  @Override
  public TableCursor<BIEntity> cursor()
  {
    return new QueryResultTableCursor(this);
  }

  /**
   * For BQueryResult support of the {@code BITable} interface, returns a column list containing
   * a single ORD column for the Entity ORDs of the query results.
   *
   * @since Niagara 4.4
   */
  @Override
  public ColumnList getColumns()
  {
    return columns;
  }

  /**
   * For BQueryResult support of the {@code BITable} interface, always returns {@code BFacets.NULL}
   * for the table facets.
   *
   * @since Niagara 4.4
   */
  @Override
  public BFacets getTableFacets()
  {
    return BFacets.NULL;
  }


///////////////////////////////////////////////////////////
// TableCursor support for query results
///////////////////////////////////////////////////////////

  private static class QueryResultTableCursor
    extends AbstractTableCursor<BIEntity>
  {
    public QueryResultTableCursor(BQueryResult result)
    {
      super(result);
      entities = result.getResults();
    }

    @Override
    public Row<BIEntity> row()
    {
      return new QueryResultTableRow(getTable(), current != null?BEntityObjectWrapper.makeEntityObject(current):null);
    }

    @Override
    protected boolean advanceCursor()
    {
      current = entities.hasNext()?entities.next():null;
      return current != null;
    }

    @Override
    protected void closeCursor()
    {
      try
      {
        entities.close();
      }
      catch(Exception ignore) {}
    }

    /**
     * Overridden to ensure close() is called so that the underlying CloseableIterator can be
     * cleaned up.
     */
    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() throws Throwable
    {
      // NCCB-53200: The finalize() implementation remains in place, and the deprecation warning suppressed,
      //              as an intentional leak protection for instances of BQueryResult operations that were
      //              in place previous to the implementation of NCCB-27299, et. al. that migrated to the
      //              CloseableIterator infrastructure. We will eventually have to remove the finalize method
      //              when it is removed from public API, but we should make every effort to properly disclose
      //              this information to 3rd party developers such that code review of BQueryResult usage can
      //              take place.
      //
      //              NCCB-53392 has been created to remove this usage in a future release with appropriate consideration.
      try
      {
        close();
      }
      finally
      {
        super.finalize();
      }
    }

    Entity current;
    CloseableIterator<Entity> entities;
  }

  private static class QueryResultTableRow
    extends AbstractRow<BIEntity>
  {
    QueryResultTableRow(BITable<BIEntity> table, BIEntity rowObject)
    {
      super(table, rowObject);
    }

    @Override
    public BIObject cell(Column column)
    {
      BIEntity entity = rowObject();
      if (column == entityOrdColumn)
      {
        BOrd ord;
        if (entity instanceof BComponent)
        {
          ord = ((BComponent)entity).getNavOrd();
        }
        else if (entity != null)
        {
          ord = entity.getOrdToEntity().orElse(BOrd.NULL);
        }
        else
        {
          ord = BOrd.NULL;
        }
        return ord;
      }
      return entity; // Shouldn't ever get here, since we only support the single ORD column
    }

    @Override
    public Row<BIEntity> safeCopy()
    {
      return this;
    }
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private static final Column entityOrdColumn =
    new GenericColumn("ord", Lexicon.make(BQueryResult.class).getText("ord"), BOrd.TYPE);

  private static final DataColumnList columns = new DataColumnList();
  static
  {
    columns.addColumn(entityOrdColumn);
  }

  private OrdQuery query;
  private OrdTarget scope;
  private BIQueryHandler queryHandler;
}
