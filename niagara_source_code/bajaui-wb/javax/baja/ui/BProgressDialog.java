/*
 * Copyright 2002 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.ui;

import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import javax.baja.gx.BImage;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BajaRuntimeException;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.commands.ReflectCommand;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.event.BWindowEvent;
import javax.baja.ui.util.UiLexicon;

/**
 * BProgressDialog is used to display a dialog to the user
 * during long tasks.  It requires that the actual work
 * be done in a background thread.
 *
 * @author    Brian Frank 21 May 02
 * @version   $Revision: 16$ $Date: 6/6/11 8:56:47 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BProgressDialog
  extends BDialog
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.BProgressDialog(2979906276)1.0$ @*/
/* Generated Thu Nov 18 14:06:34 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BProgressDialog.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Factory
////////////////////////////////////////////////////////////////  

  /**
   * Display the progress dialog until either the 
   * worker finishes or the user cancels.
   */
  public static void open(BWidget owner, String title, Worker worker, BImage icon, int min, int max)
  {
    BProgressDialog dialog = new BProgressDialog(owner, title, worker, icon, min, max);
    dialog.setBoundsCenteredOnOwner();
    worker.dialog = dialog;
    worker.start();
    dialog.open();    
  }

  /**
   * Convenience for <code>open(owner, title, worker, icon, 0, 100)</code>
   */
  public static void open(BWidget owner, String title, Worker worker, BImage icon)
  {
    open(owner, title, worker, icon, 0, 100);
  }

  /**
   * Convenience for <code>open(owner, title, worker, null, 0, 100)</code>
   */
  public static void open(BWidget owner, String title, Worker worker)
  {
    open(owner, title, worker, BImage.NULL, 0, 100);
  }

  /**
   * Show a progress dialog and start and thread to run the supplied function. This method
   * will block until the thread has done its work.
   * <p>
   * An optional argument is returned as a result of the work. The return value may be
   * empty if the dialog was cancelled or the function returned a null value.
   * </p>
   *
   * @param owner The owner of the dialog.
   * @param title The title of the progress dialog.
   * @param work The function to run in the thread. It's return value will be returned by this method
   *             once the thread has completed its work.
   * @return The return argument of the function. The return value will be null if the dialog is cancelled
   * or the function returns null.
   */
  private static <T> Optional<T> open(BWidget owner, String title, Function<BProgressDialog, T> work)
  {
    CompletableFuture<T> future = new CompletableFuture<>();

    BProgressDialog.open(owner, title,
      new BProgressDialog.Worker() {
        @Override
        public void doRun() throws Exception
        {
          try
          {
            future.complete(work.apply(getDialog()));
          }
          catch(Exception e)
          {
            e.printStackTrace();
            future.completeExceptionally(e);
          }
        }

        @Override
        public void doCancel() throws Exception
        {
          future.cancel(/*mayInterruptIfRunning - no effect*/true);
        }
      });

    try
    {
      return Optional.ofNullable(future.get());
    }
    catch(CancellationException e)
    {
      return Optional.empty();
    }
    catch(ExecutionException | InterruptedException e)
    {
      throw new BajaRuntimeException(e);
    }
  }

  /**
   * Show an indeterminate progress dialog and start and thread to run the supplied function. This method
   * will block until the thread has done its work.
   * <p>
   * An optional argument is returned as a result of the work. The return value may be
   * empty if the dialog was cancelled or the function returned a null value.
   * </p>
   *
   * @see javax.baja.ui.BProgressDialog#open(BWidget, String, java.util.function.Function)
   *
   * @param owner The owner of the dialog.
   * @param title The title of the progress dialog.
   * @param work The function to run in the thread. It's return value will be returned by this method
   *             once the thread has completed its work.
   * @return The return argument of the function. The return value will be null if the dialog is cancelled
   * or the function returns null.
   */
  public static <T> Optional<T> openIndeterminate(BWidget owner, String title, Function<BProgressDialog, T> work)
  {
    return open(owner, title, dialog -> {
      dialog.getProgressBar().setIndeterminate(true);
      return work.apply(dialog);
    });
  }

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////  

  /**
   * Protected constructor.
   *
   * @param owner Owner widget
   * @param title Dialog title
   * @param worker Worker that updates the progress
   * @param iconImage Image icon for dialog
   * @param min Lower progress limit (typically 0)
   * @param max Upper progress limit (typically 100)
   */
  protected BProgressDialog(BWidget owner, String title, Worker worker, BImage iconImage, double min, double max)
  {
    super(owner, title, true);
    this.worker = worker;
    
    icon = new BLabel(iconImage);
    
    message.setHalign(BHalign.left);
    
    progress.setMin(min);
    progress.setMax(max);
    
    add("icon", icon);
    add("message", message);
    add("progress", progress);
    add("button", button);
  }               
  
  /**
   * Public no arg constructor.  Do not use directly.
   */
  public BProgressDialog()
  {
  }

////////////////////////////////////////////////////////////////
// Access
////////////////////////////////////////////////////////////////
  
  /**
   * Get the label used to display the message text.
   */
  public BLabel getMessageLabel()
  {
    return message;
  }

  /**
   * Get the progress bar.
   */
  public BProgressBar getProgressBar()
  {
    return progress;
  }

////////////////////////////////////////////////////////////////
// Layout
////////////////////////////////////////////////////////////////

  public void computePreferredSize()
  {
    icon.computePreferredSize();
    message.computePreferredSize();
    progress.computePreferredSize();
    button.computePreferredSize();
    
    double iw = icon.getPreferredWidth();
    double ih = icon.getPreferredHeight();
    double mh = message.getPreferredHeight();
    double pw = progress.getPreferredWidth();
    double ph = progress.getPreferredHeight();
    double bh = button.getPreferredHeight();
    if (ih < 45) ih = 45;
    if (mh < 45) mh = 18;
    
    pw = 5 + iw + 5 + pw + 5;
    ph = 5 + Math.max(ih, mh + 5 + ph) + 5 + bh + 5;
    
    setPreferredSize(pw, ph);
  }
  
  public void doLayout(BWidget[] kids)
  {
    double w = getWidth();
    double h = getHeight();
    
    computePreferredSize();
    double iw = icon.getPreferredWidth();
    double ih = icon.getPreferredHeight();
    double mh = message.getPreferredHeight();
    double ph = progress.getPreferredHeight();
    double bw = button.getPreferredWidth();
    double bh = button.getPreferredHeight();
    if (ih < 45) ih = 45;
    if (mh < 45) mh = 18;
    
    double ax = 5 + iw + 5;
    double ay = 5 + (ih - (mh+5+ph))/2;
    message.setBounds(ax, ay, w-ax-5, mh);
    progress.setBounds(ax, ay + mh + 5, w-ax-5, ph);
    
    icon.setBounds(5, 5, iw, ih);
    button.setBounds((w-bw)/2, h-bh-5, bw, bh);
  }

////////////////////////////////////////////////////////////////
// Actions
////////////////////////////////////////////////////////////////


  public void windowClosing(BWindowEvent event)
  {
    cancel();
  }
    
  public void cancel()
  {
    if (!worker.done) worker.cancel();
    close();
  }
  
  public void paint(Graphics g)
  {
    if (worker.done) close();
    super.paint(g);
  }

////////////////////////////////////////////////////////////////
// Worker
////////////////////////////////////////////////////////////////  

  /**
   * Worker is the thread which does the 
   * background work for a progress dialog.
   */
  public static abstract class Worker
    extends Thread
  {
  
    public Worker()
    {
      super("ProgressDialog.Worker");
    }
    
    /**
     * Get the dialog associated with this worker.
     */
    public BProgressDialog getDialog() { return dialog; }

    /**
     * Subclasses should override <code>doRun()</code>.
     */
    public final void run()
    {
      try
      {
        doRun();
      }
      catch(Throwable e)
      {
        e.printStackTrace();
        BDialog.error(dialog, "Worker failed", e);
      }
      done = true;
      dialog.repaint();
    }

    /**
     * Subclasses should override <code>doCancel()</code>.
     */
    public final void cancel()
    {
      try
      {
        doCancel();
      }
      catch(Throwable e)
      {
        e.printStackTrace();
      }
      done = true;
      dialog.repaint();
    }
    
    /**
     * This is the override for the run implementation.
     */
    public abstract void doRun()
      throws Exception;

    /**
     * This is the callback to kill the worker 
     * thread when the user presses cancel.
     */
    public abstract void doCancel()
      throws Exception;    
    
    /**
     * Update is used to refresh the current progress.
     */
    public void updateProgress(int progressValue, String message)
    {
      updateProgress(message);
      updateProgress(progressValue);
    }
    
    /**
     * Update just the message. 
     */
    public void updateProgress(String message)
    {
      dialog.message.setText(message);
      dialog.message.relayout();
    }

    /**
     * Update just the progress value. 
     */
    public void updateProgress(int progressValue)
    {
      dialog.progress.setValue(progressValue);
      dialog.repaint();
    }

    protected BProgressDialog dialog;
    boolean done = false;        
  }

////////////////////////////////////////////////////////////////
// Test Driver
////////////////////////////////////////////////////////////////
  
  /*
  static BImage xxx = BImage.make("module://bajaui/com/tridium/ui/images/wizard/wizardSearch.png");
  public static void main(String[] args)
  {
    Worker worker = new TestWorker();
    open(new BFrame(), "hello world", worker, xxx);
  }
  
  static class TestWorker extends Worker
  {
  
    public void doRun() throws Exception
    {
      System.out.println("---> doRun");
      for(int i=0; isAlive && i<100; ++i)
      {
        updateProgress(i, "Now at " + i + "%");
        Thread.sleep(100);
      }    
      System.out.println("<--- doRun");    
    }
    
    public void doCancel()
    {
      isAlive = false;
      System.out.println("doCancel");    
    }    
    
    boolean isAlive = true;   
  }    
  */
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  final UiLexicon lex = UiLexicon.bajaui();

  Worker worker;
  BLabel icon;
  BLabel message = new BLabel("");
  BProgressBar progress = new BProgressBar();
  BButton button = new BButton(new ReflectCommand(this, lex.getText("dialog.cancel"), "cancel"));

}
