package com.naxanria.itemgot;


import com.naxanria.itemgot.config.Config;
import com.naxanria.itemgot.util.CycleList;
import com.naxanria.itemgot.util.MathUtil;
import com.naxanria.itemgot.util.PlayerUtil;
import com.naxanria.itemgot.util.StackTally;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LogHandler
{
  private CycleList<PickupInfo> list = new CycleList<>(5);
  
  private long updateTime = 5 * 1000;
  
  private int updateCheck = 20;
  private int ticks = 0;
  
  private List<ItemStack> inventory;
  
  private List<PickupInfo> infoCache = new ArrayList<>();
  
  private Comparator<PickupInfo> sorter = Comparator.comparingLong(PickupInfo::getTime);
  
  private StackTally currentTally;
  
  private EntityPlayer localPlayer;

  private void pickup(ItemStack stack)
  {
    if (localPlayer == null)
    {
      // get local player
      localPlayer = PlayerUtil.getLocalPlayer();
    }
  
    ItemGotMod.logger.info("All went well...");
  
    int tot = 0;
  
    if (currentTally != null)
    {
      tot = currentTally.getCount(stack) + stack.getCount(); //PlayerUtil.getTotalForItem(localPlayer, stack);
    }
    
    // filter out AIR, we don't care about that.
    if (stack.getItem() == Items.AIR)
    {
      return;
    }
    
//    String s = stack.getDisplayName() + " +" + stack.getCount() + " (" + tot + ")";
    
    long now = System.currentTimeMillis();
    
    PickupInfo info = null;
    // see if we can update an older one?
    for (PickupInfo pi :
      list)
    {
      if (pi.getStack().isItemEqual(stack) && now - pi.getTime()  <= updateTime)
      {
        info = pi;
        break;
      }
    }
    
    if (info == null)
    {
      info = new PickupInfo(stack, stack.getCount(), tot);
      list.add(info);
    }
    else
    {
      info.update(stack.getCount(), tot);
    }
    
    updateCache();
  }
  
  private void updateCache()
  {
    infoCache = list.toList();
    infoCache.sort(sorter);
  }
  
  public CycleList<PickupInfo> getList()
  {
    return list;
  }
  
  public void onTickEvent(TickEvent.ClientTickEvent event)
  {
    if (event.phase != TickEvent.Phase.END)
    {
      return;
    }
    
    EntityPlayer player = PlayerUtil.getLocalPlayer();
    
    if (player == null)
    {
      return;
    }
  
    Config config = Config.getInstance();
    if (config.getLogSize() != list.getMaxSize())
    {
      list.resize(config.getLogSize());
    }
  
    if (config.getRefreshTime() * 1000 != updateTime)
    {
      updateTime = config.getRefreshTime() * 1000;
    }
    
    if (config.getUpdateFrequency() != updateCheck)
    {
      updateCheck = MathUtil.clamp(config.getUpdateFrequency(), 1, 40);
    }
    
    if (++ticks % updateCheck != 0)
    {
      return;
    }
    ItemGotMod.logger.info("Here we should check for items...");
    
    if (inventory == null)
    {
      updateInventory(player);
      
      currentTally = tally(inventory);
      
      return;
    }
    
    List<ItemStack> current = player.inventory.mainInventory;
    
    StackTally tally = tally(current);
  
    ItemGotMod.logger.info(tally.getStacks().size() + "");
    
    StackTally diff = tally.difference(currentTally);

    for (ItemStack s :
      diff.getStacks())
    {
      if (s.getCount() > 0)
      {
        pickup(s);
        ItemGotMod.logger.info("Picked up items...");
      }
    }
    
    currentTally = tally;
  }
  
  private StackTally tally(List<ItemStack> inventory)
  {
    StackTally tally = new StackTally();
  
    for (ItemStack s :
      inventory)
    {
      tally.add(s);
    }
    
    ItemGotMod.logger.info("== Tally ==");
    for (ItemStack s :
      tally.getStacks())
    {
      ItemGotMod.logger.info("    " + s.getUnlocalizedName() + " " + s.getCount());
    }
    
    return tally;
  }
  
  private void updateInventory(EntityPlayer player)
  {
    inventory = new ArrayList<>(player.inventory.mainInventory);
  }
}
