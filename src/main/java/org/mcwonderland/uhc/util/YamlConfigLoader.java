package org.mcwonderland.uhc.util;

import org.mineacademy.fo.ChatUtil;
import org.mineacademy.fo.model.SimpleSound;
import org.mineacademy.fo.settings.YamlStaticConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2019-12-17 下午 03:54
 */
public class YamlConfigLoader {

    private final Class configClass;

    public static void load(Class configClass) {
        new YamlConfigLoader(configClass);

    }

    private YamlConfigLoader(Class configClass) {
        this.configClass = configClass;

        loadAllFields();
    }


    private void loadAllFields() {
        List<Class> allClasses = getAllInnerClasses();

        loadFields(configClass);

        for (Class subClass : allClasses) {
            loadFields(subClass);
        }
    }

    private List<Class> getAllInnerClasses() {
        List<Class> classes = new ArrayList<>();

        addClasses(classes, configClass);

        return classes;
    }

    private void addClasses(List<Class> toAdd, Class targetClass) {
        Class[] classes = targetClass.getClasses();
        toAdd.addAll(Arrays.asList(classes));
        for (Class sub : classes)
            addClasses(toAdd, sub);
    }

    private void loadFields(Class cla) {
        Field[] allField = cla.getDeclaredFields();


        for (Field field : allField) {
            setFieldValuesFromYaml(field);
        }

    }

    private void setFieldValuesFromYaml(Field field) {
        try {
            field.setAccessible(true);
            field.set(null, getFieldValueInYaml(field));
        } catch (Exception ex) {
        }
    }

    private Object getFieldValueInYaml(Field field) throws Exception {
        String nameInYaml = getPathInYaml(field);
        String methodName = getGetterName(field);

        Method method = YamlStaticConfig.class.getDeclaredMethod(methodName, String.class);
        method.setAccessible(true);

        return method.invoke(null, nameInYaml);
    }

    private String getPathInYaml(Field field) {
        String fieldName = ChatUtil.capitalizeFully(field.getName(), new char[]{'_'});
        StringBuilder builder = new StringBuilder(fieldName);

        Class fieldClass = field.getDeclaringClass();

        while (fieldClass != configClass) {
            builder.insert(0, fieldClass.getSimpleName() + ".");
            fieldClass = fieldClass.getDeclaringClass();
        }

        return builder.toString();
    }

    private String getGetterName(Field field) {
        Class fieldType = field.getType();


        if (fieldType.isAssignableFrom(List.class)) {
            ParameterizedType type = ( ParameterizedType ) field.getGenericType();
            Class<?> typeClass = ( Class<?> ) type.getActualTypeArguments()[0];
            return "get" + typeClass.getSimpleName() + "List";
        }

        if (fieldType.isAssignableFrom(SimpleSound.class)){
            return "getSound";
        }

        return "get" + fieldType.getSimpleName();
    }
}