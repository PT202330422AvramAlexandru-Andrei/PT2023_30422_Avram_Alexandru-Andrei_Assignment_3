package dao;

import connection.ConnectionFactory;
import model.Order;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OrderDAO {
    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
    protected static final String insertStatementString = "INSERT INTO order_items (id,client_id,product_id,quantity)" + " VALUES (?,?,?,?)";
    //aici mai trebuie gandit =========================================================================

    private static final String findStatementString = "SELECT * FROM order_items where id = ?";

    public static Order findById(int orderId) {
        Order toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setLong(1, orderId);
            rs = findStatement.executeQuery();
            rs.next();

            int clientId = rs.getInt("client_id");
            List<Product> products = new ArrayList<Product>();
            int quantity = rs.getInt("quantity");
            //here as well================================================================================================
            toReturn = new Order(orderId, products, clientId, quantity);
        } catch (Exception e) {
            LOGGER.warning("OrderDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }
}
