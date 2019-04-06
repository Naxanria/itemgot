package com.naxanria.itemgot.util;

import com.naxanria.itemgot.ItemGotMod;
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
    if (a.getItem() == b.getItem())
    {
      if (a.getItem() instanceof ItemTool)
      {
        return true;
      }
      
      if (a.getDisplayName().equals(b.getDisplayName()))
      {
        return true;
      }
  
      return a.getMetadata() == b.getMetadata();
    }
    
    return false;
  }
}
