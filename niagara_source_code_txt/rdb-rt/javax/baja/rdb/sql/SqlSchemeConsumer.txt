/*
 * Copyright 2017 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.rdb.sql;

import javax.baja.sys.BComponent;

/**
 * When BSqlScheme directly instead of ord.resolve(), using
 * <code>resolve(SqlSchemeConsumer consumer, OrdTarget base, OrdQuery ordQuery)</code>
 * provides a mechanism for handling one database row at a time rather than loading the
 * entire result set into a table.
 *
 * @author Tony Richards
 * @since Niagara 4.4
 *
 */
public interface SqlSchemeConsumer
{
  void acceptRow(BComponent row) throws Exception;
}
