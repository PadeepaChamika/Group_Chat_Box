package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.Scanner;

import static controller.LoginFormController.username;

public class ClientFormController extends Thread{
    public TextArea txtArea;
    public AnchorPane clientAnchorPaneContext;
    public Label lblClient;
    public TextField txtClientMassage;
    public VBox vBoxPane;
    public ImageView emogi1;
    public ImageView emogi2;
    public ImageView emogi3;
    public ImageView emogi4;
    public ImageView emogi5;
    public ImageView emogi6;
    public ImageView emogi7;
    public ImageView emogi8;
    public ImageView emogi9;
    public ImageView emogi10;
    public AnchorPane emogiPane;
    public ImageView imgEmoji;
    Socket socket=null;
    PrintWriter printWriter;
    FileChooser fileChooser;
    URL url;

    String []ePath=new String[10];

    private BufferedReader bufferedReader;

    public void sendOnAction(ActionEvent actionEvent) {
        send();
        emogiPane.setVisible(false);
    }

    public void send() {
        String msg = txtClientMassage.getText();
        printWriter.println(LoginFormController.username + ": " + msg);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        Text text = new Text("Me : "+msg);
        text.setStyle("-fx-font-size: 15px");
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-color:rgb(239,242,255);"
                + "-fx-background-color: rgb(91,99,99);" +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        //text.setFill(Color.color(0.934, 0.945, 0.996));
        text.setFill(Color.color(0, 0, 0));
        hBox.getChildren().add(textFlow);
        vBoxPane.getChildren().add(hBox);
        printWriter.flush();
        txtClientMassage.setText("");
        if (msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

    public void initialize(){
        connectSocket();
        lblClient.setText(username);
        emogiPane.setVisible(false);

        for (int i = 0; i < ePath.length; i++) {
            ePath[i] = "assets/emoji/" + (i + 1) + ".png";
            //System.out.println(ePath[i]);

        }
    }

    private void connectSocket() {
        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Connect With Server");

            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            this.start();

        } catch (IOException e) {

        }
    }

    public void run() {
        try {
            while (true) {
                String msg = bufferedReader.readLine();
                System.out.println("Message : " + msg);
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println("cmd : " + cmd);
                StringBuilder fulmsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println("fulmsg : " + fulmsg);
                System.out.println();
                if (cmd.equalsIgnoreCase(LoginFormController.username + ":")) {
                    continue;
                } else if (fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        HBox hBox = new HBox();
                        if (fulmsg.toString().startsWith("assets/emoji/") ) {
                            hBox.setAlignment(Pos.TOP_LEFT);
                            hBox.setPadding(new Insets(5, 10, 5, 5));
                            Text text = new Text(cmd + " ");
                            text.setStyle("-fx-font-size: 15px");
                            ImageView imageView = new ImageView();
                            Image image = new Image(String.valueOf(fulmsg));
                            imageView.setImage(image);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(100);
                            TextFlow textFlow = new TextFlow(text, imageView);
                            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                                    + "-fx-background-color: rgb(182,182,182);" +
                                    "-fx-background-radius: 10px");
                            textFlow.setPadding(new Insets(5, 0, 5, 5));
                            hBox.getChildren().add(textFlow);
                            vBoxPane.getChildren().add(hBox);
                        }else if (fulmsg.toString().endsWith(".png") || fulmsg.toString().endsWith(".jpg") || fulmsg.toString().endsWith(".jpeg") || fulmsg.toString().endsWith(".gif")) {
                            System.out.println(fulmsg);
                            hBox.setAlignment(Pos.TOP_LEFT);
                            hBox.setPadding(new Insets(5, 10, 5, 5));
                            Text text = new Text(cmd + " ");
                            text.setStyle("-fx-font-size: 15px");
                            ImageView imageView = new ImageView();
                            Image image = new Image(String.valueOf(fulmsg));
                            imageView.setImage(image);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(100);
                            TextFlow textFlow = new TextFlow(text, imageView);
                            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                                    + "-fx-background-color: rgb(182,182,182);" +
                                    "-fx-background-radius: 10px");
                            textFlow.setPadding(new Insets(5, 0, 5, 5));
                            hBox.getChildren().add(textFlow);
                            vBoxPane.getChildren().add(hBox);
                        }else {
                            hBox.setAlignment(Pos.CENTER_LEFT);
                            hBox.setPadding(new Insets(5, 10, 5, 5));
                            Text text = new Text(msg);
                            text.setStyle("-fx-font-size: 15px");
                            TextFlow textFlow = new TextFlow(text);
                            textFlow.setStyle("-fx-color:rgb(239,242,255);"
                                    + "-fx-background-color: rgb(56,62,80);" +
                                    "-fx-background-radius: 10px");
                            textFlow.setPadding(new Insets(5, 0, 5, 5));
                            text.setFill(Color.color(0, 0, 0));
                            hBox.getChildren().add(textFlow);
                            vBoxPane.getChildren().add(hBox);
                        }
                    }
                });
            }
            bufferedReader.close();
            printWriter.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void emogiPanalOnAction(MouseEvent mouseEvent) {
        emogiPane.setVisible(!emogiPane.isVisible());
    }

    public void emageSendOnAction(MouseEvent mouseEvent) throws MalformedURLException {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Image");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            printWriter.println(username + ": " + file.toURI().toURL());
        }
        if (file != null) {
            System.out.println("File Was Selected");
            url = file.toURI().toURL();
            System.out.println(url);
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 10, 5, 5));
            ImageView imageView = new ImageView();
            Image image = new Image(String.valueOf(url));
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            VBox vBox = new VBox(imageView);
            vBox.setAlignment(Pos.CENTER_RIGHT);
            vBox.setPadding(new Insets(5, 10, 5, 5));
            vBoxPane.getChildren().add(vBox);
        }
    }

    public void sendingEmogiOnAction(MouseEvent mouseEvent) {
        if (mouseEvent.getSource ( ) instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            switch (icon.getId()) {
                case "emogi1":
                    byte[] emojiBytes1 = new byte[]{/*(byte) 0xF0,*/ (byte) 0xE2, (byte) 0x9D, (byte) 0xA4};
                    String emojiAsString1 = new String(emojiBytes1, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[0]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[0]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString1);

                    }
                    break;
                case "emogi2":
                    byte[] emojiBytes2 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xA1};
                    String emojiAsString2 = new String(emojiBytes2, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[1]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[1]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString2);
                    }
                    break;
                case "emogi3":
                    byte[] emojiBytes3 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x8D};
                    String emojiAsString3 = new String(emojiBytes3, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[2]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[2]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString3);
                    }
                        break;
                case "emogi4":
                    byte[] emojiBytes4 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x82};
                    String emojiAsString4 = new String(emojiBytes4, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[3]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[3]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString4);
                    }
                        break;
                case "emogi5":
                    byte[] emojiBytes5 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x98};
                    String emojiAsString5 = new String(emojiBytes5, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[4]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[4]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString5);
                    }
                        break;
                case "emogi6":
                    byte[] emojiBytes6 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB3};
                    String emojiAsString6 = new String(emojiBytes6, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[5]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[5]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString6);
                    }
                        break;
                case "emogi7":
                    byte[] emojiBytes7 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0x93};
                    String emojiAsString7 = new String(emojiBytes7, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[6]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[6]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString7);
                    }
                        break;
                case "emogi8":
                    byte[] emojiBytes8 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB7};
                    String emojiAsString8 = new String(emojiBytes8, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[7]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[7]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString8);
                    }
                        break;
                case "emogi9":
                    byte[] emojiBytes9 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xB1};
                    String emojiAsString9 = new String(emojiBytes9, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[8]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[8]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString9);
                    }
                        break;
                case "emogi10":
                    byte[] emojiBytes10 = new byte[]{(byte) 0xF0, (byte) 0x9F, (byte) 0x98, (byte) 0xBB};
                    String emojiAsString10 = new String(emojiBytes10, StandardCharsets.UTF_8);
                    if (txtClientMassage.getText().equalsIgnoreCase("") || txtClientMassage.getText().equalsIgnoreCase(null)) {
                        ImageView imageView = new ImageView();
                        Image image = new Image(ePath[9]);
                        imageView.setImage(image);
                        imageView.setFitWidth(50);
                        imageView.setFitHeight(50);
                        VBox vBox = new VBox(imageView);
                        vBox.setAlignment(Pos.CENTER_RIGHT);
                        vBox.setPadding(new Insets(5, 10, 5, 5));
                        vBoxPane.getChildren().add(vBox);
                        printWriter.println(username + ": " + ePath[9]);
                        emogiPane.setVisible(false);
                        imgEmoji.setVisible(true);
                    } else {
                        txtClientMassage.appendText(emojiAsString10);
                    }
                        break;
            }
        }
    }
}
