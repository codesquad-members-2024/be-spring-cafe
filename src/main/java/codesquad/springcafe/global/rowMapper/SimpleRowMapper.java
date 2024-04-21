package codesquad.springcafe.global.rowMapper;

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

            mapFields(rs, instance, superFields);
            mapFields(rs, instance, subFields);

            return instance;
        } catch (Exception e) {
            throw new SQLException("매핑에 실패했습니다.", e);
        }
    }

    private void mapFields(ResultSet rs, T instance, Field[] fields) throws SQLException, IllegalAccessException {
        for (Field field : fields) {
            String fieldName = field.getName();
            Object value = rs.getObject(fieldName);

            if (value instanceof Timestamp) {
                value = ((Timestamp) value).toLocalDateTime();
            }

            field.setAccessible(true);
            field.set(instance, value);
        }
    }
}