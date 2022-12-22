package org.mcwonderland.uhc.menu.model;

import org.mcwonderland.uhc.menu.UHCMenuSection;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.config.ColorMenu;

public abstract class ColorPickerMenu extends ColorMenu {

    public ColorPickerMenu(Menu parent) {
        super(parent, UHCMenuSection.of("Color_Picker"));
    }

}
