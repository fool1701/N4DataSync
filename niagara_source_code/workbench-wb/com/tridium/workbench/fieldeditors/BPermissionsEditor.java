/*
 * Copyright 2004 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.workbench.fieldeditors;

import javax.baja.gx.BColor;
import javax.baja.gx.BFont;
import javax.baja.gx.Graphics;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.security.BPermissions;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.ui.BLabel;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.util.UiLexicon;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;

import com.tridium.ui.theme.Theme;

/**
 * BPermissionsEditor.
 *
 * @author    Andy Frank       
 * @creation  20 Jul 04
 * @version   $Revision: 3$ $Date: 6/1/05 11:43:38 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
public class BPermissionsEditor
  extends BWbFieldEditor
{ 
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.workbench.fieldeditors.BPermissionsEditor(2979906276)1.0$ @*/
/* Generated Mon Nov 22 12:06:46 EST 2021 by Slot-o-Matic (c) Tridium, Inc. 2012-2021 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BPermissionsEditor.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  public BPermissionsEditor()
  {
    add(null, operator = make(lexOperator));
    add(null, admin   = make(lexAdmin));
    add(null, oRead   = make(lexPermissions[0]));
    add(null, oWrite  = make(lexPermissions[1]));
    add(null, oInvoke = make(lexPermissions[2]));    
    add(null, aRead   = make(lexPermissions[3]));
    add(null, aWrite  = make(lexPermissions[4]));
    add(null, aInvoke = make(lexPermissions[5]));
  }
  
  private BLabel make(String text)
  {
    return new BLabel(text, font);
  }
  
////////////////////////////////////////////////////////////////
// WbEditor 
////////////////////////////////////////////////////////////////

  protected void doLoadValue(BObject value, Context cx)
  {
    BPermissions p = (BPermissions)value;    
    
    mode[0] = p.hasOperatorRead();
    mode[1] = p.hasOperatorWrite();
    mode[2] = p.hasOperatorInvoke();
    mode[3] = p.hasAdminRead();
    mode[4] = p.hasAdminWrite();
    mode[5] = p.hasAdminInvoke();
  }

  protected BObject doSaveValue(BObject value, Context cx)
    throws Exception
  {
    String s = "";
    if (mode[0]) s += "r";
    if (mode[1]) s += "w";
    if (mode[2]) s += "i";
    if (mode[3]) s += "R";
    if (mode[4]) s += "W";
    if (mode[5]) s += "I";    
    return BPermissions.make(s);
  }

////////////////////////////////////////////////////////////////
// BWidget
////////////////////////////////////////////////////////////////
  
  public void computePreferredSize()
  {
    blockWidth  = 16;    
    blockHeight = 16;
    
    double tw = Math.max(font.width(lexOperator), font.width(lexAdmin)) + 20;
    blockWidth = Math.max(blockWidth, (int)(tw/3));
    
    for (int i=0; i<lexPermissions.length; i++)
      blockWidth = Math.max(blockWidth, (int)(font.width(lexPermissions[i])+10));
    
    setPreferredSize(blockWidth*6+4, blockHeight*3);
  }
  
  public void doLayout(BWidget[] kids)
  {
    double ox = 2;
    double oy = 2;
    double tw = blockWidth + 1;
    double th = 14;

    operator.setBounds(ox, oy, (blockWidth*3), th);
    admin.setBounds(ox+(blockWidth*3), oy, (blockWidth*3), th);
    oRead.setBounds(  ox,                oy+th, tw, th);
    oWrite.setBounds( ox+blockWidth,     oy+th, tw, th);
    oInvoke.setBounds(ox+(blockWidth*2), oy+th, tw, th);
    aRead.setBounds(  ox+(blockWidth*3), oy+th, tw, th);
    aWrite.setBounds( ox+(blockWidth*4), oy+th, tw, th);
    aInvoke.setBounds(ox+(blockWidth*5), oy+th, tw, th); 
  }
  
  public void paint(Graphics g)
  {
    double ww = getWidth()-1;
    double wh = getHeight()-1;
    double bh = 30;
    
    // Background
    g.setBrush(Theme.widget().getControlBackground());
    g.fillRect(0,0,ww,bh);
    
    if (isReadonly())
      g.setBrush(Theme.widget().getControlBackground());
    else
      g.setBrush(Theme.widget().getWindowBackground());
    g.fillRect(0,bh,ww,16);
    
    g.setBrush(BColor.black);
    g.strokeLine(0,bh,ww,bh);
    g.strokeLine(blockWidth*3+2, 4, blockWidth*3+2, bh-3);
    for (int i=1; i<6; i++)
      g.strokeLine(2+(i*blockWidth), bh, 2+(i*blockWidth), bh+blockHeight);
    
    // checks
    if (mode[W])
    {
      paintMark(g, W, selected);
      paintMark(g, R, implied);
      paintMark(g, w, implied);
      paintMark(g, r, implied);
    }
    else if (mode[R])
    {
      paintMark(g, R, selected);
      paintMark(g, r, implied);      
      if (mode[w]) paintMark(g, w, selected);
    }    
    else if (mode[w])
    {
      paintMark(g, w, selected);
      paintMark(g, r, implied);      
    }
    else if (mode[r])
      paintMark(g, r, selected);
      
    if (mode[I]) 
    {
      paintMark(g, I, selected);
      paintMark(g, i, implied);
    }
    else if (mode[i]) 
      paintMark(g, i, selected);    
        
    // Labels
    paintChildren(g);
    
    // Border
    g.setBrush(BColor.black);
    g.strokeRect(1,1,ww-2,wh-2);
    
    g.setBrush(Theme.widget().getControlHighlight());
    g.strokeLine(0,wh,ww,wh);
    g.strokeLine(ww,0,ww,wh);
    
    g.setBrush(Theme.widget().getControlShadow());
    g.strokeLine(0,0,ww,0);
    g.strokeLine(0,0,0,wh);    
  }

  private void paintMark(Graphics g, int index, BColor c)
  {
    double dx = (index * blockWidth) + ((blockWidth-6)/2);
    double dy = 30 + 5;
    
    g.setBrush(c);
    g.strokeLine(dx,   dy+4, dx+2, dy+6);
    g.strokeLine(dx+1, dy+4, dx+2, dy+5);
    g.strokeLine(dx+1, dy+3, dx+3, dy+5);
    g.strokeLine(dx+2, dy+4, dx+6, dy);
    g.strokeLine(dx+2, dy+5, dx+6, dy+1);
    g.strokeLine(dx+3, dy+5, dx+7, dy+1);
  }  

////////////////////////////////////////////////////////////////
// Mouse Events
////////////////////////////////////////////////////////////////

  public void mousePressed(BMouseEvent event)
  {
    if (isReadonly()) return;
    
    double mx = event.getX();
    double my = event.getY();
    
    if ((my >= 30 && my <= 46) && (mx >= 2 && my <= 98))
    { 
      int index = (int)((mx-4) / blockWidth);      
      
      if (index == W)
      {
        mode[W] = !mode[W];
        mode[R] = false;
        mode[w] = false;
        mode[r] = false;
        setModified();
      }
      else if (index == R && !mode[W])
      {
        mode[R] = !mode[R];
        mode[r] = false;
        setModified();
      }
      else if (index == w && !mode[W])
      {
        mode[w] = !mode[w];
        mode[r] = false;
        setModified();
      }
      else if (index == r && !mode[W] && !mode[R] && !mode[w])
      {
        mode[r] = !mode[r];
        setModified();
      }
      else if (index == I)
      {
        mode[I] = !mode[I];
        mode[i] = false;
        setModified();
      }
      else if (index == i && !mode[I])
      {
        mode[i] = !mode[i];
        setModified();
      }
            
      repaint();
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////

  static final String lexOperator = UiLexicon.bajaui().getText("security.operator");
  static final String lexAdmin = UiLexicon.bajaui().getText("security.admin");
  static final String[] lexPermissions = 
  {
    UiLexicon.bajaui().getText("security.r").trim(),
    UiLexicon.bajaui().getText("security.w").trim(),
    UiLexicon.bajaui().getText("security.i").trim(),
    UiLexicon.bajaui().getText("security.r").trim(),
    UiLexicon.bajaui().getText("security.w").trim(),
    UiLexicon.bajaui().getText("security.i").trim(),
  };
  
  static final int r = 0;
  static final int w = 1;
  static final int i = 2;
  static final int R = 3;
  static final int W = 4;
  static final int I = 5;
  
  static BFont font = Theme.widget().getBoldText();
  static BColor selected = BColor.make(0x008000);
  static BColor implied  = BColor.make(0x999999);
  
  int blockWidth  = 0;
  int blockHeight = 16;
    
  BLabel operator;
  BLabel admin;
  BLabel oRead;
  BLabel oWrite;
  BLabel oInvoke;
  BLabel aRead;
  BLabel aWrite;
  BLabel aInvoke;
  boolean[] mode = new boolean[6];
}
