package org.mcwonderland.uhc.scenario.impl;

import org.mcwonderland.uhc.scenario.annotation.FilePath;
import org.mcwonderland.uhc.settings.UHCFiles;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.model.SimpleSound;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

public class ScenarioConfig extends YamlConfig {

    private ConfigBasedScenario scenario;

    public ScenarioConfig(ConfigBasedScenario scenario) {
        setPathPrefix(scenario.getName());
        this.scenario = scenario;
        loadConfiguration(UHCFiles.SCENARIOS);
    }

    public CompMaterial getMaterial() {
        return getMaterial("Type");
    }

    public String getFancyName() {
        return getString("Name");
    }

    public List<String> getDescription() {
        return getStringList("Description");
    }

    public void loadFieldValues() {
        Field[] fields = scenario.getClass().getDeclaredFields();

        for (Field field : fields) {
            FilePath filePath = field.getAnnotation(FilePath.class);

            if (filePath != null)
                setValueFromFile(field, filePath);
        }
    }

    private void setValueFromFile(Field field, FilePath filePath) {
        try {
            field.setAccessible(true);
            setValueByType(field, filePath.name());
        } catch (IllegalAccessException e) {
            Common.error(e, "Could't set the field value '" + field.getName() + "' in " + scenario.getName());
        }
    }

    private void setValueByType(Field field, String path) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (SimpleSound.class.isAssignableFrom(type))
            field.set(scenario, getSound(path));
        else if (Collection.class.isAssignableFrom(type)) {
            ParameterizedType genericType = ( ParameterizedType ) field.getGenericType();
            Class<?> typeClass = ( Class<?> ) genericType.getActualTypeArguments()[0];
            field.set(scenario, getList(path, typeClass));
        } else
            field.set(scenario, get(path, type));
    }

}
