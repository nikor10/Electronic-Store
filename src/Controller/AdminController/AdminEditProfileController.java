package Controller.AdminController;

import View.AdminViews.EditAdminProfileView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Administrator;

import java.io.*;
import java.time.LocalDate;

public class AdminEditProfileController {
    private EditAdminProfileView view = new EditAdminProfileView();
   // private EditAdminProfileView view;
    private Administrator admin;
    private Stage adminView;
    private Stage stage;

    public AdminEditProfileController(Administrator admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Admin cannot be null");
        }
        this.admin = admin;
        this.adminView = adminView;
        this.view = new EditAdminProfileView();
        this.stage = new Stage();
        loadExistingData();
        setUpEventHandlers();
    }

//    public AdminEditProfileController(Stage primaryStage) {
//
//
//    }

    public EditAdminProfileView getView() {
        return view;
    }

    private void loadExistingData() {
        view.getUsernameTF().setText(admin.getUsername());
        view.getPasswordTF().setText(admin.getPassword());
        view.getBirthdateTF().setValue(admin.getBirthday());
        view.getEmailTF().setText(admin.getEmail());
        view.getPhonenrTF().setText(admin.getPhonenr());
    }

    private void setUpEventHandlers() {
        // Upload profile image button logic
        view.getUploadImageBtn().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow());

            if (selectedFile != null) {
                try {
                    Image newProfileImage = new Image(selectedFile.toURI().toString());
                    admin.setProfileImage(readImageBytes(selectedFile));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Edit fields logic
        view.getEditUsername().setOnAction(e -> makeFieldEditable(view.getUsernameTF()));
        view.getEditPassword().setOnAction(e -> makeFieldEditable(view.getPasswordTF()));
        view.getEditBirthday().setOnAction(e -> view.getBirthdateTF().requestFocus());
        view.getEditEmail().setOnAction(e -> makeFieldEditable(view.getEmailTF()));
        view.getEditPhoneNr().setOnAction(e -> makeFieldEditable(view.getPhonenrTF()));

        // Update button logic
        view.getUpdateButton().setOnAction(e -> saveProfileUpdates());

        // Back button logic
        view.getBackBtn().setOnAction(e -> navigateBackToAdminView());

    }

    private void saveProfileUpdates() {
        try {
            // Validate and save admin profile details
            String newUsername = view.getUsernameTF().getText();
            String newPassword = view.getPasswordTF().getText();
            LocalDate newDateOfBirth = view.getBirthdateTF().getValue();
            String newEmail = view.getEmailTF().getText();
            String newPhoneNr = view.getPhonenrTF().getText();

            // Validation checks
            if (newDateOfBirth == null || newDateOfBirth.getYear() < 1925 || newDateOfBirth.getYear() > 2009) {
                throw new IllegalArgumentException("Date of Birth must be between 1925 and 2009.");
            }

            if (!newPhoneNr.matches("\\d{10}")) {
                throw new IllegalArgumentException("Phone Number must be exactly 10 digits.");
            }

            // Save updates
            admin.setUsername(newUsername);
            admin.setPassword(newPassword);
            admin.setBirthday(newDateOfBirth);
            admin.setEmail(newEmail);
            admin.setPhonenr(newPhoneNr);

            disableEditableFields();
            saveAdminToStorage();

        } catch (IllegalArgumentException ex) {
            showErrorAlert("Input Error", ex.getMessage());
        }
    }

    private void navigateBackToAdminView() {

        // Create a new AdminProfileController and load its view
        AdminProfileController adminProfileController = new AdminProfileController(admin);

        // Set the scene back to AdminProfileView
        Scene scene = new Scene(adminProfileController.getView().getParent(), 800, 600);
        stage.setScene(scene);
        stage.setTitle("Admin Profile");
    }




    private void saveAdminToStorage() {
        // Save the admin object to persistent storage (e.g., a file or database)
        try (FileOutputStream fos = new FileOutputStream("admin_data.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(admin);
            System.out.println("Admin profile updated successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void makeFieldEditable(TextField field) {
        field.setEditable(true);
        field.requestFocus();
    }

    private void disableEditableFields() {
        view.getUsernameTF().setEditable(false);
        view.getPasswordTF().setEditable(false);
        view.getBirthdateTF().setEditable(false);
        view.getEmailTF().setEditable(false);
        view.getPhonenrTF().setEditable(false);
    }

    private byte[] readImageBytes(File file) throws IOException {
        try (InputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

