package View.AdminViews;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class AdminProfileView extends BorderPane {

    private Label helloLabel = new Label();
    private final Label usernameLabel = new Label();
    private final Label positionLabel = new Label();
    private final Label birthDateLabel = new Label();

    public Label getEmailLabel() {
        return emailLabel;
    }

    public Label getHelloLabel() {
        return helloLabel;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Label getPositionLabel() {
        return positionLabel;
    }

    public Label getBirthDateLabel() {
        return birthDateLabel;
    }

    public Label getPhoneNrLabel() {
        return phoneNrLabel;
    }

    public Button getEditAdminProfile() {
        return editAdminProfile;
    }

    public Button getLogoutBtn() {
        return logoutBtn;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    private Label emailLabel = new Label();
    private Label phoneNrLabel = new Label();
    private final Button editAdminProfile = new Button("Edit Profile");
    private final Button logoutBtn = new Button("Logout");
    private final ImageView profileImage = new ImageView();

    public AdminProfileView() {
        HBox titleBar = new HBox(20);
        Label profile = new Label("Profile");
        profile.setStyle("-fx-font-size: 30; -fx-font-weight: bold; -fx-text-fill: rgb(255,207,153)");
        profile.setAlignment(Pos.BASELINE_CENTER);
        titleBar.setStyle("-fx-background-color: rgb(17,29,74)");
        titleBar.setPrefHeight(60);
        titleBar.getChildren().addAll(profile);
        titleBar.setAlignment(Pos.CENTER);


        helloLabel.setStyle("-fx-font-size: 20; -fx-text-fill: rgb(137,149,194);");
        helloLabel.setAlignment(Pos.CENTER);

        VBox title = new VBox();
        title.setAlignment(Pos.CENTER);
        title.setPrefHeight(50);
        title.getChildren().addAll(titleBar, helloLabel);
        setTop(title);

        profileImage.setPreserveRatio(true);
        profileImage.setFitHeight(150);
        profileImage.setFitWidth(150);
        Circle clip = new Circle(50, 50, 50); // Make the image round
        profileImage.setClip(clip);

        usernameLabel.setStyle("-fx-font-size: 20");
        positionLabel.setStyle("-fx-font-size: 20");
        birthDateLabel.setStyle("-fx-font-size: 20");

        VBox profileInfoLabels = new VBox(10);
        profileInfoLabels.getChildren().addAll(usernameLabel, positionLabel, birthDateLabel);

        HBox profileInfoBox = new HBox(15);
        profileInfoBox.setPadding(new Insets(30));
        profileInfoBox.setStyle("-fx-background-color: rgb(255,207,153)");
        profileInfoBox.setAlignment(Pos.CENTER_LEFT);
        profileInfoBox.getChildren().addAll(profileImage, profileInfoLabels);

        Line line = new Line();
        line.setStartX(0);
        line.setEndX(1500);
        line.setStrokeWidth(1);
        line.setStyle("-fx-stroke: black;");
        line.startXProperty().bind(this.widthProperty().divide(2)); // Center of the pane

        VBox mainLayout = new VBox(15);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().addAll(profileInfoBox, line);
        setCenter(mainLayout);

        // Set up additional info section
        phoneNrLabel.setStyle("-fx-font-size: 20");
        emailLabel.setStyle("-fx-font-size: 20");

        VBox extraInfoBox = new VBox(15);
        extraInfoBox.setStyle("-fx-font-size: 20; -fx-text-fill: black;");
        extraInfoBox.setAlignment(Pos.TOP_LEFT);
        extraInfoBox.getChildren().addAll(phoneNrLabel, emailLabel);

        HBox infoRow = new HBox(25);
        infoRow.setPadding(new Insets(15));
        infoRow.getChildren().addAll(extraInfoBox);
        mainLayout.getChildren().add(infoRow);

        // Set up buttons
        editAdminProfile.setPrefSize(125, 30);
        editAdminProfile.setStyle("-fx-background-color: #91140c ;-fx-text-fill: white; -fx-font-size: 20");


        logoutBtn.setPrefSize(125, 30);
        logoutBtn.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: rgb(255,207,153); -fx-font-size: 20");

        HBox buttonRow = new HBox(50);
        buttonRow.setPadding(new Insets(20));
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().addAll(editAdminProfile, logoutBtn);
        mainLayout.getChildren().add(buttonRow);
    }

    public void loadProfileImage(byte[] imageBytes) {
        if (imageBytes != null) {
            profileImage.setImage(new Image(new java.io.ByteArrayInputStream(imageBytes)));
        } else {
            profileImage.setImage(new Image(getClass().getResource("/View/Images/profileBoxIcon.png").toExternalForm()));
        }
    }

    public void addNavBar(HBox navBar)
    {
        setBottom(navBar);
    }
}

