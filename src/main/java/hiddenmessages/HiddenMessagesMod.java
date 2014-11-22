package hiddenmessages;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import hiddenmessages.common.CommonProxy;

@Mod(modid = HiddenMessagesMod.ID, name="Hidden Messages Mod", version = HiddenMessagesMod.VERSION)
public class HiddenMessagesMod
{
  public static final String ID = "hiddenmessagesmod";
  public static final String VERSION = "0.1.0";

  // The instance of your mod that Forge uses.
  @Mod.Instance("hiddenmessagesmod")
  public static HiddenMessagesMod instance;

  // Says where the client and server 'proxy' code is loaded.
  @SidedProxy(clientSide="hiddenmessages.clientside.ClientSideOnlyProxy", serverSide="hiddenmessages.serverside.DedicatedServerProxy")
  public static CommonProxy proxy;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit();
  }

  @EventHandler
  public void load(FMLInitializationEvent event) {
    proxy.load();
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit();
  }

  /**
   * Prepend the name with the mod ID, suitable for textures.
   * @param name
   * @return eg "speedytoolsmod:myblockname"
   */
  public static String prependModID(String name) {return ID + ":" + name;}

}
