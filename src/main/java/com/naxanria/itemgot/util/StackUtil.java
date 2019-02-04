package com.naxanria.itemgot.util;

import net.minecraft.item.ItemStack;

public class StackUtil
{
  public static boolean itemsSame(ItemStack a, ItemStack b)
  {
    // todo: NBT stuff etc.
    return  a.isItemEqual(b);
  }
}
