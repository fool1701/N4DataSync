/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.file.types.bog;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Optional;
import java.util.logging.Level;

import javax.baja.file.BLocalFileStore;
import javax.baja.io.ValueDocEncoder;
import javax.baja.io.ValueDocEncoder.BogEncoderPlugin;
import javax.baja.naming.BHost;
import javax.baja.naming.BISession;
import javax.baja.naming.BOrd;
import javax.baja.nav.BINavNode;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPassword;
import javax.baja.security.MissingEncodingKeyException;
import javax.baja.security.PasswordEncodingContext;
import javax.baja.space.BComponentSpace;
import javax.baja.sync.Transaction;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BComponent;
import javax.baja.sys.BLink;
import javax.baja.sys.BRelation;
import javax.baja.sys.BValue;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Context;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.util.Version;

import com.tridium.nre.security.EncryptionKeySource;
import com.tridium.nre.security.SecretChars;
import com.tridium.nre.security.io.BogPasswordObjectEncoder;
import com.tridium.nre.util.FileLock;
import com.tridium.sys.engine.EngineUtil;

/**
 * BBogSpace is the component space for a BogFile.
 *
 * @author    Brian Frank
 * @creation  14 Apr 03
 * @version   $Revision: 17$ $Date: 7/8/11 7:32:57 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BBogSpace
  extends BComponentSpace
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.file.types.bog.BBogSpace(2979906276)1.0$ @*/
/* Generated Fri Jan 14 17:44:42 CST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBogSpace.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public BBogSpace(BBogFile bogFile)
  {
    super(bogFile.getFileName(), null, BOrd.make(bogFile.getOrdInSession(), "bog:"));
    this.bogFile = bogFile;
  }

////////////////////////////////////////////////////////////////
// Lifecycle
////////////////////////////////////////////////////////////////

  /**
   * Get the modified flag.
   */
  public boolean isModified()
  {
    return modified;
  }

  /**
   * Broker callback when components are modified within the space.
   */
  @Override
  public void modified(BComponent c, Context context)
  {
    this.modified = true;
  }

  /**
   * Callback when child is parented.
   */
  @Override
  public void childParented(BComponent c, Property property, BValue newChild, Context context)
  {
    // if auto start is disabled, then don't activate the links
    if (context != null)
    {
      BBoolean auto = (BBoolean)context.getFacet("niagaraAutoStart");
      if (auto != null && !auto.getBoolean())
      {
        return;
      }
    }

    if (newChild instanceof BComponent)
    {
      activateLinks((BComponent)newChild);
    }
    else if (newChild instanceof BLink)
    {
      EngineUtil.activate((BLink)newChild);
    }
    else if (newChild instanceof BRelation)
    {
      EngineUtil.activate((BRelation)newChild);
    }
  }

  /**
   * Callback when child is unparented.
   */
  @Override
  public void childUnparented(BComponent c, Property property, BValue oldChild, Context context)
  {
    if (oldChild instanceof BComponent)
    {
      deactivateLinks((BComponent)oldChild);
    }
    else if (oldChild instanceof BLink)
    {
      EngineUtil.deactivate((BLink)oldChild);
    }
  }

  /**
   * Use my own transaction to get needed callbacks.
   */
  @Override
  public Transaction newTransaction(Context cx)
  {
    try
    {
      return new BogSpaceTransaction(getEncodingContext(cx));
    }
    catch (IOException ioe)
    {
      throw new BajaRuntimeException(ioe);
    }
  }

  @Override
  public Context getEncodingContext(Context base)
  {
    return PasswordEncodingContext.updateContext(base, pContext ->
    {
      try
      {
        AccessController.doPrivileged((PrivilegedExceptionAction<Void>)()->
          {
            if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.external) ||
                reversibleEncryptionPassPhrase.isPresent())
            {
              if (reversibleEncryptionPassPhrase.isPresent())
              {
                try (SecretChars passPhrase = reversibleEncryptionPassPhrase.get().getSecretChars())
                {
                  pContext.setEncryptionAndDecryptionKey(EncryptionKeySource.external, Optional.of(bogPasswordObjectEncoder.passPhraseToKey(passPhrase)));
                }
              }
              else
              {
                pContext.setEncryptionAndDecryptionKey(EncryptionKeySource.external, Optional.empty());
              }
            }
            else if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring))
            {
              pContext.setEncryptionAndDecryptionKey(EncryptionKeySource.keyring, Optional.empty());
            }
            else if (bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.none))
            {
              pContext.setEncryptionAndDecryptionKey(EncryptionKeySource.none, Optional.empty());
            }
            return null;
          });
      }
      catch (PrivilegedActionException pae)
      {
        throw new BajaRuntimeException(pae.getCause());
      }
    });
  }

  /**
   * This method recursively activates all the links within
   * the bog.  What this does is make sure that a knob is
   * installed on all the link sources.  This lets us visualize
   * the links and handles the auto delete of the link
   * when the source is deleted while working offline.
   */
  public static void activateLinks(BComponent component)
  {
    EngineUtil.activateLinks(component);
  }

  /**
   * Converse of activateLinks().
   */
  public static void deactivateLinks(BComponent component)
  {
    EngineUtil.deactivateLinks(component);
  }

////////////////////////////////////////////////////////////////
// Space
////////////////////////////////////////////////////////////////

  @Override
  public boolean isSpaceReadonly()
  {
    return bogFile != null && bogFile.isReadonly();
  }

  @Override
  public boolean isMounted()
  {
    return bogFile != null;
  }

  @Override
  public BHost getHost()
  {
    if (bogFile == null)
    {
      return null;
    }
    return bogFile.getHost();
  }

  @Override
  public BISession getSession()
  {
    if (bogFile == null)
    {
      return null;
    }
    return bogFile.getSession();
  }

  @Override
  public BOrd getAbsoluteOrd()
  {
    if (bogFile == null)
    {
      return null;
    }
    return BOrd.make(bogFile.getAbsoluteOrd(), "bog:");
  }

  @Override
  public BOrd getOrdInHost()
  {
    if (bogFile == null)
    {
      return null;
    }
    return BOrd.make(bogFile.getOrdInHost(), "bog:");
  }

  @Override
  public BOrd getOrdInSession()
  {
    if (bogFile == null)
    {
      return null;
    }
    return BOrd.make(bogFile.getOrdInSession(), "bog:");
  }

////////////////////////////////////////////////////////////////
// BINavNode
////////////////////////////////////////////////////////////////

  @Override
  public BINavNode getNavParent()
  {
    return getBogFile();
  }

////////////////////////////////////////////////////////////////
// Reversible Encryption
////////////////////////////////////////////////////////////////

  public final void clearReversibleEncryptionPassPhrase()
  {
    reversibleEncryptionPassPhrase = Optional.empty();
  }

  public final void setReversibleEncryptionPassPhrase(BPassword value)
  {
    reversibleEncryptionPassPhrase = Optional.of(value);
  }

  public final void setBogPasswordObjectEncoder(BogPasswordObjectEncoder value)
  {
    bogPasswordObjectEncoder = value;
  }

  public BogPasswordObjectEncoder getBogPasswordObjectEncoder()
  {
    return bogPasswordObjectEncoder;
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  /**
   * @since Niagara 4.0
   */
  public Version getBogVersion()
  {
    return bogVersion;
  }

  /**
   * @since Niagara 4.0
   */
  public void setBogVersion(Version value)
  {
    bogVersion = value;
  }

  /**
   * Get the BogFile storage.
   */
  public BBogFile getBogFile()
  {
    return bogFile;
  }

  /**
   * Callback from BBogFile.save()
   */
  void save()
    throws Exception
  {
    checkLock();

    if (!this.reversibleEncryptionPassPhrase.isPresent() &&
        !this.bogPasswordObjectEncoder.getKeySource().equals(EncryptionKeySource.keyring))
    {
      // special case for no provided key and not using keyring
      BogEncoderPlugin plugin = new BogEncoderPlugin(new OutputStream() { @Override
      public void write(int b) throws IOException {}});
      plugin.setFailOnEncodingExceptions(false);
      ValueDocEncoder encoder = new ValueDocEncoder(plugin);
      plugin.setBogPasswordObjectEncoder(bogPasswordObjectEncoder);
      encoder.setZipped(true);
      encoder.encodeDocument(getRootComponent());
      encoder.close();
      Optional<Exception> keyException = encoder
        .getUnhandledEncodingExceptions()
        .filter(e -> e instanceof MissingEncodingKeyException)
        .findFirst();
      if (keyException.isPresent())
      {
        throw keyException.get();
      }
    }

    BogEncoderPlugin plugin = new BogEncoderPlugin(bogFile.getOutputStream());
    plugin.setPassPhrase(this.reversibleEncryptionPassPhrase);
    ValueDocEncoder encoder = new ValueDocEncoder(plugin);
    plugin.setBogPasswordObjectEncoder(bogPasswordObjectEncoder);
    encoder.setZipped(true);
    encoder.encodeDocument(getRootComponent());
    encoder.close();
    this.bogVersion = plugin.version();
    this.bogPasswordObjectEncoder = plugin.getBogPasswordObjectEncoder();
    modified = false;
  }

  /**
   * Callback from BBogFile.close()
   */
  void close()
  {
    // fire removed nav event...
    bogFile = null;
    setRootComponent(null);
  }

  /**
   * Check that a station process doesn't have
   * an exclusive lock on the bog.
   */
  public void checkLock()
    throws Exception
  {
    if (bogFile.getStore() instanceof BLocalFileStore)
    {
      BLocalFileStore store = (BLocalFileStore)bogFile.getStore();
      File localFile = store.getLocalFile();
      if (BBogFile.log.isLoggable(Level.FINE))
      {
        BBogFile.log.fine("Check lock " + localFile);
      }
      FileLock.check(localFile);
    }
  }

////////////////////////////////////////////////////////////////
// Transaction
////////////////////////////////////////////////////////////////

  /**
   * Custom transaction that traps needed callbacks and provides password encode/decode hints
   *
   * @since Niagara 4.0
   */
  private class BogSpaceTransaction
    extends Transaction
  {
    private BogSpaceTransaction(Context baseContext)
      throws IOException
    {
      super(BBogSpace.this, baseContext);
    }

    @Override
    protected void checkAutoStart(BValue value)
    {
      if (value instanceof BComponent)
      {
        activateLinks((BComponent)value);
      }
      else if (value instanceof BLink)
      {
        EngineUtil.activate((BLink)value);
      }
      else if (value instanceof BRelation)
      {
        EngineUtil.activate((BRelation)value);
      }
    }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  BBogFile bogFile;
  boolean modified;
  long openTimestamp;
  FileLock lock;
  private Optional<BPassword> reversibleEncryptionPassPhrase = Optional.empty();
  private BogPasswordObjectEncoder bogPasswordObjectEncoder = BogPasswordObjectEncoder.makeNone();
  private Version bogVersion;
}
