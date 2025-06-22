/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sync;

import javax.baja.space.BComponentSpace;
import javax.baja.sys.BComponent;
import javax.baja.sys.BFacets;
import javax.baja.sys.BObject;
import javax.baja.sys.BasicContext;
import javax.baja.sys.Context;
import javax.baja.user.BUser;

/**
 * Transaction is a special Context type used to trap
 * BComponent modifications into a SyncBuffer.  There
 * are two ways to use Transactions:
 *
 * <pre>               
 *
 * If you have a space:
 *   Transaction tx = space.newTransaction(cx);
 *   comp.set(p1, v1, tx);
 *   comp.set(p2, v2, tx);
 *   tx.commit();                              
 *
 * If you have a component and don't know if it is mounted:
 *   Context tx = Transaction.start(comp, cx);
 *   comp.set(p1, v1, tx);
 *   comp.set(p2, v2, tx);
 *   Transaction.end(comp, tx);                              
 *
 * </pre>
 *
 * @author    Brian Frank       
 * @creation  13 Nov 01
 * @version   $Revision: 3$ $Date: 4/18/05 4:59:00 PM EDT$
 * @since     Baja 1.0
 */
public abstract class Transaction
  extends SyncBuffer
  implements Context
{                 

////////////////////////////////////////////////////////////////
// Helper Methods
////////////////////////////////////////////////////////////////
  
  /**
   * Begin a transaction.  If the component is mounted, then return 
   * {@code c.getComponentSpace().newTransaction(baseContext)}.
   * Otherwise return the baseContext.  See class documentation
   * for examples.
   */
  public static Context start(BComponent c, Context baseContext)
  {   
    BComponentSpace space = c.getComponentSpace();
    if (space != null)
      return space.newTransaction(baseContext);
    else
      return baseContext;
  }                      
  
  /**
   * Commit a transaction which was started via the {@code start()}
   * help method.  If the specified context is a Transaction then
   * commit it, otherwise assume
   */
 @SuppressWarnings("UnusedParameters")
 public static void end(BComponent c, Context context)
   throws Exception
 {                                          
   if (context instanceof Transaction)  
   {
     Transaction tx = (Transaction)context;
     tx.commit(tx.baseContext);
   }
 }  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Construct a Transaction for the specified space.
   * The client API to get a transaction is to call
   * {@code BComponentSpace.newTransaction()}.
   */
  protected Transaction(BComponentSpace space, Context baseContext)
  {
    this(space, baseContext, BFacets.NULL);
  }

  /**
   * Construct a Transaction for the specified space.
   * The client API to get a transaction is to call
   * {@code BComponentSpace.newTransaction()}.
   * @since Niagara 4.0
   */
  protected Transaction(BComponentSpace space, Context baseContext, BFacets additionalFacets)

  {
    super(space, false);

    // we don't support nested transactions
    if (baseContext instanceof Transaction)
      throw new IllegalStateException("Nested transactions not supported");

    if (baseContext == null)
      this.baseContext = new BasicContext();
    else
      this.baseContext = baseContext;

    this.facets = (additionalFacets == null) ? this.baseContext.getFacets() : BFacets.make(this.baseContext.getFacets(), additionalFacets);
  }


////////////////////////////////////////////////////////////////
// Context
////////////////////////////////////////////////////////////////

  @Override
  public Context getBase() { return baseContext; }
  @Override
  public BUser getUser() { return baseContext.getUser(); }
  @Override
  public BFacets getFacets() { return facets; }
  @Override
  public BObject getFacet(String name) { return facets.getFacet(name); }
  @Override
  public String getLanguage() { return baseContext.getLanguage(); }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private final Context baseContext;
  private final BFacets facets;
}
