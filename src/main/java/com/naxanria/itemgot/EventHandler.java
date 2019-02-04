package com.naxanria.itemgot;


import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler
{
  @SubscribeEvent
  public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
  {
    ItemGotMod.logger.info(event.getModID() + " changed");
    
    if (event.getModID().equals(ItemGotMod.MODID))
    {
      
      ItemGotMod.instance.saveConfig();
    }
  }
  
  @SubscribeEvent
  public void onClientTick(TickEvent.ClientTickEvent event)
  {
    ItemGotMod.instance.getHandler().onTickEvent(event);
  }
}
