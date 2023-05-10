package presentation;

import bll.ClientBLL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Client;
import start.ReflectionExample;

import javax.swing.*;
import java.io.IOException;

public class ClientController {

    ClientBLL clientBLL = new ClientBLL();

    JFrame frame = new JFrame("Clients Table");
    JTable clientTable = ReflectionExample.createTable(clientBLL.selectAll());
    JScrollPane scrollPane = new JScrollPane(clientTable);

    @FXML
    public Button toAddClient;

    @FXML
    public void toAddClient() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/addClient.fxml"));
        Scene crtScene = toAddClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toDeleteClient;

    @FXML
    public void toDeleteClient() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/deleteClient.fxml"));
        Scene crtScene = toDeleteClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toEditClient;

    @FXML
    public void toEditClient() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/editClient.fxml"));
        Scene crtScene = toEditClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toClient;

    @FXML
    public void toClientPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/client.fxml"));
        Scene crtScene = toClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button addClient;
    @FXML
    public TextField name, address, email, age;

    @FXML
    public void addClient() throws IOException {
        Client client = new Client(name.getText(), address.getText(), email.getText(), Integer.parseInt(age.getText()));
        clientBLL.insertClient(client);

        clientTable = ReflectionExample.createTable(clientBLL.selectAll());
        scrollPane = new JScrollPane(clientTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public TextField deleteId;

    @FXML
    public void deleteClient() throws IOException {
        clientBLL.deleteClient(Integer.parseInt(deleteId.getText()));

        clientTable = ReflectionExample.createTable(clientBLL.selectAll());
        scrollPane = new JScrollPane(clientTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public void viewClients() throws IOException {

        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public TextField editId, editName, editAddress, editEmail, editAge;

    @FXML
    public void editClient() throws IOException {
        clientBLL.updateClient(Integer.parseInt(editId.getText()), editName.getText(), editAddress.getText(), editEmail.getText(), Integer.parseInt(editAge.getText()));

        clientTable = ReflectionExample.createTable(clientBLL.selectAll());
        scrollPane = new JScrollPane(clientTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
