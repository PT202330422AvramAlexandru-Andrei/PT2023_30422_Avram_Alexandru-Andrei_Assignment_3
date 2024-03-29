package presentation;

import bll.ClientBLL;
import dao.AbstractDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Client;
import start.Reflection;

import javax.swing.*;
import java.io.IOException;

/**
 * This class is used to control the client page.
 */
public class ClientController {

    ClientBLL clientBLL = new ClientBLL();

    JFrame frame = new JFrame("Clients Table");
    JTable clientTable = AbstractDAO.createTable(AbstractDAO.selectAll(Client.class));
    JScrollPane scrollPane = new JScrollPane(clientTable);

    @FXML
    public Button toMain;
    /**
     * This method is used to go back to the main page.
     */
    @FXML
    public void toMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/view.fxml"));
        Scene crtScene = toMain.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toAddClient;

    /**
     * This method is used to go to the add client page.
     */
    @FXML
    public void toAddClient() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Client Page/addClient.fxml"));
        Scene crtScene = toAddClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toDeleteClient;

    /**
     * This method is used to go to the delete client page.
     */
    @FXML
    public void toDeleteClient() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Client Page/deleteClient.fxml"));
        Scene crtScene = toDeleteClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toEditClient;

    /**
     * This method is used to go to the edit client page.
     */
    @FXML
    public void toEditClient() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Client Page/editClient.fxml"));
        Scene crtScene = toEditClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button toClient;

    /**
     * This method is used to go to the client page.
     */
    @FXML
    public void toClientPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/Client Page/client.fxml"));
        Scene crtScene = toClient.getScene();
        crtScene.setRoot(root);
    }

    @FXML
    public Button addClient;
    @FXML
    public TextField name, address, email, age;

    /**
     * This method is used to add a client on button click, using the data from the text fields.
     */
    @FXML
    public void addClient() throws IOException {
        Client client = new Client(name.getText(), address.getText(), email.getText(), Integer.parseInt(age.getText()));
        ClientBLL.insertClient(client);

        clientTable = AbstractDAO.createTable(AbstractDAO.selectAll(Client.class));
        scrollPane = new JScrollPane(clientTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public TextField deleteId;

    /**
     * This method is used to delete a client on button click, using the data from the text field.
     */
    @FXML
    public void deleteClient() throws IOException {
        ClientBLL.deleteClient(Integer.parseInt(deleteId.getText()));

        clientTable = AbstractDAO.createTable(clientBLL.selectAll());
        scrollPane = new JScrollPane(clientTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * This method is used to view the clients table.
     */
    @FXML
    public void viewClients() throws IOException {

        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    @FXML
    public TextField editId, editName, editAddress, editEmail, editAge;

    /**
     * This method is used to edit a client on button click, using the data from the text fields.
     */
    @FXML
    public void editClient() {
        clientBLL.updateClient(Integer.parseInt(editId.getText()), editName.getText(), editAddress.getText(), editEmail.getText(), Integer.parseInt(editAge.getText()));

        clientTable = AbstractDAO.createTable(clientBLL.selectAll());
        scrollPane = new JScrollPane(clientTable);
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
