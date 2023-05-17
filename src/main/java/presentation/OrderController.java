package presentation;

import bll.OrderBLL;
import bll.ProductBLL;
import dao.AbstractDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.io.IOException;

/**
 * Controller class for the order page.
 */
public class OrderController {

    OrderBLL orderBLL = new OrderBLL();

    JFrame frame = new JFrame("Orders Table");
    JTable orderTable = AbstractDAO.createTable(AbstractDAO.selectAll(Orders.class));
    JScrollPane scrollPane = new JScrollPane(orderTable);

    @FXML
    public Button toAdd;
    @FXML
    public Button toMain;

    /**
     * Goes to add order page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toAddOrder() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Order Page/addOrder.fxml"));
        Scene crtScene = toAdd.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button addOrder;
    @FXML
    public Button toOrder;

    /**
     * Opens the orders table.
     */
    @FXML
    public void viewOrders() {
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Goes to ordering page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toOrderPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Order Page/order.fxml"));
        Scene crtScene = toOrder.getScene();
        crtScene.setRoot(root);
    }

    /**
     * Goes to main page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/view.fxml"));
        Scene crtScene = toMain.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public TextField client_id;
    @FXML
    public TextField product_id;
    @FXML
    public TextField quantity;

    /**
     * Adds an order to the database and updates the products table with data from the text fields.
     * Decrements the quantity of the product ordered from the stock.
     */
    @FXML
    public void addOrder() {
        int client_id = Integer.parseInt(this.client_id.getText());
        int product_id = Integer.parseInt(this.product_id.getText());
        int quantity = Integer.parseInt(this.quantity.getText());
        ProductBLL productBLL = new ProductBLL();
        Product product = (Product) AbstractDAO.findById(product_id, Product.class);

        if (product.getQuantity() < quantity) {
            JOptionPane.showMessageDialog(null, "Not enough products in stock!");
            return;
        }

        orderBLL.insertOrder(product_id, client_id, quantity);

        ProductBLL.updateProduct(product_id, product.getName(), product.getQuantity() - quantity, product.getPrice());

        orderTable = AbstractDAO.createTable(AbstractDAO.selectAll(Orders.class));
        scrollPane = new JScrollPane(orderTable);

        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
