package org.mcwonderland.uhc;

import lombok.SneakyThrows;
import org.mcwonderland.uhc.settings.Messages;
import org.mcwonderland.uhc.util.VersionComparator;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.MinecraftVersion;
import org.mineacademy.fo.exception.FoException;
import org.mineacademy.fo.model.SimpleReplacer;

public class DependencyChecker {

    public static void checkDepends() {
        Dependency.WORLD_BORDER.check();
        Dependency.PACKET_LISTENER_API.check();
        checkWorldBorderVer();

        if (Dependency.CUSTOM_ORE_GENERATOR.isHooked())
            checkCustomOreGenerator();
    }



    private static void checkCustomOreGenerator() {
        try {
            Class.forName("de.derfrzocker.custom.ore.generator.api.OreSettingContainer");
        } catch (ClassNotFoundException e) {
            throw new FoException("&cCustomOreGenerator 版本過舊，請至 &f"
                    + Dependency.CUSTOM_ORE_GENERATOR.getDownloadUrl() +
                    " &c下載最新版本！");
        }
    }

    @SneakyThrows
    private static void checkWorldBorderVer() {
        Dependency worldBorder = Dependency.WORLD_BORDER;
        boolean newer = MinecraftVersion.atLeast(MinecraftVersion.V.v1_13);
        boolean usingNewerWb = VersionComparator.isNewerThan(worldBorder.getVersion(), "1.8.7");

        if (newer && !usingNewerWb) {
            Common.log(new SimpleReplacer(Messages.Dependency.USING_OLD_WORLD_BORDER_IN_NEW_VERSION)
                    .replace("{link}", worldBorder.getDownloadUrl())
                    .toArray());
            Thread.sleep(3 * 1000);
            return;
        }

        if (!newer && usingNewerWb) {
            Thread.sleep(3 * 1000);
            throw new FoException(Messages.Dependency.CHANGE_TO_OLDER_WORLD_BORDER_VERSION
                    .replace("{link}", worldBorder.getDownloadUrl()));
        }
    }

}
