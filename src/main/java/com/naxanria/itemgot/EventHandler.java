package com.naxanria.itemgot;


import com.naxanria.itemgot.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ItemGotMod.MODID)
public class EventHandler
{
  @SubscribeEvent
  public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
  {
    if (event.getModID().equals(ItemGotMod.MODID))
    {
//      ItemGotMod.instance.saveConfig();
    }
  }
  
  @SubscribeEvent
  public static void onClientTick(final TickEvent.ClientTickEvent event)
  {
    ItemGotMod.instance.getHandler().onTickEvent(event);
  }
  
  @SubscribeEvent
  public static void onRenderHud(final RenderGameOverlayEvent.Post event)
  {
    if (event.getType() != RenderGameOverlayEvent.ElementType.CHAT)
    {
      return;
    }
  
    Util.notNullElse(GUIHud.getInstance(), GUIHud::render, () -> new GUIHud(Minecraft.getInstance(), ItemGotMod.instance.getHandler()));
//    GUIHud.getInstance().render();
  }
}
