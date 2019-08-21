package com.naxanria.itemgot;

import com.mojang.blaze3d.platform.GlStateManager;
import com.naxanria.itemgot.config.ItemGotConfig;
import com.naxanria.itemgot.util.ColorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ItemGotMod.MODID)
public class GUIHud extends AbstractGui
{
  private static GUIHud instance;
  private final Minecraft MC;
  private final LogHandler handler;
  private FontRenderer fontRenderer;
  private ItemRenderer renderItem;

  public GUIHud(Minecraft minecraft, LogHandler handler)
  {
//    super();
    
    instance = this;
  
    MC = minecraft;
    this.handler = handler;
    
    fontRenderer = MC.fontRenderer;
    renderItem = MC.getItemRenderer();
  }
  
  public static GUIHud getInstance()
  {
    return instance;
  }

  
  public void render()
  {
    if (fontRenderer == null)
    {
      fontRenderer = MC.fontRenderer;
    }
    
    if (renderItem == null)
    {
      renderItem = MC.getItemRenderer();
    }
    
    int x = ItemGotConfig.x;
    int y = ItemGotConfig.y;
    int w;
    
    int textSpacing = 2;
    int index = 0;
    int fh = fontRenderer.FONT_HEIGHT;
    int h = fh + textSpacing * 2;
    if (h < 16)
    {
      h = 16;
    }
//
//    int bgA = config.getBgAlpha();
//    int bgR = config.getBgRed();
//    int bgG = config.getBgGreen();
//    int bgB = config.getBgBlue();
//
//    int txtA = 255;
//    int txtR = config.getTextRed();
//    int txtG = config.getTextGreen();
//    int txtB = config.getTextBlue();
    
    int backgroundColor = ItemGotConfig.backgroundColor;
    int textColor = ItemGotConfig.textColor;
    
    long fullTime = ItemGotConfig.fadeDelay * 1000;
    long fadeTime = ItemGotConfig.fadeTime * 1000;
    
    long now = System.currentTimeMillis();
    
    boolean drawName = ItemGotConfig.drawText;
    boolean drawTotal = ItemGotConfig.drawTotal;
    int maxNameLength = ItemGotConfig.textLength;
    
    
    
    if (!drawName)
    {
      maxNameLength = 0;
    }
    
    
    
    //drawRect(x, y, x + w, y + h, color);
//    drawString(fontRenderer, "This is a test", x + 2, y + 6, 0xFFFFFF);
    RenderHelper.disableStandardItemLighting();
    RenderHelper.enableGUIStandardItemLighting();
    
    for (PickupInfo info:
         handler.getList())
    {
      long diff = now - info.getTime();
      if (diff >= fullTime + fadeTime)
      {
        continue;
      }
      
     // String debug = "";
      
      
      
      float ba = ColorHelper.getAlpha(backgroundColor);// / 255.0f;
      float ta = ColorHelper.getAlpha(textColor);// / 255.0f;
      
      if (diff > fullTime)
      {
        // adjust alphas
        diff -= fullTime;
        float perc = 1f - ((float)diff / fadeTime);
        
        
        
        ba *= perc;
        ta *= perc;
  
        //debug = debug + " perc: " + perc + " ba: " + ba + " ta: " + ta;
  
        if (ba < 30f || ta < 30f)
        {
          continue;
        }
      }
  
      ItemStack stack = info.getStack();
      
      int yp = y + 2 + index++ * (h);
      
      int ty = yp + 4;
      int tx = x + 24;
      
      renderItem.renderItemAndEffectIntoGUI(stack, x + 2, yp + 2);
      renderItem.renderItemOverlayIntoGUI(fontRenderer, stack, x + 2, yp, info.getCount() > 1 ? String.valueOf(info.getCount()) : "");
      
      String tot;
      
      if (drawTotal)
      {
        tot = "(" + info.getTotal() + ") ";// + ((now - info.getTime()) / 1000);
      }
      else
      {
        tot = "";
      }
      
      String name;
      
      if (drawName)
      {
        name = info.getName();
        if (name.length() > maxNameLength)
        {
          name = name.substring(0, maxNameLength);
        }
      }
      else
      {
        name = "";
      }
      
      String s = name + " " + tot;
  
      w =(int) (fontRenderer.getCharWidth('W') * (maxNameLength + tot.length()) + 16);
      
      fill(x + 2, yp, x + w, yp + h, ColorHelper.withAlpha(backgroundColor, (int) ba));
      drawStringRight(fontRenderer, s, x + w , ty, ColorHelper.withAlpha(textColor, (int) ta));
      
      //drawString(fontRenderer, debug, x + w + 4, ty, ColorHelper.fromRGB(255, 255, 255));
    }
  
    RenderHelper.enableStandardItemLighting();
  }
  
  private void drawStringRight(FontRenderer fontRenderer, String text, int right, int y, int color)
  {
    int w = fontRenderer.getStringWidth(text) + 6;
    int x = right - w;
    
    GlStateManager.disableLighting();
    GlStateManager.disableDepthTest();
    GlStateManager.disableBlend();
    drawString(fontRenderer, text, x, y, color);
    GlStateManager.enableLighting();
    GlStateManager.enableDepthTest();
    GlStateManager.enableBlend();
  }
  
//  private int getColor(float r, float g, float b)
//  {
//    return getColor(r, g, b, 1);
//  }
//
//  private int getColor(float r, float g, float b, float a)
//  {
//    int red = (int) (r * 255);
//    int green = (int) (g * 255);
//    int blue = (int) (b * 255);
//    int alpha = (int) (a * 255);
//
//    return (alpha << 24) | (red << 16) | (green << 8) | blue;
//  }
}
