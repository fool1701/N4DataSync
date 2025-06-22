/** Copyright 2005 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.alarm;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BIcon;
import javax.baja.sys.BString;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.BFormat;

/**
 * BAlarmSourceInfo is a data structure used to define common alarm data for use
 * with the AlarmSupport class.
 *
 * @author    Blake M Puhak
 * @creation  04 Apr 05
 * @version   $Revision: 10$ $Date: 5/19/11 11:08:39 AM EDT$
 * @since     Niagara 3.0
 */
@NiagaraType
/*
 This is the alarm class used for routing this alarm.
 */
@NiagaraProperty(
  name = "alarmClass",
  type = "String",
  defaultValue = "defaultAlarmClass",
  facets = {
    @Facet(name = "BFacets.FIELD_EDITOR", value = "\"alarm:AlarmClassFE\""),
    @Facet(name = "BFacets.UX_FIELD_EDITOR", value = "\"alarm:AlarmClassEditor\"")
  }
)
/*
 Text descriptor for the source name of the alarm. Uses BFormat, but supports only lexicons.
 */
@NiagaraProperty(
  name = "sourceName",
  type = "BFormat",
  defaultValue = "BFormat.make(\"%parent.displayName%\")"
)
/*
 Text descriptor included in a to-fault alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "toFaultText",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")"
)
/*
 Text descriptor included in a to-offnormal alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "toOffnormalText",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")"
)
/*
 Text descriptor included in a to-normal alarm for this object. Uses BFormat.
 */
@NiagaraProperty(
  name = "toNormalText",
  type = "BFormat",
  defaultValue = "BFormat.make(\"\")"
)
/*
 Ord to link to for more information on this alarm.
 */
@NiagaraProperty(
  name = "hyperlinkOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = {
    @Facet(name = "BFacets.ORD_RELATIVIZE", value = "false"),
    @Facet("BFacets.make(\"chooseView\", true)")
  }
)
/*
 Sound to play when the alarm comes into the alarm console.
 Sound must be available on the client.
 */
@NiagaraProperty(
  name = "soundFile",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet(name = "BFacets.TARGET_TYPE", value = "\"file:AudioFile\"")
)
/*
 Icon to display for this alarm in the alarm console
 Icon must be available on the client.
 */
@NiagaraProperty(
  name = "alarmIcon",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  facets = @Facet(name = "BFacets.TARGET_TYPE", value = "\"file:ImageFile\"")
)
@NiagaraProperty(
  name = "alarmInstructions",
  type = "BAlarmInstructions",
  defaultValue = "BAlarmInstructions.make(\"\")"
)
/*
 Additional user defined alarm data.
 */
@NiagaraProperty(
  name = "metaData",
  type = "BFacets",
  defaultValue = "BFacets.DEFAULT"
)
public class BAlarmSourceInfo
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.alarm.BAlarmSourceInfo(2730290247)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "alarmClass"

  /**
   * Slot for the {@code alarmClass} property.
   * This is the alarm class used for routing this alarm.
   * @see #getAlarmClass
   * @see #setAlarmClass
   */
  public static final Property alarmClass = newProperty(0, "defaultAlarmClass", BFacets.make(BFacets.make(BFacets.FIELD_EDITOR, "alarm:AlarmClassFE"), BFacets.make(BFacets.UX_FIELD_EDITOR, "alarm:AlarmClassEditor")));

  /**
   * Get the {@code alarmClass} property.
   * This is the alarm class used for routing this alarm.
   * @see #alarmClass
   */
  public String getAlarmClass() { return getString(alarmClass); }

  /**
   * Set the {@code alarmClass} property.
   * This is the alarm class used for routing this alarm.
   * @see #alarmClass
   */
  public void setAlarmClass(String v) { setString(alarmClass, v, null); }

  //endregion Property "alarmClass"

  //region Property "sourceName"

  /**
   * Slot for the {@code sourceName} property.
   * Text descriptor for the source name of the alarm. Uses BFormat, but supports only lexicons.
   * @see #getSourceName
   * @see #setSourceName
   */
  public static final Property sourceName = newProperty(0, BFormat.make("%parent.displayName%"), null);

  /**
   * Get the {@code sourceName} property.
   * Text descriptor for the source name of the alarm. Uses BFormat, but supports only lexicons.
   * @see #sourceName
   */
  public BFormat getSourceName() { return (BFormat)get(sourceName); }

  /**
   * Set the {@code sourceName} property.
   * Text descriptor for the source name of the alarm. Uses BFormat, but supports only lexicons.
   * @see #sourceName
   */
  public void setSourceName(BFormat v) { set(sourceName, v, null); }

  //endregion Property "sourceName"

  //region Property "toFaultText"

  /**
   * Slot for the {@code toFaultText} property.
   * Text descriptor included in a to-fault alarm for this object. Uses BFormat.
   * @see #getToFaultText
   * @see #setToFaultText
   */
  public static final Property toFaultText = newProperty(0, BFormat.make(""), null);

  /**
   * Get the {@code toFaultText} property.
   * Text descriptor included in a to-fault alarm for this object. Uses BFormat.
   * @see #toFaultText
   */
  public BFormat getToFaultText() { return (BFormat)get(toFaultText); }

  /**
   * Set the {@code toFaultText} property.
   * Text descriptor included in a to-fault alarm for this object. Uses BFormat.
   * @see #toFaultText
   */
  public void setToFaultText(BFormat v) { set(toFaultText, v, null); }

  //endregion Property "toFaultText"

  //region Property "toOffnormalText"

  /**
   * Slot for the {@code toOffnormalText} property.
   * Text descriptor included in a to-offnormal alarm for this object. Uses BFormat.
   * @see #getToOffnormalText
   * @see #setToOffnormalText
   */
  public static final Property toOffnormalText = newProperty(0, BFormat.make(""), null);

  /**
   * Get the {@code toOffnormalText} property.
   * Text descriptor included in a to-offnormal alarm for this object. Uses BFormat.
   * @see #toOffnormalText
   */
  public BFormat getToOffnormalText() { return (BFormat)get(toOffnormalText); }

  /**
   * Set the {@code toOffnormalText} property.
   * Text descriptor included in a to-offnormal alarm for this object. Uses BFormat.
   * @see #toOffnormalText
   */
  public void setToOffnormalText(BFormat v) { set(toOffnormalText, v, null); }

  //endregion Property "toOffnormalText"

  //region Property "toNormalText"

  /**
   * Slot for the {@code toNormalText} property.
   * Text descriptor included in a to-normal alarm for this object. Uses BFormat.
   * @see #getToNormalText
   * @see #setToNormalText
   */
  public static final Property toNormalText = newProperty(0, BFormat.make(""), null);

  /**
   * Get the {@code toNormalText} property.
   * Text descriptor included in a to-normal alarm for this object. Uses BFormat.
   * @see #toNormalText
   */
  public BFormat getToNormalText() { return (BFormat)get(toNormalText); }

  /**
   * Set the {@code toNormalText} property.
   * Text descriptor included in a to-normal alarm for this object. Uses BFormat.
   * @see #toNormalText
   */
  public void setToNormalText(BFormat v) { set(toNormalText, v, null); }

  //endregion Property "toNormalText"

  //region Property "hyperlinkOrd"

  /**
   * Slot for the {@code hyperlinkOrd} property.
   * Ord to link to for more information on this alarm.
   * @see #getHyperlinkOrd
   * @see #setHyperlinkOrd
   */
  public static final Property hyperlinkOrd = newProperty(0, BOrd.NULL, BFacets.make(BFacets.make(BFacets.ORD_RELATIVIZE, false), BFacets.make("chooseView", true)));

  /**
   * Get the {@code hyperlinkOrd} property.
   * Ord to link to for more information on this alarm.
   * @see #hyperlinkOrd
   */
  public BOrd getHyperlinkOrd() { return (BOrd)get(hyperlinkOrd); }

  /**
   * Set the {@code hyperlinkOrd} property.
   * Ord to link to for more information on this alarm.
   * @see #hyperlinkOrd
   */
  public void setHyperlinkOrd(BOrd v) { set(hyperlinkOrd, v, null); }

  //endregion Property "hyperlinkOrd"

  //region Property "soundFile"

  /**
   * Slot for the {@code soundFile} property.
   * Sound to play when the alarm comes into the alarm console.
   * Sound must be available on the client.
   * @see #getSoundFile
   * @see #setSoundFile
   */
  public static final Property soundFile = newProperty(0, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, "file:AudioFile"));

  /**
   * Get the {@code soundFile} property.
   * Sound to play when the alarm comes into the alarm console.
   * Sound must be available on the client.
   * @see #soundFile
   */
  public BOrd getSoundFile() { return (BOrd)get(soundFile); }

  /**
   * Set the {@code soundFile} property.
   * Sound to play when the alarm comes into the alarm console.
   * Sound must be available on the client.
   * @see #soundFile
   */
  public void setSoundFile(BOrd v) { set(soundFile, v, null); }

  //endregion Property "soundFile"

  //region Property "alarmIcon"

  /**
   * Slot for the {@code alarmIcon} property.
   * Icon to display for this alarm in the alarm console
   * Icon must be available on the client.
   * @see #getAlarmIcon
   * @see #setAlarmIcon
   */
  public static final Property alarmIcon = newProperty(0, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, "file:ImageFile"));

  /**
   * Get the {@code alarmIcon} property.
   * Icon to display for this alarm in the alarm console
   * Icon must be available on the client.
   * @see #alarmIcon
   */
  public BOrd getAlarmIcon() { return (BOrd)get(alarmIcon); }

  /**
   * Set the {@code alarmIcon} property.
   * Icon to display for this alarm in the alarm console
   * Icon must be available on the client.
   * @see #alarmIcon
   */
  public void setAlarmIcon(BOrd v) { set(alarmIcon, v, null); }

  //endregion Property "alarmIcon"

  //region Property "alarmInstructions"

  /**
   * Slot for the {@code alarmInstructions} property.
   * @see #getAlarmInstructions
   * @see #setAlarmInstructions
   */
  public static final Property alarmInstructions = newProperty(0, BAlarmInstructions.make(""), null);

  /**
   * Get the {@code alarmInstructions} property.
   * @see #alarmInstructions
   */
  public BAlarmInstructions getAlarmInstructions() { return (BAlarmInstructions)get(alarmInstructions); }

  /**
   * Set the {@code alarmInstructions} property.
   * @see #alarmInstructions
   */
  public void setAlarmInstructions(BAlarmInstructions v) { set(alarmInstructions, v, null); }

  //endregion Property "alarmInstructions"

  //region Property "metaData"

  /**
   * Slot for the {@code metaData} property.
   * Additional user defined alarm data.
   * @see #getMetaData
   * @see #setMetaData
   */
  public static final Property metaData = newProperty(0, BFacets.DEFAULT, null);

  /**
   * Get the {@code metaData} property.
   * Additional user defined alarm data.
   * @see #metaData
   */
  public BFacets getMetaData() { return (BFacets)get(metaData); }

  /**
   * Set the {@code metaData} property.
   * Additional user defined alarm data.
   * @see #metaData
   */
  public void setMetaData(BFacets v) { set(metaData, v, null); }

  //endregion Property "metaData"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAlarmSourceInfo.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BAlarmSourceInfo()
  {
  }

  /**
   * Make BFacets containing the the BAlarmSourceInfo properties for use in
   * BAlarmRecord.setAlarmData().
   * If a property has not been defined, it is not included in the AlarmData.
   * The state is used for determining which Text to use as the BAlarmRecord.MSG_TEXT.
   */
  public BFacets makeAlarmData(BSourceState state)
  {
    HashMap<String, BIDataValue> map = new HashMap<>();
    if (!getSourceName().toString().equals(""))
      map.put(BAlarmRecord.SOURCE_NAME, BString.make(getSourceName().format(this)));

    String msgTxt = "";
    if (state == BSourceState.offnormal)
      msgTxt = getToOffnormalText().toString();
    else if (state == BSourceState.fault)
      msgTxt = getToFaultText().toString();
    else if (state == BSourceState.normal)
      msgTxt = getToNormalText().toString();
    else if (state == BSourceState.alert)
      msgTxt = getToOffnormalText().toString();
    if (!msgTxt.equals(""))
      map.put(BAlarmRecord.MSG_TEXT, BString.make(msgTxt));

    if (getHyperlinkOrd() != BOrd.NULL)
      map.put(BAlarmRecord.HYPERLINK_ORD, BString.make(getHyperlinkOrd().toString()));
    if (getSoundFile() != BOrd.NULL)
      map.put(BAlarmRecord.SOUND_FILE, BString.make(getSoundFile().toString()));
    if (getAlarmIcon() != BOrd.NULL)
      map.put(BAlarmRecord.ICON, BString.make(getAlarmIcon().toString()));

    try
    {
      if (!getAlarmInstructions().equals(BAlarmInstructions.NULL))
        map.put(BAlarmRecord.INSTRUCTIONS, BString.make(getAlarmInstructions().encodeToString()));
    }
    catch(Exception e)
    {
      Logger.getLogger("alarm").log(Level.SEVERE, "Could not encode Alarm Instructions", e);
    }

    String[] keys = getMetaData().list();
    for (String key : keys)
    {
      map.put(key, (BIDataValue)getMetaData().get(key));
    }

    return BFacets.make(map);
  }

  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("navOnly/alarmService.png");

}
