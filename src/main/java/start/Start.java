package start;

import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;

import bll.ClientBLL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Client;

import javax.swing.*;

public class Start extends Application{

	@Override
	public void start(Stage stage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("front-end/view.fxml"));
		Scene scene = new Scene(root, 600, 600);
		stage.setTitle("Orders Management System");
		//stage.getIcons().add(new javafx.scene.image.Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
		stage.setResizable(true);
		stage.setMinHeight(350);
		stage.setMinWidth(600);
		stage.setScene(scene);
		stage.show();
	}

	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

	public static void main(String[] args) throws SQLException, PrinterException {
		launch();
	}


}
