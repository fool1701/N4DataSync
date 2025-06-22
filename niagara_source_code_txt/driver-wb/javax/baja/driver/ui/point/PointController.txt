/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.driver.ui.point;

import javax.baja.util.Lexicon;
import javax.baja.workbench.mgr.folder.FolderController;

/**
 * PointController is the MgrController to be used for BPointManagers.
 *
 * @author    Brian Frank
 * @creation  29 Jun 04
 * @version   $Revision: 3$ $Date: 9/12/04 1:43:26 PM EDT$
 * @since     Baja 1.0
 */
public class PointController
  extends FolderController
{
  
  /**
   * Constructor
   */
  public PointController(BPointManager manager)
  {
    super(manager);
  }

  protected IMgrCommand[] makeTagCommands()
  {
    return super.makeTagCommands();
  }
  
  public void updateCommands()
  {
    super.updateCommands();
  }




  final Lexicon lex = Lexicon.make(PointController.class);

} 


