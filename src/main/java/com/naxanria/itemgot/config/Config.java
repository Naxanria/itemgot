package com.naxanria.itemgot.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naxanria.itemgot.ItemGotMod;
import com.naxanria.itemgot.config.annotation.Category;
import com.naxanria.itemgot.config.annotation.Range;
import com.naxanria.itemgot.config.annotation.ShowOnly;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;

import static com.naxanria.itemgot.ItemGotMod.MODID;

public class Config
{
  private static Config instance;
  private static Config defaultConfig;
  
  private int x;
  private int y;
  
  @Range(min = 2, max = 20)
  private int logSize;
  
  @Category(name = "text")
  private boolean drawText;
  
  @Category(name = "text")
  @ShowOnly(field = "drawText")
  @Range(min = 2, max = 20)
  private int textLength;
  @Category(name = "text")
  @Range(min = 0, max = 255)
  private int textRed;
  @Category(name = "text")
  @Range(min = 0, max = 255)
  private int textGreen;
  @Category(name = "text")
  @Range(min = 0, max = 255)
  private int textBlue;
  
  @Range(min = 0, max = 255)
  private int bgRed;
  @Range(min = 0, max = 255)
  private int bgGreen;
  @Range(min = 0, max = 255)
  private int bgBlue;
  @Range(min = 0, max = 255)
  private int bgAlpha;
  
  @Range(min = 2, max = 6)
  private int refreshTime;
  @Range(max = 20)
  private int fadeTime;
  @Range(max = 20)
  private int fadeDelay;
  
  public Config()
  {
    x = 10;
    y = 10;
    logSize = 8;
    drawText = true;
    textLength = 20;

    textRed = 255;
    textGreen = 255;
    textBlue = 255;
    
    bgRed = 0;
    bgGreen = 0;
    bgBlue = 0;
    bgAlpha = 90;
    
    refreshTime = 5;
    fadeTime = 10;
    fadeDelay = 5;
  }
  
  public Config(int x, int y, int logSize, int textRed, int textGreen, int textBlue, int bgRed, int bgGreen, int bgBlue, int bgAlpha, boolean drawText, int textLength, int refreshTime, int fadeTime, int fadeDelay)
  {
    this.x = x;
    this.y = y;
    this.logSize = logSize;
    this.textRed = textRed;
    this.textGreen = textGreen;
    this.textBlue = textBlue;
    this.bgRed = bgRed;
    this.bgGreen = bgGreen;
    this.bgBlue = bgBlue;
    this.bgAlpha = bgAlpha;
    this.drawText = drawText;
    this.textLength = textLength;
    this.refreshTime = refreshTime;
    this.fadeTime = fadeTime;
    this.fadeDelay = fadeDelay;
  }
  
  public int getX()
  {
    return x;
  }
  
  public int getY()
  {
    return y;
  }
  
  public int getLogSize()
  {
    return logSize;
  }
  
  public boolean isDrawText()
  {
    return drawText;
  }
  
  public int getTextLength()
  {
    return textLength;
  }
  
  public int getTextRed()
  {
    return textRed;
  }
  
  public int getTextGreen()
  {
    return textGreen;
  }
  
  public int getTextBlue()
  {
    return textBlue;
  }
  
  public int getBgRed()
  {
    return bgRed;
  }
  
  public int getBgGreen()
  {
    return bgGreen;
  }
  
  public int getBgBlue()
  {
    return bgBlue;
  }
  
  public int getBgAlpha()
  {
    return bgAlpha;
  }
  
  public int getRefreshTime()
  {
    return refreshTime;
  }
  
  public int getFadeTime()
  {
    return fadeTime;
  }
  
  public int getFadeDelay()
  {
    return fadeDelay;
  }
  
  public static Config getInstance()
  {
    return instance;
  }
  
  public static Config getDefault()
  {
    if (defaultConfig == null)
    {
      defaultConfig = new Config();
    }
    
    return defaultConfig;
  }
  
  public static void loadConfig(File configFile)
  {
    InputStream configStream = null;
    String configString = "";
    try
    {
      // try load config file
      if (configFile.exists())
      {
        configStream = new FileInputStream(configFile);
        configString = IOUtils.toString(configStream, Charset.defaultCharset());
      } else
      {
        // try create new config file
        File parent = configFile.getParentFile();
        
        File temp = new File(parent, MODID + ".cfg");
  
        if (temp.exists())
        {
          configStream = new FileInputStream(temp);
          configString = IOUtils.toString(configStream, Charset.defaultCharset());
        } else
        {
          configString = "{}";
        }
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        if (configStream != null)
        {
          configStream.close();
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  
    Gson gson = new Gson();
    instance = gson.fromJson(configString, Config.class);
  }
  
  public static void saveConfig(File configFile)
  {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String configString = gson.toJson(instance);
    
    FileOutputStream stream = null;
    
    try
    {
      if (!configFile.exists())
      {
        ItemGotMod.logger.info("Creating new config file: " + configFile.getCanonicalPath());
        
        // check if folder exists
        
        File dir = configFile.getParentFile();
        
        //File dir = new File(path);
        if (!dir.exists())
        {
          dir.mkdir();
        }
        // create file
        
        configFile.createNewFile();
      }
      
      stream = new FileOutputStream(configFile);
      IOUtils.write(configString, stream, Charset.defaultCharset());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    finally
    {
      try
      {
        if (stream != null)
        {
          stream.close();
        }
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
  }
}

