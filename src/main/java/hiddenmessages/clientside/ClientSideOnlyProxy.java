package hiddenmessages.clientside;

import hiddenmessages.common.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * CombinedClientProxy is used to set up the mod and start it running when installed on a standalone client.
 *   It should not contain any code necessary for proper operation on a DedicatedServer.  Code required for both
 *   CombinedClient and dedicated server should go into CommonProxy
 */
public class ClientSideOnlyProxy extends CommonProxy
{
  public static CreativeTabs tabHiddenMessages;

  /**
   * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
   */
  @Override
  public void preInit()
  {
    tabHiddenMessages = new CreativeTabs("tabhiddenmessages") {
      @Override
      public ItemStack getIconItemStack() {
        return new ItemStack(CommonProxy.itemHiddenMessagesSign, 1, 0);
      }
      @Override
      public Item getTabIconItem() {return CommonProxy.itemHiddenMessagesSign;}
    };
    super.preInit();

  }

  /**
   * Do your mod setup. Build whatever data structures you care about. Register recipes,
   * send FMLInterModComms messages to other mods.
   */
  @Override
  public void load()
  {
    super.load();
  }

  /**
   * Handle interaction with other mods, complete your setup based on this.
   */
  @Override
  public void postInit()
  {
    super.postInit();
  }

}