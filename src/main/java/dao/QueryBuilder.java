package dao;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

public class QueryBuilder {

    public static String questionMarks(int nr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nr - 1; i++) {
            sb.append("?,");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static String fields(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        Field[] fields1 = Arrays.copyOfRange(fields, 1, fields.length);
        for (Field field : fields1) {
            String fieldName = field.getName();
            String typeName = field.getType().getSimpleName();
            sb.append(fieldName).append(" ").append(typeName);
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
