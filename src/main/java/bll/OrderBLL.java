package bll;

import dao.AbstractDAO;
import model.Orders;

public class OrderBLL {

    public void insertOrder(int product, int client, int quantity) {
        Orders order = new Orders(product, client, quantity);
        AbstractDAO.insert(order);
    }
}
