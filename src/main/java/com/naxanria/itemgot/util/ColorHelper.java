package com.naxanria.itemgot.util;

public class ColorHelper
{
  public static int fromRGB(int r, int g, int b)
  {
    return fromRGB(r, g, b, 255);
  }
  
  public static int fromRGB(int r, int g, int b, int a)
  {
    return (a << 24) | (r << 16) | (g << 8) | b;
  }
  
  public static int fromRGB(float r, float g, float b)
  {
    return fromRGB(r, g, b, 1);
  }
  
  public static int fromRGB(float r, float g, float b, float a)
  {
    return fromRGB((int) (r * 255), (int) (g * 255), (int) (b * 255), (int) (a * 255));
  }
  
  public static int withAlpha(int color, int alpha)
  {
    int[] cols = getARGB(color);
    return fromRGB(cols[1], cols[2], cols[3], alpha);
  }
  
  public static int getRed(int color)
  {
    return (color >> 16) & 0xFF;
  }
  
  public static int getGreen(int color)
  {
    return (color >> 8) & 0xFF;
  }
  
  public static int getBlue(int color)
  {
    return (color) & 0xFF;
  }
  
  public static int getAlpha(int color)
  {
    return (color >> 24) & 0xFF;
  }
  
  public static int[] getARGB(int color)
  {
    return new int[]
    {
      getAlpha(color),
      getRed(color),
      getGreen(color),
      getBlue(color)
    };
  }
}

