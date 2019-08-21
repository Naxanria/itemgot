package com.naxanria.itemgot;


import com.naxanria.itemgot.config.ItemGotConfig;
import com.naxanria.itemgot.event.EventHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod
(
  value = ItemGotMod.MODID
//  modid = ItemGotMod.MODID,
//  name = ItemGotMod.NAME,
//  version = ItemGotMod.VERSION,
//  clientSideOnly = true,
//  acceptableRemoteVersions = "*",
//  guiFactory = "com.naxanria.itemgot.clientConfig.GUIFactory"
)
public final class ItemGotMod
{
  public static final String MODID = "itemgot";
  public static final String VERSION = "@VERSION@";
  public static final String NAME = "ItemGot";
  
  public static ItemGotMod instance;
  
  public static final Logger logger = LogManager.getLogger(MODID);
  
  public File configFile;
  private LogHandler handler;
  
  public ItemGotMod()
  {
    instance = this;
  
    DistExecutor.runWhenOn
    (
      Dist.CLIENT,
      () -> () ->
      {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::clientInit);
        ItemGotConfig.register(ModLoadingContext.get());
      }
    );
  }
  
  private void clientInit(final FMLClientSetupEvent event)
  {
    handler = new LogHandler();
  
    EventHandler.createKeyBinds();
  }
  
  
//  @Mod.EventHandler
//  public void preInit(FMLPreInitializationEvent event)
//  {
//    handler = new LogHandler();
//
//    MinecraftForge.EVENT_BUS.register(new EventHandler());
//    MinecraftForge.EVENT_BUS.register(new GUIHud(Minecraft.getMinecraft(), handler));
//
//    //MinecraftForge.EVENT_BUS.register(handler);
//    //ForgeEventFactory.onItemPickup()
//
//    //FMLCommonHandler.instance().bus().register(handler);
//
//
//    configFile = new File(event.getSuggestedConfigurationFile().getParent(), MODID + "/" + MODID + ".cfg");
//
//    Config.loadConfig(configFile);
//    saveConfig();
//
//
//
//    Config cfg = Config.getInstance();
//
//    logger.info("x=" + cfg.getX() + ",y=" + cfg.getY() + ",text=" + cfg.isDrawText() + ":" + cfg.getTextLength());
//  }
  
//  @Mod.EventHandler
//  public void init(FMLInitializationEvent event)
//  {
//
//  }
//
//  @Mod.EventHandler
//  public void postInit(FMLPostInitializationEvent event)
//  {
//
//  }
  
//  public void saveConfig()
//  {
//    Config.saveConfig(configFile);
//  }
  
  public LogHandler getHandler()
  {
    return handler;
  }
}
