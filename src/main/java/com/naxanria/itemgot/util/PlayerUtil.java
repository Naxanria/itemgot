package com.naxanria.itemgot.util;

import com.naxanria.itemgot.ItemGotMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;

public class PlayerUtil
{
  public static int getTotalForItem(PlayerEntity player, ItemStack search)
  {
    PlayerInventory inventory = player.inventory;
    
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
  
  public static PlayerEntity getLocalPlayer()
  {
    return Minecraft.getInstance().player;
  }
  
  public static boolean arePlayersSame(PlayerEntity player1, PlayerEntity player2)
  {
    int i = player1.getUniqueID().compareTo(player2.getUniqueID());
    ItemGotMod.logger.info("<> " + i);
    return i == 0;
  }
}
