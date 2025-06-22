/*
 * Copyright (c) 2017 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.options;

import javax.baja.license.FeatureNotLicensedException;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Flags;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BWidget;

import com.tridium.nre.security.SecurityInitializer;
import com.tridium.sys.license.LicenseUtil;

/**
 * This class allows the user to set the various FIPS configuration options.
 *
 * @author Melanie Coggan on 7/6/2017
 * @since Niagara 4.5
 */
@NiagaraType
/*
 Determines whether or not FIPS options will be displayed
 */
@NiagaraProperty(
  name = "showFipsOptions",
  type = "boolean",
  defaultValue = "false"
)
/*
 Determines whether or not FIPS options will be selected by default
 */
@NiagaraProperty(
  name = "checkFipsOptionsByDefault",
  type = "boolean",
  defaultValue = "false"
)
/*
 Determines whether or not Workbench will start in FIPS mode
 */
@NiagaraProperty(
  name = "startWorkbenchInFipsMode",
  type = "boolean",
  defaultValue = "true"
)
@NiagaraProperty(
  name = "processed",
  type = "boolean",
  defaultValue = "false",
  flags = Flags.HIDDEN
)
public class BFipsOptions
  extends BUserOptions
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.options.BFipsOptions(2987263654)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "showFipsOptions"

  /**
   * Slot for the {@code showFipsOptions} property.
   * Determines whether or not FIPS options will be displayed
   * @see #getShowFipsOptions
   * @see #setShowFipsOptions
   */
  public static final Property showFipsOptions = newProperty(0, false, null);

  /**
   * Get the {@code showFipsOptions} property.
   * Determines whether or not FIPS options will be displayed
   * @see #showFipsOptions
   */
  public boolean getShowFipsOptions() { return getBoolean(showFipsOptions); }

  /**
   * Set the {@code showFipsOptions} property.
   * Determines whether or not FIPS options will be displayed
   * @see #showFipsOptions
   */
  public void setShowFipsOptions(boolean v) { setBoolean(showFipsOptions, v, null); }

  //endregion Property "showFipsOptions"

  //region Property "checkFipsOptionsByDefault"

  /**
   * Slot for the {@code checkFipsOptionsByDefault} property.
   * Determines whether or not FIPS options will be selected by default
   * @see #getCheckFipsOptionsByDefault
   * @see #setCheckFipsOptionsByDefault
   */
  public static final Property checkFipsOptionsByDefault = newProperty(0, false, null);

  /**
   * Get the {@code checkFipsOptionsByDefault} property.
   * Determines whether or not FIPS options will be selected by default
   * @see #checkFipsOptionsByDefault
   */
  public boolean getCheckFipsOptionsByDefault() { return getBoolean(checkFipsOptionsByDefault); }

  /**
   * Set the {@code checkFipsOptionsByDefault} property.
   * Determines whether or not FIPS options will be selected by default
   * @see #checkFipsOptionsByDefault
   */
  public void setCheckFipsOptionsByDefault(boolean v) { setBoolean(checkFipsOptionsByDefault, v, null); }

  //endregion Property "checkFipsOptionsByDefault"

  //region Property "startWorkbenchInFipsMode"

  /**
   * Slot for the {@code startWorkbenchInFipsMode} property.
   * Determines whether or not Workbench will start in FIPS mode
   * @see #getStartWorkbenchInFipsMode
   * @see #setStartWorkbenchInFipsMode
   */
  public static final Property startWorkbenchInFipsMode = newProperty(0, true, null);

  /**
   * Get the {@code startWorkbenchInFipsMode} property.
   * Determines whether or not Workbench will start in FIPS mode
   * @see #startWorkbenchInFipsMode
   */
  public boolean getStartWorkbenchInFipsMode() { return getBoolean(startWorkbenchInFipsMode); }

  /**
   * Set the {@code startWorkbenchInFipsMode} property.
   * Determines whether or not Workbench will start in FIPS mode
   * @see #startWorkbenchInFipsMode
   */
  public void setStartWorkbenchInFipsMode(boolean v) { setBoolean(startWorkbenchInFipsMode, v, null); }

  //endregion Property "startWorkbenchInFipsMode"

  //region Property "processed"

  /**
   * Slot for the {@code processed} property.
   * @see #getProcessed
   * @see #setProcessed
   */
  public static final Property processed = newProperty(Flags.HIDDEN, false, null);

  /**
   * Get the {@code processed} property.
   * @see #processed
   */
  public boolean getProcessed() { return getBoolean(processed); }

  /**
   * Set the {@code processed} property.
   * @see #processed
   */
  public void setProcessed(boolean v) { setBoolean(processed, v, null); }

  //endregion Property "processed"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFipsOptions.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/
////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  public static BFipsOptions getOptions()
  {
    if (options == null)
    {
      try
      {
        options = (BFipsOptions)load(TYPE);
      }
      catch (Exception e)
      {
        options = new BFipsOptions();
        options.setCheckFipsOptionsByDefault(false);
        options.setShowFipsOptions(SecurityInitializer.getInstance().isFips());
      }
    }
    return options;
  }


  @Override
  public void loaded()
    throws Exception
  {
    super.loaded();
    if (!getProcessed())
    {
      //NCCB-32327: Now that we are unlikely to throw an exception in
      //getOptions we will continue using the SecurityInitializer to determine
      //the showFipsOptions when BWidget.getApplication is null.
      if (BWidget.getApplication() == null)
      {
        setCheckFipsOptionsByDefault(false);
        setShowFipsOptions(SecurityInitializer.getInstance().isFips());
      }
      else
      {
        try
        {
          Sys.getLicenseManager().checkFeature(LicenseUtil.TRIDIUM_VENDOR, "fips140-2");
          setShowFipsOptions(true);
          setCheckFipsOptionsByDefault(true);
        }
        catch (FeatureNotLicensedException ignore)
        {}
      }

      setProcessed(true);
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////
  private static BFipsOptions options = null;
}
