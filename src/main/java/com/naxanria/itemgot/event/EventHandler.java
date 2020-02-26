package com.naxanria.itemgot.event;


import com.naxanria.itemgot.GUIHud;
import com.naxanria.itemgot.ItemGotMod;
import com.naxanria.itemgot.config.gui.ConfigGui;
import com.naxanria.itemgot.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ItemGotMod.MODID)
public class EventHandler
{
  public static final String KEY_BIND_CATEGORY = "itemgot";
  
  // fixme: no more @SubscribeEvent
  @SubscribeEvent
  public static void onConfigChanged(ModConfig.Reloading event)
  {
    if (event.getConfig().getModId().equals(ItemGotMod.MODID))
    {
      // todo: reload?
//      ItemGotMod.instance.saveConfig();
    }
  }
  
  @SubscribeEvent
  public static void onClientTick(final TickEvent.ClientTickEvent event)
  {
    KeyHandler.INSTANCE.update();
    
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
  
  public static void createKeyBinds()
  {
    KeyHandler.INSTANCE.register(new KeyParser(createKeyBinding("show_config", GLFW.GLFW_KEY_KP_9, KeyConflictContext.IN_GAME))
    {
      @Override
      public void onKeyUp()
      {
        mc.displayGuiScreen(new ConfigGui(null));
      }
  
      @Override
      public boolean isListening()
      {
        return mc.currentScreen == null;
      }
    });
  }
  
  private static KeyBinding createKeyBinding(String name, int key, KeyConflictContext conflictContext)
  {
    return new KeyBinding(name, conflictContext, InputMappings.Type.KEYSYM, key, KEY_BIND_CATEGORY);
  }
}
