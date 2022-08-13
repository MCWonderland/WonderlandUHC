package org.mcwonderland.uhc;

import lombok.Getter;
import org.mcwonderland.uhc.settings.Messages;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.Valid;

@Getter
public enum Dependency {
    DISCORD_SRV("DiscordSRV", "https://www.spigotmc.org/resources/discordsrv.18494/"),
    WORLD_BORDER("WorldBorder", MinecraftVersion.atLeast(MinecraftVersion.V.v1_13) ? "https://www.spigotmc.org/resources/worldborder.60905" : "https://dev.bukkit.org/projects/worldborder"),
    PACKET_LISTENER_API("PacketListenerApi", "https://www.spigotmc.org/resources/api-packetlistenerapi.2930/"),
    CUSTOM_ORE_GENERATOR("custom-ore-generator", "https://www.spigotmc.org/resources/custom-ore-generator-%E3%80%8E1-8-1-15-2%E3%80%8F.64339/");

    private final String pluginName;
    private final String downloadUrl;

    Dependency(String pluginName, String downloadUrl) {
        this.pluginName = pluginName;
        this.downloadUrl = downloadUrl;
    }

    public void check() {
        checkExist(Messages.Dependency.REQUIRE_DEPENDENCY);
    }

    public void checkSoft() {
        checkExist(Messages.Dependency.REQUIRE_SOFT_DEPENDENCY);
    }

    public String getVersion() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        return plugin.getDescription().getVersion();
    }

    private void checkExist(String falseMsg) {
        Valid.checkBoolean(Common.doesPluginExist(pluginName),
                falseMsg.replace("{plugin}", pluginName)
                        .replace("{url}", downloadUrl));
    }

    public boolean isHooked() {
        return Common.doesPluginExist(pluginName);
    }
}
