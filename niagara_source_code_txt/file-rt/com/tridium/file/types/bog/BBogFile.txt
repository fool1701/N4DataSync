/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.types.bog;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.baja.file.BIComponentFile;
import javax.baja.file.BIFileStore;
import javax.baja.file.BSubSpaceFile;
import javax.baja.file.types.text.BIXmlFile;
import javax.baja.io.ValueDocDecoder;
import javax.baja.io.ValueDocDecoder.BogDecoderPlugin;
import javax.baja.naming.BOrd;
import javax.baja.naming.SlotPath;
import javax.baja.nre.annotations.FileExt;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BIPasswordValidator;
import javax.baja.security.BPassword;
import javax.baja.security.BReversiblePasswordEncoder;
import javax.baja.space.BSpace;
import javax.baja.space.Mark;
import javax.baja.spy.SpyWriter;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.BObject;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.LocalizableRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Version;

import com.tridium.nre.security.EncryptionKeySource;
import com.tridium.nre.security.ISecretBytesSupplier;
import com.tridium.nre.security.PBEEncodingInfo;
import com.tridium.nre.security.SecretChars;
import com.tridium.nre.security.io.BogPasswordObjectEncoder;
import com.tridium.nre.util.FileLockException;
import com.tridium.util.PasswordUtil;
import com.tridium.util.PasswordUtil.PasswordPropertyUpdate;

/**
 * BBogFile stores a component space.
 *
 * @author    Brian Frank
 * @creation  14 Apr 03
 * @version   $Revision: 18$ $Date: 7/8/11 7:32:57 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType(
  ext = @FileExt(name = "bog")
)
@SuppressWarnings("ALL")
public class BBogFile
  extends BSubSpaceFile
  implements BIComponentFile, BIXmlFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.file.types.bog.BBogFile(270432163)1.0$ @*/
/* Generated Thu Jun 02 14:30:02 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBogFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct a file with the specified store.
   */
  public BBogFile(BIFileStore store)
  {
    super(store);
  }

  /**
   * Construct (must call setStore()).
   */
  public BBogFile()
  {
  }

////////////////////////////////////////////////////////////////
// Reversible Encryption
////////////////////////////////////////////////////////////////

  /**
   * Change the file's key source to {@link EncryptionKeySource#external} and
   * set its pass phrase to the given value. Any reversibly-encrypted values already
   * in the file are set to {@link BPassword#DEFAULT}
   *
   * @return a list of SlotPath for password values changed to {@link BPassword#DEFAULT}
   *
   * @since Niagara 4.0
   */
  public List<SlotPath> forceChangeReversibleEncryptionPassPhrase(BPassword value)
    throws Exception
  {
    Objects.requireNonNull(value);

    initFromHeader();
    if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.none))
    {
      setReversibleEncryptionPassPhrase(value);
      return Collections.emptyList();
    }

    if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring))
    {
      throw new IllegalArgumentException("Bog file cannot use a pass phrase");
    }

    open();

    try(SecretChars valueChars = value.getSecretChars())
    {
      bogPasswordObjectEncoder = BogPasswordObjectEncoder.makeExternal(valueChars);
      passPhraseValidator = BIPasswordValidator.fromPBEValidator(bogPasswordObjectEncoder.getPbeEncodingInfo());
      getBogSpace().setBogPasswordObjectEncoder(bogPasswordObjectEncoder);
      getBogSpace().modified = true;
    }

    getBogSpace().setReversibleEncryptionPassPhrase(value);

    List<SlotPath> result = PasswordUtil.forceClearReversiblePasswords(getBogSpace().getRootComponent());

    this.reversibleEncryptionPassPhrase = Optional.of(value);
    return result;
  }

  /**
   * Change the file's key source to {@link EncryptionKeySource#none}. Any reversibly-encrypted
   * values already in the file are set to {@link BPassword#DEFAULT}
   *
   * @return a list of SlotPath for password values changed to {@link BPassword#DEFAULT}
   *
   * @since Niagara 4.0
   */
  public List<SlotPath> forceClearReversibleEncryptionPassPhrase()
    throws Exception
  {
    initFromHeader();

    open();

    bogPasswordObjectEncoder = BogPasswordObjectEncoder.makeNone();
    passPhraseValidator = BPassword.DEFAULT;
    getBogSpace().setBogPasswordObjectEncoder(bogPasswordObjectEncoder);
    getBogSpace().modified = true;

    getBogSpace().clearReversibleEncryptionPassPhrase();

    List<SlotPath> result = PasswordUtil.forceClearReversiblePasswords(getBogSpace().getRootComponent());

    this.reversibleEncryptionPassPhrase = Optional.empty();

    return result;
  }

  /**
   * Supply a pass phrase to be used for decoding and encoding sensitive data.
   *
   * @since Niagara 4.0
   */
  public void setReversibleEncryptionPassPhrase(BPassword value)
    throws Exception
  {
    Objects.requireNonNull(value);

    initFromHeader();
    if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring))
    {
      throw new IllegalArgumentException("Bog file cannot use a pass phrase");
    }

    if (validateReversibleEncryptionPassPhrase(value))
    {
      if (!reversibleEncryptionPassPhrase.isPresent())
      {
        if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.none))
        {
          try(SecretChars valueChars = value.getSecretChars())
          {
            bogPasswordObjectEncoder = BogPasswordObjectEncoder.makeExternal(valueChars);
            passPhraseValidator = BIPasswordValidator.fromPBEValidator(bogPasswordObjectEncoder.getPbeEncodingInfo());
            if (getBogSpace() != null)
            {
              getBogSpace().setBogPasswordObjectEncoder(bogPasswordObjectEncoder);
              getBogSpace().modified = true;
            }
          }
        }
        if (getBogSpace() != null)
        {
          getBogSpace().setReversibleEncryptionPassPhrase(value);
          try (SecretChars passPhrase = value.getSecretChars();
               ISecretBytesSupplier key = bogPasswordObjectEncoder.passPhraseToKey(passPhrase))
          {
            PasswordPropertyUpdate passwordPropertyUpdate = password ->
              {
                if (password.getPasswordEncoder() instanceof BReversiblePasswordEncoder &&
                    ((BReversiblePasswordEncoder)password.getPasswordEncoder()).usesExternalEncryptionKey())
                {
                  BReversiblePasswordEncoder encoder = (BReversiblePasswordEncoder)password.getPasswordEncoder();
                  AccessController.doPrivileged((PrivilegedAction<Void>)() ->
                  {
                    encoder.setExternalEncryptionKey(key.get());
                    return null;
                  });
                }
                return password;
              };
            for (BComponent c : (Iterable<BComponent>)() -> getBogSpace().iterateAllComponents())
            {
              PasswordUtil.updatePasswords(c, c.getSlotPath(), passwordPropertyUpdate);
              Optional<RuntimeException> exception = passwordPropertyUpdate.exceptions().findFirst();
              if (exception.isPresent())
              {
                throw exception.get();
              }
            }
          }
        }
        this.reversibleEncryptionPassPhrase = Optional.of(value);
      }
    }
    else
    {
      throw new LocalizableRuntimeException("baja", "encryptionPassPhraseDoesntMatch");
    }
  }

  /**
   * Change the pass phrase to be used for decoding and encoding sensitive data. If the file currently
   * uses a pass phrase but it has not yet been supplied using {@link #setReversibleEncryptionPassPhrase(BPassword)},
   * an exception will be thrown.
   *
   * @since Niagara 4.0
   */
  public void updateReversibleEncryptionPassPhrase(BPassword newValue)
    throws Exception
  {
    Objects.requireNonNull(newValue);

    initFromHeader();

    if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring))
    {
      throw new IllegalArgumentException("Bog file does not use a pass phrase");
    }

    if (this.bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.external) && !this.reversibleEncryptionPassPhrase.isPresent())
    {
      throw new IllegalStateException("Original pass phrase has not been provided");
    }

    open();

    try(SecretChars valueChars = newValue.getSecretChars())
    {
      bogPasswordObjectEncoder = BogPasswordObjectEncoder.makeExternal(valueChars);
      try(ISecretBytesSupplier key = bogPasswordObjectEncoder.passPhraseToKey(valueChars))
      {
        passPhraseValidator = BIPasswordValidator.fromPBEValidator(bogPasswordObjectEncoder.getPbeEncodingInfo());
        getBogSpace().setBogPasswordObjectEncoder(bogPasswordObjectEncoder);
        getBogSpace().modified = true;

        getBogSpace().setReversibleEncryptionPassPhrase(newValue);

        PasswordPropertyUpdate passwordPropertyUpdate = password ->
          {
            if (password.getPasswordEncoder() instanceof BReversiblePasswordEncoder &&
                 ((BReversiblePasswordEncoder)password.getPasswordEncoder()).usesExternalEncryptionKey())
            {
              BReversiblePasswordEncoder oldEncoder = (BReversiblePasswordEncoder)password.getPasswordEncoder();
              BReversiblePasswordEncoder newEncoder = (BReversiblePasswordEncoder)oldEncoder.getType().getInstance();
              AccessController.doPrivileged((PrivilegedAction<Void>)() ->
                {
                  newEncoder.setExternalEncryptionKey(key.get());
                  return null;
                });
              try
              {
                newEncoder.encode(AccessController.doPrivileged((PrivilegedExceptionAction<SecretChars>)oldEncoder::getSecretChars));
              }
              catch (Exception e)
              {
                throw new BajaRuntimeException(e);
              }
              return BPassword.make(newEncoder);
            }
            return password;
          };
        for (BComponent c : (Iterable<BComponent>)() -> getBogSpace().iterateAllComponents())
        {
          PasswordUtil.updatePasswords(c, c.getSlotPath(), passwordPropertyUpdate);
          Optional<RuntimeException> exception = passwordPropertyUpdate.exceptions().findFirst();
          if (exception.isPresent())
          {
            throw exception.get();
          }
        }
      }
    }
    this.reversibleEncryptionPassPhrase = Optional.of(newValue);
  }

  /**
   * Return true if the BOG file already uses the given pass phrase to encode
   * sensitive data w/reversible encryption, or if it doesn't currently use pass
   * phrase at all.
   *
   * @since Niagara 4.0
   */
  public boolean validateReversibleEncryptionPassPhrase(BPassword value)
    throws Exception
  {
    if (value == null || value.isNull())
    {
      return false;
    }
    initFromHeader();

    return !bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.external) ||
           passPhraseValidator.validate(AccessController.doPrivileged((PrivilegedAction<String>)value::getValue));

  }

  /**
   * Return true if the bog file can use a pass phrase when using a reversible
   * scheme to encode sensitive data, otherwise the file uses a key from a station
   * key ring.
   *
   * @since Niagara 4.0
   */
  public boolean canUseReversibleEncryptionPassPhrase() throws Exception
  {
    initFromHeader();
    return !bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring);
  }

  /**
   * Return true if the bog file encrypts passwords using a key from the staiton
   * key ring.
   *
   * @since Niagara 4.0
   */
  public boolean usesKeyRingEncryption() throws Exception
  {
    initFromHeader();
    return bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring);
  }

  /**
   * Return true if the bog file uses a pass phrase when using a reversible
   * scheme to encode sensitive data.
   *
   * @since Niagara 4.0
   */
  public boolean usesReversibleEncryptionPassPhrase() throws Exception
  {
    initFromHeader();
    return bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.external);
  }

  /**
   * Return true if a pass phrase for encoding sensitive data has been provided for this file.
   *
   * @since Niagara 4.0
   */
  public boolean isReversibleEncryptionPassPhraseAvailable()
  {
    return this.reversibleEncryptionPassPhrase.isPresent();
  }

  /**
   * Return the password based encryption (PBE) encoding information used by this file, if any
   *
   * @since Niagara 4.0
   */
  public PBEEncodingInfo getPBEEncodingInfo()
    throws Exception
  {
    initFromHeader();
    return bogPasswordObjectEncoder == null ? null : bogPasswordObjectEncoder.getPbeEncodingInfo();
  }

////////////////////////////////////////////////////////////////
// Version
////////////////////////////////////////////////////////////////

  /**
   * Returns the BOG schema version for this file
   *
   * @since Niagara 4.0
   */
  public Version getBogVersion() throws Exception
  {
    initFromHeader();
    return bogVersion.get();
  }

////////////////////////////////////////////////////////////////
// BSubSpaceFile
////////////////////////////////////////////////////////////////

  /**
   * Convenience for {@code (BBogSpace)getSubSpace()}.
   */
  public BBogSpace getBogSpace()
  {
    return (BBogSpace)getSubSpace();
  }

  /**
   * Return if the associated space is modified.
   */
  @Override
  public boolean isModified()
  {
    BBogSpace bogSpace = getBogSpace();
    return bogSpace != null && bogSpace.isModified();
  }

  /**
   * Open the associated space.
   */
  @Override
  protected BSpace doOpen()
  {
    // read in objects
    BBogSpace space = new BBogSpace(this);
    if (reversibleEncryptionPassPhrase.isPresent())
    {
      space.setReversibleEncryptionPassPhrase(reversibleEncryptionPassPhrase.get());
    }
    try
    {
      space.checkLock();

      ValueDocDecoder decoder = getDecoder();
      BComponent root = (BComponent)decoder.decodeDocument();

      boolean shouldBeModified = processHeaderInformation(decoder, space);
      space.setRootComponent(root);

      BBogSpace.activateLinks(root);
      space.modified = shouldBeModified;  // ignore any mods during setup unless we have changed key source
    }
    catch(FileLockException e)
    {
      throw e;
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new CannotLoadBogException(getAbsoluteOrd().toString(), e);
    }
    return space;
  }

  /**
   * Save the associated space.  Subclasses that override this method should call super.doSave() or
   * {@link #updateHeaderVariablesFromSpace())
   */
  @Override
  protected void doSave() throws Exception
  {
    getBogSpace().save();
    // When the bog space saves, it regenerates its pass phrase encryption info, so we need to update
    // our copy of it
    updateHeaderVariablesFromSpace();
  }

  /**
   * Close the associated space.  Subclasses that override this method should call super.doClose().
   */
  @Override
  protected void doClose()
  {
    getBogSpace().close();
    bogVersion = Optional.empty();
    passPhraseValidator = BPassword.DEFAULT;
    bogPasswordObjectEncoder = null;
  }

////////////////////////////////////////////////////////////////
// IComponentFile
////////////////////////////////////////////////////////////////

  @Override
  public Mark readComponents() throws Exception
  {
    List<String> names = new ArrayList<>();
    List<BObject> values = new ArrayList<>();

    BComponent root = (BComponent)getDecoder().decodeDocument();
    for (BComponent kid : root.getChildComponents())
    {
      names.add(kid.getName());
      values.add(kid);
    }
    return new Mark(
      values.toArray(new BObject[values.size()]),
      names.toArray(new String[names.size()]));
  }

////////////////////////////////////////////////////////////////
// INavNode
////////////////////////////////////////////////////////////////

  @Override
  public BOrd getNavOrd()
  {
    return BOrd.make(getAbsoluteOrd().toString() + "|bog:|slot:/");
  }

////////////////////////////////////////////////////////////////
// Private
////////////////////////////////////////////////////////////////

  /**
   * If the header element has not been read from the file in a previous call to {@link #doOpen()}
   * or initFromHeader() read it now and initialize this object's state with the information
   * it contains.
   *
   * @since Niagara 4.0
   */
  private void initFromHeader() throws Exception
  {
    if (!bogVersion.isPresent())
    {
      try(InputStream in = getInputStream();
          InputStream bin = new BufferedInputStream(in))
      {
        BogDecoderPlugin plugin = new BogDecoderPlugin(bin);
        bogVersion = Optional.of(plugin.readHeader());
        bogPasswordObjectEncoder = plugin.getPasswordObjectEncoder();
        passPhraseValidator = plugin.getPassPhraseValidator();
      }
    }
  }

  /**
   * Returns an initialized ValueDocDecoder that can decode the file using this object's
   * reversible encryption pass phrase.
   *
   * @since Niagara 4.0
   */
  protected ValueDocDecoder getDecoder()
    throws Exception
  {
    BogDecoderPlugin plugin = new BogDecoderPlugin(this);
    plugin.setPassPhrase(reversibleEncryptionPassPhrase);
    return new ValueDocDecoder(plugin);
  }
;
  /**
   * Update the state of this object and the given space with password encoding-related
   * information in the given decoder object.   This method should be invoked only
   * after {@link javax.baja.io.ValueDocDecoder#decodeDocument()} has been called.
   *
   * @since Niagara 4.0
   */
  @SuppressWarnings("UnnecessaryFullyQualifiedName")
  protected boolean processHeaderInformation(ValueDocDecoder decoder, BBogSpace space)
    throws Exception
  {
    boolean shouldBeModified = false;
    bogVersion = Optional.of(decoder.getPlugin().getVersion());

    bogPasswordObjectEncoder = ((BogDecoderPlugin)decoder.getPlugin()).getPasswordObjectEncoder();
    if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.none) &&
      reversibleEncryptionPassPhrase.isPresent())
    {
      // file we read uses key source "none" but we're using a password - change the encoder to use "external" and mark the space modified
      try (SecretChars passPhrase = reversibleEncryptionPassPhrase.get().getSecretChars())
      {
        bogPasswordObjectEncoder = BogPasswordObjectEncoder.makeExternal(passPhrase);
      }
      passPhraseValidator = BIPasswordValidator.fromPBEValidator(bogPasswordObjectEncoder.getPbeEncodingInfo());
      space.setBogPasswordObjectEncoder(bogPasswordObjectEncoder);
      shouldBeModified = true;
    }
    else
    {
      passPhraseValidator = decoder.getPlugin().getPassPhraseValidator();
    }

    space.setBogPasswordObjectEncoder(bogPasswordObjectEncoder);
    space.setBogVersion(bogVersion.get());
    return shouldBeModified;
  }

  /**
   * Updates BOG header related variables from the BOG space.
   *
   * Subclasses should call this method after invoking {@link com.tridium.file.types.bog.BBogSpace#save()}
   * because the space may have changed some of the header variables.
   *
   * @since Niagara 4.0
   */
  protected void updateHeaderVariablesFromSpace() throws Exception
  {
    bogPasswordObjectEncoder = getBogSpace().getBogPasswordObjectEncoder();
    bogVersion = Optional.of(getBogSpace().getBogVersion());
  }

////////////////////////////////////////////////////////////////
// Object
////////////////////////////////////////////////////////////////

  /**
   * Get the icon.
   */
  @Override
  public BIcon getIcon()
  {
    return getStore() != null && isModified() ? dirtyIcon : icon;
  }
  private static final BIcon icon = BIcon.std("objectFile.png");
  private static final BIcon dirtyIcon = BIcon.std("objectFileDirty.png");

////////////////////////////////////////////////////////////////
// Spy
////////////////////////////////////////////////////////////////

  @Override
  public void spy(SpyWriter out)
    throws Exception
  {
    super.spy(out);
    BBogSpace bogSpace = getBogSpace();
    if (bogSpace != null)
    {
      out.w("<hr> <b>BogSpace</b>");
      bogSpace.spy(out);
    }
  }

////////////////////////////////////////////////////////////////
// Attribute
////////////////////////////////////////////////////////////////

  private Optional<BPassword> reversibleEncryptionPassPhrase = Optional.empty();
  private Optional<Version> bogVersion = Optional.empty();
  private BIPasswordValidator passPhraseValidator = BPassword.DEFAULT;
  private BogPasswordObjectEncoder bogPasswordObjectEncoder;
}
