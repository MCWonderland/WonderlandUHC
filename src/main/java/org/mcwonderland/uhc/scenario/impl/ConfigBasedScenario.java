package org.mcwonderland.uhc.scenario.impl;

import com.google.common.collect.Lists;
import org.mcwonderland.uhc.scenario.ScenarioName;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.model.SimpleReplacer;
import org.mineacademy.fo.remain.CompMaterial;

import java.util.List;

public abstract class ConfigBasedScenario extends AbstractScenario {

    public ConfigBasedScenario(ScenarioName name) {
        super(name.capitalize(), CompMaterial.AIR.toItem());
    }

    @Override
    protected final void onReload() {
        ScenarioConfig config = getNewConfig();

        config.loadFieldValues();
        onConfigReload();

        setIcon(ItemCreator.of(
                config.getMaterial(),
                config.getFancyName(),
                getDesc(config.getDescription())
        ).make());
    }

    private List<String> getDesc(List<String> description) {
        try {
            return replaceLore(description).buildList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Lists.newArrayList();
        }
    }


    protected void onConfigReload() {

    }

    protected SimpleReplacer replaceLore(List<String> description) {
        return new SimpleReplacer(description);
    }

    private ScenarioConfig getNewConfig() {
        return new ScenarioConfig(this);
    }
}
