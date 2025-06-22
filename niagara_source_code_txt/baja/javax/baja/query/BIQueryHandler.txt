/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.query;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.baja.naming.BOrd;
import javax.baja.naming.BOrdList;
import javax.baja.naming.BSlotScheme;
import javax.baja.naming.OrdQuery;
import javax.baja.naming.OrdTarget;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.BHandleScheme;
import javax.baja.sys.BInterface;
import javax.baja.sys.LocalizableException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.util.BTypeSpec;
import javax.baja.util.CloseableIterator;

import com.tridium.sys.station.BStationScheme;

/**
 * A BIQueryHandler processes a query on a specific target scope.
 *
 * @author John Sublett
 * @creation 01/15/2014
 * @since Niagara 4.0
 */
@NiagaraType
public interface BIQueryHandler
  extends BInterface
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.query.BIQueryHandler(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:38 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIQueryHandler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Determine whether this QueryHandler can handle a query
   * for the specified scope and scheme.
   *
   * @param scope A OrdTarget whose object specifies the scope
   *              of a query.
   * @param scheme The scheme for the query that needs to be processed.
   * @return boolean
   */
  boolean canHandle(OrdTarget scope, BQueryScheme scheme);

  /**
   * Handle the specified query against the target scope.
   *
   * @param scope The scope of the query to be processed.  This represents
   *              the collection of objects that the query will be processed
   *              against.  In most cases, the scope object will either be
   *              a BISpace or a BISpaceNode.
   * @param query The query to be processed.
   * @return CloseableIterator
   */
  CloseableIterator<Entity> query(OrdTarget scope, OrdQuery query);

  /**
   * Convenience method that can be called to perform default validation against
   * the given query ORDs. The validation ensures that only the "station", "slot",
   * "h", "neql", "bql", "namespace", "sys", "nspace", "virtual", "single", and
   * "hierarchy" ORD schemes are allowed to be present in the given query ORDs.
   *
   * @throws LocalizableException If the validation fails, this exception is thrown
   * indicating the reason for the validation failure
   *
   * @since Niagara 4.4
   */
  static void validateQueryOrds(BOrdList queryOrds) throws LocalizableException
  {
    Set<String> invalidOrdSchemes = Collections.emptySet();
    for (BOrd queryOrd : queryOrds)
    {
      OrdQuery[] ordQueries = queryOrd.parse();
      for (OrdQuery ordQuery : ordQueries)
      {
        String ordScheme = ordQuery.getScheme();
        if (!VALID_QUERY_ORD_SCHEMES.contains(ordScheme))
        {
          if (invalidOrdSchemes.isEmpty())
          {
            invalidOrdSchemes = new LinkedHashSet<>();
          }
          invalidOrdSchemes.add(ordScheme);
        }
      }
    }

    if (!invalidOrdSchemes.isEmpty())
    {
      String invalidOrdSchemesStr = String.join(", ", invalidOrdSchemes);
      throw new LocalizableException("baja", "query.invalidOrdSchemes", new Object[] { invalidOrdSchemesStr });
    }
  }

  /**
   * The default set of valid ORD schemes that can be used in a query ORD.
   *
   * @since Niagara 4.4
   */
  Set<String> VALID_QUERY_ORD_SCHEMES = Collections.unmodifiableSet(Stream.of(
    BStationScheme.INSTANCE.getId(),
    BSlotScheme.INSTANCE.getId(),
    BHandleScheme.INSTANCE.getId(),
    "neql",
    "bql",
    "namespace",
    "sys",
    "nspace",
    "virtual",
    "single",
    "hierarchy").collect(Collectors.toSet()));

  /**
   * The set of all query handlers trusted to do their own permission checking
   *
   * @since Niagara 4.6
   */
  Set<BTypeSpec> TRUSTED_QUERY_HANDLERS = Collections.unmodifiableSet(Stream.of(
    BTypeSpec.make("fox:FoxQueryHandler"),
    BTypeSpec.make("neql:NeqlComponentQueryHandler"),
    BTypeSpec.make("orientSystemDb:OrientSystemDb")
  ).collect(Collectors.toSet()));
}
