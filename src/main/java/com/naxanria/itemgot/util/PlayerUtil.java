package com.naxanria.itemgot.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PlayerUtil
{
  public static int getTotalForItem(EntityPlayer player, ItemStack search)
  {
    InventoryPlayer inventory = player.inventory;
    
    int total = 0;
    for (ItemStack stack :
      inventory.mainInventory)
    {
      if (stack.isItemEqual(search))
      {
        total += stack.getCount();
      }
    }
    
    return total;
  }
}
