/*
 * Copyright 2010 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.lonworks.datatypes;

import java.util.ArrayList;

import javax.baja.lonworks.londata.BLonDataUnion;
import javax.baja.lonworks.londata.BLonPrimitive;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.Array;
import javax.baja.sys.*;

import com.tridium.lonworks.util.LonStringUtil;

/**
 *  Contains a set of BStrings which describe the conditions for selecting between
 *  branches in a union.
 * <p>
 *
 * @author    Robert Adams
 * @creation  20 Jan 2010
 * @version   $Revision$ $Date$
 * @since     Niagara 3.0
 */
@NiagaraType
@NiagaraProperty(
  name = "conditionProp",
  type = "String",
  defaultValue = "",
  flags = Flags.READONLY
)
public class BUnionQualifiers
  extends BComponent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.lonworks.datatypes.BUnionQualifiers(3390380409)1.0$ @*/
/* Generated Thu Jun 02 14:30:03 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "conditionProp"

  /**
   * Slot for the {@code conditionProp} property.
   * @see #getConditionProp
   * @see #setConditionProp
   */
  public static final Property conditionProp = newProperty(Flags.READONLY, "", null);

  /**
   * Get the {@code conditionProp} property.
   * @see #conditionProp
   */
  public String getConditionProp() { return getString(conditionProp); }

  /**
   * Set the {@code conditionProp} property.
   * @see #conditionProp
   */
  public void setConditionProp(String v) { setString(conditionProp, v, null); }

  //endregion Property "conditionProp"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BUnionQualifiers.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public Property[] getActiveProperties(BObject cond)
  {
    initActiveHash();

    for(int i=0 ; i<uqa.length ; ++i)
    {
      if(isActive(uqa[i],cond )) return active[i];
    }

    // If nothing active return all
    return getParent().getPropertiesArray();
  }

  public boolean isSameBranch(BObject cond1, BObject cond2)
  {
    for(int i=0 ; i<uqa.length ; ++i)
    {
      boolean actv1 = isActive(uqa[i],cond1);
      boolean actv2 = isActive(uqa[i],cond2);
      // If only one condition applies - not in same brance
      if(actv1!=actv2) return false;
      // If both apply then in same branch
      if(actv1&&actv2) return true;
    }
    return false;
  }

  // Check if the conditionProp meets one of the conditions
  private boolean isActive(BUnionQualifier uq, BObject cond )
  {
  //  BLonDataUnion ld = (BLonDataUnion)getParent();
    BLonPrimitive lp = (BLonPrimitive)cond;

    // Get Conditional value
    int cval = (int)lp.getDataAsDouble();

    // Get valid values
    String valS = uq.getConditions();
    valS = valS.substring(valS.indexOf("=")+1);
    int[] valA = LonStringUtil.getIntArray(valS);
    for(int i=0; i<valA.length ; ++i)
    {
      if(valA[i]==cval) return true;
    }
    return false;
  }

  // Create array of active properties for each branch.
  private void initActiveHash()
  {
    synchronized(sync)
    {
      if(active!=null) return;

      BLonDataUnion ld = (BLonDataUnion)getParent();

      // Get array of unionQualifiers
      uqa = getChildren(BUnionQualifier.class);

      ArrayList<Array<Property>> aSet = new ArrayList<>(uqa.length);
      // Get complete list of properties - must include all as this is used by fieldEditor
      for(int i=0 ; i<uqa.length ; ++i)
      {
        aSet.add(i, new Array<>(ld.getPropertiesArray()));
      }

      // For each branch remove it's properties from other arrays.
      for(int i=0 ; i<uqa.length ; ++i)
      {
        BUnionQualifier uq = uqa[i];
        String[] props = LonStringUtil.getStringArray(uq.getBranchProps());
        for(int n=0 ; n<props.length ; ++n)
        {
          Property p = ld.getProperty(props[n]);
          for(int j=0 ; j<uqa.length ; ++j)
            if(j!=i) aSet.get(j).remove(p);
        }
      }

      active = new Property[uqa.length][];
      for(int i=0 ; i<uqa.length ; ++i)
      {
        active[i] = aSet.get(i).trim();
      }
    }
  }

  BUnionQualifier[] uqa = null;
  Property[][] active = null;
  Object sync = new Object();
}
