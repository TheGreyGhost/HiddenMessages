package hiddenmessages.serverside;


import cpw.mods.fml.server.FMLServerHandler;
import hiddenmessages.common.CommonProxy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * DedicatedServerProxy is used to set up the mod and start it running when installed on a dedicated server.
 *   It should not contain (or refer to) any client-side code at all, since the dedicated server has no client-side code.
 */

public class DedicatedServerProxy extends CommonProxy
{
  /**
   * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
   */
  @Override
  public void preInit()
  {
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
