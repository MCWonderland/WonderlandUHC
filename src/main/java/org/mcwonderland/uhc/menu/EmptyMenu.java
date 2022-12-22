package org.mcwonderland.uhc.menu;

import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.config.ConfigMenu;

public class EmptyMenu extends ConfigMenu {

    public EmptyMenu(Menu parent) {
        super(UHCMenuSection.of(""), parent);
    }


}
