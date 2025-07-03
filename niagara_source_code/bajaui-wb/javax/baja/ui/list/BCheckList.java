/*                          
 * Copyright 2000, Tridium, Inc. All Rights Reserved.
 */

package javax.baja.ui.list;

import javax.baja.gx.*;
import javax.baja.nre.annotations.NiagaraProperty;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.ui.event.*;
import javax.baja.ui.util.*;

/**
 * BCheckList extends BList to use the selection state to check 
 * and uncheck.  BCheckList uses its own implementation of 
 * ListController and ListRenderer.
 *
 * @author    Brian Frank
 * @creation  30 Sept 02
 * @version   $Revision: 24$ $Date: 6/27/11 12:44:40 PM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
/*
 Icon to use when item is selected.
 */
@NiagaraProperty(
  name = "selectedIcon",
  type = "BImage",
  defaultValue = "DEFAULT_SELECTED_ICON"
)
/*
 Icon to use when item is selected.
 */
@NiagaraProperty(
  name = "unselectedIcon",
  type = "BImage",
  defaultValue = "BImage.NULL"
)
public class BCheckList
  extends BList
{

  static final BImage DEFAULT_SELECTED_ICON = BImage.make("module://icons/x16/check.png");
  
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.ui.list.BCheckList(4093266436)1.0$ @*/
/* Generated Thu Jun 02 14:30:01 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Property "selectedIcon"

  /**
   * Slot for the {@code selectedIcon} property.
   * Icon to use when item is selected.
   * @see #getSelectedIcon
   * @see #setSelectedIcon
   */
  public static final Property selectedIcon = newProperty(0, DEFAULT_SELECTED_ICON, null);

  /**
   * Get the {@code selectedIcon} property.
   * Icon to use when item is selected.
   * @see #selectedIcon
   */
  public BImage getSelectedIcon() { return (BImage)get(selectedIcon); }

  /**
   * Set the {@code selectedIcon} property.
   * Icon to use when item is selected.
   * @see #selectedIcon
   */
  public void setSelectedIcon(BImage v) { set(selectedIcon, v, null); }

  //endregion Property "selectedIcon"

  //region Property "unselectedIcon"

  /**
   * Slot for the {@code unselectedIcon} property.
   * Icon to use when item is selected.
   * @see #getUnselectedIcon
   * @see #setUnselectedIcon
   */
  public static final Property unselectedIcon = newProperty(0, BImage.NULL, null);

  /**
   * Get the {@code unselectedIcon} property.
   * Icon to use when item is selected.
   * @see #unselectedIcon
   */
  public BImage getUnselectedIcon() { return (BImage)get(unselectedIcon); }

  /**
   * Set the {@code unselectedIcon} property.
   * Icon to use when item is selected.
   * @see #unselectedIcon
   */
  public void setUnselectedIcon(BImage v) { set(unselectedIcon, v, null); }

  //endregion Property "unselectedIcon"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BCheckList.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constuctors
////////////////////////////////////////////////////////////////

  /**
   * Construct a
   */
  public BCheckList()
  {
    setController(new CheckListController());
    setRenderer(new CheckListRenderer());
  }

////////////////////////////////////////////////////////////////
// Controller
////////////////////////////////////////////////////////////////  

  public static class CheckListController
    extends ListController
  {
    public void focusGained(BFocusEvent event)
    {
    }
    
    protected void itemPressed(BMouseEvent event, int index)
    {
      if (event.isPopupTrigger()) itemPopup(event, index);
    }
    
    protected void itemReleased(BMouseEvent event, int index)
    {
      if (event.isPopupTrigger()) itemPopup(event, index);
      else
      {
        ListSelection sel = list.getSelection();
        if (sel.isSelected(index))
          sel.deselect(index);
        else
          sel.select(index);        
      }
    }

    protected void backgroundPressed(BMouseEvent event)
    {
      if (event.isPopupTrigger()) backgroundPopup(event); 
    }                 
        
    protected BMenu makePopup(ListSubject subject)
    {                                                
      BMenu menu = new BMenu();
      menu.add(null, new SelectAllCommand(getList()));
      menu.add(null, new DeselectAllCommand(getList()));
      return menu;
    }
  }                                      
  
  public static class SelectAllCommand extends Command
  {            
    SelectAllCommand(BList list) { super(list, UiLexicon.bajaui().module, "commands.selectAll"); }
    public CommandArtifact doInvoke()
    {             
      ((BList)getOwner()).getSelection().selectAll();
      return null;
    }
  }

  public static class DeselectAllCommand extends Command
  {            
    DeselectAllCommand(BList list) { super(list, UiLexicon.bajaui().module, "commands.deselectAll"); }
    public CommandArtifact doInvoke()
    {             
      ((BList)getOwner()).getSelection().deselectAll();
      return null;
    }
  }

////////////////////////////////////////////////////////////////
// Renderer
////////////////////////////////////////////////////////////////  

  public static class CheckListRenderer
    extends ListRenderer
  {
    public BBrush getSelectionForeground(Item item)
    {
      return getForeground(item);
    }
  
    public BBrush getSelectionBackground(Item item)
    {
      return getBackground(item);
    }
  
    public double getPreferredItemWidth(Item item)
    {
      BCheckList cl = (BCheckList)getList();
      double w = super.getPreferredItemWidth(item) +
        Math.max(cl.getSelectedIcon().getWidth(), 
        cl.getUnselectedIcon().getHeight()) + cl.hgap;
      return w;
    }
  
    public void paintItem(Graphics g, Item item)
    {
      paintItemBackground(g, item);

      BCheckList list = (BCheckList)getList();
      double inset = Math.max(list.getSelectedIcon().getWidth(),
        list.getUnselectedIcon().getWidth()) + list.hgap;
      
      g.translate(inset, 0);
      super.paintItem(g, item);
      g.translate(-inset, 0);
      
      BImage icon = item.selected ? list.getSelectedIcon() : list.getUnselectedIcon();
      if (!icon.isNull())
        g.drawImage(icon, 2, 0);
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes 
////////////////////////////////////////////////////////////////
  
  private double hgap = 4;
}
