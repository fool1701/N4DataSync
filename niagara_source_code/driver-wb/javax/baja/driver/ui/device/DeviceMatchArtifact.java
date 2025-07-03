/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.device;

import javax.baja.driver.BDevice;
import javax.baja.gx.BImage;
import javax.baja.sys.Context;
import javax.baja.sys.LocalizableException;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BConstrainedPane;
import javax.baja.ui.util.UiLexicon;
import javax.baja.workbench.mgr.MgrController;
import com.tridium.sys.transfer.TransferListener;
import com.tridium.sys.transfer.TransferResult;

/**
 * DeviceMatchArtifact provides the a CommandArtifact which
 * provides post template deployment device matching processing
 * provides a wrapper provfor javax.baja.transfer.TransferResult.
 *
 * @author    Andrew Saunders
 * @creation  5 May 15
 * @since     Baja 4.0
 */
public class DeviceMatchArtifact
  implements CommandArtifact
{

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * Constructor.
   */
  public DeviceMatchArtifact(BDeviceManager manager, Object[] discovered, BDevice[] deployed, Context cx)
  {
    this.manager = manager;
    this.discovered = discovered;
    this.deployed = deployed;
    this.context = cx;
  }

////////////////////////////////////////////////////////////////
// Public
////////////////////////////////////////////////////////////////  

  /**
   * Get the TransferResult returned from the actual
   * implementation of the strategy.
   */
  public TransferResult getResult()
  {
    return result;
  }

  /**
   * Convenience for <code>getResult().getInsertNames()</code>.
   */
  public String[] getInsertNames()
  {
    return getResult().getInsertNames();
  }

////////////////////////////////////////////////////////////////
// Do / Redo
////////////////////////////////////////////////////////////////  

  @Override
  public void undo() throws Exception
  {
    throw new UnsupportedOperationException();
  }

  public void redo() throws Exception
  {
    MatchStrategy strategy = new MatchStrategy();
    if (strategy == null)
      throw new LocalizableException("bajaui", "transfer.noStrategy");
    // do transfer on background thread
    new Worker().match(strategy);
  }      
  
////////////////////////////////////////////////////////////////
// Worker
////////////////////////////////////////////////////////////////
  
  /**
   * Worker is used to run the transfer operation on a background
   * thread and display a dialog showing the progress (if the 
   * TransferStrategy is making callbacks to it's listener).
   */
  class Worker 
    extends Thread
    implements TransferListener
  {           
    public void match(MatchStrategy strategy)
      throws Exception
    { 
      // setup strategy to notify me of updates
      this.strategy = strategy;
      strategy.setListener(this);

      // build status label
      this.status = new BLabel(clockIcon, lexPleaseWait);
      status.setHalign(BHalign.left);

      // build dialog status pane
      BConstrainedPane pane = new BConstrainedPane(status);
      pane.setMinHeight(20);
      pane.setMinWidth(450);
      this.dialog = new BDialog(manager, lexPleaseWait, true, new BBorderPane(pane));


      // start operation on a background thread
      start();     
      
      // wait just a bit, before we open a dialog so really
      // quick operations don't flash up on the screen
      synchronized(lock)
      {
        lock.wait(1000);
      }
            
      // open dialog if still not done
      if (!done)
      {
        dialog.setBoundsCenteredOnOwner();
        isDialogOpen = true;
        dialog.open();
      }
      
      // check for exception on background thread
      if (exception != null)
        throw exception;
    }

    public void updateStatus(String msg)
    {
      if (status == null) return;
//      System.out.println("updateStatus with: " + msg);
      status.setText(msg);
      status.relayout();
    }
    
    public void run() 
    {               
      // do transfer
      try 
      {               
        result = strategy.match();
      }
      catch(Exception e)
      {
        exception = e;
      }
      
      // mark myself done and notify main thread
      synchronized(lock)
      {
        done = true;
        lock.notifyAll();
      }
      
      // now close the dialog
      dialog.close(); 
    }          

    MatchStrategy strategy;
    Exception exception;  
    BDialog dialog; 
    BLabel status;  
    Object lock = new Object();
    boolean done;
    boolean isDialogOpen = false;
  }

  class MatchStrategy
  {
    MatchStrategy()
    {
    }

    TransferResult match()
      throws Exception
    {
      for(int i=0; i < deployed.length; ++i)
      {
        if(discovered[i] != null && deployed[i] != null)
        {
          updateStatus("Matching device: " + deployed[i].getName());
          try
          {
            manager.getController().doMatch(discovered[i], deployed[i], MgrController.quickContext);
            deployed[i].setEnabled(true);
          }
          catch(Exception e)
          {

          }
        }
      }
      return result;
    }

////////////////////////////////////////////////////////////////
// TransferListener
////////////////////////////////////////////////////////////////

    public TransferListener getListener()
    {
      return listener;
    }

    public void setListener(TransferListener listener)
    {
      this.listener = listener;
    }

    public void updateStatus(String msg)
    {
      try
      {
        if (listener != null)
          listener.updateStatus(msg);
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }

    TransferListener listener;
  }
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  static final BImage clockIcon = BImage.make("module://icons/x32/clock.png");
  final UiLexicon lex = UiLexicon.bajaui();
  final String lexPleaseWait = lex.getText("pleaseWait");

  BDeviceManager manager;          // the owner widget
  Object[] discovered;
  BDevice[] deployed;
  Context context;        // original context of op
  TransferResult result;  // result of last redo

}
