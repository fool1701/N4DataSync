/*
 * Copyright 2001 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import java.awt.Font;
import java.util.Locale;

import javax.baja.gx.BFont;
import javax.baja.gx.BInsets;
import javax.baja.nre.annotations.AgentOn;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BObject;
import javax.baja.sys.BString;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BBorder;
import javax.baja.ui.BCheckBox;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BTextDropDown;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.ToggleCommand;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BConstrainedPane;
import javax.baja.ui.pane.BGridPane;
import javax.baja.util.Lexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.ui.theme.Theme;

/**
 * BFontFE.
 *
 * @author    Mike Jarmy
 * @creation  13 Dec 01
 * @version   $Revision: 14$ $Date: 3/23/05 11:51:43 AM EST$
 * @since     Baja 1.0
 */
@NiagaraType(
  agent = @AgentOn(
    types = "gx:Font"
  )
)
@NiagaraAction(
  name = "updateFont"
)
public class BFontFE
  extends BWbFieldEditor
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BFontFE(3454960617)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "updateFont"

  /**
   * Slot for the {@code updateFont} action.
   * @see #updateFont()
   */
  public static final Action updateFont = newAction(0, null);

  /**
   * Invoke the {@code updateFont} action.
   * @see #updateFont
   */
  public void updateFont() { invoke(updateFont, null, null); }

  //endregion Action "updateFont"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BFontFE.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////

  /**
   * BFontFE
   */
  public BFontFE()
  {
    sample.computePreferredSize();
    BConstrainedPane consSample = new BConstrainedPane(sample);
    consSample.setMinWidth((int) sample.getPreferredWidth());
    consSample.setMinHeight((int) sample.getPreferredHeight());
    consSample.setMaxWidth((int) sample.getPreferredWidth());
    consSample.setMaxHeight((int) sample.getPreferredHeight());

    BBorderPane border1 = new BBorderPane(
      consSample,
      BBorder.make(BBorder.INSET),
      BInsets.make(5));

    for (int i=0; i<fontNames.length; i++)
    {
      String menuEntry = fontNames[i];
      Font f = Font.decode(fontNames[i] + " plain");
      if (f != null)
      {
        String fontFamilyLocale = f.getFamily(Locale.forLanguageTag(Sys.getLanguage()));
        if (fontFamilyLocale != null && !(menuEntry.equals(fontFamilyLocale)))
        {
          menuEntry = fontFamilyLocale;
        }
      }
      name.getList().addItem(menuEntry);
    }

    for (int i=0; i<fontSizes.length; i++)
      size.getList().addItem(Integer.toString(fontSizes[i]));

    name.computePreferredSize();
    size.computePreferredSize();
    BConstrainedPane consName = new BConstrainedPane(name);
    BConstrainedPane consSize = new BConstrainedPane(size);
    consName.setMinWidth((int) name.getPreferredWidth());
    consSize.setMinWidth((int) size.getPreferredWidth());

    BGridPane box1 = new BGridPane(2);
    box1.add(null, consName);
    box1.add(null, consSize);

    BGridPane box2 = new BGridPane(4);
    box2.add(null, new BBorderPane(bold,0,5,0,5));
    box2.add(null, italic);
    box2.add(null, underline);
    box2.add(null, isNull);

    BGridPane box3 = new BGridPane(1);
    box3.add(null, box1);
    box3.add(null, box2);

    BGridPane box4 = new BGridPane(2);
    box4.add(null, box3);
    box4.add(null, border1);

    setContent(box4);

    addUpdateFontLinks();

    linkTo(name,      BListDropDown.actionPerformed, actionPerformed);
    linkTo(size,      BTextDropDown.actionPerformed, actionPerformed);

    linkTo(name,      BListDropDown.valueModified,   setModified);
    linkTo(size,      BTextDropDown.valueModified,   setModified);
    linkTo(bold,      BCheckBox.actionPerformed,     setModified);
    linkTo(italic,    BCheckBox.actionPerformed,     setModified);
    linkTo(underline, BCheckBox.actionPerformed,     setModified);
    linkTo(isNull,    BCheckBox.actionPerformed,     setModified);
  }

  /**
   * addUpdateFontLinks
   */
  private void addUpdateFontLinks()
  {
    linkTo("lnkNameA",     name,      BListDropDown.actionPerformed, updateFont);
    linkTo("lnkSizeA",     size,      BListDropDown.actionPerformed, updateFont);
    linkTo("lnkNameV",     name,      BListDropDown.valueModified,   updateFont);
    linkTo("lnkSizeV",     size,      BListDropDown.valueModified,   updateFont);
    linkTo("lnkBold",      bold,      BCheckBox.actionPerformed,     updateFont);
    linkTo("lnkItalic",    italic,    BCheckBox.actionPerformed,     updateFont);
    linkTo("lnkUnderline", underline, BCheckBox.actionPerformed,     updateFont);
    linkTo("lnkIsNull",    isNull,    BCheckBox.actionPerformed,     updateFont);
  }                                                                  

  /**
   * removeUpdateFontLinks
   */
  private void removeUpdateFontLinks()
  {
    remove("lnkNameA");
    remove("lnkSizeA");
    remove("lnkNameV");
    remove("lnkSizeV");
    remove("lnkBold");
    remove("lnkItalic");
    remove("lnkUnderline");
    remove("lnkIsNull");
  }

////////////////////////////////////////////////////////////////
// actions
////////////////////////////////////////////////////////////////

  /**
   * doUpdateFont
   */
  public void doUpdateFont()
  {
    sample.setFont(saveFont());
    sample.relayout();
  }

////////////////////////////////////////////////////////////////
// BWbEditor
////////////////////////////////////////////////////////////////

  /**
   * doSetReadonly
   */
  protected void doSetReadonly(boolean readonly)
  {
    name.setDropDownEnabled(!readonly);
    size.setDropDownEnabled(!readonly);
    size.getEditor().setEditable(!readonly);
    bold.setEnabled(!readonly);
    italic.setEnabled(!readonly);
    underline.setEnabled(!readonly);
  }  

  /**
   * doLoadValue
   */
  protected void doLoadValue(BObject value, Context cx)
    throws Exception
  {                       
    removeUpdateFontLinks();
    frozen = true;

    BFont font;
    if (value instanceof BString)
      font = (BFont)BFont.DEFAULT.decodeFromString(((BString)value).getString() );
    else
      font = (BFont)value;            
    sample.setFont(font);
    sample.relayout();

    if (font.equals(BFont.NULL))
    {
      isNull.setSelected(true);
      enable(false);
      nullFont();
    }
    else
    {
      isNull.setSelected(false);
      enable(true);
      font(font);
    }

    frozen = false;
    addUpdateFontLinks();
  }

  /**
   * doSaveValue
   */
  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {         
    BFont font = saveFont();

    if (value instanceof BString)
      return BString.make(font.encodeToString());
    else
      return font;
  }

  /**
   * saveFont
   */
  private BFont saveFont()
  {
    if (isNull.isSelected()) return BFont.NULL;

    String nm = (String) name.getSelectedItem();
    
    if (nm == null) nm = "null";
    Font f = Font.decode(nm + " plain");
    if (f != null)
    {
      String fontFamilyLocale = f.getFamily(Locale.ENGLISH);
      if (fontFamilyLocale != null && !(nm.equals(fontFamilyLocale)))
      {
        nm = fontFamilyLocale;        
      }
    }
    double sz = Double.parseDouble(size.getText());

    int style = 0;      
    if (bold.getSelected()) style |= BFont.BOLD;
    if (italic.getSelected()) style |= BFont.ITALIC;
    if (underline.getSelected()) style |= BFont.UNDERLINE;
    
    return BFont.make(nm, sz, style);
  }

////////////////////////////////////////////////////////////////
// private
////////////////////////////////////////////////////////////////

  /**
   * enable
   */
  private void enable(boolean flag)
  {
    name.setEnabled(flag);
    size.setEnabled(flag);
    bold.setEnabled(flag);
    italic.setEnabled(flag);
    underline.setEnabled(flag);
  }

  /**
   * font
   */
  private void font(BFont font)
  {
    String selectedFontName = font.getName();
    Font f = Font.decode(selectedFontName + " plain");
    if (f != null)
    {
      String fontFamilyLocale = f.getFamily(Locale.forLanguageTag(Sys.getLanguage()));
      if (fontFamilyLocale != null && !(selectedFontName.equals(fontFamilyLocale)))
      {
        selectedFontName = fontFamilyLocale;        
      }
    }
    name.setSelectedItem(selectedFontName);
    size.setText(Double.toString(font.getSize()));
    bold.setSelected(font.isBold());
    italic.setSelected(font.isItalic());
    underline.setSelected(font.isUnderline());
  }

  /**
   * nullFont
   */
  private void nullFont()
  {
    name.getList().getSelection().deselectAll();
    size.setText("");
    bold.setSelected(false);
    italic.setSelected(false);
    underline.setSelected(false);
  }

  /**
   * Null
   */
  private class Null extends ToggleCommand
  {
    private Null()
    {
      super(BFontFE.this, text("null"));
    }

    public CommandArtifact doInvoke()
    {
      if (frozen) return null;

      removeUpdateFontLinks();

      if (isSelected())
      {
        enable(false);
        nullFont();
      }
      else
      {
        enable(true);
        font(Theme.label().getTextFont());
      }

      doUpdateFont();
      addUpdateFontLinks();

      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Static
////////////////////////////////////////////////////////////////

  private static Lexicon lex = Lexicon.make("workbench");
  private static String text(String s) { return lex.getText("fontFE." + s); }

  private static String[] fontNames;
  static 
  { 
    fontNames = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(Locale.ENGLISH); 
  }
  private static int[] fontSizes = { 8,9,10,11,12,14,16,18,20,22,24,26,28,36,48,72 };

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  private BCheckBox isNull = new BCheckBox(new Null());

  private BListDropDown name = new BListDropDown();
  private BTextDropDown size = new BTextDropDown(4, true);  
  private BCheckBox bold = new BCheckBox(text("bold"));
  private BCheckBox italic = new BCheckBox(text("italic"));
  private BCheckBox underline = new BCheckBox(text("underline"));
  private BLabel sample = new BLabel("AaBbYyZz", BFont.make("SansSerif", 20));

  private boolean frozen = false;
}
