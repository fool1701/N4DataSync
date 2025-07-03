/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.types.bog;

import java.io.IOException;
import java.io.OutputStream;

import javax.baja.file.BIFileStore;
import javax.baja.io.ValueDocEncoder;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPassword;
import javax.baja.security.BPasswordHistory;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * Palette file is just a bog file with a different
 * extension and icon.
 *
 * @author    Brian Frank       
 * @creation  14 Apr 03
 * @version   $Revision: 5$ $Date: 7/8/11 7:32:57 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "palette")
)
public class BPaletteFile
  extends BBogFile
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.file.types.bog.BPaletteFile(3294567355)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPaletteFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BPaletteFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BPaletteFile()
  {  
  }
   
////////////////////////////////////////////////////////////////
// Overrides
////////////////////////////////////////////////////////////////

  /**
   * Save the associated space.  Will not compress contents.
   */
  @Override
  protected void doSave() throws Exception {
    BBogSpace space = getBogSpace();
    space.checkLock();

    WipePasswordCleanEncoder encoder = new WipePasswordCleanEncoder(space.bogFile.getOutputStream());
    encoder.setZipped(false);
    encoder.encodeDocument(space.getRootComponent());
    encoder.close();
    space.modified = false;
    updateHeaderVariablesFromSpace();
  }


  /**
   * Makes sure BPassword or BPasswordHistory type are not
   * encoded.
   */
  class WipePasswordCleanEncoder extends ValueDocEncoder
  {
    public WipePasswordCleanEncoder(OutputStream out) throws IOException
    {
      super(new BogEncoderPlugin(out), null);
    }

    /**
     * BPassword or BPasswordHistory type are not being encoded.
     * So it will remove dynamic password slots (frozen ones will revert to a default value)
     * @param type the Type to check.
     * @return true if the value is black listed and shouldn't be encoded.
     */
    public boolean isTypeBlackListed(Type type)
    {
      if (type.is(BPassword.TYPE) || type.is(BPasswordHistory.TYPE)) {
        return true;
      }

      return super.isTypeBlackListed(type);
    }
  }

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon() { return icon; }
  private static final BIcon icon = BIcon.std("palette.png");

  @Override
  public void setReversibleEncryptionPassPhrase(BPassword value)
    throws Exception
  {
    throw new IllegalArgumentException("Palette file does not use a pass phrase");
  }

  @Override
  public boolean validateReversibleEncryptionPassPhrase(BPassword value)
    throws Exception
  {
    throw new IllegalArgumentException("Palette file does not use a pass phrase");
  }

  @Override
  public boolean canUseReversibleEncryptionPassPhrase() throws Exception
  {
    return false;
  }

  @Override
  public boolean isReversibleEncryptionPassPhraseAvailable()
  {
    return false;
  }

  @Override
  public boolean usesKeyRingEncryption()
  {
    return false;
  }

  @Override
  public void updateReversibleEncryptionPassPhrase(BPassword newValue)
    throws Exception
  {
    throw new IllegalArgumentException("Palette file does not use a pass phrase");
  }
}
