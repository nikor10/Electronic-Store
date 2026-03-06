package View.AdminViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import models.User;

import java.io.File;

public class ManageEmployeesView extends BorderPane {


    private TextField usernameField = new TextField();
    private final DatePicker dateOfBirthPicker = new DatePicker();

    public TextField getPositionField() {
        return positionField;
    }

    public TextField getAccessLevelField() {
        return accessLevelField;
    }

    public TextField getSalaryField() {
        return salaryField;
    }

    private TextField positionField = new TextField();
    private TextField accessLevelField = new TextField();
    private TextField salaryField = new TextField();
    private TextField emailField = new TextField();
    private TextField phoneField = new TextField();
    private TextField passwordField = new TextField();
    private TextField sectorField = new TextField();

    public TextField getSectorField() {
        return sectorField;
    }

    public Button getEditPassword() {
        return editPassword;
    }

    public Button getEditSector() {
        return editSector;
    }

    public Button getRegisterUser() {
        return registerUser;
    }

    public Button getEditUsername() {
        return editUsername;
    }

    public Button getEditBirthday() {
        return editBirthday;
    }

    public Button getEditPhoneNr() {
        return editPhoneNr;
    }

    public Button getEditEmail() {
        return editEmail;
    }

    private final Button registerUser = new Button("Register Employee");
    private final Button backBtn = new Button(" Back");

    private final Button editUsername = new Button();
    private final Button editBirthday=new Button();
    private final Button editEmail=new Button();
    private final Button editPosition =new Button();
    private final Button editAccessLevel=new Button();
    private final Button editPhoneNr=new Button();
    private final Button editSalary=new Button();
    private final Button editPassword=new Button();
    private final Button editSector=new Button();

    public ManageEmployeesView() {
        setPadding(new Insets(30));
        setStyle("-fx-background-color: rgb(255, 248, 240);");

        // Back Button
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(137,149,194); -fx-font-size: 17");
        Rectangle backBG = new Rectangle(65, 30);
        backBG.setFill(Color.rgb(32, 55, 140));
        backBG.setArcHeight(40);
        backBG.setArcWidth(40);
        StackPane backStack = new StackPane(backBG, backBtn);
        backStack.setAlignment(Pos.CENTER);
        backStack.setPrefWidth(100);

        // Title Bar
        Label title = new Label("Register Employees");
        title.setStyle("-fx-font-size: 35; -fx-text-fill: rgb(146,20,12);");
        HBox titleBar = new HBox(backStack, title);
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setSpacing(25);
        titleBar.setPadding(new Insets(10));
        titleBar.setStyle("-fx-background-color: rgb(17,29,74);");
        HBox titleRow = new HBox(backStack, title);
//      titleRow.setAlignment(Pos.CENTER_LEFT);
        titleRow.setSpacing(25);
        setTop(titleRow);

        GridPane infoGrid = new GridPane();
        infoGrid.setStyle("-fx-background-color: rgb(255,207,153)");
        infoGrid.setHgap(10);
        infoGrid.setVgap(10);
        infoGrid.setPadding(new Insets(20));
        infoGrid.setAlignment(Pos.CENTER);


        VBox buttonColumn = new VBox(15);

        usernameField = styleTextField(usernameField);
        passwordField = styleTextField(passwordField);
        emailField = styleTextField(emailField);
        phoneField = styleTextField(phoneField);
        dateOfBirthPicker.setStyle("-fx-background-color: transparent");
        dateOfBirthPicker.setEditable(true);
        positionField = styleTextField(positionField);
        salaryField = styleTextField(salaryField);
        accessLevelField = styleTextField(accessLevelField);
        sectorField= styleTextField(sectorField);


        Node[] textFields = {usernameField,sectorField, passwordField, dateOfBirthPicker, emailField, phoneField, positionField, accessLevelField, salaryField};
        String[] labels = {"Username:", "Password:", "Email:", "Date of birth:", "Phone nr:","Position:", "Sector","Access level:", "Salary:"};
        Button[] buttons = {editUsername, editPassword, editBirthday,editEmail, editPhoneNr,editSector, editSalary, editPosition, editAccessLevel};

        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            label.setStyle("-fx-font-size: 20");
            infoGrid.add(buttons[i], 0, i);
            infoGrid.add(label, 1, i);
            infoGrid.add(textFields[i], 2, i);
        }

        setCenter(infoGrid);

        setPadding(new Insets(10));
//        // Profile Image Upload Section
//        uploadImageButton.setStyle("-fx-background-color: rgb(17,29,74); -fx-text-fill: white;");
//        uploadImageButton.setOnAction(e -> uploadImage(this.getScene().getWindow()));
//
//        profileImageView.setFitHeight(100);
//        profileImageView.setFitWidth(100);
//
//        VBox imageSection = new VBox(profileImageView, uploadImageButton);
//        imageSection.setAlignment(Pos.CENTER);
//        imageSection.setSpacing(10);

        // Add the GridPane to the center
        setCenter(infoGrid);

        // Register Button
        registerUser.setStyle("-fx-background-color: rgb(146,20,12); -fx-text-fill: white; -fx-font-size: 22; -fx-font-weight: bold;");
        HBox buttonBox = new HBox(registerUser);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        // Add Register Button at the bottom
        VBox bottomSection = new VBox();
        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setSpacing(10);
        bottomSection.getChildren().addAll(buttonBox);
        setBottom(bottomSection);

    }

    public TextField styleTextField(TextField textField) {
        textField.setStyle("-fx-font-size: 18; -fx-background-color: transparent; -fx-border-color: black; -fx-font-style: italic;");
        textField.setEditable(true);
        return textField;
    }

    private void addProfileField(GridPane grid, String label, Node field, int rowIndex) {
        Label fieldLabel = new Label(label);
        fieldLabel.setStyle("-fx-font-size: 16; -fx-text-fill: black;");
        field.setStyle("-fx-background-color: rgb(255, 207, 153);");
        grid.addRow(rowIndex, fieldLabel, field);
    }

//    private void uploadImage(Window window) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
//
//        File selectedFile = fileChooser.showOpenDialog(this.getScene().getWindow());
//        if (selectedFile != null) {
//            profileImageView.setImage(new Image(selectedFile.toURI().toString()));
//        }
//    }
    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public DatePicker getDateOfBirthPicker() {
        return dateOfBirthPicker;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPhoneField() {
        return phoneField;
    }


    public Button getBackBtn() {
        return backBtn;
    }

    public Button getEditPosition() {
        return editPosition;
    }

    public Button getEditAccessLevel() {
        return editAccessLevel;
    }

    public Button getEditSalary() {
        return editSalary;
    }
}
