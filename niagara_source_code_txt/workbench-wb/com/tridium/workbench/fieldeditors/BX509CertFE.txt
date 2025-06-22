/*
 * Copyright 2019 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.baja.file.BDataFile;
import javax.baja.gx.BBrush;
import javax.baja.gx.BColor;
import javax.baja.gx.BImage;
import javax.baja.naming.BOrd;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.registry.TypeInfo;
import javax.baja.security.BX509Certificate;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BButton;
import javax.baja.ui.BLabel;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.Command;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.enums.BButtonStyle;
import javax.baja.ui.enums.BHalign;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.BFormat;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbShell;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.ord.BIOrdChooser;

import com.tridium.workbench.ord.BFileOrdChooser;

/**
 * BX509CertFE is a field editor that accepts PEM encoded certificates as login
 * credentials for Users
 *
 * @author Erik Test
 * @creation 11 SEP 18
 * @since Niagara 4.8
 */
@NiagaraType(
  agent = @AgentOn(
    types = "baja:X509Certificate"
  )
)
public class BX509CertFE extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BX509CertFE(827143931)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BX509CertFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  public BX509CertFE()
  {
    button = new BButton(new Browse());
    button.setButtonStyle(BButtonStyle.toolBar);
    button.setVisible(true);

    clearButton = new BButton(new Clear());
    clearButton.setButtonStyle(BButtonStyle.toolBar);

    warningLabel.setHalign(BHalign.left);
    warningLabel.setForeground(BBrush.makeSolid(BColor.red));
    BWidget[] widgets = {field, button, clearButton};
    BGridPane pane = new BGridPane(3, widgets);

    // placing the pane in the child widgets makes the field-button pairing
    // stronger so when childWidgets re-formats itself when the warning label
    // is updated, the spacing between the field and button is unaffected
    BWidget[] childWidgets =
      {subjectDnLabel, warningLabel, pane};
    BGridPane grid = new BGridPane(1, childWidgets);
    setContent(grid);
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////

  @Override
  public void started()
  {
    // This is a big hack - but we need the shell so we can
    // query our agents correctly - so loadValue again here
    BObject val = getCurrentValue();
    if (val != null)
    {
      lockModifiedState();
      doLoadValue(val, getCurrentContext());
      unlockModifiedState();
    }
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  @Override
  protected void doSetReadonly(boolean readonly)
  {
    button.setEnabled(!readonly);
    clearButton.setEnabled(!readonly &&
      (fileOrd != null && !fileOrd.isNull() ||
       fileOrd == null && !BX509Certificate.DEFAULT.equals(cachedCert)));
  }

  @Override
  protected void doLoadValue(BObject v, Context cx)
  {
    if (cx != null)
    {
      BObject warning = cx.getFacet("warningText");
      if (warning != null)
      {
        warningText = BFormat.format(warning.toString(), null, cx);
      }
    }

    // fileOrd is only non-null when the editor is modified (either by selecting
    // a certificate file or clearing it), so whenever it is non-null, we want
    // to proceed
    if (v == null && fileOrd == null)
    {
      clearButton.setEnabled(false);
      return;
    }

    cachedCert = (BX509Certificate) v;
    if (cachedCert.getX509Certificate() == null && fileOrd == null)
    {
      clearButton.setEnabled(false);
      return;
    }

    if(fileOrd != null)
    {
      updateCertSelection();
    }
    else
    {
      clearButton.setEnabled(!isReadonly());
      String subjectDn = cachedCert.getX509Certificate().getSubjectDN().toString();
      subjectDnLabel.setText(subjectDn);
    }

    shell = getWbShell();
  }

  @Override
  protected BObject doSaveValue(BObject v, Context cx)
  {
    return cachedCert;
  }

  @Override
  public void setModified()
  {
    updateCertSelection();
    super.setModified();
  }
  
  private void updateCertSelection()
  {
    if (fileOrd == null || fileOrd.isNull())
    {
      // This means the cert selection was cleared, so revert everything back
      // to defaults
      cachedCert = BX509Certificate.DEFAULT;
      field.setText("");
      subjectDnLabel.setText("");
      warningLabel.setText(warningText);
      warningLabel.setWordWrapEnabled(true, 300);
      clearButton.setEnabled(false);
      return;
    }

    clearButton.setEnabled(!isReadonly());
    BDataFile dataFile = (BDataFile) fileOrd.resolve().get();
    try
    {
      cachedCert = BX509Certificate
        .make(new String(dataFile.read()));
      String subjectDn = cachedCert.getX509Certificate().getSubjectDN().toString();
      subjectDnLabel.setText(subjectDn);
      // notify the user that changing the certificate will disallow a reconnect
      // after a certain period of time
      warningLabel.setText(warningText);
      warningLabel.setWordWrapEnabled(true, 300);
    }
    catch (Exception e)
    {
      subjectDnLabel.setText(
        String.format(lexicon.getText("clientCert.certFormat"),
          fileOrd.toString()));
    }
  }

////////////////////////////////////////////////////////////////
// Browse
////////////////////////////////////////////////////////////////

  protected class Browse extends Command
  {
    Browse()
    {
      super(BX509CertFE.this, "");
      info = BFileOrdChooser.TYPE.getTypeInfo();
    }

    @Override
    public CommandArtifact doInvoke()
    {
      BObject base = null;
      if (shell != null) { base = shell.getActiveOrdTarget().get(); }

      String location = CERT_HOME;
      if (field.getText() != null)
      {
        String trim = field.getText().trim();
        if (trim.isEmpty())
        {
          location = trim;
        }
      }

      BOrd temp = BOrd.make(location);
      BIOrdChooser chooser = (BIOrdChooser) info.getInstance();
      fileOrd = chooser
        .openChooser(BX509CertFE.this, base, temp, getCurrentContext());
      if (fileOrd != null)
      {
        field.setText(fileOrd.encodeToString());
        setModified();
      }
      return null;
    }

    @Override
    public BImage getIcon() { return browseIcon; }

    private final TypeInfo info;
  }

////////////////////////////////////////////////////////////////
// Clear
////////////////////////////////////////////////////////////////

  private class Clear extends Command
  {
    Clear()
    {
      super(BX509CertFE.this, "");
    }

    @Override
    public CommandArtifact doInvoke()
    {
      if (!BOrd.NULL.equals(fileOrd))
      {
        // Setting fileOrd to BOrd.NULL is the indication that the cert
        // selection was cleared. fileOrd can also be null, but that means the
        // field editor was freshly loaded and hasn't been modified yet.
        fileOrd = BOrd.NULL;
        setModified();
      }
      return null;
    }

    @Override
    public BImage getIcon() { return deleteIcon; }
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private static final BImage browseIcon = BImage.make("module://icons/x16/open.png");
  private static final BImage deleteIcon = BImage.make("module://icons/x16/delete.png");
  private static final String CERT_HOME =
    AccessController.doPrivileged((PrivilegedAction<String>) () ->
      System.getProperty("niagara.user.home") +
        System.getProperty("file.separator") + "certManagement");

  private BWbShell shell;
  private final BLabel subjectDnLabel = new BLabel();
  private final BLabel warningLabel = new BLabel();
  private final BTextField field = new BTextField("", 40, false);
  private final BButton button;
  private final BButton clearButton;
  private BX509Certificate cachedCert;
  private BOrd fileOrd;
  public static final Lexicon lexicon = Lexicon.make(BX509CertFE.class);
  private String warningText = "";
}
