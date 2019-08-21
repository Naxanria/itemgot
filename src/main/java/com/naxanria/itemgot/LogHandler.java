package com.naxanria.itemgot;


import com.naxanria.itemgot.config.ItemGotConfig;
import com.naxanria.itemgot.util.CycleList;
import com.naxanria.itemgot.util.PlayerUtil;
import com.naxanria.itemgot.util.StackTally;
import com.naxanria.itemgot.util.StackUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LogHandler
{
  private CycleList<PickupInfo> list = new CycleList<>(5);
  
  private long updateTime = 5 * 1000;
  
  private int updateCheck = 20;
  private int ticks = 0;
  
  private List<PickupInfo> infoCache = new ArrayList<>();
  
  private Comparator<PickupInfo> sorter = Comparator.comparingLong(PickupInfo::getTime);
  
  private StackTally currentTally;
  
  private PlayerEntity localPlayer;

  private void pickup(ItemStack stack)
  {
    if (localPlayer == null)
    {
      // get local player
      localPlayer = PlayerUtil.getLocalPlayer();
    }
  
    int tot = 0;
  
    if (currentTally != null)
    {
      tot = currentTally.getCount(stack) + stack.getCount();
    }
    
    // filter out AIR, we don't care about that.
    if (stack.getItem() == Items.AIR)
    {
      return;
    }
    
    long now = System.currentTimeMillis();
    
    PickupInfo info = null;
    
    for (PickupInfo pi :
      list)
    {
      if (StackUtil.itemsSame(pi.getStack(), stack) /*pi.getStack().isItemEqual(stack)*/ && now - pi.getTime()  <= updateTime)
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
    
    PlayerEntity player = PlayerUtil.getLocalPlayer();
    
    if (player == null)
    {
      return;
    }
  
  

    if (ItemGotConfig.logSize != list.getMaxSize())
    {
      list.resize(ItemGotConfig.logSize);
    }
  
    if (ItemGotConfig.refreshTime * 1000 != updateTime)
    {
      updateTime = ItemGotConfig.refreshTime * 1000;
    }
    
    if (ItemGotConfig.updateFrequency != updateCheck)
    {
      updateCheck = ItemGotConfig.updateFrequency;
    }
    
    if (++ticks % updateCheck != 0)
    {
      return;
    }

    List<ItemStack> current = player.inventory.mainInventory;
    
    StackTally tally = tally(current);
    
    if (currentTally == null)
    {
      currentTally = tally;
      return;
    }
    
    StackTally diff = tally.difference(currentTally);

    for (ItemStack s :
      diff.getStacks())
    {
      if (s.getCount() > 0)
      {
        pickup(s);
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
    
    return tally;
  }
}
