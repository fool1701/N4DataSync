/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.HashMap;

import javax.baja.nre.util.IntHashMap;
import javax.baja.sync.Transaction;
import javax.baja.util.Lexicon;

/**
 * Flags are boolean values which are stored as
 * a bitmask on each slot in a BComplex.  Some
 * flags apply to all slot types, while some only
 * have meaning for certain slot types.
 * <p>
 * <table border="1" summary="Description of the different flags and their uses">
 *
 * <tr><th align="left">Flag</th><th>Char</th>
 * <th>Applies</th>
 * <th>Description</th></tr>
 *
 * <tr><th align="left">readonly</th><td>r</td>
 * <td>P</td><td>
 * The {@code readonly} flag is used to indicate
 * slots which are not accessible to users.
 * </td></tr>
 *
 * <tr><th align="left">transient</th><td>t</td>
 * <td>P</td><td>
 * Transient properties do not get persisted when saving
 * an object graph to the file system.  Transient properties
 * are usually also readonly, unless they are designed to
 * be a linkable input slot.
 * </td></tr>
 *
 * <tr><th align="left">hidden</th><td>h</td>
 * <td>P,A,T</td><td>
 * Hidden slots are designed to be invisible to the user,
 * and exist only for Java developers.  User interfaces
 * should rarely display hidden slots.
 * </td></tr>
 *
 * <tr><th align="left">summary</th><td>s</td>
 * <td>P</td><td>
 * Summary properties are the focal points of any given BComponent.
 * This flag is used by user interface tools to indicate primary
 * properties for display.  This might be as a column in a table,
 * or as a glyph in a graphical programming tool.
 * </td></tr>
 *
 * <tr><th align="left">async</th><td>a</td>
 * <td>A</td><td>
 * By default action are invoked synchronously on the caller's
 * thread.  By using the {@code async} flag on an action,
 * invocations are coalesced and executed asynchronously at some
 * point in the near future on the engine's thread.
 * </td></tr>
 *
 * <tr><th align="left">noRun</th><td>n</td>
 * <td>P</td><td>
 * NoRun properties prevent start/stop from recursing on
 * properties with this flag set.
 * </td></tr>
 *
 * <tr><th align="left">defaultOnClone</th><td>d</td>
 * <td>P</td><td>
 * Specifies that when an object is cloned via the newCopy()
 * method these properties revert to their default value, not
 * the clone source's value. Copy hints for newCopy() can
 * control how this flag affects the copy in three ways:
 * (1) Revert all properties with this flag;
 * (2) Revert all read-only properties with this flag;
 * (3) Ignore this flag, retain all non-default values.
 * See {@code CopyHints} for details.
 * </td></tr>
 *
 * <tr><th align="left">confirmRequired</th><td>c</td>
 * <td>A</td><td>
 * When the action is invoked by a user, a confirmation
 * dialog must be acknowledged before proceeding.
 * </td></tr>
 *
 * <tr><th align="left">operator</th><td>c</td>
 * <td>P,A,T</td><td>
 * This makes a slot an operator security level.  By default
 * when this flag is clear, the slot is an admin security level.
 * Note that a BStruct slot marked with the operator flag applies
 * to all of the BStruct's child slots, regardless of whether
 * the BStruct child slots have the operator flag set or not.
 * </td></tr>
 *
 * <tr><th align="left">executeOnChange</th><td>x</td>
 * <td>P</td><td>
 * Many objects such as the program object use this flag to
 * indicate that the component should be executed when this
 * property is changed.
 * </td></tr>
 *
 * <tr><th align="left">fanIn</th><td>f</td>
 * <td>P</td><td>
 * By default only one link may tied into a Property.  Setting
 * this flag allows multiple links to tie into a Property.
 * </td></tr>
 *
 * <tr><th align="left">noAudit</th><td>A</td>
 * <td>P,A</td><td>
 * Setting this flag prevents property changes and action
 * invocations from being audited.
 * </td></tr>
 *
 * <tr><th align="left">composite</th><td>p</td>
 * <td>P,A,T</td><td>
 * This flag is set and used internally to designate that
 * a slot was created using the composite feature.
 * </td></tr>
 *
 * <tr><th align="left">removeOnClone</th><td>R</td>
 * <td>P,A,T</td><td>
 * When an object is cloned via the newCopy()
 * method, any dynamic Slots that have this flag set are
 * removed from the clone.
 * </td></tr>
 *
 * <tr><th align="left">metadata</th><td>m</td>
 * <td>P</td><td>
 * The metadata flag is simply used to mark properties
 * as metadata for the parent object.  It is simply used
 * to query objects by its metadata properties.
 * </td></tr>
 *
 * <tr><th align="left">linkTarget</th><td>L</td>
 * <td>P,A,T</td><td>
 * The linkTarget flag is simply used to mark slots that
 * are targeted by active link(s).  It should never be set directly,
 * as the linkTarget flag will be set by the framework.
 * </td></tr>
 *
 * <tr><th align="left">nonCritical</th><td>N</td>
 * <td>P</td><td>
 * The nonCritical flag is used to mark properties that shouldn't
 * be persisted in the BIDataRecoveryService store (for battery-less JACE support).
 * Any events for a property marked with this flag will not be recorded
 * in the BIDataRecoveryService store.  Similarly, a component marked with
 * this flag means that any events for any of its direct child properties
 * will not be recorded in the BIDataRecoveryService store.
 * </td></tr>
 *
 * <tr><th align="left">userDefined1</th><td>1</td>
 * <td>P,A,T</td><td>User defined.</td></tr>
 *
 * <tr><th align="left">userDefined2</th><td>2</td>
 * <td>P,A,T</td><td>User defined.</td></tr>
 *
 * <tr><th align="left">userDefined3</th><td>3</td>
 * <td>P,A,T</td><td>User defined.</td></tr>
 *
 * <tr><th align="left">userDefined4</th><td>4</td>
 * <td>P,A,T</td><td>User defined.</td></tr>
 *
 * </table>
 *
 * @author    Brian Frank
 * @creation  8 Mar 00
 * @version   $Revision: 42$ $Date: 8/3/10 1:04:04 PM EDT$
 * @since     Baja 1.0
 */
public final class Flags
{

////////////////////////////////////////////////////////////////
// Constants
////////////////////////////////////////////////////////////////

  // When adding new flags you must
  //   1) create new constant
  //   2) add to FLAGS array
  //   3) add convenience method
  //   4) add to baja.lexicon
  //   5) add to workbench:com.tridium.workbench.util.BFlagConfig

  public static final int READONLY             = 0x00000001;  // 'r'
  public static final int TRANSIENT            = 0x00000002;  // 't'
  public static final int HIDDEN               = 0x00000004;  // 'h'
  public static final int SUMMARY              = 0x00000008;  // 's'
  public static final int ASYNC                = 0x00000010;  // 'a'
  public static final int NO_RUN               = 0x00000020;  // 'n'
  public static final int DEFAULT_ON_CLONE     = 0x00000040;  // 'd'
  public static final int CONFIRM_REQUIRED     = 0x00000080;  // 'c'
  public static final int OPERATOR             = 0x00000100;  // 'o'
  public static final int EXECUTE_ON_CHANGE    = 0x00000200;  // 'x'
  public static final int FAN_IN               = 0x00000400;  // 'f'
  public static final int NO_AUDIT             = 0x00000800;  // 'A'
  public static final int COMPOSITE            = 0x00001000;  // 'p'
  public static final int REMOVE_ON_CLONE      = 0x00002000;  // 'R'
  public static final int METADATA             = 0x00004000;  // 'm'
  public static final int LINK_TARGET          = 0x00008000;  // 'L'
  public static final int NON_CRITICAL         = 0x00010000;  // 'N'
  public static final int USER_DEFINED_1       = 0x10000000;  // '1'
  public static final int USER_DEFINED_2       = 0x20000000;  // '2'
  public static final int USER_DEFINED_3       = 0x40000000;  // '3'
  public static final int USER_DEFINED_4       = 0x80000000;  // '4'

  private static final Flag[] FLAGS =
  {
    new Flag(READONLY,          'r', "readonly"),
    new Flag(TRANSIENT,         't', "transient"),
    new Flag(HIDDEN,            'h', "hidden"),
    new Flag(SUMMARY,           's', "summary"),
    new Flag(ASYNC,             'a', "async"),
    new Flag(NO_RUN,            'n', "noRun"),
    new Flag(DEFAULT_ON_CLONE,  'd', "defaultOnClone"),
    new Flag(CONFIRM_REQUIRED,  'c', "confirmRequired"),
    new Flag(OPERATOR,          'o', "operator"),
    new Flag(EXECUTE_ON_CHANGE, 'x', "executeOnChange"),
    new Flag(FAN_IN,            'f', "fanIn"),
    new Flag(NO_AUDIT,          'A', "noAudit"),
    new Flag(COMPOSITE,         'p', "composite"),
    new Flag(REMOVE_ON_CLONE,   'R', "removeOnClone"),
    new Flag(METADATA,          'm', "metadata"),
    new Flag(NON_CRITICAL,      'N', "nonCritical"),
    new Flag(LINK_TARGET,       'L', "linkTarget"),
    new Flag(USER_DEFINED_1,    '1', "userDefined1"),
    new Flag(USER_DEFINED_2,    '2', "userDefined2"),
    new Flag(USER_DEFINED_3,    '3', "userDefined3"),
    new Flag(USER_DEFINED_4,    '4', "userDefined4"),
  };

////////////////////////////////////////////////////////////////
// Lookups
////////////////////////////////////////////////////////////////

  /**
   * Get the list of flags.
   */
  public static Flag[] getFlags()
  {
    return FLAGS.clone();
  }

  /**
   * Get a Flag by its bit.  Excactly one bit must
   * be set in the specified bit parameter.  Return
   * null if not found.
   */
  public static Flag getByBit(int bit)
  {
    return (Flag)byBit.get(bit);
  }

  /**
   * Get a Flag by name.  Return null if not found.
   */
  public static Flag getByName(String name)
  {
    return byName.get(name);
  }

  /**
   * Get a Flag by symbol.  Return null if not found.
   */
  public static Flag getBySymbol(char symbol)
  {
    if (symbol < 128) return bySymbol[symbol];
    return null;
  }

  // lookup tables
  private static final IntHashMap byBit = new IntHashMap();
  private static final HashMap<String, Flag> byName   = new HashMap<>();
  private static final Flag[] bySymbol  = new Flag[128];
  static
  {
    for(int i=0; i<FLAGS.length; ++i)
    {
      Flag flag = FLAGS[i];
      byBit.put(flag.mask, flag);
      byName.put(flag.name, flag);
      bySymbol[flag.symbol] = flag;
    }
  }

////////////////////////////////////////////////////////////////
// Convenience
////////////////////////////////////////////////////////////////

  /**
   * @param comp complex
   * @param slot slot on complex
   * @param flags flags to check
   * @return true if the complex has the specified slot flags on the given slot
   * @since Niagara 4.10
   */
  public static boolean has(BComplex comp, Slot slot, int flags)
  {
    return hasAll(comp, slot, flags);
  }

  /**
   * @param comp complex
   * @param slot slot on complex
   * @param flags flags to check
   * @return true if the complex has any of the given slot flags. If no flags
   * are given to the method, returns true.
   * @since Niagara 4.10
   */
  public static boolean hasAny(BComplex comp, Slot slot, int... flags)
  {
    if (flags.length == 0) { return true; }
    return (comp.getFlags(slot) & collect(flags)) != 0;
  }

  /**
   * @param comp complex
   * @param slot slot on complex
   * @param flags flags to check
   * @return true if the complex has all of the given slot flags. If no flags
   * are given to the method, returns true.
   * @since Niagara 4.10
   */
  public static boolean hasAll(BComplex comp, Slot slot, int... flags)
  {
    if (flags.length == 0) { return true; }
    int flagsToCheck = collect(flags);
    return (comp.getFlags(slot) & flagsToCheck) == flagsToCheck;
  }

  /**
   * Add the specified flags.
   * @param comp component
   * @param slot slot on component
   * @param cx security context
   * @param flags flags to add
   * @since Niagara 4.10
   */
  public static void add(BComponent comp, Slot slot, Context cx, int... flags)
  {
    comp.setFlags(slot, add(comp.getFlags(slot), flags), cx);
  }

  /**
   * @param flags starting flags
   * @param flagsToAdd flags to add
   * @return a new integer with the specified flags added
   * @since Niagara 4.10
   */
  public static int add(int flags, int... flagsToAdd)
  {
    return flags | collect(flagsToAdd);
  }

  /**
   * Remove the specified flags.
   * @param comp component
   * @param slot slot on component
   * @param cx security context
   * @param flags flags to remove
   * @since Niagara 4.10
   */
  public static void remove(BComponent comp, Slot slot, Context cx, int... flags)
  {
    comp.setFlags(slot, remove(comp.getFlags(slot), flags), cx);
  }

  /**
   * @param flags starting flag
   * @param flagsToRemove flags to remove
   * @return a new integer with the specified flags removed
   * @since Niagara 4.10
   */
  public static int remove(int flags, int... flagsToRemove)
  {
    return flags & ~collect(flagsToRemove);
  }

  private static int collect(int... flags)
  {
    int result = 0;
    for (int flag : flags) { result |= flag; }
    return result;
  }

  /**
   * Return true if the transient flag is set for the slot.
   */
  public static boolean isTransient(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.TRANSIENT) != 0;
  }

  /**
   * Return true if the readonly flag is set for the slot.
   */
  public static boolean isReadonly(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.READONLY) != 0;
  }

  /**
   * Return true if the hidden flag is set for the slot.
   */
  public static boolean isHidden(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.HIDDEN) != 0;
  }

  /**
   * Return true if the summary flag is set for the slot.
   */
  public static boolean isSummary(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.SUMMARY) != 0;
  }

  /**
   * Return true if the async flag is set for the slot.
   */
  public static boolean isAsync(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.ASYNC) != 0;
  }

  /**
   * Return true if the no run flag is set for the slot.
   */
  public static boolean isNoRun(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.NO_RUN) != 0;
  }

  /**
   * Return true if the default on clone flag is set for the slot.
   */
  public static boolean isDefaultOnClone(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.DEFAULT_ON_CLONE) != 0;
  }

  /**
   * Return true if the confirm required flag is set for the slot.
   */
  public static boolean isConfirmRequired(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.CONFIRM_REQUIRED) != 0;
  }

  /**
   * Return true if the operator flag is set for the slot.
   */
  public static boolean isOperator(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.OPERATOR) != 0;
  }

  /**
   * Return true if the operator flag is clear for the slot.
   */
  public static boolean isAdmin(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.OPERATOR) == 0;
  }

  /**
   * Return true if the execute on change flag is set for the slot.
   */
  public static boolean isExecuteOnChange(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.EXECUTE_ON_CHANGE) != 0;
  }

  /**
   * Return true if the fan in flag is set for the slot.
   */
  public static boolean isFanIn(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.FAN_IN) != 0;
  }

  /**
   * Return true if the check set flag is set for the slot.
   */
  /*
  public static boolean isCheckSet(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.CHECK_SET) != 0;
  }
  */

  /**
   * Return true if the no audit flag is set for the slot.
   */
  public static boolean isNoAudit(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.NO_AUDIT) != 0;
  }

  /**
   * Return true if the composite flag is set for the slot.
   */
  public static boolean isComposite(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.COMPOSITE) != 0;
  }

  /**
   * Return true if the removeOnClone flag is set for the slot.
   *
   * @since Niagara 3.5
   */
  public static boolean isRemoveOnClone(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.REMOVE_ON_CLONE) != 0;
  }

  /**
   * Return true if the metadata flag is set for the slot.
   *
   * @since Niagara 3.5
   */
  public static boolean isMetadata(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.METADATA) != 0;
  }

  /**
   * Return true if the linkTarget flag is set for the slot.
   *
   * @since Niagara 3.6
   */
  public static boolean isLinkTarget(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.LINK_TARGET) != 0;
  }

  /**
   * Return true if the nonCritical flag is set for the slot.
   *
   * @since Niagara 3.6
   */
  public static boolean isNonCritical(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.NON_CRITICAL) != 0;
  }

  /**
   * Return true if the user defined 1 flag is set for the slot.
   */
  public static boolean isUserDefined1(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.USER_DEFINED_1) != 0;
  }

  /**
   * Return true if the user defined 2 flag is set for the slot.
   */
  public static boolean isUserDefined2(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.USER_DEFINED_2) != 0;
  }

  /**
   * Return true if the user defined 3 flag is set for the slot.
   */
  public static boolean isUserDefined3(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.USER_DEFINED_3) != 0;
  }

  /**
   * Return true if the user defined 4 flag is set for the slot.
   */
  public static boolean isUserDefined4(BComplex object, Slot slot)
  {
    return (object.getFlags(slot) & Flags.USER_DEFINED_4) != 0;
  }

////////////////////////////////////////////////////////////////
// Sets
////////////////////////////////////////////////////////////////

  /**
   * Set or clear the readonly flag for all the properties
   * which store a non-BComponent.
   */
  public static void setAllReadonly(BComponent c, boolean readonly, Context cx)
  {
    // do this all in a transaction
    Context tx = Transaction.start(c, cx);

    // iterate thru all the props
    Property[] props = c.getPropertiesArray();
    for(int i=0; i< props.length; i++)
    {
      Property prop = props[i];
      BObject val = c.get(prop);

      // skip properties which store a component
      if (val instanceof BComponent)
        continue;

      int flags = c.getFlags(prop);
      if (readonly)
      {
        // if readonly flag is cleared, then set it
        if ((flags & READONLY) == 0)
          c.setFlags(prop, flags | READONLY, tx);
      }
      else
      {
        // if readonly flag is set AND prop default (if frozen)
        // is not readonly then clear the big
        if ((flags & READONLY) != 0 &&
            (prop.isDynamic() || (prop.getDefaultFlags() & READONLY) == 0))
          c.setFlags(prop, flags & ~READONLY, tx);
      }
    }

    // commit the transaction
    try
    {
      Transaction.end(c, tx);
    }
    catch(Exception e)
    {
      throw new BajaRuntimeException("setAllReadonly failed", e);
    }
  }

////////////////////////////////////////////////////////////////
// Utils
////////////////////////////////////////////////////////////////

  /**
   * Encode a flags bitmask into its String
   * encoding using the char code for each set bit.
   */
  public static String encodeToString(int flags)
  {
    StringBuilder s = new StringBuilder(32);
    for(int i=0; i<FLAGS.length; ++i)
      if ((FLAGS[i].mask & flags) != 0) s.append(FLAGS[i].symbol);
    return s.toString();
  }

  /**
   * Decode the flags String which is a series
   * of flag char codes into its flags bitmask.
   * Characters which aren't known are ignored.
   */
  public static int decodeFromString(String flagsStr)
  {
    int flags = 0;
    for(int i=0; i<flagsStr.length(); ++i)
    {
      Flag flag = getBySymbol(flagsStr.charAt(i));
      if (flag != null) flags |= flag.mask;
    }
    return flags;
  }

  /**
   * Get a human readable, localized string for the specified flags.
   */
  public static String toDisplayString(int flags, Context cx)
  {
    if (flags == 0)
      return Lexicon.make(Sys.getBajaModule(), cx).getText("flag.none");

    StringBuilder s = new StringBuilder();
    for(int i=0; i<FLAGS.length; ++i)
    {
      Flag flag = FLAGS[i];
      if ((flag.mask & flags) != 0)
      {
        if (s.length() > 0) s.append(',').append(' ');
        s.append(flag.getDisplayName(cx));
      }
    }
    return s.toString();
  }

////////////////////////////////////////////////////////////////
// Flag
////////////////////////////////////////////////////////////////

  /**
   * Flag represents a single flag bit.
   */
  public static class Flag
  {
    /**
     * Private constructor.
     */
    Flag(int mask, char symbol, String name)
    {
      this.mask   = mask;
      this.symbol = symbol;
      this.name   = name;
      this.lexKey = "flag." + name;
    }

    /**
     * Get the bit mask of the flag (exactly one bit is set).
     */
    public int getMask()
    {
      return mask;
    }

    /**
     * Get the ASCII char symbol which identifies this flag.
     */
    public char getSymbol()
    {
      return symbol;
    }

    /**
     * Get the programatic name of the flag.
     */
    public String getName()
    {
      return name;
    }

    /**
     * Get the display name of the mask.
     */
    public String getDisplayName(Context cx)
    {
      return Lexicon.make(Sys.getBajaModule(), cx).getText(lexKey);
    }

    /**
     * Return name.
     */
    public String toString()
    {
      return name;
    }

    String name;
    char symbol;
    int mask;
    String lexKey;
  }

}

