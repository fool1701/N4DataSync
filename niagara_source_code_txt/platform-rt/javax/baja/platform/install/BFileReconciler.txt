/*
 * Copyright 2007 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.platform.install;

import java.io.*;

import javax.baja.file.*;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * Base class for a processor that can reconcile the contents of two
 * versions of a file and return a single set of reconciled contents.
 * 
 * @author    Matt Boon
 * @creation  13 June 07
 * @version   $Revision: 3$ $Date: 10/3/07 11:36:11 AM EDT$
 * @since     Niagara 3.3
 */
@NiagaraType
public abstract class BFileReconciler
  extends BSingleton
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.platform.install.BFileReconciler(2979906276)1.0$ @*/
/* Generated Thu Aug 19 02:36:03 EDT 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFileReconciler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Reconcile
////////////////////////////////////////////////////////////////

  /**
   * Return the reconciled contents of the two given versions of a file, or
   * null if no reconciliation can/should occur.
   * 
   * @param newContents the new version of the file to be reconciled, will never
   * be null
   * @param existingContents the existing version of the file to be reconciled, will
   * never be null
   */
  public abstract ReconciliationResults reconcile(BIFile newContents, 
                                                  BIFile existingContents)
    throws Exception;

  /**
   * Return the reconciled contents of the two given versions of a file, or
   * null if no reconciliation can/should occur.
   * 
   * @param newContents the new version of the file to be reconciled
   * @param newContentCrc the CRC checksum for newContents, or -1L if unknown
   * @param existingContents the existing version of the file to be reconciled, or null 
   * if it doesn't currently exist
   * @param existingContentCrc the CRC checksum for existingContents, or -1L if unknown
   */
  public final ReconciliationResults reconcile(BIFile newContents,
                                               long   newContentCrc,
                                               BIFile existingContents,
                                               long   existingContentCrc)
    throws Exception
  {
    if ((newContents == null) ||
        ((newContentCrc == existingContentCrc) && (newContentCrc != -1L)))
    {
      // short circuit if there are identical contents (same CRC) or
      // there's no newContents to reconcile with
      return NO_RECONCILIATION;
    }
    if (existingContents == null)
    {
      return new FileReconciliationResults(newContents);
    }
    return new LazyReconciliationResults(newContents, existingContents);
  }

//==============================================================
// Inner classes follow
//==============================================================

  /**
   * Describes the result of a file reconciliation
   */
  public interface ReconciliationResults
  {
    /**
     * Stream of contents that should be used to replace the
     * existing contents.   An exception will be thrown if
     * shouldReplaceContents() returns false.
     */
    InputStream getInputStream()
      throws Exception;

    /**
     * Length of contents that should be used to replace the
     * existing contents.  An exception will be thrown if
     * shouldReplaceContents() returns false.
     */
    long getSize()
      throws Exception;

    /**
     * Return true if reconciled contents should be used
     * to replace existing contents, or if new contents should
     * be used to fill missing existing contents.
     */
    boolean shouldReplaceContents()
      throws Exception;
  }

  public static final ReconciliationResults NO_RECONCILIATION = 
    new ReconciliationResults()
    {
      public InputStream getInputStream() 
        throws Exception 
      {
        throw new IllegalStateException();
      }
      public long getSize() 
        throws Exception 
      {
        throw new IllegalStateException();
      }
      public boolean shouldReplaceContents() 
      {
        return false;
      }
    };

  /**
   * ReconciliationResults implementation that describes the
   * contents of a single file
   */
  public static class FileReconciliationResults
    implements ReconciliationResults
  {
    public FileReconciliationResults(BIFile file)
    {
      this.file = file;
    }
    public InputStream getInputStream() 
      throws Exception 
    {
      return file.getInputStream();
    }
    public long getSize() 
      throws Exception 
    {
      return file.getSize();
    }
    public boolean shouldReplaceContents()
    {
      return true;
    }
    private BIFile file;
  }  

  /**
   * ReconciliationResults implementation that defers processing until
   * the size or stream is requested
   */
  protected class LazyReconciliationResults
    implements ReconciliationResults
  {
    public LazyReconciliationResults(BIFile newContents,
                                     BIFile existingContents)
    {
      this.newContents = newContents;
      this.existingContents = existingContents;
    }
    public InputStream getInputStream()
      throws Exception
    {
      return results().getInputStream();
    }
    public long getSize()
      throws Exception
    {
      return results().getSize();
    }
    public boolean shouldReplaceContents()
      throws Exception
    {
      return results().shouldReplaceContents();
    }

    private ReconciliationResults results()
      throws Exception
    {
      if (results == null)
      {
        results = reconcile(newContents, existingContents);
      }
      return results;
    }
    private ReconciliationResults results = null;
    private BIFile newContents;
    private BIFile existingContents;
  }

}
