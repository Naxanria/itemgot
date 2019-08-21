package com.naxanria.itemgot.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class ItemGotConfig
{
  public static int x = 10;
  public static int y = 10;
  
  public static int logSize = 8;
  public static int updateFrequency = 10;
  
  public static boolean drawText = false;
  public static boolean drawTotal = true;
  
  public static int textLength = 10;
  public static int textColor = 0xffffffff;
  public static int backgroundColor = 0x00000000;
  
  public static int refreshTime = 5;
  public static int fadeTime = 10;
  public static int fadeDelay = 5;
  
  public static Client clientConfig;
  private static ForgeConfigSpec clientConfigSpec;
  
  static
  {
    final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
    clientConfigSpec = specPair.getRight();
    clientConfig = specPair.getLeft();
  }
  
  private static String key(String key)
  {
    return "itemgot.clientConfig." + key;
  }
  
  public static void register(final ModLoadingContext context)
  {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ItemGotConfig::reload);
    
    context.registerConfig(ModConfig.Type.CLIENT, clientConfigSpec);
  }
  
  public static void reload(ModConfig.ModConfigEvent event)
  {
    ModConfig config = event.getConfig();
    
    if (config.getSpec() == clientConfigSpec)
    {
      reloadClient();
    }
  }
  
  private static void reloadClient()
  {
    x = clientConfig.x.get();
    y = clientConfig.y.get();
    logSize = clientConfig.logSize.get();
    
    updateFrequency = clientConfig.updateFrequency.get();
    
    textLength = clientConfig.textLength.get();
    drawText = clientConfig.drawText.get();
    drawTotal = clientConfig.drawTotal.get();
    
    textColor = clientConfig.textColor.get();
    backgroundColor = clientConfig.backgroundColor.get();
    
    refreshTime = clientConfig.refreshTime.get();
    fadeTime = clientConfig.fadeTime.get();
    fadeDelay = clientConfig.fadeDelay.get();
  }
  
  public static class Client
  {
    public final ForgeConfigSpec.IntValue x;
    public final ForgeConfigSpec.IntValue y;
    public final ForgeConfigSpec.IntValue logSize;
    
    
    public final ForgeConfigSpec.IntValue updateFrequency;
    
    public final ForgeConfigSpec.BooleanValue drawText;
    public final ForgeConfigSpec.BooleanValue drawTotal;
    public final ForgeConfigSpec.IntValue textLength;
    
    public final ForgeConfigSpec.IntValue textColor;
    public final ForgeConfigSpec.IntValue backgroundColor;
    public final ForgeConfigSpec.IntValue refreshTime;
    public final ForgeConfigSpec.IntValue fadeTime;
    public final ForgeConfigSpec.IntValue fadeDelay;
    
    
    
    public Client(ForgeConfigSpec.Builder builder)
    {
      builder.comment("ItemGot settings").push("General");
      x = builder
        .comment("Position of the log")
        .translation(key("position_x"))
        .defineInRange("x", 10, 0, 32000);
      y = builder
        .translation(key("position_y"))
        .defineInRange("y", 10, 0, 32000);
      
      logSize = builder
        .comment("The size of the log")
        .translation(key("log_size"))
        .defineInRange("log_size", 8, 2, 20);
      
      updateFrequency = builder
        .comment("The time in ticks between checks")
        .translation(key("update_frequency"))
        .defineInRange("update_frequency", 10, 1, 80);
      
      builder.pop().comment("The look and feel").push("LookAndFeel");
      
      textLength = builder
        .comment("The max length of the item names")
        .translation(key("text_length"))
        .defineInRange("text_length", 10, 4, 32);
      
      drawText = builder
        .comment("Draw the name of the item")
        .translation(key("drawText"))
        .define("draw_text", false);
      
      drawTotal = builder
        .comment("Draw the total of that item in the inventory")
        .translation(key("draw_total"))
        .define("draw_total", true);
      
      textColor = builder
        .comment("The text color")
        .translation(key("text_color"))
        .defineInRange("text_color", 0xffffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);
      
      backgroundColor = builder
        .comment("The background color")
        .translation(key("background_color"))
        .defineInRange("background_color", 0x00000000, Integer.MIN_VALUE, Integer.MAX_VALUE);
      
      builder.pop().comment("The timings for showing").push("Timings");
      
      refreshTime = builder
        .comment("The time in seconds, that the picked up item will refresh the duration of showing the item")
        .translation(key("refresh_time"))
        .defineInRange("refresh_time", 5, 2, 10);
      
      fadeTime = builder
        .comment("The time the item fades out (in seconds)")
        .translation(key("fade_time"))
        .defineInRange("fade_time", 10, 1, 30);
      
      fadeDelay = builder
        .comment("The time the item is shown till it fades out (in seconds)")
        .translation(key("fade_delay"))
        .defineInRange("fade_delay", 5, 0, 20);
      
    }
  }
}
