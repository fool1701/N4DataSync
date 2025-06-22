/*
 * Copyright 2021 Tridium, Inc. All Rights Reserved.
 */

package javax.baja.user;

/**
 * An interface to indicate that the object supports merging user prototypes.
 */
public interface IHasPrototypeMergePolicy
{
  BUserPrototypeMergePolicy getPrototypeMergePolicy();
}
