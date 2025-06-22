/**
 * Copyright 2009 Tridium, Inc. - All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Validates that a BString value can be parsed as a Version.
 *
 * @author   Matthew Giannini
 * @creation  Mar 3, 2009
 * @version  $Revision: 3$ $Date: 9/24/09 8:57:55 AM EDT$
 * @since     Niagara 3.5
 *
 */
@NiagaraType
public final class BVersionValidator extends BObject
  implements BIValidator
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BVersionValidator(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:39 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BVersionValidator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Validates that a BString value can be parsed as a Version.
   * 
   * @see Version
   */
  @Override
  public void validate(BObject value, Context cx) throws CannotValidateException
  {
    String test = null;
    try
    {
      Version v = new Version(test = ((BString)value).getString());
    }
    catch (Exception e)
    {
      throw new CannotValidateException(lex.getText("versionValidator.invalid", new Object[]{test}));
    }
  }
  
  private static final Lexicon lex = Lexicon.make(BVersionValidator.class);
}
