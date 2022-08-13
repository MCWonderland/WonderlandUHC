package org.mcwonderland.uhc.menu;

import org.mcwonderland.uhc.settings.UHCFiles;
import org.mineacademy.fo.menu.config.MenuSection;

public class UHCMenuSection extends MenuSection {

    private UHCMenuSection(String sectionPrefix) {
        super(UHCFiles.MENUS, sectionPrefix);
    }

    public static UHCMenuSection of(String sectionPrefix) {
        return new UHCMenuSection(sectionPrefix);
    }
}
