package Controller.CashierController;

import FileHandler.UserFileHandler;
import View.CashierView.EditProfileView;
import View.CashierView.ProfileView;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import models.Cashier;
import models.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EditProfileController {
    EditProfileView view = new EditProfileView();
    Cashier cashier;
    ProfileView profileView = new ProfileView();

    public EditProfileController(Cashier cashier) {
        if (cashier == null) {
            throw new IllegalArgumentException("Cashier cannot be null");
        }
        this.cashier = cashier;
        setUpEventHandlers();
        loadExistingData();
    }

    public ProfileView getProfileView() {
        return profileView;
    }

    private void loadExistingData() {

        view.getUsernameTF().setText(cashier.getUsername());
        view.getPasswordTF().setText(cashier.getPassword());
        view.getBirthdateTF().setValue(cashier.getBirthday());
        view.getEmailTF().setText(cashier.getEmail());
        view.getPhonenrTF().setText(cashier.getPhonenr());
    }

    private void setUpEventHandlers() {
        view.getUploadImageBtn().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(view.getScene().getWindow()); //hap popupin q t zgjedhesh foton

            if (selectedFile != null) {
                try {
                    Image newProfileImage = new Image(selectedFile.toURI().toString());
                    profileView.getProfileImage().setImage(newProfileImage);
                    InputStream inputStream = new FileInputStream(selectedFile);
                    byte[] imageBytes = inputStream.readAllBytes();
                    cashier.setProfileImage(imageBytes);
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        view.getEditUsername().setOnAction(e -> {
            view.getUsernameTF().setEditable(true);
            view.getUsernameTF().requestFocus();
        });

        view.getEditPassword().setOnAction(e -> {
            view.getPasswordTF().setEditable(true);
            view.getPasswordTF().requestFocus();
        });

        view.getEditBirthday().setOnAction(e -> {
            view.getBirthdateTF().setEditable(true); // Only necessary if DatePicker was set to non-editable
            view.getBirthdateTF().requestFocus();
        });

        view.getEditEmail().setOnAction(e -> {
            view.getEmailTF().setEditable(true);
            view.getEmailTF().requestFocus();
        });

        view.getEditPhoneNr().setOnAction(e -> {
            view.getPhonenrTF().setEditable(true);
            view.getPhonenrTF().requestFocus();
        });
        view.getUpdateButton().setOnAction(e -> saveProfileUpdates());

    }

    private void saveProfileUpdates() {
        ArrayList<Cashier> users = UserFileHandler.loadCashiers();
        for(User user : users) {
            if(user.getUsername().equals(cashier.getUsername())) {
                User temp = cashier;
                try{
                    String newUsername = view.getUsernameTF().getText();
                    String newPassword = view.getPasswordTF().getText();
                    LocalDate newDateOfBirth = view.getBirthdateTF().getValue();
                    String newEmail = view.getEmailTF().getText();
                    String newPhoneNr = view.getPhonenrTF().getText();

                    if (newDateOfBirth == null || newDateOfBirth.getYear() < 1925 || newDateOfBirth.getYear() > 2009) {
                        throw new IllegalArgumentException("Date of Birth must be between 1925 and 2009.");
                    }

                    if (!newPhoneNr.matches("\\d{10}")) {
                        throw new IllegalArgumentException("Phone Number must be exactly 10 digits.");
                    }
                    cashier.setUsername(newUsername);
                    cashier.setPassword(newPassword);
                    cashier.setBirthday(newDateOfBirth);
                    cashier.setEmail(newEmail);
                    cashier.setPhonenr(newPhoneNr);

                    view.getUsernameTF().setEditable(false);
                    view.getPasswordTF().setEditable(false);
                    view.getBirthdateTF().setEditable(false);
                    view.getEmailTF().setEditable(false);
                    view.getPhonenrTF().setEditable(false);
                    saveProfileImage(cashier);



                }catch (IllegalArgumentException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
                break;
            }

        }

    }

    public void saveProfileImage(Cashier cashier) {
        if (cashier.getProfileImage() != null) {
            String directory = "profile_images/"; // Directory to store profile images
            ensureDirectoryExists(directory); // Ensure the directory exists
            String filePath = directory + cashier.getUsername() + ".png"; // Filename based on username

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(cashier.getProfileImage()); // Write byte array to file
                System.out.println("Profile picture saved at: " + filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No profile image to save.");
        }
    }

    public void ensureDirectoryExists(String directory) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public EditProfileView getView() {
        return view;
    }
}
