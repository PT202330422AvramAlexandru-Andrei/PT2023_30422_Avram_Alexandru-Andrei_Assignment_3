package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import dao.AbstractDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.Client;
import model.Product;

import javax.swing.*;
import java.io.IOException;

public class ProductController {

    ProductBLL productBLL = new ProductBLL();

    JFrame frame = new JFrame("Products Table");
    JTable productTable = AbstractDAO.createTable(AbstractDAO.selectAll(Product.class));
    JScrollPane scrollPane = new JScrollPane(productTable);

    @FXML
    public Button toMain;
    @FXML
    public void toMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/view.fxml"));
        Scene crtScene = toMain.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toProduct;

    @FXML
    public void toProductPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/product.fxml"));
        Scene crtScene = toProduct.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toAddProduct;

    @FXML
    public void toAddProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/addProduct.fxml"));
        Scene crtScene = toAddProduct.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toDeleteProduct;

    @FXML
    public void toDeleteProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/deleteProduct.fxml"));
        Scene crtScene = toDeleteProduct.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toEditProduct;

    @FXML
    public void toEditProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/editProduct.fxml"));
        Scene crtScene = toEditProduct.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public void viewProducts() throws IOException {

        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public void addProduct() {}

    @FXML
    public void deleteProduct() {}

    @FXML
    public void editProduct() {}
}
