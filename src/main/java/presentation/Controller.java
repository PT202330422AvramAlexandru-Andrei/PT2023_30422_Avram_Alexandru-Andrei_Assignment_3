package presentation;

import bll.ClientBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import start.ReflectionExample;

import javax.swing.*;
import java.io.IOException;


public class Controller {

    ClientBLL clientBLL = new ClientBLL();

    @FXML
    public Button toClient;

    @FXML
    public Button toProduct;

    @FXML
    public Button toOrder;

    @FXML
    public void toClientPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/client.fxml"));
        Scene crtScene = toClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public void toProductPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/product.fxml"));
        Scene crtScene = toClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public void toOrderPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/order.fxml"));
        Scene crtScene = toClient.getScene();
        crtScene.setRoot(root);
    }
}
