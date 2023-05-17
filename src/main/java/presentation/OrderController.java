package presentation;

import bll.OrderBLL;
import dao.AbstractDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.Orders;

import javax.swing.*;
import java.io.IOException;


public class OrderController {

    OrderBLL productBLL = new OrderBLL();

    JFrame frame = new JFrame("Orders Table");
    JTable orderTable = AbstractDAO.createTable(AbstractDAO.selectAll(Orders.class));
    JScrollPane scrollPane = new JScrollPane(orderTable);

    @FXML
    public Button toAdd;
    @FXML
    public Button toMain;

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

    @FXML
    public void viewOrders() {
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public void toOrderPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Order Page/order.fxml"));
        Scene crtScene = toOrder.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public void toMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/view.fxml"));
        Scene crtScene = toMain.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public void addOrder() {

    }
}
