package org.mcwonderland.uhc.hook;

import de.derfrzocker.custom.ore.generator.api.CustomOreGeneratorService;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class CustomOreGeneratorHook {

    public CustomOreGeneratorService getOreService() {
        return Bukkit.getServicesManager().getRegistration(CustomOreGeneratorService.class).getProvider();
    }

}
