package org.mcwonderland.uhc;

import me.lulu.datounms.DaTouNMS;
import me.lulu.datounms.UnSupportedNmsException;
import org.mineacademy.fo.exception.FoException;

public class NmsLoader {

    public static void initNms(WonderlandUHC plugin) {
        try {
            DaTouNMS.setup(plugin);
        } catch (UnSupportedNmsException e) {
            throw new FoException("&cUnsupported version, this plugin only support 1.8 ~ 1.16");
        }
    }

}
