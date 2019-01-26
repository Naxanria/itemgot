package com.naxanria.itemgot;


import com.naxanria.itemgot.config.Config;
import com.naxanria.itemgot.util.CycleList;
import com.naxanria.itemgot.util.PlayerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LogHandler
{
  private CycleList<PickupInfo> list = new CycleList<>(5);
  
  private long updateTime = 5 * 1000;
  private List<PickupInfo> infoCache = new ArrayList<>();
  
  private Comparator<PickupInfo> sorter = Comparator.comparing(c -> -c.getTime());
  
  @SubscribeEvent
  public void onPickupItem(PlayerEvent.ItemPickupEvent event)
  {
    Config config = Config.getInstance();
    if (config.getLogSize() != list.getMaxSize())
    {
      list.resize(config.getLogSize());
    }
    
    if (config.getRefreshTime() * 1000 != updateTime)
    {
      updateTime = config.getRefreshTime() * 1000;
    }
    
    ItemStack stack = event.getStack();
  
    int tot = PlayerUtil.getTotalForItem(event.player, stack);
    
    // filter out AIR, we don't care about that.
    if (stack.getItem() == Items.AIR)
    {
      return;
    }
    
    String s = stack.getDisplayName() + " +" + stack.getCount() + " (" + tot + ")";
    //String s = event.getItem().getName();
    
    //ItemGotMod.logger.info("Picked up an item! " + s);
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
//      info.setCount(info.getCount() + stack.getCount());
      info.update(stack.getCount(), tot);
//      info.setTotal(tot);
    }
    
    updateCache();
  }
  
  private void updateCache()
  {
    infoCache = list.toList();//
    infoCache.sort(sorter);
  }
  
  public CycleList<PickupInfo> getList()
  {
    return list;
  }
}
