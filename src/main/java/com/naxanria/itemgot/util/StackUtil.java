package com.naxanria.itemgot.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;

public class StackUtil
{
  public static boolean itemsSame(ItemStack a, ItemStack b)
  {
    if (a.isEmpty())
    {
      return false;
    }

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
  
      if (a.getMetadata() == b.getMetadata())
      {
        // check nbt here
        NBTTagCompound compoundA = a.writeToNBT(new NBTTagCompound());
        NBTTagCompound compoundB = b.writeToNBT(new NBTTagCompound());
        
        if (compoundA.getSize() == compoundB.getSize())
        {
          return NBTUtil.areNBTEquals(compoundA, compoundB, true);
        }
      }
    }
    
    return false;
  }
}
