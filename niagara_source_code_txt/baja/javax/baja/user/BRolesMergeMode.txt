/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.user;

import java.util.HashSet;
import java.util.Set;

import javax.baja.nre.annotations.NiagaraEnum;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.Range;
import javax.baja.sys.BFrozenEnum;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Enum to determine user prototype roles merge policy.
 * <ul>
 *   <li>union: the union of all roles will be picked</li>
 *   <li>useFirst: roles will not be merged - the first value on the list will be picked</li>
 * </ul>
 *
 * @author Melanie Coggan on 2021-12-08
 * @since Niagara 4.12
 */
@NiagaraType
@NiagaraEnum(
  range = {
    @Range("union"),
    @Range("useFirst")
  }
)
public final class BRolesMergeMode
  extends BFrozenEnum
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.user.BRolesMergeMode(3387821244)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  /** Ordinal value for union. */
  public static final int UNION = 0;
  /** Ordinal value for useFirst. */
  public static final int USE_FIRST = 1;

  /** BRolesMergeMode constant for union. */
  public static final BRolesMergeMode union = new BRolesMergeMode(UNION);
  /** BRolesMergeMode constant for useFirst. */
  public static final BRolesMergeMode useFirst = new BRolesMergeMode(USE_FIRST);

  /** Factory method with ordinal. */
  public static BRolesMergeMode make(int ordinal)
  {
    return (BRolesMergeMode)union.getRange().get(ordinal, false);
  }

  /** Factory method with tag. */
  public static BRolesMergeMode make(String tag)
  {
    return (BRolesMergeMode)union.getRange().get(tag);
  }

  /** Private constructor. */
  private BRolesMergeMode(int ordinal)
  {
    super(ordinal);
  }

  public static final BRolesMergeMode DEFAULT = union;

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BRolesMergeMode.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Returns the preferred AutoLogoffSettings based on the merge type:
   * <ul>
   *   <li>union: the union of all roles will be returned.</li>
   *   <li>useFirst: currentRoles will be returned</li>
   * </ul>
   * @param currentRoles The current roles
   * @param proposedRoles The roles we want to merge with
   * @return A Set containing all the roles based on the merge type
   */
  public Set<String> getMergedValue(Set<String> currentRoles, Set<String> proposedRoles)
  {
    if (currentRoles == null || proposedRoles == null)
    {
      throw new IllegalArgumentException("currentRoles and proposedRoles cannot be null");
    }

    switch (getOrdinal())
    {
      case UNION:
        Set<String> mergedRoles = new HashSet<>(currentRoles);
        mergedRoles.addAll(proposedRoles);
        return mergedRoles;
      case USE_FIRST:
      default:
        return currentRoles;
    }
  }
}
