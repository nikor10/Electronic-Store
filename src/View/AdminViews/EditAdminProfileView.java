package View.AdminViews;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import javax.swing.*;

import static javafx.scene.paint.Color.rgb;

public class EditAdminProfileView extends BorderPane {
    private final Button updateButton = new Button("Update");
    private final Button editUsername = createButton();
    private final Button editPassword = createButton();
    private final Button editBirthday = createButton();
    private final Button editEmail = createButton();
    private final Button editPhoneNr = createButton();
    private final Button backBtn = new Button(" Back");
    private final Button logoutBtn = new Button("Logout");
    private TextField usernameTF = new TextField();
    private TextField passwordTF = new TextField();
    private DatePicker birthdateTF = new DatePicker();
    private TextField emailTF = new TextField();
    private TextField phonenrTF = new TextField();
    private final Button uploadImageBtn = new Button("Change profile image");

    public EditAdminProfileView() {
        setPadding(new Insets(10));
        setStyle("-fx-background-color: rgb(255, 248, 240);");

        // Back button setup
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: rgb(137,149,194); -fx-font-size: 17");
        Rectangle backBG = new Rectangle(65, 30);
        backBtn.setAlignment(Pos.CENTER);
        backBG.setFill(rgb(32,55,140));
        backBG.setArcHeight(40);
        backBG.setArcWidth(40);

        StackPane backStack = new StackPane(backBG, backBtn);
        backStack.setAlignment(Pos.CENTER);
        backStack.setPrefWidth(100);
        backStack.setPadding(new Insets(10));

        // Top bar
        Label title = new Label("Edit Admin Profile");
        title.setStyle("-fx-font-size: 30; -fx-text-fill: rgb(255,207,153);");
        title.setAlignment(Pos.CENTER);

        HBox titleHBox = new HBox(backStack, title);
        titleHBox.setAlignment(Pos.CENTER_LEFT);
        titleHBox.setSpacing(25);
        titleHBox.setPadding(new Insets(10));
        titleHBox.setStyle("-fx-background-color: rgb(17,29,74)");
        setTop(titleHBox);

        // Editable profile box
        GridPane infoGrid = new GridPane();
        infoGrid.setStyle("-fx-background-color: rgb(255,207,153)");
        infoGrid.setHgap(10);
        infoGrid.setVgap(10);
        infoGrid.setPadding(new Insets(20));

        VBox buttonColumn = new VBox(15);
        buttonColumn.getChildren().addAll(editUsername, editPassword, editBirthday, editEmail, editPhoneNr);

        usernameTF = styleTextField(usernameTF);
        passwordTF = styleTextField(passwordTF);
        emailTF = styleTextField(emailTF);
        phonenrTF = styleTextField(phonenrTF);
        birthdateTF.setStyle("-fx-background-color: transparent");
        birthdateTF.setEditable(false);

        Node[] textFields = {usernameTF, passwordTF, birthdateTF, emailTF, phonenrTF};
        String[] labels = {"Username:", "Password:", "Date of birth:", "Email:", "Phone nr:"};
        Button[] buttons = {editUsername, editPassword, editBirthday, editEmail, editPhoneNr};

        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            label.setStyle("-fx-font-size: 20");
            infoGrid.add(buttons[i], 0, i);
            infoGrid.add(label, 1, i);
            infoGrid.add(textFields[i], 2, i);
        }

        setPadding(new Insets(10));

        uploadImageBtn.setStyle("-fx-background-color: rgb(165, 42, 42); -fx-text-fill: white; -fx-font-size: 16;");
        updateButton.setStyle("-fx-background-color: rgb(165, 42, 42); -fx-text-fill: white; -fx-font-size: 16;");
        updateButton.setPrefWidth(100);

        HBox buttonRow = new HBox(15);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().addAll(uploadImageBtn, updateButton);

        VBox layout = new VBox(25);
        layout.getChildren().addAll(infoGrid, buttonRow);
        setCenter(layout);

        HBox editInfoBox = new HBox(25);
        editInfoBox.setPadding(new Insets(15));
        editInfoBox.setAlignment(Pos.CENTER);
    }

    public Button createButton() {
        ImageView icon = new ImageView(new Image("/View/Images/editIcon.png"));
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        Button button = new Button();
        button.setGraphic(icon);
        return button;
    }

    public TextField styleTextField(TextField textField) {
        textField.setStyle("-fx-font-size: 18; -fx-background-color: transparent; -fx-border-color: transparent; -fx-font-style: italic;");
        textField.setEditable(false);
        return textField;
    }

    public Button getEditUsername() {
        return editUsername;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getEditPassword() {
        return editPassword;
    }

    public Button getEditBirthday() {
        return editBirthday;
    }

    public Button getEditEmail() {
        return editEmail;
    }

    public Button getEditPhoneNr() {
        return editPhoneNr;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public Button getUploadImageBtn() {
        return uploadImageBtn;
    }

    public TextField getUsernameTF() {
        return usernameTF;
    }

    public TextField getPasswordTF() {
        return passwordTF;
    }

    public DatePicker getBirthdateTF() {
        return birthdateTF;
    }

    public TextField getEmailTF() {
        return emailTF;
    }

    public TextField getPhonenrTF() {
        return phonenrTF;
    }

    public Button getLogoutBtn() {
        return logoutBtn;
    }
}
