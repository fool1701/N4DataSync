/*
 * Copyright 2015 Tridium, Inc. All Rights Reserved.
 */
package com.tridium.testng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.baja.nre.annotations.NiagaraType;
import javax.baja.nre.util.KeyValueTuple;
import javax.baja.sys.Sys;
import javax.baja.sys.Type;
import javax.baja.test.BTestNg;

import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.tridium.nre.RunnableWithException;

@NiagaraType
@Listeners({ ConfigurationFailureListener.class })
public abstract class BTridiumTestNg
  extends BTestNg
{
//region /*+ ------------ BEGIN BAJA AUTO GENERATED CODE ------------ +*/
//@formatter:off
/*@ $com.tridium.testng.BTridiumTestNg(2979906276)1.0$ @*/
/* Generated Wed Jan 05 17:05:31 EST 2022 by Slot-o-Matic (c) Tridium, Inc. 2012-2022 */

  //region Type

  @Override
  public Type getType() { return TYPE; }
  public static final Type TYPE = Sys.loadType(BTridiumTestNg.class);

  //endregion Type

//@formatter:on
//endregion /*+ ------------ END BAJA AUTO GENERATED CODE -------------- +*/

  /**
   * Convenience for creating a TestNG {@link org.testng.annotations.DataProvider} method result
   * from a series of single values
   *
   * @since Niagara 4.0
   */
  public static <T> Object[][] toDataProviderArray(Iterable<T> i)
  {
    return toDataProviderArray(StreamSupport.stream(i.spliterator(), false));
  }

  /**
   * Convenience for creating a TestNG {@link org.testng.annotations.DataProvider} method result
   * from a series of single values
   *
   * @since Niagara 4.0
   */
  public static <T> Object[][] toDataProviderArray(Stream<T> values)
  {
    return values
      .collect(
        Stream::<Object[]>builder,
        (builder, s) -> builder.accept(new Object[] { s }),
        (builder1, builder2) -> builder2.build().forEach(builder1::add))
      .build()
      .toArray(Object[][]::new);
  }

  /**
   * Convenience for creating a TestNG {@link org.testng.annotations.DataProvider} method result
   * from a series of single values
   *
   * @since Niagara 4.0
   */
  @SafeVarargs
  public static <T> Object[][] toDataProviderArray(T... v)
  {
    Object[][] result = new Object[v.length][1];
    for (int i = 0; i < v.length; i++)
    {
      result[i] = new Object[] { v[i] };
    }
    return result;
  }

  /**
   * Convenience for creating a TestNG {@link org.testng.annotations.DataProvider} method result
   *
   * @since Niagara 4.0
   */
  public static <T> Object[][] toDataProviderArray(Iterable<T> inputValues,
                                                   TestMethodSignature signature,
                                                   TestCaseProcessor<T> processor)
  {
    return StreamSupport.stream(inputValues.spliterator(), false)
                        .collect(
                          () -> new DataProviderResults(signature),
                          (testCaseValues, t) -> processor.processInputValue(testCaseValues::add, testCaseValues::newCase, t),
                          DataProviderResults::combine)
                        .toArray();
  }


  /**
   *  Asserts that the given method will fail with an {@link UnsupportedOperationException}
   *
   *  @throws AssertionError if method completes successfully <em>without</em> throwing an {@link UnsupportedOperationException}
   *  @throws E an unexpected exception
   *  @throws RuntimeException may be thrown in some unexpected cases
   *
   *  @since Niagara 4.1
   */
  public static <E extends Exception> void verifyUnsupported(RunnableWithException<E> method, String message)
    throws E
  {
    verifyException(method, UnsupportedOperationException.class, message);
  }

  /**
   *  Asserts that the given method will fail with an {@link IllegalArgumentException}
   *
   *  @throws AssertionError if method completes successfully <em>without</em> throwing an {@link IllegalArgumentException}
   *  @throws E an unexpected exception
   *  @throws RuntimeException may be thrown in some unexpected cases
   *
   *  @since Niagara 4.1
   */
  public static <E extends Exception> void verifyIllegalArgument(RunnableWithException<E> method, String message)
    throws E
  {
    verifyException(method, IllegalArgumentException.class, message);
  }

  /**
   *  Asserts that the given method will fail with an exception of a given type
   *
   *  @throws AssertionError if method completes successfully <em>without</em> throwing an exception of the given type
   *  @throws U an unexpected exception
   *  @throws RuntimeException may be thrown in some unexpected cases
   *
   *  @since Niagara 4.1
   */
  public static <U extends Exception,E extends Exception> void verifyException(RunnableWithException<U> method,
                                                                               Class<E> expectedExceptionClass,
                                                                               String message)
    throws U
  {
    try
    {
      method.run();
      Assert.fail(message);
    }
    catch (Throwable t)
    {
      if (expectedExceptionClass.isAssignableFrom(t.getClass()))
      {
        Assert.assertTrue(true, message);
        return;
      }

      throw t;
    }
  }

  /**
   * Implementing classes/lambdas populate {@link TestCase} objects with
   * test data created from an input value and give them to a consumer
   *
   * @param <T> type of the input value
   *
   * @since Niagara 4.0
   */
  @FunctionalInterface
  public interface TestCaseProcessor <T>
  {
    /**
     * Functional method that processes a single input value and creates one or more TestCases
     * for it.
     *
     * Example implementation:
     * <code>
     *
     *   TestCase case;
     *
     *   case = caseSupplier.get()
     *     .set("stringValue", inputValue.toString())
     *     .set("objectValue", inputValue)
     *     .set("option", "optionOne");
     *
     *   consumer.accept(case);
     *
     *   case = caseSupplier.get()
     *     .set("stringValue", inputValue.toString())
     *     .set("objectValue", inputValue)
     *     .set("option", "optionTwo");
     *
     *   consumer.accept(case);
     *
     * </code>
     *
     * @param caseConsumer each TestCase created by this method should be provided to consumer using
     *   {@link Consumer<TestCase>#accept(javax.baja.test.BTestNg.TestCase)}.  Note:
     *   caseSupplier.get() and consumer.accept() should be invoked the same number of times.
     * @param caseSupplier each individual TestCase should be retrieved from caseSupplier by
     *    this method using {@link Supplier#get()}
     * @param inputValue input value from which TestCases are populated
     */
    void processInputValue(Consumer<TestCase> caseConsumer, Supplier<TestCase> caseSupplier, T inputValue);
  }

  /**
   * Utility for collecting test case values to be returned by a TestNG {@link org.testng.annotations.DataProvider}
   *
   * @since Niagara 4.0
   */
  public static class DataProviderResults
  {
    /**
     * Constructor
     *
     * @param parameterTuples should agree with the signature of the test methods
     *   the data provider supplies data for
     */
    @SafeVarargs
    public DataProviderResults(KeyValueTuple<String, Class<?>>... parameterTuples)
    {
      this(new TestMethodSignature(parameterTuples));
    }

    /**
     * Constructor
     *
     * @param signature should agree with the signature of the test methods
     *   the data provider supplies data for
     */
    public DataProviderResults(TestMethodSignature signature)
    {
      this.signature = signature;
    }

    /**
     * Constructor for a single case with a single parameter
     */
    public <T> DataProviderResults(String parameterName, T parameterValue)
    {
      this(new TestMethodSignature(new KeyValueTuple<>(parameterName, parameterValue.getClass())));
      add(newCase().set(parameterName, parameterValue));
    }

    /**
     * Constructor
     */
    public <T> DataProviderResults(String parameterName, Iterable<T> parameterValues)
    {
      this(parameterName, StreamSupport.stream(parameterValues.spliterator(), false));
    }

    /**
     * Constructor
     */
    public <T> DataProviderResults(String parameterName, Stream<T> parameterValues)
    {
      boolean first = true;

      Iterator<T> iterator = parameterValues.iterator();
      if (iterator.hasNext())
      {
        T value = iterator.next();
        signature = new TestMethodSignature(new KeyValueTuple<>(parameterName, value.getClass()));
        add(newCase().set(parameterName, value));
        iterator.forEachRemaining(remainingValue-> add(newCase().set(parameterName, remainingValue)));
      }
      else
      {
        throw new IllegalStateException("Parameter values stream is empty");
      }
    }

    /**
     * Add a test case
     */
    public DataProviderResults add(TestCase testCase)
    {
      Objects.requireNonNull(testCase);
      if (testCase.signature.equals(signature))
      {
        cases.add(testCase);
        return this;
      }
      else
      {
        throw new IllegalArgumentException("declarations do not agree");
      }
    }

    /**
     * Create and return a new TestCase object that can be used with this
     * DataProviderResults
     */
    public TestCase newCase()
    {
      return new TestCase(signature);
    }

    /**
     * Return an array containing parameter values for every case.
     */
    public Object[][] toArray()
    {
      Object[][] result = new Object[cases.size()][signature.size()];
      for (int i = 0; i < cases.size(); i++)
      {
        result[i] = cases.get(i).values();
      }
      return result;
    }

    /**
     * Adds all of the test cases from the given DataProviderResults object to this one
     *
     * @throws IllegalArgumentException if combine and this do not use the same test method signature
     */
    public void combine(DataProviderResults combine)
    {
      if (combine.signature.equals(signature))
      {
        cases.addAll(combine.cases);
      }
      else
      {
        throw new IllegalArgumentException("declarations do not agree");
      }
    }

    /**
     * Adds or updates test case parameters using the given values.
     *
     * @param parameterName   Name of the parameter whose value should be set.
     * @param parameterValues Values to assign for the parameter, must not be null.
     *                        If there is only one element in the result, then each existing case will be updated
     *                        with its value, and no new cases will be added. If there is more than one
     *                        element in the result, then new cases will be added until there are
     *                        parameterValues.length cases for each existing case, with each being
     *                        identical to the existing case except for the element in the result.
     */
    public <P> void setParameterValues(String parameterName, Iterable<P> parameterValues)
    {
      Objects.requireNonNull(parameterName);
      Objects.requireNonNull(parameterValues);

      List<TestCase> casesToAdd = new ArrayList<>();

      if (cases.size() == 0)
      {
        for (P parameterValue : parameterValues)
        {
          TestCase caseToUpdate;
          casesToAdd.add(caseToUpdate = newCase());
          caseToUpdate.set(parameterName, parameterValue);
        }
      }
      else
      {
        for (TestCase existingCase : cases)
        {
          boolean firstParameter = true;
          for (P parameterValue : parameterValues)
          {
            TestCase caseToUpdate;
            if (firstParameter)
            {
              caseToUpdate = existingCase;
              firstParameter = false;
            }
            else
            {
              casesToAdd.add(caseToUpdate = existingCase.newCopy());
            }
            caseToUpdate.set(parameterName, parameterValue);
          }
        }
      }
      cases.addAll(casesToAdd);
    }

    /**
     * Adds or updates test case parameters by applying the given function to each existing test case.
     *
     * @param parameterName   Name of the parameter whose value should be set.
     * @param valuesFunction function that takes an existing test case as input and returns an iterable
     *                       of values to update the test set with.   If the return value is null or has
     *                       no elements, no value updates will occur. If it has exactly one element, then the
     *                       existing case will be modified with it and no new cases will be added.
     *                       If it has multiple elements, then the existing test case will be duplicated,
     *                       each copy having a value for the named parameter that comes from a
     *                       different result element.
     *
     * @throws IllegalStateException if invoked before any cases exist
     */
    public <P> void setParameterValues(String parameterName, Function<TestCase, Iterable<P>> valuesFunction)
    {
      Objects.requireNonNull(parameterName);
      Objects.requireNonNull(valuesFunction);

      List<TestCase> casesToAdd = new ArrayList<>();

      for (TestCase existingCase : cases)
      {
        Iterable<P> newParameterValues = valuesFunction.apply(existingCase);
        if (newParameterValues != null)
        {
          boolean firstParameter = true;
          for (P parameterValue : newParameterValues)
          {
            TestCase caseToUpdate;
            if (firstParameter)
            {
              caseToUpdate = existingCase;
              firstParameter = false;
            }
            else
            {
              casesToAdd.add(caseToUpdate = existingCase.newCopy());
            }
            caseToUpdate.set(parameterName, parameterValue);
          }
        }
      }
      cases.addAll(casesToAdd);
    }

    /**
     * For each test case in this instance that satisfies the given filter,
     * add a copy of it with modifications made by the given modifier
     */
    public void addCases(Predicate<TestCase> filter, Consumer<TestCase> modifier)
    {
      Objects.requireNonNull(modifier);
      if (filter == null) filter = testCase -> true;

      List<TestCase> newCases = cases
        .stream()
        .filter(filter)
        .map(testCase ->
             {
               TestCase newCase = testCase.newCopy();
               modifier.accept(newCase);
               return newCase;
             })
        .collect(Collectors.toList());
      newCases.forEach(this::add);
    }

    /**
     * For each test case in this instance for which the given filter function returns true,
     * modify it using the given modifier function
     */
    public void modifyCases(Predicate<TestCase> filter, Consumer<TestCase> modifier)
    {
      Objects.requireNonNull(modifier);
      if (filter == null) filter = testCase -> true;
      cases
        .stream()
        .filter(filter)
        .forEach(modifier::accept);
    }

    private final TestMethodSignature signature;
    private final List<TestCase> cases = new ArrayList<>();
  }

  /**
   * Describes the signature of the test methods that use a particular data provider
   *
   * @since Niagara 4.0
   */
  public static class TestMethodSignature
  {
    /**
     * Constructor
     *
     * @param parameterTuples should agree with the signature of the test methods
     *   the data provider supplies data for
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public TestMethodSignature(KeyValueTuple<String, Class<?>>... parameterTuples)
    {
      this.parameterTuples = parameterTuples;
      for (int i = 0; i < parameterTuples.length; i++)
      {
        if (parameterIndices.containsKey(parameterTuples[i].key))
        {
          throw new IllegalArgumentException("Duplicate parameter name: " + parameterTuples[i].key);
        }
        parameterIndices.put(parameterTuples[i].key, i);
      }
    }

    /**
     * Return the type of the parameter with the given name, or throw {@link IllegalArgumentException}
     * if not found
     */
    public Class<?> parameterType(String parameterName)
    {
      return parameterTuples[parameterIndex(parameterName)].value;
    }

    public KeyValueTuple<String,Class<?>>[] parameterTuples()
    {
      return Arrays.copyOf(parameterTuples, parameterTuples.length);
    }

    public boolean hasParameter(String parameterName)
    {
      return parameterIndices.containsKey(parameterName);
    }

    /**
     * Return the index of the parameter with the given name, or throw {@link IllegalArgumentException}
     * if not found
     */
    public int parameterIndex(String parameterName)
    {
      Integer parameterIndex = parameterIndices.get(parameterName);
      if (parameterIndex == null)
      {
        throw new IllegalArgumentException("parameter " + parameterName + " not declared");
      }
      return parameterIndex;
    }

    /**
     * Return all of this signature's parameter names
     */
    public Collection<String> parameterNames()
    {
      return new ArrayList<>(parameterIndices.keySet());
    }

    /**
     * Return the number of parameters
     */
    public int size()
    {
      return parameterIndices.size();
    }

    @Override
    public boolean equals(Object o)
    {
      return o instanceof TestMethodSignature &&
        Arrays.equals(((TestMethodSignature) o).parameterTuples, parameterTuples) &&
        Objects.equals(((TestMethodSignature) o).parameterIndices, parameterIndices);
    }

    @Override
    public int hashCode()
    {
      return Objects.hash(parameterIndices, Arrays.hashCode(parameterTuples));
    }

    private final Map<String,Integer> parameterIndices = new HashMap<>();
    private final KeyValueTuple<String,Class<?>>[] parameterTuples;
  }

  /**
   * Encapsulates the parameter values used for a single test method invocation
   *
   * @since Niagara 4.0
   */
  public static class TestCase
  {
    public TestCase(TestMethodSignature signature)
    {
      Objects.requireNonNull(signature);
      this.signature = signature;
      this.parameterValues = new Object[signature.size()];
    }

    /**
     * Set the parameter in the current test case with the given name to the given value.
     */
    public <T> TestCase set(String parameterName, T parameterValue)
    {
      Objects.requireNonNull(parameterName);
      //noinspection unchecked
      if ((parameterValue != null) &&
        !signature.parameterType(parameterName).isAssignableFrom(parameterValue.getClass()))
      {
        throw new ClassCastException("cannot assign " + parameterValue.getClass().getName() + " to " + signature.parameterType(parameterName).getName());
      }
      parameterValues[signature.parameterIndex(parameterName)] = parameterValue;
      return this;
    }

    /**
     * Return the value for the parameter with the given name
     */
    public Object get(String parameterName)
    {
      return parameterValues[signature.parameterIndex(parameterName)];
    }

    /**
     * Return the parameter values as an array of Object
     */
    public Object[] values()
    {
      return parameterValues;
    }

    /**
     * Return a shallow copy of this case
     */
    public TestCase newCopy()
    {
      TestCase result = new TestCase(signature);
      System.arraycopy(parameterValues, 0, result.parameterValues, 0, parameterValues.length);
      return result;
    }

    private final TestMethodSignature signature;
    private final Object[] parameterValues;
  }
}
