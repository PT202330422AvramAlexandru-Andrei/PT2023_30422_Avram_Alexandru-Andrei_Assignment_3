package dao;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to help build queries for the database.
 * It is used in the AbstractDAO class.
 *
 * @see dao.AbstractDAO
 */

public class QueryBuilder {

    /**
     * This method is used to build a select query.
     *
     * @param nr the number of fields
     * @return the part of the query that selects number of fields
     */
    public static String questionMarks(int nr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nr - 1; i++) {
            sb.append("?,");
        }

        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * This method is used to build a select query.
     *
     * @param fields the fields of the table
     * @return the part of the query that selects the fields
     */
    public static String fields(Field[] fields) {
        StringBuilder sb = new StringBuilder();
        Field[] fields1 = Arrays.copyOfRange(fields, 1, fields.length);
        for (Field field : fields1) {
            sb.append(field.getName() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * This method is used to build an update query.
     *
     * @param fields the fields of the table
     * @return the part of the query that updates the fields
     */
    public static String updateFields(Field[] fields, Object obj) {
        StringBuilder sb = new StringBuilder();
        Field[] fields1 = Arrays.copyOfRange(fields, 1, fields.length);
        for (Field field : fields1) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) {
                    sb.append(field.getName() + " = " + value + ",");
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
