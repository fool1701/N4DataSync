/*
 * Copyright 2003, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.history;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Optional;

import javax.baja.control.BControlPoint;
import javax.baja.history.ext.BHistoryExt;
import javax.baja.naming.SlotPath;
import javax.baja.naming.SyntaxException;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.annotations.NoSlotomatic;
import javax.baja.sys.BObject;
import javax.baja.sys.BSimple;
import javax.baja.sys.Property;
import javax.baja.sys.SlotCursor;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BHistoryId is a unique identifier for a history within an
 * entire system.  Is is composed of two parts, the source
 * device name and the history name.  A device name must
 * be unique within a system and a history name must be
 * unique within a device so together that form a unique
 * identifier. The history name is limited to 200 characters.
 *
 * @author    John Sublett
 * @creation  06 Mar 2003
 * @version   $Revision: 15$ $Date: 8/18/09 4:28:34 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NoSlotomatic
public final class BHistoryId
  extends BSimple
{

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////

  /**
   * Create an id with the specified station name and history name.
   *
   * @param deviceName The name of the station where the
   *   history data is collected.
   * @param historyName The name of the history.
   */
  public static BHistoryId make(String deviceName, String historyName)
  {
    if (((deviceName == null) || (deviceName.length() == 0)) &&
        ((historyName == null) || (historyName.length() == 0)))
      return NULL;
    else
      return (BHistoryId)(new BHistoryId(deviceName, historyName).intern());
  }

  /**
   * Parse the specified id string into an id instance.
   */
  public static BHistoryId make(String idText)
  {
    if (idText.length() == 0) return NULL;

    try
    {
      int deviceSlash;
      int nameSlash;

      deviceSlash = idText.indexOf('/');
      if (deviceSlash != 0)
      {
        // 3/17/05 S. Hoye added a check for the shorthand String, "^"
        if ((idText.startsWith(DEVICE_SHORTHAND)) ||
            (idText.startsWith(NIAGARA_STATION_SHORTHAND))) // issue 8631 - also check for "@" shorthand
        {
          if (deviceSlash < 0)
          {
            String deviceName = idText.substring(0, 1);
            String historyName = idText.substring(1);
            return (BHistoryId)(new BHistoryId(deviceName, historyName).intern());
          }
          else
            throw new SyntaxException("Invalid history id: " + idText);
        } // 3/17/05 end S. Hoye additions
        throw new SyntaxException("History id must start with a /. (" + idText + ")");
      }

      nameSlash = idText.indexOf('/', deviceSlash+1);
      if (nameSlash == -1)
        throw new SyntaxException("History name is required. (" + idText + ")");

      if (idText.indexOf('/', nameSlash+1) != -1)
        throw new SyntaxException("Invalid history id: " + idText);

      String deviceName = idText.substring(deviceSlash+1, nameSlash);
      String historyName = idText.substring(nameSlash+1);
      return (BHistoryId)(new BHistoryId(deviceName, historyName).intern());
    }
    catch(SyntaxException e)
    {
      throw e;
    }
    catch(Exception e)
    {
      throw new SyntaxException(e);
    }
  }

  private BHistoryId(String deviceName, String historyName)
  {
    this.deviceName = deviceName;
    this.historyName = historyName;

    hashCode = toString().hashCode();
  }

  /**
   * Get the station name.
   */
  public String getDeviceName()
  {
    return deviceName;
  }

  /**
   * Get the history name.
   */
  public String getHistoryName()
  {
    return historyName;
  }

  /**
   * Get the unescaped history name.
   */
  public String getHistoryDisplayName()
  {
    return SlotPath.unescape(historyName);
  }

  /**
   * Test whether this history id has shorthand for the
   * device name.
   *
   * @return Returns true if the device name is ^ false otherwise.
   */
  public boolean isShorthand()
  {
    return getDeviceName().equals(DEVICE_SHORTHAND);
  }

  /**
   * Create a shorthand id based on this full id.
   * The result id will have the shorthand character, ^,
   * replacing the device name specified if this id
   * currently contains a matching device name.
   *
   * @param deviceName The name of the device to be substituted
   *   by the shorthand character.  If this id
   *   uses this name, then the ^ shorthand character
   *   will be substituted for the device name in the resulting id.
   */
  public BHistoryId toShorthand(String deviceName)
  { // 3/17/05 S. Hoye added this method to convert to shorthand, "^"
    if (isValid())
    {
      if ((deviceName != null) && (deviceName.equals(this.deviceName)))
        return make(DEVICE_SHORTHAND, historyName);
    }
    return this;
  }

  /**
   * Create a full id based on this id, which could contain one of
   * the shorthand characters, ^ or @, for the device name.  The result
   * id will have the shorthand character replaced with the
   * device name provided.
   *
   * @param deviceName The name of the device to substitute for the
   *   shorthand character, if it exists.  If this id
   *   contains the ^ or @ shorthand characters for the device name, this
   *   name will be substituted in the resulting id.
   */
  public BHistoryId fromShorthand(String deviceName)
  { // 3/17/05 S. Hoye added this method to convert from shorthand, "^"
    if (isValid())
    {
      if ((this.deviceName.equals(DEVICE_SHORTHAND)) ||
          (this.deviceName.equals(NIAGARA_STATION_SHORTHAND))) // issue 8631 - also check for "@" shorthand
        return make(deviceName, historyName);
    }
    return this;
  }

////////////////////////////////////////////////////////////////
// Name validation
////////////////////////////////////////////////////////////////

  /**
   * Is the history name valid?  This method does not
   * check for uniqueness.  If invalid, a HistoryNameException
   * is thrown.
   *
   * @exception HistoryNameException Thrown if the specified name
   *   is not valid.
   */
  public void validateName()
  {
    validateName(historyName);
  }

  /**
   * Is the specified history name valid?  This method does not
   * check for uniqueness.  If invalid, a HistoryNameException
   * is thrown.
   *
   * @exception HistoryNameException Thrown if the specified name
   *   is not valid.
   */
  public static void validateName(String test)
  {
    if (test == null)
      throw new HistoryNameException("History name cannot be null.");

    if (!SlotPath.isValidName(test))
      throw new HistoryNameException("History name contains an invalid character.");

    if (test.length() > MAX_NAME_LENGTH)
      throw new HistoryNameException
        ("History name exceeds maximum length. (" + test.length() + " > " + MAX_NAME_LENGTH + ")");
  }

  /**
   * Is the specified history name valid?  This method does not check for
   * uniqueness.
   */
  public static boolean isValidName(String test)
  {
    try
    {
      validateName(test);
      return true;
    }
    catch(HistoryNameException ex)
    {
      return false;
    }
  }

////////////////////////////////////////////////////////////////
// BSimple
////////////////////////////////////////////////////////////////

  /**
   * Get the hash code of the id.
   */
  public int hashCode()
  {
    return hashCode;
  }

  public boolean equals(Object o)
  {
    if (!(o instanceof BHistoryId)) return false;

    BHistoryId other = (BHistoryId)o;

    return deviceName.equals(other.deviceName) &&
           historyName.equals(other.historyName);
  }

  /**
   * Encode the id to the specified output.
   */
  @Override
  public void encode(DataOutput out)
    throws IOException
  {
    out.writeUTF(deviceName);
    out.writeUTF(historyName);
  }

  /**
   * Decode the id from the specified input.
   */
  @Override
  public BObject decode(DataInput in)
    throws IOException
  {
    return new BHistoryId(in.readUTF(), in.readUTF()).intern();
  }

  /**
   * Encode the id to string.
   */
  @Override
  public String encodeToString()
  {
    if (isNull()) return "";

    // 3/17/05 S. Hoye added a check for the shorthand String, "^"
    if ((deviceName.equals(DEVICE_SHORTHAND)) ||
        (deviceName.equals(NIAGARA_STATION_SHORTHAND))) // issue 8631 - also check for "@" shorthand
    {
      return deviceName + historyName;
    } // 3/17/05 end S. Hoye additions

    return "/" + deviceName + "/" + historyName;
  }

  /**
   * Decode an id from the specified string.
   */
  @Override
  public BObject decodeFromString(String s)
  {
    return make(s);
  }

////////////////////////////////////////////////////////////////
// Nullable
////////////////////////////////////////////////////////////////

  /**
   * Is this a null instance of BHistoryId.
   */
  @Override
  public boolean isNull()
  {
    return deviceName.equals("") &&
           historyName.equals("");
  }

  /**
   * Is this id valid for an active history?
   */
  public boolean isValid()
  {
    return !deviceName.equals("") &&
           !historyName.equals("");
  }

////////////////////////////////////////////////////////////////
// Utility
////////////////////////////////////////////////////////////////

  /**
   * Returns an Optional containing the HistoryId retrieved on the
   * first enabled HistoryExt found under the given point, if one exists.
   * If no enabled HistoryExts are found, the fallback will be to return the
   * first disabled HistoryExt found. In either case, if the HistoryId is in
   * shorthand form, and it can't be converted to long form, an empty Optional
   * will be returned. Also if no HistoryExt is found, an empty Optional is returned.
   *
   * @param point - The ControlPoint instance to check for the existence of
   *              a HistoryExt child and return its HistoryId
   * @return an Optional containing the BHistoryId, or empty if one is not found
   * @since Niagara 4.0
   */
  public static Optional<BHistoryId> getHistoryIdFromPoint(BControlPoint point)
  {
    SlotCursor<Property> c = point.getProperties();

    BHistoryId id = null;
    while (c.next(BHistoryExt.class))
    {
      BHistoryExt ext = (BHistoryExt)c.get();
      if (ext.getEnabled() || (id == null))
      {
        BHistoryConfig config = ext.getHistoryConfig();
        BHistoryId historyId = config.getId();
        if (historyId.isShorthand())
        {
          if (Sys.getStation() != null)
          {
            historyId = historyId.fromShorthand(Sys.getStation().getStationName());
          }
          else
          {
            historyId = BHistoryId.NULL;
          }
        }
        id = historyId;
        if (ext.getEnabled())
        {
          break;
        }
      }
    }

    if ((id != null) && (!id.isNull()))
    {
      return Optional.of(id);
    }
    else
    {
      return Optional.empty();
    }
  }

////////////////////////////////////////////////////////////////
// Type
////////////////////////////////////////////////////////////////

  public static final BHistoryId DEFAULT = new BHistoryId("" ,"");
  public static final BHistoryId NULL    = DEFAULT;

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BHistoryId.class);

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  /**
   * History Name MAX_NAME_LENGTH is increased to 200 from 44 in Niagara 4.12.
   * The previous value for MAX_NAME_LENGTH (44) is now captured in LEGACY_HISTORY_NAME_MAX_LIMIT
   */
  public static final int MAX_NAME_LENGTH = 200;

  /**
   * @since Niagara 4.12
   */
  public static final int LEGACY_HISTORY_NAME_MAX_LIMIT = 44;

  // 3/17/05 S. Hoye added this constant for shorthand conversion, "^"
  private static final String DEVICE_SHORTHAND = "^";

  // 8/15/06 issue 8631: shorthand conversion for parent Niagara Station, "@"
  private static final String NIAGARA_STATION_SHORTHAND = "@";

  private String deviceName;
  private String historyName;

  // cache the hashCode since it is constant and used so often
  private int hashCode;
}
