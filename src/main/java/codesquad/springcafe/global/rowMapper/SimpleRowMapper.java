package codesquad.springcafe.global.rowMapper;

import codesquad.springcafe.global.annotation.AssociatedClass;
import codesquad.springcafe.global.utils.AliasGenerator;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SimpleRowMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public SimpleRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            Field[] superFields = clazz.getSuperclass().getDeclaredFields();
            Field[] subFields = clazz.getDeclaredFields();

            String tableName = AliasGenerator.getTableName(clazz);

            mapFields(rs, instance, superFields, tableName);
            mapFields(rs, instance, subFields, tableName);

            return instance;
        } catch (Exception e) {
            throw new SQLException("매핑에 실패했습니다.", e);
        }
    }

    private void mapFields(ResultSet rs, Object instance, Field[] fields, String tableName) throws SQLException, IllegalAccessException {
        for (Field field : fields) {
            String fieldName = field.getName();
            field.setAccessible(true);
            if (field.isAnnotationPresent(AssociatedClass.class)) {
                Class<?> associatedClass = field.getAnnotation(AssociatedClass.class).value();
                if (associatedClass.isAssignableFrom(field.getType())) {
                    Object associatedInstance = mapRow(rs, associatedClass);
                    field.set(instance, associatedInstance);
                    continue;
                }
            }

            Object value = rs.getObject(tableName + "_" + fieldName);

            if (value instanceof Timestamp) {
                value = ((Timestamp) value).toLocalDateTime();
            }

            field.set(instance, value);
        }
    }

    private Object mapRow(ResultSet rs, Class<?> clazz) throws SQLException {
        try {
            Object instance = clazz.getDeclaredConstructor().newInstance();

            Field[] superFields = clazz.getSuperclass().getDeclaredFields();
            Field[] subFields = clazz.getDeclaredFields();

            String tableName = AliasGenerator.getTableName(clazz);

            mapFields(rs, instance, superFields, tableName);
            mapFields(rs, instance, subFields, tableName);

            return instance;
        } catch (Exception e) {
            throw new SQLException("매핑에 실패했습니다.", e);
        }
    }
}