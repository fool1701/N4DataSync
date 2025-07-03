/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.londata;

import javax.baja.lonworks.enums.BLonElementType;
import javax.baja.lonworks.enums.BLonFileStatusEnum;
import javax.baja.lonworks.io.LonInputStream;
import javax.baja.lonworks.io.LonOutputStream;
import javax.baja.nre.annotations.Facet;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BFacets;
import javax.baja.sys.Property;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;


/**
 *   This class file represents SNVT_file_status.
 * <p>
 *  
 * @author    Robert Adams
 * @creation  4 Sept 01
 * @version   $Revision: 3$ $Date: 9/28/01 10:20:44 AM$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "fileStatus",
  type = "BLonEnum",
  defaultValue = "BLonEnum.make(BLonFileStatusEnum.DEFAULT)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.e8, null)")
)
@NiagaraProperty(
  name = "numberOfFiles",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null)")
)
@NiagaraProperty(
  name = "selectedFile",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null)")
)
@NiagaraProperty(
  name = "fileInfo",
  type = "BLonString",
  defaultValue = "BLonString.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.st,16,null)")
)
@NiagaraProperty(
  name = "size",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.s32, null)")
)
@NiagaraProperty(
  name = "fileType",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null)")
)
@NiagaraProperty(
  name = "domainId",
  type = "BLonByteArray",
  defaultValue = "BLonByteArray.make(6)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.na,6,null)")
)
@NiagaraProperty(
  name = "domainLength",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 6, 1, null)")
)
@NiagaraProperty(
  name = "subnet",
  type = "BLonInteger",
  defaultValue = "BLonInteger.make(1)",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 255, 1, null)")
)
@NiagaraProperty(
  name = "node",
  type = "BLonInteger",
  defaultValue = "BLonInteger.DEFAULT",
  facets = @Facet("LonFacetsUtil.makeFacets(BLonElementType.u8, 1, 127, 1, null)")
)
public class BLonFileStatus
  extends BLonData
{  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.londata.BLonFileStatus(3890306625)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "fileStatus"

  /**
   * Slot for the {@code fileStatus} property.
   * @see #getFileStatus
   * @see #setFileStatus
   */
  public static final Property fileStatus = newProperty(0, BLonEnum.make(BLonFileStatusEnum.DEFAULT), LonFacetsUtil.makeFacets(BLonElementType.e8, null));

  /**
   * Get the {@code fileStatus} property.
   * @see #fileStatus
   */
  public BLonEnum getFileStatus() { return (BLonEnum)get(fileStatus); }

  /**
   * Set the {@code fileStatus} property.
   * @see #fileStatus
   */
  public void setFileStatus(BLonEnum v) { set(fileStatus, v, null); }

  //endregion Property "fileStatus"

  //region Property "numberOfFiles"

  /**
   * Slot for the {@code numberOfFiles} property.
   * @see #getNumberOfFiles
   * @see #setNumberOfFiles
   */
  public static final Property numberOfFiles = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null));

  /**
   * Get the {@code numberOfFiles} property.
   * @see #numberOfFiles
   */
  public BLonInteger getNumberOfFiles() { return (BLonInteger)get(numberOfFiles); }

  /**
   * Set the {@code numberOfFiles} property.
   * @see #numberOfFiles
   */
  public void setNumberOfFiles(BLonInteger v) { set(numberOfFiles, v, null); }

  //endregion Property "numberOfFiles"

  //region Property "selectedFile"

  /**
   * Slot for the {@code selectedFile} property.
   * @see #getSelectedFile
   * @see #setSelectedFile
   */
  public static final Property selectedFile = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null));

  /**
   * Get the {@code selectedFile} property.
   * @see #selectedFile
   */
  public BLonInteger getSelectedFile() { return (BLonInteger)get(selectedFile); }

  /**
   * Set the {@code selectedFile} property.
   * @see #selectedFile
   */
  public void setSelectedFile(BLonInteger v) { set(selectedFile, v, null); }

  //endregion Property "selectedFile"

  //region Property "fileInfo"

  /**
   * Slot for the {@code fileInfo} property.
   * @see #getFileInfo
   * @see #setFileInfo
   */
  public static final Property fileInfo = newProperty(0, BLonString.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.st,16,null));

  /**
   * Get the {@code fileInfo} property.
   * @see #fileInfo
   */
  public BLonString getFileInfo() { return (BLonString)get(fileInfo); }

  /**
   * Set the {@code fileInfo} property.
   * @see #fileInfo
   */
  public void setFileInfo(BLonString v) { set(fileInfo, v, null); }

  //endregion Property "fileInfo"

  //region Property "size"

  /**
   * Slot for the {@code size} property.
   * @see #getSize
   * @see #setSize
   */
  public static final Property size = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.s32, null));

  /**
   * Get the {@code size} property.
   * @see #size
   */
  public BLonInteger getSize() { return (BLonInteger)get(size); }

  /**
   * Set the {@code size} property.
   * @see #size
   */
  public void setSize(BLonInteger v) { set(size, v, null); }

  //endregion Property "size"

  //region Property "fileType"

  /**
   * Slot for the {@code fileType} property.
   * @see #getFileType
   * @see #setFileType
   */
  public static final Property fileType = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementQualifiers.UNSIGNED_LONG1, null));

  /**
   * Get the {@code fileType} property.
   * @see #fileType
   */
  public BLonInteger getFileType() { return (BLonInteger)get(fileType); }

  /**
   * Set the {@code fileType} property.
   * @see #fileType
   */
  public void setFileType(BLonInteger v) { set(fileType, v, null); }

  //endregion Property "fileType"

  //region Property "domainId"

  /**
   * Slot for the {@code domainId} property.
   * @see #getDomainId
   * @see #setDomainId
   */
  public static final Property domainId = newProperty(0, BLonByteArray.make(6), LonFacetsUtil.makeFacets(BLonElementType.na,6,null));

  /**
   * Get the {@code domainId} property.
   * @see #domainId
   */
  public BLonByteArray getDomainId() { return (BLonByteArray)get(domainId); }

  /**
   * Set the {@code domainId} property.
   * @see #domainId
   */
  public void setDomainId(BLonByteArray v) { set(domainId, v, null); }

  //endregion Property "domainId"

  //region Property "domainLength"

  /**
   * Slot for the {@code domainLength} property.
   * @see #getDomainLength
   * @see #setDomainLength
   */
  public static final Property domainLength = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 6, 1, null));

  /**
   * Get the {@code domainLength} property.
   * @see #domainLength
   */
  public BLonInteger getDomainLength() { return (BLonInteger)get(domainLength); }

  /**
   * Set the {@code domainLength} property.
   * @see #domainLength
   */
  public void setDomainLength(BLonInteger v) { set(domainLength, v, null); }

  //endregion Property "domainLength"

  //region Property "subnet"

  /**
   * Slot for the {@code subnet} property.
   * @see #getSubnet
   * @see #setSubnet
   */
  public static final Property subnet = newProperty(0, BLonInteger.make(1), LonFacetsUtil.makeFacets(BLonElementType.u8, 0, 255, 1, null));

  /**
   * Get the {@code subnet} property.
   * @see #subnet
   */
  public BLonInteger getSubnet() { return (BLonInteger)get(subnet); }

  /**
   * Set the {@code subnet} property.
   * @see #subnet
   */
  public void setSubnet(BLonInteger v) { set(subnet, v, null); }

  //endregion Property "subnet"

  //region Property "node"

  /**
   * Slot for the {@code node} property.
   * @see #getNode
   * @see #setNode
   */
  public static final Property node = newProperty(0, BLonInteger.DEFAULT, LonFacetsUtil.makeFacets(BLonElementType.u8, 1, 127, 1, null));

  /**
   * Get the {@code node} property.
   * @see #node
   */
  public BLonInteger getNode() { return (BLonInteger)get(node); }

  /**
   * Set the {@code node} property.
   * @see #node
   */
  public void setNode(BLonInteger v) { set(node, v, null); }

  //endregion Property "node"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BLonFileStatus.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   *  Converts data to network byte format
   **/
  
  public void toOutputStream(LonOutputStream out)
  {
    primitiveToOutputStream(fileStatus, out);
    primitiveToOutputStream(numberOfFiles, out);
    primitiveToOutputStream(selectedFile, out);
    
    int st = getFileStatus().getEnum().getOrdinal();
    if(st == BLonFileStatusEnum.FS_LOOKUP_OK )
    {
      primitiveToOutputStream(fileInfo, out);
      primitiveToOutputStream(size, out);
      primitiveToOutputStream(fileType, out);
    }
    else
    {
      primitiveToOutputStream(domainId, out);
      primitiveToOutputStream(domainLength, out);
      primitiveToOutputStream(subnet, out);
      primitiveToOutputStream(node, out);
    }
  }
  
  /**
   *  Translates from network bytes. 
   */
  public void fromInputStream(LonInputStream in)
  {
    primitiveFromInputStream(fileStatus, in);
    primitiveFromInputStream(numberOfFiles, in);
    primitiveFromInputStream(selectedFile, in);
    
    int st = getFileStatus().getEnum().getOrdinal();
    if(st == BLonFileStatusEnum.FS_LOOKUP_OK )
    {
      primitiveFromInputStream(fileInfo, in);
      primitiveFromInputStream(size, in);
      primitiveFromInputStream(fileType, in);
    }
    else
    {
      primitiveFromInputStream(domainId, in);
      primitiveFromInputStream(domainLength, in);
      primitiveFromInputStream(subnet, in);
      primitiveFromInputStream(node, in);
    }
    
  }

}
