package com.naxanria.itemgot.util;

import com.naxanria.itemgot.ItemGotMod;
import net.minecraft.client.Minecraft;
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
  
  public static EntityPlayer getLocalPlayer()
  {
    return Minecraft.getMinecraft().player;
  }
  
  public static boolean arePlayersSame(EntityPlayer player1, EntityPlayer player2)
  {
    int i = player1.getUniqueID().compareTo(player2.getUniqueID());
    ItemGotMod.logger.info("<> " + i);
    return i == 0;
  }
}
