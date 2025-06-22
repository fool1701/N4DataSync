/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.file;

import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.space.Mark;
import javax.baja.sys.BComponent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;

import com.tridium.sys.transfer.TransferResult;
import com.tridium.sys.transfer.TransferStrategy;

/**
 * BIDeployable should be implemented by objects that can be deployed
 * to a BSpace in multiple copy operations.
 *
 * @author    Robert Adams
 * @creation  12 Dec 13
 * @version   $Revision: 4$ $Date: 6/11/07 12:41:23 PM EDT$
 * @since     Niagara 4.0
 */
@NiagaraType
public interface BIDeployable
  extends BIFile
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.file.BIDeployable(2979906276)1.0$ @*/
/* Generated Wed Dec 29 19:27:37 CST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  Type TYPE = Sys.loadType(BIDeployable.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Get array of steps to implement to deploy this file to the specified target.
   *
   * @param owner     parent widget for use in any dialogs to modify components in this
   *                  file before copying (may be null)
   * @param target    target component this file was to be copied to
   * @param name      name specified during copy initiation
   * @return          An array of steps which include mark and destination ord
   * @throws Exception
   */
  public Step[] getSteps(Object owner, BComponent target, String name) throws Exception;


  /**
   * Do any clean up after deployment. Should be called after deploy steps are executed.
   */
  public void postDeploy(TransferResult[] tres, TransferStrategy strategy, Context cx);

  /**
   * Get the preferred name of root component to use during deployment.
   * @return  preferred name
   */
  public String getDeployName();

  /**
   * Check to see if this file can be deployed to a given target
   * @param target   Target for deployment
   * @return         true if the deployment is possible, false if not
   */
  boolean isDeployable(BComponent target);

  public static class Step
  {
    public Step(BObject value, String name, BOrd destination)
    {
      mark = new Mark(value,name);
      this.destination = destination;
    }
    public Step(Mark mark, BOrd destination)
    {
      this.mark = mark;
      this.destination = destination;
    }

    public Mark mark;
    public BOrd destination;
  }
}
