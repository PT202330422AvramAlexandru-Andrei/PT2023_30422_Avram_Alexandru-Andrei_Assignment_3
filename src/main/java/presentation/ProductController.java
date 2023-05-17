package presentation;

import bll.ProductBLL;
import dao.AbstractDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Product;

import javax.swing.*;
import java.io.IOException;

/**
 * Controller class for the product page.
 */
public class ProductController {

    ProductBLL productBLL = new ProductBLL();

    JFrame frame = new JFrame("Products Table");
    JTable productTable = AbstractDAO.createTable(AbstractDAO.selectAll(Product.class));
    JScrollPane scrollPane = new JScrollPane(productTable);

    @FXML
    public Button toMain;
    /**
     * Goes to the main page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/view.fxml"));
        Scene crtScene = toMain.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toProduct;

    /**
     * Goes to the product page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toProductPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/product.fxml"));
        Scene crtScene = toProduct.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toAddProduct;

    /**
     * Goes to the add product page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toAddProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/addProduct.fxml"));
        Scene crtScene = toAddProduct.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toDeleteProduct;

    /**
     * Goes to the delete product page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toDeleteProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/deleteProduct.fxml"));
        Scene crtScene = toDeleteProduct.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toEditProduct;

    /**
     * Goes to the edit product page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toEditProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/editProduct.fxml"));
        Scene crtScene = toEditProduct.getScene();
        crtScene.setRoot(root);
    }

    /**
     * Opens a table with all the products.
     * @throws IOException if the file is not found
     */
    @FXML
    public void viewProducts() throws IOException {

        productTable = AbstractDAO.createTable(AbstractDAO.selectAll(Product.class));
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public TextField name;
    @FXML
    public TextField quantity;
    @FXML
    public TextField price;

    /**
     * Adds a product to the database.
     */
    @FXML
    public void addProduct() {
        Product product = new Product(name.getText(), Float.parseFloat(price.getText()), Integer.parseInt(quantity.getText()));
        productBLL.insertProduct(product);
        productTable = AbstractDAO.createTable(AbstractDAO.selectAll(Product.class));
        scrollPane = new JScrollPane(productTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public TextField deleteId;

    /**
     * Deletes a product from the database.
     */
    @FXML
    public void deleteProduct() {
        productBLL.deleteProduct(Integer.parseInt(deleteId.getText()));
        productTable = AbstractDAO.createTable(AbstractDAO.selectAll(Product.class));

        scrollPane = new JScrollPane(productTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public TextField editId;
    @FXML
    public TextField editName;
    @FXML
    public TextField editPrice;
    @FXML
    public TextField editQuantity;


    /**
     * Edits a product from the database.
     */
    @FXML
    public void editProduct() {
        ProductBLL.updateProduct(Integer.parseInt(editId.getText()), editName.getText(), Integer.parseInt(editQuantity.getText()), Float.parseFloat(editPrice.getText()));
        productTable = AbstractDAO.createTable(AbstractDAO.selectAll(Product.class));
        scrollPane = new JScrollPane(productTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
