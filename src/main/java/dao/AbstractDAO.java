package dao;

import connection.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class that implements the basic CRUD operations (create, read, update, delete)
 *
 * <p>
 *     This class uses reflection in order to create the SQL queries and to map the results to the corresponding objects.
 * </p>
 *
 * <p>
 * Example usage:
 * </p>
 *
 * <pre>
 *     <code>
 *         public static int insertProduct(Product product) {
 *         return AbstractDAO.insert(product);
 *         }
 *     </code>
 * </pre>
 */
public abstract class AbstractDAO {


    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO";// product (id,name,price,quantity)" + " VALUES (?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM";// product where id = ?";
    private final static String deleteStatementString = "DELETE FROM ";// product WHERE id = ?";
    private final static String selectAllStatementString = "SELECT * FROM";

    /**
     * Returns an object from the database based on its id and class
     *
     * @param id the id of the object to be returned
     * @param objClass the class of the object to be returned
     * @return the object with the specified id, from the correct table
     * @throws RuntimeException if the object cannot be returned
     */

    public static Object findById(int id, Class<? extends Object> objClass) {
        String findString = findStatementString + " " + objClass.getSimpleName().toLowerCase() + " where id = ?";

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        Object obj = null;
        try {
            findStatement = dbConnection.prepareStatement(findString);
            findStatement.setLong(1, id);
            rs = findStatement.executeQuery();
            if(rs.next()) {
                obj = objClass.newInstance();
                Field[] fields = objClass.getDeclaredFields();
                for(Field field : fields) {
                    String fieldName = field.getName();
                    Object fieldValue = rs.getObject(fieldName);
                    field.setAccessible(true);
                    if (field.getType() == float.class) {
                        float floatValue = ((Double) fieldValue).floatValue();
                        field.set(obj, floatValue);
                    } else {
                        field.set(obj, fieldValue);
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, objClass.getSimpleName().toLowerCase() + "DAO:findById " + e.getMessage());
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return obj;
    }

    /**
     * Inserts an object into the database
     *
     * @param obj the object to be inserted
     * @return the id of the inserted object
     * @throws RuntimeException if the object cannot be inserted
     */

    public static int insert(Object obj) {
        Connection dbConnection = ConnectionFactory.getConnection();
        String insertString = insertStatementString + " " + obj.getClass().getSimpleName().toLowerCase() + " (" + QueryBuilder.fields(obj.getClass().getDeclaredFields()) + ")" + " VALUES (" + QueryBuilder.questionMarks(obj.getClass().getDeclaredFields().length) +")";

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            Field[] fields = obj.getClass().getDeclaredFields();
            LOGGER.log(Level.INFO, "Fields: " + fields.length);
            insertStatement.setObject(1, null);
            for (int i = 1; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(obj);

                if (field.getType().equals("int")) {
                    value = Integer.parseInt(value.toString());
                }
                if (field.getType().equals("float")) {
                    value = Float.parseFloat(value.toString());
                }

                insertStatement.setObject(i, value);
            }

            LOGGER.log(Level.INFO, "Insert statement: " + insertStatement.toString());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,  obj.getClass().getSimpleName().toLowerCase() + "(a)DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }

    /**
     * Updates an object in the database
     *
     * @param obj the object to be updated
     * @throws IllegalArgumentException if the object is not valid
     * @throws SecurityException if the object cannot be accessed
     */
    public static void update(Object obj) {
        Connection connection = ConnectionFactory.getConnection();
        String tableName = obj.getClass().getSimpleName().toLowerCase();
        String updateString = "UPDATE " + tableName + " SET ";
        List<String> setStatements = new ArrayList<>();
        String idFieldName = null;
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {

                field.setAccessible(true);
                if (field.getType().equals(int.class)) {
                    field.set(obj, Integer.parseInt(field.get(obj).toString()));
                }
                if (field.getType().equals(float.class) && !field.getName().toLowerCase().equals("id")) {
                    field.set(obj, Float.parseFloat(field.get(obj).toString()));
                }
                if (!field.getName().equals("id")) {
                    String fieldName = field.getName();
                    Object value = field.get(obj);
                    if (value instanceof String) {
                        setStatements.add(fieldName + " = '" + value + "'");
                    } else {
                        setStatements.add(fieldName + " = " + value);
                    }
                } else {
                    idFieldName = field.getName();
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        updateString += String.join(", ", setStatements);
        updateString += " WHERE " + idFieldName + " = ?";
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement(updateString);
            obj.getClass().getDeclaredFields()[0].setAccessible(true);
            updateStatement.setInt(1, (int) obj.getClass().getDeclaredFields()[0].getInt(obj));
            updateStatement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(connection);
        }
    }


    /**
     * Deletes an object from the database
     *
     * @param id the id of the object to be deleted
     * @param objClass the class of the object to be deleted
     * @return the id of the deleted object
     * @throws RuntimeException if the object cannot be deleted
     */
    public static int delete(int id, Class<? extends Object> objClass) {
        Connection dbConnection = ConnectionFactory.getConnection();

        String deleteString = deleteStatementString + objClass.getSimpleName().toLowerCase() + " where id = ?";

        PreparedStatement deleteStatement = null;
        int deletedId = -1;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteString, Statement.RETURN_GENERATED_KEYS);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();

            ResultSet rs = deleteStatement.getGeneratedKeys();
            if (rs.next()) {
                deletedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, objClass.getSimpleName().toLowerCase() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
        return deletedId;
    }

    /**
     * Selects all objects from a table in the database
     *
     * @param objClass the class of the objects to be selected
     * @return a list of objects
     * @throws RuntimeException if the objects cannot be selected
     */
    public static List<?> selectAll(Class<? extends Object> objClass) {
        Connection dbConnection = ConnectionFactory.getConnection();
        String selectAllStatementString = "SELECT * FROM " + objClass.getSimpleName().toLowerCase();

        PreparedStatement selectAllStatement = null;
        List<Object> toReturn = new ArrayList<Object>();
        try {
            selectAllStatement = dbConnection.prepareStatement(selectAllStatementString);
            ResultSet rs = selectAllStatement.executeQuery();
            while(rs.next()) {
                Object obj = objClass.newInstance();
                Field[] fields = objClass.getDeclaredFields();
                for(Field field : fields) {
                    String columnName = field.getName();
                    Object columnValue = rs.getObject(columnName);
                    field.setAccessible(true);
                    if (field.getType() == float.class) {
                        float floatValue = ((Double) columnValue).floatValue();
                        field.set(obj, floatValue);
                    } else {
                        field.set(obj, columnValue);
                    }
                }
                toReturn.add(obj);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, objClass.getSimpleName().toLowerCase() +  "DAO:selectAll " + e.getMessage());
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(selectAllStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Creates a table with the objects from a list
     *
     * @param list the list of objects
     * @return a JTable with the objects from the list
     * @throws RuntimeException if the table cannot be created
     */
    public static JTable createTable(List<? extends Object> list) {
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();


        if (list.isEmpty()) {
            return table;
        }
        Class<?> crtClass = list.get(0).getClass();
        Field[] fields = crtClass.getDeclaredFields();

        for (Field field : fields) {
            model.addColumn(field.getName());
        }

        for (Object object : list) {
            Object[] rowData = new Object[fields.length];

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                try {
                    rowData[i] = field.get(object);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(rowData);
        }

        table.setModel(model);

        return table;
    }


}
