/*
 * Copyright 2010, Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BIValidator;
import javax.baja.util.CannotValidateException;
import javax.baja.util.Lexicon;

/**
 * Validates a String to ensure it meets the requirements for a Station name
 *
 * @author		gjohnson
 * @creation 	23 Jul 2010
 * @version 	1
 * @since 		Niagara 3.5.29
 */
@NiagaraType
@NiagaraSingleton
public final class BStationNameValidator
    extends BObject
    implements BIValidator
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.util.BStationNameValidator(2747097003)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BStationNameValidator INSTANCE = new BStationNameValidator();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BStationNameValidator.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
  
////////////////////////////////////////////////////////////////
// BIValidator
////////////////////////////////////////////////////////////////  
  
  @Override
  public void validate(BObject value, Context cx)
      throws CannotValidateException
  {
    // Validate the String to ensure it can be used as the name of a Station
    String stationName = value.toString();
        
    if (!SlotPath.isValidName(stationName))
      throw new CannotValidateException(lex.getText("stationNameValidator.invalidName"));
    
    if (stationName.length() > maxStationNameLength)
      throw new CannotValidateException(lex.getText("stationNameValidator.nameTooLong"));
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  
  private static final Lexicon lex = Lexicon.make(BStationNameValidator.class);
  private static final int maxStationNameLength = 32;
}
