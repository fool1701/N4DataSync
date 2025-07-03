/*
 * Copyright 2003 Tridium, Inc.  All rights reserved.
 *
 */

package com.tridium.flexSerial.ui;

import javax.baja.sys.*;
import javax.baja.ui.*;
import javax.baja.workbench.mgr.*;
import javax.baja.workbench.component.table.*;
import javax.baja.ui.event.*;

/**
 * @author Andy Saunders
 * @creation 14 Sept 2005
 * @version $Revision: $ $Date: $
 */
public class MessageManagerController
  extends MgrController
  //implements
{

  /////////////////////////////////////////////////////////////////
  // Constructors
  /////////////////////////////////////////////////////////////////

  public MessageManagerController(BMessageManager mgr) 
  {
    super(mgr);
  }

  /////////////////////////////////////////////////////////////////
  // Methods - Public and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

/*
  public void updateCommands()
  { 
    newCommand.setFlags( 0 );
    edit.setFlags( 0 );
  }
*/

/*
  public BMenu makePopup(BMgrTable table, ComponentTableSubject subject, BMenu menu)
  {                  
 
    // Keep only the 'Delete' menu item and 'Actions' sub menu items.
 
    BMenu newMenu = new BMenu();
    BMenuItem[] menuItems = menu.getMenuItems();
     
    Command deleteCommand = null;
    for( int m=0; m < menuItems.length; ++m )
    { 
      String menuItemText = menuItems[m].getText();

      if( menuItemText.equals("Delete") == true)
      {
            deleteCommand = menuItems[m].getCommand();
      }
          
  
      if( menuItemText.equals("Actions") == true)
      {
        if ( menuItems[m] instanceof BSubMenuItem )
        {
          newMenu = ((BSubMenuItem)menuItems[m]).getMenu();
        }
      }
    }
    
    if( deleteCommand != null )
    {
       newMenu.add("Delete", deleteCommand );
    }
    return newMenu;
              
  }
*/  

 /*
  public BMenu makePopup(BLearnTable table, Object[] selection, BMenu menu)
  {            
    return null;                              
  }
 */

/*
  public void cellDoubleClicked(BMgrTable table, BMouseEvent event, int row, int col)
  {                
  
  }  

 

  public void cellDoubleClicked(BLearnTable table, BMouseEvent event, int row, int col)
  {                                 

  }               

*/  

  
  
  
  
  
  /////////////////////////////////////////////////////////////////
  // Methods - Protected and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Default and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Methods - Private and in alphabetical order by method name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Inner Classes - in alphabetical order by class name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Constants - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Attributes - in alphabetical order by field name.
  /////////////////////////////////////////////////////////////////

  /////////////////////////////////////////////////////////////////
  // Initialization
  /////////////////////////////////////////////////////////////////


}//MessageManagerController
