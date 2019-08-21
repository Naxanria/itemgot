package com.naxanria.itemgot;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PickupInfo
{
  private long time;
  private ItemStack stack;
  private int count;
  private int total;
  
  public PickupInfo(ItemStack stack, int count, int total)
  {
    this.stack = stack;
    this.count = count;
    this.total = total;
    
    time = System.currentTimeMillis();
  }
  
  public void setCount(int count)
  {
    this.count = count;
    updateTime();
  }
  
  public void setTotal(int total)
  {
    this.total = total;
    updateTime();
  }
  
  private void updateTime()
  {
//    ItemGotMod.logger.info("Updating time!");
    time = System.currentTimeMillis();
  }
  
  public int getCount()
  {
    return count;
  }
  
  public long getTime()
  {
    return time;
  }
  
  public Item getItem()
  {
    return stack.getItem();
  }
  
  public ItemStack getStack()
  {
    return stack;
  }
  
  public String getName()
  {
    return stack.getDisplayName().getString();
  }
  
  public int getTotal()
  {
    return total;
  }
  
  public void update(int increment, int total)
  {
    count += increment;
    this.total = total;
    updateTime();
  }
}
