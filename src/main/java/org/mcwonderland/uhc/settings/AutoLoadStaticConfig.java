package org.mcwonderland.uhc.settings;

import org.mcwonderland.uhc.util.YamlConfigLoader;
import org.mineacademy.fo.settings.YamlStaticConfig;

public abstract class AutoLoadStaticConfig extends YamlStaticConfig {
    @Override
    protected void preLoad() {
        super.preLoad();
        YamlConfigLoader.load(this.getClass());
    }
}
