package dao;

import connection.ConnectionFactory;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractDAO {


    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO";// product (id,name,price,quantity)" + " VALUES (?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM";// product where id = ?";
    private final static String deleteStatementString = "DELETE FROM ";// product WHERE id = ?";
    private final static String selectAllStatementString = "SELECT * FROM";

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

    /*public static List<?> selectAll(Class<? extends Object> objClass) {
        Connection dbConnection = ConnectionFactory.getConnection();
        String selectAllStatementString = "SELECT * FROM " + objClass.getSimpleName().toLowerCase();

        PreparedStatement selectAllStatement = null;
        List<Object> toReturn = new ArrayList<Object>();
        try {
            selectAllStatement = dbConnection.prepareStatement(selectAllStatementString);
            ResultSet rs = selectAllStatement.executeQuery();
            while(rs.next()) {
                switch (objClass.getSimpleName()) {
                    case "Client":
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        String address = rs.getString("address");
                        String email = rs.getString("email");
                        int age = rs.getInt("age");
                        toReturn.add(new Client(id, name, address, email, age));
                    break;
                    case "Product":
                        int idP = rs.getInt("id");
                        String nameP = rs.getString("name");
                        float price = rs.getFloat("price");
                        int quantity = rs.getInt("quantity");
                        toReturn.add(new Product(idP, nameP, price, quantity));
                    break;
                    case "Orders":
                        int clientId = rs.getInt("clientId");
                        int productId = rs.getInt("productId");
                        int quantityO = rs.getInt("quantity");
                        toReturn.add(new Orders(id, clientId, productId, quantityO));
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, objClass.getSimpleName().toLowerCase() +  "DAO:selectAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(selectAllStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }*/

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
