package hiddenmessages.common;

import hiddenmessages.HiddenMessages;
import hiddenmessages.clientside.ClientSideOnlyProxy;
import hiddenmessages.serverside.CreateBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class ItemHiddenMessagesCreator extends Item
{
  public static final String NAME = "hiddenmessagescreator";
  public ItemHiddenMessagesCreator() {
    super();
    setCreativeTab(ClientSideOnlyProxy.tabHiddenMessages);
    setMaxStackSize(1);
    setUnlocalizedName(NAME);
    setFull3D();                              // setting this flag causes the item to render vertically in 3rd person view, like a pickaxe
    setTextureName(HiddenMessages.prependModID("hiddenmessagessign"));
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

  @Override
  public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
  {
    if (!world.isRemote) {
      Vec3 playerEyePosition = Vec3.createVectorHelper(entityPlayer.posX, entityPlayer.posY + (entityPlayer.getEyeHeight() - entityPlayer.getDefaultEyeHeight()), entityPlayer.posZ);
      Vec3 playerLookDirection = entityPlayer.getLookVec();
      CreateBlocks createBlocks = new CreateBlocks();
      double distanceToCentre = 32;
      double radius = 16;
      createBlocks.placeBlocks(world, playerEyePosition, playerLookDirection, distanceToCentre, radius);
    }
    return itemStack;
  }
}