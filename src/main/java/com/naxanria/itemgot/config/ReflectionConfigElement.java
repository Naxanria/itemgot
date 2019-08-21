//package com.naxanria.itemgot.config;
//
//import com.naxanria.itemgot.config.annotation.Range;
//import com.naxanria.itemgot.config.annotation.ShowOnly;
//import net.minecraftforge.fml.client.config.ConfigGuiType;
//import net.minecraftforge.fml.client.config.GuiConfigEntries;
//import net.minecraftforge.fml.client.config.GuiEditArrayEntries;
//import net.minecraftforge.fml.client.config.IConfigElement;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.List;
//import java.util.regex.Pattern;
//
//import static com.naxanria.itemgot.ItemGotMod.MODID;
//
//public class ReflectionConfigElement implements IConfigElement
//{
//  private static final HashMap<String, Class<? extends GuiConfigEntries.IConfigEntry>> lookupEntry = new HashMap<String, Class<? extends GuiConfigEntries.IConfigEntry>>()
//  {
//    {
//      put("boolean", GuiConfigEntries.BooleanEntry.class);
//      put("String", GuiConfigEntries.StringEntry.class);
//      put("int", GuiConfigEntries.IntegerEntry.class);
//    }
//  };
//
//  private static final HashMap<String, ConfigGuiType> lookup = new HashMap<String, ConfigGuiType>()
//  {
//    {
//      put("boolean", ConfigGuiType.BOOLEAN);
//      put("String", ConfigGuiType.STRING);
//      put("int", ConfigGuiType.INTEGER);
//      put("Color", ConfigGuiType.COLOR);
//    }
//  };
//
//  public static String buildName(String baseName)
//  {
//    return BASE_KEY + baseName;
//  }
//
//  private static final String BASE_KEY = MODID + ".config.";
//
//  private final Field field;
//  private final Object defValue;
//
//  public ReflectionConfigElement(Field field, Object defValue)
//  {
//    this.field = field;
//    this.defValue = defValue;
//  }
//
//  @Override
//  public boolean isProperty()
//  {
//    return true;
//  }
//
//  @Override
//  public Class<? extends GuiConfigEntries.IConfigEntry> getConfigEntryClass()
//  {
//    Range range = field.getAnnotation(Range.class);
//
//    if (range != null)
//    {
//      return GuiConfigEntries.NumberSliderEntry.class;
//    }
//
//    return  null; //lookupEntry.get(field.getType().toString());
//  }
//
//  @Override
//  public Class<? extends GuiEditArrayEntries.IArrayEntry> getArrayEntryClass()
//  {
//    return null;
//  }
//
//  @Override
//  public String getName()
//  {
//    return field.getName();
//  }
//
//  @Override
//  public String getQualifiedName()
//  {
//    return BASE_KEY + getName();
//  }
//
//  @Override
//  public String getLanguageKey()
//  {
//    return getQualifiedName().toLowerCase();
//  }
//
//  @Override
//  public String getComment()
//  {
//    return BASE_KEY + "comment." + getName().toLowerCase();
//  }
//
//  @Override
//  public List<IConfigElement> getChildElements()
//  {
//    return null;
//  }
//
//  @Override
//  public ConfigGuiType getType()
//  {
//    return lookup.getOrDefault(field.getType().toString(), ConfigGuiType.STRING);
//  }
//
//  @Override
//  public boolean isList()
//  {
//    return false;
//  }
//
//  @Override
//  public boolean isListLengthFixed()
//  {
//    return false;
//  }
//
//  @Override
//  public int getMaxListLength()
//  {
//    return 0;
//  }
//
//  @Override
//  public boolean isDefault()
//  {
//    return defValue != null && defValue.equals(get());
//  }
//
//  @Override
//  public Object getDefault()
//  {
//    return defValue;
//  }
//
//  @Override
//  public Object[] getDefaults()
//  {
//    return new Object[0];
//  }
//
//  @Override
//  public void setToDefault()
//  {
//
//  }
//
//  @Override
//  public boolean requiresWorldRestart()
//  {
//    return false;
//  }
//
//  @Override
//  public boolean showInGui()
//  {
//    ShowOnly showOnly = field.getAnnotation(ShowOnly.class);
//
//    if (showOnly != null)
//    {
//      String fieldName = showOnly.field();
//      //ItemGotMod.logger.info("Looking for field: " + fieldName);
//      try
//      {
//        Field other = (Config.class.getDeclaredField(fieldName));
//        other.setAccessible(true);
//        return other.getBoolean(Config.getInstance());
//
//      } catch (NoSuchFieldException | IllegalAccessException e)
//      {
//        e.printStackTrace();
//      }
//
//    }
//
//    return true;
//  }
//
//  @Override
//  public boolean requiresMcRestart()
//  {
//    return false;
//  }
//
//  @Override
//  public Object get()
//  {
//    try
//    {
//      field.setAccessible(true);
//      return field.get(Config.getInstance());
//    }
//    catch (IllegalAccessException e)
//    {
//      e.printStackTrace();
//    }
//
//    return null;
//  }
//
//  @Override
//  public Object[] getList()
//  {
//    return new Object[0];
//  }
//
//  @Override
//  public void set(Object value)
//  {
//    try
//    {
//      field.setAccessible(true);
//      field.set(Config.getInstance(), value);
//    }
//
//    catch (IllegalAccessException e)
//    {
//      e.printStackTrace();
//    }
//  }
//
//  @Override
//  public void set(Object[] aVal)
//  {
//
//  }
//
//  @Override
//  public String[] getValidValues()
//  {
//    return new String[0];
//  }
//
//  @Override
//  public Object getMinValue()
//  {
//    Range range = field.getAnnotation(Range.class);
//    if (range != null)
//    {
//      return range.min();
//    }
//
//    return 0;
//  }
//
//  @Override
//  public Object getMaxValue()
//  {
//    Range range = field.getAnnotation(Range.class);
//    if (range != null)
//    {
//      return range.max();
//    }
//
//    return 500;
//  }
//
//  @Override
//  public Pattern getValidationPattern()
//  {
//    return null;
//  }
//}
