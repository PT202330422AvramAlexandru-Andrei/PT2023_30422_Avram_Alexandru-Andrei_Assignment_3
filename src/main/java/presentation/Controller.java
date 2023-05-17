package presentation;

import bll.ClientBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Main controller class for the entire application.
 */
public class Controller {

    ClientBLL clientBLL = new ClientBLL();

    @FXML
    public Button toClient;

    @FXML
    public Button toProduct;

    @FXML
    public Button toOrder;

    /**
     * Goes to the client page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toClientPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Client Page/client.fxml"));
        Scene crtScene = toClient.getScene();
        crtScene.setRoot(root);
    }

    /**
     * Goes to the product page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toProductPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Product Page/product.fxml"));
        Scene crtScene = toClient.getScene();
        crtScene.setRoot(root);
    }

    /**
     * Goes to the order page.
     * @throws IOException if the file is not found
     */
    @FXML
    public void toOrderPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Order Page/order.fxml"));
        Scene crtScene = toClient.getScene();
        crtScene.setRoot(root);
    }
}
