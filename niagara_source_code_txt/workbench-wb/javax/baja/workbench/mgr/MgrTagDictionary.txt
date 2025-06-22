/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.logging.Logger;
import javax.baja.data.BIDataValue;
import javax.baja.naming.BOrd;
import javax.baja.naming.OrdTarget;
import javax.baja.naming.UnresolvedException;
import javax.baja.sys.BComponent;
import javax.baja.sys.BMarker;
import javax.baja.sys.BRelation;
import javax.baja.sys.BValue;
import javax.baja.sys.Clock;
import javax.baja.sys.Context;
import javax.baja.tag.Entity;
import javax.baja.tag.Id;
import javax.baja.tag.Tag;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;
import javax.baja.tag.Tags;
import javax.baja.ui.BDialog;
import javax.baja.ui.BWidget;
import javax.baja.ui.CommandArtifact;
import javax.baja.ui.table.TableSelection;
import javax.baja.ui.treetable.BTreeTable;
import javax.baja.util.Lexicon;
import javax.baja.workbench.BWbEditor;
import javax.baja.workbench.fieldeditor.BWbFieldEditor;
import javax.baja.workbench.mgr.tag.BTagDictionaryPane;
import javax.baja.workbench.mgr.tag.BTagFilterEnum;
import com.tridium.workbench.util.BEditTagManager;

/**
 * MgrTagDictionary is the support for integration of tag dictionaries into
 * manager views.
 *
 * @author John Sublett
 * @creation 3/3/14
 * @since Niagara 4.0
 */
public class MgrTagDictionary
  extends MgrSupport
{
  /**
   * Constructor.
   */
  public MgrTagDictionary(BAbstractManager manager)
  {
    super(manager);
  }

  public void init()
  {
    getTagDictionaryService();
  }

  public String getNamespace()
  {
    return pane.getNamespace();
  }

  public void setNamespace(String namespace)
  {
    pane.setNamespace(namespace);
  }

  public BTagFilterEnum getTagFilter()
  {
    return pane.getTagFilter();
  }

  public void setTagFilter(BTagFilterEnum tagFilter)
  {
    pane.setTagFilter(tagFilter);
  }

  /**
   * Lookup the TagDictionaryService based on the session of
   * the target component.
   *
   * @return Returns the TagDictionaryService for the current
   *   session or null if the TagDictionaryService is not available.
   */
  public TagDictionaryService getTagDictionaryService()
  {
    long ticks = Clock.ticks();
    if (service != null)
    {
//      ((BComponent)service).lease(Integer.MAX_VALUE, Long.MAX_VALUE);
      logger.fine("tagDictionaryService is cached.");
      return service;
    }

    try
    {
      OrdTarget target =
        BOrd.make("service:tagdictionary:TagDictionaryService").resolve(getTarget());
      service = (TagDictionaryService)target.get();
      ((BComponent)service).lease(Integer.MAX_VALUE, Long.MAX_VALUE);
      logger.fine("tagDictionaryService, load time(ms)=" + (Clock.ticks() - ticks));
      return service;
    }
    catch(UnresolvedException ex)
    {
      if(manager.getTarget() == null)
        return null;
      service = manager.getTarget().getTagDictionaryService();
      if(service != null)
        ((BComponent)service).lease(Integer.MAX_VALUE, Long.MAX_VALUE);
      return service;
    }
  }

  /**
   * Get the tag dictionary pane.
   *
   * @return Returns the tag dictionary pane.
   */
  public BWidget makePane()
  {
    return makePane(null);
  }

  /**
   * Get the tag dictionary pane.
   *
   * @return Returns the tag dictionary pane.
   */
  public BWidget makePane(BComponent tagEditComp)
  {
    if(pane == null)
      pane = new BTagDictionaryPane(this, tagEditComp);
    return pane;
  }

  /**
   * Get the table widget that displays the tags.
   *
   * @return Returns the tree table of tags.
   */
  public BTreeTable getTable()
  {
    return pane.getTable();
  }

  /**
   * Get the currently selected objects.  The objects will
   * all be of one of the types managed by a TagDictionary.
   *
   * @return Returns the array of currently selected objects.
   */
  public Object[] getSelectedObjects()
  {
    TableSelection selection = getTable().getSelection();
    int[] rows = selection.getRows();
    ArrayList<Object> objs = new ArrayList<>(rows.length);
    for (int i = 0; i < rows.length; i++)
    {
      Object obj = getTable().getTreeTableModel().getSubject(rows[i]);
      if (!(obj instanceof String) && !isExisting(obj))
        objs.add(obj);
    }

    return objs.toArray();
  }

  /**
   * Clear the currently object selection.
   *
   */
  public void clearSelectedObjects()
  {
    TableSelection selection = getTable().getSelection();
    selection.deselectAll();
  }

  /**
   * Test whether the specified tag is valid for the specified entity.
   * The default implementation is to return tag.isValidFor(entity).
   *
   * @param tag The tag to test.
   * @param entity The entity to test the tag on.
   * @return Returns true if the tag is valid for the entity,
   *   false otherwise.
   */
  public boolean isTaggable(TagInfo tag, Entity entity)
  {
    BComponent c = (BComponent)entity;
    if (c != null)
    {
      if(c.tags().contains(tag.getTagId()))
        return false;
    }
    return tag.isValidFor(entity);
  }

  /**
   * Test whether the specified tag group is valid for the
   * specified entity. The default implementation is
   * to return tagGroup.isValidFor(entity).
   *
   * @param tagGroup The tagGroup to test.
   * @param entity The entity to the tag group on.
   * @return Returns true if the tag group is valid for the entity,
   *   false otherwise.
   */
  public boolean isTaggable(TagGroupInfo tagGroup, Entity entity)
  {
    BComponent c = (BComponent)entity;
    Tags dbTags = c.tags();
    Iterator<TagInfo> tagGroupTags = tagGroup.getTags();
    boolean containsAll = true;
    while(tagGroupTags.hasNext())
    {
      if( ! dbTags.contains(tagGroupTags.next().getTagId()) )
      {
        containsAll = false;
        break;
      }
    }
    if(containsAll)
      return false;
    return tagGroup.isValidFor(entity);
  }

  /**
   * Return true if the specified tagInfo tags are already on the
   * selected station database components.
   * <code>isExisting(Object)</code>.
   */
  public boolean isExisting(Object tagInfo)
  {
    if(getManager() instanceof BEditTagManager)
    {
      return ((BEditTagManager)getManager()).isExisting(tagInfo);
    }
    boolean existInAll = true;
    BMgrTable dbTable = getManager().getModel().getTable();
    if(dbTable.getSelectedComponent() == null)
      return false;
    if(tagInfo instanceof TagInfo)
    {
      for (BComponent dbSelection : dbTable.getSelectedComponents())
      {
        if( !hasTag((TagInfo)tagInfo, dbSelection))
        {
          existInAll = false;
          break;
        }
      };
    }
    else if(tagInfo instanceof TagGroupInfo)
    {
      TagGroupInfo tgi = (TagGroupInfo)tagInfo;
      Iterator<TagInfo> tgiTags = tgi.getTags();
      while (tgiTags.hasNext())
      {
        TagInfo nextTagInfo = tgiTags.next();
        for (BComponent dbSelection : dbTable.getSelectedComponents())
        {
          if( !hasTag(nextTagInfo, dbSelection))
          {
            existInAll = false;
            break;
          }
        }
        ;
      }
    }
    else
      return false;
    return existInAll;
  }

  private boolean hasTag(TagInfo tag, Entity entity)
  {
    BComponent c = (BComponent)entity;
    if (c != null)
    {
      return c.tags().contains(tag.getTagId());
    }
    return false;
  }

///////////////////////////////////////////////////////////
// Prototypes
///////////////////////////////////////////////////////////

  /**
   * Create an array of prototype entities from the list of types
   * returned by MgrModel.getNewTypes().
   *
   * @return Returns a list of prototype entities that each have
   *   a parent relationship to the manager target component, and
   *   the set of implied tags for their type.
   */
  public Entity[] getNewEntityPrototypes()
  {
    if(newEntities != null)
      return newEntities.toArray(new Entity[newEntities.size()]);
    MgrModel mgrModel = getManager().getModel();
    MgrTypeInfo[] newTypes = mgrModel.getNewTypes();
    if(newTypes == null)
      return new Entity[0];
    newEntities = new ArrayList<>(newTypes.length);
    TagDictionaryService service = getTagDictionaryService();
    for (int i = 0; i < newTypes.length; i++)
    {
      try
      {
        Entity e = mgrModel.newInstance(newTypes[i]);
        // add a parent relationship to the manager target component
        BRelation relation = new BRelation(Id.newId("n:parent"), getTarget());
        e.relations().add(relation);

        Iterator<Tag> tags = service.getImpliedTags(e).iterator();
        while (tags.hasNext())
        {
          e.tags().set(tags.next());
        }

        newEntities.add(e);
      }
      catch(Exception ex)
      {
        System.out.println("MgrTagDictionary.getNewEntityPrototypes " + newTypes[i] + " is not a BComponent, skipping it.");
      }
    }

    return newEntities.toArray(new Entity[newEntities.size()]);
  }

///////////////////////////////////////////////////////////
// Tagging operations
///////////////////////////////////////////////////////////

  public CommandArtifact doTagIt(Context cx)
    throws Exception
  {
    Object[] tagObjects = getSelectedObjects();
    if ((tagObjects == null) || (tagObjects.length == 0))
      return null;

    // get the selected table row
    BComponent[] db = manager.getModel().getTable().getSelectedComponents();
    if (db == null || db.length == 0) return null;

    // check if all tag objects are markers
    boolean allMarkers = true;
    for (int i = 0; i < tagObjects.length; i++)
    {
      if (tagObjects[i] instanceof TagInfo)
      {
        if (!((TagInfo)tagObjects[i]).getTagType().is(BMarker.TYPE))
        {
          allMarkers = false;
        }
        else if (tagObjects[i] instanceof TagGroupInfo)
        {
          Iterator<TagInfo> t = ((TagGroupInfo)tagObjects[i]).getTags();
          while (t.hasNext())
          {
            if (!t.next().getTagType().is(BMarker.TYPE))
            {
              allMarkers = false;
              // break from inner loop
              break;
            }
          }

          // break from outer loop
          if (!allMarkers)
            break;
        }
      }
    }

    if (allMarkers)
      return setMarkerTags(tagObjects, db, cx);
    else
      return setValueAndMarkerTags(tagObjects, db, cx);
  }

  /**
   * Add the selected set of marker tags to all database objects and display
   * a message indicating that the operation is complete.
   *
   * @param tagObjects The marker tags to add.
   * @param db The database objects to add tags to.
   * @return The operation cannot be undone and returns null.
   */
  protected CommandArtifact setMarkerTags(Object[] tagObjects, BComponent[] db, Context cx)
  {
    // count the tags
    int tagCount = 0;
    for (int t = 0; t < tagObjects.length; t++)
    {
      if (tagObjects[t] instanceof TagInfo)
        tagCount++;
      else if (tagObjects[t] instanceof TagGroupInfo)
      {
        Iterator<TagInfo> i = ((TagGroupInfo)tagObjects[t]).getTags();
        while (i.hasNext())
        {
          tagCount++;
          i.next();
        }
      }
    }

    // apply the tags
    for (int t = 0; t < tagObjects.length; t++)
    {
      Object tagObject = tagObjects[t];
      for (int d = 0; d < db.length; d++)
      {
        if (tagObject instanceof TagInfo)
        {
          ((TagInfo)tagObject).setTagOn(db[d]);
        }
        else if (tagObject instanceof TagGroupInfo)
        {
          Iterator<TagInfo> tags = ((TagGroupInfo)tagObject).getTags();
          while (tags.hasNext())
          {
            tags.next().setTagOn(db[d]);
          }
        }
      }
    }

    Lexicon lex = Lexicon.make("workbench", cx);
    String title = null;
    String message = null;

    if (tagCount == 1)
    {
      title = lex.get("tag.added.title");
      if (db.length == 1)
      {
        message = lex.getText("tag.added.singleObject.message");
      }
      else
      {
        message = lex.getText("tag.added.multipleObjects.message",
          new Object[]{ Integer.valueOf(db.length) });
      }
    }
    else
    {
      title = lex.get("tags.added.title");
      if (db.length == 1)
      {
        message = lex.getText("tags.added.singleObject.message",
          new Object[] { Integer.valueOf(tagCount) });
      }
      else
      {
        message = lex.getText("tags.added.multipleObjects.message",
          new Object[]{ Integer.valueOf(tagCount), Integer.valueOf(db.length) });
      }
    }

    BDialog.message(getManager(), title, message);
    return null;
  }

  /**
   * Add the selected set of value tags and marker tags to all database objects.
   * The user will be prompted to edit or accept values for all value tags.
   *
   * @param tagObjects The tags and tag groups to add.
   * @param db The database objects to add tags to.
   * @return This operation cannot be undone.  Returns null.
   */
  protected CommandArtifact setValueAndMarkerTags(Object[] tagObjects, BComponent[] db, Context cx)
    throws Exception
  {
    if ((tagObjects == null) || (tagObjects.length == 0) ||
        (db == null) || (db.length == 0))
      return null;

    Lexicon lex = Lexicon.make("workbench", cx);
    MgrEdit edit = new MgrEdit(getManager(), lex.get("set.tags.title"))
    {
      protected MgrColumn[] makeColumns()
      {
        return makeTagColumns(tagObjects);
      }
    };

    // create edit list
    for(int i=0; i < db.length; ++i)
      edit.addRow(db[i]);

    // prompt user with edit dialog
    edit.setSelectAll(true);
    return edit.invoke(cx);
  }

///////////////////////////////////////////////////////////
// Match Columns
///////////////////////////////////////////////////////////

  public MgrColumn[] makeTagColumns()
  {
    return makeTagColumns(getSelectedObjects());
  }


  public MgrColumn[] makeTagColumns(Object[] tagObjects)
  {
    // tag objects are a combination of TagInfo and TagGroupInfo
    if(tagObjects == null || tagObjects.length == 0 )
    {
      return null;
    }

    // flatten the groups into a list of unique TagInfos
    LinkedHashSet<TagInfo> tagInfos = new LinkedHashSet<>(tagObjects.length);
    for (int i = 0; i < tagObjects.length; i++)
    {
      if (tagObjects[i] instanceof TagInfo)
        tagInfos.add((TagInfo)tagObjects[i]);
      else if (tagObjects[i] instanceof TagGroupInfo)
      {
        Iterator<TagInfo> tg = ((TagGroupInfo)tagObjects[i]).getTags();
        while (tg.hasNext())
          tagInfos.add(tg.next());
      }
    }

    // Create a column for each TagInfo
    ArrayList<MgrColumn> columns = new ArrayList<>(tagInfos.size() + 1);
//    columns.add(new MgrColumn.Name(MgrColumn.READONLY));
    Iterator<TagInfo> i = tagInfos.iterator();
    while (i.hasNext())
    {
      TagInfo tagInfo = i.next();
      columns.add(new TagInfoColumn(tagInfo, tagInfo.getTagId().toString(), 0));
    }

    MgrColumn[] cols = columns.toArray(new MgrColumn[columns.size()]);
    for (int c = 0; c < cols.length; c++)
      cols[c].init(getManager());
    return cols;
  }

///////////////////////////////////////////////////////////
// TagInfoColumn
///////////////////////////////////////////////////////////

  private static class TagInfoColumn
    extends MgrColumn
  {
    TagInfoColumn(TagInfo tagInfo, String displayName, int flags)
    {
      super(displayName, flags);
      this.tagInfo = tagInfo;
    }

    @Override
    public Object get(Object row)
    {
      Optional<BIDataValue> t = ((BComponent)row).tags().get(tagInfo.getTagId());
      if (t.isPresent())
        return t.get();
      else
        return tagInfo.getTag((Entity)row).getValue();
    }

    @Override
    public BValue load(MgrEditRow row)
      throws Exception
    {
      BComponent target = row.getTarget();
      Optional<BIDataValue> v = target.tags().get(tagInfo.getTagId());
      if (v.isPresent())
        return (BValue)v.get();
      else
        return (BValue)tagInfo.getTag(target).getValue();
    }

    @Override
    public void save(MgrEditRow row, BValue value, Context cx)
      throws Exception
    {
      BComponent target = row.getTarget();
      target.tags().set(tagInfo.getTagId(), (BIDataValue)value);
    }

    public BWbEditor toEditor(MgrEditRow[] rows, int colIndex, BWbEditor currentEditor)
    {
      // first make sure every row as the same type
      BValue val = rows[0].getCell(colIndex);
      for(int i=1; i<rows.length; ++i)
        if (rows[i].getCell(colIndex).getClass() != val.getClass())
          return null;
      // create editor if one not created already
      BWbFieldEditor editor = (BWbFieldEditor)currentEditor;
      if (editor == null)
        editor = BWbFieldEditor.makeFor(val);

      // if we didn't find an instance, then create a fresh one

      if (val == null) val = (BValue)tagInfo.getDefaultValue();

      // load the editor
      editor.loadValue(val.newCopy());
      return editor;
    }

    public void fromEditor(MgrEditRow[] rows, int colIndex, BWbEditor widget)
      throws Exception
    {
      BWbFieldEditor editor = (BWbFieldEditor)widget;
      BValue val = (BValue)editor.saveValue();
      for(int i=0; i<rows.length; ++i)
        rows[i].setCell(colIndex, val.newCopy());
    }

    private TagInfo tagInfo;
  }

///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////

  private static Logger logger = Logger.getLogger("mgrTagDictionary.loading");
  private TagDictionaryService service;
  private BTagDictionaryPane pane;
  private ArrayList<Entity> newEntities;
}
