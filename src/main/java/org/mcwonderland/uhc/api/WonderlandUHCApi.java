package org.mcwonderland.uhc.api;

import org.mcwonderland.uhc.WonderlandUHC;
import org.bukkit.plugin.java.JavaPlugin;

public class WonderlandUHCApi {
    private WonderlandUHC wonderlandUhc = WonderlandUHC.getInstance();
    private JavaPlugin pluginUsingApi;

    public WonderlandUHCApi(JavaPlugin plugin) {
        this.pluginUsingApi = plugin;
    }
}
