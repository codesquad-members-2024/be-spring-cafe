package codesquad.springcafe.global.utils;

import codesquad.springcafe.global.annotation.AssociatedClass;
import codesquad.springcafe.global.annotation.Table;

import java.lang.reflect.Field;
import java.util.Arrays;

public class AliasGenerator {
    public static String generateAliases(Class<?> clazz) {
        Field[] superFields = clazz.getSuperclass().getDeclaredFields();
        Field[] subFields = clazz.getDeclaredFields();

        String tableName = getTableName(clazz);

        String[] aliases = Arrays.stream(mergeArrays(superFields, subFields))
                .filter(field -> !field.isAnnotationPresent(AssociatedClass.class))
                .map(field -> tableName + "." + field.getName() + " as " + tableName + "_" + field.getName())
                .toArray(String[]::new);

        return String.join(", ", aliases);
    }

    public static String getTableName(Class<?> clazz) {
        return clazz.isAnnotationPresent(Table.class) ?
                clazz.getAnnotation(Table.class).name() : clazz.getSimpleName().toLowerCase();
    }

    private static <T> T[] mergeArrays(T[] array1, T[] array2) {
        int length1 = array1.length;
        int length2 = array2.length;

        T[] mergedArray = Arrays.copyOf(array1, length1 + length2);

        System.arraycopy(array2, 0, mergedArray, length1, length2);

        return mergedArray;
    }
}