package com.naxanria.itemgot;

import com.naxanria.itemgot.config.Config;
import com.naxanria.itemgot.util.ColorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIHud extends Gui
{
  private final Minecraft MC;
  private final LogHandler handler;
  private FontRenderer fontRenderer;
  private RenderItem renderItem;

  public GUIHud(Minecraft minecraft, LogHandler handler)
  {
    super();
  
    MC = minecraft;
    this.handler = handler;
    
    fontRenderer = MC.fontRenderer;
    renderItem = MC.getRenderItem();
  }
  
  @SubscribeEvent
  public void onRenderHud(RenderGameOverlayEvent.Post event)
  {
    if (event.getType() != RenderGameOverlayEvent.ElementType.CHAT)
    {
      return;
    }
    
    if (fontRenderer == null)
    {
      fontRenderer = MC.fontRenderer;
    }
    
    if (renderItem == null)
    {
      renderItem = MC.getRenderItem();
    }
    
    Config config = Config.getInstance();
    
    int x = config.getX();
    int y = config.getY();
    int w;
    
    int textSpacing = 2;
    int index = 0;
    int fh = fontRenderer.FONT_HEIGHT;
    int h = fh + textSpacing * 2;
    if (h < 16)
    {
      h = 16;
    }
    
    int bgA = config.getBgAlpha();
    int bgR = config.getBgRed();
    int bgG = config.getBgGreen();
    int bgB = config.getBgBlue();
    
    int txtA = 255;
    int txtR = config.getTextRed();
    int txtG = config.getTextGreen();
    int txtB = config.getTextBlue();
    
    long fullTime = config.getFadeDelay() * 1000;
    long fadeTime = config.getFadeTime() * 1000;
    
    long now = System.currentTimeMillis();
    
    boolean drawName = config.isDrawText();
    int maxNameLength = config.getTextLength();
    
    if (!drawName)
    {
      maxNameLength = 0;
    }
  
    w = fontRenderer.getCharWidth('W') * (maxNameLength + 6) + 16;
    
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
      
      String debug = "";
      
      float ba = bgA;
      float ta = txtA;
      
      if (diff > fullTime)
      {
        // adjust alphas
        diff -= fullTime;
        float perc = 1f - ((float)diff / fadeTime);
        
        
        
        ba *= perc;
        ta *= perc;
  
        debug = debug + " perc: " + perc + " ba: " + ba + " ta: " + ta;
  
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
      
      String tot = "(" + info.getTotal() + ") ";// + ((now - info.getTime()) / 1000);
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
      
      drawRect(x + 2, yp, x + w, yp + h, ColorHelper.fromRGB(bgR, bgG, bgB, (int) ba));
      drawStringRight(fontRenderer, s, x + w , ty, ColorHelper.fromRGB(txtR, txtG, txtB, (int) ta));
      
      drawString(fontRenderer, debug, x + w + 4, ty, ColorHelper.fromRGB(255, 255, 255));
    }
  
    RenderHelper.enableStandardItemLighting();
  }
  
  private void drawStringRight(FontRenderer fontRenderer, String text, int right, int y, int color)
  {
    int w = fontRenderer.getStringWidth(text);
    int x = right - w;
    GlStateManager.disableLighting();
    GlStateManager.disableDepth();
    GlStateManager.disableBlend();
    drawString(fontRenderer, text, x, y, color);
    GlStateManager.enableLighting();
    GlStateManager.enableDepth();
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
