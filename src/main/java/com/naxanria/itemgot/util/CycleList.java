package com.naxanria.itemgot.util;

//import com.sun.istack.internal.NotNull;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CycleList<T> implements Iterable<T>
{
  private int size;
  private int count;
  private int pointer;
  private Object[] elements;
  
  public CycleList()
  {
    this(10);
  }
  
  public CycleList(int size)
  {
    this.size = size;
    elements = new Object[size];
  }
  
  public int getSize()
  {
    return count;
  }
  
  public int getMaxSize()
  {
    return size;
  }
  
  public T get(int index)
  {
    int pos = (pointer + index) % getSize();
    return (T) elements[pos];
  }
  
  public T add(T t)
  {
    int pos = (pointer++ % size);
    if (count < size)
    {
      count++;
    }
    elements[pos] = t;
    
    return t;
  }
  
  public T set(int index, T t)
  {
    int pos = (index + pointer) % size;
    elements[pos] = t;
    
    return t;
  }
  
  public void resize(int newSize)
  {
    if (newSize <= 0)
    {
      throw new IllegalArgumentException();
    }
    
    if (newSize == size)
    {
      return;
    }
    
    Object[] n = new Object[newSize];
  
    // copy it over into new array
    for (int i = 0; i < n.length && i < elements.length; i++)
    {
      n[i] = elements[i];
    }
    
    elements = n;
    size = newSize;
  }
  
  @Override
  //@NotNull
  public Iterator<T> iterator()
  {
    return new Iterator<T>()
    {
      int index = 0;
      
      @Override
      public boolean hasNext()
      {
        return index < getSize();
      }
  
      @Override
      public T next()
      {
        return get(index++);
      }
    };
  }
  
  public List<T> toList()
  {
    List<T> list = new ArrayList<>();
    for (Object e :
      elements)
    {
      if (e == null)
      {
        break;
      }
      
      list.add((T) e);
    }
    
    return list;
  }
}
