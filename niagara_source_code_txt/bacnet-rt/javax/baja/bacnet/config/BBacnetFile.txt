/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.bacnet.config;

import java.io.*;
import java.util.logging.Level;

import javax.baja.bacnet.*;
import javax.baja.bacnet.datatypes.*;
import javax.baja.bacnet.enums.BBacnetFileAccessMethod;
import javax.baja.bacnet.enums.BBacnetObjectType;
import javax.baja.bacnet.enums.BBacnetPropertyIdentifier;
import javax.baja.bacnet.io.AsnException;
import javax.baja.bacnet.io.FileData;
import javax.baja.file.BIFile;
import javax.baja.file.BLocalFileStore;
import javax.baja.naming.BOrd;
import javax.baja.naming.NullOrdException;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.*;

import com.tridium.bacnet.asn.AsnInputStream;
import com.tridium.bacnet.asn.AsnUtil;
import com.tridium.bacnet.datatypes.BReadFileConfig;
import com.tridium.bacnet.datatypes.BWriteFileConfig;
import com.tridium.bacnet.stack.BBacnetStack;
import com.tridium.bacnet.stack.client.BBacnetClientLayer;

/**
 * @author Craig Gemmill
 * @version $Revision: 7$ $Date: 12/10/01 9:26:02 AM$
 * @creation 30 Jan 01
 * @since Niagara 3 Bacnet 1.0
 */
@NiagaraType
@NiagaraProperty(
  name = "objectId",
  type = "BBacnetObjectIdentifier",
  defaultValue = "BBacnetObjectIdentifier.make(BBacnetObjectType.FILE)",
  flags = Flags.SUMMARY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER)"),
  override = true
)
@NiagaraProperty(
  name = "objectType",
  type = "BEnum",
  defaultValue = "BDynamicEnum.make(BBacnetObjectType.FILE, BEnumRange.make(BBacnetObjectType.TYPE))",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED)"),
  override = true
)
@NiagaraProperty(
  name = "fileType",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.FILE_TYPE, ASN_CHARACTER_STRING)")
)
@NiagaraProperty(
  name = "fileSize",
  type = "BBacnetUnsigned",
  defaultValue = "BBacnetUnsigned.make(0)",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.FILE_SIZE, ASN_UNSIGNED)")
)
@NiagaraProperty(
  name = "modificationDate",
  type = "BBacnetDateTime",
  defaultValue = "new BBacnetDateTime()",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.MODIFICATION_DATE, ASN_CONSTRUCTED_DATA)")
)
/*
 has this file been archived?
 TRUE if no changes have been made since the last time
 the object was archived.
 */
@NiagaraProperty(
  name = "archive",
  type = "boolean",
  defaultValue = "false",
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.ARCHIVE, ASN_BOOLEAN)")
)
@NiagaraProperty(
  name = "readOnly",
  type = "boolean",
  defaultValue = "true",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.READ_ONLY, ASN_BOOLEAN)")
)
@NiagaraProperty(
  name = "fileAccessMethod",
  type = "BBacnetFileAccessMethod",
  defaultValue = "BBacnetFileAccessMethod.streamAccess",
  flags = Flags.READONLY,
  facets = @Facet("makeFacets(BBacnetPropertyIdentifier.FILE_ACCESS_METHOD, ASN_ENUMERATED)")
)
/*
 the ord to the local storage for this file's contents.
 */
@NiagaraProperty(
  name = "fileOrd",
  type = "BOrd",
  defaultValue = "BOrd.NULL",
  flags = Flags.DEFAULT_ON_CLONE,
  facets = @Facet(name = "BFacets.TARGET_TYPE", value = "\"baja:IFile\"")
)
/*
 Read file data and return the data as a BBlob.
 */
@NiagaraAction(
  name = "read",
  returnType = "BBlob"
)
/*
 Write file data given as a BBlob.
 */
@NiagaraAction(
  name = "write",
  parameterType = "BBlob",
  defaultValue = "BBlob.DEFAULT"
)
/*
 Read 'count' bytes or records from the file referenced by this
 File object from the Bacnet device, beginning at the record
 or byte designated by 'start'.
 Store it locally in the file referenced by filename.
 */
@NiagaraAction(
  name = "readFile",
  parameterType = "BStruct",
  defaultValue = "new BReadFileConfig()",
  flags = Flags.HIDDEN
)
/*
 Write to the file referenced by this File object.
 Use the file given by the argument as the source file.
 */
@NiagaraAction(
  name = "writeFile",
  parameterType = "BStruct",
  defaultValue = "new BWriteFileConfig()",
  flags = Flags.HIDDEN
)
public class BBacnetFile
  extends BBacnetObject
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.bacnet.config.BBacnetFile(1332449050)1.0$ @*/
/* Generated Thu Jun 02 14:30:00 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "objectId"

  /**
   * Slot for the {@code objectId} property.
   * @see #getObjectId
   * @see #setObjectId
   */
  public static final Property objectId = newProperty(Flags.SUMMARY, BBacnetObjectIdentifier.make(BBacnetObjectType.FILE), makeFacets(BBacnetPropertyIdentifier.OBJECT_IDENTIFIER, ASN_OBJECT_IDENTIFIER));

  //endregion Property "objectId"

  //region Property "objectType"

  /**
   * Slot for the {@code objectType} property.
   * @see #getObjectType
   * @see #setObjectType
   */
  public static final Property objectType = newProperty(Flags.READONLY, BDynamicEnum.make(BBacnetObjectType.FILE, BEnumRange.make(BBacnetObjectType.TYPE)), makeFacets(BBacnetPropertyIdentifier.OBJECT_TYPE, ASN_ENUMERATED));

  //endregion Property "objectType"

  //region Property "fileType"

  /**
   * Slot for the {@code fileType} property.
   * @see #getFileType
   * @see #setFileType
   */
  public static final Property fileType = newProperty(Flags.READONLY, "", makeFacets(BBacnetPropertyIdentifier.FILE_TYPE, ASN_CHARACTER_STRING));

  /**
   * Get the {@code fileType} property.
   * @see #fileType
   */
  public String getFileType() { return getString(fileType); }

  /**
   * Set the {@code fileType} property.
   * @see #fileType
   */
  public void setFileType(String v) { setString(fileType, v, null); }

  //endregion Property "fileType"

  //region Property "fileSize"

  /**
   * Slot for the {@code fileSize} property.
   * @see #getFileSize
   * @see #setFileSize
   */
  public static final Property fileSize = newProperty(0, BBacnetUnsigned.make(0), makeFacets(BBacnetPropertyIdentifier.FILE_SIZE, ASN_UNSIGNED));

  /**
   * Get the {@code fileSize} property.
   * @see #fileSize
   */
  public BBacnetUnsigned getFileSize() { return (BBacnetUnsigned)get(fileSize); }

  /**
   * Set the {@code fileSize} property.
   * @see #fileSize
   */
  public void setFileSize(BBacnetUnsigned v) { set(fileSize, v, null); }

  //endregion Property "fileSize"

  //region Property "modificationDate"

  /**
   * Slot for the {@code modificationDate} property.
   * @see #getModificationDate
   * @see #setModificationDate
   */
  public static final Property modificationDate = newProperty(Flags.READONLY, new BBacnetDateTime(), makeFacets(BBacnetPropertyIdentifier.MODIFICATION_DATE, ASN_CONSTRUCTED_DATA));

  /**
   * Get the {@code modificationDate} property.
   * @see #modificationDate
   */
  public BBacnetDateTime getModificationDate() { return (BBacnetDateTime)get(modificationDate); }

  /**
   * Set the {@code modificationDate} property.
   * @see #modificationDate
   */
  public void setModificationDate(BBacnetDateTime v) { set(modificationDate, v, null); }

  //endregion Property "modificationDate"

  //region Property "archive"

  /**
   * Slot for the {@code archive} property.
   * has this file been archived?
   * TRUE if no changes have been made since the last time
   * the object was archived.
   * @see #getArchive
   * @see #setArchive
   */
  public static final Property archive = newProperty(0, false, makeFacets(BBacnetPropertyIdentifier.ARCHIVE, ASN_BOOLEAN));

  /**
   * Get the {@code archive} property.
   * has this file been archived?
   * TRUE if no changes have been made since the last time
   * the object was archived.
   * @see #archive
   */
  public boolean getArchive() { return getBoolean(archive); }

  /**
   * Set the {@code archive} property.
   * has this file been archived?
   * TRUE if no changes have been made since the last time
   * the object was archived.
   * @see #archive
   */
  public void setArchive(boolean v) { setBoolean(archive, v, null); }

  //endregion Property "archive"

  //region Property "readOnly"

  /**
   * Slot for the {@code readOnly} property.
   * @see #getReadOnly
   * @see #setReadOnly
   */
  public static final Property readOnly = newProperty(Flags.READONLY, true, makeFacets(BBacnetPropertyIdentifier.READ_ONLY, ASN_BOOLEAN));

  /**
   * Get the {@code readOnly} property.
   * @see #readOnly
   */
  public boolean getReadOnly() { return getBoolean(readOnly); }

  /**
   * Set the {@code readOnly} property.
   * @see #readOnly
   */
  public void setReadOnly(boolean v) { setBoolean(readOnly, v, null); }

  //endregion Property "readOnly"

  //region Property "fileAccessMethod"

  /**
   * Slot for the {@code fileAccessMethod} property.
   * @see #getFileAccessMethod
   * @see #setFileAccessMethod
   */
  public static final Property fileAccessMethod = newProperty(Flags.READONLY, BBacnetFileAccessMethod.streamAccess, makeFacets(BBacnetPropertyIdentifier.FILE_ACCESS_METHOD, ASN_ENUMERATED));

  /**
   * Get the {@code fileAccessMethod} property.
   * @see #fileAccessMethod
   */
  public BBacnetFileAccessMethod getFileAccessMethod() { return (BBacnetFileAccessMethod)get(fileAccessMethod); }

  /**
   * Set the {@code fileAccessMethod} property.
   * @see #fileAccessMethod
   */
  public void setFileAccessMethod(BBacnetFileAccessMethod v) { set(fileAccessMethod, v, null); }

  //endregion Property "fileAccessMethod"

  //region Property "fileOrd"

  /**
   * Slot for the {@code fileOrd} property.
   * the ord to the local storage for this file's contents.
   * @see #getFileOrd
   * @see #setFileOrd
   */
  public static final Property fileOrd = newProperty(Flags.DEFAULT_ON_CLONE, BOrd.NULL, BFacets.make(BFacets.TARGET_TYPE, "baja:IFile"));

  /**
   * Get the {@code fileOrd} property.
   * the ord to the local storage for this file's contents.
   * @see #fileOrd
   */
  public BOrd getFileOrd() { return (BOrd)get(fileOrd); }

  /**
   * Set the {@code fileOrd} property.
   * the ord to the local storage for this file's contents.
   * @see #fileOrd
   */
  public void setFileOrd(BOrd v) { set(fileOrd, v, null); }

  //endregion Property "fileOrd"

  //region Action "read"

  /**
   * Slot for the {@code read} action.
   * Read file data and return the data as a BBlob.
   * @see #read()
   */
  public static final Action read = newAction(0, null);

  /**
   * Invoke the {@code read} action.
   * Read file data and return the data as a BBlob.
   * @see #read
   */
  public BBlob read() { return (BBlob)invoke(read, null, null); }

  //endregion Action "read"

  //region Action "write"

  /**
   * Slot for the {@code write} action.
   * Write file data given as a BBlob.
   * @see #write(BBlob parameter)
   */
  public static final Action write = newAction(0, BBlob.DEFAULT, null);

  /**
   * Invoke the {@code write} action.
   * Write file data given as a BBlob.
   * @see #write
   */
  public void write(BBlob parameter) { invoke(write, parameter, null); }

  //endregion Action "write"

  //region Action "readFile"

  /**
   * Slot for the {@code readFile} action.
   * Read 'count' bytes or records from the file referenced by this
   * File object from the Bacnet device, beginning at the record
   * or byte designated by 'start'.
   * Store it locally in the file referenced by filename.
   * @see #readFile(BStruct parameter)
   */
  public static final Action readFile = newAction(Flags.HIDDEN, new BReadFileConfig(), null);

  /**
   * Invoke the {@code readFile} action.
   * Read 'count' bytes or records from the file referenced by this
   * File object from the Bacnet device, beginning at the record
   * or byte designated by 'start'.
   * Store it locally in the file referenced by filename.
   * @see #readFile
   */
  public void readFile(BStruct parameter) { invoke(readFile, parameter, null); }

  //endregion Action "readFile"

  //region Action "writeFile"

  /**
   * Slot for the {@code writeFile} action.
   * Write to the file referenced by this File object.
   * Use the file given by the argument as the source file.
   * @see #writeFile(BStruct parameter)
   */
  public static final Action writeFile = newAction(Flags.HIDDEN, new BWriteFileConfig(), null);

  /**
   * Invoke the {@code writeFile} action.
   * Write to the file referenced by this File object.
   * Use the file given by the argument as the source file.
   * @see #writeFile
   */
  public void writeFile(BStruct parameter) { invoke(writeFile, parameter, null); }

  //endregion Action "writeFile"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BBacnetFile.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BBacnetFile()
  {
  }


////////////////////////////////////////////////////////////////
//  BComponent
////////////////////////////////////////////////////////////////

  /**
   * Register with the Bacnet service when this component is started.
   */
  public void started()
    throws Exception
  {
    super.started();
    getFile();
  }

  /**
   * Stopped.
   */
  public void stopped()
  {
    file = null;
  }

  /**
   * Property Changed.
   */
  public void changed(Property p, Context cx)
  {
    super.changed(p, cx);
    if (!isRunning()) return;
    if (p.equals(fileOrd))
    {
      getFile();
    }
  }


////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////

  public String toString(Context context)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(getObjectId().toString(context)).append(" local: " + getFileOrd());
    return sb.toString();
  }


////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////

  public static byte[] readFile(BBacnetDevice device,
                                BBacnetObjectIdentifier objectId)
    throws BacnetException
  {
    int fileSize = AsnUtil.fromAsnUnsignedInt(
      client().readProperty(device.getAddress(),
        objectId,
        BBacnetPropertyIdentifier.FILE_SIZE));
    int fileAccessMethod = AsnUtil.fromAsnEnumerated(
      client().readProperty(device.getAddress(),
        objectId,
        BBacnetPropertyIdentifier.FILE_ACCESS_METHOD));

    // Stream access: just read the file & return it.
    if (fileAccessMethod == BBacnetFileAccessMethod.STREAM_ACCESS)
      return readFileDataStream(device, objectId, fileSize, 0, fileSize);

      // Record access: read the file records, then put them together into
      // one big byte array.  The byte array will have to be ASN-decoded to
      // distinguish the individual records.
    else
    {
      int recordCount = AsnUtil.fromAsnUnsignedInt(
        client().readProperty(device.getAddress(),
          objectId,
          BBacnetPropertyIdentifier.RECORD_COUNT));
      BBacnetOctetString[] recs = readFileDataRecord(device, objectId, fileSize, 0, recordCount);
      ByteArrayOutputStream os = new ByteArrayOutputStream(fileSize);
      for (int i = 0; i < recs.length; i++)
      {
        if (recs[i] != null)
          os.write(recs[i].getBytes(), 0, recs[i].length());
      }
      return os.toByteArray();
    }
  }

  public BBlob doRead()
  {
    // Read file data.
    try
    {
      byte[] fileData = readFile(device(), getObjectId());
      return BBlob.make(fileData);
    }
    catch (BacnetException e)
    {
      log.log(Level.SEVERE, "Unable to read file contents for " + getObjectId() + " : " + e, e);
      throw new BajaRuntimeException(e);
    }
  }

  public void doReadFile(BStruct arg)
  {
    BBacnetNetwork.bacnet().postAsync(new ReadFileReq((BReadFileConfig)arg, this));
  }

  public void doWriteFile(BStruct arg)
  {
    BBacnetNetwork.bacnet().postAsync(new WriteFileReq((BWriteFileConfig)arg, this));
  }

  public static void writeFile(BBacnetDevice device, BBacnetObjectIdentifier objectId, byte[] fileData)
    throws BacnetException
  {
    writeFileDataStream(device, objectId, 0, fileData);
  }

  public static void writeFile(BBacnetDevice device, BBacnetObjectIdentifier objectId, int count, BBacnetOctetString[] fileRecordData)
    throws BacnetException
  {
    writeFileDataRecord(device, objectId, 0, count, fileRecordData);
  }

  public void doWrite(BBlob arg)
  {
    byte[] fileData = arg.copyBytes();
    if (getFileAccessMethod() == BBacnetFileAccessMethod.streamAccess)
    {
      try
      {
        writeFileDataStream(device(), getObjectId(), 0, fileData);
      }
      catch (BacnetException e)
      {
        log.log(Level.SEVERE, "Unable to write file contents for " + getObjectId() + " : " + e, e);
        throw new BajaRuntimeException(e);
      }
    }
    else
    {
      // This should be in the form of an array of ASN-encoded BACnetOctetStrings.
      // We need to actually break into a BBacnetOctetString[] until we get a
      // way of dealing with the records as a single opaque group.
      Array<BBacnetOctetString> a = new Array<>(BBacnetOctetString.class);
      AsnInputStream asn = new AsnInputStream(fileData);
      try
      {
        while (asn.available() > 0)
        {
          a.add(asn.readBacnetOctetString());
        }
        BBacnetOctetString[] fileRecordData = a.trim();
        writeFileDataRecord(device(), getObjectId(), 0, fileRecordData.length, fileRecordData);
      }
      catch (AsnException e)
      {
        log.severe("File data is not in array of encoded BACnetOctetStrings");
        throw new BajaRuntimeException(e);
      }
      catch (BacnetException e)
      {
        log.log(Level.SEVERE, "Unable to write file record contents for " + getObjectId() + ": " + e, e);
        throw new BajaRuntimeException(e);
      }
    }
  }


////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  private BIFile getFile()
  {
    try
    {
      if (!fileOrd.isEquivalentToDefaultValue(getFileOrd()))
      {
        BObject o = getFileOrd().get(this);
        if (o instanceof BIFile)
          file = (BIFile)o;
        else
          file = null;
      }
    }
    catch (Exception e)
    {
      log.log(Level.WARNING, "Unable to resolve file ord for " + this + ": " + getFileOrd(), e);
      file = null;
    }
    return file;
  }

  private static BBacnetClientLayer client()
  {
    return ((BBacnetStack)BBacnetNetwork.bacnet().getBacnetComm()).getClient();
  }

  private static BBacnetOctetString[] readFileDataRecord(BBacnetDevice device,
                                                         BBacnetObjectIdentifier objectId,
                                                         int fileSize,
                                                         int fileStartRecord,
                                                         int requestedRecordCount)
    throws BacnetException
  {
    if (!device.isServiceSupported("atomicReadFile"))
      throw new UnsupportedOperationException(lex.getText("serviceNotSupported.atomicReadFile"));

    BBacnetOctetString[] data = new BBacnetOctetString[requestedRecordCount];

    // If we either know we need to get it in multiple requests, or the single
    // request failed, try the multiple request approach.
    FileData ack;
    for (int i = 0; i < requestedRecordCount; i++)
    {
      ack = client().atomicReadFile(device.getAddress(),
        objectId,
        FileData.RECORD_ACCESS,
        fileStartRecord + i,
        1);
      data[i] = ack.getFileRecordData()[0];
      if (ack.isEndOfFile()) break;
    }

    // Return the data.
    return data;
  }

  private static byte[] readFileDataStream(BBacnetDevice device,
                                           BBacnetObjectIdentifier objectId,
                                           int fileSize,
                                           int fileStartPosition,
                                           int requestedOctetCount)
    throws BacnetException
  {
    if (!device.isServiceSupported("atomicReadFile"))
      throw new UnsupportedOperationException(lex.getText("serviceNotSupported.atomicReadFile"));

    byte[] data = new byte[fileSize];
    if (fileSize < requestedOctetCount)
      requestedOctetCount = fileSize;

    // Determine device's data return capability.  First take minimum of
    // device's and our own max APDU.  Then subtract a safety factor because
    // some devices abort if you get close to (but not over) the limit.
    int maxReturnableFileSize = device.getMaxAPDULengthAccepted();
    int myMax = BBacnetNetwork.localDevice().getMaxAPDULengthAccepted();
    if (myMax < maxReturnableFileSize) maxReturnableFileSize = myMax;
    maxReturnableFileSize -= ACK_HEADER_SIZE;

    int start = fileStartPosition;
    int len = maxReturnableFileSize;
    int lastByte = start + requestedOctetCount;
    FileData ack;
    do
    {
      ack = client().atomicReadFile(device.getAddress(),
        objectId,
        FileData.STREAM_ACCESS,
        start,
        len);
      byte[] b = ack.getFileData();
      System.arraycopy(b, 0, data, start, b.length);
      start += len;

    } while (!ack.isEndOfFile() && (start < lastByte));

    // Return the data.
    return data;
  }

  private static void writeFileDataRecord(BBacnetDevice device,
                                          BBacnetObjectIdentifier objectId,
                                          int fileStartRecord,
                                          int recordCount,
                                          BBacnetOctetString[] fileRecordData)
    throws BacnetException
  {
    if (!device.isServiceSupported("atomicWriteFile"))
      throw new UnsupportedOperationException(lex.getText("serviceNotSupported.atomicWriteFile"));

    // Sanity check
    if (fileRecordData == null) throw new IllegalArgumentException("fileRecordData is null!");

    // Fix count to be written if it goes beyond the last record.
    int writeCount = recordCount;
    if (fileStartRecord + recordCount > fileRecordData.length)
      writeCount = fileRecordData.length - fileStartRecord;

    // Determine device's data accept capability.  First take minimum of
    // device's and our own max APDU.  Then subtract a safety factor because
    // some devices abort if you get close to (but not over) the limit.
    int maxApdu = device.getMaxAPDULengthAccepted();
    int myMax = BBacnetNetwork.localDevice().getMaxAPDULengthAccepted();
    if (myMax < maxApdu) maxApdu = myMax;
    maxApdu -= ACK_HEADER_SIZE;

    // If we either know we need to send it in multiple requests, or the single
    // request failed, try the multiple request approach.
    int recNdx = fileStartRecord;
    int start = fileStartRecord;
    do
    {
      int len = 0;
      Array<BBacnetOctetString> a = new Array<>(BBacnetOctetString.class);
      do
      {
        a.add(fileRecordData[recNdx]);
        len += fileRecordData[recNdx].length();
        recNdx++;
      } while ((recNdx < writeCount) && (len + fileRecordData[recNdx].length() < maxApdu));
      BBacnetOctetString[] recData = a.trim();
      client().atomicWriteFileRecord(device.getAddress(),
        objectId,
        start,
        recData.length,
        recData);
      start = recNdx;
    } while (recNdx < writeCount);
  }

  private static void writeFileDataStream(BBacnetDevice device,
                                          BBacnetObjectIdentifier objectId,
                                          int fileStartPosition,
                                          byte[] fileData)
    throws BacnetException
  {
    if (!device.isServiceSupported("atomicWriteFile"))
      throw new UnsupportedOperationException(lex.getText("serviceNotSupported.atomicWriteFile"));

    int writeLength = fileData.length;

    // Determine device's data return capability.  First take minimum of
    // device's and our own max APDU.  Then subtract a safety factor because
    // some devices abort if you get close to (but not over) the limit.
    int maxApdu = device.getMaxAPDULengthAccepted();
    int myMax = BBacnetNetwork.localDevice().getMaxAPDULengthAccepted();
    if (myMax < maxApdu) maxApdu = myMax;
    maxApdu -= ACK_HEADER_SIZE;

    // If we either know we need to send it in multiple requests, or the single
    // request failed, try the multiple request approach.
    int start = fileStartPosition;
    int len = maxApdu;
    do
    {
      int copylen = len;
      if (start + copylen > writeLength) copylen = writeLength - start;
      byte[] b = new byte[copylen];
      System.arraycopy(fileData, start, b, 0, copylen);
      client().atomicWriteFileStream(device.getAddress(),
        objectId,
        start,
        b);
      start += copylen;
    } while (start < writeLength);
  }


////////////////////////////////////////////////////////////////
// ReadFileReq
////////////////////////////////////////////////////////////////

  static class ReadFileReq
    implements Runnable
  {
    ReadFileReq(BReadFileConfig arg, BBacnetFile f)
    {
      parms = arg;
      bacnetFile = f;
    }

    public void run()
    {
      int start = parms.getStart();
      int count = parms.getCount();
      byte[] fileData = null;

      try
      {
        int fileSize = AsnUtil.fromAsnUnsignedInt(
          client().readProperty(bacnetFile.device().getAddress(),
            bacnetFile.getObjectId(),
            BBacnetPropertyIdentifier.FILE_SIZE));
        int fileAccessMethod = AsnUtil.fromAsnEnumerated(
          client().readProperty(bacnetFile.device().getAddress(),
            bacnetFile.getObjectId(),
            BBacnetPropertyIdentifier.FILE_ACCESS_METHOD));

        // Stream access: just read the file & return it.
        if (fileAccessMethod == BBacnetFileAccessMethod.STREAM_ACCESS)
        {
          fileData = readFileDataStream(bacnetFile.device(),
            bacnetFile.getObjectId(),
            fileSize,
            start,
            count);
        }

        // Record access: read the file records, then put them together into
        // one big byte array.  The byte array will have to be ASN-decoded to
        // distinguish the individual records.
        else
        {
          BBacnetOctetString[] recs = readFileDataRecord(bacnetFile.device(),
            bacnetFile.getObjectId(),
            fileSize,
            start,
            count);

          ByteArrayOutputStream os = new ByteArrayOutputStream(fileSize);
          for (int i = 0; i < recs.length; i++)
          {
            if (recs[i] != null)
              os.write(recs[i].getBytes(), 0, recs[i].length());
          }
          fileData = os.toByteArray();
        }
      }
      catch (BacnetException e)
      {
        log.log(Level.SEVERE, "Unable to read file contents for " + bacnetFile.getObjectId() + " : " + e, e);
        throw new BajaRuntimeException(e);
      }

      // Now write to our local file.
      if (fileData != null)
      {
        RandomAccessFile out = null;
        try
        {
          if (bacnetFile.file == null)
            throw new NullOrdException("No local target file specified for BACnet File " + bacnetFile);
          if (bacnetFile.file.isReadonly())
            throw new IllegalStateException("Unable to write to file " + bacnetFile.getFileOrd());
          File f = ((BLocalFileStore)bacnetFile.file.getStore()).getLocalFile();
          out = new RandomAccessFile(f, "rw");

          out.write(fileData);
        }
        catch (IOException e)
        {
          log.log(Level.SEVERE, "IOException writing to local file " + bacnetFile.file, e);
          throw new BajaRuntimeException(e);
        }
        finally
        {
          if (out != null) try
          {
            out.close();
          }
          catch (IOException e)
          {
          }
        }
      }
    }

    BReadFileConfig parms;
    BBacnetFile bacnetFile;
  }


////////////////////////////////////////////////////////////////
//ReadFileReq
////////////////////////////////////////////////////////////////

  class WriteFileReq
    implements Runnable
  {
    WriteFileReq(BWriteFileConfig arg, BBacnetFile f)
    {
      parms = arg;
      bacnetFile = f;
    }

    public void run()
    {
      int remoteStart = parms.getRemoteStart();
      int localStart = parms.getLocalStart();
      byte[] fileData = null;

      // First read the data from our local file.
      RandomAccessFile src = null;
      try
      {
        if (bacnetFile.file == null)
          throw new NullOrdException("No local source file specified for BACnet File " + bacnetFile);
        File f = ((BLocalFileStore)bacnetFile.file.getStore()).getLocalFile();
        long flen = f.length() - localStart;
        if (flen > Integer.MAX_VALUE)
          throw new BajaRuntimeException("Local file data length " + flen + " is too long to write to BACnet!");
        int len = (int)flen;
        fileData = new byte[len];
        src = new RandomAccessFile(f, "r");
        src.seek(localStart);

        src.read(fileData, 0, len);
      }
      catch (IOException e)
      {
        log.log(Level.SEVERE, "IOException reading from local file " + bacnetFile.file, e);
        throw new BajaRuntimeException(e);
      }
      finally
      {
        if (src != null) try
        {
          src.close();
        }
        catch (IOException e)
        {
        }
      }

      // Now write it to the remote file.
      try
      {
        int fileAccessMethod = AsnUtil.fromAsnEnumerated(
          client().readProperty(bacnetFile.device().getAddress(),
            bacnetFile.getObjectId(),
            BBacnetPropertyIdentifier.FILE_ACCESS_METHOD));

        // Stream access: just read the file & return it.
        if (fileAccessMethod == BBacnetFileAccessMethod.STREAM_ACCESS)
        {
          writeFileDataStream(bacnetFile.device(),
            bacnetFile.getObjectId(),
            remoteStart,
            fileData);
        }

        // Record access: read the file records, then put them together into
        // one big byte array.  The byte array will have to be ASN-decoded to
        // distinguish the individual records.
        else
        {
          Array<BBacnetOctetString> a = new Array<>(BBacnetOctetString.class);
          AsnInputStream asn = new AsnInputStream(fileData);
          try
          {
            while (asn.available() > 0)
            {
              a.add(asn.readBacnetOctetString());
            }
            BBacnetOctetString[] fileRecordData = a.trim();
            writeFileDataRecord(device(), getObjectId(), remoteStart, fileRecordData.length, fileRecordData);
          }
          catch (AsnException e)
          {
            log.severe("File data is not in array of encoded BACnetOctetStrings");
            throw new BajaRuntimeException(e);
          }
        }
      }
      catch (BacnetException e)
      {
        log.log(Level.SEVERE, "Unable to write file record contents for " + getObjectId() + ": " + e, e);
        throw new BajaRuntimeException(e);
      }

    }

    BWriteFileConfig parms;
    BBacnetFile bacnetFile;
  }


////////////////////////////////////////////////////////////////
//  Constants
////////////////////////////////////////////////////////////////

  private static final int ACK_HEADER_SIZE = 30;


////////////////////////////////////////////////////////////////
//  Attributes
////////////////////////////////////////////////////////////////

  private BIFile file;

}
