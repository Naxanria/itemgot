package com.naxanria.itemgot.util;

import java.util.function.Consumer;

public class Util
{
  public static <T> boolean notNull(T t, Consumer<T> consumer)
  {
    if (t != null)
    {
      consumer.accept(t);
      return true;
    }
    
    return false;
  }
  
  public static <T> boolean notNullElse(T t, Consumer<T> consumer, Action whenNull)
  {
    if (!notNull(t, consumer))
    {
      whenNull.call();
      return false;
    }
    
    return true;
  }
}
