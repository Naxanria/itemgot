package com.naxanria.itemgot;


import com.naxanria.itemgot.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.*;

@Mod
(
  modid = ItemGotMod.MODID,
  name = ItemGotMod.NAME,
  version = ItemGotMod.VERSION,
  clientSideOnly = true,
  acceptableRemoteVersions = "*",
  guiFactory = "com.naxanria.itemgot.config.GUIFactory"
)
public final class ItemGotMod
{
  public static final String MODID = "itemgot";
  public static final String VERSION = "@VERSION@";
  public static final String NAME = "ItemGot";
  
  @Mod.Instance(MODID)
  public static ItemGotMod instance;
  
  public static final Logger logger = LogManager.getLogger(MODID);
  
  public File configFile;
  private LogHandler handler;
  
  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    handler = new LogHandler();
  
    MinecraftForge.EVENT_BUS.register(new EventHandler());
    MinecraftForge.EVENT_BUS.register(new GUIHud(Minecraft.getMinecraft(), handler));
    
    //MinecraftForge.EVENT_BUS.register(handler);
    //ForgeEventFactory.onItemPickup()
    
    //FMLCommonHandler.instance().bus().register(handler);
    
    
    configFile = new File(event.getSuggestedConfigurationFile().getParent(), MODID + "/" + MODID + ".cfg");
  
    Config.loadConfig(configFile);
    saveConfig();
    
    
    
    Config cfg = Config.getInstance();
    
    logger.info("x=" + cfg.getX() + ",y=" + cfg.getY() + ",text=" + cfg.isDrawText() + ":" + cfg.getTextLength());
  }
  
  @Mod.EventHandler
  public void init(FMLInitializationEvent event)
  {
  
  }
  
  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event)
  {
  
  }
  
  public void saveConfig()
  {
    Config.saveConfig(configFile);
  }
  
  public LogHandler getHandler()
  {
    return handler;
  }
}
