//package com.naxanria.itemgot.config;
//
//import com.naxanria.itemgot.ItemGotMod;
//import com.naxanria.itemgot.config.annotation.Category;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraftforge.fml.client.config.GuiConfig;
//import net.minecraftforge.fml.client.config.GuiConfigEntries;
//import net.minecraftforge.fml.client.config.IConfigElement;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class ItemGotGuiConfig extends GuiConfig
//{
//  private GuiConfigEntries.IConfigEntry bgRed, bgGreen, bgBlue, bgAlpha;
//
//  public ItemGotGuiConfig(GuiScreen parentScreen)
//  {
//    super(parentScreen, getConfigElements(), ItemGotMod.MODID, false, false, "ItemGotConfig");
//  }
//
//  @Override
//  public void initGui()
//  {
//    super.initGui();
//
//    entryList.listEntries
//      .forEach(
//        ce ->
//        {
//          switch (ce.getName())
//          {
//            case "bgRed":
//              bgRed = ce;
//              break;
//
//            case "bgGreen":
//              bgGreen = ce;
//              break;
//
//            case "bgBlue":
//              bgBlue = ce;
//              break;
//
//            case "bgAlpha":
//              bgAlpha = ce;
//              break;
//
//            default:
//              break;
//          }
//        }
//      );
//  }
//
//  private static List<IConfigElement> getConfigElements()
//  {
//    List<IConfigElement> elements = new ArrayList<>();
//
//    List<IConfigElement> toAdd = new ArrayList<>();
//    HashMap<String, List<IConfigElement>> categories = new HashMap<>();
//
//    final Config def = Config.getDefault();
//
//    Field[] fields = Config.class.getDeclaredFields();
//
//    for (Field field :
//      fields)
//    {
//      if (Modifier.isStatic(field.getModifiers()))
//      {
//        continue;
//      }
//
//      Object obj = null;
//      try
//      {
//        field.setAccessible(true);
//        obj = field.get(def);
//      } catch (IllegalAccessException e)
//      {
//        e.printStackTrace();
//        continue;
//      }
//
//      Category category = field.getAnnotation(Category.class);
//      if (category != null)
//      {
//        String catName = category.name();
//        List<IConfigElement> catList = categories.computeIfAbsent(catName, k -> new ArrayList<>());
//
//        catList.add(new ReflectionConfigElement(field, obj));
//      }
//      else
//      {
//        toAdd.add(new ReflectionConfigElement(field, obj));
//      }
//    }
//
//    for (String name :
//      categories.keySet())
//    {
//      CategoryConfigElement element = new CategoryConfigElement(name, categories.get(name));
//      elements.add(element);
//    }
//
//    elements.addAll(toAdd);
//
//
//    return elements;
//  }
//}
