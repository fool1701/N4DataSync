/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file.zip;

import java.util.HashMap;
import java.util.Map;

import javax.baja.file.BAbstractFileStore;
import javax.baja.file.BDirectory;
import javax.baja.file.BIFile;
import javax.baja.file.FilePath;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.SortUtil;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

/**
 * BZipFileDirectory is an in-memory file store of a 
 * directory within a zip file.
 * 
 *
 * @author    Brian Frank       
 * @creation  24 Jan 03
 * @version   $Revision: 4$ $Date: 3/28/05 9:22:57 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType
public class BZipFileDirectory
  extends BAbstractFileStore
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.zip.BZipFileDirectory(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BZipFileDirectory.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Construct.
   */
  public BZipFileDirectory(BZipSpace space, FilePath path)
  {
    super(space, path);
  }

////////////////////////////////////////////////////////////////
// List
////////////////////////////////////////////////////////////////

  BDirectory getOrMakeDir(String name)
  {
    BDirectory dir = (BDirectory)byName.get(name);
    if (dir == null)
    {
      BZipSpace space = (BZipSpace)getFileSpace();
      FilePath path = getFilePath().merge(name);
      BZipFileDirectory store = new BZipFileDirectory(space, path);
      dir = new BDirectory(store);
      byName.put(name, dir);
    }
    return dir;
  }
  
  BIFile get(String name)
  {
    return byName.get(name);
  }
  
  BIFile[] list()
  {
    if (byList == null)
    {
      byList = byName.values().toArray(new BIFile[byName.size()]);
      SortUtil.sort(byList);
    }
    return byList;
  }
  
  public void add(BIFile file)
  {
    byName.put(file.getFileName(), file);
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Return true.
   */
  @Override
  public boolean isDirectory()
  {
    return true;
  }
  
  /**
   * Return true.
   */
  @Override
  public boolean isReadonly()
  {
    return true;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  private Map<String, BIFile> byName = new HashMap<>();
  private BIFile[] byList = null;

}
