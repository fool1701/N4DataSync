/*
 * Copyright 2006 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.util;

import javax.baja.agent.BIAgent;
import javax.baja.gx.BImage;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.BBoolean;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BDialog;
import javax.baja.ui.BLabel;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.enums.BValign;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.ui.util.UiLexicon;
import javax.baja.util.BNotification;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbShell;

import com.tridium.ui.theme.Theme;

/**
 * BNotificationHandler responds to a BNotification fired
 * from a station. The handler executes on the client side
 * and has access to the current workbench shell.
 *
 * @author    Andy Frank
 * @creation  7 Dec 06
 * @version   $Revision: 7$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Niagara 3.2
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:Notification"
  )
)
public class BNotificationHandler
  extends BObject
  implements BIAgent
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.util.BNotificationHandler(3904011899)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BNotificationHandler.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/




////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Respond to a notification.
   */
  public void handle(BWbShell shell, BNotification notify, Context cx)
  {
    BImage icon = BImage.make("module://icons/x32/warning.png");
    BString t   = (BString)notify.get("title");
    BString m   = (BString)notify.get("message");
    BBoolean u  = (BBoolean)notify.get("undo");

    Lexicon lex = Lexicon.make("workbench", cx);
    String title = (t != null) ? t.getString() : lex.getText("notify.handler.notification");
    String msg   = (m != null) ? m.getString() : lex.getText("notify.handler.unknown");

    BGridPane grid1 = new BGridPane(1);
    grid1.add(null, new BLabel(title, Theme.widget().getBoldText()));
    grid1.add(null, new BLabel(msg, BHalign.left));

    BGridPane grid2 = new BGridPane(2);
    grid2.setColumnGap(10);
    grid2.setRowAlign(BValign.top);
    grid2.add(null, new BLabel(icon));
    grid2.add(null, grid1);

    OkCommand ok = new OkCommand(shell);
    UndoCommand undo = new UndoCommand(shell);
    IgnoreCommand ignore = new IgnoreCommand(shell);

    BGridPane grid3;
    if (u != null && u.getBoolean())
    {
      grid3 = new BGridPane(2);
      grid3.add(null, new BButton(undo));
      grid3.add(null, new BButton(ignore));
    }
    else
    {
      grid3 = new BGridPane(1);
      grid3.add(null, new BButton(ok));
    }

    BGridPane grid4 = new BGridPane(1);
    grid4.setRowGap(15);
    grid4.setColumnAlign(BHalign.center);
    grid4.add(null, grid2);
    grid4.add(null, grid3);

    BBorderPane pane = new BBorderPane(grid4);
    BDialog dlg = new BDialog(shell, title, true, pane);

    ok.dlg     = dlg;
    undo.dlg   = dlg;
    ignore.dlg = dlg;

    dlg.setBoundsCenteredOnOwner();
    dlg.open();
  }

////////////////////////////////////////////////////////////////
// Commands
////////////////////////////////////////////////////////////////

  static class OkCommand extends Command
  {
    public OkCommand(BWidget owner)
    {
      super(owner, UiLexicon.bajaui().getText("action.ok"));
    }
    public CommandArtifact doInvoke()
    {
      dlg.close();
      return null;
    }
    BDialog dlg;
  }

  static class UndoCommand extends Command
  {
    public UndoCommand(BWidget owner)
    {
      super(owner, UiLexicon.bajaui().getText("commands.undo.label"));
    }
    public CommandArtifact doInvoke()
    {
      dlg.close();
      BWbShell shell = (BWbShell)getOwner();
      shell.getUndoManager().getUndoCommand().invoke();
      return null;
    }
    BDialog dlg;
  }

  static class IgnoreCommand extends Command
  {
    public IgnoreCommand(BWidget owner)
    {
      super(owner, Lexicon.make("workbench").getText("notify.handler.ignore"));
    }
    public CommandArtifact doInvoke()
    {
      dlg.close();
      return null;
    }
    BDialog dlg;
  }

}
