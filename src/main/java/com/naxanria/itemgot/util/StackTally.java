package com.naxanria.itemgot.util;

import com.naxanria.itemgot.ItemGotMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StackTally
{
  private List<ItemStack> stacks = new ArrayList<>();
  
  public void add(ItemStack stack)
  {
    if (stack.isEmpty())
    {
      return;
    }
    
    ItemStack found = getSameItem(stack);
    
    if (found != null)
    {
      found.setCount(found.getCount() + stack.getCount());
      
      return;
    }
    else
    {
      found = stack.copy();
    }
    
    stacks.add(found);
    
  }
  
  public ItemStack getSameItem(ItemStack stack)
  {
    for (ItemStack s :
      stacks)
    {
      if (StackUtil.itemsSame(s, stack))
      {
        return s;
      }
    }
    
    return null;
  }
  
  public StackTally difference(StackTally other)
  {
    StackTally tally = new StackTally();
    
    for (ItemStack stack :
      stacks)
    {
      ItemStack found = other.getSameItem(stack);
      
      if (found == null)
      {
        tally.add(stack);
        continue;
      }
      
      int c = stack.getCount() - found.getCount();
      
      if (c > 0)
      {
        found = found.copy();
        found.setCount(c);
        tally.add(found);
      }
    }
    
    return tally;
  }
  
  public int getCount(ItemStack stack)
  {
    ItemStack found = getSameItem(stack);
    
    return found != null ? found.getCount() : 0;
  }
  
  public List<ItemStack> getStacks()
  {
    return stacks;
  }
  
}
