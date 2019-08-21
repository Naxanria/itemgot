package com.naxanria.itemgot.config.gui;

import com.naxanria.itemgot.config.ConfigCategoryNode;
import com.naxanria.itemgot.config.ItemGotConfig;
import com.naxanria.itemgot.gui.ScreenBase;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigGui extends ScreenBase
{
  private List<ConfigGuiEntry<?, ?>> entries = new ArrayList<>();
  private List<String> keys = new ArrayList<>();
  private ForgeConfigSpec spec;
  
  private GuiTooltip tooltip = null;
  private ConfigGuiEntry<?, ?> lastEntry;
  
  private Map<ConfigCategoryNode, CategoryWidget> widgetMap = new HashMap<>();
  private ConfigCategoryNode categories = ConfigCategoryNode.create();
  private ConfigCategoryNode currentCategory;
  
  private List<String> subCategories;
  
  private CategoryWidget categoryWidget;
  private List<GuiButtonExt> subCategoryButtons = new ArrayList<>();
  private GuiButtonExt saveButton;
  private GuiButtonExt cancelButton;
  
  
  public ConfigGui(Screen parent)
  {
    super(new StringTextComponent("Config"), parent);
  
    spec = ItemGotConfig.getClientSpec();
    
    currentCategory = categories.push("General");
    addEntry(ItemGotConfig.clientConfig.x)
      .tooltip.addInfo("The x position of the list").range(0, 32000).def(10);
    addEntry(ItemGotConfig.clientConfig.y)
      .tooltip.addInfo("The y position of the list").range(0, 32000).def(10);
    addEntry(ItemGotConfig.clientConfig.updateFrequency)
      .tooltip.addInfo("How often to check for new items (in ticks)")
      .range(1, 80).def(10);
    addEntry(ItemGotConfig.clientConfig.logSize)
      .tooltip.addInfo("The size of the log").range(2, 20).def(8);
    
    currentCategory = currentCategory.push("Look And Feel");
    
    addEntry(ItemGotConfig.clientConfig.textLength)
      .tooltip.addInfo("The max length of the item name").range(4, 32).def(10);
    addEntry(ItemGotConfig.clientConfig.drawText)
      .tooltip.addInfo("Draw the name of the item").def(false);
    addEntry(ItemGotConfig.clientConfig.drawTotal)
      .tooltip.addInfo("Show the total amount of the item").def(true);
    addEntry(ItemGotConfig.clientConfig.textColor)
      .tooltip.addInfo("The color of the text").def("-1 (white)");
    addEntry(ItemGotConfig.clientConfig.backgroundColor)
      .tooltip.addInfo("The background color").def("0 (black, no alpha)");
    
    currentCategory = currentCategory.pop().push("Timings");
    
    addEntry(ItemGotConfig.clientConfig.refreshTime)
      .tooltip.addInfo("The time in seconds that the picked up item will be refreshed")
      .range(2, 10).def(5);
    addEntry(ItemGotConfig.clientConfig.fadeTime)
      .tooltip.addInfo("The time the item fades out (in seconds)")
      .range(1, 30).def(10);
    addEntry(ItemGotConfig.clientConfig.fadeDelay)
      .tooltip.addInfo("The time the item is shown before it fades out")
      .range(0, 20).def(5);
    
    currentCategory = currentCategory.getTop();
    
    String s = I18n.format("itemgot.gui.save");
    int w = font.getStringWidth(s);
    saveButton = new GuiButtonExt(8, 0, w + 8, 20, s, this::save);
    
    s = I18n.format("itemgot.gui.cancel");
    w = font.getStringWidth(s);
    cancelButton = new GuiButtonExt(8 + saveButton.getWidth(), 0, w + 8, 20, s, this::cancel);
    
    setupCategory();
  }
  
  @Override
  public void init()
  {
    windowWidth = minecraft.mainWindow.getScaledWidth();
    windowHeight = minecraft.mainWindow.getScaledHeight();

    children.clear();
  
    int x = 8;
    for (String subCat : subCategories)
    {
      if (subCat.equals("Hidden"))
      {
        continue;
      }
      
      int w = font.getStringWidth(subCat) + 8;
      GuiButtonExt subButton = new GuiButtonExt(x, 22, w, 20, subCat, this::subCat);
      x += w + 1;
      subCategoryButtons.add(subButton);
    }
    
    children.addAll(subCategoryButtons);
    children.addAll(entries);
    
    if (categoryWidget != null)
    {
      children.add(categoryWidget);
    }
    
    saveButton.y = windowHeight - 22 + 2;
    cancelButton.y = saveButton.y;
    
    children.add(saveButton);
    children.add(cancelButton);
  }
  
  private void subCat(Button b)
  {
    setNode(currentCategory.getChild(b.getMessage()));
  }
  
  public void setupCategory()
  {
    entries.clear();
    subCategoryButtons.clear();
    
    subCategories = currentCategory.getChildren();
    entries.addAll(currentCategory.getEntries());
    
    if (!widgetMap.containsKey(currentCategory))
    {
      categoryWidget = new CategoryWidget(8, 2, currentCategory, this);
      widgetMap.put(currentCategory, categoryWidget);
    }
    else
    {
      categoryWidget = widgetMap.get(currentCategory);
    }
    
    init();
  }
  
  protected ConfigGui addEntry(ForgeConfigSpec.ConfigValue<String> var)
  {
    return addEntry(new ConfigGuiEntry<>(spec, var));
  }
  
  protected ConfigGui addEntry(ForgeConfigSpec.IntValue var)
  {
    return addEntry(new IntegerConfigGuiEntry(spec, var));
  }
  
  protected ConfigGui addEntry(ForgeConfigSpec.BooleanValue var)
  {
    return addEntry(new BooleanConfigGuiEntry(spec, var));
  }
  
  protected <T extends Enum<T>> ConfigGui addEntry(ForgeConfigSpec.EnumValue<T> var)
  {
    return addEntry(new EnumConfigGuiEntry<>(spec, var));
  }
  
  protected ConfigGui addEntry(ConfigGuiEntry<?, ?> entry)
  {
    lastEntry = entry;
    
    currentCategory.add(entry);
    tooltip = entry.tooltip;
    
    return this;
  }
  
  @Override
  public void renderBackground()
  {
    renderDirtBackground(0);
  }
  
  @Override
  public void render(int mouseX, int mouseY, float partialTicks)
  {
    this.mouseX = mouseX;
    this.mouseY = mouseY;
    
    renderBackground();
    
    renderEntries();
    renderTop();
    renderBottom();
    
    renderForeground();
  }
  
  private void renderEntries()
  {
    int x = 10;
    int y = 48;
    int width = windowWidth - x - 20;
    int scroll = 0;
    int spacing = 2;
  
    int totHeight = 0;
    
    fill(0, 45, windowWidth, windowHeight - 22, 0xaa000000);
  
    tooltip = null;
    
    for (ConfigGuiEntry<?, ?> entry :
      entries)
    {
      entry.width = width;
      entry.setPosition(x, y);
      int h = entry.height;
      totHeight += h + spacing;
    
      y += h + spacing;

      entry.render(mouseX, mouseY, 0);
  
      if (tooltip == null)
      {
        tooltip = entry.getTooltip();
        if (tooltip != null)
        {
          tooltip.x = entry.x;
          tooltip.y = entry.y - tooltip.height - 2;
        }
      }
    }
  }
  
  private void renderBottom()
  {
    int h = 22;
//    fill(0, windowHeight - h, windowWidth, windowHeight, 0x66373737);
    
    saveButton.renderButton(mouseX, mouseY, 0);
    cancelButton.renderButton(mouseX, mouseY, 0);
  }
  
  private void renderTop()
  {
//    fill(0, 0, windowWidth, 45, 0x66373737);
    
    if (categoryWidget != null)
    {
      categoryWidget.render(mouseX, mouseY, 0);
    }
    
    for (GuiButtonExt button : subCategoryButtons)
    {
      button.renderButton(mouseX, mouseY, 0);
    }
  }
  
  @Override
  public void renderPreChildren()
  {
    tooltip = null;
  }
  
  @Override
  public void renderForeground()
  {
    if (tooltip != null)
    {
      tooltip.render(tooltip.x, tooltip.y);
    }
  }
  
  public void setNode(ConfigCategoryNode node)
  {
    this.currentCategory = node;
    setupCategory();
    
  }
  
  private void save(Button button)
  {
    entries.forEach(ConfigGuiEntry::save);
    
    onClose();
  }
  
  private void cancel(Button button)
  {
    onClose();
  }
  
  public static class Builder
  {
    private final Screen parentScreen;
    private final ConfigCategoryNode categories = ConfigCategoryNode.create();
  
    public Builder(Screen parentScreen)
    {
      this.parentScreen = parentScreen;
    }
  
    public static Builder create(Screen parenTScreen)
    {
      return new Builder(parenTScreen);
    }
    
    public ConfigGui build()
    {
      return new ConfigGui(parentScreen);
    }
  }
}
