/*
 * Copyright 2014 Tridium, Inc. All Rights Reserved.
 */
package javax.baja.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

/**
 * Methods in this utility class may be useful when using the java.util.concurrent API.
 *
 * @author Matt Boon
 * @since Niagara 4.0
 */
public final class ConcurrencyUtil
{
  /**
   * Return a default thread factory used to create new threads.  The factory will
   * use {@link java.util.concurrent.Executors#defaultThreadFactory()} to create
   * the thread, then modify its name by prepending the given prefix.
   *
   * @since Niagara 4.0
   * @see java.util.concurrent.Executors#defaultThreadFactory()
   * @param threadNamePrefixSupplier Provides a name to prepend to the name assigned by {@link java.util.concurrent.Executors#defaultThreadFactory()}
   * @return a thread factory
   */
  public static ThreadFactory defaultThreadFactory(Supplier<String> threadNamePrefixSupplier)
  {
    if (threadNamePrefixSupplier == null)
    {
      return Executors.defaultThreadFactory();
    }
    else
    {
      return new ThreadFactory()
        {
          @Override
          public Thread newThread(Runnable r)
          {
            Thread result = Executors.defaultThreadFactory().newThread(r);
            result.setName(String.format("%s-%s", threadNamePrefixSupplier.get(), result.getName()));
            return result;
          }
        };
    }
  }
  /**
   * Return a default thread factory used to create new threads.  The factory will
   * use {@link java.util.concurrent.Executors#defaultThreadFactory()} to create
   * the thread, then modify its name by prepending the given prefix.
   *
   * @since Niagara 4.0
   * @see java.util.concurrent.Executors#defaultThreadFactory()
   * @param threadNamePrefix Name to prepend to the name assigned by {@link java.util.concurrent.Executors#defaultThreadFactory()}
   * @return a thread factory
   */
  public static ThreadFactory defaultThreadFactory(String threadNamePrefix)
  {
    return defaultThreadFactory(() -> threadNamePrefix);
  }
}
