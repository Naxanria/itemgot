//package com.naxanria.itemgot.config;
//
//import net.minecraftforge.fml.clientConfig.clientConfig.ConfigGuiType;
//import net.minecraftforge.fml.clientConfig.clientConfig.GuiConfigEntries;
//import net.minecraftforge.fml.clientConfig.clientConfig.GuiEditArrayEntries;
//import net.minecraftforge.fml.clientConfig.clientConfig.IConfigElement;
//
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class CategoryConfigElement implements IConfigElement
//{
//  public final String name;
//  private List<IConfigElement> children;
//
//  public CategoryConfigElement(String name, List<IConfigElement> children)
//  {
//    this.name = name;
//    this.children = children;
//  }
//
//  @Override
//  public boolean isProperty()
//  {
//    return false;
//  }
//
//  @Override
//  public Class<? extends GuiConfigEntries.IConfigEntry> getConfigEntryClass()
//  {
//    return null;
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
//    return name;
//  }
//
//  @Override
//  public String getQualifiedName()
//  {
//    return name;
//  }
//
//  @Override
//  public String getLanguageKey()
//  {
//    return name;
//  }
//
//  @Override
//  public String getComment()
//  {
//    return name;
//  }
//
//  @Override
//  public List<IConfigElement> getChildElements()
//  {
//    return children;
//  }
//
//  @Override
//  public ConfigGuiType getType()
//  {
//    return ConfigGuiType.CONFIG_CATEGORY;
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
//    return false;
//  }
//
//  @Override
//  public Object getDefault()
//  {
//    return null;
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
//
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
//    return null;
//  }
//
//  @Override
//  public Object getMaxValue()
//  {
//    return null;
//  }
//
//  @Override
//  public Pattern getValidationPattern()
//  {
//    return null;
//  }
//}
