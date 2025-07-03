/*
 * Copyright 2010 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.naming;

import javax.baja.nre.annotations.NiagaraSingleton;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;

/**
 * @author    Lee Adcock
 * @creation  Feb 2012
 * @version   $Revision: 1$ $Date: 10/7/2010 2:02:16 PM$
 * @since     Niagara 4.0
 */
@NiagaraType(
  ordScheme = "resolve"
)
@NiagaraSingleton
public class BResolveScheme
  extends BOrdScheme
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.naming.BResolveScheme(3180316234)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  public static final BResolveScheme INSTANCE = new BResolveScheme();

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BResolveScheme.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Constructor with scheme id.
   */
  private BResolveScheme()
  {
    super("resolve");
  }

////////////////////////////////////////////////////////////////
// BOrdScheme
////////////////////////////////////////////////////////////////
  
  /**
   * Return an instance of SlotPath.
   */
  @Override
  public OrdQuery parse(final String queryText)
  {
    return new OrdQuery()
    {

      @Override
      public String getScheme()
      {
        return "resolve";
      }

      @Override
      public String getBody()
      {
        return queryText;
      }

      @Override
      public boolean isHost()
      {
        return false;
      }

      @Override
      public boolean isSession()
      {
        return false;
      }

      @Override
      public void normalize(OrdQueryList list, int index)
      {
      }     
      
      public String toString()
      {
        return "Resolve";
      }
    };
  }
  
  @Override
  public OrdTarget resolve(OrdTarget base, OrdQuery query)
  {
    BObject obj = base.get();
    if(query.getBody().isEmpty() && obj instanceof BIAlias)
    {
      // If the base is an BIAlias, than it is easy to get the ord
      return ((BIAlias)obj).getOrd().resolve(((OrdTarget)base.getBase()).get());
    }
    /*
    if(obj instanceof BComplex)
    {
      // Try to get the ord from an 'ord' property slot
      Slot ordSlot = ((BComplex)obj).getProperty(query.getBody());
      if(ordSlot!=null && ordSlot.isProperty())
      {
        BValue value = ((BComplex)obj).get(ordSlot.asProperty());
        if(value instanceof BOrd)
          return ((BOrd)value).resolve(((OrdTarget)base.getBase()).get());
      }
    }

    try
    {
      // Try to use reflection to get the ord from a 'getOrd' property
      Method m = obj.getClass().getMethod("get"+TextUtil.capitalize(query.getBody()), new Class[0]);
      Object value = m.invoke(obj, new Object[0]);
      if(value instanceof BOrd)
        return ((BOrd)value).resolve(((OrdTarget)base.getBase()).get());
    } catch (NoSuchMethodException nsme) {
      // do nothing
    } catch (InvocationTargetException ite) {
      // do nothing
    } catch (IllegalAccessException iae) {
      // do nothing
    }
    */

    // If we didn't have any success finding the ord, then fail
    throw new InvalidOrdBaseException("Invalid base for resolve scheme.");        
  }
}
