/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.workbench.mgr.tag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.logging.Logger;

import javax.baja.gx.BImage;
import javax.baja.gx.BInsets;
import javax.baja.nre.annotations.NiagaraAction;
import javax.baja.nre.annotations.NiagaraType;
import javax.baja.sys.Action;
import javax.baja.sys.BComponent;
import javax.baja.sys.BIcon;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.tag.Entity;
import javax.baja.tag.TagDictionary;
import javax.baja.tag.TagDictionaryService;
import javax.baja.tag.TagGroupInfo;
import javax.baja.tag.TagInfo;
import javax.baja.ui.BLabel;
import javax.baja.ui.BListDropDown;
import javax.baja.ui.BTextField;
import javax.baja.ui.BWidget;
import javax.baja.ui.event.BFocusEvent;
import javax.baja.ui.event.BKeyEvent;
import javax.baja.ui.event.BMouseEvent;
import javax.baja.ui.event.WidgetSubscriber;
import javax.baja.ui.list.BList;
import javax.baja.ui.pane.BBorderPane;
import javax.baja.ui.pane.BEdgePane;
import javax.baja.ui.pane.BFlowPane;
import javax.baja.ui.treetable.BTreeTable;
import javax.baja.ui.treetable.TreeTableController;
import javax.baja.ui.treetable.TreeTableModel;
import javax.baja.ui.treetable.TreeTableNode;
import javax.baja.ui.util.BTitlePane;
import javax.baja.workbench.mgr.BAbstractManager;
import javax.baja.workbench.mgr.MgrTagDictionary;

import com.tridium.workbench.shell.BFontSize;
import com.tridium.workbench.shell.BGeneralOptions;

/**
 * BTagDictionaryPane
 *
 * @author John Sublett
 * @creation 3/3/14
 * @since Niagara 4.0
 */
@NiagaraType
@NiagaraAction(
  name = "updateDictionary"
)
public class BTagDictionaryPane
  extends BEdgePane
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $javax.baja.workbench.mgr.tag.BTagDictionaryPane(2231822818)1.0$ @*/
/* Generated Thu Jun 02 14:30:07 EDT 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Action "updateDictionary"

  /**
   * Slot for the {@code updateDictionary} action.
   * @see #updateDictionary()
   */
  public static final Action updateDictionary = newAction(0, null);

  /**
   * Invoke the {@code updateDictionary} action.
   * @see #updateDictionary
   */
  public void updateDictionary() { invoke(updateDictionary, null, null); }

  //endregion Action "updateDictionary"

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTagDictionaryPane.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  public BTagDictionaryPane()
  {
  }

  private static class BTagSearchTextField
    extends BTextField
  {
    BTagSearchTextField(MgrTagDictionary mgr)
    {
      this.mgr = mgr;
    }

    @Override
    public void focusGained(BFocusEvent event)
    {
      super.focusGained(event);
      mgr.getManager().getController().setQuickCommandsEnabled(false);
    }

    @Override
    public void focusLost(BFocusEvent event)
    {
      super.focusLost(event);
      mgr.getManager().getController().setQuickCommandsEnabled(true);
    }

    private MgrTagDictionary mgr;
  }

  public BTagDictionaryPane(MgrTagDictionary mgr, BComponent tagEditComp)
  {
    this.mgr = mgr;
    this.tagEditComp = tagEditComp;
    // make the header
    BFlowPane header = new BFlowPane();
    header.add(null, new BLabel(BImage.make(dictionaryIcon)));


    // build the dictionary selector
    tdChoice = new BListDropDown();
    tagSearchEntry = new BTagSearchTextField(this.mgr);
    tagSearchEntry.setVisibleColumns(10);
    tagSearch = "";
    tagFilter = new BListDropDown();
    subscriber = new FilterSubscriber(this);
    subscriber.subscribe(tagSearchEntry);
    filterLabel = new BLabel(filterIcon);
    header.add(null, tdChoice);
    header.add(null, new BLabel(searchIcon, ""));
    header.add(null, tagSearchEntry);
    header.add(null, filterLabel);
    header.add(null, tagFilter);

    BList list = tagFilter.getList();
    int[] ordinals = BTagFilterEnum.DEFAULT.getRange().getOrdinals();
    for(int i =0; i < ordinals.length; ++i)
    {
      list.addItem(BTagFilterEnum.make(ordinals[i]));
    }
    tagFilter.setSelectedItem(BTagFilterEnum.bestOnly);

    TagDictionaryService service = mgr.getTagDictionaryService();
    if (service != null)
    {
      Iterator<TagDictionary> i = service.getTagDictionaries().iterator();
      while (i.hasNext())
      {
        TagDictionary td = i.next();
        tdChoice.getList().addItem(td);
      }
      if (tdChoice.getList().getItemCount() > 0)
      {
        tdChoice.setSelectedIndex(0);
      }
    }

    // add the header with some padding
    if (BGeneralOptions.make().getFontSize() == BFontSize.large)
    {
      setTop(new BBorderPane(header, BInsets.make(0, 0, 5, 0)));
    }
    else
    {
      setTop(new BBorderPane(header, BInsets.make(0, 0, 10, 0)));
    }

    tableModel = new DictionaryModel();
    tagTable = new BTreeTable(tableModel);
    tagTable.setController(new TagDictionaryTableController());

    BTitlePane titlePane =
      BTitlePane.makePane(getLexicon().get("tagDictionary.title", "Tag Dictionary"), tagTable);

    if (tdChoice.getList().getItemCount() > 0)
    {
      tableModel.setTagDictionary((TagDictionary)tdChoice.getSelectedItem());
    }
    setCenter(titlePane);

    linkTo(tdChoice, BListDropDown.valueModified, updateDictionary);
    linkTo(tagFilter, BListDropDown.valueModified, updateDictionary);
    if(mgr.getManager() != null && mgr.getManager() instanceof BAbstractManager)
    {
      linkTo(mgr.getManager(), BAbstractManager.handleDbSelection, updateDictionary);
    }
    doUpdateDictionary();
  }

  /**
   *  This is used by BEditTagDialog when editing tags on a station component.
   */
  public void setTagEditComp(BComponent tagEditComp)
  {
    this.tagEditComp = tagEditComp;
  }

  public BComponent getTagEditComp()
  {
    return tagEditComp;
  }

  public String getNamespace()
  {
    TagDictionary td = (TagDictionary)tdChoice.getSelectedItem();
    if (td == null)
    {
      return null;
    }
    else
    {
      return td.getNamespace();
    }
  }

  public void setNamespace(String namespace)
  {
    Optional<TagDictionary> td =
      mgr.getTagDictionaryService().getTagDictionary(namespace);
    if (td.isPresent())
    {
      tdChoice.setSelectedItem(td.get());
    }
  }

  public String getTagSearch()
  {
    return tagSearch;
  }

  public void setTagSearch(String tagSearch)
  {
    this.tagSearch = tagSearch;
    this.tagSearchEntry.setText(tagSearch);
    searchUpdate();
  }

  public BTreeTable getTable()
  {
    return tagTable;
  }

  public void doUpdateDictionary()
  {
    TagDictionary td = null;
    if (tdChoice.getList().getItemCount() > 0)
    {
      td = (TagDictionary)tdChoice.getSelectedItem();
    }
    tableModel.setTagDictionary(td);
    tableModel.updateTable(true);
  }

  public void setTagFilter(BTagFilterEnum tagFilterSel)
  {
    tagFilter.setSelectedItem(tagFilterSel);
  }

  public BTagFilterEnum getTagFilter()
  {
    return (BTagFilterEnum)tagFilter.getSelectedItem();
  }

  public void setTagFilterVisible(boolean visible)
  {
    tagFilter.setVisible(visible);
    filterLabel.setVisible(visible);
  }

  public void setTagsRootExpanded(boolean expand)
  {
    Optional<TagsRoot> tagsRoot = tableModel.getTagsRoot();
    tableModel.updateTable(true);
    if(tagsRoot.isPresent())
    {
      tagsRoot.get().setExpanded(expand);
    }
  }

  public void setTagGroupsRootExpanded(boolean expand)
  {
    Optional<TagGroupsRoot> tagGroupsRoot = tableModel.getTagGroupsRoot();
    tableModel.updateTable(true);
    if(tagGroupsRoot.isPresent())
    {
      tagGroupsRoot.get().setExpanded(expand);
    }
  }

///////////////////////////////////////////////////////////
// TreeTable Model
///////////////////////////////////////////////////////////

  private class DictionaryModel
    extends TreeTableModel
  {
    public DictionaryModel()
    {
      roots = new ArrayList<>(2);
      roots.add(new TagsRoot(this));
      roots.add(new TagGroupsRoot(this));
    }

    public void setTagDictionary(TagDictionary dictionary)
    {
      boolean wasNull = this.dictionary == null;

      this.dictionary = dictionary;
      Iterator<TreeTableNode> i = roots.iterator();
      while (i.hasNext())
      {
        TreeTableNode root = i.next();
        if (root instanceof DictionaryRoot)
        {
          ((DictionaryRoot)root).updateDictionary(dictionary);
        }
      }

      updateTreeTable(wasNull);
    }

    public TagDictionary getDictionary()
    {
      return dictionary;
    }

    @Override
    public int getRootCount()
    {
      return roots.size();
    }

    public Optional<TagsRoot> getTagsRoot()
    {
      Iterator<TreeTableNode> i = roots.iterator();
      while (i.hasNext())
      {
        TreeTableNode root = i.next();
        if (root instanceof TagsRoot)
        {
          return Optional.of((TagsRoot)root);
        }
      }
      return Optional.empty();
    }

    public Optional<TagGroupsRoot> getTagGroupsRoot()
    {
      Iterator<TreeTableNode> i = roots.iterator();
      while (i.hasNext())
      {
        TreeTableNode root = i.next();
        if (root instanceof TagGroupsRoot)
        {
          return Optional.of((TagGroupsRoot)root);
        }
      }
      return Optional.empty();
    }

    @Override
    public TreeTableNode getRoot(int index)
    {
      return roots.get(index);
    }

    @Override
    public int getColumnCount()
    {
      return 2;
    }

    @Override
    public String getColumnName(int index)
    {
      switch(index)
      {
        case 0: return "Name";
        case 1: return "Type";

        default:
          return "Default-" + index;
      }
    }

    private TagDictionary dictionary;
    private ArrayList<TreeTableNode> roots;
  }

///////////////////////////////////////////////////////////
// Roots
///////////////////////////////////////////////////////////

  private interface DictionaryRoot
  {
    public void updateDictionary(TagDictionary dictionary);

  }

  /**
   * TagsRoot represents the list of tags defined in the current
   * dictionary.
   */
  private class TagsRoot
    extends TreeTableNode
    implements DictionaryRoot
  {
    public TagsRoot(TreeTableModel model)
    {
      super(model);
    }

    @Override
    public void updateDictionary(TagDictionary dictionary)
    {
      tags = null;
    }

    @Override
    public boolean isGroup()
    {
      return true;
    }

    @Override
    public Object getValueAt(int column)
    {
      if (column == 0)
      {
        return getLexicon().get("tag.objects", "Tags");
      }
      else
      {
        return "";
      }
    }

    @Override
    public int getChildCount()
    {
      reload();
      if(tags == null)
      {
        return 0;
      }
      return tags.size();
    }

    @Override
    public TreeTableNode getChild(int index)
    {
      reload();
      return tags.get(index);
    }

    public void reload()
    {
      if (tags != null)
      {
        return;
      }
      tagInfos = new ArrayList<TagInfo>();

      Type baseType = mgr.getManager().getModel().getBaseNewType();
      if(baseType == null)
      {
        logger.warning(mgr.getManager().getModel().getClass().getTypeName() + "does not implement getBaseNewType() method");
        return;
      }
      int filterSelect = ((BTagFilterEnum)tagFilter.getSelectedItem()).getOrdinal();
      // load the tags that are valid for objects managed by this view.
      Entity[] prototypes = mgr.getNewEntityPrototypes();
      TagDictionary td = ((DictionaryModel)getModel()).getDictionary();
      if (td != null)
      {
        Iterator<TagInfo> i = td.getTags();
        while (i.hasNext())
        {
          TagInfo tagInfo = i.next();

          switch(filterSelect)
          {
            case BTagFilterEnum.BEST_ONLY:
              if( tagInfo.isIdealFor(baseType) )
              {
                tagInfos.add(tagInfo);
              }
              break;
            case BTagFilterEnum.SHOW_ALL:
              tagInfos.add( tagInfo);
              break;
            case BTagFilterEnum.VALID_ONLY:
              // test validity on any of the prototype entities
              if(prototypes == null || prototypes.length == 0 && tagEditComp != null)
              {
                prototypes = new Entity[1];
                prototypes[0] = tagEditComp;
              }
              for (int e = 0; e < prototypes.length; e++)
              {
                if (tagInfo.isValidFor(prototypes[e]))
                {
                  tagInfos.add( tagInfo);
                  break;
                }
              }
              break;
            default:
          }
        }
        updateTagNodes();
      }
      // HACK ALERT!!! Just want to have the tagsRoot tree expanded if this is a deviceManager view.
      if(baseType.getTypeName().contains("Device"))
      {
        Optional<TagsRoot> tagsRoot = tableModel.getTagsRoot();
        tableModel.updateTable(true);
        if(tagsRoot.isPresent())
        {
          tagsRoot.get().setExpanded(true);
        }
      }

    }

    protected void updateTagNodes()
    {
      getTable().getSelection().deselectAll();
      tags = new ArrayList<TreeTableNode>();
      for (TagInfo tag : tagInfos)
      {
        boolean checkExactMatch = tagSearch.length() > 2 && tagSearch.endsWith(" ");
        boolean searchMatch = false;
        if(checkExactMatch)
        {
          searchMatch = tag.getName().equals(tagSearch.trim());
        }
        else
        {
          searchMatch = tag.getName().startsWith(tagSearch);
        }
        if(tagSearch == null || tagSearch.length() == 0 || searchMatch)
        {
          tags.add(new TagNode(this, tag));
        }
      }
      // if only one item is listed, select it.
      if(tags.size() == 1)
      {
        getTable().getSelection().select(1);
      }
    }

    @Override
    public BImage getIcon()
    {
      return BImage.make(tagIcon);
    }

    private ArrayList<TreeTableNode> tags;
    private ArrayList<TagInfo> tagInfos;

  }

  /**
   * TagGroupsRoot represents the list of tag groups defined in the current
   * dictionary.
   */
  private class TagGroupsRoot
    extends TreeTableNode
    implements DictionaryRoot
  {
    public TagGroupsRoot(TreeTableModel model)
    {
      super(model);
    }

    @Override
    public void updateDictionary(TagDictionary dictionary)
    {
      tagGroups = null;
    }

    @Override
    public boolean isGroup()
    {
      return true;
    }

    @Override
    public Object getValueAt(int column)
    {
      if (column == 0)
      {
        return getLexicon().get("tagGroup.objects", "Tag Groups");
      }
      else
      {
        return "";
      }
    }

    @Override
    public int getChildCount()
    {
      reload();
      if(tagGroups == null)
      {
        return 0;
      }
      return tagGroups.size();
    }

    @Override
    public TreeTableNode getChild(int index)
    {
      reload();
      return tagGroups.get(index);
    }

    public void reload()
    {
      if (tagGroups != null)
      {
        return;
      }
      tagGroupInfos = new ArrayList<TagGroupInfo>();

      Type baseType = mgr.getManager().getModel().getBaseNewType();
      if(baseType == null)
      {
        return;
      }
      int filterSelect = ((BTagFilterEnum)tagFilter.getSelectedItem()).getOrdinal();
      // load the tag groups that are valid for objects managed by this view.
      Entity[] prototypes = mgr.getNewEntityPrototypes();

      TagDictionary td = ((DictionaryModel)getModel()).getDictionary();
      if (td != null)
      {
        Iterator<TagGroupInfo> i = td.getTagGroups();
        while (i.hasNext())
        {
          TagGroupInfo tagGroupInfo = i.next();

          switch(filterSelect)
          {
            case BTagFilterEnum.BEST_ONLY:
              if( (prototypes.length == 0 && tagGroupInfo.isIdealFor(baseType)) ||
                  (prototypes.length > 0 ) && tagGroupInfo.isIdealFor(baseType) && tagGroupInfo.isValidFor(prototypes[0]) )
              {
                tagGroupInfos.add(tagGroupInfo);
              }
              break;
            case BTagFilterEnum.SHOW_ALL:
              tagGroupInfos.add(tagGroupInfo);
              break;
            case BTagFilterEnum.VALID_ONLY:
              // test validity on any of the prototype entities
              if(prototypes == null || prototypes.length == 0 && tagEditComp != null)
              {
                prototypes = new Entity[1];
                prototypes[0] = tagEditComp;
              }
              for (int e = 0; e < prototypes.length; e++)
              {
                if (tagGroupInfo.isValidFor(prototypes[e]))
                {
                  tagGroupInfos.add(tagGroupInfo);
                  break;
                }
              }
              break;
            default:
          }
        }
        updateTagGroupNodes();
        // HACK ALERT!!! Just want to have the tagGroupsRoot tree expanded if this is a point manager view.
        if(baseType.getTypeName().contains("ControlPoint"))
        {
          Optional<TagGroupsRoot> groupsRoot = tableModel.getTagGroupsRoot();
          tableModel.updateTable(true);
          if(groupsRoot.isPresent())
          {
            groupsRoot.get().setExpanded(true);
          }
        }
      }
    }

    protected void updateTagGroupNodes()
    {
      if(tagGroupInfos == null)
      {
        return;
      }
      tagGroups = new ArrayList<TreeTableNode>();
      for (TagGroupInfo tag : tagGroupInfos)
      {
        if(tagSearch == null || tagSearch.length() == 0 || tag.getName().startsWith(tagSearch))
        {
          tagGroups.add(new TagGroupNode(this, tag));
        }
      }
    }

    @Override
    public BImage getIcon()
    {
      return BImage.make(tagIcon);
    }

    private ArrayList<TreeTableNode> tagGroups;
    private ArrayList<TagGroupInfo> tagGroupInfos;
  }

///////////////////////////////////////////////////////////
// Nodes
///////////////////////////////////////////////////////////

  /**
   * TagNode represents a single TagInfo.
   */
  private class TagNode
    extends TreeTableNode
  {
    public TagNode(TreeTableNode parent, TagInfo tag)
    {
      super(parent);
      this.tag = tag;
    }

    @Override
    public boolean isGroup()
    {
      return false;
    }

    @Override
    public Object getSubject()
    {
      return tag;
    }

    @Override
    public Object getValueAt(int col)
    {
      switch(col)
      {
        case 0: return tag.getTagId().getName();
        case 1: return tag.getTagType().getTypeName();

        default:
          return "";
      }
    }

    @Override
    public BImage getIcon()
    {
      BImage icon = BImage.make(tagIcon);
      if(mgr.isExisting(tag) )
      {
        icon = icon.getDisabledImage();
      }
      return icon;
    }

    private TagInfo tag;
  }

  /**
   * TagGroupNode represents a single TagGroupInfo.
   */
  private class TagGroupNode
    extends TreeTableNode
  {
    public TagGroupNode(TreeTableNode parent, TagGroupInfo tagGroup)
    {
      super(parent);
      this.tagGroup = tagGroup;
    }

    @Override
    public boolean isGroup()
    {
      return false;
    }

    @Override
    public Object getSubject()
    {
      return tagGroup;
    }

    @Override
    public Object getValueAt(int col)
    {
      switch(col)
      {
        case 0: return tagGroup.getGroupId().getName();
        case 1: return "Tag Group";

        default:
          return "";
      }
    }

    @Override
    public BImage getIcon()
    {
      BImage icon = BImage.make(tagIcon);
      if(mgr.isExisting(tagGroup) )
      {
        icon = icon.getDisabledImage();
      }
      return icon;
    }

    private TagGroupInfo tagGroup;
  }

///////////////////////////////////////////////////////////
// Controller
///////////////////////////////////////////////////////////

  private class TagDictionaryTableController
    extends TreeTableController
  {
    /**
     * Call for double click over cell.
     */
    @Override
    protected void cellDoubleClicked(BMouseEvent event, int row, int column)
    {
      mgr.getManager().getController()
        .cellDoubleClicked(getTreeTable(), event, row, column);
    }
  }

////////////////////////////////////////////////////////////////
// FilterSubscriber
////////////////////////////////////////////////////////////////
  class FilterSubscriber extends WidgetSubscriber
  {
    FilterSubscriber(BWidget owner)
    {
      this.owner = owner;
    }
    @Override
    public void keyTyped ( BKeyEvent event)
    {
      // Key entries in the tagSearchEntry filter text field will cause the
      // Tag list in the sourceTagPane to be filtered.
      if(event.getWidget().equals(tagSearchEntry))
      {
//        System.out.println("key->" + event.getKeyChar());
        tagSearch = tagSearchEntry.getText();
        if(tagSearch.endsWith(":"))
        {
          String dctnFilter = tagSearch.substring(0, tagSearch.length()-1);
          BList list = tdChoice.getList();
          for(int i = 0; i < list.getItemCount(); ++i)
          {
            if( ! (list.getItem(i) instanceof TagDictionary) )
            {
              continue;
            }
            if( ((TagDictionary)list.getItem(i)).getNamespace().toLowerCase().startsWith(dctnFilter.toLowerCase()))
            {
              tagSearch = "";
//              tagSearchEntry.setText("");
              list.setSelectedIndex(i);
              tdChoice.repaint();
              doUpdateDictionary();
              tableModel.updateTable(true);
              break;
            }
          }
          tagSearch = "";
          tagSearchEntry.setText("");

        }
        else
        {
          searchUpdate();
        }
      }
    }

    BWidget owner;

  }

  private void searchUpdate()
  {
    Optional<TagsRoot> tagsRoot = tableModel.getTagsRoot();
    Optional<TagGroupsRoot> tagGroupsRoot = tableModel.getTagGroupsRoot();
    if(tagsRoot != null &&tagsRoot.isPresent())
    {
      TagsRoot tr = tagsRoot.get();
      tr.updateTagNodes();
      tr.setExpanded(false);
      tr.setExpanded(true);
    }
    if(tagGroupsRoot.isPresent())
    {
      TagGroupsRoot gr = tagGroupsRoot.get();
      gr.updateTagGroupNodes();
      gr.setExpanded(false);
      gr.setExpanded(true);
    }

  }
///////////////////////////////////////////////////////////
// Fields
///////////////////////////////////////////////////////////
  public static Logger logger = Logger.getLogger("tagDictionaryPane");
  private static BIcon tagIcon = BIcon.std("tag.png");
  private static BImage filterIcon = BImage.make("module://icons/x16/filter.png");
  private static BImage searchIcon = BImage.make("module://icons/x16/find.png");
  private static BIcon dictionaryIcon = BIcon.make(BIcon.std("book.png"), BIcon.std("badges/tag.png"));
  private BLabel filterLabel;
  private MgrTagDictionary mgr;
  private BComponent tagEditComp;
  private BListDropDown tdChoice;
  private BTextField tagSearchEntry;
  private String tagSearch;
  private BListDropDown tagFilter;
  private BTreeTable tagTable;
  private DictionaryModel tableModel;
  private FilterSubscriber subscriber;
}
