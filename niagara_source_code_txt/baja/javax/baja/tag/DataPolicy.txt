/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import javax.baja.sys.BComponent;

/**
 * A DetaPolicy provides information about collection of data of an object.
 *
 * @author Andrew Saunders
 */
public interface DataPolicy
{
  /**
   *   Check the given Entity for the existence of DataPolicy(s) applied through a n:tagGroup relation
   *
   *   @param entity - The Entity to check for the n:tagGroup relations
   *   @return a Collection containing the found DataPolicies.  The collection will be empty if none are found.
   */
  static Collection<DataPolicy> getDataPolicy(Entity entity)
  {
    ArrayList<DataPolicy> dataPolicies = new ArrayList<>();
    Collection<Relation> tagGroupRelations = entity.relations().getAll(Id.newId("n:tagGroup"));
    for (Relation relation : tagGroupRelations)
    {
      Entity endpoint = relation.getEndpoint();
      if (endpoint instanceof BIDataPolicy)
      {
        ((BIDataPolicy)endpoint).getDataPolicy().ifPresent(dataPolicies::add);
      }
    }
    return dataPolicies;
  }

  /**
   *   Check the given Entity for the existence of a DataPolicy for the given tagGroupId applied through a n:tagGroup relation
   *
   *   @param entity - The Entity to check for the n:tagGroup relation
   *   @param tagGroupId - The Id of the tagGroup of interest.
   *   @return a Optional containing the found DataPolicy.
   */
  static Optional<DataPolicy> getDataPolicy(Entity entity, Id tagGroupId)
  {
    Collection<Relation> collection = entity.relations().getAll(Id.newId("n:tagGroup"));
    for (Relation relation : collection)
    {
      Entity endpoint = relation.getEndpoint();
      if (endpoint instanceof TagGroupInfo)
      {
        if (((TagGroupInfo)endpoint).getGroupId().equals(tagGroupId))
        {
          return ((BIDataPolicy)endpoint).getDataPolicy();
        }
      }
    }
    return Optional.empty();
  }

  /**
   *   Get the DataPolicy for a tag with the given tagId.
   *     The tag must be a value tag, i.e., it has a value, NOT a Marker tag.
   *     It will use the base to resolve the station's TagDictionaryService and return the results of
   *     tagDictionaryService.getDataPolicyForTag(tagId)
   *
   *   @param tagId - The Id of for the Tag that is to be checked for a DataPolicy.
   *   @param base - A station component that will be used to resolve the TagDictionaryService.
   *   @return a Optional containing the found DataPolicy.
   */
  static Optional<DataPolicy> getDataPolicyForTag(Id tagId, BComponent base)
  {
    TagDictionaryService tagDictionaryService = base.getTagDictionaryService();
    if (tagDictionaryService != null)
    {
      return tagDictionaryService.getDataPolicyForTag(tagId);
    }
    return Optional.empty();
  }
}
