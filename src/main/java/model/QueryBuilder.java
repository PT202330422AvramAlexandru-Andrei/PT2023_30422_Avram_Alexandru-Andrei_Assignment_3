package model;

public class QueryBuilder {

    public static String createSelectQuery(String field, String table) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(table);
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
}
