/*
 * Copyright 2003 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import javax.baja.job.BJob;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComplex;
import javax.baja.sys.BComponent;
import javax.baja.sys.BComponentEvent;
import javax.baja.sys.BObject;
import javax.baja.sys.Context;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.TagDictionaryService;
import javax.baja.ui.BMenu;
import javax.baja.ui.BToolBar;
import javax.baja.ui.BWidget;
import javax.baja.ui.enums.BOrientation;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BSplitPane;
import javax.baja.ui.table.BTable;
import javax.baja.util.Lexicon;
import javax.baja.workbench.view.BIExportableTableView;
import javax.baja.workbench.view.BWbComponentView;

import com.tridium.workbench.util.WbViewEventWorker;

/**
 * BAbstractManager defines the basic class used to provide
 * views for managing driver devices, points, etc.  It provides
 * a consistent user experience for adding and editing objects
 * in batch, as well managing learns.
 *
 * @author    Brian Frank
 * @creation  15 Dec 03
 * @version   $Revision: 38$ $Date: 7/30/10 10:29:28 AM EDT$
 * @since     Baja 1.0
 */
@NiagaraType
@NiagaraAction(
  name = "handleLearnSelection"
)
@NiagaraAction(
  name = "handleTagSelection"
)
@NiagaraAction(
  name = "handleDbSelection"
)
public abstract class BAbstractManager
  extends BWbComponentView   
  implements BIExportableTableView
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.BAbstractManager(2244739843)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "handleLearnSelection"

  /**
   * Slot for the {@code handleLearnSelection} action.
   * @see #handleLearnSelection()
   */
  public static final Action handleLearnSelection = newAction(0, null);

  /**
   * Invoke the {@code handleLearnSelection} action.
   * @see #handleLearnSelection
   */
  public void handleLearnSelection() { invoke(handleLearnSelection, null, null); }

  //endregion Action "handleLearnSelection"

  //region Action "handleTagSelection"

  /**
   * Slot for the {@code handleTagSelection} action.
   * @see #handleTagSelection()
   */
  public static final Action handleTagSelection = newAction(0, null);

  /**
   * Invoke the {@code handleTagSelection} action.
   * @see #handleTagSelection
   */
  public void handleTagSelection() { invoke(handleTagSelection, null, null); }

  //endregion Action "handleTagSelection"

  //region Action "handleDbSelection"

  /**
   * Slot for the {@code handleDbSelection} action.
   * @see #handleDbSelection()
   */
  public static final Action handleDbSelection = newAction(0, null);

  /**
   * Invoke the {@code handleDbSelection} action.
   * @see #handleDbSelection
   */
  public void handleDbSelection() { invoke(handleDbSelection, null, null); }

  //endregion Action "handleDbSelection"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BAbstractManager.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

////////////////////////////////////////////////////////////////
// Constructor
////////////////////////////////////////////////////////////////
  
  /**
   * Constructor.
   */
  protected BAbstractManager()
  {
    // we do a subscribe in doLoadValue
    autoRegisterForComponentEvents = false;
  }
    
////////////////////////////////////////////////////////////////
// Init
////////////////////////////////////////////////////////////////
  
  /**
   * This method is used to initialize the manager and put everything 
   * together.  Until this method is run nothing has been initialized.
   * This method will automatically short circuit itself if the
   * manager has already been initialized.  This code is not run in 
   * the constructor, so that subclasses may easily initialize code
   * in their own constructor (which run after base class constructor).
   */
  protected void init()
  {                 
    // short circuit if already built
    if (initialized) return;
    initialized = true;
    
    // instantiate support instances
    model         = makeModel();
    learn         = makeLearn();
    tagDictionary = makeTagDictionary();
    template      = makeTemplate();
    controller    = makeController();   // last

    // initialize support instances
    model.init();
    if (learn != null) learn.init();
    if (tagDictionary != null) tagDictionary.init();
    if (template != null) template.init();
    controller.init();  // last
    
    // build support instance panes
    tablePane = model.makePane();   
    if (learn != null)
    { 
      learnPane = learn.makePane();
      learnPane.setVisible(controller.learnMode.isSelected());
    }
    if (tagDictionary != null)
    {
      tagDictionaryPane = tagDictionary.makePane();
      tagDictionaryPane.setVisible(controller.tagMode.isSelected());
    }
    if( template != null)
    {
      templatePane = template.makePane();
      templatePane.setVisible(controller.templateMode.isSelected());
    }
    
    
    BWidget actionBar = controller.makeActionBar();
    content = new BEdgePane();
    updateContent();
     if (actionBar != null && !actionBar.isNull())
      content.setBottom(new BBorderPane(actionBar, 5, 5, 0, 5));
    setContent(content);

    // hook up eventing for table selection
    if (getLearn() != null)
      linkTo(getLearn().getTable(), BTable.selectionModified, handleLearnSelection);
    if (getMgrTagDictionary() != null)
      linkTo(getMgrTagDictionary().getTable(), BTable.selectionModified, handleTagSelection);
    linkTo(getModel().getTable(), BTable.selectionModified, handleDbSelection);
  }

  public void updateContent()
  {
    boolean hasLearn = learnPane != null;
    boolean hasTagDictionary = tagDictionaryPane != null;
    boolean hasDevTemplate = templatePane != null;
    boolean showLearn = hasLearn && controller.learnMode.isSelected();
    boolean showTagDictionary = hasTagDictionary && controller.tagMode.isSelected();
    boolean showTemplate = hasDevTemplate && controller.templateMode.isSelected();
    if (hasLearn) learnPane.setVisible(showLearn);
    if (hasDevTemplate) templatePane.setVisible(showTemplate);
    if (hasTagDictionary) tagDictionaryPane.setVisible(showTagDictionary);
    int tableLearnSplit = 0;

    if (hasLearn && (hasDevTemplate || hasTagDictionary) )
    {
      if(upperPaneSplitter == null) upperPaneSplitter = new BSplitPane(BOrientation.horizontal, 50);
      if(learnPane.getParent() == null)
        upperPaneSplitter.setWidget1(learnPane);
      if(showLearn && (showTemplate || showTagDictionary) )
        upperPaneSplitter.setDividerPosition(50);
      else if( showLearn )
        upperPaneSplitter.setDividerPosition(100);
      else
        upperPaneSplitter.setDividerPosition(0);
      if(showTemplate)
      {
        if(templatePane.getParent() == null)
          upperPaneSplitter.setWidget2(templatePane);
      }
      else if(showTagDictionary)
      {
        if(tagDictionaryPane.getParent() == null)
          upperPaneSplitter.setWidget2(tagDictionaryPane);
      }

      if(showLearn || showTagDictionary || showTemplate )
        tableLearnSplit = 50;
      else
        tableLearnSplit = 0;
      if(mainSplitter == null) mainSplitter = new BSplitPane(BOrientation.vertical, tableLearnSplit);
      mainSplitter.setDividerPosition(tableLearnSplit);
      if(upperPaneSplitter.getParent() == null)
        mainSplitter.setWidget1(upperPaneSplitter);
      BComplex tpParent = tablePane.getParent();
      boolean tpHasParent = tpParent != null;
      if(!tpHasParent)
        mainSplitter.setWidget2(tablePane);

      if(mainSplitter.getParent() == null)
        content.setCenter(mainSplitter);
      mainSplitter.relayout();
      upperPaneSplitter.relayout();
      if(learnPane != null && learnPane.getParentWidget() != null)
        learnPane.getParentWidget().relayout();
      if (tagDictionaryPane != null && tagDictionaryPane.getParentWidget() != null)
        tagDictionaryPane.getParentWidget().relayout();
      if(templatePane != null && templatePane.getParentWidget() != null)
        templatePane.getParentWidget().relayout();

    }
    else if(hasLearn)
    {
      if(showLearn)
        tableLearnSplit = 50;
      else
        tableLearnSplit = 0;
      if(mainSplitter == null) mainSplitter = new BSplitPane(BOrientation.vertical, tableLearnSplit);
      if(learnPane.getParent() == null) mainSplitter.setWidget1(learnPane);
      if(tablePane.getParent() == null) mainSplitter.setWidget2(tablePane);

      content.setCenter(mainSplitter);
      mainSplitter.setDividerPosition(tableLearnSplit);
      mainSplitter.relayout();
    }
    else if(hasDevTemplate || hasTagDictionary)
    {
      if(showTemplate || showTagDictionary)
        tableLearnSplit = 50;
      else
        tableLearnSplit = 0;
      if(mainSplitter == null) mainSplitter = new BSplitPane(BOrientation.vertical, tableLearnSplit);
      mainSplitter.setDividerPosition(tableLearnSplit);
      if(showTemplate)
      {
        if(templatePane.getParent() == null)
          mainSplitter.setWidget1(templatePane);
      }
      else if (showTagDictionary)
      {
        if(tagDictionaryPane.getParent() == null)
          mainSplitter.setWidget1(tagDictionaryPane);
      }
      if(tablePane.getParent() == null)
        mainSplitter.setWidget2(tablePane);
      content.setCenter(mainSplitter);
      mainSplitter.relayout();
      if(templatePane != null && templatePane.getParentWidget() != null)
        templatePane.getParentWidget().relayout();
    }
    else
    {
      content.setCenter(tablePane);
    }

  }

  /**
   * Get the target component passed in doLoadValue()
   * @return target BComponent
   */
  public final BComponent getTarget() { return target ; }
      
////////////////////////////////////////////////////////////////
// Support
////////////////////////////////////////////////////////////////

  /**
   * Get the MgrModel used to manage the database components.  
   * This instance is initialized via <code>makeModel()</code>.
   */
  public final MgrModel getModel()
  {            
    return model;
  }

  /**
   * This method is called once to initialize the MgrModel.
   */
  protected MgrModel makeModel()
  {                       
    return new MgrModel(this);
  }                    

  /**
   * Get the MgrController used to manage user input and commands.  
   * This instance is initialized via <code>makeController()</code>.
   */
  public final MgrController getController()
  {            
    return controller;
  }

  /**
   * This method is called once to initialize the MgrController.
   */
  protected MgrController makeController()
  {                       
    return new MgrController(this);
  }                    

  /**
   * Return true if this manager supports learning.  This
   * is a convenience for <code>getLearn() != null</code>  
   */
  public final boolean isLearnable()
  {                        
    return learn != null;
  }                 
          
  /**
   * Get the MgrLearn used to manage the mgrTemplate process.
   * This instance is initialized via <code>makeLearn()</code>.  
   * Return null if mgrTemplate is not supported.
   */
  public final MgrLearn getLearn()
  {
    return learn;
  }
  
  /**
   * This method is called once to initialize the MgrLearn.
   */
  protected MgrLearn makeLearn()
  {
    return null;
  }

  /**
   * Return true if this manager supports tags.  This
   * is a convenience for <code>getMgrTagDictionary() != null</code>
   */
  public final boolean isTaggable()
  {
    return tagDictionary != null;
  }

  /**
   * Return true if this manager supports templates.  This
   * is a convenience for <code>getMgrTemplate != null</code>
   */
  public final boolean isTemplatable()
  {
    return template != null;
  }

  /**
   * Get the MgrTemplate used to manage the tag process.
   * This instance is initialized via <code>makeDevTemplate()</code>.
   * Return null if device templates are not supported.
   */
  public final MgrTemplate getMgrTemplate()
  {
    return template;
  }

  /**
   * Get the MgrTagDictionary support.
   *
   * @return Returns the support object for tag dictionaries.
   */
  public final MgrTagDictionary getMgrTagDictionary()
  {
    return tagDictionary;
  }

  /**
   * Make the support object for using tag dictionaries in
   * the manager view.  This feature is on my default and
   * may be turned off by overriding this method to return null.
   *
   * @return Returns the support object for tag dictionaries.
   */
  protected MgrTagDictionary makeTagDictionary()
  {
    BComponent target = getTarget();
    TagDictionaryService service = target.getTagDictionaryService();
    if(service != null)
      return new MgrTagDictionary(this);
    return null;
  }

  /**
   * This method is called once to initialize the MgrTemplate.
   */
  protected MgrTemplate makeTemplate()
  {
    return null;
  }

  /**
   * Sets the MgrEdit state to indicate how the MgrEdit is
   * being used (Add, Edit, Match, New).  This is used
   *  to add editable tags to edit dialog.
   */
  public void setMgrEditState(int state)
  {
    this.mgrEditState = state;
  }
  
  /**
   * Gets the MgrEdit state which indicate how the MgrEdit is
   * being used (Add, Edit, Match, New).  This is used
   *  to add editable tags to edit dialog.
   */
  public int getMgrEditState()
  {
    return mgrEditState;
  }

///////////////////////////////////////////////////////////
// Selection
///////////////////////////////////////////////////////////

  public void doHandleLearnSelection()
  {
    controller.learnSelectionChanged();
  }

  public void doHandleTagSelection()
  {
    controller.tagSelectionChanged();
  }

  public void doHandleDbSelection()
  {
    controller.dbSelectionChanged();
  }

////////////////////////////////////////////////////////////////
// WbView
////////////////////////////////////////////////////////////////

  public BToolBar getViewToolBar()
  {
    init();
    return controller.makeToolBar();
  }

  public BMenu[] getViewMenus()
  {
    init();
    return controller.makeMenus();
  }                     
  
  public BTable getExportTable()
  {
    return model.getTable();
  }

  public void doLoadValue(BObject obj, Context cx)
  {                       
    // save target to field
    target = (BComponent)obj;         
    
    // make sure we are built
    init();      

    // support instances
    model.load(target);
    controller.load(target);
    if (learn != null) learn.load(target);         
    if (tagDictionary != null) tagDictionary.load(target);
    if(template != null) template.load(target);
    
    // restore state        
    try { restoreState(); } catch(Exception e) { e.printStackTrace(); }
  }

  public void deactivated()
  {
    super.deactivated();
    
    // save state
    try { saveState(); } catch(Exception e) { e.printStackTrace(); }
  }                
  
////////////////////////////////////////////////////////////////
// Settings
////////////////////////////////////////////////////////////////
  
  /**
   * Called during load to restore state if cached.
   */
  public void restoreState()
  {                         
    MgrState state = MgrState.getCached(MgrState.toKey(this));
    if (state != null) state.restore(this);
  }
  
  /**
   * Called during deactivate to save cached state.
   */
  public void saveState()
  {               
    MgrState state = makeState();
    if (state != null) 
    {
      state.save(this);
      MgrState.cache(MgrState.toKey(this), state);
    } 
  }                           
  
  /**
   * This callback is made during view deactivation to let
   * subclasses use their own MgrState subclass.
   */ 
  protected MgrState makeState()
  {
    return new MgrState();
  }
                  
////////////////////////////////////////////////////////////////
// Eventing
////////////////////////////////////////////////////////////////

  public void handleComponentEvent(BComponentEvent event)
  {
    super.handleComponentEvent(event);          
    
    controller.updateCommands();

    if (learn != null)
    {
      if (learn.getTable() != null)
      {
        //Properties being added or removed from database might
        //influnce the icon learn content from either the "enabled"
        //or "disabled" view, request a refresh on change
        if (BComponentEvent.PROPERTY_ADDED == event.getId() ||
            BComponentEvent.PROPERTY_REMOVED == event.getId())
        {
          learn.getTable().resetIcons();
        }
      }
        
      if (learn.getJob() == event.getSourceComponent())
      {
        final BJob job = (BJob)event.getSourceComponent();
        if (job.getJobState().isComplete() && !learn.jobComplete)
        {
          learn.jobComplete = true;
          
          // We have suppressed subscriptions to learned objects (ie. descendant 
          // components of the learn job) in BMgrTable.handleComponentEvent() up
          // until this point.  Now it is safe to subscribe to everything on the
          // learn job at once, so do it.
          WbViewEventWorker.getInstance().invokeLater(
              new Runnable() {
                public void run()
                {
                  if (isRegisteredForComponentEvents(job))
                  {
                    registerForComponentEvents(job, Integer.MAX_VALUE);
                  }
                  learn.jobComplete(job);
                }
           });
        }
      }
    }
  }
  
////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  Lexicon lex = Lexicon.make(BAbstractManager.class);
  String lexTemplateObjects = lex.getText("template.objects");
  String lexDiscoveryObjects = lex.getText("discovery.objects");
  String lexDatabaseObjects = lex.getText("database.objects");
  
  boolean initialized;
  MgrModel model;
  MgrController controller;
  MgrLearn learn;
  MgrTagDictionary tagDictionary;
  MgrTemplate template;
  BComponent target;
  BWidget tablePane;
  BWidget learnPane;        
  BWidget tagDictionaryPane;
  BWidget templatePane;
  BSplitPane upperPaneSplitter;
  BSplitPane mainSplitter;
  int mgrEditState = 0;
  BEdgePane content;
  
} 
