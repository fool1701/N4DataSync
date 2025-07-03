/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.report;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * BReport models a report.  BReportSource is responsible for generating
 * this object, and BReportRecipient accepts it as an input.  The format
 * of the report is open-ended, being defined by a byte array and a MIME
 * type.
 * <p>
 *
 * <table border='1' summary="The properties of a report">
 * <tr>
 * <td><b>Report Name</b></td>
 * <td>The human readable title of this report.</td>
 * </tr>
 * <tr>
 * <td><b>File Name</b></td>
 * <td>If the contents of this report were saved to a file, this should contain
 * the most applicable filename to use.</td>
 * </tr>
 * <tr>
 * <td><b>MIME Type</b></td>
 * <td>The MIME type for the content contained in the byte array.</td>
 * </tr>
 * <tr>
 * <td><b>Contents</b></td>
 * <td>A byte array containing the report data.</td>
 * </tr>
 * </table>
 *
 * @author    Andy Frank
 * @creation  16 Oct 06
 * @version   $Revision: 2$ $Date: 10/19/06 3:53:10 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType
public class BReport
  extends BStruct
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.report.BReport(2979906276)1.0$ @*/
/* Generated Fri Nov 19 16:00:09 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BReport.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/


  

////////////////////////////////////////////////////////////////
// Constructor.
////////////////////////////////////////////////////////////////
  
  /**
   * Default constructor.
   */
  public BReport() {}
  
  /**
   * Create a new Report.
   */
  public BReport(String reportName, String fileName, String mimeType, byte[] content)
  {
    this.reportName = reportName;
    this.fileName   = fileName;
    this.mimeType   = mimeType;    
    this.content    = content;
  }
   
////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Get the name of the report.
   */
  public String getReportName()
  {
    return reportName;
  }
  
  /**
   * Return the filename for this report, or null not applicable.
   */
  public String getFileName()
  {
    return fileName;
  }

  /**
   * Get the MIME type for this report.
   */
  public String getMimeType()
  {
    return mimeType;
  }
    
  /**
   * Return the content for this report.
   */
  public byte[] getContent()
  {
    return content;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private String reportName;
  private String fileName;
  private String mimeType;
  private byte[] content;

}
