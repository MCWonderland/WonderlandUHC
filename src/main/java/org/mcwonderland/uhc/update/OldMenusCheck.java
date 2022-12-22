package org.mcwonderland.uhc.update;

import org.mineacademy.fo.FileUtil;

import java.io.File;

public class OldMenusCheck implements Updater {

    @Override
    public void check(String oldVer, String newVer) {
        if (newVer.startsWith("3")) {
            moveOldMenuYaml();
        }
    }

    private void moveOldMenuYaml() {
        File file = FileUtil.getFile("menus.yml");

        if (file.exists())
            file.renameTo(FileUtil.getOrMakeFile("舊版文件備份/menus.yml"));
    }

}
