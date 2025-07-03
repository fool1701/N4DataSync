/*
 * Copyright 2018 Tridium, Inc. All Rights Reserved.
 */

package com.tridium.testng;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import com.tridium.testng.annotation.IntegrationTest;
import com.tridium.testng.annotation.UnitTest;

/**
 * Transformer to inject groups based on additional annotations.
 *
 * @author Garrett L. Ward on 07 Jun 2018
 */
public class NiagaraAnnotationTransformer implements IAnnotationTransformer
{
  @SuppressWarnings("varargs")
  @SafeVarargs
  private static <T> List<T> listOf(T ... elements)
  {
    List<T> list = new LinkedList<>();
    list.addAll(Arrays.asList(elements));
    return list;
  }

  // Interface does not use generics
  @SuppressWarnings("rawtypes")
  @Override
  public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod)
  {
    NiagaraAnnotationTransformer.<AnnotatedElement>listOf(testClass, testConstructor, testMethod)
      .stream()
      .filter(Objects::nonNull)
      .map(this::getGroups)
      .forEach(groups -> addGroupsToAnnotation(annotation, groups));

  }

  private List<String> getGroups(AnnotatedElement element)
  {
    List<String> groups = new LinkedList<>();
    if (element.getDeclaredAnnotation(IntegrationTest.class) != null)
    {
      groups.add("integrationTest");
    }
    if (element.getDeclaredAnnotation(UnitTest.class) != null)
    {
      groups.add("ci");
    }
    return groups;
  }

  private static void addGroupsToAnnotation(ITestAnnotation annotation, List<String> groups)
  {
    if (groups == null || groups.isEmpty())
    {
      return;
    }
    String[] existingGroups = annotation.getGroups();
    List<String> transformedGroups = new LinkedList<>();

    if (existingGroups == null || existingGroups.length == 0)
    {
      transformedGroups.addAll(groups);
    }
    else
    {
      transformedGroups.addAll(Arrays.asList(existingGroups));
      for (String group : groups)
      {
        if (!transformedGroups.contains(group))
        {
          transformedGroups.add(group);
        }
      }
    }
    annotation.setGroups(transformedGroups.toArray(EMPTY_STRING_ARRAY));
  }

  public static final String[] EMPTY_STRING_ARRAY = new String[0];
}
