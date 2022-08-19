package org.mcwonderland.uhc.util;

import org.bukkit.Bukkit;

public class Environment {
    public static boolean isUnitTesting() {
        return Bukkit.getServer().getName().equals("ServerMock");
    }
}
