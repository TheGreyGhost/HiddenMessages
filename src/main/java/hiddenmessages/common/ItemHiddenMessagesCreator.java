package hiddenmessages.common;

import hiddenmessages.HiddenMessagesMod;
import hiddenmessages.clientside.ClientSideOnlyProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemHiddenMessagesCreator extends Item
{
  public static final String NAME = "HiddenMessagesCreator";
  public ItemHiddenMessagesCreator() {
    super();
    setCreativeTab(ClientSideOnlyProxy.tabHiddenMessages);
    setMaxStackSize(1);
    setUnlocalizedName(NAME);
    setFull3D();                              // setting this flag causes the sceptre to render vertically in 3rd person view, like a pickaxe
    setTextureName(HiddenMessagesMod.prependModID("hiddenmessagessign"));
  }

  /**
   * allows items to add custom lines of information to the mouseover description
   */
  @Override
  public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List textList, boolean useAdvancedItemTooltips)
  {
    textList.add("Left click: change text");
    textList.add("Right click: place text");
  }
}