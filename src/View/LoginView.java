package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class LoginView {

    private final TextField username = new TextField();
    private final PasswordField password = new PasswordField();
    private final ToggleGroup optionGroup = new ToggleGroup();
    private final Button loginButton = new Button("Login");

    private VBox mainLayout = new VBox();


    public LoginView(){

        Color darkRed = Color.rgb(146, 20, 12);
        Color lightOrange = Color.rgb(255, 207, 153);

        loginButton.setStyle("-fx-min-width:100; -fx-font-size: 20px; -fx-background-color: rgb(17, 29, 74); -fx-text-fill: rgb(255, 207, 153); -fx-border-color: rgb(255, 207, 153)");
        //ImageView loginHeader = new ImageView(new Image(new FileInputStream("View/Images/loginImage.png")));
        ImageView loginHeader = new ImageView(new Image("View/Images/loginStrip.png"));        loginHeader.setPreserveRatio(true);
        loginHeader.setFitWidth(1600);
        loginHeader.setFitHeight(340);


        Label title = new Label("Welcome back");
        Label title2 = new Label("Fill in your credentials");
        title.setStyle("-fx-text-fill: rgb(146, 20, 12); -fx-font-size:40; -fx-font-weight: bold;");
        title2.setStyle("-fx-text-fill: rgb(146, 20, 12); -fx-font-size: 20; -fx-font-weight: bold;");

        VBox titleBox = new VBox(1, title, title2);
        titleBox.setAlignment(Pos.CENTER);


        DropShadow shadow = new DropShadow(10, 5, 5, Color.GRAY);


        username.setPromptText("username...");
        username.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-font-size: 20; -fx-border-color: rgb(17, 29, 74); -fx-max-width: 400");
        username.setEffect(shadow);


        password.setPromptText("password...");
        password.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-font-size: 20; -fx-border-color: rgb(17, 29, 74); -fx-max-width: 400");
        password.setEffect(shadow);

        VBox inputBox = new VBox(5, username, password);
        inputBox.setPadding(new Insets(20));
        inputBox.setAlignment(Pos.CENTER);


        Label loggingInLabel = new Label("Logging in as");
        loggingInLabel.setStyle("-fx-font-size: 18; -fx-fill: rgb(30,30,36)");


        RadioButton opt1 = new RadioButton("Cashier");
        RadioButton opt2 = new RadioButton("Manager");
        RadioButton opt3 = new RadioButton("Administrator");
        opt1.setToggleGroup(optionGroup);
        opt2.setToggleGroup(optionGroup);
        opt3.setToggleGroup(optionGroup);

        VBox options = new VBox(opt1, opt2, opt3);
        options.setAlignment(Pos.CENTER_LEFT);

        VBox roleBox = new VBox(5, loggingInLabel, options);
        roleBox.setPadding(new Insets(20));
        roleBox.setStyle("-fx-background-color: rgb(255, 207, 153); -fx-padding: 20; -fx-border-color: rgb(17, 29, 74); -fx-max-width: 300");
        roleBox.setAlignment(Pos.CENTER);
        roleBox.setEffect(shadow);


        mainLayout = new VBox(20, loginHeader, titleBox, inputBox, roleBox, loginButton);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: rgb(255, 248, 240);");

    }
    public TextField getUsername() {
        return username;
    }

    public PasswordField getPassword() {
        return password;
    }

    public ToggleGroup getOptionGroup() {
        return optionGroup;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Parent getMainLayout() {
        return mainLayout;
    }

}