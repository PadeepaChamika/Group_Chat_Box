package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController{
    public TextArea txtArea;
    public AnchorPane loginAnchorPaneContext;
    public TextField txtUsername;
    public static String username;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        username = txtUsername.getText();
        URL resource = getClass().getResource("../views/ChatRoomForm.fxml");
        Parent load = FXMLLoader.load(resource);
        loginAnchorPaneContext.getChildren().clear();
        loginAnchorPaneContext.getChildren().add(load);
        txtUsername.clear();
    }

}
