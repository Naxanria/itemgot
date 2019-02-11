package com.naxanria.itemgot.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class StackUtil
{
  public static boolean itemsSame(ItemStack a, ItemStack b)
  {
    if (a.isEmpty())
    {
      return false;
    }
    
    // todo: NBT stuff etc.
    
    Item item = a.getItem();
    
    if (item instanceof ItemTool)
    {
      // dont check the damage?
      return item == b.getItem();
    }
    
    return  a.isItemEqual(b);
  }
}
