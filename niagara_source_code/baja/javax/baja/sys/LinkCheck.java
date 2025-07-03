/*
 * Copyright 2000 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.sys;

import java.util.Set;

import javax.baja.registry.Registry;
import javax.baja.registry.TypeInfo;
import javax.baja.util.*;

import com.tridium.sys.schema.Fw;

/**
 * LinkCheck is the result of a call to BComponent.checkLink().
 * It stores information about a link's validity.
 *
 * @author    Brian Frank
 * @creation  15 Aug 01
 * @version   $Revision: 18$ $Date: 7/14/10 1:51:12 PM EDT$
 * @since     Baja 1.0
 */
public final class LinkCheck
{

////////////////////////////////////////////////////////////////
// Factories
////////////////////////////////////////////////////////////////

  /**
   * Make a LinkCheck recording indicating a valid link.
   */
  public static LinkCheck makeValid()
  {
    return VALID;
  }

  /**
   * Make a LinkCheck recording indicating a invalid link.
   * This invalid reason should be localized for the Context
   * passed to {@code BComponent.linkCheck()}.
   */
  public static LinkCheck makeInvalid(String invalidReason)
  {
    return new LinkCheck(invalidReason);
  }

////////////////////////////////////////////////////////////////
// Default Checking
////////////////////////////////////////////////////////////////

  /**
   * Package scoped default checking.
   */
  static LinkCheck make(BComponent source, Slot sourceSlot, 
                        BComponent target, Slot targetSlot, 
                        Context cx)
  {
    // link target or source can not be a BPassword or BPermissionsMap or the BUser "roles" property, etc.
    if (isInvalidSlotForLink(target, targetSlot, BIUnlinkableTarget.TYPE, cx))
    {
      return invalid("linkcheck.invalidLinkTarget", cx);
    }
    if (isInvalidSlotForLink(source, sourceSlot, BIUnlinkableSource.TYPE, cx))
    {
      return invalid("linkcheck.invalidLinkSource", cx);
    }

    if (isDuplicate(source, sourceSlot, target, targetSlot))
      return invalid("linkcheck.duplicate", cx);

    if((target.getFlags(targetSlot) & Flags.METADATA) != 0)
        return invalid("linkcheck.meta", cx);
    
    if (sourceSlot.isAction())
    {
      if (targetSlot.isAction()) 
        return make(source, sourceSlot.asAction(), target, targetSlot.asAction(), cx);
      else if (targetSlot.isTopic()) 
        return make(source, sourceSlot.asAction(), target, targetSlot.asTopic(), cx);
      else if (targetSlot.isProperty())  // must do last
        return make(source, sourceSlot.asAction(), target, targetSlot.asProperty(), cx);
      else
        throw new IllegalStateException();      
    }
    else if (sourceSlot.isTopic())
    {
      if (targetSlot.isAction()) 
        return make(source, sourceSlot.asTopic(), target, targetSlot.asAction(), cx);
      else if (targetSlot.isTopic())
        return make(source, sourceSlot.asTopic(), target, targetSlot.asTopic(), cx);
      else if (targetSlot.isProperty())  // must do last
        return make(source, sourceSlot.asTopic(), target, targetSlot.asProperty(), cx);
      else
        throw new IllegalStateException();      
    }
    else if (sourceSlot.isProperty())    // must do last
    {
      if (targetSlot.isAction()) 
        return make(source, sourceSlot.asProperty(), target, targetSlot.asAction(), cx);
      else if (targetSlot.isTopic())
        return make(source, sourceSlot.asProperty(), target, targetSlot.asTopic(), cx);
      else if (targetSlot.isProperty())  // must do last
        return make(source, sourceSlot.asProperty(), target, targetSlot.asProperty(), cx);
      else
        throw new IllegalStateException();      
    }
    else
    {
      throw new IllegalStateException();
    }
  }
  
  /**
   * Check for a an exact duplicate.
   */                    
  private static boolean isDuplicate(BComponent source, Slot sourceSlot, 
                                     BComponent target, Slot targetSlot)
  {
    BLink[] links = target.getLinks(targetSlot);
    for(int i=0; i<links.length; ++i)
    {
      BLink link = links[i];
      if (link.isDirect())
      {
        if (link.getSourceComponent() == source &&
            link.getSourceSlot() == sourceSlot)
          return true;
      }
      else
      {
        // we only check duplicate by handle right now
        if (link.getSourceOrd().equals(source.getHandleOrd()) &&
            link.getSourceSlotName().equals(sourceSlot.getName()))
          return true;
      }
    }
    return false;
  }                                     

  /**
   * Property -> property checking.
   */
  private static LinkCheck make(BComponent source, Property sourceSlot, 
                                BComponent target, Property targetSlot,
                                Context cx)
  { 
    if (Flags.isReadonly(target, targetSlot)) 
      return invalid("linkcheck.propReadonly", cx);
    if (sourceSlot.getType().is(BComponent.TYPE) && !sourceSlot.getType().is(BVector.TYPE))
      return invalid("linkcheck.propComponent", cx);
    if (target.isLinkTarget(targetSlot) && !Flags.isFanIn(target, targetSlot))
      return invalid("linkcheck.propAlreadyLinked", cx);
    return checkType(source, sourceSlot, sourceSlot.getType(), targetSlot, targetSlot.getType(), cx);
  }

  /**
   * Property -> action checking.
   */
  private static LinkCheck make(BComponent source, Property sourceSlot, 
                                BComponent target, Action targetSlot,
                                Context cx)
  { 
    Type param = targetSlot.getParameterType();
    if (param == null) return VALID;
    else return checkType(source, sourceSlot,sourceSlot.getType(), targetSlot, param, cx);
  }

  /**
   * Property -> topic checking.
   */
  private static LinkCheck make(BComponent source, Property sourceSlot, 
                                BComponent target, Topic targetSlot,
                                Context cx)
  { 
    return invalid("linkcheck.propToTopic", cx);
  }

  /**
   * Action -> property checking.
   */
  private static LinkCheck make(BComponent source, Action sourceSlot, 
                                BComponent target, Property targetSlot,
                                Context cx)
  { 
    return invalid("linkcheck.actionToProp", cx);
  }

  /**
   * Action -> action checking.
   */
  private static LinkCheck make(BComponent source, Action sourceSlot, 
                                BComponent target, Action targetSlot,
                                Context cx)
  { 
    // if source is BCompositeAction, then anything goes
    if (isCompositeAction(sourceSlot))
      return VALID;
    
    Type sourceParam = sourceSlot.getParameterType();
    Type targetParam = targetSlot.getParameterType();
    if (targetParam == null) return VALID;
    if (sourceParam == null) return invalid("linkcheck.actionToActionNoArg", cx);
    return checkType(source, sourceSlot, sourceParam, targetSlot, targetParam, cx);
  }

  /**
   * Action -> topic checking.
   */
  private static LinkCheck make(BComponent source, Action sourceSlot, 
                                BComponent target, Topic targetSlot,
                                Context cx)
  { 
    // if target is BCompositeTopic, then anything goes
    if (isCompositeTopic(targetSlot))
      return VALID;
      
    Type sourceEvent = sourceSlot.getParameterType();
    Type targetEvent = targetSlot.getEventType();
    if (targetEvent == null) return VALID;
    if (sourceEvent == null) return invalid("linkcheck.actionToTopicNoEvent", cx);
    return checkType(source, sourceSlot, sourceEvent, targetSlot, targetEvent, cx);
  }

  /**
   * Topic -> property checking.
   */
  private static LinkCheck make(BComponent source, Topic sourceSlot, 
                                BComponent target, Property targetSlot,
                                Context cx)
  {        
    return invalid("linkcheck.topicToProp", cx);
  }

  /**
   * Topic -> action checking.
   */
  private static LinkCheck make(BComponent source, Topic sourceSlot, 
                                BComponent target, Action targetSlot,
                                Context cx)
  { 
    Type event = sourceSlot.getEventType();
    Type param = targetSlot.getParameterType();
    if (param == null) return VALID;
    if (event == null) return invalid("linkcheck.topicToActionNoEvent", cx);
    return checkType(source, sourceSlot, event, targetSlot, param, cx);
  }

  /**
   * Topic -> topic checking.
   */
  private static LinkCheck make(BComponent source, Topic sourceSlot, 
                                BComponent target, Topic targetSlot,
                                Context cx)
  { 
    // if target is BCompositeTopic, then anything goes
    if (isCompositeTopic(targetSlot))
      return VALID;
      
    Type sourceEvent = sourceSlot.getEventType();
    Type targetEvent = targetSlot.getEventType();
    if (targetEvent == null) return VALID;
    if (sourceEvent == null) return invalid("linkcheck.topicToTopicNoEvent", cx);
    return checkType(source, sourceSlot, sourceEvent, targetSlot, targetEvent, cx);
  }
  
  /**
   * Return valid is source type is assignable to target type.
   */
  private static LinkCheck checkType(BComponent sourceComponent, Slot sourceSlot, Type sourceType, Slot targetSlot, Type targetType, Context cx)
  {
    if(sourceType.is(targetType))
      return VALID;

    // BConversionLinks were only supported starting in 3.6.13, so they are invalid
    // on previous versions
    Version version = (Version)sourceComponent.fw(Fw.GET_REMOTE_VERSION, "baja", "rt", null, null);
    if(version!=null && version.compareTo(CONVERSION_LINK_VERSION)!=-1)
    {
      // If makeLink is overridden in a way that doesn't support BConversionLinks, detect
      // that condition.  Calling makeLink does not substitute for checking if the links is
      // valid, since makeLink will allow creating invalid links.
      BLink link = sourceComponent.makeLink(sourceComponent, sourceSlot, targetSlot, cx);
      if(link!=null && link instanceof BConversionLink)
      {
        Registry reg = Sys.getRegistry();
        TypeInfo[] adapters = reg.getAdapters(sourceType.getTypeInfo(), targetType.getTypeInfo());
        for(int i=0; i<adapters.length; i++)
        {
          if(reg.isAgent(adapters[i], BConversionLink.TYPE.getTypeInfo()))
          {
            return VALID; 
          }
        }
      }
    }
    
    Lexicon lex = Lexicon.make(Sys.getBajaModule(), cx.getLanguage());
    Object[] args = new Object[] { sourceType, targetType };
    return makeInvalid(lex.getText("linkcheck.mismatchedTypes", args));
  }
  
  /**
   * Make an invalid LinkCheck with using 
   * string from baja lexicon.
   */
  private static LinkCheck invalid(String lexiconKey, Context cx)
  {
    Lexicon lex = Lexicon.make(Sys.getBajaModule(), cx);
    return makeInvalid(lex.getText(lexiconKey));
  }
  
  /**
   * Return true if action is a BCompositeAction.
   */
  private static boolean isCompositeAction(Action a)
  {
    if (a instanceof Property)
      return ((Property)a).getDefaultValue() instanceof BCompositeAction;
    return false;
  }

  /**
   * Return true if topic is a BCompositeTopic.
   */
  private static boolean isCompositeTopic(Topic t)
  {
    if (t instanceof Property)
      return ((Property)t).getDefaultValue() instanceof BCompositeTopic;
    return false;
  }

  /**
   * Returns true if the given Slot on the given Complex is not valid for linking.
   * If the given Slot is itself a Complex property, then it will recursively
   * check its descendant Slots for link validity
   * (see {@link #containsInvalidSlotForLink(BComplex, Type, Context)}).
   * The given unlinkableType is either {@link BIUnlinkableSource#TYPE} or
   * {@link BIUnlinkableTarget#TYPE}, and it indicates whether the link check is
   * happening for the source or target of a pending link.
   *
   * @since Niagara 4.10u7
   */
  static boolean isInvalidSlotForLink(BComplex complex, Slot slot, Type unlinkableType, Context cx)
  {
    if (complex instanceof BIUnlinkableSlotsContainer)
    {
      BIUnlinkableSlotsContainer container = (BIUnlinkableSlotsContainer) complex;
      Set<Slot> unlinkableSlots = BIUnlinkableTarget.TYPE.equals(unlinkableType) ?
        container.getUnlinkableTargetSlots(cx) : container.getUnlinkableSourceSlots(cx);
      if (unlinkableSlots != null && unlinkableSlots.contains(slot))
      {
        return true;
      }
    }

    if (slot.isProperty())
    {
      Property prop = slot.asProperty();
      Type propType = prop.getType();
      // BIUnlinkableTarget/BIUnlinkableSource implemented by BPassword, BPermissionsMap, etc
      if (propType.is(unlinkableType) ||
          propType.is(BComplex.TYPE) &&
            containsInvalidSlotForLink(complex.get(prop).asComplex(), unlinkableType, cx))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * Returns true if the given Complex (recursively) contains any child Slots that
   * are not valid for linking. The given unlinkableType is either
   * {@link BIUnlinkableSource#TYPE} or {@link BIUnlinkableTarget#TYPE}, and it indicates
   * whether the link check is happening for the source or target of a pending link
   * against the given Complex (or an ancestor of the given Complex).
   *
   * @since Niagara 4.10u7
   */
  private static boolean containsInvalidSlotForLink(BComplex complex, Type unlinkableType, Context cx)
  {
    try (SlotCursor<Slot> slots = complex.getSlots())
    {
      while (slots.next())
      {
        if (isInvalidSlotForLink(complex, slots.slot(), unlinkableType, cx))
        {
          return true;
        }
      }
    }
    return false;
  }
                         
////////////////////////////////////////////////////////////////
// Constructors
////////////////////////////////////////////////////////////////
  
  /**
   * Private constructor.
   */
  private LinkCheck(String invalidReason)
  {
    this.valid = false;
    this.invalidReason = invalidReason;
  }

  /**
   * Private constructor.
   */
  private LinkCheck(boolean valid)
  {
    this.valid = valid;
  }

////////////////////////////////////////////////////////////////
// Methods
////////////////////////////////////////////////////////////////

  /**
   * Is the link checked a valid.
   */
  public boolean isValid()
  {
    return valid;
  }

  /**
   * If the link is not valid, this gets a localized 
   * String message explaining why the link is not valid.
   */
  public String getInvalidReason()
  {
    return invalidReason;
  }

////////////////////////////////////////////////////////////////
// Attributes
////////////////////////////////////////////////////////////////  

  static final LinkCheck VALID = new LinkCheck(true);
  
  // This is the first version in which conversion links were available
  private static final Version CONVERSION_LINK_VERSION = new Version(new int[] {3,6,13});
  
  private boolean valid;
  private String invalidReason;
  
}

